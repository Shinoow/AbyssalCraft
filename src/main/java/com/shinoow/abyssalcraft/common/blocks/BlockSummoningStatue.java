package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.baseblocks.BlockACHorizontal;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSummoningStatue extends BlockACHorizontal {

	boolean isTop;

	public BlockSummoningStatue(boolean top) {
		super(2.5F, 20.0F);
		mipp();
		isTop = top;
		setTranslationKey(top ? "summoning_statue_top" :  "summoning_statue_bottom");
		setCreativeTab(null);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);

		if(isTop) {
			worldIn.destroyBlock(pos.down(), false);
			worldIn.destroyBlock(pos.down(2), false);
		} else {
			worldIn.destroyBlock(pos.down(), false);
			worldIn.destroyBlock(pos.up(), false);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing facing = state.getValue(FACING);

		float yMax = isTop ? 0.7F : 1.0F;

		switch(facing) {
		case EAST:
		case WEST:
			return new AxisAlignedBB(0.375F, 0.0F, 0.05F, 0.625F, yMax, 0.95F);
		case NORTH:
		case SOUTH:
			return new AxisAlignedBB(0.05F, 0.0F, 0.375F, 0.95F, yMax, 0.625F);
		default:
			return new AxisAlignedBB(0.05F, 0.0F, 0.375F, 0.95F, yMax, 0.625F);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ACBlocks.summoning_statue);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(ACBlocks.summoning_statue);
	}

	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}
}
