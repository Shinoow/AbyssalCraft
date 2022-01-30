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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.common.entity.demon.*;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisruptionAnimalCorruption extends DisruptionEntry {

	public DisruptionAnimalCorruption() {
		super("animalCorruption", DeityType.SHUBNIGGURATH);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {

		if(world.isRemote) return;

		List<EntityAnimal> animals = world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(pos).grow(64));
		for(EntityAnimal animal : animals)
			if(animal instanceof EntityCow){
				EntityEvilCow cow = new EntityEvilCow(world);
				cow.copyLocationAndAnglesFrom(animal);
				cow.onInitialSpawn(world.getDifficultyForLocation(pos), null);
				world.removeEntity(animal);
				world.spawnEntity(cow);
			}
			else if(animal instanceof EntityChicken){
				EntityEvilChicken chicken = new EntityEvilChicken(world);
				chicken.copyLocationAndAnglesFrom(animal);
				chicken.onInitialSpawn(world.getDifficultyForLocation(pos), null);
				world.removeEntity(animal);
				world.spawnEntity(chicken);
			}
			else if(animal instanceof EntityPig){
				EntityEvilpig pig = new EntityEvilpig(world);
				pig.copyLocationAndAnglesFrom(animal);
				pig.onInitialSpawn(world.getDifficultyForLocation(pos), null);
				world.removeEntity(animal);
				world.spawnEntity(pig);
			}
			else if(animal instanceof EntitySheep){
				EntityEvilSheep sheep = new EntityEvilSheep(world);
				sheep.copyLocationAndAnglesFrom(animal);
				sheep.onInitialSpawn(world.getDifficultyForLocation(pos), null);
				world.removeEntity(animal);
				world.spawnEntity(sheep);
			}
			else if(animal instanceof AbstractHorse && animal.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD){
				AbstractHorse horse = world.rand.nextBoolean() ? new EntityZombieHorse(world) : new EntitySkeletonHorse(world);
				horse.copyLocationAndAnglesFrom(animal);
				horse.setGrowingAge(animal.getGrowingAge());
				animal.onDeath(DamageSource.GENERIC);
				world.removeEntity(animal);
				world.spawnEntity(horse);
			}
			else if(animal instanceof EntityWolf && ((EntityWolf) animal).isTamed() && !players.isEmpty()){
				((EntityWolf) animal).setTamed(false);
				((EntityWolf) animal).setOwnerId(null);
				((EntityWolf) animal).setAngry(true);
				animal.setAttackTarget(players.get(world.rand.nextInt(players.size())));
			}
			else if(animal instanceof EntityOcelot && ((EntityOcelot) animal).isTamed()){
				((EntityOcelot) animal).setTamed(false);
				((EntityOcelot) animal).setOwnerId(null);
				((EntityOcelot) animal).setTameSkin(0);
			}
			else if(animal instanceof EntityRabbit && ((EntityRabbit) animal).getRabbitType() != 99)
				((EntityRabbit) animal).setRabbitType(99);
	}
}
