/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDreadiumSamuraiArmor extends ItemArmor {
	public ItemDreadiumSamuraiArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
		par2EnumArmorMaterial.customCraftingMaterial = AbyssalCraft.dreadplate;
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem() == AbyssalCraft.dreadiumShelmet || stack.getItem() == AbyssalCraft.dreadiumSplate || stack.getItem() == AbyssalCraft.dreadiumSboots)
			return "abyssalcraft:textures/armor/dreadiumS_1.png";

		if(stack.getItem() == AbyssalCraft.dreadiumSlegs)
			return "abyssalcraft:textures/armor/dreadiumS_2.png";
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
				if(entityLiving instanceof EntityPlayer)
					armorModel.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2;
					return armorModel;
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
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
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 1));
		}
		if (itemstack.getItem() == AbyssalCraft.dreadiumSlegs)
			player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 20, 0));
		if (itemstack.getItem() == AbyssalCraft.dreadiumSboots)
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 1));

	}
}