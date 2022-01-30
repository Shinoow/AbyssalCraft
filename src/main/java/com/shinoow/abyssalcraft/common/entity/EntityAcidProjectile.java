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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.items.armor.ItemEthaxiumArmor;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
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
				world.spawnParticle(EnumParticleTypes.SLIME, posX - motionX * 0.25F + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * 0.25F - 0.5D, posZ - motionZ * 0.25F + rand.nextDouble() * 0.6D - 0.3D, 0, 0, 0, new int[0]);
				world.spawnParticle(EnumParticleTypes.SLIME, posX - motionX * 0.25F + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * 0.25F - 0.5D, posZ - motionZ * 0.25F + rand.nextDouble() * 0.6D - 0.3D, 0, 0, 0, new int[0]);
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

		if(!world.isRemote) {
			if(mop.entityHit != null) {

				if(ACConfig.hardcoreMode && mop.entityHit instanceof EntityPlayer)
					mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()).setDamageBypassesArmor().setDamageIsAbsolute(), 1F);

				if(mop.entityHit instanceof EntityLivingBase && !(mop.entityHit instanceof EntityShoggothBase)){

					EntityLivingBase entity = (EntityLivingBase)mop.entityHit;

					if(!EntityUtil.canEntityBlockDamageSource(DamageSource.causeThrownDamage(this, thrower), entity) || !ACConfig.shieldsBlockAcid) {
						if (entity.attackEntityFrom(AbyssalCraftAPI.acid, damage))
						{
							for(ItemStack armor : entity.getArmorInventoryList())
								if(!(armor.getItem() instanceof ItemEthaxiumArmor))
									armor.damageItem(damage + damage == 3 ? 1 : 2, entity);
							BlockPos pos = getPosition();
							BlockPos pos1 = entity.getPosition();

							Vec3d vec = new Vec3d(pos1.subtract(pos)).normalize();

							double d = Math.sqrt(pos1.distanceSq(pos));

							if(!ACConfig.no_acid_breaking_blocks)
								if(world.getGameRules().getBoolean("mobGriefing"))
									for(int i = 0; i < d * pos.getDistance(pos1.getX(), pos1.getY(), pos1.getZ()); i++){
										double i1 = i / pos.getDistance(pos1.getX(), pos1.getY(), pos1.getZ());
										double xp = pos.getX() + vec.x * i1 + .5;
										double yp = pos.getY() + vec.y * i1 + .5;
										double zp = pos.getZ() + vec.z * i1 + .5;
										BlockPos pos2 = new BlockPos(xp, yp, zp);
										if(!world.isAirBlock(pos2) && world.getBlockState(pos2).getBlockHardness(world, pos2) < ACConfig.acidResistanceHardness && world.getBlockState(pos2).getBlock() != ACBlocks.shoggoth_ooze
												&& world.getBlockState(pos2) != ACBlocks.monolith_stone.getDefaultState()
												&& world.getBlockState(pos2).getBlock() != ACBlocks.shoggoth_biomass && !world.getBlockState(pos2).getBlock().hasTileEntity(world.getBlockState(pos2))
												&& world.getBlockState(pos2).getBlockHardness(world, pos2) != -1 && world.getBlockState(pos2).getBlock().canEntityDestroy(world.getBlockState(pos2), world, pos2, getThrower()))
											world.destroyBlock(pos2, false);
									}
						}
					} else if(EntityUtil.damageShield(entity, damage*2))
						entity.attackEntityFrom(AbyssalCraftAPI.acid, 1);
					setDead();

				}
			}
			if(mop.typeOfHit == Type.BLOCK && world.getGameRules().getBoolean("mobGriefing")) {
				BlockPos pos = mop.getBlockPos();
				if(!world.isAirBlock(pos) && world.getBlockState(pos).getBlockHardness(world, pos) < ACConfig.acidResistanceHardness && world.getBlockState(pos).getBlock() != ACBlocks.shoggoth_ooze
						&& world.getBlockState(pos) != ACBlocks.monolith_stone.getDefaultState()
						&& world.getBlockState(pos).getBlock() != ACBlocks.shoggoth_biomass && !world.getBlockState(pos).getBlock().hasTileEntity(world.getBlockState(pos))
						&& world.getBlockState(pos).getBlockHardness(world, pos) != -1 && world.getBlockState(pos).getBlock().canEntityDestroy(world.getBlockState(pos), world, pos, getThrower()))
					world.destroyBlock(pos, false);
				setDead();
			}
		}
	}
}
