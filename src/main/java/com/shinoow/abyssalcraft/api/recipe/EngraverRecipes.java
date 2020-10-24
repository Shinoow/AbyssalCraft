/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.recipe;

import java.util.*;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class EngraverRecipes {

	private static final EngraverRecipes engravingBase = new EngraverRecipes();

	private final List<ItemStack> coins = new ArrayList<>();
	private final Map<ItemEngraving, ItemStack> engravings = new HashMap<>();
	private final Map<ItemStack, ItemStack> engravingList = new HashMap<>();
	private final Map<ItemStack, Float> experienceList = new HashMap<>();

	public static EngraverRecipes instance()
	{
		return engravingBase;
	}

	private EngraverRecipes(){}

	public void addCoin(Item coin){
		addCoin(new ItemStack(coin, 1, OreDictionary.WILDCARD_VALUE));
	}

	public void addCoin(ItemStack coin){
		coins.add(coin);
	}

	public void addEngraving(Item coin, ItemEngraving engraving, float xp){
		addEngraving(new ItemStack(coin), engraving, xp);
	}

	public void addEngraving(ItemStack coin, ItemEngraving engraving, float xp){
		engravings.put(engraving, coin);
		engravingList.put(new ItemStack(engraving), coin);
		experienceList.put(coin, xp);
	}

	/**
	 * Returns the engraving result of an item. This method doesn't really do much
	 */
	public ItemStack getEngravingResult(ItemStack stack)
	{
		return coins.stream().filter(i -> areStacksEqual(stack, i)).findFirst().orElse(ItemStack.EMPTY);
	}

	/**
	 * Returns the engraving result of an item.
	 */
	public ItemStack getEngravingResult(ItemStack par2, ItemEngraving par1)
	{
		for(ItemStack stack : coins)
			if(areStacksEqual(par2, stack))
				if(engravings.get(par1) != null)
					if(par2.getItem() != ACItems.coin && par1 == ACItems.blank_engraving ||
					par2.getItem() == ACItems.coin && par1 != ACItems.blank_engraving)
						return engravings.get(par1);

		return ItemStack.EMPTY;
	}

	private boolean areStacksEqual(ItemStack stack1, ItemStack stack2)
	{
		return (stack2.getItem() == stack1.getItem() || stack1.getItem() == ACItems.coin) && (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == stack1.getItemDamage());
	}

	/**
	 * Returns the coin list
	 */
	public List<ItemStack> getCoinList(){
		return coins;
	}

	/**
	 * Returns the actual engraving list, with engravings and coins
	 */
	public Map<ItemEngraving, ItemStack> getEngravings(){
		return engravings;
	}

	/**
	 * Returns a ItemStack version of the engraving list
	 */
	public Map<ItemStack, ItemStack> getEngravingList(){
		return engravingList;
	}

	public float getExperience(ItemStack stack)
	{
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1) return ret;

		return experienceList.entrySet().stream()
				.filter(e -> areStacksEqual(stack, e.getKey()))
				.map(e -> e.getValue())
				.findFirst()
				.orElse(0.0F);
	}
}
