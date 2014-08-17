package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class EntitySkeletonGoliath extends EntityMob {

	public EntitySkeletonGoliath(World par1World) {
		super(par1World);
		setSize(1.6F, 4.5F);
		tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(2, new EntityAIWander(this, 0.35D));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(5, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsZombie.class, 6.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityZombie.class, 6.0F));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityDepthsghoul.class, 6.0F));
		tasks.addTask(10, new EntityAIWatchClosest(this, EntitySkeleton.class, 6.0F));
		tasks.addTask(11, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 6.0F));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));

		EntityPlayer entityPlayer = par1World.getClosestPlayerToEntity(this, 30D);
		if(entityPlayer !=null && !entityPlayer.getCommandSenderName().equals("shinoow") ||
				entityPlayer !=null && !entityPlayer.getCommandSenderName().equals("Oblivionaire"))
			targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		playSound("mob.skeleton.step", 0.15F, 1.0F);
	}

	@Override
	public void onLivingUpdate()
	{
		if (worldObj.isDaytime() && !worldObj.isRemote && worldObj.provider.dimensionId != AbyssalCraft.configDimId1)
		{
			float f = getBrightness(1.0F);

			if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)))
			{
				boolean flag = true;

				if (flag)
					setFire(8);
			}
		}
		super.onLivingUpdate();
	}
}