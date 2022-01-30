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
package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.util.IHiddenRitual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class NecronomiconHouseRitual extends NecronomiconInfusionRitual implements IHiddenRitual {

	public NecronomiconHouseRitual() {
		super("house", 0, OreDictionary.WILDCARD_VALUE, 0, new ItemStack(BlockHandler.house), new ItemStack[]{new ItemStack(Items.OAK_DOOR),
				new ItemStack(Items.SPRUCE_DOOR), new ItemStack(Items.BIRCH_DOOR), new ItemStack(Items.JUNGLE_DOOR),
				new ItemStack(Items.ACACIA_DOOR), new ItemStack(Items.DARK_OAK_DOOR)}, "plankWood", "stairWood",
				"plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "stairWood");
		setRitualParticle(EnumRitualParticle.SPRINKLER);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		return EntityUtil.isPlayerCoralium(player) && super.canCompleteRitual(world, pos, player);
	}
}
