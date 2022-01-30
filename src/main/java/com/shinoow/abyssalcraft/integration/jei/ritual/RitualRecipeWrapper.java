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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.*;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RitualRecipeWrapper implements IRecipeWrapper {

	private final Object[] offerings;
	private final Object sacrifice;
	private final ItemStack output;
	private final int bookType;
	private final NecronomiconRitual ritual;
	private Map<Integer, String> dimToString = Maps.newHashMap();

	public RitualRecipeWrapper(@Nonnull NecronomiconCreationRitual ritual){
		if(ritual.getOfferings().length < 8){
			offerings = new Object[8];
			for(int i = 0; i < ritual.getOfferings().length; i++)
				offerings[i] = ritual.getOfferings()[i];
		}else offerings = ritual.getOfferings();
		sacrifice = ritual.getSacrifice();
		output = ritual.getItem();
		bookType = ritual.getBookType();
		this.ritual = ritual;
	}

	public Object[] getOfferings(){
		return offerings;
	}

	public Object getSacrifice(){
		return sacrifice;
	}

	public int getBookType(){
		return bookType;
	}

	public List<ItemStack> getOutputs(){
		return Collections.singletonList(output);
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		dimToString.put(OreDictionary.WILDCARD_VALUE, I18n.format(NecronomiconText.LABEL_ANYWHERE, new Object[0]));
		dimToString.putAll(DimensionDataRegistry.instance().getDimensionNameMappings());

		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

		boolean unicode = fr.getUnicodeFlag();
		fr.setUnicodeFlag(true);

		if(ritual.requiresSacrifice())
			fr.drawString(I18n.format(NecronomiconText.LABEL_SACRIFICE, new Object[0]), 93, 124, 0xC40000);
		fr.drawSplitString(I18n.format(NecronomiconText.LABEL_LOCATION, new Object[0]) + ": " + getDimension(ritual.getDimension()), 93, 85, 70, 0);
		fr.drawSplitString(I18n.format(NecronomiconText.LABEL_REQUIRED_ENERGY, new Object[0]) + ": " + ritual.getReqEnergy() + " PE", 93, 108, 70, 0);

		fr.setUnicodeFlag(unicode);
	}

	private String getDimension(int dim){
		if(!dimToString.containsKey(dim))
			dimToString.put(dim, "DIM"+dim);
		return dimToString.get(dim);
	}

	private ItemStack getItem(int par1){
		switch(par1){
		case 0:
			return new ItemStack(ACItems.necronomicon);
		case 1:
			return new ItemStack(ACItems.abyssal_wasteland_necronomicon);
		case 2:
			return new ItemStack(ACItems.dreadlands_necronomicon);
		case 3:
			return new ItemStack(ACItems.omothol_necronomicon);
		case 4:
			return new ItemStack(ACItems.abyssalnomicon);
		default:
			return new ItemStack(ACItems.necronomicon);
		}
	}

	private boolean list(Object obj){
		return obj == null ? false : obj instanceof ItemStack[] || obj instanceof String || obj instanceof List;
	}

	private List<ItemStack> getList(Object obj){
		if(obj instanceof ItemStack[])
			return Arrays.asList((ItemStack[])obj);
		if(obj instanceof String)
			return OreDictionary.getOres((String)obj);
		if(obj instanceof List)
			return (List)obj;
		return Collections.emptyList();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		List<List<ItemStack>> input = new ArrayList<>();

		for(Object obj : offerings)
			input.add(list(obj) ? getList(obj) : Collections.singletonList(APIUtils.convertToStack(obj)));
		input.add(Collections.singletonList(APIUtils.convertToStack(sacrifice)));
		input.add(Collections.singletonList(getItem(bookType)));
		ingredients.setInputLists(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}
}
