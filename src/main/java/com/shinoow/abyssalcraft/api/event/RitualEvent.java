/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.event;

import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * RitualEvent is fired when Necronomicon Rituals are performed. <br>
 * {@link RitualEvent.Pre} is fired before the Ritual is performed.<br>
 * {@link RitualEvent.Post} is fired before the Ritual is completed. <br>
 * {@link RitualEvent.Failed}} is fired when a Ritual is failed (allows you to change the disruption)<br>
 * <br>
 * {@link #ritual} contains the ritual being performed. <br>
 * {@link #world} contains the world at which this event is occurring. <br>
 * {@link #pos} contains the BlockPos at which this event is occurring. <br>
 * <br>
 * Any child event of this event is {@link Cancelable}.
 * <br>
 * If cancelled, the ritual won't be performed/won't complete. <br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 *
 * @author shinoow
 *
 * @since 1.4
 */
public class RitualEvent extends PlayerEvent {
	private final NecronomiconRitual ritual;
	private final World world;
	private final BlockPos pos;

	public RitualEvent(EntityPlayer player, NecronomiconRitual ritual, World world, BlockPos pos){
		super(player);
		this.ritual = ritual;
		this.world = world;
		this.pos = pos;
	}

	public NecronomiconRitual getRitual(){
		return ritual;
	}

	public World getWorld(){
		return world;
	}

	public BlockPos getPos(){
		return pos;
	}

	@Cancelable
	public static class Pre extends RitualEvent {

		public Pre(EntityPlayer player, NecronomiconRitual ritual, World world, BlockPos pos) {
			super(player, ritual, world, pos);
		}
	}

	@Cancelable
	public static class Post extends RitualEvent {

		public Post(EntityPlayer player, NecronomiconRitual ritual, World world, BlockPos pos) {
			super(player, ritual, world, pos);
		}
	}

	public static class Failed extends RitualEvent {

		private DisruptionEntry disruption;
		public Failed(EntityPlayer player, NecronomiconRitual ritual, DisruptionEntry disruption, World world, BlockPos pos) {
			super(player, ritual, world, pos);
			this.disruption = disruption;
		}

		public DisruptionEntry getDisruption() {
			return disruption;
		}

		/**
		 * Change the Disruption triggered when the ritual fails (has no effect if done client-side)
		 * @param disruption Disruption to trigger
		 */
		public void setDisruption(DisruptionEntry disruption) {
			this.disruption = disruption;
		}
	}
}
