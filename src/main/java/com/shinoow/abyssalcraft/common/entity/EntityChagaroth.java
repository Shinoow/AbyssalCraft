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
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.*;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues", modid = "iceandfire")
public class EntityChagaroth extends EntityMob implements IDreadEntity, com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8D, 0);
	public int deathTicks, flameShootTimer;
	private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);
	private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.<Integer>createKey(EntityChagaroth.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.<Integer>createKey(EntityChagaroth.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.<Integer>createKey(EntityChagaroth.class, DataSerializers.VARINT);
	private static final DataParameter<Integer>[] HEAD_TARGETS = new DataParameter[] {FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET};
	private final float[] xRotationHeads = new float[2];
	private final float[] yRotationHeads = new float[2];
	private final float[] xRotOHeads = new float[2];
	private final float[] yRotOHeads = new float[2];
	private final int[] nextHeadUpdate = new int[2];
	private final int[] idleHeadUpdates = new int[2];

	public EntityChagaroth(World par1World) {
		super(par1World);
		setSize(2.0F, 4.8F);
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true, false, entity -> !(entity instanceof IDreadEntity)));
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
	}

	@Override
	public String getName()
	{
		return TextFormatting.DARK_RED + super.getName();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox()
	{
		return getEntityBoundingBox();
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(FIRST_HEAD_TARGET, Integer.valueOf(0));
		dataManager.register(SECOND_HEAD_TARGET, Integer.valueOf(0));
		dataManager.register(THIRD_HEAD_TARGET, Integer.valueOf(0));
	}

	public int getWatchedTargetId(int head)
	{
		return dataManager.get(HEAD_TARGETS[head]).intValue();
	}

	/**
	 * Updates the target entity ID
	 */
	public void updateWatchedTargetId(int targetOffset, int newId)
	{
		dataManager.set(HEAD_TARGETS[targetOffset], Integer.valueOf(newId));
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		boolean flag = super.attackEntityAsMob(par1Entity);

		if(flag)
			if(par1Entity instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase) par1Entity))
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 4.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));
		return flag;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2000.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(30.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
		}
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ACSounds.dreadguard_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return ACSounds.dreadguard_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.dreadguard_death;
	}

	@Override
	protected float getSoundVolume()
	{
		return 5.0F;
	}

	@Override
	public int getTotalArmorValue()
	{
		return 10;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

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

		for (int i = 1; i < 3; ++i)
			if (ticksExisted >= nextHeadUpdate[i - 1])
			{
				nextHeadUpdate[i - 1] = ticksExisted + 10 + rand.nextInt(10);

				int k1 = getWatchedTargetId(i);

				if (k1 > 0)
				{
					Entity entity = world.getEntityByID(k1);

					if (entity != null && entity.isEntityAlive() && getDistanceSq(entity) <= 48D * 48D && canEntityBeSeen(entity))
					{
						if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage)
							updateWatchedTargetId(i, 0);
						else
						{
							launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
							idleHeadUpdates[i - 1] = 0;
						}
					} else
						updateWatchedTargetId(i, 0);
				}
				else
				{
					List<EntityLivingBase> list = world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(48D), Predicates.<EntityLivingBase>and(EntitySelectors.NOT_SPECTATING, entity -> !EntityUtil.isEntityDread(entity)));

					for (int j2 = 0; j2 < 10 && !list.isEmpty(); ++j2)
					{
						EntityLivingBase entitylivingbase = list.get(rand.nextInt(list.size()));

						if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen(entitylivingbase))
						{
							if (entitylivingbase instanceof EntityPlayer)
							{
								if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage) updateWatchedTargetId(i, entitylivingbase.getEntityId());
							} else
								updateWatchedTargetId(i, entitylivingbase.getEntityId());

							break;
						}

						list.remove(entitylivingbase);
					}
				}
			}

		if (getAttackTarget() != null) updateWatchedTargetId(0, getAttackTarget().getEntityId());
		else
			updateWatchedTargetId(0, 0);
	}

	private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
	{
		launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + 0.5D, p_82216_2_.posZ, true);
	}

	/**
	 * Launches a Wither skull toward (par2, par4, par6)
	 */
	private void launchWitherSkullToCoords(int p_82209_1_, double x, double y, double z, boolean invulnerable)
	{
		double d0 = getHeadX(p_82209_1_);
		double d1 = getHeadY(p_82209_1_);
		double d2 = getHeadZ(p_82209_1_);
		double d3 = x - d0;
		double d4 = y - d1;
		double d5 = z - d2;
		float f1 = MathHelper.sqrt(d3 * d3 + d5 * d5) * 0.2F;
		float f2 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
		EntityDreadSlug entitydreadslug = new EntityDreadSlug(world, this);
		entitydreadslug.posY = d1;
		entitydreadslug.posX = d0;
		entitydreadslug.posZ = d2;
		entitydreadslug.shoot(d3, d4 + f1, d5, 1.75F, 1.0F);
		if(!world.isRemote)
			world.spawnEntity(entitydreadslug);
		entitydreadslug.motionX = d3 / f2 * 0.8D * 0.8D + entitydreadslug.motionX;
		entitydreadslug.motionY = d4 / f2 * 0.8D * 0.8D + entitydreadslug.motionY;
		entitydreadslug.motionZ = d5 / f2 * 0.8D * 0.8D + entitydreadslug.motionZ;

		switch (rand.nextInt(5))
		{
		case 0:
			EntityDreadSlug entitydreadslug1 = new EntityDreadSlug(world, this);
			entitydreadslug1.posY = d1;
			entitydreadslug1.posX = d0;
			entitydreadslug1.posZ = d2;
			entitydreadslug1.shoot(d3, d4 + f1, d5, 1.75F, 1.0F);
			if(!world.isRemote)
				world.spawnEntity(entitydreadslug1);
			entitydreadslug1.motionX = d3 / f2 * 0.8D * 0.8D + entitydreadslug1.motionX;
			entitydreadslug1.motionY = d4 / f2 * 0.8D * 0.8D + entitydreadslug1.motionY;
			entitydreadslug1.motionZ = d5 / f2 * 0.8D * 0.8D + entitydreadslug1.motionZ;
			nextHeadUpdate[p_82209_1_ - 2] = ticksExisted + 10;
			break;
		case 1:
			EntityDreadSlug entitydreadslug11 = new EntityDreadSlug(world, this);
			entitydreadslug11.posY = d1;
			entitydreadslug11.posX = d0;
			entitydreadslug11.posZ = d2;
			EntityDreadSpawn mob = new EntityDreadSpawn(world);
			mob.copyLocationAndAnglesFrom(entitydreadslug11);
			entitydreadslug11.shoot(d3, d4 + f1 + rand.nextDouble() * 150D, d5, 1.3F, 1.0F);
			if(!world.isRemote)
				world.spawnEntity(entitydreadslug11);
			if(!world.isRemote)
				world.spawnEntity(mob);
			mob.startRiding(entitydreadslug11);
			mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			nextHeadUpdate[p_82209_1_ - 2] = ticksExisted + 20;
			break;
		case 2:
			EntityDreadSlug entitydreadslug111 = new EntityDreadSlug(world, this);
			entitydreadslug111.posY = d1;
			entitydreadslug111.posX = d0;
			entitydreadslug111.posZ = d2;
			EntityChagarothSpawn spawn = new EntityChagarothSpawn(world);
			spawn.copyLocationAndAnglesFrom(entitydreadslug111);
			entitydreadslug111.shoot(d3, d4 + f1 + rand.nextDouble() * 150D, d5, 1.3F, 1.0F);
			if(!world.isRemote)
				world.spawnEntity(entitydreadslug111);
			if(!world.isRemote)
				world.spawnEntity(spawn);
			spawn.startRiding(entitydreadslug111);
			spawn.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			nextHeadUpdate[p_82209_1_ - 2] = ticksExisted + 20;
			break;
		case 3:
			EntityDreadSlug entitydreadslug1111 = new EntityDreadSlug(world, this);
			entitydreadslug1111.posY = d1;
			entitydreadslug1111.posX = d0;
			entitydreadslug1111.posZ = d2;
			EntityChagarothFist fist = new EntityChagarothFist(world);
			fist.copyLocationAndAnglesFrom(entitydreadslug1111);
			entitydreadslug1111.shoot(d3, d4 + f1 + rand.nextDouble() * 150D, d5, 1.3F, 1.0F);
			if(!world.isRemote)
				world.spawnEntity(entitydreadslug1111);
			if(!world.isRemote)
				world.spawnEntity(fist);
			fist.startRiding(entitydreadslug1111);
			fist.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			nextHeadUpdate[p_82209_1_ - 2] = ticksExisted + 20;
			break;
		case 4:
			world.playEvent((EntityPlayer)null, 1016, new BlockPos(this), 0);
			d4 = y + 0.5D - d1;
			EntityDreadedCharge entitydragonfireball = new EntityDreadedCharge(world, this, d3, d4, d5);
			entitydragonfireball.posX = d0;
			entitydragonfireball.posY = d1;
			entitydragonfireball.posZ = d2;
			if(!world.isRemote)
				world.spawnEntity(entitydragonfireball);
			nextHeadUpdate[p_82209_1_ - 2] = ticksExisted + 100;
			break;
		}
	}

	private double getHeadX(int p_82214_1_)
	{
		if (p_82214_1_ <= 0)
			return posX;
		else
		{
			float f = (renderYawOffset + 180 * (p_82214_1_ - 1)) * 0.017453292F;
			float f1 = MathHelper.cos(f);
			return posX + f1 * 1.4D;
		}
	}

	private double getHeadY(int p_82208_1_)
	{
		return posY + getEyeHeight() * 0.8D;
	}

	private double getHeadZ(int p_82213_1_)
	{
		if (p_82213_1_ <= 0)
			return posZ;
		else
		{
			float f = (renderYawOffset + 180 * (p_82213_1_ - 1)) * 0.017453292F;
			float f1 = MathHelper.sin(f);
			return posZ + f1 * 1.4D;
		}
	}

	private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_)
	{
		float f = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);

		if (f > p_82204_3_) f = p_82204_3_;

		if (f < -p_82204_3_) f = -p_82204_3_;

		return p_82204_1_ + f;
	}

	@SideOnly(Side.CLIENT)
	public float getHeadYRotation(int p_82207_1_)
	{
		return yRotationHeads[p_82207_1_];
	}

	@SideOnly(Side.CLIENT)
	public float getHeadXRotation(int p_82210_1_)
	{
		return xRotationHeads[p_82210_1_];
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
		setSize(2.25F, 4.5F);

		if (ticksExisted % 40 == 0 && !world.isRemote)
			for(int x = getPosition().getX() - 3; x <= getPosition().getX() + 3; x++)
				for(int z = getPosition().getZ() - 3; z <= getPosition().getZ() + 3; z++)
					if(!(world.getBiome(new BlockPos(x, 0, z)) instanceof IDreadlandsBiome)
							&& world.getBiome(new BlockPos(x, 0, z)) != ACBiomes.purged)
					{
						Biome b = ACBiomes.dreadlands;
						Chunk c = world.getChunkFromBlockCoords(getPosition());
						c.getBiomeArray()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.getIdForBiome(b);
						c.setModified(true);
						PacketDispatcher.sendToDimension(new CleansingRitualMessage(x, z, Biome.getIdForBiome(b)), world.provider.getDimension());
					}

		setSprinting(false);
		motionX = 0.0D;
		motionZ = 0.0D;
		if (motionY > 0.0D)
			motionY = 0.0D;
		isAirBorne = false;
		onGround = true;

		for (int i = 0; i < 2; ++i)
		{
			yRotOHeads[i] = yRotationHeads[i];
			xRotOHeads[i] = xRotationHeads[i];
		}

		for (int j = 0; j < 2; ++j)
		{
			int k = getWatchedTargetId(j + 1);
			Entity entity1 = null;

			if (k > 0) entity1 = world.getEntityByID(k);

			if (entity1 != null)
			{
				double d11 = getHeadX(j + 1);
				double d12 = getHeadY(j + 1);
				double d13 = getHeadZ(j + 1);
				double d6 = entity1.posX - d11;
				double d7 = entity1.posY + entity1.getEyeHeight() - d12;
				double d8 = entity1.posZ - d13;
				double d9 = MathHelper.sqrt(d6 * d6 + d8 * d8);
				float f = (float)(MathHelper.atan2(d8, d6) * (180D / Math.PI)) - 90.0F;
				float f1 = (float)-(MathHelper.atan2(d7, d9) * (180D / Math.PI));
				xRotationHeads[j] = rotlerp(xRotationHeads[j], f1, 40.0F);
				yRotationHeads[j] = rotlerp(yRotationHeads[j], f, 10.0F);
			}
			else
			{
				xRotationHeads[j] = rotlerp(xRotationHeads[j], rotationPitch, 40.0F);
				yRotationHeads[j] = rotlerp(yRotationHeads[j], rotationYawHead, 10.0F);
			}
		}

		if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) <= 256D && flameShootTimer <= -200) flameShootTimer = 150;

		if (flameShootTimer > 0)
		{
			world.setEntityState(this, (byte)23);
			if (ticksExisted % 5 == 0 && flameShootTimer > 30)
			{
				world.playSound(null, new BlockPos(posX + 0.5D, posY + getEyeHeight(), posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 0.7F + getRNG().nextFloat(), getRNG().nextFloat() * 0.6F + 0.2F);
				world.playSound(null, new BlockPos(posX + 0.5D, posY + getEyeHeight(), posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 0.7F + getRNG().nextFloat(), getRNG().nextFloat() * 0.5F + 0.2F);
				world.playSound(null, new BlockPos(posX + 0.5D, posY + getEyeHeight(), posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 0.7F + getRNG().nextFloat(), getRNG().nextFloat() * 0.4F + 0.2F);
			}
			Entity target = getHeadLookTarget();
			if (target != null) {
				List list = world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));

				if (list != null && !list.isEmpty()) for (int i1 = 0; i1 < list.size(); i1++)
				{
					EntityLivingBase entity = (EntityLivingBase)list.get(i1);

					if (entity != null && rand.nextInt(3) == 0) if (entity.attackEntityFrom(AbyssalCraftAPI.dread, (float)(7.5D - getDistance(entity)) * 2F))
					{
						entity.addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 200, 1));
						entity.setFire((int)(10 - getDistance(entity)));
					}
				}

				if (target.attackEntityFrom(AbyssalCraftAPI.dread, (float)(7.5D - getDistance(target)) * 2F))
				{
					if (target instanceof EntityLivingBase)
						((EntityLivingBase) target).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 200, 1));
					target.setFire((int)(10 - getDistance(target)));
				}
			}
		}

		--flameShootTimer;

		if(!world.isRemote && isEntityAlive())
		{
			if(rand.nextInt(800) == 0)
			{
				EntityDreadSpawn mob = new EntityDreadSpawn(world);
				mob.copyLocationAndAnglesFrom(this);
				world.spawnEntity(mob);
				mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);

				EntityChagarothSpawn spawn = new EntityChagarothSpawn(world);
				spawn.copyLocationAndAnglesFrom(this);
				world.spawnEntity(spawn);
				spawn.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			}

			if(rand.nextInt(1600) == 0)
			{
				EntityChagarothFist fist = new EntityChagarothFist(world);
				fist.copyLocationAndAnglesFrom(this);
				world.spawnEntity(fist);
				fist.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			}

			if(rand.nextInt(2400) == 0)
			{
				EntityDreadguard dreadGuard = new EntityDreadguard(world);
				dreadGuard.copyLocationAndAnglesFrom(this);
				++dreadGuard.motionX;
				world.spawnEntity(dreadGuard);
				dreadGuard.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			}

			if(rand.nextInt(4800) == 0)
			{
				EntityGreaterDreadSpawn dreadGuard = new EntityGreaterDreadSpawn(world);
				dreadGuard.copyLocationAndAnglesFrom(this);
				++dreadGuard.motionX;
				world.spawnEntity(dreadGuard);
				dreadGuard.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			}

			if(rand.nextInt(7200) == 0)
			{
				EntityLesserDreadbeast dreadGuard = new EntityLesserDreadbeast(world);
				dreadGuard.copyLocationAndAnglesFrom(this);
				++dreadGuard.motionX;
				world.spawnEntity(dreadGuard);
				dreadGuard.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
			}
		}

		super.onLivingUpdate();
	}

	private Entity getHeadLookTarget()
	{
		Entity pointedEntity = null;
		double range = 8D + rand.nextDouble() * 20D;
		Vec3d srcVec = new Vec3d(posX, posY + getEyeHeight(), posZ);
		Vec3d lookVec = getLook(1.0F);
		RayTraceResult raytrace = world.rayTraceBlocks(srcVec, srcVec.addVector(lookVec.x * range, lookVec.y * range, lookVec.z * range));
		BlockPos hitpos = raytrace != null ? raytrace.getBlockPos() : null;
		double rx = hitpos == null ? range : Math.min(range, Math.abs(posX - hitpos.getX()));
		double ry = hitpos == null ? range : Math.min(range, Math.abs(posY - hitpos.getY()));
		double rz = hitpos == null ? range : Math.min(range, Math.abs(posZ - hitpos.getZ()));
		Vec3d destVec = srcVec.addVector(lookVec.x * range, lookVec.y * range, lookVec.z * range);
		float var9 = 8.0F;
		List<Entity> possibleList = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().offset(lookVec.x * rx, lookVec.y * ry, lookVec.z * rz).grow(var9, var9, var9));
		double hitDist = 0.0D;
		for (Entity possibleEntity : possibleList)
			if (possibleEntity != this && possibleEntity instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase)possibleEntity))
			{
				float borderSize = possibleEntity.getCollisionBorderSize();
				AxisAlignedBB collisionBB = possibleEntity.getEntityBoundingBox().grow(borderSize, borderSize, borderSize);
				RayTraceResult interceptPos = collisionBB.calculateIntercept(srcVec, destVec);
				if (collisionBB.contains(srcVec))
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
		if (world.isRemote)
		{
			Vec3d vector = getLookVec();

			double px = posX + vector.x * 1.5D;
			double py = posY + height * 0.75F;
			double pz = posZ + vector.z * 1.5D;


			for (int i = 0; i < 75; i++)
			{
				double dx = vector.x;
				double dy = vector.y;
				double dz = vector.z;

				double spread = 15.0D + getRNG().nextDouble() * 5.0D;
				double velocity = 0.5D + getRNG().nextDouble();

				dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dy += getRNG().nextGaussian() * 0.007499999832361937D;
				dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dx *= velocity;
				dy *= velocity;
				dz *= velocity;

				world.spawnParticle(EnumParticleTypes.FLAME, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz);
			}
		} else
			world.setEntityState(this, (byte)23);
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
	public void applyEntityCollision(Entity entityIn)
	{
		if (!isRidingSameEntity(entityIn)) if (!entityIn.noClip && !noClip)
		{
			double d0 = entityIn.posX - posX;
			double d1 = entityIn.posZ - posZ;
			double d2 = MathHelper.absMax(d0, d1);

			if (d2 >= 0.01D)
			{
				d2 = MathHelper.sqrt(d2);
				d0 = d0 / d2;
				d1 = d1 / d2;
				double d3 = 1.0D / d2;

				if (d3 > 1.0D) d3 = 1.0D;

				d0 = d0 * d3;
				d1 = d1 * d3;
				d0 = d0 * 0.2D;
				d1 = d1 * 0.2D;

				entityIn.addVelocity(d0, 0.0D, d1);

				d0 = d0 * 0.1D;
				d1 = d1 * 0.1D;

				addVelocity(d0, 0.0D, d1);
			}
		}
	}

	@Override
	public EnumPushReaction getPushReaction()
	{
		return EnumPushReaction.IGNORE;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if(deathTicks > 0)
			par1NBTTagCompound.setInteger("DeathTicks", deathTicks);
		par1NBTTagCompound.setInteger("BarfTimer", flameShootTimer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		deathTicks = par1NBTTagCompound.getInteger("DeathTicks");
		flameShootTimer = par1NBTTagCompound.getInteger("BarfTimer");
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		bossInfo.setPercent(getHealth() / getMaxHealth());
		//		if (par1DamageSource.getTrueSource() instanceof EntityPlayer)
		//		{
		//			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getTrueSource();
		////			entityplayer.addStat(ACAchievements.kill_chagaroth, 1);
		//		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public boolean isNonBoss(){
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par2 > 30) par2 = 10 + world.rand.nextInt(10);

		return super.attackEntityFrom(par1DamageSource, par2);
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
				world.spawnParticle(EnumParticleTypes.FLAME, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.LAVA, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				if (deathTicks >= 190 && deathTicks <= 200)
					world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			}
		}

		int i;
		int j;

		if (!world.isRemote)
			if (deathTicks > 150 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					world.spawnEntity(new EntityXPOrb(world, posX, posY, posZ, j));
					if(deathTicks == 100 || deathTicks == 120 || deathTicks == 140 || deathTicks == 160 || deathTicks == 180){
						world.spawnEntity(new EntityItem(world, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.dread_fragment, 4)));
						world.spawnEntity(new EntityItem(world, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.dreaded_chunk_of_abyssalnite, 2)));
						world.spawnEntity(new EntityItem(world, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.dreaded_shard_of_abyssalnite)));
						world.spawnEntity(new EntityItem(world, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(ACItems.dreadium_ingot)));
					}
				}
			}

		if(deathTicks == 20 && !world.isRemote)
			SpecialTextUtil.ChagarothGroup(world, I18n.translateToLocal("message.chagaroth.death.1"));
		if(deathTicks == 80 && !world.isRemote)
			SpecialTextUtil.ChagarothGroup(world, I18n.translateToLocal("message.chagaroth.death.2"));
		if(deathTicks == 140 && !world.isRemote)
			SpecialTextUtil.ChagarothGroup(world, I18n.translateToLocal("message.chagaroth.death.3"));
		if(deathTicks == 200 && !world.isRemote){
			SpecialTextUtil.ChagarothGroup(world, I18n.translateToLocal("message.chagaroth.death.4"));
			setDead();
			world.spawnEntity(new EntityItem(world, posX, posY, posZ, new ItemStack(ACItems.dread_plagued_gateway_key)));
		}
	}

	private int posneg(int num){
		return rand.nextBoolean() ? rand.nextInt(num) : -1 * rand.nextInt(num);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = world.getCurrentDate();

		attribute.removeModifier(attackDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}
}
