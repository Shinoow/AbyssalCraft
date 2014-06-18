package com.shinoow.abyssalcraft.update;

import cpw.mods.fml.common.FMLCommonHandler;

public class UpdateProxyServer implements IUpdateProxy {

	@Override
	public void announce(String announcement) {
		FMLCommonHandler.instance().getMinecraftServerInstance().logInfo(announcement);
	}
}