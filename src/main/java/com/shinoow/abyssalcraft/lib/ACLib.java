/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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
 * This package exposes a bit more internal things without forcing too much of a hard dependency
 * @author shinoow
 *
 */
public class ACLib {

	//Dimension IDs
	public static int abyssal_wasteland_id, dreadlands_id, omothol_id, dark_realm_id;

	//Gui IDs
	public static final int crystallizerGuiID = 30;
	public static final int transmutatorGuiID = 31;
	public static final int engraverGuiID = 32;
	public static final int necronmiconGuiID = 33;
	public static final int crystalbagGuiID = 34;
	public static final int materializerGuiID = 35;
	public static final int energycontainerGuiID = 36;

	//Crystal stuff
	public static final String[] crystalNames = new String[]{"Iron", "Gold", "Sulfur", "Carbon", "Oxygen", "Hydrogen", "Nitrogen", "Phosphorus",
		"Potassium", "Nitrate", "Methane", "Redstone", "Abyssalnite", "Coralium", "Dreadium", "Blaze", "Tin", "Copper",
		"Silicon", "Magnesium", "Aluminium", "Silica", "Alumina", "Magnesia", "Zinc"};
	public static final String[] crystalAtoms = new String[]{"Fe", "Au", "S", "C", "O", "H", "N", "P", "K", "NO\u2083", "CH\u2084", "none", "An",
		"Cor", "Dr", "none", "Sn", "Cu", "Si", "Mg", "Al", "SiO\u2082", "Al\u2082O\u2083", "MgO", "Zn"};
	public static final int[] crystalColors = new int[]{0xD9D9D9, 0xF3CC3E, 0xF6FF00, 0x3D3D36, 16777215, 16777215, 16777215, 0x996A18,
		0xD9D9D9, 0x1500FF, 0x19FC00, 0xFF0000, 0x4a1c89, 0x00FFEE, 0x880101, 0xFFCC00, 0xD9D8D7, 0xE89207, 0xD9D9D9,
		0xD9D9D9, 0xD9D9D9, 16777215, 0xD9D8D9, 16777215, 0xD7D8D9};
}
