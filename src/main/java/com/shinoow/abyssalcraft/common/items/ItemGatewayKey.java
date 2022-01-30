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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.blocks.BlockPortalAnchor;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPortalAnchor;
import com.shinoow.abyssalcraft.common.entity.EntityPortal;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGatewayKey extends ItemACBasic {

	private final int key;
	private boolean isUsing;

	public ItemGatewayKey(int key, String unlocalizedName){
		super(unlocalizedName);
		this.key = key;
		maxStackSize = 1;
		setCreativeTab(ACTabs.tabTools);
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		switch(key){
		case 1:
			return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
		case 2:
			return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
		default:
			return super.getItemStackDisplayName(par1ItemStack);
		}
	}

	public int getKeyType() {
		return key;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World world, List list, ITooltipFlag is){
		list.add(I18n.format("tooltip.portalplacer.1"));
		list.add(I18n.format("tooltip.portalplacer.2"));
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.setTagCompound(new NBTTagCompound());
		int dim = par1ItemStack.getTagCompound().getInteger("Dimension");
		list.add(I18n.format("tooltip.portalplacer.3", DimensionDataRegistry.instance().getDimensionName(dim)));
		DimensionDataRegistry.instance().getDataForDim(dim);
		if(world != null) {
			int currDim = world.provider.getDimension();
			if(!areDimensionsCompatible(currDim, dim) || !RitualRegistry.instance().canPerformAction(currDim, 4))
				list.add(TextFormatting.DARK_RED+""+TextFormatting.ITALIC+I18n.format("tooltip.portalplacer.4"));
		}
	}

	private boolean areDimensionsCompatible(int currentDim, int destinationDim) {
		return currentDim != destinationDim &&
				DimensionDataRegistry.instance().areDimensionsConnected(currentDim, destinationDim, key);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(!worldIn.isRemote)
			if(!isUsing) {
				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());

				int dim = stack.getTagCompound().getInteger("Dimension");
				int newDim = 0;
				List<DimensionData> dims = DimensionDataRegistry.instance().getDimensions().stream().filter(d -> d.getGatewayKey() <= key).collect(Collectors.toList());
				if(playerIn.isSneaking())
					for(int i = dims.size()-1; i > 0; i--) {
						DimensionData data = dims.get(i);
						if(data.getId() == dim) {
							if(i == 0)
								newDim = dims.get(dims.size() -1).getId();
							else
								newDim = dims.get(i-1).getId();
							break;
						}
					}
				else
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
			} else isUsing = false;

		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(getKeyType() == 3) {
			IBlockState state = worldIn.getBlockState(pos);
			if(state.getBlock() == ACBlocks.portal_anchor) {
				isUsing = true;
				boolean active = state.getValue(BlockPortalAnchor.ACTIVE);

				if(active) {
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(BlockPortalAnchor.ACTIVE), 2);
					worldIn.getEntitiesWithinAABB(EntityPortal.class, new AxisAlignedBB(pos).grow(2))
					.stream().forEach(e -> worldIn.removeEntity(e));
				} else {
					ItemStack stack = player.getHeldItem(hand);
					if(!stack.hasTagCompound())
						stack.setTagCompound(new NBTTagCompound());

					int dimension = stack.getTagCompound().getInteger("Dimension");

					if(areDimensionsCompatible(worldIn.provider.getDimension(), dimension)) {
						worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(BlockPortalAnchor.ACTIVE), 2);
						TileEntity tile = worldIn.getTileEntity(pos);
						((TileEntityPortalAnchor) tile).setDestination(dimension);
						if(!worldIn.isRemote) {
							EntityPortal portal = new EntityPortal(worldIn);
							portal.setDestination(dimension);
							portal.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, 0, 0);
							worldIn.spawnEntity(portal);
						}
					} else if(!worldIn.isRemote)
						player.sendStatusMessage(new TextComponentTranslation("message.portalplacer.error.2"), true);
				}

				return EnumActionResult.PASS;
			}
		}

		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
