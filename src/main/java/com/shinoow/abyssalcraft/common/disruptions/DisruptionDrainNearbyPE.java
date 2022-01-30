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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.*;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.api.item.ACItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisruptionDrainNearbyPE extends DisruptionEntry {

	public DisruptionDrainNearbyPE() {
		super("potentialEnergyDrain", null);

	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {
		int drained = 0;
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof IEnergyManipulator){
			int range = (int) (PEUtils.getRangeAmplifiers(world, pos, (IEnergyManipulator)tile) + ((IEnergyManipulator)tile).getAmplifier(AmplifierType.RANGE)/2);
			int xp = pos.getX();
			int yp = pos.getY();
			int zp = pos.getZ();
			for(int x = -1*(3+range); x <= 3+range; x++)
				for(int y = 0; y <= PEUtils.getRangeAmplifiers(world, pos, (IEnergyManipulator)tile); y++)
					for(int z = -1*(3+range); z <= 3+range; z++)
						if(PEUtils.isCollector(world.getTileEntity(new BlockPos(xp + x, yp - y, zp + z)))){
							BlockPos pos1 = new BlockPos(xp + x, yp - y, zp + z);
							IEnergyContainer collector = (IEnergyContainer)world.getTileEntity(pos1);
							if(!world.isRemote)
								collector.consumeEnergy(collector.getContainedEnergy()/(world.rand.nextInt(4) == 0 ? 1 : 4));
							drained++;
							for(int i = 0; i < 3; i++)
								world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos1.getX() + 0.5D, pos1.getY() + 0.5D, pos1.getZ() + 0.5D, 0,0,0);
						}
		} else {
			int range = 4;
			int xp = pos.getX();
			int yp = pos.getY();
			int zp = pos.getZ();
			for(int x = -1*(3+range); x <= 3+range; x++)
				for(int y = 0; y <= 2; y++)
					for(int z = -1*(3+range); z <= 3+range; z++)
						if(PEUtils.isCollector(world.getTileEntity(new BlockPos(xp + x, yp - y, zp + z)))){
							BlockPos pos1 = new BlockPos(xp + x, yp - y, zp + z);
							IEnergyContainer collector = (IEnergyContainer)world.getTileEntity(pos1);
							if(!world.isRemote)
								collector.consumeEnergy(collector.getContainedEnergy()/(world.rand.nextInt(4) == 0 ? 1 : 2));
							drained++;
							for(int i = 0; i < 3; i++)
								world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos1.getX() + 0.5D, pos1.getY() + 0.5D, pos1.getZ() + 0.5D, 0,0,0);
						}
		}

		if(drained == 0)
			for(EntityPlayer player : players)
				for(ItemStack item : player.inventory.mainInventory)
					if(item != null && item.getItem() instanceof IEnergyContainerItem &&
					((IEnergyContainerItem) item.getItem()).getContainedEnergy(item) > 0){
						if(!world.isRemote)
							((IEnergyContainerItem) item.getItem()).consumeEnergy(item, ((IEnergyContainerItem) item.getItem()).getContainedEnergy(item)/(world.rand.nextInt(4) == 0 ? 2 : 10));
						if(isNecronomicon(item.getItem())) player.attackEntityFrom(DamageSource.MAGIC, 2);
						for(int i = 0; i < 3; i++)
							world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, player.posX + (world.rand.nextDouble() - 0.5D) * player.width,
									player.posY + world.rand.nextDouble() * player.height, player.posZ + (world.rand.nextDouble() - 0.5D) * player.width, 0,0,0);
					}
	}

	boolean isNecronomicon(Item item){
		return item == ACItems.necronomicon || item == ACItems.abyssal_wasteland_necronomicon || item == ACItems.dreadlands_necronomicon
				|| item == ACItems.omothol_necronomicon || item == ACItems.abyssalnomicon;
	}
}
