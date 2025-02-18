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
package com.shinoow.abyssalcraft.common.handlers;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.event.FuelBurnTimeEvent;
import com.shinoow.abyssalcraft.api.event.RitualEvent;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconSummonRitual;
import com.shinoow.abyssalcraft.api.ritual.Rituals;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.items.ItemCrystalBag;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.common.world.data.NecromancyWorldSavedData;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.*;
import com.shinoow.abyssalcraft.lib.world.TeleporterDarkRealm;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.relauncher.Side;

public class AbyssalCraftEventHooks {

	@SubscribeEvent
	public void populateChunk(PopulateChunkEvent.Pre event) {
		Chunk chunk = event.getWorld().getChunk(event.getChunkX(), event.getChunkZ());
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
			if (storage != null && storage.getYLocation() >= 60)
				for (int x = 0; x < 16; ++x)
					for (int y = 0; y < 16; ++y)
						for (int z = 0; z < 16; ++z)
							if(chunk.getBiome(new BlockPos(x, y, z), event.getWorld().getBiomeProvider()) == ACBiomes.darklands_mountains) {
								Block block = storage.get(x, y, z).getBlock();
								if (block == Blocks.STONE || block == ACBlocks.abyssal_stone)
									storage.set(x, y, z, ACBlocks.darkstone.getDefaultState());
							}
	}

	//	@SubscribeEvent
	//	public void onItemPickup(EntityItemPickupEvent event) {
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.depths_ghoul_head))
	//			event.getEntityPlayer().addStat(ACAchievements.depths_ghoul_head, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.pete_head))
	//			event.getEntityPlayer().addStat(ACAchievements.pete_head, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.mr_wilson_head))
	//			event.getEntityPlayer().addStat(ACAchievements.mr_wilson_head, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.dr_orange_head))
	//			event.getEntityPlayer().addStat(ACAchievements.dr_orange_head, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.dreadlands_infused_powerstone))
	//			event.getEntityPlayer().addStat(ACAchievements.find_powerstone, 1);
	//		if(event.getItem().getItem().getItem() == ACItems.gateway_key)
	//			event.getEntityPlayer().addStat(ACAchievements.gateway_key, 1);
	//		if(event.getItem().getItem().getItem() == ACItems.dreaded_gateway_key)
	//			event.getEntityPlayer().addStat(ACAchievements.dreaded_gateway_key, 1);
	//		if(event.getItem().getItem().getItem() == ACItems.rlyehian_gateway_key)
	//			event.getEntityPlayer().addStat(ACAchievements.rlyehian_gateway_key, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.transmutator_idle))
	//			event.getEntityPlayer().addStat(ACAchievements.make_transmutator, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.crystallizer_idle))
	//			event.getEntityPlayer().addStat(ACAchievements.make_crystallizer, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.materializer))
	//			event.getEntityPlayer().addStat(ACAchievements.make_materializer, 1);
	//		if(event.getItem().getItem().getItem() == Item.getItemFromBlock(ACBlocks.engraver))
	//			event.getEntityPlayer().addStat(ACAchievements.make_engraver, 1);
	//	}

	@SubscribeEvent
	public void armorStuff(LivingHurtEvent event){
		EntityLivingBase target = event.getEntityLiving();
		if(!target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty()){
			ItemStack slot = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			if(slot.getItem() == ACItems.dreaded_abyssalnite_chestplate)
				if(event.getSource().getTrueSource() != null && target.world.rand.nextBoolean())
					event.getSource().getTrueSource().setFire(5);
			if(slot.getItem() == ACItems.plated_coralium_chestplate)
				if(event.getSource().getTrueSource() != null && target.world.rand.nextBoolean())
					event.getSource().getTrueSource().attackEntityFrom(getSource(target), 1);
		}
		if(!target.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() &&
				!target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() &&
				!target.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty() &&
				!target.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty()){
			ItemStack head = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			ItemStack chest = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			ItemStack legs = target.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
			ItemStack feet = target.getItemStackFromSlot(EntityEquipmentSlot.FEET);
			if(head.getItem() == ACItems.dreadium_samurai_helmet && chest.getItem() == ACItems.dreadium_samurai_chestplate &&
					legs.getItem() == ACItems.dreadium_samurai_leggings && feet.getItem() == ACItems.dreadium_samurai_boots &&
					event.getSource() == AbyssalCraftAPI.dread) event.setAmount(event.getAmount() * 0.5F);
			if(head.getItem() == ACItems.ethaxium_helmet && chest.getItem() == ACItems.ethaxium_chestplate &&
					legs.getItem() == ACItems.ethaxium_leggings && feet.getItem() == ACItems.ethaxium_boots &&
					event.getSource() == AbyssalCraftAPI.acid) event.setAmount(0);
		}
		if(event.getSource() == AbyssalCraftAPI.shadow) {
			ItemStack shield = target.getActiveItemStack();
			if(shield.getItem().isShield(shield, target) && shield.isItemEnchanted())
				if(EnchantmentHelper.getEnchantmentLevel(AbyssalCraftAPI.blinding_light, shield) > 0) {
					shield.damageItem((int)event.getAmount() * 2, target);
					event.setAmount(0);
				}
		}
	}

	private DamageSource getSource(EntityLivingBase entity){
		if(entity instanceof EntityPlayer)
			return DamageSource.causePlayerDamage((EntityPlayer)entity);
		return DamageSource.causeMobDamage(entity);
	}

	@SubscribeEvent
	public void damageStuff(LivingAttackEvent event){
		EntityLivingBase entity = event.getEntityLiving();
		if(EntityUtil.isEntityCoralium(entity) || EntityUtil.isEntityDread(entity) || entity instanceof IAntiEntity ||
				entity.getCreatureAttribute() == AbyssalCraftAPI.SHADOW) {
			DamageSource source = event.getSource();
			if(EntityUtil.isEntityDread(entity) && (source.getTrueSource() instanceof EntityLivingBase &&
					EntityUtil.isDreadPlagueCarrier((EntityLivingBase) source.getTrueSource())
					|| source == AbyssalCraftAPI.dread))
				event.setCanceled(true);
			if(!(entity instanceof EntityPlayer) && EntityUtil.isEntityCoralium(entity) && (source.getTrueSource() instanceof EntityLivingBase &&
					EntityUtil.isCoraliumPlagueCarrier((EntityLivingBase) source.getTrueSource())
					|| source == AbyssalCraftAPI.coralium))
				event.setCanceled(true);
			if(entity instanceof IAntiEntity && (source.getTrueSource() instanceof IAntiEntity
					|| source == AbyssalCraftAPI.antimatter))
				event.setCanceled(true);
			if(entity.getCreatureAttribute() == AbyssalCraftAPI.SHADOW && (source.getTrueSource() instanceof EntityLivingBase
					&& ((EntityLivingBase) source.getTrueSource()).getCreatureAttribute() == AbyssalCraftAPI.SHADOW
					|| source == AbyssalCraftAPI.shadow))
				event.setCanceled(true);
			if(EntityUtil.isEntityEldritch(entity) && (source.getTrueSource() instanceof EntityLivingBase && EntityUtil.isEntityEldritch((EntityLivingBase) source.getTrueSource())
					|| source == AbyssalCraftAPI.dread || source == AbyssalCraftAPI.coralium
					|| source == AbyssalCraftAPI.antimatter || source == AbyssalCraftAPI.acid))
				event.setCanceled(true);
			if(entity instanceof EntityPlayer && EntityUtil.isEntityCoralium(entity) &&
					source == AbyssalCraftAPI.coralium)
				event.setCanceled(true);
		}

		if(EntityUtil.isEntityDread(entity))
			processRadiation(event);
	}

	private static String[] sourceNames = {"fission_burn", "fatal_rads"};
	private static String[] sourceNames2 = {"nuclearBlast", "digamma", "radiation"};

	public static boolean isRadiationDamage(DamageSource source) {
		
		String type = source.getDamageType();
		
		boolean flag = false;
		
		if(Loader.isModLoaded("nuclearcraft")) {
			for(String src : sourceNames)
				if(type.contentEquals(src)) {
					return true;
				}
		}
		if(Loader.isModLoaded("hbm")) {
			for(String src : sourceNames2)
				if(type.contentEquals(src)) {
					return true;
				}
		}
		
		return false;
	}
	
	private void processRadiation(LivingAttackEvent event) {
		if(isRadiationDamage(event.getSource())) {
			event.setCanceled(true);
			// Dread Plague carriers absorb the radiation for sustenance
			// (but only if Hardcore Mode is enabled, because we are nice here)
			if(ACConfig.hardcoreMode && EntityUtil.isDreadPlagueCarrier(event.getEntityLiving()))
				event.getEntityLiving().heal(event.getAmount());
		}
	}

	@SubscribeEvent
	public void darkRealm(LivingUpdateEvent event){
		if(event.getEntityLiving() instanceof EntityPlayerMP){
			WorldServer worldServer = (WorldServer)event.getEntityLiving().world;
			EntityPlayerMP player = (EntityPlayerMP)event.getEntityLiving();
			if(player.dimension == ACLib.omothol_id && player.posY <= 0){
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 80, 255));
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20));
				player.server.getPlayerList().transferPlayerToDimension(player, ACLib.dark_realm_id, new TeleporterDarkRealm(worldServer));
				//				player.addStat(ACAchievements.enter_dark_realm, 1);
			}
		}
		if(ACConfig.darkRealmSmokeParticles)
			if(event.getEntityLiving().dimension == ACLib.dark_realm_id && !(event.getEntityLiving() instanceof EntityPlayer)
			&& event.getEntityLiving().getCreatureAttribute() != AbyssalCraftAPI.SHADOW)
				if(ACConfig.particleEntity)
					ParticleUtil.spawnShadowParticles(event.getEntityLiving());
	}

	@SubscribeEvent
	public void onCraftingEvent(PlayerEvent.ItemCraftedEvent event)
	{
		for(int h=0; h < event.craftMatrix.getSizeInventory(); h++)
			if(event.craftMatrix.getStackInSlot(h) != null){
				ItemStack k = event.craftMatrix.getStackInSlot(h);

				if(k.getItem() != null && k.getItem() instanceof ItemCrystalBag){
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
					float energy = 0;

					if(k.getTagCompound() == null)
						k.setTagCompound(compound);
					energy = k.getTagCompound().getFloat("PotEnergy");

					ItemStack l = event.crafting;

					if(l.getItem() instanceof ItemNecronomicon){
						if(l.getTagCompound() == null)
							l.setTagCompound(compound);
						if(energy != 0)
							l.getTagCompound().setFloat("PotEnergy", energy);
					}
				}
			}

		//		if(event.crafting.getItem() == ACItems.gateway_key)
		//			event.player.addStat(ACAchievements.gateway_key, 1);
		//		if(event.crafting.getItem() == ACItems.shadow_gem)
		//			event.player.addStat(ACAchievements.shadow_gems, 1);
		//		if(event.crafting.getItem() == ACItems.ethaxium_ingot)
		//			event.player.addStat(ACAchievements.ethaxium, 1);
		//		if(event.crafting.getItem() == ACItems.necronomicon)
		//			event.player.addStat(ACAchievements.necronomicon, 1);
		//		if(event.crafting.getItem() == ACItems.abyssal_wasteland_necronomicon)
		//			event.player.addStat(ACAchievements.abyssal_wasteland_necronomicon, 1);
		//		if(event.crafting.getItem() == ACItems.dreadlands_necronomicon)
		//			event.player.addStat(ACAchievements.dreadlands_necronomicon, 1);
		//		if(event.crafting.getItem() == ACItems.omothol_necronomicon)
		//			event.player.addStat(ACAchievements.omothol_necronomicon, 1);
		//		if(event.crafting.getItem() == ACItems.abyssalnomicon)
		//			event.player.addStat(ACAchievements.abyssalnomicon, 1);
		//		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.transmutator_idle))
		//			event.player.addStat(ACAchievements.make_transmutator, 1);
		//		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.crystallizer_idle))
		//			event.player.addStat(ACAchievements.make_crystallizer, 1);
		//		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.materializer))
		//			event.player.addStat(ACAchievements.make_materializer, 1);
		//		if(event.crafting.getItem() == Item.getItemFromBlock(ACBlocks.engraver))
		//			event.player.addStat(ACAchievements.make_engraver, 1);
		//		if(event.crafting.getItem() == ACItems.small_crystal_bag ||
		//				event.crafting.getItem() == ACItems.medium_crystal_bag ||
		//				event.crafting.getItem() == ACItems.large_crystal_bag ||
		//				event.crafting.getItem() == ACItems.huge_crystal_bag)
		//			event.player.addStat(ACAchievements.make_crystal_bag, 1);
	}

	@SubscribeEvent
	public void noTPinOmothol(EnderTeleportEvent event){
		if(!(event.getEntityLiving() instanceof EntityJzahar))
			if(event.getEntityLiving().dimension == ACLib.omothol_id){
				event.getEntityLiving().attackEntityFrom(DamageSource.FALL, event.getAttackDamage());
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 1));
				event.setCanceled(true);
			}
	}

	@SubscribeEvent
	public void darklandsVillages(BiomeEvent.GetVillageBlockID event){
		if(event.getBiome() instanceof IDarklandsBiome){
			if(event.getOriginal().getBlock() == Blocks.LOG || event.getOriginal().getBlock() == Blocks.LOG2){
				event.setReplacement(ACBlocks.darklands_oak_wood.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.COBBLESTONE){
				event.setReplacement(ACBlocks.darkstone_cobblestone.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.PLANKS){
				event.setReplacement(ACBlocks.darklands_oak_planks.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.OAK_STAIRS){
				event.setReplacement(ACBlocks.darklands_oak_stairs.getDefaultState().withProperty(BlockStairs.FACING, event.getOriginal().getValue(BlockStairs.FACING)));
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.STONE_STAIRS){
				event.setReplacement(ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, event.getOriginal().getValue(BlockStairs.FACING)));
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.OAK_FENCE){
				event.setReplacement(ACBlocks.darklands_oak_fence.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.STONE_SLAB){
				event.setReplacement(ACBlocks.darkstone_slab.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.DOUBLE_STONE_SLAB){
				event.setReplacement(BlockHandler.Darkstoneslab2.getDefaultState());
				event.setResult(Result.DENY);
			}
			if(event.getOriginal().getBlock() == Blocks.WOODEN_PRESSURE_PLATE){
				event.setReplacement(ACBlocks.darklands_oak_pressure_plate.getDefaultState());
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void onRitualPerformed(RitualEvent.Post event){
		if(event.getRitual() instanceof NecronomiconSummonRitual)
			//			event.getEntityPlayer().addStat(ACAchievements.summoning_ritual, 1);
			if(ACConfig.showBossDialogs) {
				if(event.getRitual() == Rituals.SUMMON_SACTHOTH)
					if(!event.getWorld().isRemote)
						SpecialTextUtil.SacthothGroup(event.getWorld(), TranslationUtil.toLocal("message.sacthoth.spawn.1"));
				if(event.getRitual() == Rituals.SUMMON_ASORAH)
					if(!event.getWorld().isRemote)
						SpecialTextUtil.AsorahGroup(event.getWorld(), TranslationUtil.toLocal("message.asorah.spawn"));
			}
		//				event.getEntityPlayer().addStat(ACAchievements.summon_asorah, 1);
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		if(event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote){
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			if(event.getSource().getTrueSource() instanceof EntityEvilSheep)
				((EntityEvilSheep)event.getSource().getTrueSource()).setKilledPlayer(player);
			else if(event.getSource().getTrueSource() instanceof EntityAntiPlayer && player.getRNG().nextBoolean()) {
				EntityAntiPlayer antiPlayer = new EntityAntiPlayer(player.world);
				antiPlayer.copyLocationAndAnglesFrom(player);
				antiPlayer.onInitialSpawn(player.world.getDifficultyForLocation(event.getSource().getTrueSource().getPosition()), (IEntityLivingData)null);
				antiPlayer.setCustomNameTag(invert(player.getName()));
				antiPlayer.enablePersistence();
				player.world.spawnEntity(antiPlayer);
				player.world.playEvent((EntityPlayer)null, 1016, event.getSource().getTrueSource().getPosition(), 0);
			}
		} else if(event.getEntityLiving() instanceof EntityLiving && event.getEntityLiving().hasCustomName() &&
				event.getEntityLiving().isNonBoss() && !event.getEntityLiving().world.isRemote) {
			EntityLivingBase e = event.getEntityLiving();
			NecromancyWorldSavedData.get(e.world).storeData(e.getName(), e.serializeNBT(), calculateSize(e.height));
		} else if(EntityList.getKey(event.getEntityLiving()) != null){
			EntityLivingBase e = event.getEntityLiving();
			if(!(e instanceof EntityEvilAnimal) && !(e instanceof EntityDemonAnimal)){
				Tuple<Integer, Float> data = InitHandler.demon_transformations.get(EntityList.getKey(e));
				World world = event.getEntityLiving().world;
				if(data != null && world.rand.nextFloat() < data.getSecond() && !world.isRemote){
					EntityLiving demon = getDemon(data.getFirst(), world);
					demon.copyLocationAndAnglesFrom(e);
					world.removeEntity(e);
					demon.onInitialSpawn(world.getDifficultyForLocation(e.getPosition()), (IEntityLivingData)null);
					world.spawnEntity(demon);
				}
			}
		}
	}

	private int calculateSize(float height){

		if(height >= 1.5f)
			return 2;
		if(height >= 0.75f)
			return 1;
		return 0;
	}

	private EntityLiving getDemon(int num, World world){
		switch(num){
		case 0:
			return new EntityDemonPig(world);
		case 1:
			return new EntityDemonCow(world);
		case 2:
			return new EntityDemonChicken(world);
		case 3:
			return new EntityDemonSheep(world);
		default:
			return new EntityDemonPig(world);
		}
	}

	private String invert(String name) {
		String newname = "";

		int length = name.length();
		boolean[] cases = new boolean[length];
		for(int j = 0; j < length; j++)
			cases[j] = Character.isUpperCase(name.charAt(j));
		for(int i = length - 1; i >= 0; i--) {
			char c = name.charAt(i);
			int k = length - 1 - i;
			newname += cases[k] ? Character.toUpperCase(c) : Character.toLowerCase(c);
		}

		return newname;
	}

	@SubscribeEvent
	public void onUseHoe(UseHoeEvent event){

		Block b = event.getWorld().getBlockState(event.getPos()).getBlock();

		if(b == ACBlocks.dreadlands_grass || b == ACBlocks.dreadlands_dirt){

			event.getWorld().playSound(event.getEntityPlayer(), event.getPos(), SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if(!event.getWorld().isRemote)
				event.getWorld().setBlockState(event.getPos(), Blocks.FARMLAND.getDefaultState());
			event.setResult(Result.ALLOW);
		}
	}

	@SubscribeEvent
	public void nameTags(EntityInteract event) {
		ItemStack stack = event.getEntityPlayer().getHeldItem(event.getHand());
		if(stack.getItem() == Items.NAME_TAG && stack.hasDisplayName() && event.getTarget() instanceof IAntiEntity) {
			EntityLiving target = (EntityLiving)event.getTarget();
			target.setCustomNameTag(invert(stack.getDisplayName()));
			target.enablePersistence();
			if(!event.getEntityPlayer().capabilities.isCreativeMode)
				stack.shrink(1);
			event.setCanceled(true);

		}
	}

	@SubscribeEvent
	public void livingDrops(LivingDropsEvent event) {
		if(event.getEntityLiving() instanceof EntityDragonBoss || event.getEntityLiving() instanceof EntityChagaroth ||
				event.getEntityLiving() instanceof EntityJzahar || event.getEntityLiving() instanceof EntitySacthoth) {
			EntityLivingBase entity = event.getEntityLiving();
			event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ACItems.greater_scroll)));
		}
	}

	@SubscribeEvent
	public void fuelBurnTime(FuelBurnTimeEvent event) {

	}

	@SubscribeEvent
	public void onTick(ServerTickEvent event) {
		if(event.side == Side.SERVER && event.type == Type.SERVER && event.phase == Phase.START)
			Scheduler.tick();
	}
}
