/**AbyssalCraft
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
package com.shinoow.abyssalcraft.client.config;

import java.util.*;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.*;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;

public class ACConfigGUI extends GuiConfig {

	public ACConfigGUI(GuiScreen parent) {
		super(parent, getConfigElements(), "abyssalcraft", true, false, "AbyssalCraft");
	}

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements(){
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement<Object>("Dimensions", "Dimensions", DimensionEntry.class));
		list.add(new DummyCategoryElement<Object>("Biomes", "Biomes", BiomeEntry.class));
		return list;
	}

	public static class DimensionEntry extends CategoryEntry{

		public DimensionEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("dimensions")).getChildElements(), "abyssalcraft", "dimensions", true, false, "Dimensions");

		}
	}
	public static class BiomeEntry extends CategoryEntry{

		public BiomeEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("biomes")).getChildElements(), "abyssalcraft", "biomes", true, false, "Biomes");
		}
	}
}