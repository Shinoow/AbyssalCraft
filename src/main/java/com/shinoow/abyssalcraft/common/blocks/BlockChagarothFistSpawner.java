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
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityChagarothFistSpawner;

public class BlockChagarothFistSpawner extends BlockContainer {

	public BlockChagarothFistSpawner() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityChagarothFistSpawner();
	}
}
