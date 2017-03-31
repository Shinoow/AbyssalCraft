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

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Registry class for Necronomicon Spells<br>
 * <b>Currently WIP</b>
 * @author shinoow
 *
 * @since 1.9
 */
public class SpellRegistry {

	List<Spell> spells = Lists.newArrayList();

	private static final SpellRegistry instance = new SpellRegistry();

	public static SpellRegistry instance(){
		return instance;
	}

	/**
	 * Registes a Necronomicon Spell
	 * @param spell The Spell, contains all data used to inscribe and cast it
	 */
	public void registerSpell(Spell spell){
		spells.add(spell);
	}
}
