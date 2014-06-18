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

public class AbyssalniteCArmor extends ItemArmor {
	public AbyssalniteCArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if(stack.getItem() == AbyssalCraft.helmetC || stack.getItem() == AbyssalCraft.plateC || stack.getItem() == AbyssalCraft.bootsC)
			return "abyssalcraft:textures/armor/abyssalniteC_1.png";

		if(stack.getItem() == AbyssalCraft.legsC)
			return "abyssalcraft:textures/armor/abyssalniteC_2.png";
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
		if (itemstack.getItem() == AbyssalCraft.helmetC)
		{
			player.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 200, 1));
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			player.addPotionEffect(new PotionEffect(Potion.field_76443_y.getId(), 200, 0));
		}
		if (itemstack.getItem() == AbyssalCraft.plateC)
		{
			player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 200, 0));
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 200, 0));
		}
		if (itemstack.getItem() == AbyssalCraft.legsC)
		{
			player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 200, 0));
		}
		if (itemstack.getItem() == AbyssalCraft.bootsC)
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 200, 1));
		}
	}
}