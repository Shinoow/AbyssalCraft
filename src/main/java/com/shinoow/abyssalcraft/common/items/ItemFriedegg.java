package com.shinoow.abyssalcraft.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFriedegg extends ItemFood {

	public ItemFriedegg(int j, float f, boolean b) {
		super(j, f, b);

	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + (this.getUnlocalizedName().substring(5)));
	}
}
