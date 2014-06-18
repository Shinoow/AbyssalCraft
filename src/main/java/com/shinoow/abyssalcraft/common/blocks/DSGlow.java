/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DSGlow extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon field_94393_a;
	@SideOnly(Side.CLIENT)
	private IIcon field_94392_b;
	@SideOnly(Side.CLIENT)
	private static IIcon iconDSGlowSideOverlay;

	public DSGlow() {
		super(Material.rock);
		setCreativeTab(AbyssalCraft.tabBlock);
		this.setHarvestLevel("pickaxe", 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2) {
		return par1 == 1 ? field_94393_a : par1 == 0 ? field_94392_b : blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSGlow");
		field_94393_a = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSB");
		field_94392_b = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSB");
		DSGlow.iconDSGlowSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DSGlow");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay()
	{
		return iconDSGlowSideOverlay;
	}
}