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
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockACSingleSlab extends BlockACSlab {

	public BlockACSingleSlab(Material par3Material, String tooltype, int harvestlevel, SoundType stepSound)
	{
		super(par3Material, tooltype, harvestlevel);
		setStepSound(stepSound);
	}

	public BlockACSingleSlab(Material par3Material, SoundType stepSound)
	{
		super(par3Material);
		setStepSound(stepSound);
	}

	@Override
	public boolean isDouble() {

		return false;
	}
}