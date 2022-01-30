/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.util.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Utility class for common handling code used along with<br>
 * {@link ISingletonInventory} implementations.
 * @author shinoow
 *
 */
public class SingletonInventoryUtil {

	/**
	 * Call this in the block's onBlockActivated method to handle
	 * placing/removing something on/from the block
	 * @param world Current World
	 * @param pos Current position
	 * @param player Interacting Player
	 * @param heldItem Current held item
	 * @return True if successful, otherwise false
	 */
	public static boolean handleBlockActivation(World world, BlockPos pos, EntityPlayer player, ItemStack heldItem){
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof ISingletonInventory)
			if(!((ISingletonInventory)tile).getItem().isEmpty()){
				if(player.inventory.addItemStackToInventory(((ISingletonInventory)tile).getItem())){
					((ISingletonInventory)tile).setItem(ItemStack.EMPTY);
					world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
					return true;
				}
			} else if(!heldItem.isEmpty()){
				ItemStack newItem = heldItem.copy();
				newItem.setCount(1);
				((ISingletonInventory)tile).setItem(newItem);
				heldItem.shrink(1);
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
				return true;
			}
		return false;
	}
}
