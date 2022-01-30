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
package com.shinoow.abyssalcraft.lib.util;

import java.util.List;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpecialTextUtil {

	/**
	 * Send a message as Asorah, The Fallen
	 * <br><b>(This should be called client-side only)</b>
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void AsorahText(String...text){
		customText("Asorah", text);
	}

	/**
	 * Send a message as Cha'garoth, The Dreadbeast
	 * <br><b>(This should be called client-side only)</b>
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void ChagarothText(String...text){
		customText("Cha'garoth", text);
	}

	/**
	 * Send a message as J'zahar, Gatekeeper of The Abyss
	 * <br><b>(This should be called client-side only)</b>
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void JzaharText(String...text){
		customText("J'zahar", text);
	}

	/**
	 * Send a message as Oblivionaire
	 * <br><b>(This should be called client-side only)</b>
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void OblivionaireText(String...text){
		customText("Oblivionaire", text);
	}

	/**
	 * Send a message as Sacthoth, Harbinger of Doom
	 * <br><b>(This should be called client-side only)</b>
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void SacthothText(String...text){
		customText("Sacthoth", text);
	}

	/**
	 * Send a message to a group as Asorah, The Fallen
	 * <br><b>(This should be called server-side only)</b>
	 * @param world Current world
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void AsorahGroup(World world, String...text){
		customGroup(world, "Asorah", text);
	}

	/**
	 * Send a message to a group as Cha'garoth, The Dreadbeast
	 * <br><b>(This should be called server-side only)</b>
	 * @param world Current world
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void ChagarothGroup(World world, String...text){
		customGroup(world, "Cha'garoth", text);
	}

	/**
	 * Send a message to a group as J'zahar, Gatekeeper of The Abyss
	 * <br><b>(This should be called server-side only)</b>
	 * @param world Current world
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void JzaharGroup(World world, String...text){
		customGroup(world, "J'zahar", text);
	}

	/**
	 * Send a message to a group as Oblivionaire
	 * <br><b>(This should be called server-side only)</b>
	 * @param world Current world
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void OblivionaireGroup(World world, String...text){
		customGroup(world, "Oblivionaire", text);
	}

	/**
	 * Send a message to a group as Sacthoth, Harbinger of Doom
	 * <br><b>(This should be called server-side only)</b>
	 * @param world Current world
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void SacthothGroup(World world, String...text){
		customGroup(world, "Sacthoth", text);
	}

	/**
	 * Send a message as someone
	 * <br><b>(This should be called client-side only)</b>
	 * @param name A name
	 * @param text A message (or two, with the second being in aklo)
	 */
	@SideOnly(Side.CLIENT)
	public static void customText(String name, String...text){
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(text.length == 1)
			player.sendMessage(new TextComponentString(name + ": " + text[0]));
		else
			player.sendMessage(new TextComponentString(name + ": " + text[EntityUtil.hasNecronomicon(player) ? 0 : 1]));
	}

	/**
	 * Send a message to a group as someone
	 * <br><b>(This should be called server-side only)</b>
	 * @param world Current world
	 * @param name A name
	 * @param text A message (or two, with the second being in aklo)
	 */
	public static void customGroup(World world, String name, String...text){
		List<EntityPlayer> players = world.playerEntities;
		for(EntityPlayer player : players)
			if(text.length == 1)
				player.sendMessage(new TextComponentString(name + ": " + text[0]));
			else
				player.sendMessage(new TextComponentString(name + ": " + text[EntityUtil.hasNecronomicon(player) ? 0 : 1]));
	}
}
