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
package com.shinoow.abyssalcraft.common.potion;

import java.util.List;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.handlers.PlagueEventHandler;
import com.shinoow.abyssalcraft.common.util.ArmorUtil;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;

public class PotionEffectUtil {

	public static void applyAntimatterEffect(EntityLivingBase entityLivingBase, int amplifier) {
		if(EntityUtil.isEntityAnti(entityLivingBase)) return;

		if(entityLivingBase.hasItemInSlot(EntityEquipmentSlot.CHEST) && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ACItems.ethaxium_chestplate)
			return;

		entityLivingBase.attackEntityFrom(AbyssalCraftAPI.antimatter, 5);

		if(!entityLivingBase.world.isRemote && entityLivingBase.isDead)
			if(entityLivingBase instanceof EntityZombie){
				EntityAntiZombie entity = new EntityAntiZombie(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityAbyssalZombie){
				EntityAntiAbyssalZombie entity = new EntityAntiAbyssalZombie(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityDepthsGhoul){
				EntityAntiGhoul entity = new EntityAntiGhoul(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityBat){
				EntityAntiBat entity = new EntityAntiBat(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(null, null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityChicken || entityLivingBase instanceof EntityDemonChicken){
				EntityAntiChicken entity = new EntityAntiChicken(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(null, null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityCow || entityLivingBase instanceof EntityDemonCow){
				EntityAntiCow entity = new EntityAntiCow(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(null, null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityCreeper){
				EntityAntiCreeper entity = new EntityAntiCreeper(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(null, null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityPig || entityLivingBase instanceof EntityDemonPig){
				EntityAntiPig entity = new EntityAntiPig(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(null, null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntityPlayer){
				EntityAntiPlayer entity = new EntityAntiPlayer(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.removePotionEffect(AbyssalCraftAPI.antimatter_potion);
				entity.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				String newname = "";

				String name = entityLivingBase.getName();
				int length = name.length();
				boolean[] cases = new boolean[length];
				for(int j = 0; j < length; j++)
					cases[j] = Character.isUpperCase(name.charAt(j));
				for(int i = length - 1; i >= 0; i--) {
					char c = name.charAt(i);
					int k = length - 1 - i;
					newname += cases[k] ? Character.toUpperCase(c) : Character.toLowerCase(c);
				}

				entity.setCustomNameTag(newname);
				entity.enablePersistence();
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntitySkeleton){
				EntityAntiSkeleton entity = new EntityAntiSkeleton(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(entity);
			} else if(entityLivingBase instanceof EntitySpider){
				EntityAntiSpider entity = new EntityAntiSpider(entityLivingBase.world);
				entity.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entity.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(entity);
			}
	}

	public static void applyCoraliumEffect(EntityLivingBase entityLivingBase, int amplifier) {
		if(entityLivingBase.ticksExisted % 200 == 0 && entityLivingBase.getRNG().nextFloat() > 0.7F
				&& entityLivingBase.world.getBiome(entityLivingBase.getPosition()) != ACBiomes.purged) {
			AxisAlignedBB axisalignedbb = entityLivingBase.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D);
			List<EntityLivingBase> list = entityLivingBase.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			for(EntityLivingBase entity : list)
				if(entity.getRNG().nextBoolean() && !entity.world.isRemote && entityLivingBase != entity)
					entity.addPotionEffect(PlagueEventHandler.getEffect(entityLivingBase.getActivePotionEffect(AbyssalCraftAPI.coralium_plague)));
		}

		if(entityLivingBase instanceof EntityPlayer && entityLivingBase.ticksExisted % 200 == 0)
			NecroDataCapability.getCap((EntityPlayer) entityLivingBase).triggerMiscUnlock("coralium_plague");

		if(EntityUtil.isEntityCoralium(entityLivingBase)) return;

		if(ArmorUtil.hasHelmetWithResistance(entityLivingBase, AbyssalCraftAPI.coralium)) return;

		if(entityLivingBase.ticksExisted % 40 >> amplifier == 0)
			entityLivingBase.attackEntityFrom(AbyssalCraftAPI.coralium, 2);

		if(!entityLivingBase.world.isRemote && entityLivingBase.isDead
				&& entityLivingBase.world.getBiome(entityLivingBase.getPosition()) != ACBiomes.purged)
			if(entityLivingBase instanceof EntityZombie){
				if(entityLivingBase.world.getWorldInfo().isHardcoreModeEnabled() && entityLivingBase.world.rand.nextInt(10) == 0) {
					EntityDepthsGhoul ghoul = new EntityDepthsGhoul(entityLivingBase.world);
					ghoul.copyLocationAndAnglesFrom(entityLivingBase);
					ghoul.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
					entityLivingBase.world.removeEntity(entityLivingBase);
					ghoul.setGhoulType(0);
					entityLivingBase.world.spawnEntity(ghoul);
				}
				else if(entityLivingBase.world.getDifficulty() == EnumDifficulty.HARD && entityLivingBase.world.rand.nextBoolean()
						|| entityLivingBase.world.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(entityLivingBase.world);
					entityzombie.copyLocationAndAnglesFrom(entityLivingBase);
					entityzombie.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
					if(entityLivingBase.isChild())
						entityzombie.setChild(true);
					entityLivingBase.world.removeEntity(entityLivingBase);
					entityLivingBase.world.spawnEntity(entityzombie);
				}
			} else if(entityLivingBase instanceof EntityPlayer){
				if(entityLivingBase.world.getDifficulty() == EnumDifficulty.HARD && entityLivingBase.world.rand.nextBoolean()
						|| entityLivingBase.world.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(entityLivingBase.world);
					entityzombie.copyLocationAndAnglesFrom(entityLivingBase);
					entityzombie.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
					if(entityLivingBase.isChild())
						entityzombie.setChild(true);
					entityLivingBase.world.spawnEntity(entityzombie);
				}
				entityLivingBase.removePotionEffect(AbyssalCraftAPI.coralium_plague);
			} else if(entityLivingBase instanceof EntitySquid)
				if(entityLivingBase.world.rand.nextBoolean()){
					EntityCoraliumSquid squid = new EntityCoraliumSquid(entityLivingBase.world);
					squid.copyLocationAndAnglesFrom(entityLivingBase);
					squid.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
					entityLivingBase.world.removeEntity(entityLivingBase);
					entityLivingBase.world.spawnEntity(squid);
				}
	}

	public static void applyDreadEffect(EntityLivingBase entityLivingBase, int amplifier) {
		if(entityLivingBase.ticksExisted % 100 == 0 && entityLivingBase.getRNG().nextFloat() > 0.3F
				&& entityLivingBase.world.getBiome(entityLivingBase.getPosition()) != ACBiomes.purged) {
			AxisAlignedBB axisalignedbb = entityLivingBase.getEntityBoundingBox().grow(3.0D, 3.0D, 3.0D);
			List<EntityLivingBase> list = entityLivingBase.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			for(EntityLivingBase entity : list)
				if(entity.getRNG().nextBoolean() && !entity.world.isRemote && entityLivingBase != entity)
					if(entity.isPotionActive(AbyssalCraftAPI.dread_plague) && entity.getActivePotionEffect(AbyssalCraftAPI.dread_plague).getAmplifier() > 0)
						entity.addPotionEffect(PlagueEventHandler.getEffect(entityLivingBase.getActivePotionEffect(AbyssalCraftAPI.dread_plague), 1));
					else entity.addPotionEffect(PlagueEventHandler.getEffect(entityLivingBase.getActivePotionEffect(AbyssalCraftAPI.dread_plague), entity.isPotionActive(AbyssalCraftAPI.dread_plague) && entityLivingBase.getRNG().nextInt(10) == 0 ? 1 : 0));
		}

		if(!ACConfig.no_dreadlands_spread)
			if(amplifier > 0 || ACConfig.hardcoreMode)
				if(entityLivingBase.ticksExisted % 100 == 0 && !entityLivingBase.world.isRemote)
					if(entityLivingBase.dimension != ACLib.dark_realm_id && entityLivingBase.dimension != ACLib.omothol_id)
						for(int x = entityLivingBase.getPosition().getX() - 1; x <= entityLivingBase.getPosition().getX() + 1; x++)
							for(int z = entityLivingBase.getPosition().getZ() - 1; z <= entityLivingBase.getPosition().getZ() + 1; z++)
								if(!(entityLivingBase.world.getBiome(new BlockPos(x, 0, z)) instanceof IDreadlandsBiome)
										&& entityLivingBase.world.getBiome(new BlockPos(x, 0, z)) != ACBiomes.purged)
									BiomeUtil.updateBiome(entityLivingBase.world, new BlockPos(x, 0, z), ACBiomes.dreadlands);

		if(entityLivingBase instanceof EntityPlayer && entityLivingBase.ticksExisted % 200 == 0)
			NecroDataCapability.getCap((EntityPlayer) entityLivingBase).triggerMiscUnlock("dread_plague");

		if(EntityUtil.isEntityDread(entityLivingBase)) return;

		if(ArmorUtil.hasHelmetWithResistance(entityLivingBase, AbyssalCraftAPI.dread)) return;

		if(entityLivingBase.ticksExisted % 25 >> amplifier == 0)
			entityLivingBase.attackEntityFrom(AbyssalCraftAPI.dread, 1);

		if(entityLivingBase instanceof EntityPlayer)
			((EntityPlayer)entityLivingBase).addExhaustion(0.025F * (amplifier+2));
		if(!entityLivingBase.world.isRemote && entityLivingBase.isDead
				&& entityLivingBase.world.rand.nextBoolean()
				&& entityLivingBase.world.getBiome(entityLivingBase.getPosition()) != ACBiomes.purged)
			if(entityLivingBase instanceof EntityZombie || entityLivingBase instanceof EntityAbyssalZombie
					|| entityLivingBase instanceof EntityAntiPlayer || entityLivingBase instanceof EntityAntiAbyssalZombie
					|| entityLivingBase instanceof EntityAntiZombie || entityLivingBase instanceof EntitySkeleton
					|| entityLivingBase instanceof EntityAntiSkeleton){
				EntityDreadling dreadling = new EntityDreadling(entityLivingBase.world);
				dreadling.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				dreadling.onInitialSpawn(null, null);
				entityLivingBase.world.spawnEntity(dreadling);
			} else if(entityLivingBase instanceof EntityPlayer){

				EntityDreadling dreadling = new EntityDreadling(entityLivingBase.world);
				dreadling.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.removePotionEffect(AbyssalCraftAPI.dread_plague);
				dreadling.onInitialSpawn(null, null);
				entityLivingBase.world.spawnEntity(dreadling);
			} else if(entityLivingBase instanceof EntitySkeletonGoliath){
				EntityDreadguard dg = new EntityDreadguard(entityLivingBase.world);
				dg.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				dg.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(dg);
			} else if(entityLivingBase instanceof EntityPig){
				EntityDemonPig dp = new EntityDemonPig(entityLivingBase.world);
				dp.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				dp.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(dp);
			} else if(entityLivingBase instanceof EntityCow){
				EntityDemonCow dc = new EntityDemonCow(entityLivingBase.world);
				dc.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				dc.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(dc);
			} else if(entityLivingBase instanceof EntityChicken){
				EntityDemonChicken dc = new EntityDemonChicken(entityLivingBase.world);
				dc.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				dc.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(dc);
			} else if(entityLivingBase instanceof EntitySheep){
				EntityDemonSheep ds = new EntityDemonSheep(entityLivingBase.world);
				ds.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				ds.onInitialSpawn(entityLivingBase.world.getDifficultyForLocation(entityLivingBase.getPosition()),(IEntityLivingData)null);
				entityLivingBase.world.spawnEntity(ds);
			} else if(!(entityLivingBase instanceof EntityPlayer)){
				EntityDreadSpawn ds = new EntityDreadSpawn(entityLivingBase.world);
				ds.copyLocationAndAnglesFrom(entityLivingBase);
				entityLivingBase.world.removeEntity(entityLivingBase);
				entityLivingBase.world.spawnEntity(ds);
			}
	}
}
