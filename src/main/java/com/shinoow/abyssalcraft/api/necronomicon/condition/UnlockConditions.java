package com.shinoow.abyssalcraft.api.necronomicon.condition;

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;

/**
 * Contains all unlock conditions added in AbyssalCraft
 *
 * @author shinoow
 *
 */
public class UnlockConditions {

	public static IUnlockCondition DARKLANDS_BIOME = new BiomePredicateCondition(b -> b instanceof IDarklandsBiome);
	
	//Dimension Conditions
	public static IUnlockCondition ABYSSAL_WASTELAND;
	public static IUnlockCondition DREADLANDS;
	public static IUnlockCondition OMOTHOL;
	public static IUnlockCondition DARK_REALM;
}
