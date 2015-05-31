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

import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.client.FMLClientHandler;

public class SpecialTextUtil {

	public static void AsorahText(String par1){
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Asorah: " + par1));
	}

	public static void ChagarothText(String par1){
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Cha'garoth: " + par1));
	}

	public static void JzaharText(String par1){
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("J'zahar: " + par1));
	}

	public static void OblivionaireText(String par1){
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Oblivionaire: " + par1));
	}

	public static void SacthothText(String par1){
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Sacthoth: " + par1));
	}
}
