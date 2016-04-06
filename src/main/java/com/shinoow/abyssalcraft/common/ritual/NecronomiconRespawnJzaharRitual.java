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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.structures.omothol.StructureJzaharTemple;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

public class NecronomiconRespawnJzaharRitual extends NecronomiconRitual {

	public NecronomiconRespawnJzaharRitual() {
		super("respawnJzahar", 3, AbyssalCraft.configDimId3, 20000F,
				new Object[]{new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.oblivionshard,
				new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.oblivionshard,
				new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.oblivionshard,
				new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.oblivionshard});

	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {

		return x == 4 && y == 54 && z == 85;
	}

	@Override
	protected void completeRitualClient(World world, int x, int y, int z, EntityPlayer player) {
		SpecialTextUtil.JzaharGroup(world, StatCollector.translateToLocalFormatted("message.jzahar.respawn", player.getCommandSenderName()));
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player) {
		StructureJzaharTemple temple = new StructureJzaharTemple();
		temple.generate(world, world.rand, 4, 53, 7);
		world.getChunkFromBlockCoords(x, z).setChunkModified();
	}
}
