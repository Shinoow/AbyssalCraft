package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Dreadlog extends BlockRotatedPillar
{
	@SideOnly(Side.CLIENT)
    private IIcon iconLogTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconLogSide;
    @SideOnly(Side.CLIENT)
    private static IIcon iconLogSideOverlay;

	public Dreadlog() {
		super(Material.wood);
		this.setCreativeTab(AbyssalCraft.tabBlock);
	}
	
		public int getRenderType()
		{
			return 31;
		}
	
		public int quantityDropped(Random par1Random)
		{
			return 1;
		}

		public Item getItemDropped(int par1, Random par2Random, int par3)
		{
			return Item.getItemFromBlock(this);
		}
		
		public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	    {
	        byte var7 = 4;
	        int var8 = var7 + 1;

	        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
	        {
	            for (int var9 = -var7; var9 <= var7; ++var9)
	            {
	                for (int var10 = -var7; var10 <= var7; ++var10)
	                {
	                    for (int var11 = -var7; var11 <= var7; ++var11)
	                    {
	                        Block var12 = par1World.getBlock(par2 + var9, par3 + var10, par4 + var11);

	                        if (var12.isLeaves(par1World, par2 + var9, par3 + var10, par4 + var11))
	                        {
	                            var12.beginLeavesDecay(par1World, par2 + var9, par3 + var10, par4 + var11);
	                        }
	                    }
	                }
	            }
	        }
	    }

	    public void updateBlockMetadata(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
	    {
	        int var9 = par1World.getBlockMetadata(par2, par3, par4) & 3;
	        byte var10 = 0;

	        switch (par5)
	        {
	            case 0:
	            case 1:
	                var10 = 0;
	                break;
	            case 2:
	            case 3:
	                var10 = 8;
	                break;
	            case 4:
	            case 5:
	                var10 = 4;
	        }

	        par1World.setBlockMetadataWithNotify(par2, par3, par4, var9 | var10, var9);
	    }


	    public int damageDropped(int par1)
	    {
	        return par1 & 3;
	    }
		
		public static int limitToValidMetadata(int par0)
	    {
	        return par0 & 3;
	    }

	    protected ItemStack createStackedBlock(int par1)
	    {
	        return new ItemStack(this, 1, limitToValidMetadata(par1));
	    }
	    
	    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
	    {
	        return true;
	    }
		
		@SideOnly(Side.CLIENT)
		public IIcon getIcon(int par1, int par2)
	    {
	    return par1 == 1 ? this.iconLogTop : (par1 == 0 ? this.iconLogTop : this.blockIcon);
	    }
		
		@SideOnly(Side.CLIENT)
		public void registerBlockIcons(IIconRegister par1IconRegister)
	    {
	        this.blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrTside");
	        this.iconLogTop = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrTtop");
	        this.iconLogSide = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrTside");
	        Dreadlog.iconLogSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DrTside");
	        }

		@SideOnly(Side.CLIENT)
	    public static IIcon getIconSideOverlay()
	    {
	    return iconLogSideOverlay;
	    }
		
		@SideOnly(Side.CLIENT)
		protected IIcon getSideIcon(int var1) {

			return iconLogSide;
		}
	
}
