/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualPedestal;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityRitualPedestal extends TileEntity implements ITickable, IRitualPedestal {

	private ItemStack item = ItemStack.EMPTY;
	private int itemID, itemMeta;
	private boolean isDirty;
	private BlockPos altarPos;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = new ItemStack(nbtItem);
		if(nbttagcompound.hasKey("AltarPos"))
			altarPos = BlockPos.fromLong(nbttagcompound.getLong("AltarPos"));
		itemID = nbttagcompound.getInteger("ItemID");
		itemMeta = nbttagcompound.getInteger("ItemMeta");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(!item.isEmpty())
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		if(altarPos != null)
			nbttagcompound.setLong("AltarPos", altarPos.toLong());
		nbttagcompound.setInteger("ItemID", itemID);
		nbttagcompound.setInteger("ItemMeta", itemMeta);

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
		IRitualAltar altar = getAltar();
		if(altar != null)
			altar.addPedestal(this);
	}

	@Override
	public void update()
	{
		if(isDirty){
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			isDirty = false;
		}

		IRitualAltar altar = getAltar();
		if(altar != null && altar.isPerformingRitual() && itemID != 0) {
			double xOffset = pos.getX() - altarPos.getX();
			double zOffset = pos.getZ() - altarPos.getZ();
			double velX = xOffset == 0 ? 0 : xOffset < 0 ? 0.5D : -0.5D;
			double velZ = zOffset == 0 ? 0 : zOffset < 0 ? 0.5D : -0.5D;
			spawnParticles(0.5D, 0.5D, velX, velZ, new int[] {itemID, itemMeta}, altar);
		}
	}

	private void spawnParticles(double xOffset, double zOffset, double velX, double velZ, int[] data, IRitualAltar altar) {
		if(!world.isRemote) return;
		switch(altar.getRitualParticle()) {
		case GLYPHS:
			int x = velX != 0 && velZ != 0 ? 4 : 6;
			int z = velZ != 0 && velX != 0 ? 4 : 6;
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, altarPos.getX() + xOffset, altarPos.getY() + 2.05, altarPos.getZ() + zOffset, velX*x,.15,velZ*z);
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5, pos.getY() + 2.05, pos.getZ() + 0.5, 0, .15, 0);
			break;
		case ITEM:
			world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + xOffset, pos.getY() + 0.95, pos.getZ() + zOffset, velX,.15,velZ, data);
			break;
		case ITEM_SMOKE_COMBO:
			if(world.rand.nextBoolean())
				world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + xOffset, pos.getY() + 0.95, pos.getZ() + zOffset, velX,.15,velZ, data);
			else world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + xOffset, pos.getY() + 1.05, pos.getZ() + zOffset, velX/2,0,velZ/2);
			break;
		case NONE:
			break;
		case SMOKE:
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + xOffset, pos.getY() + 1.05, pos.getZ() + zOffset, velX/2,0,velZ/2);
			break;
		case SMOKE_PILLARS:
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + xOffset, pos.getY() + 1, pos.getZ() + zOffset, 0,.15,0);
			break;
		case SPRINKLER:
			int t = altar.getRitualCooldown();
			while(t > 17)
				t -= 17;
			double v1 = 0.0;
			double v2 = 0.0;
			if(t % 2 == 0) {
				v1 = 0;
				v2 = 0.5;
			}
			if(t % 4 == 0) {
				v1 = 0.5;
				v2 = 0.5;
			}
			if(t % 6 == 0) {
				v1 = 0.5;
				v2 = 0;
			}
			if(t % 8 == 0) {
				v1 = 0.5;
				v2 = -0.5;
			}
			if(t % 10 == 0) {
				v1 = 0;
				v2 = -0.5;
			}
			if(t % 12 == 0) {
				v1 = -0.5;
				v2 = -0.5;
			}
			if(t % 14 == 0) {
				v1 = -0.5;
				v2 = 0;
			}
			if(t % 16 == 0) {
				v1 = -0.5;
				v2 = 0.5;
			}
			double v12 = v1 > 0 ? -0.1 : v1 < 0 ? 0.1 : 0;
			double v22 = v2 > 0 ? -0.1 : v2 < 0 ? 0.1 : 0;
			world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + xOffset, pos.getY() + 1.05, pos.getZ() + zOffset, v1,.15,v2, data);
			world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + xOffset, pos.getY() + 1.05, pos.getZ() + zOffset, v1+v12,.15,v2+v22, data);
			world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + xOffset, pos.getY() + 1.05, pos.getZ() + zOffset, v1+v12*2,.15,v2+v22*2, data);
			break;
		case PE_STREAM:
			if(altar.getRitualCooldown() % 20 == 0) {
				Vec3d vec = new Vec3d(altarPos.subtract(pos)).normalize();

				double d = Math.sqrt(altarPos.distanceSq(pos));

				for(int i = 0; i < d * 15; i++){
					double i1 = i / 15D;
					double xp = pos.getX() + vec.x * i1 + .5;
					double yp = pos.getY() + vec.y * i1 + .95;
					double zp = pos.getZ() + vec.z * i1 + .5;
					AbyssalCraft.proxy.spawnParticle("PEStream", xp, yp, zp, vec.x * .1, .15, vec.z * .1);
				}
			}
			break;
		default:
			break;
		}
		world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + xOffset, pos.getY() + 1.05, pos.getZ() + zOffset, 0,0,0);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + xOffset, pos.getY() + 1.05, pos.getZ() + zOffset, 0,0,0);
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

	private ItemStack getStack(ItemStack stack){
		if(!stack.isEmpty() && stack.getItem().hasContainerItem(stack))
			return stack.getItem().getContainerItem(stack);
		else return ItemStack.EMPTY;
	}

	@Override
	public IRitualAltar getAltar() {

		return altarPos != null && world.getTileEntity(altarPos) instanceof IRitualAltar ? (IRitualAltar)world.getTileEntity(altarPos) : null;
	}

	@Override
	public void setAltar(BlockPos pos) {
		altarPos = pos;
		getAltar().addPedestal(this);
	}

	@Override
	public void consumeItem() {
		if(!item.isEmpty()) {
			itemID = Item.getIdFromItem(item.getItem());
			itemMeta = item.getMetadata();
			setItem(getStack(item));
		} else {
			itemID = itemMeta = 0;
			isDirty = true;
		}
	}
}
