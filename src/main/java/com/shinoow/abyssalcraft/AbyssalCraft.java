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
package com.shinoow.abyssalcraft;

import java.io.*;
import java.net.URL;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.*;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.*;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.item.ItemUpgradeKit;
import com.shinoow.abyssalcraft.common.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.*;
import com.shinoow.abyssalcraft.common.blocks.itemblock.*;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.enchantments.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.items.*;
import com.shinoow.abyssalcraft.common.items.armor.*;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.potion.*;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.MapGenAbyStronghold;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.StructureAbyStrongholdPieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMinePieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMineStart;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.common.util.RitualUtil;
import com.shinoow.abyssalcraft.common.world.AbyssalCraftWorldGenerator;
import com.shinoow.abyssalcraft.common.world.WorldProviderAbyss;
import com.shinoow.abyssalcraft.common.world.WorldProviderDarkRealm;
import com.shinoow.abyssalcraft.common.world.WorldProviderDreadlands;
import com.shinoow.abyssalcraft.common.world.WorldProviderOmothol;
import com.shinoow.abyssalcraft.common.world.biome.*;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version,dependencies = "required-after:Forge@[forgeversion,);after:JEI@[2.25,)", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory", acceptedMinecraftVersions = "[1.8.9]", updateJSON = "https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/version.json")
public class AbyssalCraft {

	public static final String version = "1.9.0-pre-4";
	public static final String modid = "abyssalcraft";
	public static final String name = "AbyssalCraft";

	@Metadata(AbyssalCraft.modid)
	public static ModMetadata metadata;

	@Instance(AbyssalCraft.modid)
	public static AbyssalCraft instance = new AbyssalCraft();

	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.client.ClientProxy",
			serverSide = "com.shinoow.abyssalcraft.common.CommonProxy")
	public static CommonProxy proxy;

	public static Map<String, Integer> stringtoIDMapping = new HashMap<String, Integer>();

	public static Configuration cfg;

	public static Fluid CFluid, antifluid;

	public static final Fluid LIQUID_CORALIUM = new Fluid("liquidcoralium", new ResourceLocation("abyssalcraft", "blocks/cwater_still"),
			new ResourceLocation("abyssalcraft", "blocks/cwater_flow")).setDensity(3000).setTemperature(350);
	public static final Fluid LIQUID_ANTIMATTER = new Fluid("liquidantimatter", new ResourceLocation("abyssalcraft", "blocks/anti_still"),
			new ResourceLocation("abyssalcraft", "blocks/anti_flow")).setDensity(4000).setViscosity(1500).setTemperature(100);

	public static Achievement mineAby, killghoul, enterabyss, killdragon, summonAsorah,
	killAsorah, enterdreadlands, killdreadguard, ghoulhead, petehead, wilsonhead, orangehead,
	mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth, enterOmothol,
	enterDarkRealm, killJzahar, killOmotholelite, locateJzahar, necro, necrou1, necrou2, necrou3,
	abyssaln, ritual, ritualSummon, ritualCreate, shadowGems, mineAbyOres, mineDread, dreadium,
	eth, makeTransmutator, makeCrystallizer, makeMaterializer, makeCrystalBag, makeEngraver,
	ritualBreed, ritualPotion, ritualPotionAoE, ritualInfusion, shoggothInfestation;

	public static Block Darkstone, Darkstone_brick, Darkstone_cobble, DSGlow, Darkbrickslab1,
	Darkbrickslab2, Darkcobbleslab1, Darkcobbleslab2, Darkgrass, DBstairs, DCstairs, DLTLeaves,
	DLTLog, DLTSapling, abystone, abybrick, abyslab1, abyslab2, abystairs, Coraliumore, abyore,
	abyfence, DSCwall, ODB, abyblock, CoraliumInfusedStone, ODBcore, Crate, portal, Darkstoneslab1,
	Darkstoneslab2, Coraliumfire, DSbutton, DSpplate, DLTplank, DLTbutton, DLTpplate, DLTstairs,
	DLTslab1, DLTslab2, Cwater, PSDL, AbyCorOre, corblock, Altar, Abybutton, Abypplate, DSBfence,
	DLTfence, dreadstone, abydreadstone, abydreadore, dreadore, dreadbrick, abydreadbrick, dreadgrass,
	dreadlog, dreadleaves, dreadsapling, dreadplanks, dreadportal, dreadfire, dreadbrickfence,
	dreadbrickstairs, dreadbrickslab1, dreadbrickslab2, abydreadbrickfence, abydreadbrickstairs,
	abydreadbrickslab1, abydreadbrickslab2, DGhead, Phead, Whead, Ohead,anticwater,cstone,
	cstonebrick, cstonebrickfence, cstonebrickstairs, cstonebrickslab1, cstonebrickslab2,
	cstonebutton, cstonepplate, dreadaltartop, dreadaltarbottom, crystallizer, crystallizer_on,
	dreadiumblock, transmutator, transmutator_on, dreadguardspawner, chagarothspawner,
	chagarothfistspawner, DrTfence, nitreOre, AbyIroOre, AbyGolOre, AbyDiaOre, AbyNitOre, AbyTinOre,
	AbyCopOre, AbyPCorOre, AbyLCorOre, solidLava, ethaxium, ethaxiumbrick, ethaxiumpillar, ethaxiumstairs,
	ethaxiumslab1, ethaxiumslab2, ethaxiumfence, omotholstone, ethaxiumblock, omotholportal, omotholfire,
	engraver, house, materializer, darkethaxiumbrick, darkethaxiumpillar, darkethaxiumstairs,
	darkethaxiumslab1, darkethaxiumslab2, darkethaxiumfence, ritualaltar, ritualpedestal, shoggothBlock,
	cthulhuStatue, hasturStatue, jzaharStatue, azathothStatue, nyarlathotepStatue, yogsothothStatue,
	shubniggurathStatue, monolithStone, shoggothBiomass, energyPedestal, monolithPillar, sacrificialAltar,
	tieredEnergyPedestal, tieredSacrificialAltar;

	//Overworld biomes
	public static BiomeGenBase Darklands, DarklandsForest, DarklandsPlains, DarklandsHills,
	DarklandsMountains, corswamp;
	//Abyssal Wastelands biome
	public static BiomeGenBase Wastelands;
	//Dreadlands biomes
	public static BiomeGenBase Dreadlands, AbyDreadlands, ForestDreadlands, MountainDreadlands;
	//Omothol biome
	public static BiomeGenBase omothol;
	//Dark Realm biome
	public static BiomeGenBase darkRealm;

	//"secret" dev stuff
	public static Item devsword;
	//misc items
	public static Item OC, Staff, gatewayKey, Cbucket, PSDLfinder, EoA, gatewayKeyDL,
	cbrick, cudgel, carbonCluster, denseCarbonCluster, methane, nitre, sulfur, gatewayKeyJzh,
	tinIngot, copperIngot, lifeCrystal, shoggothFlesh, eldritchScale, omotholFlesh, necronomicon,
	necronomicon_cor, necronomicon_dre, necronomicon_omt, abyssalnomicon, crystalbag_s, crystalbag_m,
	crystalbag_l, crystalbag_h, nugget, essence, skin, charm, cthulhuCharm, hasturCharm, jzaharCharm,
	azathothCharm, nyarlathotepCharm, yogsothothCharm, shubniggurathCharm;
	//coin stuff
	public static Item coin, cthulhuCoin, elderCoin, jzaharCoin, engravingBlank, engravingCthulhu, engravingElder, engravingJzahar,
	hasturCoin, azathothCoin, nyarlathotepCoin, yogsothothCoin, shubniggurathCoin, engravingHastur, engravingAzathoth, engravingNyarlathotep,
	engravingYogsothoth, engravingShubniggurath;
	//crystals
	public static Item crystal, crystalShard;
	//shadow items
	public static Item shadowfragment, shadowshard, shadowgem, oblivionshard, soulReaper, shadowPlate, drainStaff;
	//dread items
	public static Item Dreadshard, dreadchunk, dreadiumingot, dreadfragment, dreadcloth, dreadplate, dreadblade, dreadKey;
	//abyssalnite items
	public static Item abychunk, abyingot;
	//coralium items
	public static Item Coralium, Coraliumcluster2, Coraliumcluster3, Coraliumcluster4,
	Coraliumcluster5, Coraliumcluster6, Coraliumcluster7, Coraliumcluster8, Coraliumcluster9,
	Cpearl, Corb, Cchunk, Cingot, Cplate, Corflesh, Corbone, corbow;
	//tools
	public static Item pickaxe, axe, shovel, sword, hoe, pickaxeA, axeA, shovelA, swordA, hoeA,
	Corpickaxe, Coraxe, Corshovel, Corsword, Corhoe, dreadiumpickaxe, dreadiumaxe, dreadiumshovel,
	dreadiumsword, dreadiumhoe, dreadhilt, dreadkatana, ethPickaxe, ethAxe, ethShovel, ethSword,
	ethHoe;
	//armor
	public static Item boots, helmet, plate, legs, bootsD, helmetD, plateD, legsD, Corboots,
	Corhelmet, Corplate, Corlegs, CorbootsP, CorhelmetP, CorplateP, CorlegsP, Depthsboots,
	Depthshelmet, Depthsplate, Depthslegs, dreadiumboots, dreadiumhelmet, dreadiumplate,
	dreadiumlegs, dreadiumSboots, dreadiumShelmet, dreadiumSplate, dreadiumSlegs, ethBoots,
	ethHelmet, ethPlate, ethLegs;
	//upgrade kits
	public static Item CobbleU, IronU, GoldU, DiamondU, AbyssalniteU, CoraliumU, DreadiumU, EthaxiumU;
	//foodstuffs
	public static Item MRE, ironp, chickenp, porkp, beefp, fishp, dirtyplate, friedegg, eggp,
	cloth;
	//Anti-items
	public static Item antibucket, antiBeef, antiPork, antiChicken, antiFlesh, antiBone, antiSpider_eye,
	antiCorbone, antiCorflesh;
	//Ethaxium items
	public static Item ethaxium_brick, ethaxiumIngot;

	public static Potion Cplague, Dplague, antiMatter;

	public static Enchantment coraliumE, dreadE, lightPierce, ironWall;

	public static final CreativeTabs tabBlock = new CreativeTabs("acblocks"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Darkstone);
		}
	};
	public static final CreativeTabs tabItems = new CreativeTabs("acitems"){

		@Override
		public Item getTabIconItem() {
			return OC;
		}
	};
	public static final CreativeTabs tabTools = new CreativeTabs("actools"){

		@Override
		public Item getTabIconItem() {
			return axe;
		}
	};
	public static final CreativeTabs tabCombat = new CreativeTabs("acctools"){

		@Override
		public Item getTabIconItem() {
			return sword;
		}
	};
	public static final CreativeTabs tabFood = new CreativeTabs("acfood"){

		@Override
		public Item getTabIconItem() {
			return ironp;
		}
	};
	public static final CreativeTabs tabDecoration = new CreativeTabs("acdblocks"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Crate);
		}
	};
	public static final CreativeTabs tabCrystals = new CreativeTabs("accrystals"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(crystallizer);
		}
	};
	public static final CreativeTabs tabCoins = new CreativeTabs("accoins"){

		@Override
		public Item getTabIconItem() {
			return coin;
		}
	};

	//Dimension Ids
	public static int configDimId1, configDimId2, configDimId3, configDimId4;

	public static boolean keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;

	//Biome Ids
	public static int configBiomeId1, configBiomeId2, configBiomeId3, configBiomeId4,
	configBiomeId5, configBiomeId6, configBiomeId7, configBiomeId8, configBiomeId9,
	configBiomeId10, configBiomeId11, configBiomeId12, configBiomeId13;

	public static boolean dark1, dark2, dark3, dark4, dark5, coralium1;
	public static boolean darkspawn1, darkspawn2, darkspawn3, darkspawn4, darkspawn5, coraliumspawn1;
	public static int darkWeight1, darkWeight2, darkWeight3, darkWeight4, darkWeight5, coraliumWeight;

	public static boolean shouldSpread, shouldInfect, breakLogic, destroyOcean, demonAnimalFire, updateC, darkness,
	particleBlock, particleEntity, hardcoreMode, useDynamicPotionIds, endAbyssalZombie;
	public static int evilAnimalSpawnRate;
	public static boolean shoggothOoze, oozeLeaves, oozeGrass, oozeGround, oozeSand, oozeRock, oozeCloth, oozeWood,
	oozeGourd, oozeIron, oozeClay;
	public static boolean generateDarklandsStructures, generateShoggothLairs, generateAbyssalWastelandPillars,
	generateAbyssalWastelandRuins, generateAntimatterLake, generateCoraliumLake, generateDreadlandsStalagmite;
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre,
	generateDreadlandsAbyssalniteOre, generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre,
	generateAbyssalDiamondOre, generateAbyssalNitreOre, generateAbyssalTinOre, generateAbyssalCopperOre,
	generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre;

	static int startEntityId = 300;

	public static final int crystallizerGuiID = 30;
	public static final int transmutatorGuiID = 31;
	public static final int engraverGuiID = 32;
	public static final int necronmiconGuiID = 33;
	public static final int crystalbagGuiID = 34;
	public static final int materializerGuiID = 35;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		ACLogger.info("Pre-initializing AbyssalCraft.");
		metadata = event.getModMetadata();
		metadata.description = metadata.description +"\n\n\u00a76Supporters: "+getSupporterList()+"\u00a7r";

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftEventHooks());
		MinecraftForge.TERRAIN_GEN_BUS.register(new AbyssalCraftEventHooks());
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		instance = this;
		AbyssalCraftAPI.internalNDHandler = new InternalNecroDataHandler();

		cfg = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();

		if(!FluidRegistry.isFluidRegistered("liquidcoralium")){
			CFluid = LIQUID_CORALIUM;
			FluidRegistry.registerFluid(CFluid);
		} else {
			ACLogger.warning("Liquid Coralium was already registered by another mod, adding ours as alternative.");
			CFluid = FluidRegistry.getFluid("liquidcoralium");
			FluidRegistry.registerFluid(LIQUID_CORALIUM);
		}

		if(!FluidRegistry.isFluidRegistered("liquidantimatter")){
			antifluid = LIQUID_ANTIMATTER;
			FluidRegistry.registerFluid(antifluid);
		} else {
			ACLogger.warning("Liquid Antimatter was already registered by another mod, adding ours as alternative.");
			antifluid = FluidRegistry.getFluid("liquidantimatter");
			FluidRegistry.registerFluid(LIQUID_ANTIMATTER);
		}

		//Blocks
		Darkstone = new BlockDarkstone().setCreativeTab(tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkstone");
		Darkstone_brick = new BlockACBasic(Material.rock, 1.65F, 12.0F, Block.soundTypeStone).setUnlocalizedName("darkstone_brick");
		Darkstone_cobble = new BlockACBasic(Material.rock, 2.2F, 12.0F, Block.soundTypeStone).setUnlocalizedName("darkstone_cobble");
		DSGlow = new BlockDSGlow().setCreativeTab(tabBlock).setStepSound(Block.soundTypeStone).setHardness(55F).setResistance(3000F).setLightLevel(1.0F).setUnlocalizedName("dsglow");
		Darkbrickslab1 = new BlockACSingleSlab(Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkbrickslab1");
		Darkbrickslab2 = new BlockACDoubleSlab(Darkbrickslab1, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkbrickslab2");
		Darkcobbleslab1 = new BlockACSingleSlab(Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F) .setResistance(12.0F).setUnlocalizedName("darkcobbleslab1");
		Darkcobbleslab2 = new BlockACDoubleSlab(Darkcobbleslab1, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F) .setResistance(12.0F).setUnlocalizedName("darkcobbleslab2");
		Darkgrass = new BlockDarklandsgrass().setStepSound(Block.soundTypeGrass).setCreativeTab(tabBlock).setHardness(0.4F).setUnlocalizedName("darkgrass");
		DBstairs = new BlockACStairs(Darkstone_brick).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("dbstairs");
		DCstairs = new BlockACStairs(Darkstone_cobble).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("dcstairs");
		DLTLeaves = new BlockDLTLeaves().setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setUnlocalizedName("dltleaves");
		DLTLog = new BlockDLTLog().setStepSound(Block.soundTypeWood).setHardness(2.0F).setResistance(1.0F).setUnlocalizedName("dltlog");
		DLTSapling = new BlockDLTSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("dltsapling");
		abystone = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone).setUnlocalizedName("abystone");
		abybrick = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone).setUnlocalizedName("abybrick");
		abyslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 2).setCreativeTab(tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setUnlocalizedName("abyslab1");
		abyslab2 = new BlockACDoubleSlab(abyslab1, Material.rock, "pickaxe", 2).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setUnlocalizedName("abyslab2");
		abystairs = new BlockACStairs(abybrick, "pickaxe", 2).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("abystairs");
		Coraliumore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("coraliumore");
		abyore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abyore");
		abyfence = new BlockACFence(Material.rock, "pickaxe", 2).setHardness(1.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("abyfence");
		DSCwall = new BlockDarkstonecobblewall(Darkstone_cobble).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("dscwall");
		Crate = new BlockCrate().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("crate");
		ODB = new BlockODB().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setUnlocalizedName("odb");
		abyblock = new IngotBlock(2).setUnlocalizedName("abyblock");
		CoraliumInfusedStone = new BlockACOre(3, 3.0F, 6.0F).setUnlocalizedName("coraliumstone");
		ODBcore = new BlockODBcore().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setUnlocalizedName("odbcore");
		portal = new BlockAbyssPortal().setUnlocalizedName("abyportal");
		Darkstoneslab1 = new BlockACSingleSlab(Material.rock).setStepSound(Block.soundTypeStone).setCreativeTab(tabBlock).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkstoneslab1");
		Darkstoneslab2 = new BlockACDoubleSlab(Darkstoneslab1, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkstoneslab2");
		Coraliumfire = new BlockCoraliumfire().setLightLevel(1.0F).setUnlocalizedName("coraliumfire");
		DSbutton = new BlockACButton(true, "DS").setHardness(0.6F).setResistance(12.0F).setUnlocalizedName("dsbutton");
		DSpplate = new BlockACPressureplate("DS", Material.rock, BlockACPressureplate.Sensitivity.MOBS).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("dspplate");
		DLTplank = new BlockACBasic(Material.wood, 2.0F, 5.0F, Block.soundTypeWood).setUnlocalizedName("dltplank");
		DLTbutton = new BlockACButton(true, "DLTplank").setHardness(0.5F).setUnlocalizedName("dltbutton");
		DLTpplate = new BlockACPressureplate("DLTplank", Material.wood, BlockACPressureplate.Sensitivity.EVERYTHING).setHardness(0.5F).setStepSound(Block.soundTypeWood).setUnlocalizedName("dltpplate");
		DLTstairs = new BlockACStairs(DLTplank).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("dltstairs");
		DLTslab1 = new BlockACSingleSlab(Material.rock).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("dltslab1");
		DLTslab2 = new BlockACDoubleSlab(DLTslab1, Material.rock).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("dltslab2");
		corblock = new IngotBlock(5).setUnlocalizedName("corblock");
		PSDL = new BlockPSDL().setHardness(50.0F).setResistance(3000F).setCreativeTab(tabDecoration).setUnlocalizedName("psdl");
		AbyCorOre = new BlockACOre(3, 3.0F, 6.0F).setUnlocalizedName("abycorore");
		Altar = new BlockAltar().setStepSound(Block.soundTypeStone).setHardness(4.0F).setResistance(300.0F).setUnlocalizedName("altar");
		Abybutton = new BlockACButton(false, "pickaxe", 2, "AS").setHardness(0.8F).setResistance(12.0F).setUnlocalizedName("abybutton");
		Abypplate = new BlockACPressureplate("AS", Material.rock, BlockACPressureplate.Sensitivity.MOBS, "pickaxe", 2).setHardness(0.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("abypplate");
		DSBfence = new BlockACFence(Material.rock).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("dsbfence");
		DLTfence = new BlockACFence(Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("dltfence");
		dreadore = new BlockACOre(4, 2.5F, 20.0F).setUnlocalizedName("dreadore");
		abydreadore = new BlockACOre(4, 2.5F, 20.0F).setUnlocalizedName("abydreadore");
		dreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setUnlocalizedName("dreadbrick");
		abydreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setUnlocalizedName("abydreadbrick");
		dreadlog = new BlockDreadLog().setHardness(2.0F).setResistance(12.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("dreadlog");
		dreadleaves = new BlockDreadLeaves().setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setUnlocalizedName("dreadleaves");
		dreadsapling = new BlockDreadSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("dreadsapling");
		dreadplanks = new BlockACBasic(Material.wood, 2.0F, 5.0F, Block.soundTypeWood).setUnlocalizedName("dreadplanks");
		dreadportal = new BlockDreadlandsPortal().setUnlocalizedName("dreadportal");
		dreadfire = new BlockDreadFire().setLightLevel(1.0F).setUnlocalizedName("dreadfire");
		DGhead = new BlockDGhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setUnlocalizedName("dghead");
		Cwater = new BlockCLiquid().setResistance(500.0F).setLightLevel(1.0F).setUnlocalizedName("cwater");
		dreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setUnlocalizedName("dreadstone");
		abydreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setUnlocalizedName("abydreadstone");
		dreadgrass = new BlockDreadGrass().setHardness(0.4F).setStepSound(Block.soundTypeGrass).setUnlocalizedName("dreadgrass");
		Phead = new BlockPhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setUnlocalizedName("phead");
		Whead = new BlockWhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setUnlocalizedName("whead");
		Ohead = new BlockOhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setUnlocalizedName("ohead");
		dreadbrickstairs = new BlockACStairs(dreadbrick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("dreadbrickstairs");
		dreadbrickfence = new BlockACFence(Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("dreadbrickfence");
		dreadbrickslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("dreadbrickslab1");
		dreadbrickslab2 = new BlockACDoubleSlab(dreadbrickslab1, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("dreadbrickslab2");
		abydreadbrickstairs = new BlockACStairs(abydreadbrick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("abydreadbrickstairs");
		abydreadbrickfence = new BlockACFence(Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("abydreadbrickfence");
		abydreadbrickslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("abydreadbrickslab1");
		abydreadbrickslab2 = new BlockACDoubleSlab(abydreadbrickslab1, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("abydreadbrickslab2");
		anticwater = new BlockAntiliquid().setResistance(500.0F).setLightLevel(0.5F).setUnlocalizedName("antiwater");
		cstone = new BlockCoraliumstone().setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("cstone");
		cstonebrick = new BlockACBasic(Material.rock, 1.5F, 10.0F, Block.soundTypeStone).setUnlocalizedName("cstonebrick");
		cstonebrickfence = new BlockACFence(Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("cstonebrickfence");
		cstonebrickslab1 = new BlockACSingleSlab(Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("cstonebrickslab1");
		cstonebrickslab2 = new BlockACDoubleSlab(cstonebrickslab1, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("cstonebrickslab2");
		cstonebrickstairs = new BlockACStairs(cstonebrick, "pickaxe", 0).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("cstonebrickstairs");
		cstonebutton = new BlockACButton(false, "cstone").setHardness(0.6F).setResistance(12.0F).setUnlocalizedName("cstonebutton");
		cstonepplate = new BlockACPressureplate("cstone", Material.rock, BlockACPressureplate.Sensitivity.MOBS).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("cstonepplate");
		dreadaltartop = new BlockDreadAltarTop().setHardness(30.0F).setResistance(300.0F).setStepSound(Block.soundTypeStone).setCreativeTab(tabDecoration).setUnlocalizedName("dreadaltartop");
		dreadaltarbottom = new BlockDreadAltarBottom().setHardness(30.0F).setResistance(300.0F).setStepSound(Block.soundTypeStone).setCreativeTab(tabDecoration).setUnlocalizedName("dreadaltarbottom");
		crystallizer = new BlockCrystallizer(false).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("crystallizer");
		crystallizer_on = new BlockCrystallizer(true).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.875F).setUnlocalizedName("crystallizer_on");
		dreadiumblock = new IngotBlock(6).setUnlocalizedName("dreadiumblock");
		transmutator = new BlockTransmutator(false).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("transmutator");
		transmutator_on = new BlockTransmutator(true).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.875F).setUnlocalizedName("transmutator_on");
		dreadguardspawner = new BlockDreadguardSpawner().setUnlocalizedName("dreadguardspawner");
		chagarothspawner = new BlockChagarothSpawner().setUnlocalizedName("chagarothspawner");
		chagarothfistspawner = new BlockChagarothFistSpawner().setUnlocalizedName("chagarothfistspawner");
		DrTfence = new BlockACFence(Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("drtfence");
		nitreOre = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("nitreore");
		AbyIroOre = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abyiroore");
		AbyGolOre = new BlockACOre(2, 5.0F, 10.0F).setUnlocalizedName("abygolore");
		AbyDiaOre = new BlockACOre(2, 5.0F, 10.0F).setUnlocalizedName("abydiaore");
		AbyNitOre = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abynitore");
		AbyTinOre = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abytinore");
		AbyCopOre = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abycopore");
		AbyPCorOre = new BlockACOre(5, 8.0F, 10.0F).setUnlocalizedName("abypcorore");
		AbyLCorOre = new BlockACOre(4, 10.0F, 12.0F).setUnlocalizedName("abylcorore");
		solidLava = new BlockSolidLava("solidlava");
		ethaxium = new BlockACBasic(Material.rock, "pickaxe", 8, 100.0F, Float.MAX_VALUE, Block.soundTypeStone).setUnlocalizedName("ethaxium");
		ethaxiumbrick = new BlockEthaxiumBrick().setUnlocalizedName("ethaxiumbrick");
		ethaxiumpillar = new BlockEthaxiumPillar().setUnlocalizedName("ethaxiumpillar");
		ethaxiumstairs = new BlockACStairs(ethaxiumbrick, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("ethaxiumbrickstairs");
		ethaxiumslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("ethaxiumbrickslab1");
		ethaxiumslab2 = new BlockACDoubleSlab(ethaxiumslab1, Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("ethaxiumbrickslab2");
		ethaxiumfence = new BlockACFence(Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("ethaxiumfence");
		omotholstone = new BlockACBasic(Material.rock, "pickaxe", 6, 10.0F, 12.0F, Block.soundTypeStone).setUnlocalizedName("omotholstone");
		ethaxiumblock = new IngotBlock(8).setResistance(Float.MAX_VALUE).setUnlocalizedName("ethaxiumblock");
		omotholportal = new BlockOmotholPortal().setUnlocalizedName("omotholportal");
		omotholfire = new BlockOmotholFire().setLightLevel(1.0F).setUnlocalizedName("omotholfire");
		engraver = new BlockEngraver().setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("engraver");
		house = new BlockHouse().setHardness(1.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeWood).setUnlocalizedName("engraver_on");
		materializer = new BlockMaterializer().setUnlocalizedName("materializer");
		darkethaxiumbrick = new BlockDarkEthaxiumBrick().setUnlocalizedName("darkethaxiumbrick");
		darkethaxiumpillar = new BlockDarkEthaxiumPillar().setUnlocalizedName("darkethaxiumpillar");
		darkethaxiumstairs = new BlockACStairs(darkethaxiumbrick, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("darkethaxiumbrickstairs");
		darkethaxiumslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("darkethaxiumbrickslab1");
		darkethaxiumslab2 = new BlockACDoubleSlab(darkethaxiumslab1, Material.rock, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("darkethaxiumbrickslab2");
		darkethaxiumfence = new BlockACFence(Material.rock, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setUnlocalizedName("darkethaxiumbrickfence");
		ritualaltar = new BlockRitualAltar().setUnlocalizedName("ritualaltar");
		ritualpedestal = new BlockRitualPedestal().setUnlocalizedName("ritualpedestal");
		shoggothBlock = new BlockShoggothOoze().setUnlocalizedName("shoggothblock");
		cthulhuStatue = new BlockCthulhuStatue().setUnlocalizedName("cthulhustatue");
		hasturStatue = new BlockHasturStatue().setUnlocalizedName("hasturstatue");
		jzaharStatue = new BlockJzaharStatue().setUnlocalizedName("jzaharstatue");
		azathothStatue = new BlockAzathothStatue().setUnlocalizedName("azathothstatue");
		nyarlathotepStatue = new BlockNyarlathotepStatue().setUnlocalizedName("nyarlathotepstatue");
		yogsothothStatue = new BlockYogsothothStatue().setUnlocalizedName("yogsothothstatue");
		shubniggurathStatue = new BlockShubniggurathStatue().setUnlocalizedName("shubniggurathstatue");
		monolithStone = new BlockACBasic(Material.rock, 6.0F, 24.0F, Block.soundTypeStone).setUnlocalizedName("monolithstone");
		shoggothBiomass = new BlockShoggothBiomass();
		energyPedestal = new BlockEnergyPedestal();
		monolithPillar = new BlockMonolithPillar();
		sacrificialAltar = new BlockSacrificialAltar();
		tieredEnergyPedestal = new BlockTieredEnergyPedestal();
		tieredSacrificialAltar = new BlockTieredSacrificialAltar();

		checkBiomeIds(true);

		//Biome
		Darklands = new BiomeGenDarklands(configBiomeId1).setColor(522674).setBiomeName("Darklands");
		Wastelands = new BiomeGenAbywasteland(configBiomeId2).setColor(522674).setBiomeName("Abyssal Wastelands").setDisableRain();
		Dreadlands = new BiomeGenDreadlands(configBiomeId3).setColor(522674).setBiomeName("Dreadlands").setDisableRain();
		AbyDreadlands = new BiomeGenAbyDreadlands(configBiomeId4).setColor(522674).setBiomeName("Purified Dreadlands").setDisableRain();
		ForestDreadlands = new BiomeGenForestDreadlands(configBiomeId5).setColor(522674).setBiomeName("Dreadlands Forest").setDisableRain();
		MountainDreadlands = new BiomeGenMountainDreadlands(configBiomeId6).setColor(522674).setBiomeName("Dreadlands Mountains").setDisableRain();
		DarklandsForest = new BiomeGenDarklandsForest(configBiomeId7).setColor(522674).setBiomeName("Darklands Forest");
		DarklandsPlains = new BiomeGenDarklandsPlains(configBiomeId8).setColor(522674).setBiomeName("Darklands Plains").setDisableRain();
		DarklandsHills = new BiomeGenDarklandsHills(configBiomeId9).setColor(522674).setBiomeName("Darklands Highland");
		DarklandsMountains = new BiomeGenDarklandsMountains(configBiomeId10).setColor(522674).setBiomeName("Darklands Mountains").setDisableRain();
		corswamp = new BiomeGenCorSwamp(configBiomeId11).setColor(522674).setBiomeName("Coralium Infested Swamp");
		omothol = new BiomeGenOmothol(configBiomeId12).setColor(5522674).setBiomeName("Omothol").setDisableRain();
		darkRealm = new BiomeGenDarkRealm(configBiomeId13).setColor(522674).setBiomeName("Dark Realm").setDisableRain();

		//"secret" dev stuff
		devsword = new AbyssalCraftTool();

		//Misc items
		OC = new ItemOC();
		Staff = new ItemStaff();
		gatewayKey = new ItemPortalPlacer();
		Cbucket = new ItemCBucket(Cwater);
		PSDLfinder = new ItemTrackerPSDL();
		EoA = new ItemEoA();
		gatewayKeyDL = new ItemPortalPlacerDL();
		cbrick = new ItemACBasic("cbrick");
		cudgel = new ItemCudgel();
		carbonCluster = new ItemACBasic("carboncluster");
		denseCarbonCluster = new ItemACBasic("densecarboncluster");
		methane = new ItemACBasic("methane");
		nitre = new ItemACBasic("nitre");
		sulfur = new ItemACBasic("sulfur");
		gatewayKeyJzh = new ItemPortalPlacerJzh();
		tinIngot = new ItemACBasic("tiningot");
		copperIngot = new ItemACBasic("copperingot");
		lifeCrystal = new ItemACBasic("lifecrystal");
		shoggothFlesh = new ItemMetadata("shoggothflesh", true, "overworld", "abyssalwasteland", "dreadlands", "omothol", "darkrealm");
		eldritchScale = new ItemACBasic("eldritchscale");
		omotholFlesh = new ItemOmotholFlesh(3, 0.3F, false);
		necronomicon = new ItemNecronomicon("necronomicon", 0);
		necronomicon_cor = new ItemNecronomicon("necronomicon_cor", 1);
		necronomicon_dre = new ItemNecronomicon("necronomicon_dre", 2);
		necronomicon_omt = new ItemNecronomicon("necronomicon_omt", 3);
		abyssalnomicon = new ItemNecronomicon("abyssalnomicon", 4);
		crystalbag_s = new ItemCrystalBag("crystalbag_small");
		crystalbag_m = new ItemCrystalBag("crystalbag_medium");
		crystalbag_l = new ItemCrystalBag("crystalbag_large");
		crystalbag_h = new ItemCrystalBag("crystalbag_huge");
		nugget = new ItemMetadata("nugget", true, "abyssalnite", "coralium", "dreadium", "ethaxium");
		essence = new ItemMetadata("essence", true, "abyssalwasteland", "dreadlands", "omothol");
		skin = new ItemMetadata("skin", true, "abyssalwasteland", "dreadlands", "omothol");

		//Coins
		coin = new ItemCoin("coin");
		cthulhuCoin = new ItemCoin("cthulhucoin");
		elderCoin = new ItemCoin("eldercoin");
		jzaharCoin = new ItemCoin("jzaharcoin");
		engravingBlank = new ItemEngraving("blank", 50).setCreativeTab(tabCoins);
		engravingCthulhu = new ItemEngraving("cthulhu", 10).setCreativeTab(tabCoins);
		engravingElder = new ItemEngraving("elder", 10).setCreativeTab(tabCoins);
		engravingJzahar = new ItemEngraving("jzahar", 10).setCreativeTab(tabCoins);
		hasturCoin = new ItemCoin("hasturcoin");
		azathothCoin = new ItemCoin("azathothcoin");
		nyarlathotepCoin = new ItemCoin("nyarlathotepcoin");
		yogsothothCoin = new ItemCoin("yogsothothcoin");
		shubniggurathCoin = new ItemCoin("shubniggurathcoin");
		engravingHastur = new ItemEngraving("hastur", 10).setCreativeTab(tabCoins);
		engravingAzathoth = new ItemEngraving("azathoth", 10).setCreativeTab(tabCoins);
		engravingNyarlathotep = new ItemEngraving("nyarlathotep", 10).setCreativeTab(tabCoins);
		engravingYogsothoth = new ItemEngraving("yogsothoth", 10).setCreativeTab(tabCoins);
		engravingShubniggurath = new ItemEngraving("shubniggurath", 10).setCreativeTab(tabCoins);

		//Charms
		charm = new ItemCharm("ritualcharm", true, null);
		cthulhuCharm = new ItemCharm("cthulhucharm", false, DeityType.CTHULHU);
		hasturCharm = new ItemCharm("hasturcharm", false, DeityType.HASTUR);
		jzaharCharm = new ItemCharm("jzaharcharm", false, DeityType.JZAHAR);
		azathothCharm = new ItemCharm("azathothcharm", false, DeityType.AZATHOTH);
		nyarlathotepCharm = new ItemCharm("nyarlathotepcharm", false, DeityType.NYARLATHOTEP);
		yogsothothCharm = new ItemCharm("yogsothothcharm", false, DeityType.YOGSOTHOTH);
		shubniggurathCharm = new ItemCharm("shubniggurathcharm", false, DeityType.SHUBNIGGURATH);

		//Ethaxium
		ethaxium_brick = new ItemACBasic("ethbrick");
		ethaxiumIngot = new ItemACBasic("ethaxiumingot");

		//anti-items
		antibucket = new ItemAntiBucket(anticwater);
		antiBeef = new ItemAntiFood("antibeef");
		antiChicken = new ItemAntiFood("antichicken");
		antiPork = new ItemAntiFood("antipork");
		antiFlesh = new ItemAntiFood("antiflesh");
		antiBone = new ItemACBasic("antibone");
		antiSpider_eye = new ItemAntiFood("antispidereye", false);
		antiCorflesh = new ItemCorflesh(0, 0, false, "anticorflesh");
		antiCorbone = new ItemCorbone(0, 0, false, "anticorbone");

		//crystals
		crystal = new ItemCrystal("crystal");
		crystalShard = new ItemCrystal("crystalshard");

		//Shadow items
		shadowfragment = new ItemACBasic("shadowfragment");
		shadowshard = new ItemACBasic("shadowshard");
		shadowgem = new ItemACBasic("shadowgem");
		oblivionshard = new ItemACBasic("oblivionshard");
		shadowPlate = new ItemACBasic("shadowplate");

		//Dread items
		Dreadshard = new ItemACBasic("dreadshard");
		dreadchunk = new ItemACBasic("dreadchunk");
		dreadiumingot = new ItemACBasic("dreadiumingot");
		dreadfragment = new ItemACBasic("dreadfragment");
		dreadcloth = new ItemACBasic("dreadcloth");
		dreadplate = new ItemACBasic("dreadplate");
		dreadblade = new ItemACBasic("dreadblade");
		dreadKey = new ItemACBasic("dreadkey");

		//Abyssalnite items
		abychunk = new ItemACBasic("abychunk");
		abyingot = new ItemACBasic("abyingot");

		//Coralium items
		Coraliumcluster2 = new ItemCoraliumcluster("ccluster2", "2");
		Coraliumcluster3 = new ItemCoraliumcluster("ccluster3", "3");
		Coraliumcluster4 = new ItemCoraliumcluster("ccluster4", "4");
		Coraliumcluster5 = new ItemCoraliumcluster("ccluster5", "5");
		Coraliumcluster6 = new ItemCoraliumcluster("ccluster6", "6");
		Coraliumcluster7 = new ItemCoraliumcluster("ccluster7", "7");
		Coraliumcluster8 = new ItemCoraliumcluster("ccluster8", "8");
		Coraliumcluster9 = new ItemCoraliumcluster("ccluster9", "9");
		Cpearl = new ItemACBasic("cpearl");
		Cchunk = new ItemACBasic("cchunk");
		Cingot = new ItemACBasic("cingot");
		Cplate = new ItemACBasic("platec");
		Coralium = new ItemACBasic("coralium");
		Corb = new ItemCorb();
		Corflesh = new ItemCorflesh(2, 0.1F, false, "corflesh");
		Corbone = new ItemCorbone(2, 0.1F, false, "corbone");
		corbow = new ItemCoraliumBow(20.0F, 0, 8, 16);

		//Tools
		pickaxe = new ItemACPickaxe(AbyssalCraftAPI.darkstoneTool, "dpick", 1);
		axe = new ItemACAxe(AbyssalCraftAPI.darkstoneTool, "daxe", 1);
		shovel = new ItemACShovel(AbyssalCraftAPI.darkstoneTool, "dshovel", 1);
		sword = new ItemACSword(AbyssalCraftAPI.darkstoneTool, "dsword");
		hoe = new ItemACHoe(AbyssalCraftAPI.darkstoneTool, "dhoe");
		pickaxeA = new ItemACPickaxe(AbyssalCraftAPI.abyssalniteTool, "apick", 4, EnumChatFormatting.DARK_AQUA);
		axeA = new ItemACAxe(AbyssalCraftAPI.abyssalniteTool, "aaxe", 4, EnumChatFormatting.DARK_AQUA);
		shovelA = new ItemACShovel(AbyssalCraftAPI.abyssalniteTool, "ashovel", 4, EnumChatFormatting.DARK_AQUA);
		swordA = new ItemACSword(AbyssalCraftAPI.abyssalniteTool, "asword", EnumChatFormatting.DARK_AQUA);
		hoeA = new ItemACHoe(AbyssalCraftAPI.abyssalniteTool, "ahoe", EnumChatFormatting.DARK_AQUA);
		Corpickaxe = new ItemACPickaxe(AbyssalCraftAPI.refinedCoraliumTool, "corpick", 5, EnumChatFormatting.AQUA);
		Coraxe = new ItemACAxe(AbyssalCraftAPI.refinedCoraliumTool, "coraxe", 5, EnumChatFormatting.AQUA);
		Corshovel = new ItemACShovel(AbyssalCraftAPI.refinedCoraliumTool, "corshovel", 5, EnumChatFormatting.AQUA);
		Corsword = new ItemACSword(AbyssalCraftAPI.refinedCoraliumTool, "corsword", EnumChatFormatting.AQUA);
		Corhoe = new ItemACHoe(AbyssalCraftAPI.refinedCoraliumTool, "corhoe", EnumChatFormatting.AQUA);
		dreadiumpickaxe = new ItemACPickaxe(AbyssalCraftAPI.dreadiumTool, "dreadiumpickaxe", 6, EnumChatFormatting.DARK_RED);
		dreadiumaxe = new ItemACAxe(AbyssalCraftAPI.dreadiumTool, "dreadiumaxe", 6, EnumChatFormatting.DARK_RED);
		dreadiumshovel = new ItemACShovel(AbyssalCraftAPI.dreadiumTool, "dreadiumshovel", 6, EnumChatFormatting.DARK_RED);
		dreadiumsword = new ItemACSword(AbyssalCraftAPI.dreadiumTool, "dreadiumsword", EnumChatFormatting.DARK_RED);
		dreadiumhoe = new ItemACHoe(AbyssalCraftAPI.dreadiumTool, "dreadiumhoe", EnumChatFormatting.DARK_RED);
		dreadhilt = new ItemDreadiumKatana("dreadhilt", 5.0F, 200);
		dreadkatana = new ItemDreadiumKatana("dreadkatana", 20.0F, 2000);
		soulReaper = new ItemSoulReaper("soulreaper");
		ethPickaxe = new ItemEthaxiumPickaxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumpickaxe");
		ethAxe = new ItemACAxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumaxe", 8, EnumChatFormatting.AQUA);
		ethShovel = new ItemACShovel(AbyssalCraftAPI.ethaxiumTool, "ethaxiumshovel", 8, EnumChatFormatting.AQUA);
		ethSword = new ItemACSword(AbyssalCraftAPI.ethaxiumTool, "ethaxiumsword", EnumChatFormatting.AQUA);
		ethHoe = new ItemACHoe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumhoe", EnumChatFormatting.AQUA);
		drainStaff = new ItemDrainStaff();

		//Armor
		helmet = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 0, "ahelmet");
		plate = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 1, "aplate");
		legs = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 2, "alegs");
		boots = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 3, "aboots");
		helmetD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 0, "dhelmet");
		plateD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 1, "dplate");
		legsD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 2, "dlegs");
		bootsD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 3, "dboots");
		Corhelmet = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 0, "corhelmet");
		Corplate = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 1, "corplate");
		Corlegs = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 2, "corlegs");
		Corboots = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 3, "corboots");
		CorhelmetP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 0, "corhelmetp");
		CorplateP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 1, "corplatep");
		CorlegsP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 2, "corlegsp");
		CorbootsP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 3, "corbootsp");
		Depthshelmet = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 0, "depthshelmet");
		Depthsplate = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 1, "depthsplate");
		Depthslegs = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 2, "depthslegs");
		Depthsboots = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 3, "depthsboots");
		dreadiumhelmet = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 0, "dreadiumhelmet");
		dreadiumplate = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 1, "dreadiumplate");
		dreadiumlegs = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 2, "dreadiumlegs");
		dreadiumboots = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 3, "dreadiumboots");
		dreadiumShelmet = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 0, "dreadiumsamuraihelmet");
		dreadiumSplate = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 1, "dreadiumsamuraiplate");
		dreadiumSlegs = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 2, "dreadiumsamurailegs");
		dreadiumSboots = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 3, "dreadiumsamuraiboots");
		ethHelmet = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 0, "ethaxiumhelmet");
		ethPlate = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 1, "ethaxiumplate");
		ethLegs = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 2, "ethaxiumlegs");
		ethBoots = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 3, "ethaxiumboots");

		//Upgrade kits
		CobbleU = new ItemUpgradeKit("Wood", "Cobblestone").setUnlocalizedName("cobbleu").setCreativeTab(tabItems);
		IronU = new ItemUpgradeKit("Cobblestone", "Iron").setUnlocalizedName("ironu").setCreativeTab(tabItems);
		GoldU = new ItemUpgradeKit("Iron", "Gold").setUnlocalizedName("goldu").setCreativeTab(tabItems);
		DiamondU = new ItemUpgradeKit("Gold", "Diamond").setUnlocalizedName("diamondu").setCreativeTab(tabItems);
		AbyssalniteU = new ItemUpgradeKit("Diamond", "Abyssalnite").setUnlocalizedName("abyssalniteu").setCreativeTab(tabItems);
		CoraliumU = new ItemUpgradeKit("Abyssalnite", "Coralium").setUnlocalizedName("coraliumu").setCreativeTab(tabItems);
		DreadiumU = new ItemUpgradeKit("Coralium", "Dreadium").setUnlocalizedName("dreadiumu").setCreativeTab(tabItems);
		EthaxiumU = new ItemUpgradeKit("Dreadium", "Ethaxium").setUnlocalizedName("ethaxiumu").setCreativeTab(tabItems);

		//Foodstuffs
		ironp = new ItemACBasic("ironp");
		MRE = new ItemPlatefood(20, 1F, false, "mre");
		chickenp = new ItemPlatefood(12, 1.2F, false, "chickenp");
		porkp = new ItemPlatefood(16, 1.6F, false, "porkp");
		beefp = new ItemPlatefood(6, 0.6F, false, "beefp");
		fishp = new ItemPlatefood(10, 1.2F, false, "fishp");
		dirtyplate = new ItemACBasic("dirtyplate");
		friedegg = new ItemFood(5, 0.6F, false).setCreativeTab(tabFood).setUnlocalizedName("friedegg");
		eggp = new ItemPlatefood(10, 1.2F, false, "eggp");
		cloth = new ItemWashCloth();

		GameRegistry.registerTileEntity(TileEntityCrate.class, "tileEntityCrate");
		GameRegistry.registerTileEntity(TileEntityPSDL.class, "tileEntityPSDL");
		GameRegistry.registerTileEntity(TileEntityDGhead.class, "tileEntityDGhead");
		GameRegistry.registerTileEntity(TileEntityPhead.class, "tileEntityPhead");
		GameRegistry.registerTileEntity(TileEntityWhead.class, "tileEntityWhead");
		GameRegistry.registerTileEntity(TileEntityOhead.class, "tileEntityOhead");
		GameRegistry.registerTileEntity(TileEntityDreadAltarTop.class, "tileEntityDreadAltarTop");
		GameRegistry.registerTileEntity(TileEntityDreadAltarBottom.class, "tileEntityDreadAltarBottom");
		GameRegistry.registerTileEntity(TileEntityCrystallizer.class, "tileEntityCrystallizer");
		GameRegistry.registerTileEntity(TileEntityTransmutator.class, "tileEntityTransmutator");
		GameRegistry.registerTileEntity(TileEntityDreadguardSpawner.class, "tileEntityDradguardSpawner");
		GameRegistry.registerTileEntity(TileEntityChagarothSpawner.class, "tileEntityChagarothSpawner");
		GameRegistry.registerTileEntity(TileEntityChagarothFistSpawner.class, "tileEntityChagarothFistSpawner");
		GameRegistry.registerTileEntity(TileEntityODB.class, "tileEntityODB");
		GameRegistry.registerTileEntity(TileEntityEngraver.class, "tileEntityEngraver");
		GameRegistry.registerTileEntity(TileEntityMaterializer.class, "tileEntityMaterializer");
		GameRegistry.registerTileEntity(TileEntityRitualAltar.class, "tileEntityRitualAltar");
		GameRegistry.registerTileEntity(TileEntityRitualPedestal.class, "tileEntityRitualPedestal");
		GameRegistry.registerTileEntity(TileEntityCthulhuStatue.class, "tileEntityCthulhuStatue");
		GameRegistry.registerTileEntity(TileEntityHasturStatue.class, "tileEntityHasturStatue");
		GameRegistry.registerTileEntity(TileEntityJzaharStatue.class, "tileEntityJzaharStatue");
		GameRegistry.registerTileEntity(TileEntityAzathothStatue.class, "tileEntityAzathothStatue");
		GameRegistry.registerTileEntity(TileEntityNyarlathotepStatue.class, "tileEntityNyarlathotepStatue");
		GameRegistry.registerTileEntity(TileEntityYogsothothStatue.class, "tileEntityyogsothothStatue");
		GameRegistry.registerTileEntity(TileEntityShubniggurathStatue.class, "tileEntityShubniggurathStatue");
		GameRegistry.registerTileEntity(TileEntityShoggothBiomass.class, "tileEntityShoggothBiomass");
		GameRegistry.registerTileEntity(TileEntityEnergyPedestal.class, "tileEntityEnergyPedestal");
		GameRegistry.registerTileEntity(TileEntitySacrificialAltar.class, "tileEntitySacrificialAltar");
		GameRegistry.registerTileEntity(TileEntityTieredEnergyPedestal.class, "tileEntityTieredEnergyPedestal");
		GameRegistry.registerTileEntity(TileEntityTieredSacrificialAltar.class, "tileEntityTieredSacrificialAltar");

		//TODO: uncomment stuff here
		Cplague = new PotionCplague(new ResourceLocation("abyssalcraft", "Cplague"), true, 0x00FFFF).setIconIndex(1, 0).setPotionName("potion.Cplague");
		//		AbyssalCraftAPI.addPotionRequirements(Cplague.id, "0 & 1 & !2 & 3 & 0+6");
		//		Corflesh.setPotionEffect("+0+1-2+3&4+4+13");
		//		Corbone.setPotionEffect("+0+1-2+3&4+4+13");
		Dplague = new PotionDplague(new ResourceLocation("abyssalcraft", "Dplague"), true, 0xAD1313).setIconIndex(1, 0).setPotionName("potion.Dplague");
		//		AbyssalCraftAPI.addPotionRequirements(Dplague.id, "0 & 1 & 2 & 3 & 2+6");
		//		AbyssalCraftAPI.addPotionAmplifiers(Dplague.id, "5");
		dreadfragment.setPotionEffect("0+1+2+3+13&4-4");
		antiMatter = new PotionAntimatter(new ResourceLocation("abyssalcraft", "Antimatter"), true, 0xFFFFFF).setIconIndex(1, 0).setPotionName("potion.Antimatter");
		//		AbyssalCraftAPI.addPotionRequirements(antiMatter.id, "0 & 1 & 2 & !3 & 2+6");
		//		antiFlesh.setPotionEffect("0+1+2-3+13&4-4");
		//		antiCorflesh.setPotionEffect("0+1+2-3+13&4-4");
		//		antiCorbone.setPotionEffect("0+1+2-3+13&4-4");
		//		sulfur.setPotionEffect(PotionHelper.spiderEyeEffect);
		//		crystalOxygen.setPotionEffect(PotionHelper.field_151423_m);
		//		crystalHydrogen.setPotionEffect("-0-1+2+3&4-4+13");
		//		crystalNitrogen.setPotionEffect("-0+1-2+3&4-4+13");

		coraliumE = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "coralium");
		dreadE = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "dread");
		lightPierce = new EnchantmentLightPierce(getNextAvailableEnchantmentId());
		ironWall = new EnchantmentIronWall(getNextAvailableEnchantmentId(), 2);

		AbyssalCraftAPI.enchId1 = coraliumE.effectId;
		AbyssalCraftAPI.enchId2 = dreadE.effectId;
		AbyssalCraftAPI.enchId3 = lightPierce.effectId;
		AbyssalCraftAPI.enchId4 = ironWall.effectId;

		AbyssalCraftAPI.potionId1 = Cplague.id;
		AbyssalCraftAPI.potionId2 = Dplague.id;
		AbyssalCraftAPI.potionId3 = antiMatter.id;

		//Block Register
		GameRegistry.registerBlock(Darkstone, "darkstone");
		GameRegistry.registerBlock(Darkstone_cobble, "darkstone_cobble");
		GameRegistry.registerBlock(Darkstone_brick, "darkstone_brick");
		GameRegistry.registerBlock(DSGlow, "dsglow");
		GameRegistry.registerBlock(Darkbrickslab1, ItemDarkbrickSlab.class, "darkbrickslab1");
		GameRegistry.registerBlock(Darkbrickslab2, ItemDarkbrickSlab.class, "darkbrickslab2");
		GameRegistry.registerBlock(Darkcobbleslab1, ItemDarkcobbleSlab.class, "darkcobbleslab1");
		GameRegistry.registerBlock(Darkcobbleslab2, ItemDarkcobbleSlab.class, "darkcobbleslab2");
		GameRegistry.registerBlock(Darkgrass, "darkgrass");
		GameRegistry.registerBlock(DBstairs, "dbstairs");
		GameRegistry.registerBlock(DCstairs, "dcstairs");
		GameRegistry.registerBlock(DLTLeaves, "dltleaves");
		GameRegistry.registerBlock(DLTLog, "dltlog");
		GameRegistry.registerBlock(DLTSapling, "dltsapling");
		GameRegistry.registerBlock(abystone, ItemBlockColorName.class, "abystone");
		GameRegistry.registerBlock(abybrick, ItemBlockColorName.class, "abybrick");
		GameRegistry.registerBlock(abyslab1, ItemAbySlab.class, "abyslab1");
		GameRegistry.registerBlock(abyslab2, ItemAbySlab.class, "abyslab2");
		GameRegistry.registerBlock(abystairs, ItemBlockColorName.class, "abystairs");
		GameRegistry.registerBlock(Coraliumore, "coraliumore");
		GameRegistry.registerBlock(abyore, "abyore");
		GameRegistry.registerBlock(abyfence, ItemBlockColorName.class, "abyfence");
		GameRegistry.registerBlock(DSCwall, "dscwall");
		GameRegistry.registerBlock(ODB, ItemODB.class, "odb");
		GameRegistry.registerBlock(abyblock, ItemBlockColorName.class, "abyblock");
		GameRegistry.registerBlock(CoraliumInfusedStone, "coraliumstone");
		GameRegistry.registerBlock(ODBcore, ItemBlockColorName.class, "odbcore");
		GameRegistry.registerBlock(Crate, "crate");
		GameRegistry.registerBlock(portal, "abyportal");
		GameRegistry.registerBlock(Darkstoneslab1, ItemDarkstoneSlab.class, "darkstoneslab1");
		GameRegistry.registerBlock(Darkstoneslab2, ItemDarkstoneSlab.class, "darkstoneslab2");
		GameRegistry.registerBlock(Coraliumfire, "coraliumfire");
		GameRegistry.registerBlock(DSbutton, "dsbutton");
		GameRegistry.registerBlock(DSpplate, "dspplate");
		GameRegistry.registerBlock(DLTplank, "dltplank");
		GameRegistry.registerBlock(DLTbutton, "dltbutton");
		GameRegistry.registerBlock(DLTpplate, "dltpplate");
		GameRegistry.registerBlock(DLTstairs, "dltstairs");
		GameRegistry.registerBlock(DLTslab1, ItemDLTSlab.class, "dltslab1");
		GameRegistry.registerBlock(DLTslab2, ItemDLTSlab.class, "dltslab2");
		GameRegistry.registerBlock(Cwater, "cwater");
		GameRegistry.registerBlock(corblock, ItemBlockColorName.class, "corblock");
		GameRegistry.registerBlock(PSDL, "psdl");
		GameRegistry.registerBlock(AbyCorOre, "abycorore");
		GameRegistry.registerBlock(Altar, "altar");
		GameRegistry.registerBlock(Abybutton, ItemBlockColorName.class, "abybutton");
		GameRegistry.registerBlock(Abypplate, ItemBlockColorName.class, "abypplate");
		GameRegistry.registerBlock(DSBfence, "dsbfence");
		GameRegistry.registerBlock(DLTfence, "dltfence");
		GameRegistry.registerBlock(dreadstone, "dreadstone");
		GameRegistry.registerBlock(abydreadstone, "abydreadstone");
		GameRegistry.registerBlock(abydreadore, "abydreadore");
		GameRegistry.registerBlock(dreadore, "dreadore");
		GameRegistry.registerBlock(dreadbrick, "dreadbrick");
		GameRegistry.registerBlock(abydreadbrick, "abydreadbrick");
		GameRegistry.registerBlock(dreadgrass, "dreadgrass");
		GameRegistry.registerBlock(dreadlog, "dreadlog");
		GameRegistry.registerBlock(dreadleaves, "dreadleaves");
		GameRegistry.registerBlock(dreadsapling, "dreadsapling");
		GameRegistry.registerBlock(dreadplanks, "dreadplanks");
		GameRegistry.registerBlock(dreadportal, "dreadportal");
		GameRegistry.registerBlock(dreadfire, "dreadfire");
		GameRegistry.registerBlock(DGhead, "dghead");
		GameRegistry.registerBlock(Phead, "phead");
		GameRegistry.registerBlock(Whead, "whead");
		GameRegistry.registerBlock(Ohead, "ohead");
		GameRegistry.registerBlock(dreadbrickstairs, "dreadbrickstairs");
		GameRegistry.registerBlock(dreadbrickfence, "dreadbrickfence");
		GameRegistry.registerBlock(dreadbrickslab1, ItemDreadbrickSlab.class, "dreadbrickslab1");
		GameRegistry.registerBlock(dreadbrickslab2, ItemDreadbrickSlab.class, "dreadbrickslab2");
		GameRegistry.registerBlock(abydreadbrickstairs, "abydreadbrickstairs");
		GameRegistry.registerBlock(abydreadbrickfence, "abydreadbrickfence");
		GameRegistry.registerBlock(abydreadbrickslab1, ItemAbyDreadbrickSlab.class, "abydreadbrickslab1");
		GameRegistry.registerBlock(abydreadbrickslab2, ItemAbyDreadbrickSlab.class, "abydreadbrickslab2");
		GameRegistry.registerBlock(anticwater, "antiwater");
		GameRegistry.registerBlock(cstone, "cstone");
		GameRegistry.registerBlock(cstonebrick, "cstonebrick");
		GameRegistry.registerBlock(cstonebrickfence, "cstonebrickfence");
		GameRegistry.registerBlock(cstonebrickslab1, ItemCstonebrickSlab.class, "cstonebrickslab1");
		GameRegistry.registerBlock(cstonebrickslab2, ItemCstonebrickSlab.class, "cstonebrickslab2");
		GameRegistry.registerBlock(cstonebrickstairs, "cstonebrickstairs");
		GameRegistry.registerBlock(cstonebutton, "cstonebutton");
		GameRegistry.registerBlock(cstonepplate, "cstonepplate");
		GameRegistry.registerBlock(dreadaltartop, "dreadaltartop");
		GameRegistry.registerBlock(dreadaltarbottom, "dreadaltarbottom");
		GameRegistry.registerBlock(crystallizer, "crystallizer");
		GameRegistry.registerBlock(crystallizer_on, "crystallizer_on");
		GameRegistry.registerBlock(dreadiumblock, ItemBlockColorName.class, "dreadiumblock");
		GameRegistry.registerBlock(transmutator, "transmutator");
		GameRegistry.registerBlock(transmutator_on, "transmutator_on");
		GameRegistry.registerBlock(dreadguardspawner, "dreadguardspawner");
		GameRegistry.registerBlock(chagarothspawner, "chagarothspawner");
		GameRegistry.registerBlock(chagarothfistspawner, "chagarothfistspawner");
		GameRegistry.registerBlock(DrTfence, "drtfence");
		GameRegistry.registerBlock(nitreOre, "nitreore");
		GameRegistry.registerBlock(AbyIroOre, "abyiroore");
		GameRegistry.registerBlock(AbyGolOre, "abygolore");
		GameRegistry.registerBlock(AbyDiaOre, "abydiaore");
		GameRegistry.registerBlock(AbyNitOre, "abynitore");
		GameRegistry.registerBlock(AbyTinOre, "abytinore");
		GameRegistry.registerBlock(AbyCopOre, "abycopore");
		GameRegistry.registerBlock(AbyPCorOre, "abypcorore");
		GameRegistry.registerBlock(AbyLCorOre, "abylcorore");
		GameRegistry.registerBlock(solidLava, "solidlava");
		GameRegistry.registerBlock(ethaxium, ItemBlockColorName.class, "ethaxium");
		GameRegistry.registerBlock(ethaxiumbrick, ItemMetadataBlock.class, "ethaxiumbrick");
		GameRegistry.registerBlock(ethaxiumpillar, ItemBlockColorName.class, "ethaxiumpillar");
		GameRegistry.registerBlock(ethaxiumstairs, ItemBlockColorName.class, "ethaxiumbrickstairs");
		GameRegistry.registerBlock(ethaxiumslab1, ItemEthaxiumSlab.class, "ethaxiumbrickslab1");
		GameRegistry.registerBlock(ethaxiumslab2, ItemEthaxiumSlab.class, "ethaxiumbrickslab2");
		GameRegistry.registerBlock(ethaxiumfence, ItemBlockColorName.class, "ethaxiumfence");
		GameRegistry.registerBlock(ethaxiumblock, ItemBlockColorName.class, "ethaxiumblock");
		GameRegistry.registerBlock(omotholstone, "omotholstone");
		GameRegistry.registerBlock(omotholportal, "omotholportal");
		GameRegistry.registerBlock(omotholfire, "omotholfire");
		GameRegistry.registerBlock(engraver, "engraver");
		GameRegistry.registerBlock(house, "engraver_on");
		GameRegistry.registerBlock(materializer, "materializer");
		GameRegistry.registerBlock(darkethaxiumbrick, ItemMetadataBlock.class, "darkethaxiumbrick");
		GameRegistry.registerBlock(darkethaxiumpillar, ItemBlockColorName.class, "darkethaxiumpillar");
		GameRegistry.registerBlock(darkethaxiumstairs, ItemBlockColorName.class, "darkethaxiumbrickstairs");
		GameRegistry.registerBlock(darkethaxiumslab1, ItemDarkEthaxiumSlab.class, "darkethaxiumbrickslab1");
		GameRegistry.registerBlock(darkethaxiumslab2, ItemDarkEthaxiumSlab.class, "darkethaxiumbrickslab2");
		GameRegistry.registerBlock(darkethaxiumfence, ItemBlockColorName.class, "darkethaxiumbrickfence");
		GameRegistry.registerBlock(ritualaltar, ItemRitualBlock.class, "ritualaltar");
		GameRegistry.registerBlock(ritualpedestal, ItemRitualBlock.class, "ritualpedestal");
		GameRegistry.registerBlock(shoggothBlock, "shoggothblock");
		GameRegistry.registerBlock(cthulhuStatue, "cthulhustatue");
		GameRegistry.registerBlock(hasturStatue, "hasturstatue");
		GameRegistry.registerBlock(jzaharStatue, "jzaharstatue");
		GameRegistry.registerBlock(azathothStatue, "azathothstatue");
		GameRegistry.registerBlock(nyarlathotepStatue, "nyarlathotepstatue");
		GameRegistry.registerBlock(yogsothothStatue, "yogsothothstatue");
		GameRegistry.registerBlock(shubniggurathStatue, "shubniggurathstatue");
		GameRegistry.registerBlock(monolithStone, "monolithstone");
		GameRegistry.registerBlock(shoggothBiomass, "shoggothbiomass");
		GameRegistry.registerBlock(energyPedestal, "energypedestal");
		GameRegistry.registerBlock(monolithPillar, "monolithpillar");
		GameRegistry.registerBlock(sacrificialAltar, "sacrificialaltar");
		GameRegistry.registerBlock(tieredEnergyPedestal, ItemMetadataBlock.class, "tieredenergypedestal");
		GameRegistry.registerBlock(tieredSacrificialAltar, ItemMetadataBlock.class, "tieredsacrificialaltar");

		//Item Register
		GameRegistry.registerItem(devsword, "devsword");
		GameRegistry.registerItem(OC, "oc");
		GameRegistry.registerItem(gatewayKey, "gatewaykey");
		GameRegistry.registerItem(Staff, "staff");
		GameRegistry.registerItem(Cbucket, "cbucket");
		GameRegistry.registerItem(PSDLfinder, "powerstonetracker");
		GameRegistry.registerItem(EoA, "eoa");
		GameRegistry.registerItem(gatewayKeyDL, "gatewaykeydl");
		GameRegistry.registerItem(Dreadshard, "dreadshard");
		GameRegistry.registerItem(dreadchunk, "dreadchunk");
		GameRegistry.registerItem(abychunk, "abychunk");
		GameRegistry.registerItem(abyingot, "abyingot");
		GameRegistry.registerItem(Coralium, "coralium");
		GameRegistry.registerItem(Coraliumcluster2, "ccluster2");
		GameRegistry.registerItem(Coraliumcluster3, "ccluster3");
		GameRegistry.registerItem(Coraliumcluster4, "ccluster4");
		GameRegistry.registerItem(Coraliumcluster5, "ccluster5");
		GameRegistry.registerItem(Coraliumcluster6, "ccluster6");
		GameRegistry.registerItem(Coraliumcluster7, "ccluster7");
		GameRegistry.registerItem(Coraliumcluster8, "ccluster8");
		GameRegistry.registerItem(Coraliumcluster9, "ccluster9");
		GameRegistry.registerItem(Cpearl ,"cpearl");
		GameRegistry.registerItem(Cchunk, "cchunk");
		GameRegistry.registerItem(Cingot, "cingot");
		GameRegistry.registerItem(Cplate, "platec");
		GameRegistry.registerItem(Corb, "transmutationgem");
		GameRegistry.registerItem(Corflesh, "corflesh");
		GameRegistry.registerItem(Corbone, "corbone");
		GameRegistry.registerItem(pickaxe, "dpick");
		GameRegistry.registerItem(axe, "daxe");
		GameRegistry.registerItem(shovel, "dshovel");
		GameRegistry.registerItem(sword, "dsword");
		GameRegistry.registerItem(hoe, "dhoe");
		GameRegistry.registerItem(pickaxeA, "apick");
		GameRegistry.registerItem(axeA, "aaxe");
		GameRegistry.registerItem(shovelA, "ashovel");
		GameRegistry.registerItem(swordA, "asword");
		GameRegistry.registerItem(hoeA, "ahoe");
		GameRegistry.registerItem(Corpickaxe, "corpick");
		GameRegistry.registerItem(Coraxe, "coraxe");
		GameRegistry.registerItem(Corshovel, "corshovel");
		GameRegistry.registerItem(Corsword, "corsword");
		GameRegistry.registerItem(Corhoe, "corhoe");
		GameRegistry.registerItem(helmet, "ahelmet");
		GameRegistry.registerItem(plate, "aplate");
		GameRegistry.registerItem(legs, "alegs");
		GameRegistry.registerItem(boots, "aboots");
		GameRegistry.registerItem(helmetD, "dhelmet");
		GameRegistry.registerItem(plateD, "dplate");
		GameRegistry.registerItem(legsD, "dlegs");
		GameRegistry.registerItem(bootsD, "dboots");
		GameRegistry.registerItem(Corhelmet, "corhelmet");
		GameRegistry.registerItem(Corplate, "corplate");
		GameRegistry.registerItem(Corlegs, "corlegs");
		GameRegistry.registerItem(Corboots, "corboots");
		GameRegistry.registerItem(CorhelmetP, "corhelmetp");
		GameRegistry.registerItem(CorplateP, "corplatep");
		GameRegistry.registerItem(CorlegsP, "corlegsp");
		GameRegistry.registerItem(CorbootsP, "corbootsp");
		GameRegistry.registerItem(Depthshelmet, "depthshelmet");
		GameRegistry.registerItem(Depthsplate, "depthsplate");
		GameRegistry.registerItem(Depthslegs, "depthslegs");
		GameRegistry.registerItem(Depthsboots, "depthsboots");
		GameRegistry.registerItem(CobbleU, "cobbleu");
		GameRegistry.registerItem(IronU, "ironu");
		GameRegistry.registerItem(GoldU, "goldu");
		GameRegistry.registerItem(DiamondU, "diamondu");
		GameRegistry.registerItem(AbyssalniteU, "abyssalniteu");
		GameRegistry.registerItem(CoraliumU, "coraliumu");
		GameRegistry.registerItem(MRE, "mre");
		GameRegistry.registerItem(ironp, "ironp");
		GameRegistry.registerItem(chickenp, "chickenp");
		GameRegistry.registerItem(porkp, "porkp");
		GameRegistry.registerItem(beefp, "beefp");
		GameRegistry.registerItem(fishp, "fishp");
		GameRegistry.registerItem(dirtyplate, "dirtyplate");
		GameRegistry.registerItem(friedegg, "friedegg");
		GameRegistry.registerItem(eggp, "eggp");
		GameRegistry.registerItem(cloth, "cloth");
		GameRegistry.registerItem(shadowfragment, "shadowfragment");
		GameRegistry.registerItem(shadowshard, "shadowshard");
		GameRegistry.registerItem(shadowgem, "shadowgem");
		GameRegistry.registerItem(oblivionshard, "oblivionshard");
		GameRegistry.registerItem(corbow, "corbow");
		GameRegistry.registerItem(antibucket, "antibucket");
		GameRegistry.registerItem(cbrick, "cbrick");
		GameRegistry.registerItem(cudgel, "cudgel");
		GameRegistry.registerItem(dreadiumingot, "dreadiumingot");
		GameRegistry.registerItem(dreadfragment, "dreadfragment");
		GameRegistry.registerItem(dreadiumhelmet, "dreadiumhelmet");
		GameRegistry.registerItem(dreadiumplate, "dreadiumplate");
		GameRegistry.registerItem(dreadiumlegs, "dreadiumlegs");
		GameRegistry.registerItem(dreadiumboots, "dreadiumboots");
		GameRegistry.registerItem(dreadiumpickaxe, "dreadiumpickaxe");
		GameRegistry.registerItem(dreadiumaxe, "dreadiumaxe");
		GameRegistry.registerItem(dreadiumshovel, "dreadiumshovel");
		GameRegistry.registerItem(dreadiumsword, "dreadiumsword");
		GameRegistry.registerItem(dreadiumhoe, "dreadiumhoe");
		GameRegistry.registerItem(DreadiumU, "dreadiumu");
		GameRegistry.registerItem(carbonCluster, "carboncluster");
		GameRegistry.registerItem(denseCarbonCluster, "densecarboncluster");
		GameRegistry.registerItem(methane, "methane");
		GameRegistry.registerItem(nitre, "nitre");
		GameRegistry.registerItem(sulfur, "sulfur");
		GameRegistry.registerItem(crystal, "crystal");
		GameRegistry.registerItem(crystalShard, "crystalshard");
		GameRegistry.registerItem(dreadcloth, "dreadcloth");
		GameRegistry.registerItem(dreadplate, "dreadplate");
		GameRegistry.registerItem(dreadblade, "dreadblade");
		GameRegistry.registerItem(dreadhilt, "dreadhilt");
		GameRegistry.registerItem(dreadkatana, "dreadkatana");
		GameRegistry.registerItem(dreadKey, "dreadkey");
		GameRegistry.registerItem(gatewayKeyJzh, "gatewaykeyjzh");
		GameRegistry.registerItem(dreadiumShelmet, "dreadiumsamuraihelmet");
		GameRegistry.registerItem(dreadiumSplate, "dreadiumsamuraiplate");
		GameRegistry.registerItem(dreadiumSlegs, "dreadiumsamurailegs");
		GameRegistry.registerItem(dreadiumSboots, "dreadiumsamuraiboots");
		GameRegistry.registerItem(tinIngot, "tiningot");
		GameRegistry.registerItem(copperIngot, "copperingot");
		GameRegistry.registerItem(antiBeef, "antibeef");
		GameRegistry.registerItem(antiChicken, "antichicken");
		GameRegistry.registerItem(antiPork, "antipork");
		GameRegistry.registerItem(antiFlesh, "antiflesh");
		GameRegistry.registerItem(antiBone, "antibone");
		GameRegistry.registerItem(antiSpider_eye, "antispidereye");
		GameRegistry.registerItem(soulReaper, "soulreaper");
		GameRegistry.registerItem(ethaxium_brick, "ethbrick");
		GameRegistry.registerItem(ethaxiumIngot, "ethaxiumingot");
		GameRegistry.registerItem(lifeCrystal, "lifecrystal");
		GameRegistry.registerItem(ethHelmet, "ethaxiumhelmet");
		GameRegistry.registerItem(ethPlate, "ethaxiumplate");
		GameRegistry.registerItem(ethLegs, "ethaxiumlegs");
		GameRegistry.registerItem(ethBoots, "ethaxiumboots");
		GameRegistry.registerItem(ethPickaxe, "ethaxiumpickaxe");
		GameRegistry.registerItem(ethAxe, "ethaxiumaxe");
		GameRegistry.registerItem(ethShovel, "ethaxiumshovel");
		GameRegistry.registerItem(ethSword, "ethaxiumsword");
		GameRegistry.registerItem(ethHoe, "ethaxiumhoe");
		GameRegistry.registerItem(EthaxiumU, "ethaxiumu");
		GameRegistry.registerItem(coin, "coin");
		GameRegistry.registerItem(cthulhuCoin, "cthulhucoin");
		GameRegistry.registerItem(elderCoin, "eldercoin");
		GameRegistry.registerItem(jzaharCoin, "jzaharcoin");
		GameRegistry.registerItem(engravingBlank, "engraving_blank");
		GameRegistry.registerItem(engravingCthulhu, "engraving_cthulhu");
		GameRegistry.registerItem(engravingElder, "engraving_elder");
		GameRegistry.registerItem(engravingJzahar, "engraving_jzahar");
		GameRegistry.registerItem(eldritchScale, "eldritchscale");
		GameRegistry.registerItem(omotholFlesh, "omotholflesh");
		GameRegistry.registerItem(antiCorflesh, "anticorflesh");
		GameRegistry.registerItem(antiCorbone, "anticorbone");
		GameRegistry.registerItem(necronomicon, "necronomicon");
		GameRegistry.registerItem(necronomicon_cor, "necronomicon_cor");
		GameRegistry.registerItem(necronomicon_dre, "necronomicon_dre");
		GameRegistry.registerItem(necronomicon_omt, "necronomicon_omt");
		GameRegistry.registerItem(abyssalnomicon, "abyssalnomicon");
		GameRegistry.registerItem(crystalbag_s, "crystalbag_small");
		GameRegistry.registerItem(crystalbag_m, "crystalbag_medium");
		GameRegistry.registerItem(crystalbag_l, "crystalbag_large");
		GameRegistry.registerItem(crystalbag_h, "crystalbag_huge");
		GameRegistry.registerItem(shoggothFlesh, "shoggothflesh");
		GameRegistry.registerItem(nugget, "ingotnugget");
		GameRegistry.registerItem(drainStaff, "drainstaff");
		GameRegistry.registerItem(essence, "essence");
		GameRegistry.registerItem(skin, "skin");
		GameRegistry.registerItem(charm, "charm");
		GameRegistry.registerItem(cthulhuCharm, "cthulhucharm");
		GameRegistry.registerItem(hasturCharm, "hasturcharm");
		GameRegistry.registerItem(jzaharCharm, "jzaharcharm");
		GameRegistry.registerItem(azathothCharm, "azathothcharm");
		GameRegistry.registerItem(nyarlathotepCharm, "nyarlathotepcharm");
		GameRegistry.registerItem(yogsothothCharm, "yogsothothcharm");
		GameRegistry.registerItem(shubniggurathCharm, "shubniggurathcharm");
		GameRegistry.registerItem(hasturCoin, "hasturcoin");
		GameRegistry.registerItem(azathothCoin, "azathothcoin");
		GameRegistry.registerItem(nyarlathotepCoin, "nyarlathotepcoin");
		GameRegistry.registerItem(yogsothothCoin, "yogsothothcoin");
		GameRegistry.registerItem(shubniggurathCoin, "shubniggurathcoin");
		GameRegistry.registerItem(engravingHastur, "engraving_hastur");
		GameRegistry.registerItem(engravingAzathoth, "engraving_azathoth");
		GameRegistry.registerItem(engravingNyarlathotep, "engraving_nyarlathotep");
		GameRegistry.registerItem(engravingYogsothoth, "engraving_yogsothoth");
		GameRegistry.registerItem(engravingShubniggurath, "engraving_shubniggurath");
		//		GameRegistry.registerItem(shadowPlate, "shadowplate");

		AbyssalCraftAPI.setRepairItems();
		LIQUID_CORALIUM.setBlock(Cwater).setUnlocalizedName(Cwater.getUnlocalizedName());
		LIQUID_ANTIMATTER.setBlock(anticwater).setUnlocalizedName(anticwater.getUnlocalizedName());
		if(CFluid.getBlock() == null)
			CFluid.setBlock(Cwater);
		if(antifluid.getBlock() == null)
			antifluid.setBlock(anticwater);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(CFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Cbucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(Cwater.getDefaultState(), Cbucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(antifluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(antibucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(anticwater.getDefaultState(), antibucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		//Biome
		if(dark1 == true){
			registerBiomeWithTypes(Darklands, darkWeight1, BiomeType.WARM, Type.WASTELAND, Type.SPOOKY);
			BiomeManager.addVillageBiome(Darklands, true);
		}
		if(dark2 == true){
			registerBiomeWithTypes(DarklandsForest, darkWeight2, BiomeType.WARM, Type.FOREST, Type.SPOOKY);
			BiomeManager.addVillageBiome(DarklandsForest, true);
		}
		if(dark3 == true){
			registerBiomeWithTypes(DarklandsPlains, darkWeight3, BiomeType.WARM, Type.PLAINS, Type.SPOOKY);
			BiomeManager.addVillageBiome(DarklandsPlains, true);
		}
		if(dark4 == true){
			registerBiomeWithTypes(DarklandsHills, darkWeight4, BiomeType.WARM, Type.HILLS, Type.SPOOKY);
			BiomeManager.addVillageBiome(DarklandsHills, true);
		}
		if(dark5 == true){
			registerBiomeWithTypes(DarklandsMountains, darkWeight5, BiomeType.WARM, Type.MOUNTAIN, Type.SPOOKY);
			BiomeManager.addVillageBiome(DarklandsMountains, true);
			BiomeManager.addStrongholdBiome(DarklandsMountains);
		}
		if(coralium1 == true)
			registerBiomeWithTypes(corswamp, coraliumWeight, BiomeType.WARM, Type.SWAMP);
		if(darkspawn1 == true)
			BiomeManager.addSpawnBiome(Darklands);
		if(darkspawn2 == true)
			BiomeManager.addSpawnBiome(DarklandsForest);
		if(darkspawn3 == true)
			BiomeManager.addSpawnBiome(DarklandsPlains);
		if(darkspawn4 == true)
			BiomeManager.addSpawnBiome(DarklandsHills);
		if(darkspawn5 == true)
			BiomeManager.addSpawnBiome(DarklandsMountains);
		if(coraliumspawn1 == true)
			BiomeManager.addSpawnBiome(corswamp);

		BiomeDictionary.registerBiomeType(Wastelands, Type.DEAD);
		BiomeDictionary.registerBiomeType(Dreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(AbyDreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(MountainDreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ForestDreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(omothol, Type.DEAD);
		BiomeDictionary.registerBiomeType(darkRealm, Type.DEAD);

		//Dimension
		registerDimension(configDimId1, WorldProviderAbyss.class, keepLoaded1);
		registerDimension(configDimId2, WorldProviderDreadlands.class, keepLoaded2);
		registerDimension(configDimId3, WorldProviderOmothol.class, keepLoaded3);
		registerDimension(configDimId4, WorldProviderDarkRealm.class, keepLoaded4);

		//Mobs
		registerEntityWithEgg(EntityDepthsGhoul.class, "depthsghoul", 25, 80, 3, true, 0x36A880, 0x012626);
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));

		registerEntityWithEgg(EntityEvilpig.class, "evilpig", 26, 80, 3, true, 15771042, 14377823);
		if(evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilpig.class, evilAnimalSpawnRate, 1, 3, EnumCreatureType.CREATURE, new BiomeGenBase[] {
				BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna,
				BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau,
				BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
				BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest});

		registerEntityWithEgg(EntityAbyssalZombie.class , "abyssalzombie", 27, 80, 3, true, 0x36A880, 0x052824);
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));
		if(endAbyssalZombie)
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.END));

		EntityRegistry.registerModEntity(EntityODBPrimed.class, "Primed ODB", 28, this, 80, 3, true);

		registerEntityWithEgg(EntityJzahar.class, "Jzahar", 29, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityAbygolem.class, "abygolem", 30, 80, 3, true, 0x8A00E6, 0x6100A1);

		registerEntityWithEgg(EntityDreadgolem.class, "dreadgolem", 31, 80, 3, true, 0x1E60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadguard.class, "dreadguard", 32, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityPSDLTracker.class, "PowerstoneTracker", 33, this, 64, 10, true);

		registerEntityWithEgg(EntityDragonMinion.class, "dragonminion", 34, 80, 3, true, 0x433434, 0x344344);

		registerEntityWithEgg(EntityDragonBoss.class, "dragonboss", 35, 80, 3, true, 0x476767, 0x768833);

		EntityRegistry.registerModEntity(EntityODBcPrimed.class, "Primed ODB Core", 36, this, 80, 3, true);

		registerEntityWithEgg(EntityShadowCreature.class, "shadowcreature", 37, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityShadowMonster.class, "shadowmonster", 38, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityDreadling.class, "dreadling", 39, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadSpawn.class, "dreadspawn", 40, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDemonPig.class, "demonpig", 41, 80, 3, true, 15771042, 14377823);
		EntityRegistry.addSpawn(EntityDemonPig.class, 30, 1, 3, EnumCreatureType.MONSTER, new BiomeGenBase[] {
			BiomeGenBase.hell});

		registerEntityWithEgg(EntitySkeletonGoliath.class, "gskeleton", 42, 80, 3, true, 0xD6D6C9, 0xC6C7AD);

		registerEntityWithEgg(EntityChagarothSpawn.class, "chagarothspawn", 43, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityChagarothFist.class, "chagarothfist", 44, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityChagaroth.class, "chagaroth", 45, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityShadowBeast.class, "shadowbeast", 46, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntitySacthoth.class, "shadowboss", 47, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiAbyssalZombie.class, "antiabyssalzombie", 48, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiBat.class, "antibat", 49, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiChicken.class, "antichicken", 50, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiCow.class, "anticow", 51, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiCreeper.class, "anticreeper", 52, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiGhoul.class, "antighoul", 53, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiPig.class, "antipig", 54, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiPlayer.class, "antiplayer", 55, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiSkeleton.class, "antiskeleton", 56, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiSpider.class, "antispider", 57, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiZombie.class, "antizombie", 58, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityRemnant.class, "remnant", 59, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityOmotholGhoul.class, "omotholghoul", 60, 80, 3, true, 0x133133, 0x342122);

		EntityRegistry.registerModEntity(EntityCoraliumArrow.class, "CoraliumArrow", 61, this, 64, 10, true);

		registerEntityWithEgg(EntityGatekeeperMinion.class, "jzaharminion", 62, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityGreaterDreadSpawn.class, "greaterdreadspawn", 63, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityLesserDreadbeast.class, "lesserdreadbeast", 64, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDreadSlug.class, "DreadSlug", 65, this, 64, 10, true);

		registerEntityWithEgg(EntityLesserShoggoth.class, "lessershoggoth", 66, 80, 3, true, 0x133133, 0x342122);
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, new BiomeGenBase[]{
			AbyssalCraft.Wastelands, AbyssalCraft.Dreadlands, AbyssalCraft.AbyDreadlands, AbyssalCraft.MountainDreadlands,
			AbyssalCraft.ForestDreadlands, AbyssalCraft.omothol, AbyssalCraft.darkRealm});

		registerEntityWithEgg(EntityEvilCow.class, "evilcow", 67, 80, 3, true, 4470310, 10592673);
		if(evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilCow.class, evilAnimalSpawnRate, 1, 3, EnumCreatureType.CREATURE, new BiomeGenBase[] {
				BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna,
				BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau,
				BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
				BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest});

		registerEntityWithEgg(EntityEvilChicken.class, "evilchicken", 68, 80, 3, true, 10592673, 16711680);
		if(evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilChicken.class, evilAnimalSpawnRate, 1, 3, EnumCreatureType.CREATURE, new BiomeGenBase[] {
				BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna,
				BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau,
				BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
				BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest});

		registerEntityWithEgg(EntityDemonCow.class, "demoncow", 69, 80, 3, true, 4470310, 10592673);
		EntityRegistry.addSpawn(EntityDemonCow.class, 30, 1, 3, EnumCreatureType.MONSTER, new BiomeGenBase[] {
			BiomeGenBase.hell});

		registerEntityWithEgg(EntityDemonChicken.class, "demonchicken", 70, 80, 3, true, 10592673, 16711680);
		EntityRegistry.addSpawn(EntityDemonChicken.class, 30, 1, 3, EnumCreatureType.MONSTER, new BiomeGenBase[] {
			BiomeGenBase.hell});

		//		registerEntityWithEgg(EntityShadowTitan.class, "shadowtitan", 71, 80, 3, true, 0, 0xFFFFFF);
		//
		//		registerEntityWithEgg(EntityOmotholWarden.class, "omotholwarden", 72, 80, 3, true, 0x133133, 0x342122);

		proxy.preInit();
		RitualUtil.addBlocks();
		addOreDictionaryStuff();
		addChestGenHooks();
		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
		IntegrationHandler.preInit();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

		ACLogger.info("Initializing AbyssalCraft.");
		//Achievements
		necro = new Achievement("achievement.necro", "necro", 0, 0, necronomicon, AchievementList.openInventory).registerStat();
		//Materials Achievements
		mineAby = new Achievement("achievement.mineAby", "mineAby", 2, 0, abyore, necro).registerStat();
		mineCorgem = new Achievement("achievement.mineCorgem", "mineCorgem", 4, 0, Coralium, mineAby).registerStat();
		shadowGems = new Achievement("achievement.shadowGems", "shadowGems", 6, 0, shadowgem, mineCorgem).registerStat();
		//coraliumpearl
		mineCor = new Achievement("achievement.mineCor", "mineCor", 8, 0, AbyLCorOre, shadowGems).registerStat();
		mineAbyOres = new Achievement("achievement.mineAbyOres", "mineAbyOres", 10, 0, AbyDiaOre, mineCor).registerStat();
		mineDread = new Achievement("achievement.mineDread", "mineDread", 12, 0, dreadore, mineAbyOres).registerStat();
		dreadium = new Achievement("achievement.dreadium", "dreadium", 14, 0, dreadiumingot, mineDread).registerStat();
		eth = new Achievement("achievement.ethaxium", "ethaxium", 16, 0, ethaxiumIngot, dreadium).setSpecial().registerStat();
		//Depths Ghoul Achievements
		killghoul = new Achievement("achievement.killghoul", "killghoul", -2, 0, Corbone, necro).registerStat();
		ghoulhead = new Achievement("achievement.ghoulhead", "ghoulhead", -4, 0, DGhead, killghoul).registerStat();
		petehead = new Achievement("achievement.petehead", "petehead", -4, -2, Phead, ghoulhead).registerStat();
		wilsonhead = new Achievement("achievement.wilsonhead", "wilsonhead", -4, -4, Whead, petehead).registerStat();
		orangehead = new Achievement("achievement.orangehead", "orangehead", -4, -6, Ohead, wilsonhead).registerStat();
		//Necronomicon Achievements
		necrou1 = new Achievement("achievement.necrou1", "necrou1", 2, 1, necronomicon_cor, necro).registerStat();
		necrou2 = new Achievement("achievement.necrou2", "necrou2", 4, 1, necronomicon_dre, necrou1).registerStat();
		necrou3 = new Achievement("achievement.necrou3", "necrou3", 6, 1, necronomicon_omt, necrou2).registerStat();
		abyssaln = new Achievement("achievement.abyssaln", "abyssaln", 8, 1, abyssalnomicon, necrou3).setSpecial().registerStat();
		//Ritual Achievements
		ritual = new Achievement("achievement.ritual", "ritual", -2, 1, ritualaltar, necro).setSpecial().registerStat();
		ritualSummon = new Achievement("achievement.ritualSummon", "ritualSummon", -4, 1, DGhead, ritual).registerStat();
		ritualCreate = new Achievement("achievement.ritualCreate", "ritualCreate", -4, 2, lifeCrystal, ritual).registerStat();
		ritualBreed = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.egg, ritual).registerStat();
		ritualPotion = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, Items.potionitem, ritual).registerStat();
		ritualPotionAoE = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, new ItemStack(Items.potionitem, 1, 16384), ritual).registerStat();
		ritualInfusion = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, Depthshelmet, ritual).registerStat();
		shoggothInfestation = new Achievement("achievement.shoggothInfestation", "shoggothInfestation", -6, 3, Items.skull, ritualBreed).registerStat();
		//Progression Achievements
		enterabyss = new Achievement("achievement.enterabyss", "enterabyss", 0, 2, abystone, necro).setSpecial().registerStat();
		killdragon = new Achievement("achievement.killdragon", "killdragon", 2, 2, Corflesh, enterabyss).registerStat();
		summonAsorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 4, Altar, enterabyss).registerStat();
		killAsorah = new Achievement("achievement.killAsorah", "killAsorah", 2, 4, EoA, summonAsorah).setSpecial().registerStat();
		enterdreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 2, 6, dreadstone, killAsorah).setSpecial().registerStat();
		killdreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 4, 6, Dreadshard, enterdreadlands).registerStat();
		summonChagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 2, 8, dreadaltarbottom, enterdreadlands).registerStat();
		killChagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 4, 8, dreadKey, summonChagaroth).setSpecial().registerStat();
		enterOmothol = new Achievement("achievement.enterOmothol", "enterOmothol", 4, 10, omotholstone, killChagaroth).setSpecial().registerStat();
		enterDarkRealm = new Achievement("achievement.darkRealm", "darkRealm", 2, 10, Darkstone, enterOmothol).registerStat();
		killOmotholelite = new Achievement("achievement.killOmotholelite", "killOmotholelite", 6, 10, eldritchScale, enterOmothol).registerStat();
		locateJzahar = new Achievement("achievement.locateJzahar", "locateJzahar", 4, 12, OC, enterOmothol).registerStat();
		killJzahar = new Achievement("achievement.killJzahar", "killJzahar", 6, 12, Staff, locateJzahar).setSpecial().registerStat();
		//nowwhat
		//Gateway Key Achievements
		GK1 = new Achievement("achievement.GK1", "GK1", 0, -2, gatewayKey, necro).registerStat();
		findPSDL = new Achievement("achievement.findPSDL", "findPSDL", -2, -2, PSDL, GK1).registerStat();
		GK2 = new Achievement("achievement.GK2", "GK2", 0, -4, gatewayKeyDL, GK1).registerStat();
		GK3 = new Achievement("achievement.GK3", "GK3", 0, -6, gatewayKeyJzh, GK2).registerStat();
		//Machinery Achievements
		makeTransmutator = new Achievement("achievement.makeTransmutator", "makeTransmutator", 2, -1, transmutator, necro).registerStat();
		makeCrystallizer = new Achievement("achievement.makeCrystallizer", "makeCrystallizer", 4, -2, crystallizer, makeTransmutator).registerStat();
		makeMaterializer = new Achievement("achievement.makeMaterializer", "makeMaterializer", 6, -2, materializer, makeCrystallizer).registerStat();
		makeCrystalBag = new Achievement("achievement.makeCrystalBag", "makeCrystalBag", 6, -4, crystalbag_s, makeMaterializer).registerStat();
		makeEngraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, engraver, AchievementList.openInventory).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[]{necro, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah,
				enterdreadlands, killdreadguard, ghoulhead, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth,
				enterOmothol, enterDarkRealm, necrou1, necrou2, necrou3, abyssaln, ritual, ritualSummon, ritualCreate, killOmotholelite, locateJzahar, killJzahar, shadowGems,
				mineAbyOres, mineDread, dreadium, eth, makeTransmutator, makeCrystallizer, makeMaterializer, makeCrystalBag, makeEngraver, ritualBreed, ritualPotion, ritualPotionAoE,
				ritualInfusion, shoggothInfestation}));

		proxy.init();
		MinecraftForge.EVENT_BUS.register(new ReputationEventHandler());
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(StructureDreadlandsMineStart.class, "DreadMine");
		StructureDreadlandsMinePieces.registerStructurePieces();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		AbyssalCrafting.addRecipes();
		AbyssalCraftAPI.internalNDHandler.registerInternalPages();
		IntegrationHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		ACLogger.info("Post-initializing AbyssalCraft");
		proxy.postInit();
		IntegrationHandler.postInit();
		((BlockCLiquid) Cwater).addBlocks();
		checkBiomeIds(false);
		ACLogger.info("AbyssalCraft loaded.");
	}

	@EventHandler
	public void serverStart(FMLServerAboutToStartEvent event){
		String clname = AbyssalCraftAPI.internalNDHandler.getClass().getName();
		String expect = "com.shinoow.abyssalcraft.common.handlers.InternalNecroDataHandler";
		if(!clname.equals(expect)) {
			new IllegalAccessError("The AbyssalCraft API internal NecroData handler has been overriden. "
					+ "Since things are not going to work correctly, the game will now shut down."
					+ " (Expected classname: " + expect + ", Actual classname: " + clname + ")").printStackTrace();
			FMLCommonHandler.instance().exitJava(1, true);
		}
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void handleIMC(FMLInterModComms.IMCEvent event){

		List<String> senders = new ArrayList<String>();
		for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
			if(imcMessage.key.equals("shoggothFood"))
				try {
					AbyssalCraftAPI.addShoggothFood((Class<? extends EntityLivingBase>)Class.forName(imcMessage.getStringValue()));
					ACLogger.imcInfo("Received Shoggoth Food addition %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (ClassNotFoundException e) {
					ACLogger.imcWarning("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equals("registerTransmutatorFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler)Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.TRANSMUTATOR);
					ACLogger.imcInfo("Recieved Transmutator fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					ACLogger.imcWarning("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					ACLogger.imcWarning("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					ACLogger.imcWarning("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equals("registerCrystallizerFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler)Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.CRYSTALLIZER);
					ACLogger.imcInfo("Recieved Crystallizer fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					ACLogger.imcWarning("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					ACLogger.imcWarning("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					ACLogger.imcWarning("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equals("addCrystal")){
				boolean failed = false;
				if(!imcMessage.isItemStackMessage())
					failed = true;
				else{
					ItemStack crystal = imcMessage.getItemStackValue();
					if(crystal == null){
						failed = true;
						return;
					}
					AbyssalCraftAPI.addCrystal(crystal);
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Crystal addition from mo %s", imcMessage.getSender());
				else ACLogger.imcInfo("Received Crystal addition from mo %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp")){
						failed = true;
						return;
					}
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output1 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output1"));
					ItemStack output2 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output2"));
					if(input == null || output1 == null || output2 == null){
						failed = true;
						return;
					}
					AbyssalCraftAPI.addCrystallization(input, output1, output2, stuff.getFloat("xp"));
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Crystallizer recipe from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Crystallizer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addSingleCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")){
						failed = true;
						return;
					}
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if(input == null || output == null){
						failed = true;
						return;
					}
					AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Single Crystallizer recipe from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Single Crystallizer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addOredictCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp")){
						failed = true;
						return;
					}
					String input = stuff.getString("input");
					String output1 = stuff.getString("output1");
					String output2 = stuff.getString("output2");
					if(input == null || output1 == null || output2 == null){
						failed = true;
						return;
					}
					if(stuff.hasKey("quantity1") && stuff.hasKey("quantity2"))
						AbyssalCraftAPI.addCrystallization(input, output1, stuff.getInteger("quantity1"), output2, stuff.getInteger("quantity2"), stuff.getFloat("xp"));
					else AbyssalCraftAPI.addCrystallization(input, output1, output2, stuff.getFloat("xp"));
				}
				if(failed)
					ACLogger.imcWarning("Received invalid OreDictionary Crystallizer recipe from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received OreDictionary Crystallizer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addSingleOredictCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")){
						failed = true;
						return;
					}
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if(input == null || output == null){
						failed = true;
						return;
					}
					if(stuff.hasKey("quantity"))
						AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getInteger("quantity"), stuff.getFloat("xp"));
					else AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Single OreDictionary Crystallizer recipe from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Single OreDictionary Crystallizer recipe from mod %s", imcMessage.getSender());

			}
			else if(imcMessage.key.equals("addTransmutation")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")){
						failed = true;
						return;
					}
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if(input == null || output == null){
						failed = true;
						return;
					}
					AbyssalCraftAPI.addTransmutation(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Transmutator recipe from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Transmutator recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addOredictTransmutation")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")){
						failed = true;
						return;
					}
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if(input == null || output == null){
						failed = true;
						return;
					}
					if(stuff.hasKey("quantity") && stuff.hasKey("meta"))
						AbyssalCraftAPI.addTransmutation(input, output, stuff.getInteger("quantity"), stuff.getInteger("meta"), stuff.getFloat("xp"));
					else if(stuff.hasKey("quantity"))
						AbyssalCraftAPI.addTransmutation(input, output, stuff.getInteger("quantity"), stuff.getFloat("xp"));
					else AbyssalCraftAPI.addTransmutation(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					ACLogger.imcWarning("Received invalid OreDictionary Transmutator recipe from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received OreDictionary Transmutator recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addMaterialization")){ //TODO: rewrite this
				boolean failed = false;
				ItemStack[] items;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input1") || !stuff.hasKey("output")){
						failed = true;
						return;
					}
					ItemStack input1 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input1"));
					ItemStack input2 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input2"));
					ItemStack input3 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input3"));
					ItemStack input4 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input4"));
					ItemStack input5 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input5"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if(input1 == null || output == null){
						failed = true;
						return;
					}
					if(input5 != null){
						items = new ItemStack[5];
						items[0] = input1;
						items[1] = input2;
						items[2] = input3;
						items[3] = input4;
						items[4] = input5;
					} else if(input4 != null){
						items = new ItemStack[4];
						items[0] = input1;
						items[1] = input2;
						items[2] = input3;
						items[3] = input4;
					} else if(input3 != null){
						items = new ItemStack[3];
						items[0] = input1;
						items[1] = input2;
						items[2] = input3;
					} else if(input2 != null){
						items = new ItemStack[2];
						items[0] = input1;
						items[1] = input2;
					} else {
						items = new ItemStack[1];
						items[0] = input1;
					}
					AbyssalCraftAPI.addMaterialization(items, output);
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Materializer recipe from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Materializer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("registerACIntegration"))
				try {
					AbyssalCraftAPI.registerACIntegration((IACPlugin)Class.forName(imcMessage.getStringValue()).newInstance());
					ACLogger.imcInfo("Recieved Integration %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					ACLogger.imcWarning("Could not create a instance of class %s (not a IACPlugin?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					ACLogger.imcWarning("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					ACLogger.imcWarning("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equals("shoggothBlacklist")){
				boolean failed = false;
				if(!imcMessage.isItemStackMessage())
					failed = true;
				else if(Block.getBlockFromItem(imcMessage.getItemStackValue().getItem()) != null){
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					AbyssalCraftAPI.addShoggothBlacklist(Block.getBlockFromItem(imcMessage.getItemStackValue().getItem()));
				} else failed = true;
				if(failed)
					ACLogger.imcWarning("Received invalid Shoggoth Block Blacklist from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Shoggoth Block Blacklist from mod %s", imcMessage.getSender());
			}
			else ACLogger.imcWarning("Received an IMC Message with unknown key (%s) from mod %s!", imcMessage.key, imcMessage.getSender());
		if(!senders.isEmpty())
			ACLogger.imcInfo("Recieved messages from the following mods: %s", senders);
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.modID.equals("abyssalcraft"))
			syncConfig();
	}

	private static void syncConfig(){

		configDimId1 = cfg.get("dimensions", "The Abyssal Wasteland", 50, "The first dimension, full of undead monsters.").getInt();
		configDimId2 = cfg.get("dimensions", "The Dreadlands", 51, "The second dimension, infested with mutated monsters.").getInt();
		configDimId3 = cfg.get("dimensions", "Omothol", 52, "The third dimension, also known as \u00A7oThe Realm of J'zahar\u00A7r.").getInt();
		configDimId4 = cfg.get("dimensions", "The Dark Realm", 53, "Hidden fourth dimension, reached by falling down from Omothol").getInt();

		keepLoaded1 = cfg.get("dimensions", "Prevent unloading: The Abyssal Wasteland", false, "Set true to prevent The Abyssal Wasteland from automatically unloading (might affect performance)").getBoolean();
		keepLoaded2 = cfg.get("dimensions", "Prevent unloading: The Dreadlands", false, "Set true to prevent The Dreadlands from automatically unloading (might affect performance)").getBoolean();
		keepLoaded3 = cfg.get("dimensions", "Prevent unloading: Omothol", false, "Set true to prevent Omothol from automatically unloading (might affect performance)").getBoolean();
		keepLoaded4 = cfg.get("dimensions", "Prevent unloading: The Dark Realm", false, "Set true to prevent The Dark Realm from automatically unloading (might affect performance)").getBoolean();

		configBiomeId1 = cfg.get("biomes", "Darklands", 100, "Dark biome that contains Abyssalnite", 0, 255).getInt();
		configBiomeId2 = cfg.get("biomes", "Abyssal Wasteland", 101, "Abyssal Wasteland biome, contains large quantities of Coralium", 0, 255).getInt();
		configBiomeId3 = cfg.get("biomes", "Dreadlands", 102, "Main Dreadlands biome, desolate.", 0, 255).getInt();
		configBiomeId4 = cfg.get("biomes", "Purified Dreadlands", 103, "Pre-Dreadlands biome, with larger quantities of pure Abyssalnite.", 0, 255).getInt();
		configBiomeId5 = cfg.get("biomes", "Dreadlands Forest", 104, "Forest taken over by the Dread Plague.", 0, 255).getInt();
		configBiomeId6 = cfg.get("biomes", "Dreadlands Mountains", 105, "Mountain equivalent to the Dreadlands biome.", 0, 255).getInt();
		configBiomeId7 = cfg.get("biomes", "Darklands Forest", 106, "Forest equivalent to the Darklands biome.", 0, 255).getInt();
		configBiomeId8 = cfg.get("biomes", "Darklands Plains", 107, "Plains equivalent to the Darklands biome.", 0, 255).getInt();
		configBiomeId9 = cfg.get("biomes", "Darklands Highland", 108, "Plateau version of the Darklands Plains biome.", 0, 255).getInt();
		configBiomeId10 = cfg.get("biomes", "Darklands Mountains", 109, "Mountain equivalent to the Darklands biome.", 0, 255).getInt();
		configBiomeId11 = cfg.get("biomes", "Coralium Infested Swamp", 110, "A swamp biome infested with Coralium.", 0, 255).getInt();
		configBiomeId12 = cfg.get("biomes", "Omothol", 112, "Main biome in Omothol, the realm of J'zahar.", 0, 255).getInt();
		configBiomeId13 = cfg.get("biomes", "Dark Realm", 113, "Dark Realm biome, made out of Darkstone.", 0, 255).getInt();

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

		shouldSpread = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Coralium transmutation", true, "Set true for the Liquid Coralium to convert other liquids into itself in the overworld.").getBoolean();
		shouldInfect = cfg.get(Configuration.CATEGORY_GENERAL, "Coralium Plague spreading", false, "Set true to allow the Coralium Plague to spread outside The Abyssal Wasteland.").getBoolean();
		breakLogic = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Coralium Physics", false, "Set true to allow the Liquid Coralium to break the laws of physics in terms of movement").getBoolean();
		destroyOcean = cfg.get(Configuration.CATEGORY_GENERAL, "Oceanic Coralium Pollution", false, "Set true to allow the Liquid Coralium to spread across oceans. WARNING: The game can crash from this.").getBoolean();
		demonAnimalFire = cfg.get(Configuration.CATEGORY_GENERAL, "Demon Animal burning", true, "Set to false to prevent Demon Animals (Pigs, Cows, Chickens) from burning in the overworld.").getBoolean();
		evilAnimalSpawnRate = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animal spawn rate", 20, "Spawn rate for the Evil Animals (Pigs, Cows, Chickens), keep under 35 to avoid complete annihilation.").getInt();
		darkness = cfg.get(Configuration.CATEGORY_GENERAL, "Darkness", true, "Set to false to disable the random blindness within Darklands biomes").getBoolean();
		particleBlock = cfg.get(Configuration.CATEGORY_GENERAL, "Block particles", true, "Toggles whether blocks that emits particles should do so.").getBoolean();
		particleEntity = cfg.get(Configuration.CATEGORY_GENERAL, "Entity particles", true, "Toggles whether entities that emits particles should do so.").getBoolean();
		hardcoreMode = cfg.get(Configuration.CATEGORY_GENERAL, "Hardcore Mode", false, "Toggles Hardcore mode. If set to true, all mobs will become tougher.").getBoolean();
		endAbyssalZombie = cfg.get(Configuration.CATEGORY_GENERAL, "End Abyssal Zombies", true, "Toggles whether Abyssal Zombies should spawn in The End. Takes effect after restart.").getBoolean();

		darkWeight1 = cfg.get("biome_weight", "Darklands", 10, "Biome weight for the Darklands biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight2 = cfg.get("biome_weight", "Darklands Forest", 10, "Biome weight for the Darklands Forest biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight3 = cfg.get("biome_weight", "Darklands Plains", 10, "Biome weight for the Darklands Plains biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight4 = cfg.get("biome_weight", "Darklands Highland", 10, "Biome weight for the Darklands Highland biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight5 = cfg.get("biome_weight", "Darklands Mountain", 10, "Biome weight for the Darklands Mountain biome, controls the chance of it generating").getInt();
		coraliumWeight = cfg.get("biome_weight", "Coralium Infested Swamp", 10, "Biome weight for the Coralium Infested Swamp biome, controls the chance of it generating", 0, 100).getInt();

		shoggothOoze = cfg.get("shoggoth", "Shoggoth Ooze Spread", true, "Toggles whether or not Lesser Shoggoths should spread their ooze when walking around. (Overrides all the Ooze Spread options)").getBoolean();
		oozeLeaves = cfg.get("shoggoth", "Ooze Spread: Leaves", true, "Toggles ooze spreading on blocks with the Leaves material (Leaf blocks).").getBoolean();
		oozeGrass = cfg.get("shoggoth", "Ooze Spread: Grass", true, "Toggles ooze spreading on blocks with the Grass material (Grass blocks).").getBoolean();
		oozeGround = cfg.get("shoggoth", "Ooze Spread: Ground", true, "Toggles ooze spreading on blocks with the Ground material (Dirt, Podzol).").getBoolean();
		oozeSand = cfg.get("shoggoth", "Ooze Spread: Sand", true, "Toggles ooze spreading on blocks with the Sand material (Sand, Gravel, Soul Sand).").getBoolean();
		oozeRock = cfg.get("shoggoth", "Ooze Spread: Rock", true, "Toggles ooze spreading on blocks with the Rock material (Stone, Cobblestone, Stone Bricks, Ores).").getBoolean();
		oozeCloth = cfg.get("shoggoth", "Ooze Spread: Cloth", true, "Toggles ooze spreading on blocks with the Cloth material (Wool blocks).").getBoolean();
		oozeWood = cfg.get("shoggoth", "Ooze Spread: Wood", true, "Toggles ooze spreading on blocks with the Wood material (Logs, Planks).").getBoolean();
		oozeGourd = cfg.get("shoggoth", "Ooze Spread: Gourd", true, "Toggles ooze spreading on blocks with the Gourd material (Pumpkin, Melon).").getBoolean();
		oozeIron = cfg.get("shoggoth", "Ooze Spread: Iron", true, "Toggles ooze spreading on blocks with the Iron material (Iron blocks, Gold blocks, Diamond blocks).").getBoolean();
		oozeClay = cfg.get("shoggoth", "Ooze Spread: Clay", true, "Toggles ooze spreading on blocks with the Clay material (Clay blocks).").getBoolean();

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

		if(cfg.hasChanged())
			cfg.save();
	}

	private void addOreDictionaryStuff(){

		OreDictionary.registerOre("ingotAbyssalnite", abyingot);
		OreDictionary.registerOre("ingotLiquifiedCoralium", Cingot);
		OreDictionary.registerOre("gemCoralium", Coralium);
		OreDictionary.registerOre("oreAbyssalnite", abyore);
		OreDictionary.registerOre("oreCoralium", Coraliumore);
		OreDictionary.registerOre("oreCoralium", AbyCorOre);
		OreDictionary.registerOre("oreDreadedAbyssalnite", dreadore);
		OreDictionary.registerOre("oreAbyssalnite", abydreadore);
		OreDictionary.registerOre("oreCoraliumStone", CoraliumInfusedStone);
		OreDictionary.registerOre("gemShadow", shadowgem);
		OreDictionary.registerOre("liquidCoralium", Cwater);
		OreDictionary.registerOre("materialCoraliumPearl", Cpearl);
		OreDictionary.registerOre("liquidAntimatter", anticwater);
		OreDictionary.registerOre("logWood", DLTLog);
		OreDictionary.registerOre("logWood", dreadlog);
		OreDictionary.registerOre("plankWood", DLTplank);
		OreDictionary.registerOre("plankWood", dreadplanks);
		OreDictionary.registerOre("treeSapling", DLTSapling);
		OreDictionary.registerOre("treeSapling", dreadsapling);
		OreDictionary.registerOre("treeLeaves", DLTLeaves);
		OreDictionary.registerOre("treeLeaves", dreadleaves);
		OreDictionary.registerOre("blockAbyssalnite", abyblock);
		OreDictionary.registerOre("blockLiquifiedCoralium", corblock);
		OreDictionary.registerOre("blockDreadium", dreadiumblock);
		OreDictionary.registerOre("ingotCoraliumBrick", cbrick);
		OreDictionary.registerOre("ingotDreadium", dreadiumingot);
		OreDictionary.registerOre("dustSulfur", sulfur);
		OreDictionary.registerOre("dustSaltpeter", nitre);
		OreDictionary.registerOre("materialMethane", methane);
		OreDictionary.registerOre("oreSaltpeter", nitreOre);
		OreDictionary.registerOre("crystalIron", new ItemStack(crystal, 1, 0));
		OreDictionary.registerOre("crystalGold", new ItemStack(crystal, 1, 1));
		OreDictionary.registerOre("crystalSulfur", new ItemStack(crystal, 1, 2));
		OreDictionary.registerOre("crystalCarbon", new ItemStack(crystal, 1, 3));
		OreDictionary.registerOre("crystalOxygen", new ItemStack(crystal, 1, 4));
		OreDictionary.registerOre("crystalHydrogen", new ItemStack(crystal, 1, 5));
		OreDictionary.registerOre("crystalNitrogen", new ItemStack(crystal, 1, 6));
		OreDictionary.registerOre("crystalPhosphorus", new ItemStack(crystal, 1, 7));
		OreDictionary.registerOre("crystalPotassium", new ItemStack(crystal, 1, 8));
		OreDictionary.registerOre("crystalNitrate", new ItemStack(crystal, 1, 9));
		OreDictionary.registerOre("crystalMethane", new ItemStack(crystal, 1, 10));
		OreDictionary.registerOre("crystalRedstone", new ItemStack(crystal, 1, 11));
		OreDictionary.registerOre("crystalAbyssalnite", new ItemStack(crystal, 1, 12));
		OreDictionary.registerOre("crystalCoralium", new ItemStack(crystal, 1, 13));
		OreDictionary.registerOre("crystalDreadium", new ItemStack(crystal, 1, 14));
		OreDictionary.registerOre("crystalBlaze", new ItemStack(crystal, 1, 15));
		OreDictionary.registerOre("crystalTin", new ItemStack(crystal, 1, 16));
		OreDictionary.registerOre("crystalCopper", new ItemStack(crystal, 1, 17));
		OreDictionary.registerOre("crystalSilicon", new ItemStack(crystal, 1, 18));
		OreDictionary.registerOre("crystalMagnesium", new ItemStack(crystal, 1, 19));
		OreDictionary.registerOre("crystalAluminium", new ItemStack(crystal, 1, 20));
		OreDictionary.registerOre("crystalSilica", new ItemStack(crystal, 1, 21));
		OreDictionary.registerOre("crystalAlumina", new ItemStack(crystal, 1, 22));
		OreDictionary.registerOre("crystalMagnesia", new ItemStack(crystal, 1, 23));
		OreDictionary.registerOre("crystalZinc", new ItemStack(crystal, 1, 24));
		OreDictionary.registerOre("foodFriedEgg", friedegg);
		OreDictionary.registerOre("oreIron", AbyIroOre);
		OreDictionary.registerOre("oreGold", AbyGolOre);
		OreDictionary.registerOre("oreDiamond", AbyDiaOre);
		OreDictionary.registerOre("oreSaltpeter", AbyNitOre);
		OreDictionary.registerOre("oreTin", AbyTinOre);
		OreDictionary.registerOre("oreCopper", AbyCopOre);
		OreDictionary.registerOre("ingotTin", tinIngot);
		OreDictionary.registerOre("ingotCopper", copperIngot);
		OreDictionary.registerOre("orePearlescentCoralium", AbyPCorOre);
		OreDictionary.registerOre("oreLiquifiedCoralium", AbyLCorOre);
		OreDictionary.registerOre("ingotEthaxiumBrick", ethaxium_brick);
		OreDictionary.registerOre("ingotEthaxium", ethaxiumIngot);
		OreDictionary.registerOre("blockEthaxium", ethaxiumblock);
		OreDictionary.registerOre("nuggetAbyssalnite", new ItemStack(nugget, 1, 0));
		OreDictionary.registerOre("nuggetLiquifiedCoralium", new ItemStack(nugget, 1, 1));
		OreDictionary.registerOre("nuggetDreadium", new ItemStack(nugget, 1, 2));
		OreDictionary.registerOre("nuggetEthaxium", new ItemStack(nugget, 1, 3));
		OreDictionary.registerOre("crystalShardIron", new ItemStack(crystalShard, 1, 0));
		OreDictionary.registerOre("crystalShardGold", new ItemStack(crystalShard, 1, 1));
		OreDictionary.registerOre("crystalShardSulfur", new ItemStack(crystalShard, 1, 2));
		OreDictionary.registerOre("crystalShardCarbon", new ItemStack(crystalShard, 1, 3));
		OreDictionary.registerOre("crystalShardOxygen", new ItemStack(crystalShard, 1, 4));
		OreDictionary.registerOre("crystalShardHydrogen", new ItemStack(crystalShard, 1, 5));
		OreDictionary.registerOre("crystalShardNitrogen", new ItemStack(crystalShard, 1, 6));
		OreDictionary.registerOre("crystalShardPhosphorus", new ItemStack(crystalShard, 1, 7));
		OreDictionary.registerOre("crystalShardPotassium", new ItemStack(crystalShard, 1, 8));
		OreDictionary.registerOre("crystalShardNitrate", new ItemStack(crystalShard, 1, 9));
		OreDictionary.registerOre("crystalShardMethane", new ItemStack(crystalShard, 1, 10));
		OreDictionary.registerOre("crystalShardRedstone", new ItemStack(crystalShard, 1, 11));
		OreDictionary.registerOre("crystalShardAbyssalnite", new ItemStack(crystalShard, 1, 12));
		OreDictionary.registerOre("crystalShardCoralium", new ItemStack(crystalShard, 1, 13));
		OreDictionary.registerOre("crystalShardDreadium", new ItemStack(crystalShard, 1, 14));
		OreDictionary.registerOre("crystalShardBlaze", new ItemStack(crystalShard, 1, 15));
		OreDictionary.registerOre("crystalShardTin", new ItemStack(crystalShard, 1, 16));
		OreDictionary.registerOre("crystalShardCopper", new ItemStack(crystalShard, 1, 17));
		OreDictionary.registerOre("crystalShardSilicon", new ItemStack(crystalShard, 1, 18));
		OreDictionary.registerOre("crystalShardMagnesium", new ItemStack(crystalShard, 1, 19));
		OreDictionary.registerOre("crystalShardAluminium", new ItemStack(crystalShard, 1, 20));
		OreDictionary.registerOre("crystalShardSilica", new ItemStack(crystalShard, 1, 21));
		OreDictionary.registerOre("crystalShardAlumina", new ItemStack(crystalShard, 1, 22));
		OreDictionary.registerOre("crystalShardMagnesia", new ItemStack(crystalShard, 1, 23));
		OreDictionary.registerOre("crystalShardZinc", new ItemStack(crystalShard, 1, 24));
	}

	private void addChestGenHooks(){

		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(axe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(pickaxe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(shovel), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(sword), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(DLTLog), 1, 3, 10));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(CobbleU), 1, 2, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(OC), 1,1,1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 2, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(pickaxeA), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(Corpickaxe), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(helmet), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(plate), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(legs), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(boots), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(CobbleU), 1, 2, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(IronU), 1, 2, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(GoldU), 1, 2, 4));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(DiamondU), 1, 2, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(MRE), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(Coralium), 1, 5, 8));
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
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(configDimId1));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(configDimId2));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(configDimId3));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(configDimId4));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(configDimId1));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(configDimId2));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(configDimId3));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(configDimId4));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abyslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abyslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkstoneslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkstoneslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkcobbleslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkcobbleslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkbrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abydreadbrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abydreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadbrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ethaxiumslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ethaxiumslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(portal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadportal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(omotholportal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Coraliumfire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadfire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(omotholfire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(transmutator_on));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(crystallizer_on));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(engraver));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ODB));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ODBcore));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DSBfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DSCwall));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abyfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abydreadbrickfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadbrickfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebrickfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ethaxiumfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DrTfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abystairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DBstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DCstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abydreadbrickstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadbrickstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebrickstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ethaxiumstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Abybutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DSbutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTbutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Abypplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DSpplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTpplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonepplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Altar));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadaltartop));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadaltarbottom));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(PSDL));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DGhead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Phead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Whead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Ohead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTSapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadsapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(house));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(darkethaxiumstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(darkethaxiumslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(darkethaxiumslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(darkethaxiumfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ritualaltar));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ritualpedestal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cthulhuStatue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(hasturStatue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(jzaharStatue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(azathothStatue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(nyarlathotepStatue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(yogsothothStatue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(shubniggurathStatue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(energyPedestal));
	}

	private void checkBiomeIds(boolean first){
		if(first){
			ACLogger.info("Scanning biome IDs to see if the ones needed are available.");

			if(BiomeGenBase.getBiomeGenArray()[configBiomeId1] != null && dark1)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId1, BiomeGenBase.getBiome(configBiomeId1).biomeName,
						BiomeGenBase.getBiome(configBiomeId1).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId2] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId2, BiomeGenBase.getBiome(configBiomeId2).biomeName,
						BiomeGenBase.getBiome(configBiomeId2).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId3] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId3, BiomeGenBase.getBiome(configBiomeId3).biomeName,
						BiomeGenBase.getBiome(configBiomeId3).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId4] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId4, BiomeGenBase.getBiome(configBiomeId4).biomeName,
						BiomeGenBase.getBiome(configBiomeId4).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId5] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId5, BiomeGenBase.getBiome(configBiomeId5).biomeName,
						BiomeGenBase.getBiome(configBiomeId5).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId6] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId6, BiomeGenBase.getBiome(configBiomeId6).biomeName,
						BiomeGenBase.getBiome(configBiomeId6).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId7] != null && dark2)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId7, BiomeGenBase.getBiome(configBiomeId7).biomeName,
						BiomeGenBase.getBiome(configBiomeId7).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId8] != null && dark3)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId8, BiomeGenBase.getBiome(configBiomeId8).biomeName,
						BiomeGenBase.getBiome(configBiomeId8).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId9] != null && dark4)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId9, BiomeGenBase.getBiome(configBiomeId9).biomeName,
						BiomeGenBase.getBiome(configBiomeId9).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId10] != null && dark5)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId10, BiomeGenBase.getBiome(configBiomeId10).biomeName,
						BiomeGenBase.getBiome(configBiomeId10).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId11] != null && coralium1)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId11, BiomeGenBase.getBiome(configBiomeId11).biomeName,
						BiomeGenBase.getBiome(configBiomeId11).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId12] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId12, BiomeGenBase.getBiome(configBiomeId12).biomeName,
						BiomeGenBase.getBiome(configBiomeId12).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId13] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId13, BiomeGenBase.getBiome(configBiomeId13).biomeName,
						BiomeGenBase.getBiome(configBiomeId13).getBiomeClass().getName()));

			ACLogger.info("None of the needed biome IDs are occupied!");
		} else {
			ACLogger.info("Checking so that no other mod has overridden a used biome ID.");

			if(BiomeGenBase.getBiomeGenArray()[configBiomeId1] != Darklands && dark1)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId1, BiomeGenBase.getBiome(configBiomeId1).biomeName,
						BiomeGenBase.getBiome(configBiomeId1).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId2] != Wastelands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId2, BiomeGenBase.getBiome(configBiomeId2).biomeName,
						BiomeGenBase.getBiome(configBiomeId2).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId3] != Dreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId3, BiomeGenBase.getBiome(configBiomeId3).biomeName,
						BiomeGenBase.getBiome(configBiomeId3).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId4] != AbyDreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId4, BiomeGenBase.getBiome(configBiomeId4).biomeName,
						BiomeGenBase.getBiome(configBiomeId4).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId5] != ForestDreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId5, BiomeGenBase.getBiome(configBiomeId5).biomeName,
						BiomeGenBase.getBiome(configBiomeId5).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId6] != MountainDreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId6, BiomeGenBase.getBiome(configBiomeId6).biomeName,
						BiomeGenBase.getBiome(configBiomeId6).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId7] != DarklandsForest && dark2)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId7, BiomeGenBase.getBiome(configBiomeId7).biomeName,
						BiomeGenBase.getBiome(configBiomeId7).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId8] != DarklandsPlains && dark3)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId8, BiomeGenBase.getBiome(configBiomeId8).biomeName,
						BiomeGenBase.getBiome(configBiomeId8).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId9] != DarklandsHills && dark4)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId9, BiomeGenBase.getBiome(configBiomeId9).biomeName,
						BiomeGenBase.getBiome(configBiomeId9).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId10] != DarklandsMountains && dark5)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId10, BiomeGenBase.getBiome(configBiomeId10).biomeName,
						BiomeGenBase.getBiome(configBiomeId10).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId11] != corswamp && coralium1)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId11, BiomeGenBase.getBiome(configBiomeId11).biomeName,
						BiomeGenBase.getBiome(configBiomeId11).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId12] != omothol)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId12, BiomeGenBase.getBiome(configBiomeId12).biomeName,
						BiomeGenBase.getBiome(configBiomeId12).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId13] != darkRealm)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId13, BiomeGenBase.getBiome(configBiomeId13).biomeName,
						BiomeGenBase.getBiome(configBiomeId13).getBiomeClass().getName()));

			ACLogger.info("None of the biome IDs has been overridden.");
		}
	}

	private static int getUniqueEntityId() {
		do
			startEntityId++;
		while (EntityList.getStringFromID(startEntityId) != null);

		return startEntityId;
	}

	private int getNextAvailableEnchantmentId(){

		int i = 0;

		do{
			i++;
			if(i >= 256) throw new RuntimeException("Out of available Enchantment IDs, AbyssalCraft can't load unless some IDs are freed up!");

		} while(Enchantment.getEnchantmentById(i) != null);

		return i;
	}

	@SuppressWarnings("unchecked")
	private static void registerEntityWithEgg(Class<? extends Entity> entity, String name, int modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
		int id = getUniqueEntityId();
		stringtoIDMapping.put(name, id);
		EntityRegistry.registerModEntity(entity, name, modid, instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityList.idToClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}

	private static void registerDimension(int id, Class<? extends WorldProvider> provider, boolean keepLoaded){
		DimensionManager.registerProviderType(id, provider, keepLoaded);
		DimensionManager.registerDimension(id, id);
	}

	private static void registerBiomeWithTypes(BiomeGenBase biome, int weight, BiomeType btype, Type...types){
		BiomeDictionary.registerBiomeType(biome, types);
		BiomeManager.addBiome(btype, new BiomeEntry(biome, weight));
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
			names = "Enfalas, Saice Shoop";
		}

		return names;
	}
}