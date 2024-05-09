/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.shinoow.abyssalcraft.client.model.entity.ModelEvilSheep1;
import com.shinoow.abyssalcraft.client.render.entity.RenderEvilSheep;
import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilSheep;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerEvilSheepWool implements LayerRenderer<EntityEvilSheep>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
	private final RenderEvilSheep sheepRenderer;
	private final ModelEvilSheep1 sheepModel = new ModelEvilSheep1();
	private final Map<GameProfile, GameProfile> checkedProfiles = new HashMap<>();

	public LayerEvilSheepWool(RenderEvilSheep sheepRendererIn)
	{
		sheepRenderer = sheepRendererIn;

	}

	/*
	 * Player skin fetching code borrowed from https://github.com/CyclopsMC/EvilCraft/blob/master-1.9/src/main/java/org/cyclops/evilcraft/client/render/entity/RenderVengeanceSpirit.java
	 */
	@Override
	public void doRenderLayer(EntityEvilSheep entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		if (!entitylivingbaseIn.isInvisible())
		{
			if(entitylivingbaseIn.getKilledPlayerUUID() != null && entitylivingbaseIn.getKilledPlayerName() != null && entitylivingbaseIn.getKilledPlayerName().length() > 0){
				setupModelStuff();
				GameProfile gameProfile = new GameProfile(entitylivingbaseIn.getKilledPlayerUUID(), entitylivingbaseIn.getKilledPlayerName());
				ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkin(entitylivingbaseIn.getKilledPlayerUUID());
				Minecraft minecraft = Minecraft.getMinecraft();
				// Check if we have loaded the (texturized) profile before, otherwise we load it and cache it.
				if(!checkedProfiles.containsKey(gameProfile)) {
					Property property = (Property) Iterables.getFirst(gameProfile.getProperties().get("textures"), (Object) null);
					if (property == null) {
						// The game profile enchanced with texture information.
						GameProfile newGameProfile = Minecraft.getMinecraft().getSessionService().fillProfileProperties(gameProfile, true);
						checkedProfiles.put(gameProfile, newGameProfile);
					}
				} else {
					Map map = minecraft.getSkinManager().loadSkinFromCache(checkedProfiles.get(gameProfile));
					if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
						resourcelocation = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture) map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
				}
				sheepRenderer.bindTexture(resourcelocation);
			}else {
				resetModelStuff();
				sheepRenderer.bindTexture(TEXTURE);
			}

			if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getCustomNameTag()))
			{
				int i = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
				int j = EnumDyeColor.values().length;
				int k = i % j;
				int l = (i + 1) % j;
				float f = (entitylivingbaseIn.ticksExisted % 25 + partialTicks) / 25.0F;
				float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
				float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
				GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
			}

			sheepModel.setModelAttributes(sheepRenderer.getMainModel());
			sheepModel.setLivingAnimations(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks);
			sheepModel.render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}

	private void setupModelStuff(){
		if(sheepModel.textureHeight != 64){
			sheepModel.textureHeight = 64;
			sheepModel.head = new ModelRenderer(sheepModel, 2, 2);
			sheepModel.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
			sheepModel.head.setRotationPoint(0.0F, 6.0F, -8.0F);
			sheepModel.body = new ModelRenderer(sheepModel, 8, 10);
			sheepModel.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
			sheepModel.body.setRotationPoint(0.0F, 5.0F, 2.0F);
			sheepModel.leg1 = new ModelRenderer(sheepModel, 40, 16);
			sheepModel.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
			sheepModel.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
			sheepModel.leg2 = new ModelRenderer(sheepModel, 40, 16);
			sheepModel.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
			sheepModel.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
			sheepModel.leg3 = new ModelRenderer(sheepModel, 40, 16);
			sheepModel.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
			sheepModel.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
			sheepModel.leg4 = new ModelRenderer(sheepModel, 40, 16);
			sheepModel.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
			sheepModel.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
		}
	}

	private void resetModelStuff(){
		if(sheepModel.textureHeight != 32){
			sheepModel.textureHeight = 32;
			sheepModel.head = new ModelRenderer(sheepModel, 0, 0);
			sheepModel.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
			sheepModel.head.setRotationPoint(0.0F, 6.0F, -8.0F);
			sheepModel.body = new ModelRenderer(sheepModel, 28, 8);
			sheepModel.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
			sheepModel.body.setRotationPoint(0.0F, 5.0F, 2.0F);
			float f = 0.5F;
			sheepModel.leg1 = new ModelRenderer(sheepModel, 0, 16);
			sheepModel.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
			sheepModel.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
			sheepModel.leg2 = new ModelRenderer(sheepModel, 0, 16);
			sheepModel.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
			sheepModel.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
			sheepModel.leg3 = new ModelRenderer(sheepModel, 0, 16);
			sheepModel.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
			sheepModel.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
			sheepModel.leg4 = new ModelRenderer(sheepModel, 0, 16);
			sheepModel.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
			sheepModel.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
		}
	}
}
