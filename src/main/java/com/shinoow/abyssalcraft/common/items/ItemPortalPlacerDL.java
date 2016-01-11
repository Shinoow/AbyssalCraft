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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemPortalPlacerDL extends Item {

	public ItemPortalPlacerDL() {
		super();
		maxStackSize = 1;
		setMaxDamage(1);
		setCreativeTab(AbyssalCraft.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer entityplayer, List list, boolean is){
		list.add(StatCollector.translateToLocal("tooltip.portalplacerdl.1"));
		list.add(StatCollector.translateToLocal("tooltip.portalplacerdl.2"));
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(!par3World.isRemote){
			if(par2EntityPlayer.dimension == AbyssalCraft.configDimId1 || par2EntityPlayer.dimension == AbyssalCraft.configDimId2)
			{
				int direction = MathHelper.floor_double(par2EntityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

				if(direction == 1 || direction == 3)
				{
					for(int y = 1; y < 5; y++)
						for (int z = -1; z < 2; z++)
							if(par3World.getBlock(par4, par5 + y, par6 + z) != Blocks.air && par3World.isRemote)
								return false;

					par3World.setBlock(par4, par5 + 1, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 1, par6 + 1, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 1, par6 + 2, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 1, par6 - 1, AbyssalCraft.dreadstone);

					par3World.setBlock(par4, par5 + 2, par6 - 1, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 3, par6 - 1, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 4, par6 - 1, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 5, par6 - 1, AbyssalCraft.dreadstone);

					par3World.setBlock(par4, par5 + 2, par6 + 2, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 3, par6 + 2, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 4, par6 + 2, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 5, par6 + 2, AbyssalCraft.dreadstone);

					par3World.setBlock(par4, par5 + 5, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4, par5 + 5, par6 + 1, AbyssalCraft.dreadstone);

					par3World.setBlock(par4, par5 + 2, par6 + 1, AbyssalCraft.dreadfire);
				}
				else
				{
					for(int y = 1; y < 5; y++)
						for (int x = -1; x < 2; x++)
							if(par3World.getBlock(par4 + x, par5 + y, par6) != Blocks.air && par3World.isRemote)
								return false;

					par3World.setBlock(par4, par5 + 1, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 + 1, par5 + 1, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 + 2, par5 + 1, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 - 1, par5 + 1, par6, AbyssalCraft.dreadstone);

					par3World.setBlock(par4 - 1, par5 + 2, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 - 1, par5 + 3, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 - 1, par5 + 4, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 - 1, par5 + 5, par6, AbyssalCraft.dreadstone);

					par3World.setBlock(par4 + 2, par5 + 2, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 + 2, par5 + 3, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 + 2, par5 + 4, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 + 2, par5 + 5, par6, AbyssalCraft.dreadstone);

					par3World.setBlock(par4, par5 + 5, par6, AbyssalCraft.dreadstone);
					par3World.setBlock(par4 + 1, par5 + 5, par6, AbyssalCraft.dreadstone);

					par3World.setBlock(par4 + 1, par5 + 2, par6, AbyssalCraft.dreadfire);
				}
				return true;
			}
		} else if(par2EntityPlayer.dimension == 0 || par2EntityPlayer.dimension == AbyssalCraft.configDimId3 || par2EntityPlayer.dimension == AbyssalCraft.configDimId4)
		{
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.portalplacer.error.2")));
			return false;
		} else if(par2EntityPlayer.dimension == AbyssalCraft.configDimId1 || par2EntityPlayer.dimension == AbyssalCraft.configDimId2){}
		else {
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.portalplacer.error.1")));
			return false;
		}
		return false;
	}
}
