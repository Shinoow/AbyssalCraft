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

import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.entity.EntitySinglePortal;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.item.ItemACBasic;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemStaff extends ItemACBasic {

	public ItemStaff() {
		super("gatekeeperstaff");
		setCreativeTab(ACTabs.tabTools);
		setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag is){
		list.add(I18n.format("tooltip.staff.1"));
		list.add(I18n.format("tooltip.staff.2"));
		list.add(I18n.format("tooltip.staff.3"));
		list.add(I18n.format("tooltip.staff.4"));
		list.add(I18n.format("tooltip.staff.5"));
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		int dim = stack.getTagCompound().getInteger("Dimension");
		list.add(I18n.format("tooltip.portalplacer.3", DimensionDataRegistry.instance().getDimensionName(dim)));
		if(world != null) {
			int currDim = world.provider.getDimension();
			if(currDim == dim || !RitualRegistry.instance().canPerformAction(currDim, 4))
				list.add(TextFormatting.DARK_RED+""+TextFormatting.ITALIC+I18n.format("tooltip.portalplacer.4"));
		}
		// More abilities in the future or something?
		//		l.add(I18n.format("tooltip.staff.mode.1")+": "+TextFormatting.GOLD+I18n.format(is.getTagCompound().getInteger("Mode") == 1 ? "item.drainstaff.normal.name" : "item.gatewaykey.name"));
		//		l.add(I18n.format("tooltip.staff.mode.2", TextFormatting.GOLD+ClientProxy.staff_mode.getDisplayName()+TextFormatting.GRAY));

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(!worldIn.isRemote)
		{
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());

			if(playerIn.isSneaking()) {
				int dim = stack.getTagCompound().getInteger("Dimension");
				int newDim = 0;
				List<DimensionData> dims = DimensionDataRegistry.instance().getDimensions();
				for(int i = 0; i < dims.size(); i++) {
					DimensionData data = dims.get(i);
					if(data.getId() == dim) {
						if(i == dims.size() -1)
							newDim = dims.get(0).getId();
						else
							newDim = dims.get(i+1).getId();
						break;
					}
				}

				stack.getTagCompound().setInteger("Dimension", newDim);
				playerIn.sendStatusMessage(new TextComponentTranslation(DimensionDataRegistry.instance().getDimensionName(newDim)), true);
			}
		}

		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	@Override
	public boolean isFull3D(){
		return true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){

		ItemStack stack = player.getHeldItem(hand);

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());


		if(!world.isRemote) {

			int dim = stack.getTagCompound().getInteger("Dimension");

			if(dim != world.provider.getDimension()) {

				EntitySinglePortal portal = new EntitySinglePortal(world);
				portal.setPosition(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
				portal.setDestination(dim);
				world.spawnEntity(portal);
			} else
				player.sendStatusMessage(new TextComponentTranslation("message.portalplacer.error.2"), true);

		}

		return EnumActionResult.PASS;
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}
}
