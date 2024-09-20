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
package com.shinoow.abyssalcraft.client.render.block;

import com.shinoow.abyssalcraft.common.blocks.BlockResearchTable;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityResearchTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityResearchTableRenderer extends TileEntitySpecialRenderer<TileEntityResearchTable> {

	private final ItemStack FEATHER = new ItemStack(Items.FEATHER);

	@Override
	public void render(TileEntityResearchTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


		EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockResearchTable.FACING);

		float rotY;

		switch(facing) {
		case EAST:
			rotY = -135F;
			break;
		case NORTH:
			rotY = -45F;
			break;
		case SOUTH:
			rotY = 135F;
			break;
		case WEST:
			rotY = 45F;
			break;
		default:
			rotY = 135F;
			break;

		}

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.4F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

		GlStateManager.pushMatrix();

		GlStateManager.scale(0.8F, 0.8F, 0.8F);
		GlStateManager.rotate(180F, 1F, 0F, 0F);


		GlStateManager.rotate(rotY, 0F, 1F, 0F);
		GlStateManager.rotate(45F, 0F, 0F, 1F);
		GlStateManager.translate(-0.32F, -0.5F, -0.45F);

		Minecraft.getMinecraft().getRenderItem().renderItem(FEATHER, ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}
