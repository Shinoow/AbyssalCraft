/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.entity;

import java.util.Calendar;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.core.api.entity.IDreadEntity;

public class EntityChagaroth extends EntityMob implements IBossDisplayData, IDreadEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8D, 0);
	public int deathTicks;

	public EntityChagaroth(World par1World) {
		super(par1World);
		setSize(3.5F, 5.0F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
	}

	@Override
	public String getCommandSenderName()
	{
		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocalFormatted("entity.abyssalcraft.chagaroth.name");
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 200));
		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected String getLivingSound()
	{
		return "abyssalcraft:dreadguard.idle";
	}

	@Override
	protected String getHurtSound()
	{
		return "abyssalcraft:dreadguard.hit";
	}

	@Override
	protected String getDeathSound()
	{
		return "abyssalcraft:dreadguard.death";
	}

	@Override
	protected float getSoundVolume()
	{
		return 5.0F;
	}

	@Override
	public void onLivingUpdate()
	{
		EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 32D);
		if(!worldObj.isRemote){
			if(rand.nextInt(100) == 0 && player != null){
				EntityChagarothSpawn mob = new EntityChagarothSpawn(worldObj);
				mob.copyLocationAndAnglesFrom(player);
				worldObj.spawnEntityInWorld(mob);
			}
			if(rand.nextInt(1000) == 0){
				EntityDreadSpawn mob = new EntityDreadSpawn(worldObj);
				mob.copyLocationAndAnglesFrom(this);
				worldObj.spawnEntityInWorld(mob);

				EntityChagarothSpawn spawn = new EntityChagarothSpawn(worldObj);
				spawn.copyLocationAndAnglesFrom(this);
				worldObj.spawnEntityInWorld(spawn);
				if(rand.nextInt(1000) == 0){
					EntityChagarothFist fist = new EntityChagarothFist(worldObj);
					fist.copyLocationAndAnglesFrom(spawn);
					worldObj.spawnEntityInWorld(fist);
				}
			}
			EntityChagarothFist fist = new EntityChagarothFist(worldObj);
			fist.copyLocationAndAnglesFrom(this);
			EntityDreadguard dreadGuard = new EntityDreadguard(worldObj);
			dreadGuard.copyLocationAndAnglesFrom(fist);
			if(player != null)
				switch((int)getHealth()){
				case 900:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 800:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 700:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 600:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 500:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 400:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 300:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 200:
					worldObj.spawnEntityInWorld(fist);
					break;
				case 100:
					worldObj.spawnEntityInWorld(fist);
					worldObj.spawnEntityInWorld(dreadGuard);
					break;
				}
		}
		super.onLivingUpdate();
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		if (par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killChagaroth, 1);
		}
		if(worldObj.isRemote){
			SpecialTextUtil.ChagarothText("Here, have your key back, transmutate it for a working one.");
			SpecialTextUtil.ChagarothText("The chest behind me holds \"The Legendary Treasure of The Dreadlands\",");
			SpecialTextUtil.ChagarothText("left by the ones who ran the mine before I came into the picture.");
			SpecialTextUtil.ChagarothText("Don't get your hopes up, I bet it's just a block of dirt or something.");
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		dropItem(AbyssalCraft.dreadKey, 1);

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
			worldObj.spawnParticle("flame", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("lava", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
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
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.dreadfragment, 4)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.dreadchunk, 2)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.Dreadshard)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.dreadiumingot)));
					}
				}
			}
		if(deathTicks == 200 && !worldObj.isRemote)
			setDead();
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