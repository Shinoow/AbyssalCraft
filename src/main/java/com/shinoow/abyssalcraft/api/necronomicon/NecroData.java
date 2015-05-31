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
package com.shinoow.abyssalcraft.api.necronomicon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Base data structure for Necronomicon information pages
 * @author shinoow
 *
 * @since 1.2
 */
public class NecroData {

	private String title;
	private String information;
	private PageData[] pageData;

	/**
	 * The base data structure for Necronomicon information pages
	 * @param title Title to display on the "Index" for the information page
	 * @param info Optional text to write beside buttons for sub-category pages
	 * @param datas Page data for sub-category pages
	 */
	public NecroData(String title, String info,PageData...datas){
		this.title = title;
		pageData = datas;
		information = info;
	}

	/**
	 * The base data structure for Necronomicon information pages
	 * @param title Title to display on the "Index" for the information page
	 * @param datas Page data for sub-category pages
	 */
	public NecroData(String title,PageData...datas){
		this(title, null, datas);
	}

	/**
	 * Getter for the NecroData title
	 * @return A string representing the Title of the information page
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Getter for the PageData array
	 * @return An array of stored PageData
	 */
	public PageData[] getPageData(){
		return pageData;
	}

	/**
	 * Method for getting the title of a specific sub-category
	 * @param index Position in the index to check
	 * @return A string representing the PageData title
	 */
	public String getPageTitle(int index){
		return pageData[index].title;
	}

	/**
	 * Method for getting the icon/picture array of a sub-category page
	 * @param index Position of the index to check
	 * @return An array of Objects representing the page icons/pictures, if any
	 */
	public Object[] getPageIcons(int index){
		switch(pageData[index].type){
		case ENTRY:
			return pageData[index].icons;
		case INFO:
			return pageData[index].pictures;
		case CRAFTING:
			return pageData[index].recipes;
		default:
			return null;
		}
	}

	/**
	 * Getter for the category information
	 * @return A specified string used to display on the information page
	 */
	public String getInformation(){
		return information;
	}

	/**
	 * Method for checking the total amount of pages, if needed
	 * @param datas Array of PageDatas to check
	 * @return Total amount of pages in the array (likely located in a NecroData instance)
	 */
	public static int getTotalPageAmount(PageData...datas){
		int length = 0;
		for(int i = 0; i < datas.length; i++)
			length += datas[i].pageNumber;
		return length;
	}

	/**
	 * Method for checking the amount of pages
	 * @param index Which index of the PageData array to check
	 * @param datas The PageData array to be checked
	 * @return Amount of pages on the selected index
	 */
	public static int getPageAmount(int index, PageData...datas){
		return datas[index].pageNumber;
	}

	@Override
	public String toString(){
		return "NecroData{Title: "+title + ",Information: "+(information != null ? "Yes" : "No") +",PageData: "+pageData.toString() +"}";
	}

	public static class PageData{
		private String[] pages;
		private int pageNumber;
		private String title;
		private ItemStack[] icons;
		private CraftingStack[] recipes;
		private ResourceLocation[] pictures;
		private PageType type;

		public enum PageType {
			NORMAL, ENTRY, INFO, CRAFTING
		}

		/**
		 * Page data for the NecroData structure
		 * @param num Amount of turn-ups for the sub-category
		 * @param title A title to display on the top of the page
		 * @param strings Text to be written on the pages (each string will represent one of
		 * the two open pages, leave a empty string for a empty page)
		 */
		public PageData(int num, String title, String...strings){
			this(num, title, PageType.NORMAL, null, strings);
		}

		/**
		 * Page data for the NecroData structure
		 * @param num Amount of turn-ups for the sub-category
		 * @param title A title to display on the top of the page
		 * @param pagetype A PageType, mainly used for the Object array
		 * @param stuff An array of Objects used as a icons/pictures for the page (has to be the same amount
		 * as the turn-up amount, can contain null elements if you don't want a icon/picture on a certain page)
		 * @param strings Text to be written on the pages (each string will represent one of
		 * the two open pages, leave a empty string for a empty page)
		 */
		public PageData(int num, String title, PageType pagetype, Object[] stuff, String...strings){
			pageNumber = num;
			this.title = title;
			type = pagetype;
			if(stuff != null)
				if(stuff.length == num){
					if(type.equals(PageType.ENTRY))
						icons = (ItemStack[])stuff;
					else if(type.equals(PageType.INFO))
						pictures = (ResourceLocation[])stuff;
					else if(type.equals(PageType.CRAFTING))
						recipes = (CraftingStack[])stuff;
				} else throw new IndexOutOfBoundsException("Not enough elements in the Object array! ("+num+" turn-up(s), "+icons.length+" Objects");
			if(strings.length/2 <= num)
				pages = strings;
			else throw new IndexOutOfBoundsException("Not enough pages to write on! ("+num+" turn-ups, "+strings.length+" pages)");
		}

		/**
		 * Getter for the stored strings representing pages
		 * @return An array of strings representing pages
		 */
		public String[] getPages(){
			return pages;
		}

		/**
		 * Getter for the turn-up amount
		 * @return The amount of available turn-ups (they have 2 pages each)
		 */
		public int getPageAmount(){
			return pageNumber;
		}

		/**
		 * Getter for the PageData title
		 * @return A string representing the title of the sub-category
		 */
		public String getTitle(){
			return title;
		}

		/**
		 * Getter for the page icon/picture
		 * @param index The specified index of page icons/pictures
		 * @return A Object representing a specific page icon/picture
		 */
		public Object getIcon(int index){
			switch(type){
			case ENTRY:
				return icons[index];
			case INFO:
				return pictures[index];
			case CRAFTING:
				return recipes[index];
			default:
				return null;
			}
		}

		/**
		 * Getter for all of the page icons/pictures
		 * @return An array of Object representing page icons/pictures
		 */
		public Object[] getIcons(){
			switch(type){
			case ENTRY:
				return icons;
			case INFO:
				return pictures;
			case CRAFTING:
				return recipes;
			default:
				return null;
			}
		}

		/**
		 * Getter for the PageType
		 * @return The PageType assigned to the sub-category
		 */
		public PageType getPageType(){
			return type;
		}

		@Override
		public String toString(){
			return "PageData{PageType: "+ type + ",Pages: "+pageNumber/2 +",Objects:"+getIcons().toString() +"}";
		}
	}
}
