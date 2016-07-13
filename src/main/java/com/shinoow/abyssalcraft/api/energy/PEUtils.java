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
package com.shinoow.abyssalcraft.api.energy;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

/**
 * Backbone for handling interactions between<br>
 * various PE mechanics
 * @author shinoow
 *
 */
public class PEUtils {

	/**
	 * Attempts to transfer PE from a Manipulator to nearby Players
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param manipulator PE Manipulator
	 * @param range Transfer Range
	 */
	public static void transferPEToNearbyPlayers(World world, BlockPos pos, IEnergyManipulator manipulator, int range){

		int xp = pos.getX();
		int yp = pos.getY();
		int zp = pos.getZ();

		List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(range, range, range));

		for(EntityPlayer player : players)
			if(EntityUtil.hasNecronomicon(player)){
				ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
				ItemStack item1 = player.getHeldItem(EnumHand.OFF_HAND);
				if(item != null && item.getItem() instanceof IEnergyTransporterItem ||
						item1 != null && item1.getItem() instanceof IEnergyTransporterItem){
					if(!world.isRemote){
						transferPEToStack(item, manipulator);
						transferPEToStack(item1, manipulator);
					}
					for(double i = 0; i <= 0.7; i += 0.03) {
						int xPos = xp < (int) player.posX ? 1 : xp > (int) player.posX ? -1 : 0;
						int yPos = yp < (int) player.posY ? 1 : yp > (int) player.posY ? -1 : 0;
						int zPos = zp < (int) player.posZ ? 1 : zp > (int) player.posZ ? -1 : 0;
						double x = i * Math.cos(i) / 2 * xPos;
						double y = i * Math.sin(i) / 2 * yPos;
						double z = i * Math.sin(i) / 2 * zPos;
						world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
					}
				}
			}
	}

	/**
	 * Shortcut method for Transferring PE to an ItemStack
	 * @param stack ItemStack to potentially transfer PE to
	 * @param manipulator PE Manipulator to transfer said PE
	 */
	private static void transferPEToStack(ItemStack stack, IEnergyManipulator manipulator){
		if(stack != null && ((IEnergyTransporterItem) stack.getItem()).canAcceptPEExternally(stack) &&
				((IEnergyTransporterItem) stack.getItem()).getContainedEnergy(stack) < ((IEnergyTransporterItem) stack.getItem()).getMaxEnergy(stack))
			((IEnergyTransporterItem) stack.getItem()).addEnergy(stack, manipulator.getEnergyQuanta());
	}

	/**
	 * Attempts to transfer PE from a Manipulator to nearby Collectors
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param manipulator PE Manipulator
	 * @param boost Any range boost applied to the manipulator
	 */
	public static void transferPEToCollectors(World world, BlockPos pos, IEnergyManipulator manipulator, int boost){

		int xp = pos.getX();
		int yp = pos.getY();
		int zp = pos.getZ();

		List<TileEntity> collectors = Lists.newArrayList();

		for(int x = -1*(3+boost); x <= 3+boost; x++)
			for(int y = 0; y <= getPillars(world, pos); y++)
				for(int z = -1*(3+boost); z <= 3+boost; z++)
					if(x < -2 || x > 2 || z < -2 || z > 2)
						if(isCollector(world.getTileEntity(new BlockPos(xp + x, yp - y, zp + z))))
							collectors.add(world.getTileEntity(new BlockPos(xp + x, yp - y, zp + z)));

		for(TileEntity tile : collectors)
			if(checkNearbyBlocks(world, tile.getPos()))
				if(world.rand.nextInt(120-(int)(20 * manipulator.getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) tile).getContainedEnergy() < ((IEnergyContainer) tile).getMaxEnergy()){
						if(!world.isRemote)
							((IEnergyContainer) tile).addEnergy(manipulator.getEnergyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xp < tile.getPos().getX() ? 1 : xp > tile.getPos().getX() ? -1 : 0;
							int yPos = yp < tile.getPos().getY() ? 1 : yp > tile.getPos().getY() ? -1 : 0;
							int zPos = zp < tile.getPos().getZ() ? 1 : zp > tile.getPos().getZ() ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
						}
					}
	}

	/**
	 * Checks for any range amplifying blocks below a specific BlockPos
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @return A number between 0 and 2, representing the amount of range amplifiers below this BlockPos
	 */
	private static int getPillars(World world, BlockPos pos){
		Block block1 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();
		Block block2 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ())).getBlock();
		int num = 0;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE)
			num = 1;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE
				&& block2 != null && block2 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block2).getAmplifierType() == AmplifierType.RANGE)
			num = 2;
		return num;
	}

	/**
	 * Checks if a TileEntity is a PE Collector (extends IEnergyContainer and can accept PE)
	 * @param tile TileEntity to check
	 * @return True if the TileEntity is a PE Collector, otherwise false
	 */
	private static boolean isCollector(TileEntity tile){
		if(tile != null)
			return tile instanceof IEnergyContainer && ((IEnergyContainer)tile).canAcceptPE();
		return false;
	}

	/**
	 * Checks that there aren't any PE Collectors next to the BlockPos
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @return True if the BlockPos has no PE Collectors near it, otherwise false
	 */
	private static boolean checkNearbyBlocks(World world, BlockPos pos){
		boolean flag1 = isCollector(world.getTileEntity(pos.up()));
		boolean flag2 = isCollector(world.getTileEntity(pos.down()));
		boolean flag3 = isCollector(world.getTileEntity(pos.north()));
		boolean flag4 = isCollector(world.getTileEntity(pos.west()));
		boolean flag5 = isCollector(world.getTileEntity(pos.south()));
		boolean flag6 = isCollector(world.getTileEntity(pos.east()));
		return !flag1 && !flag2 && !flag3 && !flag4 && !flag5 && !flag6;
	}
}
