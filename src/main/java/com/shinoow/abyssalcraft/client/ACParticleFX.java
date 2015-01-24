/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class ACParticleFX extends EntityFX {

	float reddustParticleScale;

	public ACParticleFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10)
	{
		this(par1World, par2, par4, par6, 1.0F, par8, par9, par10);
	}

	public ACParticleFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;

		if (par9 == 0.0F)
			par9 = 1.0F;

		particleRed = particleGreen = particleBlue = 1.0F;
		particleScale *= 0.75F;
		particleScale *= par8;
		reddustParticleScale = particleScale;
		particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
		particleMaxAge = (int)(particleMaxAge * par8);
		noClip = false;
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (particleAge + par2) / particleMaxAge * 32.0F;

		if (var8 < 0.0F)
			var8 = 0.0F;

		if (var8 > 1.0F)
			var8 = 1.0F;

		particleScale = reddustParticleScale * var8;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (particleAge++ >= particleMaxAge)
			setDead();

		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
		moveEntity(motionX, motionY, motionZ);

		if (posY == prevPosY)
		{
			motionX *= 1.1D;
			motionZ *= 1.1D;
		}

		motionX *= 0.9599999785423279D;
		motionY *= 0.9599999785423279D;
		motionZ *= 0.9599999785423279D;

		if (onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}
}