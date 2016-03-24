/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items.armor;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemCoraliumPArmor extends ItemArmor {
	public ItemCoraliumPArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		setUnlocalizedName(name);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.GREEN + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(stack.getItem() == AbyssalCraft.CorhelmetP || stack.getItem() == AbyssalCraft.CorplateP || stack.getItem() == AbyssalCraft.CorbootsP)
			return "abyssalcraft:textures/armor/coraliump_1.png";

		if(stack.getItem() == AbyssalCraft.CorlegsP)
			return "abyssalcraft:textures/armor/coraliump_2.png";
		else return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.CorhelmetP) {
			player.addPotionEffect(new PotionEffect(MobEffects.nightVision, 260, 0));
			if(player.getActivePotionEffect(AbyssalCraft.Cplague) !=null)
				player.removePotionEffect(AbyssalCraft.Cplague);
		}
		if (itemstack.getItem() == AbyssalCraft.CorplateP) {
			List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(4D, 0.0D, 4D));

			if (list != null)
				for (int k2 = 0; k2 < list.size(); k2++) {
					Entity entity = (Entity)list.get(k2);

					if (entity instanceof EntityLiving && !entity.isDead)
						entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 1);
					else if (entity instanceof EntityPlayer && !entity.isDead)
						entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 1);
				}
		}
		if (itemstack.getItem() == AbyssalCraft.CorbootsP)
			if(player.isInWater()){
				player.addPotionEffect(new PotionEffect(MobEffects.moveSpeed, 20, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.waterBreathing, 20, 1));
			}
	}
}
