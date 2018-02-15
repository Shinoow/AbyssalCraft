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
package com.shinoow.abyssalcraft.api;

import java.util.List;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.recipe.EngraverRecipes;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Utilities for the AbyssalCraft API
 * @author shinoow
 *
 * @since 1.4
 */
public class APIUtils {

	/**
	 * Checks if the ItemStack is a Crystal
	 * @param item ItemStack to check
	 * @return True if the ItemStack is a Crystal, otherwise false
	 *
	 * @since 1.4
	 */
	public static boolean isCrystal(ItemStack item){
		if(item.getItem() instanceof ICrystal)
			return true;
		for(ItemStack crystal: AbyssalCraftAPI.getCrystals())
			if(crystal.getItem() == item.getItem() && (crystal.getItemDamage() == OreDictionary.WILDCARD_VALUE
			|| crystal.getItemDamage() == item.getItemDamage()))
				return true;
		return false;
	}

	/**
	 * Checks if the ItemStack is a Coin
	 * @param item ItemStack to check
	 * @return True if the ItemStack is a Coin, otherwise false
	 *
	 * @since 1.5
	 */
	public static boolean isCoin(ItemStack item){
		for(ItemStack coin : EngraverRecipes.instance().getCoinList())
			if(coin.getItem() == item.getItem() && (coin.getItemDamage() == OreDictionary.WILDCARD_VALUE
			|| coin.getItemDamage() == item.getItemDamage()))
				return true;
		return false;
	}

	/**
	 * Converts an Object to an ItemStack, if possible
	 * @param obj Object to convert
	 * @return An ItemStack, or a ClassCastException if not possible
	 */
	public static ItemStack convertToStack(Object obj){
		if(obj == null)
			return ItemStack.EMPTY;
		else if(obj instanceof ItemStack)
			return ((ItemStack)obj).copy();
		else if(obj instanceof Item)
			return new ItemStack((Item)obj);
		else if(obj instanceof Block)
			return new ItemStack((Block)obj);
		else if(obj instanceof Ingredient)
			return ((Ingredient) obj).getMatchingStacks().length > 0 ? ((Ingredient) obj).getMatchingStacks()[0].copy() : ItemStack.EMPTY;
			else if(obj instanceof ItemStack[])
				return ((ItemStack[])obj)[0].copy();
			else if(obj instanceof String)
				return OreDictionary.getOres((String)obj).get(0).copy();
			else if(obj instanceof List)
				return ((ItemStack)((List) obj).get(0)).copy();
			else throw new ClassCastException("Not a Item, Block, ItemStack, Ingredient, Array of ItemStacks, String or List of ItemStacks!");
	}

	/**
	 * Compares an array of Objects to one containing ItemStacks
	 * @param array1 Array of Objects to compare
	 * @param array2 Array of ItemStacks to compare
	 * @param nbt If ItemStack NBT should be compared as well
	 * @return True if the contents are equal, otherwise false
	 */
	public static boolean areItemStackArraysEqual(Object[] array1, ItemStack[] array2, boolean nbt){

		List<Object> compareList = nonNullList(array1);
		List<ItemStack> itemList = Lists.newArrayList();

		for(ItemStack item : array2)
			if(!item.isEmpty())
				itemList.add(item);

		if(itemList.size() == compareList.size())
			for(ItemStack item : itemList)
				for(Object compare : compareList)
					if(areObjectsEqual(item, compare, nbt)){
						compareList.remove(compare);
						break;
					}

		return compareList.isEmpty();
	}

	/**
	 * Converts an array of Objects into a List without any null elements.<br>
	 * The only reason any Object would be null in the first place would be
	 * for visual alignment in a GUI (like, say, the Necronomicon).
	 */
	private static List<Object> nonNullList(Object[] array){
		List<Object> l = Lists.newArrayList();

		for(Object o : array)
			if(o != null)
				l.add(o);

		return l;
	}

	/**
	 * Compares an ItemStack to an Object
	 * @param stack ItemStack to compare
	 * @param obj Object to compare
	 * @param nbt If ItemStack NBT should be compared as well
	 * @return True if both Objects are equal, otherwise false
	 */
	public static boolean areObjectsEqual(ItemStack stack, Object obj, boolean nbt){
		if(obj instanceof ItemStack)
			return areStacksEqual(stack, (ItemStack)obj, nbt);
		else if(obj instanceof Item)
			return areStacksEqual(stack, new ItemStack((Item)obj), nbt);
		else if(obj instanceof Block)
			return areStacksEqual(stack, new ItemStack((Block)obj), nbt);
		else if(obj instanceof ItemStack[]){
			for(ItemStack item : (ItemStack[])obj)
				if(areStacksEqual(stack, item, nbt))
					return true;
		} else if(obj instanceof String){
			for(ItemStack item : OreDictionary.getOres((String)obj))
				if(areStacksEqual(stack, item, nbt))
					return true;
		} else if(obj instanceof List)
			for(ItemStack item :(List<ItemStack>)obj)
				if(areStacksEqual(stack, item, nbt))
					return true;
		return false;
	}

	/**
	 * Compares two ItemStacks
	 * @param stack1 First ItemStack to compare
	 * @param stack2 Second ItemStack to compare
	 * @param nbt If ItemStack NBT should be compared as well
	 * @return True if both stacks are equal, otherwise false
	 */
	public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2, boolean nbt){
		if(stack1.isEmpty() || stack2.isEmpty()) return false;
		return nbt ? areStacksEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack2, stack1) :
			areStacksEqual(stack1, stack2);
	}

	/**
	 * Compares two ItemStacks
	 * @param stack1 First ItemStack to compare
	 * @param stack2 Second ItemStack to compare
	 * @return True if both stacks are equal, otherwise false
	 */
	public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2)
	{
		if (stack1.isEmpty() || stack2.isEmpty()) return false;
		return stack1.getItem() == stack2.getItem() && (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| stack1.getItemDamage() == stack2.getItemDamage());
	}

	/**
	 * Convenience method to call in getFontRenderer of any Item implementing IUnlockableItem<br>
	 * in order to handle the font changing for locked Items
	 * @param stack The current ItemStack
	 * @return The Aklo font renderer if the Item is locked, otherwise null
	 */
	@SideOnly(Side.CLIENT)
	public static FontRenderer getFontRenderer(ItemStack stack){
		if(!(stack.getItem() instanceof IUnlockableItem)) return null;
		INecroDataCapability cap = NecroDataCapability.getCap(Minecraft.getMinecraft().player);

		return cap.isUnlocked(((IUnlockableItem) stack.getItem()).getUnlockCondition(stack), Minecraft.getMinecraft().player) ? null : AbyssalCraftAPI.getAkloFont();
	}
}
