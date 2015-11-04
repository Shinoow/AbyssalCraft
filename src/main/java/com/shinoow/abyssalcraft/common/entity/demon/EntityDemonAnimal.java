/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.demon;

import net.minecraft.block.material.Material;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

public class EntityDemonAnimal extends EntityMob implements IDreadEntity {

	private boolean canBurn = false;

	public EntityDemonAnimal(World par1World)
	{
		super(par1World);
		getNavigator().setAvoidsWater(true);
		isImmuneToFire = true;
		double var2 = 0.35D;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, var2, true));
		tasks.addTask(3, new EntityAIWander(this, var2));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected float getSoundPitch()
	{
		return 0.2F;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);

		for (int l = 0; l < 4; ++l)
		{
			i = MathHelper.floor_double(posX + (l % 2 * 2 - 1) * 0.25F);
			j = MathHelper.floor_double(posY);
			k = MathHelper.floor_double(posZ + (l / 2 % 2 * 2 - 1) * 0.25F);

			if (worldObj.provider.dimensionId != AbyssalCraft.configDimId2 && worldObj.provider.dimensionId != 0 && worldObj.getBlock(i, j, k).getMaterial() == Material.air &&
					worldObj.getBiomeGenForCoords(i, k).getFloatTemperature(i, j, k) < 10.0F && Blocks.fire.canPlaceBlockAt(worldObj, i, j, k) || canBurn == true &&
					worldObj.getBlock(i, j, k).getMaterial() == Material.air && worldObj.getBiomeGenForCoords(i, k).getFloatTemperature(i, j, k) < 10.0F && Blocks.fire.canPlaceBlockAt(worldObj, i, j, k))
				worldObj.setBlock(i, j, k, Blocks.fire);
		}
	}

	@Override
	protected void dropFewItems(boolean par1, int par2){
		int var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + par2);
		for (int var4 = 0; var4 < var3; ++var4)
			dropItem(Items.rotten_flesh, 1);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onSpawnWithEgg(par1EntityLivingData);

		if(worldObj.provider.dimensionId == 0 && AbyssalCraft.demonAnimalFire == true && rand.nextInt(3) == 0)
			canBurn = true;

		return (IEntityLivingData)data;
	}
}
