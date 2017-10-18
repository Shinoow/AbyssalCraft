/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client;

import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.client.model.block.ModelDGhead;
import com.shinoow.abyssalcraft.client.model.item.ModelDreadiumSamuraiArmor;
import com.shinoow.abyssalcraft.client.particles.ACParticleFX;
import com.shinoow.abyssalcraft.client.particles.PEStreamParticleFX;
import com.shinoow.abyssalcraft.client.render.block.RenderODB;
import com.shinoow.abyssalcraft.client.render.block.RenderODBc;
import com.shinoow.abyssalcraft.client.render.block.TileEntityJzaharSpawnerRenderer;
import com.shinoow.abyssalcraft.client.render.entity.*;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerStarSpawnTentacles;
import com.shinoow.abyssalcraft.client.render.item.RenderCoraliumArrow;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.*;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster.EnumCrystalType;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster2.EnumCrystalType2;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityAltarBlockRenderer;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityDirectionalRenderer;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityPedestalBlockRenderer;

public class ClientProxy extends CommonProxy {

	private static final ModelDreadiumSamuraiArmor chestPlate = new ModelDreadiumSamuraiArmor(1.0f);
	private static final ModelDreadiumSamuraiArmor leggings = new ModelDreadiumSamuraiArmor(0.5f);
	public static KeyBinding staff_mode;

	@Override
	public void preInit() {

		OBJLoader.INSTANCE.addDomain(AbyssalCraft.modid);

		RenderingRegistry.registerEntityRenderingHandler(EntityEvilpig.class, manager -> new RenderEvilPig(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, manager -> new RenderDepthsGhoul(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, manager -> new RenderAbyssalZombie(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityODBPrimed.class, manager -> new RenderODB(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityODBcPrimed.class, manager -> new RenderODBc(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, manager -> new RenderJzahar(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAbygolem.class, manager -> new RenderAbyssalniteGolem(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadgolem.class, manager -> new RenderDreadedAbyssalniteGolem(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadguard.class, manager -> new RenderDreadguard(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, manager -> new RenderDragonMinion(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonBoss.class, manager -> new RenderDragonBoss(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityPSDLTracker.class, manager -> new RenderSnowball(manager, ACItems.powerstone_tracker, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowCreature.class, manager -> new RenderShadowCreature(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowMonster.class, manager -> new RenderShadowMonster(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadling.class, manager -> new RenderDreadling(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSpawn.class, manager -> new RenderDreadSpawn(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonPig.class, manager -> new RenderDemonPig(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonGoliath.class, manager -> new RenderSkeletonGoliath(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothSpawn.class, manager -> new RenderChagarothSpawn(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothFist.class, manager -> new RenderChagarothFist(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityChagaroth.class, manager -> new RenderChagaroth(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowBeast.class, manager -> new RenderShadowBeast(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntitySacthoth.class, manager -> new RenderSacthoth(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityRemnant.class, manager -> new RenderRemnant(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityOmotholGhoul.class, manager -> new RenderOmotholGhoul(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumArrow.class, manager -> new RenderCoraliumArrow(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperMinion.class, manager -> new RenderGatekeeperMinion(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityGreaterDreadSpawn.class, manager -> new RenderGreaterDreadSpawn(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserDreadbeast.class, manager -> new RenderLesserDreadbeast(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSlug.class, manager -> new RenderSnowball(manager, ACItems.dread_fragment, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserShoggoth.class, manager -> new RenderLesserShoggoth(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilCow.class, manager -> new RenderEvilCow(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilChicken.class, manager -> new RenderEvilChicken(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonCow.class, manager -> new RenderDemonCow(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonChicken.class, manager -> new RenderDemonChicken(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperEssence.class, manager -> new RenderEntityItem(manager, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilSheep.class, manager -> new RenderEvilSheep(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonSheep.class, manager -> new RenderDemonSheep(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumSquid.class, manager -> new RenderCoraliumSquid(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityInkProjectile.class, manager -> new RenderSnowball(manager, Items.DYE, Minecraft.getMinecraft().getRenderItem()));

		RenderingRegistry.registerEntityRenderingHandler(EntityAntiAbyssalZombie.class, manager -> new RenderAntiAbyssalZombie(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiBat.class, manager -> new RenderAntiBat(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiChicken.class, manager -> new RenderAntiChicken(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCow.class, manager -> new RenderAntiCow(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCreeper.class, manager -> new RenderAntiCreeper(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiGhoul.class, manager -> new RenderAntiGhoul(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPig.class, manager -> new RenderAntiPig(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPlayer.class, manager -> new RenderAntiPlayer(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSkeleton.class, manager -> new RenderAntiSkeleton(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSpider.class, manager -> new RenderAntiSpider(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiZombie.class, manager -> new RenderAntiZombie(manager));

		ModelBakery.registerItemVariants(ACItems.shoggoth_flesh, makerl("shoggothflesh_overworld", "shoggothflesh_abyssalwasteland",
			"shoggothflesh_dreadlands", "shoggothflesh_omothol", "shoggothflesh_darkrealm"));
		ModelBakery.registerItemVariants(ACItems.essence, makerl("essence_abyssalwasteland", "essence_dreadlands", "essence_omothol"));
		ModelBakery.registerItemVariants(ACItems.skin, makerl("skin_abyssalwasteland", "skin_dreadlands", "skin_omothol"));
		ModelBakery.registerItemVariants(ACItems.ritual_charm, makerl("ritualcharm_empty", "ritualcharm_range", "ritualcharm_duration", "ritualcharm_power"));
		ModelBakery.registerItemVariants(ACItems.ingot_nugget, makerl("nugget_abyssalnite", "nugget_coralium", "nugget_dreadium", "nugget_ethaxium"));
		ModelBakery.registerItemVariants(ACItems.staff_of_rending, makerl("drainstaff", "drainstaff_aw", "drainstaff_dl", "drainstaff_omt"));
		ModelBakery.registerItemVariants(ACItems.staff_of_the_gatekeeper, makerl("staff", "staff2"));
		ModelBakery.registerItemVariants(ACItems.scroll, makerl("scroll_basic", "scroll_lesser", "scroll_moderate", "scroll_greater"));
		ModelBakery.registerItemVariants(ACItems.unique_scroll, makerl("scroll_unique_anti", "scroll_unique_oblivion"));

		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ethaxium_brick), makerl("ethaxiumbrick_0", "ethaxiumbrick_1", "ethaxiumbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.dark_ethaxium_brick), makerl("darkethaxiumbrick_0", "darkethaxiumbrick_1", "darkethaxiumbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_energy_pedestal), makerl("tieredenergypedestal_0", "tieredenergypedestal_1",
			"tieredenergypedestal_2", "tieredenergypedestal_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_sacrificial_altar), makerl("tieredsacrificialaltar_0", "tieredsacrificialaltar_1",
			"tieredsacrificialaltar_2", "tieredsacrificialaltar_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ritual_altar), makerl("ritualaltar_0", "ritualaltar_1", "ritualaltar_2", "ritualaltar_3",
			"ritualaltar_4", "ritualaltar_5", "ritualaltar_6", "ritualaltar_7"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ritual_pedestal), makerl("ritualpedestal_0", "ritualpedestal_1", "ritualpedestal_2",
			"ritualpedestal_3", "ritualpedestal_4", "ritualpedestal_5", "ritualpedestal_6", "ritualpedestal_7"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.darkstone_brick), makerl("darkstone_brick_0", "darkstone_brick_1", "darkstone_brick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.abyssal_stone_brick), makerl("abybrick_0", "abybrick_1", "abybrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.dreadstone_brick), makerl("dreadbrick_0", "dreadbrick_1", "dreadbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.abyssalnite_stone_brick), makerl("abydreadbrick_0", "abydreadbrick_1", "abydreadbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.coralium_stone_brick), makerl("cstonebrick_0", "cstonebrick_1", "cstonebrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_energy_collector), makerl("tieredenergycollector_0", "tieredenergycollector_1",
			"tieredenergycollector_2", "tieredenergycollector_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_energy_container), makerl("tieredenergycontainer_0", "tieredenergycontainer_1",
			"tieredenergycontainer_2", "tieredenergycontainer_3"));

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
		ModelLoader.setCustomStateMapper(ACBlocks.crystal_cluster, new StateMap.Builder().ignore(new IProperty[]{BlockCrystalCluster.TYPE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.crystal_cluster2, new StateMap.Builder().ignore(new IProperty[]{BlockCrystalCluster2.TYPE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssal_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssalnite_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.coralium_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssal_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssalnite_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.coralium_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.cthulhu_statue), 0, new ModelResourceLocation("abyssalcraft:cthulhustatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_cthulhu_statue), 0, new ModelResourceLocation("abyssalcraft:cthulhustatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.hastur_statue), 0, new ModelResourceLocation("abyssalcraft:hasturstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_hastur_statue), 0, new ModelResourceLocation("abyssalcraft:hasturstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.jzahar_statue), 0, new ModelResourceLocation("abyssalcraft:jzaharstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_jzahar_statue), 0, new ModelResourceLocation("abyssalcraft:jzaharstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.azathoth_statue), 0, new ModelResourceLocation("abyssalcraft:azathothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_azathoth_statue), 0, new ModelResourceLocation("abyssalcraft:azathothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.nyarlathotep_statue), 0, new ModelResourceLocation("abyssalcraft:nyarlathotepstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_nyarlathotep_statue), 0, new ModelResourceLocation("abyssalcraft:nyarlathotepstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.yog_sothoth_statue), 0, new ModelResourceLocation("abyssalcraft:yogsothothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_yog_sothoth_statue), 0, new ModelResourceLocation("abyssalcraft:yogsothothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.shub_niggurath_statue), 0, new ModelResourceLocation("abyssalcraft:shubniggurathstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_shub_niggurath_statue), 0, new ModelResourceLocation("abyssalcraft:shubniggurathstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.engraver), 0, new ModelResourceLocation("abyssalcraft:engraver", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.oblivion_deathbomb), 0, new ModelResourceLocation("abyssalcraft:odb", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.chagaroth_altar_top), 0, new ModelResourceLocation("abyssalcraft:dreadaltartop", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.chagaroth_altar_bottom), 0, new ModelResourceLocation("abyssalcraft:dreadaltarbottom", "inventory"));

		ModelLoader.setCustomModelResourceLocation(ACItems.cudgel, 0, new ModelResourceLocation("abyssalcraft:cudgel", "inventory"));
		ModelLoader.setCustomModelResourceLocation(ACItems.dreadium_katana, 0, new ModelResourceLocation("abyssalcraft:dreadkatana", "inventory"));

		ModelLoader.setCustomMeshDefinition(ACItems.staff_of_the_gatekeeper, stack -> stack.hasTagCompound() && stack.getTagCompound().getInteger("Mode") == 1 ? new ModelResourceLocation("abyssalcraft:staff2", "inventory") : new ModelResourceLocation("abyssalcraft:staff", "inventory"));

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftClientEventHooks());

		staff_mode = new KeyBinding("key.staff_mode.desc", Keyboard.KEY_M, "key.abyssalcraft.category");

		ClientRegistry.registerKeyBinding(staff_mode);
	}

	@Override
	public void init(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDGhead.class, new TileEntityDirectionalRenderer(new ModelDGhead(), "abyssalcraft:textures/model/depths_ghoul.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhead.class, new TileEntityDirectionalRenderer(new ModelDGhead(), "abyssalcraft:textures/model/depths_ghoul_pete.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWhead.class, new TileEntityDirectionalRenderer(new ModelDGhead(), "abyssalcraft:textures/model/depths_ghoul_wilson.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOhead.class, new TileEntityDirectionalRenderer(new ModelDGhead(), "abyssalcraft:textures/model/depths_ghoul_orange.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRitualAltar.class, new TileEntityAltarBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRitualPedestal.class, new TileEntityPedestalBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergyPedestal.class, new TileEntityPedestalBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySacrificialAltar.class, new TileEntityAltarBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTieredEnergyPedestal.class, new TileEntityPedestalBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTieredSacrificialAltar.class, new TileEntityAltarBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJzaharSpawner.class, new TileEntityJzaharSpawnerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRendingPedestal.class, new TileEntityPedestalBlockRenderer());

		registerItemRender(ItemHandler.devsword, 0);
		registerItemRender(ACItems.oblivion_catalyst, 0);
		registerItemRender(ACItems.gateway_key, 0);
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
		registerItemRender(ACItems.coralium_brick, 0);
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
		registerItemRender(ACItems.staff_of_rending, 0, "drainstaff");
		registerItemRender(ACItems.staff_of_rending, 1, "drainstaff_aw");
		registerItemRender(ACItems.staff_of_rending, 2, "drainstaff_dl");
		registerItemRender(ACItems.staff_of_rending, 3, "drainstaff_omt");
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
		registerItemRender(ACItems.interdimensional_cage, 0);
		registerItemRenders(ACItems.crystal_fragment, 25);
		registerItemRender(ACItems.stone_tablet, 0);
		registerItemRender(ACItems.scroll, 0, "scroll_basic");
		registerItemRender(ACItems.scroll, 1, "scroll_lesser");
		registerItemRender(ACItems.scroll, 2, "scroll_moderate");
		registerItemRender(ACItems.scroll, 3, "scroll_greater");
		registerItemRender(ACItems.unique_scroll, 0, "scroll_unique_anti");
		registerItemRender(ACItems.unique_scroll, 1, "scroll_unique_oblivion");

		registerItemRender(ACBlocks.darkstone, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone, 0);
		registerItemRender(ACBlocks.darkstone_brick, 0, "darkstone_brick_0");
		registerItemRender(ACBlocks.darkstone_brick, 1, "darkstone_brick_1");
		registerItemRender(ACBlocks.darkstone_brick, 2, "darkstone_brick_2");
		registerItemRender(ACBlocks.glowing_darkstone_bricks, 0);
		registerItemRender(ACBlocks.darkstone_brick_slab, 0);
		registerItemRender(BlockHandler.Darkbrickslab2, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_slab, 0);
		registerItemRender(BlockHandler.Darkcobbleslab2, 0);
		registerItemRender(ACBlocks.darklands_grass, 0);
		registerItemRender(ACBlocks.darkstone_brick_stairs, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.darklands_oak_leaves, 0);
		registerItemRender(ACBlocks.darklands_oak_wood, 0);
		registerItemRender(ACBlocks.darklands_oak_sapling, 0);
		registerItemRender(ACBlocks.abyssal_stone, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick, 0, "abybrick_0");
		registerItemRender(ACBlocks.abyssal_stone_brick, 1, "abybrick_1");
		registerItemRender(ACBlocks.abyssal_stone_brick, 2, "abybrick_2");
		registerItemRender(ACBlocks.abyssal_stone_brick_slab, 0);
		registerItemRender(BlockHandler.abyslab2, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.coralium_ore, 0);
		registerItemRender(ACBlocks.abyssalnite_ore, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_fence, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_wall, 0);
		registerItemRender(ACBlocks.block_of_abyssalnite, 0);
		registerItemRender(ACBlocks.coralium_infused_stone, 0);
		registerItemRender(ACBlocks.odb_core, 0);
		registerItemRender(ACBlocks.wooden_crate, 0);
		registerItemRender(ACBlocks.abyssal_gateway, 0);
		registerItemRender(ACBlocks.darkstone_slab, 0);
		registerItemRender(BlockHandler.Darkstoneslab2, 0);
		registerItemRender(ACBlocks.coralium_fire, 0);
		registerItemRender(ACBlocks.darkstone_button, 0);
		registerItemRender(ACBlocks.darkstone_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_planks, 0);
		registerItemRender(ACBlocks.darklands_oak_button, 0);
		registerItemRender(ACBlocks.darklands_oak_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_stairs, 0);
		registerItemRender(ACBlocks.darklands_oak_slab, 0);
		registerItemRender(BlockHandler.DLTslab2, 0);
		registerItemRender(ACBlocks.block_of_coralium, 0);
		registerItemRender(ACBlocks.dreadlands_infused_powerstone, 0);
		registerItemRender(ACBlocks.abyssal_coralium_ore, 0);
		registerItemRender(BlockHandler.Altar, 0);
		registerItemRender(ACBlocks.abyssal_stone_button, 0);
		registerItemRender(ACBlocks.abyssal_stone_pressure_plate, 0);
		registerItemRender(ACBlocks.darkstone_brick_fence, 0);
		registerItemRender(ACBlocks.darklands_oak_fence, 0);
		registerItemRender(ACBlocks.dreadstone, 0);
		registerItemRender(ACBlocks.abyssalnite_stone, 0);
		registerItemRender(ACBlocks.dreadlands_abyssalnite_ore, 0);
		registerItemRender(ACBlocks.dreaded_abyssalnite_ore, 0);
		registerItemRender(ACBlocks.dreadstone_brick, 0, "dreadbrick_0");
		registerItemRender(ACBlocks.dreadstone_brick, 1, "dreadbrick_1");
		registerItemRender(ACBlocks.dreadstone_brick, 2, "dreadbrick_2");
		registerItemRender(ACBlocks.abyssalnite_stone_brick, 0, "abydreadbrick_0");
		registerItemRender(ACBlocks.abyssalnite_stone_brick, 1, "abydreadbrick_1");
		registerItemRender(ACBlocks.abyssalnite_stone_brick, 2, "abydreadbrick_2");
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
		registerItemRender(BlockHandler.dreadbrickslab2, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick_fence, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick_slab, 0);
		registerItemRender(BlockHandler.abydreadbrickslab2, 0);
		registerItemRender(ACBlocks.coralium_stone, 0);
		registerItemRender(ACBlocks.coralium_stone_brick, 0, "cstonebrick_0");
		registerItemRender(ACBlocks.coralium_stone_brick, 1, "cstonebrick_1");
		registerItemRender(ACBlocks.coralium_stone_brick, 2, "cstonebrick_2");
		registerItemRender(ACBlocks.coralium_stone_brick_fence, 0);
		registerItemRender(ACBlocks.coralium_stone_brick_slab, 0);
		registerItemRender(BlockHandler.cstonebrickslab2, 0);
		registerItemRender(ACBlocks.coralium_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.coralium_stone_button, 0);
		registerItemRender(ACBlocks.coralium_stone_pressure_plate, 0);
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
		registerItemRender(ACBlocks.ethaxium_brick, 2, "ethaxiumbrick_2");
		registerItemRender(ACBlocks.ethaxium_pillar, 0);
		registerItemRender(ACBlocks.ethaxium_brick_stairs, 0);
		registerItemRender(ACBlocks.ethaxium_brick_slab, 0);
		registerItemRender(BlockHandler.ethaxiumslab2, 0);
		registerItemRender(ACBlocks.ethaxium_brick_fence, 0);
		registerItemRender(ACBlocks.block_of_ethaxium, 0);
		registerItemRender(ACBlocks.omothol_stone, 0);
		registerItemRender(ACBlocks.omothol_gateway, 0);
		registerItemRender(ACBlocks.omothol_fire, 0);
		registerItemRender(BlockHandler.house, 0);
		registerItemRender(ACBlocks.materializer, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick, 0, "darkethaxiumbrick_0");
		registerItemRender(ACBlocks.dark_ethaxium_brick, 1, "darkethaxiumbrick_1");
		registerItemRender(ACBlocks.dark_ethaxium_brick, 2, "darkethaxiumbrick_2");
		registerItemRender(ACBlocks.dark_ethaxium_pillar, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_stairs, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_slab, 0);
		registerItemRender(BlockHandler.darkethaxiumslab2, 0);
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
		registerItemRenders(ACBlocks.crystal_cluster, EnumCrystalType.values().length);
		registerItemRenders(ACBlocks.crystal_cluster2, EnumCrystalType2.values().length);
		registerItemRender(ACBlocks.energy_collector, 0);
		registerItemRender(ACBlocks.energy_relay, 0);
		registerItemRender(ACBlocks.energy_container, 0);
		registerItemRender(ACBlocks.tiered_energy_collector, 0, "tieredenergycollector_0");
		registerItemRender(ACBlocks.tiered_energy_collector, 1, "tieredenergycollector_1");
		registerItemRender(ACBlocks.tiered_energy_collector, 2, "tieredenergycollector_2");
		registerItemRender(ACBlocks.tiered_energy_collector, 3, "tieredenergycollector_3");
		registerItemRender(ACBlocks.overworld_energy_relay, 0);
		registerItemRender(ACBlocks.abyssal_wasteland_energy_relay, 0);
		registerItemRender(ACBlocks.dreadlands_energy_relay, 0);
		registerItemRender(ACBlocks.omothol_energy_relay, 0);
		registerItemRender(ACBlocks.tiered_energy_container, 0, "tieredenergycontainer_0");
		registerItemRender(ACBlocks.tiered_energy_container, 1, "tieredenergycontainer_1");
		registerItemRender(ACBlocks.tiered_energy_container, 2, "tieredenergycontainer_2");
		registerItemRender(ACBlocks.tiered_energy_container, 3, "tieredenergycontainer_3");
		registerItemRender(ACBlocks.abyssal_sand, 0);
		registerItemRender(ACBlocks.fused_abyssal_sand, 0);
		registerItemRender(ACBlocks.abyssal_sand_glass, 0);
		registerItemRender(ACBlocks.dreadlands_dirt, 0);
		registerItemRender(ACBlocks.abyssal_cobblestone, 0);
		registerItemRender(ACBlocks.dreadstone_cobblestone, 0);
		registerItemRender(ACBlocks.abyssalnite_cobblestone, 0);
		registerItemRender(ACBlocks.coralium_cobblestone, 0);
		registerItemRender(ACBlocks.abyssal_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.abyssal_cobblestone_slab, 0);
		registerItemRender(BlockHandler.abycobbleslab2, 0);
		registerItemRender(ACBlocks.abyssal_cobblestone_wall, 0);
		registerItemRender(ACBlocks.dreadstone_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.dreadstone_cobblestone_slab, 0);
		registerItemRender(BlockHandler.dreadcobbleslab2, 0);
		registerItemRender(ACBlocks.dreadstone_cobblestone_wall, 0);
		registerItemRender(ACBlocks.abyssalnite_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.abyssalnite_cobblestone_slab, 0);
		registerItemRender(BlockHandler.abydreadcobbleslab2, 0);
		registerItemRender(ACBlocks.abyssalnite_cobblestone_wall, 0);
		registerItemRender(ACBlocks.coralium_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.coralium_cobblestone_slab, 0);
		registerItemRender(BlockHandler.cstonecobbleslab2, 0);
		registerItemRender(ACBlocks.coralium_cobblestone_wall, 0);
		registerItemRender(ACBlocks.luminous_thistle, 0);
		registerItemRender(ACBlocks.wastelands_thorn, 0);
		registerItemRender(ACBlocks.rending_pedestal, 0);
		registerItemRender(ACBlocks.state_transformer, 0);
		registerItemRender(ACBlocks.energy_depositioner, 0);

		RenderPlayer render1 = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		render1.addLayer(new LayerStarSpawnTentacles(render1));
		RenderPlayer render2 = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
		render2.addLayer(new LayerStarSpawnTentacles(render2));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> ACLib.crystalColors[stack.getItemDamage()], ACItems.crystal, ACItems.crystal_shard, ACItems.crystal_fragment, Item.getItemFromBlock(ACBlocks.crystal_cluster));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> ACLib.crystalColors[stack.getItemDamage() + 16], Item.getItemFromBlock(ACBlocks.crystal_cluster2));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> 0xE8E8E8, ACItems.coin, ACItems.elder_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.hastur_engraved_coin, ACItems.jzahar_engraved_coin,
			ACItems.azathoth_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.shub_niggurath_engraved_coin);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 1  && stack.hasTagCompound() ? SpellRegistry.instance().getSpell(stack.getTagCompound().getString("Spell")).getColor() : 16777215, ACItems.scroll);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> ACLib.crystalColors[state.getBlock().getMetaFromState(state)], ACBlocks.crystal_cluster);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> ACLib.crystalColors[state.getBlock().getMetaFromState(state) + 16], ACBlocks.crystal_cluster2);
	}

	private void registerFluidModel(Block fluidBlock, String name) {
		Item item = Item.getItemFromBlock(fluidBlock);

		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation("abyssalcraft:fluid", name);

		ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

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

	@Override
	public void spawnParticle(String particleName, World world, double posX, double posY, double posZ, double velX, double velY, double velZ)
	{
		if(particleName.equals("CorBlood")){
			spawnParticleLegacy(particleName, posX, posY, posZ, velX, velY, velZ);
			return;
		}
		if(particleName.equals("PEStream"))
			switch(world.rand.nextInt(3)){
			case 0:
				Minecraft.getMinecraft().effectRenderer.addEffect(new PEStreamParticleFX(world, posX, posY, posZ, velX, velY, velZ, 65, 63, 170));
				break;
			case 1:
				Minecraft.getMinecraft().effectRenderer.addEffect(new PEStreamParticleFX(world, posX, posY, posZ, velX, velY, velZ, 41, 89, 48));
				break;
			case 2:
				Minecraft.getMinecraft().effectRenderer.addEffect(new PEStreamParticleFX(world, posX, posY, posZ, velX, velY, velZ, 39, 80, 135));
				break;
			default:
				Minecraft.getMinecraft().effectRenderer.addEffect(new PEStreamParticleFX(world, posX, posY, posZ, velX, velY, velZ, 3, 122, 120));
				break;
			}
	}

	public void spawnParticleLegacy(String particleName, double posX, double posY, double posZ, double velX, double velY, double velZ){
		Minecraft mc = Minecraft.getMinecraft();
		World theWorld = mc.theWorld;

		if (mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null)
		{
			int var14 = mc.gameSettings.particleSetting;

			if (var14 == 1 && theWorld.rand.nextInt(3) == 0)
				var14 = 2;

			double var15 = mc.getRenderViewEntity().posX - posX;
			double var17 = mc.getRenderViewEntity().posY - posY;
			double var19 = mc.getRenderViewEntity().posZ - posZ;
			Particle var21 = null;
			double var22 = 16.0D;

			if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
				return;
			else if (var14 > 1)
				return;
			else
			{
				if (particleName.equals("CorBlood"))
				{
					var21 = new ACParticleFX(theWorld, posX, posY, posZ, (float)velX, (float)velY, (float)velZ);
					var21.setRBGColorF(0, 1, 1);
				}

				mc.effectRenderer.addEffect(var21);
				return;
			}
		}
	}
}
