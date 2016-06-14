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

import net.minecraft.block.BlockButton;

public class BlockACButton extends BlockButton {

	public BlockACButton(boolean par1, String tooltype, int harvestlevel, String texture) {
		super(par1);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACButton(boolean par1, String texture) {
		super(par1);
	}
}