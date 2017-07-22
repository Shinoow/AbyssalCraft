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
package com.shinoow.abyssalcraft.common.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACAchievements;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityDragonMinion extends EntityMob implements IEntityMultiPart, ICoraliumEntity
{

	public static final float innerRotation = 0;
	public double targetX;
	public double targetY;
	public double targetZ;

	/**
	 * Ring buffer array for the last 64 Y-positions and yaw rotations. Used to calculate offsets for the animations.
	 */
	public double[][] ringBuffer = new double[64][3];

	/**
	 * Index into the ring buffer. Incremented once per tick and restarts at 0 once it reaches the end of the buffer.
	 */
	public int ringBufferIndex = -1;

	/** An array containing all body parts of this dragon */
	public MultiPartEntityPart[] dragonPartArray;

	/** The head bounding box of a dragon */
	public MultiPartEntityPart dragonPartHead;

	/** The body bounding box of a dragon */
	public MultiPartEntityPart dragonPartBody;
	public MultiPartEntityPart dragonPartTail1;
	public MultiPartEntityPart dragonPartTail2;
	public MultiPartEntityPart dragonPartTail3;
	public MultiPartEntityPart dragonPartWing1;
	public MultiPartEntityPart dragonPartWing2;

	/** Animation time at previous tick. */
	public float prevAnimTime;

	/**
	 * Animation time, used to control the speed of the animation cycles (wings flapping, jaw opening, etc.)
	 */
	public float animTime;

	/** Force selecting a new flight target at next tick if set to true. */
	public boolean forceNewTarget;

	private Entity target;

	public EntityDragonBoss healingcircle;

	public EntityDragonMinion(World par1World) {
		super(par1World);
		dragonPartArray = new MultiPartEntityPart[] {dragonPartHead = new MultiPartEntityPart(this, "head", 3.0F, 3.0F), dragonPartBody = new MultiPartEntityPart(this, "body", 4.0F, 4.0F), dragonPartTail1 = new MultiPartEntityPart(this, "tail", 2.0F, 2.0F), dragonPartTail2 = new MultiPartEntityPart(this, "tail", 2.0F, 2.0F), dragonPartTail3 = new MultiPartEntityPart(this, "tail", 2.0F, 2.0F), dragonPartWing1 = new MultiPartEntityPart(this, "wing", 2.0F, 2.0F), dragonPartWing2 = new MultiPartEntityPart(this, "wing", 2.0F, 2.0F)};
		setHealth(getMaxHealth());
		setSize(8.0F, 3.0F);
		noClip = true;
		targetY = 100.0D;
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		if(ACConfig.hardcoreMode) getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
		else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	public double[] getMovementOffsets(int par1, float par2)
	{
		if (getHealth() <= 0.0F)
			par2 = 0.0F;

		par2 = 1.0F - par2;
		int j = ringBufferIndex - par1 * 1 & 63;
		int k = ringBufferIndex - par1 * 1 - 1 & 63;
		double[] adouble = new double[3];
		double d0 = ringBuffer[j][0];
		double d1 = MathHelper.wrapDegrees(ringBuffer[k][0] - d0);
		adouble[0] = d0 + d1 * par2;
		d0 = ringBuffer[j][1];
		d1 = ringBuffer[k][1] - d0;
		adouble[1] = d0 + d1 * par2;
		adouble[2] = ringBuffer[j][2] + (ringBuffer[k][2] - ringBuffer[j][2]) * par2;
		return adouble;
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.coralium_plagued_flesh;

	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_SPECTRAL_DRAGON;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getTrueSource();
//			entityplayer.addStat(ACAchievements.kill_spectral_dragon, 1);
		}
	}

	@Override
	public void onLivingUpdate()
	{
		float f;
		float f1;

		if (world.isRemote)
		{
			f = MathHelper.cos(animTime * (float)Math.PI * 2.0F);
			f1 = MathHelper.cos(prevAnimTime * (float)Math.PI * 2.0F);

			if (f1 <= -0.3F && f >= -0.3F)
				world.playSound(posX, posY, posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 5.0F, 0.8F + rand.nextFloat() * 0.3F, false);
		}

		prevAnimTime = animTime;
		float f2;

		if (getHealth() <= 0.0F)
		{
			f = (rand.nextFloat() - 0.5F) * 8.0F;
			f1 = (rand.nextFloat() - 0.5F) * 4.0F;
			f2 = (rand.nextFloat() - 0.5F) * 8.0F;
			if(ACConfig.particleEntity)
				world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}
		else
		{
			updateHealingCircle();
			f = 0.2F / (MathHelper.sqrt(motionX * motionX + motionZ * motionZ) * 10.0F + 1.0F);
			f *= (float)Math.pow(2.0D, motionY);

			animTime += f;


			rotationYaw = MathHelper.wrapDegrees(rotationYaw);

			if (ringBufferIndex < 0)
				for (int i = 0; i < ringBuffer.length; ++i)
				{
					ringBuffer[i][0] = rotationYaw;
					ringBuffer[i][1] = posY;
				}

			if (++ringBufferIndex == ringBuffer.length)
				ringBufferIndex = 0;

			ringBuffer[ringBufferIndex][0] = rotationYaw;
			ringBuffer[ringBufferIndex][1] = posY;
			double d0;
			double d1;
			double d2;
			double d3;
			float f3;

			if (world.isRemote)
			{
				if (newPosRotationIncrements > 0)
				{
					d3 = posX + (interpTargetX - posX) / newPosRotationIncrements;
					d0 = posY + (interpTargetY - posY) / newPosRotationIncrements;
					d1 = posZ + (interpTargetZ - posZ) / newPosRotationIncrements;
					d2 = MathHelper.wrapDegrees(interpTargetYaw - rotationYaw);
					rotationYaw = (float)(rotationYaw + d2 / newPosRotationIncrements);
					rotationPitch = (float)(rotationPitch + (interpTargetPitch - rotationPitch) / newPosRotationIncrements);
					--newPosRotationIncrements;
					setPosition(d3, d0, d1);
					setRotation(rotationYaw, rotationPitch);
				}
			}
			else
			{
				d3 = targetX - posX;
				d0 = targetY - posY;
				d1 = targetZ - posZ;
				d2 = d3 * d3 + d0 * d0 + d1 * d1;

				if (target != null)
				{
					targetX = target.posX;
					targetZ = target.posZ;
					double d4 = targetX - posX;
					double d5 = targetZ - posZ;
					double d6 = Math.sqrt(d4 * d4 + d5 * d5);
					double d7 = 0.4000000059604645D + d6 / 80.0D - 1.0D;

					if (d7 > 10.0D)
						d7 = 10.0D;

					targetY = target.getEntityBoundingBox().minY + d7;
				}
				else
				{
					targetX += rand.nextGaussian() * 2.0D;
					targetZ += rand.nextGaussian() * 2.0D;
				}

				if (forceNewTarget || d2 < 100.0D || d2 > 22500.0D || isCollidedHorizontally || isCollidedVertically)
					setNewTarget();

				d0 /= MathHelper.sqrt(d3 * d3 + d1 * d1);
				f3 = 0.6F;

				if (d0 < -f3)
					d0 = -f3;

				if (d0 > f3)
					d0 = f3;

				motionY += d0 * 0.10000000149011612D;
				rotationYaw = MathHelper.wrapDegrees(rotationYaw);
				double d8 = 180.0D - Math.atan2(d3, d1) * 180.0D / Math.PI;
				double d9 = MathHelper.wrapDegrees(d8 - rotationYaw);

				if (d9 > 50.0D)
					d9 = 50.0D;

				if (d9 < -50.0D)
					d9 = -50.0D;

				Vec3d vec3 = new Vec3d(targetX - posX, targetY - posY, targetZ - posZ).normalize();
				Vec3d vec31 = new Vec3d(MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F), motionY, -MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F)).normalize();
				float f4 = (float)(vec31.dotProduct(vec3) + 0.5D) / 1.5F;

				if (f4 < 0.0F)
					f4 = 0.0F;

				randomYawVelocity *= 0.8F;
				float f5 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ) * 1.0F + 1.0F;
				double d10 = Math.sqrt(motionX * motionX + motionZ * motionZ) * 1.0D + 1.0D;

				if (d10 > 40.0D)
					d10 = 40.0D;

				randomYawVelocity = (float)(randomYawVelocity + d9 * (0.699999988079071D / d10 / f5));
				rotationYaw += randomYawVelocity * 0.1F;
				float f6 = (float)(2.0D / (d10 + 1.0D));
				float f7 = 0.06F;
				moveRelative(0.0F, 0.0F, -1.0F, f7 * (f4 * f6 + (1.0F - f6)));


				move(MoverType.SELF, motionX, motionY, motionZ);


				Vec3d vec32 = new Vec3d(motionX, motionY, motionZ).normalize();
				float f8 = (float)(vec32.dotProduct(vec31) + 1.0D) / 2.5F;
				f8 = 0.8F + 0.15F * f8;
				motionX *= f8;
				motionZ *= f8;
				motionY *= 0.9100000262260437D;
			}

			renderYawOffset = rotationYaw;
			dragonPartHead.width = dragonPartHead.height = 1.5F;
			dragonPartTail1.width = dragonPartTail1.height = 1.0F;
			dragonPartTail2.width = dragonPartTail2.height = 1.0F;
			dragonPartTail3.width = dragonPartTail3.height = 1.0F;
			dragonPartBody.height = 1.5F;
			dragonPartBody.width = 2.5F;
			dragonPartWing1.height = 1.0F;
			dragonPartWing1.width = 2.0F;
			dragonPartWing2.height = 1.0F;
			dragonPartWing2.width = 2.0F;
			f1 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * (float)Math.PI;
			f2 = MathHelper.cos(f1);
			float f9 = -MathHelper.sin(f1);
			float f10 = rotationYaw * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f10);
			float f12 = MathHelper.cos(f10);
			dragonPartBody.onUpdate();
			dragonPartBody.setLocationAndAngles(posX + f11 * 0.1F, posY, posZ - f12 * 0.1F, 0.0F, 0.0F);
			dragonPartWing1.onUpdate();
			dragonPartWing1.setLocationAndAngles(posX + f12 * 2.2F, posY + 1.0D, posZ + f11 * 2.2F, 0.0F, 0.0F);
			dragonPartWing2.onUpdate();
			dragonPartWing2.setLocationAndAngles(posX - f12 * 2.2F, posY + 1.0D, posZ - f11 * 2.2F, 0.0F, 0.0F);

			if (!world.isRemote && hurtTime == 0)
			{
				collideWithEntities(world.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing1.getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D).offset(0.0D, -0.5D, 0.0D)));
				collideWithEntities(world.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing2.getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D).offset(0.0D, -0.5D, 0.0D)));
				attackEntitiesInList(world.getEntitiesWithinAABBExcludingEntity(this, dragonPartHead.getEntityBoundingBox().expand(0.25D, 0.25D, 0.25D)));
			}

			double[] adouble = getMovementOffsets(5, 1.0F);
			double[] adouble1 = getMovementOffsets(0, 1.0F);
			f3 = MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F - randomYawVelocity * 0.01F);
			float f13 = MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F - randomYawVelocity * 0.01F);
			dragonPartHead.onUpdate();
			dragonPartHead.setLocationAndAngles(posX + f3 * 2.5F * f2, posY + (adouble1[1] - adouble[1]) * 1.0D + f9 * 2.5F, posZ - f13 * 2.5F * f2, 0.0F, 0.0F);

			for (int j = 0; j < 3; ++j)
			{
				MultiPartEntityPart entitydragonpart = null;

				if (j == 0)
					entitydragonpart = dragonPartTail1;

				if (j == 1)
					entitydragonpart = dragonPartTail2;

				if (j == 2)
					entitydragonpart = dragonPartTail3;

				double[] adouble2 = getMovementOffsets(12 + j * 2, 1.0F);
				float f14 = rotationYaw * (float)Math.PI / 180.0F + simplifyAngle(adouble2[0] - adouble[0]) * (float)Math.PI / 180.0F * 1.0F;
				float f15 = MathHelper.sin(f14);
				float f16 = MathHelper.cos(f14);
				float f17 = 0.1F;
				float f18 = (j + 1) * 1.5F;
				entitydragonpart.onUpdate();
				entitydragonpart.setLocationAndAngles(posX - (f11 * f17 + f15 * f18) * f2, posY + (adouble2[1] - adouble[1]) * 1.0D - (f18 + f17) * f9 + 1.0D, posZ + (f12 * f17 + f16 * f18) * f2, 0.0F, 0.0F);
			}
		}
	}

	@Override
	public void move(MoverType type, double x, double y, double z)
	{
		setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
		resetPositionToBB();
		try
		{
			doBlockCollisions();
		}
		catch (Throwable throwable)
		{
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
			addEntityCrashInfo(crashreportcategory);
			throw new ReportedException(crashreport);
		}
	}

	@Override
	protected void doBlockCollisions()
	{
		AxisAlignedBB axisalignedbb = getEntityBoundingBox();
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001D, axisalignedbb.minY + 0.001D, axisalignedbb.minZ + 0.001D);
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001D, axisalignedbb.maxY - 0.001D, axisalignedbb.maxZ - 0.001D);
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain();

		if (world.isAreaLoaded(blockpos$pooledmutableblockpos, blockpos$pooledmutableblockpos1))
			for (int i = blockpos$pooledmutableblockpos.getX(); i <= blockpos$pooledmutableblockpos1.getX(); ++i)
				for (int j = blockpos$pooledmutableblockpos.getY(); j <= blockpos$pooledmutableblockpos1.getY(); ++j)
					for (int k = blockpos$pooledmutableblockpos.getZ(); k <= blockpos$pooledmutableblockpos1.getZ(); ++k)
					{
						blockpos$pooledmutableblockpos2.setPos(i, j, k);
						IBlockState iblockstate = world.getBlockState(blockpos$pooledmutableblockpos2);

						if(iblockstate.getMaterial() == Material.PORTAL)
							addVelocity(motionX > 0 ? -3 : 3, motionY > 0 ? -3 : 3, motionZ > 0 ? -3 : 3);
					}

		blockpos$pooledmutableblockpos.release();
		blockpos$pooledmutableblockpos1.release();
		blockpos$pooledmutableblockpos2.release();
	}

	private void updateHealingCircle()
	{
		if (healingcircle != null)
			if (healingcircle.isDead)
			{
				if (!world.isRemote)
					attackEntityFromPart(dragonPartHead, DamageSource.causeExplosionDamage((Explosion)null), 100.0F);

				healingcircle = null;
			}
			else if (ticksExisted % 10 == 0 && getHealth() <= getMaxHealth())
				setHealth(getHealth() - 1.0F);

		if (rand.nextInt(10) == 0)
		{
			float f = 32.0F;
			List<?> list = world.getEntitiesWithinAABB(EntityDragonBoss.class, getEntityBoundingBox().expand(f, f, f));
			EntityDragonBoss entitydragonboss = null;
			double d0 = Double.MAX_VALUE;
			Iterator<?> iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityDragonBoss entitydragonboss1 = (EntityDragonBoss)iterator.next();
				double d1 = entitydragonboss1.getDistanceSqToEntity(this);

				if (d1 < d0)
				{
					d0 = d1;
					entitydragonboss = entitydragonboss1;
				}
			}

			healingcircle = entitydragonboss;
		}
	}

	private void collideWithEntities(List<?> par1List)
	{
		double d0 = (dragonPartBody.getEntityBoundingBox().minX + dragonPartBody.getEntityBoundingBox().maxX) / 2.0D;
		double d1 = (dragonPartBody.getEntityBoundingBox().minZ + dragonPartBody.getEntityBoundingBox().maxZ) / 2.0D;
		Iterator<?> iterator = par1List.iterator();

		while (iterator.hasNext())
		{
			Entity entity = (Entity)iterator.next();

			if (entity instanceof EntityLivingBase)
			{
				double d2 = entity.posX - d0;
				double d3 = entity.posZ - d1;
				double d4 = d2 * d2 + d3 * d3;
				entity.addVelocity(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
			}
		}
	}

	private void attackEntitiesInList(List<?> par1List)
	{
		for (int i = 0; i < par1List.size(); ++i)
		{
			Entity entity = (Entity)par1List.get(i);

			if (entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)entity))
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 200));

			if(ACConfig.hardcoreMode && entity instanceof EntityPlayer)
				entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1);
		}
	}

	private void setNewTarget()
	{
		forceNewTarget = false;

		if (rand.nextInt(2) == 0 && !world.playerEntities.isEmpty())
			target = world.playerEntities.get(rand.nextInt(world.playerEntities.size()));
		else
		{
			boolean flag = false;

			do
			{
				targetX = 0.0D;
				targetY = 70.0F + rand.nextFloat() * 50.0F;
				targetZ = 0.0D;
				targetX += rand.nextFloat() * 120.0F - 60.0F;
				targetZ += rand.nextFloat() * 120.0F - 60.0F;
				double d0 = posX - targetX;
				double d1 = posY - targetY;
				double d2 = posZ - targetZ;
				flag = d0 * d0 + d1 * d1 + d2 * d2 > 100.0D;
			}
			while (!flag);

			target = null;
		}
	}

	private float simplifyAngle(double par1)
	{
		return (float)MathHelper.wrapDegrees(par1);
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart par1EntityDragonPart, DamageSource par2DamageSource, float par3)
	{
		if (par1EntityDragonPart != dragonPartHead)
			par3 = par3 / 2.0F + 1.0F;

		float f1 = rotationYaw * (float)Math.PI / 180.0F;
		float f2 = MathHelper.sin(f1);
		float f3 = MathHelper.cos(f1);
		targetX = posX + f2 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		targetY = posY + rand.nextFloat() * 3.0F + 1.0D;
		targetZ = posZ - f3 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		target = null;

		if (par2DamageSource.getTrueSource() instanceof EntityPlayer || par2DamageSource.isExplosion())
			super.attackEntityFrom(par2DamageSource, par3);

		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		return false;
	}

	@Override
	public Entity[] getParts()
	{
		return dragonPartArray;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return false;
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_ENDERDRAGON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_ENDERDRAGON_HURT;
	}
}
