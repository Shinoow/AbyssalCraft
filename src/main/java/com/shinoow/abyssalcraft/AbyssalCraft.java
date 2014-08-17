/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.stats.*;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.*;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.*;
import com.shinoow.abyssalcraft.common.blocks.itemblock.*;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.creativetabs.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.items.*;
import com.shinoow.abyssalcraft.common.items.armor.*;
import com.shinoow.abyssalcraft.common.lib.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.potion.*;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.*;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.*;
import com.shinoow.abyssalcraft.common.util.*;
import com.shinoow.abyssalcraft.common.world.*;
import com.shinoow.abyssalcraft.common.world.biome.*;
import com.shinoow.abyssalcraft.core.api.item.ItemUpgradeKit;
import com.shinoow.abyssalcraft.update.IUpdateProxy;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.*;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version, dependencies = "required-after:Forge@[10.12.2.1147,)", useMetadata = true)
public class AbyssalCraft {

	public static final String version = "1.7.0";
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

	public static Fluid CFluid, antifluid;

	public static Achievement mineDS, mineAby, killghoul, enterabyss, killdragon, summonAsorah,
	killAsorah, enterdreadlands, killdreadguard, ghoulhead, killPete, killWilson, killOrange,
	petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, Jzhstaff,
	secret1, summonChagaroth, killChagaroth;

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
	chagarothfistspawner, DrTfence, nitreOre;

	//Overworld biomes
	public static BiomeGenBase Darklands, DarklandsForest, DarklandsPlains, DarklandsHills,
	DarklandsMountains, corswamp, corocean;
	//Abyssal Wastelands biome
	public static BiomeGenBase Wastelands;
	//Dreadlands biomes
	public static BiomeGenBase Dreadlands, AbyDreadlands, ForestDreadlands, MountainDreadlands;

	//"secret" dev stuff
	public static Item devsword;
	//misc items
	public static Item OC, Staff, portalPlacer, Cbucket, PSDLfinder, EoA, portalPlacerDL,
	antibucket, cbrick, cudgel, carbonCluster, denseCarbonCluster, methane, nitre, sulfur, portalPlacerJzh;
	//crystals (real elements)
	public static Item crystalIron, crystalGold, crystalSulfur, crystalCarbon, crystalOxygen,
	crystalHydrogen, crystalNitrogen, crystalPhosphorus, crystalPotassium;
	//crystals (ions/molecules)
	public static Item crystalNitrate, crystalMethane;
	//crystals (Minecraft/AbyssalCraft elements)
	public static Item crystalRedstone, crystalAbyssalnite, crystalCoralium, crystalDreadium,
	crystalBlaze;
	//shadow items
	public static Item shadowfragment, shadowshard, shadowgem, oblivionshard;
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
	dreadiumpickaxe, dreadiumaxe, dreadiumshovel, dreadiumsword, dreadiumhoe, dreadhilt, dreadkatana;
	//armor
	public static Item boots, helmet, plate, legs, bootsC, helmetC, plateC, legsC, bootsD,
	helmetD, plateD, legsD, Corboots, Corhelmet, Corplate, Corlegs, CorbootsP, CorhelmetP,
	CorplateP, CorlegsP, Depthsboots, Depthshelmet, Depthsplate, Depthslegs, dreadiumboots,
	dreadiumhelmet, dreadiumplate, dreadiumlegs, dreadiumSboots, dreadiumShelmet, dreadiumSplate,
	dreadiumSlegs;
	//upgrade kits
	public static Item CobbleU, IronU, GoldU, DiamondU, AbyssalniteU, CoraliumU, DreadiumU;
	//foodstuffs
	public static Item MRE, ironp, chickenp, porkp, beefp, fishp, dirtyplate, friedegg, eggp,
	cloth;

	public static Potion Cplague, Dplague;

	public static CreativeTabs tabBlock = new TabACBlocks(CreativeTabs.getNextID(), "acblocks");
	public static CreativeTabs tabItems = new TabACItems(CreativeTabs.getNextID(), "acitems");
	public static CreativeTabs tabTools = new TabACTools(CreativeTabs.getNextID(), "actools");
	public static CreativeTabs tabCombat = new TabACCombat(CreativeTabs.getNextID(), "acctools");
	public static CreativeTabs tabFood = new TabACFood(CreativeTabs.getNextID(), "acfood");
	public static CreativeTabs tabDecoration = new TabACDecoration(CreativeTabs.getNextID(), "acdblocks");
	public static CreativeTabs tabCrystals = new TabACCrystals(CreativeTabs.getNextID(), "accrystals");

	//Dimension Ids
	public static int configDimId1, configDimId2;
	//Will be used in a couple of months
	//public static int configDimId3;

	//Biome Ids
	public static int configBiomeId1, configBiomeId2, configBiomeId3, configBiomeId4,
	configBiomeId5, configBiomeId6, configBiomeId7, configBiomeId8, configBiomeId9,
	configBiomeId10, configBiomeId11, configBiomeId12;

	static int startEntityId = 300;

	public static final int crystallizerGuiID = 30;
	public static final int transmutatorGuiID = 31;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		LogHelper.info("Pre-initializing AbyssalCraft.");
		metadata = event.getModMetadata();
		Potion[] potionTypes = null;

		for (Field f : Potion.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

					potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Exception e) {
				System.err.println("Whoops, something screwed up here, please report this to shinoow:");
				System.err.println(e);
			}
		}

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftEventHooks());
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		instance = this;
		proxy.preInit();

		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();

			configDimId1 = cfg.get("Dimensions", "The Abyssal Wasteland", 50).getInt();
			configDimId2 = cfg.get("Dimensions", "The Dreadlands", 51).getInt();

			configBiomeId1 = cfg.get("Biomes", "Darklands", 100).getInt();
			configBiomeId2 = cfg.get("Biomes", "Abyssal Wasteland", 101).getInt();
			configBiomeId3 = cfg.get("Biomes", "Dreadlands", 102).getInt();
			configBiomeId4 = cfg.get("Biomes", "Purified Dreadlands", 103).getInt();
			configBiomeId5 = cfg.get("Biomes", "Dreadlands Forest", 104).getInt();
			configBiomeId6 = cfg.get("Biomes", "Dreadlands Mountains", 105).getInt();
			configBiomeId7 = cfg.get("Biomes", "Darklands Forest", 106).getInt();
			configBiomeId8 = cfg.get("Biomes", "Darklands Plains", 107).getInt();
			configBiomeId9 = cfg.get("Biomes", "Darklands Hightland", 108).getInt();
			configBiomeId10 = cfg.get("Biomes", "Darklands Mountains", 109).getInt();
			configBiomeId11 = cfg.get("Biomes", "Coralium Infested Swamp", 110).getInt();
			configBiomeId12 = cfg.get("Biomes", "Coralium Infested Ocean", 111).getInt();

		} catch (Exception e) {
			LogHelper.severe("AbyssalCraft has problems loading Configs, DAFUQ DID YOU DO!?!?!?!?!?");
			e.printStackTrace();
		} finally {
			cfg.save();
		}

		EnumHelper.addArmorMaterial("Abyssalnite", 35, new int[]{3, 8, 6, 3}, 10);
		EnumHelper.addArmorMaterial("AbyssalniteC", 36, new int[]{3, 8, 6, 3}, 30);
		EnumHelper.addArmorMaterial("Dread", 36, new int[]{3, 8, 6, 3}, 12);
		EnumHelper.addArmorMaterial("Coralium", 37, new int[]{3, 8, 6, 3}, 12);
		EnumHelper.addArmorMaterial("CoraliumP", 55, new int[]{4, 9, 7, 4}, 12);
		EnumHelper.addArmorMaterial("Depths", 33, new int[]{3, 8, 6, 3}, 25);
		EnumHelper.addArmorMaterial("Dreadium", 40, new int[]{3, 8, 6, 3}, 20);
		EnumHelper.addArmorMaterial("DreadiumS", 45, new int[]{3, 8, 6, 3}, 20);

		EnumHelper.addToolMaterial("DARKSTONE", 1, 180, 5.0F, 1, 15);
		EnumHelper.addToolMaterial("ABYSSALNITE", 4, 1261, 13.0F, 4, 10);
		EnumHelper.addToolMaterial("CORALIUM", 5, 2000, 14.0F, 5, 12);
		EnumHelper.addToolMaterial("DREADIUM", 6, 3000, 15.0F, 6, 15);
		EnumHelper.addToolMaterial("ABYSSALNITE_C", 8, 8000, 20.0F, 8, 30);

		CFluid = new Fluid("Liquid Coralium").setDensity(3000).setViscosity(1000).setTemperature(350);
		antifluid = new Fluid("Liquid Antimatter").setDensity(4000).setViscosity(1500).setTemperature(100);

		Fluid.registerLegacyName(CFluid.getName(), CFluid.getName());
		FluidRegistry.registerFluid(CFluid);
		Fluid.registerLegacyName(antifluid.getName(), antifluid.getName());
		FluidRegistry.registerFluid(antifluid);

		//Blocks
		Darkstone = new BlockDarkstone().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DS").setBlockTextureName(modid + ":" + "DS");
		Darkstone_brick = new BlockACBasic(Material.rock, "pickaxe", 0, 1.65F, 12.0F, Block.soundTypeStone).setBlockName("DSB").setBlockTextureName(modid + ":" + "DSB");
		Darkstone_cobble = new BlockACBasic(Material.rock, "pickaxe", 0, 2.2F, 12.0F, Block.soundTypeStone).setBlockName("DSC").setBlockTextureName(modid + ":" + "DSC");
		DSGlow = new BlockDSGlow().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(55F).setResistance(3000F).setLightLevel(1.0F).setBlockName("DSGlow");
		Darkbrickslab1 = new BlockDarkbrickslab(false).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs1").setBlockTextureName(modid + ":" + "DSB");
		Darkbrickslab2 = new BlockDarkbrickslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs2").setBlockTextureName(modid + ":" + "DSB");
		Darkcobbleslab1 = new BlockDarkcobbleslab(false).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F) .setResistance(12.0F).setBlockName("DSCs1").setBlockTextureName(modid + ":" + "DSC");
		Darkcobbleslab2 = new BlockDarkcobbleslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.65F) .setResistance(12.0F).setBlockName("DSCs2").setBlockTextureName(modid + ":" + "DSC");
		Darkgrass = new BlockDarklandsgrass().setStepSound(Block.soundTypeGrass).setCreativeTab(AbyssalCraft.tabBlock).setHardness(0.4F).setBlockName("DLG");
		DBstairs = new BlockDarkstonebrickstairs(0).setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs").setBlockTextureName(modid + ":" + "DSB");
		DCstairs = new BlockDarkstonecobblestairs(0).setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSCs").setBlockTextureName(modid + ":" + "DSC");
		DLTLeaves = new BlockDLTLeaves().setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setBlockName("DLTL").setBlockTextureName(modid + ":" + "DLTL");
		DLTLog = new BlockDLTLog().setStepSound(Block.soundTypeWood).setHardness(2.0F).setResistance(1.0F).setBlockName("DLTT");
		DLTSapling = new BlockDLTSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DLTS").setBlockTextureName(modid + ":" + "DLTS");
		abystone = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone).setBlockName("AS").setBlockTextureName(modid + ":" + "AS");
		abybrick = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone).setBlockName("ASB").setBlockTextureName(modid + ":" + "ASB");
		abyslab1 = new BlockAbystoneslab(false).setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs1").setBlockTextureName(modid + ":" + "ASB");
		abyslab2 = new BlockAbystoneslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs2").setBlockTextureName(modid + ":" + "ASB");
		abystairs = new BlockAbyssalstonestairs(0).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("ASBs").setBlockTextureName(modid + ":" + "ASB");
		Coraliumore = new BlockCoraliumOre().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("CO").setBlockTextureName(modid + ":" + "CO");
		abyore = new BlockACBasic(Material.rock, "pickaxe", 2, 3.0F, 6.0F, Block.soundTypeStone).setBlockName("AO").setBlockTextureName(modid + ":" + "AO");
		abyfence = new BlockACFence("ASBf", Material.rock, "pickaxe", 2).setHardness(1.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASBf").setBlockTextureName(modid + ":" + "ASBf");
		DSCwall = new BlockDarkstonecobblewall(Darkstone_cobble).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSCw").setBlockTextureName(modid + ":" + "DSC");
		Crate = new BlockCrate().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("Crate").setBlockTextureName(modid + ":" + "Crate");
		ODB = new BlockODB().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setBlockName("ODB").setBlockTextureName(modid + ":" + "ODBsides");
		abyblock = new IngotBlock(2).setBlockName("BOA").setBlockTextureName(modid + ":" + "BOA");
		CoraliumInfusedStone = new BlockCoraliumInfusedStone().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("CIS").setBlockTextureName(modid + ":" + "CIS");
		ODBcore = new BlockODBcore().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setBlockName("ODBC");
		portal = new BlockTeleporter().setBlockName("AG").setBlockTextureName(modid + ":" + "AG");
		Darkstoneslab1 = new BlockDarkstoneslab(false).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs1");
		Darkstoneslab2 = new BlockDarkstoneslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs2");
		Coraliumfire = new BlockCoraliumfire().setLightLevel(1.0F).setBlockName("Cfire");
		DSbutton = new BlockDarkstonebutton().setHardness(0.6F).setResistance(12.0F).setBlockName("DSbb").setBlockTextureName(modid + ":" + "DS");
		DSpplate = new BlockACPressureplate("DS", Material.rock, BlockACPressureplate.Sensitivity.mobs, "pickaxe", 0).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSpp").setBlockTextureName(modid + ":" + "DS");
		DLTplank = new BlockACBasic(Material.wood, "axe", 0, 2.0F, 5.0F, Block.soundTypeWood).setBlockName("DLTplank").setBlockTextureName(modid + ":" + "DLTplank");
		DLTbutton = new BlockDLTbutton().setHardness(0.5F).setBlockName("DLTplankb").setBlockTextureName(modid + ":" + "DLTplank");
		DLTpplate = new BlockACPressureplate("DLTplank", Material.wood, BlockACPressureplate.Sensitivity.everything, "axe", 0).setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("DLTpp").setBlockTextureName(modid + ":" + "DLTplank");
		DLTstairs = new BlockDLTstairs(0).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks").setBlockTextureName(modid + ":" + "DLTplank");
		DLTslab1 = new BlockDLTslab(false).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks1").setBlockTextureName(modid + ":" + "DLTplank");
		DLTslab2 = new BlockDLTslabdouble(true).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks2").setBlockTextureName(modid + ":" + "DLTplank");
		corblock = new IngotBlock(5).setBlockName("BOC").setBlockTextureName(modid + ":" + "BOC");
		PSDL = new BlockPSDL().setHardness(50.0F).setResistance(3000F).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("PSDL").setBlockTextureName(modid + ":" + "PSDL");
		AbyCorOre = new BlockACBasic(Material.rock, "pickaxe", 4, 10.0F, 12.0F, Block.soundTypeStone).setBlockName("ACO").setBlockTextureName(modid + ":" + "ACO");
		Altar = new BlockAltar().setStepSound(Block.soundTypeStone).setHardness(4.0F).setResistance(300.0F).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Altar").setBlockTextureName(modid + ":" + "Altar");
		Abybutton = new BlockAbybutton().setHardness(0.8F).setResistance(12.0F).setBlockName("ASbb").setBlockTextureName(modid + ":" + "AS");
		Abypplate = new BlockACPressureplate("AS", Material.rock, BlockACPressureplate.Sensitivity.mobs, "pickaxe", 2).setHardness(0.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASpp").setBlockTextureName(modid + ":" + "AS");
		DSBfence = new BlockACFence("DSB", Material.rock, "pickaxe", 0).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSBf");
		DLTfence = new BlockACFence("DLTplank", Material.wood, "axe", 0).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTf");
		dreadore = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("DrSO").setBlockTextureName(modid + ":" + "DrSO");
		abydreadore = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("AbyDrSO").setBlockTextureName(modid + ":" + "AbyDrSO");
		dreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("DrSB").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("AbyDrSB").setBlockTextureName(modid + ":" + "AbyDrSB");
		dreadlog = new BlockDreadlog().setHardness(2.0F).setResistance(12.0F).setStepSound(Block.soundTypeWood).setBlockName("DrT");
		dreadleaves = new BlockDreadleaves(false).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setBlockName("DrTL");
		dreadsapling = new BlockDreadsapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DrTS").setBlockTextureName(modid + ":" + "DrTS");
		dreadplanks = new BlockACBasic(Material.wood, "axe", 0, 2.0F, 5.0F, Block.soundTypeWood).setBlockName("DrTplank").setBlockTextureName(modid + ":" + "DrTplank");
		dreadportal = new BlockTeleporterDL().setBlockName("DG").setBlockTextureName(modid + ":" + "DG");
		dreadfire = new BlockDreadFire().setLightLevel(1.0F).setBlockName("Dfire");
		DGhead = new BlockDGhead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("DGhead").setBlockTextureName(modid + ":" + "DGhead");
		Cwater = new BlockCLiquid().setResistance(100.0F).setLightLevel(1.0F).setBlockName("Cwater");
		dreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("DrS").setBlockTextureName(modid + ":" + "DrS");
		abydreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("AbyDrS").setBlockTextureName(modid + ":" + "AbyDrS");
		dreadgrass = new BlockDreadgrass().setHardness(0.4F).setStepSound(Block.soundTypeGrass).setBlockName("DrG");
		Phead = new BlockPhead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Phead").setBlockTextureName(modid + ":" + "Phead");
		Whead = new BlockWhead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Whead").setBlockTextureName(modid + ":" + "Whead");
		Ohead = new BlockOhead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Ohead").setBlockTextureName(modid + ":" + "Ohead");
		dreadbrickstairs = new BlockDreadbrickstairs(0).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs").setBlockTextureName(modid + ":" + "DrSB");
		dreadbrickfence = new BlockACFence("DrSB", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBf");
		dreadbrickslab1 = new BlockDreadbrickslab(false).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs1").setBlockTextureName(modid + ":" + "DrSB");
		dreadbrickslab2 = new BlockDreadbrickslabdouble(true).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs2").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrickstairs = new BlockAbyDreadbrickstairs(0).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrickfence = new BlockACFence("AbyDrSB", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBf");
		abydreadbrickslab1 = new BlockAbyDreadbrickslab(false).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs1").setBlockTextureName(modid + ":" + "AbyDrSB");
		abydreadbrickslab2 = new BlockAbyDreadbrickslabdouble(true).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs2").setBlockTextureName(modid + ":" + "AbyDrSB");
		anticwater = new BlockAntiliquid().setResistance(100.0F).setLightLevel(0.5F).setBlockName("antiliquid");
		cstone = new BlockCoraliumstone().setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstone").setBlockTextureName(modid + ":" + "cstone");
		cstonebrick = new BlockACBasic(Material.rock, "pickaxe", 0, 1.5F, 10.0F, Block.soundTypeStone).setBlockName("cstonebrick").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickfence = new BlockACFence("cstonebrick", Material.rock, "pickaxe", 0).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebrickf");
		cstonebrickslab1 = new BlockCoraliumstonebrickslab(false).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks1").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickslab2 = new BlockCoraliumstonebrickslabdouble(true).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks2").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickstairs = new BlockCoraliumstonebrickstairs(0).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebutton = new BlockCoraliumstonebutton().setHardness(0.6F).setResistance(12.0F).setBlockName("cstonebutton");
		cstonepplate = new BlockACPressureplate("cstone", Material.rock, BlockACPressureplate.Sensitivity.mobs, "pickaxe", 0).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonepplate");
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
		DrTfence = new BlockACFence("DrTplank", Material.wood, "axe", 0).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DrTf");
		nitreOre = new BlockNitreOre().setBlockName("NO").setBlockTextureName(modid + ":" + "NO");

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

		//"secret" dev stuff
		devsword = new AbyssalCraftTool().setUnlocalizedName("Sword").setTextureName(modid + ":" + "Sword");

		//Misc items
		OC = new ItemOC(255, 1F, false).setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("OC").setTextureName(modid + ":" + "OC");
		Staff = new ItemStaff().setCreativeTab(AbyssalCraft.tabTools).setFull3D().setUnlocalizedName("SOTG").setTextureName(modid + ":" + "SOTG");
		portalPlacer = new ItemPortalPlacer().setUnlocalizedName("GK").setTextureName(modid + ":" + "GK");
		Cbucket = new ItemCBucket(Cwater).setCreativeTab(AbyssalCraft.tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Cbucket").setTextureName(modid + ":" + "Cbucket");
		PSDLfinder = new ItemTrackerPSDL().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("PSDLf").setTextureName(modid + ":" + "PSDLf");
		EoA = new ItemEoA().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("EoA").setTextureName(modid + ":" + "EoA");
		portalPlacerDL = new ItemPortalPlacerDL().setUnlocalizedName("GKD").setTextureName(modid + ":" + "GKD");
		antibucket = new ItemAntiBucket(anticwater).setCreativeTab(AbyssalCraft.tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Antibucket").setTextureName(modid + ":" + "Antibucket");
		cbrick = new ItemACBasic("cbrick").setCreativeTab(AbyssalCraft.tabItems);
		cudgel = new ItemCudgel().setCreativeTab(AbyssalCraft.tabCombat).setFull3D().setUnlocalizedName("cudgel").setTextureName(modid + ":" + "cudgel");
		carbonCluster = new ItemACBasic("CarbC").setCreativeTab(AbyssalCraft.tabItems);
		denseCarbonCluster = new ItemACBasic("DCarbC").setCreativeTab(AbyssalCraft.tabItems);
		methane = new ItemACBasic("methane").setCreativeTab(AbyssalCraft.tabItems);
		nitre = new ItemACBasic("nitre").setCreativeTab(AbyssalCraft.tabItems);
		sulfur = new ItemACBasic("sulfur").setCreativeTab(AbyssalCraft.tabItems);
		portalPlacerJzh = new ItemPortalPlacerJzh().setUnlocalizedName("GKJ").setTextureName(modid + ":" + "GKJ");

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
		crystalAbyssalnite = new ItemCrystal("crystalAbyssalnite", 0xBF02BF, "An");
		crystalCoralium = new ItemCrystal("crystalCoralium", 0x00FFEE, "Cor");
		crystalDreadium = new ItemCrystal("crystalDreadium", 0xB00000, "Dr");
		crystalBlaze = new ItemCrystal("crystalBlaze", 0xFFCC00, "none");

		//Shadow items
		shadowfragment = new ItemACBasic("SF").setCreativeTab(AbyssalCraft.tabItems);
		shadowshard = new ItemACBasic("SS").setCreativeTab(AbyssalCraft.tabItems);
		shadowgem = new ItemACBasic("SG").setCreativeTab(AbyssalCraft.tabItems);
		oblivionshard = new ItemACBasic("OS").setCreativeTab(AbyssalCraft.tabItems);

		//Dread items
		Dreadshard = new ItemACBasic("DSOA").setCreativeTab(AbyssalCraft.tabItems);
		dreadchunk = new ItemACBasic("DAC").setCreativeTab(AbyssalCraft.tabItems);
		dreadiumingot = new ItemACBasic("DI").setCreativeTab(AbyssalCraft.tabItems);
		dreadfragment = new ItemACBasic("DF").setCreativeTab(AbyssalCraft.tabItems);
		dreadcloth = new ItemACBasic("DC").setCreativeTab(AbyssalCraft.tabItems);
		dreadplate = new ItemACBasic("DPP").setCreativeTab(AbyssalCraft.tabItems);
		dreadblade = new ItemACBasic("DB").setCreativeTab(AbyssalCraft.tabItems);
		dreadKey = new ItemACBasic("DK").setCreativeTab(AbyssalCraft.tabItems);

		//Abyssalnite items
		abychunk = new ItemACBasic("AC").setCreativeTab(AbyssalCraft.tabItems);
		abyingot = new ItemACBasic("AI").setCreativeTab(AbyssalCraft.tabItems);

		//Coralium items
		Coraliumcluster2 = new ItemCoraliumcluster("2").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCA").setTextureName(modid + ":" + "CGCA");
		Coraliumcluster3 = new ItemCoraliumcluster("3").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCB").setTextureName(modid + ":" + "CGCB");
		Coraliumcluster4 = new ItemCoraliumcluster("4").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCC").setTextureName(modid + ":" + "CGCC");
		Coraliumcluster5 = new ItemCoraliumcluster("5").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCD").setTextureName(modid + ":" + "CGCD");
		Coraliumcluster6 = new ItemCoraliumcluster("6").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCE").setTextureName(modid + ":" + "CGCE");
		Coraliumcluster7 = new ItemCoraliumcluster("7").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCF").setTextureName(modid + ":" + "CGCF");
		Coraliumcluster8 = new ItemCoraliumcluster("8").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCG").setTextureName(modid + ":" + "CGCG");
		Coraliumcluster9 = new ItemCoraliumcluster("9").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCH").setTextureName(modid + ":" + "CGCH");
		Cpearl = new ItemACBasic("CP").setCreativeTab(AbyssalCraft.tabItems);
		Cchunk = new ItemACBasic("CC").setCreativeTab(AbyssalCraft.tabItems);
		Cingot = new ItemACBasic("RCI").setCreativeTab(AbyssalCraft.tabItems);
		Cplate = new ItemACBasic("CPP").setCreativeTab(AbyssalCraft.tabItems);
		Coralium = new ItemACBasic("CG").setCreativeTab(AbyssalCraft.tabItems);
		Corb = new ItemCorb().setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("TG").setTextureName(modid + ":" + "TG");
		Corflesh = new ItemCorflesh(2, 0.1F, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("CF").setTextureName(modid + ":" + "CF");
		Corbone = new ItemCorbone(2, 0.1F, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("CB").setTextureName(modid + ":" + "CB");
		corbow = new ItemCoraliumBow(20.0F, 0, 8, 16).setUnlocalizedName("Corbow").setTextureName(modid + ":" + "Corbow");

		//Tools
		pickaxe = new ItemDarkstonePickaxe(Enum.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DP").setTextureName(modid + ":" + "DP");
		axe = new ItemDarkstoneAxe(Enum.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DA").setTextureName(modid + ":" + "DA");
		shovel = new ItemDarkstoneShovel(Enum.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DS").setTextureName(modid + ":" + "DS");
		sword = new ItemDarkstoneSword(EnumToolMaterialAC.DARKSTONE).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("DSW").setTextureName(modid + ":" + "DSW");
		hoe = new ItemDarkstoneHoe(Enum.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DH").setTextureName(modid + ":" + "DH");
		pickaxeA = new ItemAbyssalnitePickaxe(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AP").setTextureName(modid + ":" + "AP");
		axeA = new ItemAbyssalniteAxe(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AA").setTextureName(modid + ":" + "AA");
		shovelA = new ItemAbyssalniteShovel(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AS").setTextureName(modid + ":" + "AS");
		swordA = new ItemAbyssalniteSword(EnumToolMaterialAC.ABYSSALNITE).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("ASW").setTextureName(modid + ":" + "ASW");
		hoeA = new ItemAbyssalniteHoe(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AH").setTextureName(modid + ":" + "AH");
		pickaxeC = new ItemAbyssalniteCPickaxe(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAP").setTextureName(modid + ":" + "CIAP");
		axeC = new ItemAbyssalniteCAxe(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAA").setTextureName(modid + ":" + "CIAA");
		shovelC = new ItemAbyssalniteCShovel(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAS").setTextureName(modid + ":" + "CIAS");
		swordC = new ItemAbyssalniteCSword(EnumToolMaterialAC.ABYSSALNITE_C).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("CIASW").setTextureName(modid + ":" + "CIASW");
		hoeC = new ItemAbyssalniteCHoe(Enum.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAH").setTextureName(modid + ":" + "CIAH");
		Corpickaxe = new ItemCoraliumPickaxe(Enum.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCP").setTextureName(modid + ":" + "RCP");
		Coraxe = new ItemCoraliumAxe(Enum.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCA").setTextureName(modid + ":" + "RCA");
		Corshovel = new ItemCoraliumShovel(Enum.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCS").setTextureName(modid + ":" + "RCS");
		Corsword = new ItemCoraliumSword(EnumToolMaterialAC.CORALIUM).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("RCSW").setTextureName(modid + ":" + "RCSW");
		Corhoe = new ItemCoraliumHoe(Enum.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCH").setTextureName(modid + ":" + "RCH");
		dreadiumpickaxe = new ItemDreadiumPickaxe(Enum.valueOf(ToolMaterial.class, "DREADIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDP").setTextureName(modid + ":" + "DDP");
		dreadiumaxe = new ItemDreadiumAxe(Enum.valueOf(ToolMaterial.class, "DREADIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDA").setTextureName(modid + ":" + "DDA");
		dreadiumshovel = new ItemDreadiumShovel(Enum.valueOf(ToolMaterial.class, "DREADIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDS").setTextureName(modid + ":" + "DDS");
		dreadiumsword = new ItemDreadiumSword(EnumToolMaterialAC.DREADIUM).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("DDSW").setTextureName(modid + ":" + "DDSW");
		dreadiumhoe = new ItemDreadiumHoe(Enum.valueOf(ToolMaterial.class, "DREADIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DDH").setTextureName(modid + ":" + "DDH");
		dreadhilt = new ItemDreadiumKatana("hilt", 5.0F, 200);
		dreadkatana = new ItemDreadiumKatana("katana", 20.0F, 2000);

		//Armor
		boots = new ItemAbyssalniteArmor(Enum.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 3).setUnlocalizedName("AAB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAB");
		helmet = new ItemAbyssalniteArmor(Enum.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 0).setUnlocalizedName("AAH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAh");
		plate = new ItemAbyssalniteArmor(Enum.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 1).setUnlocalizedName("AAC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAC");
		legs = new ItemAbyssalniteArmor(Enum.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 2).setUnlocalizedName("AAP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAP");
		bootsC = new ItemAbyssalniteCArmor(Enum.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 3).setUnlocalizedName("ACIAB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAB");
		helmetC = new ItemAbyssalniteCArmor(Enum.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 0).setUnlocalizedName("ACIAH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAH");
		plateC = new ItemAbyssalniteCArmor(Enum.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 1).setUnlocalizedName("ACIAC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAC");
		legsC = new ItemAbyssalniteCArmor(Enum.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 2).setUnlocalizedName("ACIAP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAP");
		bootsD = new ItemDreadArmor(Enum.valueOf(ArmorMaterial.class, "Dread"), 5, 3).setUnlocalizedName("ADAB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAB");
		helmetD = new ItemDreadArmor(Enum.valueOf(ArmorMaterial.class, "Dread"), 5, 0).setUnlocalizedName("ADAH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAH");
		plateD = new ItemDreadArmor(Enum.valueOf(ArmorMaterial.class, "Dread"), 5, 1).setUnlocalizedName("ADAC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAC");
		legsD = new ItemDreadArmor(Enum.valueOf(ArmorMaterial.class, "Dread"), 5, 2).setUnlocalizedName("ADAP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAP");
		Corboots = new ItemCoraliumArmor(Enum.valueOf(ArmorMaterial.class, "Coralium"), 5, 3).setUnlocalizedName("ACB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACB");
		Corhelmet = new ItemCoraliumArmor(Enum.valueOf(ArmorMaterial.class, "Coralium"), 5, 0).setUnlocalizedName("ACH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACH");
		Corplate = new ItemCoraliumArmor(Enum.valueOf(ArmorMaterial.class, "Coralium"), 5, 1).setUnlocalizedName("ACC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACC");
		Corlegs = new ItemCoraliumArmor(Enum.valueOf(ArmorMaterial.class, "Coralium"), 5, 2).setUnlocalizedName("ACP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACP");
		CorbootsP = new ItemCoraliumPArmor(Enum.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 3).setUnlocalizedName("ACBP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACBP");
		CorhelmetP = new ItemCoraliumPArmor(Enum.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 0).setUnlocalizedName("ACHP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACHP");
		CorplateP = new ItemCoraliumPArmor(Enum.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 1).setUnlocalizedName("ACCP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACCP");
		CorlegsP = new ItemCoraliumPArmor(Enum.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 2).setUnlocalizedName("ACPP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACPP");
		Depthsboots = new ItemDepthsArmor(Enum.valueOf(ArmorMaterial.class, "Depths"), 5, 3).setUnlocalizedName("ADB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADB");
		Depthshelmet = new ItemDepthsArmor(Enum.valueOf(ArmorMaterial.class, "Depths"), 5, 0).setUnlocalizedName("ADH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADH");
		Depthsplate = new ItemDepthsArmor(Enum.valueOf(ArmorMaterial.class, "Depths"), 5, 1).setUnlocalizedName("ADC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADC");
		Depthslegs = new ItemDepthsArmor(Enum.valueOf(ArmorMaterial.class, "Depths"), 5, 2).setUnlocalizedName("ADP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADP");
		dreadiumboots = new ItemDreadiumArmor(Enum.valueOf(ArmorMaterial.class, "Dreadium"), 5, 3).setUnlocalizedName("ADDB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADDB");
		dreadiumhelmet = new ItemDreadiumArmor(Enum.valueOf(ArmorMaterial.class, "Dreadium"), 5, 0).setUnlocalizedName("ADDH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADDH");
		dreadiumplate = new ItemDreadiumArmor(Enum.valueOf(ArmorMaterial.class, "Dreadium"), 5, 1).setUnlocalizedName("ADDC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADDC");
		dreadiumlegs = new ItemDreadiumArmor(Enum.valueOf(ArmorMaterial.class, "Dreadium"), 5, 2).setUnlocalizedName("ADDP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADDP");
		dreadiumSboots = new ItemDreadiumSamuraiArmor(Enum.valueOf(ArmorMaterial.class, "DreadiumS"), 5, 3).setUnlocalizedName("ADSB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADSB");
		dreadiumShelmet = new ItemDreadiumSamuraiArmor(Enum.valueOf(ArmorMaterial.class, "DreadiumS"), 5, 0).setUnlocalizedName("ADSH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADSH");
		dreadiumSplate = new ItemDreadiumSamuraiArmor(Enum.valueOf(ArmorMaterial.class, "DreadiumS"), 5, 1).setUnlocalizedName("ADSC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADSC");
		dreadiumSlegs = new ItemDreadiumSamuraiArmor(Enum.valueOf(ArmorMaterial.class, "DreadiumS"), 5, 2).setUnlocalizedName("ADSP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADSP");

		//Upgrade kits
		CobbleU = new ItemUpgradeKit("Wood", "Cobblestone").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CobU").setTextureName(modid + ":" + "CobU");
		IronU = new ItemUpgradeKit("Cobblestone", "Iron").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("IroU").setTextureName(modid + ":" + "IroU");
		GoldU = new ItemUpgradeKit("Iron", "Gold").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("GolU").setTextureName(modid + ":" + "GolU");
		DiamondU = new ItemUpgradeKit("Gold", "Diamond").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("DiaU").setTextureName(modid + ":" + "DiaU");
		AbyssalniteU = new ItemUpgradeKit("Diamond", "Abyssalnite").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("AbyU").setTextureName(modid + ":" + "AbyU");
		CoraliumU = new ItemUpgradeKit("Abyssalnite", "Coralium").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CorU").setTextureName(modid + ":" + "CorU");
		DreadiumU = new ItemUpgradeKit("Coralium", "Dreadium").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("DreU").setTextureName(modid + ":" + "DreU");

		//Foodstuffs
		ironp = new ItemACBasic("plate").setCreativeTab(AbyssalCraft.tabItems);
		MRE = new ItemPlatefood(255, 1F, false).setUnlocalizedName("MRE").setTextureName(modid + ":" + "MRE");
		chickenp = new ItemPlatefood(12, 1.2F, false).setUnlocalizedName("ChiP").setTextureName(modid + ":" + "ChiP");
		porkp = new ItemPlatefood(16, 1.6F, false).setUnlocalizedName("PorP").setTextureName(modid + ":" + "PorP");
		beefp = new ItemPlatefood(6, 0.6F, false).setUnlocalizedName("BeeP").setTextureName(modid + ":" + "BeeP");
		fishp = new ItemPlatefood(10, 1.2F, false).setUnlocalizedName("FisP").setTextureName(modid + ":" + "FisP");
		dirtyplate = new ItemACBasic("dirtyplate").setCreativeTab(AbyssalCraft.tabItems);
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

		Cplague = new PotionCplague(32, false, 0x36A880).setIconIndex(1, 0).setPotionName("potion.Cplague");
		Dplague = new PotionDplague(33, false, 0xE60000).setIconIndex(1, 0).setPotionName("potion.Dplague");

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

		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(CFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Cbucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(Cwater, Cbucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(antifluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(antibucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(anticwater, antibucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		//Biome
		BiomeDictionary.registerBiomeType(Darklands, Type.WASTELAND);
		BiomeDictionary.registerBiomeType(Wastelands, Type.WASTELAND);
		BiomeDictionary.registerBiomeType(Dreadlands, Type.PLAINS);
		BiomeDictionary.registerBiomeType(AbyDreadlands, Type.PLAINS);
		BiomeDictionary.registerBiomeType(ForestDreadlands, Type.FOREST);
		BiomeDictionary.registerBiomeType(MountainDreadlands, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(DarklandsForest, Type.FOREST);
		BiomeDictionary.registerBiomeType(DarklandsPlains, Type.PLAINS);
		BiomeDictionary.registerBiomeType(DarklandsHills, Type.HILLS);
		BiomeDictionary.registerBiomeType(DarklandsMountains, Type.MOUNTAIN);
		BiomeDictionary.registerBiomeType(corswamp, Type.SWAMP);
		BiomeDictionary.registerBiomeType(corocean, Type.WATER);

		BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.Darklands, 10));
		BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsForest, 10));
		BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsPlains, 10));
		BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsHills, 10));
		BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.DarklandsMountains, 10));
		BiomeManager.warmBiomes.add(new BiomeEntry(AbyssalCraft.corswamp, 10));
		BiomeManager.oceanBiomes.add(AbyssalCraft.corocean);

		BiomeManager.addSpawnBiome(AbyssalCraft.Darklands);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsForest);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsPlains);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsHills);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsMountains);
		BiomeManager.addSpawnBiome(AbyssalCraft.corswamp);
		BiomeManager.addSpawnBiome(AbyssalCraft.corocean);

		//Dimension
		DimensionManager.registerProviderType(configDimId1, WorldProviderAbyss.class, true);
		DimensionManager.registerDimension(configDimId1, configDimId1);
		DimensionManager.registerProviderType(configDimId2, WorldProviderDreadlands.class, true);
		DimensionManager.registerDimension(configDimId2, configDimId2);

		//Mobs
		EntityRegistry.registerModEntity(EntityDepthsghoul.class, "depthsghoul", 25, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDepthsghoul.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase[] {
			BiomeGenBase.swampland, BiomeGenBase.ocean, BiomeGenBase.beach,
			BiomeGenBase.river,AbyssalCraft.Darklands, AbyssalCraft.Wastelands,
			AbyssalCraft.DarklandsForest, AbyssalCraft.DarklandsHills,
			AbyssalCraft.DarklandsPlains});
		registerEntityEgg(EntityDepthsghoul.class, 0x36A880, 0x012626);

		EntityRegistry.registerModEntity(EntityEvilpig.class, "evilpig", 26, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityEvilpig.class, 5, 1, 5, EnumCreatureType.creature, new BiomeGenBase[] {
			BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest,
			BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle,
			BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
			BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest});
		registerEntityEgg(EntityEvilpig.class, 15771042, 14377823);

		EntityRegistry.registerModEntity(EntityDepthsZombie.class, "abyssalzombie", 27, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDepthsZombie.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase[] {
			BiomeGenBase.ocean, BiomeGenBase.beach, BiomeGenBase.river,
			BiomeGenBase.jungle, BiomeGenBase.swampland,
			BiomeGenBase.sky, AbyssalCraft.Darklands, AbyssalCraft.Wastelands,
			AbyssalCraft.DarklandsForest, AbyssalCraft.DarklandsHills,
			AbyssalCraft.DarklandsPlains});
		registerEntityEgg(EntityDepthsZombie.class, 0x36A880, 0x052824);

		EntityRegistry.registerModEntity(EntityODBPrimed.class, "Primed ODB", 28, this, 80, 3, true);

		EntityRegistry.registerModEntity(EntityJzahar.class, "Jzahar", 29, this, 80, 3, true);
		registerEntityEgg(EntityJzahar.class, 0x133133, 0x342122);

		EntityRegistry.registerModEntity(EntityAbygolem.class, "abygolem", 30, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityAbygolem.class, 5, 1, 5, EnumCreatureType.creature, new BiomeGenBase[] {
			AbyssalCraft.AbyDreadlands});
		registerEntityEgg(EntityAbygolem.class, 0x8A00E6, 0x6100A1);

		EntityRegistry.registerModEntity(EntityDreadgolem.class, "dreadgolem", 31, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDreadgolem.class, 5, 1, 5, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.Dreadlands});
		registerEntityEgg(EntityDreadgolem.class, 0x1E60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDreadguard.class, "dreadguard", 32, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDreadguard.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.MountainDreadlands});
		registerEntityEgg(EntityDreadguard.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityPSDLTracker.class, "PowerstoneTracker", 33, this, 64, 10, true);

		EntityRegistry.registerModEntity(EntityDragonMinion.class, "dragonminion", 34, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDragonMinion.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.Wastelands});
		registerEntityEgg(EntityDragonMinion.class, 0x433434, 0x344344);

		EntityRegistry.registerModEntity(EntityDragonBoss.class, "dragonboss", 35, this, 64, 10, true);
		registerEntityEgg(EntityDragonBoss.class, 0x476767, 0x768833);

		EntityRegistry.registerModEntity(EntityODBcPrimed.class, "Primed ODB Core", 36, this, 80, 3, true);

		EntityRegistry.registerModEntity(EntityShadowCreature.class, "shadowcreature", 37, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityShadowCreature.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.DarklandsMountains});
		registerEntityEgg(EntityShadowCreature.class, 0, 0xFFFFFF);

		EntityRegistry.registerModEntity(EntityShadowMonster.class, "shadowmonster", 38, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityShadowMonster.class, 2, 1, 2, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.DarklandsMountains});
		registerEntityEgg(EntityShadowMonster.class, 0, 0xFFFFFF);

		EntityRegistry.registerModEntity(EntityDreadling.class, "dreadling", 39, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDreadling.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.Dreadlands});
		registerEntityEgg(EntityDreadling.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDreadSpawn.class, "dreadspawn", 40, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDreadSpawn.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.ForestDreadlands});
		registerEntityEgg(EntityDreadSpawn.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDemonPig.class, "demonpig", 41, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDemonPig.class, 2, 1, 3, EnumCreatureType.monster, new BiomeGenBase[] {
			BiomeGenBase.hell, AbyssalCraft.ForestDreadlands});
		registerEntityEgg(EntityDemonPig.class, 15771042, 14377823);

		EntityRegistry.registerModEntity(EntitySkeletonGoliath.class, "gskeleton", 42, this, 80, 3, true);
		EntityRegistry.addSpawn(EntitySkeletonGoliath.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.Wastelands});
		registerEntityEgg(EntitySkeletonGoliath.class, 0xD6D6C9, 0xC6C7AD);

		EntityRegistry.registerModEntity(EntityChagarothSpawn.class, "chagarothspawn", 43, this, 80, 3, true);
		registerEntityEgg(EntityChagarothSpawn.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityChagarothFist.class, "chagarothfist", 44, this, 80, 3, true);
		registerEntityEgg(EntityChagarothFist.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityChagaroth.class, "chagaroth", 45, this, 80, 3, true);
		registerEntityEgg(EntityChagaroth.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityShadowBeast.class, "shadowbeast", 46, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityShadowBeast.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] {
			AbyssalCraft.DarklandsMountains});
		registerEntityEgg(EntityShadowBeast.class, 0, 0xFFFFFF);

		EntityRegistry.registerModEntity(EntitySacthoth.class, "shadowboss", 47, this, 80, 3, true);
		registerEntityEgg(EntitySacthoth.class, 0, 0xFFFFFF);

		proxy.addArmor("Abyssalnite");
		proxy.addArmor("AbyssalniteC");
		proxy.addArmor("Dread");
		proxy.addArmor("Coralium");
		proxy.addArmor("CoraliumP");
		proxy.addArmor("Depths");
		proxy.addArmor("Dreadium");
		proxy.addArmor("DreadiumS");

	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

		LogHelper.info("Initializing AbyssalCraft.");
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
		secret1 = new Achievement("achievement.secret1", "secret1", 9, -9, AbyssalCraft.devsword, AchievementList.openInventory).registerStat();
		summonChagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 3, 12, AbyssalCraft.dreadaltarbottom, AbyssalCraft.enterdreadlands).registerStat();
		killChagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 6, 12, AbyssalCraft.dreadKey, AbyssalCraft.summonChagaroth).setSpecial().registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[]{mineDS, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah, enterdreadlands, killdreadguard, ghoulhead, killPete, killWilson, killOrange, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, Jzhstaff, secret1, summonChagaroth, killChagaroth}));

		proxy.init();
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(StructureDreadlandsMineStart.class, "DreadMine");
		StructureDreadlandsMinePieces.registerStructurePieces();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
		AbyssalCrafting.addRecipes();
		proxy.registerRenderThings();

		//OreDictionary stuff
		OreDictionary.registerOre("ingotAbyssalnite", abyingot);
		OreDictionary.registerOre("ingotCoralium", Cingot);
		OreDictionary.registerOre("gemCoralium", Coralium);
		OreDictionary.registerOre("oreAbyssalnite", abyore);
		OreDictionary.registerOre("oreCoralium", Coraliumore);
		OreDictionary.registerOre("oreAbyssalCoralium", AbyCorOre);
		OreDictionary.registerOre("oreDreadedAbyssalnite", dreadore);
		OreDictionary.registerOre("oreDreadlandsAbyssalnite", abydreadore);
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
		OreDictionary.registerOre("treeLeaves", DLTLeaves);
		OreDictionary.registerOre("treeLeaves", dreadleaves);
		OreDictionary.registerOre("blockAbyssalnite", abyblock);
		OreDictionary.registerOre("blockCoralium", corblock);
		OreDictionary.registerOre("blockDreadium", dreadiumblock);
		OreDictionary.registerOre("ingotCoraliumBrick", cbrick);
		OreDictionary.registerOre("ingotDreadium", dreadiumingot);
		OreDictionary.registerOre("materialSulfur", sulfur);
		OreDictionary.registerOre("materialSalpeter", nitre);
		OreDictionary.registerOre("materialMethane", methane);
		OreDictionary.registerOre("oreSalpeter", nitre);
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
	}

	public static int getUniqueEntityId() {
		do
			startEntityId++;
		while (EntityList.getStringFromID(startEntityId) != null);

		return startEntityId;
	}

	@SuppressWarnings("unchecked")
	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor) {
		int id = getUniqueEntityId();
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		LogHelper.info("Post-initializing AbyssalCraft");
		proxy.postInit();
		LogHelper.info("AbyssalCraft loaded.");
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
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
					if(!hasPinged){
						updateProxy.announce("["+EnumChatFormatting.BLUE
								+"AbyssalCraft"+EnumChatFormatting.RESET+"] Version "+EnumChatFormatting.AQUA+webVersion+EnumChatFormatting.RESET+" of AbyssalCraft is available. Check http://adf.ly/FQarm for more info. (Your Version: "
								+EnumChatFormatting.AQUA+AbyssalCraft.version+EnumChatFormatting.RESET+")");
						hasPinged = true;
					}
				} else if(!hasPinged){
					updateProxy.announce("["+EnumChatFormatting.BLUE
							+"AbyssalCraft"+EnumChatFormatting.RESET+"] Running the latest version of AbyssalCraft, "
							+EnumChatFormatting.AQUA+AbyssalCraft.version+EnumChatFormatting.RESET+".");
					hasPinged = true;
				}
			} catch(Exception e) {
				System.err.println("UpdateChecker encountered an Exception, see following stacktrace:");
				e.printStackTrace();
			}
		}

		public boolean isUpdateAvailable() throws IOException, MalformedURLException {
			BufferedReader versionFile = new BufferedReader(new InputStreamReader(new URL("https://dl.dropboxusercontent.com/s/ff14wwf1hqav59z/version.txt?token_hash=AAHGqet5RWJdHIPJVNrE4omCxAtx_PJbN4R_1YYSFAs-Og&dl=1").openStream()));
			String curVersion = versionFile.readLine();
			webVersion = curVersion;
			versionFile.close();

			if (!curVersion.equals(AbyssalCraft.version))
				return true;

			return false;
		}
	}
}