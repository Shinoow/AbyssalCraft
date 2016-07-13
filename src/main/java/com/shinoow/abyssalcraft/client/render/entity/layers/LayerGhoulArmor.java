/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.client.model.entity.ModelDGArmor;

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
		func_177194_a(model);

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

	protected void func_177194_a(ModelDGArmor p_177194_1_)
	{
		p_177194_1_.setInvisible(false);
	}

	@Override
	protected ModelDGArmor getArmorModelHook(net.minecraft.entity.EntityLivingBase entity, net.minecraft.item.ItemStack itemStack, EntityEquipmentSlot slot, ModelDGArmor model)
	{
		return model;
	}

	@Override
	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		ResourceLocation res = null;

		switch(slot){
		case HEAD:
			res = AbyssalCraftAPI.getGhoulHelmetTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_ARMOR;
			break;
		case CHEST:
			res = AbyssalCraftAPI.getGhoulChestplateTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_ARMOR;
			break;
		case LEGS:
			res = AbyssalCraftAPI.getGhoulLeggingsTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_LEGGINGS;
			break;
		case FEET:
			res = AbyssalCraftAPI.getGhoulBootsTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_ARMOR;
			break;
		default:
			res = MISSING_ARMOR;
			break;
		}

		return res;
	}
}
