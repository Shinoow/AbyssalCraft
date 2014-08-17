/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemPortalPlacerJzh extends Item
{
	public ItemPortalPlacerJzh()
	{
		super();
		maxStackSize = 1;
		setCreativeTab(AbyssalCraft.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.BLUE + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer entityplayer, List list, boolean is){
		list.add(StatCollector.translateToLocal("tooltip.portalplacerjzh.1"));
		list.add(StatCollector.translateToLocal("tooltip.portalplacerjzh.2"));
	}

	//	@Override
	//	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	//	{
	//		if(par3World.isRemote && par3World.provider.isSurfaceWorld())
	//		{
	//		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Nothing happened..."));
	//		return false;
	//		}
	//
	//	else if(!par3World.isRemote && par2EntityPlayer.dimension != AbyssalCraft.configDimId2 || !par3World.isRemote && par2EntityPlayer.dimension != AbyssalCraft.configDimId3)
	//	{
	//			int direction = MathHelper.floor_double(par2EntityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	//
	//			if(direction == 1 || direction == 3)
	//			{
	//				for(int y = 1; y < 5; y++)
	//				{
	//					for (int z = -1; z < 2; z++)
	//					{
	//						if(par3World.getBlockMetadata(par4, par5 + y, par6 + z) != 0)
	//						{
	//							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("No room for a portal."));
	//							return false;
	//						}
	//					}
	//				}
	//
	//				par3World.setBlock(par4, par5 + 1, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 1, par6 + 1, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 1, par6 + 2, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 1, par6 - 1, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4, par5 + 2, par6 - 1, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 3, par6 - 1, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 4, par6 - 1, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 5, par6 - 1, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4, par5 + 2, par6 + 2, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 3, par6 + 2, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 4, par6 + 2, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 5, par6 + 2, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4, par5 + 5, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4, par5 + 5, par6 + 1, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4, par5 + 2, par6 + 1, AbyssalCraft.Coraliumfire);
	//			}
	//			else
	//			{
	//				for(int y = 1; y < 5; y++)
	//				{
	//					for (int x = -1; x < 2; x++)
	//					{
	//						if(par3World.getBlockMetadata(par4 + x, par5 + y, par6) != 0)
	//						{
	//							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("No room for a portal."));
	//							return false;
	//						}
	//					}
	//				}
	//
	//				par3World.setBlock(par4, par5 + 1, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 + 1, par5 + 1, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 + 2, par5 + 1, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 - 1, par5 + 1, par6, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4 - 1, par5 + 2, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 - 1, par5 + 3, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 - 1, par5 + 4, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 - 1, par5 + 5, par6, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4 + 2, par5 + 2, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 + 2, par5 + 3, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 + 2, par5 + 4, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 + 2, par5 + 5, par6, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4, par5 + 5, par6, AbyssalCraft.abystone);
	//				par3World.setBlock(par4 + 1, par5 + 5, par6, AbyssalCraft.abystone);
	//
	//				par3World.setBlock(par4 + 1, par5 + 2, par6, AbyssalCraft.Coraliumfire);
	//			}
	//			return true;
	//		}
	//		else
	//			return false;
	//	}
}