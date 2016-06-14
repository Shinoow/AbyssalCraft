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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemPortalPlacerJzh extends Item {

	public ItemPortalPlacerJzh(){
		super();
		maxStackSize = 1;
		setUnlocalizedName("gatewaykeyjzh");
		setCreativeTab(ACTabs.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer entityplayer, List list, boolean is){
		list.add(I18n.translateToLocal("tooltip.portalplacerjzh.1"));
		list.add(I18n.translateToLocal("tooltip.portalplacerjzh.2"));
	}

	@Override
	public EnumActionResult onItemUse(ItemStack is, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!world.isRemote){
			if(player.dimension == ACLib.dreadlands_id || player.dimension == ACLib.omothol_id
					|| player.dimension == ACLib.dark_realm_id)
			{
				int direction = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

				if(direction == 1 || direction == 3)
				{
					for(int y = 1; y < 5; y++)
						for (int z = -1; z < 2; z++)
							if(!world.isAirBlock(pos.add(0, y, z)))
								return EnumActionResult.FAIL;

					world.setBlockState(pos.add(0, 1, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 1, 1), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 1, 2), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 1, -1), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(0, 2, -1), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 3, -1), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 4, -1), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 5, -1), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(0, 2, 2), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 3, 2), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 4, 2), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 5, 2), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(0, 5, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(0, 5, 1), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(0, 2, 1), ACBlocks.omothol_fire.getDefaultState());
				}
				else
				{
					for(int y = 1; y < 5; y++)
						for (int x = -1; x < 2; x++)
							if(!world.isAirBlock(pos.add(x, y, 0)))
								return EnumActionResult.FAIL;

					world.setBlockState(pos.add(0, 1, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(1, 1, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(2, 1, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(-1, 1, 0), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(-1, 2, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(-1, 3, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(-1, 4, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(-1, 5, 0), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(2, 2, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(2, 3, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(2, 4, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(2, 5, 0), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(0, 5, 0), ACBlocks.omothol_stone.getDefaultState());
					world.setBlockState(pos.add(1, 5, 0), ACBlocks.omothol_stone.getDefaultState());

					world.setBlockState(pos.add(1, 2, 0), ACBlocks.omothol_fire.getDefaultState());
				}
				return EnumActionResult.SUCCESS;
			}
		} else if(player.dimension == 0 || player.dimension == ACLib.abyssal_wasteland_id)
		{
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.portalplacer.error.2"));
			return EnumActionResult.FAIL;
		} else if(player.dimension == ACLib.dreadlands_id || player.dimension == ACLib.omothol_id
				|| player.dimension == ACLib.dark_realm_id){}
		else {
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.portalplacer.error.1"));
			return EnumActionResult.FAIL;
		}
		return EnumActionResult.PASS;
	}
}