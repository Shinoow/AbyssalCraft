/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import java.util.*;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.common.handlers.AbyssalCraftEventHooks;
import com.shinoow.abyssalcraft.lib.*;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;
import com.shinoow.abyssalcraft.lib.world.TeleporterDarkRealm;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraft.world.BossInfo.Color;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityJzahar extends EntityMob implements IRangedAttackMob, IOmotholEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 10.0D, 0);
	public int deathTicks;
	private int talkTimer, shoutTicks, iframes;
	private static final DataParameter<Integer> EARTHQUAKETIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> BLACKHOLETIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> IMPLOSIONTIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SHOUTTIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COOLDOWNTIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);
	private boolean that = false;
	private boolean doShout;
	private boolean heh;

	private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 0.35D, true);
	private EntityAIAttackRanged aiArrowAttack = new EntityAIAttackRanged(this, 0.4D, 40, 20.0F);

	public EntityJzahar(World par1World) {
		super(par1World);
		setSize(1.5F, 5.7F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		isImmuneToFire = true;
	}

	@Override
	public String getName()
	{
		return TextFormatting.BLUE + super.getName();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 1000.0D : 500.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 60.0D : 30.0D);

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(EARTHQUAKETIMER, 0);
		dataManager.register(BLACKHOLETIMER, 0);
		dataManager.register(IMPLOSIONTIMER, 0);
		dataManager.register(SHOUTTIMER, 0);
		dataManager.register(COOLDOWNTIMER, 0);
	}

	public int getTimer(int timer){
		switch(timer){
		case 0:
			return dataManager.get(EARTHQUAKETIMER);
		case 1:
			return dataManager.get(BLACKHOLETIMER);
		case 2:
			return dataManager.get(IMPLOSIONTIMER);
		case 3:
			return dataManager.get(SHOUTTIMER);
		case 4:
			return dataManager.get(COOLDOWNTIMER);
		default:
			return 0;
		}
	}

	public void setTimer(int timer, int value){
		switch(timer){
		case 0:
			dataManager.set(EARTHQUAKETIMER, value);
			break;
		case 1:
			dataManager.set(BLACKHOLETIMER, value);
			break;
		case 2:
			dataManager.set(IMPLOSIONTIMER, value);
			break;
		case 3:
			dataManager.set(SHOUTTIMER, value);
			break;
		case 4:
			dataManager.set(COOLDOWNTIMER, value);
			break;
		default:
			break;
		}
	}

	public void decrementTimer(int timer){
		int decrement = ACConfig.hardcoreMode ? 2 : 1;
		setTimer(timer, getTimer(timer)-decrement);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_ENDERDRAGON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_WITHER_DEATH;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return ACLoot.ENTITY_JZAHAR;
	}

	@Override
	protected float getSoundVolume()
	{
		return 5.0F;
	}

	@Override
	public boolean isNonBoss()
	{
		return false;
	}

	@Override
	public void setDead()
	{
		if(ACConfig.hardcoreMode) { //hardcore mode bs goes here
			if(!world.isRemote && world.getDifficulty() == EnumDifficulty.PEACEFUL) {
				if(getHealth() <= 0 && deathTicks > 0)
					isDead = true;
				else if(!heh) {
					SpecialTextUtil.JzaharGroup(getEntityWorld(), "Haha, no. You're not playing fair, and neither will I!");
					SpecialTextUtil.JzaharGroup(getEntityWorld(), "Disabling 'Hardcore Mode' now will remove me, however.");
					heh = true;
				}
			} else
				isDead = true;
		} else
			isDead = true;
	}

	@Override
	protected void despawnEntity() {}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();

		if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getDistanceSq(getAttackTarget()) < width * width + getAttackTarget().width * getAttackTarget().width + 36D && (ticksExisted + getEntityId()) % 20 == 0)
			attackEntityAsMob(getAttackTarget());

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
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		swingArm(EnumHand.MAIN_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 4.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par2 >= 20) par2 = 5 + world.rand.nextInt(5);

		if (par1DamageSource.isFireDamage())
			return false;

		if (par1DamageSource.isExplosion())
			return false;

		if (par1DamageSource.isMagicDamage())
			return false;

		if(AbyssalCraftEventHooks.isRadiationDamage(par1DamageSource))
			return false;

		if(par1DamageSource == DamageSource.OUT_OF_WORLD && posY <= 0 && !getEntityWorld().isRemote) {
			getEntityWorld().removeEntity(this);
			return false;
		}

		if(!getEntityWorld().isRemote) {
			int health = (int)(getHealth() / getMaxHealth()) * 100;
			if(health < 10)
				health = 10;

			if(par1DamageSource.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getTrueSource()).capabilities.isCreativeMode && getRNG().nextInt(health) == 0 && ACConfig.jzaharBreaksFourthWall) {
				((EntityPlayer)par1DamageSource.getTrueSource()).setGameType(GameType.SURVIVAL);
				par1DamageSource.getTrueSource().attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), Integer.MAX_VALUE);
				if(par1DamageSource.getTrueSource().isEntityAlive()) {
					((EntityPlayer)par1DamageSource.getTrueSource()).setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
					par1DamageSource.getTrueSource().attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), Integer.MAX_VALUE);
				}
				if(ACConfig.showBossDialogs)
					SpecialTextUtil.JzaharGroup(getEntityWorld(), TranslationUtil.toLocal("message.jzahar.fourthwall"));
			}
		}

		if (iframes > 10)
			return false;
		else if(par2 > 0) //don't activate iframes if no damage is dealt
			iframes = 30;

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public void onKillCommand()
	{
		super.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		bossInfo.setPercent(getHealth() / getMaxHealth());
		//		List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(10, 10, 10));
		//		for(EntityPlayer player : players)
		//			player.addStat(ACAchievements.kill_jzahar, 1);
		super.onDeath(par1DamageSource);
	}

	private double getHeadX(int par1) {
		if (par1 <= 0)
			return posX;
		else {
			float f = (renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.cos(f);
			return posX + f1 * 1.3D;
		}
	}

	private double getHeadY(int par1) {
		return par1 <= 0 ? posY + 3.0D : posY + 2.2D;
	}

	private double getHeadZ(int par1) {
		if (par1 <= 0)
			return posZ;
		else {
			float f = (renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.sin(f);
			return posZ + f1 * 1.3D;
		}
	}

	@Override
	public float getEyeHeight()
	{
		return height * 0.90F;
	}

	@Override
	public void onLivingUpdate()
	{
		if(ACConfig.jzaharHealingAmount > 0 && isEntityAlive() && ticksExisted % ACConfig.jzaharHealingPace == 0)
			heal(ACConfig.jzaharHealingAmount);

		int multiplier = ACConfig.hardcoreMode ? 2 : 1;

		if(talkTimer > 0)
			talkTimer--;
		if(iframes > 0)
			--iframes;

		if(getAttackTarget() != null)
			if(getDistanceSq(getAttackTarget()) > 28D || getAttackTarget() instanceof EntityFlying || getAttackTarget().posY > posY + 4D)
			{
				tasks.addTask(2, aiArrowAttack);
				tasks.removeTask(aiAttackOnCollide);
			}
			else
			{
				tasks.addTask(2, aiAttackOnCollide);
				tasks.removeTask(aiArrowAttack);
			}

		float f = (rand.nextFloat() - 0.5F) * 8.0F;
		float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
		float f2 = (rand.nextFloat() - 0.5F) * 8.0F;

		for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(64.0D, 64.0D, 64.0D)))
			if(entity instanceof EntityDragon || entity instanceof EntityWither){
				if(!world.isRemote){
					world.removeEntity(entity);
					if(entity.isDead && ACConfig.showBossDialogs)
						SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.banish.vanilla"));
				} else if(ACConfig.particleEntity)
					world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
			}
			else if(entity instanceof EntityDragonBoss || entity instanceof EntitySacthoth || entity instanceof EntityChagaroth){
				if(!world.isRemote){
					world.removeEntity(entity);
					if(entity.isDead && ACConfig.showBossDialogs)
						SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.banish.ac"));
				} else if(ACConfig.particleEntity)
					world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
			}
			else if(entity instanceof EntityJzahar){
				if(!world.isRemote){
					world.removeEntity(entity);
					world.removeEntity(this);
					EntityJzahar newgatekeeper = new EntityJzahar(world);
					newgatekeeper.copyLocationAndAnglesFrom(this);
					if(rand.nextBoolean())
						world.spawnEntity(newgatekeeper);
					if(!that){
						that = true;
						if(ACConfig.showBossDialogs)
							SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.banish.jzh"));
					}
				} else if(ACConfig.particleEntity){
					world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
					world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				}
			}
			else if(!entity.isNonBoss()){
				if(!world.isRemote){
					world.removeEntity(entity);
					if(entity.isDead && ACConfig.showBossDialogs)
						SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.banish.other"));
				} else if(ACConfig.particleEntity)
					world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
			}
			else if(entity instanceof EntityPlayer)
				if(((EntityPlayer)entity).capabilities.isCreativeMode && talkTimer == 0 && getDistance(entity) <= 5){
					talkTimer = 1200;
					if(world.isRemote && ACConfig.showBossDialogs)
						if(EntityUtil.isPlayerCoralium((EntityPlayer)entity))
							SpecialTextUtil.JzaharText("<insert generic text here>");
						else if(ACConfig.jzaharBreaksFourthWall) {
							SpecialTextUtil.JzaharText(TranslationUtil.toLocalFormatted("message.jzahar.creative.1", entity.getName()));
							SpecialTextUtil.JzaharText(TranslationUtil.toLocal("message.jzahar.creative.2"));
						}
				}

		if (motionY > 0.42D)
			motionY = 0.42D;

		if(deathTicks == 0) {
			decrementTimer(0);
			decrementTimer(1);
			decrementTimer(2);
			decrementTimer(3);
			decrementTimer(4);

			if (getTimer(0) > 600)
				for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(64D)))
					if (entity.onGround && entity instanceof EntityLivingBase && !EntityUtil.isEntityEldritch((EntityLivingBase) entity))
					{
						entity.motionX += (float)(Math.random() * 0.1D - 0.05D);
						entity.motionY += (float)(Math.random() * 0.1D - 0.05D);
						entity.motionZ += (float)(Math.random() * 0.1D - 0.05D);
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 3));
						if (rand.nextInt(5) == 0)
							((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20, 3));

						entity.rotationPitch += (float)(Math.random() * 4.0D - 2.0D);
						entity.rotationYaw += (float)(Math.random() * 4.0D - 2.0D);
						if (rand.nextInt(5) == 0)
						{
							entity.motionY = 0.5D;
							entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F * multiplier);
						}
					}

			if(getTimer(3) < 0 && rand.nextInt(20) == 0 && getAttackTarget() != null && this.getDistanceSq(getAttackTarget()) <= 9216D && !doShout && getTimer(4) < 0) {
				playSound(ACSounds.jzahar_shout, 5F, 1F);
				if (!world.isRemote)
					SpecialTextUtil.JzaharGroup(world, "Uftoin...");
				shoutTicks = getTimer(3) - 30;
				doShout = true;
				setTimer(4, 100);
			}

			if (getTimer(3) < shoutTicks && doShout)
			{
				doShout = false;
				world.setEntityState(this, (byte)23);
				setTimer(3, 400);
				playSound(ACSounds.jzahar_blast, 5F, 1F);
				if (!world.isRemote)
					SpecialTextUtil.JzaharGroup(world, "...mglagln!");
				if(isEntityAlive())
				{
					double size = 64D;
					Vec3d vector = getLookVec();
					for(Entity entity : world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(size).offset(vector.x * 32D, vector.y * 32D, vector.z * 32D)))
						if (!(entity instanceof EntityLivingBase) || !EntityUtil.isEntityEldritch((EntityLivingBase) entity))
						{
							double dx = vector.x;
							double dy = vector.z;
							double dz = vector.z;

							double spread = 10D;
							double velocity = 2D + getRNG().nextDouble() * 8D;

							dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
							dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
							dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
							dx *= velocity;
							dy *= velocity * 0.25D;
							dz *= velocity;
							entity.addVelocity(dx, dy, dz);
							entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, 2F * (float)velocity * multiplier);
						}
				}
			}

			if (getTimer(0) < 0 && rand.nextInt(400/multiplier) == 0 && getAttackTarget() != null && getAttackTarget().onGround && getTimer(4) < 0)
			{
				swingArm(EnumHand.MAIN_HAND);
				setTimer(0, 1000);
				playSound(ACSounds.jzahar_earthquake, 5F, 1F);
				if (!world.isRemote)
					SpecialTextUtil.JzaharGroup(world, "Shugnah throd!");
				setTimer(4, 100);
			}

			if (getTimer(2) < 0 && getAttackTarget() != null && getTimer(4) < 0)
			{
				swingArm(EnumHand.MAIN_HAND);
				setTimer(2, 1200);
				playSound(ACSounds.jzahar_implosion, 5F, 1F);
				if (!world.isRemote)
					SpecialTextUtil.JzaharGroup(world, "Nilgh'ri mtli!");
				EntityImplosion entitywitherskull = new EntityImplosion(world, this);

				BlockPos targetpos = getAttackTarget().getPosition();

				entitywitherskull.setPosition(targetpos.getX() + rand.nextInt(10) * (rand.nextBoolean() ? 1 : -1), targetpos.getY() + 2, targetpos.getZ() + rand.nextInt(10) * (rand.nextBoolean() ? 1 : -1));

				world.playEvent(3000, entitywitherskull.getPosition(), 0);
				if (!world.isRemote)
					world.spawnEntity(entitywitherskull);
				setTimer(4, 100);
			}

			if (getTimer(1) < 0 && rand.nextInt(800) == 0 && getAttackTarget() != null && getTimer(4) < 0 && !ACConfig.no_black_holes)
			{
				swingArm(EnumHand.MAIN_HAND);
				setTimer(1, 1600);
				playSound(ACSounds.jzahar_black_hole, 5F, 1F);
				if (!world.isRemote)
					SpecialTextUtil.JzaharGroup(world, "Ph'nilgh'ri n'ghft!");
				EntityBlackHole entitywitherskull = new EntityBlackHole(world, this);
				entitywitherskull.copyLocationAndAnglesFrom(getAttackTarget());
				BlockPos targetpos = getAttackTarget().getPosition();

				entitywitherskull.setPosition(targetpos.getX() + (5 + rand.nextInt(10)) * (rand.nextBoolean() ? 1 : -1), targetpos.getY() + 2, targetpos.getZ() + (5 + rand.nextInt(10)) * (rand.nextBoolean() ? 1 : -1));

				world.playEvent(3000, entitywitherskull.getPosition(), 0);
				if (!world.isRemote)
					world.spawnEntity(entitywitherskull);
				setTimer(4, 100);
			}
		}

		super.onLivingUpdate();
	}

	@Override
	public void addPotionEffect(PotionEffect potioneffectIn){}

	private void addShoutParticles()
	{
		if (world.isRemote)
		{
			Vec3d vector = getLookVec();

			double px = posX + vector.x * 5D;
			double py = posY + getEyeHeight() + vector.y * 5D;
			double pz = posZ + vector.z * 5D;


			for (int i = 0; i < 1000; i++)
			{
				double dx = vector.x;
				double dy = vector.y;
				double dz = vector.z;

				double spread = 10D;
				double velocity = 2D + getRNG().nextDouble() * 18D;

				dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dx *= velocity;
				dy *= velocity;
				dz *= velocity;
				world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz);
			}
		} else
			world.setEntityState(this, (byte)23);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 23)
			addShoutParticles();
		else
			super.handleStatusUpdate(id);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttag)
	{
		super.writeEntityToNBT(nbttag);

		if(deathTicks > 0)
			nbttag.setInteger("DeathTicks", deathTicks);

		nbttag.setInteger("EarthquakeTime", getTimer(0));
		nbttag.setInteger("BlackHoleTime", getTimer(1));
		nbttag.setInteger("ImplosionTime", getTimer(2));
		nbttag.setInteger("ShoutTime", getTimer(3));
		nbttag.setInteger("CooldownTime", getTimer(4));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttag)
	{
		super.readEntityFromNBT(nbttag);

		deathTicks = nbttag.getInteger("DeathTicks");
		setTimer(0, nbttag.getInteger("EarthquakeTime"));
		setTimer(1, nbttag.getInteger("BlackHoleTime"));
		setTimer(2, nbttag.getInteger("ImplosionTime"));
		setTimer(3, nbttag.getInteger("ShoutTime"));
		setTimer(4, nbttag.getInteger("CooldownTime"));
	}

	private double speed = 0.05D;

	@Override
	protected void onDeathUpdate()
	{
		motionX = motionY = motionZ = 0;
		++deathTicks;

		if(deathTicks <= 800){
			if(deathTicks == 410)
				playSound(ACSounds.jzahar_charge, 1, 1);
			if(deathTicks < 400)
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY + 2.5D, posZ, 0, 0, 0);
			float f = (rand.nextFloat() - 0.5F) * 3.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 2.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 3.0F;
			if(deathTicks >= 100 && deathTicks < 400)
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + f, posY + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			if(deathTicks >= 200 && deathTicks < 400){
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + f, posY + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.LAVA, posX, posY + 2.5D, posZ, 0, 0, 0, 0);
			}
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY + 1.5D, posZ, 0, 0, 0);
			if (deathTicks >= 790 && deathTicks <= 800){
				world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY + 1.5D, posZ, 0.0D, 0.0D, 0.0D);
				playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 4, (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F);
			}
			if(deathTicks > 400 && deathTicks < 800){
				float size = 32F;

				List<Entity> list = world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(size, size, size));

				for(Entity entity : list)
				{
					double scale = (size - entity.getDistance(posX, posY, posZ))/size;

					Vec3d dir = new Vec3d(entity.posX - posX, entity.posY - posY, entity.posZ - posZ);
					dir = dir.normalize();
					entity.addVelocity(dir.x * -speed * scale, dir.y * -speed * scale, dir.z * -speed * scale);
				}

				speed += 0.0001;
			}
		}

		int i;
		int j;

		if (!world.isRemote)
			if (deathTicks > 750 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					world.spawnEntity(new EntityXPOrb(world, posX, posY, posZ, j));
				}
			}
		if(deathTicks == 790 && !world.isRemote){
			if(world.getGameRules().getBoolean("mobGriefing")) {
				List<BlockPos> blocks = new ArrayList<>();
				for(int x = 0; x < 10; x++)
					for(int y = 0; y < 10; y++)
						for(int z = 0; z < 10; z++){
							if(!world.isAirBlock(new BlockPos(posX + x, posY + y, posZ + z)))
								blocks.add(new BlockPos(posX + x, posY + y, posZ + z));
							if(!world.isAirBlock(new BlockPos(posX - x, posY + y, posZ + z)))
								blocks.add(new BlockPos(posX - x, posY + y, posZ + z));
							if(!world.isAirBlock(new BlockPos(posX + x, posY + y, posZ - z)))
								blocks.add(new BlockPos(posX + x, posY + y, posZ - z));
							if(!world.isAirBlock(new BlockPos(posX - x, posY + y, posZ - z)))
								blocks.add(new BlockPos(posX - x, posY + y, posZ - z));
							if(!world.isAirBlock(new BlockPos(posX + x, posY - y, posZ + z)))
								blocks.add(new BlockPos(posX + x, posY - y, posZ + z));
							if(!world.isAirBlock(new BlockPos(posX - x, posY - y, posZ + z)))
								blocks.add(new BlockPos(posX - x, posY - y, posZ + z));
							if(!world.isAirBlock(new BlockPos(posX + x, posY - y, posZ - z)))
								blocks.add(new BlockPos(posX + x, posY - y, posZ - z));
							if(!world.isAirBlock(new BlockPos(posX - x, posY - y, posZ - z)))
								blocks.add(new BlockPos(posX - x, posY - y, posZ - z));
						}
				for(BlockPos pos : blocks)
					if(world.getBlockState(pos).getBlock() != Blocks.BEDROCK)
						world.setBlockToAir(pos);
			}

			if(!world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(3,1,3)).isEmpty()){
				List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(3,1,3));
				for(Entity entity: entities)
					if(entity instanceof EntityPlayer){
						EntityPlayer player = (EntityPlayer) entity;
						player.setHealth(1);
						player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 2400, 5));
						player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 2400, 5));
						player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 2400, 5));
						player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2400, 5));
						player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 2400, 5));
						player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 2400, 5));
						player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 2400, 5));
						player.addPotionEffect(new PotionEffect(MobEffects.POISON, 2400, 5));
						if(player instanceof EntityPlayerMP){
							WorldServer worldServer = (WorldServer) player.world;
							EntityPlayerMP mp = (EntityPlayerMP) player;
							mp.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 80, 255));
							mp.server.getPlayerList().transferPlayerToDimension(mp, ACLib.dark_realm_id, new TeleporterDarkRealm(worldServer));
							//							player.addStat(ACAchievements.enter_dark_realm, 1);
						}
					}
					else if(entity instanceof EntityLivingBase || entity instanceof EntityItem)
						entity.setDead();
			}
			if(world.getClosestPlayerToEntity(this, 48) != null)
				world.spawnEntity(new EntityGatekeeperEssence(world, posX, posY, posZ));
		}

		if(ACConfig.showBossDialogs) {
			if(deathTicks == 20 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.1"));
			if(deathTicks == 100 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.2"));
			if(deathTicks == 180 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.3"));
			if(deathTicks == 260 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.4"));
			if(deathTicks == 340 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.5"));
			if(deathTicks == 420 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.6"));
			if(deathTicks == 500 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.7"));
			if(deathTicks == 580 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.8"));
			if(deathTicks == 660 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.9"));
			if(deathTicks == 800 && !world.isRemote)
				SpecialTextUtil.JzaharGroup(world, TranslationUtil.toLocal("message.jzahar.death.10"));
		}
		if(deathTicks == 800 && !world.isRemote)
			setDead();
	}

	private void launchWitherSkullToEntity(int par1, EntityLivingBase par2EntityLivingBase) {
		launchWitherSkullToCoords(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() * 0.35D, par2EntityLivingBase.posZ, par1 == 0 && rand.nextFloat() < 0.001F);
	}

	private void launchWitherSkullToCoords(int par1, double par2, double par4, double par6, boolean par8) {
		world.playEvent((EntityPlayer)null, 1014, new BlockPos(posX, posY, posZ), 0);
		double d3 = getHeadX(par1);
		double d4 = getHeadY(par1);
		double d5 = getHeadZ(par1);
		double d6 = par2 - d3;
		double d7 = par4 - d4;
		double d8 = par6 - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(world, this, d6, d7, d8);
		if (par8)
			entitywitherskull.setInvulnerable(true);
		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		world.spawnEntity(entitywitherskull);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
		launchWitherSkullToEntity(0, par1EntityLivingBase);
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

	@Override
	public void setSwingingArms(boolean swingingArms) {

	}
}
