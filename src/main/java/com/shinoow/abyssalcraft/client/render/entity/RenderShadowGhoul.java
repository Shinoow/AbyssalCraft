/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelGhoul;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerEyes;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerGhoulArmor;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerGhoulHeldItem;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityShadowGhoul;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShadowGhoul extends RenderGhoulBase<EntityShadowGhoul> {

	private static final ResourceLocation ghoulResource = new ResourceLocation("abyssalcraft:textures/model/ghoul/shadow_ghoul.png");

	public RenderShadowGhoul(RenderManager manager)
	{
		this(manager, new ModelGhoul());
		addLayer(new LayerEyes<>(this, new ResourceLocation("abyssalcraft", "textures/model/ghoul/shadow_ghoul_eyes.png")).addAlpha(EntityShadowGhoul::getBrightness));
	}

	public RenderShadowGhoul(RenderManager manager, ModelGhoul model){
		super(manager, model, 0.6F);
		addLayer(new LayerGhoulHeldItem(this));
		addLayer(new LayerGhoulArmor(this));
		addLayer(new LayerCustomHead(model.head));
	}

	@Override
	protected void preRenderCallback(EntityShadowGhoul par1EntityLivingBase, float par2)
	{
		GlStateManager.scale(0.7F, 0.7F, 0.7F);
	}

	@Override
	protected void renderModel(EntityShadowGhoul entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, entitylivingbaseIn.getBrightness());
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		actuallyRender(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.disableBlend();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShadowGhoul par1EntityLiving)
	{
		return ghoulResource;
	}
}
