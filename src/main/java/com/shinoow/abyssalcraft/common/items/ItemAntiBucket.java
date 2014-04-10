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