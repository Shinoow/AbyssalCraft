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
package com.shinoow.abyssalcraft.api.ritual;

import java.util.Arrays;
import java.util.List;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * A Necronomicon Transformation Ritual
 * @author shinoow
 *
 * @since 1.31.0
 */
public class NecronomiconTransformationRitual extends NecronomiconRitual {

	private ItemStack output, input;

	/**
	 * A Necronomicon Transformation Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be performed
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param requiresSacrifice If the ritual requires a living sacrifice
	 * @param input The input to be transformed
	 * @param output The output The output to replace the input
	 */
	public NecronomiconTransformationRitual(String unlocalizedName, int bookType, int dimension, float requiredEnergy, boolean requiresSacrifice, ItemStack input, ItemStack output) {
		super(unlocalizedName, bookType, dimension, requiredEnergy, requiresSacrifice, APIUtils.makeArrayOf(input, 8));
		this.input = input;
		this.output = output;
		setRitualParticle(EnumRitualParticle.PE_STREAM);
	}

	/**
	 * A Necronomicon Transformation Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param input The input to be transformed
	 * @param output The output The output to replace the input
	 */
	public NecronomiconTransformationRitual(String unlocalizedName, int bookType, int dimension, float requiredEnergy, ItemStack input, ItemStack output) {
		this(unlocalizedName, bookType, dimension, requiredEnergy, false, input, output);
	}

	/**
	 * A Necronomicon Transformation Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param input The input to be transformed
	 * @param output The output The output to replace the input
	 */
	public NecronomiconTransformationRitual(String unlocalizedName, int bookType, float requiredEnergy, ItemStack input, ItemStack output) {
		this(unlocalizedName, bookType, OreDictionary.WILDCARD_VALUE, requiredEnergy, input, output);
	}

	/**
	 * Creates a combined multi-dimensional array of the input and output ItemStacks
	 */
	public Object[] getCombinedContent() {
		return APIUtils.makeArrayOf(new ItemStack[] {input, output}, 8);
	}

	/**
	 * Returns the input ItemStack
	 */
	public ItemStack getInput() {
		return input;
	}

	/**
	 * Returns the output ItemStack
	 */
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		return true;
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player){

		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 1, pos.getZ(), false));

		List<BlockPos> PEDESTAL_POSITIONS = Arrays.asList(
				new BlockPos(-3, 0, 0), new BlockPos(0, 0, -3),
				new BlockPos(3, 0, 0), new BlockPos(0, 0, 3),
				new BlockPos(-2, 0, 2), new BlockPos(-2, 0, -2),
				new BlockPos(2, 0, 2), new BlockPos(2, 0, -2)
				);

		// Takes each pedestal and placed the output Item on them
		for(BlockPos pos1 : PEDESTAL_POSITIONS) {
			BlockPos pedPos = pos.add(pos1);
			TileEntity pedestal = world.getTileEntity(pos.add(pos1));
			if(pedestal != null) {
				NBTTagCompound compound = new NBTTagCompound();
				NBTTagCompound newItem = new NBTTagCompound();
				pedestal.writeToNBT(compound);
				output.writeToNBT(newItem);
				compound.setTag("Item", newItem);
				pedestal.readFromNBT(compound);
				world.notifyBlockUpdate(pedPos, world.getBlockState(pedPos), world.getBlockState(pedPos), 2);
			}
		}
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player){}
}