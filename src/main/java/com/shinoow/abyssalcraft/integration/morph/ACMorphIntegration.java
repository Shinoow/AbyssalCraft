/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.integration.morph;

import java.lang.reflect.InvocationTargetException;

import morph.api.Ability;
import morph.api.Api;

import com.shinoow.abyssalcraft.client.model.entity.ModelChagarothFist;
import com.shinoow.abyssalcraft.client.model.entity.ModelChagarothSpawn;
import com.shinoow.abyssalcraft.client.model.entity.ModelDG;
import com.shinoow.abyssalcraft.client.model.entity.ModelDreadSpawn;
import com.shinoow.abyssalcraft.client.model.entity.ModelDreadling;
import com.shinoow.abyssalcraft.client.model.entity.ModelJzahar;
import com.shinoow.abyssalcraft.client.model.entity.ModelRemnant;
import com.shinoow.abyssalcraft.client.model.entity.ModelSacthoth;
import com.shinoow.abyssalcraft.client.model.entity.ModelShadowBeast;
import com.shinoow.abyssalcraft.client.model.entity.ModelShadowCreature;
import com.shinoow.abyssalcraft.client.model.entity.ModelShadowMonster;
import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliath;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityChagaroth;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothFist;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityEvilpig;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.entity.EntitySacthoth;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiBat;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiChicken;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSpider;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;
import com.shinoow.abyssalcraft.common.util.ACLogger;

public class ACMorphIntegration {

	static Ability hostile, sunburn, climb, fallNegate, fly, fireImmunity, flyNerf, water, chicken;

	static ModelRemnant modelRemnant = new ModelRemnant();
	static ModelSkeletonGoliath modelSkeletonGoliath = new ModelSkeletonGoliath(false);
	static ModelSacthoth modelSacthoth = new ModelSacthoth();
	static ModelJzahar modelJzahar = new ModelJzahar();
	static ModelShadowCreature modelShadowc = new ModelShadowCreature();
	static ModelShadowMonster modelShadowm = new ModelShadowMonster();
	static ModelShadowBeast modelShadowb = new ModelShadowBeast();
	static ModelDreadling modelDreadling = new ModelDreadling();
	static ModelDreadSpawn modelDreadSpawn = new ModelDreadSpawn();
	static ModelChagarothFist modelChagarothFist = new ModelChagarothFist();
	static ModelChagarothSpawn modelChagarothSpawn = new ModelChagarothSpawn();
	static ModelDG modelDG = new ModelDG();

	public static void init(){
		getAbilities();
	}

	private static void getAbilities(){
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
		integrate(failed);
	}

	private static void integrate(boolean loadingFailed){

		if(loadingFailed)
			ACLogger.info("Something screwed up when getting the Abilities, so they will not be added.");
		else{
			Ability.mapAbilities(EntityDepthsGhoul.class, hostile, sunburn, water);
			Ability.mapAbilities(EntityEvilpig.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityAbyssalZombie.class, hostile, sunburn, water);
			Ability.mapAbilities(EntityJzahar.class, hostile);
			Ability.mapAbilities(EntityDreadgolem.class, hostile, fireImmunity);
			Ability.mapAbilities(EntityDreadguard.class, hostile, fireImmunity, water);
			Ability.mapAbilities(EntityDragonMinion.class, hostile, fly);
			Ability.mapAbilities(EntityDragonBoss.class, hostile, fly);
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
		}

		Api.registerArmForModel(modelRemnant, modelRemnant.rightarm);
		Api.registerArmForModel(modelSkeletonGoliath, modelSkeletonGoliath.rightarm);
		Api.registerArmForModel(modelSacthoth, modelSacthoth.rightarm1);
		Api.registerArmForModel(modelJzahar, modelJzahar.rightarm);
		Api.registerArmForModel(modelShadowc, modelShadowc.RightArm1);
		Api.registerArmForModel(modelShadowm, modelShadowm.Rarm1);
		Api.registerArmForModel(modelShadowb, modelShadowb.rarm1);
		Api.registerArmForModel(modelDreadling, modelDreadling.rightarm);
		Api.registerArmForModel(modelDreadSpawn, modelDreadSpawn.arm);
		Api.registerArmForModel(modelChagarothFist, modelChagarothFist.arm1);
		Api.registerArmForModel(modelChagarothSpawn, modelChagarothSpawn.smallspike2);
		Api.registerArmForModel(modelDG, modelDG.rarm1);

	}
}