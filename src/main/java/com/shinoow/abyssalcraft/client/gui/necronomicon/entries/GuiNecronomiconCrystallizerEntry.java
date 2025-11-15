package com.shinoow.abyssalcraft.client.gui.necronomicon.entries;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.recipe.Crystallization;
import com.shinoow.abyssalcraft.api.recipe.CrystallizerRecipes;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconRecipeBase;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;

public class GuiNecronomiconCrystallizerEntry extends GuiNecronomiconRecipeBase<Crystallization> {

	public GuiNecronomiconCrystallizerEntry(int bookType, GuiNecronomicon parent) {
		super(bookType);
		this.parent = parent;
	}

	@Override
	protected void drawRecipes(int x, int y) {
		drawTitle(localize("container.abyssalcraft.crystallizer"));

		drawTexture(NecronomiconResources.TRANSMUTATION);
		drawTexture(NecronomiconResources.CRYSTALLIZATION);
		
		for(int n = 0; n < recipes.size(); n++){
			Crystallization entry = recipes.get(n);
			drawItem(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
		}
	}

	@Override
	protected void drawItem(Crystallization entry, int num, int low, int mid, int high, int x, int y) {
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, 30 + (num-low)*20 + num-low, entry.INPUT, x, y);
			renderItem(k + 62, 30 + (num-low)*20 + num-low, entry.OUTPUT1, x, y);

			renderItem(k + 62 + 33, 30 + (num-low)*20 + num-low, entry.OUTPUT2, x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, 30 + (num-mid)*20 + num-mid, entry.INPUT, x, y);
			renderItem(k + 185, 30 + (num-mid)*20 + num-mid, entry.OUTPUT1, x, y);
			renderItem(k + 185 + 34, 30 + (num-mid)*20 + num-mid, entry.OUTPUT2, x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}

	@Override
	protected void initStuff() {
		recipes = ImmutableList.copyOf(CrystallizerRecipes.instance().getCrystallizationList());
		setTurnups(recipes.size());
	}

}
