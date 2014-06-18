package com.shinoow.abyssalcraft.update;

import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.client.FMLClientHandler;

public class UpdateProxyClient implements IUpdateProxy {

	@Override
	public void announce(String announcement) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(announcement));
	}
}