/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.*;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionPotion;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionSpawn;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionSwarm;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.ritual.*;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;
import com.shinoow.abyssalcraft.common.disruptions.*;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.common.entity.EntitySacthoth;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconBreedingRitual;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconDreadSpawnRitual;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconRespawnJzaharRitual;

import cpw.mods.fml.common.registry.GameRegistry;

public class AbyssalCrafting {

	public static void addRecipes()
	{
		addBlockCrafting();
		addBlockSmelting();
		addItemCrafting();
		addItemSmelting();
		addCrystallization();
		addTransmutation();
		addEngraving();
		addMaterialization();
		addRitualRecipes();
		addDisruptions();
	}

	private static void addBlockCrafting(){

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkstone_brick, 4), new Object[] {"AA", "AA", 'A', AbyssalCraft.Darkstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkbrickslab1, 6), new Object[] {"AAA", 'A', AbyssalCraft.Darkstone_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSGlow, 4), new Object[] {"#$#", "&%&", "#&#", '#', AbyssalCraft.Darkstone_brick, '$', Items.diamond,'&', Blocks.obsidian, '%', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkcobbleslab1, 6), new Object[] {"AAA", 'A', AbyssalCraft.Darkstone_cobble });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTplank, 4), new Object[] {"A", 'A', AbyssalCraft.DLTLog });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ODB, 1), new Object[] {"AOO", "TCO", "LOO", 'A', AbyssalCraft.antibucket, 'O', Blocks.obsidian, 'T', AbyssalCraft.OC, 'C', AbyssalCraft.ODBcore, 'L', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ODB, 1), new Object[] {"AOO", "TCO", "LOO", 'L', AbyssalCraft.antibucket, 'O', Blocks.obsidian, 'T', AbyssalCraft.OC, 'C', AbyssalCraft.ODBcore, 'A', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyblock, 1), new Object[] {"AAA", "AAA", "AAA", 'A', AbyssalCraft.abyingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ODBcore, 1), new Object[] {"#&#", "$@$", "#&#", '#', AbyssalCraft.abyingot, '&', Blocks.iron_block, '$', Items.iron_ingot,'@', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&&&", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coraliumcluster4});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster4});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster4, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coraliumcluster4, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster4, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster4, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&&&", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coraliumcluster5});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster5});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster7, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster7, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster7});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster5});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster6});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster6});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CoraliumInfusedStone, 1), new Object[] {"###", "#%#", "###", '#', Blocks.stone, '%', AbyssalCraft.Coraliumcluster9});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abybrick, 4), new Object[] {"##", "##", '#', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyslab1, 6), new Object[] {"###", '#', AbyssalCraft.abybrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyfence, 6), new Object[] {"###", "###", '#', AbyssalCraft.abybrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSCwall, 6), new Object[] {"###", "###", '#', AbyssalCraft.Darkstone_cobble});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Crate, 2), new Object[] {"#&#", "&%&", "#&#", '#', Items.stick, '&', Blocks.planks, '%', Blocks.chest});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSbutton, 1), new Object[] {"#", '#', AbyssalCraft.Darkstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSpplate, 1), new Object[] {"##", '#', AbyssalCraft.Darkstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTbutton, 1), new Object[] {"#", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTpplate, 1), new Object[] {"##", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTslab1, 6), new Object[] {"###", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkstoneslab1,6), new Object[] {"###", '#', AbyssalCraft.Darkstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.corblock, 1), new Object[] {"###", "###", "###", '#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Abybutton, 1), new Object[] {"#", '#', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Abypplate, 1), new Object[] {"##", '#', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSBfence, 6), new Object[] {"###", "###", '#', AbyssalCraft.Darkstone_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTfence, 4), new Object[] {"###", "###", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrick, 4), new Object[] {"##", "##", '#', AbyssalCraft.dreadstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrick, 4), new Object[] {"##", "##", '#', AbyssalCraft.abydreadstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadplanks, 4), new Object[] {"%", '%', AbyssalCraft.dreadlog});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickslab1, 6), new Object[] {"###", '#', AbyssalCraft.dreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickfence, 6), new Object[] {"###", "###", '#', AbyssalCraft.dreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickslab1, 6), new Object[] {"###", '#', AbyssalCraft.abydreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickfence, 6), new Object[] {"###", "###", '#', AbyssalCraft.abydreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrick, 1), new Object[] {"##", "##", '#', AbyssalCraft.cbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickslab1, 6), new Object[] {"###", '#', AbyssalCraft.cstonebrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickfence, 6), new Object[] {"###", "###", '#', AbyssalCraft.cstonebrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebutton, 1), new Object[] {"#", '#', AbyssalCraft.cstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonepplate, 1), new Object[] {"##", '#', AbyssalCraft.cstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumblock, 1), new Object[] {"###", "###", "###", '#', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DrTfence, 4), new Object[] {"###", "###", '#', AbyssalCraft.dreadplanks});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.transmutator, 1), new Object[] {"###", "#%#", "&$&", '#', AbyssalCraft.cbrick, '%', new ItemStack(AbyssalCraft.Corb, 1, OreDictionary.WILDCARD_VALUE), '&', AbyssalCraft.corblock, '$', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystallizer, 1), new Object[] {"###", "&%&", "###", '#', AbyssalCraft.dreadbrick, '&', AbyssalCraft.dreadiumblock, '%', Blocks.furnace});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadaltartop, 1), new Object[] {"#%#", "&&&", "@@@", '#', Items.stick, '%', Items.bucket, '&', AbyssalCraft.dreadcloth, '@', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadaltarbottom, 1), new Object[] {"#%#", "&@&", "T$T", '#', Items.bone, '%', AbyssalCraft.dreadcloth, '&', AbyssalCraft.dreadiumingot, '@', AbyssalCraft.portalPlacerDL, '$', AbyssalCraft.Dreadshard, 'T', AbyssalCraft.dreadstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), new Object [] {"##", "##", '#', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 1), new Object[] {"##", "##", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumpillar, 2), new Object[] {"#%", "#%", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), '%', AbyssalCraft.ethaxium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DBstairs, 4), new Object[] {"#  ", "## ", "###", '#', AbyssalCraft.Darkstone_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DCstairs, 4), new Object[] {"#  ", "## ", "###", '#', AbyssalCraft.Darkstone_cobble});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abystairs, 4), new Object[] {"#  ", "## ", "###", '#', AbyssalCraft.abybrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTstairs, 4), new Object[] {"#  ", "## ", "###", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickstairs, 4), new Object[] {"#  ", "## ", "###", '#', AbyssalCraft.dreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickstairs, 4), new Object[] {"#  ", "## ", "###", '#', AbyssalCraft.abydreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickstairs, 4), new Object[] {"#  ", "## ", "###", '#', AbyssalCraft.cstonebrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumblock, 1), new Object[] {"###", "###", "###", '#', AbyssalCraft.ethaxiumIngot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumstairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumslab1, 6), new Object[] {"AAA", 'A', new ItemStack(AbyssalCraft.ethaxiumbrick, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumfence, 6), new Object[] {"###", "###", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engraver, 1), new Object[] {"#% ", "#%&", "@% ", '#', AbyssalCraft.engravingBlank, '%', Blocks.stone, '&', Blocks.lever, '@', Blocks.anvil});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.house, 1), true, new Object[] {"#%#", "%&%", "%%%", '#', "stairWood", '%', "plankWood", '&', Items.wooden_door})); //Quite frankly, this recipe doesn't exist
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.materializer, 1), new Object[] {"###", "#%#", "&@&", '#', AbyssalCraft.ethaxium_brick, '%', Blocks.obsidian, '&', AbyssalCraft.ethaxiumblock, '@', AbyssalCraft.antibucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0), new Object[] {"#%", "#%", '#', AbyssalCraft.omotholstone, '%', AbyssalCraft.ethaxium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 1), new Object[] {"##", "##", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumpillar, 2), new Object[] {"#%", "#%", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0), '%', AbyssalCraft.omotholstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumstairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumslab1, 6), new Object[] {"AAA", 'A', new ItemStack(AbyssalCraft.darkethaxiumbrick, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumfence, 6), new Object[] {"###", "###", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.energyPedestal), new Object[]{"#%#", "#&#", "###", '#', AbyssalCraft.monolithStone, '%', AbyssalCraft.Cpearl, '&', AbyssalCraft.shadowgem});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.monolithPillar), new Object[]{"##", "##", '#', AbyssalCraft.monolithStone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.sacrificialAltar), new Object[]{"#%#", "&$&", "&&&", '#', Blocks.torch, '%', AbyssalCraft.Cpearl, '$', AbyssalCraft.shadowgem, '&', AbyssalCraft.monolithStone});
	}

	private static void addBlockSmelting(){

		GameRegistry.addSmelting(AbyssalCraft.Darkstone_cobble, new ItemStack(AbyssalCraft.Darkstone, 1), 0.1F);
		AbyssalCraftAPI.addOreSmelting("oreAbyssalnite", "ingotAbyssalnite", 3F);
		AbyssalCraftAPI.addOreSmelting("oreCoralium", "gemCoralium", 3F);
		GameRegistry.addSmelting(AbyssalCraft.DLTLog, new ItemStack(Items.coal, 1, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.CoraliumInfusedStone, new ItemStack(AbyssalCraft.Cpearl), 3F);
		GameRegistry.addSmelting(AbyssalCraft.AbyPCorOre, new ItemStack(AbyssalCraft.Cpearl), 3F);
		GameRegistry.addSmelting(AbyssalCraft.AbyLCorOre, new ItemStack(AbyssalCraft.Cingot), 3F);
		GameRegistry.addSmelting(AbyssalCraft.dreadore, new ItemStack(AbyssalCraft.abyingot, 1), 3F);
		GameRegistry.addSmelting(AbyssalCraft.cstone, new ItemStack(AbyssalCraft.cbrick, 1), 0.1F);
		GameRegistry.addSmelting(AbyssalCraft.nitreOre, new ItemStack(AbyssalCraft.nitre, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyIroOre, new ItemStack(Items.iron_ingot, 1), 0.7F);
		GameRegistry.addSmelting(AbyssalCraft.AbyGolOre, new ItemStack(Items.gold_ingot, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyDiaOre, new ItemStack(Items.diamond, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyNitOre, new ItemStack(AbyssalCraft.nitre, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyTinOre, new ItemStack(AbyssalCraft.tinIngot, 1), 0.7F);
		GameRegistry.addSmelting(AbyssalCraft.AbyCopOre, new ItemStack(AbyssalCraft.copperIngot, 1), 0.7F);
		GameRegistry.addSmelting(AbyssalCraft.ethaxium, new ItemStack(AbyssalCraft.ethaxium_brick), 0.2F);
	}

	private static void addItemCrafting(){

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.devsword, 1), new Object[] {"#", '#', AbyssalCraft.devsword}); //You ain't seen nothing

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxe, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axe, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovel, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.sword, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoe, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxeA, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axeA, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovelA, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.swordA, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoeA, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacer, 1), new Object[] {" #%", " &#", "&  ", '#', AbyssalCraft.Cpearl, '%', AbyssalCraft.OC, '&', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyingot, 9), new Object[] {"#", '#', AbyssalCraft.abyblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cplate, 1), new Object[] {"#%#", "#%#", "#%#", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cchunk, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.Coraliumcluster9, '%', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.PSDLfinder, 4), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.Coralium, '%', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cingot, 9), new Object[] {"#", '#', AbyssalCraft.corblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corpickaxe, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraxe, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corshovel, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corsword, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhoe, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowshard, 1), new Object[] {"###", "###", "###", '#', AbyssalCraft.shadowfragment});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowgem, 1), new Object[] {"###", "###", "###", '#', AbyssalCraft.shadowshard});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.oblivionshard, 1), new Object[] {" # ", "#%#", " # ", '#', AbyssalCraft.shadowgem, '%', new ItemStack(AbyssalCraft.Corb, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.corbow, 1), new Object[] {" #%", "&$%", " #%", '#', AbyssalCraft.Cingot, '%', Items.string, '&', AbyssalCraft.Cpearl, '$', new ItemStack(Items.bow, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumingot, 9), new Object[] {"#", '#', AbyssalCraft.dreadiumblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumpickaxe, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumaxe, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumshovel, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumsword, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumhoe, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.carbonCluster, 1), new Object[] {"###", "# #", "###", '#', new ItemStack(AbyssalCraft.crystal, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.denseCarbonCluster, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.carbonCluster, '%', Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"#@%", "$&$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"@%&", "$#$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"%&#", "$@$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"&#@", "$%$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadcloth, 1), new Object[] {"#%#", "%&%", "#%#", '#', Items.string, '%', AbyssalCraft.dreadfragment, '&', Items.leather});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadcloth, 1), new Object[] {"#%#", "%&%", "#%#", '%', Items.string, '#', AbyssalCraft.dreadfragment, '&', Items.leather});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadplate, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadcloth});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadblade, 1), new Object[] {"## ", "## ", "## ", '#', new ItemStack(AbyssalCraft.crystal, 1, 14)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadhilt, 1), new Object[] {"###", "%&%", "%&%", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadcloth, '&', AbyssalCraft.dreadplanks});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadkatana, 1), new Object[] {"# ", "% ", '#', AbyssalCraft.dreadblade, '%', AbyssalCraft.dreadhilt});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.gunpowder, 4), true, new Object[] {"#&#", "#%#", "###", '#', "dustSaltpeter", '%', new ItemStack(Items.coal, 1, 1), '&', "dustSulfur"}));
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 10), new Object[] {" # ", "#%#", " # ", '#', new ItemStack(AbyssalCraft.crystal, 1, 5), '%', new ItemStack(AbyssalCraft.crystal, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 9), new Object[] {" # ", "%%%", '#', new ItemStack(AbyssalCraft.crystal, 1, 6), '%', new ItemStack(AbyssalCraft.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 22), new Object[] {" # ", "%%%", " # ", '#', new ItemStack(AbyssalCraft.crystal, 1, 20), '%', new ItemStack(AbyssalCraft.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 21), new Object[] {"#%#", '#', new ItemStack(AbyssalCraft.crystal, 1, 4), '%', new ItemStack(AbyssalCraft.crystal, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 23), new Object[] {"#%", '#', new ItemStack(AbyssalCraft.crystal, 1, 19), '%', new ItemStack(AbyssalCraft.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 1, 10), new Object[] {" # ", "#%#", " # ", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 5), '%', new ItemStack(AbyssalCraft.crystalShard, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 1, 9), new Object[] {" # ", "%%%", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 6), '%', new ItemStack(AbyssalCraft.crystalShard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 1, 22), new Object[] {" # ", "%%%", " # ", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 20), '%', new ItemStack(AbyssalCraft.crystalShard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 1, 21), new Object[] {"#%#", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 4), '%', new ItemStack(AbyssalCraft.crystalShard, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 1, 23), new Object[] {"#%", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 19), '%', new ItemStack(AbyssalCraft.crystalShard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.ethaxium_brick, '%', AbyssalCraft.lifeCrystal});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] {" # ", "#%#", " # ", '#', AbyssalCraft.ethaxium_brick, '%', AbyssalCraft.OC});
		GameRegistry.addRecipe(new ItemStack(Items.spawn_egg, 1, AbyssalCraft.stringtoIDMapping.get("shadowboss")), new Object[] {"#", '#', AbyssalCraft.ODB});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot, 9), new Object[] {"#", '#', AbyssalCraft.ethaxiumblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethPickaxe, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethAxe, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethShovel, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethSword, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethHoe, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotCopper", '%', Items.flint}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotIron", '%', Items.flint}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotTin", '%', Items.flint}));
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingBlank, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(Blocks.stone_slab, 1, 0), '%', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingElder, 1), new Object[] {"#", "%", '#', AbyssalCraft.engravingBlank, '%', AbyssalCraft.ethaxiumIngot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingCthulhu, 1), new Object[] {"%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingHastur, 1), new Object[] {"%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingJzahar, 1), new Object[] {"%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', AbyssalCraft.eldritchScale});
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.engravingAzathoth, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), AbyssalCraft.engravingElder, new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1),
				new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4));
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingNyarlathotep, 1), new Object[] {"%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingYogsothoth, 1), new Object[] {"%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingShubniggurath, 1), new Object[] {"%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon, 1), new Object[] {"##%", "#&#", "##%", '#', Items.rotten_flesh, '%', Items.iron_ingot, '&', Items.book});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon_cor, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 0), '%', AbyssalCraft.necronomicon});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon_dre, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 1), '%', AbyssalCraft.necronomicon_cor});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon_omt, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 2), '%', AbyssalCraft.necronomicon_dre});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyssalnomicon, 1), new Object[] {"#$#", "%&%", "#%#", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.eldritchScale, '&', AbyssalCraft.necronomicon_omt, '$', AbyssalCraft.gatekeeperEssence});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_s, 1), new Object[] {"#%#", "%&%", "%%%", '#', Items.string, '%', Items.leather, '&', Items.gold_ingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_m, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 0), '%', AbyssalCraft.crystalbag_s});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_l, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 1), '%', AbyssalCraft.crystalbag_m});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_h, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 2), '%', AbyssalCraft.crystalbag_l});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.nugget, 9, 0), new Object[] {"#", '#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.nugget, 9, 1), new Object[] {"#", '#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.nugget, 9, 2), new Object[] {"#", '#', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.nugget, 9, 3), new Object[] {"#", '#', AbyssalCraft.ethaxiumIngot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyingot), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.nugget, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cingot), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.nugget, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumingot), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.nugget, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.nugget, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 0), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 1), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 2), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 3), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 4), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 5), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 5)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 6), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 6)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 7), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 7)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 8), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 8)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 9), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 9)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 10), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 10)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 11), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 11)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 12), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 12)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 13), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 13)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 14), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 14)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 15), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 15)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 16), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 16)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 17), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 17)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 18), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 19), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 19)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 20), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 20)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 21), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 21)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 22), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 22)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 23), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 23)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystal, 1, 24), new Object[] {"###", "###", "###", '#', new ItemStack(AbyssalCraft.crystalShard, 1, 24)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 0), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 1), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 2), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 3), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 4), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 5), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 5)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 6), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 6)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 7), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 7)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 8), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 8)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 9), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 9)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 10), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 10)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 11), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 11)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 12), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 12)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 13), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 13)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 14), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 14)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 15), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 15)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 16), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 16)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 17), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 17)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 18), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 19), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 19)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 20), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 20)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 21), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 21)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 22), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 22)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 23), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 23)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalShard, 9, 24), new Object[] {"#", '#', new ItemStack(AbyssalCraft.crystal, 1, 24)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.skin, 1, 0), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.Corflesh, '%', new ItemStack(AbyssalCraft.essence, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.skin, 1, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.dreadfragment, '%', new ItemStack(AbyssalCraft.essence, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.skin, 1, 2), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.omotholFlesh, '%', new ItemStack(AbyssalCraft.essence, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.drainStaff), new Object[] {" #%", " ##", "#  ", '#', AbyssalCraft.shadowshard, '%', AbyssalCraft.oblivionshard});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowshard, 9), new Object[] {"#", '#', AbyssalCraft.shadowgem});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowfragment, 9), new Object[] {"#", '#', AbyssalCraft.shadowshard});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.charm, 1, 0), new Object[] {"###", "#%#", "###", '#', Items.gold_ingot, '%', Items.diamond});

		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.lifeCrystal), new ItemStack(AbyssalCraft.crystal, 1, 3), new ItemStack(AbyssalCraft.crystal, 1, 5), new ItemStack(AbyssalCraft.crystal, 1, 6),
				new ItemStack(AbyssalCraft.crystal, 1, 4), new ItemStack(AbyssalCraft.crystal, 1, 7), new ItemStack(AbyssalCraft.crystal, 1, 2));

		//Coralium Gem Cluster Recipes
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster2, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster3, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster4, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster5, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster3, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster4, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster5, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster4, 1),AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster5, 1),AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster5, 1),AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster5, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster5, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster5, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster5, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster6, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster6, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster6, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster7, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster7, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster8, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster5, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster5, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster3);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster6, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster4);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster4, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster7, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster5);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster5, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster5, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster8, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster6);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster6, AbyssalCraft.Coralium);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1),AbyssalCraft.Coraliumcluster2, AbyssalCraft.Coraliumcluster7);
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 2), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 3), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 4), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster4});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 5), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster5});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 6), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster6});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 7), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster7});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 8), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster8});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 9), new Object[] {"#", '#', AbyssalCraft.Coraliumcluster9});

		addArmor(AbyssalCraft.helmet, AbyssalCraft.plate, AbyssalCraft.legs, AbyssalCraft.boots, AbyssalCraft.abyingot, AbyssalCraft.AbyssalniteU, Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots);
		addArmor(AbyssalCraft.Corhelmet, AbyssalCraft.Corplate, AbyssalCraft.Corlegs, AbyssalCraft.Corboots, AbyssalCraft.Cingot, AbyssalCraft.CoraliumU, AbyssalCraft.helmet, AbyssalCraft.plate, AbyssalCraft.legs, AbyssalCraft.boots);
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorbootsP, 1), new Object[] {"# #", "%&%", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corboots});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorhelmetP, 1), new Object[] {"&#&", "#@#", "%%%", '#', AbyssalCraft.Cplate, '&', AbyssalCraft.Cpearl, '@', AbyssalCraft.Corhelmet, '%', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorplateP, 1), new Object[] {"# #", "%@%", "%#%",'#', AbyssalCraft.Cplate, '%', AbyssalCraft.Cingot, '@', AbyssalCraft.Corplate});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorlegsP, 1), new Object[] {"%&%", "# #", "# #",'#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corlegs});
		addArmor(AbyssalCraft.dreadiumhelmet, AbyssalCraft.dreadiumplate, AbyssalCraft.dreadiumlegs, AbyssalCraft.dreadiumboots, AbyssalCraft.dreadiumingot, AbyssalCraft.DreadiumU, AbyssalCraft.Corhelmet, AbyssalCraft.Corplate, AbyssalCraft.Corlegs, AbyssalCraft.Corboots);
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSboots, 1), new Object[] {"#%#", "&&&", '#', AbyssalCraft.dreadcloth, '%', new ItemStack(AbyssalCraft.dreadiumboots, 1, OreDictionary.WILDCARD_VALUE), '&', AbyssalCraft.dreadplanks});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumShelmet, 1), new Object[] {" # ", "%&%", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadplate, '&', new ItemStack(AbyssalCraft.dreadiumhelmet, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSplate, 1), new Object[] {"#%#", "#&#", "@@@", '#', AbyssalCraft.dreadplate, '%', AbyssalCraft.dreadiumingot, '&', new ItemStack(AbyssalCraft.dreadiumplate, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.dreadcloth});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSlegs, 1), new Object[] {"#%#", "&&&", '#', AbyssalCraft.dreadplate, '%', new ItemStack(AbyssalCraft.dreadiumlegs, 1, OreDictionary.WILDCARD_VALUE), '&', AbyssalCraft.dreadcloth});
		addArmor(AbyssalCraft.ethHelmet, AbyssalCraft.ethPlate, AbyssalCraft.ethLegs, AbyssalCraft.ethBoots, AbyssalCraft.ethaxiumIngot, AbyssalCraft.EthaxiumU, AbyssalCraft.dreadiumhelmet, AbyssalCraft.dreadiumplate, AbyssalCraft.dreadiumlegs, AbyssalCraft.dreadiumboots);

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ironp, 2), new Object[] {"#", "#", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cloth, 1), new Object[] {"###", "#%#", "###", '#', Blocks.web, '%', Blocks.wool});

		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.MRE, 1), AbyssalCraft.ironp, Items.carrot, Items.potato, Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.chickenp, 1), AbyssalCraft.ironp, Items.cooked_chicken);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.porkp, 1), AbyssalCraft.ironp, Items.cooked_porkchop);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.beefp, 1), AbyssalCraft.ironp, Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.fishp, 1), AbyssalCraft.ironp, Items.cooked_fished);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.eggp, 1), AbyssalCraft.ironp, AbyssalCraft.friedegg);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.ironp, 1, 0), AbyssalCraft.dirtyplate, new ItemStack(AbyssalCraft.cloth,1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(AbyssalCraft.CobbleU, 4), "plankWood", Blocks.cobblestone, Blocks.cobblestone, Items.string, Items.flint));
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.IronU, 1), Blocks.cobblestone, Items.iron_ingot, Items.iron_ingot, AbyssalCraft.CobbleU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.GoldU, 1), Items.iron_ingot, Items.gold_ingot, Items.gold_ingot, AbyssalCraft.IronU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.DiamondU, 1), Items.gold_ingot, Items.diamond, Items.diamond, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.AbyssalniteU, 1), Items.diamond, AbyssalCraft.abyingot, AbyssalCraft.abyingot, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.CoraliumU, 1), AbyssalCraft.abyingot, AbyssalCraft.Cingot, AbyssalCraft.Cingot, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.DreadiumU, 1), AbyssalCraft.Cingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.EthaxiumU, 1), AbyssalCraft.dreadiumingot, AbyssalCraft.ethaxiumIngot, AbyssalCraft.ethaxiumIngot, AbyssalCraft.DreadiumU);

		//Wood to Cobble Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.stone_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_axe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_sword, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CobbleU});

		//Stone to Iron Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.iron_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_axe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_sword, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.IronU});

		//Iron to Gold Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.golden_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_axe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_sword, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});

		//Gold to Diamond Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.diamond_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_axe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_sword, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});

		//Diamond to Abyssalnite Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxeA, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axeA, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_axe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovelA, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.swordA, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_sword, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoeA, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.AbyssalniteU});

		//Abyssalnite to Coralium Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corpickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.pickaxeA, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraxe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.axeA, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corshovel, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.shovelA, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corsword, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.swordA, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhoe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.hoeA, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.CoraliumU});

		//Coralium to Dreadium Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumpickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.Corpickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumaxe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.Coraxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumshovel, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.Corshovel, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumsword, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.Corsword, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumhoe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.Corhoe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DreadiumU});

		//Dreadium to Ethaxium Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethPickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.dreadiumpickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethAxe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.dreadiumaxe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethShovel, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.dreadiumshovel, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethSword, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.dreadiumsword, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethHoe, 1), new Object[] {"#", "@", '#', new ItemStack(AbyssalCraft.dreadiumhoe, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.EthaxiumU});

		//Iron to Gold armor
		GameRegistry.addRecipe(new ItemStack(Items.golden_helmet, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_helmet, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_chestplate, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_chestplate, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_leggings, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_leggings, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_boots, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_boots, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.GoldU});

		//Gold to Diamond armor
		GameRegistry.addRecipe(new ItemStack(Items.diamond_helmet, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_helmet, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_chestplate, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_chestplate, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_leggings, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_leggings, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_boots, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_boots, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.DiamondU});
	}

	private static void addItemSmelting(){

		GameRegistry.addSmelting(AbyssalCraft.abychunk, new ItemStack(AbyssalCraft.abyingot), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Cchunk, new ItemStack(AbyssalCraft.Cingot, 2), 3F);
		GameRegistry.addSmelting(Items.egg, new ItemStack(AbyssalCraft.friedegg, 1), 0F);
		GameRegistry.addSmelting(AbyssalCraft.dreadchunk, new ItemStack(AbyssalCraft.abyingot), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Cbucket, new ItemStack(AbyssalCraft.cstone, 1), 0.2F);

		GameRegistry.addSmelting(AbyssalCraft.coin, new ItemStack(Items.iron_ingot, 4), 0.5F);

		GameRegistry.addSmelting(Items.leather_helmet, new ItemStack(Items.leather), 1F);
		GameRegistry.addSmelting(Items.leather_chestplate, new ItemStack(Items.leather), 1F);
		GameRegistry.addSmelting(Items.leather_leggings, new ItemStack(Items.leather), 1F);
		GameRegistry.addSmelting(Items.leather_boots, new ItemStack(Items.leather), 1F);

		GameRegistry.addSmelting(Items.iron_helmet, new ItemStack(Items.iron_ingot), 1F);
		GameRegistry.addSmelting(Items.iron_chestplate, new ItemStack(Items.iron_ingot), 1F);
		GameRegistry.addSmelting(Items.iron_leggings, new ItemStack(Items.iron_ingot), 1F);
		GameRegistry.addSmelting(Items.iron_boots, new ItemStack(Items.iron_ingot), 1F);

		GameRegistry.addSmelting(Items.golden_helmet, new ItemStack(Items.gold_ingot), 1F);
		GameRegistry.addSmelting(Items.golden_chestplate, new ItemStack(Items.gold_ingot), 1F);
		GameRegistry.addSmelting(Items.golden_leggings, new ItemStack(Items.gold_ingot), 1F);
		GameRegistry.addSmelting(Items.golden_boots, new ItemStack(Items.gold_ingot), 1F);

		GameRegistry.addSmelting(Items.diamond_helmet, new ItemStack(Items.diamond), 1F);
		GameRegistry.addSmelting(Items.diamond_chestplate, new ItemStack(Items.diamond), 1F);
		GameRegistry.addSmelting(Items.diamond_leggings, new ItemStack(Items.diamond), 1F);
		GameRegistry.addSmelting(Items.diamond_boots, new ItemStack(Items.diamond), 1F);
	}

	private static void addCrystallization(){

		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.Cwater, new ItemStack(AbyssalCraft.crystalShard, 6, 13), 0.4F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.Cbucket, new ItemStack(AbyssalCraft.crystalShard, 6, 13), 0.2F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.Cingot, new ItemStack(AbyssalCraft.crystalShard, 4, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.Cchunk, new ItemStack(AbyssalCraft.crystalShard, 4, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.AbyLCorOre, new ItemStack(AbyssalCraft.crystalShard, 4, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.abyingot, new ItemStack(AbyssalCraft.crystalShard, 4, 12), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.abychunk, new ItemStack(AbyssalCraft.crystalShard, 4, 12), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.dreadiumingot, new ItemStack(AbyssalCraft.crystalShard, 4, 14), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.iron_ingot, new ItemStack(AbyssalCraft.crystalShard, 4, 0), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.gold_ingot, new ItemStack(AbyssalCraft.crystalShard, 4, 1), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.redstone, new ItemStack(AbyssalCraft.crystalShard, 4, 11), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.coal, new ItemStack(AbyssalCraft.crystalShard, 4, 3), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.blaze_powder, new ItemStack(AbyssalCraft.crystalShard, 4, 15), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(Items.dye, 1, 15), new ItemStack(AbyssalCraft.crystalShard, 4, 7), 0.0F);
		AbyssalCraftAPI.addSingleCrystallization("oreAbyssalnite", "crystalShardAbyssalnite", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreCoralium", "crystalShardCoralium", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreIron", "crystalShardIron", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreGold", "crystalShardGold", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("ingotTin", "crystalShardTin", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreTin", "crystalShardTin", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("ingotCopper", "crystalShardCopper", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreCopper", "crystalShardCopper", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("ingotAluminum", "crystalShardAluminium", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("ingotAluminium", "crystalShardAluminium", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreAluminum", "crystalShardAluminium", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("blockCopper", "crystalCopper", 4, 0.9F);
		AbyssalCraftAPI.addSingleCrystallization("blockTin", "crystalTin", 4, 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.gold_block, new ItemStack(AbyssalCraft.crystal, 4, 1), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.iron_block, new ItemStack(AbyssalCraft.crystal, 4, 0), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.abyblock, new ItemStack(AbyssalCraft.crystal, 4, 12), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.corblock, new ItemStack(AbyssalCraft.crystal, 4, 13), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.dreadiumblock, new ItemStack(AbyssalCraft.crystal, 4, 14), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.coal_ore, new ItemStack(AbyssalCraft.crystalShard, 4, 3), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.coal_block, new ItemStack(AbyssalCraft.crystal, 4, 3), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.redstone_ore, new ItemStack(AbyssalCraft.crystalShard, 4, 11), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.redstone_block, new ItemStack(AbyssalCraft.crystal, 4, 11), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization("ingotZinc", "crystalZinc", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreZinc", "crystalZinc", 0.1F);
		AbyssalCraftAPI.addCrystallization(AbyssalCraft.dreadchunk, new ItemStack(AbyssalCraft.crystalShard, 4, 12), new ItemStack(AbyssalCraft.crystalShard, 4, 14), 0.2F);
		AbyssalCraftAPI.addCrystallization(AbyssalCraft.dreadore, new ItemStack(AbyssalCraft.crystalShard, 4, 12), new ItemStack(AbyssalCraft.crystalShard, 4, 14), 0.2F);
		AbyssalCraftAPI.addCrystallization(Blocks.water, new ItemStack(AbyssalCraft.crystalShard, 12, 5), new ItemStack(AbyssalCraft.crystalShard, 6, 4), 0.4F);
		AbyssalCraftAPI.addCrystallization(Items.water_bucket, new ItemStack(AbyssalCraft.crystalShard, 12, 5), new ItemStack(AbyssalCraft.crystalShard, 6, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.crystalShard, 6, 5), new ItemStack(AbyssalCraft.crystalShard, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Items.dye, 1, 4), new ItemStack(AbyssalCraft.crystal, 2, 21), new ItemStack(AbyssalCraft.crystalShard, 16, 2), 0.15F);
		AbyssalCraftAPI.addCrystallization(AbyssalCraft.methane, new ItemStack(AbyssalCraft.crystalShard, 4, 4), new ItemStack(AbyssalCraft.crystal, 16, 5), 0.1F);
		AbyssalCraftAPI.addCrystallization(Items.gunpowder, new ItemStack(AbyssalCraft.crystalShard, 16, 9), new ItemStack(AbyssalCraft.crystalShard, 4, 2), 0.1F);
		AbyssalCraftAPI.addCrystallization("dustSaltpeter", "crystalShardPotassium", 4, "crystalShardNitrate", 4, 0.1F);
		AbyssalCraftAPI.addCrystallization("oreSaltpeter", "crystalShardPotassium", 4, "crystalShardNitrate", 4, 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.obsidian, new ItemStack(AbyssalCraft.crystalShard, 4, 21), new ItemStack(AbyssalCraft.crystalShard, 4, 23), 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.stone, new ItemStack(AbyssalCraft.crystalShard, 4, 21), new ItemStack(AbyssalCraft.crystalShard, 4, 23), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystal,1, 21), new ItemStack(AbyssalCraft.crystal, 1, 18), new ItemStack(AbyssalCraft.crystal, 2, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystalShard,1, 21), new ItemStack(AbyssalCraft.crystalShard, 1, 18), new ItemStack(AbyssalCraft.crystalShard, 2, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystal, 1, 22), new ItemStack(AbyssalCraft.crystal, 2, 20), new ItemStack(AbyssalCraft.crystal, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystalShard, 1, 22), new ItemStack(AbyssalCraft.crystalShard, 2, 20), new ItemStack(AbyssalCraft.crystalShard, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystal, 1, 23), new ItemStack(AbyssalCraft.crystal, 1, 19), new ItemStack(AbyssalCraft.crystal, 1, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystalShard, 1, 23), new ItemStack(AbyssalCraft.crystalShard, 1, 19), new ItemStack(AbyssalCraft.crystalShard, 1, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization("ingotBronze", "crystalShardCopper", 4, "crystalShardTin", 12, 0.4F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystal, 1, 10), new ItemStack(AbyssalCraft.crystal, 1, 3), new ItemStack(AbyssalCraft.crystal, 4, 5), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystalShard, 1, 10), new ItemStack(AbyssalCraft.crystalShard, 1, 3), new ItemStack(AbyssalCraft.crystalShard, 4, 5), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystal, 1, 9), new ItemStack(AbyssalCraft.crystal, 1, 6), new ItemStack(AbyssalCraft.crystal, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.crystalShard, 1, 9), new ItemStack(AbyssalCraft.crystalShard, 1, 6), new ItemStack(AbyssalCraft.crystalShard, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.lapis_ore, new ItemStack(AbyssalCraft.crystalShard, 24, 21), new ItemStack(AbyssalCraft.crystalShard, 16, 2), 0.15F);
		AbyssalCraftAPI.addCrystallization(Blocks.lapis_block, new ItemStack(AbyssalCraft.crystal, 24, 21), new ItemStack(AbyssalCraft.crystal, 16, 2), 1.0F);
		AbyssalCraftAPI.addCrystallization("ingotBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.5F);
		AbyssalCraftAPI.addCrystallization("oreBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.5F);
		AbyssalCraftAPI.addSingleCrystallization(Items.rotten_flesh, new ItemStack(AbyssalCraft.crystalShard, 8, 7), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.crystalShard, 8, 7), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.crystalShard, 4, 13), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.crystalShard, 4, 14), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.crystalShard, 4, 3), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.shadowgem, 1), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.Corflesh), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.crystalShard, 4, 13), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.Corbone), new ItemStack(AbyssalCraft.crystalShard, 12, 7), new ItemStack(AbyssalCraft.crystalShard, 4, 13), 0.2F);
		AbyssalCraftAPI.addSingleCrystallization(Items.gold_nugget, new ItemStack(AbyssalCraft.crystalShard, 1, 1), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(AbyssalCraft.nugget, 1, 0), new ItemStack(AbyssalCraft.crystalShard, 1, 12), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(AbyssalCraft.nugget, 1, 1), new ItemStack(AbyssalCraft.crystalShard, 1, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(AbyssalCraft.nugget, 1, 2), new ItemStack(AbyssalCraft.crystalShard, 1, 14), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetIron", "crystalShardIron", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetCopper", "crystalShardCopper", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetTin", "crystalShardTin", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetAluminium", "crystalShardAluminium", 0.1F);
		AbyssalCraftAPI.addCrystallization("nuggetBronze", "crystalShardCopper", 1, "crystalShardTin", 3, 0.1F);
		AbyssalCraftAPI.addCrystallization("nuggetBrass", "crystalShardCopper", 3, "crystalShardTin", 2, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(AbyssalCraft.Coralium, new ItemStack(AbyssalCraft.crystalShard, 1, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetZinc", "crystalShardZinc", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetMagnesium", "crystalShardMagnesium", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("ingotMagnesium", "crystalShardMagnesium", 4, 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.skin, 1, 0), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.essence, 1, 0), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.skin, 1, 1), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.essence, 1, 1), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(AbyssalCraft.skin, 1, 2), new ItemStack(AbyssalCraft.crystalShard, 8, 7), new ItemStack(AbyssalCraft.essence, 1, 2), 0.2F);
		AbyssalCraftAPI.addCrystallization(AbyssalCraft.Dreadshard, new ItemStack(AbyssalCraft.crystalShard, 1, 12), new ItemStack(AbyssalCraft.crystalShard, 4, 14), 0.2F);

		//Crystallization for dusts
		AbyssalCraftAPI.addSingleCrystallization("dustIron", "crystalShardIron", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("dustGold", "crystalShardGold", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("dustTin", "crystalShardTin", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("dustCopper", "crystalShardCopper", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("dustCoal", "crystalShardCarbon", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("dustAluminium", "crystalSHardAluminium", 4, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("dustSulfur", "crystalShardSulfur", 4, 0.1F);
		AbyssalCraftAPI.addCrystallization("dustBronze", "crystalShardCopper", 4, "crystalShardTin", 12, 0.1F);
		AbyssalCraftAPI.addCrystallization("dustBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.1F);
	}

	private static void addTransmutation(){

		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 12), new ItemStack(AbyssalCraft.abyingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 13), new ItemStack(AbyssalCraft.Cingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 14), new ItemStack(AbyssalCraft.dreadiumingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.Dreadshard, new ItemStack(AbyssalCraft.dreadiumingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 0), new ItemStack(Items.iron_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 1), new ItemStack(Items.gold_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 2), new ItemStack(AbyssalCraft.sulfur, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 15), new ItemStack(Items.blaze_powder, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 11), new ItemStack(Items.redstone, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 5), new ItemStack(AbyssalCraft.crystal, 1, 5), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 4), new ItemStack(AbyssalCraft.crystal, 1, 4), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 6), new ItemStack(AbyssalCraft.crystal, 1, 6), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 10), new ItemStack(AbyssalCraft.methane, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 16), new ItemStack(AbyssalCraft.tinIngot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystal, 1, 17), new ItemStack(AbyssalCraft.copperIngot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.Darkstone, new ItemStack(Blocks.stone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.stone, new ItemStack(AbyssalCraft.Darkstone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.stonebrick, new ItemStack(AbyssalCraft.Darkstone_brick, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.Darkstone_brick, new ItemStack(Blocks.stonebrick), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.cobblestone, new ItemStack(AbyssalCraft.Darkstone_cobble, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.Darkstone_cobble, new ItemStack(Blocks.cobblestone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.cstonebrick, new ItemStack(AbyssalCraft.cbrick, 4), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.cbrick, new ItemStack(AbyssalCraft.cstone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.Cbucket, new ItemStack(AbyssalCraft.Cwater, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.Cwater, new ItemStack(AbyssalCraft.cstone, 8), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antibucket, new ItemStack(AbyssalCraft.anticwater, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.denseCarbonCluster, new ItemStack(Items.diamond), 0.5F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.dreadKey, new ItemStack(AbyssalCraft.portalPlacerJzh), 1.0F);
		AbyssalCraftAPI.addTransmutation("crystalAluminium", "ingotAluminum", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalAluminium", "ingotAluminium", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalZinc", "ingotZinc", 0.2F);
		AbyssalCraftAPI.addTransmutation(Blocks.lava, new ItemStack(AbyssalCraft.solidLava), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.end_stone, new ItemStack(AbyssalCraft.ethaxium), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.ethaxium, new ItemStack(Blocks.end_stone), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), new ItemStack(AbyssalCraft.ethaxium), 0.0F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiBeef, new ItemStack(Items.cooked_beef), 0.3F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiPork, new ItemStack(Items.cooked_porkchop), 0.3F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiChicken, new ItemStack(Items.cooked_chicken), 0.3F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiBone, new ItemStack(Items.bone, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiFlesh, new ItemStack(Items.rotten_flesh, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiSpider_eye, new ItemStack(Items.spider_eye, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiCorflesh, new ItemStack(AbyssalCraft.Corflesh, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(AbyssalCraft.antiCorbone, new ItemStack(AbyssalCraft.Corbone, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystalShard, 1, 12), new ItemStack(AbyssalCraft.nugget, 1, 0), 0.1F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystalShard, 1, 13), new ItemStack(AbyssalCraft.nugget, 1, 1), 0.1F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystalShard, 1, 14), new ItemStack(AbyssalCraft.nugget, 1, 2), 0.1F);
		AbyssalCraftAPI.addTransmutation("crystalShardAluminium", "nuggetAluminum", 0.1F);
		AbyssalCraftAPI.addTransmutation("crystalShardAluminium", "nuggetAluminium", 0.1F);
		AbyssalCraftAPI.addTransmutation("crystalShardIron", "nuggetIron", 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(AbyssalCraft.crystalShard, 1, 1), new ItemStack(Items.gold_nugget), 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardCopper", "nuggetCopper", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardTin", "nuggetTin", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardZinc", "nuggetZinc", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardMagnesium", "nuggetMagnesium", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalMagnesium", "ingotMagnesium", 0.2F);
	}

	private static void addEngraving(){

		AbyssalCraftAPI.addCoin(AbyssalCraft.coin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.cthulhuCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.elderCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.jzaharCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.hasturCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.azathothCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.nyarlathotepCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.yogsothothCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.shubniggurathCoin);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.coin, (ItemEngraving)AbyssalCraft.engravingBlank, 0.0F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.cthulhuCoin, (ItemEngraving)AbyssalCraft.engravingCthulhu, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.elderCoin, (ItemEngraving)AbyssalCraft.engravingElder, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.jzaharCoin, (ItemEngraving)AbyssalCraft.engravingJzahar, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.hasturCoin, (ItemEngraving)AbyssalCraft.engravingHastur, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.azathothCoin, (ItemEngraving)AbyssalCraft.engravingAzathoth, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.nyarlathotepCoin, (ItemEngraving)AbyssalCraft.engravingNyarlathotep, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.yogsothothCoin, (ItemEngraving)AbyssalCraft.engravingYogsothoth, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.shubniggurathCoin, (ItemEngraving)AbyssalCraft.engravingShubniggurath, 0.5F);
	}

	private static void addMaterialization(){
		AbyssalCraftAPI.addMaterialization(new ItemStack[]{new ItemStack(AbyssalCraft.crystal, 8)}, new ItemStack(Items.bone));
		AbyssalCraftAPI.addMaterialization(new ItemStack[]{new ItemStack(AbyssalCraft.crystal, 8, 2)}, new ItemStack(Items.rotten_flesh));
		AbyssalCraftAPI.addMaterialization(new ItemStack[]{new ItemStack(AbyssalCraft.crystal, 8), new ItemStack(AbyssalCraft.crystal, 13)}, new ItemStack(AbyssalCraft.Corflesh));
	}

	private static void addRitualRecipes(){
		RitualRegistry.instance().addDimensionToBookTypeAndName(0, 0, NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId1, 1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId2, 2, NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId3, 3, NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId4, 3, NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE);

		Object[] gk2offerings = new Object[]{new ItemStack(AbyssalCraft.Corb), new ItemStack(AbyssalCraft.PSDL), new ItemStack(AbyssalCraft.EoA)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("asorahGatewayKey", 1, AbyssalCraft.configDimId1, 10000F, new ItemStack(AbyssalCraft.portalPlacerDL), new ItemStack(AbyssalCraft.portalPlacer), gk2offerings));
		Object[] ocofferings = new Object[]{new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard), new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard),
				new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard), new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("oblivionCatalyst", 0, 5000F, new ItemStack(AbyssalCraft.OC), new ItemStack(Items.ender_eye), ocofferings));
		Object[] tgofferings = new Object[]{new ItemStack(Items.diamond), new ItemStack(Items.blaze_powder), new ItemStack(Items.ender_pearl), new ItemStack(Items.blaze_powder),
				new ItemStack(Items.diamond), new ItemStack(Items.blaze_powder), new ItemStack(Items.ender_pearl), new ItemStack(Items.blaze_powder)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("transmutationGem", 0, 300F, new ItemStack(AbyssalCraft.Corb), new ItemStack(AbyssalCraft.Cpearl), tgofferings));
		Object[] depthsofferings = new Object[]{new ItemStack(AbyssalCraft.Coraliumcluster9), new ItemStack(AbyssalCraft.Coraliumcluster9), new ItemStack(AbyssalCraft.Cbucket),
				new ItemStack(Blocks.vine), new ItemStack(Blocks.waterlily), new ItemStack(AbyssalCraft.Corb), new ItemStack(AbyssalCraft.Corflesh)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsHelmet", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthshelmet), new ItemStack(AbyssalCraft.Corhelmet), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsChestplate", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthsplate), new ItemStack(AbyssalCraft.Corplate), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsLeggings", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthslegs), new ItemStack(AbyssalCraft.Corlegs), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsBoots", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthsboots), new ItemStack(AbyssalCraft.Corboots), depthsofferings));
		Object[] asorahofferings = new Object[]{new ItemStack(Items.gold_ingot), new ItemStack(AbyssalCraft.Corb), new ItemStack(Items.gold_ingot), new ItemStack(AbyssalCraft.Cbucket),
				new ItemStack(Items.gold_ingot), new ItemStack(Blocks.enchanting_table), new ItemStack(Items.gold_ingot)};
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonAsorah", 1, AbyssalCraft.configDimId1, 1000F, EntityDragonBoss.class, asorahofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconBreedingRitual());
		Object[] sacthothofferings = new Object[]{new ItemStack(AbyssalCraft.OC), new ItemStack(Blocks.obsidian), new ItemStack(AbyssalCraft.Cbucket), new ItemStack(Blocks.obsidian),
				new ItemStack(AbyssalCraft.antibucket), new ItemStack(Blocks.obsidian), new ItemStack(AbyssalCraft.ODBcore), new ItemStack(Blocks.obsidian)};
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonSacthoth", 1, 1000F, EntitySacthoth.class, sacthothofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconDreadSpawnRitual());
		Object[] coraoeofferings = new Object[]{new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0),
				new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0),new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.gunpowder)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("corPotionAoE", 1, 300F, AbyssalCraft.Cplague, coraoeofferings));
		Object[] dreaoeofferings = new Object[]{new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.potionitem, 1, 0),
				new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.gunpowder)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("drePotionAoE", 2, 300F, AbyssalCraft.Dplague, dreaoeofferings));
		Object[] antiaoeofferings = new Object[]{new ItemStack(AbyssalCraft.antiFlesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.antiFlesh), new ItemStack(Items.potionitem, 1, 0),
				new ItemStack(AbyssalCraft.antiFlesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.antiFlesh), new ItemStack(Items.gunpowder)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("antiPotionAoE", 0, 300F, AbyssalCraft.antiMatter, antiaoeofferings));
		Object[] cthulhuofferings = new Object[]{new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0),
				new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1),
				new ItemStack(AbyssalCraft.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.cthulhuStatue), AbyssalCraft.monolithStone, cthulhuofferings));
		Object[] hasturofferings = new Object[]{new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1),
				new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1),
				new ItemStack(AbyssalCraft.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.hasturStatue), AbyssalCraft.monolithStone, hasturofferings));
		Object[] jzaharofferings = new Object[]{AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale,
				new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.jzaharStatue), AbyssalCraft.monolithStone, jzaharofferings));
		Object[] azathothofferings = new Object[]{new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2),
				new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1),
				new ItemStack(AbyssalCraft.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.azathothStatue), AbyssalCraft.monolithStone, azathothofferings));
		Object[] nyarlathotepofferings = new Object[]{new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2),
				new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1),
				new ItemStack(AbyssalCraft.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.nyarlathotepStatue), AbyssalCraft.monolithStone, nyarlathotepofferings));
		Object[] yogsothothofferings = new Object[]{new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3),
				new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1),
				new ItemStack(AbyssalCraft.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.yogsothothStatue), AbyssalCraft.monolithStone, yogsothothofferings));
		Object[] shubniggurathofferings = new Object[]{new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4),
				new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1),
				new ItemStack(AbyssalCraft.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.shubniggurathStatue), AbyssalCraft.monolithStone, shubniggurathofferings));
		Object[] psdlofferings = new Object[]{new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 1),
				new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 1)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("powerStone", 4, AbyssalCraft.configDimId2, 5000F, new ItemStack(AbyssalCraft.PSDL), AbyssalCraft.CoraliumInfusedStone, psdlofferings));
		Object[] ethofferings = new Object[]{AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.lifeCrystal, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick};
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("ethaxiumIngot", 3, AbyssalCraft.configDimId3, 1000F, new ItemStack(AbyssalCraft.ethaxiumIngot), ethofferings));
		Object[] dreadofferings = new Object[]{new ItemStack(AbyssalCraft.essence, 1, 1), AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard,
				AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadHelmet", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.helmetD), AbyssalCraft.helmet, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadChestplate", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.plateD), AbyssalCraft.plate, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadLeggings", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.legsD), AbyssalCraft.legs, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadBoots", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.bootsD), AbyssalCraft.boots, dreadofferings));
		Object[] rcoffers = new Object[]{AbyssalCraft.shadowfragment, Items.arrow, AbyssalCraft.shadowfragment, Items.arrow, AbyssalCraft.shadowfragment, Items.arrow, AbyssalCraft.shadowfragment, Items.arrow};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("rangeCharm", 0, 100F, new ItemStack(AbyssalCraft.charm, 1, 1), new ItemStack(AbyssalCraft.charm, 1, 0), rcoffers));
		Object[] dcoffers = new Object[]{AbyssalCraft.shadowfragment, Items.redstone, AbyssalCraft.shadowfragment, Items.redstone, AbyssalCraft.shadowfragment, Items.redstone, AbyssalCraft.shadowfragment,
				Items.redstone};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("durationCharm", 0, 100F, new ItemStack(AbyssalCraft.charm, 1, 2), new ItemStack(AbyssalCraft.charm, 1, 0), dcoffers));
		Object[] pcoffers = new Object[]{AbyssalCraft.shadowfragment, Items.glowstone_dust, AbyssalCraft.shadowfragment, Items.glowstone_dust, AbyssalCraft.shadowfragment, Items.glowstone_dust,
				AbyssalCraft.shadowfragment, Items.glowstone_dust};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("powerCharm", 0, 100F, new ItemStack(AbyssalCraft.charm, 1, 3), new ItemStack(AbyssalCraft.charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconRespawnJzaharRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("crangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 1), new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 2), new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 3), new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.hasturCharm, 1, 1), new ItemStack(AbyssalCraft.hasturCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.hasturCharm, 1, 2), new ItemStack(AbyssalCraft.hasturCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.hasturCharm, 1, 3), new ItemStack(AbyssalCraft.hasturCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 1), new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 2), new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 3), new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("arangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.azathothCharm, 1, 1), new ItemStack(AbyssalCraft.azathothCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("adurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.azathothCharm, 1, 2), new ItemStack(AbyssalCraft.azathothCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("apowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.azathothCharm, 1, 3), new ItemStack(AbyssalCraft.azathothCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 1), new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ndurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 2), new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("npowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 3), new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 1), new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ydurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 2), new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ypowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 3), new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("srangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 1), new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("sdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 2), new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("spowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 3), new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), pcoffers));
		Object[] ccoffers = new Object[]{AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin,
				AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuCharm", 4, 2000F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), ccoffers));
		Object[] hcoffers = new Object[]{AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin,
				AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturCharm", 4, 2000F, new ItemStack(AbyssalCraft.hasturCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), hcoffers));
		Object[] jcoffers = new Object[]{AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin,
				AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharCharm", 4, 2000F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), jcoffers));
		Object[] acoffers = new Object[]{AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin,
				AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothCharm", 4, 2000F, new ItemStack(AbyssalCraft.azathothCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), acoffers));
		Object[] ncoffers = new Object[]{AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin,
				AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepCharm", 4, 2000F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), ncoffers));
		Object[] ycoffers = new Object[]{AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin,
				AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothCharm", 4, 2000F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), ycoffers));
		Object[] scoffers = new Object[]{AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin,
				AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathCharm", 4, 2000F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), scoffers));
		Object[] owoffers = new Object[]{AbyssalCraft.shadowshard, Blocks.cobblestone, AbyssalCraft.Coralium, AbyssalCraft.Darkstone_cobble, AbyssalCraft.shadowshard, Blocks.cobblestone,
				AbyssalCraft.Coralium, AbyssalCraft.Darkstone_cobble};
		Object[] awoffers = new Object[]{AbyssalCraft.shadowshard, AbyssalCraft.abybrick, AbyssalCraft.Coralium, AbyssalCraft.cstonebrick, AbyssalCraft.shadowshard, AbyssalCraft.abybrick,
				AbyssalCraft.Coralium, AbyssalCraft.cstonebrick};
		Object[] dloffers = new Object[]{AbyssalCraft.shadowshard, AbyssalCraft.dreadbrick, AbyssalCraft.Coralium, AbyssalCraft.abydreadbrick, AbyssalCraft.shadowshard, AbyssalCraft.dreadbrick,
				AbyssalCraft.Coralium, AbyssalCraft.abydreadbrick};
		Object[] omtoffers = new Object[]{AbyssalCraft.shadowshard, new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), AbyssalCraft.Coralium, new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0),
				AbyssalCraft.shadowshard, new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), AbyssalCraft.Coralium, new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOWupgrade", 0, 400F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 0), AbyssalCraft.energyPedestal, owoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epAWupgrade", 1, 800F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 1), new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 0), awoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epDLupgrade", 2, 1200F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 2), new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 1), dloffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOMTupgrade", 3, 1600F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 3), new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 2), omtoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOWupgrade", 0, 400F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 0), AbyssalCraft.sacrificialAltar, owoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saAWupgrade", 1, 800F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 1), new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 0), awoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saDLupgrade", 2, 1200F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 2), new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 1), dloffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOMTupgrade", 3, 1600F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 3), new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 2), omtoffers));
		Object[] staffofferings = new Object[]{new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.eldritchScale, AbyssalCraft.ethaxiumIngot,
				AbyssalCraft.eldritchScale, AbyssalCraft.ethaxiumIngot, AbyssalCraft.eldritchScale, new ItemStack(AbyssalCraft.essence, 1, 0)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStaff", 4, AbyssalCraft.configDimId3, 15000F, true, new ItemStack(AbyssalCraft.Staff), AbyssalCraft.drainStaff, staffofferings));
	}

	private static void addDisruptions(){
		DisruptionHandler.instance().registerDisruption(new DisruptionLightning());
		DisruptionHandler.instance().registerDisruption(new DisruptionFire());
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnShoggoth", null, EntityLesserShoggoth.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnGatekeeperMinion", DeityType.JZAHAR, EntityGatekeeperMinion.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("poisonPotion", null, Potion.poison));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("slownessPotion", null, Potion.moveSlowdown));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("weaknessPotion", null, Potion.weakness));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("witherPotion", null, Potion.wither));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("coraliumPotion", null, AbyssalCraft.Cplague));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotentialEnergy());
		DisruptionHandler.instance().registerDisruption(new DisruptionFreeze());
		DisruptionHandler.instance().registerDisruption(new DisruptionSwarm("swarmShadow", null, EntityShadowCreature.class, EntityShadowMonster.class, EntityShadowBeast.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionFireRain());
		DisruptionHandler.instance().registerDisruption(new DisruptionDisplaceEntities());
		DisruptionHandler.instance().registerDisruption(new DisruptionMonolith());
		DisruptionHandler.instance().registerDisruption(new DisruptionTeleportRandomly());
	}

	private static void addArmor(Item helmet, Item chestplate, Item pants, Item boots, Item material, Item upgrade, Item oldh, Item oldc, Item oldp, Item oldb){

		GameRegistry.addRecipe(new ItemStack(helmet), new Object[] {"###", "# #", '#', material});
		GameRegistry.addRecipe(new ItemStack(chestplate), new Object[] {"# #", "###", "###", '#', material});
		GameRegistry.addRecipe(new ItemStack(pants), new Object[] {"###", "# #", "# #", '#', material});
		GameRegistry.addRecipe(new ItemStack(boots), new Object[] {"# #", "# #", '#', material});

		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"#", "@", '#', new ItemStack(oldh, 1, OreDictionary.WILDCARD_VALUE), '@', upgrade});
		GameRegistry.addRecipe(new ItemStack(chestplate, 1), new Object[] {"#", "@", '#', new ItemStack(oldc, 1, OreDictionary.WILDCARD_VALUE), '@', upgrade});
		GameRegistry.addRecipe(new ItemStack(pants, 1), new Object[] {"#", "@", '#', new ItemStack(oldp, 1, OreDictionary.WILDCARD_VALUE), '@', upgrade});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"#", "@", '#', new ItemStack(oldb, 1, OreDictionary.WILDCARD_VALUE), '@', upgrade});

		GameRegistry.addSmelting(helmet, new ItemStack(material), 1F);
		GameRegistry.addSmelting(chestplate, new ItemStack(material), 1F);
		GameRegistry.addSmelting(pants, new ItemStack(material), 1F);
		GameRegistry.addSmelting(boots, new ItemStack(material), 1F);
	}
}
