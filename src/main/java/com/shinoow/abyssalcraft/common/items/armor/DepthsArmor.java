/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
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

public class DepthsArmor extends ItemArmor {
	public DepthsArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
	{
		if(stack.getItem() == AbyssalCraft.Depthshelmet || stack.getItem() == AbyssalCraft.Depthsplate || stack.getItem() == AbyssalCraft.Depthsboots)
			return "abyssalcraft:textures/armor/depths_1.png";

		if(stack.getItem() == AbyssalCraft.Depthslegs)
			return "abyssalcraft:textures/armor/depths_2.png";
		else return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.Depthshelmet)
		{
			player.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 200, 0));
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			player.addPotionEffect(new PotionEffect(Potion.field_76443_y.getId(), 200, 0));
		}
		if (itemstack.getItem() == AbyssalCraft.Depthsplate)
		{
			player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 200, 0));
		}
		if (itemstack.getItem() == AbyssalCraft.Depthslegs)
		{
			player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 200, 1));
		}
		if (itemstack.getItem() == AbyssalCraft.Depthsboots)
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 200, 0));
		}
	}
}