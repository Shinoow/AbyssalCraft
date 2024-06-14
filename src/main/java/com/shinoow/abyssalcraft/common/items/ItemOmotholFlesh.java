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
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.knowledge.IResearchableItem;
import com.shinoow.abyssalcraft.api.knowledge.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOmotholFlesh extends ItemFood implements IResearchableItem {

	private IUnlockCondition condition = new DefaultCondition();

	public ItemOmotholFlesh(int par1, float par2, boolean par3) {
		super(par1, par2, par3);
		setTranslationKey("omotholflesh");
		setCreativeTab(ACTabs.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		world.playSound(entityPlayer, entityPlayer.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(EntityUtil.isPlayerCoralium(entityPlayer)){
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
		} else {
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300));
		}

		entityPlayer.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40));
		entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 40));
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
