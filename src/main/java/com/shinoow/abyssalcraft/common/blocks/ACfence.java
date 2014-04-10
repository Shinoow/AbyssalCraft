package com.shinoow.abyssalcraft.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ACfence extends BlockFence
{
	private String fenceIconName;

	public ACfence(String par2, Material par3Material, String par4, int par5)
	{
		super(par2, par3Material);
		this.fenceIconName = par2;
		this.setCreativeTab(AbyssalCraft.tabDecoration);
		this.setHarvestLevel(par4, par5);

	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@SuppressWarnings("rawtypes")
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		boolean flag = this.canConnectFenceTo(par1World, par2, par3, par4 - 1);
		boolean flag1 = this.canConnectFenceTo(par1World, par2, par3, par4 + 1);
		boolean flag2 = this.canConnectFenceTo(par1World, par2 - 1, par3, par4);
		boolean flag3 = this.canConnectFenceTo(par1World, par2 + 1, par3, par4);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		if (flag || flag1)
		{
			this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}

		f2 = 0.375F;
		f3 = 0.625F;

		if (flag2)
		{
			f = 0.0F;
		}

		if (flag3)
		{
			f1 = 1.0F;
		}

		if (flag2 || flag3 || !flag && !flag1)
		{
			this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		boolean var5 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
		boolean var6 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
		boolean var7 = this.canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
		boolean var8 = this.canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
		float var9 = 0.375F;
		float var10 = 0.625F;
		float var11 = 0.375F;
		float var12 = 0.625F;

		if (var5)
		{
			var11 = 0.0F;
		}

		if (var6)
		{
			var12 = 1.0F;
		}

		if (var7)
		{
			var9 = 0.0F;
		}

		if (var8)
		{
			var10 = 1.0F;
		}

		this.setBlockBounds(var9, 0.0F, var11, var10, 1.0F, var12);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return 11;
	}

	/**
	 * Returns true if the specified block can be connected by a fence
	 */
	public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		Block var5 = par1IBlockAccess.getBlock(par2, par3, par4);

		return var5 != this && var5 != Blocks.fence_gate ? (var5.getMaterial().isOpaque() && var5.renderAsNormalBlock() ? var5.getMaterial() != Material.gourd : false) : true;
	}


	public static boolean func_149825_a(Block par0)
	{
		return par0 == AbyssalCraft.abyfence || par0 == AbyssalCraft.DSBfence || par0 == AbyssalCraft.DLTfence || par0 == AbyssalCraft.dreadbrickfence || par0 == AbyssalCraft.abydreadbrickfence
				|| par0 == AbyssalCraft.cstonebrickfence;
	}
	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.fenceIconName);
	}
}
