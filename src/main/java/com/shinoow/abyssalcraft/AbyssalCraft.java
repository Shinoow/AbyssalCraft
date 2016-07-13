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
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.init.*;
import com.shinoow.abyssalcraft.lib.ACLib;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version,dependencies = "required-after:Forge@[forgeversion,);after:JEI@[2.28,)", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory", acceptedMinecraftVersions = "[1.8.9]", updateJSON = "https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/version.json")
public class AbyssalCraft {

	public static final String version = "1.9.2.5";
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

	private static List<ILifeCycleHandler> handlers = new ArrayList<ILifeCycleHandler>();

	static {
		handlers.add(new BlockHandler());
		handlers.add(new WorldHandler());
		handlers.add(new ItemHandler());
		handlers.add(new MiscHandler());
		handlers.add(new EntityHandler());
		handlers.add(new IntegrationHandler());
	}

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

	//"secret" dev stuff
	public static Item devsword;
	//shadow items
	public static Item shadowPlate;

	@Deprecated
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
	particleBlock, particleEntity, hardcoreMode, useDynamicPotionIds, evilAnimalCreatureType,
	antiItemDisintegration, abyssalZombiesPickupRottenFlesh;
	public static int evilAnimalSpawnWeight, endAbyssalZombieSpawnWeight, portalCooldown, demonAnimalSpawnWeight;
	public static boolean shoggothOoze, oozeLeaves, oozeGrass, oozeGround, oozeSand, oozeRock, oozeCloth, oozeWood,
	oozeGourd, oozeIron, oozeClay;
	public static boolean generateDarklandsStructures, generateShoggothLairs, generateAbyssalWastelandPillars,
	generateAbyssalWastelandRuins, generateAntimatterLake, generateCoraliumLake, generateDreadlandsStalagmite;
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre,
	generateDreadlandsAbyssalniteOre, generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre,
	generateAbyssalDiamondOre, generateAbyssalNitreOre, generateAbyssalTinOre, generateAbyssalCopperOre,
	generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre;

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

		for(ILifeCycleHandler handler : handlers)
			handler.preInit(event);
		proxy.preInit();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

		ACLogger.info("Initializing AbyssalCraft.");
		proxy.init();
		for(ILifeCycleHandler handler : handlers)
			handler.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		ACLogger.info("Post-initializing AbyssalCraft");
		proxy.postInit();
		for(ILifeCycleHandler handler : handlers)
			handler.postInit(event);
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
					EntityUtil.addShoggothFood((Class<? extends EntityLivingBase>)Class.forName(imcMessage.getStringValue()));
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
					if(crystal == null)
						failed = true;
					if(!failed)
						AbyssalCraftAPI.addCrystal(crystal);
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Crystal addition from mod %s", imcMessage.getSender());
				else ACLogger.imcInfo("Received Crystal addition from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp"))
						failed = true;
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output1 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output1"));
					ItemStack output2 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output2"));
					if(input == null || output1 == null || output2 == null)
						failed = true;
					if(!failed)
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
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if(input == null || output == null)
						failed = true;
					if(!failed)
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
					if(!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp"))
						failed = true;
					String input = stuff.getString("input");
					String output1 = stuff.getString("output1");
					String output2 = stuff.getString("output2");
					if(input == null || output1 == null || output2 == null)
						failed = true;
					if(!failed)
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
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if(input == null || output == null)
						failed = true;
					if(!failed)
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
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if(input == null || output == null)
						failed = true;
					if(!failed)
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
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if(input == null || output == null)
						failed = true;
					if(!failed)
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
					if(!stuff.hasKey("input1") || !stuff.hasKey("output"))
						failed = true;
					ItemStack input1 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input1"));
					ItemStack input2 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input2"));
					ItemStack input3 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input3"));
					ItemStack input4 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input4"));
					ItemStack input5 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input5"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if(input1 == null || output == null)
						failed = true;
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
					if(!failed)
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
			else if(imcMessage.key.equals("addGhoulArmor")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack helmet = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("helmet"));
					ItemStack chestplate = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("chestplate"));
					ItemStack leggings = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("leggings"));
					ItemStack boots = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("boots"));
					if(helmet != null && chestplate != null && leggings != null && boots != null){
						if(tag.hasKey("res1") && tag.hasKey("res2"))
							AbyssalCraftAPI.addGhoulArmorTextures(helmet.getItem(), chestplate.getItem(), leggings.getItem(), boots.getItem(), tag.getString("res1"), tag.getString("res2"));
						else failed = true;
					} else failed = true;
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Ghoul Armor Texture Registration from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Ghoul Armor Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulHelmet")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack helmet = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("helmet"));
					if(helmet != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulHelmetTexture(helmet.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Ghoul Helmet Texture Registration from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Ghoul Helmet Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulChestplate")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack chestplate = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("chestplate"));
					if(chestplate != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulChestplateTexture(chestplate.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Ghoul Chestplate Texture Registration from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Ghoul Chestplate Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulLeggings")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack leggings = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("leggings"));
					if(leggings != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulLeggingsTexture(leggings.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Ghoul Leggings Texture Registration from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Ghoul Leggings Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulBoots")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack boots = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("boots"));
					if(boots != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulBootsTexture(boots.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					ACLogger.imcWarning("Received invalid Ghoul Boots Texture Registration from mod %s!", imcMessage.getSender());
				else ACLogger.imcInfo("Received Ghoul Boots Texture Registration from mod %s", imcMessage.getSender());
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

		ACLib.abyssal_wasteland_id = cfg.get("dimensions", "The Abyssal Wasteland", 50, "The first dimension, full of undead monsters.").getInt();
		ACLib.dreadlands_id = cfg.get("dimensions", "The Dreadlands", 51, "The second dimension, infested with mutated monsters.").getInt();
		ACLib.omothol_id = cfg.get("dimensions", "Omothol", 52, "The third dimension, also known as \u00A7oThe Realm of J'zahar\u00A7r.").getInt();
		ACLib.dark_realm_id = cfg.get("dimensions", "The Dark Realm", 53, "Hidden fourth dimension, reached by falling down from Omothol").getInt();

		configDimId1 = ACLib.abyssal_wasteland_id;
		configDimId2 = ACLib.dreadlands_id;
		configDimId3 = ACLib.omothol_id;
		configDimId4 = ACLib.dark_realm_id;

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
		evilAnimalSpawnWeight = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animal spawn weight", 20, "Spawn weight for the Evil Animals (Pigs, Cows, Chickens), keep under 35 to avoid complete annihilation.").getInt();
		darkness = cfg.get(Configuration.CATEGORY_GENERAL, "Darkness", true, "Set to false to disable the random blindness within Darklands biomes").getBoolean();
		particleBlock = cfg.get(Configuration.CATEGORY_GENERAL, "Block particles", true, "Toggles whether blocks that emits particles should do so.").getBoolean();
		particleEntity = cfg.get(Configuration.CATEGORY_GENERAL, "Entity particles", true, "Toggles whether entities that emits particles should do so.").getBoolean();
		hardcoreMode = cfg.get(Configuration.CATEGORY_GENERAL, "Hardcore Mode", false, "Toggles Hardcore mode. If set to true, all mobs will become tougher.").getBoolean();
		endAbyssalZombieSpawnWeight = cfg.get(Configuration.CATEGORY_GENERAL, "End Abyssal Zombie spawn weight", 10, "Spawn weight for Abyssal Zombies in The End. Setting to 0 will stop them from spawning altogether.", 0, 10).getInt();
		evilAnimalCreatureType = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animals Are Monsters", false, "If enabled, sets the creature type of Evil Animals to \"monster\". The creature type affects how a entity spawns, eg \"creature\" "
				+ "treats the entity as an animal, while \"monster\" treats it as a hostile mob. If you enable this, Evil Animals will spawn like any other hostile mobs, instead of mimicking vanilla animals.\n"
				+EnumChatFormatting.RED+"[Minecraft Restart Required]"+EnumChatFormatting.RESET).getBoolean();
		antiItemDisintegration = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Antimatter item disintegration", true, "Toggles whether or not Liquid Antimatter will disintegrate any items dropped into a pool of it.").getBoolean();
		portalCooldown = cfg.get(Configuration.CATEGORY_GENERAL, "Portal cooldown", 10, "Cooldown after using a portal, increasing the value increases the delay until you can teleport again. Measured in ticks (20 ticks = 1 second).", 10, 300).getInt();
		demonAnimalSpawnWeight = cfg.get(Configuration.CATEGORY_GENERAL, "Demon Animal spawn weight", 30, "Spawn weight for the Demon Animals (Pigs, Cows, Chickens) spawning in the Nether.").getInt();
		abyssalZombiesPickupRottenFlesh = cfg.get(Configuration.CATEGORY_GENERAL, "Abyssal Zombies picking up Rotten Flesh", true, "Toggles whether or not Abyssal Zombies should pick up Rotten Flesh (entities holding items don't despawn)").getBoolean();

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