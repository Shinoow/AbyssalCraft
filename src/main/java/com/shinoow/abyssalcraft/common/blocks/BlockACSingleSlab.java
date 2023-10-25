/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockACSingleSlab extends BlockACSlab {

	public BlockACSingleSlab(Material par3Material, String tooltype, int harvestlevel, SoundType stepSound)
	{
		this(par3Material, tooltype, harvestlevel, stepSound, par3Material.getMaterialMapColor());
	}

	public BlockACSingleSlab(Material par3Material, String tooltype, int harvestlevel, SoundType stepSound, MapColor mapColor)
	{
		super(par3Material, tooltype, harvestlevel, mapColor);
		setSoundType(stepSound);
	}

	public BlockACSingleSlab(Material par3Material, SoundType stepSound)
	{
		this(par3Material, stepSound, par3Material.getMaterialMapColor());
	}

	public BlockACSingleSlab(Material par3Material, SoundType stepSound, MapColor mapColor)
	{
		super(par3Material, mapColor);
		setSoundType(stepSound);
	}

	@Override
	public boolean isDouble() {

		return false;
	}
}
