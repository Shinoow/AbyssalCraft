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
package com.shinoow.abyssalcraft.client.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Mouse;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.FireMessage;

public class AbyssalCraftClientEventHooks {

	@SubscribeEvent
	public void onUpdateFOV(FOVUpdateEvent event) {
		float fov = event.getFov();

		if( event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() == ACItems.coralium_longbow) {
			int duration = event.getEntity().getItemInUseCount();
			float multiplier = duration / 20.0F;

			if( multiplier > 1.0F )
				multiplier = 1.0F;
			else
				multiplier *= multiplier;

			fov *= 1.0F - multiplier * 0.15F;
		}

		event.setNewfov(fov);
	}

	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event)
	{
		event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ACBlocks.chagaroth_altar_bottom, ACBlocks.chagaroth_altar_top,
				ACBlocks.oblivion_deathbomb, ACBlocks.engraver, ACBlocks.cthulhu_statue, ACBlocks.hastur_statue, ACBlocks.jzahar_statue,
				ACBlocks.azathoth_statue, ACBlocks.nyarlathotep_statue, ACBlocks.yog_sothoth_statue, ACBlocks.shub_niggurath_statue);
	}

	@SubscribeEvent
	public void onMouseEvent(MouseEvent event) {
		int button = event.getButton() - 100;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		int key = mc.gameSettings.keyBindAttack.getKeyCode();

		BlockPos pos = mc.objectMouseOver.getBlockPos();
		EnumFacing face = mc.objectMouseOver.sideHit;

		if (button == key && Mouse.isButtonDown(button + 100))
			if (pos != null)
				if (world.getBlockState(pos).getBlock() != null)
					extinguishFire(player, pos, face, world, event);
	}

	private void extinguishFire(EntityPlayer player, BlockPos posIn, EnumFacing face, World world, Event event) {
		BlockPos pos = posIn.offset(face);

		if (world.getBlockState(pos).getBlock() == ACBlocks.mimic_fire ||
				world.getBlockState(pos).getBlock() == ACBlocks.coralium_fire ||
				world.getBlockState(pos).getBlock() == ACBlocks.dreaded_fire ||
				world.getBlockState(pos).getBlock() == ACBlocks.omothol_fire)
			if (event instanceof MouseEvent) {
				PacketDispatcher.sendToServer(new FireMessage(pos));
				PacketDispatcher.sendToAllAround(new FireMessage(pos), player, 30);
				event.setCanceled(true);
			}
	}
}