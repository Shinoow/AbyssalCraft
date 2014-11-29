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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.*;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

public class EntitySacthoth extends EntityMob implements IBossDisplayData, IAntiEntity, ICoraliumEntity, IDreadEntity {

	public int deathTicks;

	public EntitySacthoth(World par1World) {
		super(par1World);
		setSize(1.5F, 4.0F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
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
		dataWatcher.addObject(16, new Byte((byte)0));
	}

	@Override
	public String getCommandSenderName()
	{
		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocalFormatted("entity.abyssalcraft.shadowboss.name");
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
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(160.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.4D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.799D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 60));
		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	protected void fall(float par1) {}

	@Override
	protected String getLivingSound()
	{
		return "mob.blaze.breathe";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.blaze.hit";
	}

	@Override
	protected String getDeathSound()
	{
		return "abyssalcraft:sacthoth.death";
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
	protected void dropFewItems(boolean par1, int par2) {
		dropItem(AbyssalCraft.soulReaper, 1);
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
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par1DamageSource == DamageSource.inWall){
			teleportRandomly();
			return false;
		}
		else if(par1DamageSource.isExplosion()){
			if(worldObj.isRemote)
				SpecialTextUtil.SacthothText("I'm not saying you \u00A7ocan't\u00A7r blow me up, it just won't work.");
			return false;
		}
		else if(par1DamageSource.isProjectile()){
			if(worldObj.isRemote)
				SpecialTextUtil.SacthothText("Ha, your projectiles can't harm me.");
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
		posX = event.targetX;
		posY = event.targetY;
		posZ = event.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);

		if (worldObj.blockExists(i, j, k))
		{
			boolean flag1 = false;

			while (!flag1 && j > 0)
			{
				Block block = worldObj.getBlock(i, j - 1, k);

				if (block.getMaterial().blocksMovement())
					flag1 = true;
				else
				{
					--posY;
					--j;
				}
			}

			if (flag1)
			{
				setPosition(posX, posY, posZ);

				if (worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox))
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
				worldObj.spawnParticle("largesmoke", d7, d8, d9, f, f1, f2);
			}

			worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
			playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
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
			worldObj.spawnParticle("smoke", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("largesmoke", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("explode", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			if (deathTicks >= 190 && deathTicks <= 200)
				worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

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
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.shadowfragment, 4)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.shadowshard, 2)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.shadowgem)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.oblivionshard)));
					}
				}
				if(deathTicks >= 190 && deathTicks <= 200){
					if(deathTicks <= 200){
						EntityShadowCreature shadowCreature = new EntityShadowCreature(worldObj);
						shadowCreature.copyLocationAndAnglesFrom(this);
						worldObj.spawnEntityInWorld(shadowCreature);
					}
					if(deathTicks >= 195 && deathTicks <= 200){
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
			}
		if(deathTicks == 200 && !worldObj.isRemote)

			setDead();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onLivingUpdate()
	{
		for (int i = 0; i < 2; ++i)
			worldObj.spawnParticle("largesmoke", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);

		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(30.0D, 30.0D, 30.0D));
		if (list != null)
			for (int k2 = 0; k2 < list.size(); k2++) {
				Entity entity = (Entity)list.get(k2);
				if (entity instanceof EntityPlayer && !entity.isDead && deathTicks == 0)
					((EntityPlayer)entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 40));
			}
		EntityPlayer player = worldObj.getClosestVulnerablePlayerToEntity(this, 160D);
		if(player != null && player.getDistanceToEntity(this) >= 50D){
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

		super.onLivingUpdate();
	}
}