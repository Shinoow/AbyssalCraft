/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;
import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockDLTSapling extends BlockBush implements IGrowable
{

	public BlockDLTSapling()
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
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLTS");
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
		Object obj = new WorldGenDLT(true);
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