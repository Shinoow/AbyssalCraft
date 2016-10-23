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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntitySacrificialAltar;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockSacrificialAltar extends BlockContainer {

	public BlockSacrificialAltar(){
		super(Material.rock);
		setHardness(6.0F);
		setResistance(12.0F);
		setUnlocalizedName("sacrificialaltar");
		setStepSound(Block.soundTypeStone);
		setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F);
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileEntitySacrificialAltar();
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
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + 0.75, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + 0.25, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + 0.25, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + 0.75, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
		}

		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntitySacrificialAltar){
			int timer = ((TileEntitySacrificialAltar) tile).getCooldownTimer();

			if(timer > 0)
				world.spawnParticle(EnumParticleTypes.LAVA, x + 0.5, y + 1, z + 0.5, 0, 0, 0);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntitySacrificialAltar)
			if(((TileEntitySacrificialAltar)tile).getItem() != null){
				player.inventory.addItemStackToInventory(((TileEntitySacrificialAltar)tile).getItem());
				((TileEntitySacrificialAltar)tile).setItem(null);
				world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
				return true;
			} else {
				ItemStack heldItem = player.getHeldItem();
				if(heldItem != null){
					ItemStack newItem = heldItem.copy();
					newItem.stackSize = 1;
					((TileEntitySacrificialAltar)tile).setItem(newItem);
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
		TileEntitySacrificialAltar altar = (TileEntitySacrificialAltar) world.getTileEntity(pos);

		if(altar != null){
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
			ItemStack stack = new ItemStack(getItemDropped(state, rand, 1), 1, damageDropped(state));
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound data = new NBTTagCompound();
			altar.writeToNBT(data);
			stack.getTagCompound().setFloat("PotEnergy", data.getFloat("PotEnergy"));
			stack.getTagCompound().setInteger("CollectionLimit", data.getInteger("CollectionLimit"));
			stack.getTagCompound().setInteger("CoolDown", data.getInteger("CoolDown"));
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 0.8F + 0.1F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;

			EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, stack);
			float f3 = 0.05F;
			item.motionX = (float)rand.nextGaussian() * f3;
			item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
			item.motionZ = (float)rand.nextGaussian() * f3;
			world.spawnEntityInWorld(item);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(stack.hasTagCompound()){
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null && tile instanceof TileEntitySacrificialAltar){
				NBTTagCompound data = new NBTTagCompound();
				tile.writeToNBT(data);
				data.setFloat("PotEnergy", stack.getTagCompound().getFloat("PotEnergy"));
				data.setInteger("CollectionLimit", stack.getTagCompound().getInteger("CollectionLimit"));
				data.setInteger("CoolDown", stack.getTagCompound().getInteger("CoolDown"));
				tile.readFromNBT(data);
			}
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<ItemStack>();
	}
}