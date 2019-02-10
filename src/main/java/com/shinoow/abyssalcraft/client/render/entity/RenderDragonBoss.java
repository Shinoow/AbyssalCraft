/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelDragonBoss;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerAsorahDeath;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerAsorahEyes;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDragonBoss extends RenderLiving<EntityDragonBoss> {

	/** Scale of the model to use */
	private float scale = 1.5F;

	private static final ResourceLocation field_110842_f = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss_exploding.png");
	private static final ResourceLocation field_110843_g = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
	private static final ResourceLocation field_110844_k = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss.png");

	/** An instance of the dragon model in RenderDragon */
	protected ModelDragonBoss modelDragon;

	public RenderDragonBoss(RenderManager manager)
	{
		super(manager, new ModelDragonBoss(0.0F), 0.9F);
		modelDragon = (ModelDragonBoss)mainModel;
		addLayer(new LayerAsorahEyes(this));
		addLayer(new LayerAsorahDeath());
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityDragonBoss par1EntityDragonMinion, float par2)
	{
		GlStateManager.scale(scale, scale, scale);
	}

	/**
	 * Used to rotate the dragon as a whole in RenderDragon. It's called in the rotateCorpse method.
	 */
	@Override
	protected void applyRotations(EntityDragonBoss par1entitydragonboss, float par2, float par3, float par4)
	{
		float f3 = (float)par1entitydragonboss.getMovementOffsets(7, par4)[0];
		float f4 = (float)(par1entitydragonboss.getMovementOffsets(5, par4)[1] - par1entitydragonboss.getMovementOffsets(10, par4)[1]);
		GlStateManager.rotate(-f3, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0.0F, 1.0F);

		if (par1entitydragonboss.deathTime > 0)
		{
			float f5 = (par1entitydragonboss.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
			f5 = MathHelper.sqrt(f5);

			if (f5 > 1.0F)
				f5 = 1.0F;

			GlStateManager.rotate(f5 * getDeathMaxRotation(par1entitydragonboss), 0.0F, 0.0F, 1.0F);
		}
	}

	/**
	 * Renders the dragon model. Called by renderModel.
	 */
	@Override
	protected void renderModel(EntityDragonBoss par1EntityDragonBoss, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		if (par1EntityDragonBoss.deathTicks > 0)
		{
			float f6 = par1EntityDragonBoss.deathTicks / 200.0F;
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GlStateManager.enableAlpha();
			GL11.glAlphaFunc(GL11.GL_GREATER, f6);
			bindTexture(field_110842_f);
			mainModel.render(par1EntityDragonBoss, par2, par3, par4, par5, par6, par7);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDepthFunc(GL11.GL_EQUAL);
		}

		bindEntityTexture(par1EntityDragonBoss);
		mainModel.render(par1EntityDragonBoss, par2, par3, par4, par5, par6, par7);

		if (par1EntityDragonBoss.hurtTime > 0)
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
			mainModel.render(par1EntityDragonBoss, par2, par3, par4, par5, par6, par7);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}

	/**
	 * Renders the dragon, along with its dying animation
	 */
	@Override
	public void doRender(EntityDragonBoss dragon, double par2, double par4, double par6, float par8, float par9)
	{
		//		BossStatus.setBossStatus(dragon, false);
		super.doRender(dragon, par2, par4, par6, par8, par9);

		if (dragon.healingcircle != null)
		{
			float f = EntityDragonMinion.innerRotation + par9;
			float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
			f1 = (f1 * f1 + f1) * 0.2F;
			float f2 = (float)(dragon.healingcircle.posX - dragon.posX - (dragon.prevPosX - dragon.posX) * (1.0F - par9));
			float f3 = (float)(f1 + dragon.healingcircle.posY - 1.0D - dragon.posY - (dragon.prevPosY - dragon.posY) * (1.0F - par9));
			float f4 = (float)(dragon.healingcircle.posZ - dragon.posZ - (dragon.prevPosZ - dragon.posZ) * (1.0F - par9));
			float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
			float f6 = MathHelper.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
			GlStateManager.pushMatrix();
			GlStateManager.translate((float)par2, (float)par4 + 2.0F, (float)par6);
			GlStateManager.rotate((float)-Math.atan2(f4, f2) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate((float)-Math.atan2(f5, f3) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder worldrenderer = tessellator.getBuffer();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableCull();
			bindTexture(field_110842_f);
			GlStateManager.shadeModel(7425);
			float f7 = 0.0F - (dragon.ticksExisted + par9) * 0.01F;
			float f8 = MathHelper.sqrt(f2 * f2 + f3 * f3 + f4 * f4) / 32.0F - (dragon.ticksExisted + par9) * 0.01F;
			worldrenderer.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
			for (int j = 0; j <= 8; ++j)
			{
				float f9 = MathHelper.sin(j % 8 * (float)Math.PI * 2.0F / 8.0F) * 0.75F;
				float f10 = MathHelper.cos(j % 8 * (float)Math.PI * 2.0F / 8.0F) * 0.75F;
				float f11 = j % 8 * 1.0F / 8.0F;
				worldrenderer.pos(f9 * 0.2F, f10 * 0.2F, 0.0D).tex(f11, f8).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(f9, f10, f6).tex(f11, f7).color(255, 255, 255, 255).endVertex();
			}

			tessellator.draw();
			GlStateManager.enableCull();
			GlStateManager.shadeModel(7424);
			RenderHelper.enableStandardItemLighting();
			GlStateManager.popMatrix();
		}
	}

	protected ResourceLocation func_110841_a(EntityDragonBoss par1EntityDragonBoss)
	{
		return field_110844_k;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityDragonBoss par1EntityLivingBase, float par2)
	{
		preRenderScale(par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDragonBoss par1Entity)
	{
		return func_110841_a(par1Entity);
	}
}
