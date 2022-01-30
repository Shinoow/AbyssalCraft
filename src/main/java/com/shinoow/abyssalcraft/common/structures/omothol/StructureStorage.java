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
package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.*;
import java.util.Map.Entry;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrate;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureStorage extends WorldGenerator {

	private Set<BlockPos> positions = new HashSet<>();

	public boolean tooClose(BlockPos pos) {
		return positions.stream().anyMatch(b -> b.getDistance(pos.getX(), b.getY(), pos.getZ()) <= 300);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		while(worldIn.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;

		Rotation[] arotation = Rotation.values();

		PlacementSettings placeSettings = new PlacementSettings().setRotation(arotation[rand.nextInt(arotation.length)]).setReplacedBlock(Blocks.STRUCTURE_VOID);

		BlockPos center = pos;
		BlockPos crates = pos;
		int distX = 8, distZ = 9;

		switch(placeSettings.getRotation()) {
		case CLOCKWISE_180:
			pos = pos.add(16, 0, 18);
			center = pos.north(distZ).west(distX);
			crates = pos.north(4).west(4);
			break;
		case CLOCKWISE_90:
			distX = 9;
			distZ = 8;
			pos = pos.add(18, 0, 0);
			center = pos.south(distZ).west(distX);
			crates = pos.south(4).west(4);
			break;
		case COUNTERCLOCKWISE_90:
			distX = 9;
			distZ = 8;
			pos = pos.add(0, 0, 16);
			center = pos.north(distZ).east(distX);
			crates = pos.north(4).east(4);
			break;
		case NONE:
			pos = pos.add(0, 0, 0);
			center = pos.south(distZ).east(distX);
			crates = pos.south(4).east(4);
			break;
		default:
			pos = pos.add(0, 0, 0);
			center = pos.south(distZ).east(distX);
			crates = pos.south(4).east(4);
			break;
		}

		if(worldIn.getBlockState(center).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.north(distZ)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.south(distZ)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.west(distX)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.east(distX)).getBlock() != ACBlocks.omothol_stone) return false;

		center = worldIn.getHeight(center);
		if(center.getY() > pos.getY()) {
			pos = pos.up(center.getY() - pos.getY());
			crates = crates.up(center.getY() - crates.getY());
		}

		MinecraftServer server = worldIn.getMinecraftServer();
		TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "omothol/storage"));

		template.addBlocksToWorld(worldIn, pos, placeSettings);

		positions.add(pos);

		int num = rand.nextInt(4) + 1;

		boolean treasure = false;

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "omothol/crates_"+num));

		template.addBlocksToWorld(worldIn, crates, placeSettings);

		Map<BlockPos, String> map = template.getDataBlocks(crates, placeSettings);

		for (Entry<BlockPos, String> entry : map.entrySet())
			if(entry.getValue().startsWith("crate")) {
				worldIn.setBlockState(entry.getKey(), ACBlocks.wooden_crate.getDefaultState());
				TileEntityCrate crate = (TileEntityCrate)worldIn.getTileEntity(entry.getKey());
				if(crate != null) {
					boolean chance = treasure ? false : worldIn.rand.nextInt(5) == 0;
					crate.setLootTable(getLootTable(chance), worldIn.rand.nextLong());
					if(chance)
						treasure = true;
				}
			}

		return true;
	}

	private ResourceLocation getLootTable(boolean treasure) {
		return treasure ? ACLoot.CHEST_OMOTHOL_STORAGE_TREASURE : ACLoot.CHEST_OMOTHOL_STORAGE_JUNK;
	}
}
