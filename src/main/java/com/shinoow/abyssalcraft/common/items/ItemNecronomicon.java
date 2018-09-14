/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
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
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.api.energy.structure.StructureHandler;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.ShouldSyncMessage;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNecronomicon extends ItemACBasic implements IEnergyTransporterItem {

	private int bookType;

	public ItemNecronomicon(String par1, int type){
		super(par1);
		setMaxStackSize(1);
		bookType = type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List){
		if(isInCreativeTab(par2CreativeTab)){
			par3List.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			addEnergy(stack, getMaxEnergy(stack));
			par3List.add(stack);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		ItemStack par1ItemStack = par3EntityPlayer.getHeldItem(hand);
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.setTagCompound(new NBTTagCompound());
		if(!par1ItemStack.getTagCompound().hasKey("owner")){
			par1ItemStack.getTagCompound().setString("owner", par3EntityPlayer.getName());
			if(!par3EntityPlayer.isSneaking()){
				if(!par2World.isRemote && ACConfig.syncDataOnBookOpening)
					PacketDispatcher.sendTo(new ShouldSyncMessage(par3EntityPlayer), (EntityPlayerMP)par3EntityPlayer);
				par3EntityPlayer.openGui(AbyssalCraft.instance, ACLib.necronmiconGuiID, par2World, 0, 0, 0);
				return new ActionResult(EnumActionResult.SUCCESS, par1ItemStack);
			}
		}
		if(par1ItemStack.getTagCompound().getString("owner").equals(par3EntityPlayer.getName())){
			if(!par3EntityPlayer.isSneaking()){
				if(!par2World.isRemote && ACConfig.syncDataOnBookOpening)
					PacketDispatcher.sendTo(new ShouldSyncMessage(par3EntityPlayer), (EntityPlayerMP)par3EntityPlayer);
				par3EntityPlayer.openGui(AbyssalCraft.instance, ACLib.necronmiconGuiID, par2World, 0, 0, 0);
				return new ActionResult(EnumActionResult.SUCCESS, par1ItemStack);
			}
		}
		else if(par2World.isRemote)
			SpecialTextUtil.JzaharText(I18n.translateToLocal("message.necronomicon.nope"));
		return new ActionResult(EnumActionResult.PASS, par1ItemStack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		ItemStack is = player.getHeldItem(hand);
		if(player.isSneaking())
			if(!(w.getBlockState(pos).getBlock() instanceof BlockRitualAltar)){
				if(isOwner(player, is))
					if(RitualUtil.tryAltar(w, pos, bookType) || StructureHandler.instance().tryFormStructure(w, pos, bookType, player)){
						w.playSound(player, pos, ACSounds.remnant_scream, player.getSoundCategory(), 3F, 1F);
						//						player.addStat(ACAchievements.ritual_altar, 1);
						return EnumActionResult.SUCCESS;
					}
			} else if(w.getTileEntity(pos) instanceof IRitualAltar)
				if(isOwner(player, is)){
					IRitualAltar altar = (IRitualAltar) w.getTileEntity(pos);
					altar.performRitual(w, pos, player);
					return EnumActionResult.SUCCESS;
				}
		return EnumActionResult.PASS;
	}

	public boolean isOwner(EntityPlayer player, ItemStack stack){
		if(!stack.hasTagCompound()) return false;
		return stack.getTagCompound().getString("owner").equals(player.getName());
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		if(is.hasTagCompound() && is.getTagCompound().hasKey("owner"))
			l.add("Owner: " + is.getTagCompound().getString("owner"));
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
		return PEUtils.getContainedEnergy(stack);
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
		PEUtils.addEnergy(this, stack, energy);
	}

	@Override
	public float consumeEnergy(ItemStack stack, float energy) {
		return PEUtils.consumeEnergy(stack, energy);
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
