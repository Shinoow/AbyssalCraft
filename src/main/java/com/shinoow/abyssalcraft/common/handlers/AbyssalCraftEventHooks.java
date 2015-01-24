/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.handlers;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ItemUpgradeKit;
import com.shinoow.abyssalcraft.common.blocks.BlockDLTSapling;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadSapling;
import com.shinoow.abyssalcraft.common.world.TeleporterOmothol;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class AbyssalCraftEventHooks {

	//Bonemeal events
	@SubscribeEvent
	public void bonemealUsed(BonemealEvent event) {
		if (event.block == AbyssalCraft.DLTSapling) {
			if (!event.world.isRemote)
				((BlockDLTSapling)AbyssalCraft.DLTSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
			event.setResult(Result.ALLOW);
		}

		if (event.block == AbyssalCraft.dreadsapling) {
			if (!event.world.isRemote)
				((BlockDreadSapling)AbyssalCraft.dreadsapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
			event.setResult(Result.ALLOW);
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
							if(event.entityLiving instanceof ICoraliumEntity || event.entityLiving instanceof EntityPlayer &&
									((EntityPlayer)event.entityLiving).getCommandSenderName().equals("shinoow") || event.entityLiving instanceof EntityPlayer &&
									((EntityPlayer)event.entityLiving).getCommandSenderName().equals("Oblivionaire")){}
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
			event.entityLiving.worldObj.spawnParticle("largesmoke", event.entityLiving.posX + (rand.nextDouble() - 0.5D) * event.entityLiving.width,
					event.entityLiving.posY + rand.nextDouble() * event.entityLiving.height,
					event.entityLiving.posZ + (rand.nextDouble() - 0.5D) * event.entityLiving.width, 0,0,0);
		}
		if(AbyssalCraft.darkness)
			if(event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityPlayer){
				Random rand = new Random();
				ItemStack helmet = event.entityLiving.getEquipmentInSlot(4);
				if(event.entityLiving.worldObj.getBiomeGenForCoords((int)event.entityLiving.posX, (int)event.entityLiving.posZ) == AbyssalCraft.Darklands ||
						event.entityLiving.worldObj.getBiomeGenForCoords((int)event.entityLiving.posX, (int)event.entityLiving.posZ) == AbyssalCraft.DarklandsPlains ||
						event.entityLiving.worldObj.getBiomeGenForCoords((int)event.entityLiving.posX, (int)event.entityLiving.posZ) == AbyssalCraft.DarklandsMountains ||
						event.entityLiving.worldObj.getBiomeGenForCoords((int)event.entityLiving.posX, (int)event.entityLiving.posZ) == AbyssalCraft.DarklandsHills ||
						event.entityLiving.worldObj.getBiomeGenForCoords((int)event.entityLiving.posX, (int)event.entityLiving.posZ) == AbyssalCraft.DarklandsForest)
					if(rand.nextInt(1000) == 0)
						if(helmet != null && helmet.getItem() == AbyssalCraft.helmet){}
						else event.entityLiving.addPotionEffect(new PotionEffect(Potion.blindness.id, 100));
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
					}
	}
}