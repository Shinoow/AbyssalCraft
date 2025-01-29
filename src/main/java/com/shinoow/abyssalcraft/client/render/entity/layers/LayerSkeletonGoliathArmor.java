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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.api.armor.ArmorData;
import com.shinoow.abyssalcraft.api.armor.ArmorDataRegistry;
import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliathArmor;
import com.shinoow.abyssalcraft.lib.client.render.entity.layers.LayerACArmorBase;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSkeletonGoliathArmor extends LayerACArmorBase<ModelSkeletonGoliathArmor>
{
	private final ResourceLocation MISSING_ARMOR = new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_1.png");

	public LayerSkeletonGoliathArmor(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);
	}

	@Override
	protected void initArmor()
	{
		modelLeggings = new ModelSkeletonGoliathArmor(0.5F);
		modelArmor = new ModelSkeletonGoliathArmor(1.0F);
	}

	@Override
	protected ArmorData getDataFor(ArmorMaterial material) {

		return ArmorDataRegistry.instance().getSkeletonGoliathData(material);
	}

	@Override
	protected ResourceLocation getMissingTexture() {

		return MISSING_ARMOR;
	}
}
