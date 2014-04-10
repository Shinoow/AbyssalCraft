package com.shinoow.abyssalcraft.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxyAbyssalCraft implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	public void registerRenderThings()
	{

	}
	public void preInit()
	{

	}

	public void init()
	{

	}

	public void postInit()
	{

	}

	public int addArmor(String armor)
	{
		return 0;  
	}
}