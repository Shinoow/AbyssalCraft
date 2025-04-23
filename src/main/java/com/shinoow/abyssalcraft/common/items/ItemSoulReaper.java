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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.item.ItemACSword;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemSoulReaper extends ItemACSword {

	static ToolMaterial sword = EnumHelper.addToolMaterial("soulreaper", 0, 2000, 1, 26, 0).setRepairItem(new ItemStack(ACItems.shadow_gem));

	public ItemSoulReaper(String par1Str){
		super(sword, par1Str);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(!worldIn.isRemote && isSelected && entityIn instanceof EntityLivingBase) {

			EntityLivingBase holder = (EntityLivingBase)entityIn;

			int souls = getSouls(stack);

			if(isWithin(souls, 60, 125)) {
				holder.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 0, false, false));
			} else if(isWithin(souls, 125, 250)) {
				holder.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 1, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 0, false, false));
			} else if(isWithin(souls, 250, 500)) {
				holder.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 1, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 0, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20, 0, false, false));
			} else if(isWithin(souls, 500, 1000)) {
				holder.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 2, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 1, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20, 0, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 0, false, false));
			} else if(souls == 1000) {
				holder.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 2, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 2, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20, 0, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 0, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 20, 0, false, false));
				holder.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 20, 2, false, false));
			}
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		// Increases souls on kill, but stops increasing at max
		if(target.getHealth() == 0 && getSouls(stack) < 1000)
			increaseSouls(stack);
		stack.damageItem(1, attacker);
		return true;
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		int souls = getSouls(is);
		l.add(I18n.format("tooltip.soulreaper") + ": " + souls + "/1000");
	}

	/**
	 * Num is greater or equal to min and less than max
	 */
	private boolean isWithin(int num, int min, int max) {
		return num >= min && num < max;
	}

	/** Increases the amount of souls by 1 */
	public void increaseSouls(ItemStack stack){
		setSouls(getSouls(stack) + 1, stack);
	}

	/** Sets the amount of souls */
	public void setSouls(int par1, ItemStack stack){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("souls", par1);
	}

	public int getSouls(ItemStack stack)
	{
		return stack.hasTagCompound() ? (int)stack.getTagCompound().getInteger("souls") : 0;
	}
}
