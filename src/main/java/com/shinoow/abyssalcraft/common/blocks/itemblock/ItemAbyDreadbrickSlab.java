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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemAbyDreadbrickSlab extends ItemSlab {

	public ItemAbyDreadbrickSlab(Block block) {
		super(block, (BlockSlab)AbyssalCraft.abydreadbrickslab1, (BlockSlab)AbyssalCraft.abydreadbrickslab2, false);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
}
