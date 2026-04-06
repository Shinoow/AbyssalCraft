package com.shinoow.abyssalcraft.common.blocks.baseblocks;

import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockACHorizontal extends BlockACBasic {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockACHorizontal(int harvestlevel, float hardness, float resistance) {
		super(Material.ROCK, "pickaxe", harvestlevel, hardness, resistance, SoundType.STONE);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockACHorizontal(float hardness, float resistance) {
		super(Material.ROCK, hardness, resistance, SoundType.STONE);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockACHorizontal(int harvestlevel, float hardness, float resistance, MapColor mapColor) {
		super(Material.ROCK, "pickaxe", harvestlevel, hardness, resistance, SoundType.STONE, mapColor);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockACHorizontal(float hardness, float resistance, MapColor mapColor) {
		super(Material.ROCK, hardness, resistance, SoundType.STONE, mapColor);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, BlockUtil.getHorizontalFacing(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).build();
	}
}
