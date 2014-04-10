package com.shinoow.abyssalcraft.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.ItemGeneralAC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoraliumcluster extends ItemGeneralAC

	{
	@SuppressWarnings("rawtypes")
	private static final Map gem = new HashMap();

    /** The name of the record. */
    public final String gemName;

    @SuppressWarnings("unchecked")
	public ItemCoraliumcluster(String par2Str)
    {
        super();
        this.gemName = par2Str;
        this.maxStackSize = 16;
        this.setCreativeTab(AbyssalCraft.tabItems);
        gem.put(par2Str, this);
}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
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
        return this.gemName + " Gems";
    }

}
