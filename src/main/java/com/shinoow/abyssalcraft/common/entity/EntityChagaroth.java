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

import java.util.Calendar;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACAchievements;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

public class EntityChagaroth extends EntityMob implements IDreadEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8D, 0);
	public int deathTicks;
	private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);

	public EntityChagaroth(World par1World) {
		super(par1World);
		setSize(2.0F, 4.8F);
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
	}

	@Override
	public String getName()
	{
		return TextFormatting.DARK_RED + super.getName();
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		boolean flag = super.attackEntityAsMob(par1Entity);

		if(flag)
			if(par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 4.5F);
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
	protected SoundEvent getHurtSound()
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
		EntityPlayer player = world.getClosestPlayerToEntity(this, 32D);
		if(!world.isRemote && deathTicks == 0){
			if(rand.nextInt(100) == 0 && player != null){
				EntityChagarothSpawn mob = new EntityChagarothSpawn(world);
				mob.copyLocationAndAnglesFrom(player);
				world.spawnEntity(mob);
			}
			if(rand.nextInt(1000) == 0){
				EntityDreadSpawn mob = new EntityDreadSpawn(world);
				mob.copyLocationAndAnglesFrom(this);
				world.spawnEntity(mob);

				EntityChagarothSpawn spawn = new EntityChagarothSpawn(world);
				spawn.copyLocationAndAnglesFrom(this);
				world.spawnEntity(spawn);
			}
			EntityChagarothFist fist = new EntityChagarothFist(world);
			fist.copyLocationAndAnglesFrom(this);
			EntityDreadguard dreadGuard = new EntityDreadguard(world);
			dreadGuard.copyLocationAndAnglesFrom(fist);
			if(rand.nextInt(3600) == 0)
				world.spawnEntity(fist);
			if(rand.nextInt(7200) == 0)
				world.spawnEntity(dreadGuard);
			if(player != null)
				switch((int)getHealth()){
				case 900:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 800:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 700:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 600:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 500:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 400:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 300:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 200:
					world.spawnEntity(fist);
					damageEntity(DamageSource.generic, 1);
					break;
				case 100:
					world.spawnEntity(fist);
					world.spawnEntity(dreadGuard);
					damageEntity(DamageSource.generic, 1);
					break;
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

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		bossInfo.setPercent(getHealth() / getMaxHealth());
		if (par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(ACAchievements.kill_chagaroth, 1);
		}
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
