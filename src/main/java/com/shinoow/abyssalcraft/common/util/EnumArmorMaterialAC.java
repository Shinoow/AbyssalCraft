package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.item.Item;

public enum EnumArmorMaterialAC
{
    Abyssalnite(35, new int[]{4, 12, 6, 3}, 10),
    AbyssalniteC(36, new int[]{4, 12, 7, 4}, 30),
    Dread(36, new int[]{4, 12, 6, 3}, 12),
    Coralium(37, new int[]{4, 12, 6, 3}, 12),
    CoraliumP(55, new int[]{6, 12, 8, 5}, 12),
    Depths(33, new int[]{4, 12, 6, 3}, 25);

    /**
     * Holds the damage reduction (each 1 points is half a shield on gui) of each piece of armor (helmet, plate, legs
     * and boots)
     */
    private int[] damageReductionAmountArray;

    /** Return the enchantability factor of the material */
    private int enchantability;

    //Added by forge for custom Armor materials.
    public Item customCraftingMaterial = null;

    private EnumArmorMaterialAC(int par3, int[] par4ArrayOfInteger, int par5)
    {
        this.damageReductionAmountArray = par4ArrayOfInteger;
        this.enchantability = par5;
    }

    /**
     * Returns the durability for a armor slot of for this type.
     */
    //public int getDurability(int par1)
    //{
        //return ItemArmor.getMaxDamageArray()[par1] * this.maxDamageFactor;
    //}

    /**
     * Return the damage reduction (each 1 point is a half a shield on gui) of the piece index passed (0 = helmet, 1 =
     * plate, 2 = legs and 3 = boots)
     */
    public int getDamageReductionAmount(int par1)
    {
        return this.damageReductionAmountArray[par1];
    }

    /**
     * Return the enchantability factor of the material.
     */
    public int getEnchantability()
    {
        return this.enchantability;
    }

    /**
     * Return the crafting material for this armor material, used to determine the item that can be used to repair an
     * armor piece with an anvil
     */
    public Item getArmorCraftingMaterial()
    {
        switch (this)
        {
            case Abyssalnite:   return AbyssalCraft.abyingot;
            case AbyssalniteC:   return AbyssalCraft.Cpearl;
            case Dread:    return AbyssalCraft.Dreadshard;
            case Coralium:    return AbyssalCraft.Cingot;
            case CoraliumP: return AbyssalCraft.Cplate;
            case Depths: return AbyssalCraft.Coraliumcluster9;
            default:      return customCraftingMaterial;
        }
    }
}
