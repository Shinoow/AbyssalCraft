/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.render.Block3DRender;
import com.shinoow.abyssalcraft.client.model.entity.ModelDG;
import com.shinoow.abyssalcraft.client.model.entity.ModelDreadling;
import com.shinoow.abyssalcraft.client.model.entity.ModelJzahar;
import com.shinoow.abyssalcraft.client.model.entity.ModelShadowCreature;
import com.shinoow.abyssalcraft.client.model.entity.ModelShadowMonster;
import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliath;
import com.shinoow.abyssalcraft.client.model.item.ModelDreadiumSamuraiArmor;
import com.shinoow.abyssalcraft.client.render.block.RenderODB;
import com.shinoow.abyssalcraft.client.render.block.RenderODBc;
import com.shinoow.abyssalcraft.client.render.block.TileEntityAltarRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityDGheadRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityDreadAltarBottomRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityDreadAltarTopRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityEngraverRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityODBRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityOheadRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityPSDLRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityPheadRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityWheadRenderer;
import com.shinoow.abyssalcraft.client.render.entity.RenderAbyssalZombie;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiAbyssalZombie;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiBat;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiChicken;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiCow;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiCreeper;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiGhoul;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiPig;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiPlayer;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiSkeleton;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiSpider;
import com.shinoow.abyssalcraft.client.render.entity.RenderAntiZombie;
import com.shinoow.abyssalcraft.client.render.entity.RenderChagaroth;
import com.shinoow.abyssalcraft.client.render.entity.RenderChagarothFist;
import com.shinoow.abyssalcraft.client.render.entity.RenderChagarothSpawn;
import com.shinoow.abyssalcraft.client.render.entity.RenderDemonPig;
import com.shinoow.abyssalcraft.client.render.entity.RenderDepthsGhoul;
import com.shinoow.abyssalcraft.client.render.entity.RenderDragonBoss;
import com.shinoow.abyssalcraft.client.render.entity.RenderDragonMinion;
import com.shinoow.abyssalcraft.client.render.entity.RenderDreadSpawn;
import com.shinoow.abyssalcraft.client.render.entity.RenderDreadling;
import com.shinoow.abyssalcraft.client.render.entity.RenderJzahar;
import com.shinoow.abyssalcraft.client.render.entity.RenderPig;
import com.shinoow.abyssalcraft.client.render.entity.RenderRemnant;
import com.shinoow.abyssalcraft.client.render.entity.RenderSacthoth;
import com.shinoow.abyssalcraft.client.render.entity.RenderShadowBeast;
import com.shinoow.abyssalcraft.client.render.entity.RenderShadowCreature;
import com.shinoow.abyssalcraft.client.render.entity.RenderShadowMonster;
import com.shinoow.abyssalcraft.client.render.entity.RenderSkeletonGoliath;
import com.shinoow.abyssalcraft.client.render.entity.Renderabygolem;
import com.shinoow.abyssalcraft.client.render.entity.Renderdreadgolem;
import com.shinoow.abyssalcraft.client.render.entity.Renderdreadguard;
import com.shinoow.abyssalcraft.client.render.item.RenderCudgel;
import com.shinoow.abyssalcraft.client.render.item.RenderHilt;
import com.shinoow.abyssalcraft.client.render.item.RenderKatana;
import com.shinoow.abyssalcraft.client.render.item.RenderStaff;
import com.shinoow.abyssalcraft.client.render.player.RenderPlayerAC;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDGhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadAltarBottom;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadAltarTop;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEngraver;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityODB;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityOhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPSDL;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityWhead;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityChagaroth;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothFist;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityEvilpig;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.entity.EntityODBPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.common.entity.EntitySacthoth;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiBat;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiChicken;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCow;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPig;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSpider;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	private static final ModelDreadiumSamuraiArmor chestPlate = new ModelDreadiumSamuraiArmor(1.0f);
	private static final ModelDreadiumSamuraiArmor leggings = new ModelDreadiumSamuraiArmor(0.5f);

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderThings() {

		RenderingRegistry.registerEntityRenderingHandler(EntityEvilpig.class, new RenderPig(new ModelPig(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsGhoul.class, new RenderDepthsGhoul(new ModelDG(), 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssalZombie.class, new RenderAbyssalZombie(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityODBPrimed.class, new RenderODB());
		RenderingRegistry.registerEntityRenderingHandler(EntityODBcPrimed.class, new RenderODBc());
		RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, new RenderJzahar(new ModelJzahar(), 1.5F));
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
		if(AbyssalCraft.canRenderStarspawn == true)
			RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPlayerAC());
		RenderingRegistry.registerEntityRenderingHandler(EntityRemnant.class, new RenderRemnant());

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