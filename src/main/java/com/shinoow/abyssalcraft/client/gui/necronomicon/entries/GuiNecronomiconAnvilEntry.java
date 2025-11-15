package com.shinoow.abyssalcraft.client.gui.necronomicon.entries;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.recipe.AnvilForging;
import com.shinoow.abyssalcraft.api.recipe.AnvilForgingRecipes;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconRecipeBase;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;

public class GuiNecronomiconAnvilEntry extends GuiNecronomiconRecipeBase<AnvilForging> {

	public GuiNecronomiconAnvilEntry(int bookType, GuiNecronomicon parent) {
		super(bookType);
		this.parent = parent;
	}

	@Override
	protected void drawRecipes(int x, int y) {
		drawTitle(localize("tile.anvil.name"));

		drawTexture(NecronomiconResources.TRANSMUTATION, 28);
		drawTexture(NecronomiconResources.CRYSTALLIZATION, -77);

		for(int n = 0; n < recipes.size(); n++){
			AnvilForging entry = recipes.get(n);
			drawItem(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
		}
	}

	protected void drawItem(AnvilForging entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		int offset = 124;
		if(num < mid && num > low-1){
			renderItem(k + 18, 30 + (num-low)*20 + num-low, entry.INPUT1, x, y);
			renderItem(k + 46, 30 + (num-low)*20 + num-low, entry.INPUT2, x, y);
			renderItem(k + 90, 30 + (num-low)*20 + num-low, entry.getOutput(), x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 18 + offset, 30 + (num-mid)*20 + num-mid, entry.INPUT1, x, y);
			renderItem(k + 45 + offset, 30 + (num-mid)*20 + num-mid, entry.INPUT2, x, y);
			renderItem(k + 89 + offset, 30 + (num-mid)*20 + num-mid, entry.getOutput(), x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}

	protected void initStuff() {
		recipes = ImmutableList.copyOf(AnvilForgingRecipes.instance().getForgingList());
		setTurnups(recipes.size());
	}
}
