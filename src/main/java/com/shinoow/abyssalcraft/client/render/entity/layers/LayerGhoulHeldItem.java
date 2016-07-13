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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.client.model.entity.ModelDG;

@SideOnly(Side.CLIENT)
public class LayerGhoulHeldItem implements LayerRenderer<EntityLivingBase> {

	private final RendererLivingEntity<?> livingEntityRenderer;

	public LayerGhoulHeldItem(RendererLivingEntity<?> livingEntityRendererIn)
	{
		livingEntityRenderer = livingEntityRendererIn;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		ItemStack itemstack = entitylivingbaseIn.getHeldItem();

		if (itemstack != null)
		{
			GlStateManager.pushMatrix();

			if (livingEntityRenderer.getMainModel().isChild)
			{
				float f = 0.5F;
				GlStateManager.translate(0.0F, 0.625F, 0.0F);
				GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
				GlStateManager.scale(f, f, f);
			}

			((ModelDG)livingEntityRenderer.getMainModel()).postRenderArm(0.0625F);
			GlStateManager.translate(-0.13F, 0.95F, -0.38F);
			if(!livingEntityRenderer.getMainModel().isChild)
				GlStateManager.rotate(-45, 1, 0, 0);
			else GlStateManager.rotate(-70, 1, 0, 0);

			Item item = itemstack.getItem();
			Minecraft minecraft = Minecraft.getMinecraft();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2)
			{
				GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
				GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
				float f1 = 0.375F;
				GlStateManager.scale(-f1, -f1, f1);
			}

			if (entitylivingbaseIn.isSneaking())
				GlStateManager.translate(0.0F, 0.203125F, 0.0F);

			minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}
}
