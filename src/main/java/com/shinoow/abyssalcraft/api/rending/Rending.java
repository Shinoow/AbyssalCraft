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
package com.shinoow.abyssalcraft.api.rending;

import java.util.function.Predicate;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

/**
 * A Staff of Rending target, with its corresponding energy type and output
 * @author shinoow
 *
 * @since 1.29.0
 *
 */
public class Rending {

	private final String name, jeiDescription;
	private final int maxEnergy, dimension;
	private final ItemStack output;
	private final Predicate<EntityLiving> rendingPredicate;

	/**
	 * A Staff of Rending target
	 * @param name Energy name (prefixed with "tooltip.drainstaff.energy." for toolip)
	 * @param maxEnergy Amount of energy before the output is created
	 * @param output Output created by this rending
	 * @param rendingPredicate Predicate that determines if the target can be drained (if you don't want to target bosses, you'll have to
	 * add a check for that yourself)
	 * @param jeiDescription Description for JEI
	 * @param dimension Dimension for JEI (set to OreDictionary.WILDCARD_VALUE if your Rending isn't dimension-specific)
	 */
	public Rending(String name, int maxEnergy, ItemStack output, Predicate<EntityLiving> rendingPredicate, String jeiDescription, int dimension) {
		this.name = name;
		this.maxEnergy = maxEnergy;
		this.output = output;
		this.rendingPredicate = rendingPredicate;
		this.jeiDescription = jeiDescription;
		this.dimension = dimension;
	}

	/**
	 * Returns the Rending name (used for NBT storage)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the tooltip (energy name in lowercase prefixed by "tooltip.drainstaff.energy.")
	 */
	public String getTooltip() {
		return String.format("tooltip.drainstaff.energy.%s", name.toLowerCase());
	}

	/**
	 * Returns the Rending description used in JEI
	 */
	public String getJeiDescription() {
		return jeiDescription;
	}

	/**
	 * Returns the amount of energy that needs to be collected to create the output
	 */
	public int getMaxEnergy() {
		return maxEnergy;
	}

	/**
	 * Returns the dimension displayed in JEI
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * Returns the Rending output
	 */
	public ItemStack getOutput() {
		return output.copy();
	}

	/**
	 * Return the predicate used to determine if a target can be drained
	 */
	public Predicate<EntityLiving> getRendingFunction() {
		return rendingPredicate;
	}

	/**
	 * Determines if the target can be drained
	 * @param entity The target Entity
	 * @return True if the target can be drained, otherwise false
	 */
	public boolean isApplicable(EntityLiving entity) {
		return rendingPredicate.test(entity);
	}
}
