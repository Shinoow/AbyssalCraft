/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
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
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class LifeDrainMessage extends AbstractServerMessage<LifeDrainMessage> {

	int id;

	public LifeDrainMessage(){}

	public LifeDrainMessage(int id){
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		id = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, id, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		Entity e = player.worldObj.getEntityByID(id);
		if(e == null) return;

		if(e instanceof EntityLivingBase){
			EntityLivingBase target = (EntityLivingBase)e;
			int consumed = 0;
			for(int i = 0; i < 9; i++){
				ItemStack stack = player.inventory.getStackInSlot(i);
				if(stack.getItem() instanceof IEnergyContainerItem){
					consumed += ((IEnergyContainerItem)stack.getItem()).consumeEnergy(stack, 100);
					break;
				}
			}
			if(consumed == 100)
				if(!target.isDead && target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), 5))
					player.heal(5);
		}
	}

}
