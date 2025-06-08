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
package com.shinoow.abyssalcraft.common.spells;

import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;
import com.shinoow.abyssalcraft.common.entity.EntityCompassTentacle;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CompassSpell extends Spell {

	public CompassSpell() {
		super("compass", 1000F, Items.COMPASS);
		setRequiresCharging();
		setColor(0xa9b598);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {

		return world.provider.getDimension() == ACLib.omothol_id;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {

		world.playSound(player, pos, ACSounds.compass, player.getSoundCategory(), 3F, 1F);
	}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {

		EntityCompassTentacle e = new EntityCompassTentacle(world);
		e.setPosition(pos.getX(),pos.getY(), pos.getZ());
		world.spawnEntity(e);
		player.sendMessage(new TextComponentString("Ftah J'zahar fhtagn, nog"));

	}

}
