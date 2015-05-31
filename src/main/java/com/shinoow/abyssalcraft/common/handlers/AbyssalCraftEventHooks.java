/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.handlers;

import java.util.Random;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.*;
import com.shinoow.abyssalcraft.api.item.ItemUpgradeKit;
import com.shinoow.abyssalcraft.common.blocks.BlockDLTSapling;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadSapling;
import com.shinoow.abyssalcraft.common.items.ItemCrystalBag;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.world.TeleporterOmothol;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class AbyssalCraftEventHooks {

	//Bonemeal events
	@SubscribeEvent
	public void bonemealUsed(BonemealEvent event) {
		if(event.getResult() == Result.DEFAULT){
			if (event.block == AbyssalCraft.DLTSapling) {
				if (!event.world.isRemote)
					if(event.world.rand.nextFloat() < 0.45D)
						((BlockDLTSapling)AbyssalCraft.DLTSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
				event.setResult(Result.ALLOW);
			}
			if (event.block == AbyssalCraft.dreadsapling) {
				if (!event.world.isRemote)
					if(event.world.rand.nextFloat() < 0.45D)
						((BlockDreadSapling)AbyssalCraft.dreadsapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
				event.setResult(Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void populateChunk(PopulateChunkEvent.Pre event) {
		Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
			if (storage != null && storage.getYLocation() >= 60)
				for (int x = 0; x < 16; ++x)
					for (int y = 0; y < 16; ++y)
						for (int z = 0; z < 16; ++z)
							if(chunk.getBiomeGenForWorldCoords(x, z, event.world.getWorldChunkManager()) == AbyssalCraft.DarklandsMountains)
								if (storage.getBlockByExtId(x, y, z) == Blocks.stone)
									storage.func_150818_a(x, y, z, AbyssalCraft.Darkstone);
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Darkstone_cobble))
			event.entityPlayer.addStat(AbyssalCraft.mineDS, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.abyore))
			event.entityPlayer.addStat(AbyssalCraft.mineAby, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.Coralium)
			event.entityPlayer.addStat(AbyssalCraft.mineCorgem, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.Cchunk)
			event.entityPlayer.addStat(AbyssalCraft.mineCor, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.DGhead))
			event.entityPlayer.addStat(AbyssalCraft.ghoulhead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Phead))
			event.entityPlayer.addStat(AbyssalCraft.petehead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Whead))
			event.entityPlayer.addStat(AbyssalCraft.wilsonhead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Ohead))
			event.entityPlayer.addStat(AbyssalCraft.orangehead, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacer)
			event.entityPlayer.addStat(AbyssalCraft.GK1, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacerDL)
			event.entityPlayer.addStat(AbyssalCraft.GK2, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacerJzh)
			event.entityPlayer.addStat(AbyssalCraft.GK3, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.PSDL))
			event.entityPlayer.addStat(AbyssalCraft.findPSDL, 1);
	}

	@SubscribeEvent
	public void enchantmentEffects(LivingAttackEvent event){
		if(event.source instanceof EntityDamageSource){
			Entity entity = ((EntityDamageSource)event.source).getEntity();
			if(entity instanceof EntityLivingBase){
				ItemStack item = ((EntityLivingBase)entity).getHeldItem();
				if(item != null && item.hasTagCompound()){
					NBTTagList enchTag = item.getEnchantmentTagList();
					for(int i = 0; i < enchTag.tagCount(); i++)
						if(enchTag.getCompoundTagAt(i).getInteger("id") == AbyssalCraft.coraliumE.effectId)
							if(EntityUtil.isEntityCoralium(event.entityLiving)){}
							else event.entityLiving.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 100));
						else if(enchTag.getCompoundTagAt(i).getInteger("id") == AbyssalCraft.dreadE.effectId)
							if(event.entityLiving instanceof IDreadEntity){}
							else event.entityLiving.addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
				}
			}
		}
	}

	@SubscribeEvent
	public void ironWall(LivingHurtEvent event){
		ItemStack item = event.entityLiving.getEquipmentInSlot(3);
		if(item != null && item.hasTagCompound()){
			NBTTagList enchTag = item.getEnchantmentTagList();
			for(int i = 0; i < enchTag.tagCount(); i++)
				if(enchTag.getCompoundTagAt(i).getInteger("id") == AbyssalCraft.ironWall.effectId)
					event.entityLiving.setInWeb();
		}
	}

	@SubscribeEvent
	public void darkRealm(LivingUpdateEvent event){
		if(event.entityLiving instanceof EntityPlayerMP){
			WorldServer worldServer = (WorldServer)event.entityLiving.worldObj;
			EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;
			if(player.dimension == AbyssalCraft.configDimId3 && player.posY <= 0){
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 60, 255));
				player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 20));
				player.mcServer.getConfigurationManager().transferPlayerToDimension(player, AbyssalCraft.configDimId4, new TeleporterOmothol(worldServer));
				player.addStat(AbyssalCraft.enterDarkRealm, 1);
			}
		}
		if(event.entityLiving.worldObj.isRemote && event.entityLiving.dimension == AbyssalCraft.configDimId4){
			Random rand = new Random();
			if(AbyssalCraft.particleEntity)
				event.entityLiving.worldObj.spawnParticle("largesmoke", event.entityLiving.posX + (rand.nextDouble() - 0.5D) * event.entityLiving.width,
						event.entityLiving.posY + rand.nextDouble() * event.entityLiving.height,
						event.entityLiving.posZ + (rand.nextDouble() - 0.5D) * event.entityLiving.width, 0,0,0);
		}
		if(AbyssalCraft.darkness)
			if(event.entityLiving instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer)event.entityLiving;
				Random rand = new Random();
				ItemStack helmet = player.getEquipmentInSlot(4);
				if(player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.Darklands ||
						player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.DarklandsPlains ||
						player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.DarklandsMountains ||
						player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.DarklandsHills ||
						player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.DarklandsForest)
					if(rand.nextInt(1000) == 0)
						if(helmet == null || helmet != null && helmet.getItem() != AbyssalCraft.helmet || helmet != null && helmet.getItem() != AbyssalCraft.helmetC
						|| helmet != null && helmet.getItem() != AbyssalCraft.helmetD || helmet != null && helmet.getItem() != AbyssalCraft.Corhelmet
						|| helmet != null && helmet.getItem() != AbyssalCraft.CorhelmetP || helmet != null && helmet.getItem() != AbyssalCraft.Depthshelmet
						|| helmet != null && helmet.getItem() != AbyssalCraft.dreadiumhelmet  || helmet != null && helmet.getItem() != AbyssalCraft.dreadiumShelmet
						|| helmet != null && helmet.getItem() != AbyssalCraft.ethHelmet)
							if(!player.capabilities.isCreativeMode)
								player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100));
				if(player.getActivePotionEffect(Potion.blindness) != null && player.getActivePotionEffect(Potion.blindness).getDuration() == 0)
					player.removePotionEffect(Potion.blindness.id);
			}
	}

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event) {
		if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)
			if (event.world.getBlock(event.x, event.y + 1, event.z) == AbyssalCraft.Coraliumfire ||
			event.world.getBlock(event.x, event.y + 1, event.z) == AbyssalCraft.dreadfire ||
			event.world.getBlock(event.x, event.y + 1, event.z) == AbyssalCraft.omotholfire)
				event.world.setBlockToAir(event.x, event.y + 1, event.z);
	}

	@SubscribeEvent
	public void onCraftingEvent(PlayerEvent.ItemCraftedEvent event)
	{
		for(int h=0; h < event.craftMatrix.getSizeInventory(); h++)
			if(event.craftMatrix.getStackInSlot(h) != null)
				for(int i=0; i < event.craftMatrix.getSizeInventory(); i++)
					if(event.craftMatrix.getStackInSlot(i) != null)
					{
						ItemStack k = event.craftMatrix.getStackInSlot(h);
						ItemStack j = event.craftMatrix.getStackInSlot(i);

						if(k.getItem() != null && j.getItem() != null && k.getItem() instanceof ItemUpgradeKit)
						{
							NBTTagCompound nbttest = new NBTTagCompound();
							NBTTagList tag = new NBTTagList();

							if(j.isItemEnchanted())
							{
								NBTTagList test = j.stackTagCompound.getTagList("ench", 10);
								tag = test;
							}
							ItemStack l = event.crafting;
							if(j.isItemEnchanted())
							{
								l.stackTagCompound = nbttest;
								l.stackTagCompound.setTag("ench", tag);
							}
							event.craftMatrix.setInventorySlotContents(i, l);
						}
						else if(k.getItem() != null && k.getItem() instanceof ItemCrystalBag){
							NBTTagCompound compound = new NBTTagCompound();
							NBTTagList items = new NBTTagList();

							items = k.stackTagCompound.getTagList("ItemInventory", 10);
							ItemStack l = event.crafting;
							((ItemCrystalBag)l.getItem()).setInventorySize(l);
							if(l.stackTagCompound == null)
								l.stackTagCompound = compound;
							l.stackTagCompound.setTag("ItemInventory", items);

							event.craftMatrix.setInventorySlotContents(i, l);
						}
					}
	}
}
