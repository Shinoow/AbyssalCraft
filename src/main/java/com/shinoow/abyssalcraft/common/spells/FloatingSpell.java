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

import com.shinoow.abyssalcraft.api.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FloatingSpell extends Spell {

	public FloatingSpell() {
		super("floating", 3, 15F, new Object[] {Items.FEATHER, Items.FEATHER});
		setColor(0xFFFFFF);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		return true;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {

		player.motionY = 0.1;
	}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {

		player.fallDistance = 0;
		player.motionY = 0.1;
	}
}
