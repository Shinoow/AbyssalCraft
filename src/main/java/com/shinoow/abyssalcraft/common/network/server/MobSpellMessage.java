/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.api.spell.*;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MobSpellMessage extends AbstractServerMessage<MobSpellMessage> {

	private int id;
	private String spellID;
	private ScrollType scrollType;

	public MobSpellMessage(){}

	public MobSpellMessage(int id, String spell, ScrollType scrollType){
		this.id = id;
		spellID = spell;
		this.scrollType = scrollType;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		id = ByteBufUtils.readVarInt(buffer, 5);
		spellID = ByteBufUtils.readUTF8String(buffer);
		scrollType = ScrollType.byQuality(ByteBufUtils.readVarInt(buffer, 5));
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, id, 5);
		ByteBufUtils.writeUTF8String(buffer, spellID);
		ByteBufUtils.writeVarInt(buffer, scrollType.getQuality(), 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		Entity e = player.world.getEntityByID(id);
		if(e == null) return;
		Spell spell = SpellRegistry.instance().getSpell(spellID);

		if(!(spell instanceof EntityTargetSpell)) return;

		if(e instanceof EntityLivingBase && SpellUtils.canPlayerHurt(player, e)){
			EntityLivingBase target = (EntityLivingBase)e;

			((EntityTargetSpell) spell).castSpellOnTarget(player.world, player.getPosition(), player, scrollType, target);
		}
	}
}
