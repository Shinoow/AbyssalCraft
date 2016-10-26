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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporterItem;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.event.ACEvents.RitualEvent;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

public class TileEntityRitualAltar extends TileEntity implements ITickable, IRitualAltar {

	private int ritualTimer;
	private ItemStack[] offers = new ItemStack[8];
	private NecronomiconRitual ritual;
	private ItemStack item;
	private int rot;
	private EntityPlayer user;
	private float consumedEnergy;
	private boolean isDirty;


	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = ItemStack.loadItemStackFromNBT(nbtItem);
		rot = nbttagcompound.getInteger("Rot");
		ritualTimer = nbttagcompound.getInteger("Cooldown");
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
		nbttagcompound.setInteger("Cooldown", ritualTimer);
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
		if(isDirty || isPerformingRitual()){
			worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
			isDirty = false;
		}

		if(isPerformingRitual()){
			if(ritualTimer == 1){
				SoundEvent chant = getRandomChant();
				worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), chant, SoundCategory.PLAYERS, 1, 1, true);
				worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), chant, SoundCategory.PLAYERS, 1, 1, true);
				worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), chant, SoundCategory.PLAYERS, 1, 1, true);
			}
			ritualTimer++;

			if(ritual != null){
				if(user != null){
					for(ItemStack item : user.inventory.mainInventory)
						if(item != null && item.getItem() instanceof IEnergyTransporterItem &&
						((IEnergyTransporterItem) item.getItem()).canTransferPEExternally(item)){
							consumedEnergy += ((IEnergyTransporterItem) item.getItem()).consumeEnergy(item, ritual.getReqEnergy()/200);
							break;
						}
				} else user = worldObj.func_184137_a(pos.getX(), pos.getY(), pos.getZ(), 5, true);
				if(ritualTimer == 200)
					if(user != null){
						if(!MinecraftForge.EVENT_BUS.post(new RitualEvent.Post(user, ritual, worldObj, pos))){
							for(ItemStack item : user.inventory.mainInventory)
								if(item != null && item.getItem() instanceof IEnergyTransporterItem &&
								((IEnergyTransporterItem) item.getItem()).canTransferPEExternally(item)){
									consumedEnergy += ((IEnergyTransporterItem) item.getItem()).consumeEnergy(item, ritual.getReqEnergy()/200);
									break;
								}
							if(consumedEnergy == ritual.getReqEnergy())
								ritual.completeRitual(worldObj, pos, user);
							else if(!worldObj.isRemote){
								worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, pos.getX(), pos.getY() + 1, pos.getZ(), false));
								DisruptionHandler.instance().generateDisruption(DeityType.values()[worldObj.rand.nextInt(DeityType.values().length)], worldObj, pos, worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16, 16, 16)));
							}
							ritualTimer = 0;
							user = null;
							ritual = null;
							consumedEnergy = 0;
							isDirty = true;
						}
					} else {
						if(!worldObj.isRemote){
							worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, pos.getX(), pos.getY() + 1, pos.getZ(), false));
							DisruptionHandler.instance().generateDisruption(DeityType.values()[worldObj.rand.nextInt(DeityType.values().length)], worldObj, pos, worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16, 16, 16)));
						}
						ritualTimer = 0;
						ritual = null;
						consumedEnergy = 0;
						isDirty = true;
					}
			} else ritualTimer = 0;

			worldObj.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0,0,0);

			double x = 0, z = 0, o = 0;

			for(double i = 0; i <= 0.7; i += 0.03) {
				x = i * Math.cos(i) / 2;
				z = i * Math.sin(i) / 2;
				o = i * Math.tan(i) / 2;
			}

			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() - 2.5, pos.getY() + 0.95, pos.getZ() + 0.5, x,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.95, pos.getZ() - 2.5, 0,0,z);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 3.5, pos.getY() + 0.95, pos.getZ() + 0.5, -x,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.95, pos.getZ() + 3.5, 0,0,-z);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() - 1.5, pos.getY() + 0.95, pos.getZ() + 2.5, o,0,-o);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() - 1.5, pos.getY() + 0.95, pos.getZ() - 1.5, o,0,o);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 2.5, pos.getY() + 0.95, pos.getZ() + 2.5, -o,0,-o);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 2.5, pos.getY() + 0.95, pos.getZ() - 1.5, -o,0,o);

			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() - 2.5, pos.getY() + 1.05, pos.getZ() + 0.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() - 2.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 3.5, pos.getY() + 1.05, pos.getZ() + 0.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 3.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() - 1.5, pos.getY() + 1.05, pos.getZ() + 2.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() - 1.5, pos.getY() + 1.05, pos.getZ() - 1.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 2.5, pos.getY() + 1.05, pos.getZ() + 2.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 2.5, pos.getY() + 1.05, pos.getZ() - 1.5, 0,0,0);

			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() - 2.5, pos.getY() + 1.05, pos.getZ() + 0.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() - 2.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 3.5, pos.getY() + 1.05, pos.getZ() + 0.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 3.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() - 1.5, pos.getY() + 1.05, pos.getZ() + 2.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() - 1.5, pos.getY() + 1.05, pos.getZ() - 1.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 2.5, pos.getY() + 1.05, pos.getZ() + 2.5, 0,0,0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 2.5, pos.getY() + 1.05, pos.getZ() - 1.5, 0,0,0);
		}

		if(rot == 360)
			rot = 0;
		if(item != null)
			rot++;
	}

	@Override
	public boolean canPerform(){

		if(checkSurroundings(worldObj, pos)) return true;
		return false;
	}

	@Override
	public boolean checkSurroundings(World world, BlockPos pos){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		TileEntity ped1 = world.getTileEntity(new BlockPos(x - 3, y, z));
		TileEntity ped2 = world.getTileEntity(new BlockPos(x, y, z - 3));
		TileEntity ped3 = world.getTileEntity(new BlockPos(x + 3, y, z));
		TileEntity ped4 = world.getTileEntity(new BlockPos(x, y, z + 3));
		TileEntity ped5 = world.getTileEntity(new BlockPos(x - 2, y, z + 2));
		TileEntity ped6 = world.getTileEntity(new BlockPos(x - 2, y, z - 2));
		TileEntity ped7 = world.getTileEntity(new BlockPos(x + 2, y, z + 2));
		TileEntity ped8 = world.getTileEntity(new BlockPos(x + 2, y, z - 2));
		if(ped1 != null && ped2 != null && ped3 != null && ped4 != null && ped5 != null && ped6 != null && ped7 != null && ped8 != null)
			if(ped1 instanceof TileEntityRitualPedestal && ped2 instanceof TileEntityRitualPedestal && ped3 instanceof TileEntityRitualPedestal
					&& ped4 instanceof TileEntityRitualPedestal && ped5 instanceof TileEntityRitualPedestal && ped6 instanceof TileEntityRitualPedestal
					&& ped7 instanceof TileEntityRitualPedestal && ped8 instanceof TileEntityRitualPedestal){
				offers[0] = ((TileEntityRitualPedestal)ped1).getItem();
				offers[1] = ((TileEntityRitualPedestal)ped2).getItem();
				offers[2] = ((TileEntityRitualPedestal)ped3).getItem();
				offers[3] = ((TileEntityRitualPedestal)ped4).getItem();
				offers[4] = ((TileEntityRitualPedestal)ped5).getItem();
				offers[5] = ((TileEntityRitualPedestal)ped6).getItem();
				offers[6] = ((TileEntityRitualPedestal)ped7).getItem();
				offers[7] = ((TileEntityRitualPedestal)ped8).getItem();
				if(offers[0] == null && offers[1] == null && offers[2] == null && offers[3] == null && offers[4] == null &&
						offers[5] == null && offers[6] == null && offers[7] == null) return false;
				else return true;
			}
		return false;
	}

	@Override
	public void resetPedestals(World world, BlockPos pos){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		TileEntity ped1 = world.getTileEntity(new BlockPos(x-3, y, z));
		TileEntity ped2 = world.getTileEntity(new BlockPos(x, y, z - 3));
		TileEntity ped3 = world.getTileEntity(new BlockPos(x + 3, y, z));
		TileEntity ped4 = world.getTileEntity(new BlockPos(x, y, z + 3));
		TileEntity ped5 = world.getTileEntity(new BlockPos(x - 2, y, z + 2));
		TileEntity ped6 = world.getTileEntity(new BlockPos(x - 2, y, z - 2));
		TileEntity ped7 = world.getTileEntity(new BlockPos(x + 2, y, z + 2));
		TileEntity ped8 = world.getTileEntity(new BlockPos(x + 2, y, z - 2));
		if(ped1 != null && ped2 != null && ped3 != null && ped4 != null && ped5 != null && ped6 != null && ped7 != null && ped8 != null)
			if(ped1 instanceof TileEntityRitualPedestal && ped2 instanceof TileEntityRitualPedestal && ped3 instanceof TileEntityRitualPedestal
					&& ped4 instanceof TileEntityRitualPedestal && ped5 instanceof TileEntityRitualPedestal && ped6 instanceof TileEntityRitualPedestal
					&& ped7 instanceof TileEntityRitualPedestal && ped8 instanceof TileEntityRitualPedestal){
				((TileEntityRitualPedestal)ped1).setItem(null);
				((TileEntityRitualPedestal)ped2).setItem(null);
				((TileEntityRitualPedestal)ped3).setItem(null);
				((TileEntityRitualPedestal)ped4).setItem(null);
				((TileEntityRitualPedestal)ped5).setItem(null);
				((TileEntityRitualPedestal)ped6).setItem(null);
				((TileEntityRitualPedestal)ped7).setItem(null);
				((TileEntityRitualPedestal)ped8).setItem(null);
			}
	}

	@Override
	public void performRitual(World world, BlockPos pos, EntityPlayer player){

		if(!isPerformingRitual()){
			ItemStack item = player.getHeldItemMainhand();
			if(item.getItem() instanceof ItemNecronomicon)
				if(RitualRegistry.instance().canPerformAction(world.provider.getDimension(), ((ItemNecronomicon)item.getItem()).getBookType()))
					if(canPerform()){
						ritual = RitualRegistry.instance().getRitual(world.provider.getDimension(), ((ItemNecronomicon)item.getItem()).getBookType(), offers, this.item);
						if(ritual != null)
							if(ritual.requiresSacrifice()){
								if(!world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(4, 4, 4)).isEmpty())
									for(EntityLivingBase mob : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(4, 4, 4)))
										if(canBeSacrificed(mob))
											if(ritual.canCompleteRitual(world, pos, player))
												if(!MinecraftForge.EVENT_BUS.post(new RitualEvent.Pre(player, ritual, world, pos))){
													if(!world.isRemote){
														mob.attackEntityFrom(DamageSource.magic, 200000);
														world.addWeatherEffect(new EntityLightningBolt(worldObj, mob.posX, mob.posY, mob.posZ, false));
													}
													ritualTimer = 1;
													resetPedestals(world, pos);
													user = player;
													consumedEnergy = 0;
													isDirty = true;
												}
							} else if(ritual.canCompleteRitual(world, pos, player))
								if(!MinecraftForge.EVENT_BUS.post(new RitualEvent.Pre(player, ritual, world, pos))){
									ritualTimer = 1;
									resetPedestals(world, pos);
									user = player;
									consumedEnergy = 0;
									isDirty = true;
								}
					}
		}
	}

	/**
	 * Checks if a certain Entity can be sacrificed
	 * @param entity Entity to potentially sacrifice
	 * @return True if the Entity can be sacrificed, otherwise false
	 */
	private boolean canBeSacrificed(EntityLivingBase entity){
		return !(entity instanceof EntityPlayer) && (EntityUtil.isShoggothFood(entity) || entity instanceof EntityVillager) &&
				entity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD &&
				entity.isEntityAlive() && !entity.isChild();
	}

	public SoundEvent getRandomChant(){
		SoundEvent[] chants = {AbyssalCraft.cthulhu_chant, AbyssalCraft.yog_sothoth_chant_1, AbyssalCraft.yog_sothoth_chant_2,
				AbyssalCraft.hastur_chant_1, AbyssalCraft.hastur_chant_2, AbyssalCraft.sleeping_chant, AbyssalCraft.cthugha_chant};
		return chants[worldObj.rand.nextInt(chants.length)];
	}

	@Override
	public int getRitualCooldown(){
		return ritualTimer;
	}

	@Override
	public boolean isPerformingRitual(){
		return ritualTimer < 200 && ritualTimer > 0;
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
}
