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
import java.util.stream.Collectors;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.rending.Rending;
import com.shinoow.abyssalcraft.api.rending.RendingRegistry;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class StaffOfRendingMessage extends AbstractServerMessage<StaffOfRendingMessage> {

	int id;
	EnumHand hand;

	public StaffOfRendingMessage(){}

	public StaffOfRendingMessage(int id, EnumHand hand){
		this.id = id;
		this.hand = hand;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		id = ByteBufUtils.readVarInt(buffer, 5);
		hand = ByteBufUtils.readVarInt(buffer, 5) == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, id, 5);
		ByteBufUtils.writeVarInt(buffer, hand == EnumHand.MAIN_HAND ? 0 : 1, 5);
	}

	private boolean drain(EntityPlayer player, Entity entity, ItemStack stack) {
		IStaffOfRending staff = (IStaffOfRending)stack.getItem();
		int drainAmount = staff.getDrainAmount(stack);
		boolean success = false;
		if(entity instanceof EntityLiving) {
			EntityLiving target = (EntityLiving)entity;
			for(Rending r : RendingRegistry.instance().getRendings().stream().filter(r -> r.isApplicable(target)).collect(Collectors.toList()))
				if(!target.isDead)
					if(success || target.attackEntityFrom(DamageSource.causePlayerDamage(player), drainAmount)) {
						staff.increaseEnergy(stack, r.getName());
						success = true;
					}
		} else if(entity instanceof MultiPartEntityPart) {
			MultiPartEntityPart target = (MultiPartEntityPart)entity;
			EntityLiving parent = (EntityLiving) target.parent;
			for(Rending r : RendingRegistry.instance().getRendings().stream().filter(r -> r.isApplicable(parent)).collect(Collectors.toList()))
				if(!target.isDead)
					if(success || target.attackEntityFrom(DamageSource.causePlayerDamage(player), drainAmount)) {
						staff.increaseEnergy(stack, r.getName());
						success = true;
					}
		}
		return success;
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.getHeldItem(hand).isEmpty()) return;
		ItemStack stack = player.getHeldItem(hand);
		Entity entity = player.world.getEntityByID(id);
		if(entity == null) return;

		if(stack.getItem() instanceof IStaffOfRending)
			if(entity instanceof EntityLiving || entity instanceof MultiPartEntityPart && ((MultiPartEntityPart) entity).parent instanceof EntityLiving)
				if(drain(player, entity, stack) && EnchantmentHelper.getEnchantmentLevel(AbyssalCraftAPI.multi_rend, stack) == 1)
					player.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().grow(3))
					.stream().forEach(e -> drain(player, e, stack));
	}
}
