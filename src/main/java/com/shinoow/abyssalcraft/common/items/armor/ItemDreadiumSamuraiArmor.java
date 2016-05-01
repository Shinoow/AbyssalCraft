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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemDreadiumSamuraiArmor extends ItemArmor {
	public ItemDreadiumSamuraiArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		setUnlocalizedName(name);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem() == AbyssalCraft.dreadiumShelmet || stack.getItem() == AbyssalCraft.dreadiumSplate || stack.getItem() == AbyssalCraft.dreadiumSboots)
			return "abyssalcraft:textures/armor/dreadiums_1.png";

		if(stack.getItem() == AbyssalCraft.dreadiumSlegs)
			return "abyssalcraft:textures/armor/dreadiums_2.png";
		else return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		ModelBiped armorModel = new ModelBiped();
		if(itemStack != null){
			if(itemStack.getItem() instanceof ItemDreadiumSamuraiArmor){
				int type = ((ItemArmor)itemStack.getItem()).armorType;
				if(type == 1 || type == 3)
					armorModel = AbyssalCraft.proxy.getArmorModel(0);
				else
					armorModel = AbyssalCraft.proxy.getArmorModel(1);
			} if(armorModel != null){
				armorModel.bipedHead.showModel = armorSlot == 0;
				armorModel.bipedHeadwear.showModel = armorSlot == 0;
				armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
				armorModel.bipedRightArm.showModel = armorSlot == 1;
				armorModel.bipedLeftArm.showModel = armorSlot == 1;
				armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
				armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;
				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 :0;
				armorModel.swingProgress = entityLiving.swingProgress;
				if(entityLiving instanceof EntityPlayer){
					armorModel.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2 && ((EntityPlayer) entityLiving).getItemInUse().getItem().getItemUseAction(((EntityPlayer) entityLiving).getItemInUse()) == EnumAction.BOW;
					armorModel.heldItemRight = ((EntityPlayer) entityLiving).isBlocking() ? 3 : entityLiving.getEquipmentInSlot(0) != null ? 1 :0;
				}
				return armorModel;
			}
		}
		return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.dreadiumShelmet) {
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			if(player.getActivePotionEffect(AbyssalCraft.Dplague) !=null)
				player.removePotionEffect(AbyssalCraft.Dplague.getId());
		}
		if (itemstack.getItem() == AbyssalCraft.dreadiumSplate) {
			player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 20));
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20));
		}
		if (itemstack.getItem() == AbyssalCraft.dreadiumSlegs)
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 1));
		if (itemstack.getItem() == AbyssalCraft.dreadiumSboots)
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 1));

	}
}
