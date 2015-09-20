/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class TileStatueDirectional extends TEDirectional implements IEnergyManipulator {

	private int timer;

	@Override
	public boolean canUpdate()
	{
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		timer = nbttagcompound.getInteger("Timer");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Timer", timer);
	}

	@Override
	public int drainSpeed() {

		return 1;
	}

	@Override
	public float energyQuanta() {

		return 10;
	}

	@Override
	public int stability() {

		return 100;
	}

	@Override
	public void updateEntity(){
		super.updateEntity();

		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord).expand(16, 16, 16));

		for(EntityPlayer player : players)
			if(EntityUtil.hasNecronomicon(player)){
				ItemStack item = player.getCurrentEquippedItem();
				if(item != null && item.getItem() instanceof IEnergyTransporter){
					timer++;
					if(timer >= 60){
						timer = worldObj.rand.nextInt(10);
						((IEnergyTransporter) item.getItem()).addEnergy(item, energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xCoord < player.posX ? 1 : xCoord > player.posX ? -1 : 0;
							int yPos = yCoord < player.posY ? 1 : yCoord > player.posY ? -1 : 0;
							int zPos = zCoord < player.posZ ? 1 : zCoord > player.posZ ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.tan(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
						}
					}
				}
			}

		TileEntity pedestal1 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 3);
		TileEntity pedestal2 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 3);
		TileEntity pedestal3 = worldObj.getTileEntity(xCoord + 3, yCoord, zCoord);
		TileEntity pedestal4 = worldObj.getTileEntity(xCoord - 3, yCoord, zCoord);

		if(pedestal1 != null && pedestal1 instanceof IEnergyContainer)
			if(worldObj.rand.nextInt(40) == 0)
				if(((IEnergyContainer) pedestal1).getContainedEnergy() < ((IEnergyContainer) pedestal1).getMaxEnergy()){
					((IEnergyContainer) pedestal1).addEnergy(energyQuanta());
					for(double i = 0; i <= 0.7; i += 0.03) {
						int xPos = xCoord < pedestal1.xCoord ? 1 : xCoord > pedestal1.xCoord ? -1 : 0;
						int zPos = zCoord < pedestal1.zCoord ? 1 : zCoord > pedestal1.zCoord ? -1 : 0;
						double x = i * Math.cos(i) / 2 * xPos;
						double z = i * Math.sin(i) / 2 * zPos;
						worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, 0, z);
					}
				}
		if(pedestal2 != null && pedestal2 instanceof IEnergyContainer)
			if(worldObj.rand.nextInt(40) == 0)
				if(((IEnergyContainer) pedestal2).getContainedEnergy() < ((IEnergyContainer) pedestal2).getMaxEnergy()){
					((IEnergyContainer) pedestal2).addEnergy(energyQuanta());
					for(double i = 0; i <= 0.7; i += 0.03) {
						int xPos = xCoord < pedestal2.xCoord ? 1 : xCoord > pedestal2.xCoord ? -1 : 0;
						int zPos = zCoord < pedestal2.zCoord ? 1 : zCoord > pedestal2.zCoord ? -1 : 0;
						double x = i * Math.cos(i) / 2 * xPos;
						double z = i * Math.sin(i) / 2 * zPos;
						worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, 0, z);
					}
				}
		if(pedestal3 != null && pedestal3 instanceof IEnergyContainer)
			if(worldObj.rand.nextInt(40) == 0)
				if(((IEnergyContainer) pedestal3).getContainedEnergy() < ((IEnergyContainer) pedestal3).getMaxEnergy()){
					((IEnergyContainer) pedestal3).addEnergy(energyQuanta());
					for(double i = 0; i <= 0.7; i += 0.03) {
						int xPos = xCoord < pedestal3.xCoord ? 1 : xCoord > pedestal3.xCoord ? -1 : 0;
						int zPos = zCoord < pedestal3.zCoord ? 1 : zCoord > pedestal3.zCoord ? -1 : 0;
						double x = i * Math.cos(i) / 2 * xPos;
						double z = i * Math.sin(i) / 2 * zPos;
						worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, 0, z);
					}
				}
		if(pedestal4 != null && pedestal4 instanceof IEnergyContainer)
			if(worldObj.rand.nextInt(40) == 0)
				if(((IEnergyContainer) pedestal4).getContainedEnergy() < ((IEnergyContainer) pedestal4).getMaxEnergy()){
					((IEnergyContainer) pedestal4).addEnergy(energyQuanta());
					for(double i = 0; i <= 0.7; i += 0.03) {
						int xPos = xCoord < pedestal4.xCoord ? 1 : xCoord > pedestal4.xCoord ? -1 : 0;
						int zPos = zCoord < pedestal4.zCoord ? 1 : zCoord > pedestal4.zCoord ? -1 : 0;
						double x = i * Math.cos(i) / 2 * xPos;
						double z = i * Math.sin(i) / 2 * zPos;
						worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, 0, z);
					}
				}
	}
}
