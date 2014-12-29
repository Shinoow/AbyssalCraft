/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.entity;

import java.util.Calendar;
import java.util.UUID;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.*;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

public class EntityJzahar extends EntityMob implements IBossDisplayData, IRangedAttackMob, IAntiEntity, ICoraliumEntity, IDreadEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 10.0D, 0);
	public int deathTicks;

	public EntityJzahar(World par1World) {
		super(par1World);
		setSize(1.8F, 4.0F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityDragon.class, 0.35D, true));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityWither.class, 0.35D, true));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(5, new EntityAIArrowAttack(this, 0.4D, 40, 20.0F));
		tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(7, new EntityAIWander(this, 0.35D));
		tasks.addTask(8, new EntityAILookIdle(this));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDragon.class, 0, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityWither.class, 0, true));
		targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public String getCommandSenderName()
	{
		return EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("entity.abyssalcraft.Jzahar.name");
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.attackDamage);
		Calendar calendar = worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			attribute.applyModifier(attackDamageBoost);
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.blaze.breathe";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.enderdragon.hit";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.wither.death";
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
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		dropItem(AbyssalCraft.Staff, 1);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {

		if(worldObj.isRemote)
			SpecialTextUtil.JzaharText(StatCollector.translateToLocal("message.jzahar.death.dev"));
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
	protected void onDeathUpdate()
	{
		++deathTicks;

		int i;
		int j;

		if (!worldObj.isRemote)
			if (deathTicks > 150 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
					if(deathTicks == 100 || deathTicks == 120 || deathTicks == 140 || deathTicks == 160 || deathTicks == 180){
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.abyingot)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.Cingot)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.dreadiumingot)));
					}
				}
			}
		if(deathTicks == 200 && !worldObj.isRemote)
			setDead();
	}

	private void func_82216_a(int par1, EntityLivingBase par2EntityLivingBase) {
		func_82209_a(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() * 0.35D, par2EntityLivingBase.posZ, par1 == 0 && rand.nextFloat() < 0.001F);
	}

	private void func_82209_a(int par1, double par2, double par4, double par6, boolean par8) {
		worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, (int)posX, (int)posY, (int)posZ, 0);
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
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.attackDamage);
		Calendar calendar = worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}
}