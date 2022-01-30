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
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
	public static boolean tryAltar(World world, BlockPos pos, int bookType){
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
						if(RitualRegistry.instance().sameBookType(world.provider.getDimension(), book.get())){
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
