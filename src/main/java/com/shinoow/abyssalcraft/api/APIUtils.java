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
package com.shinoow.abyssalcraft.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.recipe.EngraverRecipes;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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

	public static boolean display_names;

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
		return AbyssalCraftAPI.getCrystals().stream().anyMatch(crystal -> areStacksEqual(item, crystal));
	}

	/**
	 * Checks if the ItemStack is a Coin
	 * @param item ItemStack to check
	 * @return True if the ItemStack is a Coin, otherwise false
	 *
	 * @since 1.5
	 */
	public static boolean isCoin(ItemStack item){
		return EngraverRecipes.instance().getCoinList().stream().anyMatch(coin -> areStacksEqual(item, coin));
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
		List<ItemStack> itemList = Arrays.stream(array2).filter(Predicates.not(ItemStack::isEmpty)).collect(Collectors.toList());

		if(itemList.size() == compareList.size())
			for(ItemStack stack : itemList)
				for(Object compare : compareList)
					if(areObjectsEqual(stack, compare, nbt)){
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
		return Arrays.stream(array).filter(Predicates.notNull()).collect(Collectors.toList());
	}

	/**
	 * Create an array of a specific size containing a specific Object
	 */
	public static Object[] makeArrayOf(Object stack, int size) {
		Object[] array = new Object[size];
		for(int i = 0; i < size; i++)
			array[i] = stack;
		return array;
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
	 * @param stack1 First ItemStack to compare (the input Item Stack)
	 * @param stack2 Second ItemStack to compare (the recipe Item Stack)
	 * @param nbt If ItemStack NBT should be compared as well
	 * @return True if both stacks are equal, otherwise false
	 */
	public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2, boolean nbt){
		if(stack1.isEmpty() || stack2.isEmpty()) return false;
		return nbt ? areStacksEqual(stack1, stack2) && areItemStackTagsEqual(stack2, stack1, 0) :
			areStacksEqual(stack1, stack2);
	}

	/**
	 * Compares two ItemStacks
	 * @param stack1 First ItemStack to compare (the input Item Stack)
	 * @param stack2 Second ItemStack to compare (the recipe Item Stack)
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
		if(!(stack.getItem() instanceof IUnlockableItem) || display_names) return null;
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(player == null) return null;
		INecroDataCapability cap = NecroDataCapability.getCap(player);

		return cap.isUnlocked(((IUnlockableItem) stack.getItem()).getUnlockCondition(stack), Minecraft.getMinecraft().player) ? null : AbyssalCraftAPI.getAkloFont();
	}

	/**
	 * Checks if two ItemStacks have the same NBT tags ({@link ItemStack#areItemStackTagsEqual(ItemStack, ItemStack)} without comparing capabilities)
	 * @param stackA First Item Stack (the recipe Item Stack)
	 * @param stackB Second Item Stack (the input Item Stack)
	 * @param strictness How strictly the Tags are compared,
	 * <ul>
	 * <li>0 = checks if stackBs tag contains stackAs</li>
	 * <li>1 = checks if stackAs tag equals stackBs</li>
	 * <li>2 = checks if stackAs tag and capabilities equals stackBs</li>
	 * </ul>
	 * @return True if the NBT tags match, otherwise false
	 */
	public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB, int strictness)
	{
		switch(strictness) {
		case 0:
			if (stackA.isEmpty() && stackB.isEmpty())
				return true;
			else if (!stackA.isEmpty() && !stackB.isEmpty())
			{
				if(stackA.getTagCompound() == null)
					return true;
				else if(stackB.getTagCompound() == null)
					return false;
				return compoundContainsCompound(stackA.getTagCompound().copy(), stackB.getTagCompound().copy());
			} else
				return false;
		case 1:
			if (stackA.isEmpty() && stackB.isEmpty())
				return true;
			else if (!stackA.isEmpty() && !stackB.isEmpty())
			{
				if (stackA.getTagCompound() == null && stackB.getTagCompound() != null)
					return false;
				else
					return stackA.getTagCompound() == null || stackA.getTagCompound().equals(stackB.getTagCompound());
			} else
				return false;
		case 2:
			return ItemStack.areItemStackTagsEqual(stackA, stackB);
		default:
			return false;
		}
	}

	/**
	 * Checks if the second NBT tag compound contains the tags of the first one
	 * @param tag First tag compound
	 * @param tag1 Second tag compound
	 * @return True if the second compound contains all of the tags from the first one, otherwise false
	 */
	private static boolean compoundContainsCompound(NBTTagCompound tag, NBTTagCompound tag1) {

		for (String s : tag.getKeySet())
		{
			NBTBase nbtbase = tag.getTag(s);

			if (nbtbase.getId() == 10)
			{
				if (tag1.hasKey(s, 10))
				{
					NBTTagCompound nbttagcompound = tag1.getCompoundTag(s);
					if(!compoundContainsCompound((NBTTagCompound)nbtbase, nbttagcompound))
						return false;
				} else
					return false;
			} else if(tag1.hasKey(s))
				if(!tag1.getTag(s).equals(nbtbase))
					return false;
		}

		return true;
	}

	/**
	 * Checks if the ItemStack contains an Item or Block that can have an inventory
	 * @param stack ItemStack to check
	 * @return True if the ItemStack has an inventory
	 */
	public static boolean hasAnInventory(ItemStack stack) {

		if(stack.getItem() == ACItems.configurator
				|| stack.getItem() instanceof ItemShulkerBox
				|| stack.getItem() == ACItems.small_crystal_bag
				|| stack.getItem() == ACItems.medium_crystal_bag
				|| stack.getItem() == ACItems.large_crystal_bag
				|| stack.getItem() == ACItems.huge_crystal_bag
				|| stack.getItem() == ACItems.stone_tablet)
			return true;

		// Maybe add a config option to blacklist more things with inventories???
		return false;
	}
}
