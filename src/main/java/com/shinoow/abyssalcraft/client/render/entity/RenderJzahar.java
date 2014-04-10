package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelJzahar;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class RenderJzahar extends RenderLiving
{
 protected ModelJzahar model;
 
 private static final ResourceLocation field_110865_p = new ResourceLocation("abyssalcraft:textures/model/boss/J'zahar.png");
 
 public RenderJzahar (ModelJzahar ModelJzahar, float f)
 {
  super(ModelJzahar, f);
  model = ((ModelJzahar)mainModel);
 }
 
 public void renderJzahar(EntityJzahar entity, double par2, double par4, double par6, float par8, float par9)
    {
	 BossStatus.setBossStatus(entity, false);
        super.doRender(entity, par2, par4, par6, par8, par9);
    }
 
 public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
	 renderJzahar((EntityJzahar)par1EntityLiving, par2, par4, par6, par8, par9);
    }
 
 public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
	 renderJzahar((EntityJzahar)par1Entity, par2, par4, par6, par8, par9);
    }

@Override
protected ResourceLocation getEntityTexture(Entity entity) {
	
	return field_110865_p;
}

}
