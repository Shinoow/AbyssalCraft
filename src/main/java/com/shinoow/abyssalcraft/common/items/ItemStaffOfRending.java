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

import java.util.List;
import java.util.stream.Collectors;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.rending.RendingRegistry;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.StaffOfRendingMessage;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.item.ItemMetadata;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemStaffOfRending extends ItemMetadata implements IStaffOfRending {

	public ItemStaffOfRending(){
		super("drainstaff", "normal", "aw", "dl", "omt");
		setCreativeTab(ACTabs.tabTools);
		setMaxStackSize(1);
	}

	@Override
	public boolean isFull3D(){
		return true;
	}

	@Override
	public void increaseEnergy(ItemStack stack, String type){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("energy"+type, getEnergy(stack, type) + getDrainAmount(stack));
	}

	@Override
	public void setEnergy(int amount, ItemStack stack, String type){
		stack.getTagCompound().setInteger("energy"+type, amount);
	}

	@Override
	public int getEnergy(ItemStack par1ItemStack, String type)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("energy"+type) ? (int)par1ItemStack.getTagCompound().getInteger("energy"+type) : 0;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote){

			RayTraceResult mov = AbyssalCraftClientEventHooks.getMouseOverExtended(50);

			if (mov != null)
				if (mov.entityHit != null && !mov.entityHit.isDead)
					if (mov.entityHit != player )
						PacketDispatcher.sendToServer(new StaffOfRendingMessage(mov.entityHit.getEntityId(), hand));
		}

		RendingRegistry.instance().getRendings().stream()
		.filter(r -> getEnergy(stack, r.getName()) >= r.getMaxEnergy())
		.forEach(r -> {
			setEnergy(0, stack, r.getName());
			if(!player.inventory.addItemStackToInventory(r.getOutput()))
				player.dropItem(r.getOutput(), true);
		});

		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@Override
	public int getDrainAmount(ItemStack stack){
		return stack.getItemDamage() + 1 + MathHelper.clamp(EnchantmentHelper.getEnchantmentLevel(AbyssalCraftAPI.sapping, stack), 0, 3);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		l.addAll(RendingRegistry.instance().getRendings().stream()
				.map(r -> String.format("%s: %d/%d", I18n.format(r.getTooltip()), getEnergy(is, r.getName()), r.getMaxEnergy()))
				.collect(Collectors.toList()));
	}

	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return getItemStackLimit(stack) == 1;
	}

	@Override
	public int getItemEnchantability(ItemStack stack)
	{
		return 5 * (stack.getItemDamage() + 1);
	}
}
