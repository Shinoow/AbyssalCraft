/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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
import com.shinoow.abyssalcraft.common.blocks.BlockStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue.EnumDeityType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityStatue;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureTemple extends WorldGenerator {

	private Set<BlockPos> positions = new HashSet<>();

	public boolean tooClose(BlockPos pos) {
		return positions.stream().anyMatch(b -> b.getDistance(pos.getX(), b.getY(), pos.getZ()) <= 200);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;

		Rotation[] arotation = Rotation.values();

		PlacementSettings placeSettings = new PlacementSettings().setRotation(arotation[rand.nextInt(arotation.length)]).setReplacedBlock(Blocks.STRUCTURE_VOID);

		BlockPos center = pos;

		switch(placeSettings.getRotation()) {
		case CLOCKWISE_180:
			pos = pos.add(28, 0, 28);
			center = pos.north(14).west(14);
			break;
		case CLOCKWISE_90:
			pos = pos.add(28, 0, 0);
			center = pos.south(14).west(14);
			break;
		case COUNTERCLOCKWISE_90:
			pos = pos.add(0, 0, 28);
			center = pos.north(14).east(14);
			break;
		case NONE:
			pos = pos.add(0, 0, 0);
			center = pos.south(14).east(14);
			break;
		default:
			pos = pos.add(0, 0, 0);
			center = pos.south(14).east(14);
			break;
		}

		if(world.getBlockState(center).getBlock() != ACBlocks.stone ||
				world.getBlockState(center.north(14)).getBlock() != ACBlocks.stone ||
				world.getBlockState(center.south(14)).getBlock() != ACBlocks.stone ||
				world.getBlockState(center.west(14)).getBlock() != ACBlocks.stone ||
				world.getBlockState(center.east(14)).getBlock() != ACBlocks.stone) return false;

		center = world.getHeight(center);
		if(center.getY() > pos.getY())
			pos = pos.up(center.getY() - pos.getY());

		//		switch(placeSettings.getRotation()) { //legacy offsets
		//		case CLOCKWISE_180:
		//			pos = pos.add(13, 0, 28);
		//			break;
		//		case CLOCKWISE_90:
		//			pos = pos.add(28, 0, -13);
		//			break;
		//		case COUNTERCLOCKWISE_90:
		//			pos = pos.add(-28, 0, 13);
		//			break;
		//		case NONE:
		//			pos = pos.add(-13, 0, -28);
		//			break;
		//		default:
		//			pos = pos.add(-13, 0, -28);
		//			break;
		//
		//		}

		MinecraftServer server = world.getMinecraftServer();
		TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "omothol/temple"));

		template.addBlocksToWorld(world, pos, placeSettings);

		Map<BlockPos, String> map = template.getDataBlocks(pos, placeSettings);

		for (Entry<BlockPos, String> entry : map.entrySet())
			if("statue_center".equals(entry.getValue())) {
				BlockPos pos2 = entry.getKey();
				world.setBlockToAir(pos2);
				world.setBlockState(pos2.down(), ACBlocks.statue.getDefaultState().withProperty(BlockStatue.TYPE, EnumDeityType.byMetadata(rand.nextInt(7))));
				TileEntity te = world.getTileEntity(pos2.down());
				if(te instanceof TileEntityStatue)
					switch(placeSettings.getRotation()) {
					case CLOCKWISE_180:
						((TileEntityStatue) te).setFacing(EnumFacing.NORTH.ordinal());
						break;
					case CLOCKWISE_90:
						((TileEntityStatue) te).setFacing(EnumFacing.WEST.ordinal());
						break;
					case COUNTERCLOCKWISE_90:
						((TileEntityStatue) te).setFacing(EnumFacing.EAST.ordinal());
						break;
					case NONE:
						((TileEntityStatue) te).setFacing(EnumFacing.SOUTH.ordinal());
						break;
					default:
						((TileEntityStatue) te).setFacing(EnumFacing.SOUTH.ordinal());
						break;
					}
			} else if("statue_left".equals(entry.getValue())) {
				BlockPos pos2 = entry.getKey();
				world.setBlockToAir(pos2);
				world.setBlockState(pos2.down(), ACBlocks.statue.getDefaultState().withProperty(BlockStatue.TYPE, EnumDeityType.byMetadata(rand.nextInt(7))));
				TileEntity te = world.getTileEntity(pos2.down());
				if(te instanceof TileEntityStatue)
					switch(placeSettings.getRotation()) {
					case CLOCKWISE_180:
						((TileEntityStatue) te).setFacing(EnumFacing.WEST.ordinal());
						break;
					case CLOCKWISE_90:
						((TileEntityStatue) te).setFacing(EnumFacing.SOUTH.ordinal());
						break;
					case COUNTERCLOCKWISE_90:
						((TileEntityStatue) te).setFacing(EnumFacing.NORTH.ordinal());
						break;
					case NONE:
						((TileEntityStatue) te).setFacing(EnumFacing.EAST.ordinal());
						break;
					default:
						((TileEntityStatue) te).setFacing(EnumFacing.EAST.ordinal());
						break;
					}
			} else if("statue_right".equals(entry.getValue())) {
				BlockPos pos2 = entry.getKey();
				world.setBlockToAir(pos2);
				world.setBlockState(pos2.down(), ACBlocks.statue.getDefaultState().withProperty(BlockStatue.TYPE, EnumDeityType.byMetadata(rand.nextInt(7))));
				TileEntity te = world.getTileEntity(pos2.down());
				if(te instanceof TileEntityStatue)
					switch(placeSettings.getRotation()) {
					case CLOCKWISE_180:
						((TileEntityStatue) te).setFacing(EnumFacing.EAST.ordinal());
						break;
					case CLOCKWISE_90:
						((TileEntityStatue) te).setFacing(EnumFacing.NORTH.ordinal());
						break;
					case COUNTERCLOCKWISE_90:
						((TileEntityStatue) te).setFacing(EnumFacing.SOUTH.ordinal());
						break;
					case NONE:
						((TileEntityStatue) te).setFacing(EnumFacing.WEST.ordinal());
						break;
					default:
						((TileEntityStatue) te).setFacing(EnumFacing.WEST.ordinal());
						break;
					}
			}

		positions.add(pos);

		return true;
	}

}
