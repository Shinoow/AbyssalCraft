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
package com.shinoow.abyssalcraft.client.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.client.model.player.ModelStarSpawnPlayer;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AbyssalCraftClientEventHooks {

	private ModelStarSpawnPlayer model = new ModelStarSpawnPlayer();

	Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void renderPlayer(RenderPlayerEvent.SetArmorModel event){

		if(EntityUtil.isPlayerCoralium(event.entityPlayer))
			renderStarSpawnPlayer(event.entityPlayer, event.partialRenderTick);

	}

	private void renderStarSpawnPlayer(EntityPlayer player, float partialTicks){

		mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/model/tentacles.png"));

		for (int j = 0; j < 1; ++j) {
			GL11.glColor4f(1F, 1F, 1F, 1F);
			float f10 = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks - (player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks);
			float f2 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
			GL11.glPushMatrix();
			GL11.glFrontFace(GL11.GL_CW);
			GL11.glRotatef(f10, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0, -0.22F, 0);
			GL11.glScalef(1, 1, 1);
			model.renderTentacles(0.0625F);
			GL11.glFrontFace(GL11.GL_CCW);
			GL11.glPopMatrix();
		}
	}

	@SubscribeEvent
	public void onUpdateFOV(FOVUpdateEvent event) {
		float fov = event.fov;

		if( event.entity.isUsingItem() && event.entity.getItemInUse().getItem() == AbyssalCraft.corbow) {
			int duration = event.entity.getItemInUseDuration();
			float multiplier = duration / 20.0F;

			if( multiplier > 1.0F )
				multiplier = 1.0F;
			else
				multiplier *= multiplier;

			fov *= 1.0F - multiplier * 0.15F;
		}

		event.newfov = fov;
	}
}
