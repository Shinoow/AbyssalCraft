/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemDarkstoneHoe extends Item
{


	public ItemDarkstoneHoe(ToolMaterial enumToolMaterial)
	{
		super();
		maxStackSize = 1;
		setMaxDamage(enumToolMaterial.getMaxUses());
	}
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
			return false;
		else
		{
			UseHoeEvent event = new UseHoeEvent(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6);
			if (MinecraftForge.EVENT_BUS.post(event))
				return false;

			if (event.getResult() == Result.ALLOW)
			{
				par1ItemStack.damageItem(1, par2EntityPlayer);
				return true;
			}

			Block var11 = par3World.getBlock(par4, par5, par6);
			int var12 = par3World.getBlockMetadata(par4, par5 + 1, par6);

			if ((par7 == 0 || var12 != 0 || var11 != Blocks.grass) && var11 != Blocks.dirt && var11 != AbyssalCraft.Darkgrass && var11 != AbyssalCraft.dreadgrass)
				return false;
			else
			{
				Block var13 = Blocks.farmland;
				par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, var13.stepSound.func_150496_b(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);

				if (par3World.isRemote)
					return true;
				else
				{
					par3World.setBlock(par4, par5, par6, var13);
					par1ItemStack.damageItem(1, par2EntityPlayer);
					return true;
				}
			}
		}
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

}