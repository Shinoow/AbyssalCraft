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
package com.shinoow.abyssalcraft.api.event;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * FuelBurnTimeEvent is fired when determining the fuel value for an ItemStack. <br>
 * <br>
 * This event is fired from {@link AbyssalCraftAPI#getFuelValue(ItemStack, FuelType)}.<br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 *
 * @author shinoow
 *
 * @since 1.21.0
 */
public class FuelBurnTimeEvent extends Event
{
	@Nonnull
	private final ItemStack itemStack;
	private int burnTime = -1;
	private final FuelType fuelType;

	public FuelBurnTimeEvent(@Nonnull ItemStack itemStack, FuelType fuelType)
	{
		this.itemStack = itemStack;
		this.fuelType = fuelType;
	}

	/**
	 * Get the ItemStack "fuel" in question.
	 */
	@Nonnull
	public ItemStack getItemStack()
	{
		return itemStack;
	}

	/**
	 * Set the burn time for the given ItemStack.
	 * Setting it to 0 will prevent the item from being used as fuel.
	 */
	public void setBurnTime(int burnTime)
	{
		this.burnTime = burnTime;
	}

	/**
	 * The resulting value of this event, the burn time for the ItemStack.
	 * A value of 0 will prevent the item from being used as fuel.
	 * A value of -1 will let the machine in question decide on the fuel value.
	 */
	public int getBurnTime()
	{
		return burnTime;
	}

	/**
	 * Get the machine this fuel will be used in (Crystallizer or Transmutator).
	 */
	public FuelType getFuelType() {
		return fuelType;
	}
}
