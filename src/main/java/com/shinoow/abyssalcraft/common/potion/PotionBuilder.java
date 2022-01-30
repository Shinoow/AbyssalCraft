/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.potion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBuilder {

	private static final ResourceLocation POTION_TEXTURE = new ResourceLocation("abyssalcraft:textures/misc/potionFX.png");

	private String name;
	private boolean isBadEffect;
	private int potionColor, iconIndexA, iconIndexB, statusIconIndex;
	private BiFunction<Integer, Integer, Boolean> readyFunction;
	private BiConsumer<EntityLivingBase, Integer> effectFunction;
	private Optional<List<ItemStack>> curativeItems = Optional.empty();

	public PotionBuilder(boolean isBadEffect, int potionColor) {
		this.isBadEffect = isBadEffect;
		this.potionColor = potionColor;
	}

	public PotionBuilder setIconIndex(int iconIndexA, int iconIndexB) {
		this.iconIndexA = iconIndexA;
		this.iconIndexB = iconIndexB;

		return this;
	}

	public PotionBuilder setStatusIconIndex(int statusIconIndex) {
		this.statusIconIndex = statusIconIndex;

		return this;
	}

	public PotionBuilder setReadyFunction(BiFunction<Integer, Integer, Boolean> readyFunction) {
		this.readyFunction = readyFunction;

		return this;
	}

	public PotionBuilder setEffectFunction(BiConsumer<EntityLivingBase, Integer> effectFunction) {
		this.effectFunction = effectFunction;

		return this;
	}

	public PotionBuilder setPotionName(String name) {
		this.name = name;

		return this;
	}

	public PotionBuilder setCurativeItems(ItemStack...curativeItem) {
		curativeItems = Optional.of(Arrays.asList(curativeItem));

		return this;
	}

	public Potion build() {
		return new Potion(isBadEffect, potionColor) {

			@Override
			public Potion setIconIndex(int par1, int par2) {
				super.setIconIndex(par1, par2);
				return this;
			}

			@Override
			public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier){
				effectFunction.accept(entityLivingBaseIn, amplifier);
			}

			@Override
			public boolean isReady(int duration, int amplifier)
			{
				return readyFunction.apply(duration, amplifier);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public int getStatusIconIndex()
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(POTION_TEXTURE);
				return statusIconIndex;
			}

			@Override
			public List<ItemStack> getCurativeItems()
			{
				return curativeItems.orElse(super.getCurativeItems());
			}

		}.setIconIndex(iconIndexA, iconIndexB).setPotionName(name);
	}
}
