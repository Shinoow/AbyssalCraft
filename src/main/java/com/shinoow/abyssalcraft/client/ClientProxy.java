/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.block.ICrystalBlock;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.api.spell.SpellUtils;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.client.handlers.ArmorDataReloadListener;
import com.shinoow.abyssalcraft.client.handlers.ClientVarsReloadListener;
import com.shinoow.abyssalcraft.client.model.block.ModelDGhead;
import com.shinoow.abyssalcraft.client.model.item.ModelDreadiumSamuraiArmor;
import com.shinoow.abyssalcraft.client.particles.ACParticleFX;
import com.shinoow.abyssalcraft.client.particles.ItemRitualParticle;
import com.shinoow.abyssalcraft.client.particles.PEStreamParticleFX;
import com.shinoow.abyssalcraft.client.render.block.*;
import com.shinoow.abyssalcraft.client.render.entity.*;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerDreadTentacles;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerStarSpawnTentacles;
import com.shinoow.abyssalcraft.client.render.item.RenderCoraliumArrow;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.BlockPortalAnchor;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualPedestal;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.entity.ghoul.*;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.client.LovecraftFont;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityAltarBlockRenderer;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityDirectionalRenderer;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityPedestalBlockRenderer;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualPedestal;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

	private static final ModelDreadiumSamuraiArmor chestPlate = new ModelDreadiumSamuraiArmor(1.0f);
	private static final ModelDreadiumSamuraiArmor leggings = new ModelDreadiumSamuraiArmor(0.5f);
	public static KeyBinding staff_mode, use_cage, configurator_mode, configurator_filter, configurator_path;
	private int particleCount;

	@Override
	public void preInit() {
		super.preInit();

		OBJLoader.INSTANCE.addDomain(AbyssalCraft.modid);

		RenderingRegistry.registerEntityRenderingHandler(EntityEvilpig.class, RenderEvilPig::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, RenderDepthsGhoul::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, RenderAbyssalZombie::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityODBPrimed.class, RenderODB::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityODBcPrimed.class, RenderODBc::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, RenderJzahar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadguard.class, RenderDreadguard::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, RenderDragonMinion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonBoss.class, RenderDragonBoss::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPSDLTracker.class, manager -> new RenderSnowball(manager, ACItems.powerstone_tracker, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowCreature.class, RenderShadowCreature::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowMonster.class, RenderShadowMonster::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadling.class, RenderDreadling::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSpawn.class, RenderDreadSpawn::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonPig.class, RenderDemonPig::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonGoliath.class, RenderSkeletonGoliath::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothSpawn.class, RenderChagarothSpawn::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothFist.class, RenderChagarothFist::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityChagaroth.class, RenderChagaroth::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowBeast.class, RenderShadowBeast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySacthoth.class, RenderSacthoth::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRemnant.class, RenderRemnant::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityOmotholGhoul.class, RenderOmotholGhoul::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumArrow.class, RenderCoraliumArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperMinion.class, RenderGatekeeperMinion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGreaterDreadSpawn.class, RenderGreaterDreadSpawn::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserDreadbeast.class, RenderLesserDreadbeast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSlug.class, manager -> new RenderSnowball(manager, ACItems.dread_fragment, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserShoggoth.class, manager -> new RenderShoggoth(manager, 0, 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilCow.class, RenderEvilCow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilChicken.class, RenderEvilChicken::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonCow.class, RenderDemonCow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonChicken.class, RenderDemonChicken::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperEssence.class, manager -> new RenderEntityItem(manager, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilSheep.class, RenderEvilSheep::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonSheep.class, RenderDemonSheep::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumSquid.class, RenderCoraliumSquid::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInkProjectile.class, manager -> new RenderSnowball(manager, Items.DYE, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadedCharge.class, RenderDreadedCharge::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAcidProjectile.class, manager -> new RenderSnowball(manager, ItemHandler.shoggoth_projectile, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, RenderBlackHole::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityImplosion.class, RenderImplosion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShubOffspring.class, RenderShubOffspring::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpiritItem.class, manager -> new RenderEntityItem(manager, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityPortal.class, RenderPortal::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShoggoth.class, manager -> new RenderShoggoth(manager, 1, 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGreaterShoggoth.class, manager -> new RenderShoggoth(manager, 2, 1.6F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCompassTentacle.class, RenderCompassTentacle::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityRemnantTrader.class, RenderRemnantTrader::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGhoul.class, RenderGhoul::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadedGhoul.class, RenderDreadedGhoul::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowGhoul.class, RenderShadowGhoul::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityAntiAbyssalZombie.class, RenderAntiAbyssalZombie::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiBat.class, RenderAntiBat::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiChicken.class, RenderAntiChicken::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCow.class, RenderAntiCow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCreeper.class, RenderAntiCreeper::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiGhoul.class, RenderAntiGhoul::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPig.class, RenderAntiPig::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPlayer.class, RenderAntiPlayer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSkeleton.class, RenderAntiSkeleton::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSpider.class, RenderAntiSpider::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiZombie.class, RenderAntiZombie::new);

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftClientEventHooks());

		staff_mode = new KeyBinding("key.staff_mode.desc", Keyboard.KEY_M, "key.abyssalcraft.category");
		use_cage = new KeyBinding("key.use_cage.desc", Keyboard.KEY_N, "key.abyssalcraft.category");
		configurator_mode = new KeyBinding("key.configurator_mode.desc", Keyboard.KEY_V, "key.abyssalcraft.category");
		configurator_filter = new KeyBinding("key.configurator_filter.desc", Keyboard.KEY_B, "key.abyssalcraft.category");
		configurator_path = new KeyBinding("key.configurator_path.desc", Keyboard.KEY_G, "key.abyssalcraft.category");

		ClientRegistry.registerKeyBinding(staff_mode);
		ClientRegistry.registerKeyBinding(use_cage);
		ClientRegistry.registerKeyBinding(configurator_mode);
		ClientRegistry.registerKeyBinding(configurator_filter);
		ClientRegistry.registerKeyBinding(configurator_path);
	}

	@Override
	public void init(){
		AbyssalCraftAPI.setAkloFont(new LovecraftFont(Minecraft.getMinecraft().gameSettings, new ResourceLocation("abyssalcraft", "textures/font/aklo.png"), Minecraft.getMinecraft().renderEngine, true));
		if(Minecraft.getMinecraft().getResourceManager() instanceof IReloadableResourceManager) {
			((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(AbyssalCraftAPI.getAkloFont());
			((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new ClientVarsReloadListener());
			((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new ArmorDataReloadListener());
		}
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityResearchTable.class, new TileEntityResearchTableRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUnlockedSealingLock.class, new TileEntityUnlockedSealingLockRenderer());

		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		RenderPlayer render1 = rm.getSkinMap().get("default");
		render1.addLayer(new LayerStarSpawnTentacles(render1));
		RenderPlayer render2 = rm.getSkinMap().get("slim");
		render2.addLayer(new LayerStarSpawnTentacles(render2));
//		rm.entityRenderMap.forEach((a,b)-> {
//			if(EntityLiving.class.isAssignableFrom(a) && b instanceof RenderLiving)
//				((RenderLiving) b).addLayer(new LayerDreadTentacles((RenderLiving) b));
//		});
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> ((ICrystal) stack.getItem()).getColor(stack), InitHandler.INSTANCE.ITEMS.stream().filter(i -> i instanceof ICrystal).toArray(Item[]::new));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> 0xE8E8E8, ACItems.coin, ACItems.token_of_jzahar);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 1 ? SpellUtils.getSpellColor(stack) : 16777215, ACItems.basic_scroll, ACItems.lesser_scroll, ACItems.moderate_scroll, ACItems.greater_scroll);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> 0xd2c9a0, ACItems.lost_page);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> getColor(((ItemBlock) stack.getItem()).getBlock()), toItems(ACBlocks.ritual_altar_stone, ACBlocks.ritual_altar_darkstone, ACBlocks.ritual_altar_abyssal_stone, ACBlocks.ritual_altar_coralium_stone,
				ACBlocks.ritual_altar_dreadstone, ACBlocks.ritual_altar_elysian_stone, ACBlocks.ritual_altar_ethaxium, ACBlocks.ritual_altar_dark_ethaxium,
				ACBlocks.ritual_pedestal_stone, ACBlocks.ritual_pedestal_darkstone, ACBlocks.ritual_pedestal_abyssal_stone, ACBlocks.ritual_pedestal_coralium_stone,
				ACBlocks.ritual_pedestal_dreadstone, ACBlocks.ritual_pedestal_elysian_stone, ACBlocks.ritual_pedestal_ethaxium, ACBlocks.ritual_pedestal_dark_ethaxium));
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> ((ICrystalBlock) state.getBlock()).getColor(state), InitHandler.INSTANCE.BLOCKS.stream().filter(b -> b instanceof ICrystalBlock).toArray(Block[]::new));
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> {
			if(state.getValue(BlockPortalAnchor.ACTIVE) && tintIndex == 1) {
				TileEntity te = BlockUtil.getTileEntitySafely(world, pos);
				return te instanceof TileEntityPortalAnchor ? ((TileEntityPortalAnchor) te).getColor() : 16777215;
			}

			return 16777215;
		}, ACBlocks.portal_anchor);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> {
			if(tintIndex == 1) {
				TileEntity te = BlockUtil.getTileEntitySafely(world, pos);
				if(te instanceof IRitualAltar && ((IRitualAltar) te).isPerformingRitual())
					return getColor(te.getWorld().rand.nextInt(3));
			}
			return getColor(state.getBlock());
		}, ACBlocks.ritual_altar_stone, ACBlocks.ritual_altar_darkstone, ACBlocks.ritual_altar_abyssal_stone, ACBlocks.ritual_altar_coralium_stone,
				ACBlocks.ritual_altar_dreadstone, ACBlocks.ritual_altar_elysian_stone, ACBlocks.ritual_altar_ethaxium, ACBlocks.ritual_altar_dark_ethaxium);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> {
			if(tintIndex == 1) {
				TileEntity te = BlockUtil.getTileEntitySafely(world, pos);
				if(te instanceof IRitualPedestal && ((IRitualPedestal) te).getAltar() != null && ((IRitualPedestal) te).getAltar().isPerformingRitual())
					return getColor(te.getWorld().rand.nextInt(3));
			}
			return getColor(state.getBlock());
		}, ACBlocks.ritual_pedestal_stone, ACBlocks.ritual_pedestal_darkstone, ACBlocks.ritual_pedestal_abyssal_stone, ACBlocks.ritual_pedestal_coralium_stone,
				ACBlocks.ritual_pedestal_dreadstone, ACBlocks.ritual_pedestal_elysian_stone, ACBlocks.ritual_pedestal_ethaxium, ACBlocks.ritual_pedestal_dark_ethaxium);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> ACBiomes.dreadlands_forest.getGrassColorAtPos(pos), ACBlocks.dreadlands_grass);
		RitualRegistry.instance().addDimensionToBookTypeAndName(0, 0, I18n.format(NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.abyssal_wasteland_id, 1, I18n.format(NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.dreadlands_id, 2, I18n.format(NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.omothol_id, 3, I18n.format(NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.dark_realm_id, 0, I18n.format(NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE));
	}

	private int getColor(int num) {
		switch(num) {
		case 0:
			return 0x413faa;
		case 1:
			return 0x295930;
		case 2:
			return 0x275087;
		default:
			return 0x037a78;
		}
	}

	private int getColor(Block block) {
		if(block instanceof BlockRitualAltar)
			return ((BlockRitualAltar) block).getTypeColor();
		if(block instanceof BlockRitualPedestal)
			return ((BlockRitualPedestal) block).getTypeColor();
		return 16777215;
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
		return ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx);
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx);
	}

	@Override
	public void spawnParticle(String particleName, double posX, double posY, double posZ, double velX, double velY, double velZ)
	{
		if(particleName.equals("CorBlood")){
			spawnParticleLegacy(particleName, posX, posY, posZ, velX, velY, velZ);
			return;
		}
		if(particleName.equals("PEStream")) {
			World world = Minecraft.getMinecraft().world;
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
	}

	@Override
	public void spawnItemParticle(double posX, double posY, double posZ, double velX, double velY, double velZ, int[] data) {
		int i = data.length > 1 ? data[1] : 0;
		Minecraft.getMinecraft().effectRenderer.addEffect(new ItemRitualParticle(Minecraft.getMinecraft().world, posX, posY, posZ, velX, velY, velZ, Item.getItemById(data[0]), i));
	}

	public void spawnParticleLegacy(String particleName, double posX, double posY, double posZ, double velX, double velY, double velZ){
		Minecraft mc = Minecraft.getMinecraft();
		World theWorld = mc.world;

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

			if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22 || var14 > 1)
				return;
			else {
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

	private Item[] toItems(Block... blocks) {
		return Stream.of(blocks)
				.map(Item::getItemFromBlock)
				.collect(Collectors.toList())
				.toArray(new Item[0]);
	}

	@Override
	public int getParticleCount() {
		return particleCount;
	}

	@Override
	public void incrementParticleCount() {
		particleCount++;
	}

	@Override
	public void decrementParticleCount() {
		particleCount--;
	}

	@Override
	public void resetParticleCount() {
		particleCount = 0;
	}

	@Override
	public RayTraceResult rayTraceEntity(float dist) {
		RayTraceResult r = AbyssalCraftClientEventHooks.getMouseOverExtended(dist);
		return r != null && r.entityHit instanceof EntityLivingBase ? r : null;
	}
}
