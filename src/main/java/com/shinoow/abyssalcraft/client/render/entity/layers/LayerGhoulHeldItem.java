/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

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

import com.shinoow.abyssalcraft.client.model.entity.ModelDG;

@SideOnly(Side.CLIENT)
public class LayerGhoulHeldItem implements LayerRenderer<EntityLivingBase> {

	private final RenderLivingBase<?> livingEntityRenderer;

	public LayerGhoulHeldItem(RenderLivingBase<?> livingEntityRendererIn)
	{
		livingEntityRenderer = livingEntityRendererIn;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
		ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();

		if (itemstack != null || itemstack1 != null)
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

	private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide p_188358_4_)
	{
		if (p_188358_2_ != null)
		{
			GlStateManager.pushMatrix();
			((ModelDG)livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, p_188358_4_);
			if(!livingEntityRenderer.getMainModel().isChild){
				GlStateManager.translate(-0.08F, 0.55F, -0.16F);
				GlStateManager.rotate(-45, 1, 0, 0);
			}else{
				GlStateManager.translate(-0.05F, 0.65F, 0F);
				GlStateManager.rotate(-70, 1, 0, 0);
			}

			if (p_188358_1_.isSneaking())
				GlStateManager.translate(0.0F, 0.2F, 0.0F);

			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			boolean flag = p_188358_4_ == EnumHandSide.LEFT;
			GlStateManager.translate(flag ? -0.0625F : 0.0625F, 0.125F, -0.625F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}
}
