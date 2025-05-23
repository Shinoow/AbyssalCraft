/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ISingletonInventory;
import com.shinoow.abyssalcraft.api.energy.IEnergyCollector;
import com.shinoow.abyssalcraft.api.energy.PEUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntitySacrificialAltar extends TileEntity implements IEnergyCollector, ISingletonInventory, ITickable {

	private ItemStack item = ItemStack.EMPTY;
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
		item = new ItemStack(nbtItem);
		energy = nbttagcompound.getFloat("PotEnergy");
		collectionLimit = nbttagcompound.getInteger("CollectionLimit");
		coolDown = nbttagcompound.getInteger("CoolDown");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(!item.isEmpty())
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
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
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			isDirty = false;
		}

		if(isCoolingDown())
			coolDown--;

		if(!world.isRemote)
			PEUtils.transferPEFromContainer(item, this, 20);

		if(entity == null){
			List<EntityLivingBase> mobs = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(8, 3, 8));

			for(EntityLivingBase mob : mobs)
				if(!(mob instanceof EntityPlayer) && !(mob instanceof EntityArmorStand))
					if(mob.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && mob.getCreatureAttribute() != AbyssalCraftAPI.SHADOW)
						if(mob.isEntityAlive())
							if(!mob.isChild()){
								entity = mob;
								break;
							}
		}

		if(entity != null){
			if(getContainedEnergy() < getMaxEnergy())
				entity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20, 0, false, false));
			if(!entity.isEntityAlive()){
				if(entity.getLastDamageSource() != null && !isCoolingDown() && getContainedEnergy() < getMaxEnergy()){
					float num = entity.getMaxHealth();
					addEnergy(num);
					collectionLimit += num;
				}
				entity = null;
			}
		}
		if(collectionLimit >= getMaxEnergy() / 5){
			collectionLimit = 0;
			coolDown = getCooldownStartNumber();
		}

		if(getContainedEnergy() > getMaxEnergy())
			energy = getMaxEnergy();
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

	protected int getCooldownStartNumber() {
		return 1200;
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
	public boolean canAcceptPE() {
		return false;
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
