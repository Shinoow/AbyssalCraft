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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.util.IRitualAltar;
import com.shinoow.abyssalcraft.common.util.RitualUtil;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

public class ItemNecronomicon extends ItemACBasic implements IEnergyTransporter {

	private int bookType;

	public ItemNecronomicon(String par1, int type){
		super(par1);
		setMaxStackSize(1);
		bookType = type;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.setTagCompound(new NBTTagCompound());
		if(!par1ItemStack.stackTagCompound.hasKey("owner")){
			par1ItemStack.stackTagCompound.setString("owner", par3EntityPlayer.getCommandSenderName());
			if(!par3EntityPlayer.isSneaking())
				par3EntityPlayer.openGui(AbyssalCraft.instance, AbyssalCraft.necronmiconGuiID, par2World, 0, 0, 0);
		}
		if(par1ItemStack.stackTagCompound.getString("owner").equals(par3EntityPlayer.getCommandSenderName())){
			if(!par3EntityPlayer.isSneaking())
				par3EntityPlayer.openGui(AbyssalCraft.instance, AbyssalCraft.necronmiconGuiID, par2World, 0, 0, 0);
		}
		else if(par2World.isRemote)
			SpecialTextUtil.JzaharText(StatCollector.translateToLocal("message.necronomicon.nope"));
		return par1ItemStack;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int meta, float hitX, float hitY, float hitZ){
		if(player.isSneaking())
			if(!(w.getBlock(x, y, z) instanceof BlockRitualAltar)){
				if(isOwner(player, is))
					if(RitualUtil.tryAltar(w, x, y, z, bookType)){
						w.playSoundAtEntity(player, "abyssalcraft:remnant.scream", 3F, 1F);
						player.addStat(AbyssalCraft.ritual, 1);
						return true;
					}
			} else if(w.getTileEntity(x, y, z) instanceof IRitualAltar)
				if(isOwner(player, is)){
					IRitualAltar altar = (IRitualAltar) w.getTileEntity(x, y, z);
					altar.performRitual(w, x, y, z, player);
					return true;
				}
		return false;
	}

	private boolean isOwner(EntityPlayer player, ItemStack stack){
		if(!stack.hasTagCompound()) return false;
		return stack.stackTagCompound.getString("owner").equals(player.getCommandSenderName());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("owner"))
			l.add("Owner: " + is.stackTagCompound.getString("owner"));
		l.add(String.format("%d/%d PE", (int)getContainedEnergy(is), getMaxEnergy(is)));
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	public int getBookType(){
		return bookType;
	}

	@Override
	public float getContainedEnergy(ItemStack stack) {
		float energy;
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(stack.stackTagCompound.hasKey("PotEnergy"))
			energy = stack.stackTagCompound.getFloat("PotEnergy");
		else {
			energy = 0;
			stack.stackTagCompound.setFloat("PotEnergy", energy);
		}
		return energy;
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		if(this == AbyssalCraft.necronomicon)
			return 5000;
		if(this == AbyssalCraft.necronomicon_cor)
			return 10000;
		if(this == AbyssalCraft.necronomicon_dre)
			return 20000;
		if(this == AbyssalCraft.necronomicon_omt)
			return 40000;
		if(this == AbyssalCraft.abyssalnomicon)
			return 100000;
		return 0;
	}

	@Override
	public void addEnergy(ItemStack stack, float energy) {
		float contained = getContainedEnergy(stack);
		if(contained + energy >= getMaxEnergy(stack))
			stack.stackTagCompound.setFloat("PotEnergy", getMaxEnergy(stack));
		else stack.stackTagCompound.setFloat("PotEnergy", contained += energy);
	}

	@Override
	public void consumeEnergy(ItemStack stack, float energy) {
		float contained = getContainedEnergy(stack);
		if(contained - energy < 0)
			stack.stackTagCompound.setFloat("PotEnergy", 0);
		else stack.stackTagCompound.setFloat("PotEnergy", contained -= energy);
	}
}
