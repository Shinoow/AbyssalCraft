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

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.lib.ParticleEffects;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.core.api.entity.ICoraliumEntity;

public class EntityDragonBoss extends EntityMob implements IBossDisplayData, IEntityMultiPart, ICoraliumEntity
{

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
	public EntityDragonPart[] dragonPartArray;

	/** The head bounding box of a dragon */
	public EntityDragonPart dragonPartHead;

	/** The body bounding box of a dragon */
	public EntityDragonPart dragonPartBody;
	public EntityDragonPart dragonPartTail1;
	public EntityDragonPart dragonPartTail2;
	public EntityDragonPart dragonPartTail3;
	public EntityDragonPart dragonPartWing1;
	public EntityDragonPart dragonPartWing2;

	/** Animation time at previous tick. */
	public float prevAnimTime;

	/**
	 * Animation time, used to control the speed of the animation cycles (wings flapping, jaw opening, etc.)
	 */
	public float animTime;

	/** Force selecting a new flight target at next tick if set to true. */
	public boolean forceNewTarget;

	private Entity target;
	public int deathTicks;

	public EntityDragonMinion healingcircle;

	public EntityDragonBoss(World par1World) {
		super(par1World);
		dragonPartArray = new EntityDragonPart[] {dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F), dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F), dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F), dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F)};
		setHealth(getMaxHealth());
		setSize(18.0F, 10.0F);
		noClip = false;
		targetY = 100.0D;
		ignoreFrustumCheck = true;
	}

	@Override
	public String getCommandSenderName()
	{
		return EnumChatFormatting.AQUA + StatCollector.translateToLocal("entity.abyssalcraft.dragonboss.name");
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
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
		double d1 = MathHelper.wrapAngleTo180_double(ringBuffer[k][0] - d0);
		adouble[0] = d0 + d1 * par2;
		d0 = ringBuffer[j][1];
		d1 = ringBuffer[k][1] - d0;
		adouble[1] = d0 + d1 * par2;
		adouble[2] = ringBuffer[j][2] + (ringBuffer[k][2] - ringBuffer[j][2]) * par2;
		return adouble;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		dropItem(AbyssalCraft.EoA, 1);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		if (par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killAsorah, 1);
		}
		if(worldObj.isRemote){
			SpecialTextUtil.OblivionaireText("Asorah? ... Asorah?!");
			SpecialTextUtil.OblivionaireText("YOU, you will pay for this mortal!");
			SpecialTextUtil.OblivionaireText("Besides, you'll never get past The Dreadlands. Have fun digging your own grave.");
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public void onLivingUpdate()
	{

		float f;
		float f1;

		if (worldObj.isRemote)
		{
			BossStatus.setBossStatus(this, true);
			f = MathHelper.cos(animTime * (float)Math.PI * 2.0F);
			f1 = MathHelper.cos(prevAnimTime * (float)Math.PI * 2.0F);

			if (f1 <= -0.3F && f >= -0.3F)
				worldObj.playSound(posX, posY, posZ, "mob.enderdragon.wings", 5.0F, 0.8F + rand.nextFloat() * 0.3F, false);
		}

		prevAnimTime = animTime;
		float f2;

		if (getHealth() <= 0.0F)
		{
			f = (rand.nextFloat() - 0.5F) * 8.0F;
			f1 = (rand.nextFloat() - 0.5F) * 4.0F;
			f2 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle("largeexplode", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}
		else
		{
			updateHealingCircle();
			f = 0.2F / (MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 10.0F + 1.0F);
			f *= (float)Math.pow(2.0D, motionY);

			animTime += f;


			rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);

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

			if (worldObj.isRemote)
			{
				if (newPosRotationIncrements > 0)
				{
					d3 = posX + (newPosX - posX) / newPosRotationIncrements;
					d0 = posY + (newPosY - posY) / newPosRotationIncrements;
					d1 = posZ + (newPosZ - posZ) / newPosRotationIncrements;
					d2 = MathHelper.wrapAngleTo180_double(newRotationYaw - rotationYaw);
					rotationYaw = (float)(rotationYaw + d2 / newPosRotationIncrements);
					rotationPitch = (float)(rotationPitch + (newRotationPitch - rotationPitch) / newPosRotationIncrements);
					--newPosRotationIncrements;
					setPosition(d3, d0, d1);
					setRotation(rotationYaw, rotationPitch);
				}

				for (int i = 0; i < 2; ++i)
					ParticleEffects.spawnParticle("CorBlood", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - 0.25D, posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
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

					targetY = target.boundingBox.minY + d7;
				}
				else
				{
					targetX += rand.nextGaussian() * 2.0D;
					targetZ += rand.nextGaussian() * 2.0D;
				}

				if (forceNewTarget || d2 < 100.0D || d2 > 22500.0D || isCollidedHorizontally || isCollidedVertically)
					setNewTarget();

				d0 /= MathHelper.sqrt_double(d3 * d3 + d1 * d1);
				f3 = 0.6F;

				if (d0 < -f3)
					d0 = -f3;

				if (d0 > f3)
					d0 = f3;

				motionY += d0 * 0.10000000149011612D;
				rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);
				double d8 = 180.0D - Math.atan2(d3, d1) * 180.0D / Math.PI;
				double d9 = MathHelper.wrapAngleTo180_double(d8 - rotationYaw);

				if (d9 > 50.0D)
					d9 = 50.0D;

				if (d9 < -50.0D)
					d9 = -50.0D;

				Vec3 vec3 = Vec3.createVectorHelper(targetX - posX, targetY - posY, targetZ - posZ).normalize();
				Vec3 vec31 = Vec3.createVectorHelper(MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F), motionY, -MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F)).normalize();
				float f4 = (float)(vec31.dotProduct(vec3) + 0.5D) / 1.5F;

				if (f4 < 0.0F)
					f4 = 0.0F;

				randomYawVelocity *= 0.8F;
				float f5 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 1.0F + 1.0F;
				double d10 = Math.sqrt(motionX * motionX + motionZ * motionZ) * 1.0D + 1.0D;

				if (d10 > 40.0D)
					d10 = 40.0D;

				randomYawVelocity = (float)(randomYawVelocity + d9 * (0.699999988079071D / d10 / f5));
				rotationYaw += randomYawVelocity * 0.1F;
				float f6 = (float)(2.0D / (d10 + 1.0D));
				float f7 = 0.06F;
				moveFlying(0.0F, -1.0F, f7 * (f4 * f6 + (1.0F - f6)));


				moveEntity(motionX, motionY, motionZ);


				Vec3 vec32 = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();
				float f8 = (float)(vec32.dotProduct(vec31) + 1.0D) / 2.0F;
				f8 = 0.8F + 0.15F * f8;
				motionX *= f8;
				motionZ *= f8;
				motionY *= 0.9100000262260437D;
			}

			renderYawOffset = rotationYaw;
			dragonPartHead.width = dragonPartHead.height = 3.0F;
			dragonPartTail1.width = dragonPartTail1.height = 2.0F;
			dragonPartTail2.width = dragonPartTail2.height = 2.0F;
			dragonPartTail3.width = dragonPartTail3.height = 2.0F;
			dragonPartBody.height = 3.0F;
			dragonPartBody.width = 5.0F;
			dragonPartWing1.height = 2.0F;
			dragonPartWing1.width = 4.0F;
			dragonPartWing2.height = 3.0F;
			dragonPartWing2.width = 4.0F;
			f1 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * (float)Math.PI;
			f2 = MathHelper.cos(f1);
			float f9 = -MathHelper.sin(f1);
			float f10 = rotationYaw * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f10);
			float f12 = MathHelper.cos(f10);
			dragonPartBody.onUpdate();
			dragonPartBody.setLocationAndAngles(posX + f11 * 0.5F, posY, posZ - f12 * 0.5F, 0.0F, 0.0F);
			dragonPartWing1.onUpdate();
			dragonPartWing1.setLocationAndAngles(posX + f12 * 4.5F, posY + 2.0D, posZ + f11 * 4.5F, 0.0F, 0.0F);
			dragonPartWing2.onUpdate();
			dragonPartWing2.setLocationAndAngles(posX - f12 * 4.5F, posY + 2.0D, posZ - f11 * 4.5F, 0.0F, 0.0F);

			if (!worldObj.isRemote && hurtTime == 0)
			{
				collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing1.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing2.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				attackEntitiesInList(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartHead.boundingBox.expand(1.0D, 1.0D, 1.0D)));
			}

			double[] adouble = getMovementOffsets(5, 1.0F);
			double[] adouble1 = getMovementOffsets(0, 1.0F);
			f3 = MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F - randomYawVelocity * 0.01F);
			float f13 = MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F - randomYawVelocity * 0.01F);
			dragonPartHead.onUpdate();
			dragonPartHead.setLocationAndAngles(posX + f3 * 5.5F * f2, posY + (adouble1[1] - adouble[1]) * 1.0D + f9 * 5.5F, posZ - f13 * 5.5F * f2, 0.0F, 0.0F);

			for (int j = 0; j < 3; ++j)
			{
				EntityDragonPart entitydragonpart = null;

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
				float f17 = 1.5F;
				float f18 = (j + 1) * 2.0F;
				entitydragonpart.onUpdate();
				entitydragonpart.setLocationAndAngles(posX - (f11 * f17 + f15 * f18) * f2, posY + (adouble2[1] - adouble[1]) * 1.0D - (f18 + f17) * f9 + 1.5D, posZ + (f12 * f17 + f16 * f18) * f2, 0.0F, 0.0F);
			}
		}
	}

	private void updateHealingCircle()
	{
		if (healingcircle != null)
			if (healingcircle.isDead)
			{
				if (!worldObj.isRemote)
					attackEntityFromPart(dragonPartHead, DamageSource.setExplosionSource((Explosion)null), 10.0F);

				healingcircle = null;
			}
			else if (ticksExisted % 10 == 0 && getHealth() < getMaxHealth())
				setHealth(getHealth() + 1.0F);
			else if (ticksExisted % 10 == 0 && getHealth() < getMaxHealth()/2)
				setHealth(getHealth() - 1.0F);
			else if (ticksExisted % 10 == 0 && getHealth() < getMaxHealth()/4)
			{
				setHealth(getHealth() + 1.0F);
				healingcircle.worldObj.createExplosion(healingcircle, healingcircle.lastTickPosX, healingcircle.lastTickPosY, healingcircle.lastTickPosZ, 10F, true);
			}

		if (rand.nextInt(10) == 0)
		{
			float f = 32.0F;
			List<?> list = worldObj.getEntitiesWithinAABB(EntityDragonMinion.class, boundingBox.expand(f, f, f));
			EntityDragonMinion entitydragonminion = null;
			double d0 = Double.MAX_VALUE;
			Iterator<?> iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityDragonMinion entitydragonminion1 = (EntityDragonMinion)iterator.next();
				double d1 = entitydragonminion1.getDistanceSqToEntity(this);

				if (d1 < d0)
				{
					d0 = d1;
					entitydragonminion = entitydragonminion1;
				}
			}

			healingcircle = entitydragonminion;
		}
	}

	private void collideWithEntities(List<?> par1List)
	{
		double d0 = (dragonPartBody.boundingBox.minX + dragonPartBody.boundingBox.maxX) / 2.0D;
		double d1 = (dragonPartBody.boundingBox.minZ + dragonPartBody.boundingBox.maxZ) / 2.0D;
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

			if (entity instanceof EntityLivingBase)
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
		}
	}

	private void setNewTarget()
	{
		forceNewTarget = false;

		if (rand.nextInt(2) == 0 && !worldObj.playerEntities.isEmpty())
			target = (Entity)worldObj.playerEntities.get(rand.nextInt(worldObj.playerEntities.size()));
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
		return (float)MathHelper.wrapAngleTo180_double(par1);
	}

	@Override
	public boolean attackEntityFromPart(EntityDragonPart par1EntityDragonPart, DamageSource par2DamageSource, float par3)
	{
		if (par1EntityDragonPart != dragonPartHead)
			par3 = par3 / 4.0F + 1.0F;

		float f1 = rotationYaw * (float)Math.PI / 180.0F;
		float f2 = MathHelper.sin(f1);
		float f3 = MathHelper.cos(f1);
		targetX = posX + f2 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		targetY = posY + rand.nextFloat() * 3.0F + 1.0D;
		targetZ = posZ - f3 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		target = null;

		if (par2DamageSource.getEntity() instanceof EntityPlayer || par2DamageSource.isExplosion())
			func_82195_e(par2DamageSource, par3);

		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		return false;
	}

	protected boolean func_82195_e(DamageSource par1DamageSource, float par2)
	{
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	protected void onDeathUpdate()
	{
		++deathTicks;

		if (deathTicks >= 180 && deathTicks <= 200)
		{
			float f = (rand.nextFloat() - 0.5F) * 8.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		int i;
		int j;

		if (!worldObj.isRemote)
		{
			if (deathTicks > 150 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
					if(deathTicks == 100 || deathTicks == 120 || deathTicks == 140 || deathTicks == 160 || deathTicks == 180){
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.Cchunk)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.Cingot)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.Corflesh)));
					}
				}
			}

			if (deathTicks == 1)
				worldObj.playBroadcastSound(1018, (int)posX, (int)posY, (int)posZ, 0);
		}

		moveEntity(0.0D, 0.10000000149011612D, 0.0D);
		renderYawOffset = rotationYaw += 20.0F;

		if (deathTicks == 200 && !worldObj.isRemote)
		{
			i = 1000;

			while (i > 0)
			{
				j = EntityXPOrb.getXPSplit(i);
				i -= j;
				worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
			}

			setDead();
		}
	}

	@Override
	public Entity[] getParts()
	{
		return dragonPartArray;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public World func_82194_d()
	{
		return worldObj;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.enderdragon.growl";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.enderdragon.hit";
	}

	@Override
	protected float getSoundVolume()
	{
		return 5.0F;
	}
}
