/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.BlockButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockACButton extends BlockButton {

	public BlockACButton(boolean par1, String tooltype, int harvestlevel, String texture) {
		super(par1);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACButton(boolean par1, String texture) {
		super(par1);
	}

	@Override
	protected void func_185615_a(EntityPlayer p_185615_1_, World player, BlockPos pos)
	{
		player.playSound(p_185615_1_, pos, SoundEvents.block_stone_button_click_on, SoundCategory.BLOCKS, 0.3F, 0.6F);
	}

	@Override
	protected void func_185617_b(World worldIn, BlockPos pos)
	{
		worldIn.playSound((EntityPlayer)null, pos, SoundEvents.block_stone_button_click_off, SoundCategory.BLOCKS, 0.3F, 0.5F);
	}
}
