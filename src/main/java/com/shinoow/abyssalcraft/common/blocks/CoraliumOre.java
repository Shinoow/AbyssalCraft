package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class CoraliumOre extends BlockGeneralAC
{
	public CoraliumOre()
	{
		super(Material.rock);
		this.setCreativeTab(AbyssalCraft.tabBlock);
		this.setHarvestLevel("pickaxe", 2);
    }
	
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return this == AbyssalCraft.Coraliumore ? AbyssalCraft.Coralium: Item.getItemFromBlock(this);
	}

}