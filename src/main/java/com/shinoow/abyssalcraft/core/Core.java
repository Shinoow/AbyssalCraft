/**AbyssalCraft Core
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
package com.shinoow.abyssalcraft.core;

import com.shinoow.abyssalcraft.core.common.CommonProxy;
import com.shinoow.abyssalcraft.core.handlers.CraftingHandler;
import com.shinoow.abyssalcraft.core.network.PacketPipeline;
import com.shinoow.abyssalcraft.core.util.CoreLogger;

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

@Mod(modid = Core.modid, name = Core.name, version = Core.version, useMetadata = true)
public class Core {

	public static final String version = "1.1.0";
	public static final String modid = "accore";
	public static final String name = "AbyssalCraft Core";

	@Metadata(Core.modid)
	public static ModMetadata metadata;

	@Instance(Core.modid)
	public static Core instance = new Core();

	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.core.client.ClientProxy",
			serverSide = "com.shinoow.abyssalcraft.core.common.CommonProxy")
	public static CommonProxy proxy;

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		CoreLogger.info("Pre-initializing Core.");
		metadata = event.getModMetadata();
		packetPipeline.initialise();
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		instance = this;
		proxy.PreInit();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

		CoreLogger.info("Initializing Core.");
		proxy.Init();
		proxy.registerRenderThings();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		CoreLogger.info("Post-initializing Core.");
		packetPipeline.postInitialise();
		proxy.PostInit();
		CoreLogger.info("Core loaded.");
	}
}