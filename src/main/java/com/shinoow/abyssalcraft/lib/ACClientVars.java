package com.shinoow.abyssalcraft.lib;

/**
 * Contains values that can be changed through a resource pack
 * @author shinoow
 *
 */
public class ACClientVars {

	private static int[] crystalColors = new int[]{0xD9D9D9, 0xF3CC3E, 0xF6FF00, 0x3D3D36, 16777215, 16777215, 16777215, 0x996A18,
			0xD9D9D9, 0x1500FF, 0x19FC00, 0xFF0000, 0x4a1c89, 0x00FFEE, 0x880101, 0xFFCC00, 0xD9D8D7, 0xE89207, 0xD9D9D9,
			0xD9D9D9, 0xD9D9D9, 16777215, 0xD9D8D9, 16777215, 0xD7D8D9, 0xD7D8D9, 0xD9D9D9, 16777215};
	private static int abyssalWastelandR = 0;
	private static int abyssalWastelandG = 105;
	private static int abyssalWastelandB = 45;
	private static int dreadlandsR = 100;
	private static int dreadlandsG = 14;
	private static int dreadlandsB = 14;
	private static int omotholR = 40;
	private static int omotholG = 30;
	private static int omotholB = 40;
	private static int darkRealmR = 30;
	private static int darkRealmG = 20;
	private static int darkRealmB = 30;
	private static int darklandsGrassColor = 0x17375c;
	private static int darklandsFoliageColor = 0x17375c;
	private static int darklandsWaterColor = 14745518;
	private static int darklandsSkyColor = 0;
	private static int darklandsPlainsGrassColor = 0x17375c;
	private static int darklandsPlainsFoliageColor = 0x17375c;
	private static int darklandsPlainsWaterColor = 14745518;
	private static int darklandsPlainsSkyColor = 0;
	private static int darklandsForestGrassColor = 0x17375c;
	private static int darklandsForestFoliageColor = 0x17375c;
	private static int darklandsForestWaterColor = 14745518;
	private static int darklandsForestSkyColor = 0;
	private static int darklandsHighlandsGrassColor = 0x17375c;
	private static int darklandsHighlandsFoliageColor = 0x17375c;
	private static int darklandsHighlandsWaterColor = 14745518;
	private static int darklandsHighlandsSkyColor = 0;
	private static int darklandsMountainsGrassColor = 0x17375c;
	private static int darklandsMountainsFoliageColor = 0x17375c;
	private static int darklandsMountainsWaterColor = 14745518;
	private static int darklandsMountainsSkyColor = 0;
	private static int coraliumInfestedSwampGrassColor = 0x59c6b4;
	private static int coraliumInfestedSwampFoliageColor = 0x59c6b4;
	private static int coraliumInfestedSwampWaterColor = 0x24FF83;
	private static int abyssalWastelandGrassColor = 0x447329;
	private static int abyssalWastelandFoliageColor = 0x447329;
	private static int abyssalWastelandWaterColor = 0x24FF83;
	private static int abyssalWastelandSkyColor = 0;
	private static int dreadlandsGrassColor = 0x910000;
	private static int dreadlandsFoliageColor = 0x910000;
	private static int dreadlandsSkyColor = 0;
	private static int dreadlandsForestGrassColor = 0x910000;
	private static int dreadlandsForestFoliageColor = 0x910000;
	private static int dreadlandsForestSkyColor = 0;
	private static int dreadlandsMountainsGrassColor = 0x910000;
	private static int dreadlandsMountainsFoliageColor = 0x910000;
	private static int dreadlandsMountainsSkyColor = 0;
	private static int purifiedDreadlandsGrassColor = 0x30217A;
	private static int purifiedDreadlandsFoliageColor = 0x30217A;
	private static int purifiedDreadlandsSkyColor = 0;
	private static int omotholGrassColor = 0x17375c;
	private static int omotholFoliageColor = 0x17375c;
	private static int omotholWaterColor = 14745518;
	private static int omotholSkyColor = 0;
	private static int darkRealmGrassColor = 0x17375c;
	private static int darkRealmFoliageColor = 0x17375c;
	private static int darkRealmWaterColor = 14745518;
	private static int darkRealmSkyColor = 0;
	private static int purgedGrassColor = 0xD7D8D9;
	private static int purgedFoliageColor = 0xD7D8D9;
	private static int purgedWaterColor = 0xD7D8D9;
	private static int purgedSkyColor = 0xD7D8D9;
	private static int coraliumPlaguePotionColor = 0x00FFFF;
	private static int dreadPlaguePotionColor = 0xAD1313;
	private static int antimatterPotionColor = 0xFFFFFF;

	public static void init(String...args) {
		
	}
	
	public static int[] getCrystalColors() {
		return crystalColors;
	}

	public static int getAbyssalWastelandR() {
		return abyssalWastelandR;
	}

	public static int getAbyssalWastelandG() {
		return abyssalWastelandG;
	}

	public static int getAbyssalWastelandB() {
		return abyssalWastelandB;
	}

	public static int getDreadlandsR() {
		return dreadlandsR;
	}

	public static int getDreadlandsG() {
		return dreadlandsG;
	}

	public static int getDreadlandsB() {
		return dreadlandsB;
	}

	public static int getOmotholR() {
		return omotholR;
	}

	public static int getOmotholG() {
		return omotholG;
	}

	public static int getOmotholB() {
		return omotholB;
	}

	public static int getDarkRealmR() {
		return darkRealmR;
	}

	public static int getDarkRealmG() {
		return darkRealmG;
	}

	public static int getDarkRealmB() {
		return darkRealmB;
	}

	public static int getDarklandsGrassColor() {
		return darklandsGrassColor;
	}

	public static int getDarklandsFoliageColor() {
		return darklandsFoliageColor;
	}

	public static int getDarklandsWaterColor() {
		return darklandsWaterColor;
	}

	public static int getDarklandsSkyColor() {
		return darklandsSkyColor;
	}

	public static int getDarklandsPlainsGrassColor() {
		return darklandsPlainsGrassColor;
	}

	public static int getDarklandsPlainsFoliageColor() {
		return darklandsPlainsFoliageColor;
	}

	public static int getDarklandsPlainsWaterColor() {
		return darklandsPlainsWaterColor;
	}

	public static int getDarklandsPlainsSkyColor() {
		return darklandsPlainsSkyColor;
	}

	public static int getDarklandsForestGrassColor() {
		return darklandsForestGrassColor;
	}

	public static int getDarklandsForestFoliageColor() {
		return darklandsForestFoliageColor;
	}

	public static int getDarklandsForestWaterColor() {
		return darklandsForestWaterColor;
	}

	public static int getDarklandsForestSkyColor() {
		return darklandsForestSkyColor;
	}

	public static int getDarklandsHighlandsGrassColor() {
		return darklandsHighlandsGrassColor;
	}

	public static int getDarklandsHighlandsFoliageColor() {
		return darklandsHighlandsFoliageColor;
	}

	public static int getDarklandsHighlandsWaterColor() {
		return darklandsHighlandsWaterColor;
	}

	public static int getDarklandsHighlandsSkyColor() {
		return darklandsHighlandsSkyColor;
	}

	public static int getDarklandsMountainsGrassColor() {
		return darklandsMountainsGrassColor;
	}

	public static int getDarklandsMountainsFoliageColor() {
		return darklandsMountainsFoliageColor;
	}

	public static int getDarklandsMountainsWaterColor() {
		return darklandsMountainsWaterColor;
	}

	public static int getDarklandsMountainsSkyColor() {
		return darklandsMountainsSkyColor;
	}

	public static int getCoraliumInfestedSwampGrassColor() {
		return coraliumInfestedSwampGrassColor;
	}

	public static int getCoraliumInfestedSwampFoliageColor() {
		return coraliumInfestedSwampFoliageColor;
	}

	public static int getCoraliumInfestedSwampWaterColor() {
		return coraliumInfestedSwampWaterColor;
	}

	public static int getAbyssalWastelandGrassColor() {
		return abyssalWastelandGrassColor;
	}

	public static int getAbyssalWastelandFoliageColor() {
		return abyssalWastelandFoliageColor;
	}

	public static int getAbyssalWastelandWaterColor() {
		return abyssalWastelandWaterColor;
	}

	public static int getAbyssalWastelandSkyColor() {
		return abyssalWastelandSkyColor;
	}

	public static int getDreadlandsGrassColor() {
		return dreadlandsGrassColor;
	}

	public static int getDreadlandsFoliageColor() {
		return dreadlandsFoliageColor;
	}

	public static int getDreadlandsSkyColor() {
		return dreadlandsSkyColor;
	}

	public static int getDreadlandsForestGrassColor() {
		return dreadlandsForestGrassColor;
	}

	public static int getDreadlandsForestFoliageColor() {
		return dreadlandsForestFoliageColor;
	}

	public static int getDreadlandsForestSkyColor() {
		return dreadlandsForestSkyColor;
	}

	public static int getDreadlandsMountainsGrassColor() {
		return dreadlandsMountainsGrassColor;
	}

	public static int getDreadlandsMountainsFoliageColor() {
		return dreadlandsMountainsFoliageColor;
	}

	public static int getDreadlandsMountainsSkyColor() {
		return dreadlandsMountainsSkyColor;
	}

	public static int getPurifiedDreadlandsGrassColor() {
		return purifiedDreadlandsGrassColor;
	}

	public static int getPurifiedDreadlandsFoliageColor() {
		return purifiedDreadlandsFoliageColor;
	}

	public static int getPurifiedDreadlandsSkyColor() {
		return purifiedDreadlandsSkyColor;
	}

	public static int getOmotholGrassColor() {
		return omotholGrassColor;
	}

	public static int getOmotholFoliageColor() {
		return omotholFoliageColor;
	}

	public static int getOmotholWaterColor() {
		return omotholWaterColor;
	}

	public static int getOmotholSkyColor() {
		return omotholSkyColor;
	}

	public static int getDarkRealmGrassColor() {
		return darkRealmGrassColor;
	}

	public static int getDarkRealmFoliageColor() {
		return darkRealmFoliageColor;
	}

	public static int getDarkRealmWaterColor() {
		return darkRealmWaterColor;
	}

	public static int getDarkRealmSkyColor() {
		return darkRealmSkyColor;
	}

	public static int getPurgedGrassColor() {
		return purgedGrassColor;
	}

	public static int getPurgedFoliageColor() {
		return purgedFoliageColor;
	}

	public static int getPurgedWaterColor() {
		return purgedWaterColor;
	}

	public static int getPurgedSkyColor() {
		return purgedSkyColor;
	}

	public static int getCoraliumPlaguePotionColor() {
		return coraliumPlaguePotionColor;
	}

	public static int getDreadPlaguePotionColor() {
		return dreadPlaguePotionColor;
	}

	public static int getAntimatterPotionColor() {
		return antimatterPotionColor;
	}
}
