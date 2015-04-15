/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.integration.thaumcraft;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.ACEntities;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;

public class ACThaumcraftIntegration implements IACPlugin {

	@Override
	public String getModName(){
		return "Thaumcraft";
	}

	@Override
	public void preInit() {

	}

	@Override
	public void init(){
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkstone), new AspectList().add(Aspect.EARTH, 2).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkgrass), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkstone_brick), new AspectList().add(Aspect.EARTH, 2).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Darkstone_cobble), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abyore), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abydreadore), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 1).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadore), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 1).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coraliumore), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.CoraliumInfusedStone), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.AbyCorOre), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.AbyLCorOre), new AspectList().add(Aspect.METAL, 3).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.AbyPCorOre), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abystone), new AspectList().add(Aspect.EARTH, 2).add(Aspect.WATER, 1).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadstone), new AspectList().add(Aspect.EARTH, 2).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abydreadstone), new AspectList().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Cwater), new AspectList().add(Aspect.WATER, 3).add(Aspect.POISON, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.anticwater), new AspectList().add(Aspect.WATER, 3).add(Aspect.VOID, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxium), new AspectList().add(Aspect.SOUL, 2).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxiumbrick), new int[]{0}, new AspectList().add(Aspect.SOUL, 3).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxiumbrick), new int[]{1}, new AspectList().add(Aspect.SOUL, 3).add(Aspect.ELDRITCH, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abyingot), new AspectList().add(Aspect.METAL, 3).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Cingot), new AspectList().add(Aspect.METAL, 3).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.dreadiumingot), new AspectList().add(Aspect.METAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.Coralium), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxiumIngot), new AspectList().add(Aspect.METAL, 1).add(Aspect.SOUL, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.cbrick), new AspectList().add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.ethaxium_brick), new AspectList().add(Aspect.FIRE, 1).add(Aspect.SOUL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.shadowgem), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.DARKNESS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.shadowshard), new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.DARKNESS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.shadowfragment), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.DARKNESS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.necronomicon), new AspectList().add(Aspect.DEATH, 1).add(Aspect.FLESH, 1).add(Aspect.DARKNESS, 1).add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.necronomicon_cor), new AspectList().add(Aspect.DEATH, 2).add(Aspect.FLESH, 2).add(Aspect.DARKNESS, 2).add(Aspect.ELDRITCH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.necronomicon_dre), new AspectList().add(Aspect.DEATH, 3).add(Aspect.FLESH, 3).add(Aspect.DARKNESS, 3).add(Aspect.ELDRITCH, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.necronomicon_omt), new AspectList().add(Aspect.DEATH, 4).add(Aspect.FLESH, 4).add(Aspect.DARKNESS, 4).add(Aspect.ELDRITCH, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.abyssalnomicon), new AspectList().add(Aspect.DEATH, 5).add(Aspect.FLESH, 5).add(Aspect.DARKNESS, 5).add(Aspect.ELDRITCH, 5));

		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalIron), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalSulfur), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalCarbon), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalOxygen), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.AIR, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalHydrogen), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalNitrogen), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.COLD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalPhosphorus), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalPotassium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalNitrate), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.COLD, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalMethane), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalRedstone), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalAbyssalnite), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalCoralium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalDreadium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalBlaze), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalTin), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalCopper), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalSilicon), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalMagnesium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalAluminium), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalSilica), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalAlumina), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalMagnesia), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(AbyssalCraft.crystalZinc), new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.METAL, 1));

		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.depths_ghoul), new AspectList().add(Aspect.DEATH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.evil_pig), new AspectList().add(Aspect.BEAST, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.abyssal_zombie), new AspectList().add(Aspect.DEATH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.jzahar), new AspectList().add(Aspect.DEATH, 5).add(Aspect.DARKNESS, 5).add(Aspect.ELDRITCH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.dreadguard), new AspectList().add(Aspect.DEATH, 5).add(Aspect.FLESH, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.spectral_dragon), new AspectList().add(Aspect.DEATH, 5).add(Aspect.AIR, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.asorah), new AspectList().add(Aspect.DEATH, 5).add(Aspect.AIR, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.shadow_creature), new AspectList().add(Aspect.DARKNESS, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.shadow_monster), new AspectList().add(Aspect.DARKNESS, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.dreadling), new AspectList().add(Aspect.DEATH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.dread_spawn), new AspectList().add(Aspect.DEATH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.demon_pig), new AspectList().add(Aspect.FLESH, 5).add(Aspect.FIRE, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.skeleton_goliath), new AspectList().add(Aspect.DEATH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.spawn_of_chagaroth), new AspectList().add(Aspect.DEATH, 5).add(Aspect.FLESH, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.fist_of_chagaroth), new AspectList().add(Aspect.DEATH, 5).add(Aspect.FLESH, 3));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.chagaroth), new AspectList().add(Aspect.DEATH, 5).add(Aspect.FLESH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.shadow_beast), new AspectList().add(Aspect.DARKNESS, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.sacthoth), new AspectList().add(Aspect.DEATH, 5).add(Aspect.DARKNESS, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.remnant), new AspectList().add(Aspect.DEATH, 5).add(Aspect.DARKNESS, 5).add(Aspect.ELDRITCH, 5));
		ThaumcraftApi.registerEntityTag(getMobName(ACEntities.omothol_ghoul), new AspectList().add(Aspect.DEATH, 5).add(Aspect.DARKNESS, 5).add(Aspect.ELDRITCH, 5));
	}

	@Override
	public void postInit() {

	}

	public static String getMobName(String name){
		return "abyssalcraft." + name;
	}
}