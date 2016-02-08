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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyAmplifier;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class TileStatueDirectional extends TEDirectional implements IEnergyManipulator, ITickable {

	private int timer;
	private final int timerMax = 120;
	private int activationTimer;
	private AmplifierType currentAmplifier;
	private DeityType currentDeity;


	//	@Override
	//	public boolean canUpdate()
	//	{
	//		return true;
	//	}

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
		Block block1 = worldObj.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();
		Block block2 = worldObj.getBlockState(new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ())).getBlock();
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
			worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, pos.getX(), pos.getY() + 1, pos.getZ()));
			DisruptionHandler.instance().generateDisruption(getDeity(), worldObj, pos, worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getBlockState(pos).getBlock().getCollisionBoundingBox(worldObj, pos, worldObj.getBlockState(pos)).expand(16, 16, 16)));
		}
	}

	@Override
	public void update(){

		if(isActive()){
			activationTimer--;
			worldObj.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0, 0, 0);
		}

		int range = (int) (7 + getPillarMultiplier() + getAmplifier(AmplifierType.RANGE));

		int xp = pos.getX();
		int yp = pos.getY();
		int zp = pos.getZ();

		if(!(worldObj.getTileEntity(new BlockPos(xp, yp + 1, zp)) instanceof IEnergyManipulator) &&
				!(worldObj.getTileEntity(new BlockPos(xp, yp - 1, zp)) instanceof IEnergyManipulator) &&
				!(worldObj.getTileEntity(new BlockPos(xp, yp + 2, zp)) instanceof IEnergyManipulator) &&
				!(worldObj.getTileEntity(new BlockPos(xp, yp - 2, zp)) instanceof IEnergyManipulator)){
			if(worldObj.getBlockState(pos).getBlock().getCollisionBoundingBox(worldObj, pos, worldObj.getBlockState(pos)) != null){
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getBlockState(pos).getBlock().getCollisionBoundingBox(worldObj, pos, worldObj.getBlockState(pos)).expand(range, range, range));

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
									int xPos = xp < (int) player.posX ? 1 : xp > (int) player.posX ? -1 : 0;
									int yPos = yp < (int) player.posY ? 1 : yp > (int) player.posY ? -1 : 0;
									int zPos = zp < (int) player.posZ ? 1 : zp > (int) player.posZ ? -1 : 0;
									double x = i * Math.cos(i) / 2 * xPos;
									double y = i * Math.sin(i) / 2 * yPos;
									double z = i * Math.sin(i) / 2 * zPos;
									worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
								}
							}
						}
					}
			}

			TileEntity pedestal1 = null;
			TileEntity pedestal2 = null;
			TileEntity pedestal3 = null;
			TileEntity pedestal4 = null;

			if(worldObj.getTileEntity(new BlockPos(xp, yp, zp + 3)) != null)
				pedestal1 = worldObj.getTileEntity(new BlockPos(xp, zp, zp + 3));
			if(worldObj.getTileEntity(new BlockPos(xp, yp - 1, zp + 3)) != null && pedestal1 == null)
				pedestal1 = worldObj.getTileEntity(new BlockPos(xp, yp - 1, zp + 3));
			if(worldObj.getTileEntity(new BlockPos(xp, yp - 2, zp + 3)) != null && pedestal1 == null)
				pedestal1 = worldObj.getTileEntity(new BlockPos(xp, yp - 2, zp + 3));

			if(worldObj.getTileEntity(new BlockPos(xp, yp, zp - 3)) != null)
				pedestal2 = worldObj.getTileEntity(new BlockPos(xp, yp, zp - 3));
			if(worldObj.getTileEntity(new BlockPos(xp, yp - 1, zp - 3)) != null && pedestal2 == null)
				pedestal2 = worldObj.getTileEntity(new BlockPos(xp, yp - 1, zp - 3));
			if(worldObj.getTileEntity(new BlockPos(xp, yp - 2, zp - 3)) != null && pedestal2 == null)
				pedestal2 = worldObj.getTileEntity(new BlockPos(xp, yp - 2, zp - 3));

			if(worldObj.getTileEntity(new BlockPos(xp + 3, yp, zp)) != null)
				pedestal3 = worldObj.getTileEntity(new BlockPos(xp + 3, yp, zp));
			if(worldObj.getTileEntity(new BlockPos(xp + 3, yp - 1, zp)) != null && pedestal3 == null)
				pedestal3 = worldObj.getTileEntity(new BlockPos(xp + 3, yp - 1, zp));
			if(worldObj.getTileEntity(new BlockPos(xp + 3, yp - 2, zp)) != null && pedestal3 == null)
				pedestal3 = worldObj.getTileEntity(new BlockPos(xp + 3, yp - 2, zp));

			if(worldObj.getTileEntity(new BlockPos(xp - 3, yp, zp)) != null)
				pedestal4 = worldObj.getTileEntity(new BlockPos(xp - 3, yp, zp));
			if(worldObj.getTileEntity(new BlockPos(xp - 3, yp - 1, zp)) != null && pedestal4 == null)
				pedestal4 = worldObj.getTileEntity(new BlockPos(xp - 3, yp - 1, zp));
			if(worldObj.getTileEntity(new BlockPos(xp - 3, yp - 2, zp)) != null && pedestal4 == null)
				pedestal4 = worldObj.getTileEntity(new BlockPos(xp - 3, yp - 2, zp));

			if(pedestal1 != null && pedestal1 instanceof IEnergyContainer && ((IEnergyContainer) pedestal1).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal1).getContainedEnergy() < ((IEnergyContainer) pedestal1).getMaxEnergy()){
						((IEnergyContainer) pedestal1).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xp < pedestal1.getPos().getX() ? 1 : xp > pedestal1.getPos().getX() ? -1 : 0;
							int yPos = yp < pedestal1.getPos().getY() ? 1 : yp > pedestal1.getPos().getY() ? -1 : 0;
							int zPos = zp < pedestal1.getPos().getZ() ? 1 : zp > pedestal1.getPos().getZ() ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
						}
					}
			if(pedestal2 != null && pedestal2 instanceof IEnergyContainer && ((IEnergyContainer) pedestal2).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal2).getContainedEnergy() < ((IEnergyContainer) pedestal2).getMaxEnergy()){
						((IEnergyContainer) pedestal2).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xp < pedestal2.getPos().getX() ? 1 : xp > pedestal2.getPos().getX() ? -1 : 0;
							int yPos = yp < pedestal2.getPos().getY() ? 1 : yp > pedestal2.getPos().getY() ? -1 : 0;
							int zPos = zp < pedestal2.getPos().getZ() ? 1 : zp > pedestal2.getPos().getZ() ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
						}
					}
			if(pedestal3 != null && pedestal3 instanceof IEnergyContainer && ((IEnergyContainer) pedestal3).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal3).getContainedEnergy() < ((IEnergyContainer) pedestal3).getMaxEnergy()){
						((IEnergyContainer) pedestal3).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xp < pedestal3.getPos().getX() ? 1 : xp > pedestal3.getPos().getX() ? -1 : 0;
							int yPos = yp < pedestal3.getPos().getY() ? 1 : yp > pedestal3.getPos().getY() ? -1 : 0;
							int zPos = zp < pedestal3.getPos().getZ() ? 1 : zp > pedestal3.getPos().getZ() ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
						}
					}
			if(pedestal4 != null && pedestal4 instanceof IEnergyContainer && ((IEnergyContainer) pedestal4).canAcceptPE())
				if(worldObj.rand.nextInt(timerMax-(int)(20 * getAmplifier(AmplifierType.DURATION))) == 0)
					if(((IEnergyContainer) pedestal4).getContainedEnergy() < ((IEnergyContainer) pedestal4).getMaxEnergy()){
						((IEnergyContainer) pedestal4).addEnergy(energyQuanta());
						for(double i = 0; i <= 0.7; i += 0.03) {
							int xPos = xp < pedestal4.getPos().getX() ? 1 : xp > pedestal4.getPos().getX() ? -1 : 0;
							int yPos = yp < pedestal4.getPos().getY() ? 1 : yp > pedestal4.getPos().getY() ? -1 : 0;
							int zPos = zp < pedestal4.getPos().getZ() ? 1 : zp > pedestal4.getPos().getZ() ? -1 : 0;
							double x = i * Math.cos(i) / 2 * xPos;
							double y = i * Math.sin(i) / 2 * yPos;
							double z = i * Math.sin(i) / 2 * zPos;
							worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
						}
					}
		}
		disrupt(worldObj.rand.nextInt(20 * (isActive() ? 40 : 200) * (worldObj.getClosestPlayer(xp, yp, zp, range * 2) != null ? 1 : 10)) == 0);
		clearData();
	}
}
