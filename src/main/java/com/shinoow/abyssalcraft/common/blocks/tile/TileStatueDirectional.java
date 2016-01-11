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
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyAmplifier;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class TileStatueDirectional extends TEDirectional implements IEnergyManipulator {

	private int timer;
	private final int timerMax = 120;
	private int activationTimer;
	private AmplifierType currentAmplifier;
	private DeityType currentDeity;


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
		activationTimer = nbttagcompound.getInteger("ActivationTimer");
		if(nbttagcompound.hasKey("Deity") && !nbttagcompound.getString("Deity").equals(""))
			currentDeity = DeityType.valueOf(nbttagcompound.getString("Deity"));
		if(nbttagcompound.hasKey("Amplifier") && !nbttagcompound.getString("Amplifier").equals(""))
			currentAmplifier = AmplifierType.valueOf(nbttagcompound.getString("Amplifier"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Timer", timer);
		nbttagcompound.setInteger("ActivationTimer", activationTimer);
		if(currentDeity != null)
			nbttagcompound.setString("Deity", currentDeity.name());
		if(currentAmplifier != null)
			nbttagcompound.setString("Amplifier", currentAmplifier.name());

	}

	@Override
	public void setActive(AmplifierType amp, DeityType deity){
		if(!isActive()){
			activationTimer = 1200;
			currentAmplifier = amp;
			currentDeity = deity;
		}
	}

	@Override
	public boolean isActive(){
		return activationTimer > 0;
	}

	@Override
	public float energyQuanta() {

		return isActive() ? 10 * getAmplifier(AmplifierType.POWER) : 5;
	}

	@Override
	public DeityType getDeity() {

		return null;
	}

	private int getPillarMultiplier(){
		Block block1 = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		Block block2 = worldObj.getBlock(xCoord, yCoord - 2, zCoord);
		int num = 1;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE)
			num = 5;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE
				&& block2 != null && block2 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block2).getAmplifierType() == AmplifierType.RANGE)
			num = 9;
		return num;
	}

	@Override
	public float getAmplifier(AmplifierType type){

		switch(type){
		case DURATION:
			if(type == currentAmplifier)
				return currentDeity == getDeity() ? 4 : 2;
			else return 1;
		case POWER:
			if(type == currentAmplifier)
				return currentDeity == getDeity() ? 2.5F : 1.5F;
			else return 1;
		case RANGE:
			if(type == currentAmplifier)
				return currentDeity == getDeity() ? 6 : 4;
			else return 0;
		default:
			return 0;
		}
	}

	@Override
	public void clearData(){
		if(!isActive()){
			NBTTagCompound tag = new NBTTagCompound();
			writeToNBT(tag);

			tag.setString("Deity", "");
			tag.setString("Amplifier", "");
			currentDeity = null;
			currentAmplifier = null;

			readFromNBT(tag);
		}
	}

	@Override
	public void disrupt(boolean factor) {
		if(factor){
			worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord + 1, zCoord));
			DisruptionHandler.instance().generateDisruption(getDeity(), worldObj, xCoord, yCoord, zCoord, worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord).expand(16, 16, 16)));
		}
	}

	@Override
	public void updateEntity(){
		super.updateEntity();

		if(isActive()){
			activationTimer--;
			worldObj.spawnParticle("portal", xCoord + 0.5, yCoord + 0.9, zCoord + 0.5, 0, 0, 0);
		}

		int range = (int) (7 + getPillarMultiplier() + getAmplifier(AmplifierType.RANGE));

		if(!(worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof IEnergyManipulator) &&
				!(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof IEnergyManipulator) &&
				!(worldObj.getTileEntity(xCoord, yCoord + 2, zCoord) instanceof IEnergyManipulator) &&
				!(worldObj.getTileEntity(xCoord, yCoord - 2, zCoord) instanceof IEnergyManipulator)){
			if(worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord) != null){
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord).expand(range, range, range));

				for(EntityPlayer player : players)
					if(EntityUtil.hasNecronomicon(player)){
						ItemStack item = player.getCurrentEquippedItem();
						if(item != null && item.getItem() instanceof IEnergyTransporter){
							timer++;
							if(timer >= (int)(timerMax / getAmplifier(AmplifierType.DURATION))){
								timer = worldObj.rand.nextInt(10);
								if(!worldObj.isRemote && ((IEnergyTransporter) item.getItem()).getContainedEnergy(item) < ((IEnergyTransporter) item.getItem()).getMaxEnergy(item))
									((IEnergyTransporter) item.getItem()).addEnergy(item, energyQuanta());
								for(double i = 0; i <= 0.7; i += 0.03) {
									int xPos = xCoord < (int) player.posX ? 1 : xCoord > (int) player.posX ? -1 : 0;
									int yPos = yCoord < (int) player.posY ? 1 : yCoord > (int) player.posY ? -1 : 0;
									int zPos = zCoord < (int) player.posZ ? 1 : zCoord > (int) player.posZ ? -1 : 0;
									double x = i * Math.cos(i) / 2 * xPos;
									double y = i * Math.sin(i) / 2 * yPos;
									double z = i * Math.sin(i) / 2 * zPos;
									worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
								}
							}
						}
					}
			}

			TileEntity pedestal1 = null;
			TileEntity pedestal2 = null;
			TileEntity pedestal3 = null;
			TileEntity pedestal4 = null;

			if(worldObj.getTileEntity(xCoord, yCoord, zCoord + 3) != null)
				pedestal1 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 3);
			if(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 3) != null && pedestal1 == null)
				pedestal1 = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 3);
			if(worldObj.getTileEntity(xCoord, yCoord - 2, zCoord + 3) != null && pedestal1 == null)
				pedestal1 = worldObj.getTileEntity(xCoord, yCoord - 2, zCoord + 3);

			if(worldObj.getTileEntity(xCoord, yCoord, zCoord - 3) != null)
				pedestal2 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 3);
			if(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 3) != null && pedestal2 == null)
				pedestal2 = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 3);
			if(worldObj.getTileEntity(xCoord, yCoord - 2, zCoord - 3) != null && pedestal2 == null)
				pedestal2 = worldObj.getTileEntity(xCoord, yCoord - 2, zCoord - 3);

			if(worldObj.getTileEntity(xCoord + 3, yCoord, zCoord) != null)
				pedestal3 = worldObj.getTileEntity(xCoord + 3, yCoord, zCoord);
			if(worldObj.getTileEntity(xCoord + 3, yCoord - 1, zCoord) != null && pedestal3 == null)
				pedestal3 = worldObj.getTileEntity(xCoord + 3, yCoord - 1, zCoord);
			if(worldObj.getTileEntity(xCoord + 3, yCoord - 2, zCoord) != null && pedestal3 == null)
				pedestal3 = worldObj.getTileEntity(xCoord + 3, yCoord - 2, zCoord);

			if(worldObj.getTileEntity(xCoord - 3, yCoord, zCoord) != null)
				pedestal4 = worldObj.getTileEntity(xCoord - 3, yCoord, zCoord);
			if(worldObj.getTileEntity(xCoord - 3, yCoord - 1, zCoord) != null && pedestal4 == null)
				pedestal4 = worldObj.getTileEntity(xCoord - 3, yCoord - 1, zCoord);
			if(worldObj.getTileEntity(xCoord - 3, yCoord - 2, zCoord) != null && pedestal4 == null)
				pedestal4 = worldObj.getTileEntity(xCoord - 3, yCoord - 2, zCoord);

			if(pedestal1 != null && pedestal1 instanceof IEnergyContainer && ((IEnergyContainer) pedestal1).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal1).getContainedEnergy() < ((IEnergyContainer) pedestal1).getMaxEnergy()){
						((IEnergyContainer) pedestal1).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xCoord < pedestal1.xCoord ? 1 : xCoord > pedestal1.xCoord ? -1 : 0;
							int yPos = yCoord < pedestal1.yCoord ? 1 : yCoord > pedestal1.yCoord ? -1 : 0;
							int zPos = zCoord < pedestal1.zCoord ? 1 : zCoord > pedestal1.zCoord ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
						}
					}
			if(pedestal2 != null && pedestal2 instanceof IEnergyContainer && ((IEnergyContainer) pedestal2).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal2).getContainedEnergy() < ((IEnergyContainer) pedestal2).getMaxEnergy()){
						((IEnergyContainer) pedestal2).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xCoord < pedestal2.xCoord ? 1 : xCoord > pedestal2.xCoord ? -1 : 0;
							int yPos = yCoord < pedestal2.yCoord ? 1 : yCoord > pedestal2.yCoord ? -1 : 0;
							int zPos = zCoord < pedestal2.zCoord ? 1 : zCoord > pedestal2.zCoord ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
						}
					}
			if(pedestal3 != null && pedestal3 instanceof IEnergyContainer && ((IEnergyContainer) pedestal3).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal3).getContainedEnergy() < ((IEnergyContainer) pedestal3).getMaxEnergy()){
						((IEnergyContainer) pedestal3).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xCoord < pedestal3.xCoord ? 1 : xCoord > pedestal3.xCoord ? -1 : 0;
							int yPos = yCoord < pedestal3.yCoord ? 1 : yCoord > pedestal3.yCoord ? -1 : 0;
							int zPos = zCoord < pedestal3.zCoord ? 1 : zCoord > pedestal3.zCoord ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
						}
					}
			if(pedestal4 != null && pedestal4 instanceof IEnergyContainer && ((IEnergyContainer) pedestal4).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal4).getContainedEnergy() < ((IEnergyContainer) pedestal4).getMaxEnergy()){
						((IEnergyContainer) pedestal4).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xCoord < pedestal4.xCoord ? 1 : xCoord > pedestal4.xCoord ? -1 : 0;
							int yPos = yCoord < pedestal4.yCoord ? 1 : yCoord > pedestal4.yCoord ? -1 : 0;
							int zPos = zCoord < pedestal4.zCoord ? 1 : zCoord > pedestal4.zCoord ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
						}
					}
		}
		disrupt(worldObj.rand.nextInt(20 * (isActive() ? 40 : 200) * (worldObj.getClosestPlayer(xCoord, yCoord, zCoord, range * 2) != null ? 1 : 10)) == 0);
		clearData();
	}
}
