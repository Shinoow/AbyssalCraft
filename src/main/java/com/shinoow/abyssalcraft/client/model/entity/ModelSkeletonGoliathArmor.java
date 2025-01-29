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
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModelSkeletonGoliathArmor extends ModelSkeletonGoliath {

	public ModelRenderer chestplate;

	public ModelSkeletonGoliathArmor() {
		this(0.0F);
	}

	public ModelSkeletonGoliathArmor(float f) {
		super(f);
		textureWidth = 128;
		textureHeight = 64;
		chestplate = new ModelRenderer(this, 79, 0);
		chestplate.setRotationPoint(0.0F, -11.0F, -2.0F);
		chestplate.addBox(-6.0F, -0.01F, -0.01F, 13, 17, 5, f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);

		chestplate.render(f5);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		chestplate.showModel = visible;
	}

	@Override
	public void setEquipmentSlotVisible(EntityEquipmentSlot slot) {
		super.setEquipmentSlotVisible(slot);
		if(slot == EntityEquipmentSlot.CHEST ||
				slot == EntityEquipmentSlot.LEGS)
			chestplate.showModel = true;
	}
}
