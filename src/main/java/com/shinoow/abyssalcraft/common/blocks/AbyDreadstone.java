package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;

public class AbyDreadstone extends BlockGeneralAC {

	public AbyDreadstone() {
		super(Material.rock);
		this.setCreativeTab(AbyssalCraft.tabBlock);
		this.setHarvestLevel("pickaxe", 4);
	}
}
