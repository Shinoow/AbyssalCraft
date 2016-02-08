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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockACDoubleSlab extends BlockACSlab {

	public BlockACDoubleSlab(Block par1SingleSlab, Material par3Material, String tooltype, int harvestlevel)
	{
		super(par1SingleSlab, par3Material, tooltype, harvestlevel);
	}

	public BlockACDoubleSlab(Block par1SingleSlab, Material par3Material)
	{
		super(par1SingleSlab, par3Material);
	}

	@Override
	public boolean isDouble() {

		return true;
	}
}