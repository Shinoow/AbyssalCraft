/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemConfigurator extends ItemACBasic {

	public ItemConfigurator() {
		super("configurator");
		setCreativeTab(ACTabs.tabTools);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		//882 4 -1782
		//882 4 -1777
		return super.onItemRightClick(par2World, par3EntityPlayer, hand);
	}


	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){

		if(!w.isRemote) {
			TileEntity te = w.getTileEntity(pos);
			if(te != null && ItemTransferCapability.getCap(te) != null) {
//				player.sendMessage(new TextComponentString("test"));
				IItemTransferCapability cap = ItemTransferCapability.getCap(te);
				cap.addTransferConfiguration(new ItemTransferConfiguration(new BlockPos[] {
						new BlockPos(882, 4, -1781), new BlockPos(883, 4, -1780),
						new BlockPos(882, 4, -1777)
				}).setFilter(NonNullList.from(ItemStack.EMPTY, new ItemStack(Items.COAL), new ItemStack(Items.APPLE))).setExitFacing(side));
				player.sendMessage(new TextComponentString(""+cap.getTransferConfigurations().size()));
			}
			return EnumActionResult.PASS;
		}

		return super.onItemUse(player, w, pos, hand, side, hitX, hitY, hitZ);
	}
}
