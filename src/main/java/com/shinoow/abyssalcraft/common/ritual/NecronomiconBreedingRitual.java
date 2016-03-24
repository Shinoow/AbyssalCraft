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

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

public class NecronomiconBreedingRitual extends NecronomiconRitual {

	private boolean shoggothInfestation;

	public NecronomiconBreedingRitual() {
		super("breeding", 0, 500F, new Object[]{Items.wheat, Items.potato, Items.carrot, Items.melon,
				Blocks.pumpkin, Items.wheat_seeds, Items.melon_seeds, Items.pumpkin_seeds});
	}

	/**
	 * Used to check if Shoggoths happened
	 * @return True if a Shoggoth infestation occurred, otherwise false
	 */
	public boolean didShoggothInfest(){
		return shoggothInfestation;
	}

	private void makeShoggoth(World world, Entity host){
		EntityLesserShoggoth shoggoth = new EntityLesserShoggoth(world);
		shoggoth.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(host.posX, host.posY, host.posZ)), (IEntityLivingData)null);
		shoggoth.setChild(true);
		shoggoth.copyLocationAndAnglesFrom(host);
		shoggothInfestation = true;
		world.spawnEntityInWorld(shoggoth);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		List<EntityAnimal> animals = world.getEntitiesWithinAABB(EntityAnimal.class, world.getBlockState(pos).getBlock().getBoundingBox(world.getBlockState(pos), world, pos).expand(16, 3, 16));
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

		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, world.getBlockState(pos).getBlock().getBoundingBox(world.getBlockState(pos), world, pos).expand(16, 3, 16));
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
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, cows.get(i));
				}
			else{
				cows.remove(cows.size()-1);
				for(int i = 0; i < cows.size()/2; i++){
					EntityCow cow = cows.get(i).createChild(cows.get(i));
					cow.setGrowingAge(-24000);
					cow.copyLocationAndAnglesFrom(cows.get(i));
					world.spawnEntityInWorld(cow);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, cows.get(i));
				}
			}
		if(!chickens.isEmpty() && chickens.size() >= 2)
			if((chickens.size() & 1) == 0)
				for(int i = 0; i < chickens.size()/2; i++){
					EntityChicken chicken = chickens.get(i).createChild(chickens.get(i));
					chicken.setGrowingAge(-24000);
					chicken.copyLocationAndAnglesFrom(chickens.get(i));
					world.spawnEntityInWorld(chicken);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, chickens.get(0));
				}
			else{
				chickens.remove(chickens.size()-1);
				for(int i = 0; i < chickens.size()/2; i++){
					EntityChicken chicken = chickens.get(i).createChild(chickens.get(i));
					chicken.setGrowingAge(-24000);
					chicken.copyLocationAndAnglesFrom(chickens.get(i));
					world.spawnEntityInWorld(chicken);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, chickens.get(0));
				}
			}
		if(!pigs.isEmpty() && pigs.size() >= 2)
			if((pigs.size() & 1) == 0)
				for(int i = 0; i < pigs.size()/2; i++){
					EntityPig pig = pigs.get(i).createChild(pigs.get(i));
					pig.setGrowingAge(-24000);
					pig.copyLocationAndAnglesFrom(pigs.get(i));
					world.spawnEntityInWorld(pig);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, pigs.get(0));
				}
			else{
				pigs.remove(pigs.size()-1);
				for(int i = 0; i < pigs.size()/2; i++){
					EntityPig pig = pigs.get(i).createChild(pigs.get(i));
					pig.setGrowingAge(-24000);
					pig.copyLocationAndAnglesFrom(pigs.get(i));
					world.spawnEntityInWorld(pig);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, pigs.get(0));
				}
			}
		if(!sheeps.isEmpty() && sheeps.size() >= 2)
			if((sheeps.size() & 1) == 0)
				for(int i = 0; i < sheeps.size()/2; i++){
					EntitySheep sheep = sheeps.get(i).createChild(sheeps.get(i));
					sheep.setGrowingAge(-24000);
					sheep.copyLocationAndAnglesFrom(sheeps.get(i));
					world.spawnEntityInWorld(sheep);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, sheeps.get(0));
				}
			else{
				sheeps.remove(sheeps.size()-1);
				for(int i = 0; i < sheeps.size()/2; i++){
					EntitySheep sheep = sheeps.get(i).createChild(sheeps.get(i));
					sheep.setGrowingAge(-24000);
					sheep.copyLocationAndAnglesFrom(sheeps.get(i));
					world.spawnEntityInWorld(sheep);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, sheeps.get(0));
				}
			}
		if(!horses.isEmpty() && horses.size() >= 2)
			if((horses.size() & 1) == 0)
				for(int i = 0; i < horses.size()/2; i++){
					EntityHorse horse = (EntityHorse) horses.get(i).createChild(horses.get(i));
					horse.setGrowingAge(-24000);
					horse.copyLocationAndAnglesFrom(horses.get(i));
					world.spawnEntityInWorld(horse);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, horses.get(0));
				}
			else{
				horses.remove(cows.size()-1);
				for(int i = 0; i < horses.size()/2; i++){
					EntityHorse horse = (EntityHorse) horses.get(i).createChild(horses.get(i));
					horse.setGrowingAge(-24000);
					horse.copyLocationAndAnglesFrom(horses.get(i));
					world.spawnEntityInWorld(horse);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, horses.get(0));
				}
			}
		if(!ocelots.isEmpty() && ocelots.size() >= 2)
			if((ocelots.size() & 1) == 0)
				for(int i = 0; i < ocelots.size()/2; i++){
					EntityOcelot ocelot = ocelots.get(i).createChild(ocelots.get(i));
					ocelot.setGrowingAge(-24000);
					ocelot.copyLocationAndAnglesFrom(ocelots.get(i));
					world.spawnEntityInWorld(ocelot);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, ocelots.get(0));
				}
			else{
				ocelots.remove(ocelots.size()-1);
				for(int i = 0; i < ocelots.size()/2; i++){
					EntityOcelot ocelot = ocelots.get(i).createChild(ocelots.get(i));
					ocelot.setGrowingAge(-24000);
					ocelot.copyLocationAndAnglesFrom(ocelots.get(i));
					world.spawnEntityInWorld(ocelot);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, ocelots.get(0));
				}
			}
		if(!wolves.isEmpty() && wolves.size() >= 2)
			if((wolves.size() & 1) == 0)
				for(int i = 0; i < wolves.size()/2; i++){
					EntityWolf wolf = wolves.get(i).createChild(wolves.get(i));
					wolf.setGrowingAge(-24000);
					wolf.copyLocationAndAnglesFrom(wolves.get(i));
					world.spawnEntityInWorld(wolf);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, wolves.get(0));
				}
			else{
				wolves.remove(wolves.size()-1);
				for(int i = 0; i < wolves.size()/2; i++){
					EntityWolf wolf = wolves.get(i).createChild(wolves.get(i));
					wolf.setGrowingAge(-24000);
					wolf.copyLocationAndAnglesFrom(wolves.get(i));
					world.spawnEntityInWorld(wolf);
					if(world.rand.nextInt(4) == 0)
						makeShoggoth(world, wolves.get(0));
				}
			}
		if(shoggothInfestation)
			player.addStat(AbyssalCraft.shoggothInfestation, 1);
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player){}
}
