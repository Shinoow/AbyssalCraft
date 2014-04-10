package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.entity.EntityDemonPig;


public class RenderDemonPig extends RenderLiving
{
	protected ModelPig model;

	private static final ResourceLocation field_110887_f = new ResourceLocation("abyssalcraft:textures/model/elite/Dread_guard.png");

	public RenderDemonPig (ModelPig ModelPig, float f)
	{
		super(ModelPig, f);
		model = ((ModelPig)mainModel);
	}

	public void renderPig(EntityDemonPig entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderPig((EntityDemonPig)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderPig((EntityDemonPig)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return field_110887_f;
	}

}