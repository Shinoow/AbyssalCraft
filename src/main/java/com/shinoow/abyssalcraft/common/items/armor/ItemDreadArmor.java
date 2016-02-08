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
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemDreadArmor extends ItemArmor {
	public ItemDreadArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		//		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem() == AbyssalCraft.helmetD || stack.getItem() == AbyssalCraft.plateD || stack.getItem() == AbyssalCraft.bootsD)
			return "abyssalcraft:textures/armor/dread_1.png";

		if(stack.getItem() == AbyssalCraft.legsD)
			return "abyssalcraft:textures/armor/dread_2.png";
		else return null;
	}

	//	@Override
	//	@SideOnly(Side.CLIENT)
	//	public void registerIcons(IIconRegister par1IconRegister)
	//	{
	//		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	//	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.helmetD)
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
		if (itemstack.getItem() == AbyssalCraft.plateD) {
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 3));
			List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.getCollisionBoundingBox().expand(4D, 0.0D, 4D));
			if (list != null)
				for (int k2 = 0; k2 < list.size(); k2++) {
					Entity entity = (Entity)list.get(k2);
					if (entity instanceof EntityLiving && !entity.isDead)
						entity.setFire(99);
					else if (entity instanceof EntityPlayer && !entity.isDead)
						entity.setFire(99);
				}
		}
		if (itemstack.getItem() == AbyssalCraft.legsD)
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 3));
		if (itemstack.getItem() == AbyssalCraft.bootsD)
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 3));
	}
}
