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
package com.shinoow.abyssalcraft.common.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.client.ClientProxy;
import com.shinoow.abyssalcraft.common.handlers.ItemTransferEventHandler;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.ParticleUtil;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemConfigurator extends ItemACBasic {

	public ItemConfigurator() {
		super("configurator");
		setCreativeTab(ACTabs.tabTools);
		addPropertyOverride(new ResourceLocation("mode"), (stack, worldIn, entityIn) -> stack.hasTagCompound() ? intToFloat(stack.getTagCompound().getInteger("Mode")) : 0 );
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(isSelected && worldIn.isRemote) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			int mode = stack.getTagCompound().getInteger("Mode");
			if(mode == 0) {
				NBTTagList path = stack.getTagCompound().getTagList("Path", NBT.TAG_LONG);
				List<BlockPos> positions = new ArrayList<>();
				for(Iterator<NBTBase> i = path.iterator(); i.hasNext();)
					positions.add(BlockPos.fromLong(((NBTTagLong)i.next()).getLong()));

				BlockPos prevPos = null;
				for(int i = 0; i < positions.size(); i++)
				{
					BlockPos pos = positions.get(i);
					boolean last = i == positions.size() - 1;
					if(last)
						worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5, 0, 0, 0);
					if(prevPos == null)
						prevPos = pos;
					else
						ParticleUtil.spawnParticleLine(prevPos, pos, 4, (v1, v2) -> {
							worldIn.spawnParticle(EnumParticleTypes.REDSTONE, v2.x, v2.y, v2.z, 0, 0, 0);
						});

					prevPos = pos;
				}
			}
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){

		if(!w.isRemote) {

			ItemStack stack = player.getHeldItem(hand);
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbt = stack.getTagCompound();
			int mode = nbt.getInteger("Mode");

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
					player.sendMessage(new TextComponentTranslation("message.configurator.1"));
				} else {
					path.appendTag(new NBTTagLong(pos.up().toLong()));
					player.sendMessage(new TextComponentTranslation("message.configurator.2"));
				}
				nbt.setTag("Path", path);
			} else if(mode == 1) {
				if(player.canPlayerEdit(pos.offset(side), side, stack)) {
					TileEntity te = w.getTileEntity(pos);
					if(te != null && ItemTransferCapability.getCap(te) != null) {
						IItemTransferCapability cap = ItemTransferCapability.getCap(te);
						NonNullList<ItemStack> filter = NonNullList.withSize(5, ItemStack.EMPTY);
						ItemStackHelper.loadAllItems(nbt, filter);
						NBTTagList path = nbt.getTagList("Path", NBT.TAG_LONG);
						List<BlockPos> positions = new ArrayList<>();
						for(Iterator<NBTBase> i = path.iterator(); i.hasNext();)
							positions.add(BlockPos.fromLong(((NBTTagLong)i.next()).getLong()));
						if(positions.isEmpty()) {
							player.sendMessage(new TextComponentTranslation("message.configurator.error.1"));
							return EnumActionResult.FAIL;
						}
						EnumFacing facing = EnumFacing.byIndex(nbt.getInteger("EntryFacing"));
						TileEntity res = w.getTileEntity(positions.get(positions.size()-1));
						if(res == null || ItemTransferEventHandler.getInventory(res, facing) == null) {
							player.sendMessage(new TextComponentTranslation("message.configurator.error.2"));
							return EnumActionResult.FAIL;
						}
						ItemTransferConfiguration cfg = new ItemTransferConfiguration(positions.toArray(new BlockPos[0]))
								.setExitFacing(side)
								.setEntryFacing(facing)
								.setFilter(filter)
								.setFilterSubtypes(nbt.getBoolean("FilterSubtype"))
								.setFilterNBT(nbt.getBoolean("FilterNBT"));
						cfg.setupSubtypeFilter();

						cap.addTransferConfiguration(cfg);
						cap.setRunning(true);
						player.sendMessage(new TextComponentTranslation("message.configurator.3"));
					} else player.sendMessage(new TextComponentTranslation("message.configurator.error.3"));
				} else player.sendMessage(new TextComponentTranslation("message.configurator.error.4"));
			} else if(mode == 2)
				if(player.canPlayerEdit(pos.offset(side), side, stack)) {
					TileEntity te = w.getTileEntity(pos);
					if(te != null && ItemTransferCapability.getCap(te) != null) {
						IItemTransferCapability cap = ItemTransferCapability.getCap(te);
						cap.clearConfigurations();
						player.sendMessage(new TextComponentTranslation("message.configurator.4"));
					} else player.sendMessage(new TextComponentTranslation("message.configurator.error.3"));
				} else player.sendMessage(new TextComponentTranslation("message.configurator.error.4"));

			return EnumActionResult.PASS;
		}

		return super.onItemUse(player, w, pos, hand, side, hitX, hitY, hitZ);
	}

	private float intToFloat(int i) {
		switch(i) {
		case 0:
			return 0;
		case 1:
			return 0.5f;
		case 2:
			return 1;
		default:
			return 0;
		}
	}

	@SideOnly(Side.CLIENT)
	public static String getMode(int mode) {
		if(mode >= 0 && mode <= 2)
			return I18n.format("tooltip.configurator.mode."+mode);
		else return getMode(0);
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		int mode = is.getTagCompound().getInteger("Mode");
		l.add(String.format("%s: %s", I18n.format("tooltip.staff.mode.1"), TextFormatting.GOLD+getMode(mode)+TextFormatting.GRAY));
		l.add(I18n.format("tooltip.staff.mode.2", TextFormatting.GOLD+ClientProxy.configurator_mode.getDisplayName()+TextFormatting.GRAY));
		l.add(I18n.format("tooltip.configurator.1", TextFormatting.GOLD+ClientProxy.configurator_filter.getDisplayName()+TextFormatting.GRAY));
		l.add(I18n.format("tooltip.configurator.2", TextFormatting.GOLD+ClientProxy.configurator_path.getDisplayName()+TextFormatting.GRAY));
	}
}
