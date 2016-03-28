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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
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
		Chunk chunk = event.getWorld().getChunkFromChunkCoords(event.getChunkX(), event.getChunkZ());
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
			if (storage != null && storage.getYLocation() >= 60)
				for (int x = 0; x < 16; ++x)
					for (int y = 0; y < 16; ++y)
						for (int z = 0; z < 16; ++z)
							if(chunk.getBiome(new BlockPos(x, y, z), event.getWorld().getBiomeProvider()) == AbyssalCraft.DarklandsMountains)
								if (storage.get(x, y, z).getBlock() == Blocks.stone)
									storage.set(x, y, z, AbyssalCraft.Darkstone.getDefaultState());
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.abyore))
			event.getEntityPlayer().addStat(AbyssalCraft.mineAby, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.Coralium ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster2 ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster3 ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster4 ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster5 ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster6 ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster7 ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster8 ||
				event.getItem().getEntityItem().getItem() == AbyssalCraft.Coraliumcluster9 ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Coraliumore))
			event.getEntityPlayer().addStat(AbyssalCraft.mineCorgem, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyLCorOre) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyPCorOre) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyCorOre))
			event.getEntityPlayer().addStat(AbyssalCraft.mineCor, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.shadowgem)
			event.getEntityPlayer().addStat(AbyssalCraft.shadowGems, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyCopOre) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyIroOre) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyGolOre) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyNitOre) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyDiaOre) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyTinOre))
			event.getEntityPlayer().addStat(AbyssalCraft.mineAbyOres, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.abydreadore) ||
				event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.dreadore))
			event.getEntityPlayer().addStat(AbyssalCraft.mineDread, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.dreadiumingot)
			event.getEntityPlayer().addStat(AbyssalCraft.dreadium, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.ethaxiumIngot)
			event.getEntityPlayer().addStat(AbyssalCraft.eth, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.necronomicon)
			event.getEntityPlayer().addStat(AbyssalCraft.necro, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.DGhead))
			event.getEntityPlayer().addStat(AbyssalCraft.ghoulhead, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Phead))
			event.getEntityPlayer().addStat(AbyssalCraft.petehead, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Whead))
			event.getEntityPlayer().addStat(AbyssalCraft.wilsonhead, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Ohead))
			event.getEntityPlayer().addStat(AbyssalCraft.orangehead, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.PSDL))
			event.getEntityPlayer().addStat(AbyssalCraft.findPSDL, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.gatewayKey)
			event.getEntityPlayer().addStat(AbyssalCraft.GK1, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.gatewayKeyDL)
			event.getEntityPlayer().addStat(AbyssalCraft.GK2, 1);
		if(event.getItem().getEntityItem().getItem() == AbyssalCraft.gatewayKeyJzh)
			event.getEntityPlayer().addStat(AbyssalCraft.GK3, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.transmutator))
			event.getEntityPlayer().addStat(AbyssalCraft.makeTransmutator, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.crystallizer))
			event.getEntityPlayer().addStat(AbyssalCraft.makeCrystallizer, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.materializer))
			event.getEntityPlayer().addStat(AbyssalCraft.makeMaterializer, 1);
		if(event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.engraver))
			event.getEntityPlayer().addStat(AbyssalCraft.makeEngraver, 1);
	}

	@SubscribeEvent
	public void enchantmentEffects(LivingAttackEvent event){
		if(event.getSource() instanceof EntityDamageSource){
			Entity entity = ((EntityDamageSource)event.getSource()).getEntity();
			if(entity instanceof EntityLivingBase){
				ItemStack item = ((EntityLivingBase)entity).getHeldItemMainhand();
				if(item != null && item.hasTagCompound()){
					NBTTagList enchTag = item.getEnchantmentTagList();
					for(int i = 0; i < enchTag.tagCount(); i++)
						if(enchTag.getCompoundTagAt(i).getInteger("id") == Enchantment.getEnchantmentID(AbyssalCraft.coraliumE))
							if(EntityUtil.isEntityCoralium(event.getEntityLiving())){}
							else event.getEntityLiving().addPotionEffect(new PotionEffect(AbyssalCraft.Cplague, 100));
						else if(enchTag.getCompoundTagAt(i).getInteger("id") == Enchantment.getEnchantmentID(AbyssalCraft.dreadE))
							if(event.getEntityLiving() instanceof IDreadEntity){}
							else event.getEntityLiving().addPotionEffect(new PotionEffect(AbyssalCraft.Dplague, 100));
				}
			}
		}
	}

	@SubscribeEvent
	public void ironWall(LivingHurtEvent event){
		ItemStack item = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		if(item != null && item.hasTagCompound()){
			NBTTagList enchTag = item.getEnchantmentTagList();
			for(int i = 0; i < enchTag.tagCount(); i++)
				if(enchTag.getCompoundTagAt(i).getInteger("id") == Enchantment.getEnchantmentID(AbyssalCraft.ironWall))
					event.getEntityLiving().setInWeb();
		}
	}

	@SubscribeEvent
	public void darkRealm(LivingUpdateEvent event){
		if(event.getEntityLiving() instanceof EntityPlayerMP){
			WorldServer worldServer = (WorldServer)event.getEntityLiving().worldObj;
			EntityPlayerMP player = (EntityPlayerMP)event.getEntityLiving();
			if(player.dimension == AbyssalCraft.configDimId3 && player.posY <= 0){
				player.addPotionEffect(new PotionEffect(MobEffects.resistance, 80, 255));
				player.addPotionEffect(new PotionEffect(MobEffects.blindness, 20));
				player.mcServer.getPlayerList().transferPlayerToDimension(player, AbyssalCraft.configDimId4, new TeleporterDarkRealm(worldServer));
				player.addStat(AbyssalCraft.enterDarkRealm, 1);
			}
		}
		if(event.getEntityLiving().worldObj.isRemote && event.getEntityLiving().dimension == AbyssalCraft.configDimId4){
			Random rand = new Random();
			if(AbyssalCraft.particleEntity)
				event.getEntityLiving().worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, event.getEntityLiving().posX + (rand.nextDouble() - 0.5D) * event.getEntityLiving().width,
						event.getEntityLiving().posY + rand.nextDouble() * event.getEntityLiving().height,
						event.getEntityLiving().posZ + (rand.nextDouble() - 0.5D) * event.getEntityLiving().width, 0,0,0);
		}
		if(AbyssalCraft.darkness)
			if(event.getEntityLiving() instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer)event.getEntityLiving();
				Random rand = new Random();
				ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
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
								player.addPotionEffect(new PotionEffect(MobEffects.blindness, 100));
				if(player.getActivePotionEffect(MobEffects.blindness) != null && player.getActivePotionEffect(MobEffects.blindness).getDuration() == 0)
					player.removePotionEffect(MobEffects.blindness);
			}
	}

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event) {
		if (event.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)
			if (event.getWorld().getBlockState(event.getPos()) == AbyssalCraft.Coraliumfire ||
			event.getWorld().getBlockState(event.getPos()) == AbyssalCraft.dreadfire ||
			event.getWorld().getBlockState(event.getPos()) == AbyssalCraft.omotholfire)
				event.getWorld().setBlockToAir(event.getPos());
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
		if(!(event.getEntityLiving() instanceof EntityJzahar))
			if(event.getEntityLiving().dimension == AbyssalCraft.configDimId3){
				event.getEntityLiving().attackEntityFrom(DamageSource.fall, event.getAttackDamage());
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.moveSlowdown, 200, 1));
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.weakness, 200, 1));
				event.setCanceled(true);
			}
	}

	@SubscribeEvent
	public void darklandsVillages(BiomeEvent.GetVillageBlockID event){
		if(event.getBiome() == AbyssalCraft.Darklands || event.getBiome() == AbyssalCraft.DarklandsPlains ||
				event.getBiome() == AbyssalCraft.DarklandsForest || event.getBiome() == AbyssalCraft.DarklandsHills ||
				event.getBiome() == AbyssalCraft.DarklandsMountains){
			if(event.getOriginal().getBlock() == Blocks.log || event.getOriginal().getBlock() == Blocks.log2){
				event.setReplacement(AbyssalCraft.DLTLog.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.cobblestone){
				event.setReplacement(AbyssalCraft.Darkstone_cobble.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.planks){
				event.setReplacement(AbyssalCraft.DLTplank.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.oak_stairs){
				event.setReplacement(AbyssalCraft.DLTstairs.getDefaultState().withProperty(BlockStairs.FACING, event.getOriginal().getValue(BlockStairs.FACING)));
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.stone_stairs){
				event.setReplacement(AbyssalCraft.DCstairs.getDefaultState().withProperty(BlockStairs.FACING, event.getOriginal().getValue(BlockStairs.FACING)));;
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.oak_fence){
				event.setReplacement(AbyssalCraft.DLTfence.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.stone_slab){
				event.setReplacement(AbyssalCraft.Darkstoneslab1.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.double_stone_slab){
				event.setReplacement(AbyssalCraft.Darkstoneslab2.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.wooden_pressure_plate){
				event.setReplacement(AbyssalCraft.DLTpplate.getDefaultState());
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void onRitualPerformed(RitualEvent.Post event){
		if(event.getRitual() instanceof NecronomiconSummonRitual){
			event.getEntityPlayer().addStat(AbyssalCraft.ritualSummon, 1);
			if(event.getRitual().getUnlocalizedName().substring(10).equals("summonSacthoth"))
				if(event.getWorld().isRemote)
					SpecialTextUtil.SacthothGroup(event.getWorld(), I18n.translateToLocal("message.sacthoth.spawn.1"));
			if(event.getRitual().getUnlocalizedName().substring(10).equals("summonAsorah")){
				if(event.getWorld().isRemote)
					SpecialTextUtil.AsorahGroup(event.getWorld(), I18n.translateToLocal("message.asorah.spawn"));
				event.getEntityPlayer().addStat(AbyssalCraft.summonAsorah, 1);
			}
		}
		if(event.getRitual() instanceof NecronomiconCreationRitual)
			event.getEntityPlayer().addStat(AbyssalCraft.ritualCreate, 1);
		if(event.getRitual() instanceof NecronomiconBreedingRitual
				|| event.getRitual() instanceof NecronomiconDreadSpawnRitual)
			event.getEntityPlayer().addStat(AbyssalCraft.ritualBreed, 1);
		if(event.getRitual() instanceof NecronomiconPotionRitual)
			event.getEntityPlayer().addStat(AbyssalCraft.ritualPotion, 1);
		if(event.getRitual() instanceof NecronomiconPotionAoERitual)
			event.getEntityPlayer().addStat(AbyssalCraft.ritualPotionAoE, 1);
		if(event.getRitual() instanceof NecronomiconInfusionRitual)
			event.getEntityPlayer().addStat(AbyssalCraft.ritualInfusion, 1);
	}
}
