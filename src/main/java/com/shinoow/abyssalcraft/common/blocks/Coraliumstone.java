package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class Coraliumstone extends BlockGeneralAC
{
	public Coraliumstone()
	{
		super(Material.rock);
		this.setCreativeTab(AbyssalCraft.tabBlock);
		this.setHarvestLevel("pickaxe", 3);
    }
	
	public Item func_149650_a(int par1, Random par2Random, int par3)
	{
		return this == AbyssalCraft.Coraliumstone ? AbyssalCraft.Cpearl: Item.getItemFromBlock(this);
	}

}