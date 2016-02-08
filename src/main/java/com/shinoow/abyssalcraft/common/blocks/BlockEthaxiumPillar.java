/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.material.Material;

public class BlockEthaxiumPillar extends BlockACBasic {

	//	@SideOnly(Side.CLIENT)
	//	private IIcon[] icons;
	//	@SideOnly(Side.CLIENT)
	//	private static IIcon overlay;

	public BlockEthaxiumPillar() {
		super(Material.rock, "pickaxe", 8, 100.0F, Float.MAX_VALUE, soundTypeStone);
	}

	//	@Override
	//	@SideOnly(Side.CLIENT)
	//	public IIcon getIcon(int par1, int par2) {
	//		return par1 == 1 ? icons[1] : par1 == 0 ? icons[1] : icons[0];
	//	}

	//	@Override
	//	@SideOnly(Side.CLIENT)
	//	public void registerBlockIcons(IIconRegister par1IconRegister)
	//	{
	//		icons = new IIcon[2];
	//		icons[0] = par1IconRegister.registerIcon("abyssalCraft:EBP");
	//		icons[1] = par1IconRegister.registerIcon("abyssalcraft:EBP_top");
	//		BlockEthaxiumPillar.overlay = par1IconRegister.registerIcon("abyssalcraft:EBP");
	//	}

	//	@SideOnly(Side.CLIENT)
	//	public static IIcon getIconSideOverlay()
	//	{
	//		return overlay;
	//	}
}
