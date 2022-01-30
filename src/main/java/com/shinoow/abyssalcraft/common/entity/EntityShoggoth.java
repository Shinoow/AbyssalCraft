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

public class EntityShoggoth extends EntityShoggothBase {

	public EntityShoggoth(World worldIn) {
		super(worldIn);
		setSize(1.2F, 1.8F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 10.0D : 50.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 16.0D : 8.0D);

	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		double a = Math.toRadians(rotationYaw);
		double offsetx = -Math.sin(a);
		double offsetz = Math.cos(a);

		//sizes
		shoggothBody.height = 1.4F;
		shoggothBody.width = 1.2F;

		shoggothHead.width = 1.1F;
		shoggothHead.height = 0.6F;

		//rotations
		shoggothBody.onUpdate();
		shoggothBody.setLocationAndAngles(posX - offsetx * 0.5F, posY, posZ - offsetz * 0.5F, 0.0F, 0.0F);

		shoggothHead.onUpdate();
		shoggothHead.setLocationAndAngles(posX - offsetx * -0.5d, posY + 1.2f, posZ - offsetz * -0.5d, 0.0F, 0.0F);

	}

	@Override
	protected ResourceLocation getLootTable(){
		switch(getShoggothType()){
		case 0:
			return ACLoot.ENTITY_SHOGGOTH;
		case 1:
			return ACLoot.ENTITY_ABYSSAL_SHOGGOTH;
		case 2:
			return ACLoot.ENTITY_DREADED_SHOGGOTH;
		case 3:
			return ACLoot.ENTITY_OMOTHOL_SHOGGOTH;
		case 4:
			return ACLoot.ENTITY_SHADOW_SHOGGOTH;
		}
		return null;
	}

	@Override
	protected void performFoodAction() {
		if(!world.isRemote){
			EntityLesserShoggoth shoggoth = new EntityLesserShoggoth(world);
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
		return 8;
	}

}
