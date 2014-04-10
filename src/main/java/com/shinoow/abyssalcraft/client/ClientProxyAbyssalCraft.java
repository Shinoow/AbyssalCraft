package com.shinoow.abyssalcraft.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.client.model.entity.ModelDG;
import com.shinoow.abyssalcraft.client.model.entity.ModelDreadSpawn;
import com.shinoow.abyssalcraft.client.model.entity.ModelDreadling;
import com.shinoow.abyssalcraft.client.model.entity.ModelJzahar;
import com.shinoow.abyssalcraft.client.model.entity.ModelShadowCreature;
import com.shinoow.abyssalcraft.client.model.entity.ModelShadowMonster;
import com.shinoow.abyssalcraft.client.render.block.ItemPSDLRenderer;
import com.shinoow.abyssalcraft.client.render.block.RenderODB;
import com.shinoow.abyssalcraft.client.render.block.RenderODBc;
import com.shinoow.abyssalcraft.client.render.block.TileEntityAltarRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityDGheadRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityOheadRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityPSDLRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityPheadRenderer;
import com.shinoow.abyssalcraft.client.render.block.TileEntityWheadRenderer;
import com.shinoow.abyssalcraft.client.render.entity.RenderDemonPig;
import com.shinoow.abyssalcraft.client.render.entity.RenderDepthsZombie;
import com.shinoow.abyssalcraft.client.render.entity.RenderDepthsghoul;
import com.shinoow.abyssalcraft.client.render.entity.RenderDragonBoss;
import com.shinoow.abyssalcraft.client.render.entity.RenderDragonMinion;
import com.shinoow.abyssalcraft.client.render.entity.RenderDreadSpawn;
import com.shinoow.abyssalcraft.client.render.entity.RenderDreadling;
import com.shinoow.abyssalcraft.client.render.entity.RenderJzahar;
import com.shinoow.abyssalcraft.client.render.entity.RenderPig;
import com.shinoow.abyssalcraft.client.render.entity.RenderShadowCreature;
import com.shinoow.abyssalcraft.client.render.entity.RenderShadowMonster;
import com.shinoow.abyssalcraft.client.render.entity.Renderabygolem;
import com.shinoow.abyssalcraft.client.render.entity.Renderdreadgolem;
import com.shinoow.abyssalcraft.client.render.entity.Renderdreadguard;
import com.shinoow.abyssalcraft.common.CommonProxyAbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDGhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityOhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPSDL;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityWhead;
import com.shinoow.abyssalcraft.common.entity.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsghoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.entity.EntityODBMeteor;
import com.shinoow.abyssalcraft.common.entity.EntityODBPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.Entityabygolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadgolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadguard;
import com.shinoow.abyssalcraft.common.entity.Entityevilpig;
import com.shinoow.abyssalcraft.common.handlers.CapeHandler;
import com.shinoow.abyssalcraft.common.util.EnumCapeGroup;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxyAbyssalCraft extends CommonProxyAbyssalCraft
{

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderThings()
	{

		RenderingRegistry.registerEntityRenderingHandler(Entityevilpig.class, new RenderPig(new ModelPig(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsghoul.class, new RenderDepthsghoul(new ModelDG(), 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDepthsZombie.class, new RenderDepthsZombie(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityODBPrimed.class, new RenderODB());
		RenderingRegistry.registerEntityRenderingHandler(EntityODBcPrimed.class, new RenderODBc());
		RenderingRegistry.registerEntityRenderingHandler(EntityJzahar.class, new RenderJzahar(new ModelJzahar(), 1.5F));
		RenderingRegistry.registerEntityRenderingHandler(Entityabygolem.class, new Renderabygolem(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(Entitydreadgolem.class, new Renderdreadgolem(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(Entitydreadguard.class, new Renderdreadguard(new ModelZombie(), 0.5F, 1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, new RenderDragonMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonBoss.class, new RenderDragonBoss());
		RenderingRegistry.registerEntityRenderingHandler(EntityPSDLTracker.class, new RenderSnowball(AbyssalCraft.PSDLfinder));
		RenderingRegistry.registerEntityRenderingHandler(EntityODBMeteor.class, new RenderFireball(3.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowCreature.class, new RenderShadowCreature(new ModelShadowCreature(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowMonster.class, new RenderShadowMonster(new ModelShadowMonster(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadling.class, new RenderDreadling(new ModelDreadling(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadSpawn.class, new RenderDreadSpawn(new ModelDreadSpawn(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonPig.class, new RenderDemonPig(new ModelPig(), 0.5F));

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPSDL.class, new TileEntityPSDLRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDGhead.class, new TileEntityDGheadRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhead.class, new TileEntityPheadRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWhead.class, new TileEntityWheadRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOhead.class, new TileEntityOheadRenderer());

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.PSDL), new ItemPSDLRenderer(new TileEntityPSDLRenderer(), new TileEntityPSDL()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AbyssalCraft.Altar), new ItemPSDLRenderer(new TileEntityAltarRenderer(), new TileEntityAltar()));
		//MinecraftForgeClient.registerItemRenderer(AbyssalCraft.Staff.itemID, (IItemRenderer)new RenderStaff());

		CapeHandler.addGroupedCape(CapeHandler.getArrayFromUrl("https://dl.dropboxusercontent.com/s/ujztlnlqbt9mkyw/Donator_10.txt?dl=1&token_hash=AAHM_2kEhZtfL-xYaBHNaMEM7OwmbANsxwch6RBb5TQPJA"), EnumCapeGroup.DONATOR_10);
		CapeHandler.addGroupedCape(CapeHandler.getArrayFromUrl("https://dl.dropboxusercontent.com/s/qfrdco0rde13wmn/Donator_25.txt?dl=1&token_hash=AAH1urzVdKgrP6b1tG7mPgskAfk-OcUQ4ccxQUTWtjgwTA"), EnumCapeGroup.DONATOR_25);
		CapeHandler.addGroupedCape(CapeHandler.getArrayFromUrl("https://dl.dropboxusercontent.com/s/cn4inlejmgeqk2x/Donator_50.txt?dl=1&token_hash=AAHmUaKaYakwCf0TTjTAcTotTbrq0EzwscqMsn8sJJcTGw"), EnumCapeGroup.DONATOR_50);
		CapeHandler.addGroupedCape(CapeHandler.getArrayFromUrl("https://dl.dropboxusercontent.com/s/d5bl5neipcg5xak/Donator_100.txt?dl=1&token_hash=AAGlkML0xE5XjzJZzTjP9bfL6hJQJEMWvog6YNPhiuNT9g"), EnumCapeGroup.DONATOR_100);
		CapeHandler.addGroupedCape(CapeHandler.getArrayFromUrl("https://dl.dropboxusercontent.com/s/ikyiimfo00ul1w6/Dev.txt?dl=1&token_hash=AAFg6DfHLKROkD5ob3ARf4xx-cJwfzp1KNdYFCzbSQeDcA"), EnumCapeGroup.DEV);
		CapeHandler.addCape("enfalas", EnumCapeGroup.ENF);
	}

	@Override
	public int addArmor(String armor)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}

}