/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemPortalPlacer extends Item {

	private final int key;

	public ItemPortalPlacer(int key, String unlocalizedName){
		super();
		this.key = key;
		maxStackSize = 1;
		setUnlocalizedName(unlocalizedName);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer entityplayer, List list, boolean is){
		list.add(I18n.translateToLocal("tooltip.portalplacer.1"));
		list.add(I18n.translateToLocal("tooltip.portalplacer.2"));
		if(key > 0)
			list.add(I18n.translateToLocal("tooltip.portalplacer.3"));
		if(Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().theWorld.provider != null)
			if(!isCorrectDim(Minecraft.getMinecraft().theWorld.provider.getDimension()))
				list.add(TextFormatting.DARK_RED+""+TextFormatting.ITALIC+I18n.translateToLocal("tooltip.portalplacer.4"));
	}

	private boolean isCorrectDim(int dim){
		switch(key){
		case 0:
			if(dim == 0 || dim == ACLib.abyssal_wasteland_id)
				return true;
			else if(AbyssalCraftAPI.getGatewayKeyOverride(dim) == 0)
				return true;
			else return false;
		case 1:
			if(dim == 0 || dim == ACLib.abyssal_wasteland_id ||
			dim == ACLib.dreadlands_id)
				return true;
			else if(AbyssalCraftAPI.getGatewayKeyOverride(dim) >= 0 && AbyssalCraftAPI.getGatewayKeyOverride(dim) < 2)
				return true;
			else return false;
		case 2:
			if(dim == 0 || dim == ACLib.abyssal_wasteland_id ||
			dim == ACLib.dreadlands_id ||
			dim == ACLib.omothol_id ||
			dim == ACLib.dark_realm_id)
				return true;
			else if(AbyssalCraftAPI.getGatewayKeyOverride(dim) >= 0)
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
			else if(AbyssalCraftAPI.getGatewayKeyOverride(dim) > 0)
				return true;
			else return false;
		case 1:
			if(dim == ACLib.omothol_id ||
			dim == ACLib.dark_realm_id)
				return true;
			else if(AbyssalCraftAPI.getGatewayKeyOverride(dim) > 1)
				return true;
			else return false;
		default:
			return false;
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack is, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!world.isRemote){
			if(isCorrectDim(player.dimension))
			{
				int direction = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
				int o = AbyssalCraftAPI.getGatewayKeyOverride(player.dimension);

				switch(key){
				case 0:
					return buildPortal(world, pos, direction, ACBlocks.abyssal_stone.getDefaultState(), ACBlocks.coralium_fire.getDefaultState());
				case 1:
					if(player.dimension == ACLib.abyssal_wasteland_id && player.isSneaking() || player.dimension == 0 || o == 0)
						return buildPortal(world, pos, direction, ACBlocks.abyssal_stone.getDefaultState(), ACBlocks.coralium_fire.getDefaultState());
					else return buildPortal(world, pos, direction, ACBlocks.dreadstone.getDefaultState(), ACBlocks.dreaded_fire.getDefaultState());
				case 2:
					if(player.dimension == ACLib.abyssal_wasteland_id && player.isSneaking() || player.dimension == 0 || o == 0)
						return buildPortal(world, pos, direction, ACBlocks.abyssal_stone.getDefaultState(), ACBlocks.coralium_fire.getDefaultState());
					else if(player.dimension == ACLib.dreadlands_id && player.isSneaking() || player.dimension == ACLib.abyssal_wasteland_id || o == 1)
						return buildPortal(world, pos, direction, ACBlocks.dreadstone.getDefaultState(), ACBlocks.dreaded_fire.getDefaultState());
					else return buildPortal(world, pos, direction, ACBlocks.omothol_stone.getDefaultState(), ACBlocks.omothol_fire.getDefaultState());
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
			boolean b = true;

			for(int z = -1; z < 3; z++)
				if(!world.getBlockState(pos.add(0, 0, z)).getBlock().isReplaceable(world, pos.add(0, 0, z)))
					b = false;

			if(b) pos = pos.down();

			for(int y = 1; y < 6; y++)
				for (int z = -1; z < 3; z++)
					if(!world.getBlockState(pos.add(0, y, z)).getBlock().isReplaceable(world, pos.add(0, y, z)))
						return EnumActionResult.FAIL;

			world.setBlockState(pos.add(0, 1, 0), frame);
			world.setBlockState(pos.add(0, 1, 1), frame);
			world.setBlockState(pos.add(0, 1, 2), frame);
			world.setBlockState(pos.add(0, 1, -1), frame);

			world.setBlockState(pos.add(0, 2, -1), frame);
			world.setBlockState(pos.add(0, 3, -1), frame);
			world.setBlockState(pos.add(0, 4, -1), frame);
			world.setBlockState(pos.add(0, 5, -1), frame);

			world.setBlockState(pos.add(0, 2, 2), frame);
			world.setBlockState(pos.add(0, 3, 2), frame);
			world.setBlockState(pos.add(0, 4, 2), frame);
			world.setBlockState(pos.add(0, 5, 2), frame);

			world.setBlockState(pos.add(0, 5, 0), frame);
			world.setBlockState(pos.add(0, 5, 1), frame);

			world.setBlockState(pos.add(0, 2, 1), fire);

			return EnumActionResult.SUCCESS;
		}
		else
		{
			boolean b = true;

			for(int x = -1; x < 3; x++)
				if(!world.getBlockState(pos.add(x, 0, 0)).getBlock().isReplaceable(world, pos.add(x, 0, 0)))
					b = false;

			if(b) pos = pos.down();

			for(int y = 1; y < 6; y++)
				for (int x = -1; x < 3; x++)
					if(!world.getBlockState(pos.add(x, y, 0)).getBlock().isReplaceable(world, pos.add(x, y, 0)))
						return EnumActionResult.FAIL;

			world.setBlockState(pos.add(0, 1, 0), frame);
			world.setBlockState(pos.add(1, 1, 0), frame);
			world.setBlockState(pos.add(2, 1, 0), frame);
			world.setBlockState(pos.add(-1, 1, 0), frame);

			world.setBlockState(pos.add(-1, 2, 0), frame);
			world.setBlockState(pos.add(-1, 3, 0), frame);
			world.setBlockState(pos.add(-1, 4, 0), frame);
			world.setBlockState(pos.add(-1, 5, 0), frame);

			world.setBlockState(pos.add(2, 2, 0), frame);
			world.setBlockState(pos.add(2, 3, 0), frame);
			world.setBlockState(pos.add(2, 4, 0), frame);
			world.setBlockState(pos.add(2, 5, 0), frame);

			world.setBlockState(pos.add(0, 5, 0), frame);
			world.setBlockState(pos.add(1, 5, 0), frame);

			world.setBlockState(pos.add(1, 2, 0), fire);

			return EnumActionResult.SUCCESS;
		}
	}
}
