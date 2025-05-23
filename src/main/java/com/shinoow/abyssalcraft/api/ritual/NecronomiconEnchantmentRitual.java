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

import java.util.Map;

import com.shinoow.abyssalcraft.api.block.IRitualAltar;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * A Necronomicon Enchantment Ritual
 * @author shinoow
 *
 * @since 1.7.5
 */
public class NecronomiconEnchantmentRitual extends NecronomiconRitual {

	private EnchantmentData enchantment;

	/**
	 * A Necronomicon Creation Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param requiresSacrifice If the ritual requires a living sacrifice
	 * @param enchantment EnchantmentData for the Enchantment applied through the ritual
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconEnchantmentRitual(String unlocalizedName, int bookType, int dimension, float requiredEnergy, boolean requiresSacrifice, EnchantmentData enchantment, Object...offerings) {
		super(unlocalizedName, bookType, dimension, requiredEnergy, requiresSacrifice, offerings);
		this.enchantment = enchantment;
		setRitualParticle(EnumRitualParticle.GLYPHS);
	}

	/**
	 * A Necronomicon Creation Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param enchantment EnchantmentData for the Enchantment applied through the ritual
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconEnchantmentRitual(String unlocalizedName, int bookType, int dimension, float requiredEnergy, EnchantmentData enchantment, Object...offerings) {
		this(unlocalizedName, bookType, dimension, requiredEnergy, false, enchantment, offerings);
	}

	/**
	 * A Necronomicon Creation Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param requiredEnergy Amount of Potential Energy required to perform
	 * @param enchantment EnchantmentData for the Enchantment applied through the ritual
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconEnchantmentRitual(String unlocalizedName, int bookType, float requiredEnergy, EnchantmentData enchantment, Object...offerings) {
		this(unlocalizedName, bookType, OreDictionary.WILDCARD_VALUE, requiredEnergy, enchantment, offerings);
	}

	@Override
	public boolean requiresItemSacrifice(){
		return true;
	}

	/**
	 * Getter for the EnchantmentData
	 * @return EnchantmentData for the Enchantment applied through the ritual
	 */
	public EnchantmentData getEnchantment(){
		return enchantment;
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		TileEntity altar = world.getTileEntity(pos);

		ItemStack stack = ItemStack.EMPTY;
		if(altar instanceof IRitualAltar)
			stack = ((IRitualAltar) altar).getItem();

		return canEnchant(stack);
	}

	private boolean canEnchant(ItemStack stack){
		if(stack.isEmpty()) return false;
		if(stack.isItemEnchanted()){
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
			for(Enchantment ench : enchantments.keySet())
				if(!ench.isCompatibleWith(enchantment.enchantment))
					return false;
			return enchantment.enchantment.canApply(stack);
		}
		return enchantment.enchantment.canApply(stack);
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		TileEntity altar = world.getTileEntity(pos);

		ItemStack stack = ItemStack.EMPTY;
		if(altar instanceof IRitualAltar)
			stack = ((IRitualAltar) altar).getItem();

		if(canEnchant(stack)){
			stack.addEnchantment(enchantment.enchantment, enchantment.enchantmentLevel);
			((IRitualAltar) altar).setItem(stack);
		}
	}
}
