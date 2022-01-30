/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.client.render;

import com.shinoow.abyssalcraft.lib.util.blocks.ISingletonInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TESR used for rendering Items placed on pedestal blocks<br>
 * (the block rendering itself is done through JSON models).
 * @author shinoow
 *
 */
@SideOnly(Side.CLIENT)
public class TileEntityPedestalBlockRenderer extends TileEntitySpecialRenderer {

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

		ISingletonInventory ped = (ISingletonInventory)te;

		if (ped != null && !ped.getItem().isEmpty()){
			GlStateManager.pushMatrix();

			boolean flag = ped.getItem().getItem() instanceof ItemBlock;

			GlStateManager.rotate(180F, 1F, 0F, 0F);
			GlStateManager.translate(0.0F, flag ? -0.56F : -0.37F, 0F);
			GlStateManager.rotate(ped.shouldItemRotate() ? getWorld().getWorldTime() : 0, 0F, 1F, 0F);

			Minecraft.getMinecraft().getRenderItem().renderItem(ped.getItem(), ItemCameraTransforms.TransformType.GROUND);

			GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}
