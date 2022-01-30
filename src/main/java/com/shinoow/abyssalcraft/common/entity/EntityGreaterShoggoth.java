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

import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityGreaterShoggoth extends EntityShoggothBase {

	public EntityGreaterShoggoth(World worldIn) {
		super(worldIn);
		setSize(1.8F, 2.6F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 200.0D : 100.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 20.0D : 10.0D);

	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		double a = Math.toRadians(rotationYaw);
		double offsetx = -Math.sin(a);
		double offsetz = Math.cos(a);

		//sizes
		shoggothBody.height = 2.2F;
		shoggothBody.width = 1.8F;

		shoggothHead.width = 1.6F;
		shoggothHead.height = 1.2F;

		//rotations
		shoggothBody.onUpdate();
		shoggothBody.setLocationAndAngles(posX - offsetx * 0.7F, posY, posZ - offsetz * 0.7F, 0.0F, 0.0F);

		shoggothHead.onUpdate();
		shoggothHead.setLocationAndAngles(posX - offsetx * -0.5d, posY + 1.5f, posZ - offsetz * -0.5d, 0.0F, 0.0F);

	}

	@Override
	protected ResourceLocation getLootTable(){
		switch(getShoggothType()){
		case 0:
			return ACLoot.ENTITY_GREATER_SHOGGOTH;
		case 1:
			return ACLoot.ENTITY_GREATER_ABYSSAL_SHOGGOTH;
		case 2:
			return ACLoot.ENTITY_GREATER_DREADED_SHOGGOTH;
		case 3:
			return ACLoot.ENTITY_GREATER_OMOTHOL_SHOGGOTH;
		case 4:
			return ACLoot.ENTITY_GREATER_SHADOW_SHOGGOTH;
		}
		return null;
	}

	@Override
	protected void performFoodAction() {
		if(!world.isRemote){
			EntityShoggoth shoggoth = new EntityShoggoth(world);
			shoggoth.copyLocationAndAnglesFrom(this);
			shoggoth.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)),(IEntityLivingData)null);
			shoggoth.setShoggothType(getShoggothType());
			world.spawnEntity(shoggoth);
			playSound(ACSounds.shoggoth_birth, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			if(getAttackTarget() != null && getAttackTarget().isEntityAlive() && getRNG().nextInt(3) == 0) {
				EntityLivingBase target = getAttackTarget();
				EntityAcidProjectile acidprojectile = new EntityAcidProjectile(world, this);
				double d0 = target.posX - posX;
				double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - acidprojectile.posY;
				double d2 = target.posZ - posZ;
				float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
				acidprojectile.shoot(d0, d1 + f1, d2, 0.8F, 8.0F);
				shoggoth.startRiding(acidprojectile);
				playSound(ACSounds.shoggoth_shoot, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
				world.spawnEntity(acidprojectile);
			}
		}
	}

	@Override
	public boolean isBig() {
		return true;
	}

	@Override
	protected int getArmorAcidDamage() {
		return 12; //TODO maybe?
	}

}
