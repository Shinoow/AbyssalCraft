/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.spell;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellUtils {

	/**
	 * Attempts to fetch a spell from an ItemStack
	 */
	@Nullable
	public static Spell getSpell(ItemStack stack){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return SpellRegistry.instance().getSpell(stack.getTagCompound().getString("Spell"));
	}

	/**
	 * Checks if the player has enough PE in their inventory
	 * @param player Player whose inventory to check
	 * @param req Target PE quanta
	 * @return Whether or not the player has enough PE
	 */
	private static boolean hasEnoughPE(EntityPlayer player, float req){
		ItemStack stack = player.getHeldItemOffhand();
		if(stack.getItem() instanceof IEnergyContainerItem
				&& ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= req)
			return true;
		else
			for(int i = 0; i < 9; i++){
				ItemStack stack1 = player.inventory.getStackInSlot(i);
				if(stack1.getItem() instanceof IEnergyContainerItem
						&& ((IEnergyContainerItem)stack1.getItem()).getContainedEnergy(stack1) >= req)
					return true;
			}
		return false;
	}

	/**
	 * Drains PE from a players inventory
	 * @param player Player to drain from
	 * @param energy Amount of energy to drain
	 */
	private static void drainPE(EntityPlayer player, float energy){
		ItemStack stack = player.getHeldItemOffhand();
		if(stack.getItem() instanceof IEnergyContainerItem
				&& ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= energy)
			((IEnergyContainerItem)stack.getItem()).consumeEnergy(stack, energy);
		else
			for(int i = 0; i < 9; i++){
				ItemStack stack1 = player.inventory.getStackInSlot(i);
				if(stack1.getItem() instanceof IEnergyContainerItem
						&& ((IEnergyContainerItem)stack1.getItem()).getContainedEnergy(stack1) >= energy){
					((IEnergyContainerItem)stack1.getItem()).consumeEnergy(stack1, energy);
					break;
				}
			}
	}

	/**
	 * Attempts to cast a charging spell
	 * @param stack Held ItemStack
	 * @param world Current World
	 * @param player Player attempting to cast the spell
	 */
	public static void castChargingSpell(ItemStack stack, World world, EntityPlayer player) {
		castChargingSpell(getSpell(stack), getScrollType(stack), world, player);
	}

	/**
	 * Attempts to cast a charging spell
	 * @param spell Spell begin cast (if present)
	 * @param scrollType Rarity of scroll casting spell
	 * @param world Current World
	 * @param player Player attempting to cast the spell
	 */
	public static void castChargingSpell(@Nullable Spell spell, ScrollType scrollType, World world, EntityPlayer player) {
		if(spell != null)
			if(spell.requiresCharging() && spell.canCastSpell(world, player.getPosition(), player, scrollType)
					&& hasEnoughPE(player, spell.getReqEnergy())){
				spell.castSpell(world, player.getPosition(), player, scrollType);
				drainPE(player, spell.getReqEnergy());
			}
	}

	/**
	 * Attempts to cast an instant spell
	 * @param stack Held ItemStack
	 * @param world Current World
	 * @param player Player attempting to cast the spell
	 * @param hand Current hand
	 */
	public static void castInstantSpell(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		castInstantSpell(getSpell(stack), getScrollType(stack), world, player, hand);
	}

	/**
	 * Attempts to cast an instant spell
	 * @param spell Spell begin cast (if present)
	 * @param scrollType Rarity of scroll casting spell
	 * @param world Current World
	 * @param player Player attempting to cast the spell
	 * @param hand Current hand
	 */
	public static void castInstantSpell(Spell spell, ScrollType scrollType, World world, EntityPlayer player, EnumHand hand) {
		if(spell != null)
			if(!spell.requiresCharging()){
				if(spell.canCastSpell(world, player.getPosition(), player, scrollType) && hasEnoughPE(player, spell.getReqEnergy())){
					spell.castSpell(world, player.getPosition(), player, scrollType);
					drainPE(player, spell.getReqEnergy());
				}
			} else{
				player.setActiveHand(hand);
				for(int i = 0; i < 3; i++)
					world.spawnParticle(EnumParticleTypes.FLAME, player.posX + (world.rand.nextDouble() - 0.5D)*player.width, player.posY + 1.0, player.posZ + (world.rand.nextDouble() - 0.5D)*player.width, 0, 0, 0);
			}
	}

	/**
	 * Checks if a player can hurt another player (assuming they are also a player)
	 */
	public static boolean canPlayerHurt(EntityPlayer player, Entity target)
	{
		if(!(target instanceof EntityPlayer)) return true;

		// Not trying to promote self-harm
		if(target == player) return true;

		EntityPlayer player2 = (EntityPlayer) target;

		if(player2.isCreative() || player2.isSpectator())
			return false;
		return player.canAttackPlayer(player2);
	}

	/**
	 * Attempts to fetch the color of a spell from an ItemStack
	 * returning a fallback on failure
	 */
	public static int getSpellColor(ItemStack stack) {
		Spell spell = getSpell(stack);
		return spell != null ? spell.getColor() : 16777215;
	}

	/**
	 * Attempts to fetch the scroll type of an ItemStack
	 */
	public static ScrollType getScrollType(ItemStack parchment) {
		if(!(parchment.getItem() instanceof IScroll)) return ScrollType.NONE;
		return ((IScroll) parchment.getItem()).getScrollType(parchment);
	}

	/**
	 * Performs a ray trace call to find an Entity<br>
	 * Target will ALWAYS be an EntityLivingBase, if found<br>
	 * CLIENT SIDE ONLY (will always be null server-side)
	 * @param dist Search distance
	 * @return A target EntityLivingBase, or null
	 */
	@Nullable
	public static RayTraceResult rayTraceTarget(float dist) {
		return AbyssalCraftAPI.getInternalMethodHandler().rayTraceTarget(dist);
	}

	/**
	 * Sends a message to the server to run the logic for the spell<br>
	 * on the Entity associated with the ID
	 * @param id Entity ID
	 * @param spell Spell ID
	 * @param scrollType Scroll Type on the scroll casting the spell
	 */
	public static void processEntitySpell(int id, String spell, ScrollType scrollType) {
		AbyssalCraftAPI.getInternalMethodHandler().processEntitySpell(id, spell, scrollType);
	}
}
