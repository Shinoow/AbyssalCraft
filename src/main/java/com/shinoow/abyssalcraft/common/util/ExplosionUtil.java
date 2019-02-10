/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.common.world.ACExplosion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ExplosionUtil {

	public static Explosion newODBExplosion(World world, Entity entity, double x, double y, double z, float strength, boolean antimatter, boolean smoke)
	{
		ACExplosion explosion = new ACExplosion(world, entity, x, y, z, strength, antimatter, smoke);
		if(net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) return explosion;
		explosion.doExplosionA();
		explosion.doExplosionB(strength <= 32);

		if(world instanceof WorldServer)
			for (EntityPlayer entityplayer : ((WorldServer)world).playerEntities)
				if (entityplayer.getDistanceSq(x, y, z) < 4096.0D)
					((EntityPlayerMP)entityplayer).connection.sendPacket(new SPacketExplosion(x, y, z , strength, explosion.getAffectedBlockPositions(), explosion.getPlayerKnockbackMap().get(entityplayer)));

		return explosion;
	}
}
