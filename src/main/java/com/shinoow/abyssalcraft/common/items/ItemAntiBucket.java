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

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event;

public class ItemAntiBucket extends ItemBucket{
	Block isFull;
	public ItemAntiBucket(Block par1){
		super(par1);
		this.setMaxStackSize(1);
		isFull = par1;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world,
			EntityPlayer player) {

		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

		if (movingobjectposition == null)
		{
			return item;
		}
		else
		{
			FillBucketEvent event = new FillBucketEvent(player, item, world, movingobjectposition);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				return item;
			}

			if (event.getResult() == Event.Result.ALLOW)
			{
				if (player.capabilities.isCreativeMode)
				{
					return item;
				}

				if (--item.stackSize <= 0)
				{
					return event.result;
				}

				if (!player.inventory.addItemStackToInventory(event.result))
				{
					//player.dropPlayerItem(event.result);
				}

				return item;
			}

			if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = movingobjectposition.blockX;
				int y = movingobjectposition.blockY;
				int z = movingobjectposition.blockZ;

				if (!world.canMineBlock(player, x, y, z))
				{
					return item;
				}


				if (movingobjectposition.sideHit == 0)
				{
					--y;
				}

				if (movingobjectposition.sideHit == 1)
				{
					++y;
				}

				if (movingobjectposition.sideHit == 2)
				{
					--z;
				}

				if (movingobjectposition.sideHit == 3)
				{
					++z;
				}

				if (movingobjectposition.sideHit == 4)
				{
					--x;
				}

				if (movingobjectposition.sideHit == 5)
				{
					++x;
				}

				if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, item))
				{
					return item;
				}

				if (this.tryPlaceContainedLiquid(world, x, y, z) && !player.capabilities.isCreativeMode)
				{
					return new ItemStack(Items.bucket);
				}

			}

			return item;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B)
	{
		l.add("This liquid can make any other liquid harden.");
	}

}