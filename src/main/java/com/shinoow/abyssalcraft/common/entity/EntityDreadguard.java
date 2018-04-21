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
import java.util.UUID;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

public class EntityDreadguard extends EntityMob implements IDreadEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);

	private int flameShootTimer;

	public EntityDreadguard(World par1World) {
		super(par1World);
		setSize(1.0F, 3.0F);
		isImmuneToFire = true;
		tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(240.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		}
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		if (par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(ACAchievements.kill_dreadguard, 1);
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag)
			if (par1Entity instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase) par1Entity))
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3 * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20)
			var1 = 20;

		return var1;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ACSounds.dreadguard_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return ACSounds.dreadguard_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.dreadguard_death;
	}

	protected void playStepSound(BlockPos pos, int par4)
	{
		playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_DREADGUARD;
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.dreaded_shard_of_abyssalnite;
	}

	@Override
	public void onLivingUpdate()
	{

		if (getAttackTarget() != null && getDistanceSqToEntity(getAttackTarget()) <= 64D && flameShootTimer <= -200) flameShootTimer = 60;

		if (flameShootTimer > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
			setAIMoveSpeed(0F);
			worldObj.setEntityState(this, (byte)23);
			if (ticksExisted % 5 == 0 && flameShootTimer > 30)
				worldObj.playSound(null, new BlockPos(posX + 0.5D, posY + getEyeHeight(), posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 0.7F + getRNG().nextFloat(), getRNG().nextFloat() * 0.5F + 0.2F);
			Entity target = getHeadLookTarget();
			if (target != null) {
				for(EntityLivingBase entity : worldObj.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().expand(2.0D, 2.0D, 2.0D), EntitySelectors.IS_ALIVE))
					if (rand.nextInt(3) == 0)
						if (entity.attackEntityFrom(AbyssalCraftAPI.dread, (float)(4.5D - getDistanceToEntity(entity))))
							entity.addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));

				if (target.attackEntityFrom(AbyssalCraftAPI.dread, (float)(4.5D - getDistanceToEntity(target))))
					if (target instanceof EntityLivingBase)
						((EntityLivingBase) target).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
			}
		}

		--flameShootTimer;

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

				worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz, Item.getIdFromItem(ACItems.dreaded_shard_of_abyssalnite));
				worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz, Item.getIdFromItem(ACItems.dread_fragment));
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

		par1NBTTagCompound.setInteger("BarfTimer", flameShootTimer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		flameShootTimer = par1NBTTagCompound.getInteger("BarfTimer");
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ACItems.dreaded_abyssalnite_helmet));
		setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ACItems.dreaded_abyssalnite_chestplate));
		setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ACItems.dreaded_abyssalnite_leggings));
		setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ACItems.dreaded_abyssalnite_boots));

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = worldObj.getCurrentDate();

		attribute.removeModifier(attackDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31){
			attribute.applyModifier(attackDamageBoost);
			if(rand.nextFloat() < 0.25F){
				setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
				inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return par1EntityLivingData;
	}
}
