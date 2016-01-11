/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.common.structures.IOmotholBuilding;

public class OmotholGenerator {

	List<IOmotholBuilding> buildings = Lists.newArrayList();

	private static final OmotholGenerator instance = new OmotholGenerator();

	public static OmotholGenerator instance(){
		return instance;
	}

	public void registerBuilding(IOmotholBuilding building){
		buildings.add(building);
	}

	public void generate(World world, Random rand, int x, int y, int z){

	}
}
