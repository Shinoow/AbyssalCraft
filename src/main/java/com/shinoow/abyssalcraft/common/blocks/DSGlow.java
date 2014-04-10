package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DSGlow extends BlockGeneralAC
{
	@SideOnly(Side.CLIENT)
	private IIcon field_94393_a;
	@SideOnly(Side.CLIENT)
	private IIcon field_94392_b;
	@SideOnly(Side.CLIENT)
	private static IIcon iconDSGlowSideOverlay;

	public DSGlow()
	{
		super(Material.rock);
		this.setCreativeTab(AbyssalCraft.tabBlock);
		this.setHarvestLevel("pickaxe", 3);
	}

	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? this.field_94393_a : (par1 == 0 ? this.field_94392_b : this.blockIcon);
	}


	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSGlow");
		this.field_94393_a = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSB");
		this.field_94392_b = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSB");
		DSGlow.iconDSGlowSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSGlow");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay()
	{
		return iconDSGlowSideOverlay;
	}
}
