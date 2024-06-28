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
package com.shinoow.abyssalcraft.lib.util;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualPedestal;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * Utility class used for assembling Ritual grounds.
 * @author shinoow
 *
 */
public class RitualUtil {

	private static Map<Supplier<IBlockState>, Integer> bookTypeMappings = new HashMap<>();
	private static Map<Supplier<IBlockState>, IBlockState> altars = new HashMap<>();
	private static Map<Supplier<IBlockState>, IBlockState> pedestals = new HashMap<>();

	public static final List<BlockPos> PEDESTAL_POSITIONS = Arrays.asList(
			new BlockPos(-3, 0, 0), new BlockPos(0, 0, -3),
			new BlockPos(3, 0, 0), new BlockPos(0, 0, 3),
			new BlockPos(-2, 0, 2), new BlockPos(-2, 0, -2),
			new BlockPos(2, 0, 2), new BlockPos(2, 0, -2)
			);

	public static void addAltarTransformation(Supplier<IBlockState> state, IBlockState altar, int bookType) {
		altars.put(state, altar);
		bookTypeMappings.put(state, bookType);
	}

	public static void addPedestalTransformation(Supplier<IBlockState> state, IBlockState pedestal, int bookType) {
		pedestals.put(state, pedestal);
		bookTypeMappings.put(state, bookType);
	}

	private static boolean matches(IBlockState state, int bookType) {
		Optional<Integer> book = bookTypeMappings.entrySet().stream()
				.filter(e -> e.getKey().get() == state)
				.map(Entry::getValue)
				.findFirst();

		return book.orElse(-1) == bookType;
	}

	private static IBlockState getAltar(IBlockState state) {
		Optional<IBlockState> altar = altars.entrySet().stream()
				.filter(e -> e.getKey().get() == state)
				.map(Entry::getValue)
				.findFirst();
		return altar.orElse(Blocks.AIR.getDefaultState());
	}

	private static IBlockState getPedestal(IBlockState state) {
		Optional<IBlockState> pedestal = pedestals.entrySet().stream()
				.filter(e -> e.getKey().get() == state)
				.map(Entry::getValue)
				.findFirst();
		return pedestal.orElse(Blocks.AIR.getDefaultState());
	}

	/**
	 * Checks if an altar can be created
	 * @param world World object
	 * @param pos Block Position
	 * @param bookType Level of the current Necronomicon held
	 * @return True if a Ritual Altar can be constructed, otherwise false
	 */
	public static boolean tryAltar(World world, BlockPos pos, int bookType, EntityPlayer player){
		IBlockState ritualBlock = world.getBlockState(pos);

		Optional<Integer> book = bookTypeMappings.entrySet().stream()
				.filter(e -> e.getKey().get() == ritualBlock)
				.map(Entry::getValue)
				.findFirst();

		if(book.isPresent())
			if(bookType >= book.get())
				if(PEDESTAL_POSITIONS.stream().allMatch(p -> matches(world.getBlockState(pos.add(p)), book.get())))
					if(PEDESTAL_POSITIONS.stream().map(p -> pos.add(p)).allMatch(p -> {
						for(MutableBlockPos p1 : BlockPos.getAllInBoxMutable(p.south().west(), p.north().east()))
							if(world.isBlockFullCube(p1) && !p1.equals(p))
								return false;
						return true;
					}))
						if(RitualRegistry.instance().sameBookType(world.provider.getDimension(), book.get()))
							if(sameChunk(world, pos, player)) {
								if(!world.isRemote)
									createAltar(world, pos);
								return true;
							}
		return false;
	}

	/**
	 * Creates the altar
	 * @param world World object
	 * @param pos Block Position
	 */
	private static void createAltar(World world, BlockPos pos){

		IBlockState altar = world.getBlockState(pos);
		world.destroyBlock(pos, false);
		world.setBlockState(pos, getAltar(altar), 2);

		for(BlockPos pos1 : PEDESTAL_POSITIONS) {
			BlockPos pos2 = pos.add(pos1);
			IBlockState pedestal = world.getBlockState(pos2);
			world.destroyBlock(pos2, false);
			world.setBlockState(pos2, getPedestal(pedestal), 2);
			((IRitualPedestal) world.getTileEntity(pos2)).setAltar(pos);
		}
	}

	/**
	 * Validates all blocks are within the same chunk
	 * @param world World object
	 * @param pos Block Position
	 * @return True if the blocks are in the same chunk, otherwise false with particles on the affected blocks
	 */
	private static boolean sameChunk(World world, BlockPos pos, EntityPlayer player) {
		Chunk chunk = world.getChunk(pos);
		if(PEDESTAL_POSITIONS.stream().map(p -> world.getChunk(pos.add(p))).allMatch(c -> c == chunk))
			return true;

		if(world.isRemote) {
			PEDESTAL_POSITIONS.stream()
			.map(p -> pos.add(p))
			.filter(p -> world.getChunk(p) != chunk)
			.forEach(p -> world.spawnParticle(EnumParticleTypes.BARRIER, p.getX()+0.5, p.getY()+1.5, p.getZ()+0.5, 0, 0, 0));
			player.sendStatusMessage(new TextComponentTranslation("message.ritual.notsamechunk"), true);
		}
		return false;
	}

	public static void modifyRitualBookType(String name, int bookType){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, bookType, "bookType");
	}

	public static void modifyRitualDimension(String name, int dimension){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, dimension, "dimension");
	}

	public static void modifyRitualSacrificeRequirement(String name, boolean requiresSacrifice){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, requiresSacrifice, "requiresSacrifice");
	}

	public static void modifyRitualEnergyRequirement(String name, float requiredEnergy){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, requiredEnergy, "requiredEnergy");
	}

	public static void modifyRitualSacrifice(String name, Object sacrifice){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null && ritual.getSacrifice() != null)
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, sacrifice, "sacrifice");
	}

	public static void modifyRitualNbtSensitivity(String name, boolean nbtSensitive){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, nbtSensitive, "nbtSensitive");
	}

	public static void modifyRitualNbtSensitivitySacrifice(String name, boolean nbtSensitiveSacrifice){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, nbtSensitiveSacrifice, "nbtSensitiveSacrifice");
	}

	public static void modifyRitualOfferings(String name, Object...offerings){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, offerings, "offerings");
	}

	public static void modifyRitualReplaceOffering(String name, Object original, Object replace, boolean nbt){
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null) {
			Object[] offerings = new Object[ritual.getOfferings().length];
			for(int i = 0; i < offerings.length; i++)
				offerings[i] = APIUtils.areObjectsEqual(APIUtils.convertToStack(original), ritual.getOfferings()[i], nbt) ? replace : ritual.getOfferings()[i];
			ObfuscationReflectionHelper.setPrivateValue(NecronomiconRitual.class, ritual, offerings, "offerings");
		}
	}

	public static void modifyRitualParticle(String name, EnumRitualParticle particle) {
		NecronomiconRitual ritual = RitualRegistry.instance().getRitual(name);
		if(ritual != null)
			ritual.setRitualParticle(particle);
	}
}
