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
package com.shinoow.abyssalcraft.common.lib;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.shinoow.abyssalcraft.AbyssalCraft;

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
	}
	private static void addBlockCrafting(){

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkstone_brick, 4), new Object []{"AA", "AA", 'A', AbyssalCraft.Darkstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkbrickslab1, 6), new Object []{"AAA", 'A', AbyssalCraft.Darkstone_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSGlow, 4), new Object []{"#$#", "&%&", "#&#", '#', AbyssalCraft.Darkstone_brick, '$', Items.diamond,'&', Blocks.obsidian, '%', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkcobbleslab1, 6), new Object []{"AAA", 'A', AbyssalCraft.Darkstone_cobble });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTplank, 4), new Object []{"A", 'A', AbyssalCraft.DLTLog });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ODB, 1), new Object []{"$&$","%#%","$%$",'$', Blocks.obsidian, '&', AbyssalCraft.OC, '%', Blocks.tnt, '#', AbyssalCraft.ODBcore});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyblock, 1), new Object []{"AAA", "AAA", "AAA", 'A', AbyssalCraft.abyingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ODBcore, 1), new Object []{"#&#", "$@$", "#&#", '#', AbyssalCraft.abyblock, '&', Blocks.iron_block, '$', Blocks.emerald_block,'@', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&&&", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coraliumcluster4});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster4});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster4, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coraliumcluster4, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster4, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster4, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&&&", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coraliumcluster5});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster5});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster3, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coraliumcluster3, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster3});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster7, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster7, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster7});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster5});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster6});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coraliumcluster6});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coralium, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coralium, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coraliumcluster2});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster2, '$', AbyssalCraft.Coraliumcluster5, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "&$%", "###", '#', Blocks.stone, '&', AbyssalCraft.Coraliumcluster5, '$', AbyssalCraft.Coraliumcluster2, '%', AbyssalCraft.Coralium});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraliumstone, 1), new Object []{"###", "#%#", "###", '#', Blocks.stone, '%', AbyssalCraft.Coraliumcluster9});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abybrick, 4), new Object []{"##", "##", '#', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyslab1, 6), new Object []{"###", '#', AbyssalCraft.abybrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyfence, 6), new Object []{"###", "###", '#', AbyssalCraft.abybrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSCwall, 6), new Object []{"###", "###", '#', AbyssalCraft.Darkstone_cobble});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Crate, 2), new Object []{"#&#", "&%&", "#&#", '#', Items.stick, '&', Blocks.planks, '%', Blocks.chest});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSbutton, 1), new Object [] {"#", '#', AbyssalCraft.Darkstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSpplate, 1), new Object []{"##", '#', AbyssalCraft.Darkstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTbutton, 1), new Object [] {"#", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTpplate, 1), new Object [] {"##", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(Items.stick, 4), new Object [] {"#", "#", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(Blocks.chest, 1), new Object [] {"###", "# #", "###", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(Blocks.crafting_table, 1), new Object [] {"##", "##", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTslab1, 6), new Object [] {"###", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkstoneslab1,6), new Object [] {"###", '#', AbyssalCraft.Darkstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.corblock, 1), new Object [] {"###", "###", "###", '#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Abybutton, 1), new Object [] {"#", '#', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Abypplate, 1), new Object []{"##", '#', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSBfence, 6), new Object []{"###", "###", '#', AbyssalCraft.Darkstone_brick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTfence, 6), new Object []{"###", "###", '#', AbyssalCraft.DLTplank});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Altar, 1), new Object [] {" # ", "%&%", "%@%", '#', Items.bucket, '%', Items.gold_ingot, '&', AbyssalCraft.Corb, '@', Blocks.enchanting_table});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrick, 4), new Object []{"##", "##", '#', AbyssalCraft.dreadstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrick, 4), new Object []{"##", "##", '#', AbyssalCraft.abydreadstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadplanks, 4), new Object []{"%", '%', AbyssalCraft.dreadlog});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickslab1, 6), new Object []{"###", '#', AbyssalCraft.dreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickfence, 6), new Object []{"###", "###", '#', AbyssalCraft.dreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickslab1, 6), new Object []{"###", '#', AbyssalCraft.abydreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickfence, 6), new Object []{"###", "###", '#', AbyssalCraft.abydreadbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrick, 1), new Object [] {"##", "##", '#', AbyssalCraft.cbrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickslab1, 6), new Object []{"###", '#', AbyssalCraft.cstonebrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickfence, 6), new Object []{"###", "###", '#', AbyssalCraft.cstonebrick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebutton, 1), new Object [] {"#", '#', AbyssalCraft.cstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonepplate, 1), new Object []{"##", '#', AbyssalCraft.cstone});

		//Stairs
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.DBstairs, 4), AbyssalCraft.Darkstone_brick, AbyssalCraft.Darkstone_brick, AbyssalCraft.Darkstone_brick, AbyssalCraft.Darkstone_brick, AbyssalCraft.Darkstone_brick, AbyssalCraft.Darkstone_brick);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.DCstairs, 4 ), AbyssalCraft.Darkstone_cobble , AbyssalCraft.Darkstone_cobble, AbyssalCraft.Darkstone_cobble, AbyssalCraft.Darkstone_cobble, AbyssalCraft.Darkstone_cobble, AbyssalCraft.Darkstone_cobble);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.abystairs, 4), AbyssalCraft.abybrick, AbyssalCraft.abybrick, AbyssalCraft.abybrick, AbyssalCraft.abybrick, AbyssalCraft.abybrick, AbyssalCraft.abybrick);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.DLTstairs, 4), AbyssalCraft.DLTplank, AbyssalCraft.DLTplank, AbyssalCraft.DLTplank, AbyssalCraft.DLTplank, AbyssalCraft.DLTplank, AbyssalCraft.DLTplank);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.dreadbrickstairs, 4), AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.abydreadbrickstairs, 4), AbyssalCraft.abydreadbrick, AbyssalCraft.abydreadbrick, AbyssalCraft.abydreadbrick, AbyssalCraft.abydreadbrick, AbyssalCraft.abydreadbrick, AbyssalCraft.abydreadbrick);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.cstonebrickstairs, 4), AbyssalCraft.cstonebrick, AbyssalCraft.cstonebrick, AbyssalCraft.cstonebrick, AbyssalCraft.cstonebrick, AbyssalCraft.cstonebrick, AbyssalCraft.cstonebrick);

	}

	private static void addBlockSmelting(){

		GameRegistry.addSmelting(AbyssalCraft.Darkstone_cobble, new ItemStack(AbyssalCraft.Darkstone, 1), 0.1F);
		GameRegistry.addSmelting(AbyssalCraft.abyore, new ItemStack(AbyssalCraft.abyingot, 1), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Coraliumore, new ItemStack(AbyssalCraft.Coralium, 1), 3F);
		GameRegistry.addSmelting(AbyssalCraft.DLTLog, new ItemStack(Items.coal, 2), 1F);
		GameRegistry.addSmelting(AbyssalCraft.Coraliumstone, new ItemStack(AbyssalCraft.Cpearl), 3F);
		GameRegistry.addSmelting(AbyssalCraft.AbyCorOre, new ItemStack(AbyssalCraft.Cingot, 2), 3F);
		GameRegistry.addSmelting(AbyssalCraft.dreadore, new ItemStack(AbyssalCraft.abyingot, 1), 3F);
		GameRegistry.addSmelting(AbyssalCraft.abydreadore, new ItemStack(AbyssalCraft.abyingot, 1), 3F);
		GameRegistry.addSmelting(AbyssalCraft.cstone, new ItemStack(AbyssalCraft.cbrick, 1), 0.1F);
	}

	private static void addItemCrafting(){

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.devsword, 1), new Object [] {"#", '#', AbyssalCraft.devsword}); //You ain't seen nothing

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxe, 1), new Object [] {"###", " % ", " % ", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axe, 1), new Object [] {"##", "#%", " %", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovel, 1), new Object [] {"#", "%", "%", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.sword, 1), new Object [] {"#", "#", "%", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoe, 1), new Object [] {"##", " %", " %", '#', AbyssalCraft.Darkstone_cobble, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object [] {"@#@", "#$#", "@#@", '$', Items.ender_eye, '#', AbyssalCraft.oblivionshard, '@', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object [] {"@#@", "#$#", "@#@", '$', Items.ender_eye, '#', Items.redstone, '@', AbyssalCraft.oblivionshard});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object [] {"@#@", "#$#", "@#@", '$', Items.nether_star, '#', Items.ender_eye, '@', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.OC, 1), new Object [] {"@#@", "#$#", "@#@", '$', Items.nether_star, '#', Items.redstone, '@', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxeA, 1), new Object [] {"###", " % ", " % ", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axeA, 1), new Object [] {"##", "#%", " %", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovelA, 1), new Object [] {"#", "%", "%", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.swordA, 1), new Object [] {"#", "#", "%", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoeA, 1), new Object [] {"##", " %", " %", '#', AbyssalCraft.abyingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacer, 1), new Object []{" #%", " &#", "&  ", '#', AbyssalCraft.Cpearl, '%', AbyssalCraft.OC, '&', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyingot, 9), new Object [] {"#", '#', AbyssalCraft.abyblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corb, 1), new Object [] {"#%#", "@$@", "#%#", '#', Items.blaze_powder, '%', Items.diamond, '@', Items.ender_pearl, '$', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corb, 1), new Object [] {"#%#", "@$@", "#%#", '#', Items.blaze_powder, '%', Items.ender_pearl, '@', Items.diamond, '$', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.pickaxeC, 1), new Object [] {"#%#", " & ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.pickaxeA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.axeC, 1), new Object [] {"#% ", "#& ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.axeA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shovelC, 1), new Object [] {"#%#", " & ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.shovelA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.swordC, 1), new Object [] {"#%#", "#&#", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.swordA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.hoeC, 1), new Object [] {"#% ", " & ", " @ ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.hoeA, '@', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cplate, 1), new Object [] {"#%#", "#%#", "#%#", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cpearl});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cchunk, 1), new Object [] {"###", "#%#", "###", '#', AbyssalCraft.Coraliumcluster9, '%', AbyssalCraft.abystone});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.PSDLfinder, 4), new Object [] {"###", "#%#", "###", '#', AbyssalCraft.Coralium, '%', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cingot, 9), new Object [] {"#", '#', AbyssalCraft.corblock});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corpickaxe, 1), new Object [] {"###", " % ", " % ", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraxe, 1), new Object [] {"##", "#%", " %", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corshovel, 1), new Object [] {"#", "%", "%", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corsword, 1), new Object [] {"#", "#", "%", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhoe, 1), new Object [] {"##", " %", " %", '#', AbyssalCraft.Cingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object [] {" # ", "@%&", '#', AbyssalCraft.EoA, '@', AbyssalCraft.Corb, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.PSDL});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object [] {" # ", "@%&", '#', AbyssalCraft.EoA, '@', AbyssalCraft.PSDL, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object [] {" # ", "@%&", '#', AbyssalCraft.Corb, '@', AbyssalCraft.EoA, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.PSDL});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object [] {" # ", "@%&", '#', AbyssalCraft.Corb, '@', AbyssalCraft.PSDL, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.EoA});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object [] {" # ", "@%&", '#', AbyssalCraft.PSDL, '@', AbyssalCraft.EoA, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacerDL, 1), new Object [] {" # ", "@%&", '#', AbyssalCraft.PSDL, '@', AbyssalCraft.Corb, '%', AbyssalCraft.portalPlacer, '&', AbyssalCraft.EoA});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowshard, 1), new Object [] {"###", "###", "###", '#', AbyssalCraft.shadowfragment});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowgem, 1), new Object [] {"###", "###", "###", '#', AbyssalCraft.shadowshard});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.oblivionshard, 1), new Object [] {"###", "#%#", "###", '#', AbyssalCraft.shadowgem, '%', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.corbow, 1), new Object [] {" #%", "&$%", " #%", '#', AbyssalCraft.Cingot, '%', Items.string, '&', AbyssalCraft.Cpearl, '$', Items.bow});

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

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.boots, 1), new Object [] {"# #", "# #", '#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.helmet, 1), new Object [] {"###", "# #", '#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.plate, 1), new Object [] {"# #", "###", "###",'#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.legs, 1), new Object [] {"###", "# #", "# #",'#', AbyssalCraft.abyingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.bootsC, 1), new Object [] {"#%#", "#&#", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.boots});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.helmetC, 1), new Object [] {"###", "#%#", " & ", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.helmet});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.plateC, 1), new Object [] {"#%#", "#&#", "###", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.plate, '&', AbyssalCraft.Corb});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.legsC, 1), new Object [] {"#%#", "#&#", "# #", '#', AbyssalCraft.abyingot, '%', AbyssalCraft.Corb, '&', AbyssalCraft.legs});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corboots, 1), new Object [] {"# #", "# #", '#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhelmet, 1), new Object [] {"###", "# #", '#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corplate, 1), new Object [] {"# #", "###", "###",'#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corlegs, 1), new Object [] {"###", "# #", "# #",'#', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorbootsP, 1), new Object [] {"# #", "%&%", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corboots});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorhelmetP, 1), new Object [] {"&#&", "#@#", "%%%", '#', AbyssalCraft.Cplate, '&', AbyssalCraft.Cpearl, '@', AbyssalCraft.Corhelmet, '%', AbyssalCraft.Cingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorplateP, 1), new Object [] {"# #", "%@%", "%#%",'#', AbyssalCraft.Cplate, '%', AbyssalCraft.Cingot, '@', AbyssalCraft.Corplate});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorlegsP, 1), new Object [] {"%&%", "# #", "# #",'#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corlegs});

		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthsboots, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corboots, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corb);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthshelmet, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corhelmet, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Cbucket, AbyssalCraft.Corb);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthsplate, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corplate, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corb);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Depthslegs, 1), AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corlegs, Blocks.vine, Blocks.waterlily, AbyssalCraft.Cchunk, AbyssalCraft.Corflesh, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Corb);

		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ironp, 2), new Object [] {"#", "#", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cloth, 1), new Object [] {"###", "#%#", "###", '#', Blocks.web, '%', Blocks.wool});

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

		//Wood to Cobble Upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_pickaxe, 1), Items.wooden_pickaxe, AbyssalCraft.CobbleU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_axe, 1), Items.wooden_axe, AbyssalCraft.CobbleU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_shovel, 1), Items.wooden_shovel, AbyssalCraft.CobbleU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_sword, 1), Items.wooden_sword, AbyssalCraft.CobbleU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_hoe, 1), Items.wooden_hoe, AbyssalCraft.CobbleU);

		//Stone to Iron Upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_pickaxe), Items.stone_pickaxe, AbyssalCraft.IronU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_axe), Items.stone_axe, AbyssalCraft.IronU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_shovel), Items.stone_shovel, AbyssalCraft.IronU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_sword), Items.stone_sword, AbyssalCraft.IronU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_hoe), Items.stone_hoe, AbyssalCraft.IronU);

		//Iron to Gold Upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_pickaxe), Items.iron_pickaxe, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_axe), Items.iron_axe, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_shovel), Items.iron_shovel, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_sword), Items.iron_sword, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_hoe), Items.iron_hoe, AbyssalCraft.GoldU);

		//Gold to Diamond Upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_pickaxe), Items.golden_pickaxe, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_axe), Items.golden_axe, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_shovel), Items.golden_shovel, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_sword), Items.golden_sword, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_hoe), Items.golden_hoe, AbyssalCraft.DiamondU);

		//Diamond to Abyssalnite Upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.pickaxeA), Items.diamond_pickaxe, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.axeA), Items.diamond_axe, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.shovelA), Items.diamond_shovel, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.swordA), Items.diamond_sword, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.hoeA), Items.diamond_hoe, AbyssalCraft.AbyssalniteU);

		//Abyssalnite to Coralium Upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corpickaxe), AbyssalCraft.pickaxeA, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraxe), AbyssalCraft.axeA, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corshovel), AbyssalCraft.shovelA, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corsword), AbyssalCraft.swordA, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corhoe), AbyssalCraft.hoeA, AbyssalCraft.CoraliumU);

		//Iron to Gold armor
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_helmet), Items.iron_helmet, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_chestplate), Items.iron_chestplate, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_leggings), Items.iron_leggings, AbyssalCraft.GoldU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_boots), Items.iron_boots, AbyssalCraft.GoldU);

		//Gold to Diamond armor
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_helmet), Items.golden_helmet, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_chestplate), Items.golden_chestplate, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_leggings), Items.golden_leggings, AbyssalCraft.DiamondU);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond_boots), Items.golden_boots, AbyssalCraft.DiamondU);

		//Diamond to Abyssalnite armor
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.helmet), Items.diamond_helmet, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.plate), Items.diamond_chestplate, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.legs), Items.diamond_leggings, AbyssalCraft.AbyssalniteU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.boots), Items.diamond_boots, AbyssalCraft.AbyssalniteU);

		//Abyssalnite to Coralium armor
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corhelmet), AbyssalCraft.helmet, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corplate), AbyssalCraft.plate, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corlegs), AbyssalCraft.legs, AbyssalCraft.CoraliumU);
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Corboots), AbyssalCraft.boots, AbyssalCraft.CoraliumU);
	}

	private static void addItemSmelting(){

		GameRegistry.addSmelting(AbyssalCraft.abychunk, new ItemStack(AbyssalCraft.abyingot, 2), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Cchunk, new ItemStack(AbyssalCraft.Cingot, 2), 3F);
		GameRegistry.addSmelting(Items.egg, new ItemStack(AbyssalCraft.friedegg, 1), 0F);

		//Special smelting (salvage)
		GameRegistry.addSmelting(Items.leather_helmet, new ItemStack(Items.leather, 2), 1F);
		GameRegistry.addSmelting(Items.leather_chestplate, new ItemStack(Items.leather, 4), 1F);
		GameRegistry.addSmelting(Items.leather_leggings, new ItemStack(Items.leather, 3), 1F);
		GameRegistry.addSmelting(Items.leather_boots, new ItemStack(Items.leather, 2), 1F);

		GameRegistry.addSmelting(Items.iron_helmet, new ItemStack(Items.iron_ingot, 2), 1F);
		GameRegistry.addSmelting(Items.iron_chestplate, new ItemStack(Items.iron_ingot, 4), 1F);
		GameRegistry.addSmelting(Items.iron_leggings, new ItemStack(Items.iron_ingot, 3), 1F);
		GameRegistry.addSmelting(Items.iron_boots, new ItemStack(Items.iron_ingot, 2), 1F);

		GameRegistry.addSmelting(Items.golden_helmet, new ItemStack(Items.gold_ingot, 2), 1F);
		GameRegistry.addSmelting(Items.golden_chestplate, new ItemStack(Items.gold_ingot, 4), 1F);
		GameRegistry.addSmelting(Items.golden_leggings, new ItemStack(Items.gold_ingot, 3), 1F);
		GameRegistry.addSmelting(Items.golden_boots, new ItemStack(Items.gold_ingot, 2), 1F);

		GameRegistry.addSmelting(Items.diamond_helmet, new ItemStack(Items.diamond, 2), 1F);
		GameRegistry.addSmelting(Items.diamond_chestplate, new ItemStack(Items.diamond, 4), 1F);
		GameRegistry.addSmelting(Items.diamond_leggings, new ItemStack(Items.diamond, 3), 1F);
		GameRegistry.addSmelting(Items.diamond_boots, new ItemStack(Items.diamond, 2), 1F);

		GameRegistry.addSmelting(AbyssalCraft.helmet, new ItemStack(AbyssalCraft.abyingot, 2), 1F);
		GameRegistry.addSmelting(AbyssalCraft.plate, new ItemStack(AbyssalCraft.abyingot, 4), 1F);
		GameRegistry.addSmelting(AbyssalCraft.legs, new ItemStack(AbyssalCraft.abyingot, 3), 1F);
		GameRegistry.addSmelting(AbyssalCraft.boots, new ItemStack(AbyssalCraft.abyingot, 2), 1F);

		GameRegistry.addSmelting(AbyssalCraft.Corhelmet, new ItemStack(AbyssalCraft.Cingot, 2), 1F);
		GameRegistry.addSmelting(AbyssalCraft.Corplate, new ItemStack(AbyssalCraft.Cingot, 4), 1F);
		GameRegistry.addSmelting(AbyssalCraft.Corlegs, new ItemStack(AbyssalCraft.Cingot, 3), 1F);
		GameRegistry.addSmelting(AbyssalCraft.Corboots, new ItemStack(AbyssalCraft.Cingot, 2), 1F);
	}
}