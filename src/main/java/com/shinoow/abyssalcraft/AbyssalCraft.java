/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.event.*;

import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.handlers.IMCHandler;
import com.shinoow.abyssalcraft.init.*;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version,dependencies = "required-after:Forge@[forgeversion,);after:JEI@[3.13.6,)", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory", acceptedMinecraftVersions = "[1.10.2]", updateJSON = "https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/version.json")
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
		handlers.forEach(handler -> handler.preInit(event));
		proxy.preInit();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.init();
		handlers.forEach(handler -> handler.init(event));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		handlers.forEach(handler -> handler.postInit(event));
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event){
		handlers.forEach(handler -> handler.loadComplete(event));
	}

	@EventHandler
	public void serverStart(FMLServerAboutToStartEvent event){
		InitHandler.INSTANCE.serverStart(event);
	}

	@EventHandler
	public void handleIMC(FMLInterModComms.IMCEvent event){
		IMCHandler.handleIMC(event);
	}
}
