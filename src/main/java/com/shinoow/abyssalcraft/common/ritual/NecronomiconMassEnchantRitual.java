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
package com.shinoow.abyssalcraft.common.ritual;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualPedestal;

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
		setRitualParticle(EnumRitualParticle.GLYPHS);
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
			if(altar.getItem().getItem() == Items.BOOK && !ACConfig.enchantBooks)
				return false;
			if(altar.getItem().isItemEnchantable()) {
				books = altar.getPedestals().stream().map(IRitualPedestal::getItem).filter(i -> i.getRarity() == EnumRarity.UNCOMMON).collect(Collectors.toList());

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

			books.stream().map(ItemEnchantedBook::getEnchantments).forEach(n -> {
				for (int i = 0; i < n.tagCount(); ++i)
				{
					NBTTagCompound nbttagcompound = n.getCompoundTagAt(i);
					int id = nbttagcompound.getShort("id");
					int lvl = Math.min(nbttagcompound.getShort("lvl"), ACConfig.enchantmentMaxLevel);
					enchantments.merge(id, lvl, (k, v) -> Math.min(k+v, ACConfig.enchantmentMaxLevel));
				}
			});

			if(!enchantments.isEmpty())
				if(altar.getItem().getItem() == Items.BOOK && ACConfig.enchantBooks) {
					ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
					book.setTagCompound(new NBTTagCompound());
					NBTTagList list = new NBTTagList();
					enchantments.entrySet().stream().forEach(e -> {
						NBTTagCompound nbt = new NBTTagCompound();
						nbt.setShort("id", e.getKey().shortValue());
						nbt.setShort("lvl", e.getValue().shortValue());
						list.appendTag(nbt);
					});
					book.getTagCompound().setTag("StoredEnchantments", list);
					altar.setItem(book);
				} else if(altar.getItem().getItem() != Items.BOOK)
					EnchantmentHelper.setEnchantments(enchantments.entrySet().stream().collect(Collectors.toMap(e -> Enchantment.getEnchantmentByID(e.getKey()), Entry::getValue)), altar.getItem());
		}
	}

}
