/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;
import com.shinoow.abyssalcraft.lib.item.ItemACBasic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemTrackerPSDL extends ItemACBasic {

	public ItemTrackerPSDL() {
		super("powerstonetracker");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		ItemStack par1ItemStack = par3EntityPlayer.getHeldItem(hand);
		RayTraceResult movingobjectposition = rayTrace(par2World, par3EntityPlayer, false);

		if (movingobjectposition != null && movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK && par2World.getBlockState(movingobjectposition.getBlockPos()) == ACBlocks.dreadlands_infused_powerstone)
			return new ActionResult(EnumActionResult.PASS, par1ItemStack);

		if (!par2World.isRemote)
		{
			BlockPos blockpos = ((WorldServer)par2World).getChunkProvider().getNearestStructurePos(par2World, "AbyStronghold", new BlockPos(par3EntityPlayer), true); //TODO change?

			if (blockpos != null)
			{
				EntityPSDLTracker entitypsdltracker = new EntityPSDLTracker(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ);
				entitypsdltracker.moveTowards(blockpos);
				par2World.spawnEntity(entitypsdltracker);
				par2World.playSound((EntityPlayer)null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				par2World.playEvent((EntityPlayer)null, 1003, new BlockPos(par3EntityPlayer), 0);

				if (!par3EntityPlayer.capabilities.isCreativeMode)
					par1ItemStack.shrink(1);

				return new ActionResult(EnumActionResult.SUCCESS, par1ItemStack);
			}
		}

		return new ActionResult(EnumActionResult.FAIL, par1ItemStack);
	}

	@Override
	public boolean hasEffect(ItemStack is){
		return true;
	}
}
