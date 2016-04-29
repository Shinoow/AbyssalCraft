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
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionPotion;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionSpawn;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionSwarm;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconPotionAoERitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconSummonRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionDisplaceEntities;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionFire;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionFireRain;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionFreeze;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionLightning;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionMonolith;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionPotentialEnergy;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionTeleportRandomly;
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

		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_brick, 4), new Object[] {"AA", "AA", 'A', ACBlocks.darkstone });
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_brick_slab, 6), new Object[] {"AAA", 'A', ACBlocks.darkstone_brick });
		GameRegistry.addRecipe(new ItemStack(ACBlocks.glowing_darkstone_bricks, 4), new Object[] {"#$#", "&%&", "#&#", '#', ACBlocks.darkstone_brick, '$', Items.diamond,'&', Blocks.obsidian, '%', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_cobblestone_slab, 6), new Object[] {"AAA", 'A', ACBlocks.darkstone_cobblestone });
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darklands_oak_planks, 4), new Object[] {"A", 'A', ACBlocks.darklands_oak_wood });
		GameRegistry.addRecipe(new ItemStack(ACBlocks.oblivion_deathbomb, 1), new Object[] {"AOO", "TCO", "LOO", 'A', ACItems.liquid_antimatter_bucket, 'O', Blocks.obsidian, 'T', ACItems.oblivion_catalyst, 'C', ACBlocks.odb_core, 'L', ACItems.liquid_coralium_bucket});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.oblivion_deathbomb, 1), new Object[] {"AOO", "TCO", "LOO", 'L', ACItems.liquid_antimatter_bucket, 'O', Blocks.obsidian, 'T', ACItems.oblivion_catalyst, 'C', ACBlocks.odb_core, 'A', ACItems.liquid_coralium_bucket});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.block_of_abyssalnite, 1), new Object[] {"AAA", "AAA", "AAA", 'A', ACItems.abyssalnite_ingot });
		GameRegistry.addRecipe(new ItemStack(ACBlocks.odb_core, 1), new Object[] {"#&#", "$@$", "#&#", '#', ACItems.abyssalnite_ingot, '&', Blocks.iron_block, '$', Items.iron_ingot,'@', ACItems.coralium_pearl});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&&&", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_3});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem_cluster_4});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_4});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_4, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem_cluster_2});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem_cluster_4, '%', ACItems.coralium_gem_cluster_2});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_4, '%', ACItems.coralium_gem_cluster_3});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_4, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_3});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&&&", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_3});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem_cluster_5});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_5});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_3, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem_cluster_3, '%', ACItems.coralium_gem});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem_cluster_3});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_3});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_7, '%', ACItems.coralium_gem});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_7, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_7});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_2});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem_cluster_2});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_5});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_6});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem_cluster_6});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem, '%', ACItems.coralium_gem_cluster_2});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem_cluster_2});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_2, '$', ACItems.coralium_gem_cluster_5, '%', ACItems.coralium_gem});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "&$%", "###", '#', Blocks.stone, '&', ACItems.coralium_gem_cluster_5, '$', ACItems.coralium_gem_cluster_2, '%', ACItems.coralium_gem});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_infused_stone, 1), new Object[] {"###", "#%#", "###", '#', Blocks.stone, '%', ACItems.coralium_gem_cluster_9});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssal_stone_brick, 4), new Object[] {"##", "##", '#', ACBlocks.abyssal_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssal_stone_brick_slab, 6), new Object[] {"###", '#', ACBlocks.abyssal_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssal_stone_brick_fence, 6), new Object[] {"###", "###", '#', ACBlocks.abyssal_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_cobblestone_wall, 6), new Object[] {"###", "###", '#', ACBlocks.darkstone_cobblestone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.wooden_crate, 2), new Object[] {"#&#", "&%&", "#&#", '#', Items.stick, '&', Blocks.planks, '%', Blocks.chest});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_button, 1), new Object[] {"#", '#', ACBlocks.darkstone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_pressure_plate, 1), new Object[] {"##", '#', ACBlocks.darkstone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darklands_oak_button, 1), new Object[] {"#", '#', ACBlocks.darklands_oak_planks});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darklands_oak_pressure_plate, 1), new Object[] {"##", '#', ACBlocks.darklands_oak_planks});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darklands_oak_slab, 6), new Object[] {"###", '#', ACBlocks.darklands_oak_planks});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_slab,6), new Object[] {"###", '#', ACBlocks.darkstone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.block_of_coralium, 1), new Object[] {"###", "###", "###", '#', ACItems.refined_coralium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssal_stone_button, 1), new Object[] {"#", '#', ACBlocks.abyssal_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssal_stone_pressure_plate, 1), new Object[] {"##", '#', ACBlocks.abyssal_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_brick_fence, 6), new Object[] {"###", "###", '#', ACBlocks.darkstone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darklands_oak_fence, 4), new Object[] {"###", "###", '#', ACBlocks.darklands_oak_planks});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dreadstone_brick, 4), new Object[] {"##", "##", '#', ACBlocks.dreadstone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssalnite_stone_brick, 4), new Object[] {"##", "##", '#', ACBlocks.abyssalnite_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dreadlands_planks, 4), new Object[] {"%", '%', ACBlocks.dreadlands_log});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dreadstone_brick_slab, 6), new Object[] {"###", '#', ACBlocks.dreadstone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dreadstone_brick_fence, 6), new Object[] {"###", "###", '#', ACBlocks.dreadstone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssalnite_stone_brick_slab, 6), new Object[] {"###", '#', ACBlocks.abyssalnite_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssalnite_stone_brick_fence, 6), new Object[] {"###", "###", '#', ACBlocks.abyssalnite_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_stone_brick, 1), new Object[] {"##", "##", '#', ACItems.coralium_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_stone_brick_slab, 6), new Object[] {"###", '#', ACBlocks.coralium_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_stone_brick_fence, 6), new Object[] {"###", "###", '#', ACBlocks.coralium_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_stone_button, 1), new Object[] {"#", '#', ACBlocks.coralium_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_stone_pressure_plate, 1), new Object[] {"##", '#', ACBlocks.coralium_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.block_of_dreadium, 1), new Object[] {"###", "###", "###", '#', ACItems.dreadium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dreadlands_wood_fence, 4), new Object[] {"###", "###", '#', ACBlocks.dreadlands_planks});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.transmutator_idle, 1), new Object[] {"###", "#%#", "&$&", '#', ACItems.coralium_brick, '%', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), '&', ACBlocks.block_of_coralium, '$', ACItems.liquid_coralium_bucket});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.crystallizer_idle, 1), new Object[] {"###", "&%&", "###", '#', ACBlocks.dreadstone_brick, '&', ACBlocks.block_of_dreadium, '%', Blocks.furnace});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.chagaroth_altar_top, 1), new Object[] {"#%#", "&&&", "@@@", '#', Items.stick, '%', Items.bucket, '&', ACItems.dread_cloth, '@', ACItems.dreadium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.chagaroth_altar_bottom, 1), new Object[] {"#%#", "&@&", "T$T", '#', Items.bone, '%', ACItems.dread_cloth, '&', ACItems.dreadium_ingot, '@', ACItems.dreaded_gateway_key, '$', ACItems.dreaded_shard_of_abyssalnite, 'T', ACBlocks.dreadstone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.ethaxium_brick, 1, 0), new Object [] {"##", "##", '#', ACItems.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.ethaxium_brick, 1, 1), new Object[] {"##", "##", '#', new ItemStack(ACBlocks.ethaxium_brick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.ethaxium_pillar, 2), new Object[] {"#%", "#%", '#', new ItemStack(ACBlocks.ethaxium_brick, 1, 0), '%', ACBlocks.ethaxium});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_brick_stairs, 4), new Object[] {"#  ", "## ", "###", '#', ACBlocks.darkstone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darkstone_cobblestone_stairs, 4), new Object[] {"#  ", "## ", "###", '#', ACBlocks.darkstone_cobblestone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssal_stone_brick_stairs, 4), new Object[] {"#  ", "## ", "###", '#', ACBlocks.abyssal_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.darklands_oak_stairs, 4), new Object[] {"#  ", "## ", "###", '#', ACBlocks.darklands_oak_planks});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dreadstone_brick_stairs, 4), new Object[] {"#  ", "## ", "###", '#', ACBlocks.dreadstone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.abyssalnite_stone_brick_stairs, 4), new Object[] {"#  ", "## ", "###", '#', ACBlocks.abyssalnite_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.coralium_stone_brick_stairs, 4), new Object[] {"#  ", "## ", "###", '#', ACBlocks.coralium_stone_brick});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.block_of_ethaxium, 1), new Object[] {"###", "###", "###", '#', ACItems.ethaxium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.ethaxium_brick_stairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(ACBlocks.ethaxium_brick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.ethaxium_brick_slab, 6), new Object[] {"AAA", 'A', new ItemStack(ACBlocks.ethaxium_brick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.ethaxium_brick_fence, 6), new Object[] {"###", "###", '#', new ItemStack(ACBlocks.ethaxium_brick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.engraver, 1), new Object[] {"#% ", "#%&", "@% ", '#', ACItems.blank_engraving, '%', Blocks.stone, '&', Blocks.lever, '@', Blocks.anvil});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.house, 1), true, new Object[] {"#%#", "%&%", "%%%", '#', "stairWood", '%', "plankWood", '&', Items.oak_door})); //Quite frankly, this recipe doesn't exist
		GameRegistry.addRecipe(new ItemStack(ACBlocks.materializer, 1), new Object[] {"###", "#%#", "&@&", '#', ACItems.ethaxium_brick, '%', Blocks.obsidian, '&', ACBlocks.block_of_ethaxium, '@', ACItems.liquid_antimatter_bucket});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0), new Object[] {"#%", "#%", '#', ACBlocks.omothol_stone, '%', ACBlocks.ethaxium});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 1), new Object[] {"##", "##", '#', new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dark_ethaxium_pillar, 2), new Object[] {"#%", "#%", '#', new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0), '%', ACBlocks.omothol_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dark_ethaxium_brick_stairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(ACBlocks.dark_ethaxium_brick, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dark_ethaxium_brick_slab, 6), new Object[] {"AAA", 'A', new ItemStack(ACBlocks.dark_ethaxium_brick, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.dark_ethaxium_brick_fence, 6), new Object[] {"###", "###", '#', new ItemStack(ACBlocks.dark_ethaxium_brick, 0)});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.energy_pedestal), new Object[]{"#%#", "#&#", "###", '#', ACBlocks.monolith_stone, '%', ACItems.coralium_pearl, '&', ACItems.shadow_gem});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.monolith_pillar), new Object[]{"##", "##", '#', ACBlocks.monolith_stone});
		GameRegistry.addRecipe(new ItemStack(ACBlocks.sacrificial_altar), new Object[]{"#%#", "&$&", "&&&", '#', Blocks.torch, '%', ACItems.coralium_pearl, '$', ACItems.shadow_gem, '&', ACBlocks.monolith_stone});
	}

	private static void addBlockSmelting(){

		GameRegistry.addSmelting(ACBlocks.darkstone_cobblestone, new ItemStack(ACBlocks.darkstone, 1), 0.1F);
		AbyssalCraftAPI.addOreSmelting("oreAbyssalnite", "ingotAbyssalnite", 3F);
		AbyssalCraftAPI.addOreSmelting("oreCoralium", "gemCoralium", 3F);
		GameRegistry.addSmelting(ACBlocks.darklands_oak_wood, new ItemStack(Items.coal, 1, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.coralium_infused_stone, new ItemStack(ACItems.coralium_pearl), 3F);
		GameRegistry.addSmelting(ACBlocks.pearlescent_coralium_ore, new ItemStack(ACItems.coralium_pearl), 3F);
		GameRegistry.addSmelting(ACBlocks.liquified_coralium_ore, new ItemStack(ACItems.refined_coralium_ingot), 3F);
		GameRegistry.addSmelting(ACBlocks.dreaded_abyssalnite_ore, new ItemStack(ACItems.abyssalnite_ingot, 1), 3F);
		GameRegistry.addSmelting(ACBlocks.coralium_stone, new ItemStack(ACItems.coralium_brick, 1), 0.1F);
		GameRegistry.addSmelting(ACBlocks.nitre_ore, new ItemStack(ACItems.nitre, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_iron_ore, new ItemStack(Items.iron_ingot, 1), 0.7F);
		GameRegistry.addSmelting(ACBlocks.abyssal_gold_ore, new ItemStack(Items.gold_ingot, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_diamond_ore, new ItemStack(Items.diamond, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_nitre_ore, new ItemStack(ACItems.nitre, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_tin_ore, new ItemStack(ACItems.tin_ingot, 1), 0.7F);
		GameRegistry.addSmelting(ACBlocks.abyssal_copper_ore, new ItemStack(ACItems.copper_ingot, 1), 0.7F);
		GameRegistry.addSmelting(ACBlocks.ethaxium, new ItemStack(ACItems.ethaxium_brick), 0.2F);
	}

	private static void addItemCrafting(){

		GameRegistry.addRecipe(new ItemStack(ACItems.darkstone_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACBlocks.darkstone_cobblestone, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.darkstone_axe, 1), new Object[] {"##", "#%", " %", '#', ACBlocks.darkstone_cobblestone, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.darkstone_shovel, 1), new Object[] {"#", "%", "%", '#', ACBlocks.darkstone_cobblestone, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.darkstone_sword, 1), new Object[] {"#", "#", "%", '#', ACBlocks.darkstone_cobblestone, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.darkstone_hoe, 1), new Object[] {"##", " %", " %", '#', ACBlocks.darkstone_cobblestone, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.abyssalnite_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.abyssalnite_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.abyssalnite_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.abyssalnite_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.abyssalnite_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.gateway_key, 1), new Object[] {" #%", " &#", "&  ", '#', ACItems.coralium_pearl, '%', ACItems.oblivion_catalyst, '&', Items.blaze_rod});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_ingot, 9), new Object[] {"#", '#', ACBlocks.block_of_abyssalnite});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_plate, 1), new Object[] {"#%#", "#%#", "#%#", '#', ACItems.refined_coralium_ingot, '%', ACItems.coralium_pearl});
		GameRegistry.addRecipe(new ItemStack(ACItems.chunk_of_coralium, 1), new Object[] {"###", "#%#", "###", '#', ACItems.coralium_gem_cluster_9, '%', ACBlocks.abyssal_stone});
		GameRegistry.addRecipe(new ItemStack(ACItems.powerstone_tracker, 4), new Object[] {"###", "#%#", "###", '#', ACItems.coralium_gem, '%', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_ingot, 9), new Object[] {"#", '#', ACBlocks.block_of_coralium});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.refined_coralium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.refined_coralium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.refined_coralium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.refined_coralium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.refined_coralium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.shadow_shard, 1), new Object[] {"###", "###", "###", '#', ACItems.shadow_fragment});
		GameRegistry.addRecipe(new ItemStack(ACItems.shadow_gem, 1), new Object[] {"###", "###", "###", '#', ACItems.shadow_shard});
		GameRegistry.addRecipe(new ItemStack(ACItems.shard_of_oblivion, 1), new Object[] {" # ", "#%#", " # ", '#', ACItems.shadow_gem, '%', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_longbow, 1), new Object[] {" #%", "&$%", " #%", '#', ACItems.refined_coralium_ingot, '%', Items.string, '&', ACItems.coralium_pearl, '$', new ItemStack(Items.bow, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_ingot, 9), new Object[] {"#", '#', ACBlocks.block_of_dreadium});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.dreadium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.dreadium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.dreadium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.dreadium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.dreadium_ingot, '%', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ACItems.carbon_cluster, 1), new Object[] {"###", "# #", "###", '#', new ItemStack(ACItems.crystal, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(ACItems.dense_carbon_cluster, 1), new Object[] {"###", "#%#", "###", '#', ACItems.carbon_cluster, '%', Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(ACItems.liquid_antimatter_bucket, 1), new Object[] {"#@%", "$&$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', ACItems.liquid_coralium_bucket});
		GameRegistry.addRecipe(new ItemStack(ACItems.liquid_antimatter_bucket, 1), new Object[] {"@%&", "$#$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', ACItems.liquid_coralium_bucket});
		GameRegistry.addRecipe(new ItemStack(ACItems.liquid_antimatter_bucket, 1), new Object[] {"%&#", "$@$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', ACItems.liquid_coralium_bucket});
		GameRegistry.addRecipe(new ItemStack(ACItems.liquid_antimatter_bucket, 1), new Object[] {"&#@", "$%$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', ACItems.liquid_coralium_bucket});
		GameRegistry.addRecipe(new ItemStack(ACItems.dread_cloth, 1), new Object[] {"#%#", "%&%", "#%#", '#', Items.string, '%', ACItems.dread_fragment, '&', Items.leather});
		GameRegistry.addRecipe(new ItemStack(ACItems.dread_cloth, 1), new Object[] {"#%#", "%&%", "#%#", '%', Items.string, '#', ACItems.dread_fragment, '&', Items.leather});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_plate, 1), new Object[] {"###", "#%#", "###", '#', ACItems.dreadium_ingot, '%', ACItems.dread_cloth});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_katana_blade, 1), new Object[] {"## ", "## ", "## ", '#', new ItemStack(ACItems.crystal, 1, 14)});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_katana_hilt, 1), new Object[] {"###", "%&%", "%&%", '#', ACItems.dreadium_ingot, '%', ACItems.dread_cloth, '&', ACBlocks.dreadlands_planks});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_katana, 1), new Object[] {"# ", "% ", '#', ACItems.dreadium_katana_blade, '%', ACItems.dreadium_katana_hilt});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.gunpowder, 4), true, new Object[] {"#&#", "#%#", "###", '#', "dustSaltpeter", '%', new ItemStack(Items.coal, 1, 1), '&', "dustSulfur"}));
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 10), new Object[] {" # ", "#%#", " # ", '#', new ItemStack(ACItems.crystal, 1, 5), '%', new ItemStack(ACItems.crystal, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 9), new Object[] {" # ", "%%%", '#', new ItemStack(ACItems.crystal, 1, 6), '%', new ItemStack(ACItems.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 22), new Object[] {" # ", "%%%", " # ", '#', new ItemStack(ACItems.crystal, 1, 20), '%', new ItemStack(ACItems.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 21), new Object[] {"#%#", '#', new ItemStack(ACItems.crystal, 1, 4), '%', new ItemStack(ACItems.crystal, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 23), new Object[] {"#%", '#', new ItemStack(ACItems.crystal, 1, 19), '%', new ItemStack(ACItems.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 1, 10), new Object[] {" # ", "#%#", " # ", '#', new ItemStack(ACItems.crystal_shard, 1, 5), '%', new ItemStack(ACItems.crystal_shard, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 1, 9), new Object[] {" # ", "%%%", '#', new ItemStack(ACItems.crystal_shard, 1, 6), '%', new ItemStack(ACItems.crystal_shard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 1, 22), new Object[] {" # ", "%%%", " # ", '#', new ItemStack(ACItems.crystal_shard, 1, 20), '%', new ItemStack(ACItems.crystal_shard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 1, 21), new Object[] {"#%#", '#', new ItemStack(ACItems.crystal_shard, 1, 4), '%', new ItemStack(ACItems.crystal_shard, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 1, 23), new Object[] {"#%", '#', new ItemStack(ACItems.crystal_shard, 1, 19), '%', new ItemStack(ACItems.crystal_shard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_ingot), new Object[] {"###", "#%#", "###", '#', ACItems.ethaxium_brick, '%', ACItems.life_crystal});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_ingot), new Object[] {" # ", "#%#", " # ", '#', ACItems.ethaxium_brick, '%', ACItems.oblivion_catalyst});
		//		GameRegistry.addRecipe(new ItemStack(Items.spawn_egg, 1, AbyssalCraft.stringtoIDMapping.get("shadowboss")), new Object[] {"#", '#', AbyssalCraft.ODB});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_ingot, 9), new Object[] {"#", '#', ACBlocks.block_of_ethaxium});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_pickaxe, 1), new Object[] {"###", " % ", " % ", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_axe, 1), new Object[] {"##", "#%", " %", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_shovel, 1), new Object[] {"#", "%", "%", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_sword, 1), new Object[] {"#", "#", "%", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_hoe, 1), new Object[] {"##", " %", " %", '#', ACItems.ethaxium_ingot, '%', ACItems.ethaxium_brick});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACItems.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotCopper", '%', Items.flint}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACItems.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotIron", '%', Items.flint}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACItems.coin, 1), true, new Object[] {" # ", "#%#", " # ", '#', "ingotTin", '%', Items.flint}));
		GameRegistry.addRecipe(new ItemStack(ACItems.blank_engraving, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(Blocks.stone_slab, 1, 0), '%', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.elder_engraving, 1), new Object[] {"#", "%", '#', ACItems.blank_engraving, '%', ACItems.ethaxium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.cthulhu_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACItems.hastur_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(ACItems.jzahar_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', ACItems.eldritch_scale});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.azathoth_engraving, 1), new ItemStack(ACItems.shoggoth_flesh, 1, 0), ACItems.elder_engraving, new ItemStack(ACItems.shoggoth_flesh, 1, 1),
				new ItemStack(ACItems.shoggoth_flesh, 1, 2), new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.shoggoth_flesh, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ACItems.nyarlathotep_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(ACItems.yog_sothoth_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(ACItems.shub_niggurath_engraving, 1), new Object[] {"%#%", "%%%", '#', ACItems.elder_engraving, '%', new ItemStack(ACItems.shoggoth_flesh, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.necronomicon, 1), new Object[] {"##%", "#&#", "##%", '#', Items.rotten_flesh, '%', Items.iron_ingot, '&', Items.book});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssal_wasteland_necronomicon, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 0), '%', ACItems.necronomicon});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadlands_necronomicon, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 1), '%', ACItems.abyssal_wasteland_necronomicon});
		GameRegistry.addRecipe(new ItemStack(ACItems.omothol_necronomicon, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 2), '%', ACItems.dreadlands_necronomicon});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnomicon, 1), new Object[] {"#$#", "%&%", "#%#", '#', ACItems.ethaxium_ingot, '%', ACItems.eldritch_scale, '&', ACItems.omothol_necronomicon, '$', ACItems.essence_of_the_gatekeeper});
		GameRegistry.addRecipe(new ItemStack(ACItems.small_crystal_bag, 1), new Object[] {"#%#", "%&%", "%%%", '#', Items.string, '%', Items.leather, '&', Items.gold_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.medium_crystal_bag, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 0), '%', ACItems.small_crystal_bag});
		GameRegistry.addRecipe(new ItemStack(ACItems.large_crystal_bag, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 1), '%', ACItems.medium_crystal_bag});
		GameRegistry.addRecipe(new ItemStack(ACItems.huge_crystal_bag, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(ACItems.skin, 1, 2), '%', ACItems.large_crystal_bag});
		GameRegistry.addRecipe(new ItemStack(ACItems.ingot_nugget, 9, 0), new Object[] {"#", '#', ACItems.abyssalnite_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.ingot_nugget, 9, 1), new Object[] {"#", '#', ACItems.refined_coralium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.ingot_nugget, 9, 2), new Object[] {"#", '#', ACItems.dreadium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.ingot_nugget, 9, 3), new Object[] {"#", '#', ACItems.ethaxium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_ingot), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.ingot_nugget, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 0), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 1), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 2), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 3), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 4), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 5), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 5)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 6), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 6)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 7), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 7)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 8), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 8)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 9), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 9)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 10), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 10)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 11), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 11)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 12), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 12)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 13), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 13)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 14), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 14)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 15), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 15)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 16), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 16)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 17), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 17)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 18), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 19), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 19)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 20), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 20)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 21), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 21)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 22), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 22)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 23), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 23)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal, 1, 24), new Object[] {"###", "###", "###", '#', new ItemStack(ACItems.crystal_shard, 1, 24)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 0), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 1), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 2), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 3), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 4), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 5), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 5)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 6), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 6)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 7), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 7)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 8), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 8)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 9), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 9)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 10), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 10)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 11), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 11)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 12), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 12)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 13), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 13)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 14), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 14)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 15), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 15)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 16), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 16)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 17), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 17)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 18), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 18)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 19), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 19)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 20), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 20)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 21), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 21)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 22), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 22)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 23), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 23)});
		GameRegistry.addRecipe(new ItemStack(ACItems.crystal_shard, 9, 24), new Object[] {"#", '#', new ItemStack(ACItems.crystal, 1, 24)});
		GameRegistry.addRecipe(new ItemStack(ACItems.skin, 1, 0), new Object[] {"###", "#%#", "###", '#', ACItems.coralium_plagued_flesh, '%', new ItemStack(ACItems.essence, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ACItems.skin, 1, 1), new Object[] {"###", "#%#", "###", '#', ACItems.dread_fragment, '%', new ItemStack(ACItems.essence, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(ACItems.skin, 1, 2), new Object[] {"###", "#%#", "###", '#', ACItems.omothol_flesh, '%', new ItemStack(ACItems.essence, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(ACItems.staff_of_rending), new Object[] {" #%", " ##", "#  ", '#', ACItems.shadow_shard, '%', ACItems.shard_of_oblivion});
		GameRegistry.addRecipe(new ItemStack(ACItems.shadow_shard, 9), new Object[] {"#", '#', ACItems.shadow_gem});
		GameRegistry.addRecipe(new ItemStack(ACItems.shadow_fragment, 9), new Object[] {"#", '#', ACItems.shadow_shard});
		GameRegistry.addRecipe(new ItemStack(ACItems.ritual_charm, 1, 0), new Object[] {"###", "#%#", "###", '#', Items.gold_ingot, '%', Items.diamond});

		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.life_crystal), new ItemStack(ACItems.crystal, 1, 3), new ItemStack(ACItems.crystal, 1, 5), new ItemStack(ACItems.crystal, 1, 6),
				new ItemStack(ACItems.crystal, 1, 4), new ItemStack(ACItems.crystal, 1, 7), new ItemStack(ACItems.crystal, 1, 2));

		//Coralium Gem Cluster Recipes
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_2, 1),ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_3, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_4, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_3, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_4, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_4, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_6, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_6, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_6, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_7, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_7, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_8, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_2, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_5, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_3, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_6, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_4, ACItems.coralium_gem, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_7, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_5);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_5, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_5, ACItems.coralium_gem, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_8, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_6);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_6, ACItems.coralium_gem);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_gem_cluster_9, 1),ACItems.coralium_gem_cluster_2, ACItems.coralium_gem_cluster_7);
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 2), new Object[] {"#", '#', ACItems.coralium_gem_cluster_2});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 3), new Object[] {"#", '#', ACItems.coralium_gem_cluster_3});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 4), new Object[] {"#", '#', ACItems.coralium_gem_cluster_4});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 5), new Object[] {"#", '#', ACItems.coralium_gem_cluster_5});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 6), new Object[] {"#", '#', ACItems.coralium_gem_cluster_6});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 7), new Object[] {"#", '#', ACItems.coralium_gem_cluster_7});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 8), new Object[] {"#", '#', ACItems.coralium_gem_cluster_8});
		GameRegistry.addRecipe(new ItemStack(ACItems.coralium_gem, 9), new Object[] {"#", '#', ACItems.coralium_gem_cluster_9});

		addArmor(ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots, ACItems.abyssalnite_ingot, ACItems.abyssalnite_upgrade_kit, Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots);
		addArmor(ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots, ACItems.refined_coralium_ingot, ACItems.coralium_upgrade_kit, ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots);
		GameRegistry.addRecipe(new ItemStack(ACItems.plated_coralium_boots, 1), new Object[] {"# #", "%&%", '#', ACItems.refined_coralium_ingot, '%', ACItems.coralium_plate, '&', ACItems.refined_coralium_boots});
		GameRegistry.addRecipe(new ItemStack(ACItems.plated_coralium_helmet, 1), new Object[] {"&#&", "#@#", "%%%", '#', ACItems.coralium_plate, '&', ACItems.coralium_pearl, '@', ACItems.refined_coralium_helmet, '%', ACItems.refined_coralium_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.plated_coralium_chestplate, 1), new Object[] {"# #", "%@%", "%#%",'#', ACItems.coralium_plate, '%', ACItems.refined_coralium_ingot, '@', ACItems.refined_coralium_chestplate});
		GameRegistry.addRecipe(new ItemStack(ACItems.plated_coralium_leggings, 1), new Object[] {"%&%", "# #", "# #",'#', ACItems.refined_coralium_ingot, '%', ACItems.coralium_plate, '&', ACItems.refined_coralium_leggings});
		addArmor(ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots, ACItems.dreadium_ingot, ACItems.dreadium_upgrade_kit, ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots);
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_samurai_boots, 1), new Object[] {"#%#", "&&&", '#', ACItems.dread_cloth, '%', new ItemStack(ACItems.dreadium_boots, 1, OreDictionary.WILDCARD_VALUE), '&', ACBlocks.dreadlands_planks});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_samurai_helmet, 1), new Object[] {" # ", "%&%", '#', ACItems.dreadium_ingot, '%', ACItems.dreadium_plate, '&', new ItemStack(ACItems.dreadium_helmet, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_samurai_chestplate, 1), new Object[] {"#%#", "#&#", "@@@", '#', ACItems.dreadium_plate, '%', ACItems.dreadium_ingot, '&', new ItemStack(ACItems.dreadium_chestplate, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.dread_cloth});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_samurai_leggings, 1), new Object[] {"#%#", "&&&", '#', ACItems.dreadium_plate, '%', new ItemStack(ACItems.dreadium_leggings, 1, OreDictionary.WILDCARD_VALUE), '&', ACItems.dread_cloth});
		addArmor(ACItems.ethaxium_helmet, ACItems.ethaxium_chestplate, ACItems.ethaxium_leggings, ACItems.ethaxium_boots, ACItems.ethaxium_ingot, ACItems.ethaxium_upgrade_kit, ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots);

		GameRegistry.addRecipe(new ItemStack(ACItems.iron_plate, 2), new Object[] {"#", "#", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(ACItems.washcloth, 1), new Object[] {"###", "#%#", "###", '#', Blocks.web, '%', Blocks.wool});

		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.mre, 1), ACItems.iron_plate, Items.carrot, Items.potato, Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.chicken_on_a_plate, 1), ACItems.iron_plate, Items.cooked_chicken);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.pork_on_a_plate, 1), ACItems.iron_plate, Items.cooked_porkchop);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.beef_on_a_plate, 1), ACItems.iron_plate, Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.fish_on_a_plate, 1), ACItems.iron_plate, Items.cooked_fish);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.fried_egg_on_a_plate, 1), ACItems.iron_plate, ACItems.fried_egg);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.iron_plate, 1, 0), ACItems.dirty_plate, new ItemStack(ACItems.washcloth,1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ACItems.cobblestone_upgrade_kit, 4), "plankWood", Blocks.cobblestone, Blocks.cobblestone, Items.string, Items.flint));
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.iron_upgrade_kit, 1), Blocks.cobblestone, Items.iron_ingot, Items.iron_ingot, ACItems.cobblestone_upgrade_kit);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.gold_upgrade_kit, 1), Items.iron_ingot, Items.gold_ingot, Items.gold_ingot, ACItems.iron_upgrade_kit);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.diamond_upgrade_kit, 1), Items.gold_ingot, Items.diamond, Items.diamond, ACItems.gold_upgrade_kit);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.abyssalnite_upgrade_kit, 1), Items.diamond, ACItems.abyssalnite_ingot, ACItems.abyssalnite_ingot, ACItems.diamond_upgrade_kit);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.coralium_upgrade_kit, 1), ACItems.abyssalnite_ingot, ACItems.refined_coralium_ingot, ACItems.refined_coralium_ingot, ACItems.abyssalnite_upgrade_kit);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.dreadium_upgrade_kit, 1), ACItems.refined_coralium_ingot, ACItems.dreadium_ingot, ACItems.dreadium_ingot, ACItems.coralium_upgrade_kit);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.ethaxium_upgrade_kit, 1), ACItems.dreadium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.dreadium_upgrade_kit);

		//Wood to Cobble Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.stone_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.cobblestone_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.cobblestone_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.cobblestone_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.cobblestone_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.wooden_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.cobblestone_upgrade_kit});

		//Stone to Iron Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.iron_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.iron_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.iron_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.iron_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.iron_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.iron_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.iron_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.iron_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.iron_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.stone_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.iron_upgrade_kit});

		//Iron to Gold Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.golden_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.golden_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.golden_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.golden_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.golden_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});

		//Gold to Diamond Upgrade
		GameRegistry.addRecipe(new ItemStack(Items.diamond_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});

		//Diamond to Abyssalnite Upgrade
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.abyssalnite_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_axe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.abyssalnite_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.abyssalnite_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_sword, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.abyssalnite_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.abyssalnite_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(Items.diamond_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.abyssalnite_upgrade_kit});

		//Abyssalnite to Coralium Upgrade
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.abyssalnite_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.coralium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_axe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.abyssalnite_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.coralium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.abyssalnite_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.coralium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_sword, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.abyssalnite_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.coralium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.refined_coralium_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.abyssalnite_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.coralium_upgrade_kit});

		//Coralium to Dreadium Upgrade
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.refined_coralium_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.dreadium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_axe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.refined_coralium_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.dreadium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.refined_coralium_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.dreadium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_sword, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.refined_coralium_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.dreadium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.dreadium_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.refined_coralium_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.dreadium_upgrade_kit});

		//Dreadium to Ethaxium Upgrade
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_pickaxe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.dreadium_pickaxe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.ethaxium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_axe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.dreadium_axe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.ethaxium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_shovel, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.dreadium_shovel, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.ethaxium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_sword, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.dreadium_sword, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.ethaxium_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(ACItems.ethaxium_hoe, 1), new Object[] {"#", "@", '#', new ItemStack(ACItems.dreadium_hoe, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.ethaxium_upgrade_kit});

		//Iron to Gold armor
		GameRegistry.addRecipe(new ItemStack(Items.golden_helmet, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_helmet, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.golden_chestplate, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_chestplate, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.golden_leggings, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_leggings, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.golden_boots, 1), new Object[] {"#", "@", '#', new ItemStack(Items.iron_boots, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.gold_upgrade_kit});

		//Gold to Diamond armor
		GameRegistry.addRecipe(new ItemStack(Items.diamond_helmet, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_helmet, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_chestplate, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_chestplate, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_leggings, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_leggings, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
		GameRegistry.addRecipe(new ItemStack(Items.diamond_boots, 1), new Object[] {"#", "@", '#', new ItemStack(Items.golden_boots, 1, OreDictionary.WILDCARD_VALUE), '@', ACItems.diamond_upgrade_kit});
	}

	private static void addItemSmelting(){

		GameRegistry.addSmelting(ACItems.chunk_of_abyssalnite, new ItemStack(ACItems.abyssalnite_ingot), 3F);
		GameRegistry.addSmelting(ACItems.chunk_of_coralium, new ItemStack(ACItems.refined_coralium_ingot, 2), 3F);
		GameRegistry.addSmelting(Items.egg, new ItemStack(ACItems.fried_egg, 1), 0F);
		GameRegistry.addSmelting(ACItems.dreaded_chunk_of_abyssalnite, new ItemStack(ACItems.abyssalnite_ingot), 3F);
		GameRegistry.addSmelting(ACItems.liquid_coralium_bucket, new ItemStack(ACBlocks.coralium_stone, 1), 0.2F);

		GameRegistry.addSmelting(ACItems.coin, new ItemStack(Items.iron_ingot, 4), 0.5F);

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

		AbyssalCraftAPI.addSingleCrystallization(ACBlocks.liquid_coralium, new ItemStack(ACItems.crystal_shard, 6, 13), 0.4F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.liquid_coralium_bucket, new ItemStack(ACItems.crystal_shard, 6, 13), 0.2F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.refined_coralium_ingot, new ItemStack(ACItems.crystal_shard, 4, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.chunk_of_coralium, new ItemStack(ACItems.crystal_shard, 4, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(ACBlocks.liquified_coralium_ore, new ItemStack(ACItems.crystal_shard, 4, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.abyssalnite_ingot, new ItemStack(ACItems.crystal_shard, 4, 12), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.chunk_of_abyssalnite, new ItemStack(ACItems.crystal_shard, 4, 12), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.dreadium_ingot, new ItemStack(ACItems.crystal_shard, 4, 14), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.iron_ingot, new ItemStack(ACItems.crystal_shard, 4, 0), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.gold_ingot, new ItemStack(ACItems.crystal_shard, 4, 1), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.redstone, new ItemStack(ACItems.crystal_shard, 4, 11), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.coal, new ItemStack(ACItems.crystal_shard, 4, 3), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.blaze_powder, new ItemStack(ACItems.crystal_shard, 4, 15), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(Items.dye, 1, 15), new ItemStack(ACItems.crystal_shard, 4, 7), 0.0F);
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
		AbyssalCraftAPI.addSingleCrystallization(Blocks.gold_block, new ItemStack(ACItems.crystal, 4, 1), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.iron_block, new ItemStack(ACItems.crystal, 4, 0), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(ACBlocks.block_of_abyssalnite, new ItemStack(ACItems.crystal, 4, 12), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(ACBlocks.block_of_coralium, new ItemStack(ACItems.crystal, 4, 13), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(ACBlocks.block_of_dreadium, new ItemStack(ACItems.crystal, 4, 14), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.coal_ore, new ItemStack(ACItems.crystal_shard, 4, 3), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.coal_block, new ItemStack(ACItems.crystal, 4, 3), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.redstone_ore, new ItemStack(ACItems.crystal_shard, 4, 11), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Blocks.redstone_block, new ItemStack(ACItems.crystal, 4, 11), 0.9F);
		AbyssalCraftAPI.addSingleCrystallization("ingotZinc", "crystalZinc", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("oreZinc", "crystalZinc", 0.1F);
		AbyssalCraftAPI.addCrystallization(ACItems.dreaded_chunk_of_abyssalnite, new ItemStack(ACItems.crystal_shard, 4, 12), new ItemStack(ACItems.crystal_shard, 4, 14), 0.2F);
		AbyssalCraftAPI.addCrystallization(ACBlocks.dreaded_abyssalnite_ore, new ItemStack(ACItems.crystal_shard, 4, 12), new ItemStack(ACItems.crystal_shard, 4, 14), 0.2F);
		AbyssalCraftAPI.addCrystallization(Items.water_bucket, new ItemStack(ACItems.crystal_shard, 12, 5), new ItemStack(ACItems.crystal_shard, 6, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Items.potionitem, 1, 0), new ItemStack(ACItems.crystal_shard, 6, 5), new ItemStack(ACItems.crystal_shard, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Items.dye, 1, 4), new ItemStack(ACItems.crystal, 2, 21), new ItemStack(ACItems.crystal_shard, 16, 2), 0.15F);
		AbyssalCraftAPI.addCrystallization(ACItems.methane, new ItemStack(ACItems.crystal_shard, 4, 4), new ItemStack(ACItems.crystal, 16, 5), 0.1F);
		AbyssalCraftAPI.addCrystallization(Items.gunpowder, new ItemStack(ACItems.crystal_shard, 16, 9), new ItemStack(ACItems.crystal_shard, 4, 2), 0.1F);
		AbyssalCraftAPI.addCrystallization("dustSaltpeter", "crystalShardPotassium", 4, "crystalShardNitrate", 4, 0.1F);
		AbyssalCraftAPI.addCrystallization("oreSaltpeter", "crystalShardPotassium", 4, "crystalShardNitrate", 4, 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.obsidian, new ItemStack(ACItems.crystal_shard, 4, 21), new ItemStack(ACItems.crystal_shard, 4, 23), 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.stone, new ItemStack(ACItems.crystal_shard, 4, 21), new ItemStack(ACItems.crystal_shard, 4, 23), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal,1, 21), new ItemStack(ACItems.crystal, 1, 18), new ItemStack(ACItems.crystal, 2, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard,1, 21), new ItemStack(ACItems.crystal_shard, 1, 18), new ItemStack(ACItems.crystal_shard, 2, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal, 1, 22), new ItemStack(ACItems.crystal, 2, 20), new ItemStack(ACItems.crystal, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard, 1, 22), new ItemStack(ACItems.crystal_shard, 2, 20), new ItemStack(ACItems.crystal_shard, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal, 1, 23), new ItemStack(ACItems.crystal, 1, 19), new ItemStack(ACItems.crystal, 1, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard, 1, 23), new ItemStack(ACItems.crystal_shard, 1, 19), new ItemStack(ACItems.crystal_shard, 1, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization("ingotBronze", "crystalShardCopper", 4, "crystalShardTin", 12, 0.4F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal, 1, 10), new ItemStack(ACItems.crystal, 1, 3), new ItemStack(ACItems.crystal, 4, 5), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard, 1, 10), new ItemStack(ACItems.crystal_shard, 1, 3), new ItemStack(ACItems.crystal_shard, 4, 5), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal, 1, 9), new ItemStack(ACItems.crystal, 1, 6), new ItemStack(ACItems.crystal, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard, 1, 9), new ItemStack(ACItems.crystal_shard, 1, 6), new ItemStack(ACItems.crystal_shard, 3, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.lapis_ore, new ItemStack(ACItems.crystal_shard, 24, 21), new ItemStack(ACItems.crystal_shard, 16, 2), 0.15F);
		AbyssalCraftAPI.addCrystallization(Blocks.lapis_block, new ItemStack(ACItems.crystal, 24, 21), new ItemStack(ACItems.crystal, 16, 2), 1.0F);
		AbyssalCraftAPI.addCrystallization("ingotBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.5F);
		AbyssalCraftAPI.addCrystallization("oreBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.5F);
		AbyssalCraftAPI.addSingleCrystallization(Items.rotten_flesh, new ItemStack(ACItems.crystal_shard, 8, 7), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.shoggoth_flesh, 1, 0), new ItemStack(ACItems.crystal_shard, 8, 7), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.shoggoth_flesh, 1, 1), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.crystal_shard, 4, 13), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.shoggoth_flesh, 1, 2), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.crystal_shard, 4, 14), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.crystal_shard, 4, 3), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.shoggoth_flesh, 1, 4), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.shadow_gem, 1), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.crystal_shard, 4, 13), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone), new ItemStack(ACItems.crystal_shard, 12, 7), new ItemStack(ACItems.crystal_shard, 4, 13), 0.2F);
		AbyssalCraftAPI.addSingleCrystallization(Items.gold_nugget, new ItemStack(ACItems.crystal_shard, 1, 1), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.ingot_nugget, 1, 0), new ItemStack(ACItems.crystal_shard, 1, 12), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.ingot_nugget, 1, 1), new ItemStack(ACItems.crystal_shard, 1, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.ingot_nugget, 1, 2), new ItemStack(ACItems.crystal_shard, 1, 14), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetIron", "crystalShardIron", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetCopper", "crystalShardCopper", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetTin", "crystalShardTin", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetAluminium", "crystalShardAluminium", 0.1F);
		AbyssalCraftAPI.addCrystallization("nuggetBronze", "crystalShardCopper", 1, "crystalShardTin", 3, 0.1F);
		AbyssalCraftAPI.addCrystallization("nuggetBrass", "crystalShardCopper", 3, "crystalShardTin", 2, 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.coralium_gem, new ItemStack(ACItems.crystal_shard, 1, 13), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetZinc", "crystalShardZinc", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("nuggetMagnesium", "crystalShardMagnesium", 0.1F);
		AbyssalCraftAPI.addSingleCrystallization("ingotMagnesium", "crystalShardMagnesium", 4, 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.skin, 1, 0), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.essence, 1, 0), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.skin, 1, 1), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.essence, 1, 1), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.skin, 1, 2), new ItemStack(ACItems.crystal_shard, 8, 7), new ItemStack(ACItems.essence, 1, 2), 0.2F);
		AbyssalCraftAPI.addCrystallization(ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACItems.crystal_shard, 1, 12), new ItemStack(ACItems.crystal_shard, 4, 14), 0.2F);

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

		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 12), new ItemStack(ACItems.abyssalnite_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 13), new ItemStack(ACItems.refined_coralium_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 14), new ItemStack(ACItems.dreadium_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACItems.dreadium_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 0), new ItemStack(Items.iron_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 1), new ItemStack(Items.gold_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 2), new ItemStack(ACItems.sulfur, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 15), new ItemStack(Items.blaze_powder, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 11), new ItemStack(Items.redstone, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 5), new ItemStack(ACItems.crystal, 1, 5), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 4), new ItemStack(ACItems.crystal, 1, 4), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 6), new ItemStack(ACItems.crystal, 1, 6), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 10), new ItemStack(ACItems.methane, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 16), new ItemStack(ACItems.tin_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal, 1, 17), new ItemStack(ACItems.copper_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(ACBlocks.darkstone, new ItemStack(Blocks.stone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.stone, new ItemStack(ACBlocks.darkstone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.stonebrick, new ItemStack(ACBlocks.darkstone_brick, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACBlocks.darkstone_brick, new ItemStack(Blocks.stonebrick), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.cobblestone, new ItemStack(ACBlocks.darkstone_cobblestone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACBlocks.darkstone_cobblestone, new ItemStack(Blocks.cobblestone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACBlocks.coralium_stone_brick, new ItemStack(ACItems.coralium_brick, 4), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.coralium_brick, new ItemStack(ACBlocks.coralium_stone, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.liquid_coralium_bucket, new ItemStack(ACBlocks.liquid_coralium, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACBlocks.liquid_coralium, new ItemStack(ACBlocks.coralium_stone, 8), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.liquid_antimatter_bucket, new ItemStack(ACBlocks.liquid_antimatter, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.dense_carbon_cluster, new ItemStack(Items.diamond), 0.5F);
		AbyssalCraftAPI.addTransmutation(ACItems.dread_plagued_gateway_key, new ItemStack(ACItems.rlyehian_gateway_key), 1.0F);
		AbyssalCraftAPI.addTransmutation("crystalAluminium", "ingotAluminum", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalAluminium", "ingotAluminium", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalZinc", "ingotZinc", 0.2F);
		AbyssalCraftAPI.addTransmutation(Items.lava_bucket, new ItemStack(ACBlocks.solid_lava), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.end_stone, new ItemStack(ACBlocks.ethaxium), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACBlocks.ethaxium, new ItemStack(Blocks.end_stone), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.ethaxium_brick, 1, 0), new ItemStack(ACBlocks.ethaxium), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_beef, new ItemStack(Items.cooked_beef), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_pork, new ItemStack(Items.cooked_porkchop), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_chicken, new ItemStack(Items.cooked_chicken), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_bone, new ItemStack(Items.bone, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.rotten_anti_flesh, new ItemStack(Items.rotten_flesh, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_spider_eye, new ItemStack(Items.spider_eye, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_plagued_flesh, new ItemStack(ACItems.coralium_plagued_flesh, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_plagued_flesh_on_a_bone, new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard, 1, 12), new ItemStack(ACItems.ingot_nugget, 1, 0), 0.1F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard, 1, 13), new ItemStack(ACItems.ingot_nugget, 1, 1), 0.1F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard, 1, 14), new ItemStack(ACItems.ingot_nugget, 1, 2), 0.1F);
		AbyssalCraftAPI.addTransmutation("crystalShardAluminium", "nuggetAluminum", 0.1F);
		AbyssalCraftAPI.addTransmutation("crystalShardAluminium", "nuggetAluminium", 0.1F);
		AbyssalCraftAPI.addTransmutation("crystalShardIron", "nuggetIron", 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard, 1, 1), new ItemStack(Items.gold_nugget), 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardCopper", "nuggetCopper", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardTin", "nuggetTin", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardZinc", "nuggetZinc", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalShardMagnesium", "nuggetMagnesium", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalMagnesium", "ingotMagnesium", 0.2F);
	}

	private static void addEngraving(){

		AbyssalCraftAPI.addCoin(ACItems.coin);
		AbyssalCraftAPI.addCoin(ACItems.cthulhu_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.elder_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.jzahar_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.hastur_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.azathoth_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.nyarlathotep_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.yog_sothoth_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.shub_niggurath_engraved_coin);
		AbyssalCraftAPI.addEngraving(ACItems.coin, (ItemEngraving)ACItems.blank_engraving, 0.0F);
		AbyssalCraftAPI.addEngraving(ACItems.cthulhu_engraved_coin, (ItemEngraving)ACItems.cthulhu_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.elder_engraved_coin, (ItemEngraving)ACItems.elder_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.jzahar_engraved_coin, (ItemEngraving)ACItems.jzahar_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.hastur_engraved_coin, (ItemEngraving)ACItems.hastur_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.azathoth_engraved_coin, (ItemEngraving)ACItems.azathoth_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.nyarlathotep_engraved_coin, (ItemEngraving)ACItems.nyarlathotep_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.yog_sothoth_engraved_coin, (ItemEngraving)ACItems.yog_sothoth_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.shub_niggurath_engraved_coin, (ItemEngraving)ACItems.shub_niggurath_engraving, 0.5F);
	}

	private static void addMaterialization(){
		AbyssalCraftAPI.addMaterialization(new ItemStack[]{new ItemStack(ACItems.crystal, 8)}, new ItemStack(Items.bone));
		AbyssalCraftAPI.addMaterialization(new ItemStack[]{new ItemStack(ACItems.crystal, 8, 2)}, new ItemStack(Items.rotten_flesh));
		AbyssalCraftAPI.addMaterialization(new ItemStack[]{new ItemStack(ACItems.crystal, 8), new ItemStack(ACItems.crystal, 13)}, new ItemStack(ACItems.coralium_plagued_flesh));
	}

	private static void addRitualRecipes(){
		RitualRegistry.instance().addDimensionToBookTypeAndName(0, 0, NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId1, 1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId2, 2, NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId3, 3, NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId4, 3, NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE);

		Object[] gk2offerings = new Object[]{new ItemStack(ACItems.transmutation_gem), new ItemStack(ACBlocks.dreadlands_infused_powerstone), new ItemStack(ACItems.eye_of_the_abyss)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("asorahGatewayKey", 1, AbyssalCraft.configDimId1, 10000F, new ItemStack(ACItems.dreaded_gateway_key), new ItemStack(ACItems.gateway_key), gk2offerings));
		Object[] ocofferings = new Object[]{new ItemStack(Items.redstone), new ItemStack(ACItems.shard_of_oblivion), new ItemStack(Items.redstone), new ItemStack(ACItems.shard_of_oblivion),
				new ItemStack(Items.redstone), new ItemStack(ACItems.shard_of_oblivion), new ItemStack(Items.redstone), new ItemStack(ACItems.shard_of_oblivion)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("oblivionCatalyst", 0, 5000F, new ItemStack(ACItems.oblivion_catalyst), new ItemStack(Items.ender_eye), ocofferings));
		Object[] tgofferings = new Object[]{new ItemStack(Items.diamond), new ItemStack(Items.blaze_powder), new ItemStack(Items.ender_pearl), new ItemStack(Items.blaze_powder),
				new ItemStack(Items.diamond), new ItemStack(Items.blaze_powder), new ItemStack(Items.ender_pearl), new ItemStack(Items.blaze_powder)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("transmutationGem", 0, 300F, new ItemStack(ACItems.transmutation_gem), new ItemStack(ACItems.coralium_pearl), tgofferings));
		Object[] depthsofferings = new Object[]{new ItemStack(ACItems.coralium_gem_cluster_9), new ItemStack(ACItems.coralium_gem_cluster_9), new ItemStack(ACItems.liquid_coralium_bucket),
				new ItemStack(Blocks.vine), new ItemStack(Blocks.waterlily), new ItemStack(ACItems.transmutation_gem), new ItemStack(ACItems.coralium_plagued_flesh)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsHelmet", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(ACItems.depths_helmet), new ItemStack(ACItems.refined_coralium_helmet), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsChestplate", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(ACItems.depths_chestplate), new ItemStack(ACItems.refined_coralium_chestplate), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsLeggings", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(ACItems.depths_leggings), new ItemStack(ACItems.refined_coralium_leggings), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsBoots", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(ACItems.depths_boots), new ItemStack(ACItems.refined_coralium_boots), depthsofferings));
		Object[] asorahofferings = new Object[]{new ItemStack(Items.gold_ingot), new ItemStack(ACItems.transmutation_gem), new ItemStack(Items.gold_ingot), new ItemStack(ACItems.liquid_coralium_bucket),
				new ItemStack(Items.gold_ingot), new ItemStack(Blocks.enchanting_table), new ItemStack(Items.gold_ingot)};
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonAsorah", 1, AbyssalCraft.configDimId1, 1000F, EntityDragonBoss.class, asorahofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconBreedingRitual());
		Object[] sacthothofferings = new Object[]{new ItemStack(ACItems.oblivion_catalyst), new ItemStack(Blocks.obsidian), new ItemStack(ACItems.liquid_coralium_bucket), new ItemStack(Blocks.obsidian),
				new ItemStack(ACItems.liquid_antimatter_bucket), new ItemStack(Blocks.obsidian), new ItemStack(ACBlocks.odb_core), new ItemStack(Blocks.obsidian)};
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonSacthoth", 1, 1000F, EntitySacthoth.class, sacthothofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconDreadSpawnRitual());
		Object[] coraoeofferings = new Object[]{new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(Items.potionitem, 1, 0),
				new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(Items.potionitem, 1, 0),new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(Items.gunpowder)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("corPotionAoE", 1, 300F, AbyssalCraftAPI.coralium_plague, coraoeofferings));
		Object[] dreaoeofferings = new Object[]{new ItemStack(ACItems.dread_fragment), new ItemStack(Items.potionitem, 1, 0), new ItemStack(ACItems.dread_fragment), new ItemStack(Items.potionitem, 1, 0),
				new ItemStack(ACItems.dread_fragment), new ItemStack(Items.potionitem, 1, 0), new ItemStack(ACItems.dread_fragment), new ItemStack(Items.gunpowder)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("drePotionAoE", 2, 300F, AbyssalCraftAPI.dread_plague, dreaoeofferings));
		Object[] antiaoeofferings = new Object[]{new ItemStack(ACItems.rotten_anti_flesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(ACItems.rotten_anti_flesh), new ItemStack(Items.potionitem, 1, 0),
				new ItemStack(ACItems.rotten_anti_flesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(ACItems.rotten_anti_flesh), new ItemStack(Items.gunpowder)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("antiPotionAoE", 0, 300F, AbyssalCraftAPI.antimatter_potion, antiaoeofferings));
		Object[] cthulhuofferings = new Object[]{new ItemStack(ACItems.shoggoth_flesh, 1, 0), new ItemStack(ACItems.shoggoth_flesh, 1, 0), new ItemStack(ACItems.shoggoth_flesh, 1, 0),
				new ItemStack(ACItems.shoggoth_flesh, 1, 0), new ItemStack(ACItems.shoggoth_flesh, 1, 0), new ItemStack(ACItems.essence, 1, 0), new ItemStack(ACItems.essence, 1, 1),
				new ItemStack(ACItems.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(ACBlocks.cthulhu_statue), ACBlocks.monolith_stone, cthulhuofferings));
		Object[] hasturofferings = new Object[]{new ItemStack(ACItems.shoggoth_flesh, 1, 1), new ItemStack(ACItems.shoggoth_flesh, 1, 1), new ItemStack(ACItems.shoggoth_flesh, 1, 1),
				new ItemStack(ACItems.shoggoth_flesh, 1, 1), new ItemStack(ACItems.shoggoth_flesh, 1, 1), new ItemStack(ACItems.essence, 1, 0), new ItemStack(ACItems.essence, 1, 1),
				new ItemStack(ACItems.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(ACBlocks.hastur_statue), ACBlocks.monolith_stone, hasturofferings));
		Object[] jzaharofferings = new Object[]{ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale,
				new ItemStack(ACItems.essence, 1, 0), new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(ACBlocks.jzahar_statue), ACBlocks.monolith_stone, jzaharofferings));
		Object[] azathothofferings = new Object[]{new ItemStack(ACItems.shoggoth_flesh, 1, 0), new ItemStack(ACItems.shoggoth_flesh, 1, 1), new ItemStack(ACItems.shoggoth_flesh, 1, 2),
				new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.shoggoth_flesh, 1, 4), new ItemStack(ACItems.essence, 1, 0), new ItemStack(ACItems.essence, 1, 1),
				new ItemStack(ACItems.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(ACBlocks.azathoth_statue), ACBlocks.monolith_stone, azathothofferings));
		Object[] nyarlathotepofferings = new Object[]{new ItemStack(ACItems.shoggoth_flesh, 1, 2), new ItemStack(ACItems.shoggoth_flesh, 1, 2), new ItemStack(ACItems.shoggoth_flesh, 1, 2),
				new ItemStack(ACItems.shoggoth_flesh, 1, 2), new ItemStack(ACItems.shoggoth_flesh, 1, 2), new ItemStack(ACItems.essence, 1, 0), new ItemStack(ACItems.essence, 1, 1),
				new ItemStack(ACItems.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(ACBlocks.nyarlathotep_statue), ACBlocks.monolith_stone, nyarlathotepofferings));
		Object[] yogsothothofferings = new Object[]{new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.shoggoth_flesh, 1, 3),
				new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.shoggoth_flesh, 1, 3), new ItemStack(ACItems.essence, 1, 0), new ItemStack(ACItems.essence, 1, 1),
				new ItemStack(ACItems.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(ACBlocks.yog_sothoth_statue), ACBlocks.monolith_stone, yogsothothofferings));
		Object[] shubniggurathofferings = new Object[]{new ItemStack(ACItems.shoggoth_flesh, 1, 4), new ItemStack(ACItems.shoggoth_flesh, 1, 4), new ItemStack(ACItems.shoggoth_flesh, 1, 4),
				new ItemStack(ACItems.shoggoth_flesh, 1, 4), new ItemStack(ACItems.shoggoth_flesh, 1, 4), new ItemStack(ACItems.essence, 1, 0), new ItemStack(ACItems.essence, 1, 1),
				new ItemStack(ACItems.essence, 1, 2)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(ACBlocks.shub_niggurath_statue), ACBlocks.monolith_stone, shubniggurathofferings));
		Object[] psdlofferings = new Object[]{new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 1),
				new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 1)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("powerStone", 4, AbyssalCraft.configDimId2, 5000F, new ItemStack(ACBlocks.dreadlands_infused_powerstone), ACBlocks.coralium_infused_stone, psdlofferings));
		Object[] ethofferings = new Object[]{ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.life_crystal, ACItems.ethaxium_brick, ACItems.ethaxium_brick};
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("ethaxiumIngot", 3, AbyssalCraft.configDimId3, 1000F, new ItemStack(ACItems.ethaxium_ingot), ethofferings));
		Object[] dreadofferings = new Object[]{new ItemStack(ACItems.essence, 1, 1), ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite,
				ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadHelmet", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(ACItems.dreaded_abyssalnite_helmet), ACItems.abyssalnite_helmet, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadChestplate", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(ACItems.dreaded_abyssalnite_chestplate), ACItems.abyssalnite_chestplate, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadLeggings", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(ACItems.dreaded_abyssalnite_leggings), ACItems.abyssalnite_leggings, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadBoots", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(ACItems.dreaded_abyssalnite_boots), ACItems.abyssalnite_boots, dreadofferings));
		Object[] rcoffers = new Object[]{ACItems.shadow_fragment, Items.arrow, ACItems.shadow_fragment, Items.arrow, ACItems.shadow_fragment, Items.arrow, ACItems.shadow_fragment, Items.arrow};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("rangeCharm", 0, 100F, new ItemStack(ACItems.ritual_charm, 1, 1), new ItemStack(ACItems.ritual_charm, 1, 0), rcoffers));
		Object[] dcoffers = new Object[]{ACItems.shadow_fragment, Items.redstone, ACItems.shadow_fragment, Items.redstone, ACItems.shadow_fragment, Items.redstone, ACItems.shadow_fragment,
				Items.redstone};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("durationCharm", 0, 100F, new ItemStack(ACItems.ritual_charm, 1, 2), new ItemStack(ACItems.ritual_charm, 1, 0), dcoffers));
		Object[] pcoffers = new Object[]{ACItems.shadow_fragment, Items.glowstone_dust, ACItems.shadow_fragment, Items.glowstone_dust, ACItems.shadow_fragment, Items.glowstone_dust,
				ACItems.shadow_fragment, Items.glowstone_dust};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("powerCharm", 0, 100F, new ItemStack(ACItems.ritual_charm, 1, 3), new ItemStack(ACItems.ritual_charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconRespawnJzaharRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("crangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.cthulhu_charm, 1, 1), new ItemStack(ACItems.cthulhu_charm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.cthulhu_charm, 1, 2), new ItemStack(ACItems.cthulhu_charm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.cthulhu_charm, 1, 3), new ItemStack(ACItems.cthulhu_charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.hastur_charm, 1, 1), new ItemStack(ACItems.hastur_charm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.hastur_charm, 1, 2), new ItemStack(ACItems.hastur_charm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.hastur_charm, 1, 3), new ItemStack(ACItems.hastur_charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.jzahar_charm, 1, 1), new ItemStack(ACItems.jzahar_charm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.jzahar_charm, 1, 2), new ItemStack(ACItems.jzahar_charm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.jzahar_charm, 1, 3), new ItemStack(ACItems.jzahar_charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("arangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.azathoth_charm, 1, 1), new ItemStack(ACItems.azathoth_charm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("adurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.azathoth_charm, 1, 2), new ItemStack(ACItems.azathoth_charm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("apowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.azathoth_charm, 1, 3), new ItemStack(ACItems.azathoth_charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.nyarlathotep_charm, 1, 1), new ItemStack(ACItems.nyarlathotep_charm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ndurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.nyarlathotep_charm, 1, 2), new ItemStack(ACItems.nyarlathotep_charm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("npowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.nyarlathotep_charm, 1, 3), new ItemStack(ACItems.nyarlathotep_charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.yog_sothoth_charm, 1, 1), new ItemStack(ACItems.yog_sothoth_charm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ydurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.yog_sothoth_charm, 1, 2), new ItemStack(ACItems.yog_sothoth_charm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ypowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.yog_sothoth_charm, 1, 3), new ItemStack(ACItems.yog_sothoth_charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("srangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.shub_niggurath_charm, 1, 1), new ItemStack(ACItems.shub_niggurath_charm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("sdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.shub_niggurath_charm, 1, 2), new ItemStack(ACItems.shub_niggurath_charm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("spowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(ACItems.shub_niggurath_charm, 1, 3), new ItemStack(ACItems.shub_niggurath_charm, 1, 0), pcoffers));
		Object[] ccoffers = new Object[]{ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin,
				ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuCharm", 4, 2000F, new ItemStack(ACItems.cthulhu_charm, 1, 0), new ItemStack(ACItems.ritual_charm, 1, 0), ccoffers));
		Object[] hcoffers = new Object[]{ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin,
				ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturCharm", 4, 2000F, new ItemStack(ACItems.hastur_charm, 1, 0), new ItemStack(ACItems.ritual_charm, 1, 0), hcoffers));
		Object[] jcoffers = new Object[]{ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin,
				ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharCharm", 4, 2000F, new ItemStack(ACItems.jzahar_charm, 1, 0), new ItemStack(ACItems.ritual_charm, 1, 0), jcoffers));
		Object[] acoffers = new Object[]{ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin,
				ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothCharm", 4, 2000F, new ItemStack(ACItems.azathoth_charm, 1, 0), new ItemStack(ACItems.ritual_charm, 1, 0), acoffers));
		Object[] ncoffers = new Object[]{ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin,
				ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepCharm", 4, 2000F, new ItemStack(ACItems.nyarlathotep_charm, 1, 0), new ItemStack(ACItems.ritual_charm, 1, 0), ncoffers));
		Object[] ycoffers = new Object[]{ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin,
				ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothCharm", 4, 2000F, new ItemStack(ACItems.yog_sothoth_charm, 1, 0), new ItemStack(ACItems.ritual_charm, 1, 0), ycoffers));
		Object[] scoffers = new Object[]{ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin,
				ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathCharm", 4, 2000F, new ItemStack(ACItems.shub_niggurath_charm, 1, 0), new ItemStack(ACItems.ritual_charm, 1, 0), scoffers));
		Object[] owoffers = new Object[]{ACItems.shadow_shard, Blocks.cobblestone, ACItems.coralium_gem, ACBlocks.darkstone_cobblestone, ACItems.shadow_shard, Blocks.cobblestone,
				ACItems.coralium_gem, ACBlocks.darkstone_cobblestone};
		Object[] awoffers = new Object[]{ACItems.shadow_shard, ACBlocks.abyssal_stone_brick, ACItems.coralium_gem, ACBlocks.coralium_stone_brick, ACItems.shadow_shard, ACBlocks.abyssal_stone_brick,
				ACItems.coralium_gem, ACBlocks.coralium_stone_brick};
		Object[] dloffers = new Object[]{ACItems.shadow_shard, ACBlocks.dreadstone_brick, ACItems.coralium_gem, ACBlocks.abyssalnite_stone_brick, ACItems.shadow_shard, ACBlocks.dreadstone_brick,
				ACItems.coralium_gem, ACBlocks.abyssalnite_stone_brick};
		Object[] omtoffers = new Object[]{ACItems.shadow_shard, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0),
				ACItems.shadow_shard, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOWupgrade", 0, 400F, new ItemStack(ACBlocks.tiered_energy_pedestal, 1, 0), ACBlocks.energy_pedestal, owoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epAWupgrade", 1, 800F, new ItemStack(ACBlocks.tiered_energy_pedestal, 1, 1), new ItemStack(ACBlocks.tiered_energy_pedestal, 1, 0), awoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epDLupgrade", 2, 1200F, new ItemStack(ACBlocks.tiered_energy_pedestal, 1, 2), new ItemStack(ACBlocks.tiered_energy_pedestal, 1, 1), dloffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.tiered_energy_pedestal, 1, 3), new ItemStack(ACBlocks.tiered_energy_pedestal, 1, 2), omtoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOWupgrade", 0, 400F, new ItemStack(ACBlocks.tiered_sacrificial_altar, 1, 0), ACBlocks.sacrificial_altar, owoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saAWupgrade", 1, 800F, new ItemStack(ACBlocks.tiered_sacrificial_altar, 1, 1), new ItemStack(ACBlocks.tiered_sacrificial_altar, 1, 0), awoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saDLupgrade", 2, 1200F, new ItemStack(ACBlocks.tiered_sacrificial_altar, 1, 2), new ItemStack(ACBlocks.tiered_sacrificial_altar, 1, 1), dloffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.tiered_sacrificial_altar, 1, 3), new ItemStack(ACBlocks.tiered_sacrificial_altar, 1, 2), omtoffers));
		Object[] staffofferings = new Object[]{new ItemStack(ACItems.essence, 1, 1), new ItemStack(ACItems.essence, 1, 2), ACItems.eldritch_scale, ACItems.ethaxium_ingot,
				ACItems.eldritch_scale, ACItems.ethaxium_ingot, ACItems.eldritch_scale, new ItemStack(ACItems.essence, 1, 0)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStaff", 4, AbyssalCraft.configDimId3, 15000F, true, new ItemStack(ACItems.staff_of_the_gatekeeper), ACItems.staff_of_rending, staffofferings));
	}

	private static void addDisruptions(){
		DisruptionHandler.instance().registerDisruption(new DisruptionLightning());
		DisruptionHandler.instance().registerDisruption(new DisruptionFire());
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnShoggoth", null, EntityLesserShoggoth.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnGatekeeperMinion", DeityType.JZAHAR, EntityGatekeeperMinion.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("poisonPotion", null, MobEffects.poison));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("slownessPotion", null, MobEffects.moveSlowdown));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("weaknessPotion", null, MobEffects.weakness));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("witherPotion", null, MobEffects.wither));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("coraliumPotion", null, AbyssalCraftAPI.coralium_plague));
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
