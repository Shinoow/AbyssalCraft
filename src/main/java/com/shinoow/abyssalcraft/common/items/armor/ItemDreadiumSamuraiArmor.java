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
package com.shinoow.abyssalcraft.common.items.armor;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDreadiumSamuraiArmor extends ItemACArmor {
	public ItemDreadiumSamuraiArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
		super(par2EnumArmorMaterial, par3, par4, name);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer) {
		if(stack.getItem() == ACItems.dreadium_samurai_helmet || stack.getItem() == ACItems.dreadium_samurai_chestplate || stack.getItem() == ACItems.dreadium_samurai_boots)
			return "abyssalcraft:textures/armor/dreadiums_1.png";

		if(stack.getItem() == ACItems.dreadium_samurai_leggings)
			return "abyssalcraft:textures/armor/dreadiums_2.png";
		else return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped armorModel) {
		if(itemStack != null){
			if(itemStack.getItem() instanceof ItemDreadiumSamuraiArmor){
				EntityEquipmentSlot type = ((ItemArmor)itemStack.getItem()).armorType;
				if(type == EntityEquipmentSlot.FEET || type == EntityEquipmentSlot.CHEST)
					armorModel = AbyssalCraft.proxy.getArmorModel(0);
				else
					armorModel = AbyssalCraft.proxy.getArmorModel(1);
			} if(armorModel != null){
				armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedBody.showModel = armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.LEGS;
				armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedRightLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;
				armorModel.bipedLeftLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;
				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.rightArmPose = entityLiving.getHeldItemMainhand() != null ? entityLiving.getHeldItemMainhand().getItemUseAction() == EnumAction.BLOCK && entityLiving.getItemInUseCount() > 0 ? ArmPose.BLOCK : entityLiving.getHeldItemMainhand().getItemUseAction() == EnumAction.BOW && entityLiving.getItemInUseCount() > 0 ? ArmPose.BOW_AND_ARROW : ArmPose.ITEM : ArmPose.EMPTY;
				armorModel.leftArmPose = entityLiving.getHeldItemOffhand() != null ? entityLiving.getHeldItemOffhand().getItemUseAction() == EnumAction.BLOCK && entityLiving.getItemInUseCount() > 0 ? ArmPose.BLOCK : entityLiving.getHeldItemMainhand() != null && entityLiving.getHeldItemMainhand().getItemUseAction() == EnumAction.BOW && entityLiving.getItemInUseCount() > 0 ? ArmPose.BOW_AND_ARROW : ArmPose.ITEM : ArmPose.EMPTY;
				armorModel.swingProgress = entityLiving.swingProgress;

				return armorModel;
			}
		}
		return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if(world.isRemote || !ACConfig.armorPotionEffects) return;
		if (itemstack.getItem() == ACItems.dreadium_samurai_helmet) {
			if(world.provider.isSurfaceWorld())
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 260, 0, false, false));
			if(player.isPotionActive(AbyssalCraftAPI.dread_plague))
				player.removePotionEffect(AbyssalCraftAPI.dread_plague);
		}
		if (itemstack.getItem() == ACItems.dreadium_samurai_chestplate) {
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20, 0, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 0, false, false));
		}
		if (itemstack.getItem() == ACItems.dreadium_samurai_leggings)
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 1, false, false));
		if (itemstack.getItem() == ACItems.dreadium_samurai_boots)
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 1, false, false));

	}
}
