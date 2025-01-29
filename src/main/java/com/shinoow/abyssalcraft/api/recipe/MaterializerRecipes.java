/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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
import java.util.stream.Collectors;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MaterializerRecipes {

	private static final MaterializerRecipes materializerBase = new MaterializerRecipes();
	/** The list of materialization results. */
	private final List<Materialization> materializationList = new ArrayList<>();

	public static MaterializerRecipes instance()
	{
		return materializerBase;
	}

	private MaterializerRecipes(){}

	public void materialize(ItemStack[] input, ItemStack output){

		materialize(new Materialization(input, output));
	}

	public void materialize(Materialization materialization){
		materializationList.add(materialization);
	}

	/**
	 * Fetches a list of things that can be materialized based off of the Crystal Bag contents
	 * @param stack Crystal Bag to check for crystals
	 * @return A list of ItemStacks containing what can be materialized
	 */
	public List<ItemStack> getMaterializationResult(ItemStack stack){

		ItemStack[] inventory = extractItemsFromBag(stack);

		if(inventory == null) return Collections.emptyList();

		List<ItemStack> displayList = materializationList.stream().filter(m -> arrayContainsOtherArray(inventory, m.input)).map(m -> m.output.copy()).collect(Collectors.toList());

		return displayList;
	}

	/**
	 * Attempts to materialize an ItemStack
	 * @param output ItemStack to materialize
	 * @param bag Crystal Bag to use crystals from
	 */
	public void processMaterialization(ItemStack output, ItemStack bag){

		ItemStack[] inventory = extractItemsFromBag(bag);

		if(inventory == null) return;

		Materialization mat = getMaterializationFor(output);

		if(mat == null) return;

		int num = output.getCount();
		int count = 0;

		if(num > 1)
			while(count < num && consumeCrystals(inventory, mat.input.clone()))
				count++;
		else consumeCrystals(inventory, mat.input.clone());

		if(num == 1 || count == num)
			replaceBagContents(bag, inventory);
	}

	/**
	 * Helper method for consuming crystals from the inventory of a Crystal Bag
	 * @param inventory Crystal Bag Inventory
	 * @param recipe List of things to be consumed from the inventory
	 * @return True if the consumption was successful, otherwise false
	 */
	public boolean consumeCrystals(ItemStack[] inventory, ItemStack[] input) {

		ItemStack[] invTemp = inventory.clone();
		List<ItemStack> recipe = makeNonWriteThroughList(input);

		for(int i = 0; i < invTemp.length; i++)
			for(ItemStack recipeItem : recipe){
				ItemStack invItem = invTemp[i];
				if(APIUtils.areStacksEqual(invItem, recipeItem))
					if(invItem.getCount() >= recipeItem.getCount()){
						invItem.shrink(recipeItem.getCount());
						if(invItem.isEmpty()) invTemp[i] = ItemStack.EMPTY;
						recipe.remove(recipeItem);
						break;
					} else {
						recipeItem.shrink(invItem.getCount());
						invTemp[i] = ItemStack.EMPTY;
						break;
					}
			}

		if(recipe.isEmpty()) {
			inventory = invTemp;
			return true;
		}

		return recipe.isEmpty();
	}

	/**
	 * Fetches the materialization recipe for a specific ItemStack
	 * @param output Recipe output to find a recipe for
	 * @return A materialization recipe if one exists, otherwise null
	 */
	public Materialization getMaterializationFor(ItemStack output){
		return materializationList.stream().filter(m -> APIUtils.areStacksEqual(output, m.output)).findFirst().orElse(null);
	}

	/**
	 * Helper method for fetching the stored content of a Crystal Bag
	 * @param bag Crystal Bag to extract crystals from
	 * @return An array of ItemStacks (provided the bag has an inventory, and it's contents are only crystals), otherwise null
	 */
	public ItemStack[] extractItemsFromBag(ItemStack bag){

		ItemStack[] inventory = null;

		if(bag.getTagCompound() == null)
			bag.setTagCompound(new NBTTagCompound());
		if(bag.getTagCompound().hasKey("ItemInventory")){
			NBTTagList items = bag.getTagCompound().getTagList("ItemInventory", 10);

			inventory = new ItemStack[items.tagCount()];
			for (int i = 0; i < items.tagCount(); ++i)
			{
				NBTTagCompound item = items.getCompoundTagAt(i);
				//				byte slot = item.getByte("Slot");

				inventory[i] = new ItemStack(item);
			}
		}

		if(inventory == null) return null;

		for(ItemStack item : inventory)
			if(!APIUtils.isCrystal(item)) return null;

		return inventory;
	}

	/**
	 * Updates the contents of the Crystal Bag (after consuming crystals to materialize stuff)
	 * @param bag Crystal Bag ItemStack
	 * @param inventory New inventory content to replace the old
	 */
	private void replaceBagContents(ItemStack bag, ItemStack[] inventory){
		if(!bag.hasTagCompound())
			bag.setTagCompound(new NBTTagCompound());

		NBTTagList items = new NBTTagList();

		for(int i = 0; i < inventory.length; i++)
			if(!inventory[i].isEmpty()){
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				inventory[i].writeToNBT(item);

				items.appendTag(item);
			}

		bag.getTagCompound().setTag("ItemInventory", items);
	}

	/**
	 * Compares two arrays, checking if the first one contains the contents of the second
	 * @param array1 First array
	 * @param array2 Second array
	 * @return True if the first array contains the contents of the second, otherwise false
	 */
	private boolean arrayContainsOtherArray(ItemStack[] array1, ItemStack[] array2){
		List<ItemStack> inventory = makeNonWriteThroughList(array1);
		List<ItemStack> recipe = makeNonWriteThroughList(array2);

		if(inventory.size() >= recipe.size())
			for(ItemStack invItem : inventory)
				for(ItemStack recipeItem : recipe)
					if(APIUtils.areStacksEqual(invItem, recipeItem))
						if(invItem.getCount() >= recipeItem.getCount()){
							invItem.shrink(recipeItem.getCount());
							if(invItem.isEmpty()) invItem = ItemStack.EMPTY;
							recipe.remove(recipeItem);
							break;
						} else {
							recipeItem.shrink(invItem.getCount());
							invItem = ItemStack.EMPTY;
							break;
						}

		return recipe.isEmpty();
	}

	private List<ItemStack> makeNonWriteThroughList(ItemStack[] array){
		ItemStack[] inputTmp = new ItemStack[array.length];
		for(int i = 0; i < array.length; i++)
			inputTmp[i] = array[i].copy();

		return new ArrayList<>(Arrays.asList(inputTmp));
	}

	/**
	 * Removes Materialization(s) based on the output
	 * @param output ItemStack to check for
	 */
	public void removeMaterialization(ItemStack output) {
		materializationList.removeIf(e -> APIUtils.areStacksEqual(output, e.output));
	}

	public List<Materialization> getMaterializationList()
	{
		return materializationList;
	}
}
