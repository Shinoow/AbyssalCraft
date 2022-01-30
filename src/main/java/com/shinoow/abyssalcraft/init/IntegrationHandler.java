/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.shinoow.abyssalcraft.api.integration.ACPlugin;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.common.util.ACLogger;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;

public class IntegrationHandler implements ILifeCycleHandler {

	boolean isInvTweaksLoaded = Loader.isModLoaded("inventorytweaks");
	boolean isJEILoaded = Loader.isModLoaded("jei");

	List<String> mods = new ArrayList<>();
	List<IACPlugin> integrations = new ArrayList<>();
	List<IACPlugin> temp = new ArrayList<>();

	/**
	 * Attempts to find mod integrations.
	 */
	private void findIntegrations(ASMDataTable asmDataTable){
		ACLogger.info("Starting the Integration Handler.");

		fetchModIntegrations(asmDataTable);

		if(!temp.isEmpty())
			ACLogger.info("Preliminary integration search complete: found {} possible mod integration(s)!", temp.size());
	}

	private void fetchModIntegrations(ASMDataTable asmDataTable){
		List<IACPlugin> plugins = fetchPlugins(asmDataTable, ACPlugin.class, IACPlugin.class);
		if(!plugins.isEmpty())
			for(IACPlugin plugin : plugins)
				temp.add(plugin);
	}

	private <T> List<T> fetchPlugins(ASMDataTable asmDataTable, Class annotationClass, Class<T> instanceClass){
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
	private void search(){
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
					ACLogger.info("Found a integration for mod {}", plugin.getModName());
					integrations.add(plugin);
					mods.add(plugin.getModName());
				}

			temp.clear();
		}

		if(!mods.isEmpty())
			ACLogger.info("Mod integrations found: {}", mods);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event){
		findIntegrations(event.getAsmData());
	}

	@Override
	public void init(FMLInitializationEvent event){
		search();
		if(!integrations.isEmpty()){
			ACLogger.info("Initializing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.init();
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event){
		if(!integrations.isEmpty()){
			ACLogger.info("Post-initializing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.postInit();
		}
	}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}
}
