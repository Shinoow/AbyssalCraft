/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureCity extends WorldGenerator {

	private Set<BlockPos> positions = new HashSet<>();

	public boolean tooClose(BlockPos pos) {
		return positions.stream().anyMatch(b -> b.getDistance(pos.getX(), b.getY(), pos.getZ()) <= 18);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		while(worldIn.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;

		int num = rand.nextInt(7);


		Rotation[] arotation = Rotation.values();

		PlacementSettings placeSettings = new PlacementSettings().setRotation(arotation[rand.nextInt(arotation.length)]).setReplacedBlock(Blocks.STRUCTURE_VOID);

		BlockPos center = pos;

		int distX = 0, distZ = 0;
		int widthX = 0, widthZ = 0;

		switch(num) {
		case 0:
			widthX = 14;
			widthZ = 14;
			distX = 7;
			distZ = 7;
			break;
		case 1:
			widthX = 11;
			widthZ = 14;
			distX = 5;
			distZ = 7;
			break;
		case 2:
			widthX = 9;
			widthZ = 14;
			distX = 4;
			distZ = 7;
			break;
		case 3:
			widthX = 11;
			widthZ = 14;
			distX = 5;
			distZ = 7;
			break;
		case 4:
			widthX = 9;
			widthZ = 8;
			distX = 4;
			distZ = 4;
			break;
		case 5:
			widthX = 19;
			widthZ = 14;
			distX = 9;
			distZ = 7;
			break;
		case 6:
			widthX = 12;
			widthZ = 16;
			distX = 6;
			distZ = 8;
			break;
		default:
			widthX = 14;
			widthZ = 14;
			distX = 7;
			distZ = 7;
			break;
		}

		switch(placeSettings.getRotation()) {
		case CLOCKWISE_180:
			pos = pos.add(widthX, 0, widthZ);
			center = pos.north(distX).west(distZ);
			break;
		case CLOCKWISE_90:
			pos = pos.add(widthX, 0, 0);
			center = pos.south(distX).west(distZ);
			break;
		case COUNTERCLOCKWISE_90:
			pos = pos.add(0, 0, widthZ);
			center = pos.north(distX).east(distZ);
			break;
		case NONE:
			pos = pos.add(0, 0, 0);
			center = pos.south(distX).east(distZ);
			break;
		default:
			pos = pos.add(0, 0, 0);
			center = pos.south(distX).east(distZ);
			break;
		}

		if(worldIn.getBlockState(center).getBlock() != ACBlocks.stone ||
				worldIn.getBlockState(center.north(distZ)).getBlock() != ACBlocks.stone ||
				worldIn.getBlockState(center.south(distZ)).getBlock() != ACBlocks.stone ||
				worldIn.getBlockState(center.west(distX)).getBlock() != ACBlocks.stone ||
				worldIn.getBlockState(center.east(distX)).getBlock() != ACBlocks.stone) return false;

		center = worldIn.getHeight(center);
		if(center.getY() > pos.getY())
			pos = pos.up(center.getY() - pos.getY());

		if(num == 0)
			pos = pos.down(4);
		else if(num == 3)
			pos = pos.down();

		MinecraftServer server = worldIn.getMinecraftServer();
		TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", getRandomStructure(num)));

		template.addBlocksToWorld(worldIn, pos, placeSettings);

		positions.add(pos);

		return true;
	}

	private String getRandomStructure(int num) {
		switch(num) {
		case 0:
			return "omothol/bar";
		case 1:
			return "omothol/blacksmith";
		case 2:
			return "omothol/church";
		case 3:
			return "omothol/farm";
		case 4:
			return "omothol/farmhouse";
		case 5:
			return "omothol/house";
		case 6:
			return "omothol/library";
		default:
			return "omothol/bar";
		}
	}
}
