/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.api.energy.IEnergyCollector;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class TileEntityIdolOfFading extends TileEntity implements ITickable, IEnergyCollector {

	private int cooldown;
	private float energy;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		cooldown = nbttagcompound.getInteger("Cooldown");
		energy = nbttagcompound.getFloat("PotEnergy");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Cooldown", cooldown);
		nbttagcompound.setFloat("PotEnergy", energy);

		return nbttagcompound;
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
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
	}

	@Override
	public void update() {
		if(world.getDifficulty() != EnumDifficulty.PEACEFUL && world.getGameRules().getBoolean("doMobSpawning")
				&& !world.canBlockSeeSky(pos.up())){
			cooldown++;
			if (cooldown >= 200 && getContainedEnergy() >= 100) {
				cooldown = 0;
				if(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 32, false) != null){
					EntityLiving mob = getShadow(world);
					setPosition(mob, pos.getX(), pos.getY(), pos.getZ());
					mob.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
					world.spawnEntity(mob);
					consumeEnergy(100);
				}
			}
		}
	}

	private void setPosition(EntityLiving entity, int x, int y, int z){
		if(world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2))){
			entity.setLocationAndAngles(x, y + 1, z, entity.rotationYaw, entity.rotationPitch);
			return;
		}
	}

	public int getCooldown(){
		return cooldown;
	}

	public void setCooldown(int cd){
		cooldown = cd;
	}

	private EntityLiving getShadow(World world) {

		if(world.rand.nextInt(10) == 0)
			return new EntityShadowBeast(world);
		else if(world.rand.nextInt(3) == 0)
			return new EntityShadowMonster(world);

		return new EntityShadowCreature(world);
	}

	@Override
	public float getContainedEnergy() {

		return energy;
	}

	@Override
	public int getMaxEnergy() {

		return 1000;
	}

	@Override
	public TileEntity getContainerTile() {

		return this;
	}

	@Override
	public void setEnergy(float energy) {

		this.energy = energy;
	}
}
