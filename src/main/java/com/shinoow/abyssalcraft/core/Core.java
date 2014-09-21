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

import java.io.File;
import java.util.*;

import net.minecraftforge.common.config.Configuration;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.*;
import com.shinoow.abyssalcraft.core.common.CommonProxy;
import com.shinoow.abyssalcraft.core.handlers.CraftingHandler;
import com.shinoow.abyssalcraft.core.util.CoreLogger;

import cpw.mods.fml.client.*;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Core extends DummyModContainer {

	public static final String version = "1.2.0";
	public static final String modid = "accore";
	public static final String name = "AbyssalCraft Core";

	public static Configuration cfg;

	public static boolean canRenderStarspawn;

	public Core() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId       = modid;
		meta.name        = name;
		meta.version     = version;
		meta.credits     = "shinoow (coding)";
		meta.authorList  = Arrays.asList("shinoow");
		meta.description = "Library used by AbyssalCraft mods - contains API as well";
		meta.url         = "https://shinoow.github.io/AbyssalCraft/";
		meta.updateUrl   = "https://shinoow.github.io/AbyssalCraft/";
		meta.screenshots = new String[0];
		meta.logoFile    = "assets/abyssalcraft/textures/logo_core.png";
	}

	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.core.client.ClientProxy",
			serverSide = "com.shinoow.abyssalcraft.core.common.CommonProxy")
	public static CommonProxy proxy;

	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {

		CoreLogger.info("Pre-initializing Core.");
		FMLCommonHandler.instance().bus().register(new CraftingHandler());

		cfg = new Configuration(event.getSuggestedConfigurationFile());

		syncConfig();

		proxy.PreInit();
	}

	@Subscribe
	public void Init(FMLInitializationEvent event) {

		CoreLogger.info("Initializing Core.");

		proxy.Init();
		proxy.registerRenderThings();
	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent event) {

		CoreLogger.info("Post-initializing Core.");
		proxy.PostInit();
		CoreLogger.info("Core loaded.");
	}

	public static void syncConfig(){

		canRenderStarspawn = cfg.getBoolean("RenderPlayer Override", "render", true, "Whether or not to override the player model"
				+ "(set false for compatibility with other mods that alters the player model)");

		if(cfg.hasChanged())
			cfg.save();
		if(canRenderStarspawn == true)
			CoreLogger.info("RenderPlayer Override enabled, the Coralium Longbow will render twice in your hand now.");
		else CoreLogger.info("RenderPlayer Override disabled, Compatibility level +100.");

	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.modID.equals("accore"))
			syncConfig();
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Override
	public File getSource() {
		return ACCorePlugin.coreLocation;
	}

	@Override
	public Class<?> getCustomResourcePackClass() {
		return getSource().isDirectory() ? FMLFolderResourcePack.class : FMLFileResourcePack.class;
	}

	@Override
	public String getGuiClassName()
	{
		return "com.shinoow.abyssalcraft.core.client.config.ACCoreGuiFactory";
	}

	@Override
	public List<String> getOwnedPackages() {
		return ImmutableList.of(
				"com.shinoow.abyssalcraft.core",
				"com.shinoow.abyssalcraft.core.api",
				"com.shinoow.abyssalcraft.core.api.addon",
				"com.shinoow.abyssalcraft.core.api.entity",
				"com.shinoow.abyssalcraft.core.api.item",
				"com.shinoow.abyssalcraft.core.api.render",
				"com.shinoow.abyssalcraft.core.client",
				"com.shinoow.abyssalcraft.core.client.config",
				"com.shinoow.abyssalcraft.core.client.model",
				"com.shinoow.abyssalcraft.core.client.render",
				"com.shinoow.abyssalcraft.core.common",
				"com.shinoow.abyssalcraft.core.handlers",
				"com.shinoow.abyssalcraft.core.util",
				"com.shinoow.abyssalcraft.core.util.recipes"
				);
	}
}