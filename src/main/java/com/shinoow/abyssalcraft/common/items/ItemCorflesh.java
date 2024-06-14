/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.IResearchableItem;
import com.shinoow.abyssalcraft.api.knowledge.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;
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

public class ItemCorflesh extends ItemFood implements IResearchableItem {

	private IUnlockCondition condition = new DefaultCondition();

	public ItemCorflesh(int j, float f, boolean b, String name) {
		super(j, f, b);
		setTranslationKey(name);
		setCreativeTab(ACTabs.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.getItem() == ACItems.anti_plagued_flesh){
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 0));
		} else {
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 1));
			if(!EntityUtil.isPlayerCoralium(entityPlayer))
				entityPlayer.addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 600, 0));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 0));
		}
	}

	@Override
	public Item setResearchItem(IUnlockCondition condition) {
		this.condition = condition;
		return this;
	}

	@Override
	public IUnlockCondition getResearchItem(ItemStack stack) {

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
