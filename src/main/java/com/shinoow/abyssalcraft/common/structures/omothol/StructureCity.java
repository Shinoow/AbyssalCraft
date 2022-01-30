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
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
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

		int num = rand.nextInt(7);

		if(num == 2 || num == 5 || num == 6)
			pos = pos.add(rand.nextInt(4) + 4, 0, rand.nextInt(4) + 4);
		else
			pos = pos.add(rand.nextInt(8) + 8, 0, rand.nextInt(8) + 8);

		pos = worldIn.getHeight(pos);

		while(worldIn.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;



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
			center = pos.north(distZ).west(distX);
			break;
		case CLOCKWISE_90:
			swap(widthX, widthZ);
			swap(distX, distZ);
			pos = pos.add(widthX, 0, 0);
			center = pos.south(distZ).west(distX);
			break;
		case COUNTERCLOCKWISE_90:
			swap(widthX, widthZ);
			swap(distX, distZ);
			pos = pos.add(0, 0, widthZ);
			center = pos.north(distZ).east(distX);
			break;
		case NONE:
			pos = pos.add(0, 0, 0);
			center = pos.south(distZ).east(distX);
			break;
		default:
			pos = pos.add(0, 0, 0);
			center = pos.south(distZ).east(distX);
			break;
		}

		if(worldIn.getBlockState(center).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.north(distZ)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.south(distZ)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.west(distX)).getBlock() != ACBlocks.omothol_stone ||
				worldIn.getBlockState(center.east(distX)).getBlock() != ACBlocks.omothol_stone) return false;

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

		Map<BlockPos, String> map = template.getDataBlocks(pos, placeSettings);

		for (Entry<BlockPos, String> entry : map.entrySet())
			if(entry.getValue().equals("chest")) {
				worldIn.setBlockState(entry.getKey(), ACBlocks.wooden_crate.getDefaultState());
				TileEntityCrate crate = (TileEntityCrate)worldIn.getTileEntity(entry.getKey());
				if(crate != null)
					crate.setLootTable(getLootTable(num), worldIn.rand.nextLong());
			} else if(entry.getValue().equals("remnant")) {
				worldIn.setBlockToAir(entry.getKey());
				EntityRemnant remnant = new EntityRemnant(worldIn);
				remnant.setLocationAndAngles(entry.getKey().getX() + 0.5D, entry.getKey().getY(), entry.getKey().getZ() + 0.5D, 0.0F, 0.0F);
				remnant.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(remnant)), (IEntityLivingData)null);
				int profession = 0;
				switch(num) {
				case 0:
					profession = rand.nextBoolean() ? 4 : 0;
					break;
				case 1:
					profession = rand.nextInt(3) == 0 ? 6 : 3;
					break;
				case 2:
					profession = 2;
					break;
				case 4:
					profession = 4;
					break;
				case 5:
					profession = rand.nextBoolean() ? 5 : 0;
					break;
				case 6:
					profession = 1;
					break;
				default:
					break;
				}
				remnant.setProfession(profession);
				worldIn.spawnEntity(remnant);
			} else if(entry.getValue().startsWith("crystal")) {
				if(rand.nextBoolean()) {
					if(rand.nextInt(100) == 0)
						worldIn.setBlockState(entry.getKey(), ACBlocks.dreadlands_infused_powerstone.getDefaultState());
					else
						worldIn.setBlockState(entry.getKey(), getCrystalCluster(rand));
				} else worldIn.setBlockToAir(entry.getKey());
			} else if(entry.getValue().equals("pedestal"))
				worldIn.setBlockState(entry.getKey(), getPedestal(rand));

		return true;
	}

	private void swap(int a, int b) {
		int temp1 = a, temp2 = b;
		a = temp2;
		b = temp1;
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

	private ResourceLocation getLootTable(int num) {
		switch(num) {
		case 1:
			return ACLoot.CHEST_OMOTHOL_BLACKSMITH;
		case 4:
			return ACLoot.CHEST_OMOTHOL_FARMHOUSE;
		case 5:
			return ACLoot.CHEST_OMOTHOL_HOUSE;
		case 6:
			return ACLoot.CHEST_OMOTHOL_LIBRARY;
		default:
			return ACLoot.CHEST_OMOTHOL_HOUSE;
		}
	}

	private IBlockState getPedestal(Random rand) {
		switch(rand.nextInt(4)) {
		case 0:
			return ACBlocks.overworld_energy_pedestal.getDefaultState();
		case 1:
			return ACBlocks.abyssal_wasteland_energy_pedestal.getDefaultState();
		case 2:
			return ACBlocks.dreadlands_energy_pedestal.getDefaultState();
		case 3:
			return ACBlocks.omothol_energy_pedestal.getDefaultState();
		default:
			return getPedestal(rand);
		}
	}

	private IBlockState getCrystalCluster(Random rand) {
		switch(rand.nextInt(ACLib.crystalNames.length)) {
		case 0:
			return ACBlocks.iron_crystal_cluster.getDefaultState();
		case 1:
			return ACBlocks.gold_crystal_cluster.getDefaultState();
		case 2:
			return ACBlocks.sulfur_crystal_cluster.getDefaultState();
		case 3:
			return ACBlocks.carbon_crystal_cluster.getDefaultState();
		case 4:
			return ACBlocks.oxygen_crystal_cluster.getDefaultState();
		case 5:
			return ACBlocks.hydrogen_crystal_cluster.getDefaultState();
		case 6:
			return ACBlocks.nitrogen_crystal_cluster.getDefaultState();
		case 7:
			return ACBlocks.phosphorus_crystal_cluster.getDefaultState();
		case 8:
			return ACBlocks.potassium_crystal_cluster.getDefaultState();
		case 9:
			return ACBlocks.nitrate_crystal_cluster.getDefaultState();
		case 10:
			return ACBlocks.methane_crystal_cluster.getDefaultState();
		case 11:
			return ACBlocks.redstone_crystal_cluster.getDefaultState();
		case 12:
			return ACBlocks.abyssalnite_crystal_cluster.getDefaultState();
		case 13:
			return ACBlocks.coralium_crystal_cluster.getDefaultState();
		case 14:
			return ACBlocks.dreadium_crystal_cluster.getDefaultState();
		case 15:
			return ACBlocks.blaze_crystal_cluster.getDefaultState();
		case 16:
			return ACBlocks.tin_crystal_cluster.getDefaultState();
		case 17:
			return ACBlocks.copper_crystal_cluster.getDefaultState();
		case 18:
			return ACBlocks.silicon_crystal_cluster.getDefaultState();
		case 19:
			return ACBlocks.magnesium_crystal_cluster.getDefaultState();
		case 20:
			return ACBlocks.aluminium_crystal_cluster.getDefaultState();
		case 21:
			return ACBlocks.silica_crystal_cluster.getDefaultState();
		case 22:
			return ACBlocks.alumina_crystal_cluster.getDefaultState();
		case 23:
			return ACBlocks.magnesia_crystal_cluster.getDefaultState();
		case 24:
			return ACBlocks.zinc_crystal_cluster.getDefaultState();
		case 25:
			return ACBlocks.calcium_crystal_cluster.getDefaultState();
		case 26:
			return ACBlocks.beryllium_crystal_cluster.getDefaultState();
		case 27:
			return ACBlocks.beryl_crystal_cluster.getDefaultState();
		default:
			return getCrystalCluster(rand);
		}
	}
}
