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
package com.shinoow.abyssalcraft.common.ritual;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

public class NecronomiconBreedingRitual extends NecronomiconRitual {

	public NecronomiconBreedingRitual() {
		super("breeding", 0, 500F, new Object[]{Items.WHEAT, Items.POTATO, Items.CARROT, Items.MELON,
				Blocks.PUMPKIN, Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS});
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		List<EntityAnimal> animals = world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(pos).expand(16, 3, 16));
		return !animals.isEmpty();
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player){

		List<EntityCow> cows = Lists.newArrayList();
		List<EntityChicken> chickens = Lists.newArrayList();
		List<EntityPig> pigs = Lists.newArrayList();
		List<EntitySheep> sheeps = Lists.newArrayList();
		List<EntityHorse> horses = Lists.newArrayList();
		List<EntityOcelot> ocelots = Lists.newArrayList();
		List<EntityWolf> wolves = Lists.newArrayList();

		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos).expand(16, 3, 16));
		for(Entity entity : entities){
			if(entity instanceof EntityCow)
				cows.add((EntityCow) entity);
			if(entity instanceof EntityChicken)
				chickens.add((EntityChicken) entity);
			if(entity instanceof EntityPig)
				pigs.add((EntityPig) entity);
			if(entity instanceof EntitySheep)
				sheeps.add((EntitySheep) entity);
			if(entity instanceof EntityHorse)
				horses.add((EntityHorse) entity);
			if(entity instanceof EntityOcelot)
				ocelots.add((EntityOcelot) entity);
			if(entity instanceof EntityWolf)
				wolves.add((EntityWolf) entity);
		}
		if(!cows.isEmpty() && cows.size() >= 2)
			if((cows.size() & 1) == 0)
				for(int i = 0; i < cows.size()/2; i++){
					EntityCow cow = cows.get(i).createChild(cows.get(i));
					cow.setGrowingAge(-24000);
					cow.copyLocationAndAnglesFrom(cows.get(i));
					world.spawnEntityInWorld(cow);
				}
			else{
				cows.remove(cows.size()-1);
				for(int i = 0; i < cows.size()/2; i++){
					EntityCow cow = cows.get(i).createChild(cows.get(i));
					cow.setGrowingAge(-24000);
					cow.copyLocationAndAnglesFrom(cows.get(i));
					world.spawnEntityInWorld(cow);
				}
			}
		if(!chickens.isEmpty() && chickens.size() >= 2)
			if((chickens.size() & 1) == 0)
				for(int i = 0; i < chickens.size()/2; i++){
					EntityChicken chicken = chickens.get(i).createChild(chickens.get(i));
					chicken.setGrowingAge(-24000);
					chicken.copyLocationAndAnglesFrom(chickens.get(i));
					world.spawnEntityInWorld(chicken);
				}
			else{
				chickens.remove(chickens.size()-1);
				for(int i = 0; i < chickens.size()/2; i++){
					EntityChicken chicken = chickens.get(i).createChild(chickens.get(i));
					chicken.setGrowingAge(-24000);
					chicken.copyLocationAndAnglesFrom(chickens.get(i));
					world.spawnEntityInWorld(chicken);
				}
			}
		if(!pigs.isEmpty() && pigs.size() >= 2)
			if((pigs.size() & 1) == 0)
				for(int i = 0; i < pigs.size()/2; i++){
					EntityPig pig = pigs.get(i).createChild(pigs.get(i));
					pig.setGrowingAge(-24000);
					pig.copyLocationAndAnglesFrom(pigs.get(i));
					world.spawnEntityInWorld(pig);
				}
			else{
				pigs.remove(pigs.size()-1);
				for(int i = 0; i < pigs.size()/2; i++){
					EntityPig pig = pigs.get(i).createChild(pigs.get(i));
					pig.setGrowingAge(-24000);
					pig.copyLocationAndAnglesFrom(pigs.get(i));
					world.spawnEntityInWorld(pig);
				}
			}
		if(!sheeps.isEmpty() && sheeps.size() >= 2)
			if((sheeps.size() & 1) == 0)
				for(int i = 0; i < sheeps.size()/2; i++){
					EntitySheep sheep = sheeps.get(i).createChild(sheeps.get(i));
					sheep.setGrowingAge(-24000);
					sheep.copyLocationAndAnglesFrom(sheeps.get(i));
					world.spawnEntityInWorld(sheep);
				}
			else{
				sheeps.remove(sheeps.size()-1);
				for(int i = 0; i < sheeps.size()/2; i++){
					EntitySheep sheep = sheeps.get(i).createChild(sheeps.get(i));
					sheep.setGrowingAge(-24000);
					sheep.copyLocationAndAnglesFrom(sheeps.get(i));
					world.spawnEntityInWorld(sheep);
				}
			}
		if(!horses.isEmpty() && horses.size() >= 2)
			if((horses.size() & 1) == 0)
				for(int i = 0; i < horses.size()/2; i++){
					EntityHorse horse = (EntityHorse) horses.get(i).createChild(horses.get(i));
					horse.setGrowingAge(-24000);
					horse.copyLocationAndAnglesFrom(horses.get(i));
					world.spawnEntityInWorld(horse);
				}
			else{
				horses.remove(cows.size()-1);
				for(int i = 0; i < horses.size()/2; i++){
					EntityHorse horse = (EntityHorse) horses.get(i).createChild(horses.get(i));
					horse.setGrowingAge(-24000);
					horse.copyLocationAndAnglesFrom(horses.get(i));
					world.spawnEntityInWorld(horse);
				}
			}
		if(!ocelots.isEmpty() && ocelots.size() >= 2)
			if((ocelots.size() & 1) == 0)
				for(int i = 0; i < ocelots.size()/2; i++){
					EntityOcelot ocelot = ocelots.get(i).createChild(ocelots.get(i));
					ocelot.setGrowingAge(-24000);
					ocelot.copyLocationAndAnglesFrom(ocelots.get(i));
					world.spawnEntityInWorld(ocelot);
				}
			else{
				ocelots.remove(ocelots.size()-1);
				for(int i = 0; i < ocelots.size()/2; i++){
					EntityOcelot ocelot = ocelots.get(i).createChild(ocelots.get(i));
					ocelot.setGrowingAge(-24000);
					ocelot.copyLocationAndAnglesFrom(ocelots.get(i));
					world.spawnEntityInWorld(ocelot);
				}
			}
		if(!wolves.isEmpty() && wolves.size() >= 2)
			if((wolves.size() & 1) == 0)
				for(int i = 0; i < wolves.size()/2; i++){
					EntityWolf wolf = wolves.get(i).createChild(wolves.get(i));
					wolf.setGrowingAge(-24000);
					wolf.copyLocationAndAnglesFrom(wolves.get(i));
					world.spawnEntityInWorld(wolf);
				}
			else{
				wolves.remove(wolves.size()-1);
				for(int i = 0; i < wolves.size()/2; i++){
					EntityWolf wolf = wolves.get(i).createChild(wolves.get(i));
					wolf.setGrowingAge(-24000);
					wolf.copyLocationAndAnglesFrom(wolves.get(i));
					world.spawnEntityInWorld(wolf);
				}
			}

		if(cows.size() > 2 && getDeathChance(world.rand, cows.size()))
			cows.get(world.rand.nextInt(cows.size())).attackEntityFrom(DamageSource.magic, 200000);
		if(chickens.size() > 2 && getDeathChance(world.rand, chickens.size()))
			chickens.get(world.rand.nextInt(chickens.size())).attackEntityFrom(DamageSource.magic, 200000);
		if(pigs.size() > 2 && getDeathChance(world.rand, pigs.size()))
			pigs.get(world.rand.nextInt(pigs.size())).attackEntityFrom(DamageSource.magic, 200000);
		if(sheeps.size() > 2 && getDeathChance(world.rand, sheeps.size()))
			sheeps.get(world.rand.nextInt(sheeps.size())).attackEntityFrom(DamageSource.magic, 200000);
		if(horses.size() > 2 && getDeathChance(world.rand, horses.size()))
			horses.get(world.rand.nextInt(horses.size())).attackEntityFrom(DamageSource.magic, 200000);
		if(ocelots.size() > 2 && getDeathChance(world.rand, ocelots.size()))
			ocelots.get(world.rand.nextInt(ocelots.size())).attackEntityFrom(DamageSource.magic, 200000);
		if(wolves.size() > 2 && getDeathChance(world.rand, wolves.size()))
			wolves.get(world.rand.nextInt(wolves.size())).attackEntityFrom(DamageSource.magic, 200000);
	}

	private boolean getDeathChance(Random rand, int num){
		if(num < 5)
			return rand.nextInt(10) == 0;
		if(num > 5 && num < 10)
			return rand.nextInt(9) == 0;
		if(num > 10 && num < 15)
			return rand.nextInt(8) == 0;
		if(num > 15 && num < 20)
			return rand.nextInt(7) == 0;
		if(num > 20 && num < 25)
			return rand.nextInt(6) == 0;
		if(num > 25 && num < 30)
			return rand.nextInt(5) == 0;
		if(num > 30 && num < 35)
			return rand.nextInt(4) == 0;
		if(num > 35 && num < 40)
			return rand.nextInt(3) == 0;
		if(num > 40 && num < 45)
			return rand.nextInt(2) == 0;
		return true;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player){}
}
