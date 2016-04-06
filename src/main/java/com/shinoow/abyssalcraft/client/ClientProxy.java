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
package com.shinoow.abyssalcraft.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.client.model.item.ModelDreadiumSamuraiArmor;
import com.shinoow.abyssalcraft.client.render.block.*;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerStarSpawnTentacles;
import com.shinoow.abyssalcraft.client.render.factory.*;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.BlockACSlab;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;

public class ClientProxy extends CommonProxy {

	private static final ModelDreadiumSamuraiArmor chestPlate = new ModelDreadiumSamuraiArmor(1.0f);
	private static final ModelDreadiumSamuraiArmor leggings = new ModelDreadiumSamuraiArmor(0.5f);

	@Override
	public void preInit() {
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilpig.class, new RenderFactoryEvilPig());
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, new RenderFactoryDepthsGhoul());
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, new RenderFactoryAbyssalZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntityODBPrimed.class, new RenderFactoryODB());
		RenderingRegistry.registerEntityRenderingHandler(EntityODBcPrimed.class, new RenderFactoryODBc());
		RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, new RenderFactoryJzahar());
		RenderingRegistry.registerEntityRenderingHandler(EntityAbygolem.class, new RenderFactoryAbyssalniteGolem());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadgolem.class, new RenderFactoryDreadedAbyssalniteGolem());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadguard.class, new RenderFactoryDreadguard());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, new RenderFactoryDragonMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonBoss.class, new RenderFactoryDragonBoss());
		RenderingRegistry.registerEntityRenderingHandler(EntityPSDLTracker.class, new RenderFactoryPowerstoneTracker());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowCreature.class, new RenderFactoryShadowCreature());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowMonster.class, new RenderFactoryShadowMonster());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadling.class, new RenderFactoryDreadling());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSpawn.class, new RenderFactoryDreadSpawn());
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonPig.class, new RenderFactoryDemonPig());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonGoliath.class, new RenderFactorySkeletonGoliath());
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothSpawn.class, new RenderFactoryChagarothSpawn());
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothFist.class, new RenderFactoryChagarothFist());
		RenderingRegistry.registerEntityRenderingHandler(EntityChagaroth.class, new RenderFactoryChagaroth());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowBeast.class, new RenderFactoryShadowBeast());
		RenderingRegistry.registerEntityRenderingHandler(EntitySacthoth.class, new RenderFactorySacthoth());
		RenderingRegistry.registerEntityRenderingHandler(EntityRemnant.class, new RenderFactoryRemnant());
		RenderingRegistry.registerEntityRenderingHandler(EntityOmotholGhoul.class, new RenderFactoryOmotholGhoul());
		RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumArrow.class, new RenderFactoryCoraliumArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperMinion.class, new RenderFactoryGatekeeperMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityGreaterDreadSpawn.class, new RenderFactoryGreaterDreadSpawn());
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserDreadbeast.class, new RenderFactoryLesserDreadbeast());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSlug.class, new RenderFactoryDreadSlug());
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserShoggoth.class, new RenderFactoryLesserShoggoth());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilCow.class, new RenderFactoryEvilCow());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilChicken.class, new RenderFactoryEvilChicken());
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonCow.class, new RenderFactoryDemonCow());
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonChicken.class, new RenderFactoryDemonChicken());

		RenderingRegistry.registerEntityRenderingHandler(EntityAntiAbyssalZombie.class, new RenderFactoryAntiAbyssalZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiBat.class, new RenderFactoryAntiBat());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiChicken.class, new RenderFactoryAntiChicken());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCow.class, new RenderFactoryAntiCow());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCreeper.class, new RenderFactoryAntiCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiGhoul.class, new RenderFactoryAntiGhoul());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPig.class, new RenderFactoryAntiPig());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPlayer.class, new RenderFactoryAntiPlayer());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSkeleton.class, new RenderFactoryAntiSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSpider.class, new RenderFactoryAntiSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiZombie.class, new RenderFactoryAntiZombie());

		ModelBakery.registerItemVariants(ACItems.shoggoth_flesh, makerl("shoggothflesh_overworld", "shoggothflesh_abyssalwasteland",
				"shoggothflesh_dreadlands", "shoggothflesh_omothol", "shoggothflesh_darkrealm"));
		ModelBakery.registerItemVariants(ACItems.essence, makerl("essence_abyssalwasteland", "essence_dreadlands", "essence_omothol"));
		ModelBakery.registerItemVariants(ACItems.skin, makerl("skin_abyssalwasteland", "skin_dreadlands", "skin_omothol"));
		ModelBakery.registerItemVariants(ACItems.ritual_charm, makerl("ritualcharm_empty", "ritualcharm_range", "ritualcharm_duration", "ritualcharm_power"));
		ModelBakery.registerItemVariants(ACItems.ingot_nugget, makerl("nugget_abyssalnite", "nugget_coralium", "nugget_dreadium", "nugget_ethaxium"));

		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ethaxium_brick), makerl("ethaxiumbrick_0", "ethaxiumbrick_1"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.dark_ethaxium_brick), makerl("darkethaxiumbrick_0", "darkethaxiumbrick_1"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_energy_pedestal), makerl("tieredenergypedestal_0", "tieredenergypedestal_1",
				"tieredenergypedestal_2", "tieredenergypedestal_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_sacrificial_altar), makerl("tieredsacrificialaltar_0", "tieredsacrificialaltar_1",
				"tieredsacrificialaltar_2", "tieredsacrificialaltar_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ritual_altar), makerl("ritualaltar_0", "ritualaltar_1", "ritualaltar_2", "ritualaltar_3",
				"ritualaltar_4", "ritualaltar_5", "ritualaltar_6", "ritualaltar_7"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ritual_pedestal), makerl("ritualpedestal_0", "ritualpedestal_1", "ritualpedestal_2",
				"ritualpedestal_3", "ritualpedestal_4", "ritualpedestal_5", "ritualpedestal_6", "ritualpedestal_7"));

		registerFluidModel(ACBlocks.liquid_coralium, "cor");
		registerFluidModel(ACBlocks.liquid_antimatter, "anti");

		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_leaves, new StateMap.Builder().ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadlands_leaves, new StateMap.Builder().ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.oblivion_deathbomb, new StateMap.Builder().ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.odb_core, new StateMap.Builder().ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssal_stone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssalnite_stone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.coralium_stone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.ethaxium_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dark_ethaxium_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_sapling, new StateMap.Builder().ignore(new IProperty[] {BlockSapling.TYPE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadlands_sapling, new StateMap.Builder().ignore(new IProperty[] {BlockSapling.TYPE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.mimic_fire, new StateMap.Builder().ignore(new IProperty[] {BlockFire.AGE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());

		loatheMyselfForBeingALazyFuck(ACBlocks.chagaroth_altar_bottom, TileEntityDreadAltarBottom.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.chagaroth_altar_top, TileEntityDreadAltarTop.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.oblivion_deathbomb, TileEntityODB.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.engraver, TileEntityEngraver.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.cthulhu_statue, TileEntityCthulhuStatue.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.hastur_statue, TileEntityHasturStatue.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.jzahar_statue, TileEntityJzaharStatue.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.azathoth_statue, TileEntityAzathothStatue.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.nyarlathotep_statue, TileEntityNyarlathotepStatue.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.yog_sothoth_statue, TileEntityYogsothothStatue.class);
		loatheMyselfForBeingALazyFuck(ACBlocks.shub_niggurath_statue, TileEntityShubniggurathStatue.class);

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftClientEventHooks());
	}

	@Override
	public void init(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDGhead.class, new TileEntityDGheadRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhead.class, new TileEntityPheadRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWhead.class, new TileEntityWheadRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOhead.class, new TileEntityOheadRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDreadAltarBottom.class, new TileEntityDreadAltarBottomRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDreadAltarTop.class, new TileEntityDreadAltarTopRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityODB.class, new TileEntityODBRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEngraver.class, new TileEntityEngraverRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRitualAltar.class, new TileEntityRitualAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRitualPedestal.class, new TileEntityRitualPedestalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCthulhuStatue.class, new TileEntityCthulhuStatueRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHasturStatue.class, new TileEntityHasturStatueRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJzaharStatue.class, new TileEntityJzaharStatueRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAzathothStatue.class, new TileEntityAzathothStatueRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNyarlathotepStatue.class, new TileEntityNyarlathotepStatueRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityYogsothothStatue.class, new TileEntityYogsothothStatueRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShubniggurathStatue.class, new TileEntityShubniggurathStatueRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergyPedestal.class, new TileEntityEnergyPedestalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySacrificialAltar.class, new TileEntitySacrificialAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTieredEnergyPedestal.class, new TileEntityTieredEnergyPedestalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTieredSacrificialAltar.class, new TileEntityTieredSacrificialAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJzaharSpawner.class, new TileEntityJzaharSpawnerRenderer());

		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.PSDL), new Block3DRender(new TileEntityPSDLRenderer(), new TileEntityPSDL()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.Altar), new Block3DRender(new TileEntityAltarRenderer(), new TileEntityAltar()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.ODB), new Block3DRender(new TileEntityODBRenderer(), new TileEntityODB()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.dreadaltarbottom), new Block3DRender(new TileEntityDreadAltarBottomRenderer(), new TileEntityDreadAltarBottom()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.dreadaltartop), new Block3DRender(new TileEntityDreadAltarTopRenderer(), new TileEntityDreadAltarTop()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.engraver), new Block3DRender(new TileEntityEngraverRenderer(), new TileEntityEngraver()));
		//		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.Staff, new RenderStaff());
		//		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.cudgel, new RenderCudgel());
		//		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.dreadhilt, new RenderHilt());
		//		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.dreadkatana, new RenderKatana());
		//		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.corbow, new RenderCoraliumBow());
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.ritual_altar), new RenderRitualAltar());
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.ritual_pedestal), new RenderRitualPedestal());
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.cthulhuStatue), new Block3DRender(new TileEntityCthulhuStatueRenderer(), new TileEntityCthulhuStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.hasturStatue), new Block3DRender(new TileEntityHasturStatueRenderer(), new TileEntityHasturStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.jzaharStatue), new Block3DRender(new TileEntityJzaharStatueRenderer(), new TileEntityJzaharStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.azathothStatue), new Block3DRender(new TileEntityAzathothStatueRenderer(), new TileEntityAzathothStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.nyarlathotepStatue), new Block3DRender(new TileEntityNyarlathotepStatueRenderer(), new TileEntityNyarlathotepStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.yogsothothStatue), new Block3DRender(new TileEntityYogsothothStatueRenderer(), new TileEntityYogsothothStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.shubniggurathStatue), new Block3DRender(new TileEntityShubniggurathStatueRenderer(), new TileEntityShubniggurathStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.energyPedestal), new Block3DRender(new TileEntityEnergyPedestalRenderer(), new TileEntityEnergyPedestal()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.sacrificialAltar), new Block3DRender(new TileEntitySacrificialAltarRenderer(), new TileEntitySacrificialAltar()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.tiered_energy_pedestal), new RenderTieredEnergyPedestal());
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ACBlocks.tiered_sacrificial_altar), new RenderTieredSacrificialAltar());

		registerItemRender(AbyssalCraft.devsword, 0);
		registerItemRender(ACItems.oblivion_catalyst, 0);
		registerItemRender(ACItems.gateway_key, 0);
		registerItemRender(ACItems.staff_of_the_gatekeeper, 0);
		registerItemRender(ACItems.liquid_coralium_bucket, 0);
		registerItemRender(ACItems.powerstone_tracker, 0);
		registerItemRender(ACItems.eye_of_the_abyss, 0);
		registerItemRender(ACItems.dreaded_gateway_key, 0);
		registerItemRender(ACItems.dreaded_shard_of_abyssalnite, 0);
		registerItemRender(ACItems.dreaded_chunk_of_abyssalnite, 0);
		registerItemRender(ACItems.chunk_of_abyssalnite, 0);
		registerItemRender(ACItems.abyssalnite_ingot, 0);
		registerItemRender(ACItems.coralium_gem, 0);
		registerItemRender(ACItems.coralium_gem_cluster_2, 0);
		registerItemRender(ACItems.coralium_gem_cluster_3, 0);
		registerItemRender(ACItems.coralium_gem_cluster_4, 0);
		registerItemRender(ACItems.coralium_gem_cluster_5, 0);
		registerItemRender(ACItems.coralium_gem_cluster_6, 0);
		registerItemRender(ACItems.coralium_gem_cluster_7, 0);
		registerItemRender(ACItems.coralium_gem_cluster_8, 0);
		registerItemRender(ACItems.coralium_gem_cluster_9, 0);
		registerItemRender(ACItems.coralium_pearl, 0);
		registerItemRender(ACItems.chunk_of_coralium, 0);
		registerItemRender(ACItems.refined_coralium_ingot, 0);
		registerItemRender(ACItems.coralium_plate, 0);
		registerItemRender(ACItems.transmutation_gem, 0);
		registerItemRender(ACItems.coralium_plagued_flesh, 0);
		registerItemRender(ACItems.coralium_plagued_flesh_on_a_bone, 0);
		registerItemRender(ACItems.darkstone_pickaxe, 0);
		registerItemRender(ACItems.darkstone_axe, 0);
		registerItemRender(ACItems.darkstone_shovel, 0);
		registerItemRender(ACItems.darkstone_sword, 0);
		registerItemRender(ACItems.darkstone_hoe, 0);
		registerItemRender(ACItems.abyssalnite_pickaxe, 0);
		registerItemRender(ACItems.abyssalnite_axe, 0);
		registerItemRender(ACItems.abyssalnite_shovel, 0);
		registerItemRender(ACItems.abyssalnite_sword, 0);
		registerItemRender(ACItems.abyssalnite_hoe, 0);
		registerItemRender(ACItems.refined_coralium_pickaxe, 0);
		registerItemRender(ACItems.refined_coralium_axe, 0);
		registerItemRender(ACItems.refined_coralium_shovel, 0);
		registerItemRender(ACItems.refined_coralium_sword, 0);
		registerItemRender(ACItems.refined_coralium_hoe, 0);
		registerItemRender(ACItems.abyssalnite_boots, 0);
		registerItemRender(ACItems.abyssalnite_helmet, 0);
		registerItemRender(ACItems.abyssalnite_chestplate, 0);
		registerItemRender(ACItems.abyssalnite_leggings, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_boots, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_helmet, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_chestplate, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_leggings, 0);
		registerItemRender(ACItems.refined_coralium_boots, 0);
		registerItemRender(ACItems.refined_coralium_helmet, 0);
		registerItemRender(ACItems.refined_coralium_chestplate, 0);
		registerItemRender(ACItems.refined_coralium_leggings, 0);
		registerItemRender(ACItems.plated_coralium_boots, 0);
		registerItemRender(ACItems.plated_coralium_helmet, 0);
		registerItemRender(ACItems.plated_coralium_chestplate, 0);
		registerItemRender(ACItems.plated_coralium_leggings, 0);
		registerItemRender(ACItems.depths_boots, 0);
		registerItemRender(ACItems.depths_helmet, 0);
		registerItemRender(ACItems.depths_chestplate, 0);
		registerItemRender(ACItems.depths_leggings, 0);
		registerItemRender(ACItems.cobblestone_upgrade_kit, 0);
		registerItemRender(ACItems.iron_upgrade_kit, 0);
		registerItemRender(ACItems.gold_upgrade_kit, 0);
		registerItemRender(ACItems.diamond_upgrade_kit, 0);
		registerItemRender(ACItems.abyssalnite_upgrade_kit, 0);
		registerItemRender(ACItems.coralium_upgrade_kit, 0);
		registerItemRender(ACItems.mre, 0);
		registerItemRender(ACItems.iron_plate, 0);
		registerItemRender(ACItems.chicken_on_a_plate, 0);
		registerItemRender(ACItems.pork_on_a_plate, 0);
		registerItemRender(ACItems.beef_on_a_plate, 0);
		registerItemRender(ACItems.fish_on_a_plate, 0);
		registerItemRender(ACItems.dirty_plate, 0);
		registerItemRender(ACItems.fried_egg, 0);
		registerItemRender(ACItems.fried_egg_on_a_plate, 0);
		registerItemRender(ACItems.washcloth, 0);
		registerItemRender(ACItems.shadow_fragment, 0);
		registerItemRender(ACItems.shadow_shard, 0);
		registerItemRender(ACItems.shadow_gem, 0);
		registerItemRender(ACItems.shard_of_oblivion, 0);
		registerItemRender(ACItems.coralium_longbow, 0);
		registerItemRender(ACItems.liquid_antimatter_bucket, 0);
		registerItemRender(ACItems.coralium_brick, 0);
		registerItemRender(ACItems.cudgel, 0);
		registerItemRender(ACItems.dreadium_ingot, 0);
		registerItemRender(ACItems.dread_fragment, 0);
		registerItemRender(ACItems.dreadium_boots, 0);
		registerItemRender(ACItems.dreadium_helmet, 0);
		registerItemRender(ACItems.dreadium_chestplate, 0);
		registerItemRender(ACItems.dreadium_leggings, 0);
		registerItemRender(ACItems.dreadium_pickaxe, 0);
		registerItemRender(ACItems.dreadium_axe, 0);
		registerItemRender(ACItems.dreadium_shovel, 0);
		registerItemRender(ACItems.dreadium_sword, 0);
		registerItemRender(ACItems.dreadium_hoe, 0);
		registerItemRender(ACItems.dreadium_upgrade_kit, 0);
		registerItemRender(ACItems.carbon_cluster, 0);
		registerItemRender(ACItems.dense_carbon_cluster, 0);
		registerItemRender(ACItems.methane, 0);
		registerItemRender(ACItems.nitre, 0);
		registerItemRender(ACItems.sulfur, 0);
		registerItemRenders(ACItems.crystal, 25);
		registerItemRenders(ACItems.crystal_shard, 25);
		registerItemRender(ACItems.dread_cloth, 0);
		registerItemRender(ACItems.dreadium_plate, 0);
		registerItemRender(ACItems.dreadium_katana_blade, 0);
		registerItemRender(ACItems.dreadium_katana_hilt, 0);
		registerItemRender(ACItems.dreadium_katana, 0);
		registerItemRender(ACItems.dread_plagued_gateway_key, 0);
		registerItemRender(ACItems.rlyehian_gateway_key, 0);
		registerItemRender(ACItems.dreadium_samurai_boots, 0);
		registerItemRender(ACItems.dreadium_samurai_helmet, 0);
		registerItemRender(ACItems.dreadium_samurai_chestplate, 0);
		registerItemRender(ACItems.dreadium_samurai_leggings, 0);
		registerItemRender(ACItems.tin_ingot, 0);
		registerItemRender(ACItems.copper_ingot, 0);
		registerItemRender(ACItems.anti_beef, 0);
		registerItemRender(ACItems.anti_chicken, 0);
		registerItemRender(ACItems.anti_pork, 0);
		registerItemRender(ACItems.rotten_anti_flesh, 0);
		registerItemRender(ACItems.anti_bone, 0);
		registerItemRender(ACItems.anti_spider_eye, 0);
		registerItemRender(ACItems.sacthoths_soul_harvesting_blade, 0);
		registerItemRender(ACItems.ethaxium_brick, 0);
		registerItemRender(ACItems.ethaxium_ingot, 0);
		registerItemRender(ACItems.life_crystal, 0);
		registerItemRender(ACItems.ethaxium_boots, 0);
		registerItemRender(ACItems.ethaxium_helmet, 0);
		registerItemRender(ACItems.ethaxium_chestplate, 0);
		registerItemRender(ACItems.ethaxium_leggings, 0);
		registerItemRender(ACItems.ethaxium_pickaxe, 0);
		registerItemRender(ACItems.ethaxium_axe, 0);
		registerItemRender(ACItems.ethaxium_shovel, 0);
		registerItemRender(ACItems.ethaxium_sword, 0);
		registerItemRender(ACItems.ethaxium_hoe, 0);
		registerItemRender(ACItems.ethaxium_upgrade_kit, 0);
		registerItemRender(ACItems.coin, 0);
		registerItemRender(ACItems.cthulhu_engraved_coin, 0);
		registerItemRender(ACItems.elder_engraved_coin, 0);
		registerItemRender(ACItems.jzahar_engraved_coin, 0);
		registerItemRender(ACItems.blank_engraving, 0);
		registerItemRender(ACItems.cthulhu_engraving, 0);
		registerItemRender(ACItems.elder_engraving, 0);
		registerItemRender(ACItems.jzahar_engraving, 0);
		registerItemRender(ACItems.eldritch_scale, 0);
		registerItemRender(ACItems.omothol_flesh, 0);
		registerItemRender(ACItems.anti_plagued_flesh, 0);
		registerItemRender(ACItems.anti_plagued_flesh_on_a_bone, 0);
		registerItemRender(ACItems.necronomicon, 0);
		registerItemRender(ACItems.abyssal_wasteland_necronomicon, 0);
		registerItemRender(ACItems.dreadlands_necronomicon, 0);
		registerItemRender(ACItems.omothol_necronomicon, 0);
		registerItemRender(ACItems.abyssalnomicon, 0);
		registerItemRender(ACItems.small_crystal_bag, 0);
		registerItemRender(ACItems.medium_crystal_bag, 0);
		registerItemRender(ACItems.large_crystal_bag, 0);
		registerItemRender(ACItems.huge_crystal_bag, 0);
		registerItemRender(ACItems.shoggoth_flesh, 0, "shoggothflesh_overworld");
		registerItemRender(ACItems.shoggoth_flesh, 1, "shoggothflesh_abyssalwasteland");
		registerItemRender(ACItems.shoggoth_flesh, 2, "shoggothflesh_dreadlands");
		registerItemRender(ACItems.shoggoth_flesh, 3, "shoggothflesh_omothol");
		registerItemRender(ACItems.shoggoth_flesh, 4, "shoggothflesh_darkrealm");
		registerItemRender(ACItems.ingot_nugget, 0, "nugget_abyssalnite");
		registerItemRender(ACItems.ingot_nugget, 1, "nugget_coralium");
		registerItemRender(ACItems.ingot_nugget, 2, "nugget_dreadium");
		registerItemRender(ACItems.ingot_nugget, 3, "nugget_ethaxium");
		registerItemRender(ACItems.staff_of_rending, 0);
		registerItemRender(ACItems.essence, 0, "essence_abyssalwasteland");
		registerItemRender(ACItems.essence, 1, "essence_dreadlands");
		registerItemRender(ACItems.essence, 2, "essence_omothol");
		registerItemRender(ACItems.skin, 0, "skin_abyssalwasteland");
		registerItemRender(ACItems.skin, 1, "skin_dreadlands");
		registerItemRender(ACItems.skin, 2, "skin_omothol");
		registerItemRender(ACItems.ritual_charm, 0, "ritualcharm_empty");
		registerItemRender(ACItems.ritual_charm, 1, "ritualcharm_range");
		registerItemRender(ACItems.ritual_charm, 2, "ritualcharm_duration");
		registerItemRender(ACItems.ritual_charm, 3, "ritualcharm_power");
		registerItemRenders(ACItems.cthulhu_charm, 4);
		registerItemRenders(ACItems.hastur_charm, 4);
		registerItemRenders(ACItems.jzahar_charm, 4);
		registerItemRenders(ACItems.azathoth_charm, 4);
		registerItemRenders(ACItems.nyarlathotep_charm, 4);
		registerItemRenders(ACItems.yog_sothoth_charm, 4);
		registerItemRenders(ACItems.shub_niggurath_charm, 4);
		registerItemRender(ACItems.hastur_engraved_coin, 0);
		registerItemRender(ACItems.azathoth_engraved_coin, 0);
		registerItemRender(ACItems.nyarlathotep_engraved_coin, 0);
		registerItemRender(ACItems.yog_sothoth_engraved_coin, 0);
		registerItemRender(ACItems.shub_niggurath_engraved_coin, 0);
		registerItemRender(ACItems.hastur_engraving, 0);
		registerItemRender(ACItems.azathoth_engraving, 0);
		registerItemRender(ACItems.nyarlathotep_engraving, 0);
		registerItemRender(ACItems.yog_sothoth_engraving, 0);
		registerItemRender(ACItems.shub_niggurath_engraving, 0);
		registerItemRender(ACItems.essence_of_the_gatekeeper, 0);

		registerItemRender(ACBlocks.darkstone, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone, 0);
		registerItemRender(ACBlocks.darkstone_brick, 0);
		registerItemRender(ACBlocks.glowing_darkstone_bricks, 0);
		registerItemRender(ACBlocks.darkstone_brick_slab, 0);
		registerItemRender(AbyssalCraft.Darkbrickslab2, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_slab, 0);
		registerItemRender(AbyssalCraft.Darkcobbleslab2, 0);
		registerItemRender(ACBlocks.darklands_grass, 0);
		registerItemRender(ACBlocks.darkstone_brick_stairs, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.darklands_oak_leaves, 0);
		registerItemRender(ACBlocks.darklands_oak_wood, 0);
		registerItemRender(ACBlocks.darklands_oak_sapling, 0);
		registerItemRender(ACBlocks.abyssal_stone, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_slab, 0);
		registerItemRender(AbyssalCraft.abyslab2, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.coralium_ore, 0);
		registerItemRender(ACBlocks.abyssalnite_ore, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_fence, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_wall, 0);
		registerItemRender(ACBlocks.oblivion_deathbomb, 0);
		registerItemRender(ACBlocks.block_of_abyssalnite, 0);
		registerItemRender(ACBlocks.coralium_infused_stone, 0);
		registerItemRender(ACBlocks.odb_core, 0);
		registerItemRender(ACBlocks.wooden_crate, 0);
		registerItemRender(ACBlocks.abyssal_gateway, 0);
		registerItemRender(ACBlocks.darkstone_slab, 0);
		registerItemRender(AbyssalCraft.Darkstoneslab2, 0);
		registerItemRender(ACBlocks.coralium_fire, 0);
		registerItemRender(ACBlocks.darkstone_button, 0);
		registerItemRender(ACBlocks.darkstone_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_planks, 0);
		registerItemRender(ACBlocks.darklands_oak_button, 0);
		registerItemRender(ACBlocks.darklands_oak_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_stairs, 0);
		registerItemRender(ACBlocks.darklands_oak_slab, 0);
		registerItemRender(AbyssalCraft.DLTslab2, 0);
		registerItemRender(ACBlocks.block_of_coralium, 0);
		registerItemRender(ACBlocks.dreadlands_infused_powerstone, 0);
		registerItemRender(ACBlocks.abyssal_coralium_ore, 0);
		registerItemRender(AbyssalCraft.Altar, 0);
		registerItemRender(ACBlocks.abyssal_stone_button, 0);
		registerItemRender(ACBlocks.abyssal_stone_pressure_plate, 0);
		registerItemRender(ACBlocks.darkstone_brick_fence, 0);
		registerItemRender(ACBlocks.darklands_oak_fence, 0);
		registerItemRender(ACBlocks.dreadstone, 0);
		registerItemRender(ACBlocks.abyssalnite_stone, 0);
		registerItemRender(ACBlocks.dreadlands_abyssalnite_ore, 0);
		registerItemRender(ACBlocks.dreaded_abyssalnite_ore, 0);
		registerItemRender(ACBlocks.dreadstone_brick, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick, 0);
		registerItemRender(ACBlocks.dreadlands_grass, 0);
		registerItemRender(ACBlocks.dreadlands_log, 0);
		registerItemRender(ACBlocks.dreadlands_leaves, 0);
		registerItemRender(ACBlocks.dreadlands_sapling, 0);
		registerItemRender(ACBlocks.dreadlands_planks, 0);
		registerItemRender(ACBlocks.dreaded_gateway, 0);
		registerItemRender(ACBlocks.dreaded_fire, 0);
		registerItemRender(ACBlocks.depths_ghoul_head, 0);
		registerItemRender(ACBlocks.pete_head, 0);
		registerItemRender(ACBlocks.mr_wilson_head, 0);
		registerItemRender(ACBlocks.dr_orange_head, 0);
		registerItemRender(ACBlocks.dreadstone_brick_stairs, 0);
		registerItemRender(ACBlocks.dreadstone_brick_fence, 0);
		registerItemRender(ACBlocks.dreadstone_brick_slab, 0);
		registerItemRender(AbyssalCraft.dreadbrickslab2, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick_fence, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick_slab, 0);
		registerItemRender(AbyssalCraft.abydreadbrickslab2, 0);
		registerItemRender(ACBlocks.coralium_stone, 0);
		registerItemRender(ACBlocks.coralium_stone_brick, 0);
		registerItemRender(ACBlocks.coralium_stone_brick_fence, 0);
		registerItemRender(ACBlocks.coralium_stone_brick_slab, 0);
		registerItemRender(AbyssalCraft.cstonebrickslab2, 0);
		registerItemRender(ACBlocks.coralium_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.coralium_stone_button, 0);
		registerItemRender(ACBlocks.coralium_stone_pressure_plate, 0);
		registerItemRender(ACBlocks.chagaroth_altar_top, 0);
		registerItemRender(ACBlocks.chagaroth_altar_bottom, 0);
		registerItemRender(ACBlocks.crystallizer_idle, 0);
		registerItemRender(ACBlocks.crystallizer_active, 0);
		registerItemRender(ACBlocks.block_of_dreadium, 0);
		registerItemRender(ACBlocks.transmutator_idle, 0);
		registerItemRender(ACBlocks.transmutator_active, 0);
		registerItemRender(ACBlocks.dreadguard_spawner, 0);
		registerItemRender(ACBlocks.chagaroth_spawner, 0);
		registerItemRender(ACBlocks.jzahar_spawner, 0);
		registerItemRender(ACBlocks.dreadlands_wood_fence, 0);
		registerItemRender(ACBlocks.nitre_ore, 0);
		registerItemRender(ACBlocks.abyssal_iron_ore, 0);
		registerItemRender(ACBlocks.abyssal_gold_ore, 0);
		registerItemRender(ACBlocks.abyssal_diamond_ore, 0);
		registerItemRender(ACBlocks.abyssal_nitre_ore, 0);
		registerItemRender(ACBlocks.abyssal_tin_ore, 0);
		registerItemRender(ACBlocks.abyssal_copper_ore, 0);
		registerItemRender(ACBlocks.pearlescent_coralium_ore, 0);
		registerItemRender(ACBlocks.liquified_coralium_ore, 0);
		registerItemRender(ACBlocks.solid_lava, 0);
		registerItemRender(ACBlocks.ethaxium, 0);
		registerItemRender(ACBlocks.ethaxium_brick, 0, "ethaxiumbrick_0");
		registerItemRender(ACBlocks.ethaxium_brick, 1, "ethaxiumbrick_1");
		registerItemRender(ACBlocks.ethaxium_pillar, 0);
		registerItemRender(ACBlocks.ethaxium_brick_stairs, 0);
		registerItemRender(ACBlocks.ethaxium_brick_slab, 0);
		registerItemRender(AbyssalCraft.ethaxiumslab2, 0);
		registerItemRender(ACBlocks.ethaxium_brick_fence, 0);
		registerItemRender(ACBlocks.block_of_ethaxium, 0);
		registerItemRender(ACBlocks.omothol_stone, 0);
		registerItemRender(ACBlocks.omothol_gateway, 0);
		registerItemRender(ACBlocks.omothol_fire, 0);
		registerItemRender(ACBlocks.engraver, 0);
		registerItemRender(AbyssalCraft.house, 0);
		registerItemRender(ACBlocks.materializer, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick, 0, "darkethaxiumbrick_0");
		registerItemRender(ACBlocks.dark_ethaxium_brick, 1, "darkethaxiumbrick_1");
		registerItemRender(ACBlocks.dark_ethaxium_pillar, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_stairs, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_slab, 0);
		registerItemRender(AbyssalCraft.darkethaxiumslab2, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_fence, 0);
		registerItemRender(ACBlocks.ritual_altar, 0, "ritualaltar_0");
		registerItemRender(ACBlocks.ritual_altar, 1, "ritualaltar_1");
		registerItemRender(ACBlocks.ritual_altar, 2, "ritualaltar_2");
		registerItemRender(ACBlocks.ritual_altar, 3, "ritualaltar_3");
		registerItemRender(ACBlocks.ritual_altar, 4, "ritualaltar_4");
		registerItemRender(ACBlocks.ritual_altar, 5, "ritualaltar_5");
		registerItemRender(ACBlocks.ritual_altar, 6, "ritualaltar_6");
		registerItemRender(ACBlocks.ritual_altar, 7, "ritualaltar_7");
		registerItemRender(ACBlocks.ritual_pedestal, 0, "ritualpedestal_0");
		registerItemRender(ACBlocks.ritual_pedestal, 1, "ritualpedestal_1");
		registerItemRender(ACBlocks.ritual_pedestal, 2, "ritualpedestal_2");
		registerItemRender(ACBlocks.ritual_pedestal, 3, "ritualpedestal_3");
		registerItemRender(ACBlocks.ritual_pedestal, 4, "ritualpedestal_4");
		registerItemRender(ACBlocks.ritual_pedestal, 5, "ritualpedestal_5");
		registerItemRender(ACBlocks.ritual_pedestal, 6, "ritualpedestal_6");
		registerItemRender(ACBlocks.ritual_pedestal, 7, "ritualpedestal_7");
		registerItemRender(ACBlocks.shoggoth_ooze, 0);
		registerItemRender(ACBlocks.cthulhu_statue, 0);
		registerItemRender(ACBlocks.hastur_statue, 0);
		registerItemRender(ACBlocks.jzahar_statue, 0);
		registerItemRender(ACBlocks.azathoth_statue, 0);
		registerItemRender(ACBlocks.nyarlathotep_statue, 0);
		registerItemRender(ACBlocks.yog_sothoth_statue, 0);
		registerItemRender(ACBlocks.shub_niggurath_statue, 0);
		registerItemRender(ACBlocks.monolith_stone, 0);
		registerItemRender(ACBlocks.shoggoth_biomass, 0);
		registerItemRender(ACBlocks.energy_pedestal, 0);
		registerItemRender(ACBlocks.monolith_pillar, 0);
		registerItemRender(ACBlocks.sacrificial_altar, 0);
		registerItemRender(ACBlocks.tiered_energy_pedestal, 0, "tieredenergypedestal_0");
		registerItemRender(ACBlocks.tiered_energy_pedestal, 1, "tieredenergypedestal_1");
		registerItemRender(ACBlocks.tiered_energy_pedestal, 2, "tieredenergypedestal_2");
		registerItemRender(ACBlocks.tiered_energy_pedestal, 3, "tieredenergypedestal_3");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 0, "tieredsacrificialaltar_0");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 1, "tieredsacrificialaltar_1");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 2, "tieredsacrificialaltar_2");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 3, "tieredsacrificialaltar_3");
		registerItemRender(ACBlocks.minion_of_the_gatekeeper_spawner, 0);
		registerItemRender(ACBlocks.mimic_fire, 0);

		RenderPlayer render1 = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		render1.addLayer(new LayerStarSpawnTentacles(render1));
		RenderPlayer render2 = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
		render2.addLayer(new LayerStarSpawnTentacles(render2));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor(){

			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				int[] crystalColors = new int[]{0xD9D9D9, 0xF3CC3E, 0xF6FF00, 0x3D3D36, 16777215, 16777215, 16777215, 0x996A18,
						0xD9D9D9, 0x1500FF, 0x19FC00, 0xFF0000, 0x8002BF, 0x00FFEE, 0xB00000, 0xFFCC00, 0xD9D8D7, 0xE89207, 0xD9D9D9,
						0xD9D9D9, 0xD9D9D9, 16777215, 0xD9D8D9, 16777215, 0xD7D8D9};
				return crystalColors[stack.getItemDamage()];
			}

		}, ACItems.crystal, ACItems.crystal_shard);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor(){

			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return 0xE8E8E8;
			}

		}, ACItems.coin, ACItems.elder_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.hastur_engraved_coin, ACItems.jzahar_engraved_coin,
		ACItems.azathoth_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.shub_niggurath_engraved_coin);
	}

	private void registerFluidModel(Block fluidBlock, String name) {
		Item item = Item.getItemFromBlock(fluidBlock);

		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation("abyssalcraft:fluid", name);

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition(){

			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {

				return modelResourceLocation;
			}

		});

		ModelLoader.setCustomStateMapper(fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}

	protected void registerItemRender(Item item, int meta, String res){
		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		render.getItemModelMesher().register(item, meta, new ModelResourceLocation("abyssalcraft:" + res, "inventory"));
	}

	protected void registerItemRender(Item item, int meta){
		registerItemRender(item, meta, item.getUnlocalizedName().substring(5));
	}

	protected void registerItemRenders(Item item, int metas){
		for(int i = 0; i < metas; i++)
			registerItemRender(item, i);
	}

	protected void registerItemRender(Block block, int meta, String res){
		registerItemRender(Item.getItemFromBlock(block), meta, res);
	}

	protected void registerItemRender(Block block, int meta){
		registerItemRender(block, meta, block.getUnlocalizedName().substring(5));
	}

	protected void registerItemRenders(Block block, int metas){
		for(int i = 0; i < metas; i++)
			registerItemRender(block, i);
	}

	private ResourceLocation[] makerl(String...strings){
		ResourceLocation[] res = new ResourceLocation[strings.length];
		for(int i = 0; i < strings.length; i++)
			res[i] = new ResourceLocation("abyssalcraft", strings[i]);
		return res;
	}

	@SuppressWarnings("deprecation")
	private void loatheMyselfForBeingALazyFuck(Block block, Class<? extends TileEntity> tile){
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(block), 0, tile);
	}

	@Override
	public ModelBiped getArmorModel(int id){
		switch (id) {
		case 0:
			return chestPlate;
		case 1:
			return leggings;
		default:
			break;
		}
		return chestPlate;
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work because you will be getting a client
		// player even when you are on the server! Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		return ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx);
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx);
	}
}