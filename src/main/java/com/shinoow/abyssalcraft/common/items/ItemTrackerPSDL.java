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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;

public class ItemTrackerPSDL extends Item {

	public ItemTrackerPSDL() {
		super();
		//		GameRegistry.registerItem(this, "powerstonetracker");
		setUnlocalizedName("powerstonetracker");
		setCreativeTab(AbyssalCraft.tabItems);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, false);

		if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && par2World.getBlockState(movingobjectposition.getBlockPos()) == AbyssalCraft.PSDL)
			return par1ItemStack;

		if (!par2World.isRemote)
		{
			BlockPos blockpos = par2World.getStrongholdPos("AbyStronghold", new BlockPos(par3EntityPlayer));

			if (blockpos != null)
			{
				EntityPSDLTracker entitypsdltracker = new EntityPSDLTracker(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ);
				entitypsdltracker.moveTowards(blockpos);
				par2World.spawnEntityInWorld(entitypsdltracker);
				par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				par2World.playAuxSFXAtEntity((EntityPlayer)null, 1002, new BlockPos(par3EntityPlayer), 0);

				if (!par3EntityPlayer.capabilities.isCreativeMode)
					--par1ItemStack.stackSize;
			}
		}

		return par1ItemStack;
	}

	@Override
	public boolean hasEffect(ItemStack is){
		return true;
	}
}
