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

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemACHoe extends ItemHoe {

	private EnumChatFormatting format;

	public ItemACHoe(ToolMaterial mat, String name){
		this(mat, name, null);
	}

	public ItemACHoe(ToolMaterial mat, String name, EnumChatFormatting format) {
		super(mat);
		setCreativeTab(AbyssalCraft.tabTools);
		setUnlocalizedName(name);
		setTextureName(AbyssalCraft.modid + ":" + name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name") : super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if (!player.canPlayerEdit(x, y, z, par7, stack))
			return false;
		else
		{
			UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
			if (MinecraftForge.EVENT_BUS.post(event))
				return false;

			if (event.getResult() == Result.ALLOW)
			{
				stack.damageItem(1, player);
				return true;
			}

			Block block = world.getBlock(x, y, z);

			if (par7 != 0 && world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt || block == AbyssalCraft.Darkgrass || block == AbyssalCraft.dreadgrass))
			{
				Block block1 = Blocks.farmland;
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);

				if (world.isRemote)
					return true;
				else
				{
					world.setBlock(x, y, z, block1);
					stack.damageItem(1, player);
					return true;
				}
			} else
				return false;
		}
	}
}