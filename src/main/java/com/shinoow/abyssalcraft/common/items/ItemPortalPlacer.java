/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.common.blocks.BlockACStone;
import com.shinoow.abyssalcraft.common.blocks.BlockACStone.EnumStoneType;
import com.shinoow.abyssalcraft.common.entity.EntityPortal;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ItemPortalPlacer extends ItemACBasic {

	private final int key;

	public ItemPortalPlacer(int key, String unlocalizedName){
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

	@Override
	public void addInformation(ItemStack par1ItemStack, World entityplayer, List list, ITooltipFlag is){
		list.add(I18n.format("tooltip.portalplacer.1"));
		list.add(I18n.format("tooltip.portalplacer.2"));
		if(key > 0)
			list.add(I18n.format("tooltip.portalplacer.3"));
		if(Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.provider != null)
			if(!isCorrectDim(Minecraft.getMinecraft().world.provider.getDimension()))
				list.add(TextFormatting.DARK_RED+""+TextFormatting.ITALIC+I18n.format("tooltip.portalplacer.4"));
	}

	private boolean isCorrectDim(int dim){
		switch(key){
		case 0:
			if(dim == 0 || dim == ACLib.abyssal_wasteland_id)
				return true;
			else if(DimensionDataRegistry.instance().getGatewayKeyOverride(dim) == 0)
				return true;
			else return false;
		case 1:
			if(dim == 0 || dim == ACLib.abyssal_wasteland_id ||
			dim == ACLib.dreadlands_id)
				return true;
			else if(DimensionDataRegistry.instance().getGatewayKeyOverride(dim) >= 0 && DimensionDataRegistry.instance().getGatewayKeyOverride(dim) < 2)
				return true;
			else return false;
		case 2:
			if(dim == 0 || dim == ACLib.abyssal_wasteland_id ||
			dim == ACLib.dreadlands_id ||
			dim == ACLib.omothol_id ||
			dim == ACLib.dark_realm_id)
				return true;
			else if(DimensionDataRegistry.instance().getGatewayKeyOverride(dim) >= 0)
				return true;
			else return false;
		default:
			return false;
		}
	}

	private boolean dimWarning(int dim){
		switch(key){
		case 0:
			if(dim == ACLib.dreadlands_id ||
			dim == ACLib.omothol_id ||
			dim == ACLib.dark_realm_id)
				return true;
			else if(DimensionDataRegistry.instance().getGatewayKeyOverride(dim) > 0)
				return true;
			else return false;
		case 1:
			if(dim == ACLib.omothol_id ||
			dim == ACLib.dark_realm_id)
				return true;
			else if(DimensionDataRegistry.instance().getGatewayKeyOverride(dim) > 1)
				return true;
			else return false;
		default:
			return false;
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!world.isRemote){
			if(isCorrectDim(player.dimension))
			{
				int direction = MathHelper.floor(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

				int o = DimensionDataRegistry.instance().getGatewayKeyOverride(player.dimension);

				switch(key){
				case 0:
					return buildPortal(world, pos, direction, ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.ABYSSAL_STONE), ACBlocks.coralium_fire.getDefaultState());
				case 1:
					if(player.dimension == ACLib.abyssal_wasteland_id && player.isSneaking() || player.dimension == 0 || o == 0)
						return buildPortal(world, pos, direction, ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.ABYSSAL_STONE), ACBlocks.coralium_fire.getDefaultState());
					else return buildPortal(world, pos, direction, ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.DREADSTONE), ACBlocks.dreaded_fire.getDefaultState());
				case 2:
					if(player.dimension == ACLib.abyssal_wasteland_id && player.isSneaking() || player.dimension == 0 || o == 0)
						return buildPortal(world, pos, direction, ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.ABYSSAL_STONE), ACBlocks.coralium_fire.getDefaultState());
					else if(player.dimension == ACLib.dreadlands_id && player.isSneaking() || player.dimension == ACLib.abyssal_wasteland_id || o == 1)
						return buildPortal(world, pos, direction, ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.DREADSTONE), ACBlocks.dreaded_fire.getDefaultState());
					else return buildPortal(world, pos, direction, ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.OMOTHOL_STONE), ACBlocks.omothol_fire.getDefaultState());
				default:
					return EnumActionResult.FAIL;
				}
			}
		} else if(dimWarning(player.dimension))
		{
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.portalplacer.error.2"));
			return EnumActionResult.FAIL;
		}
		else if(!isCorrectDim(player.dimension)){
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.portalplacer.error.1"));
			return EnumActionResult.FAIL;
		}
		return EnumActionResult.PASS;
	}

	private EnumActionResult buildPortal(World world, BlockPos pos, int direction, IBlockState frame, IBlockState fire){
		if(direction == 1 || direction == 3)
		{
//			boolean b = true;
//
//			for(int z = -1; z < 3; z++)
//				if(!world.getBlockState(pos.add(0, 0, z)).getBlock().isReplaceable(world, pos.add(0, 0, z)))
//					b = false;
//
//			if(b) pos = pos.down();
//
//			for(int y = 1; y < 6; y++)
//				for (int z = -1; z < 3; z++)
//					if(!world.getBlockState(pos.add(0, y, z)).getBlock().isReplaceable(world, pos.add(0, y, z)))
//						return EnumActionResult.FAIL;
//
//			world.setBlockState(pos.add(0, 1, 0), frame);
//			world.setBlockState(pos.add(0, 1, 1), frame);
//			world.setBlockState(pos.add(0, 1, 2), frame);
//			world.setBlockState(pos.add(0, 1, -1), frame);
//
//			world.setBlockState(pos.add(0, 2, -1), frame);
//			world.setBlockState(pos.add(0, 3, -1), frame);
//			world.setBlockState(pos.add(0, 4, -1), frame);
//			world.setBlockState(pos.add(0, 5, -1), frame);
//
//			world.setBlockState(pos.add(0, 2, 2), frame);
//			world.setBlockState(pos.add(0, 3, 2), frame);
//			world.setBlockState(pos.add(0, 4, 2), frame);
//			world.setBlockState(pos.add(0, 5, 2), frame);
//
//			world.setBlockState(pos.add(0, 5, 0), frame);
//			world.setBlockState(pos.add(0, 5, 1), frame);
//
//			world.setBlockState(pos.add(0, 2, 1), fire);

			pos = pos.up();
			
			EntityPortal portal = new EntityPortal(world);
			portal.setDestination(ACLib.abyssal_wasteland_id);
			portal.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
			world.spawnEntity(portal);
			
			return EnumActionResult.SUCCESS;
		}
		else
		{
//			boolean b = true;
//
//			for(int x = -1; x < 3; x++)
//				if(!world.getBlockState(pos.add(x, 0, 0)).getBlock().isReplaceable(world, pos.add(x, 0, 0)))
//					b = false;
//
//			if(b) pos = pos.down();
//
//			for(int y = 1; y < 6; y++)
//				for (int x = -1; x < 3; x++)
//					if(!world.getBlockState(pos.add(x, y, 0)).getBlock().isReplaceable(world, pos.add(x, y, 0)))
//						return EnumActionResult.FAIL;
//
//			world.setBlockState(pos.add(0, 1, 0), frame);
//			world.setBlockState(pos.add(1, 1, 0), frame);
//			world.setBlockState(pos.add(2, 1, 0), frame);
//			world.setBlockState(pos.add(-1, 1, 0), frame);
//
//			world.setBlockState(pos.add(-1, 2, 0), frame);
//			world.setBlockState(pos.add(-1, 3, 0), frame);
//			world.setBlockState(pos.add(-1, 4, 0), frame);
//			world.setBlockState(pos.add(-1, 5, 0), frame);
//
//			world.setBlockState(pos.add(2, 2, 0), frame);
//			world.setBlockState(pos.add(2, 3, 0), frame);
//			world.setBlockState(pos.add(2, 4, 0), frame);
//			world.setBlockState(pos.add(2, 5, 0), frame);
//
//			world.setBlockState(pos.add(0, 5, 0), frame);
//			world.setBlockState(pos.add(1, 5, 0), frame);
//
//			world.setBlockState(pos.add(1, 2, 0), fire);

			pos = pos.up();
			EntityPortal portal = new EntityPortal(world);
			portal.setDestination(ACLib.abyssal_wasteland_id);
			portal.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
			world.spawnEntity(portal);
			
			return EnumActionResult.SUCCESS;
		}
	}
}
