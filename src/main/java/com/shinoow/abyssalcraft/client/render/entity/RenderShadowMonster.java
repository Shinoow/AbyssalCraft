package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.client.model.entity.ModelShadowMonster;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;


public class RenderShadowMonster extends RenderLiving
{
 protected ModelShadowMonster model;
 
 private static final ResourceLocation field_110865_p = new ResourceLocation("abyssalcraft:textures/model/ShadowMonster.png");
 
 public RenderShadowMonster (ModelShadowMonster ModelShadowMonster, float f)
 {
  super(ModelShadowMonster, f);
  model = ((ModelShadowMonster)mainModel);
 }
 
 public void renderShadowMonster(EntityShadowMonster entity, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRender(entity, par2, par4, par6, par8, par9);
    }
 
 public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
	 renderShadowMonster((EntityShadowMonster)par1EntityLiving, par2, par4, par6, par8, par9);
    }
 
 public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
	 renderShadowMonster((EntityShadowMonster)par1Entity, par2, par4, par6, par8, par9);
    }

@Override
protected ResourceLocation getEntityTexture(Entity entity) {

	return field_110865_p;
}
}