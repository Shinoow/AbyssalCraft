/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiZombie extends RenderBiped
{
	private static final ResourceLocation zombieTextures = new ResourceLocation("abyssalcraft:textures/model/anti/zombie.png");
	private ModelBiped modelBiped;
	protected ModelBiped field_82437_k;
	protected ModelBiped field_82435_l;

	public RenderAntiZombie()
	{
		super(new ModelZombie(), 0.5F, 1.0F);
		modelBiped = modelBipedMain;
	}

	@Override
	protected void func_82421_b()
	{
		field_82423_g = new ModelZombie(1.0F, true);
		field_82425_h = new ModelZombie(0.5F, true);
		field_82437_k = field_82423_g;
		field_82435_l = field_82425_h;
	}

	protected int shouldRenderPass(EntityAntiZombie par1EntityAntiZombie, int par2, float par3)
	{
		this.renderModel(par1EntityAntiZombie);
		return super.shouldRenderPass(par1EntityAntiZombie, par2, par3);
	}

	public void doRender(EntityAntiZombie par1EntityAntiZombie, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderModel(par1EntityAntiZombie);
		super.doRender(par1EntityAntiZombie, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getEntityTexture(EntityAntiZombie par1EntityAntiZombie)
	{
		return zombieTextures;
	}

	protected void renderEquippedItems(EntityAntiZombie par1EntityAntiZombie, float par2)
	{
		this.renderModel(par1EntityAntiZombie);
		super.renderEquippedItems(par1EntityAntiZombie, par2);
	}

	private void renderModel(EntityAntiZombie par1EntityAntiZombie)
	{


		mainModel = modelBiped;
		field_82423_g = field_82437_k;
		field_82425_h = field_82435_l;


		modelBipedMain = (ModelBiped)mainModel;
	}

	@Override
	protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
	{
		this.renderEquippedItems((EntityAntiZombie)par1EntityLiving, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return this.getEntityTexture((EntityAntiZombie)par1EntityLiving);
	}

	@Override
	public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRender((EntityAntiZombie)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return this.shouldRenderPass((EntityAntiZombie)par1EntityLiving, par2, par3);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.shouldRenderPass((EntityAntiZombie)par1EntityLivingBase, par2, par3);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		this.renderEquippedItems((EntityAntiZombie)par1EntityLivingBase, par2);
	}

	@Override
	public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRender((EntityAntiZombie)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiZombie)par1Entity);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRender((EntityAntiZombie)par1Entity, par2, par4, par6, par8, par9);
	}
}