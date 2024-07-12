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
