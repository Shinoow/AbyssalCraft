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

import java.io.File;
import java.io.FileReader;
import java.util.Map.Entry;

import com.google.common.base.Strings;
import com.google.gson.*;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.common.util.ACLogger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Utility class used to convert NecroData to/from Json
 * @author shinoow
 *
 */
public class NecroDataJsonUtil {

	/**
	 * Converts a NecroData instance into a Json Object
	 * @param data NecroData to convert
	 * @return A Json Object
	 */
	public static JsonObject serializeNecroData(NecroData data){
		JsonObject json = new JsonObject();
		json.addProperty("type", "necrodata");
		json.addProperty("version", 1);
		json.addProperty("identifier", data.getIdentifier());
		json.addProperty("title", data.getTitle());
		//		if(!Strings.isNullOrEmpty(data.getInformation())) //TODO clean up
		//			json.addProperty("information", data.getInformation());
		JsonArray chapters = new JsonArray();
		//		for(Chapter c : data.getChapters())
		//			chapters.add(serializeChapter(c));
		json.add("chapters", chapters);
		return json;
	}

	/**
	 * Converts a Json Object into a NecroData instance
	 * @param json Json Object to convert
	 * @return A NecroData instance, or null if something went wrong
	 */
	public static NecroData deserializeNecroData(JsonObject json){
		if(getString(json, "type").equals("necrodata") && getInteger(json, "version") <= 1){
			String identifier = getString(json, "identifier");
			String title = getString(json, "title");
			String information = getString(json, "information");
			JsonArray chapters = getArray(json, "chapters");
			Chapter[] chaps = null;
			if(chapters != null){
				chaps = new Chapter[chapters.size()];
				for(int i = 0; i < chapters.size(); i++){
					JsonElement e = chapters.get(i);
					if(e.isJsonObject()){
						Chapter c = deserializeChapter(e.getAsJsonObject());
						if(c != null)
							chaps[i] = c;
					}
				}
			}
			if(chaps != null){
				int num = 0;
				for(Chapter c : chaps)
					if(c != null) num++;
				if(num < chaps.length){
					Chapter[] newchap = new Chapter[num];
					int newIndex = 0;
					for(int i = 0; i < chaps.length; i++)
						if(chaps[i] != null){
							newchap[newIndex] = chaps[i];
							newIndex++;
						}
					chaps = newchap;
				}
				return new NecroData(identifier, title, 0, information.length() == 0 ? null : information, chaps);
			}
		}
		return null;
	}

	/**
	 * Converts a Chapter into a Json Object
	 * @param chap Chapter to convert
	 * @return A Json Object
	 */
	public static JsonObject serializeChapter(Chapter chap){
		JsonObject json = new JsonObject();
		json.addProperty("type", "chapter");
		json.addProperty("version", 1);
		json.addProperty("identifier", chap.getIdentifier());
		json.addProperty("title", chap.getTitle());
		JsonArray pages = new JsonArray();
		for(Entry<Integer, Page> e : chap.getPages().entrySet())
			pages.add(serializePage(e.getValue()));
		json.add("pages", pages);
		return json;
	}

	/**
	 * Converts a Json Object into a Chapter
	 * @param json Json Object to convert
	 * @return A Chapter, or null if the Json Object wasn't a Chapter
	 */
	public static Chapter deserializeChapter(JsonObject json){
		if(getString(json, "type").equals("chapter") && getInteger(json, "version") <= 1){
			String identifier = getString(json, "identifier");
			String title = getString(json, "title");
			Chapter chapter = new Chapter(identifier, title, 0);
			JsonArray pages = getArray(json, "pages");
			if(pages != null)
				for(JsonElement e : pages)
					if(e.isJsonObject()){
						Page p = deserializePage(e.getAsJsonObject());
						if(p != null)
							chapter.addPage(p);
					}
			return chapter;
		}
		return null;
	}

	/**
	 * Converts a Page into a Json Object
	 * @param page Page to convert
	 * @return A Json Object
	 */
	public static JsonObject serializePage(Page page){
		JsonObject json = new JsonObject();
		json.addProperty("type", "page");
		json.addProperty("version", 1);
		json.addProperty("number", page.getPageNumber());
		serializeIcon(json, page.getIcon());
		json.addProperty("text", page.getText());
		return json;
	}

	/**
	 * Converts a Json Object into a Page
	 * @param json Json Object to convert
	 * @return A Page, or null if the Json Object wasn't a Page
	 */
	public static Page deserializePage(JsonObject json){
		if(getString(json, "type").equals("page") && getInteger(json, "version") <= 1){
			int num = getInteger(json, "number");
			Object icon = getIcon(json);
			String text = getString(json, "text");
			return new Page(num, "Title", 0, icon, text);
		}
		return null;
	}

	/**
	 * Serialize the display icon of a Page
	 * @param json Json Object to add the data to
	 * @param icon An Object representing the icon
	 */
	private static void serializeIcon(JsonObject json, Object icon){
		if(icon == null) return;
		if(!(icon instanceof ResourceLocation) && !(icon instanceof ItemStack) && !(icon instanceof CraftingStack)) return;
		if(icon instanceof ResourceLocation){
			json.addProperty("icontype", "resourcelocation");
			json.addProperty("icon", icon.toString());
		}
		else if(icon instanceof ItemStack){
			json.addProperty("icontype", "itemstack");
			json.addProperty("icon", stackToString((ItemStack)icon));
		}
		else if(icon instanceof CraftingStack){
			json.addProperty("icontype", "craftingstack");
			JsonArray stuff = new JsonArray();
			stuff.add(new JsonPrimitive(outputStackToString(((CraftingStack) icon).getOutput())));
			for(Object stack : ((CraftingStack) icon).getRecipe())
				stuff.add(new JsonPrimitive(stackToString(APIUtils.convertToStack(stack))));
			json.add("icon", stuff);
		}
		else if(icon instanceof String){
			json.addProperty("icontype", "url");
			json.addProperty("icon", (String)icon);
		}
	}

	/**
	 * Converts an ItemStack to a String
	 * @param stack ItemStack to convert
	 * @return A String representing the contents of the ItemStack (excluding size), or an empty string
	 */
	private static String stackToString(ItemStack stack){
		if(stack == null || stack.getItem() == null) return "";
		return stack.getItem().getRegistryName() + (stack.getItemDamage() > 0 ? ":" + stack.getItemDamage() : "");
	}

	/**
	 * Converts the CraftingStack output ItemStack to a String
	 * @param stack ItemStack to convert
	 * @return A String representing the contents of the ItemStack, or an empty string
	 */
	private static String outputStackToString(ItemStack stack){
		if(stack == null || stack.getItem() == null) return "";
		return stack.getItem().getRegistryName() + ":" + stack.getCount() + (stack.getItemDamage() > 0 ? ":" + stack.getItemDamage() : "");
	}

	/**
	 * Fetches a String from a Json Object (if one is stored)
	 * @param json Json Object
	 * @param id Variable Name
	 * @return A String value, or an empty String
	 */
	private static String getString(JsonObject json, String id){

		if(json == null) return "";

		if(json.has(id) && json.get(id).isJsonPrimitive() && json.get(id).getAsJsonPrimitive().isString())
			return json.get(id).getAsString();
		else return "";
	}

	/**
	 * Fetches a String value from a Json Element
	 * @param json Json Element
	 * @return A String value, or an empty String
	 */
	private static String getStringValue(JsonElement json){

		if(json == null) return "";

		if(json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
			return json.getAsString();
		else return "";
	}

	/**
	 * Fetches an Integer from a Json Object (if one is stored)
	 * @param json Json Object
	 * @param id Variable Name
	 * @return A Integer value, or 0
	 */
	public static int getInteger(JsonObject json, String id){

		if(json == null) return 0;

		if(json.has(id) && json.get(id).isJsonPrimitive() && json.get(id).getAsJsonPrimitive().isNumber())
			return json.get(id).getAsInt();
		else return 0;

	}

	/**
	 * Fetches a Json Array from a Json Object (if one is stored)
	 * @param json Json Object
	 * @param id Variable Name
	 * @return A Json Array, or null
	 */
	private static JsonArray getArray(JsonObject json, String id){

		if(json == null) return null;

		if(json.has(id) && json.get(id).isJsonArray())
			return json.get(id).getAsJsonArray();
		else return null;

	}

	/**
	 * Fetches a display icon from the Json Object representing a Page
	 * @param json Json Object
	 * @return Either the appropriate Object, or null if none was found
	 */
	private static Object getIcon(JsonObject json){
		if(json == null) return null;
		String type = getString(json, "icontype");
		if(type.length() == 0) return null;
		if(type.equals("resourcelocation"))
			return new ResourceLocation(getString(json, "icon"));
		else if(type.equals("itemstack"))
			return getStack(getString(json, "icon"));
		else if(type.equals("craftingstack"))
			return getCStack(getArray(json, "icon"));
		else if(type.equals("url"))
			return getString(json, "icon");
		return null;
	}

	/**
	 * Fetches an ItemStack from a String value
	 * @param str String value
	 * @return An ItemStack, or null if the Item was invalid
	 */
	private static ItemStack getStack(String str){
		if(Strings.isNullOrEmpty(str)) return null;
		String[] stuff = str.split(":");
		Item item = Item.REGISTRY.getObject(new ResourceLocation(stuff[0], stuff[1]));
		if(item != null)
			return new ItemStack(item, 1, stuff.length == 3 ? Integer.valueOf(stuff[2]) : 0);
		return null;
	}

	/**
	 * Fetches the CraftingStack output ItemStack from a String value
	 * @param str String value
	 * @return An ItemStack, or null if the Item was invalid
	 */
	private static ItemStack getOutputStack(String str){
		if(Strings.isNullOrEmpty(str)) return null;
		String[] stuff = str.split(":");
		Item item = Item.REGISTRY.getObject(new ResourceLocation(stuff[0], stuff[1]));
		if(item != null)
			return new ItemStack(item, Integer.valueOf(stuff[2]), stuff.length == 4 ? Integer.valueOf(stuff[3]) : 0);
		return null;
	}

	/**
	 * Creates a CraftingStack based on a Json Array
	 * @param array Json Array to fetch data from
	 * @return A CraftingStack, or null if the output was null
	 */
	private static CraftingStack getCStack(JsonArray array){
		if(array == null) return null;
		ItemStack output = getOutputStack(getStringValue(array.get(0)));
		ItemStack[] recipe = new ItemStack[9];
		for(int i = 1; i < 10; i++)
			recipe[i-1] = getStack(getStringValue(array.get(i)));
		if(output != null)
			return new CraftingStack(output, (Object[])recipe);
		return null;
	}

	/**
	 * Attempts to find a NecroData Json from a file
	 * @param file File to read from
	 * @return A Json Object containing a NecroData instance if it succeeded, otherwise null
	 */
	public static JsonObject readNecroDataJsonFromFile(File file){
		if(!file.exists())
			return null;
		try {
			FileReader fr = new FileReader(file);
			JsonObject json = new Gson().fromJson(fr, JsonObject.class);
			fr.close();
			return json;
		} catch (Exception e) {
			ACLogger.severe("Failed to read JSON file: {}", file.toString());
			e.printStackTrace();
			return null;
		}
	}
}
