/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
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

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
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
		for(int i = 0; i < 9; i++){
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() instanceof IEnergyContainerItem
					&& ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= req)
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
		for(int i = 0; i < 9; i++){
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() instanceof IEnergyContainerItem
					&& ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= energy){
				((IEnergyContainerItem)stack.getItem()).consumeEnergy(stack, energy);
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
		Spell spell = getSpell(stack);
		if(spell != null)
			if(spell.requiresCharging() && spell.canCastSpell(world, player.getPosition(), player)
					&& hasEnoughPE(player, spell.getReqEnergy())){
				spell.castSpell(world, player.getPosition(), player);
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
		Spell spell = getSpell(stack);
		if(spell != null)
			if(!spell.requiresCharging()){
				if(spell.canCastSpell(world, player.getPosition(), player) && hasEnoughPE(player, spell.getReqEnergy())){
					spell.castSpell(world, player.getPosition(), player);
					drainPE(player, spell.getReqEnergy());
				}
			} else{
				player.setActiveHand(hand);
				for(int i = 0; i < 3; i++)
					world.spawnParticle(EnumParticleTypes.FLAME, player.posX + (world.rand.nextDouble() - 0.5D)*player.width, player.posY + 1.0, player.posZ + (world.rand.nextDouble() - 0.5D)*player.width, 0, 0, 0);
			}
	}
}
