/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockRitualAltar extends BlockACBasic {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockRitualAltar() {
		super(Material.rock, 6.0F, 12.0F, Block.soundTypeStone);
		setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F);
		setCreativeTab(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 5));
		par3List.add(new ItemStack(par1, 1, 6));
		par3List.add(new ItemStack(par1, 1, 7));
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[8];
		icons[0] = par1IconRegister.registerIcon("minecraft:cobblestone");
		icons[1] = par1IconRegister.registerIcon("abyssalcraft:DSC");
		icons[2] = par1IconRegister.registerIcon("abyssalcraft:ASB");
		icons[3] = par1IconRegister.registerIcon("abyssalcraft:cstonebrick");
		icons[4] = par1IconRegister.registerIcon("abyssalcraft:DrSB");
		icons[5] = par1IconRegister.registerIcon("abyssalcraft:AbyDrSB");
		icons[6] = par1IconRegister.registerIcon("abyssalcraft:EB");
		icons[7] = par1IconRegister.registerIcon("abyssalcraft:DEB");
	}
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		if(par2 == 0) return icons[0];
		if(par2 == 1) return icons[1];
		if(par2 == 2) return icons[2];
		if(par2 == 3) return icons[3];
		if(par2 == 4) return icons[4];
		if(par2 == 5) return icons[5];
		if(par2 == 6) return icons[6];
		if(par2 == 7) return icons[7];
		return super.getIcon(par1, par2);
	}
}
