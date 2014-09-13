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
package com.shinoow.abyssalcraft.common.entity.anti;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.core.api.entity.AntiAnimal;

public class EntityAntiPig extends AntiAnimal {

	public EntityAntiPig(World par1World)
	{
		super(par1World);
		setSize(0.9F, 0.9F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		tasks.addTask(2, new EntityAIControlledByPlayer(this, 0.3F));
		tasks.addTask(3, new EntityAIMate(this, 1.0D));
		tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot_on_a_stick, false));
		tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
		tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
	}

	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.pig.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.pig.say";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.pig.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4Block)
	{
		playSound("mob.pig.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return isBurning() ? AbyssalCraft.antiPork : AbyssalCraft.antiPork;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int j = rand.nextInt(3) + 1 + rand.nextInt(1 + par2);

		for (int k = 0; k < j; ++k)
			if (isBurning())
				dropItem(AbyssalCraft.antiPork, 1);
			else
				dropItem(AbyssalCraft.antiPork, 1);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityPig){
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public EntityAntiPig createChild(EntityAgeable par1EntityAgeable)
	{
		return new EntityAntiPig(worldObj);
	}

	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack != null && par1ItemStack.getItem() == Items.carrot;
	}
}