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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.integration.morph.ACMorph;

import cpw.mods.fml.common.Loader;

public class IntegrationHandler {

	static boolean isNEILoaded = Loader.isModLoaded("NotEnoughItems");
	static boolean isMorhpLoaded = Loader.isModLoaded("Morph");
	static boolean isInvTweaksLoaded = Loader.isModLoaded("inventorytweaks");

	static List<String> mods = new ArrayList<String>();
	static List<IACPlugin> integrations = new ArrayList<IACPlugin>();

	/**
	 * Attempts to find mod integrations.
	 */
	private static void findIntegrations(){
		ACLogger.info("Checking possible mod integrations.");
		if(isNEILoaded){
			ACLogger.info("Not Enough Items is present, initializing informative stuff.");
			//This part is handled by NEI, so this message is essentially useless :P
			mods.add("Not Enough Items");
		}
		if(isMorhpLoaded){
			ACLogger.info("Morph is present, initializing weird shape-shifting stuff.");
			integrations.add(new ACMorph());
			mods.add("Morph");
		}
		if(isInvTweaksLoaded){
			ACLogger.info("Inventory Tweaks is present, initializing sorting stuff.");
			mods.add("Inventory Tweaks");
		}
		if(!AbyssalCraftAPI.getIntegrations().isEmpty()){
			ACLogger.info("Searching the AbyssalCraftAPI list for integrations.");
			for(IACPlugin plugin : AbyssalCraftAPI.getIntegrations()){
				ACLogger.info("Found a integration for mod %s", plugin.getModName());
				integrations.add(plugin);
				mods.add(plugin.getModName());
			}
		}

		if(!mods.isEmpty())
			ACLogger.info("Mod integrations found: %s", mods);
	}

	/**
	 * Runs a second scan in the AbyssalCraftAPI list,
	 * in case a mod registered the integration after Pre-init.
	 */
	private static void searchAgain(){
		int temp = mods.size();
		if(!AbyssalCraftAPI.getIntegrations().isEmpty()){
			ACLogger.info("Searching the AbyssalCraftAPI list for integrations (again).");
			for(IACPlugin plugin : AbyssalCraftAPI.getIntegrations())
				if(!mods.contains(plugin.getModName())){
					ACLogger.info("Found a integration for mod %s", plugin.getModName());
					integrations.add(plugin);
					mods.add(plugin.getModName());
				}
			if(mods.size() > temp)
				ACLogger.info("Mod integrations found (with additions): %s", mods);
			else ACLogger.info("Found no additional mod integrations.");
		}
	}

	public static void preInit(){
		findIntegrations();
		if(!integrations.isEmpty()){
			ACLogger.info("Pre-initalizing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.preInit();
		}
	}

	public static void init(){
		searchAgain();
		if(!integrations.isEmpty()){
			ACLogger.info("Initializing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.init();
		}
	}

	public static void postInit(){
		searchAgain();
		if(!integrations.isEmpty()){
			ACLogger.info("Post-initializing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.postInit();
		}
	}
}
