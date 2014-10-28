package com.shinoow.abyssalcraft.integration.nei;

import java.awt.Rectangle;
import java.util.*;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;
import codechicken.nei.*;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.shinoow.abyssalcraft.client.gui.GuiCrystallizer;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;
import com.shinoow.abyssalcraft.core.util.recipes.CrystallizerRecipes;

public class CrystallizerRecipeHandler extends TemplateRecipeHandler
{
	public class CrystallizationPair extends CachedRecipe
	{
		public CrystallizationPair(ItemStack ingred, ItemStack[] itemStacks) {
			ingred.stackSize = 1;
			this.ingred = new PositionedStack(ingred, 51, 6);
			result = new PositionedStack(itemStacks[0], 111, 24);
			if(itemStacks[1] != null)
				result2 = new PositionedStack(itemStacks[1], 128, 24);
			resultList.add(result);
			if(result2 != null)
				resultList.add(result2);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
		}

		@Override
		public PositionedStack getResult() {
			while(resultList.iterator().hasNext())
				return resultList.iterator().next();
			return resultList.iterator().next();
		}

		@Override
		public PositionedStack getOtherStack() {
			return afuels.get(cycleticks / 48 % afuels.size()).stack;
		}

		PositionedStack ingred;
		PositionedStack result;
		PositionedStack result2;
		private List<PositionedStack> resultList = new ArrayList<PositionedStack>();
	}

	public static class FuelPair
	{
		public FuelPair(ItemStack ingred, int burnTime) {
			stack = new PositionedStack(ingred, 51, 42, false);
			this.burnTime = burnTime;
		}

		public PositionedStack stack;
		public int burnTime;
	}

	public static ArrayList<FuelPair> afuels;
	public static HashSet<Block> efuels;

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "crystallization"));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCrystallizer.class;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("container.abyssalcraft.crystallizer.nei");
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		if (afuels == null)
			findFuels();
		return super.newInstance();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("crystallization") && getClass() == CrystallizerRecipeHandler.class) {//don't want subclasses getting a hold of this
			Map<ItemStack, ItemStack[]> recipes = CrystallizerRecipes.crystallization().getCrystallizationList();
			for (Entry<ItemStack, ItemStack[]> recipe : recipes.entrySet())
				arecipes.add(new CrystallizationPair(recipe.getKey(), new ItemStack[] {recipe.getValue()[0], recipe.getValue()[1]}));
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<ItemStack, ItemStack[]> recipes = CrystallizerRecipes.crystallization().getCrystallizationList();
		for (Entry<ItemStack, ItemStack[]> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameType(recipe.getValue()[0], result))
				arecipes.add(new CrystallizationPair(recipe.getKey(), new ItemStack[] {recipe.getValue()[0], recipe.getValue()[1]}));
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("fuel") && getClass() == CrystallizerRecipeHandler.class)//don't want subclasses getting a hold of this
			loadCraftingRecipes("crystallization");
		else
			super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<ItemStack, ItemStack[]> recipes = CrystallizerRecipes.crystallization().getCrystallizationList();
		for (Entry<ItemStack, ItemStack[]> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				CrystallizationPair arecipe = new CrystallizationPair(recipe.getKey(), new ItemStack[] {recipe.getValue()[0], recipe.getValue()[1]});
				arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
				arecipes.add(arecipe);
			}
	}

	@Override
	public String getGuiTexture() {
		return "abyssalcraft:textures/gui/container/crystallizer_NEI.png";
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
		drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
	}

	private static Set<Item> excludedFuels() {
		Set<Item> efuels = new HashSet<Item>();
		efuels.add(Item.getItemFromBlock(Blocks.brown_mushroom));
		efuels.add(Item.getItemFromBlock(Blocks.red_mushroom));
		efuels.add(Item.getItemFromBlock(Blocks.standing_sign));
		efuels.add(Item.getItemFromBlock(Blocks.wall_sign));
		efuels.add(Item.getItemFromBlock(Blocks.wooden_door));
		efuels.add(Item.getItemFromBlock(Blocks.trapped_chest));
		return efuels;
	}

	private static void findFuels() {
		afuels = new ArrayList<FuelPair>();
		Set<Item> efuels = excludedFuels();
		for (ItemStack item : ItemList.items)
			if (!efuels.contains(item.getItem())) {
				int burnTime = TileEntityCrystallizer.getCrystallizationTime(item);
				if (burnTime > 0)
					afuels.add(new FuelPair(item.copy(), burnTime));
			}
	}

	@Override
	public String getOverlayIdentifier() {
		return "crystallization";
	}
}
