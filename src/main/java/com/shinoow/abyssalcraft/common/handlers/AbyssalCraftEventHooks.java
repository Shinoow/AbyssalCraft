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
package com.shinoow.abyssalcraft.common.handlers;

import java.util.Random;

import net.minecraft.block.BlockStairs;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.event.ACEvents.RitualEvent;
import com.shinoow.abyssalcraft.api.item.ItemUpgradeKit;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconPotionAoERitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconPotionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconSummonRitual;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.items.ItemCrystalBag;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconBreedingRitual;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconDreadSpawnRitual;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.common.world.TeleporterDarkRealm;

public class AbyssalCraftEventHooks {

	@SubscribeEvent
	public void populateChunk(PopulateChunkEvent.Pre event) {
		Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
			if (storage != null && storage.getYLocation() >= 60)
				for (int x = 0; x < 16; ++x)
					for (int y = 0; y < 16; ++y)
						for (int z = 0; z < 16; ++z)
							if(chunk.getBiome(new BlockPos(x, y, z), event.world.getWorldChunkManager()) == AbyssalCraft.DarklandsMountains)
								if (storage.getBlockByExtId(x, y, z) == Blocks.stone)
									storage.set(x, y, z, AbyssalCraft.Darkstone.getDefaultState());
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.abyore))
			event.entityPlayer.addStat(AbyssalCraft.mineAby, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.Coralium ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster2 ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster3 ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster4 ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster5 ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster6 ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster7 ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster8 ||
				event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster9 ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Coraliumore))
			event.entityPlayer.addStat(AbyssalCraft.mineCorgem, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyLCorOre) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyPCorOre) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyCorOre))
			event.entityPlayer.addStat(AbyssalCraft.mineCor, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.shadowgem)
			event.entityPlayer.addStat(AbyssalCraft.shadowGems, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyCopOre) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyIroOre) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyGolOre) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyNitOre) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyDiaOre) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyTinOre))
			event.entityPlayer.addStat(AbyssalCraft.mineAbyOres, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.abydreadore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.dreadore))
			event.entityPlayer.addStat(AbyssalCraft.mineDread, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.dreadiumingot)
			event.entityPlayer.addStat(AbyssalCraft.dreadium, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.ethaxiumIngot)
			event.entityPlayer.addStat(AbyssalCraft.eth, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.necronomicon)
			event.entityPlayer.addStat(AbyssalCraft.necro, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.DGhead))
			event.entityPlayer.addStat(AbyssalCraft.ghoulhead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Phead))
			event.entityPlayer.addStat(AbyssalCraft.petehead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Whead))
			event.entityPlayer.addStat(AbyssalCraft.wilsonhead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Ohead))
			event.entityPlayer.addStat(AbyssalCraft.orangehead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.PSDL))
			event.entityPlayer.addStat(AbyssalCraft.findPSDL, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.gatewayKey)
			event.entityPlayer.addStat(AbyssalCraft.GK1, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.gatewayKeyDL)
			event.entityPlayer.addStat(AbyssalCraft.GK2, 1);
		if(event.item.getEntityItem().getItem() == AbyssalCraft.gatewayKeyJzh)
			event.entityPlayer.addStat(AbyssalCraft.GK3, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.transmutator))
			event.entityPlayer.addStat(AbyssalCraft.makeTransmutator, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.crystallizer))
			event.entityPlayer.addStat(AbyssalCraft.makeCrystallizer, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.materializer))
			event.entityPlayer.addStat(AbyssalCraft.makeMaterializer, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.engraver))
			event.entityPlayer.addStat(AbyssalCraft.makeEngraver, 1);
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
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 80, 255));
				player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 20));
				player.mcServer.getConfigurationManager().transferPlayerToDimension(player, AbyssalCraft.configDimId4, new TeleporterDarkRealm(worldServer));
				player.addStat(AbyssalCraft.enterDarkRealm, 1);
			}
		}
		if(event.entityLiving.worldObj.isRemote && event.entityLiving.dimension == AbyssalCraft.configDimId4){
			Random rand = new Random();
			if(AbyssalCraft.particleEntity)
				event.entityLiving.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, event.entityLiving.posX + (rand.nextDouble() - 0.5D) * event.entityLiving.width,
						event.entityLiving.posY + rand.nextDouble() * event.entityLiving.height,
						event.entityLiving.posZ + (rand.nextDouble() - 0.5D) * event.entityLiving.width, 0,0,0);
		}
		if(AbyssalCraft.darkness)
			if(event.entityLiving instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer)event.entityLiving;
				Random rand = new Random();
				ItemStack helmet = player.getEquipmentInSlot(4);
				if(player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)) == AbyssalCraft.Darklands ||
						player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)) == AbyssalCraft.DarklandsPlains ||
						player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)) == AbyssalCraft.DarklandsMountains ||
						player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)) == AbyssalCraft.DarklandsHills ||
						player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)) == AbyssalCraft.DarklandsForest)
					if(rand.nextInt(1000) == 0)
						if(helmet == null || helmet != null && helmet.getItem() != AbyssalCraft.helmet && helmet.getItem() != AbyssalCraft.helmetD
						&& helmet.getItem() != AbyssalCraft.Corhelmet && helmet.getItem() != AbyssalCraft.CorhelmetP
						&& helmet.getItem() != AbyssalCraft.Depthshelmet && helmet.getItem() != AbyssalCraft.dreadiumhelmet
						&& helmet.getItem() != AbyssalCraft.dreadiumShelmet && helmet.getItem() != AbyssalCraft.ethHelmet)
							if(!player.capabilities.isCreativeMode)
								player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100));
				if(player.getActivePotionEffect(Potion.blindness) != null && player.getActivePotionEffect(Potion.blindness).getDuration() == 0)
					player.removePotionEffect(Potion.blindness.id);
			}
	}

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event) {
		if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)
			if (event.world.getBlockState(event.pos) == AbyssalCraft.Coraliumfire ||
			event.world.getBlockState(event.pos) == AbyssalCraft.dreadfire ||
			event.world.getBlockState(event.pos) == AbyssalCraft.omotholfire)
				event.world.setBlockToAir(event.pos);
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
							NBTTagCompound compound = new NBTTagCompound();
							NBTTagList tag = new NBTTagList();

							ItemStack l = event.crafting;

							if(j.isItemEnchanted())
							{
								tag = j.getTagCompound().getTagList("ench", 10);
								if(l.getTagCompound() == null)
									l.setTagCompound(compound);
								l.getTagCompound().setTag("ench", tag);

								event.craftMatrix.setInventorySlotContents(i, l);
							}
						}
						else if(k.getItem() != null && k.getItem() instanceof ItemCrystalBag){
							NBTTagCompound compound = new NBTTagCompound();
							NBTTagList items = new NBTTagList();

							if(k.getTagCompound() == null)
								k.setTagCompound(compound);
							items = k.getTagCompound().getTagList("ItemInventory", 10);

							ItemStack l = event.crafting;

							if(l.getItem() instanceof ItemCrystalBag){
								((ItemCrystalBag)l.getItem()).setInventorySize(l);
								if(l.getTagCompound() == null)
									l.setTagCompound(compound);
								l.getTagCompound().setTag("ItemInventory", items);

								event.craftMatrix.setInventorySlotContents(i, l);
							}
						}
						else if(k.getItem() != null && k.getItem() instanceof ItemNecronomicon){
							NBTTagCompound compound = new NBTTagCompound();
							String owner = "";
							float energy = 0;

							if(k.getTagCompound() == null)
								k.setTagCompound(compound);
							owner = k.getTagCompound().getString("owner");
							energy = k.getTagCompound().getFloat("PotEnergy");

							ItemStack l = event.crafting;

							if(l.getItem() instanceof ItemNecronomicon){
								if(l.getTagCompound() == null)
									l.setTagCompound(compound);
								if(!owner.equals(""))
									l.getTagCompound().setString("owner", owner);
								if(energy != 0)
									l.getTagCompound().setFloat("PotEnergy", energy);

								event.craftMatrix.setInventorySlotContents(i, l);
							}
						}
					}

		if(event.crafting.getItem() == AbyssalCraft.gatewayKey)
			event.player.addStat(AbyssalCraft.GK1, 1);
		if(event.crafting.getItem() == AbyssalCraft.shadowgem)
			event.player.addStat(AbyssalCraft.shadowGems, 1);
		if(event.crafting.getItem() == AbyssalCraft.ethaxiumIngot)
			event.player.addStat(AbyssalCraft.eth, 1);
		if(event.crafting.getItem() == AbyssalCraft.necronomicon)
			event.player.addStat(AbyssalCraft.necro, 1);
		if(event.crafting.getItem() == AbyssalCraft.necronomicon_cor)
			event.player.addStat(AbyssalCraft.necrou1, 1);
		if(event.crafting.getItem() == AbyssalCraft.necronomicon_dre)
			event.player.addStat(AbyssalCraft.necrou2, 1);
		if(event.crafting.getItem() == AbyssalCraft.necronomicon_omt)
			event.player.addStat(AbyssalCraft.necrou3, 1);
		if(event.crafting.getItem() == AbyssalCraft.abyssalnomicon)
			event.player.addStat(AbyssalCraft.abyssaln, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(AbyssalCraft.transmutator))
			event.player.addStat(AbyssalCraft.makeTransmutator, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(AbyssalCraft.crystallizer))
			event.player.addStat(AbyssalCraft.makeCrystallizer, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(AbyssalCraft.materializer))
			event.player.addStat(AbyssalCraft.makeMaterializer, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(AbyssalCraft.engraver))
			event.player.addStat(AbyssalCraft.makeEngraver, 1);
		if(event.crafting.getItem() == AbyssalCraft.crystalbag_s ||
				event.crafting.getItem() == AbyssalCraft.crystalbag_m ||
				event.crafting.getItem() == AbyssalCraft.crystalbag_l ||
				event.crafting.getItem() == AbyssalCraft.crystalbag_h)
			event.player.addStat(AbyssalCraft.makeCrystalBag, 1);
	}

	@SubscribeEvent
	public void noTPinOmothol(EnderTeleportEvent event){
		if(!(event.entityLiving instanceof EntityJzahar))
			if(event.entityLiving.dimension == AbyssalCraft.configDimId3){
				event.entityLiving.attackEntityFrom(DamageSource.fall, event.attackDamage);
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 1));
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 1));
				event.setCanceled(true);
			}
	}

	@SubscribeEvent
	public void darklandsVillages(BiomeEvent.GetVillageBlockID event){
		if(event.biome == AbyssalCraft.Darklands || event.biome == AbyssalCraft.DarklandsPlains ||
				event.biome == AbyssalCraft.DarklandsForest || event.biome == AbyssalCraft.DarklandsHills ||
				event.biome == AbyssalCraft.DarklandsMountains){
			if(event.original.getBlock() == Blocks.log || event.original.getBlock() == Blocks.log2){
				event.replacement = AbyssalCraft.DLTLog.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.cobblestone){
				event.replacement = AbyssalCraft.Darkstone_cobble.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.planks){
				event.replacement = AbyssalCraft.DLTplank.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.oak_stairs){
				event.replacement = AbyssalCraft.DLTstairs.getDefaultState().withProperty(BlockStairs.FACING, event.original.getValue(BlockStairs.FACING));
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.stone_stairs){
				event.replacement = AbyssalCraft.DCstairs.getDefaultState().withProperty(BlockStairs.FACING, event.original.getValue(BlockStairs.FACING));;
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.oak_fence){
				event.replacement = AbyssalCraft.DLTfence.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.stone_slab){
				event.replacement = AbyssalCraft.Darkstoneslab1.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.double_wooden_slab){
				event.replacement = AbyssalCraft.Darkstoneslab2.getDefaultState();
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void onRitualPerformed(RitualEvent.Post event){
		if(event.ritual instanceof NecronomiconSummonRitual){
			event.entityPlayer.addStat(AbyssalCraft.ritualSummon, 1);
			if(event.ritual.getUnlocalizedName().substring(10).equals("summonSacthoth"))
				if(event.world.isRemote)
					SpecialTextUtil.SacthothGroup(event.world, StatCollector.translateToLocal("message.sacthoth.spawn.1"));
			if(event.ritual.getUnlocalizedName().substring(10).equals("summonAsorah")){
				if(event.world.isRemote)
					SpecialTextUtil.AsorahGroup(event.world, StatCollector.translateToLocal("message.asorah.spawn"));
				event.entityPlayer.addStat(AbyssalCraft.summonAsorah, 1);
			}
		}
		if(event.ritual instanceof NecronomiconCreationRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualCreate, 1);
		if(event.ritual instanceof NecronomiconBreedingRitual
				|| event.ritual instanceof NecronomiconDreadSpawnRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualBreed, 1);
		if(event.ritual instanceof NecronomiconPotionRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualPotion, 1);
		if(event.ritual instanceof NecronomiconPotionAoERitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualPotionAoE, 1);
		if(event.ritual instanceof NecronomiconInfusionRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualInfusion, 1);
	}
}
