/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class SalvageHandler {

	public static SalvageHandler INSTANCE = new SalvageHandler();

	private enum SalvageType{
		BOOTS, HELMET, CHESTPLATE, LEGGINGS, CUSTOM
	}

	private SalvageHandler(){
	}

	private void addSalvage(Item salvageInput, Item salvageOutput, int output, float xp, SalvageType salvageType){
		switch(salvageType){
		case BOOTS:
			GameRegistry.addSmelting(salvageInput, new ItemStack(salvageOutput, 2), 1F);
			break;
		case HELMET:
			GameRegistry.addSmelting(salvageInput, new ItemStack(salvageOutput, 2), 1F);
			break;
		case CHESTPLATE:
			GameRegistry.addSmelting(salvageInput, new ItemStack(salvageOutput, 4), 1F);
			break;
		case LEGGINGS:
			GameRegistry.addSmelting(salvageInput, new ItemStack(salvageOutput, 3), 1F);
			break;
		case CUSTOM:
			GameRegistry.addSmelting(salvageInput, new ItemStack(salvageOutput, output), xp);
			break;
		default:
			break;
		}
	}

	/**
	 * Salvage recipe for boots
	 * @param salvageInput the boots
	 * @param salvageOutput the base material of the boots (eg. leather, iron, gold etc)
	 */
	public void addBootsSalvage(Item salvageInput, Item salvageOutput){
		addSalvage(salvageInput, salvageOutput, 0, 0, SalvageType.BOOTS);
	}

	/**
	 * Salvage recipe for helmets
	 * @param salvageInput the helmet
	 * @param salvageOutput the base material of the helmet (eg. leather, iron, gold etc)
	 */
	public void addHelmetSalvage(Item salvageInput, Item salvageOutput){
		addSalvage(salvageInput, salvageOutput, 0, 0, SalvageType.HELMET);
	}

	/**
	 * Salvage recipe for chestplates
	 * @param salvageInput the chestplate
	 * @param salvageOutput the base material of the chestplate (eg. leather, iron, gold etc)
	 */
	public void addChestplateSalvage(Item salvageInput, Item salvageOutput){
		addSalvage(salvageInput, salvageOutput, 0, 0, SalvageType.CHESTPLATE);
	}

	/**
	 * Salvage recipe for leggings
	 * @param salvageInput the leggings
	 * @param salvageOutput the base material of the leggings (eg. leather, iron, gold etc)
	 */
	public void addLeggingsSalvage(Item salvageInput, Item salvageOutput){
		addSalvage(salvageInput, salvageOutput, 0, 0, SalvageType.LEGGINGS);
	}

	/**
	 * Custom salvage recipe
	 * @param salvageInput the item to salvage
	 * @param salvageOutput the salvage output
	 * @param output the amount of the output item
	 * @param exp the amount of exp gained from salvaging
	 */
	public void addCustomSalvage(Item salvageInput,int output, float xp, Item salvageOutput){
		addSalvage(salvageInput, salvageOutput, output, xp, SalvageType.CUSTOM);
	}
}
