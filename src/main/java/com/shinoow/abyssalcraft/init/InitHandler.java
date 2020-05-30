/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.lib.ACConfig.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.handlers.ClientVarsReloadListener;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiGhoul;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.*;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class InitHandler implements ILifeCycleHandler {

	public static final InitHandler INSTANCE = new InitHandler();

	public static Configuration cfg;

	private static String[] abyssalZombieBlacklist, depthsGhoulBlacklist, antiAbyssalZombieBlacklist, antiGhoulBlacklist,
	omotholGhoulBlacklist, interdimensionalCageBlacklist, dreadPlagueImmunityList, dreadPlagueCarrierList,
	coraliumPlagueImmunityList, coraliumPlagueCarrierList;

	public static int[] coraliumOreGeneration;
	private static int[] blackHoleBlacklist, oreGenDimBlacklist, structureGenDimBlacklist;
	private int[] blackHoleDimlist;

	public static boolean dark1, dark2, dark3, dark4, dark5, coralium1;
	public static boolean darkspawn1, darkspawn2, darkspawn3, darkspawn4, darkspawn5, coraliumspawn1;
	public static int darkWeight1, darkWeight2, darkWeight3, darkWeight4, darkWeight5, coraliumWeight;

	public static final Fluid LIQUID_CORALIUM = new Fluid("liquidcoralium", new ResourceLocation("abyssalcraft", "blocks/cwater_still"),
			new ResourceLocation("abyssalcraft", "blocks/cwater_flow")).setDensity(3000).setTemperature(350);
	public static final Fluid LIQUID_ANTIMATTER = new Fluid("liquidantimatter", new ResourceLocation("abyssalcraft", "blocks/anti_still"),
			new ResourceLocation("abyssalcraft", "blocks/anti_flow")).setDensity(4000).setViscosity(1500).setTemperature(100);

	private static final List<ItemStack> abyssal_zombie_blacklist = new ArrayList<>();
	private static final List<ItemStack> depths_ghoul_blacklist = new ArrayList<>();
	private static final List<ItemStack> anti_abyssal_zombie_blacklist = new ArrayList<>();
	private static final List<ItemStack> anti_ghoul_blacklist = new ArrayList<>();
	private static final List<ItemStack> omothol_ghoul_blacklist = new ArrayList<>();

	private static final List<String> dread_carriers = new ArrayList<>();
	private static final List<String> dread_immunity = new ArrayList<>();
	private static final List<String> coralium_carriers = new ArrayList<>();
	private static final List<String> coralium_immunity = new ArrayList<>();

	public static final Map<ResourceLocation, Tuple<Integer, Float>> demon_transformations = new HashMap<>();

	final List<Block> BLOCKS = new ArrayList<>();
	final List<Item> ITEMS = new ArrayList<>();
	final List<Biome> BIOMES = new ArrayList<>();
	final List<Enchantment> ENCHANTMENTS = new ArrayList<>();
	final List<Potion> POTIONS = new ArrayList<>();
	final List<PotionType> POTION_TYPES = new ArrayList<>();
	final List<SoundEvent> SOUND_EVENTS = new ArrayList<>();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		AbyssalCraft.metadata = event.getModMetadata();
		AbyssalCraft.metadata.description += "\n\n\u00a76Supporters: "+getSupporterList()+"\u00a7r";

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftEventHooks());
		MinecraftForge.TERRAIN_GEN_BUS.register(new AbyssalCraftEventHooks());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new KnowledgeEventHandler());
		MinecraftForge.EVENT_BUS.register(new PlagueEventHandler());
		MinecraftForge.EVENT_BUS.register(new PurgeEventHandler());
		MinecraftForge.EVENT_BUS.register(new ItemTransferEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(AbyssalCraft.instance, new GuiHandler());
		AbyssalCraftAPI.setInternalNDHandler(new InternalNecroDataHandler());
		AbyssalCraftAPI.setInternalMethodHandler(new InternalMethodHandler());
		ACLoot.init();
		ACTabs.init();

		cfg = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();

		if(hardcoreMode){
			AbyssalCraftAPI.coralium.setDamageIsAbsolute();
			AbyssalCraftAPI.dread.setDamageIsAbsolute();
		}

		if(!FluidRegistry.isFluidRegistered("liquidcoralium")){
			AbyssalCraftAPI.liquid_coralium_fluid = LIQUID_CORALIUM;
			FluidRegistry.registerFluid(AbyssalCraftAPI.liquid_coralium_fluid);
			FluidRegistry.addBucketForFluid(AbyssalCraftAPI.liquid_coralium_fluid);
		} else {
			ACLogger.warning("Liquid Coralium was already registered by another mod, adding ours as alternative.");
			AbyssalCraftAPI.liquid_coralium_fluid = FluidRegistry.getFluid("liquidcoralium");
			FluidRegistry.registerFluid(LIQUID_CORALIUM);
			FluidRegistry.addBucketForFluid(LIQUID_CORALIUM);
		}

		if(!FluidRegistry.isFluidRegistered("liquidantimatter")){
			AbyssalCraftAPI.liquid_antimatter_fluid = LIQUID_ANTIMATTER;
			FluidRegistry.registerFluid(AbyssalCraftAPI.liquid_antimatter_fluid);
			FluidRegistry.addBucketForFluid(AbyssalCraftAPI.liquid_antimatter_fluid);
		} else {
			ACLogger.warning("Liquid Antimatter was already registered by another mod, adding ours as alternative.");
			AbyssalCraftAPI.liquid_antimatter_fluid = FluidRegistry.getFluid("liquidantimatter");
			FluidRegistry.registerFluid(LIQUID_ANTIMATTER);
			FluidRegistry.addBucketForFluid(LIQUID_ANTIMATTER);
		}

		if(event.getSide().isClient())
			ClientVarsReloadListener.updateVars(Minecraft.getMinecraft().getResourceManager());
	}

	@Override
	public void init(FMLInitializationEvent event) {}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		constructBlacklists();
		DimensionType[] dims = DimensionManager.getRegisteredDimensions().keySet().toArray(new DimensionType[0]);
		blackHoleDimlist = new int[dims.length];
		for(int i = 0; i < dims.length; i++)
			blackHoleDimlist[i] = dims[i].getId();
	}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}

	public void serverStart(FMLServerAboutToStartEvent event){
		String clname = AbyssalCraftAPI.getInternalNDHandler().getClass().getName();
		String expect = "com.shinoow.abyssalcraft.common.handlers.InternalNecroDataHandler";
		if(!clname.equals(expect)) {
			new IllegalAccessError("The AbyssalCraft API internal NecroData handler has been overriden. "
					+ "Since things are not going to work correctly, the game will now shut down."
					+ " (Expected classname: " + expect + ", Actual classname: " + clname + ")").printStackTrace();
			FMLCommonHandler.instance().exitJava(1, true);
		}
		clname = AbyssalCraftAPI.getInternalMethodHandler().getClass().getName();
		expect = "com.shinoow.abyssalcraft.common.handlers.InternalMethodHandler";
		if(!clname.equals(expect)) {
			new IllegalAccessError("The AbyssalCraft API internal Method handler has been overriden. "
					+ "Since things are not going to work correctly, the game will now shut down."
					+ " (Expected classname: " + expect + ", Actual classname: " + clname + ")").printStackTrace();
			FMLCommonHandler.instance().exitJava(1, true);
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.getModID().equals("abyssalcraft"))
			syncConfig();
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
		RitualUtil.addBlocks();
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
		ACItems.liquid_coralium_bucket_stack = FluidUtil.getFilledBucket(new FluidStack(AbyssalCraftAPI.liquid_coralium_fluid, Fluid.BUCKET_VOLUME));
		ACItems.liquid_antimatter_bucket_stack = FluidUtil.getFilledBucket(new FluidStack(AbyssalCraftAPI.liquid_antimatter_fluid, Fluid.BUCKET_VOLUME));

		AbyssalCraftAPI.setRepairItems();
		MiscHandler.addOreDictionaryStuff();
	}

	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event){
		event.getRegistry().registerAll(BIOMES.toArray(new Biome[0]));

		if(dark1){
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.darklands, darkWeight1));
			BiomeManager.addVillageBiome(ACBiomes.darklands, true);
		}
		if(dark2){
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.darklands_forest, darkWeight2));
			BiomeManager.addVillageBiome(ACBiomes.darklands_forest, true);
		}
		if(dark3){
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.darklands_plains, darkWeight3));
			BiomeManager.addVillageBiome(ACBiomes.darklands_plains, true);
		}
		if(dark4)
			BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(ACBiomes.darklands_hills, darkWeight4));
		if(dark5){
			BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(ACBiomes.darklands_mountains, darkWeight5));
			BiomeManager.addStrongholdBiome(ACBiomes.darklands_mountains);
		}
		if(coralium1)
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.coralium_infested_swamp, coraliumWeight));
		if(darkspawn1)
			BiomeManager.addSpawnBiome(ACBiomes.darklands);
		if(darkspawn2)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_forest);
		if(darkspawn3)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_plains);
		if(darkspawn4)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_hills);
		if(darkspawn5)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_mountains);
		if(coraliumspawn1)
			BiomeManager.addSpawnBiome(ACBiomes.coralium_infested_swamp);

		BiomeDictionary.addTypes(ACBiomes.darklands, Type.WASTELAND, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_forest, Type.FOREST, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_plains, Type.PLAINS, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_hills, Type.HILLS, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_mountains, Type.MOUNTAIN, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.coralium_infested_swamp, Type.SWAMP);

		BiomeDictionary.addTypes(ACBiomes.abyssal_wastelands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.purified_dreadlands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands_mountains, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands_forest, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.omothol, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dark_realm, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.purged, Type.DEAD);
	}

	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event){
		event.getRegistry().registerAll(ENCHANTMENTS.toArray(new Enchantment[0]));
	}

	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event){
		event.getRegistry().registerAll(POTIONS.toArray(new Potion[0]));
	}

	@SubscribeEvent
	public void registerPotionTypes(RegistryEvent.Register<PotionType> event){
		event.getRegistry().registerAll(POTION_TYPES.toArray(new PotionType[0]));
	}

	@SubscribeEvent
	public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event){
		event.getRegistry().registerAll(SOUND_EVENTS.toArray(new SoundEvent[0]));
	}

	private static void syncConfig(){

		cfg.setCategoryComment("dimensions", "Dimension configuration (ID configuration and dimension unloading). Any changes take effect after a Minecraft restart.");
		cfg.setCategoryComment("biome_generation", "Biome generation configuration (whether a biome should generate). Any changes take effect after a Minecraft restart.");
		cfg.setCategoryComment("biome_spawning", "Biome spawning configuration (if players have a chance of spawning in the biomes). Any changes take effect after a Minecraft restart.");
		cfg.setCategoryComment(Configuration.CATEGORY_GENERAL, "General configuration (misc things). Only the spawn weights require a Minecraft restart for changes to take effect.");
		cfg.setCategoryComment("biome_weight", "Biome weight configuration (the chance n out of 100 that a biome is picked to generate). Any changes take effect after a Minecraft restart.");
		cfg.setCategoryComment("shoggoth", "Shoggoth Ooze configuration (blacklist materials from turning into ooze). Any changes take effect immediately.");
		cfg.setCategoryComment("worldgen", "World generation configuration (things that generate in the world). Any changes take effect immediately.");
		cfg.setCategoryComment("item_blacklist", "Entity Item Blacklist (allows you to blacklist items/blocks for entities that can pick up things). Any changes take effect after a Minecraft restart.");
		cfg.setCategoryComment("silly_settings", "These settings are generally out of place, and don't contribute to the mod experience. They exist because 'what if X did this?'");
		cfg.setCategoryComment("blocks", "These settings allow you to disable specific blocks in the mod, mainly slabs, stairs and walls. Any changes take effect after a Minecraft restart.");
		cfg.setCategoryComment("mod_compat", "Mod compatibility configuration (settings in regards to certain compatiblity with other mods that can be disabled at will).");
		cfg.setCategoryComment("wet_noodle", "These settings allow you to disable features of a destructive type, or things that to some extent will impact how the mod is played (while generally making it easier).");
		cfg.setCategoryComment("modules", "These settings allow you to disable content from the mod that may be out of place to some extent, or other reasons. Any changes take effect after a Minecraft restart.");
		cfg.setCategoryComment("spells", "Spell configuration (allows you to enable or disable specific spells added by the mod). Any changes take effect after a Minecraft restart.");

		ACLib.abyssal_wasteland_id = cfg.get("dimensions", "The Abyssal Wasteland", 50, "The first dimension, full of undead monsters.").getInt();
		ACLib.dreadlands_id = cfg.get("dimensions", "The Dreadlands", 51, "The second dimension, infested with mutated monsters.").getInt();
		ACLib.omothol_id = cfg.get("dimensions", "Omothol", 52, "The third dimension, also known as \u00A7oThe Realm of J'zahar\u00A7r.").getInt();
		ACLib.dark_realm_id = cfg.get("dimensions", "The Dark Realm", 53, "Hidden fourth dimension, reached by falling down from Omothol").getInt();

		keepLoaded1 = cfg.get("dimensions", "Prevent unloading: The Abyssal Wasteland", false, "Set true to prevent The Abyssal Wasteland from automatically unloading (might affect performance)").getBoolean();
		keepLoaded2 = cfg.get("dimensions", "Prevent unloading: The Dreadlands", false, "Set true to prevent The Dreadlands from automatically unloading (might affect performance)").getBoolean();
		keepLoaded3 = cfg.get("dimensions", "Prevent unloading: Omothol", false, "Set true to prevent Omothol from automatically unloading (might affect performance)").getBoolean();
		keepLoaded4 = cfg.get("dimensions", "Prevent unloading: The Dark Realm", false, "Set true to prevent The Dark Realm from automatically unloading (might affect performance)").getBoolean();

		dark1 = cfg.get("biome_generation", "Darklands", true, "Set true for the Darklands biome to generate.").getBoolean();
		dark2 = cfg.get("biome_generation", "Darklands Forest", true, "Set true for the Darklands Forest biome to generate.").getBoolean();
		dark3 = cfg.get("biome_generation", "Darklands Plains", true, "Set true for the Darklands Plains biome to generate.").getBoolean();
		dark4 = cfg.get("biome_generation", "Darklands Highland", true, "Set true for the Darklands Highland biome to generate.").getBoolean();
		dark5 = cfg.get("biome_generation", "Darklands Mountain", true, "Set true for the Darklands Mountain biome to generate.").getBoolean();
		coralium1 = cfg.get("biome_generation", "Coralium Infested Swamp", true, "Set true for the Coralium Infested Swamp to generate.").getBoolean();

		darkspawn1 = cfg.get("biome_spawning", "Darklands", true, "If true, you can spawn in the Darklands biome.").getBoolean();
		darkspawn2 = cfg.get("biome_spawning", "Darklands Forest", true, "If true, you can spawn in the Darklands Forest biome.").getBoolean();
		darkspawn3 = cfg.get("biome_spawning", "Darklands Plains", true, "If true, you can spawn in the Darklands Plains biome.").getBoolean();
		darkspawn4 = cfg.get("biome_spawning", "Darklands Highland", true, "If true, you can spawn in the Darklands Highland biome.").getBoolean();
		darkspawn5 = cfg.get("biome_spawning", "Darklands Mountain", true, "If true, you can spawn in the Darklands Mountain biome.").getBoolean();
		coraliumspawn1 = cfg.get("biome_spawning", "Coralium Infested Swamp", true, "If true, you can spawn in the Coralium Infested Swamp biome.").getBoolean();

		shouldSpread = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Coralium transmutation", true, "Set true for the Liquid Coralium to convert other liquids into itself and transmute blocks into their Abyssal Wasteland counterparts outside of the Abyssal Wasteland.").getBoolean();
		shouldInfect = cfg.get(Configuration.CATEGORY_GENERAL, "Coralium Plague spreading", false, "Set true to allow the Coralium Plague to spread outside The Abyssal Wasteland.").getBoolean();
		destroyOcean = cfg.get(Configuration.CATEGORY_GENERAL, "Oceanic Coralium Pollution", false, "Set true to allow the Liquid Coralium to spread across oceans. WARNING: The game can crash from this.").getBoolean();
		demonAnimalFire = cfg.get(Configuration.CATEGORY_GENERAL, "Demon Animal burning", false, "Set to false to prevent Demon Animals (Pigs, Cows, Chickens) from burning in the overworld.").getBoolean();
		evilAnimalSpawnWeight = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animal spawn weight", 15, "Spawn weight for the Evil Animals (Pigs, Cows, Chickens), keep under 35 to avoid complete annihilation.\n[range: 0 ~ 100, default: 20]", 0, 100).getInt();
		particleBlock = cfg.get(Configuration.CATEGORY_GENERAL, "Block particles", true, "Toggles whether blocks that emits particles should do so.").getBoolean();
		particleEntity = cfg.get(Configuration.CATEGORY_GENERAL, "Entity particles", true, "Toggles whether entities that emits particles should do so.").getBoolean();
		hardcoreMode = cfg.get(Configuration.CATEGORY_GENERAL, "Hardcore Mode", false, "Toggles Hardcore mode. If set to true, all mobs (in the mod) will become tougher.").getBoolean();
		antiItemDisintegration = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Antimatter item disintegration", true, "Toggles whether or not Liquid Antimatter will disintegrate any items dropped into a pool of it.").getBoolean();
		portalCooldown = cfg.get(Configuration.CATEGORY_GENERAL, "Portal cooldown", 100, "Cooldown after using a portal, increasing the value increases the delay until you can teleport again. Measured in ticks (20 ticks = 1 second).\n[range: 10 ~ 300, default: 100]", 10, 300).getInt();
		demonAnimalSpawnWeight = cfg.get(Configuration.CATEGORY_GENERAL, "Demon Animal spawn weight", 15, "Spawn weight for the Demon Animals (Pigs, Cows, Chickens) spawning in the Nether.\n[range: 0 ~ 100, default: 15]", 0, 100).getInt();
		smeltingRecipes = cfg.get(Configuration.CATEGORY_GENERAL, "Smelting Recipes", true, "Toggles whether or not to add smelting recipes for armor pieces.").getBoolean();
		purgeMobSpawns = cfg.get(Configuration.CATEGORY_GENERAL, "Purge Mob Spawns", false, "Toggles whether or not to clear and repopulate the monster spawn list of all dimension biomes to ensure no mob from another mod got in there.").getBoolean();
		interdimensionalCageBlacklist = cfg.get(Configuration.CATEGORY_GENERAL, "Interdimensional Cage Blacklist", new String[]{}, "Entities added to this list can't be captured with the Interdimensional Cage.").getStringList();
		damageAmpl = cfg.get(Configuration.CATEGORY_GENERAL, "Hardcore Mode damage amplifier", 1.0D, "When Hardcore Mode is enabled, you can use this to amplify the armor-piercing damage mobs deal.\n[range: 1.0 ~ 10.0, default: 1.0]", 1.0D, 10.0D).getDouble();
		depthsHelmetOverlayOpacity = cfg.get(Configuration.CATEGORY_GENERAL, "Visage of The Depths Overlay Opacity", 1.0D, "Sets the opacity for the overlay shown when wearing the Visage of The Depths, reducing the value increases the transparency on the texture. Client Side only!\n[range: 0.5 ~ 1.0, default: 1.0]", 0.5D, 1.0D).getDouble();
		mimicFire = cfg.get(Configuration.CATEGORY_GENERAL, "Mimic Fire", true, "Toggles whether or not Demon Animals will spread Mimic Fire instead of regular Fire (regular Fire can affect performance)").getBoolean();
		armorPotionEffects = cfg.get(Configuration.CATEGORY_GENERAL, "Armor Potion Effects", true, "Toggles any interactions where armor sets either give certain Potion Effects, or dispell others. Useful if you have another mod installed that provides similar customization to any armor set.").getBoolean();
		syncDataOnBookOpening = cfg.get(Configuration.CATEGORY_GENERAL, "Necronomicon Data Syncing", true, "Toggles whether or not the Necronomicon knowledge will sync from the server to the client each time a player opens their Necronomicon.").getBoolean();
		dreadGrassSpread = cfg.get(Configuration.CATEGORY_GENERAL, "Dreadlands Grass Spread", true, "Toggles whether or not Dreadlands Grass can spread onto normal grass and dirt, slowly turning them into their Dreadlands counterparts.").getBoolean();
		APIUtils.display_names = cfg.get(Configuration.CATEGORY_GENERAL, "Display Item Names", false, "Toggles whether or not to override the name locking and display item names regardless of the knowledge being obtained or not.").getBoolean();
		blackHoleBlacklist = cfg.get(Configuration.CATEGORY_GENERAL, "Reality Maelstrom Blacklist", new int[0], "Dimension IDs added to this list won't be used as potential destinations for black holes created by J'zahar.").getIntList();
		portalSpawnsNearPlayer = cfg.get(Configuration.CATEGORY_GENERAL, "Portal Mob Spawning Near Players", true, "Toggles whether or not portals require a player to be nearby in order for it to rarely spawn mobs. If this option is disabled they follow the same principle as Nether portals.").getBoolean();
		showBossDialogs = cfg.get(Configuration.CATEGORY_GENERAL, "Show Boss Dialogs", true, "Toggles whether or not boss dialogs are displayed at any point during their fights (when they spawn, when they die,  etc)").getBoolean();
		knowledgeSyncDelay = cfg.get(Configuration.CATEGORY_GENERAL, "Knowledge Sync Delay", 60, "Delay in ticks until Knowledge is synced to the client upon changing dimensions. Higher numbers mean you might see item names re-locked for a few seconds when changing dimension, but might reduce load time for the dimension by a little (useful in larger modpacks).\n[range: 20 ~ 400, default: 60]", 20, 400).getInt();
		dreadPlagueImmunityList = cfg.get(Configuration.CATEGORY_GENERAL, "Dread Plague Immunity List", new String[0], "Entities added to this list are considered immune to the Dread Plague.").getStringList();
		dreadPlagueCarrierList = cfg.get(Configuration.CATEGORY_GENERAL, "Dread Plague Carrier List", new String[0], "Entities added to this list are considered carriers of the Dread Plague (this also makes them immune).").getStringList();
		coraliumPlagueImmunityList = cfg.get(Configuration.CATEGORY_GENERAL, "Coralium Plague Immunity List", new String[0], "Entities added to this list are considered immune to the Coralium Plague.").getStringList();
		coraliumPlagueCarrierList = cfg.get(Configuration.CATEGORY_GENERAL, "Coralium Plague Carrier List", new String[0], "Entities added to this list are considered carriers of the Coralium Plague (this also makes them immune).").getStringList();
		lootTableContent = cfg.get(Configuration.CATEGORY_GENERAL, "Loot Table Content", true, "Toggles whether or not AbyssalCraft Items should be inserted into vanilla loot tables (dungeons, strongholds etc).").getBoolean();
		depthsGhoulBiomeDictSpawn = cfg.get(Configuration.CATEGORY_GENERAL, "Depths Ghoul Biome Dictionary Spawning", true, "Toggles whether or not Depths Ghouls should use the Biome Dictionary for finding biomes to spawn in (which might lead to them spawning in dimensions from other mods).").getBoolean();
		abyssalZombieBiomeDictSpawn = cfg.get(Configuration.CATEGORY_GENERAL, "Abyssal Zombie Biome Dictionary Spawning", true, "Toggles whether or not Abyssal Zombies should use the Biome Dictionary for finding biomes to spawn in (which might lead to them spawning in dimensions from other mods).").getBoolean();
		darkOffspringSpawnWeight = cfg.get(Configuration.CATEGORY_GENERAL, "Dark Offspring Spawn Weight", 5, "Spawn weight for Dark Offspring. They spawn twice as likely in Darklands Forests and Roofed Forests.\n[range: 0 ~ 50, default: 5]", 0, 50).getInt();
		corruptionRitualRange = cfg.get(Configuration.CATEGORY_GENERAL, "Corruption Ritual Range", 32, "The range (in chunks) that will be affected by the Ritual of Corruption (on the x and z axis)\n[range: 3 ~ 100, default: 32]", 3, 100).getInt();
		cleansingRitualRange = cfg.get(Configuration.CATEGORY_GENERAL, "Cleansing Ritual Range", 32, "The range (in chunks) that will be affected by the Ritual of Cleansing (on the x and z axis)\n[range: 3 ~ 100, default: 32]", 3, 100).getInt();
		purgingRitualRange = cfg.get(Configuration.CATEGORY_GENERAL, "Purging Ritual Range", 32, "The range (in chunks) that will be affected by the Ritual of Purging (on the x and z axis)\n[range: 3 ~ 100, default: 32]", 3, 100).getInt();
		enchantmentMaxLevel = cfg.get(Configuration.CATEGORY_GENERAL, "Mass Enchantment Max Level", 10, "The combined max level a single enchantment applied through the Mass Enchantment ritual can have. For example, if the max level is 10 and you apply 8 Sharpness 5 books, you'd end up with Sharpness 10 on the Item, rather than 40.\n[range: 1 ~ 100, default: 10]", 1, 100).getInt();
		enchantBooks = cfg.get(Configuration.CATEGORY_GENERAL, "Mass Enchantment Books", true, "Toggles whether or not Books can be enchanted through the Mass Enchantment ritual.").getBoolean();
		nightVisionEverywhere = cfg.get(Configuration.CATEGORY_GENERAL, "Plated Coralium Helmet Night Vision Everywhere", true, "Toggles whether or not the Night Vision buff from the Plated Coralium Helmet should be applied in all dimensions, rather than only Surface Worlds.").getBoolean();
		demonAnimalsSpawnOnDeath = cfg.get(Configuration.CATEGORY_GENERAL, "Demon Animals Spawn on Death", true, "Toggles whether or not an Evil Animal spawns a Demon Animal on death.").getBoolean();
		evilAnimalNewMoonSpawning = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animal New Moon Spawning", true, "Toggles whether or not Evil Animals only spawn at night during a new moon.").getBoolean();
		curingRitualRange = cfg.get(Configuration.CATEGORY_GENERAL, "Curing Ritual Range", 32, "The range (in chunks) that will be affected by the Ritual of Purging (on the x and z axis)\n[range: 3 ~ 100, default: 32]", 3, 100).getInt();

		darkWeight1 = cfg.get("biome_weight", "Darklands", 4, "Biome weight for the Darklands biome, controls the chance of it generating (n out of 100).\n[range: 0 ~ 100, default: 5]", 0, 100).getInt();
		darkWeight2 = cfg.get("biome_weight", "Darklands Forest", 4, "Biome weight for the Darklands Forest biome, controls the chance of it generating (n out of 100)\n[range: 0 ~ 100, default: 5]", 0, 100).getInt();
		darkWeight3 = cfg.get("biome_weight", "Darklands Plains", 4, "Biome weight for the Darklands Plains biome, controls the chance of it generating (n out of 100)\n[range: 0 ~ 100, default: 5]", 0, 100).getInt();
		darkWeight4 = cfg.get("biome_weight", "Darklands Highland", 4, "Biome weight for the Darklands Highland biome, controls the chance of it generating (n out of 100)\n[range: 0 ~ 100, default: 5]", 0, 100).getInt();
		darkWeight5 = cfg.get("biome_weight", "Darklands Mountain", 4, "Biome weight for the Darklands Mountain biome, controls the chance of it generating (n out of 100)\n[range: 0 ~ 100, default: 5]").getInt();
		coraliumWeight = cfg.get("biome_weight", "Coralium Infested Swamp", 6, "Biome weight for the Coralium Infested Swamp biome, controls the chance of it generating (n out of 100)\n[range: 0 ~ 100, default: 5]", 0, 100).getInt();

		shoggothOoze = cfg.get("shoggoth", "Shoggoth Ooze Spread", true, "Toggles whether or not Lesser Shoggoths should spread their ooze when walking around.").getBoolean();
		oozeExpire = cfg.get("shoggoth", "Ooze expiration", true, "Toggles whether or not Shoggoth Ooze slowly reverts to dirt after constant light exposure. Ooze blocks that aren't full blocks will shrink instead.").getBoolean();
		consumeItems = cfg.get("shoggoth", "Item Consumption", false, "Toggles whether or not Lesser Shoggoths will consume any dropped item they run into.").getBoolean();
		shieldsBlockAcid = cfg.get("shoggoth", "Shields Block Acid", true, "Toggles whether or not Shields can block the acid projectiles spat by Lesser Shoggoths.").getBoolean();
		acidResistanceHardness = cfg.get("shoggoth", "Acid Resistance Hardness", 3.0D, "The minimum Block Hardness required for a Block to not be destroyed by Shoggoth Acid (some blocks are unaffected regardless of their hardness)\n[range: 2.1 ~ 51.0, default: 3.0]", 2.1D, 51.0D).getDouble();
		acidSpitFrequency = cfg.get("shoggoth", "Acid Spit Frequency", 120, "The frequency (in ticks) at which a Lesser Shoggoth can spit acid. Higher values increase the time between each spit attack, while lower values descrease the time (and 0 disables it).\n[range: 0 ~ 300, default: 100]", 0, 300).getInt();
		monolithBuildingCooldown = cfg.get("shoggoth", "Monolith Building Cooldown", 1500, "The cooldown (in ticks) between each attempt by a Lesser Shoggoth to construct a monolith. Higher values increase the time, while lower values decrease it (and 0 disables it).\n[range: 0 ~ 2400, default: 1800]", 0, 2400).getInt();
		shoggothGlowingEyes = cfg.get("shoggoth", "Glowing Eyes", true, "Toggles whether or not the eyes of Lesser Shoggoths should glow. The glowing can be heavy on performance, so if you're dropping FPS noticeably while looking at Lesser Shoggoths, consider turning this off. Client Side only!").getBoolean();

		generateDarklandsStructures = cfg.get("worldgen", "Darklands Structures", true, "Toggles whether or not to generate random Darklands structures.").getBoolean();
		generateShoggothLairs = cfg.get("worldgen", "Shoggoth Lairs", true, "Toggles whether or not to generate Shoggoth Lairs (however, they will still generate in Omothol).").getBoolean();
		generateAbyssalWastelandPillars = cfg.get("worldgen", "Abyssal Wasteland Pillars", true, "Toggles whether or not to generate Tall Obsidian Pillars in the Abyssal Wasteland.").getBoolean();
		generateAbyssalWastelandRuins = cfg.get("worldgen", "Abyssal Wasteland Ruins", true, "Toggles whether or not to generate small ruins in the Abyssal Wasteland.").getBoolean();
		generateAntimatterLake = cfg.get("worldgen", "Liquid Antimatter Lakes", true, "Toggles whether or not to generate Liquid Antimatter Lakes in Coralium Infested Swamps.").getBoolean();
		generateCoraliumLake = cfg.get("worldgen", "Liquid Coralium Lakes", true, "Toggles whether or not to generate Liquid Coralium Lakes in the Abyssal Wasteland.").getBoolean();
		generateDreadlandsStalagmite = cfg.get("worldgen", "Dreadlands Stalagmites", true, "Toggles whether or not to generate Stalagmites in Dreadlands and Purified Dreadlands biomes.").getBoolean();

		generateCoraliumOre = cfg.get("worldgen", "Coralium Ore", true, "Toggles whether or not to generate Coralium Ore in the Overworld.").getBoolean();
		generateNitreOre = cfg.get("worldgen", "Nitre Ore", true, "Toggles whether or not to generate Nitre Ore in the Overworld.").getBoolean();
		generateAbyssalniteOre = cfg.get("worldgen", "Abyssalnite Ore", true, "Toggles wheter or not to generate Abyssalnite Ore in Darklands Biomes.").getBoolean();
		generateAbyssalCoraliumOre = cfg.get("worldgen", "Abyssal Coralium Ore", true, "Toggles whether or not to generate Coralium Ore in the Abyssal Wasteland.").getBoolean();
		generateDreadlandsAbyssalniteOre = cfg.get("worldgen", "Dreadlands Abyssalnite Ore", true, "Toggles whether or not to generate Abyssalnite Ore in the Dreadlands.").getBoolean();
		generateDreadedAbyssalniteOre = cfg.get("worldgen", "Dreaded Abyssalnite Ore", true, "Toggles whether or not to generate Dreaded Abyssalnite Ore in the Dreadlands.").getBoolean();
		generateAbyssalIronOre = cfg.get("worldgen", "Abyssal Iron Ore", true, "Toggles whether or not to generate Iron Ore in the Abyssal Wasteland.").getBoolean();
		generateAbyssalGoldOre = cfg.get("worldgen", "Abyssal Gold Ore", true, "Toggles whether or not to generate Gold Ore in the Abyssal Wasteland.").getBoolean();
		generateAbyssalDiamondOre = cfg.get("worldgen", "Abyssal Diamond Ore", true, "Toggles whether or not to generate Diamond Ore in the Abyssal Wasteland").getBoolean();
		generateAbyssalNitreOre = cfg.get("worldgen", "Abyssal Nitre Ore", true, "Toggles whether or not to generate Nitre Ore in the Abyssal Wasteland.").getBoolean();
		generateAbyssalTinOre = cfg.get("worldgen", "Abyssal Tin Ore", true, "Toggles whether or not to generate Tin Ore in the Abyssal Wasteland").getBoolean();
		generateAbyssalCopperOre = cfg.get("worldgen", "Abyssal Copper Ore", true, "Toggles whether or not to generate Copper Ore in the Abyssal Wasteland.").getBoolean();
		generatePearlescentCoraliumOre = cfg.get("worldgen", "Pearlescent Coralium Ore", true, "Toggles whether or not to generate Pearlescent Coralium Ore in the Abyssal Wasteland.").getBoolean();
		generateLiquifiedCoraliumOre = cfg.get("worldgen", "Liquified Coralium Ore", true, "Toggles whether or not to generate Liquified Coralium Ore in the Abyssal Wasteland.").getBoolean();
		shoggothLairSpawnRate = cfg.get("worldgen", "Shoggoth Lair Generation Chance: Swamps", 35, "Generation chance of a Shoggoth Lair in swamp biomes. Higher numbers decrease the chance of a Lair generating, while lower numbers increase the chance.\n[range: 0 ~ 1000, default: 30]", 0, 1000).getInt();
		coraliumOreGeneration = cfg.get("worldgen", "Coralium Ore Generation", new int[] {12, 8, 40}, "Coralium Ore generation. First parameter is the vein count, secound is amount of ores per vein, third is max height for it to generate at. Coralium Ore generation in swamps are half as common as oceans.").getIntList();
		shoggothLairSpawnRateRivers = cfg.get("worldgen", "Shoggoth Lair Generation Chance: Rivers", 30, "Generation chance of a Shoggoth Lair in river biomes. Higher numbers decrease the chance of a Lair generating, while lower numbers increase the chance.\n[range: 0 ~ 1000, default: 30]", 0, 1000).getInt();
		useAmplifiedWorldType = cfg.get("worldgen", "Use Amplified World Type", true, "Toggles whether or not the dimensions will have their terrain affected by the Amplified world type.").getBoolean();
		generateStatuesInLairs = cfg.get("worldgen", "Generate Statues In Lairs", true, "Toggles whether or not statues have a chance of generating inside a Shoggoth Lair.").getBoolean();

		oreGenDimBlacklist = cfg.get("worldgen", "Ore Generation Dimension Blacklist", new int[0], "Dimension IDs added to this list won't have any of AbyssalCraft's Overworld ores (Coralium, Nitre) generating in them. This only affects surface worlds (dimensions that handle world generation like the Overworld does).").getIntList();
		structureGenDimBlacklist = cfg.get("worldgen", "Structure Generation Dimension Blacklist", new int[0], "Dimension IDs added to this list won't have any of AbyssalCraft's Overworld structures (Darklands structures, Shoggoth lairs) generating in them. This only affects surface worlds (dimensions that handle world generation like the Overworld does).").getIntList();

		abyssalZombieBlacklist = cfg.get("item_blacklist", "Abyssal Zombie Item Blacklist", new String[]{"minecraft:rotten_flesh","minecraft:bone","abyssalcraft:antiflesh","abyssalcraft:corflesh","abyssalcraft:anticorflesh"}, "Items/Blocks added to this list won't be picked up by Abyssal Zombies. Format: modid:name:meta, where meta is optional.").getStringList();
		depthsGhoulBlacklist = cfg.get("item_blacklist", "Depths Ghoul Item Blacklist", new String[]{"minecraft:rotten_flesh","minecraft:bone","abyssalcraft:antiflesh","abyssalcraft:corflesh","abyssalcraft:anticorflesh"}, "Items/Blocks added to this list won't be picked up by Depths Ghouls. Format: modid:name:meta, where meta is optional.").getStringList();
		antiAbyssalZombieBlacklist = cfg.get("item_blacklist", "Abyssal Anti-Zombie Item Blacklist", new String[]{"minecraft:rotten_flesh","minecraft:bone","abyssalcraft:antiflesh","abyssalcraft:corflesh","abyssalcraft:anticorflesh"}, "Items/Blocks added to this list won't be picked up by Abyssal Anti-Zombies. Format: modid:name:meta, where meta is optional.").getStringList();
		antiGhoulBlacklist = cfg.get("item_blacklist", "Anti-Ghoul Item Blacklist", new String[]{"minecraft:rotten_flesh","minecraft:bone","abyssalcraft:antiflesh","abyssalcraft:corflesh","abyssalcraft:anticorflesh"}, "Items/Blocks added to this list won't be picked up by Anti-Ghouls. Format: modid:name:meta, where meta is optional.").getStringList();
		omotholGhoulBlacklist = cfg.get("item_blacklist", "Omothol Ghoul Item Blacklist", new String[]{"minecraft:rotten_flesh","minecraft:bone","abyssalcraft:antiflesh","abyssalcraft:corflesh","abyssalcraft:anticorflesh"}, "Items/Blocks added to this list won't be picked up by Omothol Ghouls. Format: modid:name:meta, where meta is optional.").getStringList();
		antiPlayersPickupLoot = cfg.get("item_blacklist", "Anti-Players Can Pick Up Loot", true, "Toggles whether or not Anti-Players can pick up loot. You really should just blacklist them in whatever mob spawner/duplicator instead.").getBoolean();

		breakLogic = cfg.get("silly_settings", "Liquid Coralium Physics", false, "Set true to allow the Liquid Coralium to break the laws of physics in terms of movement").getBoolean();
		nuclearAntimatterExplosions = cfg.get("silly_settings", "Nuclear Antimatter Explosions", false, "Take a wild guess what this does... Done guessing? Yeah, makes the antimatter explosions more genuine by making them go all nuclear. Recommended to not enable unless you want chaos and destruction.").getBoolean();
		jzaharBreaksFourthWall = cfg.get("silly_settings", "J'zahar Can Break The Fourth Wall", true, "Toggles whether or not J'zahar can break the fourth wall.").getBoolean();
		odbExplosionSize = cfg.get("silly_settings", "ODB Explosion Size", 160, "The explosion size of an ODB. 400 is the rough limit if running on 2GB of RAM, anything above that will require more allocated memory, and could crash the game or freeze it for longer periods of time.", 80, 800).getInt();
		antimatterExplosionSize = cfg.get("silly_settings", "Antimatter Explosion Size", 80, "The explosion size of antimatter mobs colliding with their normal counterpart if Nucler Antimatter Explosions is enabled.", 40, 200).getInt();

		darkstone_brick_slab = cfg.get("blocks", "Darkstone Brick Slab", true).getBoolean();
		darkstone_cobblestone_slab = cfg.get("blocks", "Darkstone Cobblestone Slab", true).getBoolean();
		darkstone_brick_stairs = cfg.get("blocks", "Darkstone Brick Stairs", true).getBoolean();
		darkstone_cobblestone_stairs = cfg.get("blocks", "Darkstone Cobblestone Stairs", true).getBoolean();
		darkstone_slab = cfg.get("blocks", "Darkstone Slab", true).getBoolean();
		darklands_oak_slab = cfg.get("blocks", "Darklands Oak Slab", true).getBoolean();
		darklands_oak_stairs = cfg.get("blocks", "Darklands Oak Stairs", true).getBoolean();
		abyssal_stone_brick_slab = cfg.get("blocks", "Abyssal Stone Brick Slab", true).getBoolean();
		abyssal_stone_brick_stairs = cfg.get("blocks", "Abyssal Stone Brick Stairs", true).getBoolean();
		coralium_stone_brick_slab = cfg.get("blocks", "Coralium Stone Brick Slab", true).getBoolean();
		coralium_stone_brick_stairs = cfg.get("blocks", "Coralium Stone Brick Stairs", true).getBoolean();
		dreadstone_brick_slab = cfg.get("blocks", "Dreadstone Brick Slab", true).getBoolean();
		dreadstone_brick_stairs = cfg.get("blocks", "Dreadstone Brick Stairs", true).getBoolean();
		abyssalnite_stone_brick_slab = cfg.get("blocks", "Abyssalnite Stone Brick Slab", true).getBoolean();
		abyssalnite_stone_brick_stairs = cfg.get("blocks", "Abyssalnite Stone Brick Stairs", true).getBoolean();
		ethaxium_brick_slab = cfg.get("blocks", "Ethaxium Brick Slab", true).getBoolean();
		ethaxium_brick_stairs = cfg.get("blocks", "Ethaxium Brick Stairs", true).getBoolean();
		abyssal_cobblestone_slab = cfg.get("blocks", "Abyssal Cobblestone Slab", true).getBoolean();
		abyssal_cobblestone_stairs = cfg.get("blocks", "Abyssal Cobblestone Stairs", true).getBoolean();
		coralium_cobblestone_slab = cfg.get("blocks", "Coralium Cobblestone Slab", true).getBoolean();
		coralium_cobblestone_stairs = cfg.get("blocks", "Coralium Cobblestone Stairs", true).getBoolean();
		dreadstone_cobblestone_slab = cfg.get("blocks", "Dreadstone Cobblestone Slab", true).getBoolean();
		dreadstone_cobblestone_stairs = cfg.get("blocks", "Dreadstone Cobblestone Stairs", true).getBoolean();
		abyssalnite_cobblestone_slab = cfg.get("blocks", "Abyssalnite Cobblestone Slab", true).getBoolean();
		abyssalnite_cobblestone_stairs = cfg.get("blocks", "Abyssalnite Cobblestone Stairs", true).getBoolean();
		darkstone_cobblestone_wall = cfg.get("blocks", "Darkstone Cobblestone Wall", true).getBoolean();
		abyssal_cobbblestone_wall = cfg.get("blocks", "Abyssal Cobblestone Wall", true).getBoolean();
		coralium_cobblestone_wall = cfg.get("blocks", "Coralium Cobblestone Wall", true).getBoolean();
		dreadstone_cobblestone_wall = cfg.get("blocks", "Dreadstone Cobblestone Wall", true).getBoolean();
		abyssalnite_cobblestone_wall = cfg.get("blocks", "Abyssalnite Cobblestone Wall", true).getBoolean();

		no_dreadlands_spread = cfg.get("wet_noodle", "Disable Dreadlands Spread", false, "Toggles whether or not the spreading of Dreadlands through the Dread Plague is disabled. Cha'garoth remains unaffected by this (because he doesn't naturally spawn outside of the dimension).").getBoolean();
		no_acid_breaking_blocks = cfg.get("wet_noodle", "Disable Acid Projectiles Breaking Blocks", false, "Toggles whether or not the acid projectiles Lesser Shoggoths spit can break blocks.").getBoolean();
		no_spectral_dragons = cfg.get("wet_noodle", "Disable Spectral Dragons", false, "Toggles whether or not Spectral Dragons should spawn in the Abyssal Wasteland.").getBoolean();
		no_projectile_damage_immunity = cfg.get("wet_noodle", "Disable Projectile Damage Immunity", false, "Toggles whether or not Lesser Shoggoths are immune to projectile damage.").getBoolean();
		no_disruptions = cfg.get("wet_noodle", "Disable Disruptions", false, "Toggles whether or not statues or failing rituals will trigger disruptions.").getBoolean();
		no_black_holes = cfg.get("wet_noodle", "Disable Black Holes", false, "Toggles whether or not J'zahar can use his attack that creates a black hole.").getBoolean();
		no_odb_explosions = cfg.get("wet_noodle", "Disable ODB Explosions", false, "Toggles whether or not Oblivion Deathbombs (or ODB Cores) can explode.").getBoolean();

		String[] transformations = cfg.getStringList("Demon Animal Transformations", Configuration.CATEGORY_GENERAL, new String[0], "Mobs added to this list will have a chance of spawning a Demon Animal of choice on death."
				+ "\nFormat: entityid;demonanimal;chance \nwhere entityid is the String used in the /summon command\n demonanimal is a Integer representing the Demon Animal to spawn (0 = Demon Pig, 1 = Demon Cow, 2 = Demon Chicken, 3 = Demon Sheep)"
				+ "\nchance is a decimal number representing the chance (optional, can be left out) of the Demon Animal being spawned (0.2 would mean a 20% chance, defaults to 100% if not set");

		hcdarkness_aw = cfg.get("mod_compat", "Hardcore Darkness: Abyssal Wasteland", true, "Toggles whether or not the Abyssal Wasteland should be darker if Hardcore Darkness is installed.").getBoolean();
		hcdarkness_dl = cfg.get("mod_compat", "Hardcore Darkness: Dreadlands", true, "Toggles whether or not the Dreadlands should be darker if Hardcore Darkness is installed.").getBoolean();
		hcdarkness_omt = cfg.get("mod_compat", "Hardcore Darkness: Omothol", true, "Toggles whether or not Omothol should be darker if Hardcore Darkness is installed.").getBoolean();
		hcdarkness_dr = cfg.get("mod_compat", "Hardcore Darkness: Dark Realm", true, "Toggles whether or not the Dark Realm should be darker if Hardcore Darkness is installed.").getBoolean();

		foodstuff = cfg.get("modules", "Enable Foodstuffs", true, "Set to false to disable Abyssalcraft Food.").getBoolean();
		upgrade_kits = cfg.get("modules", "Enable Upgrade Kits", true, "Set to false to disable Upgrade Kits.").getBoolean();
		plague_enchantments = cfg.get("modules", "Enable Plague Enchantments", true, "Set to false to disable the Coralium and Dread Enchantments.").getBoolean();
		crystal_rework = cfg.get("modules", "Crystal Rework", true, "Set to false to revert to the old crystal recipes (and them being fuels).").getBoolean();

		entropy_spell = cfg.get("spells", "Entropy", true, "Set to false to disable the Entropy spell.").getBoolean();
		life_drain_spell = cfg.get("spells", "Life Drain", true, "Set to false to disable the Life Drain spell.").getBoolean();
		mining_spell = cfg.get("spells", "Mining", true, "Set to false to disable the Mining spell.").getBoolean();
		grasp_of_cthulhu_spell = cfg.get("spells", "Grasp of Cthulhu", true, "Set to false to disable the Grasp of Cthulhu spell.").getBoolean();
		invisibility_spell = cfg.get("spells", "Hide from the Eye", true, "Set to false to disable the Hide from the Eye spell.").getBoolean();
		detachment_spell = cfg.get("spells", "Detachment", true, "Set to false to disable the Detachment spell.").getBoolean();
		steal_vigor_spell = cfg.get("spells", "Steal Vigor", true, "Set to false to disable the Steal Vigor spell.").getBoolean();
		sirens_song_spell = cfg.get("spells", "Siren's Song§", true, "Set to false to disable the Siren's Song spell.").getBoolean();
		undeath_to_dust_spell = cfg.get("spells", "Undeath to Dust", true, "Set to false to disable the Undeath to Dust spell.").getBoolean();
		ooze_removal_spell = cfg.get("spells", "Ooze Removal", true, "Set to false to disable the Ooze Removal spell.").getBoolean();
		teleport_hostile_spell = cfg.get("spells", "Sacrificial Interdiction", true, "Set to false to disable the Sacrificial Interdiction spell.").getBoolean();

		evilAnimalSpawnWeight = MathHelper.clamp(evilAnimalSpawnWeight, 0, 100);
		portalCooldown = MathHelper.clamp(portalCooldown, 10, 300);
		demonAnimalSpawnWeight = MathHelper.clamp(demonAnimalSpawnWeight, 0, 100);
		shoggothLairSpawnRate = MathHelper.clamp(shoggothLairSpawnRate, 0, 1000);
		darkWeight1 = MathHelper.clamp(darkWeight1, 0, 100);
		darkWeight2 = MathHelper.clamp(darkWeight2, 0, 100);
		darkWeight3 = MathHelper.clamp(darkWeight3, 0, 100);
		darkWeight4 = MathHelper.clamp(darkWeight4, 0, 100);
		darkWeight5 = MathHelper.clamp(darkWeight5, 0, 100);
		coraliumWeight = MathHelper.clamp(coraliumWeight, 0, 100);
		damageAmpl = MathHelper.clamp(damageAmpl, 1, 10);
		depthsHelmetOverlayOpacity = MathHelper.clamp(depthsHelmetOverlayOpacity, 0.5D, 1.0D);
		if(coraliumOreGeneration.length != 3)
			coraliumOreGeneration = new int[] {12, 8, 40};
		acidResistanceHardness = MathHelper.clamp(acidResistanceHardness, 2.1D, 51.0D);
		acidSpitFrequency = MathHelper.clamp(acidSpitFrequency, 0, 300);
		knowledgeSyncDelay = MathHelper.clamp(knowledgeSyncDelay, 20, 400);
		odbExplosionSize = MathHelper.clamp(odbExplosionSize, 80, 800);
		antimatterExplosionSize = MathHelper.clamp(antimatterExplosionSize, 40, 200);
		enchantmentMaxLevel = MathHelper.clamp(enchantmentMaxLevel, 1, 100);

		demon_transformations.clear();

		for(String str : transformations)
			if(str.length() > 0){
				String[] stuff = str.split(";");
				if(stuff.length >= 2)
					demon_transformations.put(new ResourceLocation(stuff[0]), new Tuple(Integer.valueOf(stuff[1]), stuff.length == 3 ? Float.valueOf(stuff[2]) : 1));
				else ACLogger.severe("Invalid Demon Animal Transformation: {}", str);
			}

		clearImmunityLists();

		for(String str : dreadPlagueImmunityList)
			addDreadPlagueImmunity(str);
		for(String str : dreadPlagueCarrierList)
			addDreadPlagueCarrier(str);
		for(String str : coraliumPlagueImmunityList)
			addCoraliumPlagueImmunity(str);
		for(String str : coraliumPlagueCarrierList)
			addCoraliumPlagueCarrier(str);

		if(cfg.hasChanged())
			cfg.save();
	}

	private void constructBlacklists(){
		if(abyssalZombieBlacklist.length > 0)
			for(String str : abyssalZombieBlacklist)
				if(str.length() > 0){
					String[] stuff = str.split(":");
					Item item = Item.REGISTRY.getObject(new ResourceLocation(stuff[0], stuff[1]));
					if(item != null)
						abyssal_zombie_blacklist.add(new ItemStack(item, 1, stuff.length == 3 ? Integer.valueOf(stuff[2]) : OreDictionary.WILDCARD_VALUE));
					else ACLogger.severe("{} is not a valid Item!", str);
				}
		if(depthsGhoulBlacklist.length > 0)
			for(String str : depthsGhoulBlacklist)
				if(str.length() > 0){
					String[] stuff = str.split(":");
					Item item = Item.REGISTRY.getObject(new ResourceLocation(stuff[0], stuff[1]));
					if(item != null)
						depths_ghoul_blacklist.add(new ItemStack(item, 1, stuff.length == 3 ? Integer.valueOf(stuff[2]) : OreDictionary.WILDCARD_VALUE));
					else ACLogger.severe("{} is not a valid Item!", str);
				}
		if(antiAbyssalZombieBlacklist.length > 0)
			for(String str : antiAbyssalZombieBlacklist)
				if(str.length() > 0){
					String[] stuff = str.split(":");
					Item item = Item.REGISTRY.getObject(new ResourceLocation(stuff[0], stuff[1]));
					if(item != null)
						anti_abyssal_zombie_blacklist.add(new ItemStack(item, 1, stuff.length == 3 ? Integer.valueOf(stuff[2]) : OreDictionary.WILDCARD_VALUE));
					else ACLogger.severe("{} is not a valid Item!", str);
				}
		if(antiGhoulBlacklist.length > 0)
			for(String str : antiGhoulBlacklist)
				if(str.length() > 0){
					String[] stuff = str.split(":");
					Item item = Item.REGISTRY.getObject(new ResourceLocation(stuff[0], stuff[1]));
					if(item != null)
						anti_ghoul_blacklist.add(new ItemStack(item, 1, stuff.length == 3 ? Integer.valueOf(stuff[2]) : OreDictionary.WILDCARD_VALUE));
					else ACLogger.severe("{} is not a valid Item!", str);
				}
		if(omotholGhoulBlacklist.length > 0)
			for(String str : omotholGhoulBlacklist)
				if(str.length() > 0){
					String[] stuff = str.split(":");
					Item item = Item.REGISTRY.getObject(new ResourceLocation(stuff[0], stuff[1]));
					if(item != null)
						omothol_ghoul_blacklist.add(new ItemStack(item, 1, stuff.length == 3 ? Integer.valueOf(stuff[2]) : OreDictionary.WILDCARD_VALUE));
					else ACLogger.severe("{} is not a valid Item!", str);
				}
	}

	/**
	 * Checks whether or not an Item is blacklisted for the specified Entity
	 * @param entity Entity to check
	 * @param stack ItemStack to check
	 * @return True if the Item is blacklisted, otherwise false
	 */
	public boolean isItemBlacklisted(Entity entity, ItemStack stack){
		if(entity instanceof EntityAbyssalZombie)
			if(!abyssal_zombie_blacklist.isEmpty())
				for(ItemStack stack2 : abyssal_zombie_blacklist)
					if(areStacksEqual(stack2, stack)) return true;
		if(entity instanceof EntityDepthsGhoul)
			if(!depths_ghoul_blacklist.isEmpty())
				for(ItemStack stack2 : depths_ghoul_blacklist)
					if(areStacksEqual(stack2, stack)) return true;
		if(entity instanceof EntityAntiAbyssalZombie)
			if(!anti_abyssal_zombie_blacklist.isEmpty())
				for(ItemStack stack2 : anti_abyssal_zombie_blacklist)
					if(areStacksEqual(stack2, stack)) return true;
		if(entity instanceof EntityAntiGhoul)
			if(!anti_ghoul_blacklist.isEmpty())
				for(ItemStack stack2 : anti_ghoul_blacklist)
					if(areStacksEqual(stack2, stack)) return true;
		if(entity instanceof EntityOmotholGhoul)
			if(!omothol_ghoul_blacklist.isEmpty())
				for(ItemStack stack2 : omothol_ghoul_blacklist)
					if(areStacksEqual(stack2, stack)) return true;
		return false;
	}

	private boolean areStacksEqual(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null || stack2 == null) return false;
		return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| stack1.getItemDamage() == stack2.getItemDamage());
	}

	/**
	 * Checks if an Entity is blacklisted from being captured with a Interdimensional Cage
	 * @param entity Entity to check
	 * @return True if the Entity is blacklisted, otherwise false
	 */
	public boolean isEntityBlacklisted(Entity entity){
		ResourceLocation key = EntityList.getKey(entity);
		if(key != null) {
			String id = key.toString();
			for(String str : interdimensionalCageBlacklist)
				if(str.equals(id))
					return true;
		}
		return false;
	}

	/**
	 * Selects a random dimension to transfer the Entity to
	 * @param currentdim The current dimension
	 * @param rand RNG
	 * @return The ID of a dimension that isn't the current one
	 */
	public int getRandomDimension(int currentdim, Random rand) {
		int id = 0, tries = 0;

		do {
			tries++;
			id = blackHoleDimlist[rand.nextInt(blackHoleDimlist.length)];
		} while((id == currentdim || isDimBlacklisted(id)) && tries < 20);

		return tries == 20 ? ACLib.dark_realm_id : id;
	}

	private boolean isDimBlacklisted(int id) {
		for(int check : blackHoleBlacklist)
			if(check == id)
				return true;
		return false;
	}

	/**
	 * Checks if a dimension is blacklisted from generating Overworld ores
	 * @param id Dimension ID to check
	 * @return True if the dimension is blacklisted, otherwise false
	 */
	public boolean isDimBlacklistedFromOreGen(int id) {
		for(int check : oreGenDimBlacklist)
			if(check == id)
				return true;
		return false;
	}

	/**
	 * Checks if a dimension is blacklisted from generating Overworld structures
	 * @param id Dimension ID to check
	 * @return True if the dimension is blacklisted, otherwise false
	 */
	public boolean isDimBlacklistedFromStructureGen(int id) {
		for(int check : structureGenDimBlacklist)
			if(check == id)
				return true;
		return false;
	}

	/**
	 * Adds the entity to a list of entities considered immune to the Dread Plague
	 * @param entity Entity ID string
	 */
	private static void addDreadPlagueImmunity(String entity) {
		if(EntityList.isRegistered(new ResourceLocation(entity)))
			dread_immunity.add(entity);
	}

	/**
	 * Adds the entity to a list of entities considered carriers of the Dread Plague<br>
	 * (this also adds it to the immunity list)
	 * @param entity Entity ID string
	 */
	private static void addDreadPlagueCarrier(String entity) {
		addDreadPlagueImmunity(entity);
		if(EntityList.isRegistered(new ResourceLocation(entity)))
			dread_carriers.add(entity);
	}

	/**
	 * Adds the entity to a list of entities considered immune to the Coralium Plague
	 * @param entity Entity ID string
	 */
	private static void addCoraliumPlagueImmunity(String entity) {
		if(EntityList.isRegistered(new ResourceLocation(entity)))
			coralium_immunity.add(entity);
	}

	/**
	 * Adds the entity to a list of entities considered carriers of the Coralium Plague<br>
	 * (this also adds it to the immunity list)
	 * @param entity Entity ID string
	 */
	private static void addCoraliumPlagueCarrier(String entity) {
		addCoraliumPlagueImmunity(entity);
		if(EntityList.isRegistered(new ResourceLocation(entity)))
			coralium_carriers.add(entity);
	}

	private static void clearImmunityLists() {
		dread_immunity.clear();
		dread_carriers.clear();
		coralium_immunity.clear();
		coralium_carriers.clear();
	}

	public boolean isImmuneOrCarrier(String entity, int list) {

		switch(list) {
		case 0:
			return dread_immunity.contains(entity);
		case 1:
			return dread_carriers.contains(entity);
		case 2:
			return coralium_immunity.contains(entity);
		case 3:
			return coralium_carriers.contains(entity);
		default:
			return false;
		}
	}

	private String getSupporterList(){
		BufferedReader nameFile;
		String names = "";
		try {
			nameFile = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/supporters.txt").openStream()));

			names = nameFile.readLine();
			nameFile.close();

		} catch (IOException e) {
			ACLogger.severe("Failed to fetch supporter list, using local version!");
			names = "Gentlemangamer2015";
		}

		return names;
	}
}
