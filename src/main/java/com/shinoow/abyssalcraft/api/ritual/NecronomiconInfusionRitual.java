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

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.block.IRitualAltar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * A Necronomicon Infusion Ritual
 * @author shinoow
 *
 * @since 1.4
 */
public class NecronomiconInfusionRitual extends NecronomiconCreationRitual {

	private String[] tags;

	/**
	 * A Necronomicon Infusion Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param requiresSacrifice If the ritual requires a living sacrifice
	 * @param item The Item given from the ritual
	 * @param sacrifice Item to upgrade
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconInfusionRitual(String unlocalizedName, int bookType, int dimension, float requiredEnergy,
			boolean requiresSacrifice, ItemStack item, Object sacrifice, Object...offerings) {
		super(unlocalizedName, bookType, dimension, requiredEnergy, requiresSacrifice, item, offerings);
		this.sacrifice = sacrifice;
		setRitualParticle(EnumRitualParticle.ITEM_SMOKE_COMBO);
	}

	/**
	 * A Necronomicon Infusion Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param item The Item given from the ritual
	 * @param sacrifice Item to upgrade
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconInfusionRitual(String unlocalizedName, int bookType, int dimension, float requiredEnergy,
			ItemStack item, Object sacrifice, Object...offerings) {
		this(unlocalizedName, bookType, dimension, requiredEnergy, false, item, sacrifice, offerings);

	}

	/**
	 * A Necronomicon Infusion Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param item The Item given from the ritual
	 * @param sacrifice Item to upgrade
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconInfusionRitual(String unlocalizedName, int bookType, float requiredEnergy, ItemStack item, Object sacrifice, Object...offerings) {
		this(unlocalizedName, bookType, OreDictionary.WILDCARD_VALUE, requiredEnergy, item, sacrifice, offerings);
	}

	public NecronomiconInfusionRitual setTags(String... strings){
		tags = strings;
		return this;
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		TileEntity altar = world.getTileEntity(pos);

		ItemStack stack = ItemStack.EMPTY;
		if(altar instanceof IRitualAltar)
			stack = ((IRitualAltar) altar).getItem();

		return APIUtils.areObjectsEqual(stack, sacrifice, false);
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player){
		if(canCompleteRitual(world, pos, player)){
			NBTTagCompound data = new NBTTagCompound();
			if(tags != null && tags.length > 0){
				TileEntity altarTile = world.getTileEntity(pos);

				if(altarTile instanceof IRitualAltar) {
					IRitualAltar altar = (IRitualAltar) altarTile;

					ItemStack stack = altar.getItem();
					if(!stack.hasTagCompound())
						stack.setTagCompound(new NBTTagCompound());
					for(String s : tags)
						if(stack.getTagCompound().hasKey(s))
							data.setTag(s, stack.getTagCompound().getTag(s));
				}

			}
			super.completeRitualServer(world, pos, player);
			if(!data.isEmpty()){
				TileEntity altarTile = world.getTileEntity(pos);

				if(altarTile instanceof IRitualAltar) {
					IRitualAltar altar = (IRitualAltar) altarTile;

					ItemStack stack = altar.getItem();
					if(!stack.hasTagCompound())
						stack.setTagCompound(new NBTTagCompound());
					for(String e : tags)
						if(data.hasKey(e))
							stack.getTagCompound().setTag(e, data.getTag(e));
					altar.setItem(stack);
				}
			}
		}
	}


	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player){}
}
