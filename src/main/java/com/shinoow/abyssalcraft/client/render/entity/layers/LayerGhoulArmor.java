/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.item.ItemArmor;
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

	public LayerGhoulArmor(RendererLivingEntity<?> rendererIn)
	{
		super(rendererIn);
	}

	@Override
	protected void initArmor()
	{
		field_177189_c = new ModelDGArmor(0.5F);
		field_177186_d = new ModelDGArmor(1.0F);
	}

	@Override
	protected void func_177179_a(ModelDGArmor model, int slot)
	{
		func_177194_a(model);

		switch (slot)
		{
		case 1:
			model.rleg.showModel = true;
			model.lleg.showModel = true;
			break;
		case 2:
			model.chestplate.showModel = true;
			model.pelvis.showModel = true;
			model.rleg.showModel = true;
			model.lleg.showModel = true;
			break;
		case 3:
			model.chestplate.showModel = true;
			model.Spine3.showModel = true;
			model.rarm1.showModel = true;
			model.larm1.showModel = true;
			break;
		case 4:
			model.Head.showModel = true;
			model.jaw.showModel = true;
		}
	}

	protected void func_177194_a(ModelDGArmor p_177194_1_)
	{
		p_177194_1_.setInvisible(false);
	}

	@Override
	protected ModelDGArmor getArmorModelHook(net.minecraft.entity.EntityLivingBase entity, net.minecraft.item.ItemStack itemStack, int slot, ModelDGArmor model)
	{
		return model;
	}

	@Override
	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, int slot, String type)
	{
		ResourceLocation res = null;

		ItemArmor item = (ItemArmor)stack.getItem();

		switch(item.armorType){
		case 0:
			res = AbyssalCraftAPI.getGhoulHelmetTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_ARMOR;
			break;
		case 1:
			res = AbyssalCraftAPI.getGhoulChestplateTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_ARMOR;
			break;
		case 2:
			res = AbyssalCraftAPI.getGhoulLeggingsTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_LEGGINGS;
			break;
		case 3:
			res = AbyssalCraftAPI.getGhoulBootsTexture(stack.getItem());
			if(type != null && type.equals("overlay") && res != null){
				String domain = res.getResourceDomain();
				String path = res.getResourcePath();
				res = new ResourceLocation(domain, path.substring(0, path.length() -4).concat("_overlay.png"));
			}
			if(res == null)
				res = MISSING_ARMOR;
			break;
		}

		return res;
	}
}
