/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common;

import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.*;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.core.util.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class AbyssalCrafting {

	public static void addRecipes()
	{
		//Block crafting
		addBlockCrafting();
		//Block smelting
		addBlockSmelting();
		//Item crafting
		addItemCrafting();
		//Item smelting
		addItemSmelting();
		//Salvaging (special smelting)
		addSalvage();
		//Crystallization
		addCrystallization();
		//Transmutation
		addTransmutation();
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
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Altar, 1), new Object[] {" # ", "%&%", "%@%", '#', Items.bucket, '%', Items.gold_ingot, '&', AbyssalCraft.Corb, '@', Blocks.enchanting_table});
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
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.transmutator, 1), new Object[] {"###", "#%#", "&$&", '#', AbyssalCraft.cbrick, '%', AbyssalCraft.Corb, '&', AbyssalCraft.corblock, '$', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystallizer, 1), new Object[] {"###", "&%&", "###", '#', AbyssalCraft.dreadbrick, '&', AbyssalCraft.dreadiumblock, '%', Blocks.furnace});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadaltartop, 1), new Object[] {"#%#", "&&&", "@@@", '#', Items.stick, '%', Items.bucket, '&', AbyssalCraft.dreadcloth, '@', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadaltarbottom, 1), new Object[] {"#%#", "&@&", "T$T", '#', Items.bone, '%', AbyssalCraft.dreadcloth, '&', AbyssalCraft.dreadiumingot, '@', AbyssalCraft.portalPlacerDL, '$', AbyssalCraft.Dreadshard, 'T', AbyssalCraft.dreadstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), new Object [] {"##", "##", '#', AbyssalCraft.ethaxium});
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
	}

	private static void addBlockSmelting(){

		GameRegistry.addSmelting(AbyssalCraft.Darkstone_cobble, new ItemStack(AbyssalCraft.Darkstone, 1), 0.1F);
		CoreRegistry.addOreSmelting("oreAbyssalnite", "ingotAbyssalnite", 3F);
		CoreRegistry.addOreSmelting("oreCoralium", "gemCoralium", 3F);
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
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object[] {"@#@", "#$#", "@#@", '$', Items.ender_eye, '#', AbyssalCraft.oblivionshard, '@', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object[] {"@#@", "#$#", "@#@", '$', Items.ender_eye, '#', Items.redstone, '@', AbyssalCraft.oblivionshard});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object[] {"@#@", "#$#", "@#@", '$', Items.nether_star, '#', Items.ender_eye, '@', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object[] {"@#@", "#$#", "@#@", '$', Items.nether_star, '#', Items.redstone, '@', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxeA, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axeA, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovelA, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.swordA, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoeA, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacer, 1), new Object[] {" #%", " &#", "&  ", '#', AbyssalCraft.Cpearl, '%', AbyssalCraft.OC, '&', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyingot, 9), new Object[] {"#", '#', AbyssalCraft.abyblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corb, 1), new Object[] {"#%#", "@$@", "#%#", '#', Items.blaze_powder, '%', Items.diamond, '@', Items.ender_pearl, '$', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corb, 1), new Object[] {"#%#", "@$@", "#%#", '#', Items.blaze_powder, '%', Items.ender_pearl, '@', Items.diamond, '$', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxeC, 1), new Object[] {"#%#", " & ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.pickaxeA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axeC, 1), new Object[] {"#% ", "#& ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.axeA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovelC, 1), new Object[] {"#%#", " & ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.shovelA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.swordC, 1), new Object[] {"#%#", "#&#", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.swordA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoeC, 1), new Object[] {"#% ", " & ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.hoeA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cplate, 1), new Object[] {"#%#", "#%#", "#%#", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cchunk, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.Coraliumcluster9, '%', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.PSDLfinder, 4), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.Coralium, '%', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cingot, 9), new Object[] {"#", '#', AbyssalCraft.corblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corpickaxe, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraxe, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corshovel, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corsword, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhoe, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object[] {" # ", "@%&", '#', AbyssalCraft.EoA, '@', AbyssalCraft.Corb, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.PSDL});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object[] {" # ", "@%&", '#', AbyssalCraft.EoA, '@', AbyssalCraft.PSDL, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object[] {" # ", "@%&", '#', AbyssalCraft.Corb, '@', AbyssalCraft.EoA, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.PSDL});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object[] {" # ", "@%&", '#', AbyssalCraft.Corb, '@', AbyssalCraft.PSDL, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.EoA});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object[] {" # ", "@%&", '#', AbyssalCraft.PSDL, '@', AbyssalCraft.EoA, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object[] {" # ", "@%&", '#', AbyssalCraft.PSDL, '@', AbyssalCraft.Corb, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.EoA});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowshard, 1), new Object[] {"###", "###", "###", '#', AbyssalCraft.shadowfragment});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowgem, 1), new Object[] {"###", "###", "###", '#', AbyssalCraft.shadowshard});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.oblivionshard, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.shadowgem, '%', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.corbow, 1), new Object[] {" #%", "&$%", " #%", '#', AbyssalCraft.Cingot, '%', Items.string, '&', AbyssalCraft.Cpearl, '$', Items.bow});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumingot, 9), new Object[] {"#", '#', AbyssalCraft.dreadiumblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumpickaxe, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumaxe, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumshovel, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumsword, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumhoe, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.dreadiumingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.carbonCluster, 1), new Object[] {"###", "# #", "###", '#', AbyssalCraft.crystalCarbon});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.denseCarbonCluster, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.carbonCluster, '%', Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"#@%", "$&$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"@%&", "$#$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"%&#", "$@$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] {"&#@", "$%$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadcloth, 1), new Object[] {"#%#", "%&%", "#%#", '#', Items.string, '%', AbyssalCraft.dreadfragment, '&', Items.leather});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadcloth, 1), new Object[] {"#%#", "%&%", "#%#", '%', Items.string, '#', AbyssalCraft.dreadfragment, '&', Items.leather});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadplate, 1), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadcloth});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadblade, 1), new Object[] {"##", "##", "##", '#', AbyssalCraft.crystalDreadium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadhilt, 1), new Object[] {"###", "%&%", "%&%", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadcloth, '&', AbyssalCraft.dreadplanks});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadkatana, 1), new Object[] {"#", "%", '#', AbyssalCraft.dreadblade, '%', AbyssalCraft.dreadhilt});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.gunpowder, 4), true, new Object[] {"#&#", "#%#", "###", '#', "materialSaltpeter", '%', new ItemStack(Items.coal, 1, 1), '&', "materialSulfur"}));
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalMethane), new Object[] {" # ", "#%#", " # ", '#', AbyssalCraft.crystalHydrogen, '%', AbyssalCraft.crystalCarbon});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalNitrate), new Object[] {" # ", "%%%", '#', AbyssalCraft.crystalNitrogen, '%', AbyssalCraft.crystalOxygen});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalAlumina), new Object[] {" # ", "%%%", " # ", '#', AbyssalCraft.crystalAluminium, '%', AbyssalCraft.crystalOxygen});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalSilica), new Object[] {"#%#", '#', AbyssalCraft.crystalOxygen, '%', AbyssalCraft.crystalSilicon});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalMagnesia), new Object[] {"#%", '#', AbyssalCraft.crystalMagnesium, '%', AbyssalCraft.crystalOxygen});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] {"###", "#%#", "###", '#', AbyssalCraft.ethaxium_brick, '%', AbyssalCraft.lifeCrystal});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] {" # ", "#%#", " # ", '#', AbyssalCraft.ethaxium_brick, '%', AbyssalCraft.OC});
		GameRegistry.addRecipe(new ItemStack(Items.spawn_egg, 1, 320), new Object[] {"#", '#', AbyssalCraft.ODB});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot, 9), new Object[] {"#", '#', AbyssalCraft.ethaxiumblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethPickaxe, 1), new Object[] {"###", " % ", " % ", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethAxe, 1), new Object[] {"##", "#%", " %", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethShovel, 1), new Object[] {"#", "%", "%", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethSword, 1), new Object[] {"#", "#", "%", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethHoe, 1), new Object[] {"##", " %", " %", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1, 0), true, new Object[] {" # ", "# #", " # ", '#', "ingotCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1, 1), true, new Object[] {" # ", "# #", " # ", '#', "ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1, 2), true, new Object[] {" # ", "# #", " # ", '#', "ingotGold"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1, 3), true, new Object[] {" # ", "# #", " # ", '#', "ingotTin"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1, 4), true, new Object[] {" # ", "# #", " # ", '#', "ingotAbyssalnite"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1, 5), true, new Object[] {" # ", "# #", " # ", '#', "ingotCoralium"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1, 6), true, new Object[] {" # ", "# #", " # ", '#', "ingotDreadium"}));

		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.lifeCrystal), AbyssalCraft.crystalCarbon, AbyssalCraft.crystalHydrogen, AbyssalCraft.crystalNitrogen, AbyssalCraft.crystalOxygen, AbyssalCraft.crystalPhosphorus, AbyssalCraft.crystalSulfur);

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

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.boots, 1), new Object[] {"# #", "# #", '#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.helmet, 1), new Object[] {"###", "# #", '#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.plate, 1), new Object[] {"# #", "###", "###",'#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.legs, 1), new Object[] {"###", "# #", "# #",'#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.bootsC, 1), new Object[] {"#%#", "#&#", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.boots});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.helmetC, 1), new Object[] {"###", "#%#", " & ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.helmet});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.plateC, 1), new Object[] {"#%#", "#&#", "###", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.plate, '&', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.legsC, 1), new Object[] {"#%#", "#&#", "# #", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.legs});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corboots, 1), new Object[] {"# #", "# #", '#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhelmet, 1), new Object[] {"###", "# #", '#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corplate, 1), new Object[] {"# #", "###", "###",'#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corlegs, 1), new Object[] {"###", "# #", "# #",'#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorbootsP, 1), new Object[] {"# #", "%&%", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corboots});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorhelmetP, 1), new Object[] {"&#&", "#@#", "%%%", '#', AbyssalCraft.Cplate, '&', AbyssalCraft.Cpearl, '@', AbyssalCraft.Corhelmet, '%', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorplateP, 1), new Object[] {"# #", "%@%", "%#%",'#', AbyssalCraft.Cplate, '%', AbyssalCraft.Cingot, '@', AbyssalCraft.Corplate});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorlegsP, 1), new Object[] {"%&%", "# #", "# #",'#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corlegs});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumboots, 1), new Object[] {"# #", "# #", '#', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumhelmet, 1), new Object[] {"###", "# #", '#', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumplate, 1), new Object[] {"# #", "###", "###",'#', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumlegs, 1), new Object[] {"###", "# #", "# #",'#', AbyssalCraft.dreadiumingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSboots, 1), new Object[] {"#%#", "&&&", '#', AbyssalCraft.dreadcloth, '%', AbyssalCraft.dreadiumboots, '&', AbyssalCraft.dreadplanks});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumShelmet, 1), new Object[] {" # ", "%&%", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadplate, '&', AbyssalCraft.dreadiumhelmet});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSplate, 1), new Object[] {"#%#", "#&#", "@@@", '#', AbyssalCraft.dreadplate, '%', AbyssalCraft.dreadiumingot, '&', AbyssalCraft.dreadiumplate, '@', AbyssalCraft.dreadcloth});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSlegs, 1), new Object[] {"#%#", "&&&", '#', AbyssalCraft.dreadplate, '%', AbyssalCraft.dreadiumlegs, '&', AbyssalCraft.dreadcloth});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethBoots, 1), new Object[] {"# #", "# #", '#', AbyssalCraft.ethaxiumIngot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethHelmet, 1), new Object[] {"###", "# #", '#', AbyssalCraft.ethaxiumIngot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethPlate, 1), new Object[] {"# #", "###", "###",'#', AbyssalCraft.ethaxiumIngot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethLegs, 1), new Object[] {"###", "# #", "# #",'#', AbyssalCraft.ethaxiumIngot});

		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthsboots, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corboots, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corb);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthshelmet, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corhelmet, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Cbucket, AbyssalCraft.Corb);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthsplate, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corplate, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corb);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthslegs, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corlegs, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corb);

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ironp, 2), new Object[] {"#", "#", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cloth, 1), new Object[] {"###", "#%#", "###", '#', Blocks.web, '%', Blocks.wool});

		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.MRE, 1), AbyssalCraft.ironp, Items.carrot, Items.potato, Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.chickenp, 1), AbyssalCraft.ironp, Items.cooked_chicken);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.porkp, 1), AbyssalCraft.ironp, Items.cooked_porkchop);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.beefp, 1), AbyssalCraft.ironp, Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.fishp, 1), AbyssalCraft.ironp, Items.cooked_fished);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.eggp, 1), AbyssalCraft.ironp, AbyssalCraft.friedegg);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.ironp, 1, 0), AbyssalCraft.dirtyplate, new ItemStack(AbyssalCraft.cloth,1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.CobbleU, 4), Blocks.planks, Blocks.cobblestone, Blocks.cobblestone, Items.string, Items.flint);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.IronU, 1), Blocks.cobblestone, Items.iron_ingot, Items.iron_ingot, AbyssalCraft.CobbleU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.GoldU, 1), Items.iron_ingot, Items.gold_ingot, Items.gold_ingot, AbyssalCraft.IronU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.DiamondU, 1), Items.gold_ingot, Items.diamond, Items.diamond, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.AbyssalniteU, 1), Items.diamond, AbyssalCraft.abyingot, AbyssalCraft.abyingot, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.CoraliumU, 1), AbyssalCraft.abyingot, AbyssalCraft.Cingot, AbyssalCraft.Cingot, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.DreadiumU, 1), AbyssalCraft.Cingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.EthaxiumU, 1), AbyssalCraft.dreadiumingot, AbyssalCraft.ethaxiumIngot, AbyssalCraft.ethaxiumIngot, AbyssalCraft.DreadiumU);

		//Wood to Cobble Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.stone_pickaxe, 1), new Object[] {"#", "@", '#', Items.wooden_pickaxe, '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe, 1), new Object[] {"#", "@", '#', Items.wooden_axe, '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel, 1), new Object[] {"#", "@", '#', Items.wooden_shovel, '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword, 1), new Object[] {"#", "@", '#', Items.wooden_sword, '@', AbyssalCraft.CobbleU});
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe, 1), new Object[] {"#", "@", '#', Items.wooden_hoe, '@', AbyssalCraft.CobbleU});

		//Stone to Iron Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.iron_pickaxe, 1), new Object[] {"#", "@", '#', Items.stone_pickaxe, '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_axe, 1), new Object[] {"#", "@", '#', Items.stone_axe, '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_shovel, 1), new Object[] {"#", "@", '#', Items.stone_shovel, '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_sword, 1), new Object[] {"#", "@", '#', Items.stone_sword, '@', AbyssalCraft.IronU});
		GameRegistry.addRecipe(new ItemStack(Items.iron_hoe, 1), new Object[] {"#", "@", '#', Items.stone_hoe, '@', AbyssalCraft.IronU});

		//Iron to Gold Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.golden_pickaxe, 1), new Object[] {"#", "@", '#', Items.iron_pickaxe, '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_axe, 1), new Object[] {"#", "@", '#', Items.iron_axe, '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_shovel, 1), new Object[] {"#", "@", '#', Items.iron_shovel, '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_sword, 1), new Object[] {"#", "@", '#', Items.iron_sword, '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_hoe, 1), new Object[] {"#", "@", '#', Items.iron_hoe, '@', AbyssalCraft.GoldU});

		//Gold to Diamond Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.diamond_pickaxe, 1), new Object[] {"#", "@", '#', Items.golden_pickaxe, '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_axe, 1), new Object[] {"#", "@", '#', Items.golden_axe, '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_shovel, 1), new Object[] {"#", "@", '#', Items.golden_shovel, '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_sword, 1), new Object[] {"#", "@", '#', Items.golden_sword, '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_hoe, 1), new Object[] {"#", "@", '#', Items.golden_hoe, '@', AbyssalCraft.DiamondU});

		//Diamond to Abyssalnite Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxeA, 1), new Object[] {"#", "@", '#', Items.diamond_pickaxe, '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axeA, 1), new Object[] {"#", "@", '#', Items.diamond_axe, '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovelA, 1), new Object[] {"#", "@", '#', Items.diamond_shovel, '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.swordA, 1), new Object[] {"#", "@", '#', Items.diamond_sword, '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoeA, 1), new Object[] {"#", "@", '#', Items.diamond_hoe, '@', AbyssalCraft.AbyssalniteU});

		//Abyssalnite to Coralium Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corpickaxe, 1), new Object[] {"#", "@", '#', AbyssalCraft.pickaxeA, '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraxe, 1), new Object[] {"#", "@", '#', AbyssalCraft.axeA, '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corshovel, 1), new Object[] {"#", "@", '#', AbyssalCraft.shovelA, '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corsword, 1), new Object[] {"#", "@", '#', AbyssalCraft.swordA, '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhoe, 1), new Object[] {"#", "@", '#', AbyssalCraft.hoeA, '@', AbyssalCraft.CoraliumU});

		//Coralium to Dreadium Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumpickaxe, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corpickaxe, '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumaxe, 1), new Object[] {"#", "@", '#', AbyssalCraft.Coraxe, '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumshovel, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corshovel, '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumsword, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corsword, '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumhoe, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corhoe, '@', AbyssalCraft.DreadiumU});

		//Dreadium to Ethaxium Upgrade
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethPickaxe, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumpickaxe, '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethAxe, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumaxe, '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethShovel, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumshovel, '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethSword, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumsword, '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethHoe, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumhoe, '@', AbyssalCraft.EthaxiumU});

		//Iron to Gold armor
		GameRegistry.addRecipe(new ItemStack(Items.golden_helmet, 1), new Object[] {"#", "@", '#', Items.iron_helmet, '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_chestplate, 1), new Object[] {"#", "@", '#', Items.iron_chestplate, '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_leggings, 1), new Object[] {"#", "@", '#', Items.iron_leggings, '@', AbyssalCraft.GoldU});
		GameRegistry.addRecipe(new ItemStack(Items.golden_boots, 1), new Object[] {"#", "@", '#', Items.iron_boots, '@', AbyssalCraft.GoldU});

		//Gold to Diamond armor
		GameRegistry.addRecipe(new ItemStack(Items.diamond_helmet, 1), new Object[] {"#", "@", '#', Items.golden_helmet, '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_chestplate, 1), new Object[] {"#", "@", '#', Items.golden_chestplate, '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_leggings, 1), new Object[] {"#", "@", '#', Items.golden_leggings, '@', AbyssalCraft.DiamondU});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_boots, 1), new Object[] {"#", "@", '#', Items.golden_boots, '@', AbyssalCraft.DiamondU});

		//Diamond to Abyssalnite armor
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.helmet, 1), new Object[] {"#", "@", '#', Items.diamond_helmet, '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.plate, 1), new Object[] {"#", "@", '#', Items.diamond_chestplate, '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.legs, 1), new Object[] {"#", "@", '#', Items.diamond_leggings, '@', AbyssalCraft.AbyssalniteU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.boots, 1), new Object[] {"#", "@", '#', Items.diamond_boots, '@', AbyssalCraft.AbyssalniteU});

		//Abyssalnite to Coralium armor
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhelmet, 1), new Object[] {"#", "@", '#', AbyssalCraft.helmet, '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corplate, 1), new Object[] {"#", "@", '#', AbyssalCraft.plate, '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corlegs, 1), new Object[] {"#", "@", '#', AbyssalCraft.legs, '@', AbyssalCraft.CoraliumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corboots, 1), new Object[] {"#", "@", '#', AbyssalCraft.boots, '@', AbyssalCraft.CoraliumU});

		//Coralium to Dreadium armor
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumhelmet, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corhelmet, '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumplate, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corplate, '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumlegs, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corlegs, '@', AbyssalCraft.DreadiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumboots, 1), new Object[] {"#", "@", '#', AbyssalCraft.Corboots, '@', AbyssalCraft.DreadiumU});

		//Dreadium to Ethaxium armor
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethHelmet, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumhelmet, '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethPlate, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumplate, '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethLegs, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumlegs, '@', AbyssalCraft.EthaxiumU});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethBoots, 1), new Object[] {"#", "@", '#', AbyssalCraft.dreadiumboots, '@', AbyssalCraft.EthaxiumU});
	}

	private static void addItemSmelting(){

		GameRegistry.addSmelting(AbyssalCraft.abychunk, new ItemStack(AbyssalCraft.abyingot), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Cchunk, new ItemStack(AbyssalCraft.Cingot, 2), 3F);
		GameRegistry.addSmelting(Items.egg, new ItemStack(AbyssalCraft.friedegg, 1), 0F);
		GameRegistry.addSmelting(AbyssalCraft.dreadchunk, new ItemStack(AbyssalCraft.abyingot), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Cbucket, new ItemStack(AbyssalCraft.cstone, 1), 0.2F);
	}

	private static void addSalvage(){

		SalvageHandler.INSTANCE.addBootsSalvage(Items.leather_boots, Items.leather);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.leather_helmet, Items.leather);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.leather_chestplate, Items.leather);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.leather_leggings, Items.leather);

		SalvageHandler.INSTANCE.addBootsSalvage(Items.iron_boots, Items.iron_ingot);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.iron_helmet, Items.iron_ingot);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.iron_chestplate, Items.iron_ingot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.iron_leggings, Items.iron_ingot);

		SalvageHandler.INSTANCE.addBootsSalvage(Items.golden_boots, Items.gold_ingot);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.golden_helmet, Items.gold_ingot);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.golden_chestplate, Items.gold_ingot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.golden_leggings, Items.gold_ingot);

		SalvageHandler.INSTANCE.addBootsSalvage(Items.diamond_boots, Items.diamond);
		SalvageHandler.INSTANCE.addHelmetSalvage(Items.diamond_helmet, Items.diamond);
		SalvageHandler.INSTANCE.addChestplateSalvage(Items.diamond_chestplate, Items.diamond);
		SalvageHandler.INSTANCE.addLeggingsSalvage(Items.diamond_leggings, Items.diamond);

		SalvageHandler.INSTANCE.addBootsSalvage(AbyssalCraft.boots, AbyssalCraft.abyingot);
		SalvageHandler.INSTANCE.addHelmetSalvage(AbyssalCraft.helmet, AbyssalCraft.abyingot);
		SalvageHandler.INSTANCE.addChestplateSalvage(AbyssalCraft.plate, AbyssalCraft.abyingot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(AbyssalCraft.legs, AbyssalCraft.abyingot);

		SalvageHandler.INSTANCE.addBootsSalvage(AbyssalCraft.Corboots, AbyssalCraft.Cingot);
		SalvageHandler.INSTANCE.addHelmetSalvage(AbyssalCraft.Corhelmet, AbyssalCraft.Cingot);
		SalvageHandler.INSTANCE.addChestplateSalvage(AbyssalCraft.Corplate, AbyssalCraft.Cingot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(AbyssalCraft.Corlegs, AbyssalCraft.Cingot);

		SalvageHandler.INSTANCE.addBootsSalvage(AbyssalCraft.dreadiumboots, AbyssalCraft.dreadiumingot);
		SalvageHandler.INSTANCE.addHelmetSalvage(AbyssalCraft.dreadiumhelmet, AbyssalCraft.dreadiumingot);
		SalvageHandler.INSTANCE.addChestplateSalvage(AbyssalCraft.dreadiumplate, AbyssalCraft.dreadiumingot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(AbyssalCraft.dreadiumlegs, AbyssalCraft.dreadiumingot);

		SalvageHandler.INSTANCE.addBootsSalvage(AbyssalCraft.ethBoots, AbyssalCraft.ethaxiumIngot);
		SalvageHandler.INSTANCE.addHelmetSalvage(AbyssalCraft.ethHelmet, AbyssalCraft.ethaxiumIngot);
		SalvageHandler.INSTANCE.addChestplateSalvage(AbyssalCraft.ethPlate, AbyssalCraft.ethaxiumIngot);
		SalvageHandler.INSTANCE.addLeggingsSalvage(AbyssalCraft.ethLegs, AbyssalCraft.ethaxiumIngot);
	}

	private static void addCrystallization(){

		CoreRegistry.addSingleCrystallization(AbyssalCraft.Cwater, new ItemStack(AbyssalCraft.crystalCoralium, 2), 0.4F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.Cbucket, new ItemStack(AbyssalCraft.crystalCoralium, 2), 0.2F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.Cingot, new ItemStack(AbyssalCraft.crystalCoralium), 0.1F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.Cchunk, new ItemStack(AbyssalCraft.crystalCoralium), 0.1F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.AbyLCorOre, new ItemStack(AbyssalCraft.crystalCoralium), 0.1F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.abyingot, new ItemStack(AbyssalCraft.crystalAbyssalnite), 0.1F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.abychunk, new ItemStack(AbyssalCraft.crystalAbyssalnite), 0.1F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.dreadiumingot, new ItemStack(AbyssalCraft.crystalDreadium), 0.1F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.sulfur, new ItemStack(AbyssalCraft.crystalSulfur), 0.1F);
		CoreRegistry.addSingleCrystallization(Items.iron_ingot, new ItemStack(AbyssalCraft.crystalIron), 0.1F);
		CoreRegistry.addSingleCrystallization(Items.gold_ingot, new ItemStack(AbyssalCraft.crystalGold), 0.1F);
		CoreRegistry.addSingleCrystallization(Items.redstone, new ItemStack(AbyssalCraft.crystalRedstone), 0.1F);
		CoreRegistry.addSingleCrystallization(Items.coal, new ItemStack(AbyssalCraft.crystalCarbon), 0.1F);
		CoreRegistry.addSingleCrystallization(Items.blaze_powder, new ItemStack(AbyssalCraft.crystalBlaze), 0.1F);
		CoreRegistry.addSingleCrystallization(new ItemStack(Items.dye, 1, 15), new ItemStack(AbyssalCraft.crystalPhosphorus), 0.0F);
		CoreRegistry.addSingleCrystallization("oreAbyssalnite", "crystalAbyssalnite", 0.1F);
		CoreRegistry.addSingleCrystallization("oreCoralium", "crystalCoralium", 0.1F);
		CoreRegistry.addSingleCrystallization("oreIron", "crystalIron", 0.1F);
		CoreRegistry.addSingleCrystallization("oreGold", "crystalGold", 0.1F);
		CoreRegistry.addSingleCrystallization("ingotTin", "crystalTin", 0.1F);
		CoreRegistry.addSingleCrystallization("oreTin", "crystalTin", 0.1F);
		CoreRegistry.addSingleCrystallization("ingotCopper", "crystalCopper", 0.1F);
		CoreRegistry.addSingleCrystallization("oreCopper", "crystalCopper", 0.1F);
		CoreRegistry.addSingleCrystallization("ingotAluminum", "crystalAluminium", 0.1F);
		CoreRegistry.addSingleCrystallization("ingotAluminium", "crystalAluminium", 0.1F);
		CoreRegistry.addSingleCrystallization("oreAluminum", "crystalAluminium", 0.1F);
		CoreRegistry.addSingleCrystallization("blockCopper", "crystalCopper", 9, 0.9F);
		CoreRegistry.addSingleCrystallization("blockTin", "crystalTin", 9, 0.9F);
		CoreRegistry.addSingleCrystallization(Blocks.gold_block, new ItemStack(AbyssalCraft.crystalGold, 9), 0.9F);
		CoreRegistry.addSingleCrystallization(Blocks.iron_block, new ItemStack(AbyssalCraft.crystalIron, 9), 0.9F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.abyblock, new ItemStack(AbyssalCraft.crystalAbyssalnite, 9), 0.9F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.corblock, new ItemStack(AbyssalCraft.crystalCoralium, 9), 0.9F);
		CoreRegistry.addSingleCrystallization(AbyssalCraft.dreadiumblock, new ItemStack(AbyssalCraft.crystalDreadium, 9), 0.9F);
		CoreRegistry.addSingleCrystallization(Blocks.coal_ore, new ItemStack(AbyssalCraft.crystalCarbon), 0.1F);
		CoreRegistry.addSingleCrystallization(Blocks.coal_block, new ItemStack(AbyssalCraft.crystalCarbon, 9), 0.9F);
		CoreRegistry.addSingleCrystallization(Blocks.redstone_ore, new ItemStack(AbyssalCraft.crystalRedstone), 0.1F);
		CoreRegistry.addSingleCrystallization(Blocks.redstone_block, new ItemStack(AbyssalCraft.crystalRedstone, 9), 0.9F);
		CoreRegistry.addSingleCrystallization("ingotZinc", "crystalZinc", 0.1F);
		CoreRegistry.addSingleCrystallization("oreZinc", "crystalZinc", 0.1F);
		CoreRegistry.addCrystallization(AbyssalCraft.dreadchunk, new ItemStack(AbyssalCraft.crystalAbyssalnite), new ItemStack(AbyssalCraft.crystalDreadium), 0.2F);
		CoreRegistry.addCrystallization(AbyssalCraft.dreadore, new ItemStack(AbyssalCraft.crystalAbyssalnite), new ItemStack(AbyssalCraft.crystalDreadium), 0.2F);
		CoreRegistry.addCrystallization(Blocks.water, new ItemStack(AbyssalCraft.crystalHydrogen, 4), new ItemStack(AbyssalCraft.crystalOxygen, 2), 0.4F);
		CoreRegistry.addCrystallization(Items.water_bucket, new ItemStack(AbyssalCraft.crystalHydrogen, 4), new ItemStack(AbyssalCraft.crystalOxygen, 2), 0.1F);
		CoreRegistry.addCrystallization(new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.crystalHydrogen, 2), new ItemStack(AbyssalCraft.crystalOxygen), 0.1F);
		CoreRegistry.addCrystallization(new ItemStack(Items.dye, 1, 4), new ItemStack(AbyssalCraft.crystalSilica, 6), new ItemStack(AbyssalCraft.crystalSulfur, 4), 0.15F);
		CoreRegistry.addCrystallization(AbyssalCraft.methane, new ItemStack(AbyssalCraft.crystalCarbon), new ItemStack(AbyssalCraft.crystalHydrogen, 4), 0.1F);
		CoreRegistry.addCrystallization(Items.gunpowder, new ItemStack(AbyssalCraft.crystalNitrate, 4), new ItemStack(AbyssalCraft.crystalSulfur), 0.1F);
		CoreRegistry.addCrystallization("materialSaltpeter", "crystalPotassium", "crystalNitrate", 0.1F);
		CoreRegistry.addCrystallization("oreSaltpeter", "crystalPotassium", "crystalNitrate", 0.1F);
		CoreRegistry.addCrystallization(Blocks.obsidian, new ItemStack(AbyssalCraft.crystalSilica), new ItemStack(AbyssalCraft.crystalMagnesia), 0.1F);
		CoreRegistry.addCrystallization(Blocks.stone, new ItemStack(AbyssalCraft.crystalSilica), new ItemStack(AbyssalCraft.crystalAlumina), 0.1F);
		CoreRegistry.addCrystallization(AbyssalCraft.crystalSilica, new ItemStack(AbyssalCraft.crystalSilicon), new ItemStack(AbyssalCraft.crystalOxygen, 2), 0.1F);
		CoreRegistry.addCrystallization(AbyssalCraft.crystalAlumina, new ItemStack(AbyssalCraft.crystalAluminium, 2), new ItemStack(AbyssalCraft.crystalOxygen, 3), 0.1F);
		CoreRegistry.addCrystallization(AbyssalCraft.crystalMagnesia, new ItemStack(AbyssalCraft.crystalMagnesium), new ItemStack(AbyssalCraft.crystalOxygen), 0.1F);
		CoreRegistry.addCrystallization("ingotBronze", "crystalCopper", 1, "crystalTin", 3, 0.4F);
		CoreRegistry.addCrystallization(AbyssalCraft.crystalMethane, new ItemStack(AbyssalCraft.crystalCarbon), new ItemStack(AbyssalCraft.crystalHydrogen), 0.1F);
		CoreRegistry.addCrystallization(AbyssalCraft.crystalNitrate, new ItemStack(AbyssalCraft.crystalNitrogen), new ItemStack(AbyssalCraft.crystalOxygen, 3), 0.1F);
		CoreRegistry.addCrystallization(Blocks.lapis_ore, new ItemStack(AbyssalCraft.crystalSilica, 6), new ItemStack(AbyssalCraft.crystalSulfur, 4), 0.15F);
		CoreRegistry.addCrystallization(Blocks.lapis_block, new ItemStack(AbyssalCraft.crystalSilica, 24), new ItemStack(AbyssalCraft.crystalSulfur, 16), 1.0F);
		CoreRegistry.addCrystallization("ingotBrass", "crystalCopper", 3, "crystalZinc", 2, 0.5F);
		CoreRegistry.addCrystallization("oreBrass", "crystalCopper", 3, "crystalZinc", 2, 0.5F);

		//Crystallization for dusts
		CoreRegistry.addSingleCrystallization("dustIron", "crystalIron", 0.1F);
		CoreRegistry.addSingleCrystallization("dustGold", "crystalGold", 0.1F);
		CoreRegistry.addSingleCrystallization("dustTin", "crystalTin", 0.1F);
		CoreRegistry.addSingleCrystallization("dustCopper", "crystalCopper", 0.1F);
		CoreRegistry.addSingleCrystallization("dustCoal", "crystalCarbon", 0.1F);
		CoreRegistry.addSingleCrystallization("dustAluminium", "crystalAluminium", 0.1F);
		CoreRegistry.addCrystallization("dustBronze", "crystalCopper", 1, "crystalTin", 3, 0.1F);
		CoreRegistry.addCrystallization("dustBrass", "crystalCopper", 3, "crystalZinc", 2, 0.1F);
	}

	private static void addTransmutation(){

		CoreRegistry.addTransmutation(AbyssalCraft.crystalAbyssalnite, new ItemStack(AbyssalCraft.abyingot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalCoralium, new ItemStack(AbyssalCraft.Cingot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalDreadium, new ItemStack(AbyssalCraft.dreadiumingot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.Dreadshard, new ItemStack(AbyssalCraft.dreadiumingot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalIron, new ItemStack(Items.iron_ingot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalGold, new ItemStack(Items.gold_ingot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalSulfur, new ItemStack(AbyssalCraft.sulfur, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalBlaze, new ItemStack(Items.blaze_powder, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalRedstone, new ItemStack(Items.redstone, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalHydrogen, new ItemStack(AbyssalCraft.crystalHydrogen), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalOxygen, new ItemStack(AbyssalCraft.crystalOxygen), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalNitrogen, new ItemStack(AbyssalCraft.crystalNitrogen), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalMethane, new ItemStack(AbyssalCraft.methane, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalTin, new ItemStack(AbyssalCraft.tinIngot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.crystalCopper, new ItemStack(AbyssalCraft.copperIngot, 1), 0.2F);
		CoreRegistry.addTransmutation(AbyssalCraft.Darkstone, new ItemStack(Blocks.stone, 1), 0.0F);
		CoreRegistry.addTransmutation(Blocks.stone, new ItemStack(AbyssalCraft.Darkstone, 1), 0.0F);
		CoreRegistry.addTransmutation(Blocks.stonebrick, new ItemStack(AbyssalCraft.Darkstone_brick, 1), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.Darkstone_brick, new ItemStack(Blocks.stonebrick), 0.0F);
		CoreRegistry.addTransmutation(Blocks.cobblestone, new ItemStack(AbyssalCraft.Darkstone_cobble, 1), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.Darkstone_cobble, new ItemStack(Blocks.cobblestone, 1), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.cstonebrick, new ItemStack(AbyssalCraft.cbrick, 4), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.cbrick, new ItemStack(AbyssalCraft.cstone, 1), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.Cbucket, new ItemStack(AbyssalCraft.Cwater, 1), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.Cwater, new ItemStack(AbyssalCraft.cstone, 8), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.antibucket, new ItemStack(AbyssalCraft.anticwater, 1), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.denseCarbonCluster, new ItemStack(Items.diamond), 0.5F);
		CoreRegistry.addTransmutation(AbyssalCraft.dreadKey, new ItemStack(AbyssalCraft.portalPlacerJzh), 1.0F);
		CoreRegistry.addTransmutation("crystalAluminium", "ingotAluminum", 0.2F);
		CoreRegistry.addTransmutation("crystalAluminium", "ingotAluminium", 0.2F);
		CoreRegistry.addTransmutation("crystalZinc", "ingotZinc", 0.2F);
		CoreRegistry.addTransmutation(Blocks.lava, new ItemStack(AbyssalCraft.solidLava), 0.0F);
		CoreRegistry.addTransmutation(Blocks.end_stone, new ItemStack(AbyssalCraft.ethaxium), 0.0F);
		CoreRegistry.addTransmutation(AbyssalCraft.ethaxium, new ItemStack(Blocks.end_stone), 0.0F);
		CoreRegistry.addTransmutation(new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), new ItemStack(AbyssalCraft.ethaxium), 0.0F);
	}
}