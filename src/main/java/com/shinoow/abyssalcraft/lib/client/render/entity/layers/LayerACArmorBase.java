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
package com.shinoow.abyssalcraft.lib.client.render.entity.layers;

import com.shinoow.abyssalcraft.api.armor.ArmorData;
import com.shinoow.abyssalcraft.api.armor.ArmorDataRegistry;
import com.shinoow.abyssalcraft.lib.client.model.ModelArmoredBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class LayerACArmorBase<T extends ModelArmoredBase> extends LayerArmorBase<ModelArmoredBase> {

	private final RenderLivingBase<?> renderer;
	private float alpha = 1.0F;
	private float colorR = 1.0F;
	private float colorG = 1.0F;
	private float colorB = 1.0F;
	private boolean skipRenderGlint;

	public LayerACArmorBase(RenderLivingBase<?> rendererIn) {
		super(rendererIn);
		renderer = rendererIn;
	}

	@Override
	protected void setModelSlotVisible(ModelArmoredBase model, EntityEquipmentSlot slot)
	{
		model.setVisible(false);
		model.setEquipmentSlotVisible(slot);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
	}

	private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
	{
		ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

		if (itemstack.getItem() instanceof ItemArmor)
		{
			ItemArmor itemarmor = (ItemArmor)itemstack.getItem();

			if (itemarmor.getEquipmentSlot() == slotIn)
			{
				ArmorData data = getDataFor(itemarmor.getArmorMaterial());
				int color = ArmorDataRegistry.instance().getColor(itemarmor.getArmorMaterial());
				T t = this.getModelFromSlot(slotIn);
				t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
				t.setModelAttributes(this.renderer.getMainModel());
				t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
				this.setModelSlotVisible(t, slotIn);
				this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, null));

				if (itemarmor.hasOverlay(itemstack) || data.hasOverlay()) // Allow this for anything, not only cloth
				{
					int i = itemarmor.getColor(itemstack);
					if(i == 16777215 && color != -1) // override base color if color is present
						i = color;
					float f = (i >> 16 & 255) / 255.0F;
					float f1 = (i >> 8 & 255) / 255.0F;
					float f2 = (i & 255) / 255.0F;
					GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
					t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					if(data.hasOverlay()) {
						this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, "overlay"));
						GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
						t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					} else
						GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
				} else {
					if(color != -1 && data.isColored()) { // apply color if it should
						int i = color;
						float f = (i >> 16 & 255) / 255.0F;
						float f1 = (i >> 8 & 255) / 255.0F;
						float f2 = (i & 255) / 255.0F;
						GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
					} else
						GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);

					t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
				}
				if (!this.skipRenderGlint && itemstack.hasEffect())
					renderEnchantedGlint(this.renderer, entityLivingBaseIn, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
			}
		}
	}

	@Override
	public T getModelFromSlot(EntityEquipmentSlot slotIn)
	{
		return (T)super.getModelFromSlot(slotIn);
	}

	protected T getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot, T model)
	{
		return (T)super.getArmorModelHook(entity, itemStack, slot, model);
	}

	@Override
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		ResourceLocation res = getMissingTexture();

		if(stack.getItem() instanceof ItemArmor) {
			ArmorMaterial material = ((ItemArmor) stack.getItem()).getArmorMaterial();
			ArmorData data = getDataFor(material);

			if(slot == EntityEquipmentSlot.LEGS) {
				res = data.getSecondTexture();
				if(type != null && type.equals("overlay"))
					res = data.getSecondOverlay();
			} else {
				res = data.getFirstTexture();
				if(type != null && type.equals("overlay"))
					res = data.getFirstOverlay();
			}
		}

		return res;
	}

	protected abstract ResourceLocation getMissingTexture();

	protected abstract ArmorData getDataFor(ArmorMaterial material);
}
