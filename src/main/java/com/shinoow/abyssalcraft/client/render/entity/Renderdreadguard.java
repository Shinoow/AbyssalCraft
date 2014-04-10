package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.entity.Entitydreadguard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Renderdreadguard extends RenderBiped
{
	/** Scale of the model to use */
    private float scale;
    
    private static final ResourceLocation texture = new ResourceLocation("abyssalcraft:textures/model/elite/Dread_guard.png");

    private ModelBiped field_82434_o;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    
    public Renderdreadguard(ModelZombie par1ModelBiped, float par2, float par3)
    {
    	super(par1ModelBiped, par2 * par3);
    	this.field_82434_o = this.modelBipedMain;
        this.scale = par3;
    }
    
    protected void func_82421_b()
    {
        this.field_82423_g = new ModelZombie(1.0F, true);
        this.field_82425_h = new ModelZombie(0.5F, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
    }
    
    /**
     * Applies the scale to the transform matrix
     */
    protected void preRenderScale(Entitydreadguard par1Entitydreadguard, float par2)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected int func_82429_a(Entitydreadguard par1Entitydreadguard, int par2, float par3)
    {
        this.func_82427_a(par1Entitydreadguard);
        //return super.func_130006_a(par1Entitydreadguard, par2, par3);
		return par2;
    }

    public void func_82426_a(Entitydreadguard par1Entitydreadguard, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_82427_a(par1Entitydreadguard);
        super.doRender(par1Entitydreadguard, par2, par4, par6, par8, par9);
    }
    
    protected ResourceLocation getTexture(Entitydreadguard par1Entitydreadguard)
    {
        return texture;
    }

    protected void func_82428_a(Entitydreadguard par1Entitydreadguard, float par2)
    {
        this.func_82427_a(par1Entitydreadguard);
        //super.func_130005_c(par1Entitydreadguard, par2);
    }

    private void func_82427_a(Entitydreadguard par1Entitydreadguard)
    {
        {
            this.mainModel = this.field_82434_o;
            this.field_82423_g = this.field_82437_k;
            this.field_82425_h = this.field_82435_l;
        }

        this.modelBipedMain = (ModelBiped)this.mainModel;
    }
    
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.func_82428_a((Entitydreadguard)par1EntityLivingBase, par2);
    }
    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderScale((Entitydreadguard)par1EntityLivingBase, par2);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getTexture((Entitydreadguard)par1Entity);
    }
    
}
