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
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.lib.util.blocks.ISingletonInventory;

public class TileEntitySacrificialAltar extends TileEntity implements IEnergyContainer, ISingletonInventory, ITickable {

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
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
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
	public void update()
	{
		if(isDirty){
			worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
			isDirty = false;
		}

		if(rot == 360)
			rot = 0;
		if(item != null)
			rot++;

		if(isCoolingDown())
			coolDown--;

		if(item != null)
			if(item.getItem() instanceof IEnergyContainerItem)
				if(!worldObj.isRemote && ((IEnergyContainerItem) item.getItem()).canAcceptPE(item) && canTransferPE())
					((IEnergyContainerItem) item.getItem()).addEnergy(item, consumeEnergy(1));

		if(entity == null){
			List<EntityLivingBase> mobs = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(8, 3, 8));

			for(EntityLivingBase mob : mobs)
				if(!(mob instanceof EntityPlayer) && !(mob instanceof EntityArmorStand))
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
		if(collectionLimit >= 1000){
			collectionLimit = 0;
			coolDown = 1200;
		}

		if(getContainedEnergy() > getMaxEnergy())
			energy = getMaxEnergy();
	}

	@Override
	public int getRotation(){
		return rot;
	}

	@Override
	public ItemStack getItem(){
		return item;
	}

	@Override
	public void setItem(ItemStack item){
		this.item = item;
		isDirty = true;
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

		return 5000;
	}

	@Override
	public void addEnergy(float energy) {
		isDirty = true;
		this.energy += energy;
		if(this.energy > getMaxEnergy()) this.energy = getMaxEnergy();
	}

	@Override
	public float consumeEnergy(float energy) {
		isDirty = true;
		if(energy < this.energy){
			this.energy -= energy;
			return energy;
		} else {
			float ret = this.energy;
			this.energy = 0;
			return ret;
		}
	}

	@Override
	public boolean canAcceptPE() {
		return false;
	}

	@Override
	public boolean canTransferPE() {

		return getContainedEnergy() > 0;
	}

	@Override
	public TileEntity getContainerTile() {

		return this;
	}
}