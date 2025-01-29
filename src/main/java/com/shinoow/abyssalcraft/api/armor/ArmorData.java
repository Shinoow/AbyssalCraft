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

/**
 * Class holding armor-related data for custom armors
 * @author shinoow
 *
 * @since 2.0.0
 */
public class ArmorData {

	private boolean overlay, colored;

	private ResourceLocation texture_1, texture_2, overlay_1, overlay_2;

	// enum or string target

	public ArmorData(ResourceLocation texture1, ResourceLocation texture2) {
		this(texture1, texture2, false);
	}

	public ArmorData(ResourceLocation texture1, ResourceLocation texture2, boolean overlay) {
		texture_1 = texture1;
		texture_2 = texture2;
		this.overlay = overlay;
		if(overlay) {
			overlay_1 = getOverlay(texture_1);
			overlay_2 = getOverlay(texture_2);
		} else {
			overlay_1 = new ResourceLocation("");
			overlay_2 = new ResourceLocation("");
		}
	}

	/**
	 * Returns if the material has an overlay
	 */
	public boolean hasOverlay() {
		return overlay;
	}

	/**
	 * Returns the texture for Head, chest, feet
	 */
	public ResourceLocation getFirstTexture() {
		return texture_1;
	}

	/**
	 * Returns the texture for Legs
	 */
	public ResourceLocation getSecondTexture() {
		return texture_2;
	}

	/**
	 * Returns the overlay texture for Head, chest, feet
	 */
	public ResourceLocation getFirstOverlay() {
		return overlay_1;
	}

	/**
	 * Returns the overlay texture for Legs
	 */
	public ResourceLocation getSecondOverlay() {
		return overlay_2;
	}

	/**
	 * Returns if the ArmorData is colored (this allows other logic to color it besides layers)
	 */
	public boolean isColored() {
		return colored;
	}

	public ArmorData setColored() {
		colored = true;
		return this;
	}

	private ResourceLocation getOverlay(ResourceLocation texture) {
		String domain = texture.getNamespace();
		String path = texture.getPath();
		return new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
	}
}
