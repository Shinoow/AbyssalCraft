package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;

public class abybrick extends BlockGeneralAC
{

	public abybrick() {
		super(Material.rock);
		this.setHarvestLevel("pickaxe", 2);
		
	}
}
