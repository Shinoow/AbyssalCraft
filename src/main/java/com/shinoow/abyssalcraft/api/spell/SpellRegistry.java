/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.spell;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Registry class for Necronomicon Spells<br>
 * <b>Currently WIP</b>
 * @author shinoow
 *
 * @since 1.9
 */
public class SpellRegistry {

	private final List<Spell> spells = new ArrayList<>();

	private final Logger logger = LogManager.getLogger("SpellRegistry");

	private static final SpellRegistry instance = new SpellRegistry();

	public static SpellRegistry instance(){
		return instance;
	}

	private SpellRegistry(){}

	/**
	 * Registes a Necronomicon Spell
	 * @param spell The Spell, contains all data used to inscribe and cast it
	 */
	public void registerSpell(Spell spell){
		if(spell.getBookType() <= 4 && spell.getBookType() >= 0){
			for(Spell entry : spells)
				if(spell.getUnlocalizedName().equals(entry.getUnlocalizedName())){
					logger.log(Level.ERROR, "Necronomicon Spell already registered: %s", spell.getUnlocalizedName());
					return;
				}
			spells.add(spell);
		} else logger.log(Level.ERROR, "Necronomicon book type does not exist: %d", spell.getBookType());
	}

	/**
	 * Used to fetch a list of spells
	 * @return An ArrayList containing all registered Necronomicon Spells
	 */
	public List<Spell> getSpells(){
		return spells;
	}

	public Spell getSpell(int bookType, ItemStack parchment, ItemStack[] reagents){
		for(Spell spell : spells)
			if(areSpellsEqual(spell, bookType, parchment, reagents)) return spell;
		return null;
	}

	public Spell getSpell(String name){
		for(Spell spell : spells)
			if(spell.getUnlocalizedName().equals(name))
				return spell;
		return null;
	}

	private boolean areSpellsEqual(Spell spell, int bookType, ItemStack parchment, ItemStack[] reagents){
		if(spell.getBookType() <= bookType)
			if(APIUtils.areItemStackArraysEqual(spell.getReagents(), reagents, spell.isNBTSensitive()))
				if(spell.getParent() == null && (!parchment.hasTagCompound() || !parchment.getTagCompound().hasKey("Spell")) ||
				spell.getParent() != null && spell.getParent().getUnlocalizedName().equals(parchment.getTagCompound().getString("Spell")))
					if(spell.getParchment().isEmpty() || APIUtils.areStacksEqual(parchment, spell.getParchment()))
						return parchment.getItem() instanceof IScroll && ((IScroll) parchment.getItem()).getScrollType(parchment).getQuality() >= spell.getScrollType().getQuality();
						return false;
	}

	public ItemStack inscribeSpell(Spell spell, ItemStack parchment){

		if(spell != null){
			if(!parchment.hasTagCompound())
				parchment.setTagCompound(new NBTTagCompound());
			parchment.getTagCompound().setString("Spell", spell.getUnlocalizedName());
			return parchment;
		}

		return ItemStack.EMPTY;

	}
}
