/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

public class AbyssalCraftTool extends Item {

	private float weaponDamage;
	public AbyssalCraftTool() {
		super();
		maxStackSize = 1;
		weaponDamage = 500000;
		setUnlocalizedName("devsword");
		setCreativeTab(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B) {
		l.add(I18n.translateToLocal("tooltip.devblade.1"));
		l.add(I18n.translateToLocal("tooltip.devblade.2"));
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.BLOCK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 0x11940;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand) {

		par3EntityPlayer.setActiveHand(hand);

		List list = par3EntityPlayer.world.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.getEntityBoundingBox().expand(40D, 40D, 40D));

		if(list != null)
			for(int k2 = 0; k2 < list.size(); k2++) {
				Entity entity = (Entity)list.get(k2);

				if(entity instanceof EntityLiving && !entity.isDead)
					entity.attackEntityFrom(DamageSource.causePlayerDamage(par3EntityPlayer), 50000);
				else if(entity instanceof EntityPlayer && !entity.isDead)
					entity.attackEntityFrom(DamageSource.causePlayerDamage(par3EntityPlayer), 50000);
				if(entity instanceof EntityJzahar) {
					par3EntityPlayer.setGameType(GameType.SURVIVAL);
					par3EntityPlayer.attackTargetEntityWithCurrentItem(par3EntityPlayer);
					((EntityJzahar)entity).heal(Float.MAX_VALUE);
					if(par2World.isRemote)
						Minecraft.getMinecraft().player.sendChatMessage("I really thought I could do that, didn't I?");
				}
			}
		return new ActionResult(EnumActionResult.PASS, par3EntityPlayer.getHeldItem(hand));
	}

	@Override
	public boolean canHarvestBlock(IBlockState state) {
		return state.getBlock() == Blocks.WEB;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Multimap getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap multimap = super.getAttributeModifiers(equipmentSlot, stack);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", weaponDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
		}

		return multimap;
	}
}
