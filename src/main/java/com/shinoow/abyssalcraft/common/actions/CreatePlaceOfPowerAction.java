package com.shinoow.abyssalcraft.common.actions;

import com.shinoow.abyssalcraft.api.energy.structure.StructureHandler;
import com.shinoow.abyssalcraft.api.necronomicon.INecronomiconAction;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreatePlaceOfPowerAction implements INecronomiconAction {

	@Override
	public boolean canExecuteAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		return StructureHandler.instance().canFormStructure(world, pos, bookType, player);
	}

	@Override
	public EnumActionResult executeAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		StructureHandler.instance().formStructure(world, pos, bookType, player);

		world.playSound(player, pos, ACSounds.remnant_scream, player.getSoundCategory(), 3F, 1F);

		return EnumActionResult.SUCCESS;
	}

}
