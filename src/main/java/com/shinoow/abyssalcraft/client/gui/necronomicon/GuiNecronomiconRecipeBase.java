package com.shinoow.abyssalcraft.client.gui.necronomicon;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.recipe.AnvilForging;

/**
 * Base class for pages displaying recipes
 * (or any collection of items)
 * @author shinoow
 *
 */
public abstract class GuiNecronomiconRecipeBase<T extends Object> extends GuiNecronomicon {

	protected List<T> recipes = new ArrayList<>();

	public GuiNecronomiconRecipeBase(int bookType) {
		super(bookType);
		isInfo = true;
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
		if(getKnowledgeLevel() > par1)
			isInvalid = true;
		return super.withBookType(par1);
	}

	@Override
	public void initGui(){
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		if(recipes.isEmpty())
			initStuff();
		initCommon();
	}

	@Override
	protected void updateButtonsInner() {
		buttonHome.visible = true;
	}

	@Override
	protected void drawInformationText(int x, int y) {

		getHelper().clearTooltipStack();

		drawRecipes(x, y);

		renderTooltip(x, y);
	}

	protected void setTurnups(int size){
		setTurnups(size, false);
	}

	protected void setTurnups(int size, boolean isMat){
		int i = !isMat ? 2 : 1;
		setTurnupLimit((size+6*i)/(6*i));
	}

	protected abstract void drawRecipes(int x, int y);

	protected abstract void drawItem(T entry, int num, int low, int mid, int high, int x, int y);
	
	protected abstract void initStuff();
}
