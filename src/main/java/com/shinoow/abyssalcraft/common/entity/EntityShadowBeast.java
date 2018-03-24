/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import java.util.Calendar;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShadowBeast extends EntityMob implements IOmotholEntity {

	private int shadowFlameShootTimer;

	public EntityShadowBeast(World par1World) {
		super(par1World);
		setSize(1.0F, 2.8F);
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.35D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3 * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return ACSounds.shadow_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.shadow_death;
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.shadow_gem;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_SHADOW_BEAST;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return AbyssalCraftAPI.SHADOW;
	}

	@Override
	public void onLivingUpdate()
	{
		for (int i = 0; i < 2 * getBrightness(1.0f) && ACConfig.particleEntity && worldObj.provider.getDimension() != ACLib.dark_realm_id; ++i)
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);

		if (getAttackTarget() != null && getDistanceSqToEntity(getAttackTarget()) <= 64D && shadowFlameShootTimer <= -300) shadowFlameShootTimer = 100;

		if (shadowFlameShootTimer > 0)
		{
			motionX *= 0.05D;
			motionZ *= 0.05D;
			worldObj.setEntityState(this, (byte)23);
			if (ticksExisted % 5 == 0)
				worldObj.playSound(null, new BlockPos(posX + 0.5D, posY + getEyeHeight(), posZ + 0.5D), SoundEvents.ENTITY_GHAST_SHOOT, getSoundCategory(), 0.5F + getRNG().nextFloat(), getRNG().nextFloat() * 0.7F + 0.3F);
			Entity target = getHeadLookTarget();
			if (target != null) {
				List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().expand(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));

				for(EntityLivingBase entity : list)
					if (entity != null && rand.nextInt(3) == 0) if (entity.attackEntityFrom(AbyssalCraftAPI.shadow, (float)(4.5D - getDistanceToEntity(entity)))) {
						entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
					}

				if (target.attackEntityFrom(AbyssalCraftAPI.shadow, (float)(4.5D - getDistanceToEntity(target)))) if(target instanceof EntityLivingBase)
				{
					((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
					((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
				}
			}
		}

		--shadowFlameShootTimer;

		super.onLivingUpdate();
	}

	private Entity getHeadLookTarget()
	{
		Entity pointedEntity = null;
		double range = 4D + rand.nextDouble() * 8D;
		Vec3d srcVec = new Vec3d(posX, posY + getEyeHeight(), posZ);
		Vec3d lookVec = getLook(1.0F);
		RayTraceResult raytrace = worldObj.rayTraceBlocks(srcVec, srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range));
		BlockPos hitpos = raytrace != null ? raytrace.getBlockPos() : null;
		double rx = hitpos == null ? range : Math.min(range, Math.abs(posX - hitpos.getX()));
		double ry = hitpos == null ? range : Math.min(range, Math.abs(posY - hitpos.getY()));
		double rz = hitpos == null ? range : Math.min(range, Math.abs(posZ - hitpos.getZ()));
		Vec3d destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
		float var9 = 4.0F;
		List<Entity> possibleList = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().offset(lookVec.xCoord * rx, lookVec.yCoord * ry, lookVec.zCoord * rz).expand(var9, var9, var9));
		double hitDist = 0.0D;
		for (Entity possibleEntity : possibleList)
			if (possibleEntity != this && possibleEntity instanceof EntityLivingBase)
			{
				float borderSize = possibleEntity.getCollisionBorderSize();
				AxisAlignedBB collisionBB = possibleEntity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
				RayTraceResult interceptPos = collisionBB.calculateIntercept(srcVec, destVec);
				if (collisionBB.isVecInside(srcVec))
				{
					if (0.0D < hitDist || hitDist == 0.0D)
					{
						pointedEntity = possibleEntity;
						hitDist = 0.0D;
					}
				}
				else if (interceptPos != null)
				{
					double possibleDist = srcVec.distanceTo(interceptPos.hitVec);
					if (possibleDist < hitDist || hitDist == 0.0D)
					{
						pointedEntity = possibleEntity;
						hitDist = possibleDist;
					}
				}
			}
		return pointedEntity;
	}

	protected void addMouthParticles()
	{
		if (worldObj.isRemote)
		{
			Vec3d vector = getLookVec();

			double px = posX;
			double py = posY + getEyeHeight();
			double pz = posZ;


			for (int i = 0; i < 15; i++)
			{
				double dx = vector.xCoord;
				double dy = vector.yCoord;
				double dz = vector.zCoord;

				double spread = 5.0D + getRNG().nextDouble() * 2.5D;
				double velocity = 0.5D + getRNG().nextDouble() * 0.5D;

				dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dx *= velocity;
				dy *= velocity;
				dz *= velocity;

				worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz);
			}
		} else
			worldObj.setEntityState(this, (byte)23);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 23) addMouthParticles();
		else
			super.handleStatusUpdate(id);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setInteger("BreathTimer", shadowFlameShootTimer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		shadowFlameShootTimer = par1NBTTagCompound.getInteger("BreathTimer");
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		if (getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
		{
			Calendar calendar = worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
				inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return par1EntityLivingData;
	}
}
