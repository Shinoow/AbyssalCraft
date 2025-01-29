/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityIdolOfFading;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureDarkShrine extends StructureDarklandsBase {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {

		PlacementSettings placeSettings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID);

		position = position.down(3);

		MinecraftServer server = worldIn.getMinecraftServer();
		TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "shrine/dark_shrine"));

		template.addBlocksToWorld(worldIn, position, placeSettings);

		Map<BlockPos, String> map = template.getDataBlocks(position, placeSettings);

		for(Entry<BlockPos, String> entry : map.entrySet())
			if("idol".equals(entry.getValue())) {
				worldIn.setBlockState(entry.getKey(), ACBlocks.idol_of_fading.getDefaultState());
				TileEntity tile = worldIn.getTileEntity(entry.getKey());
				if(tile instanceof TileEntityIdolOfFading)
					((TileEntityIdolOfFading)tile).addEnergy(((TileEntityIdolOfFading) tile).getMaxEnergy());
			}

		return true;
	}

}
