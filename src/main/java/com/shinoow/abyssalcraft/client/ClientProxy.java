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

		ModelBakery.registerItemVariants(AbyssalCraft.shoggothFlesh, makerl("shoggothflesh_overworld", "shoggothflesh_abyssalwasteland",
				"shoggothflesh_dreadlands", "shoggothflesh_omothol", "shoggothflesh_darkrealm"));
		ModelBakery.registerItemVariants(AbyssalCraft.essence, makerl("essence_abyssalwasteland", "essence_dreadlands", "essence_omothol"));
		ModelBakery.registerItemVariants(AbyssalCraft.skin, makerl("skin_abyssalwasteland", "skin_dreadlands", "skin_omothol"));
		ModelBakery.registerItemVariants(AbyssalCraft.charm, makerl("ritualcharm_empty", "ritualcharm_range", "ritualcharm_duration", "ritualcharm_power"));
		ModelBakery.registerItemVariants(AbyssalCraft.nugget, makerl("nugget_abyssalnite", "nugget_coralium", "nugget_dreadium", "nugget_ethaxium"));

		ModelBakery.registerItemVariants(Item.getItemFromBlock(AbyssalCraft.ethaxiumbrick), makerl("ethaxiumbrick_0", "ethaxiumbrick_1"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(AbyssalCraft.darkethaxiumbrick), makerl("darkethaxiumbrick_0", "darkethaxiumbrick_1"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(AbyssalCraft.tieredEnergyPedestal), makerl("tieredenergypedestal_0", "tieredenergypedestal_1",
				"tieredenergypedestal_2", "tieredenergypedestal_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(AbyssalCraft.tieredSacrificialAltar), makerl("tieredsacrificialaltar_0", "tieredsacrificialaltar_1",
				"tieredsacrificialaltar_2", "tieredsacrificialaltar_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(AbyssalCraft.ritualaltar), makerl("ritualaltar_0", "ritualaltar_1", "ritualaltar_2", "ritualaltar_3",
				"ritualaltar_4", "ritualaltar_5", "ritualaltar_6", "ritualaltar_7"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(AbyssalCraft.ritualpedestal), makerl("ritualpedestal_0", "ritualpedestal_1", "ritualpedestal_2",
				"ritualpedestal_3", "ritualpedestal_4", "ritualpedestal_5", "ritualpedestal_6", "ritualpedestal_7"));

		registerFluidModel(AbyssalCraft.Cwater, "cor");
		registerFluidModel(AbyssalCraft.anticwater, "anti");

		ModelLoader.setCustomStateMapper(AbyssalCraft.DLTLeaves, new StateMap.Builder().ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.dreadleaves, new StateMap.Builder().ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.ODB, new StateMap.Builder().ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.ODBcore, new StateMap.Builder().ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.Darkbrickslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.Darkcobbleslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.abyslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.Darkstoneslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.DLTslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.dreadbrickslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.abydreadbrickslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.cstonebrickslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.ethaxiumslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.darkethaxiumslab1, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.DLTSapling, new StateMap.Builder().ignore(new IProperty[] {BlockSapling.TYPE}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.dreadsapling, new StateMap.Builder().ignore(new IProperty[] {BlockSapling.TYPE}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.mimicFire, new StateMap.Builder().ignore(new IProperty[] {BlockFire.AGE}).build());
		ModelLoader.setCustomStateMapper(AbyssalCraft.DSCwall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());

		loatheMyselfForBeingALazyFuck(AbyssalCraft.dreadaltarbottom, TileEntityDreadAltarBottom.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.dreadaltartop, TileEntityDreadAltarTop.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.ODB, TileEntityODB.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.engraver, TileEntityEngraver.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.cthulhuStatue, TileEntityCthulhuStatue.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.hasturStatue, TileEntityHasturStatue.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.jzaharStatue, TileEntityJzaharStatue.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.azathothStatue, TileEntityAzathothStatue.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.nyarlathotepStatue, TileEntityNyarlathotepStatue.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.yogsothothStatue, TileEntityYogsothothStatue.class);
		loatheMyselfForBeingALazyFuck(AbyssalCraft.shubniggurathStatue, TileEntityShubniggurathStatue.class);

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
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.ritualaltar), new RenderRitualAltar());
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.ritualpedestal), new RenderRitualPedestal());
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.cthulhuStatue), new Block3DRender(new TileEntityCthulhuStatueRenderer(), new TileEntityCthulhuStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.hasturStatue), new Block3DRender(new TileEntityHasturStatueRenderer(), new TileEntityHasturStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.jzaharStatue), new Block3DRender(new TileEntityJzaharStatueRenderer(), new TileEntityJzaharStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.azathothStatue), new Block3DRender(new TileEntityAzathothStatueRenderer(), new TileEntityAzathothStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.nyarlathotepStatue), new Block3DRender(new TileEntityNyarlathotepStatueRenderer(), new TileEntityNyarlathotepStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.yogsothothStatue), new Block3DRender(new TileEntityYogsothothStatueRenderer(), new TileEntityYogsothothStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.shubniggurathStatue), new Block3DRender(new TileEntityShubniggurathStatueRenderer(), new TileEntityShubniggurathStatue()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.energyPedestal), new Block3DRender(new TileEntityEnergyPedestalRenderer(), new TileEntityEnergyPedestal()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.sacrificialAltar), new Block3DRender(new TileEntitySacrificialAltarRenderer(), new TileEntitySacrificialAltar()));
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.tieredEnergyPedestal), new RenderTieredEnergyPedestal());
		//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.tieredSacrificialAltar), new RenderTieredSacrificialAltar());

		registerItemRender(AbyssalCraft.devsword, 0);
		registerItemRender(AbyssalCraft.OC, 0);
		registerItemRender(AbyssalCraft.gatewayKey, 0);
		registerItemRender(AbyssalCraft.Staff, 0);
		registerItemRender(AbyssalCraft.Cbucket, 0);
		registerItemRender(AbyssalCraft.PSDLfinder, 0);
		registerItemRender(AbyssalCraft.EoA, 0);
		registerItemRender(AbyssalCraft.gatewayKeyDL, 0);
		registerItemRender(AbyssalCraft.Dreadshard, 0);
		registerItemRender(AbyssalCraft.dreadchunk, 0);
		registerItemRender(AbyssalCraft.abychunk, 0);
		registerItemRender(AbyssalCraft.abyingot, 0);
		registerItemRender(AbyssalCraft.Coralium, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster2, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster3, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster4, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster5, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster6, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster7, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster8, 0);
		registerItemRender(AbyssalCraft.Coraliumcluster9, 0);
		registerItemRender(AbyssalCraft.Cpearl, 0);
		registerItemRender(AbyssalCraft.Cchunk, 0);
		registerItemRender(AbyssalCraft.Cingot, 0);
		registerItemRender(AbyssalCraft.Cplate, 0);
		registerItemRender(AbyssalCraft.Corb, 0);
		registerItemRender(AbyssalCraft.Corflesh, 0);
		registerItemRender(AbyssalCraft.Corbone, 0);
		registerItemRender(AbyssalCraft.pickaxe, 0);
		registerItemRender(AbyssalCraft.axe, 0);
		registerItemRender(AbyssalCraft.shovel, 0);
		registerItemRender(AbyssalCraft.sword, 0);
		registerItemRender(AbyssalCraft.hoe, 0);
		registerItemRender(AbyssalCraft.pickaxeA, 0);
		registerItemRender(AbyssalCraft.axeA, 0);
		registerItemRender(AbyssalCraft.shovelA, 0);
		registerItemRender(AbyssalCraft.swordA, 0);
		registerItemRender(AbyssalCraft.hoeA, 0);
		registerItemRender(AbyssalCraft.Corpickaxe, 0);
		registerItemRender(AbyssalCraft.Coraxe, 0);
		registerItemRender(AbyssalCraft.Corshovel, 0);
		registerItemRender(AbyssalCraft.Corsword, 0);
		registerItemRender(AbyssalCraft.Corhoe, 0);
		registerItemRender(AbyssalCraft.boots, 0);
		registerItemRender(AbyssalCraft.helmet, 0);
		registerItemRender(AbyssalCraft.plate, 0);
		registerItemRender(AbyssalCraft.legs, 0);
		registerItemRender(AbyssalCraft.bootsD, 0);
		registerItemRender(AbyssalCraft.helmetD, 0);
		registerItemRender(AbyssalCraft.plateD, 0);
		registerItemRender(AbyssalCraft.legsD, 0);
		registerItemRender(AbyssalCraft.Corboots, 0);
		registerItemRender(AbyssalCraft.Corhelmet, 0);
		registerItemRender(AbyssalCraft.Corplate, 0);
		registerItemRender(AbyssalCraft.Corlegs, 0);
		registerItemRender(AbyssalCraft.CorbootsP, 0);
		registerItemRender(AbyssalCraft.CorhelmetP, 0);
		registerItemRender(AbyssalCraft.CorplateP, 0);
		registerItemRender(AbyssalCraft.CorlegsP, 0);
		registerItemRender(AbyssalCraft.Depthsboots, 0);
		registerItemRender(AbyssalCraft.Depthshelmet, 0);
		registerItemRender(AbyssalCraft.Depthsplate, 0);
		registerItemRender(AbyssalCraft.Depthslegs, 0);
		registerItemRender(AbyssalCraft.CobbleU, 0);
		registerItemRender(AbyssalCraft.IronU, 0);
		registerItemRender(AbyssalCraft.GoldU, 0);
		registerItemRender(AbyssalCraft.DiamondU, 0);
		registerItemRender(AbyssalCraft.AbyssalniteU, 0);
		registerItemRender(AbyssalCraft.CoraliumU, 0);
		registerItemRender(AbyssalCraft.MRE, 0);
		registerItemRender(AbyssalCraft.ironp, 0);
		registerItemRender(AbyssalCraft.chickenp, 0);
		registerItemRender(AbyssalCraft.porkp, 0);
		registerItemRender(AbyssalCraft.beefp, 0);
		registerItemRender(AbyssalCraft.fishp, 0);
		registerItemRender(AbyssalCraft.dirtyplate, 0);
		registerItemRender(AbyssalCraft.friedegg, 0);
		registerItemRender(AbyssalCraft.eggp, 0);
		registerItemRender(AbyssalCraft.cloth, 0);
		registerItemRender(AbyssalCraft.shadowfragment, 0);
		registerItemRender(AbyssalCraft.shadowshard, 0);
		registerItemRender(AbyssalCraft.shadowgem, 0);
		registerItemRender(AbyssalCraft.oblivionshard, 0);
		registerItemRender(AbyssalCraft.corbow, 0);
		registerItemRender(AbyssalCraft.antibucket, 0);
		registerItemRender(AbyssalCraft.cbrick, 0);
		registerItemRender(AbyssalCraft.cudgel, 0);
		registerItemRender(AbyssalCraft.dreadiumingot, 0);
		registerItemRender(AbyssalCraft.dreadfragment, 0);
		registerItemRender(AbyssalCraft.dreadiumboots, 0);
		registerItemRender(AbyssalCraft.dreadiumhelmet, 0);
		registerItemRender(AbyssalCraft.dreadiumplate, 0);
		registerItemRender(AbyssalCraft.dreadiumlegs, 0);
		registerItemRender(AbyssalCraft.dreadiumpickaxe, 0);
		registerItemRender(AbyssalCraft.dreadiumaxe, 0);
		registerItemRender(AbyssalCraft.dreadiumshovel, 0);
		registerItemRender(AbyssalCraft.dreadiumsword, 0);
		registerItemRender(AbyssalCraft.dreadiumhoe, 0);
		registerItemRender(AbyssalCraft.DreadiumU, 0);
		registerItemRender(AbyssalCraft.carbonCluster, 0);
		registerItemRender(AbyssalCraft.denseCarbonCluster, 0);
		registerItemRender(AbyssalCraft.methane, 0);
		registerItemRender(AbyssalCraft.nitre, 0);
		registerItemRender(AbyssalCraft.sulfur, 0);
		registerItemRenders(AbyssalCraft.crystal, 25);
		registerItemRenders(AbyssalCraft.crystalShard, 25);
		registerItemRender(AbyssalCraft.dreadcloth, 0);
		registerItemRender(AbyssalCraft.dreadplate, 0);
		registerItemRender(AbyssalCraft.dreadblade, 0);
		registerItemRender(AbyssalCraft.dreadhilt, 0);
		registerItemRender(AbyssalCraft.dreadkatana, 0);
		registerItemRender(AbyssalCraft.dreadKey, 0);
		registerItemRender(AbyssalCraft.gatewayKeyJzh, 0);
		registerItemRender(AbyssalCraft.dreadiumSboots, 0);
		registerItemRender(AbyssalCraft.dreadiumShelmet, 0);
		registerItemRender(AbyssalCraft.dreadiumSplate, 0);
		registerItemRender(AbyssalCraft.dreadiumSlegs, 0);
		registerItemRender(AbyssalCraft.tinIngot, 0);
		registerItemRender(AbyssalCraft.copperIngot, 0);
		registerItemRender(AbyssalCraft.antiBeef, 0);
		registerItemRender(AbyssalCraft.antiChicken, 0);
		registerItemRender(AbyssalCraft.antiPork, 0);
		registerItemRender(AbyssalCraft.antiFlesh, 0);
		registerItemRender(AbyssalCraft.antiBone, 0);
		registerItemRender(AbyssalCraft.antiSpider_eye, 0);
		registerItemRender(AbyssalCraft.soulReaper, 0);
		registerItemRender(AbyssalCraft.ethaxium_brick, 0);
		registerItemRender(AbyssalCraft.ethaxiumIngot, 0);
		registerItemRender(AbyssalCraft.lifeCrystal, 0);
		registerItemRender(AbyssalCraft.ethBoots, 0);
		registerItemRender(AbyssalCraft.ethHelmet, 0);
		registerItemRender(AbyssalCraft.ethPlate, 0);
		registerItemRender(AbyssalCraft.ethLegs, 0);
		registerItemRender(AbyssalCraft.ethPickaxe, 0);
		registerItemRender(AbyssalCraft.ethAxe, 0);
		registerItemRender(AbyssalCraft.ethShovel, 0);
		registerItemRender(AbyssalCraft.ethSword, 0);
		registerItemRender(AbyssalCraft.ethHoe, 0);
		registerItemRender(AbyssalCraft.EthaxiumU, 0);
		registerItemRender(AbyssalCraft.coin, 0);
		registerItemRender(AbyssalCraft.cthulhuCoin, 0);
		registerItemRender(AbyssalCraft.elderCoin, 0);
		registerItemRender(AbyssalCraft.jzaharCoin, 0);
		registerItemRender(AbyssalCraft.engravingBlank, 0);
		registerItemRender(AbyssalCraft.engravingCthulhu, 0);
		registerItemRender(AbyssalCraft.engravingElder, 0);
		registerItemRender(AbyssalCraft.engravingJzahar, 0);
		registerItemRender(AbyssalCraft.eldritchScale, 0);
		registerItemRender(AbyssalCraft.omotholFlesh, 0);
		registerItemRender(AbyssalCraft.antiCorflesh, 0);
		registerItemRender(AbyssalCraft.antiCorbone, 0);
		registerItemRender(AbyssalCraft.necronomicon, 0);
		registerItemRender(AbyssalCraft.necronomicon_cor, 0);
		registerItemRender(AbyssalCraft.necronomicon_dre, 0);
		registerItemRender(AbyssalCraft.necronomicon_omt, 0);
		registerItemRender(AbyssalCraft.abyssalnomicon, 0);
		registerItemRender(AbyssalCraft.crystalbag_s, 0);
		registerItemRender(AbyssalCraft.crystalbag_m, 0);
		registerItemRender(AbyssalCraft.crystalbag_l, 0);
		registerItemRender(AbyssalCraft.crystalbag_h, 0);
		registerItemRender(AbyssalCraft.shoggothFlesh, 0, "shoggothflesh_overworld");
		registerItemRender(AbyssalCraft.shoggothFlesh, 1, "shoggothflesh_abyssalwasteland");
		registerItemRender(AbyssalCraft.shoggothFlesh, 2, "shoggothflesh_dreadlands");
		registerItemRender(AbyssalCraft.shoggothFlesh, 3, "shoggothflesh_omothol");
		registerItemRender(AbyssalCraft.shoggothFlesh, 4, "shoggothflesh_darkrealm");
		registerItemRender(AbyssalCraft.nugget, 0, "nugget_abyssalnite");
		registerItemRender(AbyssalCraft.nugget, 1, "nugget_coralium");
		registerItemRender(AbyssalCraft.nugget, 2, "nugget_dreadium");
		registerItemRender(AbyssalCraft.nugget, 3, "nugget_ethaxium");
		registerItemRender(AbyssalCraft.drainStaff, 0);
		registerItemRender(AbyssalCraft.essence, 0, "essence_abyssalwasteland");
		registerItemRender(AbyssalCraft.essence, 1, "essence_dreadlands");
		registerItemRender(AbyssalCraft.essence, 2, "essence_omothol");
		registerItemRender(AbyssalCraft.skin, 0, "skin_abyssalwasteland");
		registerItemRender(AbyssalCraft.skin, 1, "skin_dreadlands");
		registerItemRender(AbyssalCraft.skin, 2, "skin_omothol");
		registerItemRender(AbyssalCraft.charm, 0, "ritualcharm_empty");
		registerItemRender(AbyssalCraft.charm, 1, "ritualcharm_range");
		registerItemRender(AbyssalCraft.charm, 2, "ritualcharm_duration");
		registerItemRender(AbyssalCraft.charm, 3, "ritualcharm_power");
		registerItemRenders(AbyssalCraft.cthulhuCharm, 4);
		registerItemRenders(AbyssalCraft.hasturCharm, 4);
		registerItemRenders(AbyssalCraft.jzaharCharm, 4);
		registerItemRenders(AbyssalCraft.azathothCharm, 4);
		registerItemRenders(AbyssalCraft.nyarlathotepCharm, 4);
		registerItemRenders(AbyssalCraft.yogsothothCharm, 4);
		registerItemRenders(AbyssalCraft.shubniggurathCharm, 4);
		registerItemRender(AbyssalCraft.hasturCoin, 0);
		registerItemRender(AbyssalCraft.azathothCoin, 0);
		registerItemRender(AbyssalCraft.nyarlathotepCoin, 0);
		registerItemRender(AbyssalCraft.yogsothothCoin, 0);
		registerItemRender(AbyssalCraft.shubniggurathCoin, 0);
		registerItemRender(AbyssalCraft.engravingHastur, 0);
		registerItemRender(AbyssalCraft.engravingAzathoth, 0);
		registerItemRender(AbyssalCraft.engravingNyarlathotep, 0);
		registerItemRender(AbyssalCraft.engravingYogsothoth, 0);
		registerItemRender(AbyssalCraft.engravingShubniggurath, 0);
		registerItemRender(AbyssalCraft.gatekeeperEssence, 0);

		registerItemRender(AbyssalCraft.Darkstone, 0);
		registerItemRender(AbyssalCraft.Darkstone_cobble, 0);
		registerItemRender(AbyssalCraft.Darkstone_brick, 0);
		registerItemRender(AbyssalCraft.DSGlow, 0);
		registerItemRender(AbyssalCraft.Darkbrickslab1, 0);
		registerItemRender(AbyssalCraft.Darkbrickslab2, 0);
		registerItemRender(AbyssalCraft.Darkcobbleslab1, 0);
		registerItemRender(AbyssalCraft.Darkcobbleslab2, 0);
		registerItemRender(AbyssalCraft.Darkgrass, 0);
		registerItemRender(AbyssalCraft.DBstairs, 0);
		registerItemRender(AbyssalCraft.DCstairs, 0);
		registerItemRender(AbyssalCraft.DLTLeaves, 0);
		registerItemRender(AbyssalCraft.DLTLog, 0);
		registerItemRender(AbyssalCraft.DLTSapling, 0);
		registerItemRender(AbyssalCraft.abystone, 0);
		registerItemRender(AbyssalCraft.abybrick, 0);
		registerItemRender(AbyssalCraft.abyslab1, 0);
		registerItemRender(AbyssalCraft.abyslab2, 0);
		registerItemRender(AbyssalCraft.abystairs, 0);
		registerItemRender(AbyssalCraft.Coraliumore, 0);
		registerItemRender(AbyssalCraft.abyore, 0);
		registerItemRender(AbyssalCraft.abyfence, 0);
		registerItemRender(AbyssalCraft.DSCwall, 0);
		registerItemRender(AbyssalCraft.ODB, 0);
		registerItemRender(AbyssalCraft.abyblock, 0);
		registerItemRender(AbyssalCraft.CoraliumInfusedStone, 0);
		registerItemRender(AbyssalCraft.ODBcore, 0);
		registerItemRender(AbyssalCraft.Crate, 0);
		registerItemRender(AbyssalCraft.portal, 0);
		registerItemRender(AbyssalCraft.Darkstoneslab1, 0);
		registerItemRender(AbyssalCraft.Darkstoneslab2, 0);
		registerItemRender(AbyssalCraft.Coraliumfire, 0);
		registerItemRender(AbyssalCraft.DSbutton, 0);
		registerItemRender(AbyssalCraft.DSpplate, 0);
		registerItemRender(AbyssalCraft.DLTplank, 0);
		registerItemRender(AbyssalCraft.DLTbutton, 0);
		registerItemRender(AbyssalCraft.DLTpplate, 0);
		registerItemRender(AbyssalCraft.DLTstairs, 0);
		registerItemRender(AbyssalCraft.DLTslab1, 0);
		registerItemRender(AbyssalCraft.DLTslab2, 0);
		registerItemRender(AbyssalCraft.corblock, 0);
		registerItemRender(AbyssalCraft.PSDL, 0);
		registerItemRender(AbyssalCraft.AbyCorOre, 0);
		registerItemRender(AbyssalCraft.Altar, 0);
		registerItemRender(AbyssalCraft.Abybutton, 0);
		registerItemRender(AbyssalCraft.Abypplate, 0);
		registerItemRender(AbyssalCraft.DSBfence, 0);
		registerItemRender(AbyssalCraft.DLTfence, 0);
		registerItemRender(AbyssalCraft.dreadstone, 0);
		registerItemRender(AbyssalCraft.abydreadstone, 0);
		registerItemRender(AbyssalCraft.abydreadore, 0);
		registerItemRender(AbyssalCraft.dreadore, 0);
		registerItemRender(AbyssalCraft.dreadbrick, 0);
		registerItemRender(AbyssalCraft.abydreadbrick, 0);
		registerItemRender(AbyssalCraft.dreadgrass, 0);
		registerItemRender(AbyssalCraft.dreadlog, 0);
		registerItemRender(AbyssalCraft.dreadleaves, 0);
		registerItemRender(AbyssalCraft.dreadsapling, 0);
		registerItemRender(AbyssalCraft.dreadplanks, 0);
		registerItemRender(AbyssalCraft.dreadportal, 0);
		registerItemRender(AbyssalCraft.dreadfire, 0);
		registerItemRender(AbyssalCraft.DGhead, 0);
		registerItemRender(AbyssalCraft.Phead, 0);
		registerItemRender(AbyssalCraft.Whead, 0);
		registerItemRender(AbyssalCraft.Ohead, 0);
		registerItemRender(AbyssalCraft.dreadbrickstairs, 0);
		registerItemRender(AbyssalCraft.dreadbrickfence, 0);
		registerItemRender(AbyssalCraft.dreadbrickslab1, 0);
		registerItemRender(AbyssalCraft.dreadbrickslab2, 0);
		registerItemRender(AbyssalCraft.abydreadbrickstairs, 0);
		registerItemRender(AbyssalCraft.abydreadbrickfence, 0);
		registerItemRender(AbyssalCraft.abydreadbrickslab1, 0);
		registerItemRender(AbyssalCraft.abydreadbrickslab2, 0);
		registerItemRender(AbyssalCraft.cstone, 0);
		registerItemRender(AbyssalCraft.cstonebrick, 0);
		registerItemRender(AbyssalCraft.cstonebrickfence, 0);
		registerItemRender(AbyssalCraft.cstonebrickslab1, 0);
		registerItemRender(AbyssalCraft.cstonebrickslab2, 0);
		registerItemRender(AbyssalCraft.cstonebrickstairs, 0);
		registerItemRender(AbyssalCraft.cstonebutton, 0);
		registerItemRender(AbyssalCraft.cstonepplate, 0);
		registerItemRender(AbyssalCraft.dreadaltartop, 0);
		registerItemRender(AbyssalCraft.dreadaltarbottom, 0);
		registerItemRender(AbyssalCraft.crystallizer, 0);
		registerItemRender(AbyssalCraft.crystallizer_on, 0);
		registerItemRender(AbyssalCraft.dreadiumblock, 0);
		registerItemRender(AbyssalCraft.transmutator, 0);
		registerItemRender(AbyssalCraft.transmutator_on, 0);
		registerItemRender(AbyssalCraft.dreadguardspawner, 0);
		registerItemRender(AbyssalCraft.chagarothspawner, 0);
		registerItemRender(AbyssalCraft.jzaharspawner, 0);
		registerItemRender(AbyssalCraft.DrTfence, 0);
		registerItemRender(AbyssalCraft.nitreOre, 0);
		registerItemRender(AbyssalCraft.AbyIroOre, 0);
		registerItemRender(AbyssalCraft.AbyGolOre, 0);
		registerItemRender(AbyssalCraft.AbyDiaOre, 0);
		registerItemRender(AbyssalCraft.AbyNitOre, 0);
		registerItemRender(AbyssalCraft.AbyTinOre, 0);
		registerItemRender(AbyssalCraft.AbyCopOre, 0);
		registerItemRender(AbyssalCraft.AbyPCorOre, 0);
		registerItemRender(AbyssalCraft.AbyLCorOre, 0);
		registerItemRender(AbyssalCraft.solidLava, 0);
		registerItemRender(AbyssalCraft.ethaxium, 0);
		registerItemRender(AbyssalCraft.ethaxiumbrick, 0, "ethaxiumbrick_0");
		registerItemRender(AbyssalCraft.ethaxiumbrick, 1, "ethaxiumbrick_1");
		registerItemRender(AbyssalCraft.ethaxiumpillar, 0);
		registerItemRender(AbyssalCraft.ethaxiumstairs, 0);
		registerItemRender(AbyssalCraft.ethaxiumslab1, 0);
		registerItemRender(AbyssalCraft.ethaxiumslab2, 0);
		registerItemRender(AbyssalCraft.ethaxiumfence, 0);
		registerItemRender(AbyssalCraft.ethaxiumblock, 0);
		registerItemRender(AbyssalCraft.omotholstone, 0);
		registerItemRender(AbyssalCraft.omotholportal, 0);
		registerItemRender(AbyssalCraft.omotholfire, 0);
		registerItemRender(AbyssalCraft.engraver, 0);
		registerItemRender(AbyssalCraft.house, 0);
		registerItemRender(AbyssalCraft.materializer, 0);
		registerItemRender(AbyssalCraft.darkethaxiumbrick, 0, "darkethaxiumbrick_0");
		registerItemRender(AbyssalCraft.darkethaxiumbrick, 1, "darkethaxiumbrick_1");
		registerItemRender(AbyssalCraft.darkethaxiumpillar, 0);
		registerItemRender(AbyssalCraft.darkethaxiumstairs, 0);
		registerItemRender(AbyssalCraft.darkethaxiumslab1, 0);
		registerItemRender(AbyssalCraft.darkethaxiumslab2, 0);
		registerItemRender(AbyssalCraft.darkethaxiumfence, 0);
		registerItemRender(AbyssalCraft.ritualaltar, 0, "ritualaltar_0");
		registerItemRender(AbyssalCraft.ritualaltar, 1, "ritualaltar_1");
		registerItemRender(AbyssalCraft.ritualaltar, 2, "ritualaltar_2");
		registerItemRender(AbyssalCraft.ritualaltar, 3, "ritualaltar_3");
		registerItemRender(AbyssalCraft.ritualaltar, 4, "ritualaltar_4");
		registerItemRender(AbyssalCraft.ritualaltar, 5, "ritualaltar_5");
		registerItemRender(AbyssalCraft.ritualaltar, 6, "ritualaltar_6");
		registerItemRender(AbyssalCraft.ritualaltar, 7, "ritualaltar_7");
		registerItemRender(AbyssalCraft.ritualpedestal, 0, "ritualpedestal_0");
		registerItemRender(AbyssalCraft.ritualpedestal, 1, "ritualpedestal_1");
		registerItemRender(AbyssalCraft.ritualpedestal, 2, "ritualpedestal_2");
		registerItemRender(AbyssalCraft.ritualpedestal, 3, "ritualpedestal_3");
		registerItemRender(AbyssalCraft.ritualpedestal, 4, "ritualpedestal_4");
		registerItemRender(AbyssalCraft.ritualpedestal, 5, "ritualpedestal_5");
		registerItemRender(AbyssalCraft.ritualpedestal, 6, "ritualpedestal_6");
		registerItemRender(AbyssalCraft.ritualpedestal, 7, "ritualpedestal_7");
		registerItemRender(AbyssalCraft.shoggothBlock, 0);
		registerItemRender(AbyssalCraft.cthulhuStatue, 0);
		registerItemRender(AbyssalCraft.hasturStatue, 0);
		registerItemRender(AbyssalCraft.jzaharStatue, 0);
		registerItemRender(AbyssalCraft.azathothStatue, 0);
		registerItemRender(AbyssalCraft.nyarlathotepStatue, 0);
		registerItemRender(AbyssalCraft.yogsothothStatue, 0);
		registerItemRender(AbyssalCraft.shubniggurathStatue, 0);
		registerItemRender(AbyssalCraft.monolithStone, 0);
		registerItemRender(AbyssalCraft.shoggothBiomass, 0);
		registerItemRender(AbyssalCraft.energyPedestal, 0);
		registerItemRender(AbyssalCraft.monolithPillar, 0);
		registerItemRender(AbyssalCraft.sacrificialAltar, 0);
		registerItemRender(AbyssalCraft.tieredEnergyPedestal, 0, "tieredenergypedestal_0");
		registerItemRender(AbyssalCraft.tieredEnergyPedestal, 1, "tieredenergypedestal_1");
		registerItemRender(AbyssalCraft.tieredEnergyPedestal, 2, "tieredenergypedestal_2");
		registerItemRender(AbyssalCraft.tieredEnergyPedestal, 3, "tieredenergypedestal_3");
		registerItemRender(AbyssalCraft.tieredSacrificialAltar, 0, "tieredsacrificialaltar_0");
		registerItemRender(AbyssalCraft.tieredSacrificialAltar, 1, "tieredsacrificialaltar_1");
		registerItemRender(AbyssalCraft.tieredSacrificialAltar, 2, "tieredsacrificialaltar_2");
		registerItemRender(AbyssalCraft.tieredSacrificialAltar, 3, "tieredsacrificialaltar_3");
		registerItemRender(AbyssalCraft.gatekeeperminionspawner, 0);
		registerItemRender(AbyssalCraft.mimicFire, 0);

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

		}, AbyssalCraft.crystal, AbyssalCraft.crystalShard);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor(){

			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return 0xE8E8E8;
			}

		}, AbyssalCraft.coin, AbyssalCraft.elderCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.hasturCoin, AbyssalCraft.jzaharCoin,
		AbyssalCraft.azathothCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.shubniggurathCoin);
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