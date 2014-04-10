package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.BlockGeneralAC;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Dreadgrass extends BlockGeneralAC
{
	@SideOnly(Side.CLIENT)
	private IIcon iconGrassTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconSnowOverlay;
	@SideOnly(Side.CLIENT)
	private static IIcon iconGrassSideOverlay;

	public Dreadgrass() {
		super(Material.grass);
		this.setTickRandomly(true);
		this.setCreativeTab(AbyssalCraft.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? this.iconGrassTop : (par1 == 0 ? Blocks.dirt.getBlockTextureFromSide(par1) : this.blockIcon);
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
		{
			if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && par1World.getBlockLightOpacity(par2, par3 + 1, par4) > 2)
			{
				par1World.setBlock(par2, par3, par4, Blocks.dirt);
			}
			else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
			{
				for (int l = 0; l < 4; ++l)
				{
					int i1 = par2 + par5Random.nextInt(3) - 1;
					int j1 = par3 + par5Random.nextInt(5) - 3;
					int k1 = par4 + par5Random.nextInt(3) - 1;

					if (par1World.getBlock(i1, j1, k1) == Blocks.dirt && par1World.getBlockLightValue(i1, j1 + 1, k1) >= 4 && par1World.getBlockLightOpacity(i1, j1 + 1, k1) <= 2)
					{
						par1World.setBlock(i1, j1, k1, AbyssalCraft.dreadgrass);
					}
				}
			}
		}
	}

//	@Override
//	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
//	{
//		Block plant = plantable.getPlant(world, x, y + 1, z);
//		if (plant == AbyssalCraft.dreadsapling)
//		{
//			return true;
//		}
//		return false;
//	}

	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Blocks.dirt.getItemDropped(0, par2Random, par3);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (par5 == 1)
		{
			return this.iconGrassTop;
		}
		else if (par5 == 0)
		{
			return Blocks.dirt.getBlockTextureFromSide(par5);
		}
		else
		{
			Material material = par1IBlockAccess.getBlock(par2, par3 + 1, par4).getMaterial();
			return material != Material.snow && material != Material.craftedSnow ? this.blockIcon : this.iconSnowOverlay;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrGsides");
		this.iconGrassTop = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrGtop");
		this.iconSnowOverlay = par1IconRegister.registerIcon("grass_side_snowed");
		Dreadgrass.iconGrassSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrGsides");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay()
	{
		return iconGrassSideOverlay;
	}
}
