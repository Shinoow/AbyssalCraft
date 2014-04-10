package com.shinoow.abyssalcraft.common.entity;

import java.util.Calendar;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.client.FMLClientHandler;

public class EntityJzahar extends EntityMob implements IBossDisplayData, IRangedAttackMob
{

	private static final ItemStack defaultHeldItem;

	public EntityJzahar(World par1World)
	{
		super(par1World);
		this.setSize(1.8F, 4.0F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		this.tasks.addTask(3, new EntityAIWander(this, 0.35D));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(6, new EntityAIArrowAttack(this, 0.35D, 40, 20.0F));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDragon.class, 0, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityWither.class, 0, true));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		Calendar calendar = this.worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
		{
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
		}
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	public String getEntityName()
	{
		return "\u00a79J'zahar, Gatekeeper of The Abyss";
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
		return "mob.enderdragon.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.wither.death";
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		this.dropItem(AbyssalCraft.Staff, 1);

	}

	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

//		EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
//		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
//		if(par1DamageSource.getEntity() instanceof EntityPlayer && itemstack.getItem() == AbyssalCraft.devsword)
//		{
//			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("J'zahar: Pfft, you and your Dev Blade Shin... wait, are you even Shinoow?"));
//		}
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("J'zahar: I'm still an in-dev boss, so you didn't exactly achieve anything killing me."));
	}

	@Override
	public ItemStack getHeldItem()
	{
		return defaultHeldItem;
	}
	static
	{
		defaultHeldItem = new ItemStack(AbyssalCraft.Staff, 1);
	}

	private double func_82214_u(int par1)
	{
		if (par1 <= 0)
		{
			return this.posX;
		}
		else
		{
			float f = (this.renderYawOffset + (float)(180 * (par1 - 1))) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.cos(f);
			return this.posX + (double)f1 * 1.3D;
		}
	}

	private double func_82208_v(int par1)
	{
		return par1 <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
	}

	private double func_82213_w(int par1)
	{
		if (par1 <= 0)
		{
			return this.posZ;
		}
		else
		{
			float f = (this.renderYawOffset + (float)(180 * (par1 - 1))) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.sin(f);
			return this.posZ + (double)f1 * 1.3D;
		}
	}

	private void func_82216_a(int par1, EntityLivingBase par2EntityLivingBase)
	{
		this.func_82209_a(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() * 0.35D, par2EntityLivingBase.posZ, par1 == 0 && this.rand.nextFloat() < 0.001F);
	}

	private void func_82209_a(int par1, double par2, double par4, double par6, boolean par8)
	{
		this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		double d3 = this.func_82214_u(par1);
		double d4 = this.func_82208_v(par1);
		double d5 = this.func_82213_w(par1);
		double d6 = par2 - d3;
		double d7 = par4 - d4;
		double d8 = par6 - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.worldObj, this, d6, d7, d8);

		if (par8)
		{
			entitywitherskull.setInvulnerable(true);
		}

		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		this.worldObj.spawnEntityInWorld(entitywitherskull);
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	 public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
	{
		this.func_82216_a(0, par1EntityLivingBase);
	}

}
