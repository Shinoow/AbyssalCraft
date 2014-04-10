package com.shinoow.abyssalcraft.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.ItemGeneralAC;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUpgradeKit extends ItemGeneralAC

{
	@SuppressWarnings("rawtypes")
	private static final Map type = new HashMap();

	/** The name of the record. */
	public final String typeName;
	public final String typeName2;

	@SuppressWarnings("unchecked")
	public ItemUpgradeKit(String par2Str, String par3Str)
	{
		super();
		this.typeName = par2Str;
		this.typeName2 = par3Str;
		this.maxStackSize = 16;
		this.setCreativeTab(AbyssalCraft.tabItems);
		type.put(par2Str, this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(this.getRecordTitle());
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Return the title for this record.
	 */
	public String getRecordTitle()
	{
		return this.typeName + " To " + this.typeName2;
	}

}
