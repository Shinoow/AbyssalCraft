package com.shinoow.abyssalcraft.client.render.block;

import com.shinoow.abyssalcraft.common.blocks.BlockRitualPedestal;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRitualPedestal;
import com.shinoow.abyssalcraft.common.util.ACLogger;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntityRitualPedestalRenderer extends TileEntitySpecialRenderer<TileEntityRitualPedestal> {

	@Override
	public void render(TileEntityRitualPedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IBlockState state = te.getWorld().getBlockState(te.getPos());

		if(state.getValue(BlockRitualPedestal.TILTED)) {

			BlockRendererDispatcher d = Minecraft.getMinecraft().getBlockRendererDispatcher();

			GlStateManager.pushMatrix();
			GlStateManager.translate((float)x, (float)y, (float)z + 1.0f);

			GlStateManager.pushMatrix();
			GlStateManager.rotate(45f, 0, 1, 0);
			GlStateManager.translate(0.2f, 0, 0.5f);
			d.renderBlockBrightness(te.getBlockType().getDefaultState(), 1);

			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
		}

	}
}
