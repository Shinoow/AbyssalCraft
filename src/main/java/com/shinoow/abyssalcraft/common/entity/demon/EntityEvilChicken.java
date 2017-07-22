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
package com.shinoow.abyssalcraft.common.entity.demon;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityEvilChicken extends EntityMob implements IShearable {

	public float field_70886_e;
	public float destPos;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;

	public EntityEvilChicken(World par1World) {
		super(par1World);
		setSize(0.3F, 0.7F);
		isImmuneToFire = true;
		double var2 = 0.35D;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, var2, true));
		tasks.addTask(3, new EntityAIWander(this, var2));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	public String getName()
	{
		return I18n.translateToLocal("entity.Chicken.name");
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_CHICKEN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_CHICKEN_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public void fall(float p_70069_1_, float par2) {}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		field_70888_h = field_70886_e;
		field_70884_g = destPos;
		destPos = (float)(destPos + (onGround ? -1 : 4) * 0.3D);

		if (destPos < 0.0F)
			destPos = 0.0F;

		if (destPos > 1.0F)
			destPos = 1.0F;

		if (!onGround && field_70889_i < 1.0F)
			field_70889_i = 1.0F;

		field_70889_i = (float)(field_70889_i * 0.9D);

		if (!onGround && motionY < 0.0D)
			motionY *= 0.6D;

		field_70886_e += field_70889_i * 2.0F;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if(!world.isRemote)
			if(!(par1DamageSource.getTrueSource() instanceof EntityLesserShoggoth))
			{
				EntityDemonChicken demonchicken = new EntityDemonChicken(world);
				demonchicken.copyLocationAndAnglesFrom(this);
				world.removeEntity(this);
				demonchicken.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
				world.spawnEntity(demonchicken);
			}
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_EVIL_CHICKEN;
	}

	@Override public boolean isShearable(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
	@Override
	public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess w, BlockPos pos, int fortune)
	{
		int i = 1 + rand.nextInt(3);

		java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
		for (int j = 0; j < i; ++j)
			ret.add(new ItemStack(Items.FEATHER));

		playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
		playSound(SoundEvents.ENTITY_GHAST_HURT, 1.0F, 0.2F);
		if(!world.isRemote){
			EntityDemonChicken demonchicken = new EntityDemonChicken(world);
			demonchicken.copyLocationAndAnglesFrom(this);
			world.removeEntity(this);
			demonchicken.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
			world.spawnEntity(demonchicken);
		}
		return ret;
	}
}
