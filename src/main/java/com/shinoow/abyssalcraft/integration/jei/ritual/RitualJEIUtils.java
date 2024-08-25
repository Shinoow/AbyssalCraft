package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.*;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RitualJEIUtils {

	private RitualJEIUtils() {
		
	}
	
	private static Map<Integer, String> dimToString = Maps.newHashMap();
	
	public static void init() {
		dimToString.put(OreDictionary.WILDCARD_VALUE, I18n.format(NecronomiconText.LABEL_ANYWHERE, new Object[0]));
		dimToString.putAll(DimensionDataRegistry.instance().getDimensionNameMappings());

	}
	
	public static String getDimension(int dim){
		if(!dimToString.containsKey(dim))
			dimToString.put(dim, "DIM"+dim);
		return dimToString.get(dim);
	}

	public static ItemStack getItem(int par1){
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

	public static boolean list(Object obj){
		return obj == null ? false : obj instanceof ItemStack[] || obj instanceof String || obj instanceof List;
	}

	public static List<ItemStack> getList(Object obj){
		if(obj instanceof ItemStack[])
			return Arrays.asList((ItemStack[])obj);
		if(obj instanceof String)
			return OreDictionary.getOres((String)obj);
		if(obj instanceof List)
			return (List)obj;
		return Collections.emptyList();
	}
	
	public static List<ItemStack> parseAsList(Object obj){
		return list(obj) ? getList(obj) : Collections.singletonList(APIUtils.convertToStack(obj));
	}
}
