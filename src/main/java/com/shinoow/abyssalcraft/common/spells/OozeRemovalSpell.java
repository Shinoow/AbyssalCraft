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
package com.shinoow.abyssalcraft.common.spells;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OozeRemovalSpell extends Spell {

	public OozeRemovalSpell() {
		super("oozeremoval", 100F, Blocks.SPONGE);
		setRequiresCharging();
		setColor(0x000);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		for(BlockPos pos1 : BlockPos.getAllInBoxMutable(new BlockPos(player.posX - 3, player.posY - 3, player.posZ - 3),
				new BlockPos(player.posX + 3, player.posY + 3, player.posZ + 3)))
			if(world.getBlockState(pos1).getBlock() == ACBlocks.shoggoth_ooze)
				return true;
		return false;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {
		for(BlockPos pos1 : BlockPos.getAllInBox(new BlockPos(player.posX - 3, player.posY - 3, player.posZ - 3),
				new BlockPos(player.posX + 3, player.posY + 3, player.posZ + 3)))
			if(world.getBlockState(pos1).getBlock() == ACBlocks.shoggoth_ooze)
				world.destroyBlock(pos1, false);
	}

}
