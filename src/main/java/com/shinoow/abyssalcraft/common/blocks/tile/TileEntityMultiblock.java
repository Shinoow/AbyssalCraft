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

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.structure.IPlaceOfPower;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureBase;
import com.shinoow.abyssalcraft.api.energy.structure.StructureHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityMultiblock extends TileEntity implements ITickable, IStructureBase {

	private int ticksExisted;
	private IPlaceOfPower place;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		place = StructureHandler.instance().getStructureByName(nbttagcompound.getString("Structure"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		if(place != null)
			nbttagcompound.setString("Structure", place.getIdentifier());

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
	public void update() {
		++ticksExisted;

		if(ticksExisted % 100 == 0 && place != null)
			place.validate(world, pos);
	}

	@Override
	public IPlaceOfPower getMultiblock() {

		return place;
	}

	@Override
	public void setMultiblock(IPlaceOfPower multiblock) {
		place = multiblock;
	}

	@Override
	public float getAmplifier(AmplifierType type) {

		if(place != null)
			return place.getAmplifier(type);
		return 0;
	}
}
