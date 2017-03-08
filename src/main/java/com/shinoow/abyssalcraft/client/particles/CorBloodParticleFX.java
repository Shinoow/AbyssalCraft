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
package com.shinoow.abyssalcraft.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class CorBloodParticleFX extends EntityFX {

	float reddustParticleScale;

	public CorBloodParticleFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10)
	{
		this(par1World, par2, par4, par6, 1.0F, par8, par9, par10);
	}

	public CorBloodParticleFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		xSpeed *= 0.10000000149011612D;
		ySpeed *= 0.10000000149011612D;
		zSpeed *= 0.10000000149011612D;

		if (par9 == 0.0F)
			par9 = 1.0F;

		particleRed = particleGreen = particleBlue = 1.0F;
		particleScale *= 0.75F;
		particleScale *= par8;
		reddustParticleScale = particleScale;
		particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
		particleMaxAge = (int)(particleMaxAge * par8);
		//		noClip = false;
	}

	@Override
	public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (particleAge + par2) / particleMaxAge * 32.0F;

		if (var8 < 0.0F)
			var8 = 0.0F;

		if (var8 > 1.0F)
			var8 = 1.0F;

		particleScale = reddustParticleScale * var8;
		super.renderParticle(worldRendererIn, entityIn, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (particleAge++ >= particleMaxAge)
			setExpired();

		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
		moveEntity(xSpeed, ySpeed, zSpeed);

		if (posY == prevPosY)
		{
			xSpeed *= 1.1D;
			zSpeed *= 1.1D;
		}

		xSpeed *= 0.9599999785423279D;
		ySpeed *= 0.9599999785423279D;
		zSpeed *= 0.9599999785423279D;

		if (isCollided)
		{
			xSpeed *= 0.699999988079071D;
			zSpeed *= 0.699999988079071D;
		}
	}
}
