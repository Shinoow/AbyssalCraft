/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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

	/** Increases the amount of souls by 1 */
	public int increaseSouls(ItemStack par1ItemStack){
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.setTagCompound(new NBTTagCompound());
		par1ItemStack.getTagCompound().setInteger("souls", getSouls(par1ItemStack) + 1);
		return getSouls(par1ItemStack);
	}

	/** Sets the amount of souls */
	public int setSouls(int par1, ItemStack par2ItemStack){
		par2ItemStack.getTagCompound().setInteger("souls", par1);
		return getSouls(par2ItemStack);
	}

	public int getSouls(ItemStack par1ItemStack)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("souls") ? (int)par1ItemStack.getTagCompound().getInteger("souls") : 0;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		if(par2EntityLivingBase.getHealth() == 0){
			increaseSouls(par1ItemStack);
			switch(getSouls(par1ItemStack)){
			case 32:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 64:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 128:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 1));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 256:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2400, 1));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 512:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 2));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 1));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 300));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 1024:
				setSouls(0, par1ItemStack);
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2400, 2));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, 2400, 2));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 300));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 2400, 2));
				par3EntityLivingBase.heal(20.0F);
				break;
			}
			return true;
		}
		return true;
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		int souls = getSouls(is);
		l.add(I18n.format("tooltip.soulreaper") + ": " + souls + "/1024");
	}
}
