/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.actions;

import com.shinoow.abyssalcraft.api.block.IRitualAltar;
import com.shinoow.abyssalcraft.api.necronomicon.INecronomiconAction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PerformRitualAction implements INecronomiconAction {

	@Override
	public boolean canExecuteAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		return world.getTileEntity(pos) instanceof IRitualAltar;
	}

	@Override
	public EnumActionResult executeAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		IRitualAltar altar = (IRitualAltar)world.getTileEntity(pos);

		altar.performRitual(world, pos, player);

		return EnumActionResult.SUCCESS;
	}

}
