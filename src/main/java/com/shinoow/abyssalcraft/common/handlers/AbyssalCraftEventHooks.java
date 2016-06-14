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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.event.ACEvents.RitualEvent;
import com.shinoow.abyssalcraft.api.item.ACItems;
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
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.world.TeleporterDarkRealm;

public class AbyssalCraftEventHooks {

	@SubscribeEvent
	public void populateChunk(PopulateChunkEvent.Pre event) {
		Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
			if (storage != null && storage.getYLocation() >= 60)
				for (int x = 0; x < 16; ++x)
					for (int y = 0; y < 16; ++y)
						for (int z = 0; z < 16; ++z)
							if(chunk.getBiome(new BlockPos(x, y, z), event.world.getWorldChunkManager()) == ACBiomes.darklands_mountains)
								if (storage.getBlockByExtId(x, y, z) == Blocks.stone)
									storage.set(x, y, z, ACBlocks.darkstone.getDefaultState());
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssalnite_ore))
			event.entityPlayer.addStat(AbyssalCraft.mineAby, 1);
		if(event.item.getEntityItem().getItem() == ACItems.coralium_gem ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_2 ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_3 ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_4 ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_5 ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_6 ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_7 ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_8 ||
				event.item.getEntityItem().getItem() == ACItems.coralium_gem_cluster_9 ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.coralium_ore))
			event.entityPlayer.addStat(AbyssalCraft.mineCorgem, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.liquified_coralium_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.pearlescent_coralium_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssal_coralium_ore))
			event.entityPlayer.addStat(AbyssalCraft.mineCor, 1);
		if(event.item.getEntityItem().getItem() == ACItems.shadow_gem)
			event.entityPlayer.addStat(AbyssalCraft.shadowGems, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssal_copper_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssal_iron_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssal_gold_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssal_nitre_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssal_diamond_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.abyssal_tin_ore))
			event.entityPlayer.addStat(AbyssalCraft.mineAbyOres, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.dreadlands_abyssalnite_ore) ||
				event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.dreaded_abyssalnite_ore))
			event.entityPlayer.addStat(AbyssalCraft.mineDread, 1);
		if(event.item.getEntityItem().getItem() == ACItems.dreadium_ingot)
			event.entityPlayer.addStat(AbyssalCraft.dreadium, 1);
		if(event.item.getEntityItem().getItem() == ACItems.ethaxium_ingot)
			event.entityPlayer.addStat(AbyssalCraft.eth, 1);
		if(event.item.getEntityItem().getItem() == ACItems.necronomicon)
			event.entityPlayer.addStat(AbyssalCraft.necro, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.depths_ghoul_head))
			event.entityPlayer.addStat(AbyssalCraft.ghoulhead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.pete_head))
			event.entityPlayer.addStat(AbyssalCraft.petehead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.mr_wilson_head))
			event.entityPlayer.addStat(AbyssalCraft.wilsonhead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.dr_orange_head))
			event.entityPlayer.addStat(AbyssalCraft.orangehead, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.dreadlands_infused_powerstone))
			event.entityPlayer.addStat(AbyssalCraft.findPSDL, 1);
		if(event.item.getEntityItem().getItem() == ACItems.gateway_key)
			event.entityPlayer.addStat(AbyssalCraft.GK1, 1);
		if(event.item.getEntityItem().getItem() == ACItems.dreaded_gateway_key)
			event.entityPlayer.addStat(AbyssalCraft.GK2, 1);
		if(event.item.getEntityItem().getItem() == ACItems.rlyehian_gateway_key)
			event.entityPlayer.addStat(AbyssalCraft.GK3, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.transmutator_idle))
			event.entityPlayer.addStat(AbyssalCraft.makeTransmutator, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.crystallizer_idle))
			event.entityPlayer.addStat(AbyssalCraft.makeCrystallizer, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.materializer))
			event.entityPlayer.addStat(AbyssalCraft.makeMaterializer, 1);
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(ACBlocks.engraver))
			event.entityPlayer.addStat(AbyssalCraft.makeEngraver, 1);
	}

	@SubscribeEvent
	public void darkRealm(LivingUpdateEvent event){
		if(event.entityLiving instanceof EntityPlayerMP){
			WorldServer worldServer = (WorldServer)event.entityLiving.worldObj;
			EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;
			if(player.dimension == ACLib.omothol_id && player.posY <= 0){
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 80, 255));
				player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 20));
				player.mcServer.getConfigurationManager().transferPlayerToDimension(player, ACLib.dark_realm_id, new TeleporterDarkRealm(worldServer));
				player.addStat(AbyssalCraft.enterDarkRealm, 1);
			}
		}
		if(event.entityLiving.dimension == ACLib.dark_realm_id){
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
				if(player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)) instanceof IDarklandsBiome)
					if(rand.nextInt(1000) == 0)
						if(helmet == null || helmet != null && helmet.getItem() != ACItems.abyssalnite_helmet && helmet.getItem() != ACItems.dreaded_abyssalnite_helmet
						&& helmet.getItem() != ACItems.refined_coralium_helmet && helmet.getItem() != ACItems.plated_coralium_helmet
						&& helmet.getItem() != ACItems.depths_helmet && helmet.getItem() != ACItems.dreadium_helmet
						&& helmet.getItem() != ACItems.dreadium_samurai_helmet && helmet.getItem() != ACItems.ethaxium_helmet)
							if(!player.capabilities.isCreativeMode)
								player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100));
				if(player.getActivePotionEffect(Potion.blindness) != null && player.getActivePotionEffect(Potion.blindness).getDuration() == 0)
					player.removePotionEffect(Potion.blindness.id);
			}
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
							}
						}
					}

		if(event.crafting.getItem() == ACItems.gateway_key)
			event.player.addStat(AbyssalCraft.GK1, 1);
		if(event.crafting.getItem() == ACItems.shadow_gem)
			event.player.addStat(AbyssalCraft.shadowGems, 1);
		if(event.crafting.getItem() == ACItems.ethaxium_ingot)
			event.player.addStat(AbyssalCraft.eth, 1);
		if(event.crafting.getItem() == ACItems.necronomicon)
			event.player.addStat(AbyssalCraft.necro, 1);
		if(event.crafting.getItem() == ACItems.abyssal_wasteland_necronomicon)
			event.player.addStat(AbyssalCraft.necrou1, 1);
		if(event.crafting.getItem() == ACItems.dreadlands_necronomicon)
			event.player.addStat(AbyssalCraft.necrou2, 1);
		if(event.crafting.getItem() == ACItems.omothol_necronomicon)
			event.player.addStat(AbyssalCraft.necrou3, 1);
		if(event.crafting.getItem() == ACItems.abyssalnomicon)
			event.player.addStat(AbyssalCraft.abyssaln, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.transmutator_idle))
			event.player.addStat(AbyssalCraft.makeTransmutator, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.crystallizer_idle))
			event.player.addStat(AbyssalCraft.makeCrystallizer, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.materializer))
			event.player.addStat(AbyssalCraft.makeMaterializer, 1);
		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.engraver))
			event.player.addStat(AbyssalCraft.makeEngraver, 1);
		if(event.crafting.getItem() == ACItems.small_crystal_bag ||
				event.crafting.getItem() == ACItems.medium_crystal_bag ||
				event.crafting.getItem() == ACItems.large_crystal_bag ||
				event.crafting.getItem() == ACItems.huge_crystal_bag)
			event.player.addStat(AbyssalCraft.makeCrystalBag, 1);
	}

	@SubscribeEvent
	public void noTPinOmothol(EnderTeleportEvent event){
		if(!(event.entityLiving instanceof EntityJzahar))
			if(event.entityLiving.dimension == ACLib.omothol_id){
				event.entityLiving.attackEntityFrom(DamageSource.fall, event.attackDamage);
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 1));
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 1));
				event.setCanceled(true);
			}
	}

	@SubscribeEvent
	public void darklandsVillages(BiomeEvent.GetVillageBlockID event){
		if(event.biome instanceof IDarklandsBiome){
			if(event.original.getBlock() == Blocks.log || event.original.getBlock() == Blocks.log2){
				event.replacement = ACBlocks.darklands_oak_wood.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.cobblestone){
				event.replacement = ACBlocks.darkstone_cobblestone.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.planks){
				event.replacement = ACBlocks.darklands_oak_planks.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.oak_stairs){
				event.replacement = ACBlocks.darklands_oak_stairs.getDefaultState().withProperty(BlockStairs.FACING, event.original.getValue(BlockStairs.FACING));
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.stone_stairs){
				event.replacement = ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, event.original.getValue(BlockStairs.FACING));;
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.oak_fence){
				event.replacement = ACBlocks.darklands_oak_fence.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.stone_slab){
				event.replacement = ACBlocks.darkstone_slab.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.double_stone_slab){
				event.replacement = AbyssalCraft.Darkstoneslab2.getDefaultState();
				event.setResult(Result.DENY);
			}
			if(event.original.getBlock() == Blocks.wooden_pressure_plate){
				event.replacement = ACBlocks.darklands_oak_pressure_plate.getDefaultState();
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void onRitualPerformed(RitualEvent.Post event){
		if(event.ritual instanceof NecronomiconSummonRitual){
			event.entityPlayer.addStat(AbyssalCraft.ritualSummon, 1);
			if(event.ritual.getUnlocalizedName().substring(10).equals("summonSacthoth"))
				if(!event.world.isRemote)
					SpecialTextUtil.SacthothGroup(event.world, StatCollector.translateToLocal("message.sacthoth.spawn.1"));
			if(event.ritual.getUnlocalizedName().substring(10).equals("summonAsorah")){
				if(!event.world.isRemote)
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
