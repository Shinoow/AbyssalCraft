/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ACConfigGUI extends GuiConfig {

	public ACConfigGUI(GuiScreen parent) {
		super(parent, getConfigElements(), "abyssalcraft", true, false, "AbyssalCraft");
	}

	private static List<IConfigElement> getConfigElements(){
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement(I18n.format("ac_biomes"), "ac_biomes", BiomesEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_client"), "ac_client", ClientEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_mod_compat"), "ac_mod_compat", CompatEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_dimensions"), "ac_dimensions", DimensionEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_general"), "ac_general", GeneralEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_itemblacklist"), "ac_itemblacklist", ItemBlacklistEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_mobs"), "ac_mobs", MobsEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_modules"), "ac_modules", ModuleEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_rituals"), "ac_rituals", RitualsEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_shoggoth"), "ac_shoggoth", ShoggothEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_silly_settings"), "ac_silly_settings", SillySettingsEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_spells"), "ac_spells", SpellEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_wet_noodle"), "ac_wet_noodle", WetNoodleEntry.class));
		list.add(new DummyCategoryElement(I18n.format("ac_worldgen"), "ac_worldgen", WorldGenEntry.class));
		return list;
	}

	public static class DimensionEntry extends CategoryEntry{

		public DimensionEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_DIMENSIONS)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_DIMENSIONS, true, true, I18n.format("ac_dimensions"));

		}
	}
	public static class BiomesEntry extends CategoryEntry{

		public BiomesEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_BIOMES)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_BIOMES, true, true, I18n.format("ac_biomes"));
		}
	}
	public static class GeneralEntry extends CategoryEntry{

		public GeneralEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), "abyssalcraft", Configuration.CATEGORY_GENERAL, false, false, I18n.format("ac_general"));

		}
	}
	public static class ShoggothEntry extends CategoryEntry{

		public ShoggothEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_SHOGGOTH)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_SHOGGOTH, false, false, I18n.format("ac_shoggoth"));

		}
	}
	public static class WorldGenEntry extends CategoryEntry{

		public WorldGenEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_WORLDGEN)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_WORLDGEN, false, false, I18n.format("ac_worldgen"));

		}
	}
	public static class ItemBlacklistEntry extends CategoryEntry{

		public ItemBlacklistEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_ITEM_BLACKLIST)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_ITEM_BLACKLIST, true, true, I18n.format("ac_itemblacklist"));

		}
	}
	public static class SillySettingsEntry extends CategoryEntry{

		public SillySettingsEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_SILLY_SETTINGS)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_SILLY_SETTINGS, false, false, I18n.format("ac_silly_settings"));

		}
	}
	public static class CompatEntry extends CategoryEntry{

		public CompatEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_MOD_COMPAT)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_MOD_COMPAT, false, false, I18n.format("ac_mod_compat"));

		}
	}
	public static class WetNoodleEntry extends CategoryEntry{

		public WetNoodleEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_WET_NOODLE)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_WET_NOODLE, false, false, I18n.format("ac_wet_noodle"));

		}
	}
	public static class ModuleEntry extends CategoryEntry{

		public ModuleEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_MODULES)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_MODULES, true, true, I18n.format("ac_modules"));

		}
	}
	public static class SpellEntry extends CategoryEntry{

		public SpellEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_SPELLS)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_SPELLS, true, true, I18n.format("ac_spells"));

		}
	}
	public static class ClientEntry extends CategoryEntry{

		public ClientEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(Configuration.CATEGORY_CLIENT)).getChildElements(), "abyssalcraft", Configuration.CATEGORY_CLIENT, false, false, I18n.format("ac_client"));

		}
	}
	public static class RitualsEntry extends CategoryEntry{

		public RitualsEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_RITUALS)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_RITUALS, false, false, I18n.format("ac_rituals"));

		}
	}
	public static class MobsEntry extends CategoryEntry{

		public MobsEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen, new ConfigElement(InitHandler.cfg.getCategory(ACConfig.CATEGORY_MOBS)).getChildElements(), "abyssalcraft", ACConfig.CATEGORY_MOBS, true, true, I18n.format("ac_mobs"));

		}
	}
}
