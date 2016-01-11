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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoraliumPArmor extends ItemArmor {
	public ItemCoraliumPArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.GREEN + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if(stack.getItem() == AbyssalCraft.CorhelmetP || stack.getItem() == AbyssalCraft.CorplateP || stack.getItem() == AbyssalCraft.CorbootsP)
			return "abyssalcraft:textures/armor/coraliumP_1.png";

		if(stack.getItem() == AbyssalCraft.CorlegsP)
			return "abyssalcraft:textures/armor/coraliumP_2.png";
		else return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.CorhelmetP) {
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			if(player.getActivePotionEffect(AbyssalCraft.Cplague) !=null)
				player.removePotionEffect(AbyssalCraft.Cplague.getId());
		}
		if (itemstack.getItem() == AbyssalCraft.CorplateP) {
			List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(4D, 0.0D, 4D));

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
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 1));
			}
	}
}
