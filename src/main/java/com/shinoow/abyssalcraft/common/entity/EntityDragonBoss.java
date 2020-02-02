/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;

@Interface(iface = "com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues", modid = "iceandfire")
public class EntityDragonBoss extends EntityMob implements IEntityMultiPart, ICoraliumEntity, com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues
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
	public int deathTicks;

	private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);

	public EntityDragonMinion healingcircle;

	public EntityDragonBoss(World par1World) {
		super(par1World);
		dragonPartArray = new MultiPartEntityPart[] {dragonPartHead = new MultiPartEntityPart(this, "head", 9.0F, 9.0F), dragonPartBody = new MultiPartEntityPart(this, "body", 12.0F, 12.0F), dragonPartTail1 = new MultiPartEntityPart(this, "tail", 6.0F, 6.0F), dragonPartTail2 = new MultiPartEntityPart(this, "tail", 6.0F, 6.0F), dragonPartTail3 = new MultiPartEntityPart(this, "tail", 6.0F, 6.0F), dragonPartWing1 = new MultiPartEntityPart(this, "wing", 6.0F, 6.0F), dragonPartWing2 = new MultiPartEntityPart(this, "wing", 6.0F, 6.0F)};
		setHealth(getMaxHealth());
		setSize(24.0F, 12.0F);
		noClip = false;
		targetY = 100.0D;
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
	}

	@Override
	public String getName()
	{
		return TextFormatting.AQUA + super.getName();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 800.0D : 400.0D);
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
	protected void despawnEntity() {}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		bossInfo.setPercent(getHealth() / getMaxHealth());
		//		if (par1DamageSource.getTrueSource() instanceof EntityPlayer)
		//		{
		//			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getTrueSource();
		////			entityplayer.addStat(ACAchievements.kill_asorah, 1);
		//		}
		super.onDeath(par1DamageSource);
	}

	/**
	 * Makes this boss Entity visible to the given player. Has no effect if this Entity is not a boss.
	 */
	@Override
	public void addTrackingPlayer(EntityPlayerMP player)
	{
		super.addTrackingPlayer(player);
		bossInfo.addPlayer(player);
	}

	/**
	 * Makes this boss Entity non-visible to the given player. Has no effect if this Entity is not a boss.
	 */
	@Override
	public void removeTrackingPlayer(EntityPlayerMP player)
	{
		super.removeTrackingPlayer(player);
		bossInfo.removePlayer(player);
	}

	@Override
	public void onLivingUpdate()
	{

		float f;
		float f1;

		if (world.isRemote)
		{
			//			BossStatus.setBossStatus(this, true);
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

			for (int i = 0; i < 2; ++i)
				if(ACConfig.particleEntity)
					AbyssalCraft.proxy.spawnParticle("CorBlood", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - 0.25D, posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);

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

				if (forceNewTarget || d2 < 100.0D || d2 > 22500.0D || collidedHorizontally || collidedVertically)
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
				float f8 = (float)(vec32.dotProduct(vec31) + 1.0D) / 2.0F;
				f8 = 0.8F + 0.15F * f8;
				motionX *= f8;
				motionZ *= f8;
				motionY *= 0.9100000262260437D;
			}

			renderYawOffset = rotationYaw;
			dragonPartHead.width = dragonPartHead.height = 4.5F;
			dragonPartTail1.width = dragonPartTail1.height = 3.0F;
			dragonPartTail2.width = dragonPartTail2.height = 3.0F;
			dragonPartTail3.width = dragonPartTail3.height = 3.0F;
			dragonPartBody.height = 4.5F;
			dragonPartBody.width = 7.5F;
			dragonPartWing1.height = 3.0F;
			dragonPartWing1.width = 6.0F;
			dragonPartWing2.height = 3.0F;
			dragonPartWing2.width = 6.0F;
			f1 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * (float)Math.PI;
			f2 = MathHelper.cos(f1);
			float f9 = -MathHelper.sin(f1);
			float f10 = rotationYaw * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f10);
			float f12 = MathHelper.cos(f10);
			dragonPartBody.onUpdate();
			dragonPartBody.setLocationAndAngles(posX + f11 * 1.5F, posY, posZ - f12 * 1.5F, 0.0F, 0.0F);
			dragonPartWing1.onUpdate();
			dragonPartWing1.setLocationAndAngles(posX + f12 * 6.5F, posY + 2.0D, posZ + f11 * 6.5F, 0.0F, 0.0F);
			dragonPartWing2.onUpdate();
			dragonPartWing2.setLocationAndAngles(posX - f12 * 6.5F, posY + 2.0D, posZ - f11 * 6.5F, 0.0F, 0.0F);

			if (!world.isRemote && hurtTime == 0)
			{
				collideWithEntities(world.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing1.getEntityBoundingBox().grow(5.0D, 4.0D, 5.0D).offset(0.0D, -4.0D, 0.0D)));
				collideWithEntities(world.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing2.getEntityBoundingBox().grow(5.0D, 4.0D, 5.0D).offset(0.0D, -4.0D, 0.0D)));
				attackEntitiesInList(world.getEntitiesWithinAABBExcludingEntity(this, dragonPartHead.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D)));
			}

			double[] adouble = getMovementOffsets(5, 1.0F);
			double[] adouble1 = getMovementOffsets(0, 1.0F);
			f3 = MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F - randomYawVelocity * 0.01F);
			float f13 = MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F - randomYawVelocity * 0.01F);
			dragonPartHead.onUpdate();
			dragonPartHead.setLocationAndAngles(posX + f3 * 9.2F * f2, posY + (adouble1[1] - adouble[1]) * 1.0D + f9 * 9.2F, posZ - f13 * 9.2F * f2, 0.0F, 0.0F);

			for (int j = 0; j < 3; ++j)
			{
				MultiPartEntityPart MultiPartEntityPart = null;

				if (j == 0)
					MultiPartEntityPart = dragonPartTail1;

				if (j == 1)
					MultiPartEntityPart = dragonPartTail2;

				if (j == 2)
					MultiPartEntityPart = dragonPartTail3;

				double[] adouble2 = getMovementOffsets(12 + j * 2, 1.0F);
				float f14 = rotationYaw * (float)Math.PI / 180.0F + simplifyAngle(adouble2[0] - adouble[0]) * (float)Math.PI / 180.0F * 1.0F;
				float f15 = MathHelper.sin(f14);
				float f16 = MathHelper.cos(f14);
				float f17 = 1.5F;
				float f18 = (j + 1) * 3.0F;
				MultiPartEntityPart.onUpdate();
				MultiPartEntityPart.setLocationAndAngles(posX - (f11 * f17 + f15 * f18) * f2, posY + (adouble2[1] - adouble[1]) * 1.0D - (f18 + f17) * f9 + 1.5D, posZ + (f12 * f17 + f16 * f18) * f2, 0.0F, 0.0F);
			}
		}

		bossInfo.setPercent(getHealth() / getMaxHealth());
		if(getHealth() > getMaxHealth() * 0.75 && bossInfo.getColor() != BossInfo.Color.BLUE)
			bossInfo.setColor(Color.BLUE);
		if(getHealth() < getMaxHealth() * 0.75 && getHealth() > getMaxHealth() / 2 && bossInfo.getColor() != BossInfo.Color.GREEN)
			bossInfo.setColor(Color.GREEN);
		if(getHealth() < getMaxHealth() / 2 && getHealth() > getMaxHealth() / 4 && bossInfo.getColor() != BossInfo.Color.YELLOW)
			bossInfo.setColor(Color.YELLOW);
		if(getHealth() < getMaxHealth() / 4 && getHealth() > 0 && bossInfo.getColor() != BossInfo.Color.RED)
			bossInfo.setColor(Color.RED);
	}

	@Override
	public void addPotionEffect(PotionEffect potioneffectIn){}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if(deathTicks > 0)
			par1NBTTagCompound.setInteger("DeathTicks", deathTicks);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		deathTicks = par1NBTTagCompound.getInteger("DeathTicks");
	}

	private void updateHealingCircle()
	{
		if (healingcircle != null)
			if (healingcircle.isDead)
				healingcircle = null;
			else if (ticksExisted % 10 == 0 && getHealth() < getMaxHealth())
				setHealth(getHealth() + 1.0F);
			else if (ticksExisted % 10 == 0 && getHealth() < getMaxHealth()/2)
				setHealth(getHealth() - 1.0F);
			else if (ticksExisted % 10 == 0 && getHealth() < getMaxHealth()/4)
			{
				setHealth(getHealth() + 1.0F);
				healingcircle.world.createExplosion(healingcircle, healingcircle.lastTickPosX, healingcircle.lastTickPosY, healingcircle.lastTickPosZ, 10F, true);
			}

		if (rand.nextInt(10) == 0)
		{
			float f = 32.0F;
			List<?> list = world.getEntitiesWithinAABB(EntityDragonMinion.class, getEntityBoundingBox().grow(f, f, f));
			EntityDragonMinion entitydragonminion = null;
			double d0 = Double.MAX_VALUE;
			Iterator<?> iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityDragonMinion entitydragonminion1 = (EntityDragonMinion)iterator.next();
				double d1 = entitydragonminion1.getDistanceSq(this);

				if (d1 < d0)
				{
					d0 = d1;
					entitydragonminion = entitydragonminion1;
				}
			}

			healingcircle = entitydragonminion;
		}
	}

	@Override
	public boolean isNonBoss(){
		return false;
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

			if (entity instanceof EntityLivingBase)
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);

			if(ACConfig.hardcoreMode && entity instanceof EntityPlayer)
				entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1 * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));
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
	public boolean attackEntityFromPart(MultiPartEntityPart par1MultiPartEntityPart, DamageSource par2DamageSource, float par3)
	{
		if (par1MultiPartEntityPart != dragonPartHead)
			par3 = par3 / 4.0F + 1.0F;

		if(par3 > 30) par3 = 10 + world.rand.nextInt(10);

		float f1 = rotationYaw * (float)Math.PI / 180.0F;
		float f2 = MathHelper.sin(f1);
		float f3 = MathHelper.cos(f1);
		targetX = posX + f2 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		targetY = posY + rand.nextFloat() * 3.0F + 1.0D;
		targetZ = posZ - f3 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		target = null;

		if (par2DamageSource.getTrueSource() instanceof EntityPlayer || par2DamageSource.isExplosion())
			func_82195_e(par2DamageSource, par3);

		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par1DamageSource == DamageSource.OUT_OF_WORLD && posY <= 0 && !getEntityWorld().isRemote)
			getEntityWorld().removeEntity(this);

		return false;
	}

	protected boolean func_82195_e(DamageSource par1DamageSource, float par2)
	{
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public void onKillCommand()
	{
		func_82195_e(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
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
			if(ACConfig.particleEntity)
				world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		int i;
		int j;

		if (!world.isRemote)
		{
			if (deathTicks > 150 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					world.spawnEntity(new EntityXPOrb(world, posX, posY, posZ, j));
					if(deathTicks == 100 || deathTicks == 120 || deathTicks == 140 || deathTicks == 160 || deathTicks == 180){
						world.spawnEntity(new EntityItem(world, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.chunk_of_coralium)));
						world.spawnEntity(new EntityItem(world, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.refined_coralium_ingot)));
						world.spawnEntity(new EntityItem(world, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.coralium_plagued_flesh)));
					}
				}
			}

			if (deathTicks == 1)
				world.playBroadcastSound(1018, new BlockPos(posX, posY, posZ), 0);
		}

		move(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
		renderYawOffset = rotationYaw += 20.0F;

		if(ACConfig.showBossDialogs) {
			if(deathTicks == 20 && !world.isRemote)
				SpecialTextUtil.OblivionaireGroup(world, I18n.translateToLocal("message.asorah.death.1"));
			if(deathTicks == 80 && !world.isRemote)
				SpecialTextUtil.OblivionaireGroup(world, I18n.translateToLocal("message.asorah.death.2"));
			if(deathTicks == 140 && !world.isRemote)
				SpecialTextUtil.OblivionaireGroup(world, I18n.translateToLocal("message.asorah.death.3"));
		}
		if (deathTicks == 200 && !world.isRemote){
			setDead();
			world.spawnEntity(new EntityItem(world, posX, posY, posZ, new ItemStack(ACItems.eye_of_the_abyss)));
		}
	}

	private int posneg(int num){
		return rand.nextBoolean() ? rand.nextInt(num) : -1 * rand.nextInt(num);
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

	@Override
	protected ResourceLocation getLootTable()
	{
		return ACLoot.ENTITY_ASORAH;
	}

	@Override
	protected float getSoundVolume()
	{
		return 5.0F;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public boolean canBeTurnedToStone() {

		return false;
	}
}
