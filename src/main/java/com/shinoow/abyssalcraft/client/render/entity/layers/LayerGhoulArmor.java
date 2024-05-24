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
import com.shinoow.abyssalcraft.client.model.entity.ModelDGArmor;

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
public class LayerGhoulArmor extends LayerArmorBase<ModelDGArmor>
{
	private final ResourceLocation MISSING_ARMOR = new ResourceLocation("abyssalcraft:textures/armor/ghoul/missing_1.png");
	private final ResourceLocation MISSING_LEGGINGS = new ResourceLocation("abyssalcraft:textures/armor/ghoul/missing_2.png");

	public LayerGhoulArmor(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);
	}

	@Override
	protected void initArmor()
	{
		modelLeggings = new ModelDGArmor(0.5F);
		modelArmor = new ModelDGArmor(1.0F);
	}

	@Override
	protected void setModelSlotVisible(ModelDGArmor model, EntityEquipmentSlot slot)
	{
		toggleVisibility(model);

		switch (slot)
		{
		case FEET:
			model.rleg.showModel = true;
			model.lleg.showModel = true;
			break;
		case LEGS:
			model.chestplate.showModel = true;
			model.pelvis.showModel = true;
			model.rleg.showModel = true;
			model.lleg.showModel = true;
			break;
		case CHEST:
			model.chestplate.showModel = true;
			model.Spine3.showModel = true;
			model.rarm1.showModel = true;
			model.larm1.showModel = true;
			break;
		case HEAD:
			model.Head.showModel = true;
			model.jaw.showModel = true;
			break;
		default:
			break;
		}
	}

	protected void toggleVisibility(ModelDGArmor p_177194_1_)
	{
		p_177194_1_.setInvisible(false);
	}

	@Override
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		ResourceLocation res = MISSING_ARMOR;

		if(stack.getItem() instanceof ItemArmor) {
			ArmorMaterial material = ((ItemArmor) stack.getItem()).getArmorMaterial();
			ArmorData data = ArmorDataRegistry.instance().getGhoulData(material);

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
