package com.shinoow.abyssalcraft.common.actions;

import com.shinoow.abyssalcraft.api.necronomicon.INecronomiconAction;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreateAltarAction implements INecronomiconAction {

	@Override
	public boolean canExecuteAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		return RitualUtil.canCreateAltar(world, pos, bookType, player);
	}

	@Override
	public EnumActionResult executeAction(EntityPlayer player, World world, BlockPos pos, int bookType) {

		RitualUtil.createAltar(world, pos);

		world.playSound(player, pos, ACSounds.remnant_scream, player.getSoundCategory(), 3F, 1F);

		return EnumActionResult.SUCCESS;
	}

}
