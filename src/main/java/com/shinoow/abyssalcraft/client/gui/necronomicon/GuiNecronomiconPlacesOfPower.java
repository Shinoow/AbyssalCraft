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
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.structure.IPlaceOfPower;
import com.shinoow.abyssalcraft.api.energy.structure.StructureHandler;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonHome;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.client.GuiRenderHelper;
import com.shinoow.abyssalcraft.lib.client.MultiblockRenderData;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

public class GuiNecronomiconPlacesOfPower extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonNextPageLong;
	private ButtonNextPage buttonPreviousPage, buttonPreviousPageLong;
	private GuiButton buttonDone;
	private ButtonHome buttonHome;
	private GuiNecronomicon parent;
	private List<IPlaceOfPower> places = new ArrayList<>();
	private int ticksInBook;
	private boolean showMultiblock = false;
	private MultiblockRenderData multiblockObj = new MultiblockRenderData();
	private IPlaceOfPower tooltipObj;

	public GuiNecronomiconPlacesOfPower(int bookType, GuiNecronomicon gui){
		super(bookType);
		parent = gui;
		isInfo = true;
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
		return super.withBookType(par1);
	}

	@Override
	public void initGui(){
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		currentNecro = this;
		if(places.isEmpty())
			initStuff();
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonNextPageLong = new ButtonNextPage(2, i + 203, b0 + 167, true, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(3, i + 18, b0 + 154, false, false));
		buttonList.add(buttonPreviousPageLong = new ButtonNextPage(4, i + 23, b0 + 167, false, true));
		buttonList.add(buttonHome = new ButtonHome(5, i + 118, b0 + 167));

		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1;
		buttonNextPageLong.visible = currTurnup < getTurnupLimit() -5;
		buttonPreviousPage.visible = true;
		buttonPreviousPageLong.visible = currTurnup > 4;
		buttonDone.visible = true;
		buttonHome.visible = true;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1){
				if(currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if(button.id == 2){
				if(currTurnup < getTurnupLimit() -5)
					currTurnup += 5;
			} else if(button.id == 3){
				if(currTurnup == 0){
					isInfo = false;
					mc.displayGuiScreen(parent.withBookType(getBookType()));
				} else if(currTurnup > 0)
					--currTurnup;
			} else if(button.id == 4){
				if(currTurnup > 4)
					currTurnup -= 5;
			} else if(button.id == 5)
				mc.displayGuiScreen(new GuiNecronomicon(getBookType()));

		updateButtons();
	}

	@Override
	protected void drawInformationText(int x, int y){
		drawPage(places.get(currTurnup), x, y);
	}

	//Start of code borrowed from Patchouli
	//https://github.com/Vazkii/Patchouli/blob/master/src/main/java/vazkii/patchouli/client/book/page/PageMultiblock.java

	@Override
	public void updateScreen()
	{
		if(!isShiftKeyDown())
			ticksInBook++;
		if(ticksInBook % 40 == 0)
			showMultiblock = showMultiblock ? false : true;
		super.updateScreen();
	}

	private void drawPage(IPlaceOfPower place, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String title = localize(NecronomiconText.LABEL_STRUCTURES);
		fontRenderer.drawSplitString(title, k + 17, b0 + 16, 116, 0xC40000);

		tooltipObj = null;

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(NecronomiconResources.PLACE_OF_POWER);
		drawTexturedModalRect(k, b0, 0, 0, 256, 256);

		multiblockObj.calculateData(place.getRenderData());
		writeText(1, localize(NecronomiconText.LABEL_HEIGHT, Integer.valueOf(multiblockObj.sizeY)), 127);
		writeText(1, localize(NecronomiconText.LABEL_WIDTH, Integer.valueOf(multiblockObj.sizeX)), 137);
		writeText(1, localize(NecronomiconText.LABEL_DEPTH, Integer.valueOf(multiblockObj.sizeZ)), 147);
		writeText(2, localize(NecronomiconText.LABEL_RANGE_AMPLIFIER)+": "+place.getAmplifier(AmplifierType.RANGE));
		writeText(2, localize(NecronomiconText.LABEL_DURATION_AMPLIFIER)+": "+place.getAmplifier(AmplifierType.DURATION), 38);
		writeText(2, localize(NecronomiconText.LABEL_POWER_AMPLIFIER)+": "+place.getAmplifier(AmplifierType.POWER), 48);
		writeText(2, place.getDescription(), 64);

		float maxX = 90;
		float maxY = 90;
		float diag = (float) Math.sqrt(multiblockObj.sizeX * multiblockObj.sizeX + multiblockObj.sizeZ * multiblockObj.sizeZ);
		float height = multiblockObj.sizeY;
		float scaleX = maxX / diag;
		float scaleY = maxY / height;
		float scale = -Math.min(scaleX, scaleY);

		int xPos = k + 65;
		int yPos = (int) (73 - (height - 3) * 10/ Math.max(1, height - 3));

		if(x > k + 15 && x < k + 115 && y > 25 && y < 125)
			tooltipObj = place;

		GlStateManager.pushMatrix();
		GlStateManager.translate(xPos, yPos, 100);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(-(float) multiblockObj.sizeX / 2, -(float) multiblockObj.sizeY / 2, 0);

		// Initial eye pos somewhere off in the distance in the -Z direction
		Vector4f eye = new Vector4f(0, 0, -100, 1);
		Matrix4f rotMat = new Matrix4f();
		rotMat.setIdentity();

		// For each GL rotation done, track the opposite to keep the eye pos accurate
		GlStateManager.rotate(-30F, 1F, 0F, 0F);
		rotMat.rotX((float) Math.toRadians(30F));

		float offX = (float) -multiblockObj.sizeX / 2;
		float offZ = (float) -multiblockObj.sizeZ / 2 + 1;

		float time = ticksInBook * 0.5F;
		if(!GuiScreen.isShiftKeyDown())
			time += AbyssalCraftClientEventHooks.partialTicks;
		GlStateManager.translate(-offX, 0, -offZ);
		GlStateManager.rotate(time, 0F, 1F, 0F);
		rotMat.rotY((float) Math.toRadians(-time));
		GlStateManager.rotate(45F, 0F, 1F, 0F);
		rotMat.rotY((float) Math.toRadians(-45F));
		GlStateManager.translate(offX, 0, offZ);

		// Finally apply the rotations
		rotMat.transform(eye);
		renderElements(multiblockObj, BlockPos.getAllInBoxMutable(BlockPos.ORIGIN, new BlockPos(multiblockObj.sizeX - 1, multiblockObj.sizeY - 1, multiblockObj.sizeZ - 1)), eye, place.getActivationPointForRender());

		GlStateManager.popMatrix();

		renderTooltip(x, y);
	}

	private void renderElements(MultiblockRenderData mb, Iterable<? extends BlockPos> blocks, Vector4f eye, BlockPos activationPoint) {

		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(0, 0, -1);

		TileEntityRendererDispatcher.instance.entityX = eye.x;
		TileEntityRendererDispatcher.instance.entityY = eye.y;
		TileEntityRendererDispatcher.instance.entityZ = eye.z;
		TileEntityRendererDispatcher.staticPlayerX = eye.x;
		TileEntityRendererDispatcher.staticPlayerY = eye.y;
		TileEntityRendererDispatcher.staticPlayerZ = eye.z;

		BlockRenderLayer oldRenderLayer = MinecraftForgeClient.getRenderLayer();
		for (BlockRenderLayer layer : BlockRenderLayer.values()) {
			if (layer == BlockRenderLayer.TRANSLUCENT)
				doTileEntityRenderPass(mb, blocks, 0);
			doWorldRenderPass(mb, blocks, layer, eye, activationPoint);
			if (layer == BlockRenderLayer.TRANSLUCENT)
				doTileEntityRenderPass(mb, blocks, 1);
		}
		ForgeHooksClient.setRenderLayer(oldRenderLayer);

		ForgeHooksClient.setRenderPass(-1);
		setGlStateForPass(0);
		mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
		GlStateManager.popMatrix();
	}

	private void doWorldRenderPass(MultiblockRenderData mb, Iterable<? extends BlockPos> blocks, final @Nonnull BlockRenderLayer layer, Vector4f eye, BlockPos activationPoint) {
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);

		ForgeHooksClient.setRenderLayer(layer);
		setGlStateForPass(layer);

		BufferBuilder wr = Tessellator.getInstance().getBuffer();
		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

		for (BlockPos pos : blocks) {
			IBlockState bs = mb.getBlockState(pos);
			Block block = bs.getBlock();
			if(pos.equals(activationPoint)) {
				if (block.canRenderInLayer(bs, layer))
					renderBlock(showMultiblock ? ACBlocks.multi_block.getDefaultState() : bs, pos, mb, Tessellator.getInstance().getBuffer());
			} else if (block.canRenderInLayer(bs, layer))
				renderBlock(bs, pos, mb, Tessellator.getInstance().getBuffer());
		}

		if (layer == BlockRenderLayer.TRANSLUCENT)
			wr.sortVertexData(eye.x, eye.y, eye.z);
		Tessellator.getInstance().draw();
	}

	public void renderBlock(@Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull MultiblockRenderData mb, @Nonnull BufferBuilder worldRendererIn) {

		try {
			BlockRendererDispatcher blockrendererdispatcher = mc.getBlockRendererDispatcher();
			EnumBlockRenderType type = state.getRenderType();
			if (type != EnumBlockRenderType.MODEL) {
				blockrendererdispatcher.renderBlock(state, pos, mb, worldRendererIn);
				return;
			}

			// We only want to change one param here, the check sides
			IBakedModel ibakedmodel = blockrendererdispatcher.getModelForState(state);
			state = state.getBlock().getExtendedState(state, mb, pos);
			blockrendererdispatcher.getBlockModelRenderer().renderModel(mb, ibakedmodel, state, pos, worldRendererIn, false);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Hold errored TEs weakly, this may cause some dupe errors but will prevent spamming it every frame
	private final transient Set<TileEntity> erroredTiles = Collections.newSetFromMap(new WeakHashMap<>());

	private void doTileEntityRenderPass(MultiblockRenderData mb, Iterable<? extends BlockPos> blocks, final int pass) {
		mb.setWorld(mc.world);

		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableLighting();

		ForgeHooksClient.setRenderPass(1);
		setGlStateForPass(1);

		for (BlockPos pos : blocks) {
			TileEntity te = mb.getTileEntity(pos);
			BlockPos relPos = new BlockPos(mc.player);
			if (te != null && !erroredTiles.contains(te) && te.shouldRenderInPass(pass)) {
				te.setWorld(mc.world);
				te.setPos(relPos.add(pos));

				try {
					TileEntityRendererDispatcher.instance.render(te, pos.getX(), pos.getY(), pos.getZ(), AbyssalCraftClientEventHooks.partialTicks);
				} catch (Exception e) {
					erroredTiles.add(te);
					e.printStackTrace();
				}
			}
		}

		ForgeHooksClient.setRenderPass(-1);
		RenderHelper.disableStandardItemLighting();
	}

	private void setGlStateForPass(@Nonnull BlockRenderLayer layer) {
		int pass = layer == BlockRenderLayer.TRANSLUCENT ? 1 : 0;
		setGlStateForPass(pass);
	}

	private void setGlStateForPass(int layer) {
		GlStateManager.color(1, 1, 1);

		if (layer == 0) {
			GlStateManager.enableDepth();
			GlStateManager.disableBlend();
			GlStateManager.depthMask(true);
		} else {
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.depthMask(false);
		}
	}

	//End of code borrowed from Patchouli

	@Override
	public void renderTooltip(int x, int y) {
		if(tooltipObj != null)
		{
			List<String> tooltipData = Arrays.asList(localize(tooltipObj.getRequiredBlockNames()).split(";"));
			List<String> parsedTooltip = tooltipData.stream().map(s -> TextFormatting.GRAY+s).collect(Collectors.toList());
			parsedTooltip.add(0, localize(NecronomiconText.LABEL_BUILT_WITH)+":");
			GuiRenderHelper.renderTooltip(x, y, parsedTooltip);
		}
	}

	private void initStuff(){
		for(IPlaceOfPower place : StructureHandler.instance().getStructures())
			if(isUnlocked(place.getResearchItem()) && place.getBookType() <= getKnowledgeLevel())
				places.add(place);
		setTurnupLimit(places.size());
	}
}
