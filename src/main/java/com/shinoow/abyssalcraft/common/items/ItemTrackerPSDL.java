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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;

public class ItemTrackerPSDL extends Item {

	public ItemTrackerPSDL() {
		super();

	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, false);

		if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && par2World.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == AbyssalCraft.PSDL)
			return par1ItemStack;

		if (!par2World.isRemote)
		{
			ChunkPosition chunkposition = par2World.findClosestStructure("AbyStronghold", (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ);

			if (chunkposition != null)
			{
				EntityPSDLTracker entitypsdltracker = new EntityPSDLTracker(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY + 1.62D - par3EntityPlayer.yOffset, par3EntityPlayer.posZ);
				entitypsdltracker.moveTowards(chunkposition.chunkPosX, chunkposition.chunkPosY, chunkposition.chunkPosZ);
				par2World.spawnEntityInWorld(entitypsdltracker);
				par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				par2World.playAuxSFXAtEntity((EntityPlayer)null, 1002, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ, 0);

				if (!par3EntityPlayer.capabilities.isCreativeMode)
					--par1ItemStack.stackSize;
			}
		}

		return par1ItemStack;
	}

	@Override
	public boolean hasEffect(ItemStack is, int pass){
		return true;
	}
}
