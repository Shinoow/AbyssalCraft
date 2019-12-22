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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.client.model.entity.ModelDG;

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
public class LayerGhoulHeldItem implements LayerRenderer<EntityLivingBase> {

	private final RenderLivingBase<?> livingEntityRenderer;

	public LayerGhoulHeldItem(RenderLivingBase<?> livingEntityRendererIn)
	{
		livingEntityRenderer = livingEntityRendererIn;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
		ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();

		if (!itemstack.isEmpty() || !itemstack1.isEmpty())
		{
			GlStateManager.pushMatrix();

			if (livingEntityRenderer.getMainModel().isChild)
			{
				float f = 0.5F;
				GlStateManager.translate(0.0F, 0.625F, 0.0F);
				GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
				GlStateManager.scale(f, f, f);
			}

			renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
			GlStateManager.popMatrix();
		}
	}

	private void renderHeldItem(EntityLivingBase entity, ItemStack stack, ItemCameraTransforms.TransformType transform, EnumHandSide hand)
	{
		if (!stack.isEmpty())
		{
			GlStateManager.pushMatrix();
			((ModelDG)livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, hand);
			if(!livingEntityRenderer.getMainModel().isChild){
				GlStateManager.translate(-0.08F, 0.55F, -0.16F);
				GlStateManager.rotate(-45, 1, 0, 0);
			}else{
				GlStateManager.translate(-0.05F, 0.65F, 0F);
				GlStateManager.rotate(-70, 1, 0, 0);
			}

			if (entity.isSneaking())
				GlStateManager.translate(0.0F, 0.2F, 0.0F);

			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			boolean flag = hand == EnumHandSide.LEFT;
			GlStateManager.translate(flag ? -0.0625F : 0.0625F, 0.125F, -0.625F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, transform, flag);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}
}
