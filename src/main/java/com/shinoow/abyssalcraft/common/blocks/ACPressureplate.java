package com.shinoow.abyssalcraft.common.blocks;

import java.util.Iterator;
import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ACPressureplate extends BlockBasePressurePlate
{
    /** The mob type that can trigger this pressure plate. */
    private ACPressureplate.Sensitivity triggerMobType;

    private String pressurePlateIconName;
    
    public ACPressureplate(String par2Str, Material par3Material, ACPressureplate.Sensitivity par4EnumMobType, String par5, int par6)
    {
        super(par2Str, par3Material);
        this.triggerMobType = par4EnumMobType;
        this.pressurePlateIconName = par2Str;
        this.setHarvestLevel(par5, par6);
    }

    /**
     * Argument is weight (0-15). Return the metadata to be set because of it.
     */
    protected int func_150066_d(int par1)
    {
        return par1 > 0 ? 1 : 0;
    }

    /**
     * Argument is metadata. Returns power level (0-15)
     */
    protected int func_150060_c(int par1)
    {
        return par1 == 1 ? 15 : 0;
    }

    /**
     * Returns the current state of the pressure plate. Returns a value between 0 and 15 based on the number of items on
     * it.
     */
    @Override
    @SuppressWarnings("rawtypes")
    protected int func_150065_e(World p_150065_1_, int p_150065_2_, int p_150065_3_, int p_150065_4_)
    {
        List list = null;

        if (this.triggerMobType == ACPressureplate.Sensitivity.everything)
        {
            list = p_150065_1_.getEntitiesWithinAABBExcludingEntity((Entity)null, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }

        if (this.triggerMobType == ACPressureplate.Sensitivity.mobs)
        {
            list = p_150065_1_.getEntitiesWithinAABB(EntityLivingBase.class, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }

        if (this.triggerMobType == ACPressureplate.Sensitivity.players)
        {
            list = p_150065_1_.getEntitiesWithinAABB(EntityPlayer.class, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }

        if (list != null && !list.isEmpty())
        {
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                Entity entity = (Entity)iterator.next();

                if (!entity.doesEntityNotTriggerPressurePlate())
                {
                    return 15;
                }
            }
        }

        return 0;
    }

    public static enum Sensitivity
    {
        everything,
        mobs,
        players;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.pressurePlateIconName);
    }
    
}
