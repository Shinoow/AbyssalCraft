package com.shinoow.abyssalcraft.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;

public class BlueFlameParticle extends ParticleFlame {

	public BlueFlameParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("abyssalcraft:particles/blueflame");
		this.setParticleTexture(sprite);
	}

	@Override
	public void setParticleTextureIndex(int particleTextureIndex)
	{
		// NOOP
	}


	public int getFXLayer(){
		return 1;
	}

}
