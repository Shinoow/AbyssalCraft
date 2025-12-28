package com.shinoow.abyssalcraft.common.actions;

import com.shinoow.abyssalcraft.api.block.IRitualAltar;
import com.shinoow.abyssalcraft.api.necronomicon.INecronomiconAction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PerformRitualAction implements INecronomiconAction {

	@Override
	public boolean canExecuteAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		return world.getTileEntity(pos) instanceof IRitualAltar;
	}

	@Override
	public EnumActionResult executeAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		IRitualAltar altar = (IRitualAltar)world.getTileEntity(pos);

		altar.performRitual(world, pos, player);

		return EnumActionResult.SUCCESS;
	}

}
