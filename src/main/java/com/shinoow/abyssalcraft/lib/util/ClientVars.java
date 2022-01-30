/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.util;

import java.util.Arrays;

/**
 * Object representation of clientvars.json
 * @author shinoow
 *
 */
public class ClientVars {

	private String[] crystalColors;
	private int abyssalWastelandR;
	private int abyssalWastelandG;
	private int abyssalWastelandB;
	private int dreadlandsR;
	private int dreadlandsG;
	private int dreadlandsB;
	private int omotholR;
	private int omotholG;
	private int omotholB;
	private int darkRealmR;
	private int darkRealmG;
	private int darkRealmB;
	private String darklandsGrassColor;
	private String darklandsFoliageColor;
	private String darklandsWaterColor;
	private String darklandsSkyColor;
	private String darklandsPlainsGrassColor;
	private String darklandsPlainsFoliageColor;
	private String darklandsPlainsWaterColor;
	private String darklandsPlainsSkyColor;
	private String darklandsForestGrassColor;
	private String darklandsForestFoliageColor;
	private String darklandsForestWaterColor;
	private String darklandsForestSkyColor;
	private String darklandsHighlandsGrassColor;
	private String darklandsHighlandsFoliageColor;
	private String darklandsHighlandsWaterColor;
	private String darklandsHighlandsSkyColor;
	private String darklandsMountainsGrassColor;
	private String darklandsMountainsFoliageColor;
	private String darklandsMountainsWaterColor;
	private String darklandsMountainsSkyColor;
	private String coraliumInfestedSwampGrassColor;
	private String coraliumInfestedSwampFoliageColor;
	private String coraliumInfestedSwampWaterColor;
	private String abyssalWastelandGrassColor;
	private String abyssalWastelandFoliageColor;
	private String abyssalWastelandWaterColor;
	private String abyssalWastelandSkyColor;
	private String dreadlandsGrassColor;
	private String dreadlandsFoliageColor;
	private String dreadlandsSkyColor;
	private String dreadlandsForestGrassColor;
	private String dreadlandsForestFoliageColor;
	private String dreadlandsForestSkyColor;
	private String dreadlandsMountainsGrassColor;
	private String dreadlandsMountainsFoliageColor;
	private String dreadlandsMountainsSkyColor;
	private String purifiedDreadlandsGrassColor;
	private String purifiedDreadlandsFoliageColor;
	private String purifiedDreadlandsSkyColor;
	private String omotholGrassColor;
	private String omotholFoliageColor;
	private String omotholWaterColor;
	private String omotholSkyColor;
	private String darkRealmGrassColor;
	private String darkRealmFoliageColor;
	private String darkRealmWaterColor;
	private String darkRealmSkyColor;
	private String purgedGrassColor;
	private String purgedFoliageColor;
	private String purgedWaterColor;
	private String purgedSkyColor;
	private String coraliumPlaguePotionColor;
	private String dreadPlaguePotionColor;
	private String antimatterPotionColor;
	private int asorahDeathR;
	private int asorahDeathG;
	private int asorahDeathB;
	private int jzaharDeathR;
	private int jzaharDeathG;
	private int jzaharDeathB;
	private int implosionR;
	private int implosionG;
	private int implosionB;
	private String coraliumAntidotePotionColor;
	private String dreadAntidotePotionColor;

	/**
	 * Allocation of default values in case something is left out (which could have undesired side-effects)
	 */
	public ClientVars() {
		crystalColors = new String[]{"0xD9D9D9", "0xF3CC3E", "0xF6FF00", "0x3D3D36", "16777215", "16777215", "16777215", "0x996A18",
				"0xD9D9D9", "0x1500FF", "0x19FC00", "0xFF0000", "0x4a1c89", "0x00FFEE", "0x880101", "0xFFCC00", "0xD9D8D7", "0xE89207", "0xD9D9D9",
				"0xD9D9D9", "0xD9D9D9", "16777215", "0xD9D8D9", "16777215", "0xD7D8D9", "0xD7D8D9", "0xD9D9D9", "16777215"};
		abyssalWastelandR = 0;
		abyssalWastelandG = 105;
		abyssalWastelandB = 45;
		dreadlandsR = 100;
		dreadlandsG = 14;
		dreadlandsB = 14;
		omotholR = 40;
		omotholG = 30;
		omotholB = 40;
		darkRealmR = 30;
		darkRealmG = 20;
		darkRealmB = 30;
		darklandsGrassColor = "0x17375c";
		darklandsFoliageColor = "0x17375c";
		darklandsWaterColor = "14745518";
		darklandsSkyColor = "0";
		darklandsPlainsGrassColor = "0x17375c";
		darklandsPlainsFoliageColor = "0x17375c";
		darklandsPlainsWaterColor = "14745518";
		darklandsPlainsSkyColor = "0";
		darklandsForestGrassColor = "0x17375c";
		darklandsForestFoliageColor = "0x17375c";
		darklandsForestWaterColor = "14745518";
		darklandsForestSkyColor = "0";
		darklandsHighlandsGrassColor = "0x17375c";
		darklandsHighlandsFoliageColor = "0x17375c";
		darklandsHighlandsWaterColor = "14745518";
		darklandsHighlandsSkyColor = "0";
		darklandsMountainsGrassColor = "0x17375c";
		darklandsMountainsFoliageColor = "0x17375c";
		darklandsMountainsWaterColor = "14745518";
		darklandsMountainsSkyColor = "0";
		coraliumInfestedSwampGrassColor = "0x59c6b4";
		coraliumInfestedSwampFoliageColor = "0x59c6b4";
		coraliumInfestedSwampWaterColor = "0x24FF83";
		abyssalWastelandGrassColor = "0x447329";
		abyssalWastelandFoliageColor = "0x447329";
		abyssalWastelandWaterColor = "0x24FF83";
		abyssalWastelandSkyColor = "0";
		dreadlandsGrassColor = "0x910000";
		dreadlandsFoliageColor = "0x910000";
		dreadlandsSkyColor = "0";
		dreadlandsForestGrassColor = "0x910000";
		dreadlandsForestFoliageColor = "0x910000";
		dreadlandsForestSkyColor = "0";
		dreadlandsMountainsGrassColor = "0x910000";
		dreadlandsMountainsFoliageColor = "0x910000";
		dreadlandsMountainsSkyColor = "0";
		purifiedDreadlandsGrassColor = "0x30217A";
		purifiedDreadlandsFoliageColor = "0x30217A";
		purifiedDreadlandsSkyColor = "0";
		omotholGrassColor = "0x17375c";
		omotholFoliageColor = "0x17375c";
		omotholWaterColor = "14745518";
		omotholSkyColor = "0";
		darkRealmGrassColor = "0x17375c";
		darkRealmFoliageColor = "0x17375c";
		darkRealmWaterColor = "14745518";
		darkRealmSkyColor = "0";
		purgedGrassColor = "0xD7D8D9";
		purgedFoliageColor = "0xD7D8D9";
		purgedWaterColor = "0xD7D8D9";
		purgedSkyColor = "0xD7D8D9";
		coraliumPlaguePotionColor = "0x00FFFF";
		dreadPlaguePotionColor = "0xAD1313";
		antimatterPotionColor = "0xFFFFFF";
		asorahDeathR = 0;
		asorahDeathG = 255;
		asorahDeathB = 255;
		jzaharDeathR = 81;
		jzaharDeathG = 189;
		jzaharDeathB = 178;
		implosionR = 255;
		implosionG = 255;
		implosionB = 255;
		coraliumAntidotePotionColor = "0x00ff06";
		dreadAntidotePotionColor = "0x00ff06";
	}

	public int[] getCrystalColors() {
		return Arrays.stream(crystalColors).mapToInt(Integer::decode).toArray();
	}

	public int getAbyssalWastelandR() {
		return abyssalWastelandR;
	}

	public int getAbyssalWastelandG() {
		return abyssalWastelandG;
	}

	public int getAbyssalWastelandB() {
		return abyssalWastelandB;
	}

	public int getDreadlandsR() {
		return dreadlandsR;
	}

	public int getDreadlandsG() {
		return dreadlandsG;
	}

	public int getDreadlandsB() {
		return dreadlandsB;
	}

	public int getOmotholR() {
		return omotholR;
	}

	public int getOmotholG() {
		return omotholG;
	}

	public int getOmotholB() {
		return omotholB;
	}

	public int getDarkRealmR() {
		return darkRealmR;
	}

	public int getDarkRealmG() {
		return darkRealmG;
	}

	public int getDarkRealmB() {
		return darkRealmB;
	}

	public int getDarklandsGrassColor() {
		return Integer.decode(darklandsGrassColor);
	}

	public int getDarklandsFoliageColor() {
		return Integer.decode(darklandsFoliageColor);
	}

	public int getDarklandsWaterColor() {
		return Integer.decode(darklandsWaterColor);
	}

	public int getDarklandsSkyColor() {
		return Integer.decode(darklandsSkyColor);
	}

	public int getDarklandsPlainsGrassColor() {
		return Integer.decode(darklandsPlainsGrassColor);
	}

	public int getDarklandsPlainsFoliageColor() {
		return Integer.decode(darklandsPlainsFoliageColor);
	}

	public int getDarklandsPlainsWaterColor() {
		return Integer.decode(darklandsPlainsWaterColor);
	}

	public int getDarklandsPlainsSkyColor() {
		return Integer.decode(darklandsPlainsSkyColor);
	}

	public int getDarklandsForestGrassColor() {
		return Integer.decode(darklandsForestGrassColor);
	}

	public int getDarklandsForestFoliageColor() {
		return Integer.decode(darklandsForestFoliageColor);
	}

	public int getDarklandsForestWaterColor() {
		return Integer.decode(darklandsForestWaterColor);
	}

	public int getDarklandsForestSkyColor() {
		return Integer.decode(darklandsForestSkyColor);
	}

	public int getDarklandsHighlandsGrassColor() {
		return Integer.decode(darklandsHighlandsGrassColor);
	}

	public int getDarklandsHighlandsFoliageColor() {
		return Integer.decode(darklandsHighlandsFoliageColor);
	}

	public int getDarklandsHighlandsWaterColor() {
		return Integer.decode(darklandsHighlandsWaterColor);
	}

	public int getDarklandsHighlandsSkyColor() {
		return Integer.decode(darklandsHighlandsSkyColor);
	}

	public int getDarklandsMountainsGrassColor() {
		return Integer.decode(darklandsMountainsGrassColor);
	}

	public int getDarklandsMountainsFoliageColor() {
		return Integer.decode(darklandsMountainsFoliageColor);
	}

	public int getDarklandsMountainsWaterColor() {
		return Integer.decode(darklandsMountainsWaterColor);
	}

	public int getDarklandsMountainsSkyColor() {
		return Integer.decode(darklandsMountainsSkyColor);
	}

	public int getCoraliumInfestedSwampGrassColor() {
		return Integer.decode(coraliumInfestedSwampGrassColor);
	}

	public int getCoraliumInfestedSwampFoliageColor() {
		return Integer.decode(coraliumInfestedSwampFoliageColor);
	}

	public int getCoraliumInfestedSwampWaterColor() {
		return Integer.decode(coraliumInfestedSwampWaterColor);
	}

	public int getAbyssalWastelandGrassColor() {
		return Integer.decode(abyssalWastelandGrassColor);
	}

	public int getAbyssalWastelandFoliageColor() {
		return Integer.decode(abyssalWastelandFoliageColor);
	}

	public int getAbyssalWastelandWaterColor() {
		return Integer.decode(abyssalWastelandWaterColor);
	}

	public int getAbyssalWastelandSkyColor() {
		return Integer.decode(abyssalWastelandSkyColor);
	}

	public int getDreadlandsGrassColor() {
		return Integer.decode(dreadlandsGrassColor);
	}

	public int getDreadlandsFoliageColor() {
		return Integer.decode(dreadlandsFoliageColor);
	}

	public int getDreadlandsSkyColor() {
		return Integer.decode(dreadlandsSkyColor);
	}

	public int getDreadlandsForestGrassColor() {
		return Integer.decode(dreadlandsForestGrassColor);
	}

	public int getDreadlandsForestFoliageColor() {
		return Integer.decode(dreadlandsForestFoliageColor);
	}

	public int getDreadlandsForestSkyColor() {
		return Integer.decode(dreadlandsForestSkyColor);
	}

	public int getDreadlandsMountainsGrassColor() {
		return Integer.decode(dreadlandsMountainsGrassColor);
	}

	public int getDreadlandsMountainsFoliageColor() {
		return Integer.decode(dreadlandsMountainsFoliageColor);
	}

	public int getDreadlandsMountainsSkyColor() {
		return Integer.decode(dreadlandsMountainsSkyColor);
	}

	public int getPurifiedDreadlandsGrassColor() {
		return Integer.decode(purifiedDreadlandsGrassColor);
	}

	public int getPurifiedDreadlandsFoliageColor() {
		return Integer.decode(purifiedDreadlandsFoliageColor);
	}

	public int getPurifiedDreadlandsSkyColor() {
		return Integer.decode(purifiedDreadlandsSkyColor);
	}

	public int getOmotholGrassColor() {
		return Integer.decode(omotholGrassColor);
	}

	public int getOmotholFoliageColor() {
		return Integer.decode(omotholFoliageColor);
	}

	public int getOmotholWaterColor() {
		return Integer.decode(omotholWaterColor);
	}

	public int getOmotholSkyColor() {
		return Integer.decode(omotholSkyColor);
	}

	public int getDarkRealmGrassColor() {
		return Integer.decode(darkRealmGrassColor);
	}

	public int getDarkRealmFoliageColor() {
		return Integer.decode(darkRealmFoliageColor);
	}

	public int getDarkRealmWaterColor() {
		return Integer.decode(darkRealmWaterColor);
	}

	public int getDarkRealmSkyColor() {
		return Integer.decode(darkRealmSkyColor);
	}

	public int getPurgedGrassColor() {
		return Integer.decode(purgedGrassColor);
	}

	public int getPurgedFoliageColor() {
		return Integer.decode(purgedFoliageColor);
	}

	public int getPurgedWaterColor() {
		return Integer.decode(purgedWaterColor);
	}

	public int getPurgedSkyColor() {
		return Integer.decode(purgedSkyColor);
	}

	public int getCoraliumPlaguePotionColor() {
		return Integer.decode(coraliumPlaguePotionColor);
	}

	public int getDreadPlaguePotionColor() {
		return Integer.decode(dreadPlaguePotionColor);
	}

	public int getAntimatterPotionColor() {
		return Integer.decode(antimatterPotionColor);
	}

	public int getAsorahDeathR() {
		return asorahDeathR;
	}

	public int getAsorahDeathG() {
		return asorahDeathG;
	}

	public int getAsorahDeathB() {
		return asorahDeathB;
	}

	public int getJzaharDeathR() {
		return jzaharDeathR;
	}

	public int getJzaharDeathG() {
		return jzaharDeathG;
	}

	public int getJzaharDeathB() {
		return jzaharDeathB;
	}

	public int getImplosionR() {
		return implosionR;
	}

	public int getImplosionG() {
		return implosionG;
	}

	public int getImplosionB() {
		return implosionB;
	}

	public int getCoraliumAntidotePotionColor() {
		return Integer.decode(coraliumAntidotePotionColor);
	}

	public int getDreadAntidotePotionColor() {
		return Integer.decode(dreadAntidotePotionColor);
	}

}
