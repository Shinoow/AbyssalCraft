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
package com.shinoow.abyssalcraft;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.potion.*;
import net.minecraft.stats.*;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.*;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.item.*;
import com.shinoow.abyssalcraft.common.*;
import com.shinoow.abyssalcraft.common.blocks.*;
import com.shinoow.abyssalcraft.common.blocks.itemblock.*;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.creativetabs.*;
import com.shinoow.abyssalcraft.common.enchantments.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.items.*;
import com.shinoow.abyssalcraft.common.items.armor.*;
import com.shinoow.abyssalcraft.common.potion.*;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.*;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.*;
import com.shinoow.abyssalcraft.common.util.*;
import com.shinoow.abyssalcraft.common.world.*;
import com.shinoow.abyssalcraft.common.world.biome.*;
import com.shinoow.abyssalcraft.update.IUpdateProxy;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.*;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version, dependencies = "required-after:Forge@[forgeversion,);after:Thaumcraft", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory")
public class AbyssalCraft {

	public static final String version = "1.8.4.1";
	public static final String modid = "abyssalcraft";
	public static final String name = "AbyssalCraft";

	@Metadata(AbyssalCraft.modid)
	public static ModMetadata metadata;

	@Instance(AbyssalCraft.modid)
	public static AbyssalCraft instance = new AbyssalCraft();

	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.client.ClientProxy",
			serverSide = "com.shinoow.abyssalcraft.common.CommonProxy")
	public static CommonProxy proxy;

	//Update Checker proxy
	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.update.UpdateProxyClient",
			serverSide = "com.shinoow.abyssalcraft.update.UpdateProxyServer")
	public static IUpdateProxy updateProxy;
	/**Update Checker Ping*/
	private boolean hasPinged = false;

	private boolean dev = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static Map<String, Integer> stringtoIDMapping = new HashMap<String, Integer>();

	public static Configuration cfg;

	public static Fluid CFluid, antifluid;

	public static Achievement mineDS, mineAby, killghoul, enterabyss, killdragon, summonAsorah,
	killAsorah, enterdreadlands, killdreadguard, ghoulhead, killPete, killWilson, killOrange,
	petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, Jzhstaff,
	summonChagaroth, killChagaroth, enterOmothol, enterDarkRealm, killJzahar;

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
	engraver, house;

	//Overworld biomes
	public static BiomeGenBase Darklands, DarklandsForest, DarklandsPlains, DarklandsHills,
	DarklandsMountains, corswamp, corocean;
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
	public static Item OC, Staff, portalPlacer, Cbucket, PSDLfinder, EoA, portalPlacerDL,
	cbrick, cudgel, carbonCluster, denseCarbonCluster, methane, nitre, sulfur, portalPlacerJzh,
	tinIngot, copperIngot, lifeCrystal, shoggothFlesh, eldritchScale, omotholFlesh, necronomicon,
	necronomicon_cor, necronomicon_dre, necronomicon_omt, abyssalnomicon;
	//coin stuff
	public static Item coin, cthulhuCoin, elderCoin, jzaharCoin, engravingBlank, engravingCthulhu, engravingElder, engravingJzahar;
	//crystals (real elements)
	public static Item crystalIron, crystalGold, crystalSulfur, crystalCarbon, crystalOxygen,
	crystalHydrogen, crystalNitrogen, crystalPhosphorus, crystalPotassium, crystalTin, crystalCopper,
	crystalSilicon, crystalMagnesium, crystalAluminium, crystalZinc;
	//crystals (ions/molecules)
	public static Item crystalNitrate, crystalMethane, crystalSilica, crystalAlumina, crystalMagnesia;
	//crystals (Minecraft/AbyssalCraft elements)
	public static Item crystalRedstone, crystalAbyssalnite, crystalCoralium, crystalDreadium,
	crystalBlaze;
	//shadow items
	public static Item shadowfragment, shadowshard, shadowgem, oblivionshard, soulReaper, shadowPlate;
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
	pickaxeC, axeC, shovelC, swordC, hoeC, Corpickaxe, Coraxe, Corshovel, Corsword, Corhoe,
	dreadiumpickaxe, dreadiumaxe, dreadiumshovel, dreadiumsword, dreadiumhoe, dreadhilt, dreadkatana,
	ethPickaxe, ethAxe, ethShovel, ethSword, ethHoe;
	//armor
	public static Item boots, helmet, plate, legs, bootsC, helmetC, plateC, legsC, bootsD,
	helmetD, plateD, legsD, Corboots, Corhelmet, Corplate, Corlegs, CorbootsP, CorhelmetP,
	CorplateP, CorlegsP, Depthsboots, Depthshelmet, Depthsplate, Depthslegs, dreadiumboots,
	dreadiumhelmet, dreadiumplate, dreadiumlegs, dreadiumSboots, dreadiumShelmet, dreadiumSplate,
	dreadiumSlegs, ethBoots, ethHelmet, ethPlate, ethLegs;
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

	public static CreativeTabs tabBlock = new TabACBlocks(CreativeTabs.getNextID(), "acblocks");
	public static CreativeTabs tabItems = new TabACItems(CreativeTabs.getNextID(), "acitems");
	public static CreativeTabs tabTools = new TabACTools(CreativeTabs.getNextID(), "actools");
	public static CreativeTabs tabCombat = new TabACCombat(CreativeTabs.getNextID(), "acctools");
	public static CreativeTabs tabFood = new TabACFood(CreativeTabs.getNextID(), "acfood");
	public static CreativeTabs tabDecoration = new TabACDecoration(CreativeTabs.getNextID(), "acdblocks");
	public static CreativeTabs tabCrystals = new TabACCrystals(CreativeTabs.getNextID(), "accrystals");
	public static CreativeTabs tabCoins = new TabACCoins(CreativeTabs.getNextID(), "accoins");

	//Dimension Ids
	public static int configDimId1, configDimId2, configDimId3, configDimId4;

	public static boolean keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;

	//Biome Ids
	public static int configBiomeId1, configBiomeId2, configBiomeId3, configBiomeId4,
	configBiomeId5, configBiomeId6, configBiomeId7, configBiomeId8, configBiomeId9,
	configBiomeId10, configBiomeId11, configBiomeId12, configBiomeId13, configBiomeId14;

	public static boolean dark1, dark2, dark3, dark4, dark5, coralium1, coralium2;
	public static boolean darkspawn1, darkspawn2, darkspawn3, darkspawn4, darkspawn5, coraliumspawn1, coraliumspawn2;
	public static int darkWeight1, darkWeight2, darkWeight3, darkWeight4, darkWeight5, coraliumWeight;

	public static boolean shouldSpread, shouldInfect, breakLogic, destroyOcean, demonPigFire, updateC, darkness;
	public static int evilPigSpawnRate;

	static int startEntityId = 300;

	public static final int crystallizerGuiID = 30;
	public static final int transmutatorGuiID = 31;
	public static final int engraverGuiID = 32;
	public static final int necronmiconGuiID = 33;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		ACLogger.info("Pre-initializing AbyssalCraft.");
		if(dev)
			ACLogger.info("We appear to be inside a Dev environment, disabling UpdateChecker!");
		metadata = event.getModMetadata();

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftEventHooks());
		FMLCommonHandler.instance().bus().register(new AbyssalCraftEventHooks());
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		instance = this;
		proxy.preInit();

		cfg = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
		AbyssalCraftAPI.initPotionReflection();

		CFluid = new Fluid("liquidcoralium").setDensity(3000).setViscosity(1000).setTemperature(350);
		antifluid = new Fluid("liquidantimatter").setDensity(4000).setViscosity(1500).setTemperature(100);

		Fluid.registerLegacyName(CFluid.getName(), CFluid.getName());
		FluidRegistry.registerFluid(CFluid);
		Fluid.registerLegacyName(antifluid.getName(), antifluid.getName());
		FluidRegistry.registerFluid(antifluid);

		//Blocks
		Darkstone = new BlockDarkstone().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DS").setBlockTextureName(modid + ":" + "DS");
		Darkstone_brick = new BlockACBasic(Material.rock, 1.65F, 12.0F, Block.soundTypeStone).setBlockName("DSB").setBlockTextureName(modid + ":" + "DSB");
		Darkstone_cobble = new BlockACBasic(Material.rock, 2.2F, 12.0F, Block.soundTypeStone).setBlockName("DSC").setBlockTextureName(modid + ":" + "DSC");
		DSGlow = new BlockDSGlow().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(55F).setResistance(3000F).setLightLevel(1.0F).setBlockName("DSGlow");
		Darkbrickslab1 = new BlockACSingleSlab(Darkbrickslab1, Darkbrickslab2, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs1").setBlockTextureName(modid + ":" + "DSB");
		Darkbrickslab2 = new BlockACDoubleSlab(Darkbrickslab1, Darkbrickslab2, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs2").setBlockTextureName(modid + ":" + "DSB");
		Darkcobbleslab1 = new BlockACSingleSlab(Darkcobbleslab1, Darkcobbleslab2, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F) .setResistance(12.0F).setBlockName("DSCs1").setBlockTextureName(modid + ":" + "DSC");
		Darkcobbleslab2 = new BlockACDoubleSlab(Darkcobbleslab1, Darkcobbleslab2, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F) .setResistance(12.0F).setBlockName("DSCs2").setBlockTextureName(modid + ":" + "DSC");
		Darkgrass = new BlockDarklandsgrass().setStepSound(Block.soundTypeGrass).setCreativeTab(AbyssalCraft.tabBlock).setHardness(0.4F).setBlockName("DLG");
		DBstairs = new BlockACStairs(Darkstone_brick).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs");
		DCstairs = new BlockACStairs(Darkstone_cobble).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSCs");
		DLTLeaves = new BlockDLTLeaves().setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setBlockName("DLTL").setBlockTextureName(modid + ":" + "DLTL");
		DLTLog = new BlockDLTLog().setStepSound(Block.soundTypeWood).setHardness(2.0F).setResistance(1.0F).setBlockName("DLTT");
		DLTSapling = new BlockDLTSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DLTS").setBlockTextureName(modid + ":" + "DLTS");
		abystone = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone).setBlockName("AS").setBlockTextureName(modid + ":" + "AS");
		abybrick = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone).setBlockName("ASB").setBlockTextureName(modid + ":" + "ASB");
		abyslab1 = new BlockACSingleSlab(abyslab1, abyslab2, Material.rock, "pickaxe", 2).setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs1").setBlockTextureName(modid + ":" + "ASB");
		abyslab2 = new BlockACDoubleSlab(abyslab1, abyslab2, Material.rock, "pickaxe", 2).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs2").setBlockTextureName(modid + ":" + "ASB");
		abystairs = new BlockACStairs(abybrick, "pickaxe", 2).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("ASBs");
		Coraliumore = new BlockACOre(2, 3.0F, 6.0F).setBlockName("CO").setBlockTextureName(modid + ":" + "CO");
		abyore = new BlockACOre(2, 3.0F, 6.0F).setBlockName("AO").setBlockTextureName(modid + ":" + "AO");
		abyfence = new BlockACFence("ASBf", Material.rock, "pickaxe", 2).setHardness(1.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASBf").setBlockTextureName(modid + ":" + "ASBf");
		DSCwall = new BlockDarkstonecobblewall(Darkstone_cobble).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSCw").setBlockTextureName(modid + ":" + "DSC");
		Crate = new BlockCrate().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("Crate").setBlockTextureName(modid + ":" + "Crate");
		ODB = new BlockODB().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setBlockName("ODB").setBlockTextureName(modid + ":" + "ODBsides");
		abyblock = new IngotBlock(2).setBlockName("BOA").setBlockTextureName(modid + ":" + "BOA");
		CoraliumInfusedStone = new BlockACOre(3, 3.0F, 6.0F).setBlockName("CIS").setBlockTextureName(modid + ":" + "CIS");
		ODBcore = new BlockODBcore().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setBlockName("ODBC");
		portal = new BlockAbyssPortal().setBlockName("AG").setBlockTextureName(modid + ":" + "AG");
		Darkstoneslab1 = new BlockDarkstoneSlab().setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs1");
		Darkstoneslab2 = new BlockDarkstoneSlabDouble().setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs2");
		Coraliumfire = new BlockCoraliumfire().setLightLevel(1.0F).setBlockName("Cfire");
		DSbutton = new BlockACButton(true, "DS").setHardness(0.6F).setResistance(12.0F).setBlockName("DSbb").setBlockTextureName(modid + ":" + "DS");
		DSpplate = new BlockACPressureplate("DS", Material.rock, BlockACPressureplate.Sensitivity.mobs).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSpp").setBlockTextureName(modid + ":" + "DS");
		DLTplank = new BlockACBasic(Material.wood, 2.0F, 5.0F, Block.soundTypeWood).setBlockName("DLTplank").setBlockTextureName(modid + ":" + "DLTplank");
		DLTbutton = new BlockACButton(true, "DLTplank").setHardness(0.5F).setBlockName("DLTplankb").setBlockTextureName(modid + ":" + "DLTplank");
		DLTpplate = new BlockACPressureplate("DLTplank", Material.wood, BlockACPressureplate.Sensitivity.everything).setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("DLTpp").setBlockTextureName(modid + ":" + "DLTplank");
		DLTstairs = new BlockACStairs(DLTplank).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks");
		DLTslab1 = new BlockACSingleSlab(DLTslab1, DLTslab2, Material.rock).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks1").setBlockTextureName(modid + ":" + "DLTplank");
		DLTslab2 = new BlockACDoubleSlab(DLTslab1, DLTslab2, Material.rock).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks2").setBlockTextureName(modid + ":" + "DLTplank");
		corblock = new IngotBlock(5).setBlockName("BOC").setBlockTextureName(modid + ":" + "BOC");
		PSDL = new BlockPSDL().setHardness(50.0F).setResistance(3000F).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("PSDL").setBlockTextureName(modid + ":" + "PSDL");
		AbyCorOre = new BlockACOre(3, 3.0F, 6.0F).setBlockName("ACorO").setBlockTextureName(modid + ":" + "ACorO");
		Altar = new BlockAltar().setStepSound(Block.soundTypeStone).setHardness(4.0F).setResistance(300.0F).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Altar").setBlockTextureName(modid + ":" + "Altar");
		Abybutton = new BlockACButton(false, "pickaxe", 2, "AS").setHardness(0.8F).setResistance(12.0F).setBlockName("ASbb").setBlockTextureName(modid + ":" + "AS");
		Abypplate = new BlockACPressureplate("AS", Material.rock, BlockACPressureplate.Sensitivity.mobs, "pickaxe", 2).setHardness(0.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASpp").setBlockTextureName(modid + ":" + "AS");
		DSBfence = new BlockACFence("DSBf", Material.rock).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSBf");
		DLTfence = new BlockACFence("DLTplank", Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTf");
		dreadore = new BlockACOre(4, 2.5F, 20.0F).setBlockName("DrSO").setBlockTextureName(modid + ":" + "DrSO");
		abydreadore = new BlockACOre(4, 2.5F, 20.0F).setBlockName("AbyDrSO").setBlockTextureName(modid + ":" + "AbyDrSO");
		dreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("DrSB").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("AbyDrSB").setBlockTextureName(modid + ":" + "AbyDrSB");
		dreadlog = new BlockDreadLog().setHardness(2.0F).setResistance(12.0F).setStepSound(Block.soundTypeWood).setBlockName("DrT");
		dreadleaves = new BlockDreadLeaves(false).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setBlockName("DrTL");
		dreadsapling = new BlockDreadSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DrTS").setBlockTextureName(modid + ":" + "DrTS");
		dreadplanks = new BlockACBasic(Material.wood, 2.0F, 5.0F, Block.soundTypeWood).setBlockName("DrTplank").setBlockTextureName(modid + ":" + "DrTplank");
		dreadportal = new BlockDreadlandsPortal().setBlockName("DG").setBlockTextureName(modid + ":" + "DG");
		dreadfire = new BlockDreadFire().setLightLevel(1.0F).setBlockName("Dfire");
		DGhead = new BlockDGhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("DGhead").setBlockTextureName(modid + ":" + "DGhead");
		Cwater = new BlockCLiquid().setResistance(500.0F).setLightLevel(1.0F).setBlockName("Cwater");
		dreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("DrS").setBlockTextureName(modid + ":" + "DrS");
		abydreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("AbyDrS").setBlockTextureName(modid + ":" + "AbyDrS");
		dreadgrass = new BlockDreadGrass().setHardness(0.4F).setStepSound(Block.soundTypeGrass).setBlockName("DrG");
		Phead = new BlockPhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Phead").setBlockTextureName(modid + ":" + "Phead");
		Whead = new BlockWhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Whead").setBlockTextureName(modid + ":" + "Whead");
		Ohead = new BlockOhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Ohead").setBlockTextureName(modid + ":" + "Ohead");
		dreadbrickstairs = new BlockACStairs(dreadbrick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs");
		dreadbrickfence = new BlockACFence("DrSBf", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBf");
		dreadbrickslab1 = new BlockACSingleSlab(dreadbrickslab1, dreadbrickslab2, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs1").setBlockTextureName(modid + ":" + "DrSB");
		dreadbrickslab2 = new BlockACDoubleSlab(dreadbrickslab1, dreadbrickslab2, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs2").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrickstairs = new BlockACStairs(abydreadbrick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs");
		abydreadbrickfence = new BlockACFence("AbyDrSBf", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBf");
		abydreadbrickslab1 = new BlockACSingleSlab(abydreadbrickslab1, abydreadbrickslab2, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs1").setBlockTextureName(modid + ":" + "AbyDrSB");
		abydreadbrickslab2 = new BlockACDoubleSlab(abydreadbrickslab1, abydreadbrickslab2, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs2").setBlockTextureName(modid + ":" + "AbyDrSB");
		anticwater = new BlockAntiliquid().setResistance(500.0F).setLightLevel(0.5F).setBlockName("antiliquid");
		cstone = new BlockCoraliumstone().setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstone").setBlockTextureName(modid + ":" + "cstone");
		cstonebrick = new BlockACBasic(Material.rock, 1.5F, 10.0F, Block.soundTypeStone).setBlockName("cstonebrick").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickfence = new BlockACFence("cstonebrick", Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebrickf");
		cstonebrickslab1 = new BlockACSingleSlab(cstonebrickslab1, cstonebrickslab2, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks1").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickslab2 = new BlockACDoubleSlab(cstonebrickslab1, cstonebrickslab2, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks2").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickstairs = new BlockACStairs(cstonebrick, "pickaxe", 0).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks");
		cstonebutton = new BlockACButton(false, "cstone").setHardness(0.6F).setResistance(12.0F).setBlockName("cstonebutton");
		cstonepplate = new BlockACPressureplate("cstone", Material.rock, BlockACPressureplate.Sensitivity.mobs).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonepplate");
		dreadaltartop = new BlockDreadAltarTop().setHardness(30.0F).setResistance(300.0F).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("dreadaltar1").setBlockTextureName(modid + ":" + "PSDL");
		dreadaltarbottom = new BlockDreadAltarBottom().setHardness(30.0F).setResistance(300.0F).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("dreadaltar2").setBlockTextureName(modid + ":" + "PSDL");
		crystallizer = new BlockCrystallizer(false).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("crystallizer");
		crystallizer_on = new BlockCrystallizer(true).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.875F).setBlockName("crystallizer");
		dreadiumblock = new IngotBlock(6).setBlockName("BOD").setBlockTextureName(modid + ":" + "BOD");
		transmutator = new BlockTransmutator(false).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("transmutator");
		transmutator_on = new BlockTransmutator(true).setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.875F).setBlockName("transmutator");
		dreadguardspawner = new BlockDreadguardSpawner().setBlockName("dreadguardspawner").setBlockTextureName(modid + ":" + "PSDL");
		chagarothspawner = new BlockChagarothSpawner().setBlockName("chagarothspawner").setBlockTextureName(modid + ":" + "PSDL");
		chagarothfistspawner = new BlockChagarothFistSpawner().setBlockName("chagarothfistspawner").setBlockTextureName(modid + ":" + "PSDL");
		DrTfence = new BlockACFence("DrTplank", Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DrTf");
		nitreOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("NO").setBlockTextureName(modid + ":" + "NO");
		AbyIroOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("AIO").setBlockTextureName(modid + ":" + "AIO");
		AbyGolOre = new BlockACOre(2, 5.0F, 10.0F).setBlockName("AGO").setBlockTextureName(modid + ":" + "AGO");
		AbyDiaOre = new BlockACOre(2, 5.0F, 10.0F).setBlockName("ADO").setBlockTextureName(modid + ":" + "ADO");
		AbyNitOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("ANO").setBlockTextureName(modid + ":" + "ANO");
		AbyTinOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("ATO").setBlockTextureName(modid + ":" + "ATO");
		AbyCopOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("ACO").setBlockTextureName(modid + ":" + "ACO");
		AbyPCorOre = new BlockACOre(5, 8.0F, 10.0F).setBlockName("APCorO").setBlockTextureName(modid + ":" + "APCorO");
		AbyLCorOre = new BlockACOre(4, 10.0F, 12.0F).setBlockName("ALCorO").setBlockTextureName(modid + ":" + "ALCorO");
		solidLava = new BlockSolidLava("solidLava");
		ethaxium = new BlockACBasic(Material.rock, "pickaxe", 8, 100.0F, Float.MAX_VALUE, Block.soundTypeStone).setBlockName("Eth").setBlockTextureName(modid + ":" + "Eth");
		ethaxiumbrick = new BlockEthaxiumBrick().setBlockName("EB");
		ethaxiumpillar = new BlockEthaxiumPillar().setBlockName("EBP");
		ethaxiumstairs = new BlockACStairs(ethaxiumbrick, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBs");
		ethaxiumslab1 = new BlockACSingleSlab(ethaxiumslab1, ethaxiumslab2, Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBs1").setBlockTextureName(modid + ":" + "EB");
		ethaxiumslab2 = new BlockACDoubleSlab(ethaxiumslab1, ethaxiumslab2, Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBs2").setBlockTextureName(modid + ":" + "EB");
		ethaxiumfence = new BlockACFence("EB", Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBf");
		omotholstone = new BlockACBasic(Material.rock, "pickaxe", 6, 10.0F, 12.0F, Block.soundTypeStone).setBlockName("OS").setBlockTextureName(modid + ":" + "OS");
		ethaxiumblock = new IngotBlock(8).setResistance(Float.MAX_VALUE).setBlockName("BOE").setBlockTextureName(modid + ":" + "BOE");
		omotholportal = new BlockOmotholPortal().setBlockName("OG").setBlockTextureName(modid + ":" + "OG");
		omotholfire = new BlockOmotholFire().setLightLevel(1.0F).setBlockName("Ofire");
		engraver = new BlockEngraver().setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("engraver");
		house = new BlockHouse().setHardness(1.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeWood).setBlockName("house");

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
		corocean = new BiomeGenCorOcean(configBiomeId12).setColor(522674).setBiomeName("Coralium Infested Ocean");
		omothol = new BiomeGenOmothol(configBiomeId13).setColor(5522674).setBiomeName("Omothol").setDisableRain();
		darkRealm = new BiomeGenDarkRealm(configBiomeId14).setColor(522674).setBiomeName("Dark Realm").setDisableRain();

		//"secret" dev stuff
		devsword = new AbyssalCraftTool().setUnlocalizedName("DEV_BLADE").setTextureName(modid + ":" + "Sword");

		//Misc items
		OC = new ItemOC().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("OC").setTextureName(modid + ":" + "OC");
		Staff = new ItemStaff().setCreativeTab(AbyssalCraft.tabTools).setFull3D().setUnlocalizedName("SOTG").setTextureName(modid + ":" + "SOTG");
		portalPlacer = new ItemPortalPlacer().setUnlocalizedName("GK").setTextureName(modid + ":" + "GK");
		Cbucket = new ItemCBucket(Cwater).setCreativeTab(AbyssalCraft.tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Cbucket").setTextureName(modid + ":" + "Cbucket");
		PSDLfinder = new ItemTrackerPSDL().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("PSDLf").setTextureName(modid + ":" + "PSDLf");
		EoA = new ItemEoA().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("EoA").setTextureName(modid + ":" + "EoA");
		portalPlacerDL = new ItemPortalPlacerDL().setUnlocalizedName("GKD").setTextureName(modid + ":" + "GKD");
		cbrick = new ItemACBasic("cbrick");
		cudgel = new ItemCudgel().setCreativeTab(AbyssalCraft.tabCombat).setFull3D().setUnlocalizedName("cudgel").setTextureName(modid + ":" + "cudgel");
		carbonCluster = new ItemACBasic("CarbC");
		denseCarbonCluster = new ItemACBasic("DCarbC");
		methane = new ItemACBasic("methane");
		nitre = new ItemACBasic("nitre");
		sulfur = new ItemACBasic("sulfur");
		portalPlacerJzh = new ItemPortalPlacerJzh().setUnlocalizedName("GKJ").setTextureName(modid + ":" + "GKJ");
		tinIngot = new ItemACBasic("IT");
		copperIngot = new ItemACBasic("IC");
		lifeCrystal = new ItemACBasic("lifeCrystal");
		coin = new ItemCoin("coin");
		cthulhuCoin = new ItemCoin("cthulhucoin");
		elderCoin = new ItemCoin("eldercoin");
		jzaharCoin = new ItemCoin("jzaharcoin");
		engravingBlank = new ItemEngraving("blank", 50).setCreativeTab(AbyssalCraft.tabCoins).setTextureName(modid + ":" + "engraving_blank");
		engravingCthulhu = new ItemEngraving("cthulhu", 10).setCreativeTab(AbyssalCraft.tabCoins).setTextureName(modid + ":" + "engraving_cthulhu");
		engravingElder = new ItemEngraving("elder", 10).setCreativeTab(AbyssalCraft.tabCoins).setTextureName(modid + ":" + "engraving_elder");
		engravingJzahar = new ItemEngraving("jzahar", 10).setCreativeTab(AbyssalCraft.tabCoins).setTextureName(modid + ":" + "engraving_jzahar");
		shoggothFlesh = new ItemShoggothFlesh();
		eldritchScale = new ItemACBasic("eldritchScale");
		omotholFlesh = new ItemACBasic("omotholflesh");
		necronomicon = new ItemNecronomicon("necronomicon");
		necronomicon_cor = new ItemNecronomicon("necronomicon_cor");
		necronomicon_dre = new ItemNecronomicon("necronomicon_dre");
		necronomicon_omt = new ItemNecronomicon("necronomicon_omt");
		abyssalnomicon = new ItemNecronomicon("abyssalnomicon");

		//Ethaxium
		ethaxium_brick = new ItemACBasic("EB");
		ethaxiumIngot = new ItemACBasic("EI");

		//anti-items
		antibucket = new ItemAntiBucket(anticwater).setCreativeTab(AbyssalCraft.tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Antibucket").setTextureName(modid + ":" + "Antibucket");
		antiBeef = new ItemAntiFood("antiBeef");
		antiChicken = new ItemAntiFood("antiChicken");
		antiPork = new ItemAntiFood("antiPork");
		antiFlesh = new ItemAntiFood("antiFlesh");
		antiBone = new ItemACBasic("antiBone");
		antiSpider_eye = new ItemAntiFood("antiSpider_eye", false);
		antiCorflesh = new ItemCorflesh(0, 0, false, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("antiCF").setTextureName(modid + ":" + "antiCF");
		antiCorbone = new ItemCorbone(0, 0, false, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("antiCB").setTextureName(modid + ":" + "antiCB");

		//crystals
		crystalIron = new ItemCrystal("crystalIron", 0xD9D9D9, "Fe");
		crystalGold = new ItemCrystal("crystalGold", 0xF3CC3E, "Au");
		crystalSulfur = new ItemCrystal("crystalSulfur", 0xF6FF00, "S");
		crystalCarbon = new ItemCrystal("crystalCarbon", 0x3D3D36, "C");
		crystalOxygen = new ItemCrystal("crystalOxygen", 16777215, "O");
		crystalHydrogen = new ItemCrystal("crystalHydrogen", 16777215, "H");
		crystalNitrogen = new ItemCrystal("crystalNitrogen", 16777215, "N");
		crystalPhosphorus = new ItemCrystal("crystalPhosphorus", 0x996A18, "P");
		crystalPotassium = new ItemCrystal("crystalPotassium", 0xD9D9D9, "K");
		crystalNitrate = new ItemCrystal("crystalNitrate", 0x1500FF, "NO\u2083");
		crystalMethane = new ItemCrystal("crystalMethane", 0x19FC00, "CH\u2084");
		crystalRedstone = new ItemCrystal("crystalRedstone", 0xFF0000, "none");
		crystalAbyssalnite = new ItemCrystal("crystalAbyssalnite", 0x8002BF, "An");
		crystalCoralium = new ItemCrystal("crystalCoralium", 0x00FFEE, "Cor");
		crystalDreadium = new ItemCrystal("crystalDreadium", 0xB00000, "Dr");
		crystalBlaze = new ItemCrystal("crystalBlaze", 0xFFCC00, "none");
		crystalTin = new ItemCrystal("crystalTin", 0xD9D8D7, "Sn");
		crystalCopper = new ItemCrystal("crystalCopper", 0xE89207, "Cu");
		crystalSilicon = new ItemCrystal("crystalSilicon", 0xD9D9D9, "Si");
		crystalMagnesium = new ItemCrystal("crystalMagnesium", 0xD9D9D9, "Mg");
		crystalAluminium = new ItemCrystal("crystalAluminium", 0xD9D9D9, "Al");
		crystalSilica = new ItemCrystal("crystalSilica", 16777215, "SiO\u2082");
		crystalAlumina = new ItemCrystal("crystalAlumina", 0xD9D8D9, "Al\u2082O\u2083");
		crystalMagnesia = new ItemCrystal("crystalMagnesia", 16777215, "MgO");
		crystalZinc = new ItemCrystal("crystalZinc", 0xD7D8D9, "Zn");

		//Shadow items
		shadowfragment = new ItemACBasic("SF");
		shadowshard = new ItemACBasic("SS");
		shadowgem = new ItemACBasic("SG");
		oblivionshard = new ItemACBasic("OS");
		shadowPlate = new ItemACBasic("shadowplate");

		//Dread items
		Dreadshard = new ItemACBasic("DSOA");
		dreadchunk = new ItemACBasic("DAC");
		dreadiumingot = new ItemACBasic("DI");
		dreadfragment = new ItemACBasic("DF");
		dreadcloth = new ItemACBasic("DC");
		dreadplate = new ItemACBasic("DPP");
		dreadblade = new ItemACBasic("DB");
		dreadKey = new ItemACBasic("DK");

		//Abyssalnite items
		abychunk = new ItemACBasic("AC");
		abyingot = new ItemACBasic("AI");

		//Coralium items
		Coraliumcluster2 = new ItemCoraliumcluster("2").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCA").setTextureName(modid + ":" + "CGCA");
		Coraliumcluster3 = new ItemCoraliumcluster("3").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCB").setTextureName(modid + ":" + "CGCB");
		Coraliumcluster4 = new ItemCoraliumcluster("4").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCC").setTextureName(modid + ":" + "CGCC");
		Coraliumcluster5 = new ItemCoraliumcluster("5").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCD").setTextureName(modid + ":" + "CGCD");
		Coraliumcluster6 = new ItemCoraliumcluster("6").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCE").setTextureName(modid + ":" + "CGCE");
		Coraliumcluster7 = new ItemCoraliumcluster("7").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCF").setTextureName(modid + ":" + "CGCF");
		Coraliumcluster8 = new ItemCoraliumcluster("8").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCG").setTextureName(modid + ":" + "CGCG");
		Coraliumcluster9 = new ItemCoraliumcluster("9").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCH").setTextureName(modid + ":" + "CGCH");
		Cpearl = new ItemACBasic("CP");
		Cchunk = new ItemACBasic("CC");
		Cingot = new ItemACBasic("RCI");
		Cplate = new ItemACBasic("CPP");
		Coralium = new ItemACBasic("CG");
		Corb = new ItemCorb().setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("TG").setTextureName(modid + ":" + "TG");
		Corflesh = new ItemCorflesh(2, 0.1F, false, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("CF").setTextureName(modid + ":" + "CF");
		Corbone = new ItemCorbone(2, 0.1F, false, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("CB").setTextureName(modid + ":" + "CB");
		corbow = new ItemCoraliumBow(20.0F, 0, 8, 16).setUnlocalizedName("Corbow").setTextureName(modid + ":" + "Corbow");

		//Tools
		pickaxe = new ItemDarkstonePickaxe(AbyssalCraftAPI.darkstoneTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DP").setTextureName(modid + ":" + "DP");
		axe = new ItemDarkstoneAxe(AbyssalCraftAPI.darkstoneTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DA").setTextureName(modid + ":" + "DA");
		shovel = new ItemDarkstoneShovel(AbyssalCraftAPI.darkstoneTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DS").setTextureName(modid + ":" + "DS");
		sword = new ItemDarkstoneSword(EnumToolMaterialAC.DARKSTONE).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("DSW").setTextureName(modid + ":" + "DSW");
		hoe = new ItemDarkstoneHoe(AbyssalCraftAPI.darkstoneTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DH").setTextureName(modid + ":" + "DH");
		pickaxeA = new ItemAbyssalnitePickaxe(AbyssalCraftAPI.abyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AP").setTextureName(modid + ":" + "AP");
		axeA = new ItemAbyssalniteAxe(AbyssalCraftAPI.abyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AA").setTextureName(modid + ":" + "AA");
		shovelA = new ItemAbyssalniteShovel(AbyssalCraftAPI.abyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AS").setTextureName(modid + ":" + "AS");
		swordA = new ItemAbyssalniteSword(EnumToolMaterialAC.ABYSSALNITE).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("ASW").setTextureName(modid + ":" + "ASW");
		hoeA = new ItemAbyssalniteHoe(AbyssalCraftAPI.abyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AH").setTextureName(modid + ":" + "AH");
		pickaxeC = new ItemAbyssalniteCPickaxe(AbyssalCraftAPI.coraliumInfusedAbyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAP").setTextureName(modid + ":" + "CIAP");
		axeC = new ItemAbyssalniteCAxe(AbyssalCraftAPI.coraliumInfusedAbyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAA").setTextureName(modid + ":" + "CIAA");
		shovelC = new ItemAbyssalniteCShovel(AbyssalCraftAPI.coraliumInfusedAbyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAS").setTextureName(modid + ":" + "CIAS");
		swordC = new ItemAbyssalniteCSword(EnumToolMaterialAC.ABYSSALNITE_C).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("CIASW").setTextureName(modid + ":" + "CIASW");
		hoeC = new ItemAbyssalniteCHoe(AbyssalCraftAPI.coraliumInfusedAbyssalniteTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAH").setTextureName(modid + ":" + "CIAH");
		Corpickaxe = new ItemCoraliumPickaxe(AbyssalCraftAPI.refinedCoraliumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCP").setTextureName(modid + ":" + "RCP");
		Coraxe = new ItemCoraliumAxe(AbyssalCraftAPI.refinedCoraliumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCA").setTextureName(modid + ":" + "RCA");
		Corshovel = new ItemCoraliumShovel(AbyssalCraftAPI.refinedCoraliumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCS").setTextureName(modid + ":" + "RCS");
		Corsword = new ItemCoraliumSword(EnumToolMaterialAC.CORALIUM).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("RCSW").setTextureName(modid + ":" + "RCSW");
		Corhoe = new ItemCoraliumHoe(AbyssalCraftAPI.refinedCoraliumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCH").setTextureName(modid + ":" + "RCH");
		dreadiumpickaxe = new ItemDreadiumPickaxe(AbyssalCraftAPI.dreadiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDP").setTextureName(modid + ":" + "DDP");
		dreadiumaxe = new ItemDreadiumAxe(AbyssalCraftAPI.dreadiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDA").setTextureName(modid + ":" + "DDA");
		dreadiumshovel = new ItemDreadiumShovel(AbyssalCraftAPI.dreadiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDS").setTextureName(modid + ":" + "DDS");
		dreadiumsword = new ItemDreadiumSword(EnumToolMaterialAC.DREADIUM).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("DDSW").setTextureName(modid + ":" + "DDSW");
		dreadiumhoe = new ItemDreadiumHoe(AbyssalCraftAPI.dreadiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDH").setTextureName(modid + ":" + "DDH");
		dreadhilt = new ItemDreadiumKatana("hilt", 5.0F, 200);
		dreadkatana = new ItemDreadiumKatana("katana", 20.0F, 2000);
		soulReaper = new ItemSoulReaper("soulReaper");
		ethPickaxe = new ItemEthaxiumPickaxe(AbyssalCraftAPI.ethaxiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("EP").setTextureName(modid + ":" + "EP");
		ethAxe = new ItemEthaxiumAxe(AbyssalCraftAPI.ethaxiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("EA").setTextureName(modid + ":" + "EA");
		ethShovel = new ItemEthaxiumShovel(AbyssalCraftAPI.ethaxiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("ES").setTextureName(modid + ":" + "ES");
		ethSword = new ItemEthaxiumSword(EnumToolMaterialAC.ETHAXIUM).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("ESW").setTextureName(modid + ":" + "ESW");
		ethHoe = new ItemEthaxiumHoe(AbyssalCraftAPI.ethaxiumTool).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("EH").setTextureName(modid + ":" + "EH");

		//Armor
		boots = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 3).setUnlocalizedName("AAB").setTextureName(modid + ":" + "AAB");
		helmet = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 0).setUnlocalizedName("AAH").setTextureName(modid + ":" + "AAh");
		plate = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 1).setUnlocalizedName("AAC").setTextureName(modid + ":" + "AAC");
		legs = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 2).setUnlocalizedName("AAP").setTextureName(modid + ":" + "AAP");
		bootsC = new ItemAbyssalniteCArmor(AbyssalCraftAPI.coraliumInfusedAbyssalniteArmor, 5, 3).setUnlocalizedName("ACIAB").setTextureName(modid + ":" + "ACIAB");
		helmetC = new ItemAbyssalniteCArmor(AbyssalCraftAPI.coraliumInfusedAbyssalniteArmor, 5, 0).setUnlocalizedName("ACIAH").setTextureName(modid + ":" + "ACIAH");
		plateC = new ItemAbyssalniteCArmor(AbyssalCraftAPI.coraliumInfusedAbyssalniteArmor, 5, 1).setUnlocalizedName("ACIAC").setTextureName(modid + ":" + "ACIAC");
		legsC = new ItemAbyssalniteCArmor(AbyssalCraftAPI.coraliumInfusedAbyssalniteArmor, 5, 2).setUnlocalizedName("ACIAP").setTextureName(modid + ":" + "ACIAP");
		bootsD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 3).setUnlocalizedName("ADAB").setTextureName(modid + ":" + "ADAB");
		helmetD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 0).setUnlocalizedName("ADAH").setTextureName(modid + ":" + "ADAH");
		plateD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 1).setUnlocalizedName("ADAC").setTextureName(modid + ":" + "ADAC");
		legsD = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 2).setUnlocalizedName("ADAP").setTextureName(modid + ":" + "ADAP");
		Corboots = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 3).setUnlocalizedName("ACB").setTextureName(modid + ":" + "ACB");
		Corhelmet = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 0).setUnlocalizedName("ACH").setTextureName(modid + ":" + "ACH");
		Corplate = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 1).setUnlocalizedName("ACC").setTextureName(modid + ":" + "ACC");
		Corlegs = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 2).setUnlocalizedName("ACP").setTextureName(modid + ":" + "ACP");
		CorbootsP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 3).setUnlocalizedName("ACBP").setTextureName(modid + ":" + "ACBP");
		CorhelmetP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 0).setUnlocalizedName("ACHP").setTextureName(modid + ":" + "ACHP");
		CorplateP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 1).setUnlocalizedName("ACCP").setTextureName(modid + ":" + "ACCP");
		CorlegsP = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 2).setUnlocalizedName("ACPP").setTextureName(modid + ":" + "ACPP");
		Depthsboots = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 3).setUnlocalizedName("ADB").setTextureName(modid + ":" + "ADB");
		Depthshelmet = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 0).setUnlocalizedName("ADH").setTextureName(modid + ":" + "ADH");
		Depthsplate = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 1).setUnlocalizedName("ADC").setTextureName(modid + ":" + "ADC");
		Depthslegs = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 2).setUnlocalizedName("ADP").setTextureName(modid + ":" + "ADP");
		dreadiumboots = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 3).setUnlocalizedName("ADDB").setTextureName(modid + ":" + "ADDB");
		dreadiumhelmet = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 0).setUnlocalizedName("ADDH").setTextureName(modid + ":" + "ADDH");
		dreadiumplate = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 1).setUnlocalizedName("ADDC").setTextureName(modid + ":" + "ADDC");
		dreadiumlegs = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 2).setUnlocalizedName("ADDP").setTextureName(modid + ":" + "ADDP");
		dreadiumSboots = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 3).setUnlocalizedName("ADSB").setTextureName(modid + ":" + "ADSB");
		dreadiumShelmet = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 0).setUnlocalizedName("ADSH").setTextureName(modid + ":" + "ADSH");
		dreadiumSplate = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 1).setUnlocalizedName("ADSC").setTextureName(modid + ":" + "ADSC");
		dreadiumSlegs = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 2).setUnlocalizedName("ADSP").setTextureName(modid + ":" + "ADSP");
		ethBoots = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 3).setUnlocalizedName("AEB").setTextureName(modid + ":" + "AEB");
		ethHelmet = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 0).setUnlocalizedName("AEH").setTextureName(modid + ":" + "AEH");
		ethPlate = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 1).setUnlocalizedName("AEC").setTextureName(modid + ":" + "AEC");
		ethLegs = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 2).setUnlocalizedName("AEP").setTextureName(modid + ":" + "AEP");

		//Upgrade kits
		CobbleU = new ItemUpgradeKit("Wood", "Cobblestone").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CobU").setTextureName(modid + ":" + "CobU");
		IronU = new ItemUpgradeKit("Cobblestone", "Iron").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("IroU").setTextureName(modid + ":" + "IroU");
		GoldU = new ItemUpgradeKit("Iron", "Gold").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("GolU").setTextureName(modid + ":" + "GolU");
		DiamondU = new ItemUpgradeKit("Gold", "Diamond").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("DiaU").setTextureName(modid + ":" + "DiaU");
		AbyssalniteU = new ItemUpgradeKit("Diamond", "Abyssalnite").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("AbyU").setTextureName(modid + ":" + "AbyU");
		CoraliumU = new ItemUpgradeKit("Abyssalnite", "Coralium").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CorU").setTextureName(modid + ":" + "CorU");
		DreadiumU = new ItemUpgradeKit("Coralium", "Dreadium").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("DreU").setTextureName(modid + ":" + "DreU");
		EthaxiumU = new ItemUpgradeKit("Dreadium", "Ethaxium").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("EthU").setTextureName(modid + ":" + "EthU");

		//Foodstuffs
		ironp = new ItemACBasic("plate");
		MRE = new ItemPlatefood(255, 1F, false).setUnlocalizedName("MRE").setTextureName(modid + ":" + "MRE");
		chickenp = new ItemPlatefood(12, 1.2F, false).setUnlocalizedName("ChiP").setTextureName(modid + ":" + "ChiP");
		porkp = new ItemPlatefood(16, 1.6F, false).setUnlocalizedName("PorP").setTextureName(modid + ":" + "PorP");
		beefp = new ItemPlatefood(6, 0.6F, false).setUnlocalizedName("BeeP").setTextureName(modid + ":" + "BeeP");
		fishp = new ItemPlatefood(10, 1.2F, false).setUnlocalizedName("FisP").setTextureName(modid + ":" + "FisP");
		dirtyplate = new ItemACBasic("dirtyplate");
		friedegg = new ItemFriedegg(5, 0.6F, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("friedegg").setTextureName(modid + ":" + "friedegg");
		eggp = new ItemPlatefood(10, 1.2F, false).setUnlocalizedName("eggp").setTextureName(modid + ":" + "eggp");
		cloth = new ItemWashCloth().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("cloth").setTextureName(modid + ":" + "cloth");

		GameRegistry.registerTileEntity(TileEntityCrate.class, "tileEntityCrate");
		GameRegistry.registerTileEntity(TileEntityPSDL.class, "tileEntityPSDL");
		GameRegistry.registerTileEntity(TileEntityAltar.class, "tileEntityAltar");
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

		Cplague = new PotionCplague(AbyssalCraftAPI.potionId1, true, 0x00FFFF).setIconIndex(1, 0).setPotionName("potion.Cplague");
		AbyssalCraftAPI.addPotionRequirements(Cplague.id, "0 & 1 & !2 & 3 & 0+6");
		crystalCoralium.setPotionEffect("+0+1-2+3&4+4+13");
		Dplague = new PotionDplague(AbyssalCraftAPI.potionId2, true, 0xAD1313).setIconIndex(1, 0).setPotionName("potion.Dplague");
		AbyssalCraftAPI.addPotionRequirements(Dplague.id, "0 & 1 & 2 & 3 & 2+6");
		AbyssalCraftAPI.addPotionAmplifiers(Dplague.id, "5");
		crystalDreadium.setPotionEffect("0+1+2+3+13&4-4");
		antiMatter = new PotionAntimatter(AbyssalCraftAPI.potionId3, true, 0xFFFFFF).setIconIndex(1, 0).setPotionName("potion.Antimatter");
		AbyssalCraftAPI.addPotionRequirements(antiMatter.id, "0 & 1 & 2 & !3 & 2+6");
		antibucket.setPotionEffect("0+1+2-3+13&4-4");
		crystalSulfur.setPotionEffect(PotionHelper.spiderEyeEffect);
		crystalOxygen.setPotionEffect(PotionHelper.field_151423_m);
		crystalHydrogen.setPotionEffect("-0-1+2+3&4-4+13");
		crystalNitrogen.setPotionEffect("-0+1-2+3&4-4+13");

		coraliumE = new EnchantmentWeaponInfusion(AbyssalCraftAPI.enchId1, 2, "coralium");
		dreadE = new EnchantmentWeaponInfusion(AbyssalCraftAPI.enchId2, 2, "dread");
		lightPierce = new EnchantmentLightPierce(AbyssalCraftAPI.enchId3);
		ironWall = new EnchantmentIronWall(AbyssalCraftAPI.enchId4, 2);

		//Block Register
		GameRegistry.registerBlock(Darkstone, "darkstone");
		GameRegistry.registerBlock(Darkstone_cobble, "darkstone_cobble");
		GameRegistry.registerBlock(Darkstone_brick, "darkstone_brick");
		GameRegistry.registerBlock(DSGlow, "dsglow");
		GameRegistry.registerBlock(Darkbrickslab1, ItemDarkbrickSlab.class, "darkbrickslab1");
		GameRegistry.registerBlock(Darkbrickslab2, ItemDarkbrickSlab.class, "darkbrickslab2");
		GameRegistry.registerBlock(Darkcobbleslab1, ItemDarkcobbleSlab.class, "darkcobblelsab1");
		GameRegistry.registerBlock(Darkcobbleslab2, ItemDarkcobbleSlab.class, "darkcobblelsab2");
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
		GameRegistry.registerBlock(Crate, "Crate");
		GameRegistry.registerBlock(portal, "portal");
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
		GameRegistry.registerBlock(Altar, ItemAltar.class, "altar");
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
		GameRegistry.registerBlock(abydreadbrickslab1, ItemAbyDreadbrickSlab.class, "abydreadbrick1");
		GameRegistry.registerBlock(abydreadbrickslab2, ItemAbyDreadbrickSlab.class, "abydreadbrick2");
		GameRegistry.registerBlock(anticwater, "antiwater");
		GameRegistry.registerBlock(cstone, "cstone");
		GameRegistry.registerBlock(cstonebrick, "cstonebrick");
		GameRegistry.registerBlock(cstonebrickfence, "cstonebrickfence");
		GameRegistry.registerBlock(cstonebrickslab1, ItemCstonebrickSlab.class, "cstonebrickslab1");
		GameRegistry.registerBlock(cstonebrickslab2, ItemCstonebrickSlab.class, "cstobebrickslab2");
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
		GameRegistry.registerBlock(ethaxiumslab1, ItemBlockColorName.class, "ethaxiumbrickslab1");
		GameRegistry.registerBlock(ethaxiumslab2, ItemBlockColorName.class, "ethaxiumbrickslab2");
		GameRegistry.registerBlock(ethaxiumfence, ItemBlockColorName.class, "ethaxiumfence");
		GameRegistry.registerBlock(ethaxiumblock, ItemBlockColorName.class, "ethaxiumblock");
		GameRegistry.registerBlock(omotholstone, "omotholstone");
		GameRegistry.registerBlock(omotholportal, "omotholportal");
		GameRegistry.registerBlock(omotholfire, "omotholfire");
		GameRegistry.registerBlock(engraver, "engraver");
		GameRegistry.registerBlock(house, "engraver_on");

		//Item Register
		GameRegistry.registerItem(devsword, "devsword");
		GameRegistry.registerItem(OC, "OC");
		GameRegistry.registerItem(portalPlacer, "portalplacer");
		GameRegistry.registerItem(Staff, "staff");
		GameRegistry.registerItem(Cbucket, "cbucket");
		GameRegistry.registerItem(PSDLfinder, "psdlfinder");
		GameRegistry.registerItem(EoA, "eoa");
		GameRegistry.registerItem(portalPlacerDL, "portalplacerdl");
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
		GameRegistry.registerItem(Corb, "corb");
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
		GameRegistry.registerItem(pickaxeC, "cpickaxe");
		GameRegistry.registerItem(axeC, "caxe");
		GameRegistry.registerItem(shovelC, "cshovel");
		GameRegistry.registerItem(swordC, "csword");
		GameRegistry.registerItem(hoeC, "choe");
		GameRegistry.registerItem(Corpickaxe, "corpick");
		GameRegistry.registerItem(Coraxe, "coraxe");
		GameRegistry.registerItem(Corshovel, "corshovel");
		GameRegistry.registerItem(Corsword, "corsword");
		GameRegistry.registerItem(Corhoe, "corhoe");
		GameRegistry.registerItem(boots, "aboots");
		GameRegistry.registerItem(helmet, "ahelmet");
		GameRegistry.registerItem(plate, "aplate");
		GameRegistry.registerItem(legs, "alegs");
		GameRegistry.registerItem(bootsC, "cboots");
		GameRegistry.registerItem(helmetC, "chelmet");
		GameRegistry.registerItem(plateC, "cplate");
		GameRegistry.registerItem(legsC, "clegs");
		GameRegistry.registerItem(bootsD, "dboots");
		GameRegistry.registerItem(helmetD, "dhelmet");
		GameRegistry.registerItem(plateD, "dplate");
		GameRegistry.registerItem(legsD, "dlegs");
		GameRegistry.registerItem(Corboots, "corboots");
		GameRegistry.registerItem(Corhelmet, "corhelmet");
		GameRegistry.registerItem(Corplate, "corplate");
		GameRegistry.registerItem(Corlegs, "corlegs");
		GameRegistry.registerItem(CorbootsP, "corbootsp");
		GameRegistry.registerItem(CorhelmetP, "corhelmetp");
		GameRegistry.registerItem(CorplateP, "corplatep");
		GameRegistry.registerItem(CorlegsP, "corlegsp");
		GameRegistry.registerItem(Depthsboots, "depthsboots");
		GameRegistry.registerItem(Depthshelmet, "depthshelmet");
		GameRegistry.registerItem(Depthsplate, "depthsplate");
		GameRegistry.registerItem(Depthslegs, "depthslegs");
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
		GameRegistry.registerItem(dreadiumingot, "dreadumingot");
		GameRegistry.registerItem(dreadfragment, "dreadfragment");
		GameRegistry.registerItem(dreadiumboots, "dreadiumboots");
		GameRegistry.registerItem(dreadiumhelmet, "dreadiumhelmet");
		GameRegistry.registerItem(dreadiumplate, "dreadiumplate");
		GameRegistry.registerItem(dreadiumlegs, "dreadiumlegs");
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
		GameRegistry.registerItem(crystalIron, "crystaliron");
		GameRegistry.registerItem(crystalGold, "crystalgold");
		GameRegistry.registerItem(crystalSulfur, "crystalsulfur");
		GameRegistry.registerItem(crystalCarbon, "crystalcarbon");
		GameRegistry.registerItem(crystalOxygen, "crystaloxygen");
		GameRegistry.registerItem(crystalHydrogen, "crystalhydrogen");
		GameRegistry.registerItem(crystalNitrogen, "crystalnitrogen");
		GameRegistry.registerItem(crystalPhosphorus, "crystalphosphorus");
		GameRegistry.registerItem(crystalPotassium, "crystalpotassium");
		GameRegistry.registerItem(crystalNitrate, "crystalnitrate");
		GameRegistry.registerItem(crystalMethane, "crystalmethane");
		GameRegistry.registerItem(crystalRedstone, "crystalredstone");
		GameRegistry.registerItem(crystalAbyssalnite, "crystalabyssalnite");
		GameRegistry.registerItem(crystalCoralium, "crystalcoralium");
		GameRegistry.registerItem(crystalDreadium, "crystaldreadium");
		GameRegistry.registerItem(crystalBlaze, "crystalblaze");
		GameRegistry.registerItem(dreadcloth, "dreadcloth");
		GameRegistry.registerItem(dreadplate, "dreadplate");
		GameRegistry.registerItem(dreadblade, "dreadblade");
		GameRegistry.registerItem(dreadhilt, "dreadhilt");
		GameRegistry.registerItem(dreadkatana, "dreadkatana");
		GameRegistry.registerItem(dreadKey, "dreadkey");
		GameRegistry.registerItem(portalPlacerJzh, "portalplacerjzh");
		GameRegistry.registerItem(dreadiumSboots, "dreadiumsamuraiboots");
		GameRegistry.registerItem(dreadiumShelmet, "dreadiumsamuraihelmet");
		GameRegistry.registerItem(dreadiumSplate, "dreadiumsamuraiplate");
		GameRegistry.registerItem(dreadiumSlegs, "dreadiumsamurailegs");
		GameRegistry.registerItem(tinIngot, "tingingot");
		GameRegistry.registerItem(copperIngot, "copperingot");
		GameRegistry.registerItem(crystalTin, "crystaltin");
		GameRegistry.registerItem(crystalCopper, "crystalcopper");
		GameRegistry.registerItem(crystalSilicon, "crystalsilicon");
		GameRegistry.registerItem(crystalMagnesium, "crystalmagnesium");
		GameRegistry.registerItem(crystalAluminium, "crystalaluminium");
		GameRegistry.registerItem(crystalSilica, "crystalsilica");
		GameRegistry.registerItem(crystalAlumina, "crystalalumina");
		GameRegistry.registerItem(crystalMagnesia, "crystalmagnesia");
		GameRegistry.registerItem(crystalZinc, "crystalzinc");
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
		GameRegistry.registerItem(ethBoots, "ethaxiumboots");
		GameRegistry.registerItem(ethHelmet, "ethaxiumhelmet");
		GameRegistry.registerItem(ethPlate, "ethaxiumplate");
		GameRegistry.registerItem(ethLegs, "ethaxiumlegs");
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
		//		GameRegistry.registerItem(shoggothFlesh, "shoggothflesh");
		//		GameRegistry.registerItem(shadowPlate, "shadowplate");

		CFluid.setBlock(Cwater);
		antifluid.setBlock(anticwater);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(CFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Cbucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(Cwater, Cbucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(antifluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(antibucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(anticwater, antibucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		//Biome
		if(dark1 == true){
			BiomeDictionary.registerBiomeType(Darklands, Type.WASTELAND);
			BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.Darklands, darkWeight1));
		}
		if(dark2 == true){
			BiomeDictionary.registerBiomeType(DarklandsForest, Type.FOREST);
			BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsForest, darkWeight2));
		}
		if(dark3 == true){
			BiomeDictionary.registerBiomeType(DarklandsPlains, Type.PLAINS);
			BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsPlains, darkWeight3));
		}
		if(dark4 == true){
			BiomeDictionary.registerBiomeType(DarklandsHills, Type.HILLS);
			BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsHills, darkWeight4));
		}
		if(dark5 == true){
			BiomeDictionary.registerBiomeType(DarklandsMountains, Type.MOUNTAIN);
			BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsMountains, darkWeight5));
		}
		if(coralium1 == true){
			BiomeDictionary.registerBiomeType(corswamp, Type.SWAMP);
			BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.corswamp, coraliumWeight));
		}
		if(coralium2 == true){
			BiomeDictionary.registerBiomeType(corocean, Type.WATER);
			BiomeManager.oceanBiomes.add(AbyssalCraft.corocean);
		}
		if(darkspawn1 == true)
			BiomeManager.addSpawnBiome(AbyssalCraft.Darklands);
		if(darkspawn2 == true)
			BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsForest);
		if(darkspawn3 == true)
			BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsPlains);
		if(darkspawn4 == true)
			BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsHills);
		if(darkspawn5 == true)
			BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsMountains);
		if(coraliumspawn1 == true)
			BiomeManager.addSpawnBiome(AbyssalCraft.corswamp);
		if(coraliumspawn2 == true)
			BiomeManager.addSpawnBiome(AbyssalCraft.corocean);

		BiomeDictionary.registerBiomeType(Wastelands, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(Dreadlands, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(AbyDreadlands, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(MountainDreadlands, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(ForestDreadlands, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(omothol, Type.MAGICAL);
		BiomeDictionary.registerBiomeType(darkRealm, Type.MAGICAL);

		//Dimension
		registerDimension(configDimId1, WorldProviderAbyss.class, keepLoaded1);
		registerDimension(configDimId2, WorldProviderDreadlands.class, keepLoaded2);
		registerDimension(configDimId3, WorldProviderOmothol.class, keepLoaded3);
		registerDimension(configDimId4, WorldProviderDarkRealm.class, keepLoaded4);

		//Mobs
		EntityRegistry.registerModEntity(EntityDepthsGhoul.class, "depthsghoul", 25, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.SWAMP));
		registerEntityEgg(EntityDepthsGhoul.class, 0x36A880, 0x012626, "depthsghoul");

		EntityRegistry.registerModEntity(EntityEvilpig.class, "evilpig", 26, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityEvilpig.class, evilPigSpawnRate, 1, 3, EnumCreatureType.creature, new BiomeGenBase[] {
			BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna,
			BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau,
			BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
			BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest});
		registerEntityEgg(EntityEvilpig.class, 15771042, 14377823, "evilpig");

		EntityRegistry.registerModEntity(EntityAbyssalZombie.class, "abyssalzombie", 27, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.SWAMP));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.END));
		registerEntityEgg(EntityAbyssalZombie.class, 0x36A880, 0x052824, "abyssalzombie");

		EntityRegistry.registerModEntity(EntityODBPrimed.class, "Primed ODB", 28, this, 80, 3, true);

		EntityRegistry.registerModEntity(EntityJzahar.class, "Jzahar", 29, this, 80, 3, true);
		registerEntityEgg(EntityJzahar.class, 0x133133, 0x342122, "Jzahar");

		EntityRegistry.registerModEntity(EntityAbygolem.class, "abygolem", 30, this, 80, 3, true);
		registerEntityEgg(EntityAbygolem.class, 0x8A00E6, 0x6100A1, "abygolem");

		EntityRegistry.registerModEntity(EntityDreadgolem.class, "dreadgolem", 31, this, 80, 3, true);
		registerEntityEgg(EntityDreadgolem.class, 0x1E60000, 0xCC0000, "dreadgolem");

		EntityRegistry.registerModEntity(EntityDreadguard.class, "dreadguard", 32, this, 80, 3, true);
		registerEntityEgg(EntityDreadguard.class, 0xE60000, 0xCC0000, "dreadguard");

		EntityRegistry.registerModEntity(EntityPSDLTracker.class, "PowerstoneTracker", 33, this, 64, 10, true);

		EntityRegistry.registerModEntity(EntityDragonMinion.class, "dragonminion", 34, this, 80, 3, true);
		registerEntityEgg(EntityDragonMinion.class, 0x433434, 0x344344, "dragonminion");

		EntityRegistry.registerModEntity(EntityDragonBoss.class, "dragonboss", 35, this, 80, 3, true);
		registerEntityEgg(EntityDragonBoss.class, 0x476767, 0x768833, "dragonboss");

		EntityRegistry.registerModEntity(EntityODBcPrimed.class, "Primed ODB Core", 36, this, 80, 3, true);

		EntityRegistry.registerModEntity(EntityShadowCreature.class, "shadowcreature", 37, this, 80, 3, true);
		registerEntityEgg(EntityShadowCreature.class, 0, 0xFFFFFF, "shadowcreature");

		EntityRegistry.registerModEntity(EntityShadowMonster.class, "shadowmonster", 38, this, 80, 3, true);
		registerEntityEgg(EntityShadowMonster.class, 0, 0xFFFFFF, "shadowmonster");

		EntityRegistry.registerModEntity(EntityDreadling.class, "dreadling", 39, this, 80, 3, true);
		registerEntityEgg(EntityDreadling.class, 0xE60000, 0xCC0000, "dreadling");

		EntityRegistry.registerModEntity(EntityDreadSpawn.class, "dreadspawn", 40, this, 80, 3, true);
		registerEntityEgg(EntityDreadSpawn.class, 0xE60000, 0xCC0000, "dreadspawn");

		EntityRegistry.registerModEntity(EntityDemonPig.class, "demonpig", 41, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDemonPig.class, 30, 1, 3, EnumCreatureType.monster, new BiomeGenBase[] {
			BiomeGenBase.hell});
		registerEntityEgg(EntityDemonPig.class, 15771042, 14377823, "demonpig");

		EntityRegistry.registerModEntity(EntitySkeletonGoliath.class, "gskeleton", 42, this, 80, 3, true);
		registerEntityEgg(EntitySkeletonGoliath.class, 0xD6D6C9, 0xC6C7AD, "gskeleton");

		EntityRegistry.registerModEntity(EntityChagarothSpawn.class, "chagarothspawn", 43, this, 80, 3, true);
		registerEntityEgg(EntityChagarothSpawn.class, 0xE60000, 0xCC0000, "chagarothspawn");

		EntityRegistry.registerModEntity(EntityChagarothFist.class, "chagarothfist", 44, this, 80, 3, true);
		registerEntityEgg(EntityChagarothFist.class, 0xE60000, 0xCC0000, "chagarothfist");

		EntityRegistry.registerModEntity(EntityChagaroth.class, "chagaroth", 45, this, 80, 3, true);
		registerEntityEgg(EntityChagaroth.class, 0xE60000, 0xCC0000, "chagaroth");

		EntityRegistry.registerModEntity(EntityShadowBeast.class, "shadowbeast", 46, this, 80, 3, true);
		registerEntityEgg(EntityShadowBeast.class, 0, 0xFFFFFF, "shadowbeast");

		EntityRegistry.registerModEntity(EntitySacthoth.class, "shadowboss", 47, this, 80, 3, true);
		registerEntityEgg(EntitySacthoth.class, 0, 0xFFFFFF, "shadowboss");

		EntityRegistry.registerModEntity(EntityAntiAbyssalZombie.class, "antiabyssalzombie", 48, this, 80, 3, true);
		registerEntityEgg(EntityAntiAbyssalZombie.class, 0xFFFFFF, 0xFFFFFF, "antiabyssalzombie");

		EntityRegistry.registerModEntity(EntityAntiBat.class, "antibat", 49, this, 80, 3, true);
		registerEntityEgg(EntityAntiBat.class, 0xFFFFFF, 0xFFFFFF, "antibat");

		EntityRegistry.registerModEntity(EntityAntiChicken.class, "antichicken", 50, this, 80, 3, true);
		registerEntityEgg(EntityAntiChicken.class, 0xFFFFFF, 0xFFFFFF, "antichicken");

		EntityRegistry.registerModEntity(EntityAntiCow.class, "anticow", 51, this, 80, 3, true);
		registerEntityEgg(EntityAntiCow.class, 0xFFFFFF, 0xFFFFFF, "anticow");

		EntityRegistry.registerModEntity(EntityAntiCreeper.class, "anticreeper", 52, this, 80, 3, true);
		registerEntityEgg(EntityAntiCreeper.class, 0xFFFFFF, 0xFFFFFF, "anticreeper");

		EntityRegistry.registerModEntity(EntityAntiGhoul.class, "antighoul", 53, this, 80, 3, true);
		registerEntityEgg(EntityAntiGhoul.class, 0xFFFFFF, 0xFFFFFF, "antighoul");

		EntityRegistry.registerModEntity(EntityAntiPig.class, "antipig", 54, this, 80, 3, true);
		registerEntityEgg(EntityAntiPig.class, 0xFFFFFF, 0xFFFFFF, "antipig");

		EntityRegistry.registerModEntity(EntityAntiPlayer.class, "antiplayer", 55, this, 80, 3, true);
		registerEntityEgg(EntityAntiPlayer.class, 0xFFFFFF, 0xFFFFFF, "antiplayer");

		EntityRegistry.registerModEntity(EntityAntiSkeleton.class, "antiskeleton", 56, this, 80, 3, true);
		registerEntityEgg(EntityAntiSkeleton.class, 0xFFFFFF, 0xFFFFFF, "antiskeleton");

		EntityRegistry.registerModEntity(EntityAntiSpider.class, "antispider", 57, this, 80, 3, true);
		registerEntityEgg(EntityAntiSpider.class, 0xFFFFFF, 0xFFFFFF, "antispider");

		EntityRegistry.registerModEntity(EntityAntiZombie.class, "antizombie", 58, this, 80, 3, true);
		registerEntityEgg(EntityAntiZombie.class, 0xFFFFFF, 0xFFFFFF, "antizombie");

		EntityRegistry.registerModEntity(EntityRemnant.class, "remnant", 59, this, 80, 3, true);
		registerEntityEgg(EntityRemnant.class, 0x133133, 0x342122, "remnant");

		EntityRegistry.registerModEntity(EntityOmotholGhoul.class, "omotholghoul", 60, this, 80, 3, true);
		registerEntityEgg(EntityOmotholGhoul.class, 0x133133, 0x342122, "omotholghoul");

		EntityRegistry.registerModEntity(EntityCoraliumArrow.class, "CoraliumArrow", 61, this, 64, 10, true);

		//		EntityRegistry.registerModEntity(EntityLesserShoggoth.class, "lessershoggoth", 62, this, 80, 3, true);
		//		registerEntityEgg(EntityLesserShoggoth.class, 0x133133, 0x342122, "lessershoggoth");
		//
		//		EntityRegistry.registerModEntity(EntityShadowTitan.class, "shadowtitan", 63, this, 80, 3, true);
		//		registerEntityEgg(EntityShadowTitan.class, 0, 0xFFFFFF, "shadowtitan");
		//
		//		EntityRegistry.registerModEntity(EntityOmotholWarden.class, "omotholwarden", 64, this, 80, 3, true);
		//		registerEntityEgg(EntityOmotholWarden.class, 0x133133, 0x342122, "omotholwarden");
		//
		//		EntityRegistry.registerModEntity(EntityGatekeeperMinion.class, "jzaharminion", 65, this, 80, 3, true);
		//		registerEntityEgg(EntityGatekeeperMinion.class, 0x133133, 0x342122, "jzaharminion");

		proxy.addArmor("Abyssalnite");
		proxy.addArmor("AbyssalniteC");
		proxy.addArmor("Dread");
		proxy.addArmor("Coralium");
		proxy.addArmor("CoraliumP");
		proxy.addArmor("Depths");
		proxy.addArmor("Dreadium");
		proxy.addArmor("DreadiumS");
		proxy.addArmor("Ethaxium");

		addOreDictionaryStuff();
		addChestGenHooks();
		addDungeonHooks();
		sendIMC();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

		ACLogger.info("Initializing AbyssalCraft.");
		//Achievements
		mineDS = new Achievement("achievement.mineDS", "mineDS", 0, 0, AbyssalCraft.Darkstone_cobble, AchievementList.openInventory).registerStat();
		mineAby = new Achievement("achievement.mineAby", "mineAby", 3, 0, AbyssalCraft.abychunk, AbyssalCraft.mineDS).registerStat();
		killghoul = new Achievement("achievement.killghoul", "killghoul", -3, 0, AbyssalCraft.Corbone, AbyssalCraft.mineDS).registerStat();
		enterabyss = new Achievement("achievement.enterabyss", "enterabyss", 0, 3, AbyssalCraft.abystone, AbyssalCraft.mineDS).setSpecial().registerStat();
		killdragon = new Achievement("achievement.killdragon", "killdragon", 3, 3, AbyssalCraft.Corflesh, AbyssalCraft.enterabyss).registerStat();
		summonAsorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 6, AbyssalCraft.Altar, AbyssalCraft.enterabyss).registerStat();
		killAsorah = new Achievement("achievement.killAsorah", "killAsorah", 3, 6, AbyssalCraft.EoA, AbyssalCraft.summonAsorah).setSpecial().registerStat();
		enterdreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 3, 9, AbyssalCraft.dreadstone, AbyssalCraft.killAsorah).setSpecial().registerStat();
		killdreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 6, 9, AbyssalCraft.Dreadshard, AbyssalCraft.enterdreadlands).registerStat();
		ghoulhead = new Achievement("achievement.ghoulhead", "ghoulhead", -6, 0, AbyssalCraft.DGhead, AbyssalCraft.killghoul).registerStat();
		killPete = new Achievement("achievement.killPete", "killPete", -3, -3, AbyssalCraft.Corbone, AbyssalCraft.killghoul).registerStat();
		killWilson = new Achievement("achievement.killWilson", "killWilson", -3, -6, AbyssalCraft.Corbone, AbyssalCraft.killPete).registerStat();
		killOrange = new Achievement("achievement.killOrange", "killOrange", -3, -9, AbyssalCraft.Corbone, AbyssalCraft.killWilson).registerStat();
		petehead = new Achievement("achievement.petehead", "petehead", -6, -3, AbyssalCraft.Phead, AbyssalCraft.ghoulhead).registerStat();
		wilsonhead = new Achievement("achievement.wilsonhead", "wilsonhead", -6, -6, AbyssalCraft.Whead, AbyssalCraft.petehead).registerStat();
		orangehead = new Achievement("achievement.orangehead", "orangehead", -6, -9, AbyssalCraft.Ohead, AbyssalCraft.wilsonhead).registerStat();
		mineCorgem = new Achievement("achievement.mineCorgem", "mineCorgem", 6, 0, AbyssalCraft.Coralium, AbyssalCraft.mineAby).registerStat();
		mineCor = new Achievement("achievement.mineCor", "mineCor", 9, 0, AbyssalCraft.Cchunk, AbyssalCraft.mineCorgem).registerStat();
		GK1 = new Achievement("achievement.GK1", "GK1", 0, -3, AbyssalCraft.portalPlacer, AbyssalCraft.mineDS).registerStat();
		findPSDL = new Achievement("achievement.findPSDL", "findPSDL", 3, -3, AbyssalCraft.PSDL, AbyssalCraft.GK1).registerStat();
		GK2 = new Achievement("achievement.GK2", "GK2", 0, -5, AbyssalCraft.portalPlacerDL, AbyssalCraft.GK1).registerStat();
		GK3 = new Achievement("achievement.GK3", "GK3", 0, -7, AbyssalCraft.portalPlacerJzh, AbyssalCraft.GK2).registerStat();
		Jzhstaff = new Achievement("achievement.Jzhstaff", "Jzhstaff", 0, -9, AbyssalCraft.Staff, AbyssalCraft.GK3).setSpecial().registerStat();
		summonChagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 3, 12, AbyssalCraft.dreadaltarbottom, AbyssalCraft.enterdreadlands).registerStat();
		killChagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 6, 12, AbyssalCraft.dreadKey, AbyssalCraft.summonChagaroth).setSpecial().registerStat();
		enterOmothol = new Achievement("achievement.enterOmothol", "enterOmothol", 6, 15, AbyssalCraft.omotholstone, AbyssalCraft.killChagaroth).setSpecial().registerStat();
		enterDarkRealm = new Achievement("achievement.darkRealm", "darkRealm", 3, 15, AbyssalCraft.Darkstone, (Achievement)null).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[]{mineDS, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah,
				enterdreadlands, killdreadguard, ghoulhead, killPete, killWilson, killOrange, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, Jzhstaff,
				summonChagaroth, killChagaroth, enterOmothol, enterDarkRealm}));

		proxy.init();
		FMLCommonHandler.instance().bus().register(instance);
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(StructureDreadlandsMineStart.class, "DreadMine");
		StructureDreadlandsMinePieces.registerStructurePieces();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		registerVanillaSalvage();
		AbyssalCrafting.addRecipes();
		proxy.registerRenderThings();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		ACLogger.info("Post-initializing AbyssalCraft");
		proxy.postInit();
		IntegrationHandler.init();
		ACLogger.info("AbyssalCraft loaded.");
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void handleIMC(FMLInterModComms.IMCEvent event){

		List<String> senders = new ArrayList<String>();
		for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
			if(imcMessage.key.equalsIgnoreCase("shoggothFood"))
				try {
					AbyssalCraftAPI.addShoggothFood((Class<? extends EntityLivingBase>)Class.forName(imcMessage.getStringValue()));
					ACLogger.info("Received Shoggoth Food addition %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (ClassNotFoundException e) {
					ACLogger.severe("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equalsIgnoreCase("registerTransmutatorFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler)Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.TRANSMUTATOR);
					ACLogger.info("Recieved Transmutator fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					ACLogger.severe("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					ACLogger.severe("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					ACLogger.severe("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equalsIgnoreCase("registerCrystallizerFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler)Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.CRYSTALLIZER);
					ACLogger.info("Recieved Crystallizer fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					ACLogger.severe("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					ACLogger.severe("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					ACLogger.severe("Could not find class %s", imcMessage.getStringValue());
				}
		if(!senders.isEmpty())
			ACLogger.info("Recieved messages from the following mods: %s", senders);
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
		configBiomeId12 = cfg.get("biomes", "Coralium Infested Ocean", 111, "A ocean biome infested with Coralium.", 0, 255).getInt();
		configBiomeId13 = cfg.get("biomes", "Omothol", 112, "Main biome in Omothol, the realm of J'zahar.", 0, 255).getInt();
		configBiomeId14 = cfg.get("biomes", "Dark Realm", 113, "Dark Realm biome, made out of Darkstone.", 0, 255).getInt();

		dark1 = cfg.get("biome_generation", "Darklands", true, "Set true for the Darklands biome to generate.").getBoolean();
		dark2 = cfg.get("biome_generation", "Darklands Forest", true, "Set true for the Darklands Forest biome to generate.").getBoolean();
		dark3 = cfg.get("biome_generation", "Darklands Plains", true, "Set true for the Darklands Plains biome to generate.").getBoolean();
		dark4 = cfg.get("biome_generation", "Darklands Highland", true, "Set true for the Darklands Highland biome to generate.").getBoolean();
		dark5 = cfg.get("biome_generation", "Darklands Mountain", true, "Set true for the Darklands Mountain biome to generate.").getBoolean();
		coralium1 = cfg.get("biome_generation", "Coralium Infested Swamp", true, "Set true for the Coralium Infested Swamp to generate.").getBoolean();
		coralium2 = cfg.get("biome_generation", "Coralium Infested Ocean", true, "Set true for the Coralium Infested Ocean to generate.").getBoolean();

		darkspawn1 = cfg.get("biome_spawning", "Darklands", true, "If true, you can spawn in the Darklands biome.").getBoolean();
		darkspawn2 = cfg.get("biome_spawning", "Darklands Forest", true, "If true, you can spawn in the Darklands Forest biome.").getBoolean();
		darkspawn3 = cfg.get("biome_spawning", "Darklands Plains", true, "If true, you can spawn in the Darklands Plains biome.").getBoolean();
		darkspawn4 = cfg.get("biome_spawning", "Darklands Highland", true, "If true, you can spawn in the Darklands Highland biome.").getBoolean();
		darkspawn5 = cfg.get("biome_spawning", "Darklands Mountain", true, "If true, you can spawn in the Darklands Mountain biome.").getBoolean();
		coraliumspawn1 = cfg.get("biome_spawning", "Coralium Infested Swamp", true, "If true, you can spawn in the Coralium Infested Swamp biome.").getBoolean();
		coraliumspawn2 = cfg.get("biome_spawning", "Coralium Infested Ocean", true, "If true, you can spawn in the Coralium Infested Ocean biome.").getBoolean();

		shouldSpread = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Coralium transmutation", true, "Set true for the Liquid Coralium to convert other liquids into itself in the overworld.").getBoolean();
		shouldInfect = cfg.get(Configuration.CATEGORY_GENERAL, "Coralium Plague spreading", false, "Set true to allow the Coralium Plague to spread outside The Abyssal Wasteland.").getBoolean();
		breakLogic = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Coralium Physics", false, "Set true to allow the Liquid Coralium to break the laws of physics in terms of movement").getBoolean();
		destroyOcean = cfg.get(Configuration.CATEGORY_GENERAL, "Oceanic Coralium Pollution", false, "Set true to allow the Liquid Coralium to spread across oceans. WARNING: The game can crash from this.").getBoolean();
		demonPigFire = cfg.get(Configuration.CATEGORY_GENERAL, "Demon Pig burning", true, "Set to false to prevent Demon Pigs from burning in the overworld.").getBoolean();
		evilPigSpawnRate = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Pig spawn rate", 20, "Spawn rate for the Evil Pig, keep under 35 to avoid complete annihilation.").getInt();
		updateC = cfg.get(Configuration.CATEGORY_GENERAL, "UpdateChecker", true, "Set to false to disable the UpdateChecker.").getBoolean();
		darkness = cfg.get(Configuration.CATEGORY_GENERAL, "Darkness", true, "Set to false to disable the random blindness within Darklands biomes").getBoolean();

		darkWeight1 = cfg.get("biome_weight", "Darklands", 10, "Biome weight for the Darklands biome, controls the chance of it generating").getInt();
		darkWeight2 = cfg.get("biome_weight", "Darklands Forest", 10, "Biome weight for the Darklands Forest biome, controls the chance of it generating").getInt();
		darkWeight3 = cfg.get("biome_weight", "Darklands Plains", 10, "Biome weight for the Darklands Plains biome, controls the chance of it generating").getInt();
		darkWeight4 = cfg.get("biome_weight", "Darklands Highland", 10, "Biome weight for the Darklands Highland biome, controls the chance of it generating").getInt();
		darkWeight5 = cfg.get("biome_weight", "Darklands Mountain", 10, "Biome weight for the Darklands Mountain biome, controls the chance of it generating").getInt();
		coraliumWeight = cfg.get("biome_weight", "Coralium Infested Swamp", 10, "Biome weight for the Coralium Infested Swamp biome, controls the chance of it generating").getInt();

		AbyssalCraftAPI.enchId1 = cfg.get("enchantments", "Coralium Infusion", 230, "The Coralium enchantment.", 0, 255).getInt();
		AbyssalCraftAPI.enchId2 = cfg.get("enchantments", "Dread Infusion", 231, "The Dread enchantment.", 0, 255).getInt();
		AbyssalCraftAPI.enchId3 = cfg.get("enchantments", "Light Pierce", 232, "The Light Pierce enchantment.", 0, 255).getInt();
		AbyssalCraftAPI.enchId4 = cfg.get("enchantments", "Iron Wall", 233, "The Iron Wall enchantment.", 0, 255).getInt();

		AbyssalCraftAPI.potionId1 = cfg.get("potions", "Coralium Plague", 100, "The Coralium Plague potion effect.", 0, 150).getInt();
		AbyssalCraftAPI.potionId2 = cfg.get("potions", "Dread Plague", 101, "The Dread Plague potion effect.", 0, 150).getInt();
		AbyssalCraftAPI.potionId3 = cfg.get("potions", "Antimatter", 102, "The Antimatter potion effect.", 0, 150).getInt();

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
		OreDictionary.registerOre("blockCoralium", corblock);
		OreDictionary.registerOre("blockDreadium", dreadiumblock);
		OreDictionary.registerOre("ingotCoraliumBrick", cbrick);
		OreDictionary.registerOre("ingotDreadium", dreadiumingot);
		OreDictionary.registerOre("materialSulfur", sulfur);
		OreDictionary.registerOre("materialSaltpeter", nitre);
		OreDictionary.registerOre("materialMethane", methane);
		OreDictionary.registerOre("oreSaltpeter", nitreOre);
		OreDictionary.registerOre("crystalIron", crystalIron);
		OreDictionary.registerOre("crystalGold", crystalGold);
		OreDictionary.registerOre("crystalSulfur", crystalSulfur);
		OreDictionary.registerOre("crystalCarbon", crystalCarbon);
		OreDictionary.registerOre("crystalOxygen", crystalOxygen);
		OreDictionary.registerOre("crystalHydrogen", crystalHydrogen);
		OreDictionary.registerOre("crystalNitrogen", crystalNitrogen);
		OreDictionary.registerOre("crystalPhosphorus", crystalPhosphorus);
		OreDictionary.registerOre("crystalPotassium", crystalPotassium);
		OreDictionary.registerOre("crystalNitrate", crystalNitrate);
		OreDictionary.registerOre("crystalMethane", crystalMethane);
		OreDictionary.registerOre("crystalRedstone", crystalRedstone);
		OreDictionary.registerOre("crystalAbyssalnite", crystalAbyssalnite);
		OreDictionary.registerOre("crystalCoralium", crystalCoralium);
		OreDictionary.registerOre("crystalDreadium", crystalDreadium);
		OreDictionary.registerOre("crystalBlaze", crystalBlaze);
		OreDictionary.registerOre("foodFriedEgg", friedegg);
		OreDictionary.registerOre("oreIron", AbyIroOre);
		OreDictionary.registerOre("oreGold", AbyGolOre);
		OreDictionary.registerOre("oreDiamond", AbyDiaOre);
		OreDictionary.registerOre("oreSaltpeter", AbyNitOre);
		OreDictionary.registerOre("oreTin", AbyTinOre);
		OreDictionary.registerOre("oreCopper", AbyCopOre);
		OreDictionary.registerOre("ingotTin", tinIngot);
		OreDictionary.registerOre("ingotCopper", copperIngot);
		OreDictionary.registerOre("crystalTin", crystalTin);
		OreDictionary.registerOre("crystalCopper", crystalCopper);
		OreDictionary.registerOre("crystalSilicon", crystalSilicon);
		OreDictionary.registerOre("crystalMagnesium", crystalMagnesium);
		OreDictionary.registerOre("crystalAluminium", crystalAluminium);
		OreDictionary.registerOre("crystalSilica", crystalSilica);
		OreDictionary.registerOre("crystalAlumina", crystalAlumina);
		OreDictionary.registerOre("crystalMagnesia", crystalMagnesia);
		OreDictionary.registerOre("crystalZinc", crystalZinc);
		OreDictionary.registerOre("orePearlescentCoralium", AbyPCorOre);
		OreDictionary.registerOre("oreLiquifiedCoralium", AbyLCorOre);
		OreDictionary.registerOre("ingotEthaxiumBrick", ethaxium_brick);
		OreDictionary.registerOre("ingotEthaxium", ethaxiumIngot);
		OreDictionary.registerOre("blockEthaxium", ethaxiumblock);
	}

	private void addChestGenHooks(){

		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.axe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.pickaxe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.shovel), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.sword), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.DLTLog), 1, 3, 10));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.CobbleU), 1, 2, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.OC), 1,1,1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.abyingot), 1, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.abyingot), 1, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.abyingot), 1, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.abyingot), 1, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.abyingot), 1, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.Cingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.Cingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.Cingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.Cingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.Cingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.dreadiumingot), 1, 2, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.dreadiumingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.dreadiumingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.dreadiumingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.dreadiumingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.crystalZinc), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.crystalZinc), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.crystalZinc), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.crystalZinc), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.crystalZinc), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.pickaxeA), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.Corpickaxe), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.helmet), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.plate), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.legs), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.boots), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.CobbleU), 1, 2, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.IronU), 1, 2, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.GoldU), 1, 2, 4));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.DiamondU), 1, 2, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.MRE), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(AbyssalCraft.Coralium), 1, 5, 8));
	}

	private void addDungeonHooks(){
		DungeonHooks.addDungeonMob("abyssalzombie", 150);
		DungeonHooks.addDungeonMob("depthsghoul", 100);
		DungeonHooks.addDungeonMob("shadowcreature", 120);
		DungeonHooks.addDungeonMob("shadowmonster", 100);
		DungeonHooks.addDungeonMob("shadowbeast", 30);
		DungeonHooks.addDungeonMob("antiabyssalzombie", 50);
		DungeonHooks.addDungeonMob("antighoul", 50);
		DungeonHooks.addDungeonMob("antiskeleton", 50);
		DungeonHooks.addDungeonMob("antispider", 50);
		DungeonHooks.addDungeonMob("antizombie", 50);
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abyslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abyslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Darkstoneslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Darkstoneslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Darkcobbleslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Darkcobbleslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Darkbrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Darkbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DLTslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DLTslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abydreadbrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abydreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadbrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.cstonebrickslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.cstonebrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.ethaxiumslab1));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.ethaxiumslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.portal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadportal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.omotholportal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Coraliumfire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadfire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.omotholfire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.transmutator_on));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.crystallizer_on));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.engraver));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.ODB));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.ODBcore));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DSBfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DSCwall));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abyfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DLTfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abydreadbrickfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadbrickfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.cstonebrickfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.ethaxiumfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DrTfence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abystairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DBstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DCstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DLTstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.abydreadbrickstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadbrickstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.cstonebrickstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.ethaxiumstairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Abybutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DSbutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DLTbutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.cstonebutton));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Abypplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DSpplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DLTpplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.cstonepplate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Altar));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadaltartop));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadaltarbottom));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.PSDL));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DGhead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Phead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Whead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.Ohead));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.DLTSapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(AbyssalCraft.dreadsapling));
	}

	private void registerVanillaSalvage(){

		SalvageHandler.INSTANCE.addBootsSalvage(Items.leather_boots, Items.leather);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.leather_helmet, Items.leather);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.leather_chestplate, Items.leather);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.leather_leggings, Items.leather);

		SalvageHandler.INSTANCE.addBootsSalvage(Items.iron_boots, Items.iron_ingot);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.iron_helmet, Items.iron_ingot);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.iron_chestplate, Items.iron_ingot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.iron_leggings, Items.iron_ingot);

		SalvageHandler.INSTANCE.addBootsSalvage(Items.golden_boots, Items.gold_ingot);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.golden_helmet, Items.gold_ingot);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.golden_chestplate, Items.gold_ingot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.golden_leggings, Items.gold_ingot);

		SalvageHandler.INSTANCE.addBootsSalvage(Items.diamond_boots, Items.diamond);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.diamond_helmet, Items.diamond);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.diamond_chestplate, Items.diamond);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.diamond_leggings, Items.diamond);
	}

	private static int getUniqueEntityId() {
		do
			startEntityId++;
		while (EntityList.getStringFromID(startEntityId) != null);

		return startEntityId;
	}

	@SuppressWarnings("unchecked")
	private static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor, String name) {
		int id = getUniqueEntityId();
		stringtoIDMapping.put(name, id);
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}

	private static void registerDimension(int id, Class<? extends WorldProvider> provider, boolean keepLoaded){
		DimensionManager.registerProviderType(id, provider, keepLoaded);
		DimensionManager.registerDimension(id, id);
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if(!dev && updateC)
			new UpdateCheck().start();
	}

	/**
	 * Update checker for AbyssalCraft,
	 * based on code from AtomicStryker's UpdateChecker mod (proxy part)
	 * @author shinoow
	 */
	private class UpdateCheck extends Thread {

		private String webVersion;

		@Override
		public void run() {
			try {
				Thread.sleep(10000L);

				if(isUpdateAvailable()) {
					if(!hasPinged && version.length() > 7){
						hasPinged = true;
						updateProxy.announce("[\u00A79AbyssalCraft\u00A7r] Using a development version, not checking for newer versions. (\u00A7b"+ version + "\u00A7r)");
					}
					if(!hasPinged){
						hasPinged = true;
						updateProxy.announce("[\u00A79AbyssalCraft\u00A7r] Version \u00A7b"+webVersion+"\u00A7r of AbyssalCraft is available. Check http://adf.ly/FQarm for more info. (Your Version: \u00A7b"+AbyssalCraft.version+"\u00A7r)");
					}
				} else if(!hasPinged){
					hasPinged = true;
					updateProxy.announce("[\u00A79AbyssalCraft\u00A7r] Running the latest version of AbyssalCraft, \u00A7b"+AbyssalCraft.version+"\u00A7r.");
				}
			} catch(Exception e) {
				if(!hasPinged){
					hasPinged = true;
					System.err.println("UpdateChecker encountered an Exception, see following stacktrace:");
					e.printStackTrace();
					updateProxy.announce("[\u00A79AbyssalCraft\u00A7r] No internet connection found, unable to check mod version.");
				}
			}
		}

		public boolean isUpdateAvailable() throws IOException, MalformedURLException {
			BufferedReader versionFile = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/version.txt").openStream()));
			String curVersion = versionFile.readLine();
			webVersion = curVersion;
			versionFile.close();

			if (!curVersion.equals(AbyssalCraft.version))
				return true;

			return false;
		}
	}
}