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


public class DLTSapling extends BlockBush implements IGrowable
{

	public DLTSapling()
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
		if(world.isRemote)
		{

			super.updateTick(world, i, j, k, random);

			if(world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(7) == 0)
			{
				int l = world.getBlockMetadata(i, j, k);
				if((l & 8) == 0)
				{
					world.setBlockMetadataWithNotify(i, j, k, l | 8, l);
				} else
				{
					growTree(world, i, j, k, random);
				}
			}
		}
	}

	public void growTree(World world, int i, int j, int k, Random random)
	{
		int l = world.getBlockMetadata(i, j, k) & 3;
		world.setBlock(i, j, k, Blocks.air, 0, l);
		Object obj = null;
		obj = new WorldGenDLT(true);
		if(!((WorldGenerator) obj).generate(world, random, i, j, k))
		{
			world.setBlock(i, j, k, this, l, l);
		}
	}


	public void fertilize(World world, int x, int y, int z)
	{

	}

	@Override
	public boolean func_149851_a(World var1, int var2, int var3, int var4,
			boolean var5) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean func_149852_a(World var1, Random var2, int var3, int var4,
			int var5) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void func_149853_b(World var1, Random var2, int var3, int var4, int var5) {
		// TODO Auto-generated method stub

	}
}