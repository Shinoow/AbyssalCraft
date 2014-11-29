/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliath;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderSkeletonGoliath extends RenderLiving {

	protected ModelSkeletonGoliath model;

	private float scale = 1.5F;

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/elite/SkeletonGoliath.png");

	public RenderSkeletonGoliath (ModelSkeletonGoliath model, float f)
	{
		super(model, f);
		model = (ModelSkeletonGoliath)mainModel;
	}

	protected void preRenderScale(EntitySkeletonGoliath par1EntitySkeletonGoliath, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}

	public void doRender(EntitySkeletonGoliath entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return mobTexture;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		preRenderScale((EntitySkeletonGoliath)par1EntityLivingBase, par2);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRender((EntitySkeletonGoliath)par1Entity, par2, par4, par6, par8, par9);
	}
}