/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntitySequentialBrewingStand;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSequentialBrewingStand extends BlockContainer
{
	public static final PropertyBool[] HAS_BOTTLE = new PropertyBool[] {PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2")};
	public static final PropertyDirection EXIT_DIRECTION = PropertyDirection.create("exit_direction");
	public static final PropertyDirection ENTRY_DIRECTION = PropertyDirection.create("entry_direction");
	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
	protected static final AxisAlignedBB STICK_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);
	private static boolean keepInventory;

	public BlockSequentialBrewingStand()
	{
		super(Material.IRON);
		setDefaultState(blockState.getBaseState()
				.withProperty(HAS_BOTTLE[0], false)
				.withProperty(HAS_BOTTLE[1], false)
				.withProperty(HAS_BOTTLE[2], false)
				.withProperty(EXIT_DIRECTION, EnumFacing.NORTH)
				.withProperty(ENTRY_DIRECTION, EnumFacing.UP));
		setTranslationKey("sequential_brewing_stand");
		setHardness(0.5F);
		setLightLevel(0.125F);
		setCreativeTab(ACTabs.tabBlock);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing facing = getDirection(worldIn, pos, EnumFacing.NORTH);
		EnumFacing facing2 = getAdjacentDirection(worldIn, pos, facing);

		return state.withProperty(EXIT_DIRECTION, facing).withProperty(ENTRY_DIRECTION, facing2);
	}

	private EnumFacing getDirection(IBlockAccess worldIn, BlockPos pos, EnumFacing def)
	{
		TileEntity tile = BlockUtil.getTileEntitySafely(worldIn, pos);
		EnumFacing facing = null;
		if(tile instanceof TileEntitySequentialBrewingStand)
			facing = ((TileEntitySequentialBrewingStand) tile).getDirection();

		return facing != null ? facing : def;
	}

	private EnumFacing getAdjacentDirection(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
	{
		EnumFacing facing2 = null;
		for(EnumFacing direction : EnumFacing.HORIZONTALS)
			if(direction != facing) {
				TileEntity tile = BlockUtil.getTileEntitySafely(worldIn, pos.offset(direction));

				if(tile instanceof TileEntitySequentialBrewingStand && ((TileEntitySequentialBrewingStand) tile).getDirection().getOpposite() == direction)
				{
					facing2 = direction;
					break;
				}
			}

		return facing2 != null ? facing2 : EnumFacing.UP;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	/**
	 * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	 * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySequentialBrewingStand();
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
	{
		addCollisionBoxToList(pos, entityBox, collidingBoxes, STICK_AABB);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BASE_AABB;
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
			return true;
		else
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntitySequentialBrewingStand)
			{

				FMLNetworkHandler.openGui(playerIn, AbyssalCraft.instance, ACLib.sequentialBrewingStandGuiID, worldIn, pos.getX(), pos.getY(), pos.getZ());
				playerIn.addStat(StatList.BREWINGSTAND_INTERACTION);
			}

			return true;
		}
	}

	public static void updateBlockState(boolean[] filledSlots, World par1World, BlockPos pos)
	{
		IBlockState state = par1World.getBlockState(pos);
		TileEntity tileentity = par1World.getTileEntity(pos);
		keepInventory = true;

		for (int i = 0; i < HAS_BOTTLE.length; ++i)
			state = state.withProperty(HAS_BOTTLE[i], filledSlots[i]);

		par1World.setBlockState(pos, state, 3);

		keepInventory = false;

		if (tileentity != null)
		{
			tileentity.validate();
			par1World.setTileEntity(pos, tileentity);
		}
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntitySequentialBrewingStand)
		{
			if (stack.hasDisplayName())
				((TileEntitySequentialBrewingStand)tileentity).setName(stack.getDisplayName());
			((TileEntitySequentialBrewingStand)tileentity).setDirection(placer.getHorizontalFacing().rotateY());
		}

	}

	/**
	 * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	 * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	 * of whether the block can receive random update ticks
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double d0 = pos.getX() + 0.4F + rand.nextFloat() * 0.2F;
		double d1 = pos.getY() + 0.7F + rand.nextFloat() * 0.3F;
		double d2 = pos.getZ() + 0.4F + rand.nextFloat() * 0.2F;
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if(!keepInventory) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntitySequentialBrewingStand)
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntitySequentialBrewingStand)tileentity);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	/**
	 * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
	 * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = getDefaultState();

		for (int i = 0; i < 3; ++i)
			iblockstate = iblockstate.withProperty(HAS_BOTTLE[i], (meta & 1 << i) > 0);

		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		for (int j = 0; j < 3; ++j)
			if (state.getValue(HAS_BOTTLE[j]))
				i |= 1 << j;

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(HAS_BOTTLE).add(EXIT_DIRECTION).add(ENTRY_DIRECTION).build();
	}

	/**
	 * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
	 * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
	 * does not fit the other descriptions and will generally cause other things not to connect to the face.
	 *
	 * @return an approximation of the form of the given face
	 */
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}
