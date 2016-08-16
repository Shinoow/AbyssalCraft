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
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
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
		if(nbttagcompound.hasKey("Deity") && !nbttagcompound.hasKey("ActiveDeity")){//Converting the old tags
			nbttagcompound.setString("ActiveDeity", nbttagcompound.getString("Deity"));
			nbttagcompound.removeTag("Deity");
		}
		if(nbttagcompound.hasKey("Amplifier") && !nbttagcompound.hasKey("ActiveAmplifier")){
			nbttagcompound.setString("ActiveAmplifier", nbttagcompound.getString("Amplifier"));
			nbttagcompound.removeTag("Amplifier");
		}
		PEUtils.readManipulatorNBT(this, nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Timer", timer);
		nbttagcompound.setInteger("ActivationTimer", activationTimer);
		PEUtils.writeManipulatorNBT(this, nbttagcompound);

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
			setActiveDeity(deity);
			setActiveAmplifier(amp);
		}
	}

	@Override
	public boolean isActive(){
		return activationTimer > 0;
	}

	@Override
	public float getEnergyQuanta() {

		return isActive() ? 10 * Math.max(getAmplifier(AmplifierType.POWER), 1.0F) : 5;
	}

	@Override
	public DeityType getDeity() {

		return null;
	}

	@Override
	public float getAmplifier(AmplifierType type){

		if(type == currentAmplifier)
			switch(type){
			case DURATION:
				return currentDeity == getDeity() ? 4 : 2;
			case POWER:
				return currentDeity == getDeity() ? 2.5F : 1.5F;
			case RANGE:
				return currentDeity == getDeity() ? 6 : 4;
			default:
				return 0;
			}
		else return 0;
	}

	@Override
	public DeityType getActiveDeity() {

		return currentDeity;
	}

	@Override
	public AmplifierType getActiveAmplifier() {

		return currentAmplifier;
	}

	@Override
	public void setActiveDeity(DeityType deity) {

		currentDeity = deity;
	}

	@Override
	public void setActiveAmplifier(AmplifierType amplifier) {

		currentAmplifier = amplifier;
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

		int range = (int) (7 + PEUtils.getRangeAmplifiers(worldObj, pos)*4 + getAmplifier(AmplifierType.RANGE));

		int xp = pos.getX();
		int yp = pos.getY();
		int zp = pos.getZ();

		if(PEUtils.checkForAdjacentManipulators(worldObj, pos)){
			if(worldObj.func_184137_a(xp, yp, zp, range, false) != null &&
					EntityUtil.hasNecronomicon(worldObj.func_184137_a(xp, yp, zp, range, false))){
				ItemStack item = worldObj.func_184137_a(xp, yp, zp, range, false).getHeldItem(EnumHand.MAIN_HAND);
				ItemStack item1 = worldObj.func_184137_a(xp, yp, zp, range, false).getHeldItem(EnumHand.OFF_HAND);
				if(item != null && item.getItem() instanceof IEnergyTransporterItem ||
						item1 != null && item1.getItem() instanceof IEnergyTransporterItem){
					timer++;
					if(timer >= (int)(timerMax / Math.max(getAmplifier(AmplifierType.DURATION), 1.0F))){
						timer = worldObj.rand.nextInt(10);
						PEUtils.transferPEToNearbyPlayers(worldObj, pos, this, range);
					}
				}
			}

			PEUtils.transferPEToCollectors(worldObj, pos, this, (int)(PEUtils.getRangeAmplifiers(worldObj, pos) + getAmplifier(AmplifierType.RANGE)/2));
		}
		disrupt(worldObj.rand.nextInt(20 * (isActive() ? 40 : 200) * (worldObj.func_184137_a(xp, yp, zp, range * 2, true) != null ? 1 : 10)) == 0);
		PEUtils.clearManipulatorData(this);
	}
}