package com.shinoow.abyssalcraft.common.blocks;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class Cstonebrickslabdouble extends BlockSlab
{
	/** The list of the types of step blocks. */
	public Cstonebrickslabdouble(boolean par2)
	{
		super(par2, Material.rock);
		this.setCreativeTab(null);
		this.setLightOpacity(0);
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return AbyssalCraft.cstonebrickslab1.getItemDropped(par1, par2Random, par3);
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{
		if(par1World.getBlock(par2, par3 - 1, par4) == AbyssalCraft.cstonebrickslab2)
		{
			par1World.setBlock(par2, par3, par4, Blocks.air);
			par1World.setBlock(par2, par3 - 1, par4, AbyssalCraft.cstonebrickslab2);
		}
	}
	/**
	 * Returns an item stack containing a single instance of the current block type. 'par1' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	@SideOnly(Side.CLIENT)
	private static boolean func_150003_a(Block p_150003_0_)
	{
		return p_150003_0_ == AbyssalCraft.cstonebrickslab2;
	}

	/**
	 * Returns an item stack containing a single instance of the current block type. 'par1' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	@SideOnly(Side.CLIENT)
	public Item func_149694_d(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return func_150003_a(this) ? Item.getItemFromBlock(this) : (this == AbyssalCraft.cstonebrickslab2 ? Item.getItemFromBlock(AbyssalCraft.cstonebrickslab1) : Item.getItemFromBlock(AbyssalCraft.cstonebrickslab1));
	}

	public String func_150002_b(int var1)
	{
		return AbyssalCraft.cstonebrickslab2.getLocalizedName();
	}

}