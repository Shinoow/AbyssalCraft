package com.shinoow.abyssalcraft.api.necronomicon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Registry for Necronomicon actions (logic triggered when shift right-clicking the book)
 * @author shinoow
 *
 * @since 2.0.0
 */
public class NecronomiconActionRegistry {

	private final List<INecronomiconAction> actions = new ArrayList<>();

	private final Logger logger = LogManager.getLogger("NecronomiconActionRegistry");

	private static final NecronomiconActionRegistry instance = new NecronomiconActionRegistry();

	public static NecronomiconActionRegistry instance() {
		return instance;
	}

	private NecronomiconActionRegistry() {}

	public void addAction(INecronomiconAction action) {
		actions.add(action);
	}

	public List<INecronomiconAction> getActions() {
		return actions;
	}

	/**
	 * Attempts to get an action based on the given parameters
	 * @param player
	 * @param world
	 * @param blockPos
	 * @param bookType
	 * @return
	 */
	@Nullable
	public INecronomiconAction getActionFor(EntityPlayer player, World world, BlockPos blockPos, int bookType) {
		Optional<INecronomiconAction> action = actions.stream()
				.filter(a -> a.canExecuteAction(player, world, blockPos, bookType))
				.findFirst();

		return action.orElse(null);
	}
}
