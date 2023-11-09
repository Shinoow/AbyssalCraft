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
package com.shinoow.abyssalcraft.lib.util;

import java.util.*;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualPedestal;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * Utility class used for assembling Ritual grounds.
 * @author shinoow
 *
 */
public class RitualUtil {

	private static Map<IBlockState, Integer> ritualBlocks = new HashMap<>();
	private static Map<IBlockState, Integer> altarMeta = new HashMap<>();

	public static final List<BlockPos> PEDESTAL_POSITIONS = Arrays.asList(
			new BlockPos(-3, 0, 0), new BlockPos(0, 0, -3),
			new BlockPos(3, 0, 0), new BlockPos(0, 0, 3),
			new BlockPos(-2, 0, 2), new BlockPos(-2, 0, -2),
			new BlockPos(2, 0, 2), new BlockPos(2, 0, -2)
			);

	public static void addBlocks(){
		ritualBlocks.put(Blocks.COBBLESTONE.getDefaultState(), 0);
		ritualBlocks.put(ACBlocks.cobblestone.getStateFromMeta(0), 0);
		ritualBlocks.put(ACBlocks.cobblestone.getStateFromMeta(1), 1);
		ritualBlocks.put(ACBlocks.cobblestone.getStateFromMeta(4), 1);
		ritualBlocks.put(ACBlocks.cobblestone.getStateFromMeta(2), 2);
		ritualBlocks.put(ACBlocks.cobblestone.getStateFromMeta(3), 2);
		ritualBlocks.put(ACBlocks.ethaxium_brick.getDefaultState(), 3);
		ritualBlocks.put(ACBlocks.dark_ethaxium_brick.getDefaultState(), 3);

		altarMeta.put(Blocks.COBBLESTONE.getDefaultState(), 0);
		altarMeta.put(ACBlocks.cobblestone.getStateFromMeta(0), 1);
		altarMeta.put(ACBlocks.cobblestone.getStateFromMeta(1), 2);
		altarMeta.put(ACBlocks.cobblestone.getStateFromMeta(4), 3);
		altarMeta.put(ACBlocks.cobblestone.getStateFromMeta(2), 4);
		altarMeta.put(ACBlocks.cobblestone.getStateFromMeta(3), 5);
		altarMeta.put(ACBlocks.ethaxium_brick.getDefaultState(), 6);
		altarMeta.put(ACBlocks.dark_ethaxium_brick.getDefaultState(), 7);
	}

	/**
	 * Checks if an altar can be created
	 * @param world World object
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @param bookType Level of the current Necronomicon held
	 * @return True if a Ritual Altar can be constructed, otherwise false
	 */
	public static boolean tryAltar(World world, BlockPos pos, int bookType){
		IBlockState ritualBlock = world.getBlockState(pos);
		int x = 0;
		int y = 0;
		int z = 0;
		if(ritualBlock != null && ritualBlocks.containsKey(ritualBlock))
			if(bookType >= ritualBlocks.get(ritualBlock))
				if(world.getBlockState(pos.add(x -3, y, z)) == ritualBlock &&
				world.getBlockState(pos.add(x, y, z -3)) == ritualBlock &&
				world.getBlockState(pos.add(x + 3, y, z)) == ritualBlock &&
				world.getBlockState(pos.add(x, y, z + 3)) == ritualBlock &&
				world.getBlockState(pos.add(x -2, y,z + 2)) == ritualBlock &&
				world.getBlockState(pos.add(x -2, y, z -2)) == ritualBlock &&
				world.getBlockState(pos.add(x + 2, y, z + 2)) == ritualBlock &&
				world.getBlockState(pos.add(x + 2, y, z -2)) == ritualBlock)
					if(!world.isBlockFullCube(pos.add(x -3, y, z -1)) && !world.isBlockFullCube(pos.add(x -3, y, z + 1)) &&
							!world.isBlockFullCube(pos.add(x -4, y, z)) && !world.isBlockFullCube(pos.add(x -4, y, z -1)) &&
							!world.isBlockFullCube(pos.add(x -4, y, z + 1)) && !world.isBlockFullCube(pos.add(x -3, y, z -2)) &&
							!world.isBlockFullCube(pos.add(x -3, y, z -3)) && !world.isBlockFullCube(pos.add(x -2, y, z -3)) &&
							!world.isBlockFullCube(pos.add(x -1, y, z -3)) && !world.isBlockFullCube(pos.add(x -1, y, z -4)) &&
							!world.isBlockFullCube(pos.add(x, y, z -4)) && !world.isBlockFullCube(pos.add(x + 1, y, z -4)) &&
							!world.isBlockFullCube(pos.add(x + 1, y, z -3)) && !world.isBlockFullCube(pos.add(x + 2, y, z -3)) &&
							!world.isBlockFullCube(pos.add(x + 3, y, z -3)) && !world.isBlockFullCube(pos.add(x + 3, y, z -2)) &&
							!world.isBlockFullCube(pos.add(x + 3, y, z -1)) && !world.isBlockFullCube(pos.add(x + 4, y, z -1)) &&
							!world.isBlockFullCube(pos.add(x + 4, y, z)) && !world.isBlockFullCube(pos.add(x + 4, y, z + 1)) &&
							!world.isBlockFullCube(pos.add(x + 3, y, z + 1)) && !world.isBlockFullCube(pos.add(x + 3, y, z + 2)) &&
							!world.isBlockFullCube(pos.add(x + 3, y, z + 3)) && !world.isBlockFullCube(pos.add(x + 2, y, z + 3)) &&
							!world.isBlockFullCube(pos.add(x + 1, y, z + 3)) && !world.isBlockFullCube(pos.add(x + 1, y, z + 4)) &&
							!world.isBlockFullCube(pos.add(x, y, z + 4)) && !world.isBlockFullCube(pos.add(x -1, y, z + 4)) &&
							!world.isBlockFullCube(pos.add(x -1, y, z + 3)) && !world.isBlockFullCube(pos.add(x -2, y, z + 3)) &&
							!world.isBlockFullCube(pos.add(x-3, y, z + 3)) && !world.isBlockFullCube(pos.add(x -3, y, z + 2)) &&
							!world.isBlockFullCube(pos.add(x-1, y, z + 0)) && !world.isBlockFullCube(pos.add(x + 1, y, z)) &&
							!world.isBlockFullCube(pos.add(x, y, z -1)) && !world.isBlockFullCube(pos.add(x, y, z + 1)) &&
							!world.isBlockFullCube(pos.add(x-1, y, z + 1)) && !world.isBlockFullCube(pos.add(x -2, y, z)) &&
							!world.isBlockFullCube(pos.add(x-2, y, z)) && !world.isBlockFullCube(pos.add(x -2, y, z -1)) &&
							!world.isBlockFullCube(pos.add(x-1, y, z -1)) && !world.isBlockFullCube(pos.add(x -1, y, z -2)) &&
							!world.isBlockFullCube(pos.add(x, y, z -2)) && !world.isBlockFullCube(pos.add(x + 1, y, z -2)) &&
							!world.isBlockFullCube(pos.add(x + 1, y, z -1)) && !world.isBlockFullCube(pos.add(x + 2, y, z -1)) &&
							!world.isBlockFullCube(pos.add(x + 2, y, z)) && !world.isBlockFullCube(pos.add(x + 2, y, z + 1)) &&
							!world.isBlockFullCube(pos.add(x + 1, y, z + 1)) && !world.isBlockFullCube(pos.add(x + 1, y, z + 2)) &&
							!world.isBlockFullCube(pos.add(x, y, z + 2)) && !world.isBlockFullCube(pos.add(x -1, y, z + 2)))
						if(RitualRegistry.instance().sameBookType(world.provider.getDimension(), ritualBlocks.get(ritualBlock)))
							if(sameChunk(world, pos)) {
								if(!world.isRemote)
									createAltar(world, pos, ritualBlock);
								return true;
							}
		return false;
	}

	/**
	 * Creates the altar
	 * @param world World object
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @param block Ritual Block
	 */
	private static void createAltar(World world, BlockPos pos, IBlockState block){
		if(altarMeta.containsKey(block)){
			int meta = altarMeta.get(block);

			world.destroyBlock(pos, false);
			world.destroyBlock(pos.west(3), false);
			world.destroyBlock(pos.north(3), false);
			world.destroyBlock(pos.east(3), false);
			world.destroyBlock(pos.south(3), false);
			world.destroyBlock(pos.west(2).south(2), false);
			world.destroyBlock(pos.west(2).north(2), false);
			world.destroyBlock(pos.east(2).south(2), false);
			world.destroyBlock(pos.east(2).north(2), false);
			world.setBlockState(pos, ACBlocks.ritual_altar.getStateFromMeta(meta), 2);
			world.setBlockState(pos.west(3), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.west(3))).setAltar(pos);
			world.setBlockState(pos.north(3), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.north(3))).setAltar(pos);
			world.setBlockState(pos.east(3), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.east(3))).setAltar(pos);
			world.setBlockState(pos.south(3), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.south(3))).setAltar(pos);
			world.setBlockState(pos.west(2).south(2), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.west(2).south(2))).setAltar(pos);
			world.setBlockState(pos.west(2).north(2), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.west(2).north(2))).setAltar(pos);
			world.setBlockState(pos.east(2).south(2), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.east(2).south(2))).setAltar(pos);
			world.setBlockState(pos.east(2).north(2), ACBlocks.ritual_pedestal.getStateFromMeta(meta), 2);
			((IRitualPedestal)world.getTileEntity(pos.east(2).north(2))).setAltar(pos);
		}
	}

	/**
	 * Validates all blocks are within the same chunk
	 * @param world World object
	 * @param pos Block Position
	 * @return True if the blocks are in the same chunk, otherwise false with particles on the affected blocks
	 */
	private static boolean sameChunk(World world, BlockPos pos) {
		Chunk chunk = world.getChunk(pos);
		if(PEDESTAL_POSITIONS.stream().map(p -> world.getChunk(pos.add(p))).allMatch(c -> c == chunk))
			return true;

		if(world.isRemote)
			PEDESTAL_POSITIONS.stream()
			.map(p -> pos.add(p))
			.filter(p -> world.getChunk(p) != chunk)
			.forEach(p -> world.spawnParticle(EnumParticleTypes.BARRIER, p.getX()+0.5, p.getY()+1.5, p.getZ()+0.5, 0, 0, 0));

		return false;
	}

	public static void modifyRitualBookType(String name, int bookType){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, bookType, "bookType");
				break;
			}
	}

	public static void modifyRitualDimension(String name, int dimension){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, dimension, "dimension");
				break;
			}
	}

	public static void modifyRitualSacrificeRequirement(String name, boolean requiresSacrifice){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, requiresSacrifice, "requiresSacrifice");
				break;
			}
	}

	public static void modifyRitualEnergyRequirement(String name, float requiredEnergy){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, requiredEnergy, "requiredEnergy");
				break;
			}
	}

	public static void modifyRitualSacrifice(String name, Object sacrifice){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name) && r.getSacrifice() != null){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, sacrifice, "sacrifice");
				break;
			}
	}

	public static void modifyRitualNbtSensitivity(String name, boolean nbtSensitive){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, nbtSensitive, "nbtSensitive");
				break;
			}
	}

	public static void modifyRitualNbtSensitivitySacrifice(String name, boolean nbtSensitiveSacrifice){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, nbtSensitiveSacrifice, "nbtSensitiveSacrifice");
				break;
			}
	}

	public static void modifyRitualOfferings(String name, Object...offerings){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, offerings, "offerings");
				break;
			}
	}

	public static void modifyRitualReplaceOffering(String name, Object original, Object replace, boolean nbt){
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				Object[] offerings = new Object[r.getOfferings().length];
				for(int i = 0; i < offerings.length; i++)
					offerings[i] = APIUtils.areObjectsEqual(APIUtils.convertToStack(original), r.getOfferings()[i], nbt) ? replace : r.getOfferings()[i];
				ReflectionHelper.setPrivateValue(NecronomiconRitual.class, r, offerings, "offerings");
				break;
			}
	}

	public static void modifyRitualParticle(String name, EnumRitualParticle particle) {
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().substring("ac.ritual.".length()).equals(name)){
				r.setRitualParticle(particle);
				break;
			}
	}
}
