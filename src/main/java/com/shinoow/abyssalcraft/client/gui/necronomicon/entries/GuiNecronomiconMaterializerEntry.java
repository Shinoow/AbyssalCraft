package com.shinoow.abyssalcraft.client.gui.necronomicon.entries;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.recipe.Materialization;
import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconRecipeBase;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;

public class GuiNecronomiconMaterializerEntry extends GuiNecronomiconRecipeBase<Materialization> {

	public GuiNecronomiconMaterializerEntry(int bookType, GuiNecronomicon parent) {
		super(bookType);
		this.parent = parent;
	}

	@Override
	protected void drawRecipes(int x, int y) {
		drawTitle(localize("container.abyssalcraft.materializer"));

		drawTexture(NecronomiconResources.MATERIALIZATION);
		
		for(int n = 0; n < recipes.size(); n++){
			Materialization m = recipes.get(n);
			if((currTurnup+1)*6 > n && n > currTurnup*6-1)
				drawItem(m, n, currTurnup*6, 0, 0, x, y);
		}
	}

	@Override
	protected void drawItem(Materialization entry, int num, int low, int mid, int high, int x, int y) {
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;

		renderItem(k + 38, 30 + (num-low)*20 + num-low, entry.input[0].copy(), x, y);
		if(entry.input.length >= 2)
			renderItem(k + 59, 30 + (num-low)*20 + num-low, entry.input[1].copy(), x, y);
		if(entry.input.length >= 3)
			renderItem(k + 59 + 21, 30 + (num-low)*20 + num-low, entry.input[2].copy(), x, y);
		if(entry.input.length >= 4)
			renderItem(k + 145, 30 + (num-low)*20 + num-low, entry.input[3].copy(), x, y);
		if(entry.input.length == 5)
			renderItem(k + 166, 30 + (num-low)*20 + num-low, entry.input[4].copy(), x, y);
		renderItem(k + 166 + 44, 30 + (num-low)*20 + num-low, entry.output.copy(), x, y);

		fontRenderer.setUnicodeFlag(unicode);
	}

	@Override
	protected void initStuff() {
		recipes = ImmutableList.copyOf(MaterializerRecipes.instance().getMaterializationList());
		setTurnups(recipes.size());
	}

}
