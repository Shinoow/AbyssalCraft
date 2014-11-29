/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.common.world.ACExplosion;

public class ExplosionUtil {

	public static ACExplosion newODBExplosion(World par0World, Entity par1Entity, double par2, double par4, double par6, float par8, int par9, boolean par10, boolean par11)
	{
		ACExplosion explosion = new ACExplosion(par0World, par1Entity, par2, par4, par6, par8, par9);
		explosion.isAntimatter = par10;
		explosion.isSmoking = par11;
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		return explosion;
	}
}