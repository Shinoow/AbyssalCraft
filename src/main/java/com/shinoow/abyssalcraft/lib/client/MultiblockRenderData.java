/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.client;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Uses code borrowed from Patchouli
 * https://github.com/Vazkii/Patchouli/blob/master/src/main/java/vazkii/patchouli/common/multiblock/Multiblock.java
 */
public class MultiblockRenderData implements IBlockAccess {

	public int sizeX, sizeY, sizeZ;
	private World world;
	private IBlockState[][][] states;

	private final transient Map<BlockPos, TileEntity> teCache = new HashMap<>();

	public void calculateData(IBlockState[][][] data) {

		sizeY = data.length;
		sizeX = data[0].length;
		sizeZ = data[0][0].length;
		states = new IBlockState[sizeY][sizeX][sizeZ];
		for(int y = 0; y < sizeY; y++)
			for(int x = 0; x < sizeX; x++)
				for(int z = 0; z < sizeZ; z++) {
					IBlockState state = data[y][x][z];
					if(state == null)
						states[y][x][z] = Blocks.AIR.getDefaultState();
					else
						states[y][x][z] = state;
				}

	}

	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	@Nullable
	public TileEntity getTileEntity(BlockPos pos) {
		IBlockState state = getBlockState(pos);
		if (state.getBlock().hasTileEntity(state))
			return teCache.computeIfAbsent(pos.toImmutable(), p -> state.getBlock().createTileEntity(world, state));
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getCombinedLight(BlockPos pos, int lightValue) {
		return 0xF000F0;
	}

	@Override
	public IBlockState getBlockState(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if (x < 0 || y < 0 || z < 0 || x >= sizeX || y >= sizeY || z >= sizeZ)
			return Blocks.AIR.getDefaultState();
		return states[y][x][z];
	}

	@Override
	public boolean isAirBlock(BlockPos pos) {
		IBlockState state = getBlockState(pos);
		return state.getBlock().isAir(state, this, pos);
	}

	@Override
	public Biome getBiome(BlockPos pos) {

		return Biomes.PLAINS;
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction) {

		return 0;
	}

	@Override
	public WorldType getWorldType() {

		return WorldType.DEFAULT;
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
		return getBlockState(pos).isSideSolid(this, pos, side);
	}
}
