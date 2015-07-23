/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.thaumcraft;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.ItemWandCasting;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.ACEntities;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.integration.thaumcraft.creativetabs.TabACThaum;
import com.shinoow.abyssalcraft.integration.thaumcraft.items.ItemACThaumcraft;
import com.shinoow.abyssalcraft.integration.thaumcraft.wands.*;

import cpw.mods.fml.common.registry.GameRegistry;

public class ACTC implements IACPlugin {

	public static WandCap abyssalniteCap, coraliumCap, dreadiumCap, ethaxiumCap;
	public static WandRod darklandsRod, coraliumRod, dreadlandsRod, omotholRod;
	public static Item nugget, wandCap, wandCore;
	public static Aspect CORALIUM, DREAD;
	public static ItemStack darkWand, corWand, dreadWand, omotholWand, endWand;

	@Override
	public String getModName(){
		return "Thaumcraft";
	}

	@Override
	public void preInit() {

		if(AbyssalCraft.tcitems){

			//did it like this solely for disabling everything item-related if something toggles it off
			TabACThaum.instance = new TabACThaum();

			nugget = new ItemACThaumcraft("nugget", true, "abyssalnite", "coralium", "dreadium", "ethaxium");
			wandCap = new ItemACThaumcraft("wandcap", true, "abyssalnite", "coralium", "dreadium", "ethaxium");
			wandCore = new ItemACThaumcraft("wandcore", true, "darklands", "coralium", "dreadlands", "omothol");

			GameRegistry.registerItem(nugget, "ingotnugget");
			GameRegistry.registerItem(wandCap, "wandcap");
			GameRegistry.registerItem(wandCore, "wandcore");

			darklandsRod = new WandRodAC("darklands", 50, new ItemStack(wandCore, 1, 0), 1, new DarklandsRodOnUpdate(), new ResourceLocation("abyssalcraft","textures/model/wands/wand_rod_darklands.png"));
			coraliumRod = new WandRodAC("coralium", 75, new ItemStack(wandCore, 1, 1), 3, new CoraliumRodOnUpdate(), new ResourceLocation("abyssalcraft","textures/model/wands/wand_rod_coralium.png"));
			dreadlandsRod = new WandRodAC("dreadlands", 100, new ItemStack(wandCore, 1, 2), 5, new DreadlandsRodOnUpdate(), new ResourceLocation("abyssalcraft","textures/model/wands/wand_rod_dreadlands.png"));
			omotholRod = new WandRodAC("omothol", 0, new ItemStack(wandCore, 1, 3), 7, new OmotholRodOnUpdate(), new ResourceLocation("abyssalcraft","textures/model/wands/wand_rod_omothol.png"));

			abyssalniteCap = new WandCapAC("abyssalnite", 0.95F, new ItemStack(wandCap, 1, 0), 1);
			coraliumCap = new WandCapAC("coralium", 0.9F, new ItemStack(wandCap, 1, 1), 3);
			dreadiumCap = new WandCapAC("dreadium", 0.85F, new ItemStack(wandCap, 1, 2), 5);
			ethaxiumCap = new WandCapAC("ethaxium", 0.8F, new ItemStack(wandCap, 1, 3), 7);

			darkWand = ItemApi.getItem("itemWandCasting", 1000);
			((ItemWandCasting)darkWand.getItem()).setCap(darkWand, WandCap.caps.get("abyssalnite"));
			((ItemWandCasting)darkWand.getItem()).setRod(darkWand, WandRod.rods.get("darklands"));
			((ItemWandCasting) darkWand.getItem()).storeAllVis(darkWand, new AspectList().add(Aspect.AIR, 5000).add(Aspect.EARTH, 5000).add(Aspect.FIRE, 5000).add(Aspect.WATER, 5000).add(Aspect.ORDER, 5000).add(Aspect.ENTROPY, 5000));

			corWand = ItemApi.getItem("itemWandCasting", 1001);
			((ItemWandCasting)corWand.getItem()).setCap(corWand, WandCap.caps.get("coralium"));
			((ItemWandCasting)corWand.getItem()).setRod(corWand, WandRod.rods.get("coralium"));
			((ItemWandCasting) corWand.getItem()).storeAllVis(corWand, new AspectList().add(Aspect.AIR, 7500).add(Aspect.EARTH, 7500).add(Aspect.FIRE, 7500).add(Aspect.WATER, 7500).add(Aspect.ORDER, 7500).add(Aspect.ENTROPY, 7500));

			dreadWand = ItemApi.getItem("itemWandCasting", 1002);
			((ItemWandCasting)dreadWand.getItem()).setCap(dreadWand, WandCap.caps.get("dreadium"));
			((ItemWandCasting)dreadWand.getItem()).setRod(dreadWand, WandRod.rods.get("dreadlands"));
			((ItemWandCasting) dreadWand.getItem()).storeAllVis(dreadWand, new AspectList().add(Aspect.AIR, 10000).add(Aspect.EARTH, 10000).add(Aspect.FIRE, 10000).add(Aspect.WATER, 10000).add(Aspect.ORDER, 10000).add(Aspect.ENTROPY, 10000));

			omotholWand = ItemApi.getItem("itemWandCasting", 1003);
			((ItemWandCasting)omotholWand.getItem()).setCap(omotholWand, WandCap.caps.get("ethaxium"));
			((ItemWandCasting)omotholWand.getItem()).setRod(omotholWand, WandRod.rods.get("omothol"));
			((ItemWandCasting) omotholWand.getItem()).storeAllVis(omotholWand, new AspectList().add(Aspect.AIR, 0).add(Aspect.EARTH, 0).add(Aspect.FIRE, 0).add(Aspect.WATER, 0).add(Aspect.ORDER, 0).add(Aspect.ENTROPY, 0));

			endWand = ItemApi.getItem("itemWandCasting", 1003);
			((ItemWandCasting)endWand.getItem()).setCap(endWand, WandCap.caps.get("void"));
			((ItemWandCasting)endWand.getItem()).setRod(endWand, WandRod.rods.get("omothol"));
			((ItemWandCasting) endWand.getItem()).storeAllVis(endWand, new AspectList().add(Aspect.AIR, 0).add(Aspect.EARTH, 0).add(Aspect.FIRE, 0).add(Aspect.WATER, 0).add(Aspect.ORDER, 0).add(Aspect.ENTROPY, 0));

			TabACThaum.instance.addWands();
			TabACThaum.instance.addAllItemsAndBlocks();

			//registering nuggets to the OreDictionary
			OreDictionary.registerOre("nuggetAbyssalnite", new ItemStack(nugget, 1, 0));
			OreDictionary.registerOre("nuggetLiquifiedCoralium", new ItemStack(nugget, 1, 1));
			OreDictionary.registerOre("nuggetDreadium", new ItemStack(nugget, 1, 2));
			OreDictionary.registerOre("nuggetEthaxium", new ItemStack(nugget, 1, 3));
		}
	}

	@Override
	public void init(){

		//initializing aspects
		CORALIUM = new Aspect("coralos", 0x00FFEE, new Aspect[] {Aspect.POISON, Aspect.WATER}, new ResourceLocation("abyssalcraft", "textures/aspects/coralos.png"), 1);
		DREAD = new Aspect("dreadia", 0xB00000, new Aspect[] {Aspect.POISON, Aspect.FIRE}, new ResourceLocation("abyssalcraft", "textures/aspects/dreadia.png"), 1);

		if(AbyssalCraft.tcitems){

			//crafting
			GameRegistry.addRecipe(new ItemStack(nugget, 9, 0), new Object[] {"#", '#', AbyssalCraft.abyingot});
			GameRegistry.addRecipe(new ItemStack(nugget, 9, 1), new Object[] {"#", '#', AbyssalCraft.Cingot});
			GameRegistry.addRecipe(new ItemStack(nugget, 9, 2), new Object[] {"#", '#', AbyssalCraft.dreadiumingot});
			GameRegistry.addRecipe(new ItemStack(nugget, 9, 3), new Object[] {"#", '#', AbyssalCraft.ethaxiumIngot});
			GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyingot), new Object[] {"###", "###", "###", '#', new ItemStack(nugget, 1, 0)});
			GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cingot), new Object[] {"###", "###", "###", '#', new ItemStack(nugget, 1, 1)});
			GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumingot), new Object[] {"###", "###", "###", '#', new ItemStack(nugget, 1, 2)});
			GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] {"###", "###", "###", '#', new ItemStack(nugget, 1, 3)});

			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCap, 1, 0), new AspectList().add(Aspect.ORDER, 3).add(Aspect.FIRE, 3).add(Aspect.AIR, 3), new Object[] {"###", "# #", '#', new ItemStack(nugget, 1, 0)});
			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCap, 1, 1), new AspectList().add(Aspect.ORDER, 4).add(Aspect.FIRE, 4).add(Aspect.AIR, 4), new Object[] {"###", "# #", '#', new ItemStack(nugget, 1, 1)});
			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCap, 1, 2), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5).add(Aspect.AIR, 5), new Object[] {"###", "# #", '#', new ItemStack(nugget, 1, 2)});
			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCap, 1, 3), new AspectList().add(Aspect.ORDER, 6).add(Aspect.FIRE, 6).add(Aspect.AIR, 6), new Object[] {"###", "# #", '#', new ItemStack(nugget, 1, 3)});
			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCore, 1, 0), new AspectList().add(Aspect.ENTROPY, 5), new Object[] {" # ", "#  ", '#', AbyssalCraft.DLTLog});
			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCore, 1, 1), new AspectList().add(Aspect.ENTROPY, 5), new Object[] {" # ", "#  ", '#', AbyssalCraft.cstone});
			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCore, 1, 2), new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.FIRE, 5), new Object[] {" # ", "#  ", '#', AbyssalCraft.dreadlog});
			ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(wandCore, 1, 3), new AspectList().add(Aspect.ENTROPY, 5), new Object[] {" # ", "#  ", '#', AbyssalCraft.omotholstone});
			ThaumcraftApi.addArcaneCraftingRecipe("", darkWand, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.ORDER, 5), new Object[] {"  #", " % ", "#  ", '#', new ItemStack(wandCap, 1, 0), '%', new ItemStack(wandCore, 1, 0)});
			ThaumcraftApi.addArcaneCraftingRecipe("", dreadWand, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.ORDER, 5), new Object[] {"  #", " % ", "#  ", '#', new ItemStack(wandCap, 1, 2), '%', new ItemStack(wandCore, 1, 2)});

			ThaumcraftApi.addInfusionCraftingRecipe("", corWand, 20, new AspectList().add(Aspect.ENTROPY, 10).add(CORALIUM, 10).add(Aspect.DARKNESS, 10), new ItemStack(wandCore, 1, 1), new ItemStack[]{new ItemStack(wandCap, 1, 1), new ItemStack(wandCap, 1, 1)});
			ThaumcraftApi.addInfusionCraftingRecipe("", omotholWand, 30, new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.SOUL, 10).add(Aspect.DARKNESS, 10), new ItemStack(wandCore, 1, 3), new ItemStack[]{new ItemStack(wandCap, 1, 3), new ItemStack(wandCap, 1, 3)});
			ThaumcraftApi.addInfusionCraftingRecipe("", endWand, 30, new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.ELDRITCH, 10).add(Aspect.DARKNESS, 10), new ItemStack(wandCore, 1, 3), new ItemStack[]{ItemApi.getItem("itemWandCap", 7), ItemApi.getItem("itemWandCap", 7)});
		}

		//Aspects, blocks
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkstone), new AspectList().add(Aspect.EARTH, 2).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkgrass), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkstone_brick), new AspectList().add(Aspect.EARTH, 2).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkstone_cobble), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abyore), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abydreadore), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadore), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(Aspect.DARKNESS, 1).add(DREAD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumore), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.CoraliumInfusedStone), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.EARTH, 1).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.AbyCorOre), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.AbyLCorOre), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.AbyPCorOre), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abystone), new AspectList().add(Aspect.EARTH, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abybrick), new AspectList().add(Aspect.EARTH, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Abybutton), new AspectList().add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.portal), new AspectList().add(Aspect.DARKNESS, 4).add(Aspect.UNDEAD, 1).add(Aspect.TRAVEL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadportal), new AspectList().add(Aspect.DARKNESS, 4).add(DREAD, 1).add(Aspect.TRAVEL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.omotholportal), new AspectList().add(Aspect.DARKNESS, 4).add(Aspect.ELDRITCH, 1).add(Aspect.TRAVEL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadstone), new AspectList().add(Aspect.EARTH, 2).add(DREAD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abydreadstone), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Cwater), new AspectList().add(Aspect.WATER, 3).add(Aspect.POISON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.anticwater), new AspectList().add(Aspect.WATER, 3).add(Aspect.VOID, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxium), new AspectList().add(Aspect.SOUL, 2).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxiumbrick), new int[]{0}, new AspectList().add(Aspect.SOUL, 3).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxiumbrick), new int[]{1}, new AspectList().add(Aspect.SOUL, 3).add(Aspect.ELDRITCH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.omotholstone), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.cstone), new AspectList().add(Aspect.EARTH, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.PSDL), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ENERGY, 2).add(DREAD, 1));

		//Aspects, items
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abyingot), new AspectList().add(Aspect.METAL, 3).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Cingot), new AspectList().add(Aspect.METAL, 3).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadiumingot), new AspectList().add(Aspect.METAL, 3).add(DREAD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coralium), new AspectList().add(Aspect.CRYSTAL, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster2), new AspectList().add(Aspect.CRYSTAL, 4).add(CORALIUM, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster3), new AspectList().add(Aspect.CRYSTAL, 6).add(CORALIUM, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster4), new AspectList().add(Aspect.CRYSTAL, 8).add(CORALIUM, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster5), new AspectList().add(Aspect.CRYSTAL, 10).add(CORALIUM, 5));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster6), new AspectList().add(Aspect.CRYSTAL, 12).add(CORALIUM, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster7), new AspectList().add(Aspect.CRYSTAL, 14).add(CORALIUM, 7));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster8), new AspectList().add(Aspect.CRYSTAL, 16).add(CORALIUM, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumcluster9), new AspectList().add(Aspect.CRYSTAL, 18).add(CORALIUM, 9));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxiumIngot), new AspectList().add(Aspect.METAL, 1).add(Aspect.SOUL, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.cbrick), new AspectList().add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxium_brick), new AspectList().add(Aspect.FIRE, 1).add(Aspect.SOUL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.shadowfragment), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.DARKNESS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.nitre), new AspectList().add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coralium), new AspectList().add(Aspect.CRYSTAL, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Cpearl), new AspectList().add(Aspect.CRYSTAL, 18).add(CORALIUM, 9));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Corflesh), new AspectList().add(Aspect.FLESH, 2).add(Aspect.MAN, 1).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Corbone), new AspectList().add(Aspect.FLESH, 2).add(Aspect.MAN, 1).add(CORALIUM, 1).add(Aspect.DEATH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadfragment), new AspectList().add(Aspect.FLESH, 2).add(Aspect.MAN, 1).add(DREAD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.omotholFlesh), new AspectList().add(Aspect.FLESH, 2).add(Aspect.MAN, 1).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.eldritchScale), new AspectList().add(Aspect.FLESH, 2).add(Aspect.ELDRITCH, 1).add(Aspect.WATER, 1).add(Aspect.ARMOR, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.PSDLfinder), new AspectList().add(Aspect.MAGIC, 2).add(Aspect.SENSES, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.EoA), new AspectList().add(Aspect.SENSES, 3).add(Aspect.DARKNESS, 3).add(CORALIUM, 2));

		//Aspects, crystals
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalIron), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalSulfur), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalCarbon), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalOxygen), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.AIR, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalHydrogen), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalNitrogen), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.COLD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalPhosphorus), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalPotassium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalNitrate), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.COLD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalMethane), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalRedstone), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalAbyssalnite), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalCoralium), new AspectList().add(Aspect.CRYSTAL, 3).add(CORALIUM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalDreadium), new AspectList().add(Aspect.CRYSTAL, 3).add(DREAD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalBlaze), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalTin), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalCopper), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalSilicon), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalMagnesium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalAluminium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalSilica), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalAlumina), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalMagnesia), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalZinc), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));

		//Aspect, entities
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.depths_ghoul), new AspectList().add(Aspect.UNDEAD, 6).add(Aspect.DEATH, 1).add(Aspect.EARTH, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.evil_pig), new AspectList().add(Aspect.BEAST, 2).add(Aspect.EARTH, 2).add(Aspect.FLESH, 2));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.abyssal_zombie), new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.MAN, 1).add(Aspect.EARTH, 2).add(CORALIUM, 1));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.jzahar), new AspectList().add(Aspect.DEATH, 8).add(Aspect.DARKNESS, 10).add(Aspect.ELDRITCH, 15));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.dreadguard), new AspectList().add(Aspect.UNDEAD, 5).add(Aspect.FLESH, 5).add(DREAD, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.spectral_dragon), new AspectList().add(Aspect.SOUL, 6).add(Aspect.AIR, 3).add(Aspect.BEAST, 2));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.asorah), new AspectList().add(Aspect.SOUL, 12).add(Aspect.AIR, 8).add(Aspect.BEAST, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.shadow_creature), new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.BEAST, 1));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.shadow_monster), new AspectList().add(Aspect.DARKNESS, 4).add(Aspect.BEAST, 2));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.dreadling), new AspectList().add(Aspect.UNDEAD, 3).add(DREAD, 3).add(Aspect.FLESH, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.dread_spawn), new AspectList().add(Aspect.UNDEAD, 3).add(DREAD, 3).add(Aspect.FLESH, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.demon_pig), new AspectList().add(Aspect.FLESH, 3).add(Aspect.FIRE, 3).add(Aspect.BEAST, 2));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.skeleton_goliath), new AspectList().add(Aspect.UNDEAD, 6).add(Aspect.DEATH, 1).add(Aspect.EARTH, 2));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.spawn_of_chagaroth), new AspectList().add(Aspect.UNDEAD, 3).add(Aspect.FLESH, 3).add(DREAD, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.fist_of_chagaroth), new AspectList().add(Aspect.UNDEAD, 3).add(Aspect.FLESH, 3).add(DREAD, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.chagaroth), new AspectList().add(Aspect.UNDEAD, 8).add(Aspect.FLESH, 10).add(DREAD, 15));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.shadow_beast), new AspectList().add(Aspect.DARKNESS, 6).add(Aspect.BEAST, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.sacthoth), new AspectList().add(Aspect.DEATH, 10).add(Aspect.DARKNESS, 15).add(Aspect.BEAST, 8));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.remnant), new AspectList().add(Aspect.DEATH, 5).add(Aspect.DARKNESS, 5).add(Aspect.ELDRITCH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.omothol_ghoul), new AspectList().add(Aspect.DEATH, 5).add(Aspect.DARKNESS, 5).add(Aspect.ELDRITCH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.minion_of_the_gatekeeper), new AspectList().add(Aspect.DEATH, 5).add(Aspect.DARKNESS, 5).add(Aspect.ELDRITCH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.greater_dread_spawn), new AspectList().add(Aspect.UNDEAD, 3).add(DREAD, 3).add(Aspect.FLESH, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.lesser_dreadbeast), new AspectList().add(Aspect.UNDEAD, 3).add(DREAD, 3).add(Aspect.FLESH, 3));

		//Infusion enchanting
		ThaumcraftApi.addInfusionEnchantmentRecipe("INFUSIONENCHANTMENT", AbyssalCraft.lightPierce, 10, new AspectList().add(Aspect.LIGHT, 10), new ItemStack[]{new ItemStack(Items.glowstone_dust), new ItemStack(Items.arrow)});
		ThaumcraftApi.addInfusionEnchantmentRecipe("INFUSIONENCHANTMENT", AbyssalCraft.ironWall, 5, new AspectList().add(Aspect.ARMOR, 10).add(Aspect.EARTH, 10), new ItemStack[]{new ItemStack(Blocks.iron_block), new ItemStack(Blocks.cobblestone_wall)});
		ThaumcraftApi.addInfusionEnchantmentRecipe("INFUSIONENCHANTMENT", AbyssalCraft.coraliumE, 20, new AspectList().add(CORALIUM, 10).add(Aspect.ENERGY, 10), new ItemStack[]{new ItemStack(AbyssalCraft.Cingot), new ItemStack(AbyssalCraft.Corflesh)});
		ThaumcraftApi.addInfusionEnchantmentRecipe("INFUSIONENCHANTMENT", AbyssalCraft.dreadE, 20, new AspectList().add(DREAD, 10).add(Aspect.ENERGY, 10), new ItemStack[]{new ItemStack(AbyssalCraft.dreadiumingot), new ItemStack(AbyssalCraft.dreadfragment)});
	}

	@Override
	public void postInit() {

	}

	public static String getMobName(String name){
		return "abyssalcraft." + name;
	}
}
