/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporterItem;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityStatue extends TileEntity implements IEnergyManipulator, ITickable {

	private int timer;
	private final int timerMax = 120;
	private int activationTimer;
	private AmplifierType currentAmplifier;
	private DeityType currentDeity;
	private int tolerance;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		timer = nbttagcompound.getInteger("Timer");
		activationTimer = nbttagcompound.getInteger("ActivationTimer");
		tolerance = nbttagcompound.getInteger("Tolerance");
		PEUtils.readManipulatorNBT(this, nbttagcompound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Timer", timer);
		nbttagcompound.setInteger("ActivationTimer", activationTimer);
		nbttagcompound.setInteger("Tolerance", tolerance);
		PEUtils.writeManipulatorNBT(this, nbttagcompound);

		return nbttagcompound;
	}

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.loadedTileEntityList.remove(this);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
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
	public void addTolerance(int num) {
		tolerance += num;
	}

	@Override
	public int getTolerance() {

		return tolerance;
	}

	@Override
	public boolean canTransferPE() {

		return true;
	}

	@Override
	public void disrupt() {
		tolerance = 0;
		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 1, pos.getZ(), false));
		DisruptionHandler.instance().generateDisruption(getDeity(), world, pos, world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16, 16, 16)));
	}

	@Override
	public void update(){

		if(isActive()){
			activationTimer--;
			world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0, 0, 0);
		} else PEUtils.clearManipulatorData(this);

		int range = (int) (7 + PEUtils.getRangeAmplifiers(world, pos)*4 + getAmplifier(AmplifierType.RANGE));

		int xp = pos.getX();
		int yp = pos.getY();
		int zp = pos.getZ();

		if(world.canBlockSeeSky(pos))
			if(PEUtils.checkForAdjacentManipulators(world, pos)){
				if(world.getClosestPlayer(xp, yp, zp, range, false) != null &&
						EntityUtil.hasNecronomicon(world.getClosestPlayer(xp, yp, zp, range, false))){
					ItemStack item = world.getClosestPlayer(xp, yp, zp, range, false).getHeldItem(EnumHand.MAIN_HAND);
					ItemStack item1 = world.getClosestPlayer(xp, yp, zp, range, false).getHeldItem(EnumHand.OFF_HAND);
					if(!item.isEmpty() && item.getItem() instanceof IEnergyTransporterItem ||
							!item1.isEmpty() && item1.getItem() instanceof IEnergyTransporterItem){
						timer++;
						if(timer >= (int)(timerMax / Math.max(getAmplifier(AmplifierType.DURATION), 1.0F))){
							timer = world.rand.nextInt(10);
							PEUtils.transferPEToNearbyPlayers(world, pos, this, range);
						}
					}
				}

				PEUtils.transferPEToCollectors(world, pos, this, (int)(PEUtils.getRangeAmplifiers(world, pos) + getAmplifier(AmplifierType.RANGE)/2));
			}
		if(tolerance >= 100)
			disrupt();
	}
}
