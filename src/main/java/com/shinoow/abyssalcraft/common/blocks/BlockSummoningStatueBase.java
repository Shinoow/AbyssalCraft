package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.common.blocks.baseblocks.BlockACBasic;
import com.shinoow.abyssalcraft.common.blocks.baseblocks.BlockACHorizontal;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSummoningStatueBase extends BlockACBasic {

	public BlockSummoningStatueBase() {
		super(Material.ROCK, 2.5F, 20.0F, SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setTranslationKey("summoning_statue");
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {

		BlockPos pos1 = pos.up();
		BlockPos pos2 = pos1.up();

		if(worldIn.getBlockState(pos1).getBlock().isReplaceable(worldIn, pos1)
				&& worldIn.getBlockState(pos2).getBlock().isReplaceable(worldIn, pos2)) {
			return super.canPlaceBlockAt(worldIn, pos);
		}

		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.destroyBlock(pos.up(), false);
		worldIn.destroyBlock(pos.up(2), false);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {

		EnumFacing placerFacing = placer.getHorizontalFacing().getOpposite();
		worldIn.setBlockState(pos.up(), BlockHandler.summoning_statue_bottom.getDefaultState().withProperty(BlockACHorizontal.FACING, placerFacing), 2);
		worldIn.setBlockState(pos.up(2), BlockHandler.summoning_statue_top.getDefaultState().withProperty(BlockACHorizontal.FACING, placerFacing), 2);

		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
}
