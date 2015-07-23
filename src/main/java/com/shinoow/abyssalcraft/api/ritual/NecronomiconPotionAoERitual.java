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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.ACPotions;

/**
 * A Necronomicon Area-of-Effect Ritual
 * @author shinoow
 *
 * @since 1.4
 */
public class NecronomiconPotionAoERitual extends NecronomiconRitual {

	private Object potion;

	/**
	 * A Necronomicon Potion Area-of-Effect Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param remnantHelp If Remnants can aid you when performing the ritual
	 * @param potions Either a Potion effect or a Potion ID (will last for 20 seconds)
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconPotionAoERitual(String unlocalizedName, int bookType, int dimension, boolean remnantHelp, Object potion, ItemStack...offerings) {
		super(unlocalizedName, bookType, dimension, remnantHelp, offerings);
		this.potion = potion;
	}

	/**
	 * A Necronomicon Potion Area-of-Effect Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param potions Either a Potion effect or a Potion ID (will last for 20 seconds)
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconPotionAoERitual(String unlocalizedName, int bookType, int dimension, Object potion, ItemStack...offerings) {
		this(unlocalizedName, bookType, dimension, false, potion, offerings);

	}

	/**
	 * A Necronomicon Potion Area-of-Effect Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param potions Either a Potion effect or a Potion ID (will last for 20 seconds)
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconPotionAoERitual(String unlocalizedName, int bookType, Object potion, ItemStack...offerings) {
		this(unlocalizedName, bookType, -1, potion, offerings);

	}

	/**
	 * A getter for the Potion Effect
	 * @return Either a Potion Effect or null
	 */
	public Potion getPotionEffect(){
		if(potion instanceof Potion)
			return (Potion) potion;
		if(potion instanceof Integer)
			return Potion.potionTypes[(int) potion];
		return null;
	}

	private boolean isEntityImmune(Potion potion, Entity entity){
		boolean result = false;
		try {
			Class utilClass = Class.forName("com.shinoow.abyssalcraft.common.util.EntityUtil");

			result = potion == ACPotions.Coralium_plague && (Boolean)utilClass.getDeclaredMethod("isEntityCoralium", EntityLivingBase.class).invoke(null, (EntityLivingBase)entity) ||
					potion == ACPotions.Dread_plague && (Boolean)utilClass.getDeclaredMethod("isEntityDread", EntityLivingBase.class).invoke(null, (EntityLivingBase)entity) ||
					potion == ACPotions.Antimatter && (Boolean)utilClass.getDeclaredMethod("isEntityAnti", EntityLivingBase.class).invoke(null, (EntityLivingBase)entity);

		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {

		return true;
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player){

		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z).expand(16, 3, 16));

		if(!entities.isEmpty())
			for(Entity entity : entities)
				if(entity instanceof EntityLiving && !entity.isDead)
					if(!isEntityImmune(getPotionEffect(), entity))
						((EntityLiving)entity).addPotionEffect(new PotionEffect(getPotionEffect().id, 400));
	}

	@Override
	protected void completeRitualClient(World world, int x, int y, int z, EntityPlayer player){}
}
