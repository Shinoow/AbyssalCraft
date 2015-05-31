/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class ACConfigGUI extends GuiConfig {

	public ACConfigGUI(GuiScreen parent) {
		super(parent, getConfigElements(), "abyssalcraft", true, false, "AbyssalCraft");
	}

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements(){
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_dimensions"), "ac_dimensions", DimensionEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_biomes"), "ac_biomes", BiomeEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_biomegen"), "ac_biomegen", BiomeGenerationEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_biomespawn"), "ac_biomespawn", BiomeSpawnEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_biomeweight"), "ac_biomeweight", BiomeWeightEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_general"), "ac_general", GeneralEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_enchantment"), "ac_enchantment", EnchantmentEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_potion"), "ac_potion", PotionEntry.class));
		list.add(new DummyCategoryElement<Object>(StatCollector.translateToLocal("ac_integration"), "ac_integration", IntegrationEntry.class));
		return list;
	}

	public static class DimensionEntry extends CategoryEntry{

		public DimensionEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("dimensions")).getChildElements(), "abyssalcraft", "dimensions", true, true, StatCollector.translateToLocal("ac_dimensions"));

		}
	}
	public static class BiomeEntry extends CategoryEntry{

		public BiomeEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("biomes")).getChildElements(), "abyssalcraft", "biomes", true, true, StatCollector.translateToLocal("ac_biomes"));
		}
	}
	public static class BiomeGenerationEntry extends CategoryEntry{

		public BiomeGenerationEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("biome_generation")).getChildElements(), "abyssalcraft", "biome_generation", true, true, StatCollector.translateToLocal("ac_biomegen"));
		}
	}
	public static class BiomeSpawnEntry extends CategoryEntry{

		public BiomeSpawnEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("biome_spawning")).getChildElements(), "abyssalcraft", "biome_spawning", true, true, StatCollector.translateToLocal("ac_biomespawn"));
		}
	}
	public static class BiomeWeightEntry extends CategoryEntry{

		public BiomeWeightEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("biome_weight")).getChildElements(), "abyssalcraft", "biome_weight", true, true, StatCollector.translateToLocal("ac_biomeweight"));
		}
	}
	public static class GeneralEntry extends CategoryEntry{

		public GeneralEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("general")).getChildElements(), "abyssalcraft", "general", false, false, StatCollector.translateToLocal("ac_general"));

		}
	}
	public static class EnchantmentEntry extends CategoryEntry{

		public EnchantmentEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("enchantments")).getChildElements(), "abyssalcraft", "enchantments", true, true, StatCollector.translateToLocal("ac_enchantment"));

		}
	}
	public static class PotionEntry extends CategoryEntry{

		public PotionEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("potions")).getChildElements(), "abyssalcraft", "potions", true, true, StatCollector.translateToLocal("ac_potion"));

		}
	}
	public static class IntegrationEntry extends CategoryEntry{

		public IntegrationEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement<?> configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement<Object>(AbyssalCraft.cfg.getCategory("integrations")).getChildElements(), "abyssalcraft", "integrations", true, true, StatCollector.translateToLocal("ac_integration"));

		}
	}
}
