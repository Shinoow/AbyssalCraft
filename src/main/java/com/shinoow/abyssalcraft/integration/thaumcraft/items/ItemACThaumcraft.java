package com.shinoow.abyssalcraft.integration.thaumcraft.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import com.shinoow.abyssalcraft.integration.thaumcraft.creativetabs.TabACThaum;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemACThaumcraft extends Item {

	private String[] names;

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	/**
	 * Non-metadata item
	 * @param name unlocalized name
	 */
	public ItemACThaumcraft(String name){
		this(name, false, (String[])null);
	}

	/**
	 * Metadata item
	 * @param name unlocalized name
	 * @param meta if it's a metadata item
	 * @param names additional names (for metadata)
	 */
	public ItemACThaumcraft(String name, boolean meta, String...names) {
		super();
		this.names = names;
		if(names != null)
			setMaxDamage(0);
		setUnlocalizedName(name);
		setHasSubtypes(meta);
		setTextureName(name);
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			TabACThaum.instance.addItem(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int i)
	{
		if(names == null)
			return icons[0];
		else{
			int j = MathHelper.clamp_int(i, 0, names.length);
			return icons[j];
		}
	}

	@Override
	public int getMetadata(int meta) {
		return names == null ? 0 : meta;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List){
		if(names != null)
			for(int i = 0; i < names.length; ++i)
				par3List.add(new ItemStack(par1Item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		if(names == null)
			icons[0] = par1IconRegister.registerIcon("abyssalcraft:" + getIconString());
		else {
			icons = new IIcon[names.length];
			for(int i = 0; i < names.length; i++)
				icons[i] = par1IconRegister.registerIcon("abyssalcraft:" + getIconString() + "_" + names[i]);
		}

	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(names == null)
			return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
		else return StatCollector.translateToLocal(getUnlocalizedName() + "." + names[par1ItemStack.getItemDamage()] + ".name");
	}
}