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
package com.shinoow.abyssalcraft.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class EntityCoraliumArrow extends EntityArrow {

	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private Block inTile;
	private int inData;
	private boolean inGround;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;
	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;

	public EntityCoraliumArrow(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}

	public EntityCoraliumArrow(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityCoraliumArrow(World par1World) {
		super(par1World);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float)(MathHelper.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float)(MathHelper.atan2(motionY, f) * 180.0D / Math.PI);
		}

		BlockPos blockpos = new BlockPos(xTile, yTile, zTile);
		IBlockState iblockstate = worldObj.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (block.getMaterial(iblockstate) != Material.air)
		{
			//			block.setBlockBoundsBasedOnState(worldObj, blockpos);
			AxisAlignedBB axisalignedbb = block.getBoundingBox(iblockstate, worldObj, blockpos);

			if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3d(posX, posY, posZ)))
				inGround = true;
		}

		if (arrowShake > 0)
			--arrowShake;

		if (inGround)
		{
			int j = block.getMetaFromState(iblockstate);

			if (block == inTile && j == inData)
			{
				++ticksInGround;

				if (ticksInGround >= 1200)
					setDead();
			}
			else
			{
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
			}
		}
		else
		{
			++ticksInAir;
			Vec3d vec31 = new Vec3d(posX, posY, posZ);
			Vec3d vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
			RayTraceResult movingobjectposition = worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
			vec31 = new Vec3d(posX, posY, posZ);
			vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

			if (movingobjectposition != null)
				vec3 = new Vec3d(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);

			Entity entity = null;
			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;

			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity1 = list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != shootingEntity || ticksInAir >= 5))
				{
					float f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(f1, f1, f1);
					RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (movingobjectposition1 != null)
					{
						double d1 = vec31.squareDistanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null)
				movingobjectposition = new RayTraceResult(entity);

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !((EntityPlayer)shootingEntity).canAttackPlayer(entityplayer))
					movingobjectposition = null;
			}

			if (movingobjectposition != null)
				if (movingobjectposition.entityHit != null)
				{
					float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					int l = MathHelper.ceiling_double_int(f2 * damage);

					if (getIsCritical())
						l += rand.nextInt(l / 2 + 2);

					DamageSource damagesource;

					if (shootingEntity == null)
						damagesource = DamageSource.causeArrowDamage(this, this);
					else
						damagesource = DamageSource.causeArrowDamage(this, shootingEntity);

					if (isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
						movingobjectposition.entityHit.setFire(5);

					if (movingobjectposition.entityHit.attackEntityFrom(damagesource, l))
					{
						if (movingobjectposition.entityHit instanceof EntityLivingBase)
						{
							EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

							if(EntityUtil.isEntityCoralium(entitylivingbase)){}
							else entitylivingbase.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague, 100));

							if (!worldObj.isRemote)
								entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);

							if(entitylivingbase.isDead && entitylivingbase instanceof EntityZombie){
								EntityDepthsGhoul ghoul = new EntityDepthsGhoul(entitylivingbase.worldObj);
								if(entitylivingbase.worldObj.getDifficulty() == EnumDifficulty.HARD && entitylivingbase.worldObj.rand.nextBoolean()
										|| entitylivingbase.worldObj.rand.nextInt(8) == 0) {
									ghoul.copyLocationAndAnglesFrom(entitylivingbase);
									ghoul.onInitialSpawn(worldObj.getDifficultyForLocation(movingobjectposition.getBlockPos()), (IEntityLivingData)null);
									if(entitylivingbase.isChild())
										ghoul.setChild(true);
									ghoul.setGhoulType(0);
									entitylivingbase.worldObj.removeEntity(entitylivingbase);
									entitylivingbase.worldObj.spawnEntityInWorld(ghoul);
								}
							}

							if (!worldObj.isRemote)
								entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);

							if (knockbackStrength > 0)
							{
								float f7 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

								if (f7 > 0.0F)
									movingobjectposition.entityHit.addVelocity(motionX * knockbackStrength * 0.6000000238418579D / f7, 0.1D, motionZ * knockbackStrength * 0.6000000238418579D / f7);
							}

							if (shootingEntity instanceof EntityLivingBase)
							{
								EnchantmentHelper.applyThornEnchantments(entitylivingbase, shootingEntity);
								EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)shootingEntity, entitylivingbase);
							}

							if (shootingEntity != null && movingobjectposition.entityHit != shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP)
								((EntityPlayerMP)shootingEntity).playerNetServerHandler.sendPacket(new SPacketChangeGameState(6, 0.0F));
						}

						playSound(SoundEvents.entity_arrow_hit_player, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

						if (!(movingobjectposition.entityHit instanceof EntityEnderman))
							setDead();
					}
					else
					{
						motionX *= -0.10000000149011612D;
						motionY *= -0.10000000149011612D;
						motionZ *= -0.10000000149011612D;
						rotationYaw += 180.0F;
						prevRotationYaw += 180.0F;
						ticksInAir = 0;
					}
				}
				else
				{
					BlockPos blockpos1 = movingobjectposition.getBlockPos();
					xTile = blockpos1.getX();
					yTile = blockpos1.getY();
					zTile = blockpos1.getZ();
					IBlockState iblockstate1 = worldObj.getBlockState(blockpos1);
					inTile = iblockstate1.getBlock();
					inData = inTile.getMetaFromState(iblockstate1);
					motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
					motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
					motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
					float f5 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / f5 * 0.05000000074505806D;
					posY -= motionY / f5 * 0.05000000074505806D;
					posZ -= motionZ / f5 * 0.05000000074505806D;
					playSound(SoundEvents.entity_arrow_hit, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					inGround = true;
					arrowShake = 7;
					setIsCritical(false);

					if (inTile.getMaterial(iblockstate1) != Material.air)
						inTile.onEntityCollidedWithBlock(worldObj, blockpos1, iblockstate1, this);
				}

			if (getIsCritical())
				for (int k = 0; k < 4; ++k)
					worldObj.spawnParticle(EnumParticleTypes.CRIT, posX + motionX * k / 4.0D, posY + motionY * k / 4.0D, posZ + motionZ * k / 4.0D, -motionX, -motionY + 0.2D, -motionZ, new int[0]);

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float)(MathHelper.atan2(motionX, motionZ) * 180.0D / Math.PI);

			for (rotationPitch = (float)(MathHelper.atan2(motionY, f3) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
				;

			while (rotationPitch - prevRotationPitch >= 180.0F)
				prevRotationPitch += 360.0F;

			while (rotationYaw - prevRotationYaw < -180.0F)
				prevRotationYaw -= 360.0F;

			while (rotationYaw - prevRotationYaw >= 180.0F)
				prevRotationYaw += 360.0F;

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float f4 = 0.99F;
			float f6 = 0.05F;

			if (isInWater())
			{
				for (int i1 = 0; i1 < 4; ++i1)
				{
					float f8 = 0.25F;
					worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * f8, posY - motionY * f8, posZ - motionZ * f8, motionX, motionY, motionZ, new int[0]);
				}

				f4 = 0.6F;
			}

			if (isWet())
				extinguish();

			motionX *= f4;
			motionY *= f4;
			motionZ *= f4;
			motionY -= f6;
			setPosition(posX, posY, posZ);
			doBlockCollisions();
		}
	}

	@Override
	protected ItemStack getArrowStack() {

		return new ItemStack(Items.arrow);
	}
}