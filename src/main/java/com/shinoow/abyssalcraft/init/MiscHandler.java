/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.AbyssalCraft.modid;
import static com.shinoow.abyssalcraft.init.BlockHandler.*;
import static com.shinoow.abyssalcraft.lib.ACSounds.*;

import java.io.File;
import java.util.Stack;

import javax.annotation.Nonnull;

import com.google.common.base.Predicate;
import com.google.gson.JsonObject;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.condition.ConditionProcessorRegistry;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapabilityStorage;
import com.shinoow.abyssalcraft.common.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster.EnumCrystalType;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster2.EnumCrystalType2;
import com.shinoow.abyssalcraft.common.caps.INecromancyCapability;
import com.shinoow.abyssalcraft.common.caps.NecromancyCapability;
import com.shinoow.abyssalcraft.common.caps.NecromancyCapabilityStorage;
import com.shinoow.abyssalcraft.common.enchantments.EnchantmentIronWall;
import com.shinoow.abyssalcraft.common.enchantments.EnchantmentLightPierce;
import com.shinoow.abyssalcraft.common.enchantments.EnchantmentWeaponInfusion;
import com.shinoow.abyssalcraft.common.handlers.CrystalFuelHandler;
import com.shinoow.abyssalcraft.common.handlers.FurnaceFuelHandler;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.potion.PotionAntimatter;
import com.shinoow.abyssalcraft.common.potion.PotionCplague;
import com.shinoow.abyssalcraft.common.potion.PotionDplague;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.common.util.ShapedNBTRecipe;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.NecroDataJsonUtil;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class MiscHandler implements ILifeCycleHandler {

	public static PotionType Cplague_normal, Cplague_long, Dplague_normal, Dplague_long,
	Dplague_strong, antiMatter_normal, antiMatter_long;

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(this);

		AbyssalCraftAPI.coralium_plague = new PotionCplague(true, 0x00FFFF).setIconIndex(1, 0).setPotionName("potion.Cplague");
		AbyssalCraftAPI.dread_plague = new PotionDplague(true, 0xAD1313).setIconIndex(1, 0).setPotionName("potion.Dplague");
		AbyssalCraftAPI.antimatter_potion = new PotionAntimatter(true, 0xFFFFFF).setIconIndex(1, 0).setPotionName("potion.Antimatter");

		registerPotion(new ResourceLocation("abyssalcraft", "cplague"), AbyssalCraftAPI.coralium_plague);
		registerPotion(new ResourceLocation("abyssalcraft", "dplague"), AbyssalCraftAPI.dread_plague);
		registerPotion(new ResourceLocation("abyssalcraft", "antimatter"), AbyssalCraftAPI.antimatter_potion);

		Cplague_normal = new PotionType("Cplague", new PotionEffect(AbyssalCraftAPI.coralium_plague, 3600));
		Cplague_long = new PotionType("Cplague", new PotionEffect(AbyssalCraftAPI.coralium_plague, 9600));
		Dplague_normal = new PotionType("Dplague", new PotionEffect(AbyssalCraftAPI.dread_plague, 3600));
		Dplague_long = new PotionType("Dplague", new PotionEffect(AbyssalCraftAPI.dread_plague, 9600));
		Dplague_strong = new PotionType("Dplague", new PotionEffect(AbyssalCraftAPI.dread_plague, 432, 1));
		antiMatter_normal = new PotionType("Antimatter", new PotionEffect(AbyssalCraftAPI.antimatter_potion, 3600));
		antiMatter_long = new PotionType("Antimatter", new PotionEffect(AbyssalCraftAPI.antimatter_potion, 9600));

		registerPotionType(new ResourceLocation("abyssalcraft", "cplague"), Cplague_normal);
		registerPotionType(new ResourceLocation("abyssalcraft", "cplague_long"), Cplague_long);
		registerPotionType(new ResourceLocation("abyssalcraft", "dplague"), Dplague_normal);
		registerPotionType(new ResourceLocation("abyssalcraft", "dplague_long"), Dplague_long);
		registerPotionType(new ResourceLocation("abyssalcraft", "dplague_strong"), Dplague_strong);
		registerPotionType(new ResourceLocation("abyssalcraft", "antimatter"), antiMatter_normal);
		registerPotionType(new ResourceLocation("abyssalcraft", "antimatter_long"), antiMatter_long);

		addBrewing(PotionTypes.AWKWARD, ACItems.coralium_plagued_flesh, Cplague_normal);
		addBrewing(PotionTypes.AWKWARD, ACItems.coralium_plagued_flesh_on_a_bone, Cplague_normal);
		addBrewing(Cplague_normal, Items.REDSTONE, Cplague_long);
		addBrewing(PotionTypes.AWKWARD, ACItems.dread_fragment, Dplague_normal);
		addBrewing(Dplague_normal, Items.REDSTONE, Dplague_long);
		addBrewing(Dplague_normal, Items.GLOWSTONE_DUST, Dplague_strong);
		addBrewing(PotionTypes.AWKWARD, ACItems.rotten_anti_flesh, antiMatter_normal);
		addBrewing(PotionTypes.AWKWARD, ACItems.anti_plagued_flesh, antiMatter_normal);
		addBrewing(PotionTypes.AWKWARD, ACItems.anti_plagued_flesh_on_a_bone, antiMatter_normal);
		addBrewing(antiMatter_normal, Items.REDSTONE, antiMatter_long);

		AbyssalCraftAPI.coralium_enchantment = new EnchantmentWeaponInfusion("coralium");
		AbyssalCraftAPI.dread_enchantment = new EnchantmentWeaponInfusion("dread");
		AbyssalCraftAPI.light_pierce = new EnchantmentLightPierce();
		AbyssalCraftAPI.iron_wall = new EnchantmentIronWall();

		registerEnchantment(new ResourceLocation("abyssalcraft", "coralium"), AbyssalCraftAPI.coralium_enchantment);
		registerEnchantment(new ResourceLocation("abyssalcraft", "dread"), AbyssalCraftAPI.dread_enchantment);
		registerEnchantment(new ResourceLocation("abyssalcraft", "light_pierce"), AbyssalCraftAPI.light_pierce);
		registerEnchantment(new ResourceLocation("abyssalcraft", "iron_wall"), AbyssalCraftAPI.iron_wall);

		InitHandler.LIQUID_CORALIUM.setBlock(ACBlocks.liquid_coralium);
		InitHandler.LIQUID_ANTIMATTER.setBlock(ACBlocks.liquid_antimatter);
		if(AbyssalCraftAPI.liquid_coralium_fluid.getBlock() == null)
			AbyssalCraftAPI.liquid_coralium_fluid.setBlock(ACBlocks.liquid_coralium);
		if(AbyssalCraftAPI.liquid_antimatter_fluid.getBlock() == null)
			AbyssalCraftAPI.liquid_antimatter_fluid.setBlock(ACBlocks.liquid_antimatter);

		dreadguard_ambient = registerSoundEvent("dreadguard.idle");
		dreadguard_hurt = registerSoundEvent("dreadguard.hit");
		dreadguard_death = registerSoundEvent("dreadguard.death");
		ghoul_normal_ambient = registerSoundEvent("ghoul.normal.idle");
		ghoul_hurt = registerSoundEvent("ghoul.hit");
		ghoul_death = registerSoundEvent("ghoul.death");
		ghoul_pete_ambient = registerSoundEvent("ghoul.pete.idle");
		ghoul_wilson_ambient = registerSoundEvent("ghoul.wilson.idle");
		ghoul_orange_ambient = registerSoundEvent("ghoul.orange.idle");
		golem_death = registerSoundEvent("golem.death");
		golem_hurt = registerSoundEvent("golem.hit");
		golem_ambient = registerSoundEvent("golem.idle");
		sacthoth_death = registerSoundEvent("sacthoth.death");
		shadow_death = registerSoundEvent("shadow.death");
		shadow_hurt = registerSoundEvent("shadow.hit");
		remnant_scream = registerSoundEvent("remnant.scream");
		remnant_yes = registerSoundEvent("remnant.yes");
		remnant_no = registerSoundEvent("remnant.no");
		remnant_priest_chant = registerSoundEvent("remnant.priest.chant");
		shoggoth_ambient = registerSoundEvent("shoggoth.idle");
		shoggoth_hurt = registerSoundEvent("shoggoth.hit");
		shoggoth_death = registerSoundEvent("shoggoth.death");
		jzahar_charge = registerSoundEvent("jzahar.charge");
		cthulhu_chant = registerSoundEvent("chant.cthulhu");
		yog_sothoth_chant_1 = registerSoundEvent("chant.yog_sothoth_1");
		yog_sothoth_chant_2 = registerSoundEvent("chant.yog_sothoth_2");
		hastur_chant_1 = registerSoundEvent("chant.hastur_1");
		hastur_chant_2 = registerSoundEvent("chant.hastur_2");
		sleeping_chant = registerSoundEvent("chant.sleeping");
		cthugha_chant = registerSoundEvent("chant.cthugha");
		dread_spawn_ambient = registerSoundEvent("dreadspawn.idle");
		dread_spawn_hurt = registerSoundEvent("dreadspawn.hit");
		dread_spawn_death = registerSoundEvent("dreadspawn.death");
		abyssal_zombie_ambient = registerSoundEvent("abyssalzombie.idle");
		abyssal_zombie_hurt = registerSoundEvent("abyssalzombie.hit");
		abyssal_zombie_death = registerSoundEvent("abyssalzombie.death");
		antiplayer_hurt = registerSoundEvent("antiplayer.hurt");
		dreadguard_barf = registerSoundEvent("dreadguard.barf");

		CapabilityManager.INSTANCE.register(INecroDataCapability.class, NecroDataCapabilityStorage.instance, NecroDataCapability::new);
		CapabilityManager.INSTANCE.register(INecromancyCapability.class, NecromancyCapabilityStorage.instance, NecromancyCapability::new);

		ConditionProcessorRegistry.instance().registerProcessor(0, (condition, cap, player) -> { return cap.getBiomeTriggers().contains(condition.getConditionObject()); });
		ConditionProcessorRegistry.instance().registerProcessor(1, (condition, cap, player) -> { return cap.getEntityTriggers().contains(condition.getConditionObject()); });
		ConditionProcessorRegistry.instance().registerProcessor(2, (condition, cap, player) -> { return cap.getDimensionTriggers().contains(condition.getConditionObject()); });
		ConditionProcessorRegistry.instance().registerProcessor(3, (condition, cap, player) -> {
			for(String name : (String[])condition.getConditionObject())
				if(cap.getBiomeTriggers().contains(name))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(4, (condition, cap, player) -> {
			for(String name : (String[])condition.getConditionObject())
				if(cap.getEntityTriggers().contains(name))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(5, (condition, cap, player) -> {
			for(String name : cap.getBiomeTriggers())
				if(((Predicate<Biome>)condition.getConditionObject()).apply(ForgeRegistries.BIOMES.getValue(new ResourceLocation(name))))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(6, (condition, cap, player) -> {
			for(String name : cap.getEntityTriggers())
				if(((Predicate<Class<? extends Entity>>)condition.getConditionObject()).apply(EntityList.getClass(new ResourceLocation(name))))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(7, (condition, cap, player) -> { return cap.getArtifactTriggers().contains(condition.getConditionObject()); });
		ConditionProcessorRegistry.instance().registerProcessor(8, (condition, cap, player) -> { return cap.getPageTriggers().contains(condition.getConditionObject()); });
		ConditionProcessorRegistry.instance().registerProcessor(9, (condition, cap, player) -> { return cap.getWhisperTriggers().contains(condition.getConditionObject()); });
		ConditionProcessorRegistry.instance().registerProcessor(10, (condition, cap, player) -> { return cap.getMiscTriggers().contains(condition.getConditionObject()); });

		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		//Achievements
		//		//Depths Ghoul Achievements
		//		ACAchievements.kill_depths_ghoul = new Achievement("achievement.killghoul", "killghoul", -2, 0, ACItems.coralium_plagued_flesh_on_a_bone, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.depths_ghoul_head = new Achievement("achievement.ghoulhead", "ghoulhead", -4, 0, ACBlocks.depths_ghoul_head, ACAchievements.kill_depths_ghoul).registerStat();
		//		ACAchievements.pete_head = new Achievement("achievement.petehead", "petehead", -4, -2, ACBlocks.pete_head, ACAchievements.depths_ghoul_head).registerStat();
		//		ACAchievements.mr_wilson_head = new Achievement("achievement.wilsonhead", "wilsonhead", -4, -4, ACBlocks.mr_wilson_head, ACAchievements.pete_head).registerStat();
		//		ACAchievements.dr_orange_head = new Achievement("achievement.orangehead", "orangehead", -4, -6, ACBlocks.dr_orange_head, ACAchievements.mr_wilson_head).registerStat();
		//		//Necronomicon Achievements
		//		ACAchievements.abyssal_wasteland_necronomicon = new Achievement("achievement.necrou1", "necrou1", 2, 1, ACItems.abyssal_wasteland_necronomicon, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.dreadlands_necronomicon = new Achievement("achievement.necrou2", "necrou2", 4, 1, ACItems.dreadlands_necronomicon, ACAchievements.abyssal_wasteland_necronomicon).registerStat();
		//		ACAchievements.omothol_necronomicon = new Achievement("achievement.necrou3", "necrou3", 6, 1, ACItems.omothol_necronomicon, ACAchievements.dreadlands_necronomicon).registerStat();
		//		ACAchievements.abyssalnomicon = new Achievement("achievement.abyssaln", "abyssaln", 8, 1, ACItems.abyssalnomicon, ACAchievements.omothol_necronomicon).setSpecial().registerStat();
		//		//Ritual Achievements
		//		ACAchievements.ritual_altar = new Achievement("achievement.ritual", "ritual", -2, 1, ACBlocks.ritual_altar, ACAchievements.necronomicon).setSpecial().registerStat();
		//		ACAchievements.summoning_ritual = new Achievement("achievement.ritualSummon", "ritualSummon", -4, 1, ACBlocks.depths_ghoul_head, ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.creation_ritual = new Achievement("achievement.ritualCreate", "ritualCreate", -4, 2, ACItems.life_crystal, ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.breeding_ritual = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.EGG, ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.potion_ritual = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.aoe_potion_ritual = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.WATER), ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.infusion_ritual = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, ACItems.depths_helmet, ACAchievements.ritual_altar).registerStat();
		//		//Progression Achievements
		//		ACAchievements.enter_abyssal_wasteland = new Achievement("achievement.enterabyss", "enterabyss", 0, 2, ACBlocks.abyssal_stone, ACAchievements.necronomicon).setSpecial().registerStat();
		//		ACAchievements.kill_spectral_dragon = new Achievement("achievement.killdragon", "killdragon", 2, 2, ACItems.coralium_plagued_flesh, ACAchievements.enter_abyssal_wasteland).registerStat();
		//		ACAchievements.summon_asorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 4, Altar, ACAchievements.enter_abyssal_wasteland).registerStat();
		//		ACAchievements.kill_asorah = new Achievement("achievement.killAsorah", "killAsorah", 2, 4, ACItems.eye_of_the_abyss, ACAchievements.summon_asorah).setSpecial().registerStat();
		//		ACAchievements.enter_dreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 2, 6, ACBlocks.dreadstone, ACAchievements.kill_asorah).setSpecial().registerStat();
		//		ACAchievements.kill_dreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 4, 6, ACItems.dreaded_shard_of_abyssalnite, ACAchievements.enter_dreadlands).registerStat();
		//		ACAchievements.summon_chagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 2, 8, ACBlocks.chagaroth_altar_bottom, ACAchievements.enter_dreadlands).registerStat();
		//		ACAchievements.kill_chagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 4, 8, ACItems.dread_plagued_gateway_key, ACAchievements.summon_chagaroth).setSpecial().registerStat();
		//		ACAchievements.enter_omothol = new Achievement("achievement.enterOmothol", "enterOmothol", 4, 10, ACBlocks.omothol_stone, ACAchievements.kill_chagaroth).setSpecial().registerStat();
		//		ACAchievements.enter_dark_realm = new Achievement("achievement.darkRealm", "darkRealm", 2, 10, ACBlocks.darkstone, ACAchievements.enter_omothol).registerStat();
		//		ACAchievements.kill_omothol_elite = new Achievement("achievement.killOmotholelite", "killOmotholelite", 6, 10, ACItems.eldritch_scale, ACAchievements.enter_omothol).registerStat();
		//		ACAchievements.locate_jzahar = new Achievement("achievement.locateJzahar", "locateJzahar", 4, 12, ACItems.jzahar_charm, ACAchievements.enter_omothol).registerStat();
		//		ACAchievements.kill_jzahar = new Achievement("achievement.killJzahar", "killJzahar", 6, 12, ACItems.staff_of_the_gatekeeper, ACAchievements.locate_jzahar).setSpecial().registerStat();
		//		//nowwhat
		//		//Gateway Key Achievements
		//		ACAchievements.gateway_key = new Achievement("achievement.GK1", "GK1", 0, -2, ACItems.gateway_key, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.find_powerstone = new Achievement("achievement.findPSDL", "findPSDL", -2, -2, ACBlocks.dreadlands_infused_powerstone, ACAchievements.gateway_key).registerStat();
		//		ACAchievements.dreaded_gateway_key = new Achievement("achievement.GK2", "GK2", 0, -4, ACItems.dreaded_gateway_key, ACAchievements.gateway_key).registerStat();
		//		ACAchievements.rlyehian_gateway_key = new Achievement("achievement.GK3", "GK3", 0, -6, ACItems.rlyehian_gateway_key, ACAchievements.dreaded_gateway_key).registerStat();
		//		//Machinery Achievements
		//		ACAchievements.make_transmutator = new Achievement("achievement.makeTransmutator", "makeTransmutator", 2, -1, ACBlocks.transmutator_idle, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.make_crystallizer = new Achievement("achievement.makeCrystallizer", "makeCrystallizer", 4, -2, ACBlocks.crystallizer_idle, ACAchievements.make_transmutator).registerStat();
		//		ACAchievements.make_materializer = new Achievement("achievement.makeMaterializer", "makeMaterializer", 6, -2, ACBlocks.materializer, ACAchievements.make_crystallizer).registerStat();
		//		ACAchievements.make_crystal_bag = new Achievement("achievement.makeCrystalBag", "makeCrystalBag", 6, -4, ACItems.small_crystal_bag, ACAchievements.make_materializer).registerStat();
		//		ACAchievements.make_engraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, ACBlocks.engraver, AchievementList.OPEN_INVENTORY).registerStat();
		//
		//		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", ACAchievements.getAchievements()));

		//		RecipeSorter.register("abyssalcraft:shapednbt", ShapedNBTRecipe.class, Category.SHAPED, "after:minecraft:shaped");

		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.CRYSTALLIZER);
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.TRANSMUTATOR);
		AbyssalCrafting.addRecipes();
		AbyssalCraftAPI.addGhoulArmorTextures(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS, "abyssalcraft:textures/armor/ghoul/leather_1.png", "abyssalcraft:textures/armor/ghoul/leather_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS, "abyssalcraft:textures/armor/ghoul/chainmail_1.png", "abyssalcraft:textures/armor/ghoul/chainmail_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, "abyssalcraft:textures/armor/ghoul/iron_1.png", "abyssalcraft:textures/armor/ghoul/iron_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, "abyssalcraft:textures/armor/ghoul/gold_1.png", "abyssalcraft:textures/armor/ghoul/gold_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS, "abyssalcraft:textures/armor/ghoul/diamond_1.png", "abyssalcraft:textures/armor/ghoul/diamond_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/abyssalnite_1.png", "abyssalcraft:textures/armor/ghoul/abyssalnite_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots, "abyssalcraft:textures/armor/ghoul/coralium_1.png", "abyssalcraft:textures/armor/ghoul/coralium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots, "abyssalcraft:textures/armor/ghoul/dreadium_1.png", "abyssalcraft:textures/armor/ghoul/dreadium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.ethaxium_helmet, ACItems.ethaxium_chestplate, ACItems.ethaxium_leggings, ACItems.ethaxium_boots, "abyssalcraft:textures/armor/ghoul/ethaxium_1.png", "abyssalcraft:textures/armor/ghoul/ethaxium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreaded_abyssalnite_helmet, ACItems.dreaded_abyssalnite_chestplate, ACItems.dreaded_abyssalnite_leggings, ACItems.dreaded_abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/dread_1.png", "abyssalcraft:textures/armor/ghoul/dread_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.depths_helmet, ACItems.depths_chestplate, ACItems.depths_leggings, ACItems.depths_boots, "abyssalcraft:textures/armor/ghoul/depths_1.png", "abyssalcraft:textures/armor/ghoul/depths_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.plated_coralium_helmet, ACItems.plated_coralium_chestplate, ACItems.plated_coralium_leggings, ACItems.plated_coralium_boots, "abyssalcraft:textures/armor/ghoul/coraliump_1.png", "abyssalcraft:textures/armor/ghoul/coraliump_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_samurai_helmet, ACItems.dreadium_samurai_chestplate, ACItems.dreadium_samurai_leggings, ACItems.dreadium_samurai_boots, "abyssalcraft:textures/armor/ghoul/dreadiums_1.png", "abyssalcraft:textures/armor/ghoul/dreadiums_2.png");
		AbyssalCraftAPI.getInternalNDHandler().registerInternalPages();
		//		StructureHandler.instance().registerStructure(new TestStructure());
		FMLCommonHandler.instance().getDataFixer().init(modid, 1).registerFix(FixTypes.BLOCK_ENTITY, new IFixableData() {

			@Override
			public int getFixVersion() {

				return 1;
			}

			@Override
			public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
				final String id = compound.getString("id");
				switch(id) {
				case "minecraft:tileentitycrate":
				case "minecraft:tileentitydghead":
				case "minecraft:tileentityphead":
				case "minecraft:tileentitywhead":
				case "minecraft:tileentityohead":
				case "minecraft:tileentitycrystallizer":
				case "minecraft:tileentitytransmutator":
				case "minecraft:tileentitydradguardspawner":
				case "minecraft:tileentitychagarothspawner":
				case "minecraft:tileentityengraver":
				case "minecraft:tileentitymaterializer":
				case "minecraft:tileentityritualaltar":
				case "minecraft:tileentityritualpedestal":
				case "minecraft:tileentitystatue":
				case "minecraft:tileentitydecorativestatue":
				case "minecraft:tileentityshoggothbiomass":
				case "minecraft:tileentityenergypedestal":
				case "minecraft:tileentitysacrificialaltar":
				case "minecraft:tileentitytieredenergypedestal":
				case "minecraft:tileentitytieredsacrificialaltar":
				case "minecraft:tileentityjzaharspawner":
				case "minecraft:tileentitygatekeeperminionspawner":
				case "minecraft:tileentityenergycollector":
				case "minecraft:tileentityenergyrelay":
				case "minecraft:tileentityenergycontainer":
				case "minecraft:tileentitytieredenergycollector":
				case "minecraft:tileentitytieredenergyrelay":
				case "minecraft:tileentitytieredenergycontainer":
				case "minecraft:tileentityrendingpedestal":
				case "minecraft:tileentitystatetransformer":
				case "minecraft:tileentityenergydepositioner":
					compound.setString("id", id.replace("minecraft", "abyssalcraft"));
					break;
				}

				return compound;
			}

		});
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		File folder = new File("config/abyssalcraft/");
		folder.mkdirs();
		Stack<File> folders = new Stack<File>();
		folders.add(folder);
		while(!folders.isEmpty()){
			File dir = folders.pop();
			File[] listOfFiles = dir.listFiles();
			for(File file : listOfFiles != null ? listOfFiles : new File[0])
				if(file.isFile()){
					JsonObject json = NecroDataJsonUtil.readNecroDataJsonFromFile(file);
					if(json != null){
						NecroData nd = NecroDataJsonUtil.deserializeNecroData(json);
						int book = NecroDataJsonUtil.getInteger(json, "booktype");
						if(nd != null){
							ACLogger.info("Successfully deserialized JSON file for NecroData %s", nd.getIdentifier());
							AbyssalCraftAPI.registerNecronomiconData(nd, book);
						}
					}
				}
		}
	}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}

	static void addOreDictionaryStuff(){

		OreDictionary.registerOre("ingotAbyssalnite", ACItems.abyssalnite_ingot);
		OreDictionary.registerOre("ingotLiquifiedCoralium", ACItems.refined_coralium_ingot);
		OreDictionary.registerOre("gemCoralium", ACItems.coralium_gem);
		OreDictionary.registerOre("oreAbyssalnite", ACBlocks.abyssalnite_ore);
		OreDictionary.registerOre("oreCoralium", ACBlocks.coralium_ore);
		OreDictionary.registerOre("oreCoralium", ACBlocks.abyssal_coralium_ore);
		OreDictionary.registerOre("oreDreadedAbyssalnite", ACBlocks.dreaded_abyssalnite_ore);
		OreDictionary.registerOre("oreAbyssalnite", ACBlocks.dreadlands_abyssalnite_ore);
		OreDictionary.registerOre("oreCoraliumStone", ACBlocks.coralium_infused_stone);
		OreDictionary.registerOre("gemShadow", ACItems.shadow_gem);
		OreDictionary.registerOre("liquidCoralium", ACBlocks.liquid_coralium);
		OreDictionary.registerOre("materialCoraliumPearl", ACItems.coralium_pearl);
		OreDictionary.registerOre("liquidAntimatter", ACBlocks.liquid_antimatter);
		OreDictionary.registerOre("logWood", ACBlocks.darklands_oak_wood);
		OreDictionary.registerOre("logWood", ACBlocks.dreadlands_log);
		OreDictionary.registerOre("plankWood", ACBlocks.darklands_oak_planks);
		OreDictionary.registerOre("plankWood", ACBlocks.dreadlands_planks);
		if(ACConfig.darklands_oak_stairs)
			OreDictionary.registerOre("stairWood", ACBlocks.darklands_oak_stairs);
		OreDictionary.registerOre("treeSapling", ACBlocks.darklands_oak_sapling);
		OreDictionary.registerOre("treeSapling", ACBlocks.dreadlands_sapling);
		OreDictionary.registerOre("treeLeaves", ACBlocks.darklands_oak_leaves);
		OreDictionary.registerOre("treeLeaves", ACBlocks.dreadlands_leaves);
		OreDictionary.registerOre("blockAbyssalnite", new ItemStack(ACBlocks.ingot_block, 1, 0));
		OreDictionary.registerOre("blockLiquifiedCoralium", new ItemStack(ACBlocks.ingot_block, 1, 1));
		OreDictionary.registerOre("blockDreadium", new ItemStack(ACBlocks.ingot_block, 1, 2));
		OreDictionary.registerOre("ingotCoraliumBrick", ACItems.coralium_brick);
		OreDictionary.registerOre("ingotDreadium", ACItems.dreadium_ingot);
		OreDictionary.registerOre("dustSulfur", ACItems.sulfur);
		OreDictionary.registerOre("dustSaltpeter", ACItems.nitre);
		OreDictionary.registerOre("materialMethane", ACItems.methane);
		OreDictionary.registerOre("oreSaltpeter", ACBlocks.nitre_ore);
		OreDictionary.registerOre("crystalIron", new ItemStack(ACItems.crystal, 1, 0));
		OreDictionary.registerOre("crystalGold", new ItemStack(ACItems.crystal, 1, 1));
		OreDictionary.registerOre("crystalSulfur", new ItemStack(ACItems.crystal, 1, 2));
		OreDictionary.registerOre("crystalCarbon", new ItemStack(ACItems.crystal, 1, 3));
		OreDictionary.registerOre("crystalOxygen", new ItemStack(ACItems.crystal, 1, 4));
		OreDictionary.registerOre("crystalHydrogen", new ItemStack(ACItems.crystal, 1, 5));
		OreDictionary.registerOre("crystalNitrogen", new ItemStack(ACItems.crystal, 1, 6));
		OreDictionary.registerOre("crystalPhosphorus", new ItemStack(ACItems.crystal, 1, 7));
		OreDictionary.registerOre("crystalPotassium", new ItemStack(ACItems.crystal, 1, 8));
		OreDictionary.registerOre("crystalNitrate", new ItemStack(ACItems.crystal, 1, 9));
		OreDictionary.registerOre("crystalMethane", new ItemStack(ACItems.crystal, 1, 10));
		OreDictionary.registerOre("crystalRedstone", new ItemStack(ACItems.crystal, 1, 11));
		OreDictionary.registerOre("crystalAbyssalnite", new ItemStack(ACItems.crystal, 1, 12));
		OreDictionary.registerOre("crystalCoralium", new ItemStack(ACItems.crystal, 1, 13));
		OreDictionary.registerOre("crystalDreadium", new ItemStack(ACItems.crystal, 1, 14));
		OreDictionary.registerOre("crystalBlaze", new ItemStack(ACItems.crystal, 1, 15));
		OreDictionary.registerOre("crystalTin", new ItemStack(ACItems.crystal, 1, 16));
		OreDictionary.registerOre("crystalCopper", new ItemStack(ACItems.crystal, 1, 17));
		OreDictionary.registerOre("crystalSilicon", new ItemStack(ACItems.crystal, 1, 18));
		OreDictionary.registerOre("crystalMagnesium", new ItemStack(ACItems.crystal, 1, 19));
		OreDictionary.registerOre("crystalAluminium", new ItemStack(ACItems.crystal, 1, 20));
		OreDictionary.registerOre("crystalSilica", new ItemStack(ACItems.crystal, 1, 21));
		OreDictionary.registerOre("crystalAlumina", new ItemStack(ACItems.crystal, 1, 22));
		OreDictionary.registerOre("crystalMagnesia", new ItemStack(ACItems.crystal, 1, 23));
		OreDictionary.registerOre("crystalZinc", new ItemStack(ACItems.crystal, 1, 24));
		OreDictionary.registerOre("foodFriedEgg", ACItems.fried_egg);
		OreDictionary.registerOre("oreIron", ACBlocks.abyssal_iron_ore);
		OreDictionary.registerOre("oreGold", ACBlocks.abyssal_gold_ore);
		OreDictionary.registerOre("oreDiamond", ACBlocks.abyssal_diamond_ore);
		OreDictionary.registerOre("oreSaltpeter", ACBlocks.abyssal_nitre_ore);
		OreDictionary.registerOre("oreTin", ACBlocks.abyssal_tin_ore);
		OreDictionary.registerOre("oreCopper", ACBlocks.abyssal_copper_ore);
		OreDictionary.registerOre("ingotTin", ACItems.tin_ingot);
		OreDictionary.registerOre("ingotCopper", ACItems.copper_ingot);
		OreDictionary.registerOre("orePearlescentCoralium", ACBlocks.pearlescent_coralium_ore);
		OreDictionary.registerOre("oreLiquifiedCoralium", ACBlocks.liquified_coralium_ore);
		OreDictionary.registerOre("ingotEthaxiumBrick", ACItems.ethaxium_brick);
		OreDictionary.registerOre("ingotEthaxium", ACItems.ethaxium_ingot);
		OreDictionary.registerOre("blockEthaxium", new ItemStack(ACBlocks.ingot_block, 1, 3));
		OreDictionary.registerOre("nuggetAbyssalnite", new ItemStack(ACItems.ingot_nugget, 1, 0));
		OreDictionary.registerOre("nuggetLiquifiedCoralium", new ItemStack(ACItems.ingot_nugget, 1, 1));
		OreDictionary.registerOre("nuggetDreadium", new ItemStack(ACItems.ingot_nugget, 1, 2));
		OreDictionary.registerOre("nuggetEthaxium", new ItemStack(ACItems.ingot_nugget, 1, 3));
		OreDictionary.registerOre("crystalShardIron", new ItemStack(ACItems.crystal_shard, 1, 0));
		OreDictionary.registerOre("crystalShardGold", new ItemStack(ACItems.crystal_shard, 1, 1));
		OreDictionary.registerOre("crystalShardSulfur", new ItemStack(ACItems.crystal_shard, 1, 2));
		OreDictionary.registerOre("crystalShardCarbon", new ItemStack(ACItems.crystal_shard, 1, 3));
		OreDictionary.registerOre("crystalShardOxygen", new ItemStack(ACItems.crystal_shard, 1, 4));
		OreDictionary.registerOre("crystalShardHydrogen", new ItemStack(ACItems.crystal_shard, 1, 5));
		OreDictionary.registerOre("crystalShardNitrogen", new ItemStack(ACItems.crystal_shard, 1, 6));
		OreDictionary.registerOre("crystalShardPhosphorus", new ItemStack(ACItems.crystal_shard, 1, 7));
		OreDictionary.registerOre("crystalShardPotassium", new ItemStack(ACItems.crystal_shard, 1, 8));
		OreDictionary.registerOre("crystalShardNitrate", new ItemStack(ACItems.crystal_shard, 1, 9));
		OreDictionary.registerOre("crystalShardMethane", new ItemStack(ACItems.crystal_shard, 1, 10));
		OreDictionary.registerOre("crystalShardRedstone", new ItemStack(ACItems.crystal_shard, 1, 11));
		OreDictionary.registerOre("crystalShardAbyssalnite", new ItemStack(ACItems.crystal_shard, 1, 12));
		OreDictionary.registerOre("crystalShardCoralium", new ItemStack(ACItems.crystal_shard, 1, 13));
		OreDictionary.registerOre("crystalShardDreadium", new ItemStack(ACItems.crystal_shard, 1, 14));
		OreDictionary.registerOre("crystalShardBlaze", new ItemStack(ACItems.crystal_shard, 1, 15));
		OreDictionary.registerOre("crystalShardTin", new ItemStack(ACItems.crystal_shard, 1, 16));
		OreDictionary.registerOre("crystalShardCopper", new ItemStack(ACItems.crystal_shard, 1, 17));
		OreDictionary.registerOre("crystalShardSilicon", new ItemStack(ACItems.crystal_shard, 1, 18));
		OreDictionary.registerOre("crystalShardMagnesium", new ItemStack(ACItems.crystal_shard, 1, 19));
		OreDictionary.registerOre("crystalShardAluminium", new ItemStack(ACItems.crystal_shard, 1, 20));
		OreDictionary.registerOre("crystalShardSilica", new ItemStack(ACItems.crystal_shard, 1, 21));
		OreDictionary.registerOre("crystalShardAlumina", new ItemStack(ACItems.crystal_shard, 1, 22));
		OreDictionary.registerOre("crystalShardMagnesia", new ItemStack(ACItems.crystal_shard, 1, 23));
		OreDictionary.registerOre("crystalShardZinc", new ItemStack(ACItems.crystal_shard, 1, 24));
		OreDictionary.registerOre("blockGlass", ACBlocks.abyssal_sand_glass);
		OreDictionary.registerOre("crystalFragmentIron", new ItemStack(ACItems.crystal_fragment, 1, 0));
		OreDictionary.registerOre("crystalFragmentGold", new ItemStack(ACItems.crystal_fragment, 1, 1));
		OreDictionary.registerOre("crystalFragmentSulfur", new ItemStack(ACItems.crystal_fragment, 1, 2));
		OreDictionary.registerOre("crystalFragmentCarbon", new ItemStack(ACItems.crystal_fragment, 1, 3));
		OreDictionary.registerOre("crystalFragmentOxygen", new ItemStack(ACItems.crystal_fragment, 1, 4));
		OreDictionary.registerOre("crystalFragmentHydrogen", new ItemStack(ACItems.crystal_fragment, 1, 5));
		OreDictionary.registerOre("crystalFragmentNitrogen", new ItemStack(ACItems.crystal_fragment, 1, 6));
		OreDictionary.registerOre("crystalFragmentPhosphorus", new ItemStack(ACItems.crystal_fragment, 1, 7));
		OreDictionary.registerOre("crystalFragmentPotassium", new ItemStack(ACItems.crystal_fragment, 1, 8));
		OreDictionary.registerOre("crystalFragmentNitrate", new ItemStack(ACItems.crystal_fragment, 1, 9));
		OreDictionary.registerOre("crystalFragmentMethane", new ItemStack(ACItems.crystal_fragment, 1, 10));
		OreDictionary.registerOre("crystalFragmentRedstone", new ItemStack(ACItems.crystal_fragment, 1, 11));
		OreDictionary.registerOre("crystalFragmentAbyssalnite", new ItemStack(ACItems.crystal_fragment, 1, 12));
		OreDictionary.registerOre("crystalFragmentCoralium", new ItemStack(ACItems.crystal_fragment, 1, 13));
		OreDictionary.registerOre("crystalFragmentDreadium", new ItemStack(ACItems.crystal_fragment, 1, 14));
		OreDictionary.registerOre("crystalFragmentBlaze", new ItemStack(ACItems.crystal_fragment, 1, 15));
		OreDictionary.registerOre("crystalFragmentTin", new ItemStack(ACItems.crystal_fragment, 1, 16));
		OreDictionary.registerOre("crystalFragmentCopper", new ItemStack(ACItems.crystal_fragment, 1, 17));
		OreDictionary.registerOre("crystalFragmentSilicon", new ItemStack(ACItems.crystal_fragment, 1, 18));
		OreDictionary.registerOre("crystalFragmentMagnesium", new ItemStack(ACItems.crystal_fragment, 1, 19));
		OreDictionary.registerOre("crystalFragmentAluminium", new ItemStack(ACItems.crystal_fragment, 1, 20));
		OreDictionary.registerOre("crystalFragmentSilica", new ItemStack(ACItems.crystal_fragment, 1, 21));
		OreDictionary.registerOre("crystalFragmentAlumina", new ItemStack(ACItems.crystal_fragment, 1, 22));
		OreDictionary.registerOre("crystalFragmentMagnesia", new ItemStack(ACItems.crystal_fragment, 1, 23));
		OreDictionary.registerOre("crystalFragmentZinc", new ItemStack(ACItems.crystal_fragment, 1, 24));
	}

	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent event){
		if(event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.darkstone_axe, 3, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_axe"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_pickaxe, 3, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_pickaxe"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_shovel, 2, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_shovel"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_sword, 2, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_sword"));
				main.addEntry(new LootEntryItem(Item.getItemFromBlock(ACBlocks.darklands_oak_wood), 10, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":darklands_oak_wood"));
				main.addEntry(new LootEntryItem(ACItems.cobblestone_upgrade_kit, 2, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":cobblestone_upgrade_kit"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.oblivion_catalyst, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":oblivion_catalyst"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_pickaxe, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_pickaxe"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_helmet, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_helmet"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_chestplate, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_chestplate"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_leggings, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_leggings"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_boots, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_boots"));
				main.addEntry(new LootEntryItem(ACItems.cobblestone_upgrade_kit, 10, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":cobblestone_upgrade_kit"));
				main.addEntry(new LootEntryItem(ACItems.iron_upgrade_kit, 7, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":iron_upgrade_kit"));
				main.addEntry(new LootEntryItem(ACItems.gold_upgrade_kit, 4, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":gold_upgrade_kit"));
				main.addEntry(new LootEntryItem(ACItems.diamond_upgrade_kit, 1, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":diamond_upgrade_kit"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.mre, 5, 0 , new LootFunction[0], new LootCondition[0], modid + ":mre"));
				main.addEntry(new LootEntryItem(ACItems.coralium_gem, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":coralium_gem"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event){

		IForgeRegistry<IRecipe> reg = event.getRegistry();

		addShapedRecipe(reg, rl("darkstone_bricks_0"), null, new ItemStack(ACBlocks.darkstone_brick, 4, 0), new Object[] {"AA", "AA", 'A', new ItemStack(ACBlocks.stone, 1, 0) });
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.darkstone_brick, 1, 0), ACBlocks.darkstone_brick_slab, ACBlocks.darkstone_brick_stairs, ACBlocks.darkstone_brick_fence);
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.cobblestone, 1, 0), ACBlocks.darkstone_cobblestone_slab, ACBlocks.darkstone_cobblestone_stairs, ACBlocks.darkstone_cobblestone_wall);
		addStoneStuffRecipes(reg, new ItemStack(ACBlocks.stone, 1, 0), ACBlocks.darkstone_slab, ACBlocks.darkstone_button, ACBlocks.darkstone_pressure_plate);
		if(ACConfig.darkstone_brick_slab)
			addShapedRecipe(reg, rl("darkstone_bricks_1"), null, new ItemStack(ACBlocks.darkstone_brick, 1, 1), new Object[] {"#", "#", '#', new ItemStack(ACBlocks.darkstone_brick_slab)});
		addShapedRecipe(reg, rl("glowing_darkstone_bricks"), null, new ItemStack(ACBlocks.glowing_darkstone_bricks, 4), new Object[] {"#$#", "&%&", "#&#", '#', new ItemStack(ACBlocks.darkstone_brick, 1, 0), '$', Items.DIAMOND,'&', Blocks.OBSIDIAN, '%', Blocks.GLOWSTONE });

		addShapedRecipe(reg, rl("darklands_oak_planks"), null, new ItemStack(ACBlocks.darklands_oak_planks, 4), new Object[] {"A", 'A', ACBlocks.darklands_oak_wood });
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.darklands_oak_planks), ACBlocks.darklands_oak_slab, ACBlocks.darklands_oak_stairs, null);
		addStoneStuffRecipes(reg, new ItemStack(ACBlocks.darklands_oak_planks), null, ACBlocks.darklands_oak_button, ACBlocks.darklands_oak_pressure_plate);
		addShapedOreRecipe(reg, ACBlocks.darklands_oak_fence.getRegistryName(), null, new ItemStack(ACBlocks.darklands_oak_fence, 3), new Object[] {"#%#", "#%#", '#', ACBlocks.darklands_oak_planks, '%', "stickWood"});

		addShapedRecipe(reg, rl("abyssal_stone_bricks_0"), null, new ItemStack(ACBlocks.abyssal_stone_brick, 4, 0), new Object[] {"##", "##", '#', new ItemStack(ACBlocks.stone, 1, 1)});
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.abyssal_stone_brick, 1, 0), ACBlocks.abyssal_stone_brick_slab, ACBlocks.abyssal_stone_brick_stairs, ACBlocks.abyssal_stone_brick_fence);
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.cobblestone, 1, 1), ACBlocks.abyssal_cobblestone_slab, ACBlocks.abyssal_cobblestone_stairs, ACBlocks.abyssal_cobblestone_wall);
		addStoneStuffRecipes(reg, new ItemStack(ACBlocks.stone, 1, 1), null, ACBlocks.abyssal_stone_button, ACBlocks.abyssal_stone_pressure_plate);
		if(ACConfig.abyssal_stone_brick_slab)
			addShapedRecipe(reg, rl("abyssal_stone_bricks_1"), null, new ItemStack(ACBlocks.abyssal_stone_brick, 1, 1), new Object[] {"#", "#", '#', new ItemStack(ACBlocks.abyssal_stone_brick_slab)});

		addShapedRecipe(reg, rl("dreadstone_bricks_0"), null, new ItemStack(ACBlocks.dreadstone_brick, 4, 0), new Object[] {"##", "##", '#', new ItemStack(ACBlocks.stone, 1, 2)});
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.dreadstone_brick, 1, 0), ACBlocks.dreadstone_brick_slab, ACBlocks.dreadstone_brick_stairs, ACBlocks.dreadstone_brick_fence);
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.cobblestone, 1, 2), ACBlocks.dreadstone_cobblestone_slab, ACBlocks.dreadstone_cobblestone_stairs, ACBlocks.dreadstone_cobblestone_wall);
		//stone recipe (if added)
		if(ACConfig.dreadstone_brick_slab)
			addShapedRecipe(reg, rl("dreadstone_bricks_1"), null, new ItemStack(ACBlocks.dreadstone_brick, 1, 1), new Object[] {"#", "#", '#', ACBlocks.dreadstone_brick_slab});

		addShapedRecipe(reg, rl("abyssalnite_stone_bricks_0"), null, new ItemStack(ACBlocks.abyssalnite_stone_brick, 4, 0), new Object[] {"##", "##", '#', new ItemStack(ACBlocks.stone, 1, 3)});
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.abyssalnite_stone_brick, 1, 0), ACBlocks.abyssalnite_stone_brick_slab, ACBlocks.abyssalnite_stone_brick_stairs, ACBlocks.abyssalnite_stone_brick_fence);
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.cobblestone, 1, 3), ACBlocks.abyssalnite_cobblestone_slab, ACBlocks.abyssalnite_cobblestone_stairs, ACBlocks.abyssalnite_cobblestone_wall);
		//stone recipes (if added)
		if(ACConfig.abyssalnite_stone_brick_slab)
			addShapedRecipe(reg, rl("abyssalnite_stone_bricks_1"), null, new ItemStack(ACBlocks.abyssalnite_stone_brick, 1, 1), new Object[] {"#", "#", '#', ACBlocks.abyssalnite_stone_brick_slab});

		addShapedRecipe(reg, rl("dreadlands_planks"), null, new ItemStack(ACBlocks.dreadlands_planks, 4), new Object[] {"%", '%', ACBlocks.dreadlands_log});
		addShapedOreRecipe(reg, ACBlocks.dreadlands_wood_fence.getRegistryName(), null, new ItemStack(ACBlocks.dreadlands_wood_fence, 3), new Object[] {"#%#", "#%#", '#', ACBlocks.dreadlands_planks, '%', "stickWood"});

		addShapedRecipe(reg, rl("coralium_stone_bricks_0"), null, new ItemStack(ACBlocks.coralium_stone_brick, 1, 0), new Object[] {"##", "##", '#', ACItems.coralium_brick});
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.coralium_stone_brick, 1, 0), ACBlocks.coralium_stone_brick_slab, ACBlocks.coralium_stone_brick_stairs, ACBlocks.coralium_stone_brick_fence);
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.cobblestone, 1, 4), ACBlocks.coralium_cobblestone_slab, ACBlocks.coralium_cobblestone_stairs, ACBlocks.coralium_cobblestone_wall);
		addStoneStuffRecipes(reg, new ItemStack(ACBlocks.stone, 1, 4), null, ACBlocks.coralium_stone_button, ACBlocks.coralium_stone_pressure_plate);
		if(ACConfig.coralium_stone_brick_slab)
			addShapedRecipe(reg, rl("coralium_stone_bricks_1"), null, new ItemStack(ACBlocks.coralium_stone_brick, 1, 1), new Object[] {"#", "#", '#', ACBlocks.coralium_stone_brick_slab});

		addShapedRecipe(reg, rl("ethaxium_bricks_0"), null, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), new Object [] {"##", "##", '#', ACItems.ethaxium_brick});
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), ACBlocks.ethaxium_brick_slab, ACBlocks.ethaxium_brick_stairs, ACBlocks.ethaxium_brick_fence);
		if(ACConfig.ethaxium_brick_slab)
			addShapedRecipe(reg, rl("ethaxium_bricks_1"), null, new ItemStack(ACBlocks.ethaxium_brick, 1, 1), new Object[] {"#", "#", '#', ACBlocks.ethaxium_brick_slab});
		addShapedRecipe(reg, rl("ethaxium_pillar"), null, new ItemStack(ACBlocks.ethaxium_pillar, 2), new Object[] {"#%", "#%", '#', new ItemStack(ACBlocks.ethaxium_brick, 1, 0), '%', new ItemStack(ACBlocks.stone, 1, 5)});

		addShapedRecipe(reg, rl("dark_ethaxium_bricks_0"), null, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0), new Object[] {"#%", "#%", '#', new ItemStack(ACBlocks.stone, 1, 6), '%', new ItemStack(ACBlocks.stone, 1, 5)});
		addBlockStuffRecipes(reg, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0), ACBlocks.dark_ethaxium_brick_slab, ACBlocks.dark_ethaxium_brick_stairs, ACBlocks.dark_ethaxium_brick_fence);
		addShapedRecipe(reg, rl("dark_ethaxium_bricks_1"), null, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 1), new Object[] {"#", "#", '#', ACBlocks.dark_ethaxium_brick_slab});
		addShapedRecipe(reg, rl("dark_ethaxium_pillar"), null, new ItemStack(ACBlocks.dark_ethaxium_pillar, 2), new Object[] {"#%", "#%", '#', new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0), '%', new ItemStack(ACBlocks.stone, 1, 6)});

		addShapedNBTRecipe(reg, rl("oblivion_deathbomb_0"), null, new ItemStack(ACBlocks.oblivion_deathbomb), "#%%", "&$%", "£%%", '#', ACItems.liquid_antimatter_bucket_stack, '£', ACItems.liquid_coralium_bucket_stack, '%', Blocks.OBSIDIAN, '&', ACItems.oblivion_catalyst, '$', ACBlocks.odb_core);
		addShapedNBTRecipe(reg, rl("oblivion_deathbomb_1"), null, new ItemStack(ACBlocks.oblivion_deathbomb), "#%%", "&$%", "£%%", '#', ACItems.liquid_coralium_bucket_stack, '£', ACItems.liquid_antimatter_bucket_stack, '%', Blocks.OBSIDIAN, '&', ACItems.oblivion_catalyst, '$', ACBlocks.odb_core);
		addShapedRecipe(reg, rl("block_of_abyssalnite"), null, new ItemStack(ACBlocks.ingot_block, 1, 0), new Object[] {"AAA", "AAA", "AAA", 'A', ACItems.abyssalnite_ingot });
		addShapedRecipe(reg, rl("odb_core"), null, new ItemStack(ACBlocks.odb_core, 1), new Object[] {"#&#", "$@$", "#&#", '#', ACItems.abyssalnite_ingot, '&', Blocks.IRON_BLOCK, '$', Items.IRON_INGOT,'@', ACItems.coralium_pearl});
		addShapedRecipe(reg, rl("coralium_infused_stone_0"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&&&", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_3});
		addShapedRecipe(reg, rl("coralium_infused_stone_1"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem_cluster_4});
		addShapedRecipe(reg, rl("coralium_infused_stone_2"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_4});
		addShapedRecipe(reg, rl("coralium_infused_stone_3"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_4, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem_cluster_2});
		addShapedRecipe(reg, rl("coralium_infused_stone_4"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem_cluster_4, '%', ACItems.coralium_gem_cluster_2});
		addShapedRecipe(reg, rl("coralium_infused_stone_5"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_4, '%', ACItems.coralium_gem_cluster_3});
		addShapedRecipe(reg, rl("coralium_infused_stone_6"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_4, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_3});
		addShapedRecipe(reg, rl("coralium_infused_stone_7"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&&&", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_3});
		addShapedRecipe(reg, rl("coralium_infused_stone_8"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem_cluster_5});
		addShapedRecipe(reg, rl("coralium_infused_stone_9"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_5});
		addShapedRecipe(reg, rl("coralium_infused_stone_10"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem});
		addShapedRecipe(reg, rl("coralium_infused_stone_11"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem});
		addShapedRecipe(reg, rl("coralium_infused_stone_12"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem_cluster_3});
		addShapedRecipe(reg, rl("coralium_infused_stone_13"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_3});
		addShapedRecipe(reg, rl("coralium_infused_stone_14"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_7, '%', ACItems.coralium_gem});
		addShapedRecipe(reg, rl("coralium_infused_stone_15"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_7, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem});
		addShapedRecipe(reg, rl("coralium_infused_stone_16"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_7});
		addShapedRecipe(reg, rl("coralium_infused_stone_17"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_2});
		addShapedRecipe(reg, rl("coralium_infused_stone_18"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem_cluster_2});
		addShapedRecipe(reg, rl("coralium_infused_stone_19"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_5});
		addShapedRecipe(reg, rl("coralium_infused_stone_20"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_6});
		addShapedRecipe(reg, rl("coralium_infused_stone_21"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_6});
		addShapedRecipe(reg, rl("coralium_infused_stone_22"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_2});
		addShapedRecipe(reg, rl("coralium_infused_stone_23"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem_cluster_2});
		addShapedRecipe(reg, rl("coralium_infused_stone_24"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem});
		addShapedRecipe(reg, rl("coralium_infused_stone_25"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.STONE, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem});
		addShapedRecipe(reg, rl("coralium_infused_stone_26"), null, new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "#%#", "###", '#', Blocks.STONE, '%', ACItems.coralium_gem_cluster_9});
		addShapedOreRecipe(reg, rl("wooden_crate"), null, new ItemStack(ACBlocks.wooden_crate, 2), new Object[] {"#&#", "&%&", "#&#", '#', "stickWood", '&', "plankWood", '%', "chestWood"});
		addShapedRecipe(reg, rl("block_of_coralium"), null, new ItemStack(ACBlocks.ingot_block, 1, 1), new Object[] {"###", "###", "###", '#', ACItems.refined_coralium_ingot});
		addShapedRecipe(reg, rl("block_of_dreadium"), null, new ItemStack(ACBlocks.ingot_block, 1, 2), new Object[] {"###", "###", "###", '#', ACItems.dreadium_ingot});
		addShapedNBTRecipe(reg, rl("transmutator"), null, new ItemStack(ACBlocks.transmutator_idle, 1), "###", "#%#", "&$&", '#', ACItems.coralium_brick, '%', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), '&', new ItemStack(ACBlocks.ingot_block, 1, 1), '$', ACItems.liquid_coralium_bucket_stack);
		addShapedRecipe(reg, rl("crystallizer"), null, new ItemStack(ACBlocks.crystallizer_idle, 1), new Object[] {"###", "&%&", "###", '#', new ItemStack(ACBlocks.dreadstone_brick, 1, 0), '&', new ItemStack(ACBlocks.ingot_block, 1, 2), '%', Blocks.FURNACE});
		addShapedRecipe(reg, rl("block_of_ethaxium"), null, new ItemStack(ACBlocks.ingot_block, 1, 3), new Object[] {"###", "###", "###", '#', ACItems.ethaxium_ingot});
		addShapedRecipe(reg, rl("engraver"), null, new ItemStack(ACBlocks.engraver, 1), new Object[] {"#% ", "#%&", "@% ", '#', ACItems.blank_engraving, '%', Blocks.STONE, '&', Blocks.LEVER, '@', Blocks.ANVIL});
		addShapedNBTRecipe(reg, rl("materializer"), null, new ItemStack(ACBlocks.materializer), "###", "#%#", "&$&", '#', ACItems.ethaxium_brick, '%', Blocks.OBSIDIAN, '&', new ItemStack(ACBlocks.ingot_block, 1, 3), '$', ACItems.liquid_antimatter_bucket_stack);
		addShapedRecipe(reg, rl("energy_pedestal"), null, new ItemStack(ACBlocks.energy_pedestal), new Object[]{"#%#", "#&#", "###", '#', new ItemStack(ACBlocks.stone, 1, 7), '%', ACItems.coralium_pearl, '&', ACItems.shadow_gem});
		addShapedRecipe(reg, rl("monolith_stone_pillar"), null, new ItemStack(ACBlocks.monolith_pillar), new Object[]{"##", "##", '#', new ItemStack(ACBlocks.stone, 1, 7)});
		addShapedRecipe(reg, rl("sacrificial_altar"), null, new ItemStack(ACBlocks.sacrificial_altar), new Object[]{"#%#", "&$&", "&&&", '#', ACItems.shadow_fragment, '%', ACItems.coralium_pearl, '$', ACItems.shadow_gem, '&', new ItemStack(ACBlocks.stone, 1, 7)});
		addShapelessRecipe(reg, rl("decorative_cthulhu_statue"), null, new ItemStack(ACBlocks.decorative_statue, 1, 0), new ItemStack(ACBlocks.stone, 1, 7), Items.CLAY_BALL, new ItemStack(Items.DYE, 1, 6));
		addShapelessRecipe(reg, rl("decorative_hastur_statue"), null, new ItemStack(ACBlocks.decorative_statue, 1, 1), new ItemStack(ACBlocks.stone, 1, 7), Items.CLAY_BALL, new ItemStack(Items.DYE, 1, 11));
		addShapelessRecipe(reg, rl("decorative_jzahar_statue"), null, new ItemStack(ACBlocks.decorative_statue, 1, 2), new ItemStack(ACBlocks.stone, 1, 7), Items.CLAY_BALL, new ItemStack(Items.DYE, 1, 8));
		addShapelessRecipe(reg, rl("decorative_azathoth_statue"), null, new ItemStack(ACBlocks.decorative_statue, 1, 3), new ItemStack(ACBlocks.stone, 1, 7), Items.CLAY_BALL, new ItemStack(Items.DYE, 1, 5));
		addShapelessRecipe(reg, rl("decorative_nyarlathotep_statue"), null, new ItemStack(ACBlocks.decorative_statue, 1, 4), new ItemStack(ACBlocks.stone, 1, 7), Items.CLAY_BALL, new ItemStack(Items.DYE, 1, 4));
		addShapelessRecipe(reg, rl("decorative_yog_sothoth_statue"), null, new ItemStack(ACBlocks.decorative_statue, 1, 5), new ItemStack(ACBlocks.stone, 1, 7), Items.CLAY_BALL, Items.GLOWSTONE_DUST);
		addShapelessRecipe(reg, rl("decorative_shub_niggurath_statue"), null, new ItemStack(ACBlocks.decorative_statue, 1, 6), new ItemStack(ACBlocks.stone, 1, 7), Items.CLAY_BALL, new ItemStack(Items.DYE, 1, 0));
		addShapedRecipe(reg, rl("energy_collector"), null, new ItemStack(ACBlocks.energy_collector), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACBlocks.stone, 1, 7), '%', ACItems.shadow_gem});
		addShapedRecipe(reg, rl("energy_relay"), null, new ItemStack(ACBlocks.energy_relay), new Object[] {"##%", "#&#", "##%", '#', new ItemStack(ACBlocks.stone, 1, 7), '%', ACItems.shadow_shard, '&', ACItems.shadow_gem});
		addShapedRecipe(reg, rl("rending_pedestal"), null, new ItemStack(ACBlocks.rending_pedestal), new Object[] {"#%#", "#&#", "###", '#', new ItemStack(ACBlocks.stone, 1, 7), '%', ACItems.shard_of_oblivion, '&', ACItems.shadow_gem});
		addShapedOreRecipe(reg, rl("state_transformer"), null, new ItemStack(ACBlocks.state_transformer), "###", "#&#", "#@#", '#', new ItemStack(ACBlocks.stone, 1, 7), '&', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), '@', "chestWood");
		addShapedRecipe(reg, rl("energy_depositioner"), null, new ItemStack(ACBlocks.energy_depositioner), "###", "%&%", "#@#", '#', new ItemStack(ACBlocks.stone, 1, 7), '%', ACItems.shadow_gem, '&', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), '@', ACBlocks.energy_relay);

		addShapedOreRecipe(reg, rl("darkstone_pickaxe"), null, new ItemStack(ACItems.darkstone_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', new ItemStack(ACBlocks.cobblestone, 1, 0), '%', "stickWood"});
		addShapedOreRecipe(reg, rl("darkstone_axe"), null, new ItemStack(ACItems.darkstone_axe, 1), new Object[] {"##", "#%", " %", '#', new ItemStack(ACBlocks.cobblestone, 1, 0), '%', "stickWood"});
		addShapedOreRecipe(reg, rl("darkstone_shovel"), null, new ItemStack(ACItems.darkstone_shovel, 1), new Object[] {"#", "%", "%", '#', new ItemStack(ACBlocks.cobblestone, 1, 0), '%', "stickWood"});
		addShapedOreRecipe(reg, rl("darkstone_sword"), null, new ItemStack(ACItems.darkstone_sword, 1), new Object[] {"#", "#", "%", '#', new ItemStack(ACBlocks.cobblestone, 1, 0), '%', "stickWood"});
		addShapedOreRecipe(reg, rl("darkstone_hoe"), null, new ItemStack(ACItems.darkstone_hoe, 1), new Object[] {"##", " %", " %", '#', new ItemStack(ACBlocks.cobblestone, 1, 0), '%', "stickWood"});
		addShapedOreRecipe(reg, rl("abyssalnite_pickaxe"), null, new ItemStack(ACItems.abyssalnite_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.abyssalnite_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("abyssalnite_axe"), null, new ItemStack(ACItems.abyssalnite_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.abyssalnite_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("abyssalnite_shovel"), null, new ItemStack(ACItems.abyssalnite_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.abyssalnite_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("abyssalnite_sword"), null, new ItemStack(ACItems.abyssalnite_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.abyssalnite_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("abyssalnite_hoe"), null, new ItemStack(ACItems.abyssalnite_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.abyssalnite_ingot, '%', "stickWood"});
		addShapedRecipe(reg, rl("gateway_key"), null, new ItemStack(ACItems.gateway_key, 1), new Object[] {" #%", " &#", "&  ", '#', ACItems.coralium_pearl, '%', ACItems.oblivion_catalyst, '&', Items.BLAZE_ROD});
		addShapedRecipe(reg, rl("abyssalnite_ingot"), null, new ItemStack(ACItems.abyssalnite_ingot, 9), new Object[] {"#", '#', new ItemStack(ACBlocks.ingot_block, 1, 0)});
		addShapedRecipe(reg, rl("coralium_plate"), null, new ItemStack(ACItems.coralium_plate, 1), new Object[] {"#%#", "#%#", "#%#", '#', ACItems.refined_coralium_ingot, '%', ACItems.coralium_pearl});
		addShapedRecipe(reg, rl("chunk_of_coralium"), null, new ItemStack(ACItems.chunk_of_coralium, 1), new Object[] {"###", "#%#", "###", '#', ACItems.coralium_gem_cluster_9, '%', new ItemStack(ACBlocks.stone, 1, 1)});
		addShapedRecipe(reg, rl("powerstone_tracker"), null, new ItemStack(ACItems.powerstone_tracker, 4), new Object[] {"###", "#%#", "###", '#', ACItems.coralium_gem, '%', Items.ENDER_EYE});
		addShapedRecipe(reg, rl("refined_coralium_ingot"), null, new ItemStack(ACItems.refined_coralium_ingot, 9), new Object[] {"#", '#', new ItemStack(ACBlocks.ingot_block, 1, 1)});
		addShapedOreRecipe(reg, rl("refined_coralium_pickaxe"), null, new ItemStack(ACItems.refined_coralium_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.refined_coralium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("refined_coralium_axe"), null, new ItemStack(ACItems.refined_coralium_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.refined_coralium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("refined_coralium_shovel"), null, new ItemStack(ACItems.refined_coralium_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.refined_coralium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("refined_coralium_sword"), null, new ItemStack(ACItems.refined_coralium_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.refined_coralium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("refined_coralium_hoe"), null, new ItemStack(ACItems.refined_coralium_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.refined_coralium_ingot, '%', "stickWood"});
		addShapedRecipe(reg, rl("shadow_shard"), null, new ItemStack(ACItems.shadow_shard, 1), new Object[] {"###", "###", "###", '#', ACItems.shadow_fragment});
		addShapedRecipe(reg, rl("shadow_gem"), null, new ItemStack(ACItems.shadow_gem, 1), new Object[] {"###", "###", "###", '#', ACItems.shadow_shard});
		addShapedRecipe(reg, rl("shard_of_oblivion"), null, new ItemStack(ACItems.shard_of_oblivion, 1), new Object[] {" # ", "#%#", " # ", '#', ACItems.shadow_gem, '%', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE)});
		addShapedRecipe(reg, rl("coralium_longbow"), null, new ItemStack(ACItems.coralium_longbow, 1), new Object[] {" #%", "&$%", " #%", '#', ACItems.refined_coralium_ingot, '%', Items.STRING, '&', ACItems.coralium_pearl, '$', new ItemStack(Items.BOW, 1, OreDictionary.WILDCARD_VALUE)});
		addShapedRecipe(reg, rl("dreadium_ingot"), null, new ItemStack(ACItems.dreadium_ingot, 9), new Object[] {"#", '#', new ItemStack(ACBlocks.ingot_block, 1, 2)});
		addShapedOreRecipe(reg, rl("dreadium_pickaxe"), null, new ItemStack(ACItems.dreadium_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.dreadium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("dreadium_axe"), null, new ItemStack(ACItems.dreadium_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.dreadium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("dreadium_shovel"), null, new ItemStack(ACItems.dreadium_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.dreadium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("dreadium_sword"), null, new ItemStack(ACItems.dreadium_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.dreadium_ingot, '%', "stickWood"});
		addShapedOreRecipe(reg, rl("dreadium_hoe"), null, new ItemStack(ACItems.dreadium_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.dreadium_ingot, '%', "stickWood"});
		addShapedRecipe(reg, rl("carbon_cluster"), null, new ItemStack(ACItems.carbon_cluster, 1), new Object[] {"###", "# #", "###", '#', new ItemStack(ACItems.crystal, 1, 3)});
		addShapedRecipe(reg, rl("dense_carbon_cluster"), null, new ItemStack(ACItems.dense_carbon_cluster, 1), new Object[] {"###", "#%#", "###", '#', ACItems.carbon_cluster, '%', Blocks.OBSIDIAN});
		addShapedRecipe(reg, rl("dread_cloth_0"), null, new ItemStack(ACItems.dread_cloth, 1), new Object[] {"#%#", "%&%", "#%#", '#', Items.STRING, '%', ACItems.dread_fragment, '&', Items.LEATHER});
		addShapedRecipe(reg, rl("dread_cloth_1"), null, new ItemStack(ACItems.dread_cloth, 1), new Object[] {"#%#", "%&%", "#%#", '%', Items.STRING, '#', ACItems.dread_fragment, '&', Items.LEATHER});
		addShapedRecipe(reg, rl("dreadium_plate"), null, new ItemStack(ACItems.dreadium_plate, 1), new Object[] {"###", "#%#", "###", '#', ACItems.dreadium_ingot, '%', ACItems.dread_cloth});
		addShapedRecipe(reg, rl("dreadium_katana_blade"), null, new ItemStack(ACItems.dreadium_katana_blade, 1), new Object[] {"## ", "## ", "## ", '#', new ItemStack(ACItems.crystal, 1, 14)});
		addShapedRecipe(reg, rl("dreadium_katana_hilt"), null, new ItemStack(ACItems.dreadium_katana_hilt, 1), new Object[] {"###", "%&%", "%&%", '#', ACItems.dreadium_ingot, '%', ACItems.dread_cloth, '&', ACBlocks.dreadlands_planks});
		addShapedRecipe(reg, rl("dreadium_katana"), null, new ItemStack(ACItems.dreadium_katana, 1), new Object[] {"# ", "% ", '#', ACItems.dreadium_katana_blade, '%', ACItems.dreadium_katana_hilt});
		addShapedOreRecipe(reg, rl("gunpowder"), null, new ItemStack(Items.GUNPOWDER, 4), true, new Object[] {"#&#", "#%#", "###", '#', "dustSaltpeter", '%', new ItemStack(Items.COAL, 1, 1), '&', "dustSulfur"});
		addShapedRecipe(reg, rl("crystallized_methane"), null, new ItemStack(ACItems.crystal, 1, 10), new Object[] {" # ", "#%#", " # ", '#', new ItemStack(ACItems.crystal, 1, 5), '%', new ItemStack(ACItems.crystal, 1, 3)});
		addShapedRecipe(reg, rl("crystallized_nitrate"), null, new ItemStack(ACItems.crystal, 1, 9), new Object[] {" # ", "%%%", '#', new ItemStack(ACItems.crystal, 1, 6), '%', new ItemStack(ACItems.crystal, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_alumina"), null, new ItemStack(ACItems.crystal, 1, 22), new Object[] {" # ", "%%%", " # ", '#', new ItemStack(ACItems.crystal, 1, 20), '%', new ItemStack(ACItems.crystal, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_silica"), null, new ItemStack(ACItems.crystal, 1, 21), new Object[] {"#%#", '#', new ItemStack(ACItems.crystal, 1, 4), '%', new ItemStack(ACItems.crystal, 1, 18)});
		addShapedRecipe(reg, rl("crystallized_magnesia"), null, new ItemStack(ACItems.crystal, 1, 23), new Object[] {"#%", '#', new ItemStack(ACItems.crystal, 1, 19), '%', new ItemStack(ACItems.crystal, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_methane_shard"), null, new ItemStack(ACItems.crystal_shard, 1, 10), new Object[] {" # ", "#%#", " # ", '#', new ItemStack(ACItems.crystal_shard, 1, 5), '%', new ItemStack(ACItems.crystal_shard, 1, 3)});
		addShapedRecipe(reg, rl("crystallized_nitrate_shard"), null, new ItemStack(ACItems.crystal_shard, 1, 9), new Object[] {" # ", "%%%", '#', new ItemStack(ACItems.crystal_shard, 1, 6), '%', new ItemStack(ACItems.crystal_shard, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_alumina_shard"), null, new ItemStack(ACItems.crystal_shard, 1, 22), new Object[] {" # ", "%%%", " # ", '#', new ItemStack(ACItems.crystal_shard, 1, 20), '%', new ItemStack(ACItems.crystal_shard, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_silica_shard"), null, new ItemStack(ACItems.crystal_shard, 1, 21), new Object[] {"#%#", '#', new ItemStack(ACItems.crystal_shard, 1, 4), '%', new ItemStack(ACItems.crystal_shard, 1, 18)});
		addShapedRecipe(reg, rl("crystallized_magnesia_shard"), null, new ItemStack(ACItems.crystal_shard, 1, 23), new Object[] {"#%", '#', new ItemStack(ACItems.crystal_shard, 1, 19), '%', new ItemStack(ACItems.crystal_shard, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_methane_fragment"), null, new ItemStack(ACItems.crystal_fragment, 1, 10), new Object[] {" # ", "#%#", " # ", '#', new ItemStack(ACItems.crystal_fragment, 1, 5), '%', new ItemStack(ACItems.crystal_fragment, 1, 3)});
		addShapedRecipe(reg, rl("crystallized_nitrate_fragment"), null, new ItemStack(ACItems.crystal_fragment, 1, 9), new Object[] {" # ", "%%%", '#', new ItemStack(ACItems.crystal_fragment, 1, 6), '%', new ItemStack(ACItems.crystal_fragment, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_alumina_shard"), null, new ItemStack(ACItems.crystal_fragment, 1, 22), new Object[] {" # ", "%%%", " # ", '#', new ItemStack(ACItems.crystal_fragment, 1, 20), '%', new ItemStack(ACItems.crystal_fragment, 1, 4)});
		addShapedRecipe(reg, rl("crystallized_silica_shard"), null, new ItemStack(ACItems.crystal_fragment, 1, 21), new Object[] {"#%#", '#', new ItemStack(ACItems.crystal_fragment, 1, 4), '%', new ItemStack(ACItems.crystal_fragment, 1, 18)});
		addShapedRecipe(reg, rl("crystallized_magnesia_shard"), null, new ItemStack(ACItems.crystal_fragment, 1, 23), new Object[] {"#%", '#', new ItemStack(ACItems.crystal_fragment, 1, 19), '%', new ItemStack(ACItems.crystal_fragment, 1, 4)});
		addShapedRecipe(reg, rl("ethaxium_ingot_0"), null, new ItemStack(ACItems.ethaxium_ingot), new Object[] {"###", "#%#", "###", '#', ACItems.ethaxium_brick, '%', ACItems.life_crystal});
		addShapedRecipe(reg, rl("ethaxium_ingot_1"), null, new ItemStack(ACItems.ethaxium_ingot), new Object[] {" # ", "#%#", " # ", '#', ACItems.ethaxium_brick, '%', ACItems.oblivion_catalyst});
		ItemStack egg = new ItemStack(Items.SPAWN_EGG);
		NBTTagCompound tag = egg.hasTagCompound() ? egg.getTagCompound() : new NBTTagCompound();
		NBTTagCompound tag1 = new NBTTagCompound();
		tag1.setString("id", "abyssalcraft.shadowboss");
		tag.setTag("EntityTag", tag1);
		egg.setTagCompound(tag);
		addShapedRecipe(reg, rl("spawn_egg"), null, egg, new Object[] {"#", '#', ACBlocks.oblivion_deathbomb});
		addShapedRecipe(reg, rl("ethaxium_ingot"), null, new ItemStack(ACItems.ethaxium_ingot, 9), new Object[] {"#", '#', new ItemStack(ACBlocks.ingot_block, 1, 3)});
		addShapedRecipe(reg, rl("ethaxium_pickaxe"), null, new ItemStack(ACItems.ethaxium_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		addShapedRecipe(reg, rl("ethaxium_axe"), null, new ItemStack(ACItems.ethaxium_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		addShapedRecipe(reg, rl("ethaxium_shovel"), null, new ItemStack(ACItems.ethaxium_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		addShapedRecipe(reg, rl("ethaxium_sword"), null, new ItemStack(ACItems.ethaxium_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		addShapedRecipe(reg, rl("ethaxium_hoe"), null, new ItemStack(ACItems.ethaxium_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		addShapedOreRecipe(reg, rl("coin_0"), null, new ItemStack(ACItems.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotCopper", '%', Items.FLINT});
		addShapedOreRecipe(reg, rl("coin_1"), null, new ItemStack(ACItems.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotIron", '%', Items.FLINT});
		addShapedOreRecipe(reg, rl("coin_2"), null, new ItemStack(ACItems.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotTin", '%', Items.FLINT});
		addShapedRecipe(reg, rl("blank_engraving"), null, new ItemStack(ACItems.blank_engraving, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(Blocks.STONE_SLAB, 1, 0), '%', Items.IRON_INGOT});
		addShapedRecipe(reg, rl("elder_engraving"), null, new ItemStack(ACItems.elder_engraving, 1), new Object[] {"#", "%", '#', ACItems.blank_engraving, '%', ACItems.ethaxium_ingot});
		addShapedRecipe(reg, rl("cthulhu_engraving"), null, new ItemStack(ACItems.cthulhu_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 0)});
		addShapedRecipe(reg, rl("hastur_engraving"), null, new ItemStack(ACItems.hastur_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 1)});
		addShapedRecipe(reg, rl("jzahar_engraving"), null, new ItemStack(ACItems.jzahar_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', ACItems.eldritch_scale});
		addShapelessRecipe(reg, rl("azathoth_engraving"), null, new ItemStack(ACItems.azathoth_engraving, 1), new ItemStack(ACItems.shoggoth_flesh, 1, 0), ACItems.elder_engraving, new ItemStack(ACItems.shoggoth_flesh, 1, 1),
				new ItemStack(ACItems.shoggoth_flesh, 1, 2), new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.shoggoth_flesh, 1, 4));
		addShapedRecipe(reg, rl("nyarlathotep_engraving"), null, new ItemStack(ACItems.nyarlathotep_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 2)});
		addShapedRecipe(reg, rl("yog_sothoth_engraving"), null, new ItemStack(ACItems.yog_sothoth_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 3)});
		addShapedRecipe(reg, rl("shub_niggurath_engraving"), null, new ItemStack(ACItems.shub_niggurath_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 4)});
		addShapedRecipe(reg, rl("necronomicon"), null, new ItemStack(ACItems.necronomicon, 1), new Object[] {"##%", "#&#", "##%", '#', Items.ROTTEN_FLESH, '%', Items.IRON_INGOT, '&', Items.BOOK});
		addShapedRecipe(reg, rl("abyssal_wasteland_necronomicon"), null, new ItemStack(ACItems.abyssal_wasteland_necronomicon, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 0), '%', ACItems.necronomicon});
		addShapedRecipe(reg, rl("dreadlands_necronomicon"), null, new ItemStack(ACItems.dreadlands_necronomicon, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 1), '%', ACItems.abyssal_wasteland_necronomicon});
		addShapedRecipe(reg, rl("omothol_necronomicon"), null, new ItemStack(ACItems.omothol_necronomicon, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 2), '%', ACItems.dreadlands_necronomicon});
		addShapedRecipe(reg, rl("abyssalnomicon"), null, new ItemStack(ACItems.abyssalnomicon, 1), new Object[] {"#$#", "%&%", "#%#", '#', ACItems.ethaxium_ingot, '%', ACItems.eldritch_scale, '&', ACItems.omothol_necronomicon, '$', ACItems.essence_of_the_gatekeeper});
		addShapedRecipe(reg, rl("small_crystal_bag"), null, new ItemStack(ACItems.small_crystal_bag, 1), new Object[] {"#%#", "%&%", "%%%", '#', Items.STRING, '%', Items.LEATHER, '&', Items.GOLD_INGOT});
		addShapedRecipe(reg, rl("medium_crystal_bag"), null, new ItemStack(ACItems.medium_crystal_bag, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 0), '%', ACItems.small_crystal_bag});
		addShapedRecipe(reg, rl("large_crystal_bag"), null, new ItemStack(ACItems.large_crystal_bag, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 1), '%', ACItems.medium_crystal_bag});
		addShapedRecipe(reg, rl("huge_crystal_bag"), null, new ItemStack(ACItems.huge_crystal_bag, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 2), '%', ACItems.large_crystal_bag});
		addShapedRecipe(reg, rl("abyssalnite_nugget"), null, new ItemStack(ACItems.ingot_nugget, 9, 0), new Object[] {"#", '#', ACItems.abyssalnite_ingot});
		addShapedRecipe(reg, rl("refined_coralium_nugget"), null, new ItemStack(ACItems.ingot_nugget, 9, 1), new Object[] {"#", '#', ACItems.refined_coralium_ingot});
		addShapedRecipe(reg, rl("dreadium_nugget"), null, new ItemStack(ACItems.ingot_nugget, 9, 2), new Object[] {"#", '#', ACItems.dreadium_ingot});
		addShapedRecipe(reg, rl("ethaxium_ingot"), null, new ItemStack(ACItems.ingot_nugget, 9, 3), new Object[] {"#", '#', ACItems.ethaxium_ingot});
		addShapedRecipe(reg, rl("abyssalnite_ingot_nugget"), null, new ItemStack(ACItems.abyssalnite_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 0)});
		addShapedRecipe(reg, rl("refined_coralium_ingot_nugget"), null, new ItemStack(ACItems.refined_coralium_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 1)});
		addShapedRecipe(reg, rl("dreadium_ingot_nugget"), null, new ItemStack(ACItems.dreadium_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 2)});
		addShapedRecipe(reg, rl("ethaxium_ingot_nugget"), null, new ItemStack(ACItems.ethaxium_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 3)});
		for(int i = 0; i < EnumCrystalType.values().length; i++){
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType.byMetadata(i) + "_cluster"), null, new ItemStack(ACBlocks.crystal_cluster, 1, i), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal, 1, i)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType.byMetadata(i) + "_0"), null, new ItemStack(ACItems.crystal, 9, i), new Object[] {"#", '#', new ItemStack(ACBlocks.crystal_cluster, 1, i)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType.byMetadata(i) + "_1"), null, new ItemStack(ACItems.crystal, 1, i), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, i)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType.byMetadata(i) + "_shard_0"), null, new ItemStack(ACItems.crystal_shard, 9, i), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, i)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType.byMetadata(i) + "_shard_1"), null, new ItemStack(ACItems.crystal_shard, 1, i), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_fragment, 1, i)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType.byMetadata(i) + "_fragment_0"), null, new ItemStack(ACItems.crystal_fragment, 9, i), new Object[] {"#", '#', new ItemStack(ACItems.crystal_shard, 1, i)});
		}
		for(int i = 0; i < EnumCrystalType2.values().length; i++){
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType2.byMetadata(i) + "_cluster"), null, new ItemStack(ACBlocks.crystal_cluster2, 1, i), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal, 1, i+16)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType2.byMetadata(i) + "_0"), null, new ItemStack(ACItems.crystal, 9, i+16), new Object[] {"#", '#', new ItemStack(ACBlocks.crystal_cluster2, 1, i)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType2.byMetadata(i) + "_1"), null, new ItemStack(ACItems.crystal, 1, i+16), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, i+16)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType2.byMetadata(i) + "_shard_0"), null, new ItemStack(ACItems.crystal_shard, 9, i+16), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, i+16)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType2.byMetadata(i) + "_shard_1"), null, new ItemStack(ACItems.crystal_shard, 1, i+16), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_fragment, 1, i+16)});
			addShapedRecipe(reg, rl("crystallized_"+ EnumCrystalType2.byMetadata(i) + "_fragment_0"), null, new ItemStack(ACItems.crystal_fragment, 9, i+16), new Object[] {"#", '#', new ItemStack(ACItems.crystal_shard, 1, i+16)});
		}
		addShapedRecipe(reg, rl("skin_of_the_abyssal_wasteland"), null, new ItemStack(ACItems.skin, 1, 0), new Object[] {"###", "#%#", "###", '#', ACItems.coralium_plagued_flesh, '%', new ItemStack(ACItems.essence, 1, 0)});
		addShapedRecipe(reg, rl("skin_of_the_dreadlands"), null, new ItemStack(ACItems.skin, 1, 1), new Object[] {"###", "#%#", "###", '#', ACItems.dread_fragment, '%', new ItemStack(ACItems.essence, 1, 1)});
		addShapedRecipe(reg, rl("skin_of_omothol"), null, new ItemStack(ACItems.skin, 1, 2), new Object[] {"###", "#%#", "###", '#', ACItems.omothol_flesh, '%', new ItemStack(ACItems.essence, 1, 2)});
		addShapedRecipe(reg, rl("staff_of_rending"), null, new ItemStack(ACItems.staff_of_rending, 1, 0), new Object[] {" #%", " ##", "#  ", '#', ACItems.shadow_shard, '%', ACItems.shard_of_oblivion});
		addShapedRecipe(reg, rl("shadow_shard_0"), null, new ItemStack(ACItems.shadow_shard, 9), new Object[] {"#", '#', ACItems.shadow_gem});
		addShapedRecipe(reg, rl("shadow_fragment_0"), null, new ItemStack(ACItems.shadow_fragment, 9), new Object[] {"#", '#', ACItems.shadow_shard});
		addShapedRecipe(reg, rl("ritual_charm"), null, new ItemStack(ACItems.ritual_charm, 1, 0), new Object[] {"###", "#%#", "###", '#', Items.GOLD_INGOT, '%', Items.DIAMOND});
		addShapedRecipe(reg, rl("stone_tablet"), null, new ItemStack(ACItems.stone_tablet), "#%#", "%&%", "#%#", '#', ACItems.shadow_shard, '%', new ItemStack(ACBlocks.stone, 1, 7), '&', ACItems.shadow_gem);

		addShapelessRecipe(reg, rl("life_crystal"), null, new ItemStack(ACItems.life_crystal), new ItemStack(ACItems.crystal, 1, 3), new ItemStack(ACItems.crystal, 1, 5), new ItemStack(ACItems.crystal, 1, 6),
				new ItemStack(ACItems.crystal, 1, 4), new ItemStack(ACItems.crystal, 1, 7), new ItemStack(ACItems.crystal, 1, 2));

		//Coralium Gem Cluster Recipes
		addShapelessRecipe(reg, rl("coralium_gem_cluster_0"), null, new ItemStack(ACItems.coralium_gem_cluster_2, 1),ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_1"), null, new ItemStack(ACItems.coralium_gem_cluster_3, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_2"), null, new ItemStack(ACItems.coralium_gem_cluster_4, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_3"), null, new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_4"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_5"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_6"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_7"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_8"), null, new ItemStack(ACItems.coralium_gem_cluster_3, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_9"), null, new ItemStack(ACItems.coralium_gem_cluster_4, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_10"), null, new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_11"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_12"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_13"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_14"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_15"), null, new ItemStack(ACItems.coralium_gem_cluster_4, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_16"), null, new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_17"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_18"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_19"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_20"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_21"), null, new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_22"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_23"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_24"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_25"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_26"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_27"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_28"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_29"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_30"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_6, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_31"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_6, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_32"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_6, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_33"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_7, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_34"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_7, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_35"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_8, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_36"), null, new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_37"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_38"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_39"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_40"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_41"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_42"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_43"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_44"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_45"), null, new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_46"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_47"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_48"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_49"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_50"), null, new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_51"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_52"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_53"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_54"), null, new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_5);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_55"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_5, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_56"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_57"), null, new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_6);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_58"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_6, ACItems.coralium_gem);
		addShapelessRecipe(reg, rl("coralium_gem_cluster_59"), null, new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_7);
		addShapedRecipe(reg, rl("coralium_gem_0"), null, new ItemStack(ACItems.coralium_gem, 2), new Object[] {"#", '#', ACItems.coralium_gem_cluster_2});
		addShapedRecipe(reg, rl("coralium_gem_1"), null, new ItemStack(ACItems.coralium_gem, 3), new Object[] {"#", '#', ACItems.coralium_gem_cluster_3});
		addShapedRecipe(reg, rl("coralium_gem_2"), null, new ItemStack(ACItems.coralium_gem, 4), new Object[] {"#", '#', ACItems.coralium_gem_cluster_4});
		addShapedRecipe(reg, rl("coralium_gem_3"), null, new ItemStack(ACItems.coralium_gem, 5), new Object[] {"#", '#', ACItems.coralium_gem_cluster_5});
		addShapedRecipe(reg, rl("coralium_gem_4"), null, new ItemStack(ACItems.coralium_gem, 6), new Object[] {"#", '#', ACItems.coralium_gem_cluster_6});
		addShapedRecipe(reg, rl("coralium_gem_5"), null, new ItemStack(ACItems.coralium_gem, 7), new Object[] {"#", '#', ACItems.coralium_gem_cluster_7});
		addShapedRecipe(reg, rl("coralium_gem_6"), null, new ItemStack(ACItems.coralium_gem, 8), new Object[] {"#", '#', ACItems.coralium_gem_cluster_8});
		addShapedRecipe(reg, rl("coralium_gem_7"), null, new ItemStack(ACItems.coralium_gem, 9), new Object[] {"#", '#', ACItems.coralium_gem_cluster_9});

		addArmor(ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots, ACItems.abyssalnite_ingot, new ItemStack(ACItems.ingot_nugget, 1, 0), ACItems.abyssalnite_upgrade_kit, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
		addArmor(ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots, ACItems.refined_coralium_ingot, new ItemStack(ACItems.ingot_nugget, 1, 1), ACItems.coralium_upgrade_kit, ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots);
		addShapedRecipe(reg, rl("plated_coralium_boots"), null, new ItemStack(ACItems.plated_coralium_boots, 1), new Object[] {"# #", "%&%", '#', ACItems.refined_coralium_ingot, '%', ACItems.coralium_plate, '&', ACItems.refined_coralium_boots});
		addShapedRecipe(reg, rl("plated_coralium_helmet"), null, new ItemStack(ACItems.plated_coralium_helmet, 1), new Object[] {"&#&", "#@#", "%%%", '#', ACItems.coralium_plate, '&', ACItems.coralium_pearl, '@', ACItems.refined_coralium_helmet, '%', ACItems.refined_coralium_ingot});
		addShapedRecipe(reg, rl("plated_coralium_chestplate"), null, new ItemStack(ACItems.plated_coralium_chestplate, 1), new Object[] {"# #", "%@%", "%#%",'#', ACItems.coralium_plate, '%', ACItems.refined_coralium_ingot, '@', ACItems.refined_coralium_chestplate});
		addShapedRecipe(reg, rl("plated_coralium_leggings"), null, new ItemStack(ACItems.plated_coralium_leggings, 1), new Object[] {"%&%", "# #", "# #",'#', ACItems.refined_coralium_ingot, '%', ACItems.coralium_plate, '&', ACItems.refined_coralium_leggings});
		addArmor(ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots, ACItems.dreadium_ingot, new ItemStack(ACItems.ingot_nugget, 1, 2), ACItems.dreadium_upgrade_kit, ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots);
		addShapedRecipe(reg, rl("dreadium_samurai_boots"), null, new ItemStack(ACItems.dreadium_samurai_boots, 1), new Object[] {"#%#", "&&&", '#', ACItems.dread_cloth, '%', new ItemStack(ACItems.dreadium_boots, 1, OreDictionary.WILDCARD_VALUE), '&', ACBlocks.dreadlands_planks});
		addShapedRecipe(reg, rl("dreadium_samurai_helmet"), null, new ItemStack(ACItems.dreadium_samurai_helmet, 1), new Object[] {" # ", "%&%", '#', ACItems.dreadium_ingot, '%', ACItems.dreadium_plate, '&', new ItemStack(ACItems.dreadium_helmet, 1, OreDictionary.WILDCARD_VALUE)});
		addShapedRecipe(reg, rl("dreadium_samurai_chestplate"), null, new ItemStack(ACItems.dreadium_samurai_chestplate, 1), new Object[] {"#%#", "#&#", "@@@", '#', ACItems.dreadium_plate, '%', ACItems.dreadium_ingot, '&', new ItemStack(ACItems.dreadium_chestplate, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.dread_cloth});
		addShapedRecipe(reg, rl("dreadium_samurai_leggings"), null, new ItemStack(ACItems.dreadium_samurai_leggings, 1), new Object[] {"#%#", "&&&", '#', ACItems.dreadium_plate, '%', new ItemStack(ACItems.dreadium_leggings, 1, OreDictionary.WILDCARD_VALUE), '&', ACItems.dread_cloth});
		addArmor(ACItems.ethaxium_helmet, ACItems.ethaxium_chestplate, ACItems.ethaxium_leggings, ACItems.ethaxium_boots, ACItems.ethaxium_ingot, new ItemStack(ACItems.ingot_nugget, 1, 3), ACItems.ethaxium_upgrade_kit, ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots);

		addShapedRecipe(reg, rl("iron_plate"), null, new ItemStack(ACItems.iron_plate, 2), new Object[] {"#", "#", '#', Items.IRON_INGOT});
		addShapedRecipe(reg, rl("washcloth"), null, new ItemStack(ACItems.washcloth, 1), new Object[] {"###", "#%#", "###", '#', Blocks.WEB, '%', Blocks.WOOL});

		addShapelessRecipe(reg, rl("mre"), null, new ItemStack(ACItems.mre, 1), ACItems.iron_plate, Items.CARROT, Items.POTATO, Items.COOKED_BEEF);
		addShapelessRecipe(reg, rl("chicken_on_a_plate"), null, new ItemStack(ACItems.chicken_on_a_plate, 1), ACItems.iron_plate, Items.COOKED_CHICKEN);
		addShapelessRecipe(reg, rl("pork_on_a_plate"), null, new ItemStack(ACItems.pork_on_a_plate, 1), ACItems.iron_plate, Items.COOKED_PORKCHOP);
		addShapelessRecipe(reg, rl("beef_on_a_plate"), null, new ItemStack(ACItems.beef_on_a_plate, 1), ACItems.iron_plate, Items.COOKED_BEEF);
		addShapelessRecipe(reg, rl("fish_on_a_plate"), null, new ItemStack(ACItems.fish_on_a_plate, 1), ACItems.iron_plate, Items.COOKED_FISH);
		addShapelessRecipe(reg, rl("fried_egg_on_a_plate"), null, new ItemStack(ACItems.fried_egg_on_a_plate, 1), ACItems.iron_plate, ACItems.fried_egg);
		addShapelessRecipe(reg, rl("iron_plate_0"), null, new ItemStack(ACItems.iron_plate, 1, 0), ACItems.dirty_plate, new ItemStack(ACItems.washcloth,1, OreDictionary.WILDCARD_VALUE));

		reg.register(new ShapelessOreRecipe(null, new ItemStack(ACItems.cobblestone_upgrade_kit, 4), "plankWood", Blocks.COBBLESTONE, Blocks.COBBLESTONE, Items.STRING, Items.FLINT).setRegistryName(rl("cobblestone_upgrade_kit")));
		addShapelessRecipe(reg, rl("iron_upgrade_kit"), null, new ItemStack(ACItems.iron_upgrade_kit, 1), Blocks.COBBLESTONE, Items.IRON_INGOT, Items.IRON_INGOT, ACItems.cobblestone_upgrade_kit);
		addShapelessRecipe(reg, rl("gold_upgrade_kit"), null, new ItemStack(ACItems.gold_upgrade_kit, 1), Items.IRON_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, ACItems.iron_upgrade_kit);
		addShapelessRecipe(reg, rl("diamond_upgrade_kit"), null, new ItemStack(ACItems.diamond_upgrade_kit, 1), Items.GOLD_INGOT, Items.DIAMOND, Items.DIAMOND, ACItems.gold_upgrade_kit);
		addShapelessRecipe(reg, rl("abyssalnite_upgrade_kit"), null, new ItemStack(ACItems.abyssalnite_upgrade_kit, 1), Items.DIAMOND, ACItems.abyssalnite_ingot, ACItems.abyssalnite_ingot, ACItems.diamond_upgrade_kit);
		addShapelessRecipe(reg, rl("coralium_upgrade_kit"), null, new ItemStack(ACItems.coralium_upgrade_kit, 1), ACItems.abyssalnite_ingot, ACItems.refined_coralium_ingot, ACItems.refined_coralium_ingot, ACItems.abyssalnite_upgrade_kit);
		addShapelessRecipe(reg, rl("dreadium_upgrade_kit"), null, new ItemStack(ACItems.dreadium_upgrade_kit, 1), ACItems.refined_coralium_ingot, ACItems.dreadium_ingot, ACItems.dreadium_ingot, ACItems.coralium_upgrade_kit);
		addShapelessRecipe(reg, rl("ethaxium_upgrade_kit"), null, new ItemStack(ACItems.ethaxium_upgrade_kit, 1), ACItems.dreadium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.dreadium_upgrade_kit);

		addShapelessRecipe(reg, rl("coralium_plague_antidote"), null, new ItemStack(ACItems.antidote, 1, 0), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), ACBlocks.wastelands_thorn, ACItems.anti_plagued_flesh, new ItemStack(ACItems.shoggoth_flesh, 1, 1));
		addShapelessRecipe(reg, rl("dread_plague_antidote"), null, new ItemStack(ACItems.antidote, 1, 1), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), ACBlocks.dreadlands_sapling, ACItems.dread_fragment, new ItemStack(ACItems.shoggoth_flesh, 1, 2));
	}



	private static void addArmor(Item helmet, Item chestplate, Item pants, Item boots, Item material, ItemStack nugget, Item upgrade, Item oldh, Item oldc, Item oldp, Item oldb){
		GameRegistry.addShapedRecipe(helmet.getRegistryName(), null, new ItemStack(helmet), new Object[] {"###", "# #", '#', material});
		GameRegistry.addShapedRecipe(chestplate.getRegistryName(), null, new ItemStack(chestplate), new Object[] {"# #", "###", "###", '#', material});
		GameRegistry.addShapedRecipe(pants.getRegistryName(), null, new ItemStack(pants), new Object[] {"###", "# #", "# #", '#', material});
		GameRegistry.addShapedRecipe(boots.getRegistryName(), null, new ItemStack(boots), new Object[] {"# #", "# #", '#', material});
	}

	private void addStoneStuffRecipes(IForgeRegistry<IRecipe> reg, ItemStack stone, Block slab, Block button, Block pplate){
		if(slab != null) addSlabRecipe(reg, stone, slab);
		if(button != null) addButtonRecipe(reg, stone, button);
		if(pplate != null) addPPlateRecipe(reg, stone, pplate);
	}

	private void addBlockStuffRecipes(IForgeRegistry<IRecipe> reg, ItemStack block, Block slab, Block stairs, Block fence){
		if(slab != null) addSlabRecipe(reg, block, slab);
		if(stairs != null) addStairsRecipe(reg, block, stairs);
		if(fence != null) addFenceRecipe(reg, block, fence);
	}

	private void addSlabRecipe(IForgeRegistry<IRecipe> reg, ItemStack input, Block slab){ addShapedRecipe(reg, slab.getRegistryName(), null, new ItemStack(slab, 6), new Object[] {"AAA", 'A', input }); }
	private void addStairsRecipe(IForgeRegistry<IRecipe> reg, ItemStack input, Block stairs){ addShapedRecipe(reg, stairs.getRegistryName(), null, new ItemStack(stairs, 4), new Object[] {"#  ", "## ", "###", '#', input}); }
	private void addFenceRecipe(IForgeRegistry<IRecipe> reg, ItemStack input, Block fence){ addShapedRecipe(reg, fence.getRegistryName(), null, new ItemStack(fence, 6), new Object[] {"###", "###", '#', input}); }
	private void addButtonRecipe(IForgeRegistry<IRecipe> reg, ItemStack input, Block button){ addShapedRecipe(reg, button.getRegistryName(), null, new ItemStack(button, 1), new Object[] {"#", '#', input}); }
	private void addPPlateRecipe(IForgeRegistry<IRecipe> reg, ItemStack input, Block pplate){ addShapedRecipe(reg, pplate.getRegistryName(), null, new ItemStack(pplate, 1), new Object[] {"##", '#', input}); }

	private ResourceLocation rl(String name){
		return new ResourceLocation("abyssalcraft", name);
	}

	private void addShapedRecipe(IForgeRegistry<IRecipe> reg, ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Object... params)
	{
		ShapedPrimer primer = CraftingHelper.parseShaped(params);
		reg.register(new ShapedRecipes(group == null ? "" : group.toString(), primer.width, primer.height, primer.input, output).setRegistryName(name));
	}

	private void addShapedNBTRecipe(IForgeRegistry<IRecipe> reg, ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Object... params)
	{
		ShapedPrimer primer = CraftingHelper.parseShaped(params);
		reg.register(new ShapedNBTRecipe(group == null ? "" : group.toString(), primer.width, primer.height, primer.input, output).setRegistryName(name));
	}

	private void addShapedOreRecipe(IForgeRegistry<IRecipe> reg, ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Object... params)
	{
		reg.register(new ShapedOreRecipe(group, output, params).setRegistryName(name));
	}

	private void addShapelessRecipe(IForgeRegistry<IRecipe> reg, ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Object... params)
	{
		NonNullList<Ingredient> lst = NonNullList.create();
		for (Object i : params)
			lst.add(CraftingHelper.getIngredient(i));
		reg.register(new ShapelessRecipes(group == null ? "" : group.toString(), output, lst).setRegistryName(name));
	}

	private void addDungeonHooks(){
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","abyssalzombie"), 150);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","depthsghoul"), 100);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","shadowcreature"), 120);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","shadowmonster"), 100);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","shadowbeast"), 30);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antiabyssalzombie"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antighoul"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antiskeleton"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antispider"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antizombie"), 50);
	}

	private void sendIMC(){
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.abyssal_wasteland_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.dreadlands_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.omothol_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.dark_realm_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.abyssal_wasteland_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.dreadlands_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.omothol_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.dark_realm_id));
		if(ACConfig.abyssal_stone_brick_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abyslab2));
		}
		if(ACConfig.darkstone_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkstoneslab2));
		}
		if(ACConfig.darkstone_cobblestone_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkcobbleslab2));
		}
		if(ACConfig.darkstone_brick_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkbrickslab2));
		}
		if(ACConfig.darklands_oak_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTslab2));
		}
		if(ACConfig.abyssalnite_stone_brick_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abydreadbrickslab2));
		}
		if(ACConfig.dreadstone_brick_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadbrickslab2));
		}
		if(ACConfig.coralium_stone_brick_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebrickslab2));
		}
		if(ACConfig.ethaxium_brick_slab) {
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_slab));
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ethaxiumslab2));
		}
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_gateway));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreaded_gateway));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.omothol_gateway));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_fire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreaded_fire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.omothol_fire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.transmutator_active));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.crystallizer_active));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.engraver));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.oblivion_deathbomb));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.odb_core));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_fence));
		if(ACConfig.darkstone_cobblestone_wall)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_wall));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_wood_fence));
		if(ACConfig.abyssal_stone_brick_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_stairs));
		if(ACConfig.darkstone_brick_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_stairs));
		if(ACConfig.darkstone_cobblestone_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_stairs));
		if(ACConfig.darklands_oak_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_stairs));
		if(ACConfig.abyssalnite_stone_brick_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_stairs));
		if(ACConfig.dreadstone_brick_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_stairs));
		if(ACConfig.coralium_stone_brick_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_stairs));
		if(ACConfig.ethaxium_brick_stairs)
			FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Altar));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.chagaroth_altar_top));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.chagaroth_altar_bottom));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_infused_powerstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.depths_ghoul_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.pete_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.mr_wilson_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dr_orange_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_sapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_sapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(house));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dark_ethaxium_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dark_ethaxium_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(darkethaxiumslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dark_ethaxium_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.energy_pedestal));
	}

	private static void registerPotion(ResourceLocation res, Potion pot){
		InitHandler.INSTANCE.POTIONS.add(pot.setRegistryName(res));
	}

	private static void registerEnchantment(ResourceLocation res, Enchantment ench){
		InitHandler.INSTANCE.ENCHANTMENTS.add(ench.setRegistryName(res));
	}

	private static void registerPotionType(ResourceLocation res, PotionType pot){
		InitHandler.INSTANCE.POTION_TYPES.add(pot.setRegistryName(res));
	}

	private static SoundEvent registerSoundEvent(String name){
		ResourceLocation res = new ResourceLocation(modid, name);
		SoundEvent evt = new SoundEvent(res).setRegistryName(res);
		InitHandler.INSTANCE.SOUND_EVENTS.add(evt);
		return evt;
	}

	private void addBrewing(PotionType input, Item ingredient, PotionType output){
		PotionHelper.addMix(input, ingredient, output);
	}
}
