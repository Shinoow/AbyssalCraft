/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.ritual;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class NecronomiconCuringRitual extends NecronomiconRitual {

	private Biome replacementBiome;

	public NecronomiconCuringRitual() {
		super("curing", 4, 20000F, new ItemStack(ACItems.antidote, 1, 1), null, new ItemStack(ACItems.antidote, 1, 1),
				null, new ItemStack(ACItems.antidote, 1, 1), null, new ItemStack(ACItems.antidote, 1, 1));
		setRitualParticle(EnumRitualParticle.PE_STREAM);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		boolean ret = false;
		if(world.provider.getDimension() != ACLib.dreadlands_id)
			for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
				for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){
					BlockPos pos1 = new BlockPos(x, 0, z);
					if(!ret) {
						if(world.getBiome(pos1) instanceof IDreadlandsBiome)
							ret = true;
					} else {
						Biome biome = world.getBiome(pos1);
						if(!(biome instanceof IDreadlandsBiome) && biome != ACBiomes.purged) {
							replacementBiome = biome;
							return true;
						}
					}
				}
		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		int num = 1, num2 = 0, range = ACConfig.curingRitualRange * 8;
		for(int x = pos.getX() - range; x < pos.getX() + range + 1; x++)
			for(int z = pos.getZ() - range; z < pos.getZ() + range + 1; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!(world.getBiome(pos1) instanceof IDreadlandsBiome)) continue;

				Scheduler.schedule(new ScheduledProcess(num * 2) {

					@Override
					public void execute() {

						for(EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos1).grow(3, 128, 3), Predicates.and(EntitySelectors.IS_ALIVE, EntityUtil::isDreadPlagueCarrier)))
							e.onKillCommand();

						BiomeUtil.updateBiome(world, pos1, replacementBiome, true);
					}

				});
				num2++;
				if(num2 % 256 == 0)
					num++;
			}
	}
}
