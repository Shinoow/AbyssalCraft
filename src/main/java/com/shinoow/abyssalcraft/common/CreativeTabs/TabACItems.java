package com.shinoow.abyssalcraft.common.CreativeTabs;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabACItems extends CreativeTabs
{
        public TabACItems(int id, String name)
        {
                 super(id, name);
         }

         @Override
         @SideOnly(Side.CLIENT)
         public String getTranslatedTabLabel()
         {
                 return "AbyssalCraft Items";
         }

         @Override
         @SideOnly(Side.CLIENT)
         public Item getTabIconItem()
         {
        	 return AbyssalCraft.OC;

         }
}