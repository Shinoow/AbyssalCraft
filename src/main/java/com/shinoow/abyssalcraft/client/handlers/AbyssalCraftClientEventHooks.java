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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Mouse;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.FireMessage;

public class AbyssalCraftClientEventHooks {

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

	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event)
	{
		event.modelManager.getBlockModelShapes().registerBuiltInBlocks(AbyssalCraft.dreadaltarbottom, AbyssalCraft.dreadaltartop, AbyssalCraft.ODB,
				AbyssalCraft.engraver, AbyssalCraft.cthulhuStatue, AbyssalCraft.hasturStatue, AbyssalCraft.jzaharStatue, AbyssalCraft.azathothStatue,
				AbyssalCraft.nyarlathotepStatue, AbyssalCraft.yogsothothStatue, AbyssalCraft.shubniggurathStatue);
	}

	@SubscribeEvent
	public void onMouseEvent(MouseEvent event) {
		int button = event.button - 100;
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

		if (world.getBlockState(pos).getBlock() == AbyssalCraft.mimicFire ||
				world.getBlockState(pos).getBlock() == AbyssalCraft.Coraliumfire ||
				world.getBlockState(pos).getBlock() == AbyssalCraft.dreadfire ||
				world.getBlockState(pos).getBlock() == AbyssalCraft.omotholfire)
			if (event instanceof MouseEvent) {
				PacketDispatcher.sendToServer(new FireMessage(pos));
				event.setCanceled(true);
			}
	}
}