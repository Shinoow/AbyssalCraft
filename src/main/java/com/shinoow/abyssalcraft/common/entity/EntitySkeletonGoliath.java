package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class EntitySkeletonGoliath extends EntityMob {

	public EntitySkeletonGoliath(World par1World) {
		super(par1World);
		setSize(1.6F, 4.5F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsghoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
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
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
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
	protected Item getDropItem()
	{
		return Items.bone;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		dropItem(AbyssalCraft.cudgel, 1);
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