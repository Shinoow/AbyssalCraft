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

import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.integration.morph.ACMorphIntegration;
import com.shinoow.abyssalcraft.integration.thaumcraft.ACThaumcraftIntegration;

import cpw.mods.fml.common.Loader;

public class IntegrationHandler {

	static boolean isNEILoaded = Loader.isModLoaded("NotEnoughItems");
	static boolean isThaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
	static boolean isMorhpLoaded = Loader.isModLoaded("Morph");

	static List<String> mods = new ArrayList<String>();

	public static void init(){
		ACLogger.info("Checking possible mod integrations.");
		if(isNEILoaded){
			ACLogger.info("Not Enough Items is present, initializing informative stuff");
			//This part is handled by NEI, so this message is essentially useless :P
			mods.add("Not Enough Items");
		}
		if(isThaumcraftLoaded){
			ACLogger.info("Thaumcraft is present, initializing evil stuff.");
			ACThaumcraftIntegration.init();
			mods.add("Thaumcraft");
		}
		if(isMorhpLoaded){
			ACLogger.info("Morph is present, initializing weird shape-shifting stuff.");
			ACMorphIntegration.init();
			mods.add("Morph");
		}
		if(!mods.isEmpty())
			ACLogger.info("Mod integrations found: %s", mods);
	}
}