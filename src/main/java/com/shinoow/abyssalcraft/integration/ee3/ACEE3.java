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
package com.shinoow.abyssalcraft.integration.ee3;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.api.ritual.*;

public class ACEE3 implements IACPlugin {

	@Override
	public String getModName() {

		return "Equivalent Exchange 3";
	}

	@Override
	public void preInit() {

		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Darkstone, 1);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Darkstone_cobble, 1);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.abystone, 1);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.cstone, 16);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.dreadstone, 1);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.abydreadstone, 1);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.omotholstone, 1);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.ethaxium, 16);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Darkgrass, 1);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.dreadgrass, 1);

		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.cbrick, 16);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.ethaxium_brick, 16);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Coralium, 32);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Corflesh, 32);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Corbone, 80);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.dreadfragment, 48);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.omotholFlesh, 64);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.eldritchScale, 24);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.cthulhuCoin, 1028);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.elderCoin, 1028);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.jzaharCoin, 1028);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Cpearl, 262);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.shadowfragment, 16);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.abyingot, 3072);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Cingot, 4096);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.dreadiumingot, 5120);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.abychunk, 3072);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.dreadchunk, 6144);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.Dreadshard, 3072);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.EoA, 32768);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.PSDL, 32768);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.dreadKey, 251904);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.portalPlacerJzh, 251904);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.CFluid, 32);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antifluid, 32);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiBone, 48);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.friedegg, 32);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiBeef, 24);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiChicken, 24);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiFlesh, 24);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiCorflesh, 32);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiCorbone, 80);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiSpider_eye, 128);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(AbyssalCraft.antiPork, 24);

		for(String name :OreDictionary.getOreNames())
			if(name.startsWith("crystal")){
				List<ItemStack> ores = OreDictionary.getOres(name);
				Iterator iter = ores.iterator();
				while(iter.hasNext())
					EnergyValueRegistryProxy.addPreAssignedEnergyValue(iter.next(), 1024);
			}
	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {
		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual instanceof NecronomiconCreationRitual)
				RecipeRegistryProxy.addRecipe(((NecronomiconCreationRitual) ritual).getItem(), getInputs(ritual));
	}

	private List getInputs(NecronomiconRitual ritual){
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.addAll(Arrays.asList(ritual.getOfferings()));
		if(ritual instanceof NecronomiconInfusionRitual)
			inputs.add(((NecronomiconInfusionRitual) ritual).getSacrifice());
		return inputs;
	}
}