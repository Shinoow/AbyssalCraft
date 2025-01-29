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

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Simple collection of ItemStacks used for displaying crafting recipes in the Necronomicon
 * @author shinoow
 *
 * @since 1.3.5
 */
public class CraftingStack {

	private ItemStack output;
	private Object[] recipe = new Object[9];

	/**
	 * Simple collection of Objects used to display a recipe
	 * @param output The Item/Block/ItemStack that's crafted
	 * @param recipe The Items/Blocks/ItemStacks used to craft said Item/Block/ItemStack (has to contain 9 elements, all of them can be null)
	 */
	public CraftingStack(Object output, Object...recipe){
		if(output != null){
			if(recipe != null){
				if(recipe.length == 9){
					this.output = APIUtils.convertToStack(output);
					this.recipe = recipe;
				} else throw new ArrayIndexOutOfBoundsException("The array must contain preciesly 9 elements, not "+recipe.length+"!");
			} else throw new NullPointerException("This array can't be empty!");
		} else throw new NullPointerException("Output can't be null!");
	}

	/**
	 * Simple collection of Objects used to display a recipe.<br>
	 * This version looks through the CraftingManager for the<br>
	 * Item/Block's Crafting Recipe.
	 * @param output The Item/Block/ItemStack that's crafted
	 */
	public CraftingStack(Object output){
		if(output != null){
			int size = 0;
			Object[] stuff = new Object[9];
			this.output = APIUtils.convertToStack(output);
			for(IRecipe recipe : ForgeRegistries.RECIPES)
				if(recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(this.output)){
					if(recipe instanceof ShapedRecipes){
						for(int i = 0; i < recipe.getIngredients().size(); i++)
							stuff[i] = ((ShapedRecipes) recipe).getIngredients().get(i);
						size = ((ShapedRecipes) recipe).recipeHeight * ((ShapedRecipes) recipe).recipeWidth;
					} if(recipe instanceof ShapelessRecipes)
						for(int i = 0; i < recipe.getIngredients().size(); i++)
							stuff[i] = ((ShapelessRecipes) recipe).recipeItems.get(i);
					if(recipe instanceof ShapedOreRecipe){
						for(int i = 0; i < recipe.getIngredients().size(); i++)
							stuff[i] = ((ShapedOreRecipe) recipe).getIngredients().get(i);
						size = ((ShapedOreRecipe) recipe).getRecipeHeight() * ((ShapedOreRecipe) recipe).getRecipeWidth();
					} if(recipe instanceof ShapelessOreRecipe)
						for(int i = 0; i < recipe.getIngredients().size(); i++)
							stuff[i] = ((ShapelessOreRecipe) recipe).getIngredients().get(i);

					if(size == 4){
						Object[] copy = stuff.clone();
						stuff = new Object[9];
						for(int i = 0; i < 2; i++){
							stuff[i] = copy[i];
							stuff[i+3] = copy[i+2];
						}
					}
					this.output.setCount(recipe.getRecipeOutput().getCount());
				}
			recipe = stuff;
		}
	}

	/**
	 * Getter for the output ItemStack
	 * @return A ItemStack representing the output
	 */
	public ItemStack getOutput(){
		return output;
	}

	/**
	 * Getter for the Object array containing the recipe
	 * @return An array of Objects representing the crafting recipe
	 */
	public Object[] getRecipe(){
		return recipe;
	}

	@Override
	public String toString(){
		return "CraftingStack{Output: "+ output.toString()+ ", Recipe: "+ recipe.toString() +"}";
	}
}
