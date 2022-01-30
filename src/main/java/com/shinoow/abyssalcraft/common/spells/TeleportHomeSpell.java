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
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;
import com.shinoow.abyssalcraft.common.world.TeleporterHomeSpell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class TeleportHomeSpell extends Spell {

	public TeleportHomeSpell() {
		super("teleportHome", 1000F, new Object[] {new ItemStack(Items.BED, 1, OreDictionary.WILDCARD_VALUE)});
		setRequiresCharging();
		setScrollType(ScrollType.MODERATE);
		setColor(0x0565ff);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		return player.getBedLocation(player.getSpawnDimension()) != null;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {

		BlockPos bedPos = player.getBedLocation(player.getSpawnDimension());

		if(player.dimension == player.getSpawnDimension())
			player.setPositionAndUpdate(bedPos.getX() + 0.5D, bedPos.getY(), bedPos.getZ() + 0.5D);
		else {
			TeleporterHomeSpell teleporter = new TeleporterHomeSpell(player.getServer().getWorld(player.getSpawnDimension()), bedPos);
			player.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)player, player.getSpawnDimension(), teleporter);
		}
	}

}
