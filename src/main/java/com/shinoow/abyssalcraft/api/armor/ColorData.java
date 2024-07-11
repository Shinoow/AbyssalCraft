package com.shinoow.abyssalcraft.api.armor;

public class ColorData {

	private String material, color;

	public ColorData() {
		material = "";
		color = "";
	}

	public String getMaterial() {
		return material;
	}

	public int getColor() {
		return Integer.decode(color);
	}
}
