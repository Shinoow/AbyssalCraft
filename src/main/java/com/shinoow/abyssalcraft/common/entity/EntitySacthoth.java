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

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.*;
import net.minecraft.world.BossInfo.Color;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySacthoth extends EntityMob implements IOmotholEntity {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntitySacthoth.class, DataSerializers.BYTE);
	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8D, 0);
	public int deathTicks, shadowFlameShootTimer;
	private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);

	public EntitySacthoth(World par1World) {
		super(par1World);
		setSize(1.2F, 3.8F);
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.35D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
	}

	@Override
	protected PathNavigate getNewNavigator(World worldIn)
	{
		return new PathNavigateClimber(this, worldIn);
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(CLIMBING, new Byte((byte)0));
	}

	@Override
	public String getName()
	{
		return TextFormatting.DARK_RED + super.getName();
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!worldObj.isRemote)
			setBesideClimbableBlock(isCollidedHorizontally);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(160.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.799D);

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(600.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(30.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
		}
	}

	@Override
	protected void despawnEntity() {}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
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
	public boolean isNonBoss(){
		return false;
	}

	@Override
	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		boolean flag = super.attackEntityAsMob(par1Entity);

		if(flag)
			if(par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 60));

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 4.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_BLAZE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.sacthoth_death;
	}

	@Override
	protected float getSoundVolume()
	{
		return 5.0F;
	}

	@Override
	public int getTotalArmorValue()
	{
		return 20;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return AbyssalCraftAPI.SHADOW;
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	 * setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock()
	{
		return (dataManager.get(CLIMBING) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1)
	{
		byte b0 = dataManager.get(CLIMBING);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataManager.set(CLIMBING, Byte.valueOf(b0));
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par2 > 30) par2 = 10 + worldObj.rand.nextInt(10);

		if(par1DamageSource == DamageSource.inWall){
			teleportRandomly();
			return false;
		}
		else if(par1DamageSource.isExplosion()){
			if(worldObj.isRemote)
				SpecialTextUtil.SacthothText(I18n.translateToLocal("message.sacthoth.damage.explosion"));
			return false;
		}
		else if(par1DamageSource.isProjectile()){
			if(worldObj.isRemote)
				SpecialTextUtil.SacthothText(I18n.translateToLocal("message.sacthoth.damage.projectile"));
			return false;
		}
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	protected boolean teleportRandomly()
	{
		double d0 = posX + (rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = posY + (rand.nextInt(64) - 32);
		double d2 = posZ + (rand.nextDouble() - 0.5D) * 64.0D;
		return teleportTo(d0, d1, d2);
	}

	protected boolean teleportTo(double par1, double par3, double par5)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(this, par1, par3, par5, 0);
		if (MinecraftForge.EVENT_BUS.post(event))
			return false;
		double d3 = posX;
		double d4 = posY;
		double d5 = posZ;
		posX = event.getTargetX();
		posY = event.getTargetY();
		posZ = event.getTargetZ();
		boolean flag = false;
		BlockPos pos = new BlockPos(posX, posY, posZ);

		if (worldObj.isBlockLoaded(pos))
		{
			boolean flag1 = false;

			while (!flag1 && pos.getY() > 0)
			{
				BlockPos pos1 = pos.down();
				IBlockState block = worldObj.getBlockState(pos1);

				if (block.getMaterial().blocksMovement())
					flag1 = true;
				else
				{
					--posY;
					pos = pos1;
				}
			}

			if (flag1)
			{
				setPosition(posX, posY, posZ);

				if (worldObj.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && !worldObj.containsAnyLiquid(getEntityBoundingBox()))
					flag = true;
			}
		}

		if (!flag)
		{
			setPosition(d3, d4, d5);
			return false;
		}
		else
		{
			short short1 = 128;

			for (int l = 0; l < short1; ++l)
			{
				double d6 = l / (short1 - 1.0D);
				float f = (rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = d3 + (posX - d3) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				double d8 = d4 + (posY - d4) * d6 + rand.nextDouble() * height;
				double d9 = d5 + (posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				if(ACConfig.particleEntity)
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d8, d9, f, f1, f2);
			}

			worldObj.playSound(d3, d4, d5, SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F, false);
			playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
			return true;
		}
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		bossInfo.setPercent(getHealth() / getMaxHealth());
		super.onDeath(par1DamageSource);
	}

	@Override
	protected void onDeathUpdate()
	{
		++deathTicks;

		if (deathTicks <= 200)
		{
			float f = (rand.nextFloat() - 0.5F) * 8.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 8.0F;
			if(ACConfig.particleEntity){
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				if (deathTicks >= 190 && deathTicks <= 200)
					worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			}
		}

		int i;
		int j;

		if (!worldObj.isRemote){
			if (deathTicks > 150 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
					if(deathTicks == 100 || deathTicks == 120 || deathTicks == 140 || deathTicks == 160 || deathTicks == 180){
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.shadow_fragment, 4)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.shadow_shard, 2)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.shadow_gem)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.shard_of_oblivion)));
					}
				}
			}
			if(deathTicks >= 100 && deathTicks <= 200){
				if(deathTicks <= 110){
					EntityShadowCreature shadowCreature = new EntityShadowCreature(worldObj);
					shadowCreature.copyLocationAndAnglesFrom(this);
					worldObj.spawnEntityInWorld(shadowCreature);
				}
				if(deathTicks >= 160 && deathTicks <= 165){
					EntityShadowMonster shadowMonster = new EntityShadowMonster(worldObj);
					shadowMonster.copyLocationAndAnglesFrom(this);
					worldObj.spawnEntityInWorld(shadowMonster);
				}
				if(deathTicks == 200){
					EntityShadowBeast shadowBeast = new EntityShadowBeast(worldObj);
					shadowBeast.copyLocationAndAnglesFrom(this);
					worldObj.spawnEntityInWorld(shadowBeast);
				}
			}
			if(deathTicks == 200 && !worldObj.isRemote){
				setDead();
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(ACItems.sacthoths_soul_harvesting_blade)));
			}
		}
	}

	private int posneg(int num){
		return rand.nextBoolean() ? rand.nextInt(num) : -1 * rand.nextInt(num);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(deathTicks == 0) par1Entity.applyEntityCollision(this);
	}

	@Override
	public void onLivingUpdate()
	{
		for (int i = 0; i < 2 * getBrightness(1.0f) && ACConfig.particleEntity && worldObj.provider.getDimension() != ACLib.dark_realm_id; ++i)
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);

		for(Entity entity : worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(30.0D, 30.0D, 30.0D)))
			if (entity instanceof EntityPlayer && !entity.isDead && deathTicks == 0 && !((EntityPlayer)entity).capabilities.isCreativeMode)
				((EntityPlayer)entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40));
		EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 160D);
		if(player != null && player.getDistanceToEntity(this) >= 50D && !player.capabilities.isCreativeMode){
			if(player.posX - posX > 50)
				teleportTo(player.posX + 30, player.posY, player.posZ);
			if(player.posX - posX < -50)
				teleportTo(player.posX - 30, player.posY, player.posZ);
			if(player.posZ - posZ > 50)
				teleportTo(player.posX, player.posY, player.posZ - 30);
			if(player.posZ - posZ < -50)
				teleportTo(player.posX, player.posY, player.posZ + 30);
			if(player.posY - posY > 50)
				teleportTo(player.posX, player.posY, player.posZ);
			if(player.posY - posY < -50)
				teleportTo(player.posX, player.posY, player.posZ);
		}

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
				List<EntityLivingBase> list1 = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().expand(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));

				for(EntityLivingBase entity : list1)

					if (entity != null && rand.nextInt(3) == 0) if (entity.attackEntityFrom(AbyssalCraftAPI.shadow, (float)(7.5D - getDistanceToEntity(entity)))) {
						entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
					}


				if (target.attackEntityFrom(AbyssalCraftAPI.shadow, (float)(7.5D - getDistanceToEntity(target)))) if(target instanceof EntityLivingBase)
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


			for (int i = 0; i < 45; i++)
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

		if(deathTicks > 0)
			par1NBTTagCompound.setInteger("DeathTicks", deathTicks);
		par1NBTTagCompound.setInteger("BreathTimer", shadowFlameShootTimer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		deathTicks = par1NBTTagCompound.getInteger("DeathTicks");
		shadowFlameShootTimer = par1NBTTagCompound.getInteger("BreathTimer");
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		if(worldObj.isDaytime())
			worldObj.setWorldTime(14000L);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = worldObj.getCurrentDate();

		attribute.removeModifier(attackDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}
}
