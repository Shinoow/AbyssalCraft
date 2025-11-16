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
package com.shinoow.abyssalcraft.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Utility class for sound-related stuff
 * (might be completely pointless)
 * @author shinoow
 *
 */
public class SoundUtil {

	/**
	 * Garbage method name for playing a sound with "nearby volume and pitch"
	 */
	public static void playSoundNearby(World world, BlockPos pos, SoundEvent sound, SoundCategory category) {
		playSound(world, null, pos, 0.5, sound, category, 0.5f,  world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
	}

	public static void playSound(World world, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
		world.playSound(null, x, y, z, sound, category, volume, pitch);
	}

	public static void playSound(World world, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch) {
		playSound(world, null, pos, 0.5, sound, category, volume, pitch);
	}

	public static void playSound(World world, BlockPos pos, double offset, SoundEvent sound, SoundCategory category, float volume, float pitch) {
		playSound(world, null, pos, offset, sound, category, volume, pitch);
	}

	public static void playSound(World world, EntityPlayer player, BlockPos pos, double offset, SoundEvent sound, SoundCategory category, float volume, float pitch) {
		world.playSound(player, pos.getX() + offset, pos.getY() + offset, pos.getZ() + offset, sound, category, volume, pitch);
	}
}
