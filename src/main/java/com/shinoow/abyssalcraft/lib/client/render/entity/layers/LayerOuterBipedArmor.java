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
package com.shinoow.abyssalcraft.lib.client.render.entity.layers;

import java.util.Map;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.lib.util.items.IOuterArmor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerOuterBipedArmor extends LayerBipedArmor {

	private final RenderLivingBase<?> renderer;
	private float alpha = 1.0F;
	private float colorR = 1.0F;
	private float colorG = 1.0F;
	private float colorB = 1.0F;
	private boolean skipRenderGlint;
	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.<String, ResourceLocation>newHashMap();
	private static final ResourceLocation DEFAULT = new ResourceLocation("abyssalcraft:textures/armor/default.png");

	public LayerOuterBipedArmor(RenderLivingBase<?> rendererIn) {
		super(rendererIn);
		renderer = rendererIn;
	}

	@Override
	protected void initArmor()
	{
		modelLeggings = new ModelBiped(0.75F);
		modelArmor = new ModelBiped(1.25F);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
		renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
		renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
		renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
	}

	private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
	{
		ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

		if (itemstack.getItem() instanceof ItemArmor && itemstack.getItem() instanceof IOuterArmor)
		{
			ItemArmor itemarmor = (ItemArmor)itemstack.getItem();

			if (itemarmor.getEquipmentSlot() == slotIn)
			{
				ModelBiped t = getModelFromSlot(slotIn);
				t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
				t.setModelAttributes(renderer.getMainModel());
				t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
				setModelSlotVisible(t, slotIn);
				isLegSlot(slotIn);
				renderer.bindTexture(getArmorResource(entityLivingBaseIn, itemstack, slotIn, null));

				{
					if (itemarmor.hasOverlay(itemstack)) // Allow this for anything, not only cloth
					{
						int i = itemarmor.getColor(itemstack);
						float f = (i >> 16 & 255) / 255.0F;
						float f1 = (i >> 8 & 255) / 255.0F;
						float f2 = (i & 255) / 255.0F;
						GlStateManager.color(colorR * f, colorG * f1, colorB * f2, alpha);
						t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
						renderer.bindTexture(getArmorResource(entityLivingBaseIn, itemstack, slotIn, "overlay"));
					}
					{ // Non-colored
						GlStateManager.color(colorR, colorG, colorB, alpha);
						t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					} // Default
					if (!skipRenderGlint && itemstack.hasEffect())
						renderEnchantedGlint(renderer, entityLivingBaseIn, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
				}
			}
		}
	}

	private boolean isLegSlot(EntityEquipmentSlot slotIn)
	{
		return slotIn == EntityEquipmentSlot.LEGS;
	}

	@Override
	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		if(stack.getItem() instanceof IOuterArmor) {
			String s1 = ((IOuterArmor) stack.getItem()).getOuterArmorTexture(stack, entity, slot, type);

			if(s1 != null) {
				ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

				if (resourcelocation == null)
				{
					resourcelocation = new ResourceLocation(s1);
					ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
				}

				return resourcelocation;
			}
		}

		return DEFAULT;
	}
}
