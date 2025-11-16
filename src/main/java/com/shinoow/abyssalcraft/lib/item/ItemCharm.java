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
package com.shinoow.abyssalcraft.lib.item;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IAmplifierCharm;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.lib.util.SoundUtil;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Basic implementation of an Amplifier Charm
 * @author shinoow
 *
 */
public class ItemCharm extends ItemACBasic implements IAmplifierCharm {

	private DeityType deity;
	private AmplifierType amplifier;

	public ItemCharm(String name, AmplifierType amplifier) {
		this(name, amplifier, null);
	}

	public ItemCharm(String name, AmplifierType amplifier, DeityType deity) {
		super(name);
		this.amplifier = amplifier;
		this.deity = deity;
	}

	@Override
	public AmplifierType getAmplifier(ItemStack stack) {

		return amplifier;
	}

	@Override
	public DeityType getDeity(ItemStack stack) {

		return deity;
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		l.add(I18n.format("ac.text.amplifier") + ": " + EnergyEnum.getAmplifierName(getAmplifier(is)));
		l.add(I18n.format("ac.text.deity") + ": " + EnergyEnum.getDeityName(getDeity(is)));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		ItemStack stack = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IEnergyManipulator){
			IEnergyManipulator manipulator = (IEnergyManipulator) tile;
			if(!world.isRemote) {
				boolean success = false;
				if(!manipulator.isActive() && getAmplifier(stack) != null) {
					manipulator.setActive(getAmplifier(stack), getDeity(stack));
					stack.shrink(1);
					success = true;
				} else if(manipulator.isActive() && getAmplifier(stack) == null) {
					manipulator.setActiveAmplifier(null);
					manipulator.setActiveDeity(null);
					success = true;
				}
				if(success)
					SoundUtil.playSoundNearby(world, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public String getTranslationKey(ItemStack stack)
	{
		return getTranslationKey();
	}
}
