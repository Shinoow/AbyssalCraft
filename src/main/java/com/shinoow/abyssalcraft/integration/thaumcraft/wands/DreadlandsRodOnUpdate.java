/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.thaumcraft.wands;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;

public class DreadlandsRodOnUpdate implements IWandRodOnUpdate {

	Aspect primals[] = Aspect.getPrimalAspects().toArray(new Aspect[0]);

	@Override
	public void onUpdate(ItemStack itemstack, EntityPlayer player) {
		if(player.ticksExisted % 100 == 0)
			if(player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.Dreadlands ||
			player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.ForestDreadlands ||
			player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == AbyssalCraft.MountainDreadlands)
				for(int x = 0;x < primals.length;x++)
					if(((ItemWandCasting)itemstack.getItem()).getVis(itemstack, primals[x]) < ((ItemWandCasting)itemstack.getItem()).getMaxVis(itemstack) / 10)
						((ItemWandCasting)itemstack.getItem()).addVis(itemstack, primals[x], 1, true);
	}
}
