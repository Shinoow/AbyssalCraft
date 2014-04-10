package com.shinoow.abyssalcraft.common.blocks;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class Darkstoneslab extends BlockSlab
{

	@SideOnly(Side.CLIENT)
	private IIcon iconSlabTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconSlabBottom;
	@SideOnly(Side.CLIENT)
	private static IIcon iconSlabSideOverlay;

	/** The list of the types of step blocks. */
	public Darkstoneslab(boolean par2)
	{
		super(par2, Material.rock);
		this.setCreativeTab(AbyssalCraft.tabBlock);
		this.setLightOpacity(0);
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{
		if(par1World.getBlock(par2, par3 - 1, par4) == AbyssalCraft.Darkstoneslab1)
		{
			par1World.setBlock(par2, par3, par4, Blocks.air);
			par1World.setBlock(par2, par3 - 1, par4, AbyssalCraft.Darkstoneslab2);
		}
	}
	@SideOnly(Side.CLIENT)
	private static boolean func_150003_a(Block p_150003_0_)
	{
		return p_150003_0_ == AbyssalCraft.Darkstoneslab1;
	}

	/**
	 * Returns an item stack containing a single instance of the current block type. 'par1' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	@SideOnly(Side.CLIENT)
	public Item func_149694_d(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return func_150003_a(this) ? Item.getItemFromBlock(this) : (this == AbyssalCraft.Darkstoneslab2 ? Item.getItemFromBlock(AbyssalCraft.Darkstoneslab1) : Item.getItemFromBlock(AbyssalCraft.Darkstoneslab1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String func_150002_b(int var1) {

		return AbyssalCraft.Darkstoneslab1.getLocalizedName();
	}

	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? this.iconSlabTop : (par1 == 0 ? this.iconSlabBottom : this.blockIcon);
	}


	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSsSides");
		this.iconSlabTop = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSsTop");
		this.iconSlabBottom = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSsTop");
		Darkstoneslab.iconSlabSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSsSides");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay()
	{
		return iconSlabSideOverlay;
	}
}