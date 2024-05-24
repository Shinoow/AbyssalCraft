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
import com.shinoow.abyssalcraft.lib.client.model.ModelArmoredBase;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class LayerACArmorBase<T extends ModelArmoredBase> extends LayerArmorBase<ModelArmoredBase> {

	public LayerACArmorBase(RenderLivingBase<?> rendererIn) {
		super(rendererIn);
	}

	@Override
	protected void setModelSlotVisible(ModelArmoredBase model, EntityEquipmentSlot slot)
	{
		model.setVisible(false);
		model.setEquipmentSlotVisible(slot);
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
