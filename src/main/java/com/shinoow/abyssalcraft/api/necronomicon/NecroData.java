/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.necronomicon;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Base data structure for Necronomicon information pages
 * @author shinoow
 *
 * @since 1.2
 */
public class NecroData implements INecroData {

	private String identifier;
	private String title;
	private String information;
	private List<INecroData> containedData = new ArrayList<>();
	private IResearchItem condition;
	private int displayIcon;

	public NecroData(String identifier, String title, int displayIcon, String info, IResearchItem condition, INecroData...data){
		this.identifier = identifier;
		this.title = title;
		this.displayIcon = displayIcon;
		information = info;
		this.condition = condition;
		for(INecroData t : data)
			addData(t);
	}

	public NecroData(String identifier, String title, int displayIcon, IResearchItem condition, INecroData...data){
		this(identifier, title, displayIcon, null, condition, data);
	}

	public NecroData(String identifier, String title, int displayIcon, String info, INecroData...data){
		this(identifier, title, displayIcon, info, ResearchItems.DEFAULT, data);
	}

	public NecroData(String identifier, String title, int displayIcon, INecroData...data){
		this(identifier, title, displayIcon, null, ResearchItems.DEFAULT, data);
	}

	/**
	 * Getter for the NecroData title
	 * @return A string representing the Title of the information page
	 */
	@Override
	public String getTitle(){
		return title;
	}

	@Override
	public int getDisplayIcon() {
		return displayIcon;
	}

	/**
	 * Getter for the category information
	 * @return A specified string used to display on the information page
	 */
	@Override
	public String getText() {
		return information;
	}

	@Override
	public boolean hasText(){
		return !StringUtils.isEmpty(information);
	}

	/**
	 * Getter for the NecroData identifier
	 * @return A unique string representing this NecroData
	 */
	@Override
	public String getIdentifier(){
		return identifier;
	}

	@Override
	public NecroData setResearch(IResearchItem research) {
		condition = research;
		return this;
	}

	@Override
	public IResearchItem getResearch() {

		return condition;
	}

	public List<INecroData> getContainedData(){
		return ImmutableList.copyOf(containedData);
	}

	public void addData(INecroData data){
		for(int i = 0; i < containedData.size(); i++)
			if(containedData.get(i).getIdentifier().equals(data.getIdentifier())){
				containedData.set(i, data);
				return;
			}

		if(hasText() ? containedData.size() < 7 : containedData.size() < 14)
			containedData.add(data);
		else AbyssalCraftAPI.logger.log(Level.ERROR, "NecroData instance is already full, can't add more data!");
	}

	public void removeData(String identifier){
		for(INecroData data : containedData)
			if(data.getIdentifier().equals(identifier)){
				containedData.remove(data);
				break;
			}
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof NecroData)) return false;

		NecroData nd = (NecroData)obj;

		boolean b1 = nd.title.equals(title);
		boolean b2 = nd.identifier.equals(identifier);
		boolean b3 = !nd.hasText() && !hasText() || nd.information.equals(information);
		boolean b4 = true;
		for(Object o1 : nd.containedData)
			for(Object o2 : containedData)
				if(!o1.equals(o2))
					b4 = false;

		return b1 && b2 && b3 && b4 && nd.condition.getID().equals(condition.getID());
	}

	/**
	 * A Necronomicon Chapter (collection of pages)
	 * @author shinoow
	 *
	 * @since 1.6
	 */
	public static class Chapter implements INecroData {
		private NavigableMap<Integer, Page> pages = new TreeMap<>((o1, o2) -> (o1 > o2 ? 1 : o1 < o2 ? -1 : 0));
		private String identifier;
		private String title;
		private IResearchItem condition;
		private int displayIcon;

		/**
		 * A Necronomicon Chapter
		 * @param identifier Identifier (used to locate the chapter, should be unique for every NecroData)
		 * @param title Title to display on pages in the Chapter
		 */
		public Chapter(String identifier, String title, int displayIcon, IResearchItem condition){
			this.identifier = identifier;
			this.title = title;
			this.displayIcon = displayIcon;
			this.condition = condition;
		}

		/**
		 * A Necronomicon Chapter
		 * @param identifier Identifier (used to locate the chapter, should be unique for every NecroData)
		 * @param title Title to display on pages in the Chapter
		 */
		public Chapter(String identifier, String title, int displayIcon){
			this(identifier, title, displayIcon, ResearchItems.DEFAULT);
		}

		/**
		 * A Necronomicon Chapter
		 * @param identifier Identifier (used to locate the chapter, should be unique for every NecroData)
		 * @param title Title to display on pages in the Chapter
		 * @param pages an array of Pages (it is optional to do it this way)
		 */
		public Chapter(String identifier, String title, int displayIcon, IResearchItem condition, Page...pages){
			this(identifier, title, displayIcon, condition);
			for(Page page : pages)
				addPage(page);
		}

		public Chapter(String identifier, String title, int displayIcon, Page...pages){
			this(identifier, title, displayIcon, ResearchItems.DEFAULT, pages);
		}

		/**
		 * Getter for the Chapter identifier
		 */
		@Override
		public String getIdentifier(){
			return identifier;
		}

		/**
		 * Getter for the Chapter title
		 */
		@Override
		public String getTitle(){
			return title;
		}

		@Override
		public int getDisplayIcon() {

			return displayIcon;
		}

		@Override
		public String getText() {
			return null;
		}

		@Override
		public boolean hasText() {
			return false;
		}

		@Override
		public Chapter setResearch(IResearchItem research) {
			condition = research;
			return this;
		}

		@Override
		public IResearchItem getResearch() {
			return condition;
		}

		/**
		 * Fetches a unmodifiable Map of all the Pages contained in this Chapter
		 */
		public Map<Integer, Page> getPages(){
			return Collections.unmodifiableMap(pages);
		}

		/**
		 * Getter for the page amount
		 */
		public int getPageAmount(){
			return pages.size();
		}

		/**
		 * Getter for the turn-up amount (last page number evenly divided by 2)
		 */
		public int getTurnupAmount(){
			return pages.lastKey() / 2 + (pages.lastKey() % 2 == 0 ? 0 : 1);
		}

		/**
		 * Adds a page to the Chapter
		 * <br>(use {@link #insertPage(Page)} if you don't wish to override any existing page at the given page number)
		 * @param page Page to add
		 */
		public void addPage(Page page){
			pages.put(page.pageNum, page);
		}

		/**
		 * Removes a Page (if it exists)
		 * @param pageNum Page number
		 */
		public void removePage(int pageNum){
			pages.remove(pageNum);
			NavigableMap<Integer, Page> newPages = new TreeMap<>((o1, o2) -> (o1 > o2 ? 1 : o1 < o2 ? -1 : 0));
			for(Entry<Integer, Page> e : pages.entrySet())
				if(e.getKey() < pageNum)
					newPages.put(e.getKey(), e.getValue());
				else {
					Page page = e.getValue();
					page.pageNum -= 1;
					newPages.put(page.pageNum, page);
				}
			pages = newPages;
		}

		/**
		 * Fetches a page (if it exists)
		 * @param pageNum Page number
		 */
		public Page getPage(int pageNum){
			return pages.get(pageNum);
		}

		/**
		 * Checks if a Page exists
		 * @param pageNum Page number
		 */
		public boolean hasPage(int pageNum){
			return pages.containsKey(pageNum);
		}

		/**
		 * Inserts a Page at its assigned page number, offsetting any existing page at the number
		 * @param page Page to insert
		 */
		public void insertPage(Page page) {
			if(hasPage(page.pageNum)) {
				NavigableMap<Integer, Page> newPages = new TreeMap<>((o1, o2) -> (o1 > o2 ? 1 : o1 < o2 ? -1 : 0));
				for(Entry<Integer, Page> e : pages.entrySet())
					if(e.getKey() < page.pageNum)
						newPages.put(e.getKey(), e.getValue());
					else {
						Page page1 = e.getValue();
						page1.pageNum += 1;
						newPages.put(page1.pageNum, page1);
					}
				pages = newPages;
			}
			addPage(page);
		}

		/**
		 * Shorthand for adding multiple Pages in one go
		 * <br>(executes {@link #addPage(Page)} on each Page, so recommended use on empty Chapters)
		 * @param pages Pages to add
		 */
		public void addPages(Page...pages) {
			for(Page page : pages)
				addPage(page);
		}

		@Override
		public boolean equals(Object obj){

			if(!(obj instanceof Chapter)) return false;

			Chapter c = (Chapter)obj;

			boolean b1 = c.title.equals(title);
			boolean b2 = c.identifier.equals(identifier);
			boolean b3 = true;
			for(Entry<Integer, Page> e1 : c.pages.entrySet())
				for(Entry<Integer, Page> e2 : pages.entrySet())
					if(e1.getKey() != e2.getKey() || !e1.getValue().equals(e2.getValue()))
						b3 = false;

			return b1 && b2 && b3 && c.condition.getID().equals(condition.getID());
		}
	}

	/**
	 * A Necronomicon Page
	 * @author shinoow
	 *
	 * @since 1.6
	 */
	public static class Page implements INecroData {
		private Object icon;
		private String title;
		private int pageNum;
		private String text;
		private IResearchItem condition;
		private int displayIcon;
		private Chapter reference;

		/**
		 * A Necronomicon Page
		 * @param pageNum Page number
		 * @param text Text to display on the Page
		 */
		public Page(int pageNum, String title, int displayIcon, String text){
			this(pageNum, title, displayIcon, null, text, ResearchItems.DEFAULT);
		}

		/**
		 * A Necronomicon Page
		 * @param pageNum Page number
		 * @param text Text to display on the Page
		 * @param condition Condition to determine whether or not this page can be read
		 */
		public Page(int pageNum, String title, int displayIcon, String text, IResearchItem condition){
			this(pageNum, title, displayIcon, null, text, condition);
		}

		/**
		 * A Necronomicon Page
		 * @param pageNum Page number
		 * @param icon ResourceLocation/ItemStack/CraftingStack to display on the Page
		 * @param text Text to display on the Page
		 */
		public Page(int pageNum, String title, int displayIcon, Object icon, String text){
			this(pageNum, title, displayIcon, icon, text, ResearchItems.DEFAULT);
		}

		/**
		 * A Necronomicon Page
		 * @param pageNum Page number
		 * @param icon ResourceLocation/ItemStack/CraftingStack to display on the Page
		 * @param text Text to display on the Page
		 */
		public Page(int pageNum, String title, int displayIcon, Object icon, String text, IResearchItem condition){
			if(pageNum == 0) throw new ArithmeticException("The Page number can't be zero");
			this.pageNum = pageNum;
			this.title = title;
			this.displayIcon = displayIcon;
			if(icon != null)
				if(!(icon instanceof ResourceLocation) && !(icon instanceof ItemStack) && !(icon instanceof CraftingStack) && !(icon instanceof String))
					throw new IllegalArgumentException("Icon isn't a ResourceLocation, ItemStack, CraftingStack or URL String!");
			this.icon = verify(icon);
			this.text = text;
			this.condition = condition;
		}

		private Object verify(Object obj){
			if(obj instanceof String) AbyssalCraftAPI.getInternalNDHandler().verifyImageURL((String)obj);
			if(!(obj instanceof ResourceLocation)) return obj;
			if(FMLCommonHandler.instance().getSide().isServer()) return obj;
			ResourceLocation res = (ResourceLocation)obj;
			if(res.toString().equals("abyssalcraft:textures/gui/necronomicon/missing.png")) return obj;
			try {
				TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream());
			} catch (IOException e) {
				return new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png");
			}
			return res;
		}

		/**
		 * Fetches the page's number (used for ordering and overriding/removing/replacing pages).
		 */
		public int getPageNumber(){
			return pageNum;
		}

		/**
		 * Fetches the ResourceLocation/ItemStack/CraftingStack to display on the page (if any exist).
		 */
		public Object getIcon(){
			return icon;
		}

		@Override
		public String getTitle() {

			return title;
		}

		@Override
		public int getDisplayIcon() {

			return displayIcon;
		}

		@Override
		public boolean hasText() {

			return true;
		}

		@Override
		public String getIdentifier() {

			return "";
		}

		/**
		 * Fetches the text to display on the page.
		 */
		@Override
		public String getText(){
			return text;
		}

		@Override
		public Page setResearch(IResearchItem research) {
			condition = research;
			return this;
		}

		/**
		 * Fetches the unlocking condition (determines if the page can be read)
		 */
		@Override
		public IResearchItem getResearch(){
			return condition;
		}

		/**
		 * Adds a reference to a Page or two (for display if the button is pressed)
		 * <br>(Only supports Pages at the moment, might support Chapters in the future)
		 */
		public Page setReference(Page...pages) {
			Page[] pages1 = new Page[pages.length > 2 ? 2 : pages.length];
			for(int i = 0; i < pages.length; i++) {
				pages1[i] = pages[i].copy(); // clone each page
				pages1[i].pageNum = i+1; //new page number for page clones
			}
			reference = new Chapter("", "necronomicon.reference", displayIcon, pages1);
			return this;
		}

		@Nullable
		public Chapter getReference() {
			return reference;
		}

		@Override
		public boolean equals(Object obj){
			if(!(obj instanceof Page)) return false;

			Page page = (Page)obj;

			boolean b1 = page.title.equals(title);
			boolean b2 = page.pageNum == pageNum;
			boolean b3 = page.icon == null && icon == null || page.icon.equals(icon);
			boolean b4 = page.text.equals(text);
			boolean b5 = page.condition.getID().equals(condition.getID());

			return b1 && b2 && b3 && b4 && b5;
		}

		public Page copy() {
			return new Page(pageNum, title, displayIcon, icon, text, condition);
		}
	}
}
