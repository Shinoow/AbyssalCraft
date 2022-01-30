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
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.ToggleStateMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ToggleStateSpell extends Spell {

	public ToggleStateSpell() {
		super("toggleState", 3, 1000F, new Object[] {Blocks.LEVER});

	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		if(world.isRemote) {
			boolean foundTe = false;
			RayTraceResult rt = player.rayTrace(16, 0);
			if(rt != null && rt.typeOfHit == Type.BLOCK) {
				BlockPos pos1 = rt.getBlockPos();
				TileEntity te = world.getTileEntity(pos1);
				if(te != null && ItemTransferCapability.getCap(te) != null)
					foundTe = true;
			}

			if(foundTe)
				return true;
			else {
				MutableBlockPos pos1 = new MutableBlockPos();
				for(int x = pos.getX() - 16; x < pos.getX() + 16; x++)
					for(int y = pos.getY() - 16; y < pos.getY() + 16; y++)
						for(int z = pos.getZ() - 16; z < pos.getZ() + 16; z++) {
							pos1.setPos(x, y, z);
							TileEntity te = world.getTileEntity(pos1);
							if(te != null && ItemTransferCapability.getCap(te) != null)
								return true;
						}
			}
		}
		return false;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {
		RayTraceResult rt = player.rayTrace(16, 0);
		if(rt != null && rt.typeOfHit == Type.BLOCK) {
			BlockPos pos1 = rt.getBlockPos();
			TileEntity te = world.getTileEntity(pos1);
			if(te != null && ItemTransferCapability.getCap(te) != null)
				PacketDispatcher.sendToServer(new ToggleStateMessage(pos1));
			else
				PacketDispatcher.sendToServer(new ToggleStateMessage(pos));
		}
	}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {}
}
