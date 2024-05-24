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

import com.shinoow.abyssalcraft.api.armor.ArmorData;
import com.shinoow.abyssalcraft.api.armor.ArmorDataRegistry;
import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliathArmor;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSkeletonGoliathArmor extends LayerArmorBase<ModelSkeletonGoliathArmor>
{
	private final ResourceLocation MISSING_ARMOR = new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_1.png");
	private final ResourceLocation MISSING_LEGGINGS = new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_2.png");

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
	protected void setModelSlotVisible(ModelSkeletonGoliathArmor model, EntityEquipmentSlot slot)
	{
		toggleVisibility(model);

		switch (slot)
		{
		case FEET:
			model.rightleg.showModel = true;
			model.leftleg.showModel = true;
			break;
		case LEGS:
			model.chestplate.showModel = true;
			model.pelvis.showModel = true;
			model.rightleg.showModel = true;
			model.leftleg.showModel = true;
			break;
		case CHEST:
			model.chestplate.showModel = true;
			model.rightarm.showModel = true;
			model.leftarm.showModel = true;
			break;
		case HEAD:
			model.head.showModel = true;
			break;
		default:
			break;
		}
	}

	protected void toggleVisibility(ModelSkeletonGoliathArmor model)
	{
		model.setInvisible(false);
	}

	@Override
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		ResourceLocation res = MISSING_ARMOR;

		if(stack.getItem() instanceof ItemArmor) {
			ArmorMaterial material = ((ItemArmor) stack.getItem()).getArmorMaterial();
			ArmorData data = ArmorDataRegistry.instance().getSkeletonGoliathData(material);

			if(slot == EntityEquipmentSlot.LEGS) {
				res = data.getSecondTexture();
				if(type != null && type.equals("overlay")){
					res = data.getSecondOverlay();
				}
			} else {
				res = data.getFirstTexture();
				if(type != null && type.equals("overlay")){
					res = data.getFirstOverlay();
				}
			}
		}

		return res;
	}
}
