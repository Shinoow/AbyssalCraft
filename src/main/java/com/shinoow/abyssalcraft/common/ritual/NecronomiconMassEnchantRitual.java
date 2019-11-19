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
package com.shinoow.abyssalcraft.common.ritual;

import java.util.*;
import java.util.stream.Collectors;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecronomiconMassEnchantRitual extends NecronomiconRitual {

	List<ItemStack> books = new ArrayList<>();

	public NecronomiconMassEnchantRitual() {
		super("massEnchantment", 4, 50000, new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.ENCHANTED_BOOK),
				new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.ENCHANTED_BOOK),
				new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.ENCHANTED_BOOK));

	}

	@Override
	public boolean requiresItemSacrifice(){
		return true;
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IRitualAltar) {
			IRitualAltar altar = (IRitualAltar) tile;
			if(altar.getItem().isItemEnchantable()) {
				books = altar.getPedestals().stream().map(p -> p.getItem()).filter(i -> i.getRarity() == EnumRarity.UNCOMMON).collect(Collectors.toList());

				return books.size() == 8;
			}
		}

		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {

	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {

		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IRitualAltar) {
			IRitualAltar altar = (IRitualAltar) tile;

			Map<Integer, Integer> enchantments = new HashMap<>();

			for(ItemStack stack : books) {
				NBTTagList nbttaglist = ItemEnchantedBook.getEnchantments(stack);

				for (int i = 0; i < nbttaglist.tagCount(); ++i)
				{
					NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
					int id = nbttagcompound.getShort("id");
					int lvl = nbttagcompound.getShort("lvl");
					enchantments.merge(id, lvl, (k, v) -> k+v); //maybe restrict this with a max value? Could lock it at 10 or something, with a config option for the sake of destroying balance
				}
			}

			if(!enchantments.isEmpty())
				EnchantmentHelper.setEnchantments(enchantments.entrySet().stream().collect(Collectors.toMap(e -> Enchantment.getEnchantmentByID(e.getKey()), e -> e.getValue())), altar.getItem());
		}
	}

}
