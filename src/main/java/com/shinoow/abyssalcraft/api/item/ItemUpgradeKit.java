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
package com.shinoow.abyssalcraft.api.item;

import java.util.List;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Use this class to create Upgrade Kits for tools/armor.
 * You will need to create a crafting recipe for the upgrade:
 * GameRegistry.addRecipe(new ItemStack(NewItem, 1),  new Object [] {"#", "@", '#', OldItem, '@', UpgradeKit);
 *
 * @author shinoow
 *
 * @since 1.0
 */
public class ItemUpgradeKit extends Item implements IUnlockableItem {

	private IUnlockCondition condition = new DefaultCondition();

	public final String typeName;
	public final String typeName2;

	/**
	 * The Strings are only for display.
	 * Remember to create a crafting recipe for the upgrade:
	 * GameRegistry.addRecipe(new ItemStack(NewItem, 1),  new Object [] {"#", "@", '#', OldItem, '@', UpgradeKit);
	 * @param par2Str The old material
	 * @param par3Str The new material
	 */
	public ItemUpgradeKit(String par2Str, String par3Str){
		super();
		typeName = par2Str;
		typeName2 = par3Str;
		maxStackSize = 16;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		l.add(typeName + " To " + typeName2);
	}

	@Override
	public Item setUnlockCondition(IUnlockCondition condition) {
		this.condition = condition;
		return this;
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack) {

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
