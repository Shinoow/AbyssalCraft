package com.shinoow.abyssalcraft.api.necronomicon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * An action performed by a Necronomicon
 * @author shinoow
 *
 * @since 2.0.0
 */
public interface INecronomiconAction {

	/**
	 * Logic that checks if the action can be performed (not sided)
	 * <br> (shouldn't affect the world, this is the 'can I do this here' part)
	 */
	boolean canExecuteAction(EntityPlayer player, World world, BlockPos pos, int bookType);
	
	/**
	 * Execute the action, and returns the result (not sided)
	 * <br> (the 'make do thing' part)
	 */
	EnumActionResult executeAction(EntityPlayer player, World world, BlockPos pos, int bookType);
}
