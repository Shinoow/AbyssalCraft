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
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.common.util.ISingletonInventory;

public class TileEntityTieredSacrificialAltar extends TileEntity implements IEnergyContainer, ISingletonInventory, ITickable {

	private ItemStack item;
	private int rot;
	private float energy;
	Random rand = new Random();
	EntityLivingBase entity;
	private int collectionLimit;
	private int coolDown;
	private boolean isDirty;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = ItemStack.loadItemStackFromNBT(nbtItem);
		rot = nbttagcompound.getInteger("Rot");
		energy = nbttagcompound.getFloat("PotEnergy");
		collectionLimit = nbttagcompound.getInteger("CollectionLimit");
		coolDown = nbttagcompound.getInteger("CoolDown");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(item != null)
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		nbttagcompound.setInteger("Rot", rot);
		nbttagcompound.setFloat("PotEnergy", energy);
		nbttagcompound.setInteger("CollectionLimit", collectionLimit);
		nbttagcompound.setInteger("CoolDown", coolDown);
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
	public void update()
	{
		if(isDirty)
			//			worldObj.markBlockForUpdate(pos); //TODO: find replacement
			isDirty = false;

		if(rot == 360)
			rot = 0;
		if(item != null)
			rot++;

		if(isCoolingDown())
			coolDown--;

		if(item != null)
			if(item.getItem() instanceof IEnergyTransporter)
				if(getContainedEnergy() > 0 && ((IEnergyTransporter) item.getItem()).getContainedEnergy(item) < ((IEnergyTransporter) item.getItem()).getMaxEnergy(item)){
					((IEnergyTransporter) item.getItem()).addEnergy(item, 1);
					consumeEnergy(1);
				}

		if(entity == null){
			List<EntityLivingBase> mobs = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(8, 3, 8));

			for(EntityLivingBase mob : mobs)
				if(!(mob instanceof EntityPlayer))
					if(mob.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD)
						if(mob.isEntityAlive())
							if(!mob.isChild()){
								entity = mob;
								break;
							}
		}

		if(entity != null){
			if(getContainedEnergy() < getMaxEnergy())
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX, entity.posY, entity.posZ, 0, 0, 0);
			if(!entity.isEntityAlive()){
				float num = entity.getMaxHealth();
				entity = null;
				if(!isCoolingDown() && getContainedEnergy() < getMaxEnergy()){
					//				if(entity.getLastAttacker() instanceof EntityPlayer){
					addEnergy(num);
					//				}
					collectionLimit += num;
				}
			}
		}
		if(collectionLimit >= getCollectionLimit()){
			collectionLimit = 0;
			coolDown = getCooldown();
		}

		if(getContainedEnergy() > getMaxEnergy())
			energy = getMaxEnergy();
	}

	private int getCollectionLimit(){
		return getMaxEnergy() / 5;
	}

	private int getCooldown(){
		int base = 1200;
		switch(getBlockMetadata()){
		case 0:
			return  (int) (base * 1.5);
		case 1:
			return base * 2;
		case 2:
			return (int) (base * 2.5);
		case 3:
			return base * 3;
		default:
			return base;
		}
	}

	public int getRotation(){
		return rot;
	}

	@Override
	public ItemStack getItem(){
		return item;
	}

	@Override
	public void setItem(ItemStack item){
		isDirty = true;
		this.item = item;
	}

	public int getCooldownTimer(){
		return coolDown;
	}

	public boolean isCoolingDown(){
		return coolDown > 0;
	}

	@Override
	public float getContainedEnergy() {
		return energy;
	}

	@Override
	public int getMaxEnergy() {
		int base = 5000;
		switch(getBlockMetadata()){
		case 0:
			return  (int) (base * 1.5);
		case 1:
			return base * 2;
		case 2:
			return (int) (base * 2.5);
		case 3:
			return base * 3;
		default:
			return base;
		}
	}

	@Override
	public void addEnergy(float energy) {
		float multiplier = 1.0F;
		switch(getBlockMetadata()){
		case 0:
			multiplier = 1.25F;
			break;
		case 1:
			multiplier = 1.5F;
			break;
		case 2:
			multiplier = 1.75F;
			break;
		case 3:
			multiplier = 2.0F;
			break;
		}
		this.energy += energy * multiplier;
	}

	@Override
	public void consumeEnergy(float energy) {
		this.energy -= energy;
	}

	@Override
	public boolean canAcceptPE() {
		return false;
	}
}
