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
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.BiomeProperties;
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
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.item.ACItems;
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
import com.shinoow.abyssalcraft.common.structures.omothol.MapGenOmothol;
import com.shinoow.abyssalcraft.common.structures.omothol.StructureOmotholPieces;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.common.util.RitualUtil;
import com.shinoow.abyssalcraft.common.world.AbyssalCraftWorldGenerator;
import com.shinoow.abyssalcraft.common.world.WorldProviderAbyss;
import com.shinoow.abyssalcraft.common.world.WorldProviderDarkRealm;
import com.shinoow.abyssalcraft.common.world.WorldProviderDreadlands;
import com.shinoow.abyssalcraft.common.world.WorldProviderOmothol;
import com.shinoow.abyssalcraft.common.world.biome.*;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version,dependencies = "required-after:Forge@[forgeversion,);after:JEI@[3.3.3,)", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory", acceptedMinecraftVersions = "[1.9]", updateJSON = "https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/version.json")
public class AbyssalCraft {

	public static final String version = "1.9.1.6";
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

	public static Block Darkbrickslab2, Darkcobbleslab2, abyslab2, Darkstoneslab2, DLTslab2,
	Altar, dreadbrickslab2, abydreadbrickslab2, cstonebrickslab2, ethaxiumslab2, house,
	darkethaxiumslab2;

	@Deprecated
	public static BiomeGenBase Darklands, DarklandsForest, DarklandsPlains, DarklandsHills,
	DarklandsMountains, corswamp, Wastelands, Dreadlands, AbyDreadlands, ForestDreadlands,
	MountainDreadlands, omothol, darkRealm;

	//"secret" dev stuff
	public static Item devsword;
	//shadow items
	public static Item shadowPlate;

	public static PotionType Cplague_normal, Cplague_long, Dplague_normal, Dplague_long,
	Dplague_strong, antiMatter_normal, antiMatter_long;

	public static final CreativeTabs tabBlock = new CreativeTabs("acblocks"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ACBlocks.darkstone);
		}
	};
	public static final CreativeTabs tabItems = new CreativeTabs("acitems"){

		@Override
		public Item getTabIconItem() {
			return ACItems.necronomicon;
		}
	};
	public static final CreativeTabs tabTools = new CreativeTabs("actools"){

		@Override
		public Item getTabIconItem() {
			return ACItems.darkstone_axe;
		}
	};
	public static final CreativeTabs tabCombat = new CreativeTabs("acctools"){

		@Override
		public Item getTabIconItem() {
			return ACItems.darkstone_sword;
		}
	};
	public static final CreativeTabs tabFood = new CreativeTabs("acfood"){

		@Override
		public Item getTabIconItem() {
			return ACItems.iron_plate;
		}
	};
	public static final CreativeTabs tabDecoration = new CreativeTabs("acdblocks"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ACBlocks.wooden_crate);
		}
	};
	public static final CreativeTabs tabCrystals = new CreativeTabs("accrystals"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ACBlocks.crystallizer_idle);
		}
	};
	public static final CreativeTabs tabCoins = new CreativeTabs("accoins"){

		@Override
		public Item getTabIconItem() {
			return ACItems.coin;
		}
	};

	//Dimension Ids
	public static int configDimId1, configDimId2, configDimId3, configDimId4;

	public static boolean keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;

	public static boolean dark1, dark2, dark3, dark4, dark5, coralium1;
	public static boolean darkspawn1, darkspawn2, darkspawn3, darkspawn4, darkspawn5, coraliumspawn1;
	public static int darkWeight1, darkWeight2, darkWeight3, darkWeight4, darkWeight5, coraliumWeight;

	public static boolean shouldSpread, shouldInfect, breakLogic, destroyOcean, demonAnimalFire, updateC, darkness,
	particleBlock, particleEntity, hardcoreMode, useDynamicPotionIds, endAbyssalZombie, evilAnimalCreatureType,
	antiItemDisintegration;
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

	public static DimensionType THE_ABYSSAL_WASTELAND, THE_DREADLANDS, OMOTHOL, THE_DARK_REALM;

	public static SoundEvent dreadguard_ambient, dreadguard_hurt, dreadguard_death, ghoul_normal_ambient,
	ghoul_normal_hurt, ghoul_death, ghoul_pete_hurt, ghoul_pete_ambient, golem_death, golem_hurt, golem_ambient,
	sacthoth_death, shadow_death, shadow_hurt, remnant_scream, shoggoth_ambient, shoggoth_hurt, shoggoth_death;

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
		AbyssalCraftAPI.setInternalNDHandler(new InternalNecroDataHandler());

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
		ACBlocks.darkstone = new BlockDarkstone().setCreativeTab(tabBlock).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkstone");
		ACBlocks.darkstone_brick = new BlockACBasic(Material.rock, 1.65F, 12.0F, SoundType.STONE).setUnlocalizedName("darkstone_brick");
		ACBlocks.darkstone_cobblestone = new BlockACBasic(Material.rock, 2.2F, 12.0F, SoundType.STONE).setUnlocalizedName("darkstone_cobble");
		ACBlocks.glowing_darkstone_bricks = new BlockACBasic(Material.rock, "pickaxe", 3, 55F, 3000F, SoundType.STONE).setLightLevel(1.0F).setUnlocalizedName("dsglow");
		ACBlocks.darkstone_brick_slab = new BlockACSingleSlab(Material.rock, SoundType.STONE).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkbrickslab1");
		Darkbrickslab2 = new BlockACDoubleSlab(ACBlocks.darkstone_brick_slab, Material.rock).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkbrickslab2");
		ACBlocks.darkstone_cobblestone_slab = new BlockACSingleSlab(Material.rock, SoundType.STONE).setHardness(1.65F) .setResistance(12.0F).setUnlocalizedName("darkcobbleslab1");
		Darkcobbleslab2 = new BlockACDoubleSlab(ACBlocks.darkstone_cobblestone_slab, Material.rock).setHardness(1.65F) .setResistance(12.0F).setUnlocalizedName("darkcobbleslab2");
		ACBlocks.darklands_grass = new BlockDarklandsgrass().setCreativeTab(tabBlock).setHardness(0.4F).setUnlocalizedName("darkgrass");
		ACBlocks.darkstone_brick_stairs = new BlockACStairs(ACBlocks.darkstone_brick).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("dbstairs");
		ACBlocks.darkstone_cobblestone_stairs = new BlockACStairs(ACBlocks.darkstone_cobblestone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("dcstairs");
		ACBlocks.darklands_oak_leaves = new BlockDLTLeaves().setHardness(0.2F).setResistance(1.0F).setUnlocalizedName("dltleaves");
		ACBlocks.darklands_oak_wood = new BlockDLTLog().setHardness(2.0F).setResistance(1.0F).setUnlocalizedName("dltlog");
		ACBlocks.darklands_oak_sapling = new BlockDLTSapling().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("dltsapling");
		ACBlocks.abyssal_stone = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, SoundType.STONE).setUnlocalizedName("abystone");
		ACBlocks.abyssal_stone_brick = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, SoundType.STONE).setUnlocalizedName("abybrick");
		ACBlocks.abyssal_stone_brick_slab = new BlockACSingleSlab(Material.rock, "pickaxe", 2, SoundType.STONE).setCreativeTab(tabBlock).setHardness(1.8F).setResistance(12.0F).setUnlocalizedName("abyslab1");
		abyslab2 = new BlockACDoubleSlab(ACBlocks.abyssal_stone_brick_slab, Material.rock, "pickaxe", 2).setHardness(1.8F).setResistance(12.0F).setUnlocalizedName("abyslab2");
		ACBlocks.abyssal_stone_brick_stairs = new BlockACStairs(ACBlocks.abyssal_stone_brick, "pickaxe", 2).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("abystairs");
		ACBlocks.coralium_ore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("coraliumore");
		ACBlocks.abyssalnite_ore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abyore");
		ACBlocks.abyssal_stone_brick_fence = new BlockACFence(Material.rock, "pickaxe", 2, SoundType.STONE).setHardness(1.8F).setResistance(12.0F).setUnlocalizedName("abyfence");
		ACBlocks.darkstone_cobblestone_wall = new BlockDarkstonecobblewall(ACBlocks.darkstone_cobblestone).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("dscwall");
		ACBlocks.wooden_crate = new BlockCrate().setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("crate");
		ACBlocks.oblivion_deathbomb = new BlockODB().setHardness(3.0F).setResistance(0F).setUnlocalizedName("odb");
		ACBlocks.block_of_abyssalnite = new IngotBlock(2).setUnlocalizedName("abyblock");
		ACBlocks.coralium_infused_stone = new BlockACOre(3, 3.0F, 6.0F).setUnlocalizedName("coraliumstone");
		ACBlocks.odb_core = new BlockODBcore().setHardness(3.0F).setResistance(0F).setUnlocalizedName("odbcore");
		ACBlocks.abyssal_gateway = new BlockAbyssPortal().setUnlocalizedName("abyportal");
		ACBlocks.darkstone_slab = new BlockACSingleSlab(Material.rock, SoundType.STONE).setCreativeTab(tabBlock).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkstoneslab1");
		Darkstoneslab2 = new BlockACDoubleSlab(ACBlocks.darkstone_slab, Material.rock).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("darkstoneslab2");
		ACBlocks.coralium_fire = new BlockCoraliumfire().setLightLevel(1.0F).setUnlocalizedName("coraliumfire");
		ACBlocks.darkstone_button = new BlockACButton(true, "DS").setHardness(0.6F).setResistance(12.0F).setUnlocalizedName("dsbutton");
		ACBlocks.darkstone_pressure_plate = new BlockACPressureplate("DS", Material.rock, BlockACPressureplate.Sensitivity.MOBS, SoundType.STONE).setHardness(0.6F).setResistance(12.0F).setUnlocalizedName("dspplate");
		ACBlocks.darklands_oak_planks = new BlockACBasic(Material.wood, 2.0F, 5.0F, SoundType.WOOD).setUnlocalizedName("dltplank");
		ACBlocks.darklands_oak_button = new BlockACButton(true, "DLTplank").setHardness(0.5F).setUnlocalizedName("dltbutton");
		ACBlocks.darklands_oak_pressure_plate = new BlockACPressureplate("DLTplank", Material.wood, BlockACPressureplate.Sensitivity.EVERYTHING, SoundType.WOOD).setHardness(0.5F).setUnlocalizedName("dltpplate");
		ACBlocks.darklands_oak_stairs = new BlockACStairs(ACBlocks.darklands_oak_planks).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("dltstairs");
		ACBlocks.darklands_oak_slab = new BlockACSingleSlab(Material.wood, SoundType.WOOD).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("dltslab1");
		DLTslab2 = new BlockACDoubleSlab(ACBlocks.darklands_oak_slab, Material.wood).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("dltslab2");
		ACBlocks.block_of_coralium = new IngotBlock(5).setUnlocalizedName("corblock");
		ACBlocks.dreadlands_infused_powerstone = new BlockPSDL().setHardness(50.0F).setResistance(3000F).setCreativeTab(tabDecoration).setUnlocalizedName("psdl");
		ACBlocks.abyssal_coralium_ore = new BlockACOre(3, 3.0F, 6.0F).setUnlocalizedName("abycorore");
		Altar = new BlockAltar().setHardness(4.0F).setResistance(300.0F).setUnlocalizedName("altar");
		ACBlocks.abyssal_stone_button = new BlockACButton(false, "pickaxe", 2, "AS").setHardness(0.8F).setResistance(12.0F).setUnlocalizedName("abybutton");
		ACBlocks.abyssal_stone_pressure_plate = new BlockACPressureplate("AS", Material.rock, BlockACPressureplate.Sensitivity.MOBS, "pickaxe", 2, SoundType.STONE).setHardness(0.8F).setResistance(12.0F).setUnlocalizedName("abypplate");
		ACBlocks.darkstone_brick_fence = new BlockACFence(Material.rock, SoundType.STONE).setHardness(1.65F).setResistance(12.0F).setUnlocalizedName("dsbfence");
		ACBlocks.darklands_oak_fence = new BlockACFence(Material.wood, SoundType.WOOD).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("dltfence");
		ACBlocks.dreaded_abyssalnite_ore = new BlockACOre(4, 2.5F, 20.0F).setUnlocalizedName("dreadore");
		ACBlocks.dreadlands_abyssalnite_ore = new BlockACOre(4, 2.5F, 20.0F).setUnlocalizedName("abydreadore");
		ACBlocks.dreadstone_brick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, SoundType.STONE).setUnlocalizedName("dreadbrick");
		ACBlocks.abyssalnite_stone_brick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, SoundType.STONE).setUnlocalizedName("abydreadbrick");
		ACBlocks.dreadlands_log = new BlockDreadLog().setHardness(2.0F).setResistance(12.0F).setUnlocalizedName("dreadlog");
		ACBlocks.dreadlands_leaves = new BlockDreadLeaves().setHardness(0.2F).setResistance(1.0F).setUnlocalizedName("dreadleaves");
		ACBlocks.dreadlands_sapling = new BlockDreadSapling().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("dreadsapling");
		ACBlocks.dreadlands_planks = new BlockACBasic(Material.wood, 2.0F, 5.0F, SoundType.WOOD).setUnlocalizedName("dreadplanks");
		ACBlocks.dreaded_gateway = new BlockDreadlandsPortal().setUnlocalizedName("dreadportal");
		ACBlocks.dreaded_fire = new BlockDreadFire().setLightLevel(1.0F).setUnlocalizedName("dreadfire");
		ACBlocks.depths_ghoul_head = new BlockDGhead().setHardness(1.0F).setResistance(6.0F).setCreativeTab(tabDecoration).setUnlocalizedName("dghead");
		ACBlocks.liquid_coralium = new BlockCLiquid().setResistance(500.0F).setLightLevel(1.0F).setUnlocalizedName("cwater");
		ACBlocks.dreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, SoundType.STONE).setUnlocalizedName("dreadstone");
		ACBlocks.abyssalnite_stone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, SoundType.STONE).setUnlocalizedName("abydreadstone");
		ACBlocks.dreadlands_grass = new BlockDreadGrass().setHardness(0.4F).setUnlocalizedName("dreadgrass");
		ACBlocks.pete_head = new BlockPhead().setHardness(1.0F).setResistance(6.0F).setCreativeTab(tabDecoration).setUnlocalizedName("phead");
		ACBlocks.mr_wilson_head = new BlockWhead().setHardness(1.0F).setResistance(6.0F).setCreativeTab(tabDecoration).setUnlocalizedName("whead");
		ACBlocks.dr_orange_head = new BlockOhead().setHardness(1.0F).setResistance(6.0F).setCreativeTab(tabDecoration).setUnlocalizedName("ohead");
		ACBlocks.dreadstone_brick_stairs = new BlockACStairs(ACBlocks.dreadstone_brick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("dreadbrickstairs");
		ACBlocks.dreadstone_brick_fence = new BlockACFence(Material.rock, "pickaxe", 4, SoundType.STONE).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("dreadbrickfence");
		ACBlocks.dreadstone_brick_slab = new BlockACSingleSlab(Material.rock, "pickaxe", 4, SoundType.STONE).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("dreadbrickslab1");
		dreadbrickslab2 = new BlockACDoubleSlab(ACBlocks.dreadstone_brick_slab, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("dreadbrickslab2");
		ACBlocks.abyssalnite_stone_brick_stairs = new BlockACStairs(ACBlocks.abyssalnite_stone_brick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("abydreadbrickstairs");
		ACBlocks.abyssalnite_stone_brick_fence = new BlockACFence(Material.rock, "pickaxe", 4, SoundType.STONE).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("abydreadbrickfence");
		ACBlocks.abyssalnite_stone_brick_slab = new BlockACSingleSlab(Material.rock, "pickaxe", 4, SoundType.STONE).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("abydreadbrickslab1");
		abydreadbrickslab2 = new BlockACDoubleSlab(ACBlocks.abyssalnite_stone_brick_slab, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setUnlocalizedName("abydreadbrickslab2");
		ACBlocks.liquid_antimatter = new BlockAntiliquid().setResistance(500.0F).setLightLevel(0.5F).setUnlocalizedName("antiwater");
		ACBlocks.coralium_stone = new BlockCoraliumstone().setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("cstone");
		ACBlocks.coralium_stone_brick = new BlockACBasic(Material.rock, 1.5F, 10.0F, SoundType.STONE).setUnlocalizedName("cstonebrick");
		ACBlocks.coralium_stone_brick_fence = new BlockACFence(Material.rock, SoundType.STONE).setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("cstonebrickfence");
		ACBlocks.coralium_stone_brick_slab = new BlockACSingleSlab(Material.rock, SoundType.STONE).setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("cstonebrickslab1");
		cstonebrickslab2 = new BlockACDoubleSlab(ACBlocks.coralium_stone_brick_slab, Material.rock).setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("cstonebrickslab2");
		ACBlocks.coralium_stone_brick_stairs = new BlockACStairs(ACBlocks.coralium_stone_brick, "pickaxe", 0).setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("cstonebrickstairs");
		ACBlocks.coralium_stone_button = new BlockACButton(false, "cstone").setHardness(0.6F).setResistance(12.0F).setUnlocalizedName("cstonebutton");
		ACBlocks.coralium_stone_pressure_plate = new BlockACPressureplate("cstone", Material.rock, BlockACPressureplate.Sensitivity.MOBS, SoundType.STONE).setHardness(0.6F).setResistance(12.0F).setUnlocalizedName("cstonepplate");
		ACBlocks.chagaroth_altar_top = new BlockDreadAltarTop().setHardness(30.0F).setResistance(300.0F).setCreativeTab(tabDecoration).setUnlocalizedName("dreadaltartop");
		ACBlocks.chagaroth_altar_bottom = new BlockDreadAltarBottom().setHardness(30.0F).setResistance(300.0F).setCreativeTab(tabDecoration).setUnlocalizedName("dreadaltarbottom");
		ACBlocks.crystallizer_idle = new BlockCrystallizer(false).setHardness(2.5F).setResistance(12.0F).setUnlocalizedName("crystallizer");
		ACBlocks.crystallizer_active = new BlockCrystallizer(true).setHardness(2.5F).setResistance(12.0F).setLightLevel(0.875F).setUnlocalizedName("crystallizer_on");
		ACBlocks.block_of_dreadium = new IngotBlock(6).setUnlocalizedName("dreadiumblock");
		ACBlocks.transmutator_idle = new BlockTransmutator(false).setHardness(2.5F).setResistance(12.0F).setUnlocalizedName("transmutator");
		ACBlocks.transmutator_active = new BlockTransmutator(true).setHardness(2.5F).setResistance(12.0F).setLightLevel(0.875F).setUnlocalizedName("transmutator_on");
		ACBlocks.dreadguard_spawner = new BlockDreadguardSpawner().setUnlocalizedName("dreadguardspawner");
		ACBlocks.chagaroth_spawner = new BlockChagarothSpawner().setUnlocalizedName("chagarothspawner");
		ACBlocks.dreadlands_wood_fence = new BlockACFence(Material.wood, SoundType.WOOD).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("drtfence");
		ACBlocks.nitre_ore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("nitreore");
		ACBlocks.abyssal_iron_ore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abyiroore");
		ACBlocks.abyssal_gold_ore = new BlockACOre(2, 5.0F, 10.0F).setUnlocalizedName("abygolore");
		ACBlocks.abyssal_diamond_ore = new BlockACOre(2, 5.0F, 10.0F).setUnlocalizedName("abydiaore");
		ACBlocks.abyssal_nitre_ore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abynitore");
		ACBlocks.abyssal_tin_ore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abytinore");
		ACBlocks.abyssal_copper_ore = new BlockACOre(2, 3.0F, 6.0F).setUnlocalizedName("abycopore");
		ACBlocks.pearlescent_coralium_ore = new BlockACOre(5, 8.0F, 10.0F).setUnlocalizedName("abypcorore");
		ACBlocks.liquified_coralium_ore = new BlockACOre(4, 10.0F, 12.0F).setUnlocalizedName("abylcorore");
		ACBlocks.solid_lava = new BlockSolidLava("solidlava");
		ACBlocks.ethaxium = new BlockACBasic(Material.rock, "pickaxe", 8, 100.0F, Float.MAX_VALUE, SoundType.STONE).setUnlocalizedName("ethaxium");
		ACBlocks.ethaxium_brick = new BlockEthaxiumBrick().setUnlocalizedName("ethaxiumbrick");
		ACBlocks.ethaxium_pillar = new BlockEthaxiumPillar().setUnlocalizedName("ethaxiumpillar");
		ACBlocks.ethaxium_brick_stairs = new BlockACStairs(ACBlocks.ethaxium_brick, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("ethaxiumbrickstairs");
		ACBlocks.ethaxium_brick_slab = new BlockACSingleSlab(Material.rock, "pickaxe", 8, SoundType.STONE).setHardness(100.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("ethaxiumbrickslab1");
		ethaxiumslab2 = new BlockACDoubleSlab(ACBlocks.ethaxium_brick_slab, Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("ethaxiumbrickslab2");
		ACBlocks.ethaxium_brick_fence = new BlockACFence(Material.rock, "pickaxe", 8, SoundType.STONE).setHardness(100.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("ethaxiumfence");
		ACBlocks.omothol_stone = new BlockACBasic(Material.rock, "pickaxe", 6, 10.0F, 12.0F, SoundType.STONE).setUnlocalizedName("omotholstone");
		ACBlocks.block_of_ethaxium = new IngotBlock(8).setResistance(Float.MAX_VALUE).setUnlocalizedName("ethaxiumblock");
		ACBlocks.omothol_gateway = new BlockOmotholPortal().setUnlocalizedName("omotholportal");
		ACBlocks.omothol_fire = new BlockOmotholFire().setLightLevel(1.0F).setUnlocalizedName("omotholfire");
		ACBlocks.engraver = new BlockEngraver().setHardness(2.5F).setResistance(12.0F).setUnlocalizedName("engraver");
		house = new BlockHouse().setHardness(1.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("engraver_on");
		ACBlocks.materializer = new BlockMaterializer().setUnlocalizedName("materializer");
		ACBlocks.dark_ethaxium_brick = new BlockDarkEthaxiumBrick().setUnlocalizedName("darkethaxiumbrick");
		ACBlocks.dark_ethaxium_pillar = new BlockDarkEthaxiumPillar().setUnlocalizedName("darkethaxiumpillar");
		ACBlocks.dark_ethaxium_brick_stairs = new BlockACStairs(ACBlocks.dark_ethaxium_brick, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("darkethaxiumbrickstairs");
		ACBlocks.dark_ethaxium_brick_slab = new BlockACSingleSlab(Material.rock, "pickaxe", 8, SoundType.STONE).setHardness(150.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("darkethaxiumbrickslab1");
		darkethaxiumslab2 = new BlockACDoubleSlab(ACBlocks.dark_ethaxium_brick_slab, Material.rock, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("darkethaxiumbrickslab2");
		ACBlocks.dark_ethaxium_brick_fence = new BlockACFence(Material.rock, "pickaxe", 8, SoundType.STONE).setHardness(150.0F).setResistance(Float.MAX_VALUE).setUnlocalizedName("darkethaxiumbrickfence");
		ACBlocks.ritual_altar = new BlockRitualAltar().setUnlocalizedName("ritualaltar");
		ACBlocks.ritual_pedestal = new BlockRitualPedestal().setUnlocalizedName("ritualpedestal");
		ACBlocks.shoggoth_ooze = new BlockShoggothOoze().setUnlocalizedName("shoggothblock");
		ACBlocks.cthulhu_statue = new BlockCthulhuStatue().setUnlocalizedName("cthulhustatue");
		ACBlocks.hastur_statue = new BlockHasturStatue().setUnlocalizedName("hasturstatue");
		ACBlocks.jzahar_statue = new BlockJzaharStatue().setUnlocalizedName("jzaharstatue");
		ACBlocks.azathoth_statue = new BlockAzathothStatue().setUnlocalizedName("azathothstatue");
		ACBlocks.nyarlathotep_statue = new BlockNyarlathotepStatue().setUnlocalizedName("nyarlathotepstatue");
		ACBlocks.yog_sothoth_statue = new BlockYogsothothStatue().setUnlocalizedName("yogsothothstatue");
		ACBlocks.shub_niggurath_statue = new BlockShubniggurathStatue().setUnlocalizedName("shubniggurathstatue");
		ACBlocks.monolith_stone = new BlockACBasic(Material.rock, 6.0F, 24.0F, SoundType.STONE).setUnlocalizedName("monolithstone");
		ACBlocks.shoggoth_biomass = new BlockShoggothBiomass();
		ACBlocks.energy_pedestal = new BlockEnergyPedestal();
		ACBlocks.monolith_pillar = new BlockMonolithPillar();
		ACBlocks.sacrificial_altar = new BlockSacrificialAltar();
		ACBlocks.tiered_energy_pedestal = new BlockTieredEnergyPedestal();
		ACBlocks.tiered_sacrificial_altar = new BlockTieredSacrificialAltar();
		ACBlocks.jzahar_spawner = new BlockJzaharSpawner().setUnlocalizedName("jzaharspawner");
		ACBlocks.minion_of_the_gatekeeper_spawner = new BlockGatekeeperMinionSpawner().setUnlocalizedName("gatekeeperminionspawner");
		ACBlocks.mimic_fire = new BlockMimicFire().setUnlocalizedName("fire");

		((BlockShoggothOoze) ACBlocks.shoggoth_ooze).initBlacklist();

		//Biome
		ACBiomes.darklands = new BiomeGenDarklands(new BiomeProperties("Darklands").setWaterColor(14745518));
		ACBiomes.abyssal_wastelands = new BiomeGenAbywasteland(new BiomeProperties("Abyssal Wastelands").setWaterColor(0x24FF83).setRainDisabled());
		ACBiomes.dreadlands = new BiomeGenDreadlands(new BiomeProperties("Dreadlands").setRainDisabled());
		ACBiomes.purified_dreadlands = new BiomeGenAbyDreadlands(new BiomeProperties("Purified Dreadlands").setRainDisabled());
		ACBiomes.dreadlands_forest = new BiomeGenForestDreadlands(new BiomeProperties("Dreadlands Forest").setRainDisabled());
		ACBiomes.dreadlands_mountains = new BiomeGenMountainDreadlands(new BiomeProperties("Dreadlands Mountains").setBaseHeight(1.3F).setHeightVariation(0.9F).setRainDisabled());
		ACBiomes.darklands_forest = new BiomeGenDarklandsForest(new BiomeProperties("Darklands Forest").setWaterColor(14745518));
		ACBiomes.darklands_plains = new BiomeGenDarklandsPlains(new BiomeProperties("Darklands Plains").setWaterColor(14745518).setRainDisabled());
		ACBiomes.darklands_hills = new BiomeGenDarklandsHills(new BiomeProperties("Darklands Highland").setWaterColor(14745518).setBaseHeight(1.1F).setHeightVariation(0.5F));
		ACBiomes.darklands_mountains = new BiomeGenDarklandsMountains(new BiomeProperties("Darklands Mountains").setWaterColor(14745518).setBaseHeight(1.3F).setHeightVariation(0.9F).setRainDisabled());
		ACBiomes.coralium_infested_swamp = new BiomeGenCorSwamp(new BiomeProperties("Coralium Infested Swamp").setWaterColor(0x24FF83).setBaseHeight(-0.2F).setHeightVariation(0.1F));
		ACBiomes.omothol = new BiomeGenOmothol(new BiomeProperties("Omothol").setWaterColor(14745518).setRainDisabled());
		ACBiomes.dark_realm = new BiomeGenDarkRealm(new BiomeProperties("Dark Realm").setWaterColor(14745518).setRainDisabled());

		//TODO remove in AC 1.9.2
		Darklands = ACBiomes.darklands;
		DarklandsForest = ACBiomes.darklands_forest;
		DarklandsPlains = ACBiomes.darklands_plains;
		DarklandsHills = ACBiomes.darklands_hills;
		DarklandsMountains = ACBiomes.darklands_mountains;
		corswamp = ACBiomes.coralium_infested_swamp;
		Wastelands = ACBiomes.abyssal_wastelands;
		Dreadlands = ACBiomes.dreadlands;
		AbyDreadlands = ACBiomes.purified_dreadlands;
		ForestDreadlands = ACBiomes.dreadlands_forest;
		MountainDreadlands = ACBiomes.dreadlands_mountains;
		omothol = ACBiomes.omothol;
		darkRealm = ACBiomes.dark_realm;

		//"secret" dev stuff
		devsword = new AbyssalCraftTool();

		//Misc items
		ACItems.oblivion_catalyst = new ItemOC();
		ACItems.staff_of_the_gatekeeper = new ItemStaff();
		ACItems.gateway_key = new ItemPortalPlacer();
		ACItems.liquid_coralium_bucket = new ItemCBucket(ACBlocks.liquid_coralium);
		ACItems.powerstone_tracker = new ItemTrackerPSDL();
		ACItems.eye_of_the_abyss = new ItemEoA();
		ACItems.dreaded_gateway_key = new ItemPortalPlacerDL();
		ACItems.coralium_brick = new ItemACBasic("cbrick");
		ACItems.cudgel = new ItemCudgel();
		ACItems.carbon_cluster = new ItemACBasic("carboncluster");
		ACItems.dense_carbon_cluster = new ItemACBasic("densecarboncluster");
		ACItems.methane = new ItemACBasic("methane");
		ACItems.nitre = new ItemACBasic("nitre");
		ACItems.sulfur = new ItemACBasic("sulfur");
		ACItems.rlyehian_gateway_key = new ItemPortalPlacerJzh();
		ACItems.tin_ingot = new ItemACBasic("tiningot");
		ACItems.copper_ingot = new ItemACBasic("copperingot");
		ACItems.life_crystal = new ItemACBasic("lifecrystal");
		ACItems.shoggoth_flesh = new ItemMetadata("shoggothflesh", true, "overworld", "abyssalwasteland", "dreadlands", "omothol", "darkrealm");
		ACItems.eldritch_scale = new ItemACBasic("eldritchscale");
		ACItems.omothol_flesh = new ItemOmotholFlesh(3, 0.3F, false);
		ACItems.necronomicon = new ItemNecronomicon("necronomicon", 0);
		ACItems.abyssal_wasteland_necronomicon = new ItemNecronomicon("necronomicon_cor", 1);
		ACItems.dreadlands_necronomicon = new ItemNecronomicon("necronomicon_dre", 2);
		ACItems.omothol_necronomicon = new ItemNecronomicon("necronomicon_omt", 3);
		ACItems.abyssalnomicon = new ItemNecronomicon("abyssalnomicon", 4);
		ACItems.small_crystal_bag = new ItemCrystalBag("crystalbag_small");
		ACItems.medium_crystal_bag = new ItemCrystalBag("crystalbag_medium");
		ACItems.large_crystal_bag = new ItemCrystalBag("crystalbag_large");
		ACItems.huge_crystal_bag = new ItemCrystalBag("crystalbag_huge");
		ACItems.ingot_nugget = new ItemMetadata("nugget", true, "abyssalnite", "coralium", "dreadium", "ethaxium");
		ACItems.essence = new ItemMetadata("essence", true, "abyssalwasteland", "dreadlands", "omothol");
		ACItems.skin = new ItemMetadata("skin", true, "abyssalwasteland", "dreadlands", "omothol");
		ACItems.essence_of_the_gatekeeper = new ItemGatekeeperEssence();

		//Coins
		ACItems.coin = new ItemCoin("coin");
		ACItems.cthulhu_engraved_coin = new ItemCoin("cthulhucoin");
		ACItems.elder_engraved_coin = new ItemCoin("eldercoin");
		ACItems.jzahar_engraved_coin = new ItemCoin("jzaharcoin");
		ACItems.blank_engraving = new ItemEngraving("blank", 50).setCreativeTab(tabCoins);
		ACItems.cthulhu_engraving = new ItemEngraving("cthulhu", 10).setCreativeTab(tabCoins);
		ACItems.elder_engraving = new ItemEngraving("elder", 10).setCreativeTab(tabCoins);
		ACItems.jzahar_engraving = new ItemEngraving("jzahar", 10).setCreativeTab(tabCoins);
		ACItems.hastur_engraved_coin = new ItemCoin("hasturcoin");
		ACItems.azathoth_engraved_coin = new ItemCoin("azathothcoin");
		ACItems.nyarlathotep_engraved_coin = new ItemCoin("nyarlathotepcoin");
		ACItems.yog_sothoth_engraved_coin = new ItemCoin("yogsothothcoin");
		ACItems.shub_niggurath_engraved_coin = new ItemCoin("shubniggurathcoin");
		ACItems.hastur_engraving = new ItemEngraving("hastur", 10).setCreativeTab(tabCoins);
		ACItems.azathoth_engraving = new ItemEngraving("azathoth", 10).setCreativeTab(tabCoins);
		ACItems.nyarlathotep_engraving = new ItemEngraving("nyarlathotep", 10).setCreativeTab(tabCoins);
		ACItems.yog_sothoth_engraving = new ItemEngraving("yogsothoth", 10).setCreativeTab(tabCoins);
		ACItems.shub_niggurath_engraving = new ItemEngraving("shubniggurath", 10).setCreativeTab(tabCoins);

		//Charms
		ACItems.ritual_charm = new ItemCharm("ritualcharm", true, null);
		ACItems.cthulhu_charm = new ItemCharm("cthulhucharm", false, DeityType.CTHULHU);
		ACItems.hastur_charm = new ItemCharm("hasturcharm", false, DeityType.HASTUR);
		ACItems.jzahar_charm = new ItemCharm("jzaharcharm", false, DeityType.JZAHAR);
		ACItems.azathoth_charm = new ItemCharm("azathothcharm", false, DeityType.AZATHOTH);
		ACItems.nyarlathotep_charm = new ItemCharm("nyarlathotepcharm", false, DeityType.NYARLATHOTEP);
		ACItems.yog_sothoth_charm = new ItemCharm("yogsothothcharm", false, DeityType.YOGSOTHOTH);
		ACItems.shub_niggurath_charm = new ItemCharm("shubniggurathcharm", false, DeityType.SHUBNIGGURATH);

		//Ethaxium
		ACItems.ethaxium_brick = new ItemACBasic("ethbrick");
		ACItems.ethaxium_ingot = new ItemACBasic("ethaxiumingot");

		//anti-items
		ACItems.liquid_antimatter_bucket = new ItemAntiBucket(ACBlocks.liquid_antimatter);
		ACItems.anti_beef = new ItemAntiFood("antibeef");
		ACItems.anti_chicken = new ItemAntiFood("antichicken");
		ACItems.anti_pork = new ItemAntiFood("antipork");
		ACItems.rotten_anti_flesh = new ItemAntiFood("antiflesh");
		ACItems.anti_bone = new ItemACBasic("antibone");
		ACItems.anti_spider_eye = new ItemAntiFood("antispidereye", false);
		ACItems.anti_plagued_flesh = new ItemCorflesh(0, 0, false, "anticorflesh");
		ACItems.anti_plagued_flesh_on_a_bone = new ItemCorbone(0, 0, false, "anticorbone");

		//crystals
		ACItems.crystal = new ItemCrystal("crystal");
		ACItems.crystal_shard = new ItemCrystal("crystalshard");

		//Shadow items
		ACItems.shadow_fragment = new ItemACBasic("shadowfragment");
		ACItems.shadow_shard = new ItemACBasic("shadowshard");
		ACItems.shadow_gem = new ItemACBasic("shadowgem");
		ACItems.shard_of_oblivion = new ItemACBasic("oblivionshard");
		shadowPlate = new ItemACBasic("shadowplate");

		//Dread items
		ACItems.dreaded_shard_of_abyssalnite = new ItemACBasic("dreadshard");
		ACItems.dreaded_chunk_of_abyssalnite = new ItemACBasic("dreadchunk");
		ACItems.dreadium_ingot = new ItemACBasic("dreadiumingot");
		ACItems.dread_fragment = new ItemACBasic("dreadfragment");
		ACItems.dread_cloth = new ItemACBasic("dreadcloth");
		ACItems.dreadium_plate = new ItemACBasic("dreadplate");
		ACItems.dreadium_katana_blade = new ItemACBasic("dreadblade");
		ACItems.dread_plagued_gateway_key = new ItemACBasic("dreadkey");

		//Abyssalnite items
		ACItems.chunk_of_abyssalnite = new ItemACBasic("abychunk");
		ACItems.abyssalnite_ingot = new ItemACBasic("abyingot");

		//Coralium items
		ACItems.coralium_gem_cluster_2 = new ItemCoraliumcluster("ccluster2", "2");
		ACItems.coralium_gem_cluster_3 = new ItemCoraliumcluster("ccluster3", "3");
		ACItems.coralium_gem_cluster_4 = new ItemCoraliumcluster("ccluster4", "4");
		ACItems.coralium_gem_cluster_5 = new ItemCoraliumcluster("ccluster5", "5");
		ACItems.coralium_gem_cluster_6 = new ItemCoraliumcluster("ccluster6", "6");
		ACItems.coralium_gem_cluster_7 = new ItemCoraliumcluster("ccluster7", "7");
		ACItems.coralium_gem_cluster_8 = new ItemCoraliumcluster("ccluster8", "8");
		ACItems.coralium_gem_cluster_9 = new ItemCoraliumcluster("ccluster9", "9");
		ACItems.coralium_pearl = new ItemACBasic("cpearl");
		ACItems.chunk_of_coralium = new ItemACBasic("cchunk");
		ACItems.refined_coralium_ingot = new ItemACBasic("cingot");
		ACItems.coralium_plate = new ItemACBasic("platec");
		ACItems.coralium_gem = new ItemACBasic("coralium");
		ACItems.transmutation_gem = new ItemCorb();
		ACItems.coralium_plagued_flesh = new ItemCorflesh(2, 0.1F, false, "corflesh");
		ACItems.coralium_plagued_flesh_on_a_bone = new ItemCorbone(2, 0.1F, false, "corbone");
		ACItems.coralium_longbow = new ItemCoraliumBow(20.0F, 0, 8, 16);

		//Tools
		ACItems.darkstone_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.darkstoneTool, "dpick", 1);
		ACItems.darkstone_axe = new ItemACAxe(AbyssalCraftAPI.darkstoneTool, "daxe", 1);
		ACItems.darkstone_shovel = new ItemACShovel(AbyssalCraftAPI.darkstoneTool, "dshovel", 1);
		ACItems.darkstone_sword = new ItemACSword(AbyssalCraftAPI.darkstoneTool, "dsword");
		ACItems.darkstone_hoe = new ItemACHoe(AbyssalCraftAPI.darkstoneTool, "dhoe");
		ACItems.abyssalnite_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.abyssalniteTool, "apick", 4, TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_axe = new ItemACAxe(AbyssalCraftAPI.abyssalniteTool, "aaxe", 4, TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_shovel = new ItemACShovel(AbyssalCraftAPI.abyssalniteTool, "ashovel", 4, TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_sword = new ItemACSword(AbyssalCraftAPI.abyssalniteTool, "asword", TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_hoe = new ItemACHoe(AbyssalCraftAPI.abyssalniteTool, "ahoe", TextFormatting.DARK_AQUA);
		ACItems.refined_coralium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.refinedCoraliumTool, "corpick", 5, TextFormatting.AQUA);
		ACItems.refined_coralium_axe = new ItemACAxe(AbyssalCraftAPI.refinedCoraliumTool, "coraxe", 5, TextFormatting.AQUA);
		ACItems.refined_coralium_shovel = new ItemACShovel(AbyssalCraftAPI.refinedCoraliumTool, "corshovel", 5, TextFormatting.AQUA);
		ACItems.refined_coralium_sword = new ItemACSword(AbyssalCraftAPI.refinedCoraliumTool, "corsword", TextFormatting.AQUA);
		ACItems.refined_coralium_hoe = new ItemACHoe(AbyssalCraftAPI.refinedCoraliumTool, "corhoe", TextFormatting.AQUA);
		ACItems.dreadium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.dreadiumTool, "dreadiumpickaxe", 6, TextFormatting.DARK_RED);
		ACItems.dreadium_axe = new ItemACAxe(AbyssalCraftAPI.dreadiumTool, "dreadiumaxe", 6, TextFormatting.DARK_RED);
		ACItems.dreadium_shovel = new ItemACShovel(AbyssalCraftAPI.dreadiumTool, "dreadiumshovel", 6, TextFormatting.DARK_RED);
		ACItems.dreadium_sword = new ItemACSword(AbyssalCraftAPI.dreadiumTool, "dreadiumsword", TextFormatting.DARK_RED);
		ACItems.dreadium_hoe = new ItemACHoe(AbyssalCraftAPI.dreadiumTool, "dreadiumhoe", TextFormatting.DARK_RED);
		ACItems.dreadium_katana_hilt = new ItemDreadiumKatana("dreadhilt", 5.0F, 200);
		ACItems.dreadium_katana = new ItemDreadiumKatana("dreadkatana", 20.0F, 2000);
		ACItems.sacthoths_soul_harvesting_blade = new ItemSoulReaper("soulreaper");
		ACItems.ethaxium_pickaxe = new ItemEthaxiumPickaxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumpickaxe");
		ACItems.ethaxium_axe = new ItemACAxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumaxe", 8, TextFormatting.AQUA);
		ACItems.ethaxium_shovel = new ItemACShovel(AbyssalCraftAPI.ethaxiumTool, "ethaxiumshovel", 8, TextFormatting.AQUA);
		ACItems.ethaxium_sword = new ItemACSword(AbyssalCraftAPI.ethaxiumTool, "ethaxiumsword", TextFormatting.AQUA);
		ACItems.ethaxium_hoe = new ItemACHoe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumhoe", TextFormatting.AQUA);
		ACItems.staff_of_rending = new ItemDrainStaff();

		//Armor
		ACItems.abyssalnite_helmet = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.HEAD, "ahelmet");
		ACItems.abyssalnite_chestplate = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.CHEST, "aplate");
		ACItems.abyssalnite_leggings = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.LEGS, "alegs");
		ACItems.abyssalnite_boots = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.FEET, "aboots");
		ACItems.dreaded_abyssalnite_helmet = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.HEAD, "dhelmet");
		ACItems.dreaded_abyssalnite_chestplate = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.CHEST, "dplate");
		ACItems.dreaded_abyssalnite_leggings = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.LEGS, "dlegs");
		ACItems.dreaded_abyssalnite_boots = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.FEET, "dboots");
		ACItems.refined_coralium_helmet = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmet");
		ACItems.refined_coralium_chestplate = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplate");
		ACItems.refined_coralium_leggings = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegs");
		ACItems.refined_coralium_boots = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corboots");
		ACItems.plated_coralium_helmet = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmetp");
		ACItems.plated_coralium_chestplate = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplatep");
		ACItems.plated_coralium_leggings = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegsp");
		ACItems.plated_coralium_boots = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corbootsp");
		ACItems.depths_helmet = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.HEAD, "depthshelmet");
		ACItems.depths_chestplate = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.CHEST, "depthsplate");
		ACItems.depths_leggings = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.LEGS, "depthslegs");
		ACItems.depths_boots = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.FEET, "depthsboots");
		ACItems.dreadium_helmet = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.HEAD, "dreadiumhelmet");
		ACItems.dreadium_chestplate = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.CHEST, "dreadiumplate");
		ACItems.dreadium_leggings = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.LEGS, "dreadiumlegs");
		ACItems.dreadium_boots = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.FEET, "dreadiumboots");
		ACItems.dreadium_samurai_helmet = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.HEAD, "dreadiumsamuraihelmet");
		ACItems.dreadium_samurai_chestplate = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.CHEST, "dreadiumsamuraiplate");
		ACItems.dreadium_samurai_leggings = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.LEGS, "dreadiumsamurailegs");
		ACItems.dreadium_samurai_boots = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.FEET, "dreadiumsamuraiboots");
		ACItems.ethaxium_helmet = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.HEAD, "ethaxiumhelmet");
		ACItems.ethaxium_chestplate = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.CHEST, "ethaxiumplate");
		ACItems.ethaxium_leggings = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.LEGS, "ethaxiumlegs");
		ACItems.ethaxium_boots = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.FEET, "ethaxiumboots");

		//Upgrade kits
		ACItems.cobblestone_upgrade_kit = new ItemUpgradeKit("Wood", "Cobblestone").setUnlocalizedName("cobbleu").setCreativeTab(tabItems);
		ACItems.iron_upgrade_kit = new ItemUpgradeKit("Cobblestone", "Iron").setUnlocalizedName("ironu").setCreativeTab(tabItems);
		ACItems.gold_upgrade_kit = new ItemUpgradeKit("Iron", "Gold").setUnlocalizedName("goldu").setCreativeTab(tabItems);
		ACItems.diamond_upgrade_kit = new ItemUpgradeKit("Gold", "Diamond").setUnlocalizedName("diamondu").setCreativeTab(tabItems);
		ACItems.abyssalnite_upgrade_kit = new ItemUpgradeKit("Diamond", "Abyssalnite").setUnlocalizedName("abyssalniteu").setCreativeTab(tabItems);
		ACItems.coralium_upgrade_kit = new ItemUpgradeKit("Abyssalnite", "Coralium").setUnlocalizedName("coraliumu").setCreativeTab(tabItems);
		ACItems.dreadium_upgrade_kit = new ItemUpgradeKit("Coralium", "Dreadium").setUnlocalizedName("dreadiumu").setCreativeTab(tabItems);
		ACItems.ethaxium_upgrade_kit = new ItemUpgradeKit("Dreadium", "Ethaxium").setUnlocalizedName("ethaxiumu").setCreativeTab(tabItems);

		//Foodstuffs
		ACItems.iron_plate = new ItemACBasic("ironp");
		ACItems.mre = new ItemPlatefood(20, 1F, false, "mre");
		ACItems.chicken_on_a_plate = new ItemPlatefood(12, 1.2F, false, "chickenp");
		ACItems.pork_on_a_plate = new ItemPlatefood(16, 1.6F, false, "porkp");
		ACItems.beef_on_a_plate = new ItemPlatefood(6, 0.6F, false, "beefp");
		ACItems.fish_on_a_plate = new ItemPlatefood(10, 1.2F, false, "fishp");
		ACItems.dirty_plate = new ItemACBasic("dirtyplate");
		ACItems.fried_egg = new ItemFood(5, 0.6F, false).setCreativeTab(tabFood).setUnlocalizedName("friedegg");
		ACItems.fried_egg_on_a_plate = new ItemPlatefood(10, 1.2F, false, "eggp");
		ACItems.washcloth = new ItemWashCloth();

		GameRegistry.registerTileEntity(TileEntityCrate.class, "tileEntityCrate");
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
		GameRegistry.registerTileEntity(TileEntityJzaharSpawner.class, "tileEntityJzaharSpawner");
		GameRegistry.registerTileEntity(TileEntityGatekeeperMinionSpawner.class, "tileEntityGatekeeperMinionSpawner");

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

		//TODO: add potion brewing recipes when possible

		//		Corflesh.setPotionEffect("+0+1-2+3&4+4+13");
		//		Corbone.setPotionEffect("+0+1-2+3&4+4+13");

		//		dreadfragment.setPotionEffect("0+1+2+3+13&4-4");

		//		antiFlesh.setPotionEffect("0+1+2-3+13&4-4");
		//		antiCorflesh.setPotionEffect("0+1+2-3+13&4-4");
		//		antiCorbone.setPotionEffect("0+1+2-3+13&4-4");
		//		sulfur.setPotionEffect(PotionHelper.spiderEyeEffect);
		//		crystalOxygen.setPotionEffect(PotionHelper.field_151423_m);
		//		crystalHydrogen.setPotionEffect("-0-1+2+3&4-4+13");
		//		crystalNitrogen.setPotionEffect("-0+1-2+3&4-4+13");

		AbyssalCraftAPI.coralium_enchantment = new EnchantmentWeaponInfusion("coralium");
		AbyssalCraftAPI.dread_enchantment = new EnchantmentWeaponInfusion("dread");
		AbyssalCraftAPI.light_pierce = new EnchantmentLightPierce();
		AbyssalCraftAPI.iron_wall = new EnchantmentIronWall();

		registerEnchantment(new ResourceLocation("abyssalcraft", "coralium"), AbyssalCraftAPI.coralium_enchantment);
		registerEnchantment(new ResourceLocation("abyssalcraft", "dread"), AbyssalCraftAPI.dread_enchantment);
		registerEnchantment(new ResourceLocation("abyssalcraft", "light_pierce"), AbyssalCraftAPI.light_pierce);
		registerEnchantment(new ResourceLocation("abyssalcraft", "iron_wall"), AbyssalCraftAPI.iron_wall);

		//Block Register
		registerBlock(ACBlocks.darkstone, "darkstone");
		registerBlock(ACBlocks.darkstone_cobblestone, "darkstone_cobble");
		registerBlock(ACBlocks.darkstone_brick, "darkstone_brick");
		registerBlock(ACBlocks.glowing_darkstone_bricks, "dsglow");
		registerBlock(ACBlocks.darkstone_brick_slab, new ItemDarkbrickSlab(ACBlocks.darkstone_brick_slab), "darkbrickslab1");
		registerBlock(Darkbrickslab2, new ItemDarkbrickSlab(Darkbrickslab2), "darkbrickslab2");
		registerBlock(ACBlocks.darkstone_cobblestone_slab, new ItemDarkcobbleSlab(ACBlocks.darkstone_cobblestone_slab), "darkcobbleslab1");
		registerBlock(Darkcobbleslab2, new ItemDarkcobbleSlab(Darkcobbleslab2), "darkcobbleslab2");
		registerBlock(ACBlocks.darklands_grass, "darkgrass");
		registerBlock(ACBlocks.darkstone_brick_stairs, "dbstairs");
		registerBlock(ACBlocks.darkstone_cobblestone_stairs, "dcstairs");
		registerBlock(ACBlocks.darklands_oak_leaves, "dltleaves");
		registerBlock(ACBlocks.darklands_oak_wood, "dltlog");
		registerBlock(ACBlocks.darklands_oak_sapling, "dltsapling");
		registerBlock(ACBlocks.abyssal_stone, new ItemBlockColorName(ACBlocks.abyssal_stone), "abystone");
		registerBlock(ACBlocks.abyssal_stone_brick, new ItemBlockColorName(ACBlocks.abyssal_stone_brick), "abybrick");
		registerBlock(ACBlocks.abyssal_stone_brick_slab, new ItemAbySlab(ACBlocks.abyssal_stone_brick_slab), "abyslab1");
		registerBlock(abyslab2, new ItemAbySlab(abyslab2), "abyslab2");
		registerBlock(ACBlocks.abyssal_stone_brick_stairs, new ItemBlockColorName(ACBlocks.abyssal_stone_brick_stairs), "abystairs");
		registerBlock(ACBlocks.coralium_ore, "coraliumore");
		registerBlock(ACBlocks.abyssalnite_ore, "abyore");
		registerBlock(ACBlocks.abyssal_stone_brick_fence, new ItemBlockColorName(ACBlocks.abyssal_stone_brick_fence), "abyfence");
		registerBlock(ACBlocks.darkstone_cobblestone_wall, "dscwall");
		registerBlock(ACBlocks.oblivion_deathbomb, new ItemODB(ACBlocks.oblivion_deathbomb), "odb");
		registerBlock(ACBlocks.block_of_abyssalnite, new ItemBlockColorName(ACBlocks.block_of_abyssalnite), "abyblock");
		registerBlock(ACBlocks.coralium_infused_stone, "coraliumstone");
		registerBlock(ACBlocks.odb_core, new ItemBlockColorName(ACBlocks.odb_core), "odbcore");
		registerBlock(ACBlocks.wooden_crate, "crate");
		registerBlock(ACBlocks.abyssal_gateway, "abyportal");
		registerBlock(ACBlocks.darkstone_slab, new ItemDarkstoneSlab(ACBlocks.darkstone_slab), "darkstoneslab1");
		registerBlock(Darkstoneslab2, new ItemDarkstoneSlab(Darkstoneslab2), "darkstoneslab2");
		registerBlock(ACBlocks.coralium_fire, "coraliumfire");
		registerBlock(ACBlocks.darkstone_button, "dsbutton");
		registerBlock(ACBlocks.darkstone_pressure_plate, "dspplate");
		registerBlock(ACBlocks.darklands_oak_planks, "dltplank");
		registerBlock(ACBlocks.darklands_oak_button, "dltbutton");
		registerBlock(ACBlocks.darklands_oak_pressure_plate, "dltpplate");
		registerBlock(ACBlocks.darklands_oak_stairs, "dltstairs");
		registerBlock(ACBlocks.darklands_oak_slab, new ItemDLTSlab(ACBlocks.darklands_oak_slab), "dltslab1");
		registerBlock(DLTslab2, new ItemDLTSlab(DLTslab2), "dltslab2");
		registerBlock(ACBlocks.liquid_coralium, "cwater");
		registerBlock(ACBlocks.block_of_coralium, new ItemBlockColorName(ACBlocks.block_of_coralium), "corblock");
		registerBlock(ACBlocks.dreadlands_infused_powerstone, "psdl");
		registerBlock(ACBlocks.abyssal_coralium_ore, "abycorore");
		registerBlock(Altar, "altar");
		registerBlock(ACBlocks.abyssal_stone_button, new ItemBlockColorName(ACBlocks.abyssal_stone_button), "abybutton");
		registerBlock(ACBlocks.abyssal_stone_pressure_plate, new ItemBlockColorName(ACBlocks.abyssal_stone_pressure_plate), "abypplate");
		registerBlock(ACBlocks.darkstone_brick_fence, "dsbfence");
		registerBlock(ACBlocks.darklands_oak_fence, "dltfence");
		registerBlock(ACBlocks.dreadstone, "dreadstone");
		registerBlock(ACBlocks.abyssalnite_stone, "abydreadstone");
		registerBlock(ACBlocks.dreadlands_abyssalnite_ore, "abydreadore");
		registerBlock(ACBlocks.dreaded_abyssalnite_ore, "dreadore");
		registerBlock(ACBlocks.dreadstone_brick, "dreadbrick");
		registerBlock(ACBlocks.abyssalnite_stone_brick, "abydreadbrick");
		registerBlock(ACBlocks.dreadlands_grass, "dreadgrass");
		registerBlock(ACBlocks.dreadlands_log, "dreadlog");
		registerBlock(ACBlocks.dreadlands_leaves, "dreadleaves");
		registerBlock(ACBlocks.dreadlands_sapling, "dreadsapling");
		registerBlock(ACBlocks.dreadlands_planks, "dreadplanks");
		registerBlock(ACBlocks.dreaded_gateway, "dreadportal");
		registerBlock(ACBlocks.dreaded_fire, "dreadfire");
		registerBlock(ACBlocks.depths_ghoul_head, "dghead");
		registerBlock(ACBlocks.pete_head, "phead");
		registerBlock(ACBlocks.mr_wilson_head, "whead");
		registerBlock(ACBlocks.dr_orange_head, "ohead");
		registerBlock(ACBlocks.dreadstone_brick_stairs, "dreadbrickstairs");
		registerBlock(ACBlocks.dreadstone_brick_fence, "dreadbrickfence");
		registerBlock(ACBlocks.dreadstone_brick_slab, new ItemDreadbrickSlab(ACBlocks.dreadstone_brick_slab), "dreadbrickslab1");
		registerBlock(dreadbrickslab2, new ItemDreadbrickSlab(dreadbrickslab2), "dreadbrickslab2");
		registerBlock(ACBlocks.abyssalnite_stone_brick_stairs, "abydreadbrickstairs");
		registerBlock(ACBlocks.abyssalnite_stone_brick_fence, "abydreadbrickfence");
		registerBlock(ACBlocks.abyssalnite_stone_brick_slab, new ItemAbyDreadbrickSlab(ACBlocks.abyssalnite_stone_brick_slab), "abydreadbrickslab1");
		registerBlock(abydreadbrickslab2, new ItemAbyDreadbrickSlab(abydreadbrickslab2), "abydreadbrickslab2");
		registerBlock(ACBlocks.liquid_antimatter, "antiwater");
		registerBlock(ACBlocks.coralium_stone, "cstone");
		registerBlock(ACBlocks.coralium_stone_brick, "cstonebrick");
		registerBlock(ACBlocks.coralium_stone_brick_fence, "cstonebrickfence");
		registerBlock(ACBlocks.coralium_stone_brick_slab, new ItemCstonebrickSlab(ACBlocks.coralium_stone_brick_slab), "cstonebrickslab1");
		registerBlock(cstonebrickslab2, new ItemCstonebrickSlab(cstonebrickslab2), "cstonebrickslab2");
		registerBlock(ACBlocks.coralium_stone_brick_stairs, "cstonebrickstairs");
		registerBlock(ACBlocks.coralium_stone_button, "cstonebutton");
		registerBlock(ACBlocks.coralium_stone_pressure_plate, "cstonepplate");
		registerBlock(ACBlocks.chagaroth_altar_top, "dreadaltartop");
		registerBlock(ACBlocks.chagaroth_altar_bottom, "dreadaltarbottom");
		registerBlock(ACBlocks.crystallizer_idle, "crystallizer");
		registerBlock(ACBlocks.crystallizer_active, "crystallizer_on");
		registerBlock(ACBlocks.block_of_dreadium, new ItemBlockColorName(ACBlocks.block_of_dreadium), "dreadiumblock");
		registerBlock(ACBlocks.transmutator_idle, "transmutator");
		registerBlock(ACBlocks.transmutator_active, "transmutator_on");
		registerBlock(ACBlocks.dreadguard_spawner, "dreadguardspawner");
		registerBlock(ACBlocks.chagaroth_spawner, "chagarothspawner");
		registerBlock(ACBlocks.dreadlands_wood_fence, "drtfence");
		registerBlock(ACBlocks.nitre_ore, "nitreore");
		registerBlock(ACBlocks.abyssal_iron_ore, "abyiroore");
		registerBlock(ACBlocks.abyssal_gold_ore, "abygolore");
		registerBlock(ACBlocks.abyssal_diamond_ore, "abydiaore");
		registerBlock(ACBlocks.abyssal_nitre_ore, "abynitore");
		registerBlock(ACBlocks.abyssal_tin_ore, "abytinore");
		registerBlock(ACBlocks.abyssal_copper_ore, "abycopore");
		registerBlock(ACBlocks.pearlescent_coralium_ore, "abypcorore");
		registerBlock(ACBlocks.liquified_coralium_ore, "abylcorore");
		registerBlock(ACBlocks.solid_lava, "solidlava");
		registerBlock(ACBlocks.ethaxium, new ItemBlockColorName(ACBlocks.ethaxium), "ethaxium");
		registerBlock(ACBlocks.ethaxium_brick, new ItemMetadataBlock(ACBlocks.ethaxium_brick), "ethaxiumbrick");
		registerBlock(ACBlocks.ethaxium_pillar, new ItemBlockColorName(ACBlocks.ethaxium_pillar), "ethaxiumpillar");
		registerBlock(ACBlocks.ethaxium_brick_stairs, new ItemBlockColorName(ACBlocks.ethaxium_brick_stairs), "ethaxiumbrickstairs");
		registerBlock(ACBlocks.ethaxium_brick_slab, new ItemEthaxiumSlab(ACBlocks.ethaxium_brick_slab), "ethaxiumbrickslab1");
		registerBlock(ethaxiumslab2, new ItemEthaxiumSlab(ethaxiumslab2), "ethaxiumbrickslab2");
		registerBlock(ACBlocks.ethaxium_brick_fence, new ItemBlockColorName(ACBlocks.ethaxium_brick_fence), "ethaxiumfence");
		registerBlock(ACBlocks.block_of_ethaxium, new ItemBlockColorName(ACBlocks.block_of_ethaxium), "ethaxiumblock");
		registerBlock(ACBlocks.omothol_stone, "omotholstone");
		registerBlock(ACBlocks.omothol_gateway, "omotholportal");
		registerBlock(ACBlocks.omothol_fire, "omotholfire");
		registerBlock(ACBlocks.engraver, "engraver");
		registerBlock(house, "engraver_on");
		registerBlock(ACBlocks.materializer, "materializer");
		registerBlock(ACBlocks.dark_ethaxium_brick, new ItemMetadataBlock(ACBlocks.dark_ethaxium_brick), "darkethaxiumbrick");
		registerBlock(ACBlocks.dark_ethaxium_pillar, new ItemBlockColorName(ACBlocks.dark_ethaxium_pillar), "darkethaxiumpillar");
		registerBlock(ACBlocks.dark_ethaxium_brick_stairs, new ItemBlockColorName(ACBlocks.dark_ethaxium_brick_stairs), "darkethaxiumbrickstairs");
		registerBlock(ACBlocks.dark_ethaxium_brick_slab, new ItemDarkEthaxiumSlab(ACBlocks.dark_ethaxium_brick_slab), "darkethaxiumbrickslab1");
		registerBlock(darkethaxiumslab2, new ItemDarkEthaxiumSlab(darkethaxiumslab2), "darkethaxiumbrickslab2");
		registerBlock(ACBlocks.dark_ethaxium_brick_fence, new ItemBlockColorName(ACBlocks.dark_ethaxium_brick_fence), "darkethaxiumbrickfence");
		registerBlock(ACBlocks.ritual_altar, new ItemRitualBlock(ACBlocks.ritual_altar), "ritualaltar");
		registerBlock(ACBlocks.ritual_pedestal, new ItemRitualBlock(ACBlocks.ritual_pedestal), "ritualpedestal");
		registerBlock(ACBlocks.shoggoth_ooze, "shoggothblock");
		registerBlock(ACBlocks.cthulhu_statue, "cthulhustatue");
		registerBlock(ACBlocks.hastur_statue, "hasturstatue");
		registerBlock(ACBlocks.jzahar_statue, "jzaharstatue");
		registerBlock(ACBlocks.azathoth_statue, "azathothstatue");
		registerBlock(ACBlocks.nyarlathotep_statue, "nyarlathotepstatue");
		registerBlock(ACBlocks.yog_sothoth_statue, "yogsothothstatue");
		registerBlock(ACBlocks.shub_niggurath_statue, "shubniggurathstatue");
		registerBlock(ACBlocks.monolith_stone, "monolithstone");
		registerBlock(ACBlocks.shoggoth_biomass, "shoggothbiomass");
		registerBlock(ACBlocks.energy_pedestal, "energypedestal");
		registerBlock(ACBlocks.monolith_pillar, "monolithpillar");
		registerBlock(ACBlocks.sacrificial_altar, "sacrificialaltar");
		registerBlock(ACBlocks.tiered_energy_pedestal, new ItemMetadataBlock(ACBlocks.tiered_energy_pedestal), "tieredenergypedestal");
		registerBlock(ACBlocks.tiered_sacrificial_altar, new ItemMetadataBlock(ACBlocks.tiered_sacrificial_altar), "tieredsacrificialaltar");
		registerBlock(ACBlocks.jzahar_spawner, "jzaharspawner");
		registerBlock(ACBlocks.minion_of_the_gatekeeper_spawner, "gatekeeperminionspawner");
		registerBlock(ACBlocks.mimic_fire, "fire");

		//Item Register
		registerItem(devsword, "devsword");
		registerItem(ACItems.oblivion_catalyst, "oc");
		registerItem(ACItems.gateway_key, "gatewaykey");
		registerItem(ACItems.staff_of_the_gatekeeper, "staff");
		registerItem(ACItems.liquid_coralium_bucket, "cbucket");
		registerItem(ACItems.powerstone_tracker, "powerstonetracker");
		registerItem(ACItems.eye_of_the_abyss, "eoa");
		registerItem(ACItems.dreaded_gateway_key, "gatewaykeydl");
		registerItem(ACItems.dreaded_shard_of_abyssalnite, "dreadshard");
		registerItem(ACItems.dreaded_chunk_of_abyssalnite, "dreadchunk");
		registerItem(ACItems.chunk_of_abyssalnite, "abychunk");
		registerItem(ACItems.abyssalnite_ingot, "abyingot");
		registerItem(ACItems.coralium_gem, "coralium");
		registerItem(ACItems.coralium_gem_cluster_2, "ccluster2");
		registerItem(ACItems.coralium_gem_cluster_3, "ccluster3");
		registerItem(ACItems.coralium_gem_cluster_4, "ccluster4");
		registerItem(ACItems.coralium_gem_cluster_5, "ccluster5");
		registerItem(ACItems.coralium_gem_cluster_6, "ccluster6");
		registerItem(ACItems.coralium_gem_cluster_7, "ccluster7");
		registerItem(ACItems.coralium_gem_cluster_8, "ccluster8");
		registerItem(ACItems.coralium_gem_cluster_9, "ccluster9");
		registerItem(ACItems.coralium_pearl ,"cpearl");
		registerItem(ACItems.chunk_of_coralium, "cchunk");
		registerItem(ACItems.refined_coralium_ingot, "cingot");
		registerItem(ACItems.coralium_plate, "platec");
		registerItem(ACItems.transmutation_gem, "transmutationgem");
		registerItem(ACItems.coralium_plagued_flesh, "corflesh");
		registerItem(ACItems.coralium_plagued_flesh_on_a_bone, "corbone");
		registerItem(ACItems.darkstone_pickaxe, "dpick");
		registerItem(ACItems.darkstone_axe, "daxe");
		registerItem(ACItems.darkstone_shovel, "dshovel");
		registerItem(ACItems.darkstone_sword, "dsword");
		registerItem(ACItems.darkstone_hoe, "dhoe");
		registerItem(ACItems.abyssalnite_pickaxe, "apick");
		registerItem(ACItems.abyssalnite_axe, "aaxe");
		registerItem(ACItems.abyssalnite_shovel, "ashovel");
		registerItem(ACItems.abyssalnite_sword, "asword");
		registerItem(ACItems.abyssalnite_hoe, "ahoe");
		registerItem(ACItems.refined_coralium_pickaxe, "corpick");
		registerItem(ACItems.refined_coralium_axe, "coraxe");
		registerItem(ACItems.refined_coralium_shovel, "corshovel");
		registerItem(ACItems.refined_coralium_sword, "corsword");
		registerItem(ACItems.refined_coralium_hoe, "corhoe");
		registerItem(ACItems.abyssalnite_helmet, "ahelmet");
		registerItem(ACItems.abyssalnite_chestplate, "aplate");
		registerItem(ACItems.abyssalnite_leggings, "alegs");
		registerItem(ACItems.abyssalnite_boots, "aboots");
		registerItem(ACItems.dreaded_abyssalnite_helmet, "dhelmet");
		registerItem(ACItems.dreaded_abyssalnite_chestplate, "dplate");
		registerItem(ACItems.dreaded_abyssalnite_leggings, "dlegs");
		registerItem(ACItems.dreaded_abyssalnite_boots, "dboots");
		registerItem(ACItems.refined_coralium_helmet, "corhelmet");
		registerItem(ACItems.refined_coralium_chestplate, "corplate");
		registerItem(ACItems.refined_coralium_leggings, "corlegs");
		registerItem(ACItems.refined_coralium_boots, "corboots");
		registerItem(ACItems.plated_coralium_helmet, "corhelmetp");
		registerItem(ACItems.plated_coralium_chestplate, "corplatep");
		registerItem(ACItems.plated_coralium_leggings, "corlegsp");
		registerItem(ACItems.plated_coralium_boots, "corbootsp");
		registerItem(ACItems.depths_helmet, "depthshelmet");
		registerItem(ACItems.depths_chestplate, "depthsplate");
		registerItem(ACItems.depths_leggings, "depthslegs");
		registerItem(ACItems.depths_boots, "depthsboots");
		registerItem(ACItems.cobblestone_upgrade_kit, "cobbleu");
		registerItem(ACItems.iron_upgrade_kit, "ironu");
		registerItem(ACItems.gold_upgrade_kit, "goldu");
		registerItem(ACItems.diamond_upgrade_kit, "diamondu");
		registerItem(ACItems.abyssalnite_upgrade_kit, "abyssalniteu");
		registerItem(ACItems.coralium_upgrade_kit, "coraliumu");
		registerItem(ACItems.mre, "mre");
		registerItem(ACItems.iron_plate, "ironp");
		registerItem(ACItems.chicken_on_a_plate, "chickenp");
		registerItem(ACItems.pork_on_a_plate, "porkp");
		registerItem(ACItems.beef_on_a_plate, "beefp");
		registerItem(ACItems.fish_on_a_plate, "fishp");
		registerItem(ACItems.dirty_plate, "dirtyplate");
		registerItem(ACItems.fried_egg, "friedegg");
		registerItem(ACItems.fried_egg_on_a_plate, "eggp");
		registerItem(ACItems.washcloth, "cloth");
		registerItem(ACItems.shadow_fragment, "shadowfragment");
		registerItem(ACItems.shadow_shard, "shadowshard");
		registerItem(ACItems.shadow_gem, "shadowgem");
		registerItem(ACItems.shard_of_oblivion, "oblivionshard");
		registerItem(ACItems.coralium_longbow, "corbow");
		registerItem(ACItems.liquid_antimatter_bucket, "antibucket");
		registerItem(ACItems.coralium_brick, "cbrick");
		registerItem(ACItems.cudgel, "cudgel");
		registerItem(ACItems.dreadium_ingot, "dreadiumingot");
		registerItem(ACItems.dread_fragment, "dreadfragment");
		registerItem(ACItems.dreadium_helmet, "dreadiumhelmet");
		registerItem(ACItems.dreadium_chestplate, "dreadiumplate");
		registerItem(ACItems.dreadium_leggings, "dreadiumlegs");
		registerItem(ACItems.dreadium_boots, "dreadiumboots");
		registerItem(ACItems.dreadium_pickaxe, "dreadiumpickaxe");
		registerItem(ACItems.dreadium_axe, "dreadiumaxe");
		registerItem(ACItems.dreadium_shovel, "dreadiumshovel");
		registerItem(ACItems.dreadium_sword, "dreadiumsword");
		registerItem(ACItems.dreadium_hoe, "dreadiumhoe");
		registerItem(ACItems.dreadium_upgrade_kit, "dreadiumu");
		registerItem(ACItems.carbon_cluster, "carboncluster");
		registerItem(ACItems.dense_carbon_cluster, "densecarboncluster");
		registerItem(ACItems.methane, "methane");
		registerItem(ACItems.nitre, "nitre");
		registerItem(ACItems.sulfur, "sulfur");
		registerItem(ACItems.crystal, "crystal");
		registerItem(ACItems.crystal_shard, "crystalshard");
		registerItem(ACItems.dread_cloth, "dreadcloth");
		registerItem(ACItems.dreadium_plate, "dreadplate");
		registerItem(ACItems.dreadium_katana_blade, "dreadblade");
		registerItem(ACItems.dreadium_katana_hilt, "dreadhilt");
		registerItem(ACItems.dreadium_katana, "dreadkatana");
		registerItem(ACItems.dread_plagued_gateway_key, "dreadkey");
		registerItem(ACItems.rlyehian_gateway_key, "gatewaykeyjzh");
		registerItem(ACItems.dreadium_samurai_helmet, "dreadiumsamuraihelmet");
		registerItem(ACItems.dreadium_samurai_chestplate, "dreadiumsamuraiplate");
		registerItem(ACItems.dreadium_samurai_leggings, "dreadiumsamurailegs");
		registerItem(ACItems.dreadium_samurai_boots, "dreadiumsamuraiboots");
		registerItem(ACItems.tin_ingot, "tiningot");
		registerItem(ACItems.copper_ingot, "copperingot");
		registerItem(ACItems.anti_beef, "antibeef");
		registerItem(ACItems.anti_chicken, "antichicken");
		registerItem(ACItems.anti_pork, "antipork");
		registerItem(ACItems.rotten_anti_flesh, "antiflesh");
		registerItem(ACItems.anti_bone, "antibone");
		registerItem(ACItems.anti_spider_eye, "antispidereye");
		registerItem(ACItems.sacthoths_soul_harvesting_blade, "soulreaper");
		registerItem(ACItems.ethaxium_brick, "ethbrick");
		registerItem(ACItems.ethaxium_ingot, "ethaxiumingot");
		registerItem(ACItems.life_crystal, "lifecrystal");
		registerItem(ACItems.ethaxium_helmet, "ethaxiumhelmet");
		registerItem(ACItems.ethaxium_chestplate, "ethaxiumplate");
		registerItem(ACItems.ethaxium_leggings, "ethaxiumlegs");
		registerItem(ACItems.ethaxium_boots, "ethaxiumboots");
		registerItem(ACItems.ethaxium_pickaxe, "ethaxiumpickaxe");
		registerItem(ACItems.ethaxium_axe, "ethaxiumaxe");
		registerItem(ACItems.ethaxium_shovel, "ethaxiumshovel");
		registerItem(ACItems.ethaxium_sword, "ethaxiumsword");
		registerItem(ACItems.ethaxium_hoe, "ethaxiumhoe");
		registerItem(ACItems.ethaxium_upgrade_kit, "ethaxiumu");
		registerItem(ACItems.coin, "coin");
		registerItem(ACItems.cthulhu_engraved_coin, "cthulhucoin");
		registerItem(ACItems.elder_engraved_coin, "eldercoin");
		registerItem(ACItems.jzahar_engraved_coin, "jzaharcoin");
		registerItem(ACItems.blank_engraving, "engraving_blank");
		registerItem(ACItems.cthulhu_engraving, "engraving_cthulhu");
		registerItem(ACItems.elder_engraving, "engraving_elder");
		registerItem(ACItems.jzahar_engraving, "engraving_jzahar");
		registerItem(ACItems.eldritch_scale, "eldritchscale");
		registerItem(ACItems.omothol_flesh, "omotholflesh");
		registerItem(ACItems.anti_plagued_flesh, "anticorflesh");
		registerItem(ACItems.anti_plagued_flesh_on_a_bone, "anticorbone");
		registerItem(ACItems.necronomicon, "necronomicon");
		registerItem(ACItems.abyssal_wasteland_necronomicon, "necronomicon_cor");
		registerItem(ACItems.dreadlands_necronomicon, "necronomicon_dre");
		registerItem(ACItems.omothol_necronomicon, "necronomicon_omt");
		registerItem(ACItems.abyssalnomicon, "abyssalnomicon");
		registerItem(ACItems.small_crystal_bag, "crystalbag_small");
		registerItem(ACItems.medium_crystal_bag, "crystalbag_medium");
		registerItem(ACItems.large_crystal_bag, "crystalbag_large");
		registerItem(ACItems.huge_crystal_bag, "crystalbag_huge");
		registerItem(ACItems.shoggoth_flesh, "shoggothflesh");
		registerItem(ACItems.ingot_nugget, "ingotnugget");
		registerItem(ACItems.staff_of_rending, "drainstaff");
		registerItem(ACItems.essence, "essence");
		registerItem(ACItems.skin, "skin");
		registerItem(ACItems.ritual_charm, "charm");
		registerItem(ACItems.cthulhu_charm, "cthulhucharm");
		registerItem(ACItems.hastur_charm, "hasturcharm");
		registerItem(ACItems.jzahar_charm, "jzaharcharm");
		registerItem(ACItems.azathoth_charm, "azathothcharm");
		registerItem(ACItems.nyarlathotep_charm, "nyarlathotepcharm");
		registerItem(ACItems.yog_sothoth_charm, "yogsothothcharm");
		registerItem(ACItems.shub_niggurath_charm, "shubniggurathcharm");
		registerItem(ACItems.hastur_engraved_coin, "hasturcoin");
		registerItem(ACItems.azathoth_engraved_coin, "azathothcoin");
		registerItem(ACItems.nyarlathotep_engraved_coin, "nyarlathotepcoin");
		registerItem(ACItems.yog_sothoth_engraved_coin, "yogsothothcoin");
		registerItem(ACItems.shub_niggurath_engraved_coin, "shubniggurathcoin");
		registerItem(ACItems.hastur_engraving, "engraving_hastur");
		registerItem(ACItems.azathoth_engraving, "engraving_azathoth");
		registerItem(ACItems.nyarlathotep_engraving, "engraving_nyarlathotep");
		registerItem(ACItems.yog_sothoth_engraving, "engraving_yogsothoth");
		registerItem(ACItems.shub_niggurath_engraving, "engraving_shubniggurath");
		registerItem(ACItems.essence_of_the_gatekeeper, "gatekeeperessence");
		//		registerItem(shadowPlate, "shadowplate");

		AbyssalCraftAPI.setRepairItems();
		LIQUID_CORALIUM.setBlock(ACBlocks.liquid_coralium).setUnlocalizedName(ACBlocks.liquid_coralium.getUnlocalizedName());
		LIQUID_ANTIMATTER.setBlock(ACBlocks.liquid_antimatter).setUnlocalizedName(ACBlocks.liquid_antimatter.getUnlocalizedName());
		if(CFluid.getBlock() == null)
			CFluid.setBlock(ACBlocks.liquid_coralium);
		if(antifluid.getBlock() == null)
			antifluid.setBlock(ACBlocks.liquid_antimatter);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(CFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_coralium_bucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_coralium.getDefaultState(), ACItems.liquid_coralium_bucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(antifluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_antimatter_bucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_antimatter.getDefaultState(), ACItems.liquid_antimatter_bucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		//Biome
		if(dark1 == true){
			registerBiomeWithTypes(ACBiomes.darklands, "darklands", darkWeight1, BiomeType.WARM, Type.WASTELAND, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands, true);
		}
		if(dark2 == true){
			registerBiomeWithTypes(ACBiomes.darklands_forest, "darklands_forest", darkWeight2, BiomeType.WARM, Type.FOREST, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_forest, true);
		}
		if(dark3 == true){
			registerBiomeWithTypes(ACBiomes.darklands_plains, "darklands_plains", darkWeight3, BiomeType.WARM, Type.PLAINS, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_plains, true);
		}
		if(dark4 == true){
			registerBiomeWithTypes(ACBiomes.darklands_hills, "darklands_hills", darkWeight4, BiomeType.WARM, Type.HILLS, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_hills, true);
		}
		if(dark5 == true){
			registerBiomeWithTypes(ACBiomes.darklands_mountains, "darklands_mountains", darkWeight5, BiomeType.WARM, Type.MOUNTAIN, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_mountains, true);
			BiomeManager.addStrongholdBiome(ACBiomes.darklands_mountains);
		}
		if(coralium1 == true)
			registerBiomeWithTypes(ACBiomes.coralium_infested_swamp, "coralium_infested_swamp", coraliumWeight, BiomeType.WARM, Type.SWAMP);
		if(darkspawn1 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands);
		if(darkspawn2 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_forest);
		if(darkspawn3 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_plains);
		if(darkspawn4 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_hills);
		if(darkspawn5 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_mountains);
		if(coraliumspawn1 == true)
			BiomeManager.addSpawnBiome(ACBiomes.coralium_infested_swamp);

		GameRegistry.register(ACBiomes.abyssal_wastelands.setRegistryName(new ResourceLocation(modid, "abyssal_wastelands")));
		GameRegistry.register(ACBiomes.dreadlands.setRegistryName(new ResourceLocation(modid, "dreadlands")));
		GameRegistry.register(ACBiomes.purified_dreadlands.setRegistryName(new ResourceLocation(modid, "purified_dreadlands")));
		GameRegistry.register(ACBiomes.dreadlands_forest.setRegistryName(new ResourceLocation(modid, "dreadlands_forest")));
		GameRegistry.register(ACBiomes.dreadlands_mountains.setRegistryName(new ResourceLocation(modid, "dreadlands_mountains")));
		GameRegistry.register(ACBiomes.omothol.setRegistryName(new ResourceLocation(modid, "omothol")));
		GameRegistry.register(ACBiomes.dark_realm.setRegistryName(new ResourceLocation(modid, "dark_realm")));

		BiomeDictionary.registerBiomeType(ACBiomes.abyssal_wastelands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.purified_dreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands_mountains, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands_forest, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.omothol, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dark_realm, Type.DEAD);

		THE_ABYSSAL_WASTELAND = DimensionType.register("The Abyssal Wasteland", "_aw", configDimId1, WorldProviderAbyss.class, keepLoaded1);
		THE_DREADLANDS = DimensionType.register("The Dreadlands", "_dl", configDimId2, WorldProviderDreadlands.class, keepLoaded2);
		OMOTHOL = DimensionType.register("Omothol", "_omt", configDimId3, WorldProviderOmothol.class, keepLoaded3);
		THE_DARK_REALM = DimensionType.register("The Dark Realm", "_dl", configDimId4, WorldProviderDarkRealm.class, keepLoaded4);

		//Dimension
		DimensionManager.registerDimension(configDimId1, THE_ABYSSAL_WASTELAND);
		DimensionManager.registerDimension(configDimId2, THE_DREADLANDS);
		DimensionManager.registerDimension(configDimId3, OMOTHOL);
		DimensionManager.registerDimension(configDimId4, THE_DARK_REALM);

		dreadguard_ambient = registerSoundEvent("dreadguard.idle");
		dreadguard_hurt = registerSoundEvent("dreadguard.hit");
		dreadguard_death = registerSoundEvent("dreadguard.death");
		ghoul_normal_ambient = registerSoundEvent("ghoul.normal.idle");
		ghoul_normal_hurt = registerSoundEvent("ghoul.normal.hit");
		ghoul_death = registerSoundEvent("ghoul.death");
		ghoul_pete_hurt = registerSoundEvent("ghoul.pete.hit");
		ghoul_pete_ambient = registerSoundEvent("ghoul.pete.idle");
		golem_death = registerSoundEvent("golem.death");
		golem_hurt = registerSoundEvent("golem.hit");
		golem_ambient = registerSoundEvent("golem.idle");
		sacthoth_death = registerSoundEvent("sacthoth.death");
		shadow_death = registerSoundEvent("shadow.death");
		shadow_hurt = registerSoundEvent("shadow.hit");
		remnant_scream = registerSoundEvent("remnant.scream");
		shoggoth_ambient = registerSoundEvent("shoggoth.idle");
		shoggoth_hurt = registerSoundEvent("shoggoth.hit");
		shoggoth_death = registerSoundEvent("shoggoth.death");

		//Mobs
		registerEntityWithEgg(EntityDepthsGhoul.class, "depthsghoul", 25, 80, 3, true, 0x36A880, 0x012626);
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));
		EntityRegistry.removeSpawn(EntityDepthsGhoul.class, EnumCreatureType.MONSTER, new BiomeGenBase[]{ Biomes.mushroomIslandShore });

		registerEntityWithEgg(EntityEvilpig.class, "evilpig", 26, 80, 3, true, 15771042, 14377823);
		if(evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilpig.class, evilAnimalSpawnRate, 1, 3, evilAnimalCreatureType ? EnumCreatureType.MONSTER : EnumCreatureType.CREATURE, new BiomeGenBase[] {
				Biomes.taiga, Biomes.plains, Biomes.forest, Biomes.savanna,
				Biomes.beach, Biomes.extremeHills, Biomes.jungle, Biomes.savannaPlateau,
				Biomes.swampland, Biomes.icePlains, Biomes.birchForest,
				Biomes.birchForestHills, Biomes.roofedForest});

		registerEntityWithEgg(EntityAbyssalZombie.class , "abyssalzombie", 27, 80, 3, true, 0x36A880, 0x052824);
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));
		if(endAbyssalZombie)
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.END));
		EntityRegistry.removeSpawn(EntityAbyssalZombie.class, EnumCreatureType.MONSTER, new BiomeGenBase[]{ Biomes.mushroomIslandShore });

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
			Biomes.hell});

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
			ACBiomes.abyssal_wastelands, ACBiomes.dreadlands, ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains,
			ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm});
		EntityRegistry.removeSpawn(EntityLesserShoggoth.class, EnumCreatureType.MONSTER, new BiomeGenBase[]{ Biomes.mushroomIslandShore });

		registerEntityWithEgg(EntityEvilCow.class, "evilcow", 67, 80, 3, true, 4470310, 10592673);
		if(evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilCow.class, evilAnimalSpawnRate, 1, 3, evilAnimalCreatureType ? EnumCreatureType.MONSTER : EnumCreatureType.CREATURE, new BiomeGenBase[] {
				Biomes.taiga, Biomes.plains, Biomes.forest, Biomes.savanna,
				Biomes.beach, Biomes.extremeHills, Biomes.jungle, Biomes.savannaPlateau,
				Biomes.swampland, Biomes.icePlains, Biomes.birchForest,
				Biomes.birchForestHills, Biomes.roofedForest});

		registerEntityWithEgg(EntityEvilChicken.class, "evilchicken", 68, 80, 3, true, 10592673, 16711680);
		if(evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilChicken.class, evilAnimalSpawnRate, 1, 3, evilAnimalCreatureType ? EnumCreatureType.MONSTER : EnumCreatureType.CREATURE, new BiomeGenBase[] {
				Biomes.taiga, Biomes.plains, Biomes.forest, Biomes.savanna,
				Biomes.beach, Biomes.extremeHills, Biomes.jungle, Biomes.savannaPlateau,
				Biomes.swampland, Biomes.icePlains, Biomes.birchForest,
				Biomes.birchForestHills, Biomes.roofedForest});

		registerEntityWithEgg(EntityDemonCow.class, "demoncow", 69, 80, 3, true, 4470310, 10592673);
		EntityRegistry.addSpawn(EntityDemonCow.class, 30, 1, 3, EnumCreatureType.MONSTER, new BiomeGenBase[] {
			Biomes.hell});

		registerEntityWithEgg(EntityDemonChicken.class, "demonchicken", 70, 80, 3, true, 10592673, 16711680);
		EntityRegistry.addSpawn(EntityDemonChicken.class, 30, 1, 3, EnumCreatureType.MONSTER, new BiomeGenBase[] {
			Biomes.hell});

		//		registerEntityWithEgg(EntityShadowTitan.class, "shadowtitan", 71, 80, 3, true, 0, 0xFFFFFF);
		//
		//		registerEntityWithEgg(EntityOmotholWarden.class, "omotholwarden", 72, 80, 3, true, 0x133133, 0x342122);

		proxy.preInit();
		RitualUtil.addBlocks();
		addOreDictionaryStuff();
		addChestGenHooks(); //TODO: uncomment later
		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
		IntegrationHandler.preInit(event.getAsmData());
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

		ACLogger.info("Initializing AbyssalCraft.");
		//Achievements
		necro = new Achievement("achievement.necro", "necro", 0, 0, ACItems.necronomicon, AchievementList.openInventory).registerStat();
		//Materials Achievements
		mineAby = new Achievement("achievement.mineAby", "mineAby", 2, 0, ACBlocks.abyssalnite_ore, necro).registerStat();
		mineCorgem = new Achievement("achievement.mineCorgem", "mineCorgem", 4, 0, ACItems.coralium_gem, mineAby).registerStat();
		shadowGems = new Achievement("achievement.shadowGems", "shadowGems", 6, 0, ACItems.shadow_gem, mineCorgem).registerStat();
		//coraliumpearl
		mineCor = new Achievement("achievement.mineCor", "mineCor", 8, 0, ACBlocks.liquified_coralium_ore, shadowGems).registerStat();
		mineAbyOres = new Achievement("achievement.mineAbyOres", "mineAbyOres", 10, 0, ACBlocks.abyssal_diamond_ore, mineCor).registerStat();
		mineDread = new Achievement("achievement.mineDread", "mineDread", 12, 0, ACBlocks.dreaded_abyssalnite_ore, mineAbyOres).registerStat();
		dreadium = new Achievement("achievement.dreadium", "dreadium", 14, 0, ACItems.dreadium_ingot, mineDread).registerStat();
		eth = new Achievement("achievement.ethaxium", "ethaxium", 16, 0, ACItems.ethaxium_ingot, dreadium).setSpecial().registerStat();
		//Depths Ghoul Achievements
		killghoul = new Achievement("achievement.killghoul", "killghoul", -2, 0, ACItems.coralium_plagued_flesh_on_a_bone, necro).registerStat();
		ghoulhead = new Achievement("achievement.ghoulhead", "ghoulhead", -4, 0, ACBlocks.depths_ghoul_head, killghoul).registerStat();
		petehead = new Achievement("achievement.petehead", "petehead", -4, -2, ACBlocks.pete_head, ghoulhead).registerStat();
		wilsonhead = new Achievement("achievement.wilsonhead", "wilsonhead", -4, -4, ACBlocks.mr_wilson_head, petehead).registerStat();
		orangehead = new Achievement("achievement.orangehead", "orangehead", -4, -6, ACBlocks.dr_orange_head, wilsonhead).registerStat();
		//Necronomicon Achievements
		necrou1 = new Achievement("achievement.necrou1", "necrou1", 2, 1, ACItems.abyssal_wasteland_necronomicon, necro).registerStat();
		necrou2 = new Achievement("achievement.necrou2", "necrou2", 4, 1, ACItems.dreadlands_necronomicon, necrou1).registerStat();
		necrou3 = new Achievement("achievement.necrou3", "necrou3", 6, 1, ACItems.omothol_necronomicon, necrou2).registerStat();
		abyssaln = new Achievement("achievement.abyssaln", "abyssaln", 8, 1, ACItems.abyssalnomicon, necrou3).setSpecial().registerStat();
		//Ritual Achievements
		ritual = new Achievement("achievement.ritual", "ritual", -2, 1, ACBlocks.ritual_altar, necro).setSpecial().registerStat();
		ritualSummon = new Achievement("achievement.ritualSummon", "ritualSummon", -4, 1, ACBlocks.depths_ghoul_head, ritual).registerStat();
		ritualCreate = new Achievement("achievement.ritualCreate", "ritualCreate", -4, 2, ACItems.life_crystal, ritual).registerStat();
		ritualBreed = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.egg, ritual).registerStat();
		ritualPotion = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, Items.potionitem, ritual).registerStat();
		ritualPotionAoE = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, new ItemStack(Items.potionitem, 1, 16384), ritual).registerStat();
		ritualInfusion = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, ACItems.depths_helmet, ritual).registerStat();
		shoggothInfestation = new Achievement("achievement.shoggothInfestation", "shoggothInfestation", -6, 3, Items.skull, ritualBreed).registerStat();
		//Progression Achievements
		enterabyss = new Achievement("achievement.enterabyss", "enterabyss", 0, 2, ACBlocks.abyssal_stone, necro).setSpecial().registerStat();
		killdragon = new Achievement("achievement.killdragon", "killdragon", 2, 2, ACItems.coralium_plagued_flesh, enterabyss).registerStat();
		summonAsorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 4, Altar, enterabyss).registerStat();
		killAsorah = new Achievement("achievement.killAsorah", "killAsorah", 2, 4, ACItems.eye_of_the_abyss, summonAsorah).setSpecial().registerStat();
		enterdreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 2, 6, ACBlocks.dreadstone, killAsorah).setSpecial().registerStat();
		killdreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 4, 6, ACItems.dreaded_shard_of_abyssalnite, enterdreadlands).registerStat();
		summonChagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 2, 8, ACBlocks.chagaroth_altar_bottom, enterdreadlands).registerStat();
		killChagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 4, 8, ACItems.dread_plagued_gateway_key, summonChagaroth).setSpecial().registerStat();
		enterOmothol = new Achievement("achievement.enterOmothol", "enterOmothol", 4, 10, ACBlocks.omothol_stone, killChagaroth).setSpecial().registerStat();
		enterDarkRealm = new Achievement("achievement.darkRealm", "darkRealm", 2, 10, ACBlocks.darkstone, enterOmothol).registerStat();
		killOmotholelite = new Achievement("achievement.killOmotholelite", "killOmotholelite", 6, 10, ACItems.eldritch_scale, enterOmothol).registerStat();
		locateJzahar = new Achievement("achievement.locateJzahar", "locateJzahar", 4, 12, ACItems.jzahar_charm, enterOmothol).registerStat();
		killJzahar = new Achievement("achievement.killJzahar", "killJzahar", 6, 12, ACItems.staff_of_the_gatekeeper, locateJzahar).setSpecial().registerStat();
		//nowwhat
		//Gateway Key Achievements
		GK1 = new Achievement("achievement.GK1", "GK1", 0, -2, ACItems.gateway_key, necro).registerStat();
		findPSDL = new Achievement("achievement.findPSDL", "findPSDL", -2, -2, ACBlocks.dreadlands_infused_powerstone, GK1).registerStat();
		GK2 = new Achievement("achievement.GK2", "GK2", 0, -4, ACItems.dreaded_gateway_key, GK1).registerStat();
		GK3 = new Achievement("achievement.GK3", "GK3", 0, -6, ACItems.rlyehian_gateway_key, GK2).registerStat();
		//Machinery Achievements
		makeTransmutator = new Achievement("achievement.makeTransmutator", "makeTransmutator", 2, -1, ACBlocks.transmutator_idle, necro).registerStat();
		makeCrystallizer = new Achievement("achievement.makeCrystallizer", "makeCrystallizer", 4, -2, ACBlocks.crystallizer_idle, makeTransmutator).registerStat();
		makeMaterializer = new Achievement("achievement.makeMaterializer", "makeMaterializer", 6, -2, ACBlocks.materializer, makeCrystallizer).registerStat();
		makeCrystalBag = new Achievement("achievement.makeCrystalBag", "makeCrystalBag", 6, -4, ACItems.small_crystal_bag, makeMaterializer).registerStat();
		makeEngraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, ACBlocks.engraver, AchievementList.openInventory).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[]{necro, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah,
				enterdreadlands, killdreadguard, ghoulhead, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth,
				enterOmothol, enterDarkRealm, necrou1, necrou2, necrou3, abyssaln, ritual, ritualSummon, ritualCreate, killOmotholelite, locateJzahar, killJzahar, shadowGems,
				mineAbyOres, mineDread, dreadium, eth, makeTransmutator, makeCrystallizer, makeMaterializer, makeCrystalBag, makeEngraver, ritualBreed, ritualPotion, ritualPotionAoE,
				ritualInfusion, shoggothInfestation}));

		proxy.init();
		//		MinecraftForge.EVENT_BUS.register(new ReputationEventHandler()); //TODO: uncomment later
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(StructureDreadlandsMineStart.class, "DreadMine");
		StructureDreadlandsMinePieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(MapGenOmothol.Start.class, "Omothol");
		StructureOmotholPieces.registerOmotholPieces();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		AbyssalCrafting.addRecipes();
		AbyssalCraftAPI.getInternalNDHandler().registerInternalPages();
		IntegrationHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		ACLogger.info("Post-initializing AbyssalCraft");
		proxy.postInit();
		IntegrationHandler.postInit();
		((BlockCLiquid) ACBlocks.liquid_coralium).addBlocks();
		ACLogger.info("AbyssalCraft loaded.");
	}

	@EventHandler
	public void serverStart(FMLServerAboutToStartEvent event){
		String clname = AbyssalCraftAPI.getInternalNDHandler().getClass().getName();
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
					if(stuff.hasKey("quantity"))
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
		if(eventArgs.getModID().equals("abyssalcraft"))
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
		evilAnimalCreatureType = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animals Are Monsters", false, "If enabled, sets the creature type of Evil Animals to \"monster\". The creature type affects how a entity spawns, eg \"creature\" "
				+ "treats the entity as an animal, while \"monster\" treats it as a hostile mob. If you enable this, Evil Animals will spawn like any other hostile mobs, instead of mimicking vanilla animals.\n"
				+TextFormatting.RED+"[Minecraft Restart Required]"+TextFormatting.RESET).getBoolean();
		antiItemDisintegration = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Antimatter item disintegration", true, "Toggles whether or not Liquid Antimatter will disintegrate any items dropped into a pool of it.").getBoolean();

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

		//		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(axe), 1, 1, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(pickaxe), 1, 1, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(shovel), 1, 1, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(sword), 1, 1, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(DLTLog), 1, 3, 10));
		//		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(CobbleU), 1, 2, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(OC), 1,1,1));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(abyingot), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 2, 1));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(crystal, 1, 24), 1, 5, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(pickaxeA), 1, 1, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(Corpickaxe), 1, 1, 1));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(helmet), 1, 1, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(plate), 1, 1, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(legs), 1, 1, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(boots), 1, 1, 2));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(CobbleU), 1, 2, 10));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(IronU), 1, 2, 7));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(GoldU), 1, 2, 4));
		//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(DiamondU), 1, 2, 1));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(MRE), 1, 1, 5));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(Coralium), 1, 5, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(shadowfragment), 1, 10, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(shadowshard), 1, 6, 5));
		//		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(shadowgem), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(shadowfragment), 1, 10, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(shadowshard), 1, 6, 5));
		//		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(shadowgem), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowfragment), 1, 10, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowshard), 1, 6, 5));
		//		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowgem), 1, 3, 3));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowfragment), 1, 10, 8));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowshard), 1, 6, 5));
		//		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowgem), 1, 3, 3));
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

	private static int getUniqueEntityId() {
		do
			startEntityId++;
		while (EntityList.idToClassMapping.containsKey(startEntityId));

		return startEntityId;
	}

	@SuppressWarnings("unchecked")
	private static void registerEntityWithEgg(Class<? extends Entity> entity, String name, int modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
		int id = getUniqueEntityId();
		stringtoIDMapping.put(name, id);
		EntityRegistry.registerModEntity(entity, name, modid, instance, trackingRange, updateFrequency, sendsVelocityUpdates, primaryColor, secondaryColor);
		EntityList.idToClassMapping.put(id, entity);
	}

	private static void registerBiomeWithTypes(BiomeGenBase biome, String name, int weight, BiomeType btype, Type...types){
		GameRegistry.register(biome.setRegistryName(new ResourceLocation(modid, name)));
		BiomeDictionary.registerBiomeType(biome, types);
		BiomeManager.addBiome(btype, new BiomeEntry(biome, weight));
	}

	private static void registerPotion(ResourceLocation res, Potion pot){
		GameRegistry.register(pot.setRegistryName(res));
	}

	private static void registerEnchantment(ResourceLocation res, Enchantment ench){
		GameRegistry.register(ench.setRegistryName(res));
	}

	private static void registerPotionType(ResourceLocation res, PotionType pot){
		GameRegistry.register(pot.setRegistryName(res));
	}

	private static SoundEvent registerSoundEvent(String name){
		ResourceLocation res = new ResourceLocation(modid, name);
		SoundEvent evt = new SoundEvent(res).setRegistryName(res);
		GameRegistry.register(evt);
		return evt;
	}

	private static void registerItem(Item item, String name){
		GameRegistry.register(item.setRegistryName(new ResourceLocation(modid, name)));
	}

	private static void registerBlock(Block block, String name){
		registerBlock(block, new ItemBlock(block), name);
	}

	private static void registerBlock(Block block, ItemBlock item, String name){
		GameRegistry.register(block.setRegistryName(new ResourceLocation(modid, name)));
		registerItem(item, name);
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