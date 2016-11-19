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
package com.shinoow.abyssalcraft.client.particles;

import java.util.Random;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PEStreamParticleFX extends Particle {

	Random random = new Random();
	public double colorR = 0;
	public double colorG = 0;
	public double colorB = 0;
	float scale;

	public PEStreamParticleFX(World worldIn, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b)
	{
		super(worldIn, x,y,z,0,0,0);
		colorR = r;
		colorG = g;
		colorB = b;
		if (colorR > 1.0)
			colorR = colorR/255.0;
		if (colorG > 1.0)
			colorG = colorG/255.0;
		if (colorB > 1.0)
			colorB = colorB/255.0;
		setRBGColorF((float)r, (float)g, (float)b);
		particleMaxAge = 20;
		particleScale *= 0.75F;
		scale = particleScale;
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		//		noClip = true;
	}

	@Override
	public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (particleAge + par2) / particleMaxAge * 32.0F;
		var8 = MathHelper.clamp_float(var8, 0.0F, 1.0F);
		particleScale = scale * var8;
		super.renderParticle(worldRendererIn, entityIn, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		motionX *= 0.65;
		motionY *= 0.65;
		motionZ *= 0.65;

		if (random.nextInt(4) == 0)
			particleAge --;

		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);

		float lifeCoeff = ((float)particleMaxAge-(float)particleAge)/particleMaxAge;
		particleRed = Math.min(1.0f, (float)colorR*(1.5f-lifeCoeff));
		particleGreen = Math.min(1.0f, (float)colorG*(1.5f-lifeCoeff));
		particleBlue = Math.min(1.0f, (float)colorB*(1.5f-lifeCoeff));
		particleAlpha = lifeCoeff;
	}
}
