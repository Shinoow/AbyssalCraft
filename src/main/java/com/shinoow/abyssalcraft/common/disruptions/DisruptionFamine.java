package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisruptionFamine extends DisruptionEntry {

	public DisruptionFamine(String unlocalizedName, DeityType deity) {
		super(unlocalizedName, deity);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {
		if(world.isRemote) return;
		
		for(EntityPlayer player: players)
			player.getFoodStats().setFoodLevel(0);
	}

}
