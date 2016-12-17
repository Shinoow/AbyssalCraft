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
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.AbyssalCraft.*;
import static com.shinoow.abyssalcraft.init.BlockHandler.*;

import java.io.File;
import java.util.Stack;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.*;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.google.gson.JsonObject;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.common.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.enchantments.*;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.potion.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.lib.ACAchievements;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.NecroDataJsonUtil;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;

public class MiscHandler implements ILifeCycleHandler {

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		AbyssalCraftAPI.coralium_plague = new PotionCplague(new ResourceLocation("abyssalcraft", "Cplague"), true, 0x00FFFF).setIconIndex(1, 0).setPotionName("potion.Cplague");
		AbyssalCraftAPI.dread_plague = new PotionDplague(new ResourceLocation("abyssalcraft", "Dplague"), true, 0xAD1313).setIconIndex(1, 0).setPotionName("potion.Dplague");
		AbyssalCraftAPI.antimatter_potion = new PotionAntimatter(new ResourceLocation("abyssalcraft", "Antimatter"), true, 0xFFFFFF).setIconIndex(1, 0).setPotionName("potion.Antimatter");

		AbyssalCraftAPI.coralium_enchantment = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "coralium");
		AbyssalCraftAPI.dread_enchantment = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "dread");
		AbyssalCraftAPI.light_pierce = new EnchantmentLightPierce(getNextAvailableEnchantmentId());
		AbyssalCraftAPI.iron_wall = new EnchantmentIronWall(getNextAvailableEnchantmentId(), 2);

		Enchantment.addToBookList(AbyssalCraftAPI.coralium_enchantment);
		Enchantment.addToBookList(AbyssalCraftAPI.dread_enchantment);
		Enchantment.addToBookList(AbyssalCraftAPI.light_pierce);
		Enchantment.addToBookList(AbyssalCraftAPI.iron_wall);

		InitHandler.LIQUID_CORALIUM.setBlock(ACBlocks.liquid_coralium);
		InitHandler.LIQUID_ANTIMATTER.setBlock(ACBlocks.liquid_antimatter);
		if(AbyssalCraftAPI.liquid_coralium_fluid.getBlock() == null)
			AbyssalCraftAPI.liquid_coralium_fluid.setBlock(ACBlocks.liquid_coralium);
		if(AbyssalCraftAPI.liquid_antimatter_fluid.getBlock() == null)
			AbyssalCraftAPI.liquid_antimatter_fluid.setBlock(ACBlocks.liquid_antimatter);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(AbyssalCraftAPI.liquid_coralium_fluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_coralium_bucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_coralium.getDefaultState(), ACItems.liquid_coralium_bucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(AbyssalCraftAPI.liquid_antimatter_fluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_antimatter_bucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_antimatter.getDefaultState(), ACItems.liquid_antimatter_bucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		RitualUtil.addBlocks();
		addOreDictionaryStuff();
		addChestGenHooks();
		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		//Achievements
		ACAchievements.necronomicon = new Achievement("achievement.necro", "necro", 0, 0, ACItems.necronomicon, AchievementList.openInventory).registerStat();
		//Materials Achievements
		ACAchievements.mine_abyssalnite = new Achievement("achievement.mineAby", "mineAby", 2, 0, ACBlocks.abyssalnite_ore, ACAchievements.necronomicon).registerStat();
		ACAchievements.mine_coralium = new Achievement("achievement.mineCorgem", "mineCorgem", 4, 0, ACItems.coralium_gem, ACAchievements.mine_abyssalnite).registerStat();
		ACAchievements.shadow_gems = new Achievement("achievement.shadowGems", "shadowGems", 6, 0, ACItems.shadow_gem, ACAchievements.mine_coralium).registerStat();
		//coraliumpearl
		ACAchievements.mine_abyssal_coralium = new Achievement("achievement.mineCor", "mineCor", 8, 0, ACBlocks.liquified_coralium_ore, ACAchievements.shadow_gems).registerStat();
		ACAchievements.mine_abyssal_ores = new Achievement("achievement.mineAbyOres", "mineAbyOres", 10, 0, ACBlocks.abyssal_diamond_ore, ACAchievements.mine_abyssal_coralium).registerStat();
		ACAchievements.mine_dreadlands_ores = new Achievement("achievement.mineDread", "mineDread", 12, 0, ACBlocks.dreaded_abyssalnite_ore, ACAchievements.mine_abyssal_ores).registerStat();
		ACAchievements.dreadium = new Achievement("achievement.dreadium", "dreadium", 14, 0, ACItems.dreadium_ingot, ACAchievements.mine_dreadlands_ores).registerStat();
		ACAchievements.ethaxium = new Achievement("achievement.ethaxium", "ethaxium", 16, 0, ACItems.ethaxium_ingot, ACAchievements.dreadium).setSpecial().registerStat();
		//Depths Ghoul Achievements
		ACAchievements.kill_depths_ghoul = new Achievement("achievement.killghoul", "killghoul", -2, 0, ACItems.coralium_plagued_flesh_on_a_bone, ACAchievements.necronomicon).registerStat();
		ACAchievements.depths_ghoul_head = new Achievement("achievement.ghoulhead", "ghoulhead", -4, 0, ACBlocks.depths_ghoul_head, ACAchievements.kill_depths_ghoul).registerStat();
		ACAchievements.pete_head = new Achievement("achievement.petehead", "petehead", -4, -2, ACBlocks.pete_head, ACAchievements.depths_ghoul_head).registerStat();
		ACAchievements.mr_wilson_head = new Achievement("achievement.wilsonhead", "wilsonhead", -4, -4, ACBlocks.mr_wilson_head, ACAchievements.pete_head).registerStat();
		ACAchievements.dr_orange_head = new Achievement("achievement.orangehead", "orangehead", -4, -6, ACBlocks.dr_orange_head, ACAchievements.mr_wilson_head).registerStat();
		//Necronomicon Achievements
		ACAchievements.abyssal_wasteland_necronomicon = new Achievement("achievement.necrou1", "necrou1", 2, 1, ACItems.abyssal_wasteland_necronomicon, ACAchievements.necronomicon).registerStat();
		ACAchievements.dreadlands_necronomicon = new Achievement("achievement.necrou2", "necrou2", 4, 1, ACItems.dreadlands_necronomicon, ACAchievements.abyssal_wasteland_necronomicon).registerStat();
		ACAchievements.omothol_necronomicon = new Achievement("achievement.necrou3", "necrou3", 6, 1, ACItems.omothol_necronomicon, ACAchievements.dreadlands_necronomicon).registerStat();
		ACAchievements.abyssalnomicon = new Achievement("achievement.abyssaln", "abyssaln", 8, 1, ACItems.abyssalnomicon, ACAchievements.omothol_necronomicon).setSpecial().registerStat();
		//Ritual Achievements
		ACAchievements.ritual_altar = new Achievement("achievement.ritual", "ritual", -2, 1, ACBlocks.ritual_altar, ACAchievements.necronomicon).setSpecial().registerStat();
		ACAchievements.summoning_ritual = new Achievement("achievement.ritualSummon", "ritualSummon", -4, 1, ACBlocks.depths_ghoul_head, ACAchievements.ritual_altar).registerStat();
		ACAchievements.creation_ritual = new Achievement("achievement.ritualCreate", "ritualCreate", -4, 2, ACItems.life_crystal, ACAchievements.ritual_altar).registerStat();
		ACAchievements.breeding_ritual = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.egg, ACAchievements.ritual_altar).registerStat();
		ACAchievements.potion_ritual = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, Items.potionitem, ACAchievements.ritual_altar).registerStat();
		ACAchievements.aoe_potion_ritual = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, new ItemStack(Items.potionitem, 1, 16384), ACAchievements.ritual_altar).registerStat();
		ACAchievements.infusion_ritual = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, ACItems.depths_helmet, ACAchievements.ritual_altar).registerStat();
		//Progression Achievements
		ACAchievements.enter_abyssal_wasteland = new Achievement("achievement.enterabyss", "enterabyss", 0, 2, ACBlocks.abyssal_stone, ACAchievements.necronomicon).setSpecial().registerStat();
		ACAchievements.kill_spectral_dragon = new Achievement("achievement.killdragon", "killdragon", 2, 2, ACItems.coralium_plagued_flesh, ACAchievements.enter_abyssal_wasteland).registerStat();
		ACAchievements.summon_asorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 4, Altar, ACAchievements.enter_abyssal_wasteland).registerStat();
		ACAchievements.kill_asorah = new Achievement("achievement.killAsorah", "killAsorah", 2, 4, ACItems.eye_of_the_abyss, ACAchievements.summon_asorah).setSpecial().registerStat();
		ACAchievements.enter_dreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 2, 6, ACBlocks.dreadstone, ACAchievements.kill_asorah).setSpecial().registerStat();
		ACAchievements.kill_dreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 4, 6, ACItems.dreaded_shard_of_abyssalnite, ACAchievements.enter_dreadlands).registerStat();
		ACAchievements.summon_chagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 2, 8, ACBlocks.chagaroth_altar_bottom, ACAchievements.enter_dreadlands).registerStat();
		ACAchievements.kill_chagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 4, 8, ACItems.dread_plagued_gateway_key, ACAchievements.summon_chagaroth).setSpecial().registerStat();
		ACAchievements.enter_omothol = new Achievement("achievement.enterOmothol", "enterOmothol", 4, 10, ACBlocks.omothol_stone, ACAchievements.kill_chagaroth).setSpecial().registerStat();
		ACAchievements.enter_dark_realm = new Achievement("achievement.darkRealm", "darkRealm", 2, 10, ACBlocks.darkstone, ACAchievements.enter_omothol).registerStat();
		ACAchievements.kill_omothol_elite = new Achievement("achievement.killOmotholelite", "killOmotholelite", 6, 10, ACItems.eldritch_scale, ACAchievements.enter_omothol).registerStat();
		ACAchievements.locate_jzahar = new Achievement("achievement.locateJzahar", "locateJzahar", 4, 12, ACItems.jzahar_charm, ACAchievements.enter_omothol).registerStat();
		ACAchievements.kill_jzahar = new Achievement("achievement.killJzahar", "killJzahar", 6, 12, ACItems.staff_of_the_gatekeeper, ACAchievements.locate_jzahar).setSpecial().registerStat();
		//nowwhat
		//Gateway Key Achievements
		ACAchievements.gateway_key = new Achievement("achievement.GK1", "GK1", 0, -2, ACItems.gateway_key, ACAchievements.necronomicon).registerStat();
		ACAchievements.find_powerstone = new Achievement("achievement.findPSDL", "findPSDL", -2, -2, ACBlocks.dreadlands_infused_powerstone, ACAchievements.gateway_key).registerStat();
		ACAchievements.dreaded_gateway_key = new Achievement("achievement.GK2", "GK2", 0, -4, ACItems.dreaded_gateway_key, ACAchievements.gateway_key).registerStat();
		ACAchievements.rlyehian_gateway_key = new Achievement("achievement.GK3", "GK3", 0, -6, ACItems.rlyehian_gateway_key, ACAchievements.dreaded_gateway_key).registerStat();
		//Machinery Achievements
		ACAchievements.make_transmutator = new Achievement("achievement.makeTransmutator", "makeTransmutator", 2, -1, ACBlocks.transmutator_idle, ACAchievements.necronomicon).registerStat();
		ACAchievements.make_crystallizer = new Achievement("achievement.makeCrystallizer", "makeCrystallizer", 4, -2, ACBlocks.crystallizer_idle, ACAchievements.make_transmutator).registerStat();
		ACAchievements.make_materializer = new Achievement("achievement.makeMaterializer", "makeMaterializer", 6, -2, ACBlocks.materializer, ACAchievements.make_crystallizer).registerStat();
		ACAchievements.make_crystal_bag = new Achievement("achievement.makeCrystalBag", "makeCrystalBag", 6, -4, ACItems.small_crystal_bag, ACAchievements.make_materializer).registerStat();
		ACAchievements.make_engraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, ACBlocks.engraver, AchievementList.openInventory).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", ACAchievements.getAchievements()));

		necro = ACAchievements.necronomicon;
		mineAby = ACAchievements.mine_abyssalnite;
		killghoul = ACAchievements.kill_depths_ghoul;
		enterabyss = ACAchievements.enter_abyssal_wasteland;
		killdragon = ACAchievements.kill_spectral_dragon;
		summonAsorah = ACAchievements.summon_asorah;
		killAsorah = ACAchievements.kill_asorah;
		enterdreadlands = ACAchievements.enter_dreadlands;
		killdreadguard = ACAchievements.kill_dreadguard;
		ghoulhead = ACAchievements.depths_ghoul_head;
		petehead = ACAchievements.pete_head;
		wilsonhead = ACAchievements.mr_wilson_head;
		orangehead = ACAchievements.dr_orange_head;
		mineCorgem = ACAchievements.mine_coralium;
		mineCor = ACAchievements.mine_abyssal_coralium;
		findPSDL = ACAchievements.find_powerstone;
		GK1 = ACAchievements.gateway_key;
		GK2 = ACAchievements.dreaded_gateway_key;
		GK3 = ACAchievements.rlyehian_gateway_key;
		summonChagaroth = ACAchievements.summon_chagaroth;
		killChagaroth = ACAchievements.kill_chagaroth;
		enterOmothol = ACAchievements.enter_omothol;
		enterDarkRealm = ACAchievements.enter_dark_realm;
		necrou1 = ACAchievements.abyssal_wasteland_necronomicon;
		necrou2 = ACAchievements.dreadlands_necronomicon;
		necrou3 = ACAchievements.omothol_necronomicon;
		abyssaln = ACAchievements.abyssalnomicon;
		ritual = ACAchievements.ritual_altar;
		ritualSummon = ACAchievements.summoning_ritual;
		ritualCreate = ACAchievements.creation_ritual;
		killOmotholelite = ACAchievements.kill_omothol_elite;
		locateJzahar = ACAchievements.locate_jzahar;
		killJzahar = ACAchievements.kill_jzahar;
		shadowGems = ACAchievements.shadow_gems;
		mineAbyOres = ACAchievements.mine_abyssal_ores;
		mineDread = ACAchievements.mine_dreadlands_ores;
		dreadium = ACAchievements.dreadium;
		eth = ACAchievements.ethaxium;
		makeTransmutator = ACAchievements.make_transmutator;
		makeCrystallizer = ACAchievements.make_crystallizer;
		makeMaterializer = ACAchievements.make_materializer;
		makeCrystalBag = ACAchievements.make_crystal_bag;
		makeEngraver = ACAchievements.make_engraver;
		ritualBreed = ACAchievements.breeding_ritual;
		ritualPotion = ACAchievements.potion_ritual;
		ritualPotionAoE = ACAchievements.aoe_potion_ritual;
		ritualInfusion = ACAchievements.infusion_ritual;

		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.CRYSTALLIZER);
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.TRANSMUTATOR);
		AbyssalCrafting.addRecipes();
		AbyssalCraftAPI.addGhoulArmorTextures(Items.leather_helmet, Items.leather_chestplate, Items.leather_leggings, Items.leather_boots, "abyssalcraft:textures/armor/ghoul/leather_1.png", "abyssalcraft:textures/armor/ghoul/leather_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.chainmail_helmet, Items.chainmail_chestplate, Items.chainmail_leggings, Items.chainmail_boots, "abyssalcraft:textures/armor/ghoul/chainmail_1.png", "abyssalcraft:textures/armor/ghoul/chainmail_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots, "abyssalcraft:textures/armor/ghoul/iron_1.png", "abyssalcraft:textures/armor/ghoul/iron_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.golden_helmet, Items.golden_chestplate, Items.golden_leggings, Items.golden_boots, "abyssalcraft:textures/armor/ghoul/gold_1.png", "abyssalcraft:textures/armor/ghoul/gold_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots, "abyssalcraft:textures/armor/ghoul/diamond_1.png", "abyssalcraft:textures/armor/ghoul/diamond_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/abyssalnite_1.png", "abyssalcraft:textures/armor/ghoul/abyssalnite_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots, "abyssalcraft:textures/armor/ghoul/coralium_1.png", "abyssalcraft:textures/armor/ghoul/coralium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots, "abyssalcraft:textures/armor/ghoul/dreadium_1.png", "abyssalcraft:textures/armor/ghoul/dreadium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.ethaxium_helmet, ACItems.ethaxium_chestplate, ACItems.ethaxium_leggings, ACItems.ethaxium_boots, "abyssalcraft:textures/armor/ghoul/ethaxium_1.png", "abyssalcraft:textures/armor/ghoul/ethaxium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreaded_abyssalnite_helmet, ACItems.dreaded_abyssalnite_chestplate, ACItems.dreaded_abyssalnite_leggings, ACItems.dreaded_abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/dread_1.png", "abyssalcraft:textures/armor/ghoul/dread_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.depths_helmet, ACItems.depths_chestplate, ACItems.depths_leggings, ACItems.depths_boots, "abyssalcraft:textures/armor/ghoul/depths_1.png", "abyssalcraft:textures/armor/ghoul/depths_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.plated_coralium_helmet, ACItems.plated_coralium_chestplate, ACItems.plated_coralium_leggings, ACItems.plated_coralium_boots, "abyssalcraft:textures/armor/ghoul/coraliump_1.png", "abyssalcraft:textures/armor/ghoul/coraliump_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_samurai_helmet, ACItems.dreadium_samurai_chestplate, ACItems.dreadium_samurai_leggings, ACItems.dreadium_samurai_boots, "abyssalcraft:textures/armor/ghoul/dreadiums_1.png", "abyssalcraft:textures/armor/ghoul/dreadiums_2.png");
		AbyssalCraftAPI.getInternalNDHandler().registerInternalPages();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		parseNDJsonFiles();
	}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}

	private void parseNDJsonFiles(){

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

	private void addOreDictionaryStuff(){

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
		OreDictionary.registerOre("treeSapling", ACBlocks.darklands_oak_sapling);
		OreDictionary.registerOre("treeSapling", ACBlocks.dreadlands_sapling);
		OreDictionary.registerOre("treeLeaves", ACBlocks.darklands_oak_leaves);
		OreDictionary.registerOre("treeLeaves", ACBlocks.dreadlands_leaves);
		OreDictionary.registerOre("blockAbyssalnite", ACBlocks.block_of_abyssalnite);
		OreDictionary.registerOre("blockLiquifiedCoralium", ACBlocks.block_of_coralium);
		OreDictionary.registerOre("blockDreadium", ACBlocks.block_of_dreadium);
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
		OreDictionary.registerOre("blockEthaxium", ACBlocks.block_of_ethaxium);
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
	}

	private void addChestGenHooks(){

		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_axe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_pickaxe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_shovel), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_sword), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACBlocks.darklands_oak_wood), 1, 3, 10));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.cobblestone_upgrade_kit), 1, 2, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.oblivion_catalyst), 1,1,1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_pickaxe), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_helmet), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_chestplate), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_leggings), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_boots), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.cobblestone_upgrade_kit), 1, 2, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.iron_upgrade_kit), 1, 2, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.gold_upgrade_kit), 1, 2, 4));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.diamond_upgrade_kit), 1, 2, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.mre), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.coralium_gem), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
	}

	private void addDungeonHooks(){
		DungeonHooks.addDungeonMob("abyssalcraft.abyssalzombie", 150);
		DungeonHooks.addDungeonMob("abyssalcraft.depthsghoul", 100);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowcreature", 120);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowmonster", 100);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowbeast", 30);
		DungeonHooks.addDungeonMob("abyssalcraft.antiabyssalzombie", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antighoul", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antiskeleton", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antispider", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antizombie", 50);
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abyslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkstoneslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkcobbleslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abydreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ethaxiumslab2));
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_wall));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_wood_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_stairs));
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.cthulhu_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.hastur_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.jzahar_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.azathoth_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.nyarlathotep_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.yog_sothoth_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.shub_niggurath_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.energy_pedestal));
	}

	private int getNextAvailableEnchantmentId(){

		int i = 0;

		do{
			i++;
			if(i >= 256) throw new RuntimeException("Out of available Enchantment IDs, AbyssalCraft can't load unless some IDs are freed up!");

		} while(Enchantment.getEnchantmentById(i) != null);

		return i;
	}
}
