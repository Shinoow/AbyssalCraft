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
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.HashSet;
import java.util.Set;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporterItem;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureComponent;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class TileEntityStatue extends TileEntity implements IEnergyManipulator, ITickable, IStructureComponent {

	private int timer;
	private final int timerMax = 120;
	private AmplifierType currentAmplifier;
	private DeityType currentDeity;
	private int tolerance;
	//	private int facing;
	private boolean isMultiblock;
	private BlockPos basePos;
	private Set<BlockPos> positions = new HashSet<>();

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		timer = nbttagcompound.getInteger("Timer");
		tolerance = nbttagcompound.getInteger("Tolerance");
		PEUtils.readManipulatorNBT(this, nbttagcompound);
		//		facing = nbttagcompound.getInteger("Facing");
		isMultiblock = nbttagcompound.getBoolean("IsMultiblock");
		basePos = BlockPos.fromLong(nbttagcompound.getLong("BasePosition"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Timer", timer);
		nbttagcompound.setInteger("Tolerance", tolerance);
		PEUtils.writeManipulatorNBT(this, nbttagcompound);
		//		nbttagcompound.setInteger("Facing", facing);
		nbttagcompound.setBoolean("IsMultiblock", isMultiblock);
		if(basePos != null)
			nbttagcompound.setLong("BasePosition", basePos.toLong());

		return nbttagcompound;
	}

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
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
			setActiveDeity(deity);
			setActiveAmplifier(amp);
		}
	}

	@Override
	public boolean isActive(){
		return currentAmplifier != null;
	}

	@Override
	public float getEnergyQuanta() {

		return isActive() ? 10 * (Math.max(getAmplifier(AmplifierType.POWER), 1.0F) + PEUtils.getStructureAmplifier(world, this, AmplifierType.POWER)) : 5;
	}

	@Override
	public DeityType getDeity(IBlockState state) {

		switch(((BlockStatue)state.getBlock()).TYPE) {
		case AZATHOTH:
			return DeityType.AZATHOTH;
		case CTHULHU:
			return DeityType.CTHULHU;
		case HASTUR:
			return DeityType.HASTUR;
		case JZAHAR:
			return DeityType.JZAHAR;
		case NYARLATHOTEP:
			return DeityType.NYARLATHOTEP;
		case SHUBNIGGURATH:
			return DeityType.SHUBNIGGURATH;
		case YOGSOTHOTH:
			return DeityType.YOGSOTHOTH;
		default:
			return null;
		}
	}

	@Override
	public float getAmplifier(AmplifierType type){

		if(type == currentAmplifier)
			switch(type){
			case DURATION:
				return currentDeity == getDeity(world.getBlockState(pos)) ? 4 : 2;
			case POWER:
				return currentDeity == getDeity(world.getBlockState(pos)) ? 2.5F : 1.5F;
			case RANGE:
				return currentDeity == getDeity(world.getBlockState(pos)) ? 6 : 4;
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
		if(!isInMultiblock())
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
		if(world.provider.getDimension() != ACLib.omothol_id && !isInMultiblock() && !ACConfig.no_disruptions) {
			world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 1, pos.getZ(), true));
			DisruptionHandler.instance().generateDisruption(getDeity(world.getBlockState(pos)), world, pos, world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(16, 16, 16)));
		}
	}

	@Override
	public Set<BlockPos> getEnergyCollectors(){
		return positions;
	}

	@Override
	public void update(){

		if(isActive())
			((WorldServer)world).spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0, 0, 0, 0, 1.0);
		else PEUtils.clearManipulatorData(this);

		int range = (int) (7 + PEUtils.getRangeAmplifiers(world, pos, this)*4 + getAmplifier(AmplifierType.RANGE));

		if(world.canBlockSeeSky(pos) || isMultiblock)
			if(PEUtils.checkForAdjacentManipulators(world, pos) || isMultiblock){
				if(world.getWorldTime() % 200 == 0)
					PEUtils.locateCollectors(world, pos, this);
				EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), range, false);
				if(player != null &&
						EntityUtil.hasNecronomicon(player)){
					ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
					ItemStack item1 = player.getHeldItem(EnumHand.OFF_HAND);
					if(!item.isEmpty() && item.getItem() instanceof IEnergyTransporterItem ||
							!item1.isEmpty() && item1.getItem() instanceof IEnergyTransporterItem){
						timer++;
						if(timer >= (int)(timerMax / (Math.max(getAmplifier(AmplifierType.DURATION), 1.0F) + PEUtils.getStructureAmplifier(world, this, AmplifierType.DURATION)))){
							timer = world.rand.nextInt(10);
							PEUtils.transferPEToNearbyPlayers(world, pos, this, range);
						}
					}
				}

				PEUtils.transferPEToNearbyDroppedItems(world, pos, this, range);
				PEUtils.transferPEToCollectors(world, pos, this);
			}
		if(tolerance >= 100)
			disrupt();
	}

	//	public int getFacing(){
	//		return facing;
	//	}
	//
	//	public void setFacing(int face){
	//		facing = face;
	//	}

	@Override
	public boolean isInMultiblock() {

		return isMultiblock;
	}

	@Override
	public void setInMultiblock(boolean bool) {
		isMultiblock = bool;

	}

	@Override
	public BlockPos getBasePosition() {

		return basePos;
	}

	@Override
	public void setBasePosition(BlockPos pos) {
		basePos = pos;
	}
}
