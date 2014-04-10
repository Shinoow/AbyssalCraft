package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;

public class abystone extends BlockGeneralAC
{

	public abystone() {
		super(Material.rock);
		this.setHarvestLevel("pickaxe", 2);
		
	}
}
