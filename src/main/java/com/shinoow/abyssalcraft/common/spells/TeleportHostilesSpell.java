/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.spells;

import com.shinoow.abyssalcraft.api.spell.EntityTargetSpell;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class TeleportHostilesSpell extends EntityTargetSpell {

	public TeleportHostilesSpell() {
		super("teleporthostiles", 10000F, Items.SUGAR);
		setScrollType(ScrollType.GREATER);
		setRequiresCharging();
		setColor(0x31227c);
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target, ScrollType scrollType) {

		return target.isCreatureType(EnumCreatureType.CREATURE, false) && target.isChild();
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType, EntityLivingBase target) {

		if(target.isCreatureType(EnumCreatureType.CREATURE, false) && target.isChild())
			if(target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), 200F))
				for(EntityLivingBase mob : player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(50)))
					if(mob.isCreatureType(EnumCreatureType.MONSTER, false))
						teleportRandomly(mob, player.world, player);
	}

	protected boolean teleportRandomly(EntityLivingBase entity, World world, EntityPlayer player)
	{
		int tries = 0;
		double d0 = entity.posX + (world.rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = entity.posY + (world.rand.nextInt(64) - 32);
		double d2 = entity.posZ + (world.rand.nextDouble() - 0.5D) * 64.0D;
		while(player.getDistance(d0, d1, d2) < 50) {
			if(tries == 20) break;
			tries++;
			d0 = entity.posX + (world.rand.nextDouble() - 0.5D) * 96.0D;
			d1 = entity.posY + (world.rand.nextInt(64) - 32);
			d2 = entity.posZ + (world.rand.nextDouble() - 0.5D) * 96.0D;
			if(teleportTo(d0, d1, d2, entity, world))
				return true;
		}
		return teleportTo(d0, d1, d2, entity, world);
	}

	protected boolean teleportTo(double par1, double par3, double par5, EntityLivingBase entity, World world)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(entity, par1, par3, par5, 0);
		if (MinecraftForge.EVENT_BUS.post(event))
			return false;
		double d3 = entity.posX;
		double d4 = entity.posY;
		double d5 = entity.posZ;
		entity.posX = event.getTargetX();
		entity.posY = event.getTargetY();
		entity.posZ = event.getTargetZ();
		boolean flag = false;
		BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);

		if (world.isBlockLoaded(pos))
		{
			boolean flag1 = false;

			while (!flag1 && pos.getY() > 0)
			{
				BlockPos pos1 = pos.down();
				IBlockState block = world.getBlockState(pos1);

				if (block.getMaterial().blocksMovement())
					flag1 = true;
				else
				{
					--entity.posY;
					pos = pos1;
				}
			}

			if (flag1)
			{
				entity.setPosition(entity.posX, entity.posY, entity.posZ);

				if (world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(entity.getEntityBoundingBox()))
					flag = true;
			}
		}

		if (!flag)
		{
			entity.setPosition(d3, d4, d5);
			return false;
		}
		else
		{
			short short1 = 128;

			for (int l = 0; l < short1; ++l)
			{
				double d6 = l / (short1 - 1.0D);
				float f = (world.rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (world.rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (world.rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = d3 + (entity.posX - d3) * d6 + (world.rand.nextDouble() - 0.5D) * entity.width * 2.0D;
				double d8 = d4 + (entity.posY - d4) * d6 + world.rand.nextDouble() * entity.height;
				double d9 = d5 + (entity.posZ - d5) * d6 + (world.rand.nextDouble() - 0.5D) * entity.width * 2.0D;
				world.spawnParticle(EnumParticleTypes.PORTAL, d7, d8, d9, f, f1, f2);
			}

			world.playSound(d3, d4, d5, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
			entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
			return true;
		}
	}
}
