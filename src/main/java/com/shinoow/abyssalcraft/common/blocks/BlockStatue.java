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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.HashMap;
import java.util.Map;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityStatue;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJModel;

public class BlockStatue extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public EnumDeityType TYPE;
	public static final Map<EnumDeityType, Block> VARIANTS = new HashMap<>();

	public BlockStatue(EnumDeityType type) {
		super(Material.ROCK);
		TYPE = type;
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setHarvestLevel("pickaxe", 0);
		VARIANTS.put(TYPE, this);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(stack.hasTagCompound()){
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof TileEntityStatue){
				NBTTagCompound data = new NBTTagCompound();
				tile.writeToNBT(data);
				data.setInteger("Tolerance", 10);
				tile.readFromNBT(data);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityStatue();
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).add(OBJModel.OBJProperty.INSTANCE).build();
	}

	public enum EnumDeityType implements IStringSerializable {
		CTHULHU(0, "cthulhu", DeityType.CTHULHU),
		HASTUR(1, "hastur", DeityType.HASTUR),
		JZAHAR(2, "jzahar", DeityType.JZAHAR),
		AZATHOTH(3, "azathoth", DeityType.AZATHOTH),
		NYARLATHOTEP(4, "nyarlathotep", DeityType.NYARLATHOTEP),
		YOGSOTHOTH(5, "yogsothoth", DeityType.YOGSOTHOTH),
		SHUBNIGGURATH(6, "shubniggurath", DeityType.SHUBNIGGURATH);

		private static final EnumDeityType[] META_LOOKUP = new EnumDeityType[values().length];

		private int meta;
		private String name;
		private DeityType deity;

		private EnumDeityType(int meta, String name, DeityType deity) {
			this.meta = meta;
			this.name = name;
			this.deity = deity;
		}

		public static EnumDeityType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length)
				meta = 0;

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta() {
			return meta;
		}

		public DeityType getDeity() {
			return deity;
		}

		@Override
		public String toString() {
			return getName();
		}

		static {
			for(EnumDeityType type : values())
				META_LOOKUP[type.getMeta()] = type;
		}
	}
}
