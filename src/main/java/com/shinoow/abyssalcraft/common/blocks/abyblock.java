package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;

public class abyblock extends BlockGeneralAC
{

	public abyblock() {
		super(Material.iron);
		this.setHarvestLevel("pickaxe", 2);
		
	}
}
