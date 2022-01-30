/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.world.TeleporterDarkRealm;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlackHole extends Entity
{
	public EntityJzahar shootingEntity;

	public EntityBlackHole(World worldIn)
	{
		super(worldIn);
		preventEntitySpawning = true;
		setSize(2.0F, 2.0F);
	}

	public EntityBlackHole(World worldIn, EntityJzahar entity)
	{
		this(worldIn);
		shootingEntity = entity;
		copyLocationAndAnglesFrom(entity);
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit(){}

	double speed = 0.05D;

	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (ticksExisted > 300)
			setDead();

		for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(48D)))
			if(!(entity instanceof IOmotholEntity))
				if (entity.isEntityAlive() && entity instanceof EntityLivingBase){
					if (entity.posY < posY)
						entity.motionY += 0.025F;
					double d1 = 7D;
					double d2 = posX + (rand.nextDouble() * 8D - 4D) - entity.posX;
					double d3 = posY + (rand.nextDouble() * 8D - 4D) - entity.posY;
					double d4 = posZ + (rand.nextDouble() * 8D - 4D) - entity.posZ;
					double d5 = d2 * d2 + d3 * d3 + d4 * d4;
					entity.addVelocity(d2 / d5 * d1 * speed, d3 / d5 * d1 * speed, d4 / d5 * d1 * speed);
					//					if (entity.getDistanceSq(this) <= 36D && shootingEntity != null)
					//						entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 4.0F);
					if (entity.getDistanceSq(this) <= 9D && shootingEntity != null && !world.isRemote){

						EntityLivingBase livingBase = (EntityLivingBase)entity;

						int id = InitHandler.INSTANCE.getRandomDimension(livingBase.dimension, rand);

						if(livingBase.timeUntilPortal <= 0){
							if(livingBase instanceof EntityPlayerMP){
								EntityPlayerMP thePlayer = (EntityPlayerMP)livingBase;

								thePlayer.timeUntilPortal = ACConfig.portalCooldown;
								if(ForgeHooks.onTravelToDimension(thePlayer, id)) {
									thePlayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 80, 255));
									thePlayer.setPosition(rand.nextInt(100), 80, rand.nextInt(100));
									thePlayer.server.getPlayerList().transferPlayerToDimension(thePlayer, id, new TeleporterDarkRealm(thePlayer.server.getWorld(id)));
								}
							} else {
								MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
								livingBase.timeUntilPortal = livingBase.getPortalCooldown();

								if(ForgeHooks.onTravelToDimension(livingBase, id)) {

									int i = livingBase.dimension;

									livingBase.dimension = ACLib.omothol_id;
									world.removeEntityDangerously(livingBase);

									livingBase.isDead = false;

									livingBase.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 80, 255));
									livingBase.setPosition(rand.nextInt(100), 80, rand.nextInt(100));
									server.getPlayerList().transferEntityToWorld(livingBase, i, server.getWorld(i), server.getWorld(id), new TeleporterDarkRealm(server.getWorld(id)));

								}

							}
						} else livingBase.timeUntilPortal = livingBase.getPortalCooldown();

						//						((EntityLivingBase)entity).setHealth(((EntityLivingBase)entity).getHealth() - 20F);
						//						entity.attackEntityFrom(DamageSource.CRAMMING.setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute(), 50F);

						//						if (!entity.isEntityAlive()){
						//							if (entity instanceof EntityLiving)
						//								((EntityLiving)entity).spawnExplosionParticle();
						//							entity.setDead();
						//						}
					}
				} else if (!(entity instanceof EntityBlackHole) && !(entity instanceof EntityImplosion)){
					double d1 = 7D;
					double d2 = posX + (rand.nextDouble() * 4D - 2D) - entity.posX;
					double d3 = posY + (rand.nextDouble() * 4D - 2D) - entity.posY;
					double d4 = posZ + (rand.nextDouble() * 4D - 2D) - entity.posZ;
					double d5 = d2 * d2 + d3 * d3 + d4 * d4;
					entity.setPosition(entity.posX, entity.posY, entity.posZ);
					entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
					world.setEntityState(entity, (byte)2);
					if (entity.getDistanceSq(this) <= 4D)
						entity.setDead();
				}

		speed += 0.0005;

		int i = MathHelper.floor(posY);
		int i1 = MathHelper.floor(posX);
		int j1 = MathHelper.floor(posZ);
		for (int l1 = -6; l1 <= 6; l1++)
			for (int i2 = -6; i2 <= 6; i2++)
				for (int j = -6; j <= 6; j++)
				{
					int j2 = i1 + l1;
					int k = i + j;
					int l = j1 + i2;
					BlockPos pos = new BlockPos(j2, k, l);
					IBlockState state = world.getBlockState(pos);
					if (!state.getBlock().isAir(state, world, pos) && rand.nextInt(10) == 0 && !world.isRemote && world.isAreaLoaded(getPosition().add(-32, -32, -32), getPosition().add(32, 32, 32)) && state.getBlockHardness(world, pos) != -1)
						if (state.getMaterial().isLiquid())
							world.setBlockToAir(new BlockPos(j2, k, l));
						else
							world.spawnEntity(new EntityFallingBlock(world, j2, k + 0.5D, l, state));
				}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound){}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound){}

	@Override
	public EnumPushReaction getPushReaction()
	{
		return EnumPushReaction.IGNORE;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		return true;
	}
}
