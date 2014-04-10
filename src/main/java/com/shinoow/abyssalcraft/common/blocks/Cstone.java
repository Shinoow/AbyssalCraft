package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.BlockGeneralAC;

public class Cstone extends BlockGeneralAC {

	public Cstone() {
		super(Material.rock);
		this.setCreativeTab(AbyssalCraft.tabBlock);
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

		if (!par1World.isRemote)
		{
			if (par1World.getBlock(par2, par3, par4).getMaterial() == CLiquid.Cwater)
			{
				par1World.setBlock(par2, par3, par4, this);
			}

		}
	}
}
