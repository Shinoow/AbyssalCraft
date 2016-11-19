/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.item.ACItems;

/**
 * Loot Table references
 * @author shinoow
 *
 */
public class ACLoot {

	public static final List<WeightedRandomChestContent> strongholdChestContents = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 4, 10), new WeightedRandomChestContent(ACItems.transmutation_gem, 0, 1, 1, 3), new WeightedRandomChestContent(ACItems.abyssalnite_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(ACItems.refined_coralium_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(ACItems.coralium_pearl, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 3, 15), new WeightedRandomChestContent(ACItems.abyssalnite_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(ACItems.abyssalnite_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(ACItems.abyssalnite_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(ACItems.abyssalnite_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(ACItems.abyssalnite_boots, 0, 1, 1, 5), new WeightedRandomChestContent(ACItems.oblivion_catalyst, 0, 1, 1, 1), new WeightedRandomChestContent(ACItems.crystal, 24, 1, 5, 8)});
	public static final List<WeightedRandomChestContent> strongholdRoomCrossingChestContents = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(ACItems.abyssalnite_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(ACItems.refined_coralium_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(ACItems.coralium_pearl, 0, 1, 3, 5), new WeightedRandomChestContent(ACItems.coralium_gem, 0, 3, 8, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 3, 15), new WeightedRandomChestContent(ACItems.mre, 0, 1, 1, 10), new WeightedRandomChestContent(ACItems.abyssalnite_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(ACItems.crystal, 24, 1, 5, 8)});
	public static final List<WeightedRandomChestContent> mineshaftChestContents = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(ACItems.abyssalnite_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(ACItems.coralium_gem, 0, 4, 9, 5), new WeightedRandomChestContent(ACItems.shadow_shard, 0, 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(ACItems.dreaded_shard_of_abyssalnite, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(ACItems.refined_coralium_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(ACItems.chunk_of_abyssalnite, 0, 2, 4, 10), new WeightedRandomChestContent(ACItems.dreaded_chunk_of_abyssalnite, 0, 2, 4, 10), new WeightedRandomChestContent(ACItems.transmutation_gem, 0, 1, 1, 3), new WeightedRandomChestContent(ACItems.oblivion_catalyst, 0, 1, 1, 1), new WeightedRandomChestContent(ACItems.crystal, 24, 1, 5, 8)});

}
