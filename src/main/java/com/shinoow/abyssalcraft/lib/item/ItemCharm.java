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
			if(!world.isRemote)
				if(!((IEnergyManipulator) tile).isActive() && getAmplifier(stack) != null) {
					((IEnergyManipulator) tile).setActive(getAmplifier(stack), getDeity(stack));
					stack.shrink(1);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
				} else if(((IEnergyManipulator) tile).isActive() && getAmplifier(stack) == null) {
					((IEnergyManipulator) tile).setActiveAmplifier(null);
					((IEnergyManipulator) tile).setActiveDeity(null);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
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
