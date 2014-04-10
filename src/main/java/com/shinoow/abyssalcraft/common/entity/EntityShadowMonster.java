package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class EntityShadowMonster extends EntityMob {

	public EntityShadowMonster(World par1World) {
		super(par1World);
		this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.35D));
        this.tasks.addTask(2, new EntityAIWander(this, 0.35D));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(5, new EntityAIFleeSun(this, 0.35D));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	protected void applyEntityAttributes()
    {
            super.applyEntityAttributes();
            // Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
            // Follow Range - default 32.0D - min 0.0D - max 2048.0D
            this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
            // Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
            this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);
            // Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
            // Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }
	
	protected boolean isAIEnabled()
	{
	         return true;
	}
	
	protected String getLivingSound()
	{
	    return "mob.blaze.breathe";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
	    return "mob.blaze.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
	    return "mob.blaze.death";
	}
	
	protected Item getDropItem()
	{
	    return AbyssalCraft.shadowshard;
	    
	}
	
	public void onLivingUpdate()
    {
		for (int i = 0; i < 2; ++i)
        {
            this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
		
		super.onLivingUpdate();
    }
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
	    return EnumCreatureAttribute.UNDEFINED;
	}
}
