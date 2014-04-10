package com.shinoow.abyssalcraft.common.blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
public class Dreadbrickstairs extends BlockStairs
{
	private static final int[][] field_72159_a = new int[][] {{2, 6}, {3, 7}, {2, 3}, {6, 7}, {0, 4}, {1, 5}, {0, 1}, {4, 5}};
	private boolean field_72156_cr = false;
	private int field_72160_cs = 0;
	public Dreadbrickstairs(int j)
	{
		super(AbyssalCraft.dreadbrick, j);
		this.setLightOpacity(0);
		this.setCreativeTab(AbyssalCraft.tabBlock);
		this.setHarvestLevel("pickaxe", 4);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (this.field_72156_cr)
		{
			this.setBlockBounds(0.5F * (float)(this.field_72160_cs % 2), 0.5F * (float)(this.field_72160_cs / 2 % 2), 0.5F * (float)(this.field_72160_cs / 4 % 2), 0.5F + 0.5F * (float)(this.field_72160_cs % 2), 0.5F + 0.5F * (float)(this.field_72160_cs / 2 % 2), 0.5F + 0.5F * (float)(this.field_72160_cs / 4 % 2));
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	/**
	 * Is this block (a) opaque and (B) a full 1m cube? This determines whether or not to render the shared face of two
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
	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return 10;
	}

	private boolean func_150146_f(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
		return func_150148_a(i1) && par1IBlockAccess.getBlockMetadata(par2, par3, par4) == par5;
	}

	public boolean func_82542_g(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		int i1 = l & 3;
		float f = 0.5F;
		float f1 = 1.0F;

		if ((l & 4) != 0)
		{
			f = 0.0F;
			f1 = 0.5F;
		}

		float f2 = 0.0F;
		float f3 = 1.0F;
		float f4 = 0.0F;
		float f5 = 0.5F;
		boolean flag = true;
		Block j1;
		int k1;
		int l1;

		if (i1 == 0)
		{
			f2 = 0.5F;
			f5 = 1.0F;
			j1 = par1IBlockAccess.getBlock(par2 + 1, par3, par4);
			k1 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);

			if (func_150148_a(j1) && (l & 4) == (k1 & 4))
			{
				l1 = k1 & 3;

				if (l1 == 3 && !this.func_150146_f(par1IBlockAccess, par2, par3, par4 + 1, l))
				{
					f5 = 0.5F;
					flag = false;
				}
				else if (l1 == 2 && !this.func_150146_f(par1IBlockAccess, par2, par3, par4 - 1, l))
				{
					f4 = 0.5F;
					flag = false;
				}
			}
		}
		else if (i1 == 1)
		{
			f3 = 0.5F;
			f5 = 1.0F;
			j1 = par1IBlockAccess.getBlock(par2 - 1, par3, par4);
			k1 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);

			if (func_150148_a(j1) && (l & 4) == (k1 & 4))
			{
				l1 = k1 & 3;

				if (l1 == 3 && !this.func_150146_f(par1IBlockAccess, par2, par3, par4 + 1, l))
				{
					f5 = 0.5F;
					flag = false;
				}
				else if (l1 == 2 && !this.func_150146_f(par1IBlockAccess, par2, par3, par4 - 1, l))
				{
					f4 = 0.5F;
					flag = false;
				}
			}
		}
		else if (i1 == 2)
		{
			f4 = 0.5F;
			f5 = 1.0F;
			j1 = par1IBlockAccess.getBlock(par2, par3, par4 + 1);
			k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);

			if (func_150148_a(j1) && (l & 4) == (k1 & 4))
			{
				l1 = k1 & 3;

				if (l1 == 1 && !this.func_150146_f(par1IBlockAccess, par2 + 1, par3, par4, l))
				{
					f3 = 0.5F;
					flag = false;
				}
				else if (l1 == 0 && !this.func_150146_f(par1IBlockAccess, par2 - 1, par3, par4, l))
				{
					f2 = 0.5F;
					flag = false;
				}
			}
		}
		else if (i1 == 3)
		{
			j1 = par1IBlockAccess.getBlock(par2, par3, par4 - 1);
			k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);

			if (func_150148_a(j1) && (l & 4) == (k1 & 4))
			{
				l1 = k1 & 3;

				if (l1 == 1 && !this.func_150146_f(par1IBlockAccess, par2 + 1, par3, par4, l))
				{
					f3 = 0.5F;
					flag = false;
				}
				else if (l1 == 0 && !this.func_150146_f(par1IBlockAccess, par2 - 1, par3, par4, l))
				{
					f2 = 0.5F;
					flag = false;
				}
			}
		}

		this.setBlockBounds(f2, f, f4, f3, f1, f5);
		return flag;
	}
	/**
	 * Called when the block is placed in the world.
	 */
	 public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
	{
		int l = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int i1 = par1World.getBlockMetadata(par2, par3, par4) & 4;

		if (l == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | i1, 2);
		}

		if (l == 1)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | i1, 2);
		}

		if (l == 2)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | i1, 2);
		}

		if (l == 3)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | i1, 2);
		}
	}
	 /**
	  * called before onBlockPlacedBy by ItemBlock and ItemReed
	  */
	 public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	 {
		 return par5 != 0 && (par5 == 1 || (double)par7 <= 0.5D) ? par9 : par9 | 4;
	 }
	 /**
	  * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	  * x, y, z, startVec, endVec
	  */
	 public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	 {
		 MovingObjectPosition[] var7 = new MovingObjectPosition[8];
		 int var8 = par1World.getBlockMetadata(par2, par3, par4);
		 int var9 = var8 & 3;
		 boolean var10 = (var8 & 4) == 4;
		 int[] var11 = field_72159_a[var9 + (var10 ? 4 : 0)];
		 this.field_72156_cr = true;
		 int var14;
		 int var15;
		 int var16;
		 for (int var12 = 0; var12 < 8; ++var12)
		 {
			 this.field_72160_cs = var12;
			 int[] var13 = var11;
			 var14 = var11.length;
			 for (var15 = 0; var15 < var14; ++var15)
			 {
				 var16 = var13[var15];
				 if (var16 == var12)
				 {
					 ;
				 }
			 }
			 var7[var12] = super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
		 }
		 int[] var21 = var11;
		 int var24 = var11.length;
		 for (var14 = 0; var14 < var24; ++var14)
		 {
			 var15 = var21[var14];
			 var7[var15] = null;
		 }
		 MovingObjectPosition var23 = null;
		 double var22 = 0.0D;
		 MovingObjectPosition[] var25 = var7;
		 var16 = var7.length;
		 for (int var17 = 0; var17 < var16; ++var17)
		 {
			 MovingObjectPosition var18 = var25[var17];
			 if (var18 != null)
			 {
				 double var19 = var18.hitVec.squareDistanceTo(par6Vec3);
				 if (var19 > var22)
				 {
					 var23 = var18;
					 var22 = var19;
				 }
			 }
		 }
		 return var23;
	 }
}