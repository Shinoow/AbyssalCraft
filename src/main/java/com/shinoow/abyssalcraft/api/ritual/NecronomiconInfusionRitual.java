/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * A Necronomicon Infusion Ritual
 * @author shinoow
 *
 * @since 1.4
 */
public class NecronomiconInfusionRitual extends NecronomiconCreationRitual {

	private ItemStack sacrifice;

	/**
	 * A Necronomicon Infusion Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param remnantHelp If Remnants can aid you when performing the ritual
	 * @param item The Item given from the ritual
	 * @param sacrifice Item to upgrade
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconInfusionRitual(String unlocalizedName, int bookType, int dimension, boolean remnantHelp, ItemStack item,
			ItemStack sacrifice, ItemStack...offerings) {
		super(unlocalizedName, bookType, dimension, remnantHelp, item, offerings);
		this.sacrifice = sacrifice;
	}

	/**
	 * A Necronomicon Infusion Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param item The Item given from the ritual
	 * @param sacrifice Item to upgrade
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconInfusionRitual(String unlocalizedName, int bookType, int dimension, ItemStack item,
			ItemStack sacrifice, ItemStack...offerings) {
		this(unlocalizedName, bookType, dimension, false, item, sacrifice, offerings);

	}

	/**
	 * A Necronomicon Infusion Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param item The Item given from the ritual
	 * @param sacrifice Item to upgrade
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconInfusionRitual(String unlocalizedName, int bookType, ItemStack item, ItemStack sacrifice, ItemStack...offerings) {
		this(unlocalizedName, bookType, -1, item, sacrifice, offerings);
	}

	/**
	 * Getter for the sacrifice
	 * @return A ItemStack representing the item to be infused
	 */
	public ItemStack getSacrifice(){
		return sacrifice;
	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {

		TileEntity altar = world.getTileEntity(x, y, z);

		NBTTagCompound compound = new NBTTagCompound();
		altar.writeToNBT(compound);
		NBTTagCompound nbtItem = compound.getCompoundTag("Item");

		return RitualRegistry.instance().areStacksEqual(ItemStack.loadItemStackFromNBT(nbtItem), sacrifice);
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player){
		if(canCompleteRitual(world, x, y, z, player)) super.completeRitualServer(world, x, y, z, player);
	}

	@Override
	protected void completeRitualClient(World world, int x, int y, int z, EntityPlayer player){
		if(canCompleteRitual(world, x, y, z, player)) super.completeRitualClient(world, x, y, z, player);
	}
}
