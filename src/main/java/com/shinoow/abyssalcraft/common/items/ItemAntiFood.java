/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAntiFood extends ItemFood implements IUnlockableItem {

	private IUnlockCondition condition = new DefaultCondition();

	public ItemAntiFood(String par1, boolean par2) {
		super(0, 0, par2);
		setUnlocalizedName(par1);
		setCreativeTab(ACTabs.tabFood);
	}

	public ItemAntiFood(String par1) {
		this(par1, true);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.getItem() == ACItems.rotten_anti_flesh)
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 600, 1));
		else if(itemStack.getItem() == ACItems.anti_spider_eye)
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 0));
		else entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 1));
	}

	@Override
	public Item setUnlockCondition(IUnlockCondition condition){
		this.condition = condition;
		return this;
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack){
		return condition;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public net.minecraft.client.gui.FontRenderer getFontRenderer(ItemStack stack)
	{
		return APIUtils.getFontRenderer(stack);
	}
}
