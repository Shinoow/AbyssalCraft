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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureTower extends WorldGenerator {

	private Set<BlockPos> positions = new HashSet<>();

	public boolean tooClose(BlockPos pos) {
		return positions.stream().anyMatch(b -> b.getDistance(pos.getX(), b.getY(), pos.getZ()) <= 200);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		while(worldIn.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;

		if(worldIn.getBlockState(pos).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(pos.east(6)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(pos.west(6)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(pos.north(6)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(pos.south(6)).getBlock()!= ACBlocks.omothol_stone) return false;

		pos = pos.up().north(6).west(6);

		PlacementSettings placeSettings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID);

		MinecraftServer server = worldIn.getMinecraftServer();
		TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "omothol/tower_1"));

		template.addBlocksToWorld(worldIn, pos, placeSettings);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "omothol/tower_2"));

		template.addBlocksToWorld(worldIn, pos.offset(EnumFacing.UP, 32), placeSettings);

		positions.add(pos);

		return true;
	}

}
