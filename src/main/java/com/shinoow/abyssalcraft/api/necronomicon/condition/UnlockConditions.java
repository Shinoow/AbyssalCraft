package com.shinoow.abyssalcraft.api.necronomicon.condition;

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

/**
 * Contains all unlock conditions added in AbyssalCraft
 *
 * @author shinoow
 *
 */
public class UnlockConditions {

	//Biome conditions
	public static IUnlockCondition DARKLANDS_BIOME = new BiomePredicateCondition(b -> b instanceof IDarklandsBiome);
	public static IUnlockCondition CORALIUM_INFESTED_SWAMP;
	public static IUnlockCondition CORALIUM_BIOMES;
	
	//Dimension conditions
	public static IUnlockCondition ABYSSAL_WASTELAND;
	public static IUnlockCondition DREADLANDS;
	public static IUnlockCondition OMOTHOL;
	public static IUnlockCondition DARK_REALM;
	public static IUnlockCondition NETHER = new DimensionCondition(-1);
	
	//Entity conditions
	public static IUnlockCondition ABYSSAL_ZOMBIE = new EntityCondition("abyssalcraft:abyssalzombie");
	public static IUnlockCondition DEPTHS_GHOUL = new EntityCondition("abyssalcraft:depthsghoul");
	public static IUnlockCondition SACTHOTH = new EntityCondition("abyssalcraft:shadowboss");
	public static IUnlockCondition SHADOW_MOBS = new MultiEntityCondition("abyssalcraft:shadowcreature", "abyssalcraft:shadowmonster","abyssalcraft:shadowbeast");
	public static IUnlockCondition SKELETON_GOLIATH = new EntityCondition("abyssalcraft:gskeleton");
	public static IUnlockCondition SPECTRAL_DRAGON = new EntityCondition("abyssalcraft:dragonminion");
	public static IUnlockCondition OMOTHOL_GHOUL = new EntityCondition("abyssalcraft:omotholghoul");
	public static IUnlockCondition DREADED_ABYSSALNITE_GOLEM = new EntityCondition("abyssalcraft:dreadgolem");
	public static IUnlockCondition ABYSSALNITE_GOLEM = new EntityCondition("abyssalcraft:abygolem");
	public static IUnlockCondition ELITE_DREAD_MOB = new MultiEntityCondition("abyssalcraft:dreadguard", "abyssalcraft:greaterdreadspawn", "abyssalcraft:lesserdreadbeast");
	public static IUnlockCondition DREAD_MOB = new EntityPredicateCondition(e -> IDreadEntity.class.isAssignableFrom(e));
}
