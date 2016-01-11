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

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDrT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDreadSapling extends BlockBush implements IGrowable {

	public BlockDreadSapling()
	{
		super();
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrTS");
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if(!world.isRemote)
		{
			super.updateTick(world, i, j, k, random);
			if(world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(7) == 0)
				growTree(world, i, j, k, random);
		}
	}

	public void growTree(World world, int x, int y, int z, Random random)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, random, x, y, z)) return;

		world.setBlock(x, y, z, Blocks.air, 0, 1);
		Object obj = new WorldGenDrT(true);
		if(!((WorldGenerator) obj).generate(world, random, x, y, z))
			world.setBlock(x, y, z, this, 0, 4);
	}

	@Override
	public boolean func_149851_a(World var1, int var2, int var3, int var4,
			boolean var5) {

		return true;
	}

	@Override
	public boolean func_149852_a(World var1, Random var2, int var3, int var4,
			int var5) {

		return var1.rand.nextFloat() < 0.45D;
	}

	@Override
	public void func_149853_b(World var1, Random var2, int var3, int var4, int var5) {
		growTree(var1, var3, var4, var5, var2);
	}
}
