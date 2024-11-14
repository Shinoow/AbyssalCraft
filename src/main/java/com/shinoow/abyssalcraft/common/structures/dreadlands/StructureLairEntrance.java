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
package com.shinoow.abyssalcraft.common.structures.dreadlands;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureLairEntrance extends WorldGenerator {

	private Set<BlockPos> positions = new HashSet<>();

	private boolean tooClose(BlockPos pos) {
		return positions.stream().anyMatch(b -> b.getDistance(pos.getX(), b.getY(), pos.getZ()) <= 200);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {

		while(worldIn.isAirBlock(position) && position.getY() > 2)
			position = position.down();
		if(position.getY() <= 1) return false;

		BlockPos pos1 = position.add(10, 0, 13); //sealing lock position + a little extra

		while(worldIn.isAirBlock(pos1) && pos1.getY() > 2)
			pos1 = pos1.down();

		if(tooClose(position))
			return false;
		if(worldIn.getBiome(pos1) != ACBiomes.dreadlands_ocean)
			return false;
		else {

			PlacementSettings placeSettings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID);

			if(worldIn.getBlockState(position).getMaterial().isLiquid())
				while(!worldIn.isAirBlock(position.up()))
					position = position.up();


			position = position.down(position.getY() - pos1.getY());

			MinecraftServer server = worldIn.getMinecraftServer();
			TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

			Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_entrance"));

			template.addBlocksToWorld(worldIn, position, placeSettings);

			positions.add(position);
		}
		return false;
	}

}
