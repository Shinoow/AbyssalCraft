/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.props;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.common.util.ACLogger;

public class ReputationProps implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "DeityReputation";

	private int cthulhuRep, hasturRep, jzaharRep, azathothRep, nyarlathotepRep, shubniggurathRep, yogsothothRep;

	public ReputationProps(EntityPlayer player) {
	}

	/**
	 * Used to register these extended properties for the player during EntityConstructing event
	 */
	public static final void register(EntityPlayer player) {
		//		player.registerExtendedProperties(ReputationProps.EXT_PROP_NAME, new ReputationProps(player));
	}

	/**
	 * Returns ReputationProps properties for player
	 */
	public static final ReputationProps get(EntityPlayer player) {
		//		return (ReputationProps) player.getExtendedProperties(EXT_PROP_NAME);
		return null;
	}

	/**
	 * Copies additional player data from the given ReputationProps instance
	 * Avoids NBT disk I/O overhead when cloning a player after respawn
	 */
	public void copy(ReputationProps props) {
		cthulhuRep = props.cthulhuRep;
		hasturRep = props.hasturRep;
		jzaharRep = props.jzaharRep;
		azathothRep = props.azathothRep;
		nyarlathotepRep = props.nyarlathotepRep;
		shubniggurathRep = props.shubniggurathRep;
		yogsothothRep = props.yogsothothRep;
	}

	@Override
	public final void saveNBTData(NBTTagCompound compound) {

		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger(DeityType.CTHULHU.getName(), cthulhuRep);
		properties.setInteger(DeityType.HASTUR.getName(), hasturRep);
		properties.setInteger(DeityType.JZAHAR.getName(), jzaharRep);
		properties.setInteger(DeityType.AZATHOTH.getName(), azathothRep);
		properties.setInteger(DeityType.NYARLATHOTEP.getName(), nyarlathotepRep);
		properties.setInteger(DeityType.SHUBNIGGURATH.getName(), shubniggurathRep);
		properties.setInteger(DeityType.YOGSOTHOTH.getName(), yogsothothRep);


		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public final void loadNBTData(NBTTagCompound compound) {

		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

		if(properties != null){
			if(properties.hasKey(DeityType.CTHULHU.getName()))
				cthulhuRep = properties.getInteger(DeityType.CTHULHU.getName());
			if(properties.hasKey(DeityType.HASTUR.getName()))
				hasturRep = properties.getInteger(DeityType.HASTUR.getName());
			if(properties.hasKey(DeityType.JZAHAR.getName()))
				jzaharRep = properties.getInteger(DeityType.JZAHAR.getName());
			if(properties.hasKey(DeityType.AZATHOTH.getName()))
				azathothRep = properties.getInteger(DeityType.AZATHOTH.getName());
			if(properties.hasKey(DeityType.NYARLATHOTEP.getName()))
				nyarlathotepRep = properties.getInteger(DeityType.NYARLATHOTEP.getName());
			if(properties.hasKey(DeityType.SHUBNIGGURATH.getName()))
				shubniggurathRep = properties.getInteger(DeityType.SHUBNIGGURATH.getName());
			if(properties.hasKey(DeityType.YOGSOTHOTH.getName()))
				yogsothothRep = properties.getInteger(DeityType.YOGSOTHOTH.getName());
		}
	}

	@Override
	public void init(Entity entity, World world) {}


	public void onUpdate() {

	}

	/**
	 * Increases reputation for a deity
	 * @param deity Deity in question
	 * @param num Amount of reputation to increase
	 */
	public final void increaseRep(DeityType deity, int num){
		ACLogger.info("%s liked that action, reputation increased by %d", deity.getName(), num);
		switch(deity){
		case AZATHOTH:
			azathothRep += num;
			break;
		case CTHULHU:
			cthulhuRep += num;
			break;
		case HASTUR:
			hasturRep += num;
			break;
		case JZAHAR:
			jzaharRep += num;
			break;
		case NYARLATHOTEP:
			nyarlathotepRep += num;
			break;
		case SHUBNIGGURATH:
			shubniggurathRep += num;
			break;
		case YOGSOTHOTH:
			yogsothothRep += num;
			break;
		default:
			break;
		}
	}

	/**
	 * Decreases reputation for a deity
	 * @param deity Deity in question
	 * @param num Amount of reputation to decrease
	 */
	public final void decreaseRep(DeityType deity, int num){
		ACLogger.info("%s disliked that action, reputation decreased by %d", deity.getName(), num);
		switch(deity){
		case AZATHOTH:
			azathothRep -= num;
			if(azathothRep < 0) azathothRep = 0;
			break;
		case CTHULHU:
			cthulhuRep -= num;
			if(cthulhuRep < 0) cthulhuRep = 0;
			break;
		case HASTUR:
			hasturRep -= num;
			if(hasturRep < 0) hasturRep = 0;
			break;
		case JZAHAR:
			jzaharRep -= num;
			if(jzaharRep < 0) jzaharRep = 0;
			break;
		case NYARLATHOTEP:
			nyarlathotepRep -= num;
			if(nyarlathotepRep < 0) nyarlathotepRep = 0;
			break;
		case SHUBNIGGURATH:
			shubniggurathRep -= num;
			if(shubniggurathRep < 0) shubniggurathRep = 0;
			break;
		case YOGSOTHOTH:
			yogsothothRep -= num;
			if(yogsothothRep < 0) yogsothothRep = 0;
			break;
		default:
			break;
		}
	}

	/**
	 * Resets the reputation of a deity
	 * @param deity Deity in question
	 */
	public void resetRep(DeityType deity){
		switch(deity){
		case AZATHOTH:
			azathothRep = 0;
			break;
		case CTHULHU:
			cthulhuRep = 0;
			break;
		case HASTUR:
			hasturRep = 0;
			break;
		case JZAHAR:
			jzaharRep = 0;
			break;
		case NYARLATHOTEP:
			nyarlathotepRep = 0;
			break;
		case SHUBNIGGURATH:
			shubniggurathRep = 0;
			break;
		case YOGSOTHOTH:
			yogsothothRep = 0;
			break;
		default:
			break;
		}
	}

	/**
	 * Resets the reputation of all deities
	 */
	public void resetAllRep(){
		for(DeityType deity : DeityType.values())
			resetRep(deity);
	}

	/**
	 * Gets the current reputation for a deity
	 * @param deity Deity in question
	 */
	public final int getCurrentRep(DeityType deity){
		switch(deity){
		case AZATHOTH:
			return azathothRep;
		case CTHULHU:
			return cthulhuRep;
		case HASTUR:
			return hasturRep;
		case JZAHAR:
			return jzaharRep;
		case NYARLATHOTEP:
			return nyarlathotepRep;
		case SHUBNIGGURATH:
			return shubniggurathRep;
		case YOGSOTHOTH:
			return yogsothothRep;
		default:
			return 0;
		}
	}
}
