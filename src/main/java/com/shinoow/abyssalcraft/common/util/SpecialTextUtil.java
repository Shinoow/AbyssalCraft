/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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