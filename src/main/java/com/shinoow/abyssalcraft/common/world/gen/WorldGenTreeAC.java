package com.shinoow.abyssalcraft.common.world.gen;

import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenTreeAC extends WorldGenTrees {

	protected boolean fixed;
	
	public WorldGenTreeAC(boolean p_i2027_1_) {
		super(p_i2027_1_);
	}
	
	/**
	 * Sets a fixed height and fixed trunk height
	 */
	public void setFixed() {
		fixed = true;
	}
}
