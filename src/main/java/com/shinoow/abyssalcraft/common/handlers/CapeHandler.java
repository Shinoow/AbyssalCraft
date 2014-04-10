package com.shinoow.abyssalcraft.common.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.util.EnumCapeGroup;

public class CapeHandler {

	/**
	 * Adds a cape for just a single player
	 * @param username: The username of the player. 
	 * @param url: The url of the cape image.
	 */
	public static void addCape(String username, EnumCapeGroup group)
	{
		String url = "";

		switch(group)
		{
		case DONATOR_10:
			url = "https://raw.github.com/Shinoow/AbyssalCraft/master/capes/Donator_10.png";
			break;
			
		case DONATOR_25:
			url = "https://raw.github.com/Shinoow/AbyssalCraft/master/capes/Donator_25.png";
			break;
			
		case DONATOR_50:
			url = "https://raw.github.com/Shinoow/AbyssalCraft/master/capes/Donator_50.png";
			break;
			
		case DONATOR_100:
			url = "https://raw.github.com/Shinoow/AbyssalCraft/master/capes/Donator_100.png";
			break;
			
		case DEV:
			url = "https://raw.github.com/Shinoow/AbyssalCraft/master/capes/Dev.png";
			break;
			
		case ENF:
			url = "https://raw.github.com/Shinoow/AbyssalCraft/master/capes/Enf_cape.png";
			break;
		}

		ThreadDownloadImageData object = new ThreadDownloadImageData(url, null, null);
		Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/" + username), (ITextureObject) object);
	}

	/**
	 * Adds cape for a group of players from an array.
	 * @param group: Array of users to give the cape to.
	 * @param url: The url of the cape image.
	 */
	public static void addGroupedCape(String[] group, EnumCapeGroup cape)
	{

		for (String username : group) 
		{

			addCape(username, cape);
		}
	}

	/**
	 * Grabs a string array from an online source. 
	 * Each line of file is a new username.
	 * @param url: Link to the text file.
	 * @return: An array from a text file. If link is invalid returns a blank array.
	 */
	public static String[] getArrayFromUrl(String url)
	{

		ArrayList<String> list = new ArrayList<String>();

		try
		{

			BufferedReader urlReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));

			String line;

			while((line = urlReader.readLine()) != null)
			{

				list.add(line);
			}

			return (String[]) list.toArray(new String[list.size()]);
		}

		catch(Exception e) {

			e.printStackTrace();
			list.add("");
			return (String[]) list.toArray(new String[list.size()]);
		}
	}
}
