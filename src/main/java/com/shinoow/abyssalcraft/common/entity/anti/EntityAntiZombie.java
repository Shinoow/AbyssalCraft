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
package com.shinoow.abyssalcraft.common.entity.anti;

import java.util.Calendar;
import java.util.UUID;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.util.ExplosionUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAntiZombie extends EntityMob implements IAntiEntity {

	private static final DataParameter<Boolean> CHILD = EntityDataManager.createKey(EntityAntiZombie.class, DataSerializers.BOOLEAN);
	protected static final IAttribute spawnReinforcementsAttribute = new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D).setDescription("Spawn Reinforcements Chance");
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private final EntityAIBreakDoor breakDoors = new EntityAIBreakDoor(this);
	private boolean canBreak = false;
	private float width = -1.0F;
	private float height;
	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);

	public EntityAntiZombie(World par1World)
	{
		super(par1World);
		((PathNavigateGround)getNavigator()).setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		tasks.addTask(7, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
		setSize(0.6F, 1.8F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		getAttributeMap().registerAttribute(spawnReinforcementsAttribute).setBaseValue(rand.nextDouble() * ForgeModContainer.zombieSummonBaseChance);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(ACConfig.hardcoreMode ? 60.0D : 30.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 6.0D : 3.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(CHILD, false);
	}

	public boolean canBearkDoors()
	{
		return canBreak;
	}

	public void func_146070_a(boolean par1)
	{
		if (canBreak != par1)
		{
			canBreak = par1;

			if (par1)
				tasks.addTask(1, breakDoors);
			else
				tasks.removeTask(breakDoors);
		}
	}

	@Override
	public boolean isChild()
	{
		return dataManager.get(CHILD);
	}

	@Override
	protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
	{
		if (this.isChild())
			experienceValue = (int)(experienceValue * 2.5F);

		return super.getExperiencePoints(par1EntityPlayer);
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean par1)
	{
		dataManager.set(CHILD, par1);

		if (world != null && !world.isRemote)
		{
			IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			iattributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				iattributeinstance.applyModifier(babySpeedBoostModifier);
		}

		this.isChild(par1);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag)
		{
			int i = world.getDifficulty().getId();

			if (getHeldItemMainhand().isEmpty() && isBurning() && rand.nextFloat() < i * 0.3F)
				par1Entity.setFire(2 * i);
		}

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_ZOMBIE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_ZOMBIE_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4Block)
	{
		playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.rotten_anti_flesh;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_ZOMBIE;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (this.isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setBoolean("CanBreakDoors", canBearkDoors());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);

		func_146070_a(par1NBTTagCompound.getBoolean("CanBreakDoors"));
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!world.isRemote && par1Entity instanceof EntityZombie){
			boolean flag = world.getGameRules().getBoolean("mobGriefing");
			if(ACConfig.nuclearAntimatterExplosions)
				ExplosionUtil.newODBExplosion(world, this, posX, posY, posZ, ACConfig.antimatterExplosionSize, true, flag);
			else world.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		Object entity = super.onInitialSpawn(difficulty, par1EntityLivingData);
		float f = difficulty.getClampedAdditionalDifficulty();
		setCanPickUpLoot(ACConfig.hardcoreMode ? true : rand.nextFloat() < 0.55F * f);

		if(ACConfig.hardcoreMode)
			EntityUtil.suitUp(this, false);
		else {
			setEquipmentBasedOnDifficulty(difficulty);
			setEnchantmentBasedOnDifficulty(difficulty);
		}

		if (entity == null)
			entity = new EntityAntiZombie.GroupData(world.rand.nextFloat() < ForgeModContainer.zombieBabyChance, world.rand.nextFloat() < 0.05F, null);

		if (entity instanceof EntityAntiZombie.GroupData)
		{
			EntityAntiZombie.GroupData groupdata = (EntityAntiZombie.GroupData)entity;

			if (groupdata.field_142048_a)
				setChild(true);
		}

		func_146070_a(rand.nextFloat() < f * 0.1F);

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextDouble() * 0.05000000074505806D, 0));
		double d0 = rand.nextDouble() * 1.5D * difficulty.getClampedAdditionalDifficulty();

		if (d0 > 1.0D)
			getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));

		if (rand.nextFloat() < f * 0.05F)
		{
			getEntityAttribute(spawnReinforcementsAttribute).applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 0.25D + 0.5D, 0));
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 3.0D + 1.0D, 2));
			func_146070_a(true);
		}

		EntityUtil.hahaPumpkinGoesBrrr(this, rand);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = world.getCurrentDate();

		attribute.removeModifier(attackDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(attackDamageBoost);

		return (IEntityLivingData)entity;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte par1)
	{
		if (par1 == 16)
			world.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, getSoundCategory(), 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
		else
			super.handleStatusUpdate(par1);
	}

	public void isChild(boolean par1)
	{
		modifySize(par1 ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float par1, float par2)
	{
		boolean flag = width > 0.0F && height > 0.0F;
		width = par1;
		height = par2;

		if (!flag)
			modifySize(1.0F);
	}

	protected final void modifySize(float par1)
	{
		super.setSize(width * par1, height * par1);
	}

	class GroupData implements IEntityLivingData
	{
		public boolean field_142048_a;
		public boolean field_142046_b;
		private GroupData(boolean par2, boolean par3)
		{
			field_142048_a = false;
			field_142046_b = false;
			field_142048_a = par2;
			field_142046_b = par3;
		}

		GroupData(boolean par2, boolean par3, Object par4EntityZombieINNER1)
		{
			this(par2, par3);
		}
	}
}
