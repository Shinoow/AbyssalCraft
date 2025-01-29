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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.item.Item;
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
	private final List<ItemStack> scrolls = new ArrayList<>();

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
				if(spell.getID().equals(entry.getID())){
					logger.log(Level.ERROR, "Necronomicon Spell already registered: {}", spell.getID());
					return;
				}
			spells.add(spell);
		} else logger.log(Level.ERROR, "Necronomicon book type does not exist: {}", spell.getBookType());
	}

	/**
	 * Used to fetch a list of spells
	 * @return An ArrayList containing all registered Necronomicon Spells
	 */
	public List<Spell> getSpells(){
		return spells;
	}

	public Spell getSpell(int bookType, ItemStack parchment, ItemStack[] reagents){
		return spells.stream().filter(spell -> areSpellsEqual(spell, bookType, parchment, reagents)).findFirst().orElse(null);
	}

	public Spell getSpell(String name){
		return spells.stream().filter(spell -> spell.getID().equals(name)).findFirst().orElse(null);
	}

	private boolean areSpellsEqual(Spell spell, int bookType, ItemStack parchment, ItemStack[] reagents){
		if(spell.getBookType() <= bookType)
			if(APIUtils.areItemStackArraysEqual(spell.getReagents(), reagents, spell.isNBTSensitive()))
				if(spell.getParent() == null && (!parchment.hasTagCompound() || !parchment.getTagCompound().hasKey("Spell")) ||
				spell.getParent() != null && spell.getParent().getID().equals(parchment.getTagCompound().getString("Spell")))
					if(spell.getParchment().isEmpty() || APIUtils.areStacksEqual(parchment, spell.getParchment()))
						return SpellUtils.getScrollType(parchment).getQuality() >= spell.getScrollType().getQuality();
						return false;
	}

	public ItemStack inscribeSpell(Spell spell, ItemStack parchment){

		if(spell != null){
			if(!parchment.hasTagCompound())
				parchment.setTagCompound(new NBTTagCompound());
			parchment.getTagCompound().setString("Spell", spell.getID());
			return parchment;
		}

		return ItemStack.EMPTY;
	}

	/**
	 * Add a scroll to the list of scrolls to display<br>
	 * in the Necronomicon for compatible spells
	 * @param scroll Item implementing IScroll
	 */
	public void addScroll(Item scroll) {
		addScroll(new ItemStack(scroll));
	}

	/**
	 * Add a scroll to the list of scrolls to display<br>
	 * in the Necronomicon for compatible spells
	 * @param scroll ItemStack implementing IScroll
	 */
	public void addScroll(ItemStack scroll) {
		if(scroll.getItem() instanceof IScroll)
			scrolls.add(scroll);
	}

	/**
	 * Grabs a list of all scroll items
	 */
	public List<ItemStack> getScrolls(ScrollType scrollType){
		return scrolls.stream().filter(s -> SpellUtils.getScrollType(s).getQuality() >= scrollType.getQuality())
				.collect(Collectors.toList());
	}
}
