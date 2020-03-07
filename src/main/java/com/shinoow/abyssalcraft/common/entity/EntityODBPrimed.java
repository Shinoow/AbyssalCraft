/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import java.util.List;

import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.common.util.ExplosionUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class EntityODBPrimed extends Entity {

	/** How long the fuse is */
	public int fuse;
	private EntityLivingBase odbPlacedBy;

	public EntityODBPrimed(World par1World)
	{
		super(par1World);
		fuse = 200;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
	}

	public EntityODBPrimed(World par1World, double par2, double par4, double par6, EntityLivingBase par8EntityLivingBase)
	{
		this(par1World);
		setPosition(par2, par4, par6);
		float var8 = (float)(Math.random() * Math.PI * 2.0D);
		motionX = -((float)Math.sin(var8)) * 0.02F;
		motionY = 0.20000000298023224D;
		motionZ = -((float)Math.cos(var8)) * 0.02F;
		fuse = 200;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
		odbPlacedBy = par8EntityLivingBase;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !isDead;
	}

	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		move(MoverType.SELF, motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;

		if (onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
			motionY *= -0.5D;
		}

		if (fuse-- <= 0)
		{
			setDead();

			explode();

		} else{
			handleWaterMovement();
			if(ACConfig.particleEntity && !world.isRemote)
				((WorldServer)world).spawnParticle(EnumParticleTypes.PORTAL, true, posX, posY + 0.5D, posZ, 10, 0.0D, 0, 0, 1D);
		}
	}

	private void explode()
	{
		if(!world.isRemote && !ACConfig.no_odb_explosions){
			ACLogger.info("Unleashing hell shortly.");

			ExplosionUtil.newODBExplosion(world, this, posX, posY, posZ, ACConfig.odbExplosionSize, false, true);

			ACLogger.info("Hell successfully unleashed.");
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("Fuse", (byte)fuse);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		fuse = par1NBTTagCompound.getByte("Fuse");
	}

	/**
	 * returns null or the entityliving it was placed or ignited by
	 */
	public EntityLivingBase getODBPlacedBy()
	{
		return odbPlacedBy;
	}

	@Override
	public float getEyeHeight()
	{
		return 0.0F;
	}

	public void finishExplosion(int delay, Explosion explosion) {
		Scheduler.schedule(new ScheduledProcess(delay) {

			@Override
			public void execute() {
				int x, x1, z, z1;
				for(x = 0; x < 9; x++)
					for(z = 0; z < 9; z++)
						for(x1 = 0; x1 < 9; x1++)
							for(z1 = 0; z1 < 9; z1++){
								checkAndReplace(new BlockPos(posX + x, posY, posZ + z), explosion);
								checkAndReplace(new BlockPos(posX - x1, posY, posZ - z1), explosion);
								checkAndReplace(new BlockPos(posX + x, posY, posZ - z1), explosion);
								checkAndReplace(new BlockPos(posX - x1, posY, posZ  + z), explosion);
							}
				EntitySacthoth sacthoth = new EntitySacthoth(world);
				sacthoth.setPosition(posX, posY + 1, posZ);
				sacthoth.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY + 1, posZ)), null);
				world.spawnEntity(sacthoth);
				if(ACConfig.showBossDialogs)
					SpecialTextUtil.SacthothGroup(world, I18n.translateToLocal("message.sacthoth.spawn.1"));
			}

		});
	}
	
	private void checkAndReplace(BlockPos pos, Explosion explosion){

			IBlockState iblockstate = world.getBlockState(pos);

			if(getExplosionResistance(explosion, world, pos, iblockstate) < 600000 && iblockstate.getMaterial() != Material.AIR)
				world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
	}
}
