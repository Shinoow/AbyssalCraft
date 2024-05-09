/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.model.entity.ModelSacthoth;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSacthothHeldItem implements LayerRenderer<EntityLivingBase> {
	protected final RenderLivingBase<?> livingEntityRenderer;
	private final ItemStack STACK = new ItemStack(ACItems.sacthoths_soul_harvesting_blade);

	public LayerSacthothHeldItem(RenderLivingBase render)
	{
		livingEntityRenderer = render;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isEntityAlive())
		{
			GlStateManager.pushMatrix();
			renderHeldItem(entitylivingbaseIn, STACK, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			GlStateManager.popMatrix();
		}
	}

	private void renderHeldItem(EntityLivingBase entity, ItemStack stack, ItemCameraTransforms.TransformType transform, EnumHandSide handSide)
	{
		if (!stack.isEmpty())
		{
			GlStateManager.pushMatrix();
			((ModelSacthoth)livingEntityRenderer.getMainModel()).leftarm1.postRender(0.0625F);
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.scale(1.2F, 1.2F, 1.2F);
			GlStateManager.translate(-0.125F, 0.325F, 0.825F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, transform, false);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
