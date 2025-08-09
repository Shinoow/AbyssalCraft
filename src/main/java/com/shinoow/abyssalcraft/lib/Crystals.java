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
package com.shinoow.abyssalcraft.lib;

/**
 * All crystal-relevant things in one place
 * @author shinoow
 *
 */
public class Crystals {

	public static final int IRON = 0;
	public static final int GOLD = 1;
	public static final int SULFUR = 2;
	public static final int CARBON = 3;
	public static final int OXYGEN = 4;
	public static final int HYDROGEN = 5;
	public static final int NITROGEN = 6;
	public static final int PHOSPHORUS = 7;
	public static final int POTASSIUM = 8;
	public static final int NITRATE = 9;
	public static final int METHANE = 10;
	public static final int REDSTONE = 11;
	public static final int ABYSSALNITE = 12;
	public static final int CORALIUM = 13;
	public static final int DREADIUM = 14;
	public static final int BLAZE = 15;
	public static final int SILICON = 16;
	public static final int MAGNESIUM = 17;
	public static final int ALUMINIUM = 18;
	public static final int SILICA = 19;
	public static final int ALUMINA = 20;
	public static final int MAGNESIA = 21;
	public static final int ZINC = 22;
	public static final int CALCIUM = 23;
	public static final int BERYLLIUM = 24;
	public static final int BERYL = 25;

	public static final String[] crystalNames = new String[]{"Iron", "Gold", "Sulfur", "Carbon", "Oxygen", "Hydrogen", "Nitrogen", "Phosphorus",
			"Potassium", "Nitrate", "Methane", "Redstone", "Abyssalnite", "Coralium", "Dreadium", "Blaze", "Silicon", "Magnesium",
			"Aluminium", "Silica", "Alumina", "Magnesia", "Zinc", "Calcium", "Beryllium", "Beryl"};
	public static final String[] crystalAtoms = new String[]{"Fe", "Au", "S", "C", "O", "H", "N", "P", "K", "NO\u2083", "CH\u2084", "none", "An",
			"Cor", "Dr", "none", "Si", "Mg", "Al", "SiO\u2082", "Al\u2082O\u2083", "MgO", "Zn", "Ca", "Be", "Be\u2083Al\u2082(SiO\u2083)\u2086"};

	public static String getCrystalType(int i) {
		if(i == IRON || i == GOLD || i == POTASSIUM || i == ABYSSALNITE || i == CORALIUM
				|| i == DREADIUM || i == SILICON || i == MAGNESIUM || i == ALUMINIUM
				|| i == ZINC || i == CALCIUM || i == BERYLLIUM)
			return "crystal_metal";
		if(i == OXYGEN || i == HYDROGEN || i == NITROGEN || i == NITRATE || i == METHANE)
			return "crystal_gas";
		return "crystal";
	}
}
