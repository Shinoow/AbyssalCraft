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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.BlockTieredEnergyPedestal.EnumDimType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTieredSacrificialAltar;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockTieredSacrificialAltar extends BlockContainer {

	public static final PropertyEnum DIMENSION = PropertyEnum.create("dimension", EnumDimType.class);

	public BlockTieredSacrificialAltar(){
		super(Material.rock);
		setHardness(6.0F);
		setResistance(12.0F);
		setUnlocalizedName("tieredsacrificialaltar");
		setStepSound(Block.soundTypeStone);
		setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F);
		setCreativeTab(ACTabs.tabDecoration);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileEntityTieredSacrificialAltar();
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
	public int damageDropped (IBlockState state) {
		return ((EnumDimType)state.getValue(DIMENSION)).getMeta();
	}

	@Override
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random) {
		super.randomDisplayTick(world, pos, state, random);

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if(AbyssalCraft.particleBlock){
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.75, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.25, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.25, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.75, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.75, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.25, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.25, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, x + 0.75, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
		}

		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntityTieredSacrificialAltar){
			int timer = ((TileEntityTieredSacrificialAltar) tile).getCooldownTimer();

			if(timer > 0)
				world.spawnParticle(EnumParticleTypes.LAVA, x + 0.5, y + 1, z + 0.5, 0, 0, 0);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntityTieredSacrificialAltar)
			if(((TileEntityTieredSacrificialAltar)tile).getItem() != null){
				player.inventory.addItemStackToInventory(((TileEntityTieredSacrificialAltar)tile).getItem());
				((TileEntityTieredSacrificialAltar)tile).setItem(null);
				world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
				return true;
			} else {
				ItemStack heldItem = player.getHeldItem();
				if(heldItem != null){
					ItemStack newItem = heldItem.copy();
					newItem.stackSize = 1;
					((TileEntityTieredSacrificialAltar)tile).setItem(newItem);
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
					return true;
				}
			}
		return false;
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		Random rand = new Random();
		TileEntityTieredSacrificialAltar altar = (TileEntityTieredSacrificialAltar) world.getTileEntity(pos);

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
		return getDefaultState().withProperty(DIMENSION, EnumDimType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumDimType)state.getValue(DIMENSION)).getMeta();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { DIMENSION });
	}
}