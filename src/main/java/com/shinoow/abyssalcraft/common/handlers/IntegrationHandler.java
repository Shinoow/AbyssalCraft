/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.handlers;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.integration.morph.ACMorphIntegration;
import com.shinoow.abyssalcraft.integration.thaumcraft.ACThaumcraftIntegration;

import cpw.mods.fml.common.Loader;

public class IntegrationHandler {

	static boolean isNEILoaded = Loader.isModLoaded("NotEnoughItems");
	static boolean isThaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
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
			ACLogger.info("Not Enough Items is present, initializing informative stuff");
			//This part is handled by NEI, so this message is essentially useless :P
			mods.add("Not Enough Items");
		}
		if(isThaumcraftLoaded){
			ACLogger.info("Thaumcraft is present, initializing evil stuff.");
			integrations.add(new ACThaumcraftIntegration());
			mods.add("Thaumcraft");
		}
		if(isMorhpLoaded){
			ACLogger.info("Morph is present, initializing weird shape-shifting stuff.");
			integrations.add(new ACMorphIntegration());
			mods.add("Morph");
		}
		if(isInvTweaksLoaded){
			ACLogger.info("Inventory Tweaks is present, initializing sorting stuff");
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
		if(!integrations.isEmpty()){
			ACLogger.info("Post-initializing integrations!");
			for(IACPlugin plugin : integrations)
				plugin.postInit();
		}
	}
}