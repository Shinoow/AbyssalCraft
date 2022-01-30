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

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.DisplayRoutesMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class DisplayRoutesSpell extends Spell {

	public DisplayRoutesSpell() {
		super("displayRoutes", 3, 100F, new Object[] {new ItemStack(Blocks.GLASS_PANE)});

	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		MutableBlockPos pos1 = new MutableBlockPos();
		for(int x = pos.getX() - 16; x < pos.getX() + 16; x++)
			for(int y = pos.getY() - 16; y < pos.getY() + 16; y++)
				for(int z = pos.getZ() - 16; z < pos.getZ() + 16; z++) {
					pos1.setPos(x, y, z);
					TileEntity te = world.getTileEntity(pos1);
					if(te != null) {
						IItemTransferCapability cap = ItemTransferCapability.getCap(te);
						if(cap != null && !cap.getTransferConfigurations().isEmpty())
							return true;
					}
				}
		return false;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {

	}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {

		List<BlockPos[]> routes = new ArrayList<>();

		MutableBlockPos pos1 = new MutableBlockPos();
		for(int x = pos.getX() - 16; x < pos.getX() + 16; x++)
			for(int y = pos.getY() - 16; y < pos.getY() + 16; y++)
				for(int z = pos.getZ() - 16; z < pos.getZ() + 16; z++) {
					pos1.setPos(x, y, z);
					TileEntity te = world.getTileEntity(pos1);
					if(te != null) {
						IItemTransferCapability cap = ItemTransferCapability.getCap(te);
						if(cap != null && cap.isRunning())
							for(ItemTransferConfiguration cfg : cap.getTransferConfigurations()) {
								List<BlockPos> route = Lists.asList(te.getPos(), cfg.getRoute());
								routes.add(route.toArray(new BlockPos[0]));
							}
					}
				}

		PacketDispatcher.sendTo(new DisplayRoutesMessage(routes), (EntityPlayerMP)player);
	}
}
