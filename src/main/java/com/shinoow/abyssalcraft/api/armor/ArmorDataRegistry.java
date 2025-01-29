/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.armor;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;

/**
 * Registry for armor textures or colors for custom models
 * @author shinoow
 *
 * @since 2.0.0
 */
public class ArmorDataRegistry {

	private final Logger LOGGER = LogManager.getLogger("ArmorDataRegistry");

	private static final ArmorDataRegistry INSTANCE = new ArmorDataRegistry();

	private static final Map<ArmorMaterial, ArmorData> GHOUL_DATA = new HashMap<>();
	private static final Map<ArmorMaterial, ArmorData> SKELETON_GOLIATH_DATA = new HashMap<>();
	private static final Map<ArmorMaterial, ArmorData> INNER_GHOUL_DATA = new HashMap<>();
	private static final Map<ArmorMaterial, ArmorData> INNER_SKELETON_GOLIATH_DATA = new HashMap<>();

	private static final Map<ArmorMaterial, Integer> COLORS = new HashMap<>();
	private static final Map<ArmorMaterial, Integer> INNER_COLORS = new HashMap<>();

	private ArmorData EMPTY_GHOUL = new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/ghoul/base_1.png"), new ResourceLocation("abyssalcraft:textures/armor/ghoul/base_2.png"), true).setColored();
	private ArmorData EMPTY_SKELETON_GOLIATH = new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_1.png"), new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_2.png"), true).setColored();

	private ArmorDataRegistry() {}

	public static ArmorDataRegistry instance() {
		return INSTANCE;
	}

	public void registerGhoulData(ArmorMaterial material, ArmorData data) {
		if(GHOUL_DATA.containsKey(material))
			LOGGER.warn("Ghoul data was already registered for {}, overriding it", material.toString());
		GHOUL_DATA.put(material, data);
		INNER_GHOUL_DATA.put(material, data);
	}

	public void registerSkeletonGoliathData(ArmorMaterial material, ArmorData data) {
		if(SKELETON_GOLIATH_DATA.containsKey(material))
			LOGGER.warn("Skeleton Goliath data was already registered for {}, overriding it", material.toString());
		SKELETON_GOLIATH_DATA.put(material, data);
		INNER_SKELETON_GOLIATH_DATA.put(material, data);
	}

	public void registerColor(ArmorMaterial material, int color) {
		if(COLORS.containsKey(material))
			LOGGER.warn("A color was already registered for {}, overriding it", material.toString());
		COLORS.put(material, color);
		INNER_COLORS.put(material, color);
	}

	public ArmorData getGhoulData(ArmorMaterial material) {
		return INNER_GHOUL_DATA.getOrDefault(material, EMPTY_GHOUL);
	}

	public ArmorData getSkeletonGoliathData(ArmorMaterial material) {
		return INNER_SKELETON_GOLIATH_DATA.getOrDefault(material, EMPTY_SKELETON_GOLIATH);
	}

	/**
	 * Returns a color for a material, or -1 as "no color"
	 */
	public int getColor(ArmorMaterial material) {
		return INNER_COLORS.getOrDefault(material, -1);
	}

	public void processCollection(ArmorDataCollection collection) {
		for(ColorData cd : collection.getColors()) {
			ArmorMaterial mat = tryGet(cd.getMaterial());
			if(mat != null)
				INNER_COLORS.put(mat, cd.getColor());
			else
				LOGGER.warn("Could not find material: {}", cd.getMaterial());
		}
		for(TextureData td : collection.getGhoulTextures()) {
			ArmorMaterial mat = tryGet(td.getMaterial());
			if(mat != null)
				INNER_GHOUL_DATA.put(mat, td.convert());
			else
				LOGGER.warn("Could not find material: {}", td.getMaterial());
		}
		for(TextureData td : collection.getSkeletonGoliathTextures()) {
			ArmorMaterial mat = tryGet(td.getMaterial());
			if(mat != null)
				INNER_SKELETON_GOLIATH_DATA.put(mat, td.convert());
			else
				LOGGER.warn("Could not find material: {}", td.getMaterial());
		}
	}

	private ArmorMaterial tryGet(String name) {
		for(ArmorMaterial mat1 : ArmorMaterial.values())
			if(mat1.name().equalsIgnoreCase(name))
				return mat1;

		return null;
	}

	public void clearInnerCollections() {
		INNER_GHOUL_DATA.clear();
		INNER_SKELETON_GOLIATH_DATA.clear();
		INNER_COLORS.clear();
		INNER_GHOUL_DATA.putAll(GHOUL_DATA);
		INNER_SKELETON_GOLIATH_DATA.putAll(SKELETON_GOLIATH_DATA);
		INNER_COLORS.putAll(COLORS);
	}
}
