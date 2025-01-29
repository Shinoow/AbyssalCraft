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
package com.shinoow.abyssalcraft.client.gui;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.common.inventory.ContainerSpellbook;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiSpellbook extends GuiContainer {

	private static final ResourceLocation iconLocation = new ResourceLocation("abyssalcraft:textures/gui/container/spellcraft_test.png");

	private ContainerSpellbook spellbook;

	public GuiSpellbook(ContainerSpellbook spellbook) {
		super(spellbook);
		this.spellbook = spellbook;
		ySize = 238;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		IEnergyContainerItem container = (IEnergyContainerItem)spellbook.book.getItem();
		String s = String.format("%d/%d PE", (int)container.getContainedEnergy(spellbook.book), container.getMaxEnergy(spellbook.book));
		fontRenderer.drawString(s, 15, 120, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory"), 6, ySize - 92, 4210752);

		Spell spell = spellbook.currentSpell;
		String s0 = I18n.format(NecronomiconText.LABEL_SPELL_NAME)+": ";
		String s1 = "";
		String s2 = I18n.format(NecronomiconText.LABEL_SPELL_PE)+": ";
		String s3 = "";
		String s4 = I18n.format(NecronomiconText.LABEL_SPELL_TYPE)+": ";
		String s5 = "";

		if(spell != null && spellbook.isUnlocked(spell)) {
			s1 = TextFormatting.AQUA + spell.getLocalizedName();
			s3 = "" + (int)spell.getReqEnergy();
			s5 = TextFormatting.GOLD + (spell.requiresCharging() ? "Charging" : "Instant");
		}

		fontRenderer.drawString(s0 + s1, 15, 15, 4210752);
		fontRenderer.drawString(s2 + s3, 15, 25, 4210752);
		fontRenderer.drawString(s4 + s5, 15, 35, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(iconLocation);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		//		drawTexturedModalRect(k, l + invRows * 18 + 17, 0, 160, xSize, 96);
	}
}
