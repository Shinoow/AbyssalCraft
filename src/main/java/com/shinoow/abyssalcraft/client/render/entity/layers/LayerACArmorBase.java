package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliathArmor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public abstract class LayerACArmorBase<T extends ModelBase> extends LayerArmorBase {

	public LayerACArmorBase(RenderLivingBase<?> rendererIn) {
		super(rendererIn);
	}

	protected abstract void toggleVisibility(T model);
}
