package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;

public class Abybutton extends BlockButton {

	public Abybutton()
	{
		super(false);
		this.setHarvestLevel("pickaxe", 2);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "AS");
	}
}