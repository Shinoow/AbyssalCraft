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
package com.shinoow.abyssalcraft.lib.util.blocks;

import java.util.Random;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.PEUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockUtil {

	public static TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache)
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		else
			return blockAccess.getTileEntity(pos);
	}

	public static void dropTileEntityAsItemWithExtra(World world, BlockPos pos, IBlockState state, Block block) {
		Random rand = new Random();
		TileEntity tile = world.getTileEntity(pos);

		if(tile instanceof IInventory)
			InventoryHelper.dropInventoryItems(world, pos, (IInventory)tile);

		if(tile instanceof ISingletonInventory)
			if(!((ISingletonInventory)tile).getItem().isEmpty()){
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, ((ISingletonInventory)tile).getItem());
				float f3 = 0.05F;
				item.motionX = (float)rand.nextGaussian() * f3;
				item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f3;
				world.spawnEntity(item);
			}

		if(tile instanceof IEnergyContainer && !(tile instanceof IEnergyManipulator)){
			ItemStack stack = new ItemStack(block.getItemDropped(state, rand, 1), 1, block.damageDropped(state));
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setFloat("PotEnergy", ((IEnergyContainer)tile).getContainedEnergy());
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 0.8F + 0.1F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;

			EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, stack);
			float f3 = 0.05F;
			item.motionX = (float)rand.nextGaussian() * f3;
			item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
			item.motionZ = (float)rand.nextGaussian() * f3;
			world.spawnEntity(item);
		}

		if(tile instanceof IEnergyManipulator && !(tile instanceof IEnergyContainer)){
			ItemStack stack = new ItemStack(block.getItemDropped(state, rand, 1), 1, block.damageDropped(state));
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound data = new NBTTagCompound();
			tile.writeToNBT(data);
			stack.getTagCompound().setInteger("Timer", data.getInteger("Timer"));
			if(data.hasKey("PotEnergy")) {
				stack.getTagCompound().setInteger("Tolerance", data.getInteger("Tolerance"));
				stack.getTagCompound().setFloat("PotEnergy", data.getFloat("PotEnergy"));
			} else
				stack.getTagCompound().setInteger("Tolerance", data.getInteger("Tolerance") + 10);
			PEUtils.writeManipulatorNBT((IEnergyManipulator)tile, stack.getTagCompound());
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 0.8F + 0.1F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;

			EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, stack);
			float f3 = 0.05F;
			item.motionX = (float)rand.nextGaussian() * f3;
			item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
			item.motionZ = (float)rand.nextGaussian() * f3;
			world.spawnEntity(item);
		}
	}
}
