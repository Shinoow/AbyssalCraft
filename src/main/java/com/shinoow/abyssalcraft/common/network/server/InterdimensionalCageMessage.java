/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

public class InterdimensionalCageMessage extends AbstractServerMessage<InterdimensionalCageMessage> {

	int id;

	public InterdimensionalCageMessage(){}

	public InterdimensionalCageMessage(int id){
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		id = ByteBufUtils.readVarInt(buffer, 4);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, id, 4);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.getCurrentEquippedItem() == null) return;
		ItemStack stack = player.getCurrentEquippedItem();
		Entity e = player.worldObj.getEntityByID(id);
		if(e == null) return;

		if(stack.getItem() == ACItems.interdimensional_cage && stack.getItem() instanceof IEnergyContainerItem)
			if(e instanceof EntityLivingBase){
				EntityLivingBase target = (EntityLivingBase)e;
				if(!(target instanceof IBossDisplayData) && target.riddenByEntity == null && target.ridingEntity == null)
					if(!target.isDead && ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= getPEFromSize(target.width, target.height)){
						NBTTagCompound tag = target.serializeNBT();
						stack.getTagCompound().setTag("Entity", tag);
						stack.getTagCompound().setString("EntityName", target.getName());
						((IEnergyContainerItem)stack.getItem()).consumeEnergy(stack, getPEFromSize(target.width, target.height));
						player.worldObj.removeEntity(target);
					}
			}
	}

	private float getPEFromSize(float width, float height){
		return height * width * width * 100;
	}
}
