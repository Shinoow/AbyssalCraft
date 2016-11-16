/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.BossInfo.Color;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.world.TeleporterDarkRealm;

public class EntityJzahar extends EntityMob implements IRangedAttackMob, IAntiEntity, ICoraliumEntity, IDreadEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 10.0D, 0);
	public int deathTicks;
	private int talkTimer;
	private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);
	private boolean that = false;

	public EntityJzahar(World par1World) {
		super(par1World);
		setSize(1.5F, 5.7F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.35D, true));
		tasks.addTask(3, new EntityAIAttackRanged(this, 0.4D, 40, 20.0F));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	public String getName()
	{
		return TextFormatting.BLUE + super.getName();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(40.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
		}
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_ENDERDRAGON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_WITHER_DEATH;
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
	public boolean isNonBoss()
	{
		return false;
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

		if(AbyssalCraft.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 4.5F);

		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par2 > 30) par2 = 10 + worldObj.rand.nextInt(10);

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		bossInfo.setPercent(getHealth() / getMaxHealth());
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(10, 10, 10));
		for(EntityPlayer player : players)
			player.addStat(AbyssalCraft.killJzahar, 1);
		super.onDeath(par1DamageSource);
	}

	private double func_82214_u(int par1) {
		if (par1 <= 0)
			return posX;
		else {
			float f = (renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.cos(f);
			return posX + f1 * 1.3D;
		}
	}

	private double func_82208_v(int par1) {
		return par1 <= 0 ? posY + 3.0D : posY + 2.2D;
	}

	private double func_82213_w(int par1) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public void onLivingUpdate()
	{
		if(talkTimer > 0)
			talkTimer--;

		float f = (rand.nextFloat() - 0.5F) * 8.0F;
		float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
		float f2 = (rand.nextFloat() - 0.5F) * 8.0F;

		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 64.0D, 64.0D));
		if (list != null)
			for (int k2 = 0; k2 < list.size(); k2++) {
				Entity entity = (Entity)list.get(k2);
				if(entity instanceof EntityDragon || entity instanceof EntityWither){
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						if(entity.isDead)
							SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.banish.vanilla"));
					} else if(AbyssalCraft.particleEntity)
						worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
				}
				else if(entity instanceof EntityDragonBoss || entity instanceof EntitySacthoth || entity instanceof EntityChagaroth){
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						if(entity.isDead)
							SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.banish.ac"));
					} else if(AbyssalCraft.particleEntity)
						worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
				}
				else if(entity instanceof EntityJzahar){
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						worldObj.removeEntity(this);
						EntityJzahar newgatekeeper = new EntityJzahar(worldObj);
						newgatekeeper.copyLocationAndAnglesFrom(this);
						if(rand.nextBoolean())
							worldObj.spawnEntityInWorld(newgatekeeper);
						if(!that){
							that = true;
							SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.banish.jzh"));
						}
					} else if(AbyssalCraft.particleEntity){
						worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
						worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
					}
				}
				else if(!entity.isNonBoss()){
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						if(entity.isDead)
							SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.banish.other"));
					} else if(AbyssalCraft.particleEntity)
						worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
				}
				else if(entity instanceof EntityPlayer)
					if(((EntityPlayer)entity).capabilities.isCreativeMode && talkTimer == 0 && getDistanceToEntity(entity) <= 5){
						talkTimer = 1200;
						if(worldObj.isRemote)
							if(EntityUtil.isPlayerCoralium((EntityPlayer)entity))
								SpecialTextUtil.JzaharText("<insert generic text here>");
							else {
								SpecialTextUtil.JzaharText(String.format(I18n.translateToLocal("message.jzahar.creative.1"), entity.getName()));
								SpecialTextUtil.JzaharText(I18n.translateToLocal("message.jzahar.creative.2"));
							}
					}
			}
		super.onLivingUpdate();
	}

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

	double speed = 0.05D;

	@Override
	protected void onDeathUpdate()
	{
		motionX = motionY = motionZ = 0;
		++deathTicks;

		if(deathTicks <= 800){
			if(deathTicks == 410)
				playSound(AbyssalCraft.jzahar_charge, 1, 1);
			if(deathTicks < 400)
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY + 2.5D, posZ, 0, 0, 0);
			float f = (rand.nextFloat() - 0.5F) * 3.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 2.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 3.0F;
			if(deathTicks >= 100 && deathTicks < 400)
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + f, posY + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			if(deathTicks >= 200 && deathTicks < 400){
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + f, posY + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle(EnumParticleTypes.LAVA, posX, posY + 2.5D, posZ, 0, 0, 0, 0);
			}
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY + 1.5D, posZ, 0, 0, 0);
			if (deathTicks >= 790 && deathTicks <= 800){
				worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY + 1.5D, posZ, 0.0D, 0.0D, 0.0D);
				playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 4, (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F);
			}
			if(deathTicks > 400 && deathTicks < 800){
				float size = 32F;

				List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().expand(size, size, size));

				for(Entity entity : list)
				{
					double scale = (size - entity.getDistance(posX, posY, posZ))/size;

					Vec3d dir = new Vec3d(entity.posX - posX, entity.posY - posY, entity.posZ - posZ);
					dir = dir.normalize();
					entity.addVelocity(dir.xCoord * -speed * scale, dir.yCoord * -speed * scale, dir.zCoord * -speed * scale);
				}

				speed += 0.0001;
			}
		}

		int i;
		int j;

		if (!worldObj.isRemote)
			if (deathTicks > 750 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
				}
			}
		if(deathTicks == 790 && !worldObj.isRemote){
			List<BlockPos> blocks = new ArrayList<BlockPos>();
			for(int x = 0; x < 10; x++)
				for(int y = 0; y < 10; y++)
					for(int z = 0; z < 10; z++){
						if(!worldObj.isAirBlock(new BlockPos(posX + x, posY + y, posZ + z)))
							blocks.add(new BlockPos(posX + x, posY + y, posZ + z));
						if(!worldObj.isAirBlock(new BlockPos(posX - x, posY + y, posZ + z)))
							blocks.add(new BlockPos(posX - x, posY + y, posZ + z));
						if(!worldObj.isAirBlock(new BlockPos(posX + x, posY + y, posZ - z)))
							blocks.add(new BlockPos(posX + x, posY + y, posZ - z));
						if(!worldObj.isAirBlock(new BlockPos(posX - x, posY + y, posZ - z)))
							blocks.add(new BlockPos(posX - x, posY + y, posZ - z));
						if(!worldObj.isAirBlock(new BlockPos(posX + x, posY - y, posZ + z)))
							blocks.add(new BlockPos(posX + x, posY - y, posZ + z));
						if(!worldObj.isAirBlock(new BlockPos(posX - x, posY - y, posZ + z)))
							blocks.add(new BlockPos(posX - x, posY - y, posZ + z));
						if(!worldObj.isAirBlock(new BlockPos(posX + x, posY - y, posZ - z)))
							blocks.add(new BlockPos(posX + x, posY - y, posZ - z));
						if(!worldObj.isAirBlock(new BlockPos(posX - x, posY - y, posZ - z)))
							blocks.add(new BlockPos(posX - x, posY - y, posZ - z));
					}
			for(BlockPos pos : blocks)
				if(worldObj.getBlockState(pos).getBlock() != Blocks.BEDROCK)
					worldObj.setBlockToAir(pos);

			if(!worldObj.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().expand(3,1,3)).isEmpty()){
				List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(3,1,3));
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
							WorldServer worldServer = (WorldServer) player.worldObj;
							EntityPlayerMP mp = (EntityPlayerMP) player;
							mp.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 80, 255));
							mp.mcServer.getPlayerList().transferPlayerToDimension(mp, ACLib.dark_realm_id, new TeleporterDarkRealm(worldServer));
							player.addStat(AbyssalCraft.enterDarkRealm, 1);
						}
					}
					else if(entity instanceof EntityLivingBase || entity instanceof EntityItem)
						entity.setDead();
			}
			if(worldObj.getClosestPlayerToEntity(this, 48) != null)
				worldObj.spawnEntityInWorld(new EntityGatekeeperEssence(worldObj, posX, posY, posZ));
		}

		if(deathTicks == 20 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.1"));
		if(deathTicks == 100 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.2"));
		if(deathTicks == 180 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.3"));
		if(deathTicks == 260 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.4"));
		if(deathTicks == 340 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.5"));
		if(deathTicks == 420 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.6"));
		if(deathTicks == 500 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.7"));
		if(deathTicks == 580 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.8"));
		if(deathTicks == 660 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.9"));
		if(deathTicks == 800 && !worldObj.isRemote){
			SpecialTextUtil.JzaharGroup(worldObj, I18n.translateToLocal("message.jzahar.death.10"));
			setDead();
		}
	}

	private void func_82216_a(int par1, EntityLivingBase par2EntityLivingBase) {
		func_82209_a(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() * 0.35D, par2EntityLivingBase.posZ, par1 == 0 && rand.nextFloat() < 0.001F);
	}

	private void func_82209_a(int par1, double par2, double par4, double par6, boolean par8) {
		worldObj.playEvent((EntityPlayer)null, 1014, new BlockPos(posX, posY, posZ), 0);
		double d3 = func_82214_u(par1);
		double d4 = func_82208_v(par1);
		double d5 = func_82213_w(par1);
		double d6 = par2 - d3;
		double d7 = par4 - d4;
		double d8 = par6 - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(worldObj, this, d6, d7, d8);
		if (par8)
			entitywitherskull.setInvulnerable(true);
		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		worldObj.spawnEntityInWorld(entitywitherskull);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
		func_82216_a(0, par1EntityLivingBase);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = worldObj.getCurrentDate();

		attribute.removeModifier(attackDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}
}
