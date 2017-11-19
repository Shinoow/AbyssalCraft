/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.client.model.entity.ModelJzahar;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderJzahar extends RenderLiving {

	protected ModelJzahar model;

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/J'zahar.png");

	public RenderJzahar (ModelJzahar ModelJzahar, float f)
	{
		super(ModelJzahar, f);
		model = (ModelJzahar)mainModel;
	}

	public void doRender(EntityJzahar entity, double par2, double par4, double par6, float par8, float par9)
	{
		BossStatus.setBossStatus(entity, false);
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return mobTexture;
	}

	protected void renderJzaharDying(EntityJzahar par1EntityJzahar, float par2)
	{
		super.renderEquippedItems(par1EntityJzahar, par2);
		Tessellator tessellator = Tessellator.instance;

		if (par1EntityJzahar.deathTicks > 400)
		{
			RenderHelper.disableStandardItemLighting();
			float f1 = ((par1EntityJzahar.deathTicks - 400) + par2) / 400.0F;
			float f2 = 0.0F;

			if (f1 > 0.8F)
				f2 = (f1 - 0.8F) / 0.2F;

			Random random = new Random(432L);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
			GL11.glPushMatrix();
			GL11.glScalef(0.25F, 0.25F, 0.25F);

			for (int i = 0; i < (f1 + f1 * f1) / 2.0F * 30.0F; ++i)
			{
				GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
				tessellator.startDrawing(6);
				float f3 = random.nextFloat() * 20.0F + 5.0F + f2;
				float f4 = random.nextFloat() * 2.0F + 1.0F + f2;
				tessellator.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - f2)));
				tessellator.addVertex(0.0D, 0.0D, 0.0D);
				tessellator.setColorRGBA(81, 189, 178, 0);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.0D, f3, 1.0F * f4);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.draw();
			}

			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			RenderHelper.enableStandardItemLighting();
		}
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRender((EntityJzahar)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		renderJzaharDying((EntityJzahar) par1EntityLivingBase, par2);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		GL11.glScalef(1.5F, 1.5F, 1.5F);
	}
}