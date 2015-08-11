/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.recipe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.common.items.ItemCrystalBag;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

public class MaterializerRecipes {

	private static final MaterializerRecipes materializerBase = new MaterializerRecipes();
	/** The list of materialization results. */
	private Map<ItemStack[], ItemStack> materializationList = Maps.newHashMap();
	private Map<ItemStack, Float> experienceList = Maps.newHashMap();
	private Map<ItemStack, Integer> levelList = Maps.newHashMap();

	public static MaterializerRecipes instance()
	{
		return materializerBase;
	}

	private MaterializerRecipes()
	{

	}

	public void materialize(ItemStack[] input, ItemStack output, int level){

		for(ItemStack item : input)
			if(!APIUtils.isCrystal(item)) throw new ClassCastException("All of the input items has to be Crystals!");
		materializationList.put(input, output);
		levelList.put(output, level);
		experienceList.put(output, Float.valueOf(level == 0 ? 0.25F : level/2));
	}

	/**
	 * Returns the materialization result of an item.
	 */
	public ItemStack getMaterializationResult(ItemStack par1ItemStack)
	{
		Iterator<?> iterator = materializationList.entrySet().iterator();
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

	public ItemStack getMaterializationResult(ItemStack stack, int book){

		NBTTagList items = new NBTTagList();
		ItemStack[] inventory = null;

		if(stack.getItem() instanceof ItemCrystalBag){
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			if(stack.stackTagCompound.hasKey("ItemInventory", 10)){
				items = stack.stackTagCompound.getTagList("ItemInventory", 10);

				inventory = new ItemStack[items.tagCount()];
				for (int i = 0; i < items.tagCount(); ++i)
				{
					NBTTagCompound item = items.getCompoundTagAt(i);
					byte slot = item.getByte("Slot");

					inventory[slot] = ItemStack.loadItemStackFromNBT(item);
				}
			}

			Iterator<Entry<ItemStack[], ItemStack>> iterator = materializationList.entrySet().iterator();
			Entry<ItemStack[], ItemStack> entry;

			do
			{
				if(!iterator.hasNext())
					return null;

				entry = iterator.next();
			}
			while(!arrayContainsOtherArray(inventory, entry.getKey()));

			return entry.getValue();

		}

		return null;
	}

	private boolean areStacksEqual(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return par2ItemStack.getItem() == par1ItemStack.getItem() && (par2ItemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE || par2ItemStack.getItemDamage() == par1ItemStack.getItemDamage());
	}

	private boolean arrayContainsOtherArray(ItemStack[] array1, ItemStack[] array2){
		List<ItemStack> inventory = Lists.newArrayList(array1);
		List<ItemStack> recipe = Lists.newArrayList(array2);

		if(inventory.size() >= recipe.size())
			for(ItemStack invItem : inventory)
				for(ItemStack recipeItem : recipe)
					if(areStacksEqual(invItem, recipeItem) && invItem.stackSize >= recipeItem.stackSize){
						recipe.remove(recipeItem);
						break;
					}

		return recipe.isEmpty();
	}

	public Map<ItemStack[], ItemStack> getMaterializationList()
	{
		return materializationList;
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
