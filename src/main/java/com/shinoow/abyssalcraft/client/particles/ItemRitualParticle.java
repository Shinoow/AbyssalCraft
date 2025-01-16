package com.shinoow.abyssalcraft.client.particles;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemRitualParticle extends Particle {

	Random random = new Random();

	public ItemRitualParticle(World worldIn, double posXIn, double posYIn, double posZIn, double vx, double vy, double vz, Item itemIn, int meta)
	{
		super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);
		setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(itemIn, meta));
		particleRed = 1.0F;
		particleGreen = 1.0F;
		particleBlue = 1.0F;
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		particleScale /= 2.0F;
		particleMaxAge = 20;
		motionX += vx;
		motionZ += vz;
	}

	/**
	 * Retrieve what effect layer (what texture) the particle should be rendered with. 0 for the particle sprite sheet,
	 * 1 for the main Texture atlas, and 3 for a custom texture
	 */
	@Override
	public int getFXLayer()
	{
		return 1;
	}

	/**
	 * Renders the particle
	 */
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
		float f = (particleTextureIndexX + particleTextureJitterX / 4.0F) / 16.0F;
		float f1 = f + 0.015609375F;
		float f2 = (particleTextureIndexY + particleTextureJitterY / 4.0F) / 16.0F;
		float f3 = f2 + 0.015609375F;
		float f4 = 0.1F * particleScale;

		if (particleTexture != null)
		{
			f = particleTexture.getInterpolatedU(particleTextureJitterX / 4.0F * 16.0F);
			f1 = particleTexture.getInterpolatedU((particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
			f2 = particleTexture.getInterpolatedV(particleTextureJitterY / 4.0F * 16.0F);
			f3 = particleTexture.getInterpolatedV((particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
		}

		float f5 = (float)(prevPosX + (posX - prevPosX) * partialTicks - interpPosX);
		float f6 = (float)(prevPosY + (posY - prevPosY) * partialTicks - interpPosY);
		float f7 = (float)(prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ);
		int i = getBrightnessForRender(partialTicks);
		int j = i >> 16 & 65535;
		int k = i & 65535;
		buffer.pos(f5 - rotationX * f4 - rotationXY * f4, f6 - rotationZ * f4, f7 - rotationYZ * f4 - rotationXZ * f4).tex(f, f3).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(j, k).endVertex();
		buffer.pos(f5 - rotationX * f4 + rotationXY * f4, f6 + rotationZ * f4, f7 - rotationYZ * f4 + rotationXZ * f4).tex(f, f2).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(j, k).endVertex();
		buffer.pos(f5 + rotationX * f4 + rotationXY * f4, f6 + rotationZ * f4, f7 + rotationYZ * f4 + rotationXZ * f4).tex(f1, f2).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(j, k).endVertex();
		buffer.pos(f5 + rotationX * f4 - rotationXY * f4, f6 - rotationZ * f4, f7 + rotationYZ * f4 - rotationXZ * f4).tex(f1, f3).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(j, k).endVertex();
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if (random.nextInt(4) == 0)
			particleAge --;

		float lifeCoeff = ((float)particleMaxAge-(float)particleAge)/particleMaxAge;

		particleAlpha = lifeCoeff;
	}
}