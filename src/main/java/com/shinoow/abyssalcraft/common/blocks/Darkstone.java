package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class Darkstone extends BlockGeneralAC
{
	public Darkstone()
	{
		super(Material.rock);
		this.setCreativeTab(AbyssalCraft.tabBlock);
	}
	
	public Item getItemDropped(int i, Random random, int j)
	{
		return AbyssalCraft.Darkstone_cobble.getItemDropped(0, random, j);
	}
	
}
