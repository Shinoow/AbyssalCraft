/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.client.handlers.ClientVarsReloadListener;
import com.shinoow.abyssalcraft.client.lib.LovecraftFont;
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
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityAltarBlockRenderer;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityDirectionalRenderer;
import com.shinoow.abyssalcraft.lib.client.render.TileEntityPedestalBlockRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
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

		OBJLoader.INSTANCE.addDomain(AbyssalCraft.modid);

		RenderingRegistry.registerEntityRenderingHandler(EntityEvilpig.class, RenderEvilPig::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, RenderDepthsGhoul::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, RenderAbyssalZombie::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityODBPrimed.class, RenderODB::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityODBcPrimed.class, RenderODBc::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, RenderJzahar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAbygolem.class, RenderAbyssalniteGolem::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadgolem.class, RenderDreadedAbyssalniteGolem::new);
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
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserShoggoth.class, RenderLesserShoggoth::new);
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

		RenderPlayer render1 = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		render1.addLayer(new LayerStarSpawnTentacles(render1));
		RenderPlayer render2 = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
		render2.addLayer(new LayerStarSpawnTentacles(render2));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> ACClientVars.getCrystalColors()[stack.getItemDamage()], ACItems.crystal, ACItems.crystal_shard, ACItems.crystal_fragment, Item.getItemFromBlock(ACBlocks.crystal_cluster));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> ACClientVars.getCrystalColors()[stack.getItemDamage() + 16], Item.getItemFromBlock(ACBlocks.crystal_cluster2));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> 0xE8E8E8, ACItems.coin, ACItems.elder_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.hastur_engraved_coin, ACItems.jzahar_engraved_coin,
				ACItems.azathoth_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.shub_niggurath_engraved_coin);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 1  && stack.hasTagCompound() ? SpellRegistry.instance().getSpell(stack.getTagCompound().getString("Spell")).getColor() : 16777215, ACItems.scroll);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> ACClientVars.getCrystalColors()[state.getBlock().getMetaFromState(state)], ACBlocks.crystal_cluster);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> ACClientVars.getCrystalColors()[state.getBlock().getMetaFromState(state) + 16], ACBlocks.crystal_cluster2);
		RitualRegistry.instance().addDimensionToBookTypeAndName(0, 0, I18n.format(NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.abyssal_wasteland_id, 1, I18n.format(NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.dreadlands_id, 2, I18n.format(NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.omothol_id, 3, I18n.format(NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE));
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.dark_realm_id, 3, I18n.format(NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE));
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

			if ((var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22) || (var14 > 1))
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
}
