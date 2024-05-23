package com.shinoow.abyssalcraft.api.armor;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;

public class ArmorDataRegistry {

	private final Logger logger = LogManager.getLogger("ArmorDataRegistry");

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
		GHOUL_DATA.put(material, data);
	}

	public void registerSkeletonGoliathData(ArmorMaterial material, ArmorData data) {
		SKELETON_GOLIATH_DATA.put(material, data);
	}

	public void registerColor(ArmorMaterial material, int color) {
		COLORS.put(material, color);
	}

	public ArmorData getGhoulData(ArmorMaterial material) {
		return GHOUL_DATA.getOrDefault(material, EMPTY_GHOUL);
	}

	public ArmorData getSkeletonGoliathData(ArmorMaterial material) {
		return SKELETON_GOLIATH_DATA.getOrDefault(material, EMPTY_SKELETON_GOLIATH);
	}

	public int getColor(ArmorMaterial material) {
		return COLORS.get(material);
	}
}
