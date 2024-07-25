package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockACFenceGate extends BlockFenceGate {

	private MapColor mapColor;

	public BlockACFenceGate(MapColor mapColor) {
		super(EnumType.OAK);
		this.mapColor = mapColor;
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		return mapColor;
	}

}
