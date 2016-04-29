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
package com.shinoow.abyssalcraft.common.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import com.shinoow.abyssalcraft.api.integration.ACPlugin;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.common.util.ACLogger;

public class IntegrationHandler {

	static boolean isInvTweaksLoaded = Loader.isModLoaded("inventorytweaks");
	static boolean isJEILoaded = Loader.isModLoaded("JEI");

	static List<String> mods = new ArrayList<String>();
	static List<IACPlugin> integrations = new ArrayList<IACPlugin>();
	static List<IACPlugin> temp = new ArrayList<IACPlugin>();

	/**
	 * Attempts to find mod integrations.
	 */
	private static void findIntegrations(ASMDataTable asmDataTable){
		ACLogger.info("Starting the Integration Handler.");

		fetchModIntegrations(asmDataTable);

		if(!temp.isEmpty())
			ACLogger.info("Preliminary integration search complete: found %d possible mod integration(s)!", temp.size());
	}

	private static void fetchModIntegrations(ASMDataTable asmDataTable){
		List<IACPlugin> plugins = fetchPlugins(asmDataTable, ACPlugin.class, IACPlugin.class);
		if(!plugins.isEmpty())
			for(IACPlugin plugin : plugins)
				temp.add(plugin);
	}

	private static <T> List<T> fetchPlugins(ASMDataTable asmDataTable, Class annotationClass, Class<T> instanceClass){
		String annotationClassName = annotationClass.getCanonicalName();
		Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationClassName);
		List<T> instances = new ArrayList<>();
		for (ASMDataTable.ASMData asmData : asmDatas)
			try {
				Class<?> asmClass = Class.forName(asmData.getClassName());
				Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
				T instance = asmInstanceClass.newInstance();
				instances.add(instance);
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				ACLogger.severe("Failed to load: {}", asmData.getClassName(), e);
			}
		return instances;
	}

	/**
	 * Does the initial search for integrations (eg. go over found plugins and scan ones registered through the API)
	 */
	private static void search(){
		if(isInvTweaksLoaded){
			ACLogger.info("Inventory Tweaks is present, initializing sorting stuff.");
			mods.add("Inventory Tweaks");
		}
		if(isJEILoaded){
			ACLogger.info("Just Enough Items is present, initializing informative stuff.");
			mods.add("Just Enough Items");
		}

		if(!temp.isEmpty()){
			for(IACPlugin plugin : temp)
				if(plugin.canLoad()){
					ACLogger.info("Found a integration for mod %s", plugin.getModName());
					integrations.add(plugin);
					mods.add(plugin.getModName());
				}

			temp.clear();
		}

		if(!mods.isEmpty())
			ACLogger.info("Mod integrations found: %s", mods);
	}

	public static void preInit(ASMDataTable asmDataTable){
		findIntegrations(asmDataTable);
	}

	public static void init(){
		search();
		if(!integrations.isEmpty()){
			ACLogger.info("Initializing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.init();
		}
	}

	public static void postInit(){
		if(!integrations.isEmpty()){
			ACLogger.info("Post-initializing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.postInit();
		}
	}
}