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
package com.shinoow.abyssalcraft.lib.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IAmplifierCharm;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;

/**
 * Basic implementation of an Amplifier Charm (based on metadata)
 * @author shinoow
 *
 */
public class ItemCharm extends ItemMetadata implements IAmplifierCharm {

	private DeityType deity;

	@Deprecated
	public ItemCharm(String name, boolean moreIcons, DeityType deity){
		this(name, deity);
	}

	public ItemCharm(String name, DeityType deity) {
		super(name, "empty", "range", "duration", "power");
		this.deity = deity;
	}

	@Override
	public AmplifierType getAmplifier(ItemStack stack) {

		switch(stack.getItemDamage()){
		case 0:
			return null;
		case 1:
			return AmplifierType.RANGE;
		case 2:
			return AmplifierType.DURATION;
		case 3:
			return AmplifierType.POWER;
		default:
			return null;
		}
	}

	@Override
	public DeityType getDeity(ItemStack stack) {

		return deity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(I18n.translateToLocal("ac.text.amplifier") + ": " + EnergyEnum.getAmplifierName(getAmplifier(is)));
		l.add(I18n.translateToLocal("ac.text.deity") + ": " + EnergyEnum.getDeityName(getDeity(is)));
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IEnergyManipulator && !((IEnergyManipulator) tile).isActive()){
			((IEnergyManipulator) tile).setActive(getAmplifier(stack), getDeity(stack));
			if(!world.isRemote)
				stack.stackSize--;
			world.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return I18n.translateToLocal(getUnlocalizedName() + ".name");
	}
}
