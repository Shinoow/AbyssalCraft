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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.common.handlers.ItemTransferEventHandler;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemConfigurator extends ItemACBasic {

	public ItemConfigurator() {
		super("configurator");
		setCreativeTab(ACTabs.tabTools);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if(!world.isRemote) {
			ItemStack stack = player.getHeldItem(hand);
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			if(!stack.getTagCompound().hasKey("Mode"))
				stack.getTagCompound().setInteger("Mode", 0);
			int mode = stack.getTagCompound().getInteger("Mode");
			if(player.isSneaking()) {
				stack.getTagCompound().setInteger("Mode", mode == 0 ? 1 : 0);
				player.sendMessage(new TextComponentString(String.format("Mode: %s", mode == 0 ? "Apply configuration" : "Set path")));
			} else if(mode == 1)
				player.openGui(AbyssalCraft.instance, ACLib.configuratorGuiID, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}


		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){

		if(!w.isRemote) {

			ItemStack stack = player.getHeldItem(hand);
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbt = stack.getTagCompound();
			int mode = nbt.getInteger("Mode");
			//modes: 0 = path creation, 1 = everything else?

			if(mode == 0) {
				NBTTagList path = null;
				if(nbt.hasKey("Path"))
					path = nbt.getTagList("Path", NBT.TAG_LONG);
				else
					path = new NBTTagList();

				TileEntity te = w.getTileEntity(pos);

				if(te != null && ItemTransferEventHandler.hasInventory(te)) {
					path.appendTag(new NBTTagLong(pos.toLong()));
					nbt.setInteger("EntryFacing", side.getIndex());
					player.sendMessage(new TextComponentString("Block with Inventory discovered! Entry side set to the side you clicked at."));
				} else {
					path.appendTag(new NBTTagLong(pos.up().toLong()));
					player.sendMessage(new TextComponentString("Position added to path"));
				}
				nbt.setTag("Path", path);
			} else if(mode == 1) {
				TileEntity te = w.getTileEntity(pos);
				if(te != null && ItemTransferCapability.getCap(te) != null) {
					IItemTransferCapability cap = ItemTransferCapability.getCap(te);
					//TODO grab the filter from somewhere...
					NonNullList<ItemStack> filter = NonNullList.withSize(5, ItemStack.EMPTY);
					ItemStackHelper.loadAllItems(nbt, filter);
					NBTTagList path = nbt.getTagList("Path", NBT.TAG_LONG);
					List<BlockPos> positions = new ArrayList<>();
					for(Iterator<NBTBase> i = path.iterator(); i.hasNext();)
						positions.add(BlockPos.fromLong(((NBTTagLong)i.next()).getLong()));
					ItemTransferConfiguration cfg = new ItemTransferConfiguration(positions.toArray(new BlockPos[0]));
					cfg.setExitFacing(side);
					if(nbt.hasKey("EntryFacing"))
						cfg.setEntryFacing(EnumFacing.getFront(nbt.getInteger("EntryFacing")));
					cfg.setFilter(filter);

					cap.addTransferConfiguration(cfg);
					cap.setRunning(true);
					player.sendMessage(new TextComponentString("Configuration set for block!"));
				}
			}

			return EnumActionResult.PASS;
		}

		return super.onItemUse(player, w, pos, hand, side, hitX, hitY, hitZ);
	}
}
