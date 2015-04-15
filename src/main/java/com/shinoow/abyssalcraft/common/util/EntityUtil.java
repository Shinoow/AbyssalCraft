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
package com.shinoow.abyssalcraft.common.util;

import java.util.UUID;

import com.shinoow.abyssalcraft.api.entity.*;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.launchwrapper.Launch;

public final class EntityUtil {

	private EntityUtil(){}

	/**
	 * Checks if the Entity is immune to the Coralium Plague
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityCoralium(EntityLivingBase par1){
		return par1 instanceof ICoraliumEntity || par1 instanceof EntityPlayer && isPlayerCoralium((EntityPlayer)par1);
	}

	/**
	 * Checks if a Player has a certain name, and nulls the Coralium Plague if they do
	 * @param par1 The Player to check
	 * @return True if the Player has a certain name, otherwise false
	 */
	public static final boolean isPlayerCoralium(EntityPlayer par1){
		if(Vars.dev)
			return par1.getCommandSenderName().equals("shinoow") || par1.getCommandSenderName().equals("Oblivionaire");
		else return par1.getUniqueID().equals(Vars.uuid1) || par1.getUniqueID().equals(Vars.uuid2);
	}

	/**
	 * Checks if the Entity is immune to the Dread Plague
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityDread(EntityLivingBase par1){
		return par1 instanceof IDreadEntity;
	}

	/**
	 * Checks if the Entity is immune to Antimatter
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityAnti(EntityLivingBase par1){
		return par1 instanceof IAntiEntity;
	}

	static class Vars{
		static boolean dev = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
		static UUID uuid1 = UUID.fromString("a5d8abca-0979-4bb0-825a-f1ccda0b350b");
		static UUID uuid2 = UUID.fromString("08f3211c-d425-47fd-afd8-f0e7f94152c4");
	}
}