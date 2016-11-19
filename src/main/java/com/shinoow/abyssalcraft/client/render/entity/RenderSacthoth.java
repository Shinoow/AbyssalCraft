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
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.client.model.entity.ModelSacthoth;
import com.shinoow.abyssalcraft.common.entity.EntitySacthoth;

@SideOnly(Side.CLIENT)
public class RenderSacthoth extends RenderLiving<EntitySacthoth> {

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/Sacthoth.png");

	public RenderSacthoth(RenderManager manager)
	{
		super(manager, new ModelSacthoth(), 0.5F);
	}

	@Override
	public void doRender(EntitySacthoth entity, double par2, double par4, double par6, float par8, float par9)
	{
		BossStatus.setBossStatus(entity, false);
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySacthoth entity) {

		return mobTexture;
	}
}