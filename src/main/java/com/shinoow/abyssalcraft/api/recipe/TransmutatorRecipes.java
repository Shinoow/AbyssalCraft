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

import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.OreDictionary;

public class TransmutatorRecipes {

	private static final TransmutatorRecipes transmutationBase = new TransmutatorRecipes();
	/** The list of transmutation results. */
	private Map<ItemStack, ItemStack> transmutationList = new HashMap<ItemStack, ItemStack>();
	private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

	public static TransmutatorRecipes transmutation()
	{
		return transmutationBase;
	}

	private TransmutatorRecipes()
	{
		transmutate(Items.diamond, new ItemStack(Items.coal, 64), 0.2F);
		transmutate(Items.water_bucket, new ItemStack(Blocks.water, 1), 0.0F);
		transmutate(Items.lava_bucket, new ItemStack(Blocks.lava, 1), 0.0F);
		transmutate(Blocks.wool, new ItemStack(Items.string, 9), 0.0F);
		transmutate(Blocks.gravel, new ItemStack(Items.flint, 2), 0.0F);
		transmutate(Blocks.quartz_block, new ItemStack(Items.quartz, 4), 0.0F);
		transmutate(Blocks.nether_brick, new ItemStack(Items.netherbrick, 4), 0.0F);
		transmutate(Items.netherbrick, new ItemStack(Blocks.netherrack), 0.0F);
		transmutate(Blocks.water, new ItemStack(Blocks.ice, 8), 0.0F);
		transmutate(Items.wheat, new ItemStack(Items.wheat_seeds), 0.0F);
		transmutate(Items.wheat_seeds, new ItemStack(Items.wheat), 0.0F);
	}

	public void transmutate(Block input, ItemStack output, float xp)
	{
		transmutate(Item.getItemFromBlock(input), output, xp);
	}

	public void transmutate(Item input, ItemStack output, float xp)
	{
		transmutate(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, xp);
	}

	public void transmutate(ItemStack input, ItemStack output, float xp)
	{
		transmutationList.put(input, output);
		experienceList.put(output, Float.valueOf(xp));
	}

	/**
	 * Returns the transmutation result of an item.
	 */
	public ItemStack getTransmutationResult(ItemStack par1ItemStack)
	{
		Iterator<?> iterator = transmutationList.entrySet().iterator();
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

	private boolean areStacksEqual(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return par2ItemStack.getItem() == par1ItemStack.getItem() && (par2ItemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE || par2ItemStack.getItemDamage() == par1ItemStack.getItemDamage());
	}

	public Map<ItemStack, ItemStack> getTransmutationList()
	{
		return transmutationList;
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