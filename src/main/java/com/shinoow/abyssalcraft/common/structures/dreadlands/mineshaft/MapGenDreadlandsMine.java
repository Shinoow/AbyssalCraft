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
package com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft;

import java.util.*;
import java.util.Map.Entry;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.*;

public class MapGenDreadlandsMine extends MapGenStructure
{
	private double spawnChance = 0.004D;

	public MapGenDreadlandsMine() {}

	@Override
	public String func_143025_a()
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
				spawnChance = MathHelper.parseDoubleWithDefault((String)entry.getValue(), spawnChance);
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
		return new StructureDreadlandsMineStart(worldObj, rand, par1, par2);
	}
}