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
package com.shinoow.abyssalcraft.client;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.render.Block3DRender;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.client.model.entity.*;
import com.shinoow.abyssalcraft.client.model.item.ModelDreadiumSamuraiArmor;
import com.shinoow.abyssalcraft.client.render.block.*;
import com.shinoow.abyssalcraft.client.render.entity.*;
import com.shinoow.abyssalcraft.client.render.item.*;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	private static final ModelDreadiumSamuraiArmor chestPlate = new ModelDreadiumSamuraiArmor(1.0f);
	private static final ModelDreadiumSamuraiArmor leggings = new ModelDreadiumSamuraiArmor(0.5f);

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new AbyssalCraftClientEventHooks());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderThings() {

		RenderingRegistry.registerEntityRenderingHandler(EntityEvilpig.class, new RenderEvilPig(new ModelPig(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, new RenderDepthsGhoul(new ModelDG(), 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, new RenderAbyssalZombie(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityODBPrimed.class, new RenderODB());
		RenderingRegistry.registerEntityRenderingHandler(EntityODBcPrimed.class, new RenderODBc());
		RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, new RenderJzahar(new ModelJzahar(true), 1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAbygolem.class, new Renderabygolem(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadgolem.class, new Renderdreadgolem(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadguard.class, new Renderdreadguard());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, new RenderDragonMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonBoss.class, new RenderDragonBoss());
		RenderingRegistry.registerEntityRenderingHandler(EntityPSDLTracker.class, new RenderSnowball(AbyssalCraft.PSDLfinder));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowCreature.class, new RenderShadowCreature(new ModelShadowCreature(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowMonster.class, new RenderShadowMonster(new ModelShadowMonster(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadling.class, new RenderDreadling(new ModelDreadling(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSpawn.class, new RenderDreadSpawn());
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonPig.class, new RenderDemonPig(new ModelPig(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonGoliath.class, new RenderSkeletonGoliath(new ModelSkeletonGoliath(true), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothSpawn.class, new RenderChagarothSpawn());
		RenderingRegistry.registerEntityRenderingHandler(EntityChagarothFist.class, new RenderChagarothFist());
		RenderingRegistry.registerEntityRenderingHandler(EntityChagaroth.class, new RenderChagaroth());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowBeast.class, new RenderShadowBeast());
		RenderingRegistry.registerEntityRenderingHandler(EntitySacthoth.class, new RenderSacthoth());
		RenderingRegistry.registerEntityRenderingHandler(EntityRemnant.class, new RenderRemnant());
		RenderingRegistry.registerEntityRenderingHandler(EntityOmotholGhoul.class, new RenderOmotholGhoul());
		RenderingRegistry.registerEntityRenderingHandler(EntityCoraliumArrow.class, new RenderCoraliumArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityGatekeeperMinion.class, new RenderGatekeeperMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityGreaterDreadSpawn.class, new RenderGreaterDreadSpawn());
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserDreadbeast.class, new RenderLesserDreadbeast());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSlug.class, new RenderSnowball(AbyssalCraft.dreadfragment));
		RenderingRegistry.registerEntityRenderingHandler(EntityLesserShoggoth.class, new RenderLesserShoggoth());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilCow.class, new RenderEvilCow(new ModelCow(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilChicken.class, new RenderEvilChicken(new ModelChicken(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonCow.class, new RenderDemonCow(new ModelCow(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonChicken.class, new RenderDemonChicken(new ModelChicken(), 0.5F));

		RenderingRegistry.registerEntityRenderingHandler(EntityAntiAbyssalZombie.class, new RenderAntiAbyssalZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiBat.class, new RenderAntiBat());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiChicken.class, new RenderAntiChicken());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCow.class, new RenderAntiCow());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiCreeper.class, new RenderAntiCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiGhoul.class, new RenderAntiGhoul());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPig.class, new RenderAntiPig());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiPlayer.class, new RenderAntiPlayer());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSkeleton.class, new RenderAntiSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiSpider.class, new RenderAntiSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntityAntiZombie.class, new RenderAntiZombie());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPSDL.class, new TileEntityPSDLRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
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

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.PSDL), new Block3DRender(new TileEntityPSDLRenderer(), new TileEntityPSDL()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.Altar), new Block3DRender(new TileEntityAltarRenderer(), new TileEntityAltar()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.ODB), new Block3DRender(new TileEntityODBRenderer(), new TileEntityODB()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.dreadaltarbottom), new Block3DRender(new TileEntityDreadAltarBottomRenderer(), new TileEntityDreadAltarBottom()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.dreadaltartop), new Block3DRender(new TileEntityDreadAltarTopRenderer(), new TileEntityDreadAltarTop()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.engraver), new Block3DRender(new TileEntityEngraverRenderer(), new TileEntityEngraver()));
		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.Staff, new RenderStaff());
		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.cudgel, new RenderCudgel());
		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.dreadhilt, new RenderHilt());
		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.dreadkatana, new RenderKatana());
		MinecraftForgeClient.registerItemRenderer(AbyssalCraft.corbow, new RenderCoraliumBow());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.ritualaltar), new RenderRitualAltar());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.ritualpedestal), new RenderRitualPedestal());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.cthulhuStatue), new Block3DRender(new TileEntityCthulhuStatueRenderer(), new TileEntityCthulhuStatue()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.hasturStatue), new Block3DRender(new TileEntityHasturStatueRenderer(), new TileEntityHasturStatue()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.jzaharStatue), new Block3DRender(new TileEntityJzaharStatueRenderer(), new TileEntityJzaharStatue()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.azathothStatue), new Block3DRender(new TileEntityAzathothStatueRenderer(), new TileEntityAzathothStatue()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.nyarlathotepStatue), new Block3DRender(new TileEntityNyarlathotepStatueRenderer(), new TileEntityNyarlathotepStatue()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.yogsothothStatue), new Block3DRender(new TileEntityYogsothothStatueRenderer(), new TileEntityYogsothothStatue()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.shubniggurathStatue), new Block3DRender(new TileEntityShubniggurathStatueRenderer(), new TileEntityShubniggurathStatue()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.energyPedestal), new Block3DRender(new TileEntityEnergyPedestalRenderer(), new TileEntityEnergyPedestal()));
	}

	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
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
}
