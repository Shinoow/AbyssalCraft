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
package com.shinoow.abyssalcraft.common.entity;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCoraliumArrow extends EntityArrow {

	private int field_145791_d = -1;
	private int field_145792_e = -1;
	private int field_145789_f = -1;
	private Block field_145790_g;
	private int inData;
	private boolean inGround;

	private int ticksInGround;
	private int ticksInAir;
	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;

	public EntityCoraliumArrow(World par1World,
			EntityLivingBase par2EntityLivingBase, float par3) {
		super(par1World, par2EntityLivingBase, par3);
	}

	public EntityCoraliumArrow(World par1World,
			EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase, float par4, float par5) {
		super(par1World, par2EntityLivingBase, par3EntityLivingBase, par4, par5);
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
			prevRotationYaw = rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float)(Math.atan2(motionY, f) * 180.0D / Math.PI);
		}

		Block block = worldObj.getBlock(field_145791_d, field_145792_e, field_145789_f);

		if (block.getMaterial() != Material.air)
		{
			block.setBlockBoundsBasedOnState(worldObj, field_145791_d, field_145792_e, field_145789_f);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(worldObj, field_145791_d, field_145792_e, field_145789_f);

			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ)))
				inGround = true;
		}

		if (arrowShake > 0)
			--arrowShake;

		if (inGround)
		{
			int j = worldObj.getBlockMetadata(field_145791_d, field_145792_e, field_145789_f);

			if (block == field_145790_g && j == inData)
			{
				++ticksInGround;

				if (ticksInGround == 1200)
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
			Vec3 vec31 = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingobjectposition = worldObj.func_147447_a(vec31, vec3, false, true, false);
			vec31 = Vec3.createVectorHelper(posX, posY, posZ);
			vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

			if (movingobjectposition != null)
				vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);

			Entity entity = null;
			List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int i;
			float f1;

			for (i = 0; i < list.size(); ++i)
			{
				Entity entity1 = (Entity)list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != shootingEntity || ticksInAir >= 5))
				{
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (movingobjectposition1 != null)
					{
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null)
				movingobjectposition = new MovingObjectPosition(entity);

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !((EntityPlayer)shootingEntity).canAttackPlayer(entityplayer))
					movingobjectposition = null;
			}

			float f2;
			float f4;

			if (movingobjectposition != null)
				if (movingobjectposition.entityHit != null)
				{
					f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					int k = MathHelper.ceiling_double_int(f2 * getDamage());

					if (getIsCritical())
						k += rand.nextInt(k / 2 + 2);

					DamageSource damagesource = null;

					if (shootingEntity == null)
						damagesource = DamageSource.causeArrowDamage(this, this);
					else
						damagesource = DamageSource.causeArrowDamage(this, shootingEntity);

					if (isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
						movingobjectposition.entityHit.setFire(5);

					if (movingobjectposition.entityHit.attackEntityFrom(damagesource, k))
					{
						if (movingobjectposition.entityHit instanceof EntityLivingBase)
						{
							EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

							if(EntityUtil.isEntityCoralium(entitylivingbase)){}
							else entitylivingbase.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 100));

							if (!worldObj.isRemote)
								entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);

							if(entitylivingbase.isDead && entitylivingbase instanceof EntityZombie){
								EntityDepthsGhoul ghoul = new EntityDepthsGhoul(entitylivingbase.worldObj);
								if(entitylivingbase.worldObj.difficultySetting == EnumDifficulty.HARD && entitylivingbase.worldObj.rand.nextBoolean()
										|| entitylivingbase.worldObj.rand.nextInt(8) == 0) {
									ghoul.copyLocationAndAnglesFrom(entitylivingbase);
									ghoul.onSpawnWithEgg((IEntityLivingData)null);
									if(entitylivingbase.isChild())
										ghoul.setChild(true);
									ghoul.setGhoulType(0);
									entitylivingbase.worldObj.removeEntity(entitylivingbase);
									entitylivingbase.worldObj.spawnEntityInWorld(ghoul);
								}
							}

							if (knockbackStrength > 0)
							{
								f4 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

								if (f4 > 0.0F)
									movingobjectposition.entityHit.addVelocity(motionX * knockbackStrength * 0.6000000238418579D / f4, 0.1D, motionZ * knockbackStrength * 0.6000000238418579D / f4);
							}

							if (shootingEntity != null && shootingEntity instanceof EntityLivingBase)
							{
								EnchantmentHelper.func_151384_a(entitylivingbase, shootingEntity);
								EnchantmentHelper.func_151385_b((EntityLivingBase)shootingEntity, entitylivingbase);
							}

							if (shootingEntity != null && movingobjectposition.entityHit != shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP)
								((EntityPlayerMP)shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
						}

						playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

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
					field_145791_d = movingobjectposition.blockX;
					field_145792_e = movingobjectposition.blockY;
					field_145789_f = movingobjectposition.blockZ;
					field_145790_g = block;
					inData = worldObj.getBlockMetadata(field_145791_d, field_145792_e, field_145789_f);
					motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
					motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
					motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
					f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / f2 * 0.05000000074505806D;
					posY -= motionY / f2 * 0.05000000074505806D;
					posZ -= motionZ / f2 * 0.05000000074505806D;
					playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					inGround = true;
					arrowShake = 7;
					setIsCritical(false);

					if (field_145790_g.getMaterial() != Material.air)
						field_145790_g.onEntityCollidedWithBlock(worldObj, field_145791_d, field_145792_e, field_145789_f, this);
				}

			if (getIsCritical())
				for (i = 0; i < 4; ++i)
					worldObj.spawnParticle("crit", posX + motionX * i / 4.0D, posY + motionY * i / 4.0D, posZ + motionZ * i / 4.0D, -motionX, -motionY + 0.2D, -motionZ);

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

			for (rotationPitch = (float)(Math.atan2(motionY, f2) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
				;

			while (rotationPitch - prevRotationPitch >= 180.0F)
				prevRotationPitch += 360.0F;

			while (rotationYaw - prevRotationYaw < -180.0F)
				prevRotationYaw -= 360.0F;

			while (rotationYaw - prevRotationYaw >= 180.0F)
				prevRotationYaw += 360.0F;

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float f3 = 0.99F;
			f1 = 0.05F;

			if (isInWater())
			{
				for (int l = 0; l < 4; ++l)
				{
					f4 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * f4, posY - motionY * f4, posZ - motionZ * f4, motionX, motionY, motionZ);
				}

				f3 = 0.8F;
			}

			if (isWet())
				extinguish();

			motionX *= f3;
			motionY *= f3;
			motionZ *= f3;
			motionY -= f1;
			setPosition(posX, posY, posZ);
			func_145775_I();
		}
	}
}
