/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.api.recipe;

import java.util.*;
import java.util.Map.Entry;

import com.shinoow.abyssalcraft.api.item.ItemEngraving;

import net.minecraft.item.*;
import net.minecraftforge.oredict.OreDictionary;

public class EngraverRecipes {

	private static final EngraverRecipes engravingBase = new EngraverRecipes();
	/** The list of engraving results. */
	private Map<ItemStack, ItemStack> engravingList = new HashMap<ItemStack, ItemStack>();
	private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();
	/** List of Engraving Templates with Associated Engraved Coins */
	private Map<ItemEngraving, ItemStack> engravingOutputs = new HashMap<ItemEngraving, ItemStack>();
	private Map<ItemEngraving, ItemStack> engravingInputs = new HashMap<ItemEngraving, ItemStack>();

	public static EngraverRecipes engraving()
	{
		return engravingBase;
	}

	private EngraverRecipes(){}

	public void engrave(Item input, ItemStack output, ItemEngraving engraving, float xp)
	{
		engrave(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, engraving, xp);
	}

	public void engrave(ItemStack input, ItemStack output, ItemEngraving engraving, float xp)
	{
		engravingList.put(input, output);
		experienceList.put(output, Float.valueOf(xp));
		engravingOutputs.put(engraving, output);
		engravingInputs.put(engraving, input);
	}

	/**
	 * Returns the engraving result of an item.
	 */
	public ItemStack getEngravingResult(ItemStack par1ItemStack)
	{
		Iterator<?> iterator = engravingList.entrySet().iterator();
		Entry<?, ?> entry;

		do
		{
			if (!iterator.hasNext())
				return null;

			entry = (Entry<?, ?>)iterator.next();
		}
		while (!areStacksEqual(par1ItemStack, (ItemStack)entry.getKey()));

		return (ItemStack)entry.getValue();
	}

	/**
	 * Returns the engraving result of an item.
	 */
	public ItemStack getEngravingResult(ItemStack par1ItemStack, ItemEngraving par2Engraving)
	{
		ItemEngraving engraving = getEngravingTemplate(par1ItemStack, par2Engraving);
		Iterator<?> iterator = engravingInputs.entrySet().iterator();
		Entry<?, ?> entry;

		do
		{
			if (!iterator.hasNext())
				return null;

			entry = (Entry<?, ?>)iterator.next();
		}
		while (!areEngravingsEqual(engraving, (ItemEngraving)entry.getKey()));

		return (ItemStack)entry.getValue();
	}

	public ItemEngraving getEngravingTemplate(ItemStack par1ItemStack, ItemEngraving engraving)
	{
		Iterator<?> iterator = engravingInputs.entrySet().iterator();
		Entry<?, ?> entry;

		do
		{
			if (!iterator.hasNext())
				return null;

			entry = (Entry<?, ?>)iterator.next();
		}
		while (!areStacksEqual(par1ItemStack, (ItemStack)entry.getValue()));

		return (ItemEngraving)entry.getKey() == engraving ? engraving : null;
	}

	private boolean areStacksEqual(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return par2ItemStack.getItem() == par1ItemStack.getItem() && (par2ItemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE || par2ItemStack.getItemDamage() == par1ItemStack.getItemDamage());
	}

	private boolean areEngravingsEqual(ItemEngraving par1, ItemEngraving par2){
		return par1 == par2;
	}

	public Map<ItemStack, ItemStack> getEngravingList()
	{
		return engravingList;
	}

	public Map<ItemEngraving, ItemStack> getEngravingTemplates(){
		return engravingOutputs;
	}

	public float getExperience(ItemStack par1ItemStack)
	{
		float ret = par1ItemStack.getItem().getSmeltingExperience(par1ItemStack);
		if (ret != -1) return ret;

		Iterator<?> iterator = experienceList.entrySet().iterator();
		Entry<?, ?> entry;

		do
		{
			if (!iterator.hasNext())
				return 0.0F;

			entry = (Entry<?, ?>)iterator.next();
		}
		while (!areStacksEqual(par1ItemStack, (ItemStack)entry.getKey()));

		return ((Float)entry.getValue()).floatValue();
	}
}