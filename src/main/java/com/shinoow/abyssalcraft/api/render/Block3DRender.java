/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.blocks.tile.TileStatueDirectional;

public class Block3DRender implements IItemRenderer {

	TileEntitySpecialRenderer render;
	private TileEntity tile;

	/**
	 * Used to render blocks with special models while holding them
	 * @param render The TESR for the block
	 * @param tile TE for the block
	 */
	public Block3DRender(TileEntitySpecialRenderer render, TileEntity tile) {
		this.render= render;
		this.tile= tile;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)  {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY)
			GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		if (type == IItemRenderer.ItemRenderType.INVENTORY && tile instanceof TileStatueDirectional){
			GL11.glTranslatef(0, -1, 0);
			GL11.glRotatef(180, 0, 1, 0);
		}
		render.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
	}
}
