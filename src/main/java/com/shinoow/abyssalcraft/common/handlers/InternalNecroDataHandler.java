/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.internal.DummyNecroDataHandler;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.*;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.api.necronomicon.condition.*;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconPlacesOfPower;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.lib.*;
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
		Chapter outergods = new Chapter("outergods", NecronomiconText.LABEL_OUTER_GODS, 0);
		Chapter greatoldones = new Chapter("greatoldones", NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0);
		internalNecroData.add(new NecroData("greatoldones", NecronomiconText.LABEL_PANTHEON, 0, NecronomiconText.INFORMATION_GREAT_OLD_ONES,
				outergods, greatoldones));
		Chapter overworldmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0);
		Chapter overworldprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0);
		Chapter overworldentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES, 0);
		Chapter overworldspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0);
		Chapter overworldarmortools = new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0);
		internalNecroData.add(new NecroData("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, 0, NecronomiconText.INFORMATION_OVERWORLD,
				overworldmaterials, overworldprogression, overworldentities, overworldspecialmaterials, overworldarmortools));
		Chapter abyssalwastelandmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 1);
		Chapter abyssalwastelandprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION, 1);
		Chapter abyssalwastelandentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES, 1);
		Chapter abyssalwastelandspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1);
		Chapter abyssalwastelandarmortools = new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1);
		Chapter abyssalwastelandplague = new Chapter("coraliumplague", NecronomiconText.LABEL_CORALIUM_PLAGUE, 1, new MiscCondition("coralium_plague"));
		internalNecroData.add(new NecroData("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, 1, NecronomiconText.INFORMATION_ABYSSAL_WASTELAND,
				new NecronomiconCondition(1), abyssalwastelandmaterials, abyssalwastelandprogression, abyssalwastelandentities, abyssalwastelandspecialmaterials,
				abyssalwastelandarmortools, abyssalwastelandplague));
		Chapter dreadlandsmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 2);
		Chapter dreadlandsprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION, 2);
		Chapter dreadlandsentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES, 2);
		Chapter dreadlandsspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2);
		Chapter dreadlandsarmortools = new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2);
		Chapter dreadlandsplague = new Chapter("dreadplague", NecronomiconText.LABEL_DREAD_PLAGUE, 2, new MiscCondition("dread_plague"));
		internalNecroData.add(new NecroData("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, 2, NecronomiconText.INFORMATION_DREADLANDS,
				new NecronomiconCondition(2), dreadlandsmaterials, dreadlandsprogression, dreadlandsentities, dreadlandsspecialmaterials, dreadlandsarmortools,
				dreadlandsplague));
		Chapter omotholmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 3);
		Chapter omotholprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3);
		Chapter omotholentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES, 3);
		Chapter omotholspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3);
		Chapter itemtransportsystem = new Chapter("itemtransportsystem", NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3);
		internalNecroData.add(new NecroData("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, 3, NecronomiconText.INFORMATION_OMOTHOL,
				new NecronomiconCondition(3), omotholmaterials, omotholprogression, omotholentities, omotholspecialmaterials, itemtransportsystem));
		Chapter darkrealmmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 3);
		Chapter darkrealmprogression = new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3);
		Chapter darkrealmentities = new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES, 3);
		internalNecroData.add(new NecroData("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, 3, NecronomiconText.INFORMATION_DARK_REALM,
				new NecronomiconCondition(3), darkrealmmaterials, darkrealmprogression, darkrealmentities));
		Chapter ritualgettingstarted = new Chapter("gettingstarted", NecronomiconText.LABEL_GETTING_STARTED, 0);
		Chapter ritualmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0);
		internalNecroData.add(new NecroData("rituals", NecronomiconText.LABEL_INFO, 0, ritualgettingstarted, ritualmaterials));
		Chapter peinfo = new Chapter("information", NecronomiconText.LABEL_INFO, 0);
		Chapter pematerials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0);
		Chapter pespecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0);
		Chapter placesOfPowerInfo = new Chapter("information", NecronomiconText.LABEL_INFO, 0);
		GuiInstance structures = new GuiInstance(0, NecronomiconText.LABEL_STRUCTURES, "structures") {

			@Override
			public IUnlockCondition getCondition() {

				return new DefaultCondition();
			}

			@Override
			@SideOnly(Side.CLIENT)
			public GuiScreen getOpenGui(int bookType, GuiScreen parent) {

				return new GuiNecronomiconPlacesOfPower(bookType, (GuiNecronomicon) parent);
			}
		};
		NecroData placesOfPower = new NecroData("placesofpower", NecronomiconText.LABEL_PLACES_OF_POWER, 0, NecronomiconText.PLACES_OF_POWER_INFO, placesOfPowerInfo, structures);
		internalNecroData.add(new NecroData("potentialenergy", NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, peinfo, pematerials, pespecialmaterials, placesOfPower));
		Chapter miscspecialmaterials = new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0);
		Chapter miscenchantments = new Chapter("enchantments", NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0);
		Chapter miscstatues = new Chapter("decorativestatues", NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0);
		internalNecroData.add(new NecroData("miscinfo", NecronomiconText.LABEL_MISC_INFORMATION, 0, NecronomiconText.MISC_INFORMATION, miscspecialmaterials,
				miscenchantments, miscstatues));
		Chapter spellgettingstarted = new Chapter("gettingstarted", NecronomiconText.LABEL_GETTING_STARTED, 0);
		Chapter spellcasting = new Chapter("casting", NecronomiconText.LABEL_CASTING, 0);
		Chapter spellmaterials = new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS, 0);
		internalNecroData.add(new NecroData("spells", NecronomiconText.LABEL_INFO, 0, spellgettingstarted, spellcasting, spellmaterials));
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

	private void addPages(String necroidentifier, String chapteridentifier, Page...pages){
		for(Page page : pages)
			addPage(page, necroidentifier, chapteridentifier);
	}

	private void addInternalPages(String outernecroidentifier, String innernecroidentifier, String chapteridentifier, Page...pages) {
		for(Page page : pages)
			for(NecroData data : internalNecroData)
				if(data.getIdentifier().equals(outernecroidentifier))
					for(INecroData d : data.getContainedData())
						if(d instanceof NecroData && d.getIdentifier().equals(innernecroidentifier))
							for(INecroData d1 : ((NecroData) d).getContainedData())
								if(d1 instanceof Chapter && d1.getIdentifier().equals(chapteridentifier))
									((Chapter)d1).addPage(page);
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
		addPages("greatoldones", "outergods", new Page(1, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.AZATHOTH_SEAL, NecronomiconText.AZATHOTH_1),
				new Page(2, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.AZATHOTH_2),
				new Page(3, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.NYARLATHOTEP_SEAL, NecronomiconText.NYARLATHOTEP_1),
				new Page(4, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.NYARLATHOTEP_2),
				new Page(5, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.YOG_SOTHOTH_SEAL, NecronomiconText.YOG_SOTHOTH_1),
				new Page(6, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.YOG_SOTHOTH_2),
				new Page(7, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconResources.SHUB_NIGGURATH_SEAL, NecronomiconText.SHUB_NIGGURATH_1),
				new Page(8, NecronomiconText.LABEL_OUTER_GODS, 0, NecronomiconText.SHUB_NIGGURATH_2));
		addPages("greatoldones", "greatoldones", new Page(1, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconResources.CTHULHU_SEAL, NecronomiconText.CTHULHU_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconText.CTHULHU_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconResources.HASTUR_SEAL, NecronomiconText.HASTUR_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconText.HASTUR_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconResources.JZAHAR_SEAL, NecronomiconText.JZAHAR_1),
				new Page(6, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, 0, NecronomiconText.JZAHAR_2));
		addPages("overworld", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.abyssalnite_ore), NecronomiconText.MATERIAL_ABYSSALNITE_1),
				new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.stone, 1, 0), NecronomiconText.MATERIAL_DARKSTONE_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, NecronomiconText.MATERIAL_DARKSTONE_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.coralium_ore), NecronomiconText.MATERIAL_CORALIUM_1),
				new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, NecronomiconText.MATERIAL_CORALIUM_2),
				new Page(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.darklands_oak_sapling), NecronomiconText.MATERIAL_DARKLANDS_OAK_1),
				new Page(8, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.nitre_ore), NecronomiconText.MATERIAL_NITRE_1),
				new Page(9, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.liquid_antimatter), NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_1),
				new Page(10, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_2));
		addPages("overworld", "progression", new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_3),
				new Page(4, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_4),
				new Page(5, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 0, NecronomiconText.PROGRESSION_OVERWORLD_5));
		addPages("overworld", "entities", new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.ABYSSAL_ZOMBIE, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_1, new EntityCondition(EntityAbyssalZombie.class)),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_2, new EntityCondition(EntityAbyssalZombie.class)),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.DEPTHS_GHOUL, NecronomiconText.ENTITY_DEPTHS_GHOUL_1, new EntityCondition(EntityDepthsGhoul.class)),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_DEPTHS_GHOUL_2, new EntityCondition(EntityDepthsGhoul.class)),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1, new EntityCondition(EntityShadowCreature.class)),
				new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHADOW_CREATURE_2, new EntityCondition(EntityShadowCreature.class)),
				new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1, new EntityCondition(EntityShadowMonster.class)),
				new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHADOW_MONSTER_2, new EntityCondition(EntityShadowMonster.class)),
				new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1, new EntityCondition(EntityShadowBeast.class)),
				new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHADOW_BEAST_2, new EntityCondition(EntityShadowBeast.class)),
				new Page(11, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.ANTI_ENTITIES, NecronomiconText.ENTITY_ANTI_1, new EntityPredicateCondition(input -> IAntiEntity.class.isAssignableFrom(input))),
				new Page(12, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_ANTI_2, new EntityPredicateCondition(input -> IAntiEntity.class.isAssignableFrom(input))),
				new Page(13, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.EVIL_ANIMALS, NecronomiconText.ENTITY_EVIL_ANIMALS_1, new MultiEntityCondition(EntityEvilChicken.class, EntityEvilCow.class, EntityEvilpig.class, EntityEvilSheep.class)),
				new Page(14, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_EVIL_ANIMALS_2, new MultiEntityCondition(EntityEvilChicken.class, EntityEvilCow.class, EntityEvilpig.class, EntityEvilSheep.class)),
				new Page(15, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.SHUB_OFFSPRING, NecronomiconText.ENTITY_SHUB_OFFSPRING_1, new EntityCondition(EntityShubOffspring.class)),
				new Page(16, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_SHUB_OFFSPRING_2, new EntityCondition(EntityShubOffspring.class)),
				new Page(17, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, new EntityCondition(EntityLesserShoggoth.class)),
				new Page(18, NecronomiconText.LABEL_INFORMATION_ENTITIES, 0, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, new EntityCondition(EntityLesserShoggoth.class)));
		addPages("overworld", "specialmaterials", new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.coralium_infused_stone, Blocks.STONE, Blocks.STONE, Blocks.STONE, ACItems.coralium_gem_cluster_3,
				ACItems.coralium_gem_cluster_3, ACItems.coralium_gem_cluster_3, Blocks.STONE, Blocks.STONE, Blocks.STONE),
				NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.shadow_gem), NecronomiconText.CRAFTING_SHADOW_GEM_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_SHADOW_GEM_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.shard_of_oblivion), NecronomiconText.CRAFTING_SHARD_OF_OBLIVION),
				new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.gateway_key), NecronomiconText.CRAFTING_GATEWAY_KEY),
				new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(new ItemStack(ACItems.skin, 1, 0)), NecronomiconText.CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND_1, new DimensionCondition(ACLib.abyssal_wasteland_id)),
				new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.abyssal_wasteland_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_C, new DimensionCondition(ACLib.abyssal_wasteland_id)));
		addPages("overworld", "armortools", new Page(1, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0, new CraftingStack(ACItems.staff_of_rending), NecronomiconText.CRAFTING_STAFF_OF_RENDING_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 0, NecronomiconText.CRAFTING_STAFF_OF_RENDING_2));
		addPages("abyssalwasteland", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.stone, 1, 1), NecronomiconText.MATERIAL_ABYSSAL_STONE_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.abyssal_sand), NecronomiconText.MATERIAL_ABYSSAL_SAND),
				new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.fused_abyssal_sand), NecronomiconText.MATERIAL_FUSED_ABYSSAL_SAND),
				new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.abyssal_diamond_ore), NecronomiconText.MATERIAL_ABYSSAL_ORES_1),
				new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, NecronomiconText.MATERIAL_ABYSSAL_ORES_2),
				new Page(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.abyssal_coralium_ore), NecronomiconText.MATERIAL_ABYSSAL_CORALIUM_1),
				new Page(8, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.liquified_coralium_ore), NecronomiconText.MATERIAL_LIQUIFIED_CORALIUM_1),
				new Page(9, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.pearlescent_coralium_ore), NecronomiconText.MATERIAL_PEARLESCENT_CORALIUM_1),
				new Page(11, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.liquid_coralium), NecronomiconText.MATERIAL_LIQUID_CORALIUM_1),
				new Page(12, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, NecronomiconText.MATERIAL_LIQUID_CORALIUM_2),
				new Page(13, NecronomiconText.LABEL_INFORMATION_MATERIALS, 1, new ItemStack(ACBlocks.dreadlands_infused_powerstone), NecronomiconText.MATERIAL_DREADLANDS_INFUSED_POWERSTONE_1));
		addPages("abyssalwasteland", "progression", new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 1, NecronomiconText.PROGRESSION_ABYSSAL_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 1, NecronomiconText.PROGRESSION_ABYSSAL_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 1, NecronomiconText.PROGRESSION_ABYSSAL_3));
		addPages("abyssalwasteland", "entities", new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.ABYSSAL_ZOMBIE, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_1, new EntityCondition(EntityAbyssalZombie.class)),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_2, new EntityCondition(EntityAbyssalZombie.class)),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.DEPTHS_GHOUL, NecronomiconText.ENTITY_DEPTHS_GHOUL_1, new EntityCondition(EntityDepthsGhoul.class)),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_DEPTHS_GHOUL_2, new EntityCondition(EntityDepthsGhoul.class)),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.SKELETON_GOLIATH, NecronomiconText.ENTITY_SKELETON_GOLIATH_1, new EntityCondition(EntitySkeletonGoliath.class)),
				new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_SKELETON_GOLIATH_2, new EntityCondition(EntitySkeletonGoliath.class)),
				new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.SPECTRAL_DRAGON, NecronomiconText.ENTITY_SPECTRAL_DRAGON_1, new EntityCondition(EntityDragonMinion.class)),
				new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_SPECTRAL_DRAGON_2, new EntityCondition(EntityDragonMinion.class)),
				new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.ASORAH, NecronomiconText.ENTITY_ASORAH_1),
				new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_ASORAH_2),
				new Page(11, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.CORALIUM_INFESTED_SQUID, NecronomiconText.ENTITY_CORALIUM_INFESTED_SQUID_1, new EntityCondition(EntityCoraliumSquid.class)),
				new Page(12, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_CORALIUM_INFESTED_SQUID_2, new EntityCondition(EntityCoraliumSquid.class)),
				new Page(13, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, new EntityCondition(EntityLesserShoggoth.class)),
				new Page(14, NecronomiconText.LABEL_INFORMATION_ENTITIES, 1, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, new EntityCondition(EntityLesserShoggoth.class)));
		addPages("abyssalwasteland", "specialmaterials", new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.powerstone_tracker),
				NecronomiconText.CRAFTING_POWERSTONE_TRACKER),
				new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACBlocks.transmutator_idle), NecronomiconText.CRAFTING_TRANSMUTATOR_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, NecronomiconText.CRAFTING_TRANSMUTATOR_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.chunk_of_coralium), NecronomiconText.CRAFTING_CORALIUM_CHUNK),
				new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.coralium_plate), NecronomiconText.CRAFTING_CORALIUM_PLATE),
				new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(new ItemStack(ACItems.skin, 1, 1)), NecronomiconText.CRAFTING_SKIN_OF_THE_DREADLANDS_1, new DimensionCondition(ACLib.dreadlands_id)),
				new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 1, new CraftingStack(ACItems.dreadlands_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_D, new DimensionCondition(ACLib.dreadlands_id)));
		addPages("abyssalwasteland", "armortools", new Page(1, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_helmet),
				NecronomiconText.CRAFTING_PLATED_CORALIUM_HELMET),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_chestplate), NecronomiconText.CRAFTING_PLATED_CORALIUM_CHESTPLATE),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_leggings), NecronomiconText.CRAFTING_PLATED_CORALIUM_LEGGINGS),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.plated_coralium_boots), NecronomiconText.CRAFTING_PLATED_CORALIUM_BOOTS),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 1, new CraftingStack(ACItems.coralium_longbow), NecronomiconText.CRAFTING_CORALIUM_LONGBOW));
		addPages("abyssalwasteland", "coraliumplague", new Page(1, NecronomiconText.LABEL_CORALIUM_PLAGUE, 1, NecronomiconText.CORALIUM_PLAGUE_INFO_1, new MiscCondition("coralium_plague")),
				new Page(2, NecronomiconText.LABEL_CORALIUM_PLAGUE, 1, new CraftingStack(new ItemStack(ACItems.antidote, 1, 0)), NecronomiconText.CORALIUM_PLAGUE_INFO_2, new MiscCondition("coralium_plague")));
		addPages("dreadlands", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.stone, 1, 2), NecronomiconText.MATERIAL_DREADSTONE_1),
				new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.stone, 1, 3), NecronomiconText.MATERIAL_ABYSSALNITE_STONE_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, NecronomiconText.MATERIAL_ABYSSALNITE_STONE_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreadlands_abyssalnite_ore), NecronomiconText.MATERIAL_DREADLANDS_ABYSSALNITE_1),
				new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreaded_abyssalnite_ore), NecronomiconText.MATERIAL_DREADED_ABYSSALNITE_1),
				new Page(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreadlands_grass), NecronomiconText.MATERIAL_DREADLANDS_GRASS_1),
				new Page(8, NecronomiconText.LABEL_INFORMATION_MATERIALS, 2, new ItemStack(ACBlocks.dreadlands_sapling), NecronomiconText.MATERIAL_DREADLANDS_TREE_1));
		addPages("dreadlands", "progression", new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 2, NecronomiconText.PROGRESSION_DREADLANDS_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 2, NecronomiconText.PROGRESSION_DREADLANDS_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 2, NecronomiconText.PROGRESSION_DREADLANDS_3));
		addPages("dreadlands", "entities", new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.ABYSSALNITE_GOLEM, NecronomiconText.ENTITY_ABYSSALNITE_GOLEM_1, new EntityCondition(EntityAbygolem.class)),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_ABYSSALNITE_GOLEM_2, new EntityCondition(EntityAbygolem.class)),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DREADED_ABYSSALNITE_GOLEM, NecronomiconText.ENTITY_DREADED_ABYSSALNITE_GOLEM_1, new EntityCondition(EntityDreadgolem.class)),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DREADED_ABYSSALNITE_GOLEM_2, new EntityCondition(EntityDreadgolem.class)),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DREADLING, NecronomiconText.ENTITY_DREADLING_1, new EntityCondition(EntityDreadling.class)),
				new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DREADLING_2, new EntityCondition(EntityDreadling.class)),
				new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DREAD_SPAWN, NecronomiconText.ENTITY_DREAD_SPAWN_1, new MultiEntityCondition(EntityDreadSpawn.class, EntityGreaterDreadSpawn.class, EntityLesserDreadbeast.class)),
				new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DREAD_SPAWN_2, new MultiEntityCondition(EntityDreadSpawn.class, EntityGreaterDreadSpawn.class, EntityLesserDreadbeast.class)),
				new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DEMON_ANIMALS, NecronomiconText.ENTITY_DEMON_ANIMALS_1, new EntityPredicateCondition(input -> EntityDemonAnimal.class.isAssignableFrom(input))),
				new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DEMON_ANIMALS_2, new EntityPredicateCondition(input -> EntityDemonAnimal.class.isAssignableFrom(input))),
				new Page(11, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.SPAWN_OF_CHAGAROTH, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_1, new EntityCondition(EntityChagarothSpawn.class)),
				new Page(12, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_2, new EntityCondition(EntityChagarothSpawn.class)),
				new Page(13, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.FIST_OF_CHAGAROTH, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_1, new EntityCondition(EntityChagarothFist.class)),
				new Page(14, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_2, new EntityCondition(EntityChagarothFist.class)),
				new Page(15, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.DREADGUARD, NecronomiconText.ENTITY_DREADGUARD_1, new EntityCondition(EntityDreadguard.class)),
				new Page(16, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_DREADGUARD_2, new EntityCondition(EntityDreadguard.class)),
				new Page(17, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.CHAGAROTH, NecronomiconText.ENTITY_CHAGAROTH_1),
				new Page(18, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_CHAGAROTH_2),
				new Page(19, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, new EntityCondition(EntityLesserShoggoth.class)),
				new Page(20, NecronomiconText.LABEL_INFORMATION_ENTITIES, 2, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, new EntityCondition(EntityLesserShoggoth.class)));
		addPages("dreadlands", "specialmaterials", new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(new ItemStack(ACBlocks.ingot_block, 1, 2)), NecronomiconText.CRAFTING_DREADIUM_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, NecronomiconText.CRAFTING_DREADIUM_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACBlocks.crystallizer_idle), NecronomiconText.CRAFTING_CRYSTALLIZER_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, NecronomiconText.CRAFTING_CRYSTALLIZER_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dread_cloth), NecronomiconText.CRAFTING_DREAD_CLOTH),
				new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dreadium_plate), NecronomiconText.CRAFTING_DREADIUM_PLATE),
				new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dreadium_katana_hilt), NecronomiconText.CRAFTING_DREADIUM_HILT),
				new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.dreadium_katana_blade), NecronomiconText.CRAFTING_DREADIUM_BLADE),
				new Page(9, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(new ItemStack(ACItems.skin, 1, 2)), NecronomiconText.CRAFTING_SKIN_OF_OMOTHOL_1, new DimensionCondition(ACLib.omothol_id)),
				new Page(10, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 2, new CraftingStack(ACItems.omothol_necronomicon), NecronomiconText.CRAFTING_NECRONOMICON_O, new DimensionCondition(ACLib.omothol_id)));
		addPages("dreadlands", "armortools", new Page(1, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_helmet),
				NecronomiconText.CRAFTING_DREADIUM_SAMURAI_HELMET),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_chestplate), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_CHESTPLATE),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_leggings), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_LEGGINGS),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_samurai_boots), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_BOOTS),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, 2, new CraftingStack(ACItems.dreadium_katana), NecronomiconText.CRAFTING_DREADIUM_KATANA));
		addPages("dreadlands", "dreadplague", new Page(1, NecronomiconText.LABEL_DREAD_PLAGUE, 2, NecronomiconText.DREAD_PLAGUE_INFO_1, new MiscCondition("dread_plague")),
				new Page(2, NecronomiconText.LABEL_DREAD_PLAGUE, 2, new CraftingStack(new ItemStack(ACItems.antidote, 1, 1)), NecronomiconText.DREAD_PLAGUE_INFO_2, new MiscCondition("dread_plague")));
		addPages("omothol", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.stone, 1, 6), NecronomiconText.MATERIAL_OMOTHOL_STONE_1),
				new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.stone, 1, 5), NecronomiconText.MATERIAL_ETHAXIUM_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, NecronomiconText.MATERIAL_ETHAXIUM_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.dark_ethaxium_brick), NecronomiconText.MATERIAL_DARK_ETHAXIUM_1));
		addPages("omothol", "progression", new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_OMOTHOL_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_OMOTHOL_2));
		addPages("omothol", "entities", new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.REMNANT, NecronomiconText.ENTITY_REMNANT_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_REMNANT_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.OMOTHOL_GHOUL, NecronomiconText.ENTITY_OMOTHOL_GHOUL_1, new EntityCondition(EntityOmotholGhoul.class)),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_OMOTHOL_GHOUL_2, new EntityCondition(EntityOmotholGhoul.class)),
				//new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.OMOTHOL_WARDEN, NecronomiconText.ENTITY_OMOTHOL_WARDEN_1),
				//new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_OMOTHOL_WARDEN_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.MINION_OF_THE_GATEKEEPER, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_1, new EntityCondition(EntityGatekeeperMinion.class)),
				new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_2, new EntityCondition(EntityGatekeeperMinion.class)),
				new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.JZAHAR, NecronomiconText.ENTITY_JZAHAR_1),
				new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_JZAHAR_2),
				new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, new EntityCondition(EntityLesserShoggoth.class)),
				new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, new EntityCondition(EntityLesserShoggoth.class)));
		addPages("omothol", "specialmaterials", new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.life_crystal), NecronomiconText.CRAFTING_LIFE_CRYSTAL_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_LIFE_CRYSTAL_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.ethaxium_ingot, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick,
						ACItems.life_crystal, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.ethaxium_brick), NecronomiconText.CRAFTING_ETHAXIUM_INGOT_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_ETHAXIUM_INGOT_2),
				new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACBlocks.ethaxium_pillar), NecronomiconText.CRAFTING_ETHAXIUM_PILLAR),
				new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACBlocks.dark_ethaxium_pillar), NecronomiconText.CRAFTING_DARK_ETHAXIUM_PILLAR),
				new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.blank_engraving), NecronomiconText.CRAFTING_BLANK_ENGRAVING_1),
				new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_BLANK_ENGRAVING_2),
				new Page(9, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.coin, null, Items.IRON_INGOT, null, Items.IRON_INGOT, Items.FLINT, Items.IRON_INGOT, null, Items.IRON_INGOT, null), NecronomiconText.CRAFTING_COIN),
				new Page(10, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACBlocks.engraver), NecronomiconText.CRAFTING_ENGRAVER),
				new Page(11, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.small_crystal_bag), NecronomiconText.CRAFTING_CRYSTAL_BAG_1),
				new Page(12, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_CRYSTAL_BAG_2),
				new Page(13, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACBlocks.materializer), NecronomiconText.CRAFTING_MATERIALIZER_1),
				new Page(14, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_MATERIALIZER_2),
				new Page(15, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, new CraftingStack(ACItems.abyssalnomicon), NecronomiconText.CRAFTING_ABYSSALNOMICON_1),
				new Page(16, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 3, NecronomiconText.CRAFTING_ABYSSALNOMICON_2));
		addPages("omothol", "itemtransportsystem", new Page(1, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, new ItemStack(ACItems.configurator_shard), NecronomiconText.ITEM_TRANSPORT_TUT_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, new ItemStack(ACItems.configurator), NecronomiconText.ITEM_TRANSPORT_TUT_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconResources.ITEM_TRANSPORT_TUT_1, NecronomiconText.ITEM_TRANSPORT_TUT_3),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_4),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_5),
				new Page(6, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconResources.ITEM_TRANSPORT_TUT_2, NecronomiconText.ITEM_TRANSPORT_TUT_6),
				new Page(7, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_7),
				new Page(8, NecronomiconText.LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM, 3, NecronomiconText.ITEM_TRANSPORT_TUT_8));
		addPages("darkrealm", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, new ItemStack(ACBlocks.stone, 1, 0), NecronomiconText.MATERIAL_DARKSTONE_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 3, NecronomiconText.MATERIAL_DARKSTONE_2));
		addPages("darkrealm", "progression", new Page(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_DARK_REALM_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, 3, NecronomiconText.PROGRESSION_DARK_REALM_2));
		addPages("darkrealm", "entities", new Page(1, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1, new EntityCondition(EntityShadowCreature.class)),
				new Page(2, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SHADOW_CREATURE_2, new EntityCondition(EntityShadowCreature.class)),
				new Page(3, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1, new EntityCondition(EntityShadowMonster.class)),
				new Page(4, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SHADOW_MONSTER_2, new EntityCondition(EntityShadowMonster.class)),
				new Page(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1, new EntityCondition(EntityShadowBeast.class)),
				new Page(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SHADOW_BEAST_2, new EntityCondition(EntityShadowBeast.class)),
				//new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SHADOW_TITAN, NecronomiconText.ENTITY_SHADOW_TITAN_1),
				//new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SHADOW_TITAN_2),
				new Page(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.SACTHOTH, NecronomiconText.ENTITY_SACTHOTH_1),
				new Page(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_SACTHOTH_2),
				new Page(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1, new EntityCondition(EntityLesserShoggoth.class)),
				new Page(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, 3, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2, new EntityCondition(EntityLesserShoggoth.class)));
		addPages("rituals", "gettingstarted", new Page(1, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.RITUAL_TUT_1, NecronomiconText.RITUAL_TUT_1),
				new Page(2, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.RITUAL_TUT_2),
				new Page(3, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.RITUAL_TUT_2, NecronomiconText.RITUAL_TUT_3),
				new Page(4, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.BLANK, NecronomiconText.RITUAL_TUT_4),
				new Page(5, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconResources.RITUAL_TUT_3, NecronomiconText.RITUAL_TUT_5),
				new Page(6, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.RITUAL_TUT_6),
				new Page(7, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.RITUAL_TUT_7));
		addPages("rituals", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.ritual_altar), NecronomiconText.MATERIAL_RITUAL_ALTAR_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.ritual_pedestal), NecronomiconText.MATERIAL_RITUAL_PEDESTAL_1),
				new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.stone, 1, 7), NecronomiconText.MATERIAL_MONOLITH_STONE_1));
		addPages("potentialenergy", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACBlocks.stone, 1, 7), NecronomiconText.MATERIAL_MONOLITH_STONE_1));
		addPages("potentialenergy", "specialmaterials", new Page(1, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_pedestal), NecronomiconText.CRAFTING_ENERGY_PEDESTAL_1),
				new Page(2, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_ENERGY_PEDESTAL_2),
				new Page(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.monolith_pillar), NecronomiconText.CRAFTING_MONOLITH_PILLAR_1),
				new Page(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(new ItemStack(ACItems.ritual_charm, 1, 0)), NecronomiconText.CRAFTING_RITUAL_CHARM_1),
				new Page(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.sacrificial_altar), NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_1),
				new Page(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_2),
				new Page(7, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_collector), NecronomiconText.CRAFTING_ENERGY_COLLECTOR),
				new Page(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_relay), NecronomiconText.CRAFTING_ENERGY_RELAY),
				new Page(9, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.rending_pedestal), NecronomiconText.CRAFTING_RENDING_PEDESTAL),
				new Page(10, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACItems.stone_tablet), NecronomiconText.CRAFTING_STONE_TABLET),
				new Page(11, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.state_transformer), NecronomiconText.CRAFTING_STATE_TRANSFORMER_1),
				new Page(12, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_STATE_TRANSFORMER_2),
				new Page(13, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, new CraftingStack(ACBlocks.energy_depositioner), NecronomiconText.CRAFTING_ENERGY_DEPOSITIONER_1),
				new Page(14, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, 0, NecronomiconText.CRAFTING_ENERGY_DEPOSITIONER_2));
		addPages("potentialenergy", "information", new Page(1, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_1),
				new Page(2, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_1, NecronomiconText.PE_TUT_2),
				new Page(3, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_2, NecronomiconText.PE_TUT_3),
				new Page(4, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_4),
				new Page(5, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_3, NecronomiconText.PE_TUT_5),
				new Page(6, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_6),
				new Page(7, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_4, NecronomiconText.PE_TUT_7),
				new Page(8, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_8),
				new Page(9, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_5, NecronomiconText.PE_TUT_9),
				new Page(10, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_6, NecronomiconText.PE_TUT_10),
				new Page(11, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_7, NecronomiconText.PE_TUT_11),
				new Page(12, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_8, NecronomiconText.PE_TUT_12),
				new Page(13, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_9, NecronomiconText.PE_TUT_13),
				new Page(14, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_10, NecronomiconText.PE_TUT_14),
				new Page(15, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_11, NecronomiconText.PE_TUT_15),
				new Page(16, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_12, NecronomiconText.PE_TUT_16),
				new Page(17, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_13, NecronomiconText.PE_TUT_17),
				new Page(18, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_14, NecronomiconText.PE_TUT_18),
				new Page(19, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconResources.PE_TUT_15, NecronomiconText.PE_TUT_19),
				new Page(20, NecronomiconText.LABEL_POTENTIAL_ENERGY, 0, NecronomiconText.PE_TUT_20));
		addInternalPages("potentialenergy", "placesofpower", "information", new Page(1, NecronomiconText.LABEL_INFO, 0, NecronomiconText.PLACES_OF_POWER_INFO_1), new Page(2, NecronomiconText.LABEL_INFO, 0, NecronomiconText.PLACES_OF_POWER_INFO_2));

		String title = NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS;
		List<Page> pages = new ArrayList<>();
		if (ACConfig.upgrade_kits) {
			pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACItems.cobblestone_upgrade_kit), NecronomiconText.CRAFTING_UPGRADE_KIT_1));
			pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACItems.iron_upgrade_kit), NecronomiconText.CRAFTING_UPGRADE_KIT_2));
		}
		if (ACConfig.foodstuff) {
			pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(new ItemStack(ACItems.iron_plate, 2), null, null, null, null, Items.IRON_INGOT, null, null, Items.IRON_INGOT, null), NecronomiconText.CRAFTING_IRON_PLATE));
			pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACItems.washcloth), NecronomiconText.CRAFTING_WASHCLOTH));
			pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACItems.mre), NecronomiconText.CRAFTING_MRE));
			pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACItems.beef_on_a_plate), NecronomiconText.CRAFTING_PLATE_FOOD));
		}
		pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACBlocks.odb_core), NecronomiconText.CRAFTING_ODB_CORE));
		pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACBlocks.oblivion_deathbomb), NecronomiconText.CRAFTING_ODB));
		pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACItems.carbon_cluster), NecronomiconText.CRAFTING_CARBON_CLUSTER));
		pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACItems.dense_carbon_cluster), NecronomiconText.CRAFTING_DENSE_CARBON_CLUSTER));
		pages.add(new Page(pages.size() + 1, title, 0, new CraftingStack(ACBlocks.wooden_crate), NecronomiconText.CRAFTING_CRATE));
		addPages("miscinfo", "specialmaterials", pages.toArray(new Page[0]));
		if(ACConfig.plague_enchantments)
			addPages("miscinfo", "enchantments", new Page(1, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.coralium_enchantment, 1)), NecronomiconText.ENCHANTMENT_CORALIUM, new NecronomiconCondition(1)),
					new Page(2, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.dread_enchantment, 1)), NecronomiconText.ENCHANTMENT_DREAD, new NecronomiconCondition(2)),
					new Page(3, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.light_pierce, AbyssalCraftAPI.light_pierce.getMaxLevel())), NecronomiconText.ENCHANTMENT_LIGHT_PIERCE, new MultiEntityCondition(EntityShadowCreature.class, EntityShadowMonster.class, EntityShadowBeast.class)),
					new Page(4, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.iron_wall, 1)), NecronomiconText.ENCHANTMENT_IRON_WALL),
					new Page(5, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.sapping, AbyssalCraftAPI.sapping.getMaxLevel())), NecronomiconText.ENCHANTMENT_SAPPING),
					new Page(6, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.multi_rend, 1)), NecronomiconText.ENCHANTMENT_MULTI_REND));
		else
			addPages("miscinfo", "enchantments", new Page(1, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.light_pierce, AbyssalCraftAPI.light_pierce.getMaxLevel())), NecronomiconText.ENCHANTMENT_LIGHT_PIERCE, new MultiEntityCondition(EntityShadowCreature.class, EntityShadowMonster.class, EntityShadowBeast.class)),
					new Page(2, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.iron_wall, 1)), NecronomiconText.ENCHANTMENT_IRON_WALL),
					new Page(3, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.sapping, AbyssalCraftAPI.sapping.getMaxLevel())), NecronomiconText.ENCHANTMENT_SAPPING),
					new Page(4, NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS, 0, ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(AbyssalCraftAPI.multi_rend, 1)), NecronomiconText.ENCHANTMENT_MULTI_REND));
		addPages("miscinfo", "decorativestatues", new Page(1, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_statue, 1, 3)), NecronomiconText.CRAFTING_DECORATIVE_AZATHOTH_STATUE),
				new Page(2, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_statue, 1, 0)), NecronomiconText.CRAFTING_DECORATIVE_CTHULHU_STATUE),
				new Page(3, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_statue, 1, 1)), NecronomiconText.CRAFTING_DECORATIVE_HASTUR_STATUE),
				new Page(4, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_statue, 1, 2)), NecronomiconText.CRAFTING_DECORATIVE_JZAHAR_STATUE),
				new Page(5, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_statue, 1, 4)), NecronomiconText.CRAFTING_DECORATIVE_NYARLATHOTEP_STATUE),
				new Page(6, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_statue, 1, 5)), NecronomiconText.CRAFTING_DECORATIVE_YOG_SOTHOTH_STATUE),
				new Page(7, NecronomiconText.LABEL_INFORMATION_DECORATIVE_STATUES, 0, new CraftingStack(new ItemStack(ACBlocks.decorative_statue, 1, 6)), NecronomiconText.CRAFTING_DECORATIVE_SHUB_NIGGURATH_STATUE));
		addPages("spells", "gettingstarted", new Page(1, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.SPELL_TUT_1),
				new Page(2, NecronomiconText.LABEL_GETTING_STARTED, 0, NecronomiconText.SPELL_TUT_2));
		addPages("spells", "casting", new Page(1, NecronomiconText.LABEL_CASTING, 0, NecronomiconText.SPELL_TUT_3));
		addPages("spells", "materials", new Page(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.scroll, 1, 0), NecronomiconText.WIP),
				new Page(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.scroll, 1, 1), NecronomiconText.WIP),
				new Page(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.scroll, 1, 2), NecronomiconText.WIP),
				new Page(4, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.scroll, 1, 3), NecronomiconText.WIP),
				new Page(5, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.unique_scroll, 1, 0), NecronomiconText.WIP),
				new Page(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, 0, new ItemStack(ACItems.unique_scroll, 1, 1), NecronomiconText.WIP));
		setupPatreonData();
	}

	private void setupPatreonData(){
		if(FMLCommonHandler.instance().getSide().isServer()) return;
		
		new Thread("AbyssalCraft Necronomicon Patreon data") {
			public void run() {
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
					chapter.addPage(new Page(1, NecronomiconText.LABEL_PATRONS, 0, new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/patreon/gentlemangamer2015.png"), "Gentlemangamer2015"));
				}

				if(chapter != null)
					GuiNecronomicon.setPatreonInfo(chapter);
			}
		}.start();
	}
}
