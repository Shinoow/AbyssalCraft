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
package com.shinoow.abyssalcraft.common.command;

import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.NecroDataCapMessage;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandUnlockAllKnowledge extends CommandBase {

	@Override
	public String getName() {

		return "acunlockallknowledge";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "/acunlockallknowledge";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if(sender instanceof EntityPlayer && !sender.getEntityWorld().isRemote){
			INecroDataCapability cap = NecroDataCapability.getCap((EntityPlayer) sender);
			if(!cap.hasUnlockedAllKnowledge()){
				cap.unlockAllKnowledge(true);
				sender.sendMessage(new TextComponentString("All knowledge has been unlocked!"));
			} else {
				cap.unlockAllKnowledge(false);
				sender.sendMessage(new TextComponentString("All knowledge has been re-locked... kinda!"));
			}

			cap.setLastSyncTime(System.currentTimeMillis());
			PacketDispatcher.sendTo(new NecroDataCapMessage((EntityPlayer) sender), (EntityPlayerMP)sender);
		}
	}

}
