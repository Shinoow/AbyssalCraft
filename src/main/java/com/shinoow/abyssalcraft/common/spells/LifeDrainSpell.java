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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LifeDrainSpell extends EntityTargetSpell {

	public LifeDrainSpell() {
		super("lifedrain", 100, Items.APPLE);
		setParchment(new ItemStack(ACItems.basic_scroll));
		setRequiresCharging();
		setColor(0xa00404);
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target) {

		return true;
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, EntityLivingBase target) {
		if(!target.isDead && target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), 5)) {
			player.heal(5);
			pos = pos.up();
			BlockPos pos1 = target.getPosition();

			Vec3d vec = new Vec3d(pos1.subtract(pos)).normalize();

			double d = Math.sqrt(pos1.distanceSq(pos));

			for(int i = 0; i < d * 15; i++){
				double i1 = i / 15D;
				double xp = pos.getX() + vec.x * i1 + .5;
				double yp = pos.getY() + vec.y * i1 + .5;
				double zp = pos.getZ() + vec.z * i1 + .5;
				((WorldServer)player.world).spawnParticle(EnumParticleTypes.FLAME, xp, yp, zp, 0, vec.x * .1, .15, vec.z * .1, 1.0);
			}
		}
	}
}
