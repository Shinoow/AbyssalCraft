/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.items.armor.ItemEthaxiumArmor;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAcidProjectile extends EntityThrowable {

	int damage = 0;

	public EntityAcidProjectile(World p_i1776_1_) {
		super(p_i1776_1_);
	}

	public EntityAcidProjectile(World p_i1777_1_, EntityLivingBase p_i1777_2_) {
		super(p_i1777_1_, p_i1777_2_);
		damage = p_i1777_2_.isChild() ? 3 : 6;
	}

	public EntityAcidProjectile(World p_i1778_1_, double p_i1778_2_,
		double p_i1778_4_, double p_i1778_6_) {
		super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
	}

	@Override
	public void onUpdate()
	{
		if(ACConfig.particleEntity)
			for(int i = 0; i < 4; i++){
				worldObj.spawnParticle(EnumParticleTypes.SLIME, posX - motionX * 0.25F + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * 0.25F - 0.5D, posZ - motionZ * 0.25F + rand.nextDouble() * 0.6D - 0.3D, 0, 0, 0, new int[0]);
				worldObj.spawnParticle(EnumParticleTypes.SLIME, posX - motionX * 0.25F + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * 0.25F - 0.5D, posZ - motionZ * 0.25F + rand.nextDouble() * 0.6D - 0.3D, 0, 0, 0, new int[0]);
			}
		super.onUpdate();
	}

	@Override
	protected void onImpact(RayTraceResult mop) {

		if (isBeingRidden() && isEntityInsideOpaqueBlock())
		{
			for (Entity entity : getPassengers()){
				entity.copyLocationAndAnglesFrom(this);
				entity.motionX += rand.nextGaussian();
				entity.motionY += 0.5D;
				entity.motionZ += rand.nextGaussian();
			}
			setDead();
		}

		if(!worldObj.isRemote) {
			if(mop.entityHit != null) {

				if(ACConfig.hardcoreMode && mop.entityHit instanceof EntityPlayer)
					mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()).setDamageBypassesArmor().setDamageIsAbsolute(), 1F);

				if(mop.entityHit instanceof EntityLivingBase && !(mop.entityHit instanceof EntityLesserShoggoth)){

					EntityLivingBase entity = (EntityLivingBase)mop.entityHit;

					if(!canEntityBlockDamageSource(DamageSource.causeThrownDamage(this, getThrower()), entity) || !ACConfig.shieldsBlockAcid) {
						if (mop.entityHit.attackEntityFrom(AbyssalCraftAPI.acid, damage))
						{
							for(ItemStack armor : mop.entityHit.getArmorInventoryList())
								if(armor != null && !(armor.getItem() instanceof ItemEthaxiumArmor))
									armor.damageItem(damage + damage == 3 ? 1 : 2, (EntityLivingBase)mop.entityHit);
							BlockPos pos = getPosition();
							BlockPos pos1 = mop.entityHit.getPosition();

							Vec3d vec = new Vec3d(pos1.subtract(pos)).normalize();

							double d = Math.sqrt(pos1.distanceSq(pos));

							for(int i = 0; i < d * pos.getDistance(pos1.getX(), pos1.getY(), pos1.getZ()); i++){
								double i1 = i / pos.getDistance(pos1.getX(), pos1.getY(), pos1.getZ());
								double xp = pos.getX() + vec.xCoord * i1 + .5;
								double yp = pos.getY() + vec.yCoord * i1 + .5;
								double zp = pos.getZ() + vec.zCoord * i1 + .5;
								BlockPos pos2 = new BlockPos(xp, yp, zp);
								if(!worldObj.isAirBlock(pos2) && worldObj.getBlockState(pos2).getBlockHardness(worldObj, pos2) < ACConfig.acidResistanceHardness && worldObj.getBlockState(pos2).getBlock() != ACBlocks.shoggoth_ooze
									&& worldObj.getBlockState(pos2).getBlock() != ACBlocks.monolith_stone
									&& worldObj.getBlockState(pos2).getBlock() != ACBlocks.shoggoth_biomass && !worldObj.getBlockState(pos).getBlock().hasTileEntity(worldObj.getBlockState(pos)))
									worldObj.destroyBlock(pos2, false);
							}
						}
					} else {
						ItemStack shield = entity.getActiveItemStack();
						if(shield != null && shield.getItem() instanceof ItemShield) {
							shield.damageItem(damage*2, entity);
							entity.attackEntityFrom(AbyssalCraftAPI.acid, 1);
						}
					}

					setDead();
				}
			}
			if(mop.typeOfHit == Type.BLOCK) {
				BlockPos pos = mop.getBlockPos();
				if(!worldObj.isAirBlock(pos) && worldObj.getBlockState(pos).getBlockHardness(worldObj, pos) < 10 && worldObj.getBlockState(pos).getBlock() != ACBlocks.shoggoth_ooze
					&& worldObj.getBlockState(pos).getBlock() != ACBlocks.monolith_stone
					&& worldObj.getBlockState(pos).getBlock() != ACBlocks.shoggoth_biomass && !worldObj.getBlockState(pos).getBlock().hasTileEntity(worldObj.getBlockState(pos)))
					worldObj.destroyBlock(pos, false);
				setDead();
			}
		}
	}

	private boolean canEntityBlockDamageSource(DamageSource damageSourceIn, EntityLivingBase target)
	{
		if (!damageSourceIn.isUnblockable() && target.isActiveItemStackBlocking())
		{
			Vec3d vec3d = damageSourceIn.getDamageLocation();

			if (vec3d != null)
			{
				Vec3d vec3d1 = target.getLook(1.0F);
				Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(target.posX, target.posY, target.posZ)).normalize();
				vec3d2 = new Vec3d(vec3d2.xCoord, 0.0D, vec3d2.zCoord);

				if (vec3d2.dotProduct(vec3d1) < 0.0D)
					return true;
			}
		}

		return false;
	}
}
