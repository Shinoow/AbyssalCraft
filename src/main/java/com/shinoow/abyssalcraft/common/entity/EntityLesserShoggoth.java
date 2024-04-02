/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityLesserShoggoth extends EntityShoggothBase {


	public EntityLesserShoggoth(World par1World) {
		super(par1World);
		setSize(0.9F, 1.3F); //1.8F, 2.6F
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 50.0D : 25.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 12.0D : 6.0D);

	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		double a = Math.toRadians(rotationYaw);
		double offsetx = -Math.sin(a);
		double offsetz = Math.cos(a);

		//sizes
		shoggothBody.height = 1.1F;
		shoggothBody.width = 0.9F;

		shoggothHead.width = 0.8F;
		shoggothHead.height = 0.6F;

		//rotations
		shoggothBody.onUpdate();
		shoggothBody.setLocationAndAngles(posX - offsetx * 0.35F, posY, posZ - offsetz * 0.35F, 0.0F, 0.0F);

		shoggothHead.onUpdate();
		shoggothHead.setLocationAndAngles(posX - offsetx * -0.25d, posY + 0.75f, posZ - offsetz * -0.25d, 0.0F, 0.0F);
	}

	@Override
	protected ResourceLocation getLootTable(){
		switch(getShoggothType()){
		case 0:
			return ACLoot.ENTITY_LESSER_SHOGGOTH;
		case 1:
			return ACLoot.ENTITY_LESSER_ABYSSAL_SHOGGOTH;
		case 2:
			return ACLoot.ENTITY_LESSER_DREADED_SHOGGOTH;
		case 3:
			return ACLoot.ENTITY_LESSER_OMOTHOL_SHOGGOTH;
		case 4:
			return ACLoot.ENTITY_LESSER_SHADOW_SHOGGOTH;
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
			if(hasCustomName())
				shoggoth.setCustomNameTag(getCustomNameTag());
			world.spawnEntity(shoggoth);
			world.removeEntity(this);
		}
	}

	@Override
	public boolean isBig() {

		return false;
	}

	@Override
	protected int getArmorAcidDamage() {

		return 4;
	}
}
