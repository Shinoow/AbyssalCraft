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
package com.shinoow.abyssalcraft.common.util;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class SpecialTextUtil {

	/**
	 * Send a message as Asorah, The Fallen
	 * @param text A message
	 */
	public static void AsorahText(String text){
		customText("Asorah", text);
	}

	/**
	 * Send a message as Cha'garoth, The Dreadbeast
	 * @param text A message
	 */
	public static void ChagarothText(String text){
		customText("Cha'garoth", text);
	}

	/**
	 * Send a message as J'zahar, Gatekeeper of The Abyss
	 * @param text A message
	 */
	public static void JzaharText(String text){
		customText("J'zahar", text);
	}

	/**
	 * Send a message as Oblivionaire
	 * @param text A message
	 */
	public static void OblivionaireText(String text){
		customText("Oblivionaire", text);
	}

	/**
	 * Send a message as Sacthoth, Harbinger of Doom
	 * @param text A message
	 */
	public static void SacthothText(String text){
		customText("Sacthoth", text);
	}

	/**
	 * Send a message to a group as Asorah, The Fallen
	 * @param world Current world
	 * @param text A message
	 */
	public static void AsorahGroup(World world, String text){
		customGroup(world, "Asorah", text);
	}

	/**
	 * Send a message to a group as Cha'garoth, The Dreadbeast
	 * @param world Current world
	 * @param text A message
	 */
	public static void ChagarothGroup(World world, String text){
		customGroup(world, "Cha'garoth", text);
	}

	/**
	 * Send a message to a group as J'zahar, Gatekeeper of The Abyss
	 * @param world Current world
	 * @param text A message
	 */
	public static void JzaharGroup(World world, String text){
		customGroup(world, "J'zahar", text);
	}

	/**
	 * Send a message to a group as Oblivionaire
	 * @param world Current world
	 * @param text A message
	 */
	public static void OblivionaireGroup(World world, String text){
		customGroup(world, "Oblivionaire", text);
	}

	/**
	 * Send a message to a group as Sacthoth, Harbinger of Doom
	 * @param world Current world
	 * @param text A message
	 */
	public static void SacthothGroup(World world, String text){
		customGroup(world, "Sacthoth", text);
	}

	/**
	 * Send a message as someone
	 * @param name A name
	 * @param text A message
	 */
	public static void customText(String name, String text){
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(name + ": " + text));
	}

	/**
	 * Send a message to a group as someone
	 * @param world Current world
	 * @param name A name
	 * @param text A message
	 */
	public static void customGroup(World world, String name, String text){
		List<EntityPlayer> players = world.playerEntities;
		for(EntityPlayer player : players)
			player.addChatMessage(new ChatComponentText(name + ": " + text));
	}
}