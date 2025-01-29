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
package com.shinoow.abyssalcraft.api.dimension;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/**
 * Class holding dimension-related data for portals
 * @author shinoow
 *
 * @since 2.0.0
 */
public class DimensionData {

	private int r, g, b;
	private int id, gatewayKey;
	private Set<Integer> connectedDimensions;
	private Class<? extends EntityLiving> mobClass;
	private ResourceLocation overlay;

	public DimensionData() {}

	private DimensionData(Builder builder) {
		id = builder.id;
		r = builder.r;
		g = builder.g;
		b = builder.b;
		gatewayKey = builder.gatewayKey;
		connectedDimensions = builder.connectedDimensions;
		mobClass = builder.mobClass;
		overlay = builder.overlay;
	}

	/**
	 * Returns the red segment of the color
	 */
	public int getR() {
		return r;
	}

	/**
	 * Returns the green segment of the color
	 */
	public int getG() {
		return g;
	}

	/**
	 * Returns the blue segment of the color
	 */
	public int getB() {
		return b;
	}

	/**
	 * Changes the color of the portal edge<br>
	 * (set any value to -1 if you don't want to replace it)
	 */
	public void changeColor(int r, int g, int b) {
		if(r >= 0)
			this.r = r;
		if(g >= 0)
			this.g = g;
		if(b >= 0)
			this.b = b;
	}

	/**
	 * Returns the dimension ID associated
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the required Gateway Key
	 * <ul>
	 * <li>0 = Gateway Key</li>
	 * <li>1 = Dreadlands Infused Gateway Key</li>
	 * <li>2 = Omothol-forged Gateway Key</li>
	 * <li>3 = The Silver Key</li>
	 * </ul>
	 */
	public int getGatewayKey() {
		return gatewayKey;
	}

	/**
	 * Changes the required Gateway Key
	 * <ul>
	 * <li>0 = Gateway Key</li>
	 * <li>1 = Dreadlands Infused Gateway Key</li>
	 * <li>2 = Omothol-forged Gateway Key</li>
	 * <li>3 = The Silver Key</li>
	 * </ul>
	 */
	public void setGatewayKey(int key) {
		gatewayKey = key;
	}

	/**
	 * Returns the dimensions this dimension is connected to<br>
	 * If two dimensions are connected, you can make a portal in one going to the other
	 */
	public Set<Integer> getConnectedDimensions() {
		return connectedDimensions;
	}

	/**
	 * Returns the class of the mob the portal can spawn at times
	 */
	@Nullable
	public Class<? extends EntityLiving> getMobClass() {
		return mobClass;
	}

	/**
	 * Changes which mob the portal can spawn at times
	 */
	public void setMobClass(Class<? extends EntityLiving> clazz) {
		mobClass = clazz;
	}

	/**
	 * Returns the overlay texture for the portal, if any
	 */
	@Nullable
	public ResourceLocation getOverlay() {
		return overlay;
	}

	/**
	 * Changes the overlay textures for the portal
	 */
	public void setOverlay(ResourceLocation rl) {
		overlay = rl;
	}

	public static class Builder {

		private int r, g, b;

		private int id, gatewayKey;
		private Set<Integer> connectedDimensions = new HashSet<>();
		private Class<? extends EntityLiving> mobClass;
		private ResourceLocation overlay;

		public Builder(int id) {
			this.id = id;
			r = 1;
			g = 1;
			b = 1;
		}

		/**
		 * Sets the portal color (a value between 0 and 255)
		 * @param r Red color
		 * @param g Green color
		 * @param b Blue color
		 */
		public Builder setColor(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
			return this;
		}

		/**
		 * Sets the required gateway key
		 * <ul>
		 * <li>0 = Gateway Key</li>
		 * <li>1 = Asorah's Dreaded Gateway Key</li>
		 * <li>1 = Cha'garoth's R'lyehian Gateway Key</li>
		 * <li>3 = The Silver Key</li>
		 * </ul>
		 * @param gatewayKey Integer representing the key in question
		 */
		public Builder setGatewayKey(int gatewayKey) {
			this.gatewayKey = gatewayKey;
			return this;
		}

		/**
		 * Adds a connected dimension<br>
		 * If two dimensions are connected, you can make a portal in one going to the other
		 * If none are added, the dimension isn't restricted
		 * @param id ID of the dimension to add
		 */
		public Builder addConnectedDimension(int id) {
			connectedDimensions.add(id);
			return this;
		}

		/**
		 * Sets the mob the portal for this dimension can spawn at times
		 * @param mobClass Class of the mob
		 */
		public Builder setMob(Class<? extends EntityLiving> mobClass) {
			this.mobClass = mobClass;
			return this;
		}

		/**
		 * Sets an overlay texture for the portal
		 * @param overlay Resource Location pointing to the texture
		 */
		public Builder setOverlay(ResourceLocation overlay) {
			this.overlay = overlay;
			return this;
		}

		/**
		 * Builds the Dimension Data
		 */
		public DimensionData build() {
			DimensionData data = new DimensionData(this);

			return data;
		}
	}
}
