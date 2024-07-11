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
