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
package com.shinoow.abyssalcraft.client.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ACConfigGUI extends GuiConfig {

	public ACConfigGUI(GuiScreen parent) {
		super(parent, getConfigElements(), "abyssalcraft", true, false, "AbyssalCraft");
	}

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements(){
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_dimensions"), "ac_dimensions", DimensionEntry.class));
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_biomes"), "ac_biomes", BiomeEntry.class));
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_biomegen"), "ac_biomegen", BiomeGenerationEntry.class));
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_biomespawn"), "ac_biomespawn", BiomeSpawnEntry.class));
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_biomeweight"), "ac_biomeweight", BiomeWeightEntry.class));
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_general"), "ac_general", GeneralEntry.class));
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_shoggoth"), "ac_shoggoth", ShoggothEntry.class));
		list.add(new DummyCategoryElement(I18n.translateToLocal("ac_worldgen"), "ac_worldgen", WorldGenEntry.class));
		return list;
	}

	public static class DimensionEntry extends CategoryEntry{

		public DimensionEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("dimensions")).getChildElements(), "abyssalcraft", "dimensions", true, true, I18n.translateToLocal("ac_dimensions"));

		}
	}
	public static class BiomeEntry extends CategoryEntry{

		public BiomeEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("biomes")).getChildElements(), "abyssalcraft", "biomes", true, true, I18n.translateToLocal("ac_biomes"));
		}
	}
	public static class BiomeGenerationEntry extends CategoryEntry{

		public BiomeGenerationEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("biome_generation")).getChildElements(), "abyssalcraft", "biome_generation", true, true, I18n.translateToLocal("ac_biomegen"));
		}
	}
	public static class BiomeSpawnEntry extends CategoryEntry{

		public BiomeSpawnEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("biome_spawning")).getChildElements(), "abyssalcraft", "biome_spawning", true, true, I18n.translateToLocal("ac_biomespawn"));
		}
	}
	public static class BiomeWeightEntry extends CategoryEntry{

		public BiomeWeightEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("biome_weight")).getChildElements(), "abyssalcraft", "biome_weight", true, true, I18n.translateToLocal("ac_biomeweight"));
		}
	}
	public static class GeneralEntry extends CategoryEntry{

		public GeneralEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("general")).getChildElements(), "abyssalcraft", "general", false, false, I18n.translateToLocal("ac_general"));

		}
	}
	public static class ShoggothEntry extends CategoryEntry{

		public ShoggothEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("shoggoth")).getChildElements(), "abyssalcraft", "shoggoth", false, false, I18n.translateToLocal("ac_shoggoth"));

		}
	}
	public static class WorldGenEntry extends CategoryEntry{

		public WorldGenEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(AbyssalCraft.cfg.getCategory("worldgen")).getChildElements(), "abyssalcraft", "worldgen", false, false, I18n.translateToLocal("ac_worldgen"));

		}
	}
}
