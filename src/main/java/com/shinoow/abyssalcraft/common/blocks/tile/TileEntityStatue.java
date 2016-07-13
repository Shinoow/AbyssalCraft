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

import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyAmplifier;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporterItem;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

public class TileEntityStatue extends TileEntity implements IEnergyManipulator, ITickable {

	private int timer;
	private final int timerMax = 120;
	private int activationTimer;
	private AmplifierType currentAmplifier;
	private DeityType currentDeity;

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
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(pos, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		readFromNBT(packet.getNbtCompound());
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
	public float getEnergyQuanta() {

		return isActive() ? 10 * getAmplifier(AmplifierType.POWER) : 5;
	}

	@Override
	public DeityType getDeity() {

		return null;
	}

	private int getPillarMultiplier(){
		Block block1 = worldObj.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();
		Block block2 = worldObj.getBlockState(new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ())).getBlock();
		int num = 0;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE)
			num = 4;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE
				&& block2 != null && block2 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block2).getAmplifierType() == AmplifierType.RANGE)
			num = 8;
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
			if(!worldObj.isRemote)
				worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, pos.getX(), pos.getY() + 1, pos.getZ(), false));
			DisruptionHandler.instance().generateDisruption(getDeity(), worldObj, pos, worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16, 16, 16)));
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
			if(worldObj.func_184137_a(xp, yp, zp, range, false) != null &&
					EntityUtil.hasNecronomicon(worldObj.func_184137_a(xp, yp, zp, range, false))){
				ItemStack item = worldObj.func_184137_a(xp, yp, zp, range, false).getHeldItem(EnumHand.MAIN_HAND);
				ItemStack item1 = worldObj.func_184137_a(xp, yp, zp, range, false).getHeldItem(EnumHand.OFF_HAND);
				if(item != null && item.getItem() instanceof IEnergyTransporterItem ||
						item1 != null && item1.getItem() instanceof IEnergyTransporterItem){
					timer++;
					if(timer >= (int)(timerMax / getAmplifier(AmplifierType.DURATION))){
						timer = worldObj.rand.nextInt(10);
						PEUtils.transferPEToNearbyPlayers(worldObj, pos, this, range);
					}
				}
			}

			PEUtils.transferPEToCollectors(worldObj, pos, this, (int)(getPillarMultiplier()/4 + getAmplifier(AmplifierType.RANGE)/2));
		}
		disrupt(worldObj.rand.nextInt(20 * (isActive() ? 40 : 200) * (worldObj.func_184137_a(xp, yp, zp, range * 2, true) != null ? 1 : 10)) == 0);
		clearData();
	}
}