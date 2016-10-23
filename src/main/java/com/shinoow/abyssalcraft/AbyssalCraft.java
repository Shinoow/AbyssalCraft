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

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.potion.PotionType;
import net.minecraft.stats.Achievement;
import net.minecraft.util.*;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.init.*;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version,dependencies = "required-after:Forge@[forgeversion,);after:JEI@[3.3.3,)", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory", acceptedMinecraftVersions = "[1.9]", updateJSON = "https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/version.json")
public class AbyssalCraft {

	public static final String version = "ac_version";
	public static final String modid = "abyssalcraft";
	public static final String name = "AbyssalCraft";

	@Metadata(AbyssalCraft.modid)
	public static ModMetadata metadata;

	@Instance(AbyssalCraft.modid)
	public static AbyssalCraft instance = new AbyssalCraft();

	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.client.ClientProxy",
			serverSide = "com.shinoow.abyssalcraft.common.CommonProxy")
	public static CommonProxy proxy;

	private static List<ILifeCycleHandler> handlers = new ArrayList<ILifeCycleHandler>(){{
		add(InitHandler.INSTANCE);
		add(new BlockHandler());
		add(new WorldHandler());
		add(new ItemHandler());
		add(new MiscHandler());
		add(new EntityHandler());
		add(new IntegrationHandler());
	}};

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
	ritualBreed, ritualPotion, ritualPotionAoE, ritualInfusion;

	public static Block Darkbrickslab2, Darkcobbleslab2, abyslab2, Darkstoneslab2, DLTslab2,
	Altar, dreadbrickslab2, abydreadbrickslab2, cstonebrickslab2, ethaxiumslab2, house,
	darkethaxiumslab2;

	//"secret" dev stuff
	public static Item devsword;
	//shadow items
	public static Item shadowPlate;

	public static PotionType Cplague_normal, Cplague_long, Dplague_normal, Dplague_long,
	Dplague_strong, antiMatter_normal, antiMatter_long;

	public static boolean keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;

	public static boolean shouldSpread, shouldInfect, breakLogic, destroyOcean, demonAnimalFire, darkness,
	particleBlock, particleEntity, hardcoreMode, useDynamicPotionIds, evilAnimalCreatureType,
	antiItemDisintegration;
	public static int evilAnimalSpawnWeight, endAbyssalZombieSpawnWeight, portalCooldown, demonAnimalSpawnWeight, shoggothLairSpawnRate;
	public static boolean shoggothOoze, oozeLeaves, oozeGrass, oozeGround, oozeSand, oozeRock, oozeCloth, oozeWood,
	oozeGourd, oozeIron, oozeClay, oozeExpire;
	public static boolean generateDarklandsStructures, generateShoggothLairs, generateAbyssalWastelandPillars,
	generateAbyssalWastelandRuins, generateAntimatterLake, generateCoraliumLake, generateDreadlandsStalagmite;
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre,
	generateDreadlandsAbyssalniteOre, generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre,
	generateAbyssalDiamondOre, generateAbyssalNitreOre, generateAbyssalTinOre, generateAbyssalCopperOre,
	generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre;

	public static DimensionType THE_ABYSSAL_WASTELAND, THE_DREADLANDS, OMOTHOL, THE_DARK_REALM;

	public static SoundEvent dreadguard_ambient, dreadguard_hurt, dreadguard_death, ghoul_normal_ambient,
	ghoul_hurt, ghoul_death, ghoul_pete_ambient, ghoul_wilson_ambient, ghoul_orange_ambient, golem_death,
	golem_hurt, golem_ambient, sacthoth_death, shadow_death, shadow_hurt, remnant_scream, remnant_yes,
	remnant_no, remnant_priest_chant, shoggoth_ambient, shoggoth_hurt, shoggoth_death, jzahar_charge,
	cthulhu_chant, yog_sothoth_chant_1, yog_sothoth_chant_2, hastur_chant_1, hastur_chant_2, sleeping_chant,
	cthugha_chant, dread_spawn_ambient, dread_spawn_hurt, dread_spawn_death;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		ACLogger.info("Pre-initializing AbyssalCraft.");
		instance = this;

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
		InitHandler.INSTANCE.serverStart(event);
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void handleIMC(FMLInterModComms.IMCEvent event){
		IMCHandler.handleIMC(event);
	}

	/**
	 * Checks whether or not an Item is blacklisted for the specified Entity
	 * @param entity Entity to check
	 * @param stack ItemStack to check
	 * @return True if the Item is blacklisted, otherwise false
	 */
	public static boolean isItemBlacklisted(Entity entity, ItemStack stack){
		return InitHandler.isItemBlacklisted(entity, stack);
	}


}