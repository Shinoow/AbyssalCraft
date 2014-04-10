package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.item.Item;

public enum EnumToolMaterialAC
{
		DARKSTONE(1, 180, 5.0F, 1, 15),
		ABYSSALNITE(4, 1261, 13.0F, 4, 10),
		CORALIUM(6, 4000, 14.0F, 6, 12),
		ABYSSALNITE_C(8, 8000, 20.0F, 8, 30);
        private final int harvestLevel;
        private final int maxUses;
        private final float efficiencyOnProperMaterial;
        private final int damageVsEntity;
        private final int enchantability;
        private EnumToolMaterialAC(int par3, int par4, float par5, int par6, int par7)
        {
                harvestLevel = par3;
                maxUses = par4;
                efficiencyOnProperMaterial = par5;
                damageVsEntity = par6;
                enchantability = par7;
        }
        
        public Item customCraftingMaterial = null;
        
        public int getMaxUses()
        {
                return maxUses;
        }
        public float getEfficiencyOnProperMaterial()
        {
                return efficiencyOnProperMaterial;
        }
        public int getDamageVsEntity()
        {
                return damageVsEntity;
        }
        public int getHarvestLevel()
        {
                return harvestLevel;
        }
        public int getEnchantability()
        {
                return enchantability;
        }
        public Item getToolCraftingMaterial()
		{	
			switch(this)
			{
			case DARKSTONE: return Item.getItemFromBlock(AbyssalCraft.Darkstone_cobble);
			case ABYSSALNITE: return AbyssalCraft.abyingot;
			case CORALIUM: return AbyssalCraft.Cingot;
			case ABYSSALNITE_C: return AbyssalCraft.Cpearl;
			default:      return customCraftingMaterial;
			}
		}
}
