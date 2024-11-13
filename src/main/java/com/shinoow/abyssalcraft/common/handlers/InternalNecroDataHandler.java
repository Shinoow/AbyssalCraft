/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.handlers;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.internal.DummyNecroDataHandler;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;
import com.shinoow.abyssalcraft.api.necronomicon.*;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.client.gui.necronomicon.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.util.NecroDataJsonUtil;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InternalNecroDataHandler extends DummyNecroDataHandler {

	private final List<NecroData> internalNecroData = new ArrayList<>();

	public InternalNecroDataHandler(){
		Chapters.OUTER_GODS = new Chapter("outergods", NecronomiconText.LABEL_OUTER_GODS, 0);
		Chapters.GREAT_OLD_ONES = new Chapter("greatoldones", NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0);
		Chapters.ABYSSAL_WASTELAND_PLAGUE = new Chapter("coraliumplague", NecronomiconText.LABEL_CORALIUM_PLAGUE, 1, ResearchItems.CORALIUM_PLAGUE);
		Chapters.DREADLANDS_PLAGUE = new Chapter("dreadplague", NecronomiconText.LABEL_DREAD_PLAGUE, 2, ResearchItems.DREAD_PLAGUE);
		Chapters.ITEM_TRANSPORT_SYSTEM = new Chapter("itemtransportsystem", NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, ResearchItems.getBookResearch(3));
		Chapters.RITUAL_GETTING_STARTED = new Chapter("gettingstarted", NecronomiconText.LABEL_GETTING_STARTED, 0);
		Chapters.RITUAL_MATERIALS = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0);
		Chapters.PE_INFO = new Chapter("information", NecronomiconText.LABEL_INFORMATION, 0);
		Chapters.PE_MATERIALS = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0);
		Chapters.PE_CRAFTING = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0);
		Chapters.PLACES_OF_POWER_INFO = new Chapter("information", NecronomiconText.LABEL_INFORMATION, 0);
		Chapters.IDOLS = new Chapter("idols", NecronomiconText.LABEL_INFORMATION_IDOLS, 0);
		Chapters.MISC_CRAFTING = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0);
		Chapters.MISC_ENCHANTMENTS = new Chapter("enchantments", NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0);
		Chapters.MISC_STATUES = new Chapter("decorativestatues", NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0);
		Chapters.SPELL_GETTING_STARTED = new Chapter("gettingstarted", NecronomiconText.LABEL_GETTING_STARTED, 0);
		Chapters.SPELL_CASTING = new Chapter("casting", NecronomiconText.LABEL_CASTING, 0);
		Chapters.SPELL_MATERIALS = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0);

		Chapters.PATRONS = new Chapter("patrons", NecronomiconText.LABEL_PATRONS, 0);
		Chapters.ABYSSALCRAFT_INFO = new Chapter("acinfo", NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0);

		Chapters.OVERWORLD = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAND = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));
		Chapters.OMOTHOL = new Chapter("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, ResearchItems.getBookResearch(3));
		Chapters.DARK_REALM = new Chapter("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, ResearchItems.getBookResearch(3));

		Chapters.OVERWORLD_PROGRESSION = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAND_PROGRESSION = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS_PROGRESSION = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));
		Chapters.OMOTHOL_PROGRESSION = new Chapter("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, ResearchItems.getBookResearch(3));
		Chapters.DARK_REALM_PROGRESSION = new Chapter("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, ResearchItems.getBookResearch(3));

		Chapters.OVERWORLD_BIOMES = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAND_BIOMES = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS_BIOMES = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));
		Chapters.OMOTHOL_BIOMES = new Chapter("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, ResearchItems.getBookResearch(3));
		Chapters.DARK_REALM_BIOMES = new Chapter("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, ResearchItems.getBookResearch(3));

		Chapters.OVERWORLD_MOBS = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAND_MOBS = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS_MOBS = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));
		Chapters.OMOTHOL_MOBS = new Chapter("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, ResearchItems.getBookResearch(3));
		Chapters.DARK_REALM_MOBS = new Chapter("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, ResearchItems.getBookResearch(3));

		Chapters.OVERWORLD_MATERIALS = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAMD_MATERIALS = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS_MATERIALS = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));
		Chapters.OMOTHOL_MATERIALS = new Chapter("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, ResearchItems.getBookResearch(3));
		Chapters.DARK_REALM_MATERIALS = new Chapter("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, ResearchItems.getBookResearch(3));

		Chapters.OVERWORLD_CRAFTING = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAND_CRAFTING = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS_CRAFTING = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));
		Chapters.OMOTHOL_CRAFTING = new Chapter("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, ResearchItems.getBookResearch(3));

		Chapters.OVERWORLD_ARMORTOOLS = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAND_ARMORTOOLS = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS_ARMORTOOLS = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));

		Chapters.OVERWORLD_STRUCTURES = new Chapter("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0);
		Chapters.ABYSSAL_WASTELAND_STRUCTURES = new Chapter("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, ResearchItems.getBookResearch(1));
		Chapters.DREADLANDS_STRUCTURES = new Chapter("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, ResearchItems.getBookResearch(2));
		Chapters.OMOTHOL_STRUCTURES = new Chapter("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, ResearchItems.getBookResearch(3));
		Chapters.DARK_REALM_STRUCTURES = new Chapter("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, ResearchItems.getBookResearch(3));

		internalNecroData.add(new NecroData("pantheon", NecronomiconText.LABEL_PANTHEON, 0, NecronomiconText.INFORMATION_GREAT_OLD_ONES,
				Chapters.OUTER_GODS, Chapters.GREAT_OLD_ONES));
		internalNecroData.add(new NecroData("rituals", NecronomiconText.LABEL_INFORMATION, 0, Chapters.RITUAL_GETTING_STARTED, Chapters.RITUAL_MATERIALS));
		GuiInstance structures = new GuiInstance(0, NecronomiconText.LABEL_STRUCTURES, "structures") {

			@Override
			@SideOnly(Side.CLIENT)
			public GuiScreen getOpenGui(int bookType, GuiScreen parent) {

				return new GuiNecronomiconPlacesOfPower(bookType, (GuiNecronomicon) parent);
			}
		};
		NecroData placesOfPower = new NecroData("placesofpower", NecronomiconText.LABEL_PLACES_OF_POWER, 0, NecronomiconText.PLACES_OF_POWER_INFO, Chapters.PLACES_OF_POWER_INFO, structures);
		internalNecroData.add(new NecroData("potentialenergy", NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, Chapters.PE_INFO, Chapters.PE_MATERIALS, Chapters.PE_CRAFTING, placesOfPower, Chapters.IDOLS));
		internalNecroData.add(new NecroData("miscinfo", NecronomiconText.LABEL_MISC_INFORMATION, 0, NecronomiconText.MISC_INFORMATION, Chapters.MISC_CRAFTING,
				Chapters.MISC_ENCHANTMENTS, Chapters.MISC_STATUES, Chapters.ABYSSAL_WASTELAND_PLAGUE, Chapters.DREADLANDS_PLAGUE, Chapters.ITEM_TRANSPORT_SYSTEM));
		internalNecroData.add(new NecroData("spells", NecronomiconText.LABEL_INFORMATION, 0, Chapters.SPELL_GETTING_STARTED, Chapters.SPELL_CASTING, Chapters.SPELL_MATERIALS));

		internalNecroData.add(new NecroData("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.WIP, Chapters.OVERWORLD_PROGRESSION, Chapters.ABYSSAL_WASTELAND_PROGRESSION, Chapters.DREADLANDS_PROGRESSION, Chapters.OMOTHOL_PROGRESSION, Chapters.DARK_REALM_PROGRESSION));
		internalNecroData.add(new NecroData("dimensions", NecronomiconText.LABEL_INFORMATION_DIMENSIONS, 0, NecronomiconText.WIP, Chapters.OVERWORLD, Chapters.ABYSSAL_WASTELAND, Chapters.DREADLANDS, Chapters.OMOTHOL, Chapters.DARK_REALM));
		internalNecroData.add(new NecroData("biomes", NecronomiconText.LABEL_INFORMATION_BIOMES, 0, NecronomiconText.WIP, Chapters.OVERWORLD_BIOMES, Chapters.ABYSSAL_WASTELAND_BIOMES, Chapters.DREADLANDS_BIOMES, Chapters.OMOTHOL_BIOMES, Chapters.DARK_REALM_BIOMES));
		internalNecroData.add(new NecroData("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, Chapters.OVERWORLD_MOBS, Chapters.ABYSSAL_WASTELAND_MOBS, Chapters.DREADLANDS_MOBS, Chapters.OMOTHOL_MOBS, Chapters.DARK_REALM_MOBS));
		internalNecroData.add(new NecroData("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, Chapters.OVERWORLD_MATERIALS, Chapters.ABYSSAL_WASTELAMD_MATERIALS, Chapters.DREADLANDS_MATERIALS, Chapters.OMOTHOL_MATERIALS, Chapters.DARK_REALM_MATERIALS));
		internalNecroData.add(new NecroData("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, Chapters.OVERWORLD_CRAFTING, Chapters.ABYSSAL_WASTELAND_CRAFTING, Chapters.DREADLANDS_CRAFTING, Chapters.OMOTHOL_CRAFTING));
		internalNecroData.add(new NecroData("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0, Chapters.OVERWORLD_ARMORTOOLS, Chapters.ABYSSAL_WASTELAND_ARMORTOOLS, Chapters.DREADLANDS_ARMORTOOLS));
		internalNecroData.add(new NecroData("structures", NecronomiconText.LABEL_STRUCTURES, 0, NecronomiconText.WIP, Chapters.OVERWORLD_STRUCTURES, Chapters.ABYSSAL_WASTELAND_STRUCTURES, Chapters.DREADLANDS_STRUCTURES, Chapters.OMOTHOL_STRUCTURES, Chapters.DARK_REALM_STRUCTURES));

		internalNecroData.add(new NecroData("information", NecronomiconText.LABEL_INFORMATION, 0, Chapters.ABYSSALCRAFT_INFO,
				getInternalNecroData("pantheon"),
				new Page(1, NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON, 4, NecronomiconText.INFORMATION_ABYSSALNOMICON),
				Chapters.PATRONS, new GuiInstance(0, NecronomiconText.LABEL_INFORMATION_MACHINES, "machines", ResearchItems.ABYSSAL_WASTELAND_NECRO){
			@Override
			@SideOnly(Side.CLIENT)
			public GuiScreen getOpenGui(int bookType, GuiScreen parent) { return new GuiNecronomiconMachines(bookType, (GuiNecronomicon) parent); }
		}, getInternalNecroData("progression"),
				getInternalNecroData("dimensions"),
				getInternalNecroData("biomes"),
				getInternalNecroData("entities"),
				getInternalNecroData("materials"),
				getInternalNecroData("specialmaterials"),
				getInternalNecroData("armortools"),
				getInternalNecroData("structures"),
				getInternalNecroData("miscinfo")));
		internalNecroData.add(new NecroData("ritualinfo", NecronomiconText.LABEL_RITUALS, 0, NecronomiconText.RITUAL_INFO, getInternalNecroData("rituals"),
				new RitualGuiInstance(0, NecronomiconText.LABEL_NORMAL, "ritualsoverworld"),
				new RitualGuiInstance(1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, "ritualsabyssalwasteland"),
				new RitualGuiInstance(2, NecronomiconText.LABEL_INFORMATION_DREADLANDS, "ritualsdreadlands"),
				new RitualGuiInstance(3, NecronomiconText.LABEL_INFORMATION_OMOTHOL, "ritualsomothol"),
				new RitualGuiInstance(4, ACItems.abyssalnomicon.getTranslationKey() + ".name", "ritualsabyssalnomicon")));
	}

	@Override
	public NecroData getInternalNecroData(String identifier){
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(identifier))
				return data;
		return null;
	}

	@Override
	public void addChapter(Chapter chapter, String identifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(identifier)){
				data.addData(chapter);
				return;
			}
	}

	@Override
	public void removeChapter(String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(necroidentifier))
				data.removeData(chapteridentifier);
	}

	@Override
	public void addPage(Page page, String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(necroidentifier))
				for(INecroData d : data.getContainedData())
					if(d instanceof Chapter && d.getIdentifier().equals(chapteridentifier))
						((Chapter)d).addPage(page);
	}

	@Override
	public void removePage(int pageNum, String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(necroidentifier))
				for(INecroData d : data.getContainedData())
					if(d instanceof Chapter && d.getIdentifier().equals(chapteridentifier))
						((Chapter)d).removePage(pageNum);
	}

	@Override
	public void insertPage(Page page, String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(necroidentifier))
				for(INecroData d : data.getContainedData())
					if(d instanceof Chapter && d.getIdentifier().equals(chapteridentifier))
						((Chapter)d).insertPage(page);
	}

	@Override
	public void verifyImageURL(String url) {
		if(FMLCommonHandler.instance().getSide().isServer()) return;

		if(GuiNecronomicon.failcache.contains(url) || GuiNecronomicon.successcache.get(url) != null) return;

		try {
			DynamicTexture t = new DynamicTexture(ImageIO.read(new URL(url)));
			GuiNecronomicon.successcache.put(url, t);
		} catch (Exception e) {
			ACLogger.warning("Failed to fetch image, send the following Stack Trace to Shinoow,");
			ACLogger.warning("(or figure out the error on your own):");
			e.printStackTrace();
			GuiNecronomicon.failcache.add(url);
		}
	}

	@Override
	public void registerInternalPages() {

		Pages.WIP = new Page(1, NecronomiconText.WIP, 0, NecronomiconText.WIP);

		Pages.AZATHOTH_1 = new Page(1, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.AZATHOTH_SEAL, NecronomiconText.AZATHOTH_1);
		Pages.AZATHOTH_2 = new Page(2, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.AZATHOTH_2);
		Pages.NYARLATHOTEP_1 = new Page(3, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.NYARLATHOTEP_SEAL, NecronomiconText.NYARLATHOTEP_1);
		Pages.NYARLATHOTEP_2 = new Page(4, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.NYARLATHOTEP_2);
		Pages.YOG_SOTHOTH_1 = new Page(5, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.YOG_SOTHOTH_SEAL, NecronomiconText.YOG_SOTHOTH_1);
		Pages.YOG_SOTHOTH_2 = new Page(6, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.YOG_SOTHOTH_2);
		Pages.SHUB_NIGGURATH_1 = new Page(7, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.SHUB_NIGGURATH_SEAL, NecronomiconText.SHUB_NIGGURATH_1);
		Pages.SHUB_NIGGURATH_2 = new Page(8, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.SHUB_NIGGURATH_2);

		Pages.CTHULHU_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconResources.CTHULHU_SEAL, NecronomiconText.CTHULHU_1);
		Pages.CTHULHU_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconText.CTHULHU_2);
		Pages.HASTUR_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconResources.HASTUR_SEAL, NecronomiconText.HASTUR_1);
		Pages.HASTUR_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconText.HASTUR_2);
		Pages.JZAHAR_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconResources.JZAHAR_SEAL, NecronomiconText.JZAHAR_1);
		Pages.JZAHAR_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconText.JZAHAR_2);

		Pages.MATERIAL_ABYSSALNITE_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.abyssalnite_ore), NecronomiconText.MATERIAL_ABYSSALNITE_1);
		Pages.MATERIAL_DARKSTONE_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.darkstone), NecronomiconText.MATERIAL_DARKSTONE_1);
		Pages.MATERIAL_DARKSTONE_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, NecronomiconText.MATERIAL_DARKSTONE_2);
		Pages.MATERIAL_CORALIUM_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.coralium_ore), NecronomiconText.MATERIAL_CORALIUM_1);
		Pages.MATERIAL_CORALIUM_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, NecronomiconText.MATERIAL_CORALIUM_2);
		Pages.MATERIAL_DARKLANDS_OAK_1 = new Page(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.darklands_oak_sapling), NecronomiconText.MATERIAL_DARKLANDS_OAK_1);
		Pages.MATERIAL_NITRE_1 = new Page(8, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.nitre_ore), NecronomiconText.MATERIAL_NITRE_1);
		Pages.MATERIAL_LIQUID_ANTIMATTER_1 = new Page(9, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.liquid_antimatter), NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_1);
		Pages.MATERIAL_LIQUID_ANTIMATTER_2 = new Page(10, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_2);

		Pages.PROGRESSION_OVERWORLD_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_1);
		Pages.PROGRESSION_OVERWORLD_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_2);
		Pages.PROGRESSION_OVERWORLD_3 = new Page(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_3);
		Pages.PROGRESSION_OVERWORLD_4 = new Page(4, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_4);
		Pages.PROGRESSION_OVERWORLD_5 = new Page(5, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_5);
		Pages.PROGRESSION_OVERWORLD_6 = new Page(6, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_6);

		Pages.ENTITY_ABYSSAL_ZOMBIE_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.ABYSSAL_ZOMBIE, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_1, ResearchItems.ABYSSAL_ZOMBIE);
		Pages.ENTITY_ABYSSAL_ZOMBIE_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_2, ResearchItems.ABYSSAL_ZOMBIE);
		Pages.ENTITY_DEPTHS_GHOUL_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.DEPTHS_GHOUL, NecronomiconText.ENTITY_DEPTHS_GHOUL_1, ResearchItems.DEPTHS_GHOUL);
		Pages.ENTITY_DEPTHS_GHOUL_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_DEPTHS_GHOUL_2, ResearchItems.DEPTHS_GHOUL);
		Pages.ENTITY_SHADOW_CREATURE_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1, ResearchItems.SHADOW_CREATURE);
		Pages.ENTITY_SHADOW_CREATURE_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHADOW_CREATURE_2, ResearchItems.SHADOW_CREATURE);
		Pages.ENTITY_SHADOW_MONSTER_1 = new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1, ResearchItems.SHADOW_MONSTER);
		Pages.ENTITY_SHADOW_MONSTER_2 = new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHADOW_MONSTER_2, ResearchItems.SHADOW_MONSTER);
		Pages.ENTITY_SHADOW_BEAST_1 = new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1, ResearchItems.SHADOW_BEAST);
		Pages.ENTITY_SHADOW_BEAST_2 = new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHADOW_BEAST_2, ResearchItems.SHADOW_BEAST);
		Pages.ENTITY_ANTI_1 = new Page(11, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.ANTI_ENTITIES, NecronomiconText.ENTITY_ANTI_1, ResearchItems.ANTI_MOB);
		Pages.ENTITY_ANTI_2 = new Page(12, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_ANTI_2, ResearchItems.ANTI_MOB);
		Pages.ENTITY_EVIL_ANIMALS_1 = new Page(13, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.EVIL_ANIMALS, NecronomiconText.ENTITY_EVIL_ANIMALS_1, ResearchItems.EVIL_ANIMAL);
		Pages.ENTITY_EVIL_ANIMALS_2 = new Page(14, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_EVIL_ANIMALS_2, ResearchItems.EVIL_ANIMAL);
		Pages.ENTITY_SHUB_OFFSPRING_1 = new Page(15, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHUB_OFFSPRING, NecronomiconText.ENTITY_SHUB_OFFSPRING_1, ResearchItems.SHUB_OFFSPRING);
		Pages.ENTITY_SHUB_OFFSPRING_2 = new Page(16, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHUB_OFFSPRING_2, ResearchItems.SHUB_OFFSPRING);
		Pages.ENTITY_SHOGGOTH_1 = new Page(17, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, ResearchItems.SHOGGOTH);
		Pages.ENTITY_SHOGGOTH_2 = new Page(18, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, ResearchItems.SHOGGOTH);

		Pages.CRAFTING_CORALIUM_INFUSED_STONE_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.coralium_infused_stone, Blocks.STONE, Blocks.STONE, Blocks.STONE, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem_cluster_3, Blocks.STONE, Blocks.STONE, Blocks.STONE), NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_1);
		Pages.CRAFTING_CORALIUM_INFUSED_STONE_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_2);
		Pages.CRAFTING_SHADOW_GEM_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.shadow_gem), NecronomiconText.CRAFTING_SHADOW_GEM_1);
		Pages.CRAFTING_SHADOW_GEM_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_SHADOW_GEM_2);
		Pages.CRAFTING_SHARD_OF_OBLIVION = new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.shard_of_oblivion), NecronomiconText.CRAFTING_SHARD_OF_OBLIVION);
		Pages.CRAFTING_GATEWAY_KEY = new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.gateway_key), NecronomiconText.CRAFTING_GATEWAY_KEY);
		Pages.CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND = new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(new ItemStack(ACItems.skin_of_the_abyssal_wasteland)), NecronomiconText.CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND_1, ResearchItems.ABYSSAL_WASTELAND);
		Pages.CRAFTING_NECRONOMICON_C = new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.abyssal_wasteland_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_C, ResearchItems.ABYSSAL_WASTELAND);

		Pages.CRAFTING_STAFF_OF_RENDING_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0, new CraftingStack(ACItems.staff_of_rending), NecronomiconText.CRAFTING_STAFF_OF_RENDING_1);
		Pages.CRAFTING_STAFF_OF_RENDING_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0, NecronomiconText.CRAFTING_STAFF_OF_RENDING_2);
		Pages.CRAFTING_SEQUENTIAL_BREWING_STAND_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0, new CraftingStack(ACBlocks.sequential_brewing_stand), NecronomiconText.CRAFTING_SEQUENTIAL_BREWING_STAND_1);
		Pages.CRAFTING_SEQUENTIAL_BREWING_STAND_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0, NecronomiconText.CRAFTING_SEQUENTIAL_BREWING_STAND_2);

		Pages.INFORMATION_OVERWORLD = new Page(1, NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0, NecronomiconText.INFORMATION_OVERWORLD);

		Pages.MATERIAL_ABYSSAL_STONE = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.abyssal_stone), NecronomiconText.MATERIAL_ABYSSAL_STONE_1);
		Pages.MATERIAL_ABYSSAL_SAND = new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.abyssal_sand), NecronomiconText.MATERIAL_ABYSSAL_SAND);
		Pages.MATERIAL_FUSED_ABYSSAL_SAND = new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.fused_abyssal_sand), NecronomiconText.MATERIAL_FUSED_ABYSSAL_SAND);
		Pages.MATERIAL_ABYSSAL_ORES_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.abyssal_diamond_ore), NecronomiconText.MATERIAL_ABYSSAL_ORES_1);
		Pages.MATERIAL_ABYSSAL_ORES_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, NecronomiconText.MATERIAL_ABYSSAL_ORES_2);
		Pages.MATERIAL_ABYSSAL_CORALIUM = new Page(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.abyssal_coralium_ore), NecronomiconText.MATERIAL_ABYSSAL_CORALIUM_1);
		Pages.MATERIAL_LIQUIFIED_CORALIUM = new Page(8, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.liquified_coralium_ore), NecronomiconText.MATERIAL_LIQUIFIED_CORALIUM_1);
		Pages.MATERIAL_PEARLESCENT_CORALIUM = new Page(9, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.pearlescent_coralium_ore), NecronomiconText.MATERIAL_PEARLESCENT_CORALIUM_1);
		Pages.MATERIAL_LIQUID_CORALIUM_1 = new Page(11, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.liquid_coralium), NecronomiconText.MATERIAL_LIQUID_CORALIUM_1);
		Pages.MATERIAL_LIQUID_CORALIUM_2 = new Page(12, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, NecronomiconText.MATERIAL_LIQUID_CORALIUM_2);
		Pages.MATERIAL_DREADLANDS_INFUSED_POWERSTONE = new Page(13, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.dreadlands_infused_powerstone), NecronomiconText.MATERIAL_DREADLANDS_INFUSED_POWERSTONE_1);

		Pages.PROGRESSION_ABYSSAL_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 1, NecronomiconText.PROGRESSION_ABYSSAL_1);
		Pages.PROGRESSION_ABYSSAL_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 1, NecronomiconText.PROGRESSION_ABYSSAL_2);
		Pages.PROGRESSION_ABYSSAL_3 = new Page(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 1, NecronomiconText.PROGRESSION_ABYSSAL_3);

		Pages.ENTITY_SKELETON_GOLIATH_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.SKELETON_GOLIATH, NecronomiconText.ENTITY_SKELETON_GOLIATH_1, ResearchItems.SKELETON_GOLIATH);
		Pages.ENTITY_SKELETON_GOLIATH_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_SKELETON_GOLIATH_2, ResearchItems.SKELETON_GOLIATH);
		Pages.ENTITY_SPECTRAL_DRAGON_1 = new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.SPECTRAL_DRAGON, NecronomiconText.ENTITY_SPECTRAL_DRAGON_1, ResearchItems.SPECTRAL_DRAGON);
		Pages.ENTITY_SPECTRAL_DRAGON_2 = new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_SPECTRAL_DRAGON_2, ResearchItems.SPECTRAL_DRAGON);
		Pages.ENTITY_ASORAH_1 = new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.ASORAH, NecronomiconText.ENTITY_ASORAH_1);
		Pages.ENTITY_ASORAH_2 = new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_ASORAH_2);
		Pages.ENTITY_CORALIUM_INFESTED_SQUID_1 = new Page(11, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.CORALIUM_INFESTED_SQUID, NecronomiconText.ENTITY_CORALIUM_INFESTED_SQUID_1, ResearchItems.CORALIUM_INFESTED_SQUID);
		Pages.ENTITY_CORALIUM_INFESTED_SQUID_2 = new Page(12, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_CORALIUM_INFESTED_SQUID_2, ResearchItems.CORALIUM_INFESTED_SQUID);
		Pages.ENTITY_ABYSSAL_SHOGGOTH_1 = new Page(13, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, ResearchItems.SHOGGOTH);
		Pages.ENTITY_ABYSSAL_SHOGGOTH_2 = new Page(14, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, ResearchItems.SHOGGOTH);

		Pages.CRAFTING_POWERSTONE_TRACKER = new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.powerstone_tracker), NecronomiconText.CRAFTING_POWERSTONE_TRACKER);
		Pages.CRAFTING_TRANSMUTATOR_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACBlocks.transmutator_idle), NecronomiconText.CRAFTING_TRANSMUTATOR_1);
		Pages.CRAFTING_TRANSMUTATOR_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, NecronomiconText.CRAFTING_TRANSMUTATOR_2);
		Pages.CRAFTING_CORALIUM_CHUNK = new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.chunk_of_coralium), NecronomiconText.CRAFTING_CORALIUM_CHUNK);
		Pages.CRAFTING_CORALIUM_PLATE = new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.coralium_plate), NecronomiconText.CRAFTING_CORALIUM_PLATE);
		Pages.CRAFTING_SKIN_OF_THE_DREADLANDS = new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(new ItemStack(ACItems.skin_of_the_dreadlands)), NecronomiconText.CRAFTING_SKIN_OF_THE_DREADLANDS_1, ResearchItems.DREADLANDS);
		Pages.CRAFTING_NECRONOMICON_D = new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.dreadlands_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_D, ResearchItems.DREADLANDS);

		Pages.CRAFTING_PLATED_CORALIUM_HELMET = new Page(1, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_helmet), NecronomiconText.CRAFTING_PLATED_CORALIUM_HELMET);
		Pages.CRAFTING_PLATED_CORALIUM_CHESTPLATE = new Page(2, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_chestplate), NecronomiconText.CRAFTING_PLATED_CORALIUM_CHESTPLATE);
		Pages.CRAFTING_PLATED_CORALIUM_LEGGINGS = new Page(3, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_leggings), NecronomiconText.CRAFTING_PLATED_CORALIUM_LEGGINGS);
		Pages.CRAFTING_PLATED_CORALIUM_BOOTS = new Page(4, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_boots), NecronomiconText.CRAFTING_PLATED_CORALIUM_BOOTS);
		Pages.CRAFTING_CORALIUM_LONGBOW = new Page(5, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.coralium_longbow), NecronomiconText.CRAFTING_CORALIUM_LONGBOW);

		Pages.CORALIUM_PLAGUE_INFO_1 = new Page(1, NecronomiconText.LABEL_CORALIUM_PLAGUE, 1, NecronomiconText.CORALIUM_PLAGUE_INFO_1, ResearchItems.CORALIUM_PLAGUE);
		Pages.CORALIUM_PLAGUE_INFO_2 = new Page(2, NecronomiconText.LABEL_CORALIUM_PLAGUE, 1, new CraftingStack(new ItemStack(ACItems.coralium_plague_antidote)), NecronomiconText.CORALIUM_PLAGUE_INFO_2, ResearchItems.CORALIUM_PLAGUE);

		Pages.INFORMATION_ABYSSAL_WASTELAND = new Page(1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, NecronomiconText.INFORMATION_ABYSSAL_WASTELAND);

		Pages.MATERIAL_DREADSTONE = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreadstone), NecronomiconText.MATERIAL_DREADSTONE_1);
		Pages.MATERIAL_ABYSSALNITE_STONE_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.elysian_stone), NecronomiconText.MATERIAL_ABYSSALNITE_STONE_1);
		Pages.MATERIAL_ABYSSALNITE_STONE_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, NecronomiconText.MATERIAL_ABYSSALNITE_STONE_2);
		Pages.MATERIAL_DREADLANDS_ABYSSALNITE = new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreadlands_abyssalnite_ore), NecronomiconText.MATERIAL_DREADLANDS_ABYSSALNITE_1);
		Pages.MATERIAL_DREADED_ABYSSALNITE = new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreaded_abyssalnite_ore), NecronomiconText.MATERIAL_DREADED_ABYSSALNITE_1);
		Pages.MATERIAL_DREADLANDS_GRASS = new Page(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreadlands_grass), NecronomiconText.MATERIAL_DREADLANDS_GRASS_1);
		Pages.MATERIAL_DREADLANDS_TREE = new Page(8, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreadwood_sapling), NecronomiconText.MATERIAL_DREADLANDS_TREE_1);

		Pages.PROGRESSION_DREADLANDS_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 2, NecronomiconText.PROGRESSION_DREADLANDS_1);
		Pages.PROGRESSION_DREADLANDS_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 2, NecronomiconText.PROGRESSION_DREADLANDS_2);
		Pages.PROGRESSION_DREADLANDS_3 = new Page(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 2, NecronomiconText.PROGRESSION_DREADLANDS_3);

		Pages.ENTITY_DREADLING_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DREADLING, NecronomiconText.ENTITY_DREADLING_1, ResearchItems.DREADLING);
		Pages.ENTITY_DREADLING_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DREADLING_2, ResearchItems.DREADLING);
		Pages.ENTITY_DREAD_SPAWN_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DREAD_SPAWN, NecronomiconText.ENTITY_DREAD_SPAWN_1, ResearchItems.DREAD_SPAWN);
		Pages.ENTITY_DREAD_SPAWN_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DREAD_SPAWN_2, ResearchItems.DREAD_SPAWN);
		Pages.ENTITY_DEMON_ANIMALS_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DEMON_ANIMALS, NecronomiconText.ENTITY_DEMON_ANIMALS_1, ResearchItems.DEMON_ANIMAL);
		Pages.ENTITY_DEMON_ANIMALS_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DEMON_ANIMALS_2, ResearchItems.DEMON_ANIMAL);
		Pages.ENTITY_SPAWN_OF_CHAGAROTH_1 = new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.SPAWN_OF_CHAGAROTH, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_1, ResearchItems.SPAWN_OF_CHAGAROTH);
		Pages.ENTITY_SPAWN_OF_CHAGAROTH_2 = new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_2, ResearchItems.SPAWN_OF_CHAGAROTH);
		Pages.ENTITY_FIST_OF_CHAGAROTH_1 = new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.FIST_OF_CHAGAROTH, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_1, ResearchItems.FIST_OF_CHAGAROTH);
		Pages.ENTITY_FIST_OF_CHAGAROTH_2 = new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_2, ResearchItems.FIST_OF_CHAGAROTH);
		Pages.ENTITY_DREADGUARD_1 = new Page(11, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DREADGUARD, NecronomiconText.ENTITY_DREADGUARD_1, ResearchItems.DREADGUARD);
		Pages.ENTITY_DREADGUARD_2 = new Page(12, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DREADGUARD_2, ResearchItems.DREADGUARD);
		Pages.ENTITY_CHAGAROTH_1 = new Page(13, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.CHAGAROTH, NecronomiconText.ENTITY_CHAGAROTH_1);
		Pages.ENTITY_CHAGAROTH_2 = new Page(14, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_CHAGAROTH_2);
		Pages.ENTITY_DREADLANDS_SHOGGOTH_1 = new Page(15, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, ResearchItems.SHOGGOTH);
		Pages.ENTITY_DREADLANDS_SHOGGOTH_2 = new Page(16, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, ResearchItems.SHOGGOTH);

		Pages.CRAFTING_DREADIUM_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(new ItemStack(ACBlocks.block_of_dreadium)), NecronomiconText.CRAFTING_DREADIUM_1);
		Pages.CRAFTING_DREADIUM_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, NecronomiconText.CRAFTING_DREADIUM_2);
		Pages.CRAFTING_CRYSTALLIZER_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACBlocks.crystallizer_idle), NecronomiconText.CRAFTING_CRYSTALLIZER_1);
		Pages.CRAFTING_CRYSTALLIZER_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, NecronomiconText.CRAFTING_CRYSTALLIZER_2);
		Pages.CRAFTING_DREAD_CLOTH = new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dread_cloth), NecronomiconText.CRAFTING_DREAD_CLOTH);
		Pages.CRAFTING_DREADIUM_PLATE = new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dreadium_plate), NecronomiconText.CRAFTING_DREADIUM_PLATE);
		Pages.CRAFTING_DREADIUM_HILT = new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dreadium_katana_hilt), NecronomiconText.CRAFTING_DREADIUM_HILT);
		Pages.CRAFTING_DREADIUM_BLADE = new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dreadium_katana_blade), NecronomiconText.CRAFTING_DREADIUM_BLADE);
		Pages.CRAFTING_SKIN_OF_OMOTHOL = new Page(9, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(new ItemStack(ACItems.skin_of_omothol)), NecronomiconText.CRAFTING_SKIN_OF_OMOTHOL_1, ResearchItems.OMOTHOL);
		Pages.CRAFTING_NECRONOMICON_O = new Page(10, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.omothol_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_O, ResearchItems.OMOTHOL);

		Pages.CRAFTING_DREADIUM_SAMURAI_HELMET = new Page(1, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_helmet), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_HELMET);
		Pages.CRAFTING_DREADIUM_SAMURAI_CHESTPLATE = new Page(2, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_chestplate), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_CHESTPLATE);
		Pages.CRAFTING_DREADIUM_SAMURAI_LEGGINGS = new Page(3, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_leggings), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_LEGGINGS);
		Pages.CRAFTING_DREADIUM_SAMURAI_BOOTS = new Page(4, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_boots), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_BOOTS);
		Pages.CRAFTING_DREADIUM_KATANA = new Page(5, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_katana), NecronomiconText.CRAFTING_DREADIUM_KATANA);

		Pages.DREAD_PLAGUE_INFO_1 = new Page(1, NecronomiconText.LABEL_DREAD_PLAGUE, 2, NecronomiconText.DREAD_PLAGUE_INFO_1, ResearchItems.DREAD_PLAGUE);
		Pages.DREAD_PLAGUE_INFO_2 = new Page(2, NecronomiconText.LABEL_DREAD_PLAGUE, 2, new CraftingStack(new ItemStack(ACItems.dread_plague_antidote)), NecronomiconText.DREAD_PLAGUE_INFO_2, ResearchItems.DREAD_PLAGUE);

		Pages.INFORMATION_DREADLANDS = new Page(1, NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, NecronomiconText.INFORMATION_DREADLANDS);

		Pages.MATERIAL_OMOTHOL_STONE = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.omothol_stone), NecronomiconText.MATERIAL_OMOTHOL_STONE_1);
		Pages.MATERIAL_ETHAXIUM_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.ethaxium), NecronomiconText.MATERIAL_ETHAXIUM_1);
		Pages.MATERIAL_ETHAXIUM_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, NecronomiconText.MATERIAL_ETHAXIUM_2);
		Pages.MATERIAL_DARK_ETHAXIUM_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.dark_ethaxium_brick), NecronomiconText.MATERIAL_DARK_ETHAXIUM_1);

		Pages.PROGRESSION_OMOTHOL_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_OMOTHOL_1);
		Pages.PROGRESSION_OMOTHOL_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_OMOTHOL_2);

		Pages.ENTITY_REMNANT_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.REMNANT, NecronomiconText.ENTITY_REMNANT_1);
		Pages.ENTITY_REMNANT_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_REMNANT_2);
		Pages.ENTITY_OMOTHOL_GHOUL_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.OMOTHOL_GHOUL, NecronomiconText.ENTITY_OMOTHOL_GHOUL_1, ResearchItems.OMOTHOL_GHOUL);
		Pages.ENTITY_OMOTHOL_GHOUL_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_OMOTHOL_GHOUL_2, ResearchItems.OMOTHOL_GHOUL);
		Pages.ENTITY_MINION_OF_THE_GATEKEEPER_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.MINION_OF_THE_GATEKEEPER, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_1, ResearchItems.MINION_OF_THE_GATEKEEPER);
		Pages.ENTITY_MINION_OF_THE_GATEKEEPER_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_2, ResearchItems.MINION_OF_THE_GATEKEEPER);
		Pages.ENTITY_JZAHAR_1 = new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.JZAHAR, NecronomiconText.ENTITY_JZAHAR_1);
		Pages.ENTITY_JZAHAR_2 = new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_JZAHAR_2);
		Pages.ENTITY_OMOTHOL_SHOGGOTH_1 = new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, ResearchItems.SHOGGOTH);
		Pages.ENTITY_OMOTHOL_SHOGGOTH_2 = new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, ResearchItems.SHOGGOTH);

		Pages.CRAFTING_LIFE_CRYSTAL_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.life_crystal), NecronomiconText.CRAFTING_LIFE_CRYSTAL_1);
		Pages.CRAFTING_LIFE_CRYSTAL_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_LIFE_CRYSTAL_2);
		Pages.CRAFTING_ETHAXIUM_INGOT_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.ethaxium_ingot, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.life_crystal, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick), NecronomiconText.CRAFTING_ETHAXIUM_INGOT_1);
		Pages.CRAFTING_ETHAXIUM_INGOT_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_ETHAXIUM_INGOT_2);
		Pages.CRAFTING_ETHAXIUM_PILLAR = new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACBlocks.ethaxium_pillar), NecronomiconText.CRAFTING_ETHAXIUM_PILLAR);
		Pages.CRAFTING_DARK_ETHAXIUM_PILLAR = new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACBlocks.dark_ethaxium_pillar), NecronomiconText.CRAFTING_DARK_ETHAXIUM_PILLAR);
		Pages.CRAFTING_COIN = new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.coin, null, Items.IRON_INGOT, null, Items.IRON_INGOT, Items.FLINT, Items.IRON_INGOT, null, Items.IRON_INGOT, null), NecronomiconText.CRAFTING_COIN);
		Pages.CRAFTING_CRYSTAL_BAG_1 = new Page(9, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.small_crystal_bag), NecronomiconText.CRAFTING_CRYSTAL_BAG_1);
		Pages.CRAFTING_CRYSTAL_BAG_2 = new Page(10, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_CRYSTAL_BAG_2);
		Pages.CRAFTING_MATERIALIZER_1 = new Page(11, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACBlocks.materializer), NecronomiconText.CRAFTING_MATERIALIZER_1);
		Pages.CRAFTING_MATERIALIZER_2 = new Page(12, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_MATERIALIZER_2);
		Pages.CRAFTING_ABYSSALNOMICON_1 = new Page(13, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.abyssalnomicon), NecronomiconText.CRAFTING_ABYSSALNOMICON_1);
		Pages.CRAFTING_ABYSSALNOMICON_2 = new Page(14, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_ABYSSALNOMICON_2);

		Pages.ITEM_TRANSPORT_TUT_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, new ItemStack(ACItems.configurator_shard_0), NecronomiconText.ITEM_TRANSPORT_TUT_1);
		Pages.ITEM_TRANSPORT_TUT_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, new ItemStack(ACItems.configurator), NecronomiconText.ITEM_TRANSPORT_TUT_2);
		Pages.ITEM_TRANSPORT_TUT_3 = new Page(3, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconResources.ITEM_TRANSPORT_TUT_1, NecronomiconText.ITEM_TRANSPORT_TUT_3);
		Pages.ITEM_TRANSPORT_TUT_4 = new Page(4, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_4);
		Pages.ITEM_TRANSPORT_TUT_5 = new Page(5, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_5);
		Pages.ITEM_TRANSPORT_TUT_6 = new Page(6, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconResources.ITEM_TRANSPORT_TUT_2, NecronomiconText.ITEM_TRANSPORT_TUT_6);
		Pages.ITEM_TRANSPORT_TUT_7 = new Page(7, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_7);
		Pages.ITEM_TRANSPORT_TUT_8 = new Page(8, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_8);

		Pages.INFORMATION_OMOTHOL = new Page(1, NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, NecronomiconText.INFORMATION_OMOTHOL);

		Pages.MATERIAL_DARKSTONE_DR_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.darkstone), NecronomiconText.MATERIAL_DARKSTONE_1);
		Pages.MATERIAL_DARKSTONE_DR_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, NecronomiconText.MATERIAL_DARKSTONE_2);

		Pages.PROGRESSION_DARK_REALM_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_DARK_REALM_1);
		Pages.PROGRESSION_DARK_REALM_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_DARK_REALM_2);

		Pages.ENTITY_SHADOW_CREATURE_DR_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1, ResearchItems.SHADOW_CREATURE);
		Pages.ENTITY_SHADOW_CREATURE_DR_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SHADOW_CREATURE_2, ResearchItems.SHADOW_CREATURE);
		Pages.ENTITY_SHADOW_MONSTER_DR_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1, ResearchItems.SHADOW_MONSTER);
		Pages.ENTITY_SHADOW_MONSTER_DR_2 = new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SHADOW_MONSTER_2, ResearchItems.SHADOW_MONSTER);
		Pages.ENTITY_SHADOW_BEAST_DR_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1, ResearchItems.SHADOW_BEAST);
		Pages.ENTITY_SHADOW_BEAST_DR_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SHADOW_BEAST_2, ResearchItems.SHADOW_BEAST);
		Pages.ENTITY_SACTHOTH_1 = new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SACTHOTH, NecronomiconText.ENTITY_SACTHOTH_1);
		Pages.ENTITY_SACTHOTH_2 = new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SACTHOTH_2);
		Pages.ENTITY_SHADOW_SHOGGOTH_1 = new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, ResearchItems.SHOGGOTH);
		Pages.ENTITY_SHADOW_SHOGGOTH_2 = new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, ResearchItems.SHOGGOTH);

		Pages.INFORMATION_DARK_REALM = new Page(1, NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, NecronomiconText.INFORMATION_DARK_REALM);

		Pages.RITUAL_TUT_1 = new Page(1, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.RITUAL_TUT_1, NecronomiconText.RITUAL_TUT_1);
		Pages.RITUAL_TUT_2 = new Page(2, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.RITUAL_TUT_2);
		Pages.RITUAL_TUT_3 = new Page(3, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.RITUAL_TUT_2, NecronomiconText.RITUAL_TUT_3);
		Pages.RITUAL_TUT_4 = new Page(4, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.BLANK, NecronomiconText.RITUAL_TUT_4);
		Pages.RITUAL_TUT_5 = new Page(5, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.RITUAL_TUT_3, NecronomiconText.RITUAL_TUT_5);
		Pages.RITUAL_TUT_6 = new Page(6, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.RITUAL_TUT_6);
		Pages.RITUAL_TUT_7 = new Page(7, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.RITUAL_TUT_7);

		Pages.MATERIAL_RITUAL_ALTAR = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.ritual_altar_stone), NecronomiconText.MATERIAL_RITUAL_ALTAR_1);
		Pages.MATERIAL_RITUAL_PEDESTAL = new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.ritual_pedestal_stone), NecronomiconText.MATERIAL_RITUAL_PEDESTAL_1);
		Pages.MATERIAL_MONOLITH_STONE_1 = new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.monolith_stone), NecronomiconText.MATERIAL_MONOLITH_STONE_1);

		Pages.MATERIAL_MONOLITH_STONE_2 = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.monolith_stone), NecronomiconText.MATERIAL_MONOLITH_STONE_1);

		Pages.CRAFTING_ENERGY_PEDESTAL_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_pedestal), NecronomiconText.CRAFTING_ENERGY_PEDESTAL_1);
		Pages.CRAFTING_ENERGY_PEDESTAL_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_ENERGY_PEDESTAL_2);
		Pages.CRAFTING_MONOLITH_PILLAR = new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.monolith_pillar), NecronomiconText.CRAFTING_MONOLITH_PILLAR_1);
		Pages.CRAFTING_RITUAL_CHARM = new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(new ItemStack(ACItems.ritual_charm)), NecronomiconText.CRAFTING_RITUAL_CHARM_1);
		Pages.CRAFTING_SACRIFICIAL_ALTAR_1 = new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.sacrificial_altar), NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_1);
		Pages.CRAFTING_SACRIFICIAL_ALTAR_2 = new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_2);
		Pages.CRAFTING_ENERGY_COLLECTOR = new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_collector), NecronomiconText.CRAFTING_ENERGY_COLLECTOR);
		Pages.CRAFTING_ENERGY_RELAY = new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_relay), NecronomiconText.CRAFTING_ENERGY_RELAY);
		Pages.CRAFTING_RENDING_PEDESTAL = new Page(9, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.rending_pedestal), NecronomiconText.CRAFTING_RENDING_PEDESTAL);
		Pages.CRAFTING_STONE_TABLET = new Page(10, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.stone_tablet), NecronomiconText.CRAFTING_STONE_TABLET);
		Pages.CRAFTING_STATE_TRANSFORMER_1 = new Page(11, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.state_transformer), NecronomiconText.CRAFTING_STATE_TRANSFORMER_1);
		Pages.CRAFTING_STATE_TRANSFORMER_2 = new Page(12, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_STATE_TRANSFORMER_2);
		Pages.CRAFTING_ENERGY_DEPOSITIONER_1 = new Page(13, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_depositioner), NecronomiconText.CRAFTING_ENERGY_DEPOSITIONER_1);
		Pages.CRAFTING_ENERGY_DEPOSITIONER_2 = new Page(14, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_ENERGY_DEPOSITIONER_2);

		Pages.PE_TUT_1 = new Page(1, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_1);
		Pages.PE_TUT_2 = new Page(2, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_1, NecronomiconText.PE_TUT_2);
		Pages.PE_TUT_3 = new Page(3, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_2, NecronomiconText.PE_TUT_3);
		Pages.PE_TUT_4 = new Page(4, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_4);
		Pages.PE_TUT_5 = new Page(5, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_3, NecronomiconText.PE_TUT_5);
		Pages.PE_TUT_6 = new Page(6, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_6);
		Pages.PE_TUT_7 = new Page(7, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_4, NecronomiconText.PE_TUT_7);
		Pages.PE_TUT_8 = new Page(8, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_8);
		Pages.PE_TUT_9 = new Page(9, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_5, NecronomiconText.PE_TUT_9);
		Pages.PE_TUT_10 = new Page(10, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_6, NecronomiconText.PE_TUT_10);
		Pages.PE_TUT_11 = new Page(11, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_7, NecronomiconText.PE_TUT_11);
		Pages.PE_TUT_12 = new Page(12, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_8, NecronomiconText.PE_TUT_12);
		Pages.PE_TUT_13 = new Page(13, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_9, NecronomiconText.PE_TUT_13);
		Pages.PE_TUT_14 = new Page(14, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_10, NecronomiconText.PE_TUT_14);
		Pages.PE_TUT_15 = new Page(15, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_11, NecronomiconText.PE_TUT_15);
		Pages.PE_TUT_16 = new Page(16, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_12, NecronomiconText.PE_TUT_16);
		Pages.PE_TUT_17 = new Page(17, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_13, NecronomiconText.PE_TUT_17);
		Pages.PE_TUT_18 = new Page(18, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_14, NecronomiconText.PE_TUT_18);
		Pages.PE_TUT_19 = new Page(19, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_15, NecronomiconText.PE_TUT_19);
		Pages.PE_TUT_20 = new Page(20, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_20);

		Pages.PLACES_OF_POWER_INFO_1 = new Page(1, NecronomiconText.LABEL_INFORMATION, 0, NecronomiconText.PLACES_OF_POWER_INFO_1);
		Pages.PLACES_OF_POWER_INFO_2 = new Page(2, NecronomiconText.LABEL_INFORMATION, 0, NecronomiconText.PLACES_OF_POWER_INFO_2);

		Pages.IDOLS_INFO = new Page(1, NecronomiconText.LABEL_INFORMATION_IDOLS, 0, NecronomiconText.IDOLS_INFO);
		Pages.MATERIAL_IDOL_OF_FADING = new Page(2, NecronomiconText.LABEL_INFORMATION_IDOLS, 0, new ItemStack(ACBlocks.idol_of_fading), NecronomiconText.MATERIAL_IDOL_OF_FADING);

		Pages.CRAFTING_ODB_CORE = new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.odb_core), NecronomiconText.CRAFTING_ODB_CORE);
		Pages.CRAFTING_ODB = new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.oblivion_deathbomb), NecronomiconText.CRAFTING_ODB);
		Pages.CRAFTING_CARBON_CLUSTER = new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.carbon_cluster), NecronomiconText.CRAFTING_CARBON_CLUSTER);
		Pages.CRAFTING_DENSE_CARBON_CLUSTER = new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.dense_carbon_cluster), NecronomiconText.CRAFTING_DENSE_CARBON_CLUSTER);
		Pages.CRAFTING_CRATE = new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.wooden_crate), NecronomiconText.CRAFTING_CRATE);

		Pages.ENCHANTMENT_LIGHT_PIERCE = new Page(1, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.light_pierce, AbyssalCraftAPI.light_pierce.getMaxLevel())), NecronomiconText.ENCHANTMENT_LIGHT_PIERCE, ResearchItems.SHADOW_MOBS);
		Pages.ENCHANTMENT_IRON_WALL = new Page(2, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.iron_wall, 1)), NecronomiconText.ENCHANTMENT_IRON_WALL);
		Pages.ENCHANTMENT_SAPPING = new Page(3, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.sapping, AbyssalCraftAPI.sapping.getMaxLevel())), NecronomiconText.ENCHANTMENT_SAPPING);
		Pages.ENCHANTMENT_MULTI_REND = new Page(4, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.multi_rend, 1)), NecronomiconText.ENCHANTMENT_MULTI_REND);
		Pages.ENCHANTMENT_BLINDING_LIGHT = new Page(5, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.blinding_light, 1)), NecronomiconText.ENCHANTMENT_BLINDING_LIGHT);

		Pages.CRAFTING_DECORATIVE_AZATHOTH_STATUE = new Page(1, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_azathoth_statue)), NecronomiconText.CRAFTING_DECORATIVE_AZATHOTH_STATUE);
		Pages.CRAFTING_DECORATIVE_CTHULHU_STATUE = new Page(2, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_cthulhu_statue)), NecronomiconText.CRAFTING_DECORATIVE_CTHULHU_STATUE);
		Pages.CRAFTING_DECORATIVE_HASTUR_STATUE = new Page(3, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_hastur_statue)), NecronomiconText.CRAFTING_DECORATIVE_HASTUR_STATUE);
		Pages.CRAFTING_DECORATIVE_JZAHAR_STATUE = new Page(4, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_jzahar_statue)), NecronomiconText.CRAFTING_DECORATIVE_JZAHAR_STATUE);
		Pages.CRAFTING_DECORATIVE_NYARLATHOTEP_STATUE = new Page(5, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_nyarlathotep_statue)), NecronomiconText.CRAFTING_DECORATIVE_NYARLATHOTEP_STATUE);
		Pages.CRAFTING_DECORATIVE_YOG_SOTHOTH_STATUE = new Page(6, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_yog_sothoth_statue)), NecronomiconText.CRAFTING_DECORATIVE_YOG_SOTHOTH_STATUE);
		Pages.CRAFTING_DECORATIVE_SHUB_NIGGURATH_STATUE = new Page(7, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_shub_niggurath_statue)), NecronomiconText.CRAFTING_DECORATIVE_SHUB_NIGGURATH_STATUE);

		Pages.SPELL_TUT_1 = new Page(1, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.SPELL_TUT_1);
		Pages.SPELL_TUT_2 = new Page(2, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.SPELL_TUT_2);

		Pages.SPELL_TUT_3 = new Page(1, NecronomiconText.LABEL_CASTING, 0, NecronomiconText.SPELL_TUT_3);

		Pages.MATERIAL_BASIC_SCROLL = new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.basic_scroll), NecronomiconText.WIP);
		Pages.MATERIAL_LESSER_SCROLL = new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.lesser_scroll), NecronomiconText.WIP);
		Pages.MATERIAL_MODERATE_SCROLL = new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.moderate_scroll), NecronomiconText.WIP);
		Pages.MATERIAL_GREATER_SCROLL = new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.greater_scroll), NecronomiconText.WIP);
		Pages.MATERIAL_ANTIMATTER_SCROLL = new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.antimatter_scroll), NecronomiconText.WIP);
		Pages.MATERIAL_OBLIVION_SCROLL = new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.oblivion_scroll), NecronomiconText.WIP);

		Pages.INFORMATION_ABYSSALCRAFT_PAGE_1 = new Page(1, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.ABYSSALCRAFT_1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_1);
		Pages.INFORMATION_ABYSSALCRAFT_PAGE_2 = new Page(2, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_2);
		Pages.INFORMATION_ABYSSALCRAFT_PAGE_3 = new Page(3, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.ABYSSALCRAFT_2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_3);
		Pages.INFORMATION_ABYSSALCRAFT_PAGE_4 = new Page(4, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_4);
		Pages.INFORMATION_ABYSSALCRAFT_PAGE_5 = new Page(5, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.ABYSSALCRAFT_3, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_5);
		Pages.INFORMATION_ABYSSALCRAFT_PAGE_6 = new Page(6, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_6);

		Pages.PROGRESSION_OVERWORLD_1.setReference(Pages.CRAFTING_GATEWAY_KEY);
		Pages.PROGRESSION_OVERWORLD_2.setReference(Pages.CRAFTING_STAFF_OF_RENDING_1, Pages.CRAFTING_STAFF_OF_RENDING_2);
		Pages.PROGRESSION_OVERWORLD_3.setReference(Pages.CRAFTING_SHARD_OF_OBLIVION);
		Pages.PROGRESSION_OVERWORLD_6.setReference(Pages.CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND);
		Pages.PROGRESSION_ABYSSAL_1.setReference(Pages.MATERIAL_DREADLANDS_INFUSED_POWERSTONE);
		Pages.PROGRESSION_ABYSSAL_2.setReference(Pages.CRAFTING_POWERSTONE_TRACKER);
		Pages.PROGRESSION_ABYSSAL_3.setReference(Pages.CRAFTING_SKIN_OF_THE_DREADLANDS);
		Pages.PROGRESSION_DREADLANDS_1.setReference(Pages.CRAFTING_TRANSMUTATOR_1, Pages.CRAFTING_TRANSMUTATOR_2);
		Pages.PROGRESSION_DREADLANDS_3.setReference(Pages.CRAFTING_SKIN_OF_OMOTHOL);
		Pages.PROGRESSION_OMOTHOL_2.setReference(Pages.CRAFTING_ABYSSALNOMICON_1, Pages.CRAFTING_ABYSSALNOMICON_2);
		//TODO set all of the other references here

		Chapters.OUTER_GODS.addPages(Pages.AZATHOTH_1, Pages.AZATHOTH_2, Pages.NYARLATHOTEP_1, Pages.NYARLATHOTEP_2, Pages.YOG_SOTHOTH_1, Pages.YOG_SOTHOTH_2, Pages.SHUB_NIGGURATH_1, Pages.SHUB_NIGGURATH_2);
		Chapters.GREAT_OLD_ONES.addPages(Pages.CTHULHU_1, Pages.CTHULHU_2, Pages.HASTUR_1, Pages.HASTUR_2, Pages.JZAHAR_1, Pages.JZAHAR_2);
		Chapters.OVERWORLD_MATERIALS.addPages(Pages.MATERIAL_ABYSSALNITE_1, Pages.MATERIAL_DARKSTONE_1, Pages.MATERIAL_DARKSTONE_2, Pages.MATERIAL_CORALIUM_1, Pages.MATERIAL_CORALIUM_2, Pages.MATERIAL_DARKLANDS_OAK_1, Pages.MATERIAL_NITRE_1, Pages.MATERIAL_LIQUID_ANTIMATTER_1, Pages.MATERIAL_LIQUID_ANTIMATTER_2);
		Chapters.OVERWORLD_PROGRESSION.addPages(Pages.PROGRESSION_OVERWORLD_1, Pages.PROGRESSION_OVERWORLD_2, Pages.PROGRESSION_OVERWORLD_3, Pages.PROGRESSION_OVERWORLD_4, Pages.PROGRESSION_OVERWORLD_5, Pages.PROGRESSION_OVERWORLD_6);
		Chapters.OVERWORLD_MOBS.addPages(Pages.ENTITY_ABYSSAL_ZOMBIE_1, Pages.ENTITY_ABYSSAL_ZOMBIE_2, Pages.ENTITY_DEPTHS_GHOUL_1, Pages.ENTITY_DEPTHS_GHOUL_2, Pages.ENTITY_SHADOW_CREATURE_1, Pages.ENTITY_SHADOW_CREATURE_2, Pages.ENTITY_SHADOW_MONSTER_1, Pages.ENTITY_SHADOW_MONSTER_2, Pages.ENTITY_SHADOW_BEAST_1, Pages.ENTITY_SHADOW_BEAST_2, Pages.ENTITY_ANTI_1, Pages.ENTITY_ANTI_2, Pages.ENTITY_EVIL_ANIMALS_1, Pages.ENTITY_EVIL_ANIMALS_2, Pages.ENTITY_SHUB_OFFSPRING_1, Pages.ENTITY_SHUB_OFFSPRING_2, Pages.ENTITY_SHOGGOTH_1, Pages.ENTITY_SHOGGOTH_2);
		Chapters.OVERWORLD_CRAFTING.addPages(Pages.CRAFTING_CORALIUM_INFUSED_STONE_1, Pages.CRAFTING_CORALIUM_INFUSED_STONE_2, Pages.CRAFTING_SHADOW_GEM_1, Pages.CRAFTING_SHADOW_GEM_2, Pages.CRAFTING_SHARD_OF_OBLIVION, Pages.CRAFTING_GATEWAY_KEY, Pages.CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND, Pages.CRAFTING_NECRONOMICON_C);
		Chapters.OVERWORLD_ARMORTOOLS.addPages(Pages.CRAFTING_STAFF_OF_RENDING_1, Pages.CRAFTING_STAFF_OF_RENDING_2, Pages.CRAFTING_SEQUENTIAL_BREWING_STAND_1, Pages.CRAFTING_SEQUENTIAL_BREWING_STAND_2);
		Chapters.OVERWORLD.addPage(Pages.INFORMATION_OVERWORLD); //TODO dimensions
		Chapters.OVERWORLD_BIOMES.addPage(Pages.WIP); //TODO biomes
		Chapters.OVERWORLD_STRUCTURES.addPage(Pages.WIP); //TODO structures
		Chapters.ABYSSAL_WASTELAMD_MATERIALS.addPages(Pages.MATERIAL_ABYSSAL_STONE, Pages.MATERIAL_ABYSSAL_SAND, Pages.MATERIAL_FUSED_ABYSSAL_SAND, Pages.MATERIAL_ABYSSAL_ORES_1, Pages.MATERIAL_ABYSSAL_ORES_2, Pages.MATERIAL_ABYSSAL_CORALIUM, Pages.MATERIAL_LIQUIFIED_CORALIUM, Pages.MATERIAL_PEARLESCENT_CORALIUM, Pages.MATERIAL_LIQUID_CORALIUM_1, Pages.MATERIAL_LIQUID_CORALIUM_2, Pages.MATERIAL_DREADLANDS_INFUSED_POWERSTONE);
		Chapters.ABYSSAL_WASTELAND_PROGRESSION.addPages(Pages.PROGRESSION_ABYSSAL_1, Pages.PROGRESSION_ABYSSAL_2, Pages.PROGRESSION_ABYSSAL_3);
		Chapters.ABYSSAL_WASTELAND_MOBS.addPages(Pages.ENTITY_ABYSSAL_ZOMBIE_1, Pages.ENTITY_ABYSSAL_ZOMBIE_2, Pages.ENTITY_DEPTHS_GHOUL_1, Pages.ENTITY_DEPTHS_GHOUL_2, Pages.ENTITY_SKELETON_GOLIATH_1, Pages.ENTITY_SKELETON_GOLIATH_2, Pages.ENTITY_SPECTRAL_DRAGON_1, Pages.ENTITY_SPECTRAL_DRAGON_2, Pages.ENTITY_ASORAH_1, Pages.ENTITY_ASORAH_2, Pages.ENTITY_CORALIUM_INFESTED_SQUID_1, Pages.ENTITY_CORALIUM_INFESTED_SQUID_2, Pages.ENTITY_ABYSSAL_SHOGGOTH_1, Pages.ENTITY_ABYSSAL_SHOGGOTH_2);
		Chapters.ABYSSAL_WASTELAND_CRAFTING.addPages(Pages.CRAFTING_POWERSTONE_TRACKER, Pages.CRAFTING_TRANSMUTATOR_1, Pages.CRAFTING_TRANSMUTATOR_2, Pages.CRAFTING_CORALIUM_CHUNK, Pages.CRAFTING_CORALIUM_PLATE, Pages.CRAFTING_SKIN_OF_THE_DREADLANDS, Pages.CRAFTING_NECRONOMICON_D);
		Chapters.ABYSSAL_WASTELAND_ARMORTOOLS.addPages(Pages.CRAFTING_PLATED_CORALIUM_HELMET, Pages.CRAFTING_PLATED_CORALIUM_CHESTPLATE, Pages.CRAFTING_PLATED_CORALIUM_LEGGINGS, Pages.CRAFTING_PLATED_CORALIUM_BOOTS, Pages.CRAFTING_CORALIUM_LONGBOW);
		Chapters.ABYSSAL_WASTELAND_PLAGUE.addPages(Pages.CORALIUM_PLAGUE_INFO_1, Pages.CORALIUM_PLAGUE_INFO_2);
		Chapters.ABYSSAL_WASTELAND.addPage(Pages.INFORMATION_ABYSSAL_WASTELAND); //TODO dimensions
		Chapters.ABYSSAL_WASTELAND_BIOMES.addPage(Pages.WIP); //TODO biomes
		Chapters.ABYSSAL_WASTELAND_STRUCTURES.addPage(Pages.WIP); //TODO structures
		Chapters.DREADLANDS_MATERIALS.addPages(Pages.MATERIAL_DREADSTONE, Pages.MATERIAL_ABYSSALNITE_STONE_1, Pages.MATERIAL_ABYSSALNITE_STONE_2, Pages.MATERIAL_DREADLANDS_ABYSSALNITE, Pages.MATERIAL_DREADED_ABYSSALNITE, Pages.MATERIAL_DREADLANDS_GRASS, Pages.MATERIAL_DREADLANDS_TREE);
		Chapters.DREADLANDS_PROGRESSION.addPages(Pages.PROGRESSION_DREADLANDS_1, Pages.PROGRESSION_DREADLANDS_2, Pages.PROGRESSION_DREADLANDS_3);
		Chapters.DREADLANDS_MOBS.addPages(Pages.ENTITY_DREADLING_1, Pages.ENTITY_DREADLING_2, Pages.ENTITY_DREAD_SPAWN_1, Pages.ENTITY_DREAD_SPAWN_2, Pages.ENTITY_DEMON_ANIMALS_1, Pages.ENTITY_DEMON_ANIMALS_2, Pages.ENTITY_SPAWN_OF_CHAGAROTH_1, Pages.ENTITY_SPAWN_OF_CHAGAROTH_2, Pages.ENTITY_FIST_OF_CHAGAROTH_1, Pages.ENTITY_FIST_OF_CHAGAROTH_2, Pages.ENTITY_DREADGUARD_1, Pages.ENTITY_DREADGUARD_2, Pages.ENTITY_CHAGAROTH_1, Pages.ENTITY_CHAGAROTH_2, Pages.ENTITY_DREADLANDS_SHOGGOTH_1, Pages.ENTITY_DREADLANDS_SHOGGOTH_2);
		Chapters.DREADLANDS_CRAFTING.addPages(Pages.CRAFTING_DREADIUM_1, Pages.CRAFTING_DREADIUM_2, Pages.CRAFTING_CRYSTALLIZER_1, Pages.CRAFTING_CRYSTALLIZER_2, Pages.CRAFTING_DREAD_CLOTH, Pages.CRAFTING_DREADIUM_PLATE, Pages.CRAFTING_DREADIUM_HILT, Pages.CRAFTING_DREADIUM_BLADE, Pages.CRAFTING_SKIN_OF_OMOTHOL, Pages.CRAFTING_NECRONOMICON_O);
		Chapters.DREADLANDS_ARMORTOOLS.addPages(Pages.CRAFTING_DREADIUM_SAMURAI_HELMET, Pages.CRAFTING_DREADIUM_SAMURAI_CHESTPLATE, Pages.CRAFTING_DREADIUM_SAMURAI_LEGGINGS, Pages.CRAFTING_DREADIUM_SAMURAI_BOOTS, Pages.CRAFTING_DREADIUM_KATANA);
		Chapters.DREADLANDS_PLAGUE.addPages(Pages.DREAD_PLAGUE_INFO_1, Pages.DREAD_PLAGUE_INFO_2);
		Chapters.DREADLANDS.addPage(Pages.INFORMATION_DREADLANDS); //TODO dimensions
		Chapters.DREADLANDS_BIOMES.addPage(Pages.WIP); //TODO biomes
		Chapters.DREADLANDS_STRUCTURES.addPage(Pages.WIP); //TODO structures
		Chapters.OMOTHOL_MATERIALS.addPages(Pages.MATERIAL_OMOTHOL_STONE, Pages.MATERIAL_ETHAXIUM_1, Pages.MATERIAL_ETHAXIUM_2, Pages.MATERIAL_DARK_ETHAXIUM_1);
		Chapters.OMOTHOL_PROGRESSION.addPages(Pages.PROGRESSION_OMOTHOL_1, Pages.PROGRESSION_OMOTHOL_2);
		Chapters.OMOTHOL_MOBS.addPages(Pages.ENTITY_REMNANT_1, Pages.ENTITY_REMNANT_2, Pages.ENTITY_OMOTHOL_GHOUL_1, Pages.ENTITY_OMOTHOL_GHOUL_2, Pages.ENTITY_MINION_OF_THE_GATEKEEPER_1, Pages.ENTITY_MINION_OF_THE_GATEKEEPER_2, Pages.ENTITY_JZAHAR_1, Pages.ENTITY_JZAHAR_2, Pages.ENTITY_OMOTHOL_SHOGGOTH_1, Pages.ENTITY_OMOTHOL_SHOGGOTH_2);
		Chapters.OMOTHOL_CRAFTING.addPages(Pages.CRAFTING_LIFE_CRYSTAL_1, Pages.CRAFTING_LIFE_CRYSTAL_2, Pages.CRAFTING_ETHAXIUM_INGOT_1, Pages.CRAFTING_ETHAXIUM_INGOT_2, Pages.CRAFTING_ETHAXIUM_PILLAR, Pages.CRAFTING_DARK_ETHAXIUM_PILLAR, Pages.CRAFTING_COIN, Pages.CRAFTING_CRYSTAL_BAG_1, Pages.CRAFTING_CRYSTAL_BAG_2, Pages.CRAFTING_MATERIALIZER_1, Pages.CRAFTING_MATERIALIZER_2, Pages.CRAFTING_ABYSSALNOMICON_1, Pages.CRAFTING_ABYSSALNOMICON_2);
		Chapters.ITEM_TRANSPORT_SYSTEM.addPages(Pages.ITEM_TRANSPORT_TUT_1, Pages.ITEM_TRANSPORT_TUT_2, Pages.ITEM_TRANSPORT_TUT_3, Pages.ITEM_TRANSPORT_TUT_4, Pages.ITEM_TRANSPORT_TUT_5, Pages.ITEM_TRANSPORT_TUT_6, Pages.ITEM_TRANSPORT_TUT_7, Pages.ITEM_TRANSPORT_TUT_8);
		Chapters.OMOTHOL.addPage(Pages.INFORMATION_OMOTHOL); //TODO dimensions
		Chapters.OMOTHOL_BIOMES.addPage(Pages.WIP); //TODO biomes
		Chapters.OMOTHOL_STRUCTURES.addPage(Pages.WIP); //TODO structures
		Chapters.DARK_REALM_MATERIALS.addPages(Pages.MATERIAL_DARKSTONE_DR_1, Pages.MATERIAL_DARKSTONE_DR_2);
		Chapters.DARK_REALM_PROGRESSION.addPages(Pages.PROGRESSION_DARK_REALM_1, Pages.PROGRESSION_DARK_REALM_2);
		Chapters.DARK_REALM_MOBS.addPages(Pages.ENTITY_SHADOW_CREATURE_DR_1, Pages.ENTITY_SHADOW_CREATURE_DR_2, Pages.ENTITY_SHADOW_MONSTER_DR_1, Pages.ENTITY_SHADOW_MONSTER_DR_2, Pages.ENTITY_SHADOW_BEAST_DR_1, Pages.ENTITY_SHADOW_BEAST_DR_2, Pages.ENTITY_SACTHOTH_1, Pages.ENTITY_SACTHOTH_2, Pages.ENTITY_SHADOW_SHOGGOTH_1, Pages.ENTITY_SHADOW_SHOGGOTH_2);
		Chapters.DARK_REALM.addPage(Pages.INFORMATION_DARK_REALM); //TODO dimensions
		Chapters.DARK_REALM_BIOMES.addPage(Pages.WIP); //TODO biomes
		Chapters.DARK_REALM_STRUCTURES.addPage(Pages.WIP); //TODO structures
		Chapters.RITUAL_GETTING_STARTED.addPages(Pages.RITUAL_TUT_1, Pages.RITUAL_TUT_2, Pages.RITUAL_TUT_3, Pages.RITUAL_TUT_4, Pages.RITUAL_TUT_5, Pages.RITUAL_TUT_6, Pages.RITUAL_TUT_7);
		Chapters.RITUAL_MATERIALS.addPages(Pages.MATERIAL_RITUAL_ALTAR, Pages.MATERIAL_RITUAL_PEDESTAL, Pages.MATERIAL_MONOLITH_STONE_1);
		Chapters.PE_MATERIALS.addPage(Pages.MATERIAL_MONOLITH_STONE_2);
		Chapters.PE_CRAFTING.addPages(Pages.CRAFTING_ENERGY_PEDESTAL_1, Pages.CRAFTING_ENERGY_PEDESTAL_2, Pages.CRAFTING_MONOLITH_PILLAR, Pages.CRAFTING_RITUAL_CHARM, Pages.CRAFTING_SACRIFICIAL_ALTAR_1, Pages.CRAFTING_SACRIFICIAL_ALTAR_2, Pages.CRAFTING_ENERGY_COLLECTOR, Pages.CRAFTING_ENERGY_RELAY, Pages.CRAFTING_RENDING_PEDESTAL, Pages.CRAFTING_STONE_TABLET, Pages.CRAFTING_STATE_TRANSFORMER_1, Pages.CRAFTING_STATE_TRANSFORMER_2, Pages.CRAFTING_ENERGY_DEPOSITIONER_1, Pages.CRAFTING_ENERGY_DEPOSITIONER_2);
		Chapters.PE_INFO.addPages(Pages.PE_TUT_1, Pages.PE_TUT_2, Pages.PE_TUT_3, Pages.PE_TUT_4, Pages.PE_TUT_5, Pages.PE_TUT_6, Pages.PE_TUT_7, Pages.PE_TUT_8, Pages.PE_TUT_9, Pages.PE_TUT_10, Pages.PE_TUT_11, Pages.PE_TUT_12, Pages.PE_TUT_13, Pages.PE_TUT_14, Pages.PE_TUT_15, Pages.PE_TUT_16, Pages.PE_TUT_17, Pages.PE_TUT_18, Pages.PE_TUT_19, Pages.PE_TUT_20);
		Chapters.PLACES_OF_POWER_INFO.addPages(Pages.PLACES_OF_POWER_INFO_1, Pages.PLACES_OF_POWER_INFO_2);
		Chapters.IDOLS.addPages(Pages.IDOLS_INFO, Pages.MATERIAL_IDOL_OF_FADING);
		Chapters.MISC_CRAFTING.addPages(Pages.CRAFTING_ODB_CORE, Pages.CRAFTING_ODB, Pages.CRAFTING_CARBON_CLUSTER, Pages.CRAFTING_DENSE_CARBON_CLUSTER, Pages.CRAFTING_CRATE);
		Chapters.MISC_ENCHANTMENTS.addPages(Pages.ENCHANTMENT_LIGHT_PIERCE, Pages.ENCHANTMENT_IRON_WALL, Pages.ENCHANTMENT_SAPPING, Pages.ENCHANTMENT_MULTI_REND, Pages.ENCHANTMENT_BLINDING_LIGHT);
		Chapters.MISC_STATUES.addPages(Pages.CRAFTING_DECORATIVE_AZATHOTH_STATUE, Pages.CRAFTING_DECORATIVE_CTHULHU_STATUE, Pages.CRAFTING_DECORATIVE_HASTUR_STATUE, Pages.CRAFTING_DECORATIVE_JZAHAR_STATUE, Pages.CRAFTING_DECORATIVE_NYARLATHOTEP_STATUE, Pages.CRAFTING_DECORATIVE_YOG_SOTHOTH_STATUE, Pages.CRAFTING_DECORATIVE_SHUB_NIGGURATH_STATUE);
		Chapters.SPELL_GETTING_STARTED.addPages(Pages.SPELL_TUT_1, Pages.SPELL_TUT_2);
		Chapters.SPELL_CASTING.addPage(Pages.SPELL_TUT_3);
		Chapters.SPELL_MATERIALS.addPages(Pages.MATERIAL_BASIC_SCROLL, Pages.MATERIAL_LESSER_SCROLL, Pages.MATERIAL_MODERATE_SCROLL, Pages.MATERIAL_GREATER_SCROLL, Pages.MATERIAL_ANTIMATTER_SCROLL, Pages.MATERIAL_OBLIVION_SCROLL);
		Chapters.ABYSSALCRAFT_INFO.addPages(Pages.INFORMATION_ABYSSALCRAFT_PAGE_1, Pages.INFORMATION_ABYSSALCRAFT_PAGE_2, Pages.INFORMATION_ABYSSALCRAFT_PAGE_3, Pages.INFORMATION_ABYSSALCRAFT_PAGE_4, Pages.INFORMATION_ABYSSALCRAFT_PAGE_5, Pages.INFORMATION_ABYSSALCRAFT_PAGE_6);

		setupPatreonData();
	}

	private void setupPatreonData(){
		if(FMLCommonHandler.instance().getSide().isServer()) return;

		Chapter chapter = null;
		try {
			URL url = new URL("https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/patrons.json");
			InputStream con = url.openStream();
			String data = new String(ByteStreams.toByteArray(con));
			con.close();

			chapter = NecroDataJsonUtil.deserializeChapter(new Gson().fromJson(data, JsonObject.class));

		} catch (Exception e) {
			ACLogger.warning("Failed to fetch the Patreon Data, using local version instead!");
			chapter = new Chapter("patrons", NecronomiconText.LABEL_PATRONS, 0);
			chapter.addPage(new Page(1, NecronomiconText.LABEL_PATRONS, 0, new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"), "Nobody :/"));
		}

		if(chapter != null)
			Chapters.PATRONS.addPages(chapter.getPages().values().toArray(new Page[0]));
	}

	private class RitualGuiInstance extends GuiInstance {

		protected RitualGuiInstance(int displayIcon, String title, String identifier){
			super(displayIcon, title, identifier, ResearchItems.getBookResearch(displayIcon));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiScreen getOpenGui(int bookType, GuiScreen parent) {
			return new GuiNecronomiconRitualEntry(bookType, (GuiNecronomicon) parent, displayIcon);
		}
	}
}
