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
package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.common.inventory.ContainerMaterializer;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class TransferStackMessage extends AbstractServerMessage<TransferStackMessage> {

	public TransferStackMessage(){}

	private int slot;
	private ItemStack stack;

	public TransferStackMessage(int slot, ItemStack stack){
		this.slot = slot;
		this.stack = stack;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		slot = ByteBufUtils.readVarInt(buffer, 5);
		stack = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, slot, 5);
		ByteBufUtils.writeItemStack(buffer, stack);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.openContainer instanceof ContainerMaterializer && canMaterialize(stack, player.openContainer.getSlot(0).getStack())){
			player.openContainer.putStackInSlot(slot, stack);
			player.openContainer.detectAndSendChanges();
		}
	}

	private boolean canMaterialize(ItemStack stack, ItemStack bag){

		for(ItemStack stack1 : MaterializerRecipes.instance().getMaterializationResult(bag))
			if(APIUtils.areStacksEqual(stack, stack1))
				return true;

		return false;
	}
}
