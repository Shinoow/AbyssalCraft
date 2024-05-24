package com.shinoow.abyssalcraft.api.armor;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;

public class ArmorDataRegistry {

	private final Logger LOGGER = LogManager.getLogger("ArmorDataRegistry");

	private static final ArmorDataRegistry INSTANCE = new ArmorDataRegistry();

	private static final Map<ArmorMaterial, ArmorData> GHOUL_DATA = new HashMap<>();
	private static final Map<ArmorMaterial, ArmorData> SKELETON_GOLIATH_DATA = new HashMap<>();

	private static final Map<ArmorMaterial, Integer> COLORS = new HashMap<>();

	private ArmorData EMPTY_GHOUL = new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/ghoul/missing_1.png"), new ResourceLocation("abyssalcraft:textures/armor/ghoul/missing_2.png")).setEmpty();
	private ArmorData EMPTY_SKELETON_GOLIATH = new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_1.png"), new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_2.png")).setEmpty();

	private ArmorDataRegistry() {}

	public static ArmorDataRegistry instance() {
		return INSTANCE;
	}

	public void registerGhoulData(ArmorMaterial material, ArmorData data) {
		if(GHOUL_DATA.containsKey(material))
			LOGGER.warn("Ghoul data was already registered for {}, overriding it", material.toString());
		GHOUL_DATA.put(material, data);
	}

	public void registerSkeletonGoliathData(ArmorMaterial material, ArmorData data) {
		if(SKELETON_GOLIATH_DATA.containsKey(material))
			LOGGER.warn("Skeleton Goliath data was already registered for {}, overriding it", material.toString());
		SKELETON_GOLIATH_DATA.put(material, data);
	}

	public void registerColor(ArmorMaterial material, int color) {
		if(COLORS.containsKey(material))
			LOGGER.warn("A color was already registered for {}, overriding it", material.toString());
		COLORS.put(material, color);
	}

	public ArmorData getGhoulData(ArmorMaterial material) {
		return GHOUL_DATA.getOrDefault(material, EMPTY_GHOUL);
	}

	public ArmorData getSkeletonGoliathData(ArmorMaterial material) {
		return SKELETON_GOLIATH_DATA.getOrDefault(material, EMPTY_SKELETON_GOLIATH);
	}

	/**
	 * Returns a color for a material, or -1 as "no color"
	 */
	public int getColor(ArmorMaterial material) {
		return COLORS.getOrDefault(material, -1);
	}
}
