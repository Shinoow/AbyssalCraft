package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;

public class corblock extends BlockGeneralAC {

	public corblock() {
		super(Material.iron);
		this.setHarvestLevel("pickaxe", 5);
		
	}

}
