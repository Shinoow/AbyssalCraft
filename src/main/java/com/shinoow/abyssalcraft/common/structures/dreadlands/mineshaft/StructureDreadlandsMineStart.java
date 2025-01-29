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
package com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureDreadlandsMineStart extends StructureStart {

	public StructureDreadlandsMineStart() {}

	public StructureDreadlandsMineStart(World par1World, Random par2Random, int par3, int par4) {
		super(par3, par4);
		StructureDreadlandsMinePieces.Room room = new StructureDreadlandsMinePieces.Room(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
		components.add(room);
		room.buildComponent(room, components, par2Random);
		updateBoundingBox();
		markAvailableHeight(par1World, par2Random, 10);
	}
}
