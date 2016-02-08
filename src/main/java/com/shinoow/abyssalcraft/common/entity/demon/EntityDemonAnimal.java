/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

public class EntityDemonAnimal extends EntityMob implements IDreadEntity {

	private boolean canBurn = false;

	public EntityDemonAnimal(World par1World)
	{
		super(par1World);
		((PathNavigateGround)getNavigator()).setAvoidsWater(true);
		isImmuneToFire = true;
		double var2 = 0.35D;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, var2, true));
		tasks.addTask(3, new EntityAIWander(this, var2));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
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
			BlockPos pos = new BlockPos(i, j, k);

			if (worldObj.provider.getDimensionId() != AbyssalCraft.configDimId2 && worldObj.provider.getDimensionId() != 0 && worldObj.getBlockState(pos).getBlock().getMaterial() == Material.air &&
					worldObj.getBiomeGenForCoords(pos).getFloatTemperature(pos) < 10.0F && Blocks.fire.canPlaceBlockAt(worldObj, pos) || canBurn == true &&
					worldObj.getBlockState(pos).getBlock().getMaterial() == Material.air && worldObj.getBiomeGenForCoords(pos).getFloatTemperature(pos) < 10.0F && Blocks.fire.canPlaceBlockAt(worldObj, pos))
				worldObj.setBlockState(pos, Blocks.fire.getDefaultState());
		}
	}

	@Override
	protected void dropFewItems(boolean par1, int par2){
		int var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + par2);
		for (int var4 = 0; var4 < var3; ++var4)
			dropItem(Items.rotten_flesh, 1);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);

		if(worldObj.provider.getDimensionId() == 0 && AbyssalCraft.demonAnimalFire == true && rand.nextInt(3) == 0)
			canBurn = true;

		return (IEntityLivingData)data;
	}
}
