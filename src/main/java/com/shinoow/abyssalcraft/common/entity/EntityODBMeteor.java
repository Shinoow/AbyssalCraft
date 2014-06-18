/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityODBMeteor extends Entity
{

	public int field_92057_e = 1;
	private int field_145795_e = -1;
	private int field_145793_f = -1;
	private int field_145794_g = -1;
	private Block field_145796_h;
	private boolean inGround;
	public EntityLivingBase shootingEntity;
	private int ticksAlive;
	private int ticksInAir;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;

	public EntityODBMeteor(World par1World)
	{
		super(par1World);
		setSize(3.0F, 3.0F);
	}

	@Override
	protected void entityInit() {}

	public EntityODBMeteor(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World);
		setSize(3.0F, 3.0F);
		setLocationAndAngles(par2, par4, par6, rotationYaw, rotationPitch);
		setPosition(par2, par4, par6);
		double d6 = MathHelper.sqrt_double(par8 * par8 + par10 * par10 + par12 * par12);
		accelerationX = par8 / d6 * 0.1D;
		accelerationY = par10 / d6 * 0.1D;
		accelerationZ = par12 / d6 * 0.1D;
	}

	public EntityODBMeteor(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
	{
		super(par1World);
		shootingEntity = par2EntityLivingBase;
		setSize(3.0F, 3.0F);
		setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY, par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = motionY = motionZ = 0.0D;
		par3 += rand.nextGaussian() * 0.4D;
		par5 += rand.nextGaussian() * 0.4D;
		par7 += rand.nextGaussian() * 0.4D;
		double d3 = MathHelper.sqrt_double(par3 * par3 + par5 * par5 + par7 * par7);
		accelerationX = par3 / d3 * 0.1D;
		accelerationY = par5 / d3 * 0.1D;
		accelerationZ = par7 / d3 * 0.1D;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void onUpdate()
	{
		if (!worldObj.isRemote || !worldObj.blockExists((int)posX, (int)posY, (int)posZ))
		{
			setDead();
		}
		else
		{
			super.onUpdate();

			if (inGround)
			{
				if (worldObj.getBlock(field_145795_e, field_145793_f, field_145794_g) == field_145796_h)
				{
					++ticksAlive;

					if (ticksAlive == 600)
					{
						setDead();
					}

					return;
				}

				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksAlive = 0;
				ticksInAir = 0;
			}
			else
			{
				++ticksInAir;
			}

			Vec3 vec3 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			Vec3 vec31 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3, vec31);
			vec3 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			vec31 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);

			if (movingobjectposition != null)
			{
				vec31 = worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;

			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity1 = (Entity)list.get(i);

				if (entity1.canBeCollidedWith() && (!entity1.isEntityEqual(shootingEntity) || ticksInAir >= 25))
				{
					float f = 0.3F;
					AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

					if (movingobjectposition1 != null)
					{
						double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null)
			{
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null)
			{
				onImpact(movingobjectposition);
			}

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float)(Math.atan2(motionZ, motionX) * 180.0D / Math.PI) + 90.0F;

			for (rotationPitch = (float)(Math.atan2(f1, motionY) * 180.0D / Math.PI) - 90.0F; rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			{
				;
			}

			while (rotationPitch - prevRotationPitch >= 180.0F)
			{
				prevRotationPitch += 360.0F;
			}

			while (rotationYaw - prevRotationYaw < -180.0F)
			{
				prevRotationYaw -= 360.0F;
			}

			while (rotationYaw - prevRotationYaw >= 180.0F)
			{
				prevRotationYaw += 360.0F;
			}

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float f2 = getMotionFactor();

			if (isInWater())
			{
				for (int j = 0; j < 4; ++j)
				{
					float f3 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ);
				}

				f2 = 0.8F;
			}

			motionX += accelerationX;
			motionY += accelerationY;
			motionZ += accelerationZ;
			motionX *= f2;
			motionY *= f2;
			motionZ *= f2;
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
			setPosition(posX, posY, posZ);
		}
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public float getCollisionBorderSize()
	{
		return 1.0F;
	}

	protected float getMotionFactor()
	{
		return 0.95F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)field_145795_e);
		par1NBTTagCompound.setShort("yTile", (short)field_145793_f);
		par1NBTTagCompound.setShort("zTile", (short)field_145794_g);
		par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(field_145796_h));
		par1NBTTagCompound.setByte("inGround", (byte)(inGround ? 1 : 0));
		par1NBTTagCompound.setTag("direction", newDoubleNBTList(new double[] {motionX, motionY, motionZ}));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		field_145795_e = par1NBTTagCompound.getShort("xTile");
		field_145793_f = par1NBTTagCompound.getShort("yTile");
		field_145794_g = par1NBTTagCompound.getShort("zTile");
		field_145796_h = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
		inGround = par1NBTTagCompound.getByte("inGround") == 1;

		if (par1NBTTagCompound.hasKey("direction", 9))
		{
			NBTTagList nbttaglist = par1NBTTagCompound.getTagList("direction", 6);
			motionX = nbttaglist.func_150309_d(0);
			motionY = nbttaglist.func_150309_d(1);
			motionZ = nbttaglist.func_150309_d(2);
		}
		else
		{
			setDead();
		}
	}

	protected void onImpact(MovingObjectPosition movingobjectposition)
	{
		if (!worldObj.isRemote)
		{
			if (movingobjectposition.entityHit != null)
			{
				movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, shootingEntity), 6.0F);
			}

			worldObj.newExplosion((Entity)null, posX, posY, posZ, field_92057_e, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			setDead();
		}
	}

}
