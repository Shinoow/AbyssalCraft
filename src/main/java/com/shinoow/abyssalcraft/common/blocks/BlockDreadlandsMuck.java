package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDreadlandsMuck extends BlockACBasic {

	protected static final AxisAlignedBB MUCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

	public BlockDreadlandsMuck() {
		super(Material.GROUND, 0.5F, 2.5F, SoundType.SLIME);
		setTranslationKey("dreadlands_muck");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return MUCK_AABB;
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		entity.motionX *= 0.8D;
		entity.motionZ *= 0.8D;
	}
}
