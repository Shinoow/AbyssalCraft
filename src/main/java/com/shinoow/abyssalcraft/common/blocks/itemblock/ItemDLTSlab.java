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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.init.BlockHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;

public class ItemDLTSlab extends ItemSlabAC {

	public ItemDLTSlab(Block block) {
		super(block, (BlockSlab)ACBlocks.darklands_oak_slab, (BlockSlab)BlockHandler.DLTslab2);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
}
