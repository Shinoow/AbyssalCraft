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

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;
import com.shinoow.abyssalcraft.init.InitHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class InterdimensionalCageMessage extends AbstractServerMessage<InterdimensionalCageMessage> {

	int id;
	EnumHand hand;

	public InterdimensionalCageMessage(){}

	public InterdimensionalCageMessage(int id, EnumHand hand){
		this.id = id;
		this.hand = hand;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		id = ByteBufUtils.readVarInt(buffer, 4);
		hand = ByteBufUtils.readVarInt(buffer, 4) == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, id, 4);
		ByteBufUtils.writeVarInt(buffer, hand == EnumHand.MAIN_HAND ? 0 : 1, 4);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.getHeldItem(hand) == null) return;
		ItemStack stack = player.getHeldItem(hand);
		Entity e = player.world.getEntityByID(id);
		if(e == null) return;
		if(InitHandler.INSTANCE.isEntityBlacklisted(e)) return;

		if(stack.getItem() == ACItems.interdimensional_cage && stack.getItem() instanceof IEnergyContainerItem)
			if(e instanceof EntityLivingBase){
				EntityLivingBase target = (EntityLivingBase)e;
				if(target.isNonBoss() && target.getPassengers().isEmpty() && target.getRidingEntity() == null)
					if(!target.isDead && ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= getPEFromSize(target.width, target.height)){
						NBTTagCompound tag = target.serializeNBT();
						stack.getTagCompound().setTag("Entity", tag);
						stack.getTagCompound().setString("EntityName", target.getName());
						((IEnergyContainerItem)stack.getItem()).consumeEnergy(stack, getPEFromSize(target.width, target.height));
						player.world.removeEntity(target);
					}
			}
	}

	private float getPEFromSize(float width, float height){
		return height * width * width * 100;
	}
}
