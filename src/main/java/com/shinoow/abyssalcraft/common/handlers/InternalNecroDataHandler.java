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

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.internal.DummyNecroDataHandler;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconInformation;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.util.NecroDataJsonUtil;

public class InternalNecroDataHandler extends DummyNecroDataHandler {

	private final List<NecroData> internalNecroData = Lists.newArrayList();

	public InternalNecroDataHandler(){
		Chapter outergods = new Chapter("outergods", NecronomiconText.LABEL_OUTER_GODS);
		Chapter greatoldones = new Chapter("greatoldones", NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES);
		internalNecroData.add(new NecroData("greatoldones", NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, NecronomiconText.INFORMATION_GREAT_OLD_ONES,
				outergods, greatoldones));
		Chapter overworldmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS);
		Chapter overworldprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION);
		Chapter overworldentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES);
		Chapter overworldspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS);
		Chapter overworldarmortools = new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS);
		internalNecroData.add(new NecroData("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, NecronomiconText.INFORMATION_OVERWORLD,
				overworldmaterials, overworldprogression, overworldentities, overworldspecialmaterials, overworldarmortools));
		Chapter abyssalwastelandmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS);
		Chapter abyssalwastelandprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION);
		Chapter abyssalwastelandentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES);
		Chapter abyssalwastelandspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS);
		Chapter abyssalwastelandarmortools = new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS);
		internalNecroData.add(new NecroData("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, NecronomiconText.INFORMATION_ABYSSAL_WASTELAND,
				abyssalwastelandmaterials, abyssalwastelandprogression, abyssalwastelandentities, abyssalwastelandspecialmaterials, abyssalwastelandarmortools));
		Chapter dreadlandsmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS);
		Chapter dreadlandsprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION);
		Chapter dreadlandsentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES);
		Chapter dreadlandsspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS);
		Chapter dreadlandsarmortools = new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS);
		internalNecroData.add(new NecroData("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, NecronomiconText.INFORMATION_DREADLANDS,
				dreadlandsmaterials, dreadlandsprogression, dreadlandsentities, dreadlandsspecialmaterials, dreadlandsarmortools));
		Chapter omotholmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS);
		Chapter omotholprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION);
		Chapter omotholentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES);
		Chapter omotholspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS);
		internalNecroData.add(new NecroData("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, NecronomiconText.INFORMATION_OMOTHOL,
				omotholmaterials, omotholprogression, omotholentities, omotholspecialmaterials));
		Chapter darkrealmmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS);
		Chapter darkrealmprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION);
		Chapter darkrealmentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES);
		internalNecroData.add(new NecroData("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, NecronomiconText.INFORMATION_DARK_REALM,
				darkrealmmaterials, darkrealmprogression, darkrealmentities));
		Chapter ritualgettingstarted = new Chapter("gettingstarted", NecronomiconText.LABEL_GETTING_STARTED);
		Chapter ritualmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS);
		Chapter ritualspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS);
		Chapter ritualpotentialenergy = new Chapter("potentialenergy", NecronomiconText.LABEL_POTENTIAL_ENERGY);
		internalNecroData.add(new NecroData("rituals", NecronomiconText.LABEL_INFO, ritualgettingstarted, ritualmaterials,
				ritualspecialmaterials, ritualpotentialenergy));
		Chapter miscspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS);
		Chapter miscenchantments = new Chapter("enchantments", NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS);
		internalNecroData.add(new NecroData("miscinfo", NecronomiconText.LABEL_MISC_INFORMATION, NecronomiconText.MISC_INFORMATION, miscspecialmaterials,
				miscenchantments));
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
				data.addChapter(chapter);
				return;
			}
	}

	@Override
	public void removeChapter(String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(necroidentifier))
				data.removeChapter(chapteridentifier);
	}

	@Override
	public void addPage(Page page, String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(necroidentifier))
				for(Chapter chapter : data.getChapters())
					if(chapter.getIdentifier().equals(chapteridentifier))
						chapter.addPage(page);
	}

	@Override
	public void removePage(int pageNum, String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData)
			if(data.getIdentifier().equals(necroidentifier))
				for(Chapter chapter : data.getChapters())
					if(chapter.getIdentifier().equals(chapteridentifier))
						chapter.removePage(pageNum);
	}

	private void addPages(String necroidentifier, String chapteridentifier, Page...pages){
		for(Page page : pages)
			addPage(page, necroidentifier, chapteridentifier);

	}

	@Override
	public void verifyImageURL(String url) {
		if(FMLCommonHandler.instance().getSide().isServer()) return;

		if(GuiNecronomicon.failcache.contains(url) || GuiNecronomicon.successcache.get(url) != null) return;

		try {
			DynamicTexture t = new DynamicTexture(ImageIO.read(new URL(url)));
			GuiNecronomicon.successcache.put(url, t);
		} catch (Exception e) {
			GuiNecronomicon.failcache.add(url);
		}
	}

	@Override
	public void registerInternalPages() {
		addPages("greatoldones", "outergods", new Page(1, NecronomiconResources.AZATHOTH_SEAL, NecronomiconText.AZATHOTH_1),
				new Page(2, NecronomiconText.AZATHOTH_2),
				new Page(3, NecronomiconResources.NYARLATHOTEP_SEAL, NecronomiconText.NYARLATHOTEP_1),
				new Page(4, NecronomiconText.NYARLATHOTEP_2),
				new Page(5, NecronomiconResources.YOG_SOTHOTH_SEAL, NecronomiconText.YOG_SOTHOTH_1),
				new Page(6, NecronomiconText.YOG_SOTHOTH_2),
				new Page(7, NecronomiconResources.SHUB_NIGGURATH_SEAL, NecronomiconText.SHUB_NIGGURATH_1),
				new Page(8, NecronomiconText.SHUB_NIGGURATH_2));
		addPages("greatoldones", "greatoldones", new Page(1, NecronomiconResources.CTHULHU_SEAL, NecronomiconText.CTHULHU_1),
				new Page(2, NecronomiconText.CTHULHU_2),
				new Page(3, NecronomiconResources.HASTUR_SEAL, NecronomiconText.HASTUR_1),
				new Page(4, NecronomiconText.HASTUR_2),
				new Page(5, NecronomiconResources.JZAHAR_SEAL, NecronomiconText.JZAHAR_1),
				new Page(6, NecronomiconText.JZAHAR_2));
		addPages("overworld", "materials", new Page(1, new ItemStack(ACBlocks.abyssalnite_ore), NecronomiconText.MATERIAL_ABYSSALNITE_1),
				new Page(2, NecronomiconText.MATERIAL_ABYSSALNITE_2),
				new Page(3, new ItemStack(ACBlocks.darkstone), NecronomiconText.MATERIAL_DARKSTONE_1),
				new Page(4, NecronomiconText.MATERIAL_DARKSTONE_2),
				new Page(5, new ItemStack(ACBlocks.coralium_ore), NecronomiconText.MATERIAL_CORALIUM_1),
				new Page(6, NecronomiconText.MATERIAL_CORALIUM_2),
				new Page(7, new ItemStack(ACBlocks.darklands_oak_sapling), NecronomiconText.MATERIAL_DARKLANDS_OAK_1),
				new Page(8, NecronomiconText.MATERIAL_DARKLANDS_OAK_2),
				new Page(9, new ItemStack(ACBlocks.nitre_ore), NecronomiconText.MATERIAL_NITRE_1),
				new Page(10, NecronomiconText.MATERIAL_NITRE_2),
				new Page(11, new ItemStack(ACBlocks.liquid_antimatter), NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_1),
				new Page(12, NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_2),
				new Page(13, new ItemStack(ACBlocks.darklands_grass), NecronomiconText.MATERIAL_DARKLANDS_GRASS_1),
				new Page(14, NecronomiconText.MATERIAL_DARKLANDS_GRASS_2));
		addPages("overworld", "progression", new Page(1, NecronomiconText.PROGRESSION_OVERWORLD_1),
				new Page(2, NecronomiconText.PROGRESSION_OVERWORLD_2),
				new Page(3, NecronomiconText.PROGRESSION_OVERWORLD_3),
				new Page(4, NecronomiconText.PROGRESSION_OVERWORLD_4),
				new Page(5, NecronomiconText.PROGRESSION_OVERWORLD_5));
		addPages("overworld", "entities", new Page(1, NecronomiconResources.ABYSSAL_ZOMBIE, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_1),
				new Page(2, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_2),
				new Page(3, NecronomiconResources.DEPTHS_GHOUL, NecronomiconText.ENTITY_DEPTHS_GHOUL_1),
				new Page(4, NecronomiconText.ENTITY_DEPTHS_GHOUL_2),
				new Page(5, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1),
				new Page(6, NecronomiconText.ENTITY_SHADOW_CREATURE_2),
				new Page(7, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1),
				new Page(8, NecronomiconText.ENTITY_SHADOW_MONSTER_2),
				new Page(9, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1),
				new Page(10, NecronomiconText.ENTITY_SHADOW_BEAST_2),
				new Page(11, NecronomiconResources.ANTI_ENTITIES, NecronomiconText.ENTITY_ANTI_1),
				new Page(12, NecronomiconText.ENTITY_ANTI_2),
				new Page(13, NecronomiconResources.EVIL_ANIMALS, NecronomiconText.ENTITY_EVIL_ANIMALS_1),
				new Page(14, NecronomiconText.ENTITY_EVIL_ANIMALS_2),
				new Page(15, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
				new Page(16, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2));
		addPages("overworld", "specialmaterials", new Page(1, new CraftingStack(ACBlocks.coralium_infused_stone, Blocks.stone, Blocks.stone, Blocks.stone, ACItems.coralium_gem_cluster_3,
				ACItems.coralium_gem_cluster_3, ACItems.coralium_gem_cluster_3, Blocks.stone, Blocks.stone, Blocks.stone),
				NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_1),
				new Page(2, NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_2),
				new Page(3, new CraftingStack(ACItems.shadow_gem), NecronomiconText.CRAFTING_SHADOW_GEM_1),
				new Page(4, NecronomiconText.CRAFTING_SHADOW_GEM_2),
				new Page(5, new CraftingStack(ACItems.shard_of_oblivion), NecronomiconText.CRAFTING_SHARD_OF_OBLIVION),
				new Page(6, new CraftingStack(ACItems.gateway_key), NecronomiconText.CRAFTING_GATEWAY_KEY),
				new Page(7, new CraftingStack(new ItemStack(ACItems.skin, 1, 0)), NecronomiconText.CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND_1),
				new Page(8, new CraftingStack(ACItems.abyssal_wasteland_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_C));
		addPages("overworld", "armortools", new Page(1, new CraftingStack(ACItems.staff_of_rending), NecronomiconText.CRAFTING_STAFF_OF_RENDING_1),
				new Page(2, NecronomiconText.CRAFTING_STAFF_OF_RENDING_2));
		addPages("abyssalwasteland", "materials", new Page(1, new ItemStack(ACBlocks.abyssal_stone), NecronomiconText.MATERIAL_ABYSSAL_STONE_1),
				new Page(2, NecronomiconText.MATERIAL_ABYSSAL_STONE_2),
				new Page(3, new ItemStack(ACBlocks.abyssal_diamond_ore), NecronomiconText.MATERIAL_ABYSSAL_ORES_1),
				new Page(4, NecronomiconText.MATERIAL_ABYSSAL_ORES_2),
				new Page(5, new ItemStack(ACBlocks.abyssal_coralium_ore), NecronomiconText.MATERIAL_ABYSSAL_CORALIUM_1),
				new Page(6, NecronomiconText.MATERIAL_ABYSSAL_CORALIUM_2),
				new Page(7, new ItemStack(ACBlocks.liquified_coralium_ore), NecronomiconText.MATERIAL_LIQUIFIED_CORALIUM_1),
				new Page(8, NecronomiconText.MATERIAL_LIQUIFIED_CORALIUM_2),
				new Page(9, new ItemStack(ACBlocks.pearlescent_coralium_ore), NecronomiconText.MATERIAL_PEARLESCENT_CORALIUM_1),
				new Page(10, NecronomiconText.MATERIAL_PEARLESCENT_CORALIUM_2),
				new Page(11, new ItemStack(ACBlocks.liquid_coralium), NecronomiconText.MATERIAL_LIQUID_CORALIUM_1),
				new Page(12, NecronomiconText.MATERIAL_LIQUID_CORALIUM_2),
				new Page(13, new ItemStack(ACBlocks.dreadlands_infused_powerstone), NecronomiconText.MATERIAL_DREADLANDS_INFUSED_POWERSTONE_1),
				new Page(14, NecronomiconText.MATERIAL_DREADLANDS_INFUSED_POWERSTONE_2));
		addPages("abyssalwasteland", "progression", new Page(1, NecronomiconText.PROGRESSION_ABYSSAL_1),
				new Page(2, NecronomiconText.PROGRESSION_ABYSSAL_2),
				new Page(3, NecronomiconText.PROGRESSION_ABYSSAL_3));
		addPages("abyssalwasteland", "entities", new Page(1, NecronomiconResources.ABYSSAL_ZOMBIE, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_1),
				new Page(2, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_2),
				new Page(3, NecronomiconResources.DEPTHS_GHOUL, NecronomiconText.ENTITY_DEPTHS_GHOUL_1),
				new Page(4, NecronomiconText.ENTITY_DEPTHS_GHOUL_2),
				new Page(5, NecronomiconResources.SKELETON_GOLIATH, NecronomiconText.ENTITY_SKELETON_GOLIATH_1),
				new Page(6, NecronomiconText.ENTITY_SKELETON_GOLIATH_2),
				new Page(7, NecronomiconResources.SPECTRAL_DRAGON, NecronomiconText.ENTITY_SPECTRAL_DRAGON_1),
				new Page(8, NecronomiconText.ENTITY_SPECTRAL_DRAGON_2),
				new Page(9, NecronomiconResources.ASORAH, NecronomiconText.ENTITY_ASORAH_1),
				new Page(10, NecronomiconText.ENTITY_ASORAH_2),
				new Page(11, NecronomiconResources.CORALIUM_INFESTED_SQUID, NecronomiconText.ENTITY_CORALIUM_INFESTED_SQUID_1),
				new Page(12, NecronomiconText.ENTITY_CORALIUM_INFESTED_SQUID_2),
				new Page(13, NecronomiconResources.LESSER_SHOGGOTH_ABYSSAL, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
				new Page(14, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2));
		addPages("abyssalwasteland", "specialmaterials", new Page(1, new CraftingStack(ACItems.powerstone_tracker),
				NecronomiconText.CRAFTING_POWERSTONE_TRACKER),
				new Page(3, new CraftingStack(ACBlocks.transmutator_idle), NecronomiconText.CRAFTING_TRANSMUTATOR_1),
				new Page(4, NecronomiconText.CRAFTING_TRANSMUTATOR_2),
				new Page(5, new CraftingStack(ACItems.chunk_of_coralium), NecronomiconText.CRAFTING_CORALIUM_CHUNK),
				new Page(6, new CraftingStack(ACItems.coralium_plate), NecronomiconText.CRAFTING_CORALIUM_PLATE),
				new Page(7, new CraftingStack(new ItemStack(ACItems.skin, 1, 1)), NecronomiconText.CRAFTING_SKIN_OF_THE_DREADLANDS_1),
				new Page(8, new CraftingStack(ACItems.dreadlands_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_D));
		addPages("abyssalwasteland", "armortools", new Page(1, new CraftingStack(ACItems.plated_coralium_helmet),
				NecronomiconText.CRAFTING_PLATED_CORALIUM_HELMET),
				new Page(2, new CraftingStack(ACItems.plated_coralium_chestplate), NecronomiconText.CRAFTING_PLATED_CORALIUM_CHESTPLATE),
				new Page(3, new CraftingStack(ACItems.plated_coralium_leggings), NecronomiconText.CRAFTING_PLATED_CORALIUM_LEGGINGS),
				new Page(4, new CraftingStack(ACItems.plated_coralium_boots), NecronomiconText.CRAFTING_PLATED_CORALIUM_BOOTS),
				new Page(5, new CraftingStack(ACItems.coralium_longbow), NecronomiconText.CRAFTING_CORALIUM_LONGBOW));
		addPages("dreadlands", "materials", new Page(1, new ItemStack(ACBlocks.dreadstone), NecronomiconText.MATERIAL_DREADSTONE_1),
				new Page(2, NecronomiconText.MATERIAL_DREADSTONE_2),
				new Page(3, new ItemStack(ACBlocks.abyssalnite_stone), NecronomiconText.MATERIAL_ABYSSALNITE_STONE_1),
				new Page(4, NecronomiconText.MATERIAL_ABYSSALNITE_STONE_2),
				new Page(5, new ItemStack(ACBlocks.dreadlands_abyssalnite_ore), NecronomiconText.MATERIAL_DREADLANDS_ABYSSALNITE_1),
				new Page(6, NecronomiconText.MATERIAL_DREADLANDS_ABYSSALNITE_2),
				new Page(7, new ItemStack(ACBlocks.dreaded_abyssalnite_ore), NecronomiconText.MATERIAL_DREADED_ABYSSALNITE_1),
				new Page(8, NecronomiconText.MATERIAL_DREADED_ABYSSALNITE_2),
				new Page(9, new ItemStack(ACBlocks.dreadlands_grass), NecronomiconText.MATERIAL_DREADLANDS_GRASS_1),
				new Page(10, NecronomiconText.MATERIAL_DREADLANDS_GRASS_2),
				new Page(11, new ItemStack(ACBlocks.dreadlands_sapling), NecronomiconText.MATERIAL_DREADLANDS_TREE_1),
				new Page(12, NecronomiconText.MATERIAL_DREADLANDS_TREE_2));
		addPages("dreadlands", "progression", new Page(1, NecronomiconText.PROGRESSION_DREADLANDS_1),
				new Page(2, NecronomiconText.PROGRESSION_DREADLANDS_2),
				new Page(3, NecronomiconText.PROGRESSION_DREADLANDS_3));
		addPages("dreadlands", "entities", new Page(1, NecronomiconResources.ABYSSALNITE_GOLEM, NecronomiconText.ENTITY_ABYSSALNITE_GOLEM_1),
				new Page(2, NecronomiconText.ENTITY_ABYSSALNITE_GOLEM_2),
				new Page(3, NecronomiconResources.DREADED_ABYSSALNITE_GOLEM, NecronomiconText.ENTITY_DREADED_ABYSSALNITE_GOLEM_1),
				new Page(4, NecronomiconText.ENTITY_DREADED_ABYSSALNITE_GOLEM_2),
				new Page(5, NecronomiconResources.DREADLING, NecronomiconText.ENTITY_DREADLING_1),
				new Page(6, NecronomiconText.ENTITY_DREADLING_2),
				new Page(7, NecronomiconResources.DREAD_SPAWN, NecronomiconText.ENTITY_DREAD_SPAWN_1),
				new Page(8, NecronomiconText.ENTITY_DREAD_SPAWN_2),
				new Page(9, NecronomiconResources.DEMON_ANIMALS, NecronomiconText.ENTITY_DEMON_ANIMALS_1),
				new Page(10, NecronomiconText.ENTITY_DEMON_ANIMALS_2),
				new Page(11, NecronomiconResources.SPAWN_OF_CHAGAROTH, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_1),
				new Page(12, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_2),
				new Page(13, NecronomiconResources.FIST_OF_CHAGAROTH, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_1),
				new Page(14, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_2),
				new Page(15, NecronomiconResources.DREADGUARD, NecronomiconText.ENTITY_DREADGUARD_1),
				new Page(16, NecronomiconText.ENTITY_DREADGUARD_2),
				new Page(17, NecronomiconResources.CHAGAROTH, NecronomiconText.ENTITY_CHAGAROTH_1),
				new Page(18, NecronomiconText.ENTITY_CHAGAROTH_2),
				new Page(19, NecronomiconResources.LESSER_SHOGGOTH_DREADED, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
				new Page(20, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2));
		addPages("dreadlands", "specialmaterials", new Page(1, new CraftingStack(ACBlocks.block_of_dreadium), NecronomiconText.CRAFTING_DREADIUM_1),
				new Page(2, NecronomiconText.CRAFTING_DREADIUM_2),
				new Page(3, new CraftingStack(ACBlocks.crystallizer_idle), NecronomiconText.CRAFTING_CRYSTALLIZER_1),
				new Page(4, NecronomiconText.CRAFTING_CRYSTALLIZER_2),
				new Page(5, new CraftingStack(ACItems.dread_cloth), NecronomiconText.CRAFTING_DREAD_CLOTH),
				new Page(6, new CraftingStack(ACItems.dreadium_plate), NecronomiconText.CRAFTING_DREADIUM_PLATE),
				new Page(7, new CraftingStack(ACItems.dreadium_katana_hilt), NecronomiconText.CRAFTING_DREADIUM_HILT),
				new Page(8, new CraftingStack(ACItems.dreadium_katana_blade), NecronomiconText.CRAFTING_DREADIUM_BLADE),
				new Page(9, new CraftingStack(new ItemStack(ACItems.skin, 1, 2)), NecronomiconText.CRAFTING_SKIN_OF_OMOTHOL_1),
				new Page(10, new CraftingStack(ACItems.omothol_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_O));
		addPages("dreadlands", "armortools", new Page(1, new CraftingStack(ACItems.dreadium_samurai_helmet),
				NecronomiconText.CRAFTING_DREADIUM_SAMURAI_HELMET),
				new Page(2, new CraftingStack(ACItems.dreadium_samurai_chestplate), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_CHESTPLATE),
				new Page(3, new CraftingStack(ACItems.dreadium_samurai_leggings), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_LEGGINGS),
				new Page(4, new CraftingStack(ACItems.dreadium_samurai_boots), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_BOOTS),
				new Page(5, new CraftingStack(ACItems.dreadium_katana), NecronomiconText.CRAFTING_DREADIUM_KATANA));
		addPages("omothol", "materials", new Page(1, new ItemStack(ACBlocks.omothol_stone), NecronomiconText.MATERIAL_OMOTHOL_STONE_1),
				new Page(2, NecronomiconText.MATERIAL_OMOTHOL_STONE_2),
				new Page(3, new ItemStack(ACBlocks.ethaxium), NecronomiconText.MATERIAL_ETHAXIUM_1),
				new Page(4, NecronomiconText.MATERIAL_ETHAXIUM_2),
				new Page(5, new ItemStack(ACBlocks.dark_ethaxium_brick), NecronomiconText.MATERIAL_DARK_ETHAXIUM_1),
				new Page(6, NecronomiconText.MATERIAL_DARK_ETHAXIUM_2));
		addPages("omothol", "progression", new Page(1, NecronomiconText.PROGRESSION_OMOTHOL_1),
				new Page(2, NecronomiconText.PROGRESSION_OMOTHOL_2));
		addPages("omothol", "entities", new Page(1, NecronomiconResources.REMNANT, NecronomiconText.ENTITY_REMNANT_1),
				new Page(2, NecronomiconText.ENTITY_REMNANT_2),
				new Page(3, NecronomiconResources.OMOTHOL_GHOUL, NecronomiconText.ENTITY_OMOTHOL_GHOUL_1),
				new Page(4, NecronomiconText.ENTITY_OMOTHOL_GHOUL_2),
				new Page(5, NecronomiconResources.OMOTHOL_WARDEN, NecronomiconText.ENTITY_OMOTHOL_WARDEN_1),
				new Page(6, NecronomiconText.ENTITY_OMOTHOL_WARDEN_2),
				new Page(7, NecronomiconResources.MINION_OF_THE_GATEKEEPER, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_1),
				new Page(8, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_2),
				new Page(9, NecronomiconResources.JZAHAR, NecronomiconText.ENTITY_JZAHAR_1),
				new Page(10, NecronomiconText.ENTITY_JZAHAR_2),
				new Page(11, NecronomiconResources.LESSER_SHOGGOTH_OMOTHOL, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
				new Page(12, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2));
		addPages("omothol", "specialmaterials", new Page(1, new CraftingStack(ACItems.life_crystal), NecronomiconText.CRAFTING_LIFE_CRYSTAL_1),
				new Page(2, NecronomiconText.CRAFTING_LIFE_CRYSTAL_2),
				new Page(3, new CraftingStack(ACItems.ethaxium_ingot, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick,
						ACItems.life_crystal, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick), NecronomiconText.CRAFTING_ETHAXIUM_INGOT_1),
						new Page(4, NecronomiconText.CRAFTING_ETHAXIUM_INGOT_2),
						new Page(5, new CraftingStack(ACItems.blank_engraving), NecronomiconText.CRAFTING_BLANK_ENGRAVING_1),
						new Page(6, NecronomiconText.CRAFTING_BLANK_ENGRAVING_2),
						new Page(7, new CraftingStack(ACItems.coin, null, Items.iron_ingot, null, Items.iron_ingot, Items.flint, Items.iron_ingot, null, Items.iron_ingot, null), NecronomiconText.CRAFTING_COIN),
						new Page(8, new CraftingStack(ACBlocks.engraver), NecronomiconText.CRAFTING_ENGRAVER),
						new Page(9, new CraftingStack(ACItems.small_crystal_bag), NecronomiconText.CRAFTING_CRYSTAL_BAG_1),
						new Page(10, NecronomiconText.CRAFTING_CRYSTAL_BAG_2),
						new Page(11, new CraftingStack(ACBlocks.materializer), NecronomiconText.CRAFTING_MATERIALIZER_1),
						new Page(12, NecronomiconText.CRAFTING_MATERIALIZER_2),
						new Page(13, new CraftingStack(ACItems.abyssalnomicon), NecronomiconText.CRAFTING_ABYSSALNOMICON_1),
						new Page(14, NecronomiconText.CRAFTING_ABYSSALNOMICON_2));
		addPages("darkrealm", "materials", new Page(1, new ItemStack(ACBlocks.darkstone), NecronomiconText.MATERIAL_DARKSTONE_1),
				new Page(2, NecronomiconText.MATERIAL_DARKSTONE_2));
		addPages("darkrealm", "progression", new Page(1, NecronomiconText.PROGRESSION_DARK_REALM_1),
				new Page(2, NecronomiconText.PROGRESSION_DARK_REALM_2));
		addPages("darkrealm", "entities", new Page(1, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1),
				new Page(2, NecronomiconText.ENTITY_SHADOW_CREATURE_2),
				new Page(3, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1),
				new Page(4, NecronomiconText.ENTITY_SHADOW_MONSTER_2),
				new Page(5, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1),
				new Page(6, NecronomiconText.ENTITY_SHADOW_BEAST_2),
				new Page(7, NecronomiconResources.SHADOW_TITAN, NecronomiconText.ENTITY_SHADOW_TITAN_1),
				new Page(8, NecronomiconText.ENTITY_SHADOW_TITAN_2),
				new Page(9, NecronomiconResources.SACTHOTH, NecronomiconText.ENTITY_SACTHOTH_1),
				new Page(10, NecronomiconText.ENTITY_SACTHOTH_2),
				new Page(11, NecronomiconResources.LESSER_SHOGGOTH_SHADOW, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
				new Page(12, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2));
		addPages("rituals", "gettingstarted", new Page(1, NecronomiconResources.RITUAL_TUT_1, NecronomiconText.RITUAL_TUT_1),
				new Page(2, NecronomiconText.RITUAL_TUT_2),
				new Page(3, NecronomiconResources.RITUAL_TUT_2, NecronomiconText.RITUAL_TUT_3),
				new Page(4, NecronomiconResources.BLANK, NecronomiconText.RITUAL_TUT_4),
				new Page(5, NecronomiconResources.RITUAL_TUT_3, NecronomiconText.RITUAL_TUT_5),
				new Page(6, NecronomiconText.RITUAL_TUT_6),
				new Page(7, NecronomiconText.RITUAL_TUT_7));
		addPages("rituals", "materials", new Page(1, new ItemStack(ACBlocks.ritual_altar), NecronomiconText.MATERIAL_RITUAL_ALTAR_1),
				new Page(2, new ItemStack(ACBlocks.ritual_pedestal), NecronomiconText.MATERIAL_RITUAL_PEDESTAL_1),
				new Page(3, new ItemStack(ACBlocks.monolith_stone), NecronomiconText.MATERIAL_MONOLITH_STONE_1));
		addPages("rituals", "specialmaterials", new Page(1, new CraftingStack(ACBlocks.energy_pedestal), NecronomiconText.CRAFTING_ENERGY_PEDESTAL_1),
				new Page(2, NecronomiconText.CRAFTING_ENERGY_PEDESTAL_2),
				new Page(3, new CraftingStack(ACBlocks.monolith_pillar), NecronomiconText.CRAFTING_MONOLITH_PILLAR_1),
				new Page(4, new CraftingStack(new ItemStack(ACItems.ritual_charm, 1, 0)), NecronomiconText.CRAFTING_RITUAL_CHARM_1),
				new Page(5, new CraftingStack(ACBlocks.sacrificial_altar), NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_1),
				new Page(6, NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_2),
				new Page(7, new CraftingStack(ACBlocks.energy_collector), NecronomiconText.CRAFTING_ENERGY_COLLECTOR),
				new Page(8, new CraftingStack(ACBlocks.energy_relay), NecronomiconText.CRAFTING_ENERGY_RELAY));
		addPages("rituals", "potentialenergy", new Page(1, NecronomiconText.PE_TUT_1),
				new Page(2, NecronomiconResources.PE_TUT_1, NecronomiconText.PE_TUT_2),
				new Page(3, NecronomiconResources.PE_TUT_2, NecronomiconText.PE_TUT_3),
				new Page(4, NecronomiconText.PE_TUT_4),
				new Page(5, NecronomiconResources.PE_TUT_3, NecronomiconText.PE_TUT_5),
				new Page(6, NecronomiconText.PE_TUT_6),
				new Page(7, NecronomiconResources.PE_TUT_4, NecronomiconText.PE_TUT_7),
				new Page(8, NecronomiconText.PE_TUT_8),
				new Page(9, NecronomiconResources.PE_TUT_5, NecronomiconText.PE_TUT_9),
				new Page(10, NecronomiconResources.PE_TUT_6, NecronomiconText.PE_TUT_10),
				new Page(11, NecronomiconResources.PE_TUT_7, NecronomiconText.PE_TUT_11),
				new Page(12, NecronomiconResources.PE_TUT_8, NecronomiconText.PE_TUT_12),
				new Page(13, NecronomiconResources.PE_TUT_9, NecronomiconText.PE_TUT_13),
				new Page(14, NecronomiconResources.PE_TUT_10, NecronomiconText.PE_TUT_14),
				new Page(15, NecronomiconResources.PE_TUT_11, NecronomiconText.PE_TUT_15),
				new Page(16, NecronomiconResources.PE_TUT_12, NecronomiconText.PE_TUT_16),
				new Page(17, NecronomiconText.PE_TUT_17));
		addPages("miscinfo", "specialmaterials", new Page(1, new CraftingStack(ACItems.cobblestone_upgrade_kit), NecronomiconText.CRAFTING_UPGRADE_KIT_1),
				new Page(2, new CraftingStack(ACItems.iron_upgrade_kit), NecronomiconText.CRAFTING_UPGRADE_KIT_2),
				new Page(3, new CraftingStack(new ItemStack(ACItems.iron_plate, 2), null, null, null, null, Items.iron_ingot, null, null, Items.iron_ingot, null), NecronomiconText.CRAFTING_IRON_PLATE),
				new Page(4, new CraftingStack(ACItems.washcloth), NecronomiconText.CRAFTING_WASHCLOTH),
				new Page(5, new CraftingStack(ACItems.mre), NecronomiconText.CRAFTING_MRE),
				new Page(6, new CraftingStack(ACItems.beef_on_a_plate), NecronomiconText.CRAFTING_PLATE_FOOD),
				new Page(7, new CraftingStack(ACBlocks.odb_core), NecronomiconText.CRAFTING_ODB_CORE),
				new Page(8, new CraftingStack(ACBlocks.oblivion_deathbomb), NecronomiconText.CRAFTING_ODB),
				new Page(9, new CraftingStack(ACItems.carbon_cluster), NecronomiconText.CRAFTING_CARBON_CLUSTER),
				new Page(10, new CraftingStack(ACItems.dense_carbon_cluster), NecronomiconText.CRAFTING_DENSE_CARBON_CLUSTER),
				new Page(11, new CraftingStack(ACBlocks.wooden_crate), NecronomiconText.CRAFTING_CRATE));
		addPages("miscinfo", "enchantments", new Page(1, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.coralium_enchantment, 1)), NecronomiconText.ENCHANTMENT_CORALIUM),
				new Page(2, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.dread_enchantment, 1)), NecronomiconText.ENCHANTMENT_DREAD),
				new Page(3, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.light_pierce, AbyssalCraftAPI.light_pierce.getMaxLevel())), NecronomiconText.ENCHANTMENT_LIGHT_PIERCE),
				new Page(4, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.iron_wall, 1)), NecronomiconText.ENCHANTMENT_IRON_WALL));
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
			chapter = new Chapter("patrons", NecronomiconText.LABEL_PATRONS);
			chapter.addPage(new Page(1, new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/patreon/saice.png"), "Saice Shoop"));
			chapter.addPage(new Page(2, new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/patreon/minecreatr.png"), "Minecreatr"));
			chapter.addPage(new Page(3, new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"), "Kendoshii"));
		}

		if(chapter != null)
			GuiNecronomiconInformation.setPatreonInfo(chapter);
	}
}