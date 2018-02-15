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
package com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenDreadlandsMine extends MapGenStructure
{
	private double spawnChance = 0.004D;

	public MapGenDreadlandsMine() {}

	@Override
	public String getStructureName()
	{
		return "Dreadlands Mineshaft";
	}

	public MapGenDreadlandsMine(Map<?, ?> par1Map)
	{
		Iterator<?> iterator = par1Map.entrySet().iterator();

		while (iterator.hasNext())
		{
			Entry<?, ?> entry = (Entry<?, ?>)iterator.next();

			if (((String)entry.getKey()).equals("chance"))
				spawnChance = MathHelper.getDouble((String)entry.getValue(), spawnChance);
		}
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		return rand.nextDouble() < spawnChance && rand.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2));
	}

	@Override
	protected StructureStart getStructureStart(int par1, int par2)
	{
		return new StructureDreadlandsMineStart(world, rand, par1, par2);
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {

		return null;
	}
}
