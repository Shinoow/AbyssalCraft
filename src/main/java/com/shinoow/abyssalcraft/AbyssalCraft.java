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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.stats.Achievement;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;

import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.handlers.IMCHandler;
import com.shinoow.abyssalcraft.init.*;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version,dependencies = "required-after:Forge@[forgeversion,);after:JEI@[3.6.8,)", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory", acceptedMinecraftVersions = "[1.9.4]", updateJSON = "https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/version.json")
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

	static { FluidRegistry.enableUniversalBucket(); }

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		for(ILifeCycleHandler handler : handlers)
			handler.preInit(event);
		proxy.preInit();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.init();
		for(ILifeCycleHandler handler : handlers)
			handler.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		for(ILifeCycleHandler handler : handlers)
			handler.postInit(event);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event){
		for(ILifeCycleHandler handler : handlers)
			handler.loadComplete(event);
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

	//TODO remove all of this around AC 1.9.4 or 1.9.5
	@Deprecated
	public static DimensionType THE_ABYSSAL_WASTELAND, THE_DREADLANDS, OMOTHOL, THE_DARK_REALM;
	@Deprecated
	public static int evilAnimalSpawnWeight, endAbyssalZombieSpawnWeight, portalCooldown, demonAnimalSpawnWeight, shoggothLairSpawnRate;
	@Deprecated
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre, generateDreadlandsAbyssalniteOre,
	generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre, generateAbyssalDiamondOre, generateAbyssalNitreOre,
	generateAbyssalTinOre, generateAbyssalCopperOre, generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre, generateDarklandsStructures,
	generateShoggothLairs, generateAbyssalWastelandPillars, generateAbyssalWastelandRuins, generateAntimatterLake, generateCoraliumLake,
	generateDreadlandsStalagmite,shoggothOoze, oozeLeaves, oozeGrass, oozeGround, oozeSand, oozeRock, oozeCloth, oozeWood, oozeGourd, oozeIron,
	oozeClay, oozeExpire, shouldSpread, shouldInfect, breakLogic, destroyOcean, demonAnimalFire, darkness, particleBlock, particleEntity,
	hardcoreMode, evilAnimalCreatureType, antiItemDisintegration, smeltingRecipes, keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;
	@Deprecated
	public static Achievement mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah, enterdreadlands, killdreadguard,
	ghoulhead, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth,
	enterOmothol, enterDarkRealm, killJzahar, killOmotholelite, locateJzahar, necro, necrou1, necrou2, necrou3, abyssaln, ritual,
	ritualSummon, ritualCreate, shadowGems, mineAbyOres, mineDread, dreadium, eth, makeTransmutator, makeCrystallizer, makeMaterializer,
	makeCrystalBag, makeEngraver, ritualBreed, ritualPotion, ritualPotionAoE, ritualInfusion;
}