/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.spell;

import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Subclass for Spells aimed at affecting a target<br>
 * Comes with "pre-packaged" with logic<br>
 * (Some bits are checked client-side, then processed server-side)
 * @author shinoow
 *
 */
public abstract class EntityTargetSpell extends Spell {

	private float range = 15;

	public EntityTargetSpell(String unlocalizedName, int bookType, float requiredEnergy, Object... reagents) {
		super(unlocalizedName, bookType, requiredEnergy, reagents);
	}

	public EntityTargetSpell(String unlocalizedName, float requiredEnergy, Object... reagents) {
		super(unlocalizedName, requiredEnergy, reagents);
	}

	protected void setRange(float range) {
		this.range = range;
	}

	public float getRange() {
		return range;
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {

		RayTraceResult r = SpellUtils.rayTraceTarget(getRange());
		if(r != null && SpellUtils.canPlayerHurt(player, r.entityHit))
			return canCastSpellOnTarget((EntityLivingBase) r.entityHit, scrollType);
		return false;
	}

	/**
	 * Checks if the spell can be cast on the target
	 */
	protected abstract boolean canCastSpellOnTarget(EntityLivingBase target, ScrollType scrollType);

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {

		boolean canCast = false;
		RayTraceResult r = SpellUtils.rayTraceTarget(getRange());
		if(r != null && SpellUtils.canPlayerHurt(player, r.entityHit)
				&& canCastSpellOnTarget((EntityLivingBase) r.entityHit, scrollType))
			canCast = true;
		if(canCast)
			SpellUtils.processEntitySpell(r.entityHit.getEntityId(), getID(), scrollType);
	}

	/**
	 * Casts the spell on the target<br>
	 * (this is where you do the things)
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 * @param target Target of the spell
	 */
	public abstract void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType, EntityLivingBase target);

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {}

}
