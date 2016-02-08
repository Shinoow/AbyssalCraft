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
package com.shinoow.abyssalcraft.client.render.factory;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.shinoow.abyssalcraft.client.render.entity.RenderEvilCow;

public class RenderFactoryEvilCow implements IRenderFactory {

	@Override
	public Render createRenderFor(RenderManager manager) {

		return new RenderEvilCow(manager, new ModelCow(), 0.5F);
	}
}
