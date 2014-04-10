package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDGhead;

public class DGhead extends BlockContainer {

	public DGhead() {
		super(Material.cloth);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.8F, 0.7F, 0.8F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDGhead();
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType() {
		return -3;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public AxisAlignedBB func_149668_a(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		if (par5EntityLivingBase == null)
		{
			return;
		}

		TileEntityDGhead tile = (TileEntityDGhead) par1World.getTileEntity(par2, par3, par4);
		tile.direction = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	}

}
