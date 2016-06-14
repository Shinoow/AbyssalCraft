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
package com.shinoow.abyssalcraft.common.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.structures.omothol.StructureJzaharTemple;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

public class NecronomiconRespawnJzaharRitual extends NecronomiconRitual {

	public NecronomiconRespawnJzaharRitual() {
		super("respawnJzahar", 3, ACLib.omothol_id, 20000F,
				new Object[]{new ItemStack(ACItems.essence, 1, 2), ACItems.shard_of_oblivion,
				new ItemStack(ACItems.essence, 1, 2), ACItems.shard_of_oblivion,
				new ItemStack(ACItems.essence, 1, 2), ACItems.shard_of_oblivion,
				new ItemStack(ACItems.essence, 1, 2), ACItems.shard_of_oblivion});
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		return pos.getX() == 4 && pos.getY() == 54 && pos.getZ() == 85;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		StructureJzaharTemple temple = new StructureJzaharTemple();
		temple.generate(world, world.rand, new BlockPos(4, 53, 7));
		world.getChunkFromBlockCoords(pos).setChunkModified();
		SpecialTextUtil.JzaharGroup(world, StatCollector.translateToLocalFormatted("message.jzahar.respawn", player.getName()));
	}
}
