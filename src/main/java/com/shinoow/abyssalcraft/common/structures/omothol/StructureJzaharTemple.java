/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureJzaharTemple extends WorldGenerator {

	public StructureJzaharTemple() {}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;

		PlacementSettings placeSettings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID);

		pos = pos.add(-4, -2, -1);

		MinecraftServer server = world.getMinecraftServer();
		TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "temple/jzahartemple_front_right"));

		template.addBlocksToWorld(world, pos.west(32), placeSettings);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "temple/jzahartemple_front_middle"));

		template.addBlocksToWorld(world, pos, placeSettings);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "temple/jzahartemple_front_left"));

		template.addBlocksToWorld(world, pos.east(32), placeSettings);

		pos = pos.south(32);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "temple/jzahartemple_middle_right"));

		template.addBlocksToWorld(world, pos.west(32), placeSettings);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "temple/jzahartemple_middle_middle"));

		template.addBlocksToWorld(world, pos, placeSettings);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "temple/jzahartemple_middle_left"));

		template.addBlocksToWorld(world, pos.east(32), placeSettings);

		pos = pos.south(32).west(9);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "temple/jzahartemple_back"));

		template.addBlocksToWorld(world, pos, placeSettings);

		return true;
	}
}
