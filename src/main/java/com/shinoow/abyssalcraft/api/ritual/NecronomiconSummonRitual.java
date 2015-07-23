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

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * A Necronomicon Summoning Ritual
 * @author shinoow
 *
 * @since 1.4
 */
public class NecronomiconSummonRitual extends NecronomiconRitual {

	private Class<? extends EntityLivingBase> entity;

	/**
	 * A Necronomicon Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param remnantHelp If Remnants can aid you when performing the ritual
	 * @param entity Class representing the entity this ritual summons
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconSummonRitual(String unlocalizedName, int bookType, int dimension, boolean remnantHelp, Class<? extends EntityLivingBase> entity, ItemStack...offerings) {
		super(unlocalizedName, bookType, dimension, remnantHelp, offerings);
		this.entity = entity;
	}

	/**
	 * A Necronomicon Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param dimension Dimension where the ritual can be peformed
	 * @param entity Class representing the entity this ritual summons
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconSummonRitual(String unlocalizedName, int bookType, int dimension, Class<? extends EntityLivingBase> entity, ItemStack...offerings) {
		this(unlocalizedName, bookType, dimension, false, entity, offerings);
	}

	/**
	 * A Necronomicon Ritual
	 * @param unlocalizedName A string representing the ritual name
	 * @param bookType Necronomicon book type required
	 * @param entity Class representing the entity this ritual summons
	 * @param offerings Components used to perform the ritual, are consumed afterwards
	 */
	public NecronomiconSummonRitual(String unlocalizedName, int bookType, Class<? extends EntityLivingBase> entity, ItemStack...offerings) {
		this(unlocalizedName, bookType, -1, entity, offerings);
	}

	/**
	 * Getter for the entity
	 * @return A Class that represents the entity this ritual summons
	 */
	public Class<? extends EntityLivingBase> getEntity(){
		return entity;
	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {

		return true;
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player){

		EntityLivingBase entityliving = null;
		try {
			entityliving = entity.getConstructor(World.class).newInstance(world);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if(entityliving != null){
			entityliving.setLocationAndAngles(x, y + 1, z, entityliving.rotationYaw, entityliving.rotationPitch);
			((EntityLiving) entityliving).onSpawnWithEgg((IEntityLivingData)null);
			world.spawnEntityInWorld(entityliving);
		}
	}

	@Override
	protected void completeRitualClient(World world, int x, int y, int z, EntityPlayer player){
		EntityLivingBase entityliving = null;
		try {
			entityliving = entity.getConstructor(World.class).newInstance(world);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if(entityliving == null) player.addChatMessage(new ChatComponentText("The Entity could not be summoned."));

	}
}