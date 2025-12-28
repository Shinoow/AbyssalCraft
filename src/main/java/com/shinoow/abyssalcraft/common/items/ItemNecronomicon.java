/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporterItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.INecronomiconAction;
import com.shinoow.abyssalcraft.api.necronomicon.NecronomiconActionRegistry;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.ShouldSyncMessage;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.item.ItemACBasic;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemNecronomicon extends ItemACBasic implements IEnergyTransporterItem {

	private int bookType;

	public ItemNecronomicon(String par1, int type) {
		super(par1);
		setMaxStackSize(1);
		bookType = type;
	}

	@Override
	public void getSubItems(CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List) {
		if (isInCreativeTab(par2CreativeTab)) {
			par3List.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			addEnergy(stack, getMaxEnergy(stack));
			par3List.add(stack);
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TranslationUtil.toLocalFormatted("tooltip.tiereditem.tier", bookType));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand) {
		ItemStack stack = par3EntityPlayer.getHeldItem(hand);
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		if (!par3EntityPlayer.isSneaking()) {
			if (!par2World.isRemote && ACConfig.syncDataOnBookOpening)
				PacketDispatcher.sendTo(new ShouldSyncMessage(par3EntityPlayer), (EntityPlayerMP) par3EntityPlayer);
			par3EntityPlayer.openGui(AbyssalCraft.instance, ACLib.necronmiconGuiID, par2World, 0, 0, 0);
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			INecronomiconAction action = NecronomiconActionRegistry.instance().getActionFor(player, world, pos, bookType);

			if(action != null) {
				return action.executeAction(player, world, pos, bookType);
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	public int getBookType() {
		return bookType;
	}

	/**
	 * Calculates how many seconds a ritual takes to perform (and how much PE per second)
	 * <br>based on book capacity and ritual cost
	 * @return Tuple containing PE to drain every second and ritual length (in seconds)
	 */
	public Tuple<Float, Integer> getPercentileAndSeconds(ItemStack stack, float pe) {
		float max = getMaxEnergy(stack);
		float tenth = max / 10;
		float res = tenth;
		int res1 = 1;
		if(pe == max) // ritual takes max capacity, therefore 10 seconds
			return new Tuple(max, 10);
		while(res < pe) { // increment by 10 percent
			res += tenth;
			res1++;
		}
		return new Tuple(res, res1);
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		if (this == ACItems.necronomicon)
			return 5000;
		if (this == ACItems.abyssal_wasteland_necronomicon)
			return 10000;
		if (this == ACItems.dreadlands_necronomicon)
			return 20000;
		if (this == ACItems.omothol_necronomicon)
			return 40000;
		if (this == ACItems.abyssalnomicon)
			return 100000;
		return 0;
	}
}
