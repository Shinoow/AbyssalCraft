package com.shinoow.abyssalcraft.common.blocks;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Darkstonecobblewall extends BlockWall{

	public Darkstonecobblewall(Block par2Block) {
		super(par2Block);
		this.setCreativeTab(AbyssalCraft.tabBlock);

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public IIcon getIcon(int par1, int par2)
    {
        return AbyssalCraft.Darkstone_cobble.getBlockTextureFromSide(par1);
    }
}
