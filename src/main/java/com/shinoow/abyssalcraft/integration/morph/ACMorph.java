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
package com.shinoow.abyssalcraft.integration.morph;

import java.lang.reflect.InvocationTargetException;

import morph.api.Ability;
import morph.api.Api;

import com.shinoow.abyssalcraft.api.integration.ACPlugin;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.client.model.entity.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;

@ACPlugin
public class ACMorph implements IACPlugin {

	Ability hostile, sunburn, climb, fallNegate, fly, fireImmunity, flyNerf, water, chicken;

	ModelRemnant modelRemnant = new ModelRemnant();
	ModelSkeletonGoliath modelSkeletonGoliath = new ModelSkeletonGoliath(false);
	ModelSacthoth modelSacthoth = new ModelSacthoth();
	ModelJzahar modelJzahar = new ModelJzahar(false);
	ModelShadowCreature modelShadowc = new ModelShadowCreature();
	ModelShadowMonster modelShadowm = new ModelShadowMonster();
	ModelShadowBeast modelShadowb = new ModelShadowBeast();
	ModelDreadling modelDreadling = new ModelDreadling();
	ModelDreadSpawn modelDreadSpawn = new ModelDreadSpawn();
	ModelChagarothFist modelChagarothFist = new ModelChagarothFist();
	ModelChagarothSpawn modelChagarothSpawn = new ModelChagarothSpawn();
	ModelDG modelDG = new ModelDG();
	ModelLesserShoggoth modelLesserShoggoth = new ModelLesserShoggoth();

	@Override
	public String getModName(){
		return "Morph";
	}

	@Override
	public boolean canLoad() {

		return Loader.isModLoaded("Morph");
	}

	@Override
	public void preInit(){}

	@Override
	public void init(){
		getAbilities();

		if(FMLCommonHandler.instance().getEffectiveSide().isClient()){
			Api.registerArmForModel(modelRemnant, modelRemnant.rightarm);
			Api.registerArmForModel(modelSkeletonGoliath, modelSkeletonGoliath.rightarm);
			Api.registerArmForModel(modelSacthoth, modelSacthoth.rightarm1);
			Api.registerArmForModel(modelJzahar, modelJzahar.arm);
			Api.registerArmForModel(modelShadowc, modelShadowc.RightArm1);
			Api.registerArmForModel(modelShadowm, modelShadowm.Rarm1);
			Api.registerArmForModel(modelShadowb, modelShadowb.rarm1);
			Api.registerArmForModel(modelDreadling, modelDreadling.rightarm);
			Api.registerArmForModel(modelDreadSpawn, modelDreadSpawn.arm);
			Api.registerArmForModel(modelChagarothFist, modelChagarothFist.arm1);
			Api.registerArmForModel(modelChagarothSpawn, modelChagarothSpawn.smallspike2);
			Api.registerArmForModel(modelDG, modelDG.rarm1);
			Api.registerArmForModel(modelLesserShoggoth, modelLesserShoggoth.rArm01a);
		}

		Api.blacklistEntity(EntityDragonMinion.class);
		Api.blacklistEntity(EntityDragonBoss.class);
	}

	@Override
	public void postInit(){
	}

	private void getAbilities(){
		boolean failed = false;
		try {
			hostile = (Ability)Class.forName("morph.common.ability.AbilityHostile").newInstance();
			sunburn = (Ability)Class.forName("morph.common.ability.AbilitySunburn").newInstance();
			climb = (Ability)Class.forName("morph.common.ability.AbilityClimb").newInstance();
			fallNegate = (Ability)Class.forName("morph.common.ability.AbilityFallNegate").newInstance();
			fly = (Ability)Class.forName("morph.common.ability.AbilityFly").getConstructor(Boolean.TYPE).newInstance(false);
			fireImmunity = (Ability)Class.forName("morph.common.ability.AbilityFireImmunity").newInstance();
			flyNerf = (Ability)Class.forName("morph.common.ability.AbilityFly").newInstance();
			water = (Ability)Class.forName("morph.common.ability.AbilitySwim").getConstructor(Boolean.TYPE).newInstance(true);
			chicken = (Ability)Class.forName("morph.common.ability.AbilityFloat").getConstructor(Double.TYPE, Boolean.TYPE).newInstance(-0.114D, true);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			ACLogger.warning("Failed to find all Ability classes (or something similar to that):");
			e.printStackTrace();
			failed = true;
		}
		loadAbilities(failed);
	}

	private void loadAbilities(boolean loadingFailed){

		if(loadingFailed)
			ACLogger.info("Something screwed up when getting the Abilities, so they will not be added.");
		else{
			Ability.mapAbilities(EntityDepthsGhoul.class, hostile, sunburn, water);
			Ability.mapAbilities(EntityEvilpig.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityAbyssalZombie.class, hostile, sunburn, water);
			Ability.mapAbilities(EntityJzahar.class, hostile);
			Ability.mapAbilities(EntityDreadgolem.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityDreadguard.class, hostile, fireImmunity, water);
			Ability.mapAbilities(EntityShadowCreature.class, hostile);
			Ability.mapAbilities(EntityShadowMonster.class, hostile);
			Ability.mapAbilities(EntityDreadling.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityDreadSpawn.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityDemonPig.class, hostile, fireImmunity);
			Ability.mapAbilities(EntitySkeletonGoliath.class, hostile, sunburn);
			Ability.mapAbilities(EntityChagarothSpawn.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityChagarothFist.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityChagaroth.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityShadowBeast.class, hostile);
			Ability.mapAbilities(EntitySacthoth.class, hostile, fireImmunity, fallNegate, climb);
			Ability.mapAbilities(EntityAntiAbyssalZombie.class, hostile);
			Ability.mapAbilities(EntityAntiBat.class, flyNerf);
			Ability.mapAbilities(EntityAntiChicken.class, chicken);
			Ability.mapAbilities(EntityAntiCreeper.class, hostile);
			Ability.mapAbilities(EntityAntiGhoul.class, hostile);
			Ability.mapAbilities(EntityAntiPlayer.class, hostile);
			Ability.mapAbilities(EntityAntiSkeleton.class, hostile);
			Ability.mapAbilities(EntityAntiSpider.class, hostile, climb);
			Ability.mapAbilities(EntityAntiZombie.class, hostile);
			Ability.mapAbilities(EntityGreaterDreadSpawn.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityLesserDreadbeast.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityLesserShoggoth.class, hostile, water);
			Ability.mapAbilities(EntityEvilCow.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityEvilChicken.class, hostile, fireImmunity, chicken);
			Ability.mapAbilities(EntityDemonCow.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityDemonChicken.class, hostile, fireImmunity, chicken);
		}
	}
}