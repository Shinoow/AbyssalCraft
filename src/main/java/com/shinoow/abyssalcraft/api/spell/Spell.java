/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

/**
 * Base Necronomicon Spell.<br>
 * Extend to make your own spells.<br>
 * <b>Currently WIP</b>
 * @author shinoow
 *
 * @since 1.9
 */
public abstract class Spell {

	private Object[] reagents = new Object[5];
	private ItemStack parchment;
	private String unlocalizedName;
	private float requiredEnergy;

	/**
	 * A Necronomicon Spell
	 * @param unlocalizedName A String representing the spell name
	 * @param requiredEnergy Amount of Potential Energy required to cast
	 * @param parchment Parchment to inscribe the spell on
	 * @param reagents Components used to inscribe the spell with
	 */
	public Spell(String unlocalizedName, float requiredEnergy, ItemStack parchment, Object...reagents){
		this.unlocalizedName = unlocalizedName;
		this.requiredEnergy = requiredEnergy;
		this.parchment = parchment;
		if(reagents.length < 5){
			this.reagents = new Object[reagents.length];
			for(int i = 0; i < reagents.length; i++)
				this.reagents[i] = reagents[i];
		} else this.reagents = reagents;
	}

	/**
	 * Used to fetch the reagents used to inscribe this spell
	 * @return An array of Objects representing reagents
	 */
	public Object[] getReagents(){
		return reagents;
	}

	/**
	 * Used to fetch the parchment this spell can be inscribed on
	 * @return An ItemStack representing the parchment
	 */
	public ItemStack getParchment(){
		return parchment;
	}

	/**
	 * Used to fetch the required Potential Energy for the spell
	 * @return A Float representing the amount of Potential Energy required to cast the spell
	 */
	public float getReqEnergy(){
		return requiredEnergy;
	}

	/**
	 * Used to fetch the unlocalized name for a spell
	 * @return A string prefixed by "ac.spell."
	 */
	public String getUnlocalizedName(){
		return "ac.spell." + unlocalizedName;
	}

	/**
	 * Used to fetch the localized name for a spell
	 * @return A localized string representing a name
	 */
	public String getLocalizedName(){
		return I18n.translateToLocal(getUnlocalizedName());
	}

	/**
	 * Used to fetch the description for the spell
	 * @return A localized string representing a description
	 */
	public String getDescription(){
		return I18n.translateToLocal(getUnlocalizedName() + ".desc");
	}

	/**
	 * Override this to ensure that the spell can be cast
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 * @return True if all conditions are met, otherwise false
	 */
	public abstract boolean canCastSpell(World world, BlockPos pos, EntityPlayer player);

	/**
	 * Called when a spell is cast
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 */
	public void castSpell(World world, BlockPos pos, EntityPlayer player){
		if(!world.isRemote) castSpellServer(world, pos, player);
		if(world.isRemote) castSpellClient(world, pos, player);
	}

	/**
	 * Override this to do something client-side when the spell is cast
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 */
	protected abstract void castSpellClient(World world, BlockPos pos, EntityPlayer player);

	/**
	 * Override this to do something server-side when the spell is casted
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 */
	protected abstract void castSpellServer(World world, BlockPos pos, EntityPlayer player);
}
