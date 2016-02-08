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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.client.model.entity.ModelGatekeeperMinion;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;

@SideOnly(Side.CLIENT)
public class RenderGatekeeperMinion extends RenderLiving<EntityGatekeeperMinion> {

	private static ResourceLocation texture = new ResourceLocation("abyssalcraft:textures/model/elite/GatekeeperMinion.png");

	public RenderGatekeeperMinion(RenderManager manager)
	{
		super(manager, new ModelGatekeeperMinion(), 0.5F);
	}

	//	public void doRender(EntityGatekeeperMinion entity, double par2, double par4, double par6, float par8, float par9)
	//	{
	//		super.doRender(entity, par2, par4, par6, par8, par9);
	//	}

	//	@Override
	//	public void doRender(EntityGatekeeperMinion par1Entity, double par2, double par4, double par6, float par8, float par9)
	//	{
	//		doRender((EntityGatekeeperMinion)par1Entity, par2, par4, par6, par8, par9);
	//	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGatekeeperMinion entity) {

		return texture;
	}
}
