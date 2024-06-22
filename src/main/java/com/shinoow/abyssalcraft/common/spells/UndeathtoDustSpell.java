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

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.EntityTargetSpell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class UndeathtoDustSpell extends EntityTargetSpell {

	public UndeathtoDustSpell() {
		super("undeathtodust", 1000F, Items.BONE);
		setParchment(new ItemStack(ACItems.moderate_scroll));
		setRequiresCharging();
		setColor(0x1a1b1c);
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target) {

		return target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, EntityLivingBase target) {

		if(target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && target.isNonBoss()) {
			player.world.removeEntity(target);
			((WorldServer)player.world).spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, target.posX, target.posY + 2.0D, target.posZ, 0, 0.0D, 0.0D, 0.0D, 1.0);
		}
	}

}
