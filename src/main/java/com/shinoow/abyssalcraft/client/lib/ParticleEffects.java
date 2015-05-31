/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.client.ACParticleFX;

public class ParticleEffects {

	private static Minecraft mc = Minecraft.getMinecraft();
	private static World theWorld = mc.theWorld;
	public static EntityFX spawnParticle(String particleName, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null)
		{
			int var14 = mc.gameSettings.particleSetting;

			if (var14 == 1 && theWorld.rand.nextInt(3) == 0)
				var14 = 2;

			double var15 = mc.renderViewEntity.posX - par2;
			double var17 = mc.renderViewEntity.posY - par4;
			double var19 = mc.renderViewEntity.posZ - par6;
			EntityFX var21 = null;
			double var22 = 16.0D;

			if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
				return null;
			else if (var14 > 1)
				return null;
			else
			{
				if (particleName.equals("CorBlood"))
				{
					var21 = new ACParticleFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
					var21.setRBGColorF(0, 255, 255);
				}

				mc.effectRenderer.addEffect(var21);
				return var21;
			}
		}
		return null;
	}
}
