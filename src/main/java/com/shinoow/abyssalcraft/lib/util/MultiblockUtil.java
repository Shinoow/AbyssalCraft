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
package com.shinoow.abyssalcraft.lib.util;

import com.shinoow.abyssalcraft.api.energy.structure.IPlaceOfPower;
import com.shinoow.abyssalcraft.api.energy.structure.StructureHandler;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiblockUtil {

	public static boolean tryFormMultiblock(World world, BlockPos pos, int booktype, EntityPlayer player) {

		for(IPlaceOfPower place : StructureHandler.instance().getStructures())
			if(NecroDataCapability.getCap(player).isUnlocked(place.getUnlockCondition(), player) &&
					booktype >= place.getBookType() && place.canConstruct(world, pos, player)) {
				if(!world.isRemote)
					place.construct(world, pos);
				return true;
			}
		return false;
	}
}
