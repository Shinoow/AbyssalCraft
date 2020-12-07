package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPortalAnchor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortalAnchor extends BlockACBasic {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockPortalAnchor() {
		super(Material.ROCK, 10.0F, 24.0F, SoundType.STONE);
		setUnlocalizedName("portal_anchor");
		setDefaultState(blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityPortalAnchor();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ACBlocks.monolith_stone);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(ACBlocks.monolith_stone);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(ACTIVE, (meta & 1) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ACTIVE) ? 1 : 0;
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(ACTIVE).build();
	}
}
