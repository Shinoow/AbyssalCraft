/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.armor;

public class ArmorDataCollection {

	private ColorData[] colors;
	private TextureData[] ghoulTextures;
	private TextureData[] skeletonGoliathTextures;

	public ArmorDataCollection() {
		colors = new ColorData[0];
		ghoulTextures = new TextureData[0];
		skeletonGoliathTextures = new TextureData[0];
	}

	public ColorData[] getColors() {
		return colors;
	}

	public TextureData[] getGhoulTextures() {
		return ghoulTextures;
	}

	public TextureData[] getSkeletonGoliathTextures() {
		return skeletonGoliathTextures;
	}
}
