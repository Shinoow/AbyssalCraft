/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.IPlantable;

public class NecronomiconPurgingRitual extends NecronomiconRitual {

	public NecronomiconPurgingRitual() {
		super("purging", 3, 10000F, new ItemStack(ACBlocks.crystal_cluster2, 1, 9), null, new ItemStack(ACBlocks.crystal_cluster2, 1, 9),
			null, new ItemStack(ACBlocks.crystal_cluster2, 1, 9), null, new ItemStack(ACBlocks.crystal_cluster2, 1, 9));
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		return world.getBiome(pos) instanceof IDreadlandsBiome && world.provider.getDimension() != ACLib.dreadlands_id;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
			for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				for(EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos1).expand(0, 128, 0), EntitySelectors.IS_ALIVE))
					if(!(e instanceof EntityPlayer))
						world.removeEntity(e);

				if(world.getBiome(pos1) == ACBiomes.purged) continue;

				for(int y = 255; y > -1; y--){
					IBlockState state = world.getBlockState(pos1.up(y));
					if(state.getBlock() instanceof IPlantable)
						world.setBlockToAir(pos1.up(y));
					else if(state.getMaterial().isLiquid())
						world.setBlockToAir(pos1.up(y));
					else if(state.getBlock() == ACBlocks.ritual_altar || state.getBlock() == ACBlocks.ritual_pedestal)
						world.setBlockState(pos1.up(y), ACBlocks.calcified_stone.getDefaultState());
					else if(!state.isFullCube())
						world.setBlockToAir(pos1.up(y));
					else if(!world.isAirBlock(pos1.up(y)) && state.getBlock() != Blocks.BEDROCK)
						world.setBlockState(pos1.up(y), ACBlocks.calcified_stone.getDefaultState());
				}

				Chunk c = world.getChunkFromBlockCoords(pos1);
				c.getBiomeArray()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.getIdForBiome(ACBiomes.purged);
				c.setModified(true);
				PacketDispatcher.sendToDimension(new CleansingRitualMessage(x, z, Biome.getIdForBiome(ACBiomes.purged)), world.provider.getDimension());
			}
	}
}
