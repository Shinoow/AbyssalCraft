/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.anti;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIAntiCreeperSwell;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityAntiCreeper extends EntityMob implements IAntiEntity {

	private static final DataParameter<Integer> STATE = EntityDataManager.<Integer>createKey(EntityAntiCreeper.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean>createKey(EntityAntiCreeper.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.<Boolean>createKey(EntityAntiCreeper.class, DataSerializers.BOOLEAN);
	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
	 * weird)
	 */
	private int lastActiveTime;
	/**
	 * The amount of time since the creeper was close enough to the player to ignite
	 */
	private int timeSinceIgnited;
	private int fuseTime = 30;
	/** Explosion radius for this creeper. */
	private int explosionRadius = 10;

	public EntityAntiCreeper(World par1World){
		super(par1World);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAntiCreeperSwell(this));
		tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
		tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		tasks.addTask(5, new EntityAIWander(this, 0.8D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);

		if(ACConfig.hardcoreMode) getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
		else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
	}

	@Override
	public int getMaxFallHeight()
	{
		return getAttackTarget() == null ? 3 : 3 + (int)(getHealth() - 1.0F);
	}

	@Override
	public void fall(float par1, float par2)
	{
		super.fall(par1, par2);
		timeSinceIgnited = (int)(timeSinceIgnited + par1 * 1.5F);

		if (timeSinceIgnited > fuseTime - 5)
			timeSinceIgnited = fuseTime - 5;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(STATE, Integer.valueOf(-1));
		dataManager.register(POWERED, Boolean.valueOf(false));
		dataManager.register(IGNITED, Boolean.valueOf(false));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (dataManager.get(POWERED).booleanValue())
			par1NBTTagCompound.setBoolean("powered", true);

		par1NBTTagCompound.setShort("Fuse", (short)fuseTime);
		par1NBTTagCompound.setByte("ExplosionRadius", (byte)explosionRadius);
		par1NBTTagCompound.setBoolean("ignited", hasIgnited());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		dataManager.set(POWERED, Boolean.valueOf(par1NBTTagCompound.getBoolean("powered")));

		if (par1NBTTagCompound.hasKey("Fuse", 99))
			fuseTime = par1NBTTagCompound.getShort("Fuse");

		if (par1NBTTagCompound.hasKey("ExplosionRadius", 99))
			explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");

		if (par1NBTTagCompound.getBoolean("ignited"))
			ignite();
	}

	@Override
	public void onUpdate()
	{
		if (isEntityAlive())
		{
			lastActiveTime = timeSinceIgnited;

			if (hasIgnited())
				setCreeperState(1);

			int i = getCreeperState();

			if (i > 0 && timeSinceIgnited == 0)
				playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);

			timeSinceIgnited += i;

			if (timeSinceIgnited < 0)
				timeSinceIgnited = 0;

			if (timeSinceIgnited >= fuseTime)
			{
				timeSinceIgnited = fuseTime;
				explode();
			}
		}

		super.onUpdate();
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_CREEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_CREEPER_DEATH;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntityAntiSkeleton)
		{
			int i = Item.getIdFromItem(Items.RECORD_13);
			int j = Item.getIdFromItem(Items.RECORD_WAIT);
			int k = i + rand.nextInt(j - i + 1);
			dropItem(Item.getItemById(k), 1);
		}
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityCreeper){
			boolean flag = worldObj.getGameRules().getBoolean("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		return true;
	}

	public boolean getPowered()
	{
		return dataManager.get(POWERED).booleanValue();
	}

	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
	 */
	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float par1)
	{
		return (lastActiveTime + (timeSinceIgnited - lastActiveTime) * par1) / (fuseTime - 2);
	}

	@Override
	protected Item getDropItem()
	{
		return Items.GUNPOWDER;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_CREEPER;
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState()
	{
		return dataManager.get(STATE).intValue();
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int state)
	{
		dataManager.set(STATE, Integer.valueOf(state));
	}

	@Override
	public void onStruckByLightning(EntityLightningBolt lightningBolt)
	{
		super.onStruckByLightning(lightningBolt);
		dataManager.set(POWERED, Boolean.valueOf(true));
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand p_184645_2_, ItemStack stack)
	{
		if (stack != null && stack.getItem() == Items.FLINT_AND_STEEL)
		{
			worldObj.playSound(player, posX, posY, posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, rand.nextFloat() * 0.4F + 0.8F);
			player.swingArm(p_184645_2_);

			if (!worldObj.isRemote)
			{
				ignite();
				stack.damageItem(1, player);
				return true;
			}
		}

		return super.processInteract(player, p_184645_2_, stack);
	}

	private void explode()
	{
		if (!worldObj.isRemote){
			boolean flag = worldObj.getGameRules().getBoolean("mobGriefing");
			float f = getPowered() ? 2.0F : 1.0F;
			worldObj.createExplosion(this, posX, posY, posZ, explosionRadius * f, flag);
			setDead();
		}
	}

	public boolean hasIgnited()
	{
		return dataManager.get(IGNITED).booleanValue();
	}

	public void ignite()
	{
		dataManager.set(IGNITED, Boolean.valueOf(true));
	}
}
