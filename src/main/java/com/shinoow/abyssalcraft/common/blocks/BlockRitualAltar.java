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

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRitualAltar;
import com.shinoow.abyssalcraft.lib.ACConfig;

public class BlockRitualAltar extends BlockContainer {

	private static HashMap<Integer, Block> blockMeta = Maps.newHashMap();
	public static final PropertyEnum MATERIAL = PropertyEnum.create("material", EnumRitualMatType.class);

	public BlockRitualAltar() {
		super(Material.rock);
		setHardness(6.0F);
		setResistance(12.0F);
		setStepSound(Block.soundTypeStone);
		setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F);
		setCreativeTab(null);
		setLightLevel(0.375F);
		setDefaultState(blockState.getBaseState().withProperty(MATERIAL, EnumRitualMatType.COBBLESTONE));
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 5));
		par3List.add(new ItemStack(par1, 1, 6));
		par3List.add(new ItemStack(par1, 1, 7));
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {

		return new TileEntityRitualAltar();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return Item.getItemFromBlock(blockMeta.get(((EnumRitualMatType)state.getValue(MATERIAL)).getMeta()));
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public void randomDisplayTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		super.randomDisplayTick(par1World, pos, state, par5Random);
		if(ACConfig.particleBlock){
			par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.75, pos.getY() + 1.05, pos.getZ() + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.25, pos.getY() + 1.05, pos.getZ() + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.25, pos.getY() + 1.05, pos.getZ() + 0.25, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.75, pos.getY() + 1.05, pos.getZ() + 0.25, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.75, pos.getY() + 1.05, pos.getZ() + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.25, pos.getY() + 1.05, pos.getZ() + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.25, pos.getY() + 1.05, pos.getZ() + 0.25, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.75, pos.getY() + 1.05, pos.getZ() + 0.25, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntityRitualAltar)
			if(((TileEntityRitualAltar)tile).getItem() != null){
				player.inventory.addItemStackToInventory(((TileEntityRitualAltar)tile).getItem());
				((TileEntityRitualAltar)tile).setItem(null);
				world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
				return true;
			} else {
				ItemStack heldItem = player.getHeldItem();
				if(heldItem != null){
					ItemStack newItem = heldItem.copy();
					newItem.stackSize = 1;
					((TileEntityRitualAltar)tile).setItem(newItem);
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
					return true;
				}
			}
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		Random rand = new Random();
		TileEntityRitualAltar altar = (TileEntityRitualAltar) world.getTileEntity(pos);

		if(altar != null)
			if(altar.getItem() != null){
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, altar.getItem());
				float f3 = 0.05F;
				item.motionX = (float)rand.nextGaussian() * f3;
				item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f3;
				world.spawnEntityInWorld(item);
			}

		super.breakBlock(world, pos, state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(MATERIAL, EnumRitualMatType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumRitualMatType)state.getValue(MATERIAL)).getMeta();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { MATERIAL });
	}

	static {
		blockMeta.put(0, Blocks.cobblestone);
		blockMeta.put(1, ACBlocks.darkstone_cobblestone);
		blockMeta.put(2, ACBlocks.abyssal_stone_brick);
		blockMeta.put(3, ACBlocks.coralium_stone_brick);
		blockMeta.put(4, ACBlocks.dreadstone_brick);
		blockMeta.put(5, ACBlocks.abyssalnite_stone_brick);
		blockMeta.put(6, ACBlocks.ethaxium_brick);
		blockMeta.put(7, ACBlocks.dark_ethaxium_brick);
	}

	public enum EnumRitualMatType implements IStringSerializable {
		COBBLESTONE(0, "cobblestone"),
		DARKSTONE_COBBLESTONE(1, "darkstone_cobblestone"),
		ABYSSAL_STONE_BRICK(2, "abyssal_stone_brick"),
		CORALIUM_STONE_BRICK(3, "coralium_stone_brick"),
		DREADSTONE_BRICK(4, "dreadstone_brick"),
		ABYSSALNITE_STONE_BRICK(5, "abyssalnite_stone_brick"),
		ETHAXIUM_BRICK(6, "ethaxium_brick"),
		DARK_ETHAXIUM_BRICK(7, "dark_ethaxium_brick");

		private static final EnumRitualMatType[] META_LOOKUP = new EnumRitualMatType[values().length];

		private int meta;
		private String name;

		private EnumRitualMatType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public static EnumRitualMatType byMetadata(int meta)
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

		@Override
		public String toString() {
			return getName();
		}

		static {
			for(EnumRitualMatType type : values())
				META_LOOKUP[type.getMeta()] = type;
		}
	}
}