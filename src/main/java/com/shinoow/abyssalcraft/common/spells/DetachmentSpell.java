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

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellUtils;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.MobSpellMessage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DetachmentSpell extends Spell {

	public DetachmentSpell() {
		super("detachment", 100, Items.IRON_INGOT);
		setParchment(new ItemStack(ACItems.scroll, 1, 2));
		setRequiresCharging();
		setColor(0x463faa);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		if(world.isRemote){
			RayTraceResult r = AbyssalCraftClientEventHooks.getMouseOverExtended(15);
			if(r != null && r.entityHit instanceof EntityLivingBase)
				if(SpellUtils.canPlayerHurt(player, r.entityHit))
					for(ItemStack stack : ((EntityLivingBase)r.entityHit).getArmorInventoryList())
						if(!stack.isEmpty())
							return true;
		}
		return false;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {
		RayTraceResult r = AbyssalCraftClientEventHooks.getMouseOverExtended(15);
		if(r != null && r.entityHit instanceof EntityLivingBase){
			boolean hasInventory = false;
			for(ItemStack stack : ((EntityLivingBase)r.entityHit).getArmorInventoryList())
				if(!stack.isEmpty()) {
					hasInventory = true;
					break;
				}
			if(hasInventory)
				PacketDispatcher.sendToServer(new MobSpellMessage(r.entityHit.getEntityId(), 3));
		}
	}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {}

}
