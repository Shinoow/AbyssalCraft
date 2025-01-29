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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliath;
import com.shinoow.abyssalcraft.client.render.entity.RenderSkeletonGoliath;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSkeletonGoliathHeldItem implements LayerRenderer<EntitySkeletonGoliath> {
	protected final RenderSkeletonGoliath livingEntityRenderer;

	public LayerSkeletonGoliathHeldItem(RenderSkeletonGoliath render)
	{
		livingEntityRenderer = render;
	}

	@Override
	public void doRenderLayer(EntitySkeletonGoliath entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
		ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();


		if (!itemstack.isEmpty() || !itemstack1.isEmpty())
		{
			GlStateManager.pushMatrix();
			renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
			GlStateManager.popMatrix();
		}
	}

	private void renderHeldItem(EntityLivingBase entity, ItemStack stack, ItemCameraTransforms.TransformType transform, EnumHandSide handSide)
	{
		if (!stack.isEmpty())
		{
			GlStateManager.pushMatrix();
			((ModelSkeletonGoliath)livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, handSide);
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
			float scale = 0.9F;
			GlStateManager.scale(scale, scale, scale);
			boolean flag = handSide == EnumHandSide.LEFT;
			GlStateManager.translate(flag ? -0.16F : 0.16F, 0.14F, -1.03F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, transform, flag);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
