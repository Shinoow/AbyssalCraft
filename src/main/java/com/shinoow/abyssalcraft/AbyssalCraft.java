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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.shinoow.abyssalcraft.common.AbyssalCraftItems;
import com.shinoow.abyssalcraft.common.CommonProxyAbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.ACPressureplate;
import com.shinoow.abyssalcraft.common.blocks.ACfence;
import com.shinoow.abyssalcraft.common.blocks.AbyCoraliumore;
import com.shinoow.abyssalcraft.common.blocks.AbyDreadbrick;
import com.shinoow.abyssalcraft.common.blocks.AbyDreadbrickslab;
import com.shinoow.abyssalcraft.common.blocks.AbyDreadbrickslabdouble;
import com.shinoow.abyssalcraft.common.blocks.AbyDreadbrickstairs;
import com.shinoow.abyssalcraft.common.blocks.AbyDreadore;
import com.shinoow.abyssalcraft.common.blocks.AbyDreadstone;
import com.shinoow.abyssalcraft.common.blocks.AbyOre;
import com.shinoow.abyssalcraft.common.blocks.Abybutton;
import com.shinoow.abyssalcraft.common.blocks.Abyssalstonestairs;
import com.shinoow.abyssalcraft.common.blocks.Altar;
import com.shinoow.abyssalcraft.common.blocks.Antiliquid;
import com.shinoow.abyssalcraft.common.blocks.BlockTeleporter;
import com.shinoow.abyssalcraft.common.blocks.BlockTeleporterDL;
import com.shinoow.abyssalcraft.common.blocks.CLiquid;
import com.shinoow.abyssalcraft.common.blocks.CoraliumOre;
import com.shinoow.abyssalcraft.common.blocks.Coraliumfire;
import com.shinoow.abyssalcraft.common.blocks.Coraliumstone;
import com.shinoow.abyssalcraft.common.blocks.Crate;
import com.shinoow.abyssalcraft.common.blocks.Cstone;
import com.shinoow.abyssalcraft.common.blocks.CstoneBrick;
import com.shinoow.abyssalcraft.common.blocks.Cstonebrickslab;
import com.shinoow.abyssalcraft.common.blocks.Cstonebrickslabdouble;
import com.shinoow.abyssalcraft.common.blocks.Cstonebrickstairs;
import com.shinoow.abyssalcraft.common.blocks.Cstonebutton;
import com.shinoow.abyssalcraft.common.blocks.DGhead;
import com.shinoow.abyssalcraft.common.blocks.DLTLeaves;
import com.shinoow.abyssalcraft.common.blocks.DLTLog;
import com.shinoow.abyssalcraft.common.blocks.DLTSapling;
import com.shinoow.abyssalcraft.common.blocks.DLTbutton;
import com.shinoow.abyssalcraft.common.blocks.DLTplank;
import com.shinoow.abyssalcraft.common.blocks.DLTslab;
import com.shinoow.abyssalcraft.common.blocks.DLTslabdouble;
import com.shinoow.abyssalcraft.common.blocks.DLTstairs;
import com.shinoow.abyssalcraft.common.blocks.DSGlow;
import com.shinoow.abyssalcraft.common.blocks.Darkbrickslab;
import com.shinoow.abyssalcraft.common.blocks.Darkbrickslabdouble;
import com.shinoow.abyssalcraft.common.blocks.Darkcobbleslab;
import com.shinoow.abyssalcraft.common.blocks.Darkcobbleslabdouble;
import com.shinoow.abyssalcraft.common.blocks.Darklandsgrass;
import com.shinoow.abyssalcraft.common.blocks.Darkstone;
import com.shinoow.abyssalcraft.common.blocks.Darkstone_brick;
import com.shinoow.abyssalcraft.common.blocks.Darkstone_cobble;
import com.shinoow.abyssalcraft.common.blocks.Darkstonebrickstairs;
import com.shinoow.abyssalcraft.common.blocks.Darkstonebutton;
import com.shinoow.abyssalcraft.common.blocks.Darkstonecobblestairs;
import com.shinoow.abyssalcraft.common.blocks.Darkstonecobblewall;
import com.shinoow.abyssalcraft.common.blocks.Darkstoneslab;
import com.shinoow.abyssalcraft.common.blocks.Darkstoneslabdouble;
import com.shinoow.abyssalcraft.common.blocks.DreadFire;
import com.shinoow.abyssalcraft.common.blocks.Dreadbrick;
import com.shinoow.abyssalcraft.common.blocks.Dreadbrickslab;
import com.shinoow.abyssalcraft.common.blocks.Dreadbrickslabdouble;
import com.shinoow.abyssalcraft.common.blocks.Dreadbrickstairs;
import com.shinoow.abyssalcraft.common.blocks.Dreadgrass;
import com.shinoow.abyssalcraft.common.blocks.Dreadleaves;
import com.shinoow.abyssalcraft.common.blocks.Dreadlog;
import com.shinoow.abyssalcraft.common.blocks.Dreadore;
import com.shinoow.abyssalcraft.common.blocks.Dreadplanks;
import com.shinoow.abyssalcraft.common.blocks.Dreadsapling;
import com.shinoow.abyssalcraft.common.blocks.Dreadstone;
import com.shinoow.abyssalcraft.common.blocks.ODB;
import com.shinoow.abyssalcraft.common.blocks.ODBcore;
import com.shinoow.abyssalcraft.common.blocks.Ohead;
import com.shinoow.abyssalcraft.common.blocks.PSDL;
import com.shinoow.abyssalcraft.common.blocks.Phead;
import com.shinoow.abyssalcraft.common.blocks.Whead;
import com.shinoow.abyssalcraft.common.blocks.abyblock;
import com.shinoow.abyssalcraft.common.blocks.abybrick;
import com.shinoow.abyssalcraft.common.blocks.abystone;
import com.shinoow.abyssalcraft.common.blocks.abystoneslab;
import com.shinoow.abyssalcraft.common.blocks.abystoneslabdouble;
import com.shinoow.abyssalcraft.common.blocks.corblock;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemAbyDreadbrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemAbySlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemAltar;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemCstonebrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDLTSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDarkbrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDarkcobbleSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDarkstoneSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDreadbrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemODB;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrate;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDGhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityOhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPSDL;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityWhead;
import com.shinoow.abyssalcraft.common.creativetabs.TabACBlocks;
import com.shinoow.abyssalcraft.common.creativetabs.TabACCombat;
import com.shinoow.abyssalcraft.common.creativetabs.TabACDecoration;
import com.shinoow.abyssalcraft.common.creativetabs.TabACFood;
import com.shinoow.abyssalcraft.common.creativetabs.TabACItems;
import com.shinoow.abyssalcraft.common.creativetabs.TabACTools;
import com.shinoow.abyssalcraft.common.entity.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsghoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.entity.EntityODBMeteor;
import com.shinoow.abyssalcraft.common.entity.EntityODBPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.Entityabygolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadgolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadguard;
import com.shinoow.abyssalcraft.common.entity.Entityevilpig;
import com.shinoow.abyssalcraft.common.handlers.AbyssalCraftEventHooks;
import com.shinoow.abyssalcraft.common.handlers.BiomeEventHandler;
import com.shinoow.abyssalcraft.common.handlers.BucketHandler;
import com.shinoow.abyssalcraft.common.handlers.CraftingHandler;
import com.shinoow.abyssalcraft.common.items.AbyssalCraftTool;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteAxe;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteCAxe;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteCHoe;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteCPickaxe;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteCShovel;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteCSword;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteHoe;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalnitePickaxe;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteShovel;
import com.shinoow.abyssalcraft.common.items.ItemAbyssalniteSword;
import com.shinoow.abyssalcraft.common.items.ItemAntiBucket;
import com.shinoow.abyssalcraft.common.items.ItemCBucket;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumAxe;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumBow;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumHoe;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumPickaxe;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumShovel;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumSword;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumcluster;
import com.shinoow.abyssalcraft.common.items.ItemCorb;
import com.shinoow.abyssalcraft.common.items.ItemCorbone;
import com.shinoow.abyssalcraft.common.items.ItemCorflesh;
import com.shinoow.abyssalcraft.common.items.ItemDarkstoneAxe;
import com.shinoow.abyssalcraft.common.items.ItemDarkstoneHoe;
import com.shinoow.abyssalcraft.common.items.ItemDarkstonePickaxe;
import com.shinoow.abyssalcraft.common.items.ItemDarkstoneShovel;
import com.shinoow.abyssalcraft.common.items.ItemDarkstoneSword;
import com.shinoow.abyssalcraft.common.items.ItemEoA;
import com.shinoow.abyssalcraft.common.items.ItemFriedegg;
import com.shinoow.abyssalcraft.common.items.ItemOC;
import com.shinoow.abyssalcraft.common.items.ItemPlatefood;
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacer;
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacerDL;
import com.shinoow.abyssalcraft.common.items.ItemStaff;
import com.shinoow.abyssalcraft.common.items.ItemTrackerPSDL;
import com.shinoow.abyssalcraft.common.items.ItemUpgradeKit;
import com.shinoow.abyssalcraft.common.items.ItemWashCloth;
import com.shinoow.abyssalcraft.common.items.armor.AbyssalniteArmor;
import com.shinoow.abyssalcraft.common.items.armor.AbyssalniteCArmor;
import com.shinoow.abyssalcraft.common.items.armor.CoraliumArmor;
import com.shinoow.abyssalcraft.common.items.armor.CoraliumPArmor;
import com.shinoow.abyssalcraft.common.items.armor.DepthsArmor;
import com.shinoow.abyssalcraft.common.items.armor.DreadArmor;
import com.shinoow.abyssalcraft.common.lib.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.potion.PotionCplague;
import com.shinoow.abyssalcraft.common.potion.PotionDplague;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.MapGenAbyStronghold;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.StructureAbyStrongholdPieces;
import com.shinoow.abyssalcraft.common.util.EnumBiomeType;
import com.shinoow.abyssalcraft.common.util.EnumToolMaterialAC;
import com.shinoow.abyssalcraft.common.util.LogHelper;
import com.shinoow.abyssalcraft.common.world.AbyssalCraftWorldGenerator;
import com.shinoow.abyssalcraft.common.world.WorldProviderAbyss;
import com.shinoow.abyssalcraft.common.world.WorldProviderDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenAbyDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenAbywasteland;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenCorOcean;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenCorSwamp;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsForest;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsHills;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsMountains;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsPlains;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenForestDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenMountainDreadlands;
import com.shinoow.abyssalcraft.packet.PacketPipeline;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;


@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version, useMetadata = true)
public class AbyssalCraft
{
	public static final String version = "1.5.0";
	public static final String modid = "abyssalcraft";
	public static final String name = "AbyssalCraft";

	@Metadata(AbyssalCraft.modid)
	public static ModMetadata metadata;

	@Instance(AbyssalCraft.modid)
	public static AbyssalCraft instance = new AbyssalCraft();

	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.client.ClientProxyAbyssalCraft",
			serverSide = "com.shinoow.abyssalcraft.common.CommonProxyAbyssalCraft")
	public static CommonProxyAbyssalCraft proxy;

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	public static Fluid CFluid;
	public static Fluid antifluid;

	public static Achievement mineDS;
	public static Achievement mineAby;
	public static Achievement killghoul;
	public static Achievement enterabyss;
	public static Achievement killdragon;
	public static Achievement summonAsorah;
	public static Achievement killAsorah;
	public static Achievement enterdreadlands;
	public static Achievement killdreadguard;
	public static Achievement ghoulhead;
	public static Achievement killPete;
	public static Achievement killWilson;
	public static Achievement killOrange;
	public static Achievement petehead;
	public static Achievement wilsonhead;
	public static Achievement orangehead;
	public static Achievement mineCorgem;
	public static Achievement mineCor;
	public static Achievement findPSDL;
	public static Achievement GK1;
	public static Achievement GK2;
	public static Achievement GK3;
	public static Achievement Jzhstaff;
	public static Achievement secret1;

	public static Block Darkstone;
	public static Block Darkstone_brick;
	public static Block Darkstone_cobble;
	public static Block DSGlow;
	public static Block Darkbrickslab1;
	public static Block Darkbrickslab2;
	public static Block Darkcobbleslab1;
	public static Block Darkcobbleslab2;
	public static Block Darkgrass;
	public static Block DBstairs;
	public static Block DCstairs;
	public static Block DLTLeaves;
	public static Block DLTLog;
	public static Block DLTSapling;
	public static Block abystone;
	public static Block abybrick;
	public static Block abyslab1;
	public static Block abyslab2;
	public static Block abystairs;
	public static Block Coraliumore;
	public static Block abyore;
	public static Block abyfence;
	public static Block DSCwall;
	public static Block ODB;
	public static Block abyblock;
	public static Block Coraliumstone;
	public static Block ODBcore;
	public static Block Crate;
	public static Block portal;
	public static Block Darkstoneslab1;
	public static Block Darkstoneslab2;
	public static Block Coraliumfire;
	public static Block DSbutton;
	public static Block DSpplate;
	public static Block DLTplank;
	public static Block DLTbutton;
	public static Block DLTpplate;
	public static Block DLTstairs;
	public static Block DLTslab1;
	public static Block DLTslab2;
	public static Block Cwater;
	public static Block PSDL;
	public static Block AbyCorOre;
	public static Block corblock;
	public static Block Altar;
	public static Block Abybutton;
	public static Block Abypplate;
	public static Block DSBfence;
	public static Block DLTfence;
	//Dreadlands block vars
	public static Block dreadstone;
	public static Block abydreadstone;
	public static Block abydreadore;
	public static Block dreadore;
	public static Block dreadbrick;
	public static Block abydreadbrick;
	public static Block dreadgrass;
	public static Block dreadlog;
	public static Block dreadleaves;
	public static Block dreadsapling;
	public static Block dreadplanks;
	public static Block dreadportal;
	public static Block dreadfire;
	public static Block dreadbrickfence;
	public static Block dreadbrickstairs;
	public static Block dreadbrickslab1;
	public static Block dreadbrickslab2;
	public static Block abydreadbrickfence;
	public static Block abydreadbrickstairs;
	public static Block abydreadbrickslab1;
	public static Block abydreadbrickslab2;
	//End of Dreadlands block vars
	public static Block DGhead;
	public static Block Phead;
	public static Block Whead;
	public static Block Ohead;
	public static Block anticwater;
	public static Block cstone;
	public static Block cstonebrick;
	public static Block cstonebrickfence;
	public static Block cstonebrickstairs;
	public static Block cstonebrickslab1;
	public static Block cstonebrickslab2;
	public static Block cstonebutton;
	public static Block cstonepplate;

	//Overworld biomes
	public static BiomeGenBase Darklands;
	public static BiomeGenBase DarklandsForest;
	public static BiomeGenBase DarklandsPlains;
	public static BiomeGenBase DarklandsHills;
	public static BiomeGenBase DarklandsMountains;
	public static BiomeGenBase corswamp;
	public static BiomeGenBase corocean;
	//Abyssal Wastelands biome
	public static BiomeGenBase Wastelands;
	//Dreadlands biomes
	public static BiomeGenBase Dreadlands;
	public static BiomeGenBase AbyDreadlands;
	public static BiomeGenBase ForestDreadlands;
	public static BiomeGenBase MountainDreadlands;

	public static Item OC;
	public static Item pickaxe;
	public static Item axe;
	public static Item shovel;
	public static Item sword;
	public static Item hoe;
	public static Item abychunk;
	public static Item abyingot;
	public static Item Staff;
	public static Item Coralium;
	public static Item boots;
	public static Item helmet;
	public static Item plate;
	public static Item legs;
	public static Item pickaxeA;
	public static Item axeA;
	public static Item shovelA;
	public static Item swordA;
	public static Item hoeA;
	public static Item Coraliumcluster2;
	public static Item Coraliumcluster3;
	public static Item Coraliumcluster4;
	public static Item Coraliumcluster5;
	public static Item Coraliumcluster6;
	public static Item Coraliumcluster7;
	public static Item Coraliumcluster8;
	public static Item Coraliumcluster9;
	public static Item Cpearl;
	public static Item portalPlacer;
	public static Item bootsC;
	public static Item helmetC;
	public static Item plateC;
	public static Item legsC;
	public static Item pickaxeC;
	public static Item axeC;
	public static Item shovelC;
	public static Item swordC;
	public static Item hoeC;
	public static Item Corb;
	public static Item Dreadshard;
	public static Item bootsD;
	public static Item helmetD;
	public static Item plateD;
	public static Item legsD;
	public static Item Cchunk;
	public static Item Cingot;
	public static Item Cplate;
	public static Item CobbleU;
	public static Item IronU;
	public static Item GoldU;
	public static Item DiamondU;
	public static Item AbyssalniteU;
	public static Item CoraliumU;
	public static Item MRE;
	public static Item ironp;
	public static Item chickenp;
	public static Item porkp;
	public static Item beefp;
	public static Item fishp;
	public static Item Cbucket;
	public static Item dirtyplate;
	public static Item friedegg;
	public static Item eggp;
	public static Item cloth;
	public static Item PSDLfinder;
	public static Item Corflesh;
	public static Item Corbone;
	public static Item Corboots;
	public static Item Corhelmet;
	public static Item Corplate;
	public static Item Corlegs;
	public static Item Corpickaxe;
	public static Item Coraxe;
	public static Item Corshovel;
	public static Item Corsword;
	public static Item Corhoe;
	public static Item CorbootsP;
	public static Item CorhelmetP;
	public static Item CorplateP;
	public static Item CorlegsP;
	public static Item Depthsboots;
	public static Item Depthshelmet;
	public static Item Depthsplate;
	public static Item Depthslegs;
	public static Item EoA;
	//Dreadlands item vars
	public static Item portalPlacerDL;
	public static Item dreadchunk;
	//End of Dreadlands item vars
	public static Item shadowfragment;
	public static Item shadowshard;
	public static Item shadowgem;
	public static Item oblivionshard;
	public static Item corbow;
	public static Item antibucket;
	public static Item cbrick;

	//secret dev items
	public static Item devsword;

	public static Potion Cplague;
	public static Potion Dplague;

	public static CreativeTabs tabBlock = new TabACBlocks(CreativeTabs.getNextID(), "AbyssalCraft Blocks");
	public static CreativeTabs tabItems = new TabACItems(CreativeTabs.getNextID(), "AbyssalCraft Items");
	public static CreativeTabs tabTools = new TabACTools(CreativeTabs.getNextID(), "AbyssalCraft Tools");
	public static CreativeTabs tabCombat = new TabACCombat(CreativeTabs.getNextID(), "AbyssalCraft Combat Tools");
	public static CreativeTabs tabFood = new TabACFood(CreativeTabs.getNextID(), "AbyssalCraft Foodstuffs");
	public static CreativeTabs tabDecoration = new TabACDecoration(CreativeTabs.getNextID(), "AbyssalCraft Decoration Blocks");

	public static int dimension = 50;
	public static int dimension2 = 51;
	//Will be used in a couple of months
	//public static int dimension3 = 52;
	static int startEntityId = 300;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		metadata = event.getModMetadata();
		packetPipeline.initialise();
		LogHelper.info("Preparing AbyssalCraft");
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
		MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeEventHandler());
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		instance = this;
		proxy.preInit();

		//armor enums, might inject this right into the ArmorMaterial class
		EnumHelper.addArmorMaterial("Abyssalnite", 35, new int[]{3, 8, 6, 3}, 10);
		EnumHelper.addArmorMaterial("AbyssalniteC", 36, new int[]{3, 8, 6, 3}, 30);
		EnumHelper.addArmorMaterial("Dread", 36, new int[]{3, 8, 6, 3}, 12);
		EnumHelper.addArmorMaterial("Coralium", 37, new int[]{3, 8, 6, 3}, 12);
		EnumHelper.addArmorMaterial("CoraliumP", 55, new int[]{3, 8, 6, 3}, 12);
		EnumHelper.addArmorMaterial("Depths", 33, new int[]{3, 8, 6, 3}, 25);

		//tool enums, merhaps do the same as with armor enums
		EnumHelper.addToolMaterial("DARKSTONE", 1, 180, 5.0F, 1, 15);
		EnumHelper.addToolMaterial("ABYSSALNITE", 4, 1261, 13.0F, 4, 10);
		EnumHelper.addToolMaterial("CORALIUM", 6, 4000, 14.0F, 6, 12);
		EnumHelper.addToolMaterial("ABYSSALNITE_C", 8, 8000, 20.0F, 8, 30);
		LogHelper.info("Preparing blocks");

		CFluid = new Fluid("Liquid Coralium").setDensity(3000).setViscosity(1000).setTemperature(350);
		antifluid = new Fluid("").setDensity(4000).setViscosity(1500).setTemperature(100);

		Fluid.registerLegacyName(CFluid.getName(), CFluid.getName());
		FluidRegistry.registerFluid(CFluid);
		Fluid.registerLegacyName(antifluid.getName(), antifluid.getName());
		FluidRegistry.registerFluid(antifluid);

		//Blocks
		Darkstone = new Darkstone().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DS").setBlockTextureName(modid + ":" + "DS");
		Darkstone_brick = new Darkstone_brick().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSB").setBlockTextureName(modid + ":" + "DSB");
		Darkstone_cobble = new Darkstone_cobble().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(2.2F).setResistance(12.0F).setBlockName("DSC").setBlockTextureName(modid + ":" + "DSC");
		DSGlow = new DSGlow().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(55F).setResistance(3000F).setLightLevel(1.0F).setBlockName("DSGlow");
		Darkbrickslab1 = (BlockSlab) new Darkbrickslab(false).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs1").setBlockTextureName(modid + ":" + "DSB");
		Darkbrickslab2 = (BlockSlab) new Darkbrickslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs2").setBlockTextureName(modid + ":" + "DSB");
		Darkcobbleslab1 = (BlockSlab) new Darkcobbleslab(false).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F) .setResistance(12.0F).setBlockName("DSCs1").setBlockTextureName(modid + ":" + "DSC");
		Darkcobbleslab2 = (BlockSlab) new Darkcobbleslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.65F) .setResistance(12.0F).setBlockName("DSCs2").setBlockTextureName(modid + ":" + "DSC");
		Darkgrass = new Darklandsgrass().setStepSound(Block.soundTypeGrass).setCreativeTab(AbyssalCraft.tabBlock).setHardness(0.4F).setBlockName("DLG");
		DBstairs = new Darkstonebrickstairs(0).setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs").setBlockTextureName(modid + ":" + "DSB");
		DCstairs = new Darkstonecobblestairs(0).setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSCs").setBlockTextureName(modid + ":" + "DSC");
		DLTLeaves = (DLTLeaves)new DLTLeaves().setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setBlockName("DLTL").setBlockTextureName(modid + ":" + "DLTL");
		DLTLog = new DLTLog().setStepSound(Block.soundTypeWood).setHardness(2.0F).setResistance(1.0F).setBlockName("DLTT");
		DLTSapling = (DLTSapling)new DLTSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DLTS").setBlockTextureName(modid + ":" + "DLTS");
		abystone = new abystone().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("AS").setBlockTextureName(modid + ":" + "AS");
		abybrick = new abybrick().setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASB").setBlockTextureName(modid + ":" + "ASB");
		abyslab1 = (BlockSlab) new abystoneslab(false).setCreativeTab(AbyssalCraft.tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs1").setBlockTextureName(modid + ":" + "ASB");
		abyslab2 = (BlockSlab) new abystoneslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs2").setBlockTextureName(modid + ":" + "ASB");
		abystairs = new Abyssalstonestairs(0).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("ASBs").setBlockTextureName(modid + ":" + "ASB");
		Coraliumore = new CoraliumOre().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("CO").setBlockTextureName(modid + ":" + "CO");
		abyore = new AbyOre().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("AO").setBlockTextureName(modid + ":" + "AO");
		abyfence = new ACfence("ASBf", Material.rock, "pickaxe", 2).setHardness(1.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASBf").setBlockTextureName(modid + ":" + "ASBf");
		DSCwall = new Darkstonecobblewall(Darkstone_cobble).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSCw").setBlockTextureName(modid + ":" + "DSC");
		Crate = new Crate().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("Crate").setBlockTextureName(modid + ":" + "Crate");
		ODB = new ODB().setStepSound(Block.soundTypeGrass).setHardness(3.0F).setResistance(0F).setBlockName("ODB");
		abyblock = new abyblock().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(12.0F).setCreativeTab(AbyssalCraft.tabBlock).setBlockName("BOA").setBlockTextureName(modid + ":" + "BOA");
		Coraliumstone = new Coraliumstone().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("CIS").setBlockTextureName(modid + ":" + "CIS");
		ODBcore = new ODBcore().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setBlockName("ODBC");
		portal = new BlockTeleporter().setBlockName("AG").setBlockTextureName(modid + ":" + "AG");
		Darkstoneslab1 = (BlockSlab) new Darkstoneslab(false).setStepSound(Block.soundTypeStone).setCreativeTab(AbyssalCraft.tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs1");
		Darkstoneslab2 = (BlockSlab) new Darkstoneslabdouble(true).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs2");
		Coraliumfire = new Coraliumfire().setLightLevel(1.0F).setBlockName("Cfire");
		DSbutton = new Darkstonebutton().setHardness(0.6F).setResistance(12.0F).setBlockName("DSbb").setBlockTextureName(modid + ":" + "DS");
		DSpplate = new ACPressureplate("DS", Material.rock, ACPressureplate.Sensitivity.mobs, "pickaxe", 0).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSpp").setBlockTextureName(modid + ":" + "DS");
		DLTplank = new DLTplank().setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setCreativeTab(AbyssalCraft.tabBlock).setBlockName("DLTplank").setBlockTextureName(modid + ":" + "DLTplank");
		DLTbutton = new DLTbutton().setHardness(0.5F).setBlockName("DLTplankb").setBlockTextureName(modid + ":" + "DLTplank");
		DLTpplate = new ACPressureplate("DLTplank", Material.wood, ACPressureplate.Sensitivity.everything, "axe", 0).setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("DLTpp").setBlockTextureName(modid + ":" + "DLTplank");
		DLTstairs = new DLTstairs(0).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks").setBlockTextureName(modid + ":" + "DLTplank");
		DLTslab1 = (BlockSlab) new DLTslab(false).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks1").setBlockTextureName(modid + ":" + "DLTplank");
		DLTslab2 = (BlockSlab) new DLTslabdouble(true).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks2").setBlockTextureName(modid + ":" + "DLTplank");
		corblock = new corblock().setStepSound(Block.soundTypeMetal).setHardness(4.0F).setResistance(6.0F).setCreativeTab(AbyssalCraft.tabBlock).setBlockName("BOC").setBlockTextureName(modid + ":" + "BOC");
		PSDL = (new PSDL().setHardness(50.0F).setResistance(3000F).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("PSDL").setBlockTextureName(modid + ":" + "PSDL"));
		AbyCorOre = (new AbyCoraliumore().setHardness(10.0F).setResistance(12.0F).setBlockName("ACO").setBlockTextureName(modid + ":" + "ACO"));
		Altar = new Altar().setStepSound(Block.soundTypeStone).setHardness(4.0F).setResistance(300.0F).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Altar").setBlockTextureName(modid + ":" + "Altar");
		Abybutton = new Abybutton().setHardness(0.8F).setResistance(12.0F).setBlockName("ASbb").setBlockTextureName(modid + ":" + "AS");
		Abypplate = new ACPressureplate("AS", Material.rock, ACPressureplate.Sensitivity.mobs, "pickaxe", 2).setHardness(0.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASpp").setBlockTextureName(modid + ":" + "AS");
		DSBfence = new ACfence("DSB", Material.rock, "pickaxe", 0).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSBf");
		DLTfence = new ACfence("DLTplank", Material.wood, "axe", 0).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTf");
		dreadore = new Dreadore().setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSO").setBlockTextureName(modid + ":" + "DrSO");
		abydreadore = new AbyDreadore().setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSO").setBlockTextureName(modid + ":" + "AbyDrSO");
		dreadbrick = new Dreadbrick().setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSB").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrick = new AbyDreadbrick().setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSB").setBlockTextureName(modid + ":" + "AbyDrSB");
		dreadlog = new Dreadlog().setHardness(2.0F).setResistance(12.0F).setStepSound(Block.soundTypeWood).setBlockName("DrT");
		dreadleaves = (Dreadleaves)new Dreadleaves(false).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setBlockName("DrTL");
		dreadsapling = (Dreadsapling)new Dreadsapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DrTS").setBlockTextureName(modid + ":" + "DrTS");
		dreadplanks = new Dreadplanks().setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setCreativeTab(AbyssalCraft.tabBlock).setBlockName("DrTplank").setBlockTextureName(modid + ":" + "DrTplank");
		dreadportal = new BlockTeleporterDL().setBlockName("DG").setBlockTextureName(modid + ":" + "DG");
		dreadfire = new DreadFire().setLightLevel(1.0F).setBlockName("Dfire");
		DGhead = new DGhead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("DGhead").setBlockTextureName(modid + ":" + "DGhead");
		Cwater = (new CLiquid().setResistance(100.0F).setLightLevel(1.0F).setBlockName("Cwater"));
		dreadstone = new Dreadstone().setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrS").setBlockTextureName(modid + ":" + "DrS");
		abydreadstone = new AbyDreadstone().setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrS").setBlockTextureName(modid + ":" + "AbyDrS");
		dreadgrass = new Dreadgrass().setHardness(0.4F).setStepSound(Block.soundTypeGrass).setBlockName("DrG");
		Phead = new Phead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Phead").setBlockTextureName(modid + ":" + "Phead");
		Whead = new Whead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Whead").setBlockTextureName(modid + ":" + "Whead");
		Ohead = new Ohead().setHardness(2.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(AbyssalCraft.tabDecoration).setBlockName("Ohead").setBlockTextureName(modid + ":" + "Ohead");
		dreadbrickstairs = new Dreadbrickstairs(0).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs").setBlockTextureName(modid + ":" + "DrSB");
		dreadbrickfence = new ACfence("DrSB", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBf");
		dreadbrickslab1 = (BlockSlab) new Dreadbrickslab(false).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs1").setBlockTextureName(modid + ":" + "DrSB");
		dreadbrickslab2 = (BlockSlab) new Dreadbrickslabdouble(true).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs2").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrickstairs = new AbyDreadbrickstairs(0).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrickfence = new ACfence("AbyDrSB", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBf");
		abydreadbrickslab1 = (BlockSlab) new AbyDreadbrickslab(false).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs1").setBlockTextureName(modid + ":" + "AbyDrSB");
		abydreadbrickslab2 = (BlockSlab) new AbyDreadbrickslabdouble(true).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs2").setBlockTextureName(modid + ":" + "AbyDrSB");
		anticwater = new Antiliquid().setResistance(100.0F).setLightLevel(0.5F).setBlockName("antiliquid");
		cstone = new Cstone().setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstone").setBlockTextureName(modid + ":" + "cstone");
		cstonebrick = new CstoneBrick().setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebrick").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickfence = new ACfence("cstonebrick", Material.rock, "pickaxe", 0).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebrickf");
		cstonebrickslab1 = (BlockSlab) new Cstonebrickslab(false).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks1").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickslab2 = (BlockSlab) new Cstonebrickslabdouble(true).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks2").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickstairs = new Cstonebrickstairs(0).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebutton = new Cstonebutton().setHardness(0.6F).setResistance(12.0F).setBlockName("cstonebutton");
		cstonepplate = new ACPressureplate("cstone", Material.rock, ACPressureplate.Sensitivity.mobs, "pickaxe", 0).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonepplate");

		//Biome
		Darklands = (new BiomeGenDarklands(100).setColor(522674).setBiomeName("Darklands"));
		Wastelands = (new BiomeGenAbywasteland(101).setColor(522674).setBiomeName("Abyssal Wastelands").setDisableRain());
		Dreadlands = (new BiomeGenDreadlands(102).setColor(522674).setBiomeName("Dreadlands").setDisableRain());
		AbyDreadlands = (new BiomeGenAbyDreadlands(103).setColor(522674).setBiomeName("Purified Dreadlands").setDisableRain());
		ForestDreadlands = (new BiomeGenForestDreadlands(104).setColor(522674).setBiomeName("Dreadlands Forest").setDisableRain());
		MountainDreadlands = (new BiomeGenMountainDreadlands(105).setColor(522674).setBiomeName("Dreadlands Mountains").setDisableRain());
		DarklandsForest = (new BiomeGenDarklandsForest(106).setColor(522674).setBiomeName("Darklands Forest"));
		DarklandsPlains = (new BiomeGenDarklandsPlains(107).setColor(522674).setBiomeName("Darklands Plains").setDisableRain());
		DarklandsHills = (new BiomeGenDarklandsHills(108).setColor(522674).setBiomeName("Darklands Highland"));
		DarklandsMountains =(new BiomeGenDarklandsMountains(109).setColor(522674).setBiomeName("Darklands Mountains").setDisableRain());
		corswamp = (new BiomeGenCorSwamp(110).setColor(522674).setBiomeName("Coralium Infested Swamp"));
		corocean = (new BiomeGenCorOcean(111).setColor(522674).setBiomeName("Coralium Infested Ocean"));

		LogHelper.info("Preparing items");
		//secret dev stuff
		devsword = new AbyssalCraftTool(EnumToolMaterialAC.ABYSSALNITE_C).setUnlocalizedName("Sword").setTextureName(modid + ":" + "Sword");

		//Misc items
		OC = (new ItemOC(255, 1F, false).setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("OC").setTextureName(modid + ":" + "OC"));
		Staff = new ItemStaff().setCreativeTab(AbyssalCraft.tabTools).setFull3D().setUnlocalizedName("SOTG").setTextureName(modid + ":" + "SOTG");
		portalPlacer = new ItemPortalPlacer().setUnlocalizedName("GK").setTextureName(modid + ":" + "GK");
		Cbucket = (new ItemCBucket(Cwater).setCreativeTab(AbyssalCraft.tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Cbucket").setTextureName(modid + ":" + "Cbucket"));
		PSDLfinder = (new ItemTrackerPSDL().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("PSDLf").setTextureName(modid + ":" + "PSDLf"));
		EoA = new ItemEoA().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("EoA").setTextureName(modid + ":" + "EoA");
		portalPlacerDL = new ItemPortalPlacerDL().setUnlocalizedName("GKD").setTextureName(modid + ":" + "GKD");
		antibucket = new ItemAntiBucket(anticwater).setCreativeTab(AbyssalCraft.tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Antibucket").setTextureName(modid + ":" + "Antibucket");
		cbrick = new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("cbrick").setTextureName(modid + ":" + "cbrick");

		//Shadow items
		shadowfragment = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("SF").setTextureName(modid + ":" + "SF"));
		shadowshard = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("SS").setTextureName(modid + ":" + "SS"));
		shadowgem = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("SG").setTextureName(modid + ":" + "SG"));
		oblivionshard = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("OS").setTextureName(modid + ":" + "OS"));

		//Dread items
		Dreadshard = new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("DSOA").setTextureName(modid + ":" + "DSOA");
		dreadchunk = new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("DAC").setTextureName(modid + ":" + "DAC");

		//Abyssalnite items
		abychunk = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("AC").setTextureName(modid + ":" + "AC"));
		abyingot = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("AI").setTextureName(modid + ":" + "AI"));

		//Coralium items
		Coraliumcluster2 = (new ItemCoraliumcluster("2").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCA").setTextureName(modid + ":" + "CGCA"));
		Coraliumcluster3 = (new ItemCoraliumcluster("3").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCB").setTextureName(modid + ":" + "CGCB"));
		Coraliumcluster4 = (new ItemCoraliumcluster("4").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCC").setTextureName(modid + ":" + "CGCC"));
		Coraliumcluster5 = (new ItemCoraliumcluster("5").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCD").setTextureName(modid + ":" + "CGCD"));
		Coraliumcluster6 = (new ItemCoraliumcluster("6").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCE").setTextureName(modid + ":" + "CGCE"));
		Coraliumcluster7 = (new ItemCoraliumcluster("7").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCF").setTextureName(modid + ":" + "CGCF"));
		Coraliumcluster8 = (new ItemCoraliumcluster("8").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCG").setTextureName(modid + ":" + "CGCG"));
		Coraliumcluster9 = (new ItemCoraliumcluster("9").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CGCH").setTextureName(modid + ":" + "CGCH"));
		Cpearl = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CP").setTextureName(modid + ":" + "CP"));
		Cchunk = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CC").setTextureName(modid + ":" + "CC"));
		Cingot = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("RCI").setTextureName(modid + ":" + "RCI"));
		Cplate = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CPP").setTextureName(modid + ":" + "CPP"));
		Coralium = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CG").setTextureName(modid + ":" + "CG"));
		Corb = new ItemCorb().setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("TG").setTextureName(modid + ":" + "TG");
		Corflesh = new ItemCorflesh(2, 0.1F, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("CF").setTextureName(modid + ":" + "CF");
		Corbone = new ItemCorbone(2, 0.1F, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("CB").setTextureName(modid + ":" + "CB");
		corbow = new ItemCoraliumBow(20.0F, 0, 8, 16).setUnlocalizedName("Corbow").setTextureName(modid + ":" + "Corbow");

		//Tools
		pickaxe = (new ItemDarkstonePickaxe(ToolMaterial.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DP").setTextureName(modid + ":" + "DP"));
		axe = (new ItemDarkstoneAxe(ToolMaterial.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DA").setTextureName(modid + ":" + "DA"));
		shovel = (new ItemDarkstoneShovel(ToolMaterial.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DS").setTextureName(modid + ":" + "DS"));
		sword = (new ItemDarkstoneSword(EnumToolMaterialAC.DARKSTONE).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("DSW").setTextureName(modid + ":" + "DSW"));
		hoe = (new ItemDarkstoneHoe(ToolMaterial.valueOf(ToolMaterial.class, "DARKSTONE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("DH").setTextureName(modid + ":" + "DH"));
		pickaxeA = new ItemAbyssalnitePickaxe(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AP").setTextureName(modid + ":" + "AP");
		axeA = new ItemAbyssalniteAxe(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AA").setTextureName(modid + ":" + "AA");
		shovelA = new ItemAbyssalniteShovel(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AS").setTextureName(modid + ":" + "AS");
		swordA = new ItemAbyssalniteSword(EnumToolMaterialAC.ABYSSALNITE).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("ASW").setTextureName(modid + ":" + "ASW");
		hoeA = new ItemAbyssalniteHoe(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("AH").setTextureName(modid + ":" + "AH");
		pickaxeC = new ItemAbyssalniteCPickaxe(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAP").setTextureName(modid + ":" + "CIAP");
		axeC = new ItemAbyssalniteCAxe(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAA").setTextureName(modid + ":" + "CIAA");
		shovelC = new ItemAbyssalniteCShovel(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAS").setTextureName(modid + ":" + "CIAS");
		swordC = new ItemAbyssalniteCSword(EnumToolMaterialAC.ABYSSALNITE_C).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("CIASW").setTextureName(modid + ":" + "CIASW");
		hoeC = new ItemAbyssalniteCHoe(ToolMaterial.valueOf(ToolMaterial.class, "ABYSSALNITE_C")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("CIAH").setTextureName(modid + ":" + "CIAH");
		Corpickaxe = new ItemCoraliumPickaxe(ToolMaterial.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCP").setTextureName(modid + ":" + "RCP");
		Coraxe = new ItemCoraliumAxe(ToolMaterial.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCA").setTextureName(modid + ":" + "RCA");
		Corshovel = new ItemCoraliumShovel(ToolMaterial.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCS").setTextureName(modid + ":" + "RCS");
		Corsword = new ItemCoraliumSword(EnumToolMaterialAC.CORALIUM).setCreativeTab(AbyssalCraft.tabCombat).setUnlocalizedName("RCSW").setTextureName(modid + ":" + "RCSW");
		Corhoe = new ItemCoraliumHoe(ToolMaterial.valueOf(ToolMaterial.class, "CORALIUM")).setCreativeTab(AbyssalCraft.tabTools).setUnlocalizedName("RCH").setTextureName(modid + ":" + "RCH");

		//Armor
		boots = (new AbyssalniteArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 3).setUnlocalizedName("AAB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAB"));
		helmet = (new AbyssalniteArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 0).setUnlocalizedName("AAH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAh"));
		plate = (new AbyssalniteArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 1).setUnlocalizedName("AAC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAC"));
		legs = (new AbyssalniteArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Abyssalnite"), 5, 2).setUnlocalizedName("AAP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "AAP"));
		bootsC = (new AbyssalniteCArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 3).setUnlocalizedName("ACIAB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAB"));
		helmetC = (new AbyssalniteCArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 0).setUnlocalizedName("ACIAH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAH"));
		plateC = (new AbyssalniteCArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 1).setUnlocalizedName("ACIAC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAC"));
		legsC = (new AbyssalniteCArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "AbyssalniteC"), 5, 2).setUnlocalizedName("ACIAP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACIAP"));
		bootsD = (new DreadArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Dread"), 5, 3).setUnlocalizedName("ADAB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAB"));
		helmetD = (new DreadArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Dread"), 5, 0).setUnlocalizedName("ADAH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAH"));
		plateD = (new DreadArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Dread"), 5, 1).setUnlocalizedName("ADAC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAC"));
		legsD = (new DreadArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Dread"), 5, 2).setUnlocalizedName("ADAP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADAP"));
		Corboots = (new CoraliumArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Coralium"), 5, 3).setUnlocalizedName("ACB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACB"));
		Corhelmet = (new CoraliumArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Coralium"), 5, 0).setUnlocalizedName("ACH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACH"));
		Corplate = (new CoraliumArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Coralium"), 5, 1).setUnlocalizedName("ACC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACC"));
		Corlegs = (new CoraliumArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Coralium"), 5, 2).setUnlocalizedName("ACP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACP"));
		CorbootsP = (new CoraliumPArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 3).setUnlocalizedName("ACBP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACBP"));
		CorhelmetP = (new CoraliumPArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 0).setUnlocalizedName("ACHP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACHP"));
		CorplateP = (new CoraliumPArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 1).setUnlocalizedName("ACCP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACCP"));
		CorlegsP = (new CoraliumPArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "CoraliumP"), 5, 2).setUnlocalizedName("ACPP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ACPP"));
		Depthsboots = (new DepthsArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Depths"), 5, 3).setUnlocalizedName("ADB").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADB"));
		Depthshelmet = (new DepthsArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Depths"), 5, 0).setUnlocalizedName("ADH").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADH"));
		Depthsplate = (new DepthsArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Depths"), 5, 1).setUnlocalizedName("ADC").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADC"));
		Depthslegs = (new DepthsArmor(ArmorMaterial.valueOf(ArmorMaterial.class, "Depths"), 5, 2).setUnlocalizedName("ADP").setCreativeTab(AbyssalCraft.tabCombat).setTextureName(modid + ":" + "ADP"));


		//Upgrade kits
		CobbleU = (new ItemUpgradeKit("Wood", "Cobblestone").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CobU").setTextureName(modid + ":" + "CobU"));
		IronU = (new ItemUpgradeKit("Cobblestone", "Iron").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("IroU").setTextureName(modid + ":" + "IroU"));
		GoldU = (new ItemUpgradeKit("Iron", "Gold").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("GolU").setTextureName(modid + ":" + "GolU"));
		DiamondU = (new ItemUpgradeKit("Gold", "Diamond").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("DiaU").setTextureName(modid + ":" + "DiaU"));
		AbyssalniteU = (new ItemUpgradeKit("Diamond", "Abyssalnite").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("AbyU").setTextureName(modid + ":" + "AbyU"));
		CoraliumU = (new ItemUpgradeKit("Abyssalnite", "Coralium").setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("CorU").setTextureName(modid + ":" + "CorU"));

		//Foodstuffs
		ironp = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("plate").setTextureName(modid + ":" + "plate"));
		MRE = (new ItemPlatefood(255, 1F, false).setUnlocalizedName("MRE").setTextureName(modid + ":" + "MRE"));
		chickenp = (new ItemPlatefood(12, 1.2F, false).setUnlocalizedName("ChiP").setTextureName(modid + ":" + "ChiP"));
		porkp = (new ItemPlatefood(16, 1.6F, false).setUnlocalizedName("PorP").setTextureName(modid + ":" + "PorP"));
		beefp = (new ItemPlatefood(6, 0.6F, false).setUnlocalizedName("BeeP").setTextureName(modid + ":" + "BeeP"));
		fishp = (new ItemPlatefood(10, 1.2F, false).setUnlocalizedName("FisP").setTextureName(modid + ":" + "FisP"));
		dirtyplate = (new AbyssalCraftItems().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("dirtyplate").setTextureName(modid + ":" + "dirtyplate"));
		friedegg = (new ItemFriedegg(5, 0.6F, false).setCreativeTab(AbyssalCraft.tabFood).setUnlocalizedName("friedegg").setTextureName(modid + ":" + "friedegg"));
		eggp = (new ItemPlatefood(10, 1.2F, false).setUnlocalizedName("eggp").setTextureName(modid + ":" + "eggp"));
		cloth = (new ItemWashCloth().setCreativeTab(AbyssalCraft.tabItems).setUnlocalizedName("cloth").setTextureName(modid + ":" + "cloth"));

		GameRegistry.registerTileEntity(TileEntityCrate.class, "tileEntityCrate");
		GameRegistry.registerTileEntity(TileEntityPSDL.class, "tileEntityPSDL");
		GameRegistry.registerTileEntity(TileEntityAltar.class, "tileEntityAltar");
		GameRegistry.registerTileEntity(TileEntityDGhead.class, "tileEntityDGhead");
		GameRegistry.registerTileEntity(TileEntityPhead.class, "tileEntityPhead");
		GameRegistry.registerTileEntity(TileEntityWhead.class, "tileEntityWhead");
		GameRegistry.registerTileEntity(TileEntityOhead.class, "tileEntityOhead");

		Cplague = (new PotionCplague(32, false, 0)).setIconIndex(0, 0).setPotionName("potion.Cplague");
		Dplague = (new PotionDplague(33, false, 0)).setIconIndex(0, 0).setPotionName("potion.Dplague");

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
		GameRegistry.registerBlock(abystone, "abystone");
		GameRegistry.registerBlock(abybrick, "abybrick");
		GameRegistry.registerBlock(abyslab1, ItemAbySlab.class, "abyslab1");
		GameRegistry.registerBlock(abyslab2, ItemAbySlab.class, "abyslab2");
		GameRegistry.registerBlock(abystairs, "abystairs");
		GameRegistry.registerBlock(Coraliumore, "coraliumore");
		GameRegistry.registerBlock(abyore, "abyore");
		GameRegistry.registerBlock(abyfence, "abyfence");
		GameRegistry.registerBlock(DSCwall, "dscwall");
		GameRegistry.registerBlock(ODB, ItemODB.class, "odb");
		GameRegistry.registerBlock(abyblock, "abyblock");
		GameRegistry.registerBlock(Coraliumstone, "coraliumstone");
		GameRegistry.registerBlock(ODBcore, "odbcore");
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
		GameRegistry.registerBlock(corblock, "corblock");
		GameRegistry.registerBlock(PSDL, "psdl");
		GameRegistry.registerBlock(AbyCorOre, "abycorore");
		GameRegistry.registerBlock(Altar, ItemAltar.class, "altar");
		GameRegistry.registerBlock(Abybutton, "abybutton");
		GameRegistry.registerBlock(Abypplate, "abypplate");
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

		LogHelper.info("Blocks loaded");

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

		LogHelper.info("Items loaded");

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

		BiomeEventHandler.registerBiome(Darklands, EnumBiomeType.WARM);
		BiomeEventHandler.registerBiome(DarklandsForest, EnumBiomeType.WARM);
		BiomeEventHandler.registerBiome(DarklandsPlains, EnumBiomeType.WARM);
		BiomeEventHandler.registerBiome(DarklandsHills, EnumBiomeType.WARM);
		BiomeEventHandler.registerBiome(DarklandsMountains, EnumBiomeType.WARM);
		BiomeEventHandler.registerBiome(corswamp, EnumBiomeType.WARM);
		BiomeEventHandler.registerBiome(corocean, EnumBiomeType.WARM);

		BiomeManager.addSpawnBiome(AbyssalCraft.Darklands);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsForest);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsPlains);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsHills);
		BiomeManager.addSpawnBiome(AbyssalCraft.DarklandsMountains);
		BiomeManager.addSpawnBiome(AbyssalCraft.corswamp);
		BiomeManager.addSpawnBiome(AbyssalCraft.corocean);

		//Dimension
		DimensionManager.registerProviderType(dimension, WorldProviderAbyss.class, true);
		DimensionManager.registerDimension(dimension, dimension);
		DimensionManager.registerProviderType(dimension2, WorldProviderDreadlands.class, true);
		DimensionManager.registerDimension(dimension2, dimension2);

		LogHelper.info("Preparing entities");

		// Mobs
		EntityRegistry.registerModEntity(EntityDepthsghoul.class, "depthsghoul", 25, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDepthsghoul.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase []
				{
			BiomeGenBase.swampland, BiomeGenBase.ocean, BiomeGenBase.beach, 
			BiomeGenBase.river,AbyssalCraft.Darklands, AbyssalCraft.Wastelands,
			AbyssalCraft.DarklandsForest, AbyssalCraft.DarklandsHills,
			AbyssalCraft.DarklandsPlains});
		registerEntityEgg(EntityDepthsghoul.class, 0x36A880, 0x012626);

		EntityRegistry.registerModEntity(Entityevilpig.class, "evilpig", 26, this, 80, 3, true);
		EntityRegistry.addSpawn(Entityevilpig.class, 5, 1, 5, EnumCreatureType.creature, new BiomeGenBase []
				{
			BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest,
			BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle,
			BiomeGenBase.swampland, BiomeGenBase.icePlains });
		registerEntityEgg(Entityevilpig.class, 0xE19B98, 0xAD4E4B);

		EntityRegistry.registerModEntity(EntityDepthsZombie.class, "abyssalzombie", 27, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDepthsZombie.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase []
				{
			BiomeGenBase.ocean, BiomeGenBase.beach, BiomeGenBase.river,
			BiomeGenBase.jungle, BiomeGenBase.swampland,
			BiomeGenBase.sky, AbyssalCraft.Darklands, AbyssalCraft.Wastelands,
			AbyssalCraft.DarklandsForest, AbyssalCraft.DarklandsHills,
			AbyssalCraft.DarklandsPlains});
		registerEntityEgg(EntityDepthsZombie.class, 0x36A880, 0x052824);

		EntityRegistry.registerModEntity(EntityODBPrimed.class, "Primed ODB", 28, this, 80, 3, true);

		EntityRegistry.registerModEntity(EntityJzahar.class, "Jzahar", 29, this, 80, 3, true);
		registerEntityEgg(EntityJzahar.class, 0x133133, 0x342122);

		EntityRegistry.registerModEntity(Entityabygolem.class, "abygolem", 30, this, 80, 3, true);
		EntityRegistry.addSpawn(Entityabygolem.class, 5, 1, 5, EnumCreatureType.creature, new BiomeGenBase []
				{
			AbyssalCraft.AbyDreadlands });
		registerEntityEgg(Entityabygolem.class, 0x8A00E6, 0x6100A1);

		EntityRegistry.registerModEntity(Entitydreadgolem.class, "dreadgolem", 31, this, 80, 3, true);
		EntityRegistry.addSpawn(Entitydreadgolem.class, 5, 1, 5, EnumCreatureType.monster, new BiomeGenBase []
				{
			AbyssalCraft.Dreadlands });
		registerEntityEgg(Entitydreadgolem.class, 0x1E60000, 0xCC0000);

		EntityRegistry.registerModEntity(Entitydreadguard.class, "dreadguard", 32, this, 80, 3, true);
		registerEntityEgg(Entitydreadguard.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityPSDLTracker.class, "PowerstoneTracker", 33, this, 64, 10, true);

		EntityRegistry.registerModEntity(EntityDragonMinion.class, "dragonminion", 34, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDragonMinion.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase []
				{
			AbyssalCraft.Wastelands });
		registerEntityEgg(EntityDragonMinion.class, 0x433434, 0x344344);

		EntityRegistry.registerModEntity(EntityDragonBoss.class, "dragonboss", 35, this, 64, 10, true);
		registerEntityEgg(EntityDragonBoss.class, 0x476767, 0x768833);

		EntityRegistry.registerModEntity(EntityODBcPrimed.class, "Primed ODB Core", 36, this, 80, 3, true);

		EntityRegistry.registerModEntity(EntityODBMeteor.class, "ODBMeteor", 37, this, 64, 10, true);

		EntityRegistry.registerModEntity(EntityShadowCreature.class, "shadowcreature", 38, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityShadowCreature.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase []
				{
			AbyssalCraft.DarklandsMountains });
		registerEntityEgg(EntityShadowCreature.class, 0, 0xFFFFFF);

		EntityRegistry.registerModEntity(EntityShadowMonster.class, "shadowmonster", 39, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityShadowMonster.class, 2, 1, 2, EnumCreatureType.monster, new BiomeGenBase []
				{
			AbyssalCraft.DarklandsMountains });
		registerEntityEgg(EntityShadowMonster.class, 0, 0xFFFFFF);

		EntityRegistry.registerModEntity(EntityDreadling.class, "dreadling", 40, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDreadling.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase []
				{
			AbyssalCraft.Dreadlands });
		registerEntityEgg(EntityDreadling.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDreadSpawn.class, "dreadspawn", 41, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDreadSpawn.class, 3, 1, 3, EnumCreatureType.monster, new BiomeGenBase []
				{
			AbyssalCraft.ForestDreadlands });
		registerEntityEgg(EntityDreadSpawn.class, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDemonPig.class, "demonpig", 42, this, 80, 3, true);
		EntityRegistry.addSpawn(EntityDemonPig.class, 2, 1, 3, EnumCreatureType.monster, new BiomeGenBase []
				{
			BiomeGenBase.hell });
		registerEntityEgg(EntityDemonPig.class, 0xE19B98, 0xAD4E4B);

		LogHelper.info("Entities loaded");

		proxy.addArmor("Abyssalnite");
		proxy.addArmor("AbyssalniteC");
		proxy.addArmor("Dread");
		proxy.addArmor("Coralium");
		proxy.addArmor("CoraliumP");
		proxy.addArmor("Depths");

	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		//start, mineDS, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah, enterdreadlands, killdreadguard, ghoulhead, killPete, killWilson, killOrange, petehead, wilsonhead, orangehead, mineCorgem, mineCorore, findPSDL, GK1, Gk2, Gk3, Jzhstaff

		//Achievements	    
		mineDS = new Achievement("achievement.mineDS", "mineDS", 0, 0, AbyssalCraft.Darkstone_cobble, AchievementList.openInventory).registerStat();
		mineAby = new Achievement("achievement.mineAby", "mineAby", 3, 0, AbyssalCraft.abychunk, AbyssalCraft.mineDS).registerStat();
		killghoul = new Achievement("achievement.killghoul", "killghoul", -3, 0, AbyssalCraft.Corbone, AbyssalCraft.mineDS).registerStat();
		enterabyss = new Achievement("achievement.enterabyss", "enterabyss", 0, 3, AbyssalCraft.portal, AbyssalCraft.mineDS).setSpecial().registerStat();
		killdragon = new Achievement("achievement.killdragon", "killdragon", 3, 3, AbyssalCraft.Corflesh, AbyssalCraft.enterabyss).registerStat();
		summonAsorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 6, AbyssalCraft.Altar, AbyssalCraft.enterabyss).registerStat();
		killAsorah = new Achievement("achievement.killAsorah", "killAsorah", 3, 6, AbyssalCraft.EoA, AbyssalCraft.summonAsorah).setSpecial().registerStat();
		enterdreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 3, 9, AbyssalCraft.dreadportal, AbyssalCraft.killAsorah).setSpecial().registerStat();
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
		GK3 = new Achievement("achievement.GK3", "GK3", 0, -7, AbyssalCraft.portalPlacer, AbyssalCraft.GK2).registerStat();
		Jzhstaff = new Achievement("achievement.Jzhtaff", "Jzhtaff", 0, -9, AbyssalCraft.Staff, AbyssalCraft.GK3).setSpecial().registerStat();
		secret1 = new Achievement("achievement.secret1", "secret1", 9, -9, AbyssalCraft.devsword, AchievementList.openInventory).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[]{mineDS, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah, enterdreadlands, killdreadguard, ghoulhead, killPete, killWilson, killOrange, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, Jzhstaff, secret1}));

		proxy.init();
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.func_143046_a();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
		LogHelper.info("Preparing recipes");
		AbyssalCrafting.addRecipes();
		LogHelper.info("Recipes loaded");
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
		OreDictionary.registerOre("oreCoraliumStone", Coraliumstone);
		OreDictionary.registerOre("gemShadow", shadowgem);
		OreDictionary.registerOre("liquidCoralium", Cwater);
		OreDictionary.registerOre("materialCoraliumPearl", Cpearl);
		OreDictionary.registerOre("liquidAntimatter", anticwater);
	}

	public static int getUniqueEntityId() 
	{
		do 
		{
			startEntityId++;
		} 
		while (EntityList.getStringFromID(startEntityId) != null);

		return startEntityId;
	}

	@SuppressWarnings("unchecked")
	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor) 
	{
		int id = getUniqueEntityId();
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
		packetPipeline.postInitialise();
		LogHelper.info("AbyssalCraft done loading");
	}
}