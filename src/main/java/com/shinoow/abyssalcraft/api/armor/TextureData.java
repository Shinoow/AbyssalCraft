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

import net.minecraft.util.ResourceLocation;

public class TextureData {

	private boolean overlay, colored;

	private String material, texture_1, texture_2;

	public TextureData() {
		overlay = false;
		colored = false;
		material = "";
		texture_1 = "";
		texture_2 = "";
	}

	public ResourceLocation getFirstTexture() {
		return new ResourceLocation(texture_1);
	}

	public ResourceLocation getSecondTexture() {
		return new ResourceLocation(texture_2);
	}

	public String getMaterial() {
		return material;
	}

	public ArmorData convert() {
		ArmorData data = new ArmorData(getFirstTexture(), getSecondTexture(), overlay);
		if(colored)
			data.setColored();
		return data;
	}
}
