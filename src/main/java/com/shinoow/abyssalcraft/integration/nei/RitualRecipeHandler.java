///*******************************************************************************
// * AbyssalCraft
// * Copyright (c) 2012 - 2016 Shinoow.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the GNU Lesser Public License v3
// * which accompanies this distribution, and is available at
// * http://www.gnu.org/licenses/lgpl-3.0.txt
// *
// * Contributors:
// *     Shinoow -  implementation
// ******************************************************************************/
//package com.shinoow.abyssalcraft.integration.nei;
//
//import static codechicken.lib.gui.GuiDraw.changeTexture;
//import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
//
//import java.awt.Rectangle;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.FontRenderer;
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.StatCollector;
//import net.minecraftforge.oredict.OreDictionary;
//
//import org.lwjgl.opengl.GL11;
//
//import codechicken.nei.NEIServerUtils;
//import codechicken.nei.PositionedStack;
//import codechicken.nei.recipe.TemplateRecipeHandler;
//
//import com.google.common.collect.Maps;
//import com.shinoow.abyssalcraft.AbyssalCraft;
//import com.shinoow.abyssalcraft.api.ritual.*;
//import com.shinoow.abyssalcraft.client.lib.NecronomiconText;
//
//public class RitualRecipeHandler extends TemplateRecipeHandler {
//
//	public class CachedRitual extends CachedRecipe
//	{
//		public ArrayList<PositionedStack> ingredients;
//		public PositionedStack result;
//		public int xBoost = 15, yBoost = -24;
//		public NecronomiconRitual ritual;
//
//		public CachedRitual(int bookType, Object[] offerings, Object sacrifice, ItemStack output){
//			result = new PositionedStack(output, 58 + xBoost, 139 + yBoost);
//			ingredients = new ArrayList<PositionedStack>();
//			ingredients.add(new PositionedStack(new ItemStack(getItem(bookType)), 0 + xBoost, 133 + yBoost));
//			if(sacrifice != null)
//				ingredients.add(new PositionedStack(getStack(sacrifice), 58 + xBoost, 66 + yBoost));
//			setOfferings(offerings);
//		}
//
//		public CachedRitual(NecronomiconCreationRitual ritual){
//			this(ritual.getBookType(), ritual.getOfferings(), ritual.getSacrifice(), ritual.getItem());
//			this.ritual = ritual;
//		}
//
//
//		public void setOfferings(Object[] offerings){
//			if(offerings.length >= 1){
//				PositionedStack stack = new PositionedStack(getStack(offerings[0]), 58 + xBoost, 30 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			} if(offerings.length >= 2){
//				PositionedStack stack = new PositionedStack(getStack(offerings[1]), 84 + xBoost, 40 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			} if(offerings.length >= 3){
//				PositionedStack stack = new PositionedStack(getStack(offerings[2]), 94 + xBoost, 66 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			} if(offerings.length >= 4){
//				PositionedStack stack = new PositionedStack(getStack(offerings[3]), 84 + xBoost, 92 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			} if(offerings.length >= 5){
//				PositionedStack stack = new PositionedStack(getStack(offerings[4]), 58 + xBoost, 103 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			} if(offerings.length >= 6){
//				PositionedStack stack = new PositionedStack(getStack(offerings[5]), 32 + xBoost, 92 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			} if(offerings.length >= 7){
//				PositionedStack stack = new PositionedStack(getStack(offerings[6]), 22 + xBoost, 66 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			} if(offerings.length == 8){
//				PositionedStack stack = new PositionedStack(getStack(offerings[7]), 32 + xBoost, 40 + yBoost);
//				stack.setMaxSize(1);
//				ingredients.add(stack);
//			}
//		}
//
//		@Override
//		public List<PositionedStack> getIngredients() {
//			return getCycledIngredients(cycleticks / 20, ingredients);
//		}
//
//		@Override
//		public PositionedStack getResult() {
//			return result;
//		}
//
//		public void computeVisuals() {
//			for (PositionedStack p : ingredients)
//				p.generatePermutations();
//		}
//
//		private Item getItem(int par1){
//			switch(par1){
//			case 0:
//				return AbyssalCraft.necronomicon;
//			case 1:
//				return AbyssalCraft.necronomicon_cor;
//			case 2:
//				return AbyssalCraft.necronomicon_dre;
//			case 3:
//				return AbyssalCraft.necronomicon_omt;
//			case 4:
//				return AbyssalCraft.abyssalnomicon;
//			default:
//				return AbyssalCraft.necronomicon;
//			}
//		}
//
//		private Object getStack(Object obj){
//			if(obj instanceof Item)
//				return new ItemStack((Item)obj);
//			if(obj instanceof Block)
//				return new ItemStack((Block)obj);
//			if(obj instanceof ItemStack)
//				return obj;
//			if(obj instanceof ItemStack[])
//				return obj;
//			if(obj instanceof String)
//				return OreDictionary.getOres((String)obj);
//			return obj;
//		}
//	}
//
//	private Map<Integer, String> dimToString = Maps.newHashMap();
//
//	@Override
//	public void loadTransferRects() {
//		transferRects.add(new RecipeTransferRect(new Rectangle(73, 96, 16, 18), "ritual"));
//	}
//
//	@Override
//	public Class<? extends GuiContainer> getGuiClass() {
//		return null;
//	}
//
//	@Override
//	public String getRecipeName() {
//		return StatCollector.translateToLocal("container.abyssalcraft.rituals.nei");
//	}
//
//	@Override
//	public void loadCraftingRecipes(String outputId, Object... results) {
//		if (outputId.equals("ritual") && getClass() == RitualRecipeHandler.class)
//			for(NecronomiconRitual nritual : RitualRegistry.instance().getRituals()){
//				CachedRitual ritual = null;
//				if(nritual instanceof NecronomiconCreationRitual)
//					ritual = new CachedRitual((NecronomiconCreationRitual) nritual);
//				if(ritual == null) continue;
//
//				ritual.computeVisuals();
//				arecipes.add(ritual);
//			}
//		else
//			super.loadCraftingRecipes(outputId, results);
//	}
//
//	@Override
//	public void loadCraftingRecipes(ItemStack result) {
//		for(NecronomiconRitual nritual : RitualRegistry.instance().getRituals())
//			if(nritual instanceof NecronomiconCreationRitual &&
//					NEIServerUtils.areStacksSameTypeCrafting(((NecronomiconCreationRitual) nritual).getItem(), result)) {
//				CachedRitual ritual = null;
//				if(nritual instanceof NecronomiconCreationRitual)
//					ritual = new CachedRitual((NecronomiconCreationRitual) nritual);
//				if(ritual == null) continue;
//
//				ritual.computeVisuals();
//				arecipes.add(ritual);
//			}
//	}
//
//	@Override
//	public void loadUsageRecipes(ItemStack ingredient) {
//		for(NecronomiconRitual nritual : RitualRegistry.instance().getRituals()){
//			CachedRitual ritual = null;
//			if(nritual instanceof NecronomiconCreationRitual)
//				ritual = new CachedRitual((NecronomiconCreationRitual) nritual);
//			if(ritual == null || !ritual.contains(ritual.ingredients, ingredient.getItem())) continue;
//
//			ritual.computeVisuals();
//			if(ritual.contains(ritual.ingredients, ingredient)){
//				ritual.setIngredientPermutation(ritual.ingredients, ingredient);
//				arecipes.add(ritual);
//			}
//		}
//	}
//
//	@Override
//	public String getGuiTexture() {
//		return "abyssalcraft:textures/gui/container/ritual_NEI.png";
//	}
//
//	@Override
//	public void drawBackground(int recipe) {
//		GL11.glColor4f(1, 1, 1, 1);
//		changeTexture(getGuiTexture());
//		drawTexturedModalRect(0, 0, 5, 11, 166, 140);
//	}
//
//	@Override
//	public int recipiesPerPage()
//	{
//		return 1;
//	}
//
//	@Override
//	public void drawForeground( int recipe )
//	{
//		dimToString.put(-1, NecronomiconText.LABEL_ANYWHERE);
//		dimToString.putAll(RitualRegistry.instance().getDimensionNameMappings());
//
//		super.drawForeground(recipe);
//
//		if( arecipes.size() > recipe )
//		{
//			CachedRecipe cr = arecipes.get( recipe );
//			if(cr instanceof CachedRitual){
//				CachedRitual ritual = (CachedRitual) arecipes.get(recipe);
//
//				FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
//
//				boolean unicode = fr.getUnicodeFlag();
//				fr.setUnicodeFlag(true);
//
//				if(ritual.ritual.canRemnantAid())
//					fr.drawString(NecronomiconText.LABEL_REMNANT_HELP, 93, 124, 0xC40000);
//				fr.drawSplitString(NecronomiconText.LABEL_LOCATION + ": " + getDimension(ritual.ritual.getDimension()), 93, 85, 70, 0);
//				fr.drawSplitString(NecronomiconText.LABEL_REQUIRED_ENERGY + ": " + ritual.ritual.getReqEnergy() + " PE", 93, 108, 70, 0);
//
//				fr.setUnicodeFlag(unicode);
//			}
//		}
//	}
//
//	private String getDimension(int dim){
//		if(!dimToString.containsKey(dim))
//			dimToString.put(dim, "DIM"+dim);
//		return dimToString.get(dim);
//	}
//}
