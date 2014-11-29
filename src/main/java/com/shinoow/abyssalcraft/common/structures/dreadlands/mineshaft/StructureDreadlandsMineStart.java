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

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureDreadlandsMineStart extends StructureStart {

	public StructureDreadlandsMineStart() {}

	@SuppressWarnings("unchecked")
	public StructureDreadlandsMineStart(World par1World, Random par2Random, int par3, int par4) {
		super(par3, par4);
		StructureDreadlandsMinePieces.Room room = new StructureDreadlandsMinePieces.Room(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
		components.add(room);
		room.buildComponent(room, components, par2Random);
		updateBoundingBox();
		markAvailableHeight(par1World, par2Random, 10);
	}
}