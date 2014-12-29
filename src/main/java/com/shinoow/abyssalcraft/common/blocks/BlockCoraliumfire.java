/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoraliumfire extends BlockFire {

	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	public BlockCoraliumfire()
	{
		super();
		setTickRandomly(true);
	}

	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return iconArray[0];
	}

	private boolean canNeighborBurn(World par1World, int par2, int par3, int par4)
	{
		return canCatchFire(par1World, par2 + 1, par3, par4, ForgeDirection.WEST) ||
				canCatchFire(par1World, par2 - 1, par3, par4, ForgeDirection.EAST) ||
				canCatchFire(par1World, par2, par3 - 1, par4, ForgeDirection.UP) ||
				canCatchFire(par1World, par2, par3 + 1, par4, ForgeDirection.DOWN) ||
				canCatchFire(par1World, par2, par3, par4 - 1, ForgeDirection.SOUTH) ||
				canCatchFire(par1World, par2, par3, par4 + 1, ForgeDirection.NORTH);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);

		if(par5Entity instanceof EntityLivingBase){
			((EntityLivingBase)par5Entity).setFire(10);
			if(par5Entity instanceof ICoraliumEntity) {}
			else ((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 100));
		}
		if(par5Entity instanceof EntityItem)
			par5Entity.setDead();
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{

		if (par1World.getBlock(par2, par3 - 1, par4) != AbyssalCraft.abystone || !BlockAbyssPortal.tryToCreatePortal(par1World, par2, par3, par4))
			if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4) && !canNeighborBurn(par1World, par2, par3, par4))
				par1World.setBlockToAir(par2, par3, par4);
			else
				par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World) + par1World.rand.nextInt(10));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		iconArray = new IIcon[] { par1IconRegister.registerIcon("abyssalcraft:cfire_layer_0"), par1IconRegister.registerIcon("abyssalcraft:cfire_layer_1") };
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "cfire_layer_0");
	}
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getFireIcon(int par1) {
		return iconArray[par1];
	}

}