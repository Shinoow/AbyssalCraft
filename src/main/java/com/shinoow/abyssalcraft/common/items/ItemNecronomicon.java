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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporterItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

public class ItemNecronomicon extends ItemACBasic implements IEnergyTransporterItem {

	private int bookType;

	public ItemNecronomicon(String par1, int type){
		super(par1);
		setMaxStackSize(1);
		bookType = type;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List){
		par3List.add(new ItemStack(par1Item));
		ItemStack stack = new ItemStack(par1Item);
		addEnergy(stack, getMaxEnergy(stack));
		par3List.add(stack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.setTagCompound(new NBTTagCompound());
		if(!par1ItemStack.getTagCompound().hasKey("owner")){
			par1ItemStack.getTagCompound().setString("owner", par3EntityPlayer.getName());
			if(!par3EntityPlayer.isSneaking())
				par3EntityPlayer.openGui(AbyssalCraft.instance, ACLib.necronmiconGuiID, par2World, 0, 0, 0);
		}
		if(par1ItemStack.getTagCompound().getString("owner").equals(par3EntityPlayer.getName())){
			if(!par3EntityPlayer.isSneaking())
				par3EntityPlayer.openGui(AbyssalCraft.instance, ACLib.necronmiconGuiID, par2World, 0, 0, 0);
		}
		else if(par2World.isRemote)
			SpecialTextUtil.JzaharText(StatCollector.translateToLocal("message.necronomicon.nope"));
		return par1ItemStack;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
		if(player.isSneaking())
			if(!(w.getBlockState(pos).getBlock() instanceof BlockRitualAltar)){
				if(isOwner(player, is))
					if(RitualUtil.tryAltar(w, pos, bookType)){
						w.playSoundAtEntity(player, "abyssalcraft:remnant.scream", 3F, 1F);
						player.addStat(AbyssalCraft.ritual, 1);
						return true;
					}
			} else if(w.getTileEntity(pos) instanceof IRitualAltar)
				if(isOwner(player, is)){
					IRitualAltar altar = (IRitualAltar) w.getTileEntity(pos);
					altar.performRitual(w, pos, player);
					return true;
				}
		return false;
	}

	private boolean isOwner(EntityPlayer player, ItemStack stack){
		if(!stack.hasTagCompound()) return false;
		return stack.getTagCompound().getString("owner").equals(player.getName());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		if(is.hasTagCompound() && is.getTagCompound().hasKey("owner"))
			l.add("Owner: " + is.getTagCompound().getString("owner"));
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
		if(stack.getTagCompound().hasKey("PotEnergy"))
			energy = stack.getTagCompound().getFloat("PotEnergy");
		else {
			energy = 0;
			stack.getTagCompound().setFloat("PotEnergy", energy);
		}
		return energy;
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		if(this == ACItems.necronomicon)
			return 5000;
		if(this == ACItems.abyssal_wasteland_necronomicon)
			return 10000;
		if(this == ACItems.dreadlands_necronomicon)
			return 20000;
		if(this == ACItems.omothol_necronomicon)
			return 40000;
		if(this == ACItems.abyssalnomicon)
			return 100000;
		return 0;
	}

	@Override
	public void addEnergy(ItemStack stack, float energy) {
		float contained = getContainedEnergy(stack);
		if(contained + energy >= getMaxEnergy(stack))
			stack.getTagCompound().setFloat("PotEnergy", getMaxEnergy(stack));
		else stack.getTagCompound().setFloat("PotEnergy", contained += energy);
	}

	@Override
	public float consumeEnergy(ItemStack stack, float energy) {
		float contained = getContainedEnergy(stack);
		if(energy < contained){
			stack.getTagCompound().setFloat("PotEnergy", contained -= energy);
			return energy;
		} else {
			stack.getTagCompound().setFloat("PotEnergy", 0);
			return contained;
		}
	}

	@Override
	public boolean canAcceptPE(ItemStack stack) {
		return getContainedEnergy(stack) < getMaxEnergy(stack);
	}

	@Override
	public boolean canTransferPE(ItemStack stack) {
		return getContainedEnergy(stack) > 0;
	}

	@Override
	public boolean canAcceptPEExternally(ItemStack stack) {
		return getContainedEnergy(stack) < getMaxEnergy(stack);
	}

	@Override
	public boolean canTransferPEExternally(ItemStack stack) {
		return getContainedEnergy(stack) > 0;
	}
}