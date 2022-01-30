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
package com.shinoow.abyssalcraft.common.ritual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.entity.demon.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecronomiconBreedingRitual extends NecronomiconRitual {

	public NecronomiconBreedingRitual() {
		super("breeding", 0, 500F, new Object[]{Items.WHEAT, Items.POTATO, Items.CARROT, Items.MELON,
				Blocks.PUMPKIN, Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS});
		setRitualParticle(EnumRitualParticle.SMOKE_PILLARS);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		List<EntityAnimal> animals = world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(pos).grow(16, 3, 16));
		return !animals.isEmpty();
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player){

		List<EntityCow> cows = new ArrayList<>();
		List<EntityChicken> chickens = new ArrayList<>();
		List<EntityPig> pigs = new ArrayList<>();
		List<EntitySheep> sheeps = new ArrayList<>();
		List<EntityHorse> horses = new ArrayList<>();
		List<EntityOcelot> ocelots = new ArrayList<>();
		List<EntityWolf> wolves = new ArrayList<>();
		List<EntityRabbit> rabbits = new ArrayList<>();

		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos).grow(16, 3, 16));
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
			if(entity instanceof EntityRabbit)
				rabbits.add((EntityRabbit) entity);
		}
		if(!cows.isEmpty() && cows.size() >= 2)
			if((cows.size() & 1) == 0)
				for(int i = 0; i < cows.size()/2; i++){
					EntityCow cow = cows.get(i).createChild(cows.get(i));
					cow.setGrowingAge(-24000);
					cow.copyLocationAndAnglesFrom(cows.get(i));
					world.spawnEntity(cow);
				}
			else{
				cows.remove(cows.size()-1);
				for(int i = 0; i < cows.size()/2; i++){
					EntityCow cow = cows.get(i).createChild(cows.get(i));
					cow.setGrowingAge(-24000);
					cow.copyLocationAndAnglesFrom(cows.get(i));
					world.spawnEntity(cow);
				}
			}
		if(!chickens.isEmpty() && chickens.size() >= 2)
			if((chickens.size() & 1) == 0)
				for(int i = 0; i < chickens.size()/2; i++){
					EntityChicken chicken = chickens.get(i).createChild(chickens.get(i));
					chicken.setGrowingAge(-24000);
					chicken.copyLocationAndAnglesFrom(chickens.get(i));
					world.spawnEntity(chicken);
				}
			else{
				chickens.remove(chickens.size()-1);
				for(int i = 0; i < chickens.size()/2; i++){
					EntityChicken chicken = chickens.get(i).createChild(chickens.get(i));
					chicken.setGrowingAge(-24000);
					chicken.copyLocationAndAnglesFrom(chickens.get(i));
					world.spawnEntity(chicken);
				}
			}
		if(!pigs.isEmpty() && pigs.size() >= 2)
			if((pigs.size() & 1) == 0)
				for(int i = 0; i < pigs.size()/2; i++){
					EntityPig pig = pigs.get(i).createChild(pigs.get(i));
					pig.setGrowingAge(-24000);
					pig.copyLocationAndAnglesFrom(pigs.get(i));
					world.spawnEntity(pig);
				}
			else{
				pigs.remove(pigs.size()-1);
				for(int i = 0; i < pigs.size()/2; i++){
					EntityPig pig = pigs.get(i).createChild(pigs.get(i));
					pig.setGrowingAge(-24000);
					pig.copyLocationAndAnglesFrom(pigs.get(i));
					world.spawnEntity(pig);
				}
			}
		if(!sheeps.isEmpty() && sheeps.size() >= 2)
			if((sheeps.size() & 1) == 0)
				for(int i = 0; i < sheeps.size()/2; i++){
					EntitySheep sheep = sheeps.get(i).createChild(sheeps.get(i));
					sheep.setGrowingAge(-24000);
					sheep.copyLocationAndAnglesFrom(sheeps.get(i));
					world.spawnEntity(sheep);
				}
			else{
				sheeps.remove(sheeps.size()-1);
				for(int i = 0; i < sheeps.size()/2; i++){
					EntitySheep sheep = sheeps.get(i).createChild(sheeps.get(i));
					sheep.setGrowingAge(-24000);
					sheep.copyLocationAndAnglesFrom(sheeps.get(i));
					world.spawnEntity(sheep);
				}
			}
		if(!horses.isEmpty() && horses.size() >= 2)
			if((horses.size() & 1) == 0)
				for(int i = 0; i < horses.size()/2; i++){
					EntityHorse horse = (EntityHorse) horses.get(i).createChild(horses.get(i));
					horse.setGrowingAge(-24000);
					horse.copyLocationAndAnglesFrom(horses.get(i));
					world.spawnEntity(horse);
				}
			else{
				horses.remove(cows.size()-1);
				for(int i = 0; i < horses.size()/2; i++){
					EntityHorse horse = (EntityHorse) horses.get(i).createChild(horses.get(i));
					horse.setGrowingAge(-24000);
					horse.copyLocationAndAnglesFrom(horses.get(i));
					world.spawnEntity(horse);
				}
			}
		if(!ocelots.isEmpty() && ocelots.size() >= 2)
			if((ocelots.size() & 1) == 0)
				for(int i = 0; i < ocelots.size()/2; i++){
					EntityOcelot ocelot = ocelots.get(i).createChild(ocelots.get(i));
					ocelot.setGrowingAge(-24000);
					ocelot.copyLocationAndAnglesFrom(ocelots.get(i));
					world.spawnEntity(ocelot);
				}
			else{
				ocelots.remove(ocelots.size()-1);
				for(int i = 0; i < ocelots.size()/2; i++){
					EntityOcelot ocelot = ocelots.get(i).createChild(ocelots.get(i));
					ocelot.setGrowingAge(-24000);
					ocelot.copyLocationAndAnglesFrom(ocelots.get(i));
					world.spawnEntity(ocelot);
				}
			}
		if(!wolves.isEmpty() && wolves.size() >= 2)
			if((wolves.size() & 1) == 0)
				for(int i = 0; i < wolves.size()/2; i++){
					EntityWolf wolf = wolves.get(i).createChild(wolves.get(i));
					wolf.setGrowingAge(-24000);
					wolf.copyLocationAndAnglesFrom(wolves.get(i));
					world.spawnEntity(wolf);
				}
			else{
				wolves.remove(wolves.size()-1);
				for(int i = 0; i < wolves.size()/2; i++){
					EntityWolf wolf = wolves.get(i).createChild(wolves.get(i));
					wolf.setGrowingAge(-24000);
					wolf.copyLocationAndAnglesFrom(wolves.get(i));
					world.spawnEntity(wolf);
				}
			}
		if(!rabbits.isEmpty() && rabbits.size() >= 2)
			if((rabbits.size() & 1) == 0)
				for(int i = 0; i < rabbits.size()/2; i++){
					EntityRabbit rabbit = rabbits.get(i).createChild(rabbits.get(i));
					rabbit.setGrowingAge(-24000);
					rabbit.copyLocationAndAnglesFrom(rabbits.get(i));
					world.spawnEntity(rabbit);
				}
			else{
				rabbits.remove(rabbits.size()-1);
				for(int i = 0; i < rabbits.size()/2; i++){
					EntityRabbit rabbit = rabbits.get(i).createChild(rabbits.get(i));
					rabbit.setGrowingAge(-24000);
					rabbit.copyLocationAndAnglesFrom(rabbits.get(i));
					world.spawnEntity(rabbit);
				}
			}

		if(cows.size() > 2 && getDeathChance(world.rand, cows.size())){
			Entity e = cows.get(world.rand.nextInt(cows.size()));
			EntityEvilCow cow = new EntityEvilCow(world);
			cow.copyLocationAndAnglesFrom(e);
			world.removeEntity(e);
			world.spawnEntity(cow);
		} if(chickens.size() > 2 && getDeathChance(world.rand, chickens.size())){
			Entity e = chickens.get(world.rand.nextInt(chickens.size()));
			EntityEvilChicken chicken = new EntityEvilChicken(world);
			chicken.copyLocationAndAnglesFrom(e);
			world.removeEntity(e);
			world.spawnEntity(chicken);
		} if(pigs.size() > 2 && getDeathChance(world.rand, pigs.size())){
			Entity e = pigs.get(world.rand.nextInt(pigs.size()));
			EntityEvilpig pig = new EntityEvilpig(world);
			pig.copyLocationAndAnglesFrom(e);
			world.removeEntity(e);
			world.spawnEntity(pig);
		} if(sheeps.size() > 2 && getDeathChance(world.rand, sheeps.size())){
			Entity e = sheeps.get(world.rand.nextInt(sheeps.size()));
			EntityEvilSheep sheep = new EntityEvilSheep(world);
			sheep.copyLocationAndAnglesFrom(e);
			world.removeEntity(e);
			world.spawnEntity(sheep);
		} if(horses.size() > 2 && getDeathChance(world.rand, horses.size()))
			horses.get(world.rand.nextInt(horses.size())).attackEntityFrom(DamageSource.MAGIC, 200000);
		if(ocelots.size() > 2 && getDeathChance(world.rand, ocelots.size()))
			ocelots.get(world.rand.nextInt(ocelots.size())).attackEntityFrom(DamageSource.MAGIC, 200000);
		if(wolves.size() > 2 && getDeathChance(world.rand, wolves.size()))
			wolves.get(world.rand.nextInt(wolves.size())).attackEntityFrom(DamageSource.MAGIC, 200000);
		if(rabbits.size() > 2 && getDeathChance(world.rand, rabbits.size()))
			rabbits.get(world.rand.nextInt(rabbits.size())).attackEntityFrom(DamageSource.MAGIC, 200000);
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
