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
package com.shinoow.abyssalcraft.common.spells;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.spell.EntityTargetSpell;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DetachmentSpell extends EntityTargetSpell {

	public DetachmentSpell() {
		super("detachment", 100, Items.IRON_INGOT);
		setScrollType(ScrollType.MODERATE);
		setRequiresCharging();
		setColor(0x463faa);
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target, ScrollType scrollType) {
		for(ItemStack stack : target.getArmorInventoryList())
			if(!stack.isEmpty())
				return true;
		return false;
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType, EntityLivingBase target) {
		List<EntityEquipmentSlot> slots = new ArrayList<>();
		for(EntityEquipmentSlot slot : EntityEquipmentSlot.values())
			if(target.hasItemInSlot(slot))
				slots.add(slot);
		EntityEquipmentSlot removeSlot = slots.get(player.world.rand.nextInt(slots.size()));
		target.entityDropItem(target.getItemStackFromSlot(removeSlot), 0);
		target.setItemStackToSlot(removeSlot, ItemStack.EMPTY);
	}

}
