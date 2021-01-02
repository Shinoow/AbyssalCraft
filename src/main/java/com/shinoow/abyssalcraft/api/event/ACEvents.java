/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.event;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * A class containing events used by AbyssalCraft.<br>
 * All events are fired on the {@link MinecraftForge#EVENT_BUS}.
 *
 * @author shinoow
 *
 * @since 1.4
 */
public class ACEvents {

	/**
	 * ItemTransmutedEvent is fired when a player picks up an Item from the output slot of a Transmutator.<br>
	 * <br>
	 * {@link #transmuted} contains the Item found in the Transmutator output slot. <br>
	 * <br>
	 * This event is not {@link Cancelable}.
	 * <br>
	 * This event does not have a result. {@link HasResult}<br>
	 * <br>
	 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
	 *
	 * @author shinoow
	 *
	 * @since 1.4
	 */
	public static class ItemTransmutedEvent extends PlayerEvent {

		private final ItemStack transmuted;

		public ItemTransmutedEvent(EntityPlayer player, ItemStack crafting){
			super(player);
			transmuted = crafting;
		}

		public ItemStack getTransmutedStack(){
			return transmuted;
		}
	}

	/**
	 * ItemCrystallizedEvent is fired when a player picks up an Item from the output slot of a Crystallizer.<br>
	 * <br>
	 * {@link #crystallized} contains the Item found in the Crystallizer output slot. <br>
	 * <br>
	 * This event is not {@link Cancelable}.
	 * <br>
	 * This event does not have a result. {@link HasResult}<br>
	 * <br>
	 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
	 *
	 * @author shinoow
	 *
	 * @since 1.4
	 */
	public static class ItemCrystallizedEvent extends PlayerEvent {

		private final ItemStack crystallized;

		public ItemCrystallizedEvent(EntityPlayer player, ItemStack crafting){
			super(player);
			crystallized = crafting;
		}

		public ItemStack getCrystallizedStack(){
			return crystallized;
		}
	}

	/**
	 * ItemEngravedEvent is fired when a player picks up an Item from the output slot of an Engraver.<br>
	 * <br>
	 * {@link #engraved} contains the Item found in the Engraver output slot. <br>
	 * <br>
	 * This event is not {@link Cancelable}.
	 * <br>
	 * This event does not have a result. {@link HasResult}<br>
	 * <br>
	 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
	 *
	 * @author shinoow
	 *
	 * @since 1.4
	 */
	public static class ItemEngravedEvent extends PlayerEvent {

		private final ItemStack engraved;

		public ItemEngravedEvent(EntityPlayer player, ItemStack crafting){
			super(player);
			engraved = crafting;
		}

		public ItemStack getEngravedStack(){
			return engraved;
		}
	}

	/**
	 * ItemMaterializedEvent is fired when a player picks up an Item from the output slot of a Materializer.<br>
	 * <br>
	 * {@link #materialized} contains the Item found in the Materializer output slot. <br>
	 * <br>
	 * This event is not {@link Cancelable}.
	 * <br>
	 * This event does not have a result. {@link HasResult}<br>
	 * <br>
	 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
	 *
	 * @author shinoow
	 *
	 * @since 1.4
	 */
	public static class ItemMaterializedEvent extends PlayerEvent {

		private final ItemStack materialized;

		public ItemMaterializedEvent(EntityPlayer player, ItemStack crafting){
			super(player);
			materialized = crafting;
		}

		public ItemStack getMaterializedStack(){
			return materialized;
		}
	}

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
	public static class RitualEvent extends PlayerEvent {
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

	/**
	 * DisruptionEvent is fired before a Disruption is triggered.<br>
	 * <br>
	 * {@link #deity} contains the Deity Type for this particular Disruption (can be null).<br>
	 * {@link #world} contains the world at which this event is occurring. <br>
	 * {@link #pos} contains the BlockPos at which this event is occurring. <br>
	 * {@link #players} contains a list of nearby players where this event is occurring (can be empty).<br>
	 * {@link #disruption} contains the Disruption about to be triggered after this event.<br>
	 * <br>
	 * This event is {@link Cancelable}.
	 * <br>
	 * If cancelled, the Disruption won't occur. <br>
	 * <br>
	 * This event does not have a result. {@link HasResult}<br>
	 * <br>
	 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
	 *
	 * @author shinoow
	 *
	 * @since 1.7.5
	 */
	@Cancelable
	public static class DisruptionEvent extends Event {
		private final DeityType deity;
		private final World world;
		private final BlockPos pos;
		private final List<EntityPlayer> players;
		private final DisruptionEntry disruption;

		public DisruptionEvent(DeityType deity, World world, BlockPos pos, List<EntityPlayer> players, DisruptionEntry disruption){
			this.deity = deity;
			this.world = world;
			this.pos = pos;
			this.players = players;
			this.disruption = disruption;
		}

		public DeityType getDeityType(){
			return deity;
		}

		public World getWorld(){
			return world;
		}

		public BlockPos getPos(){
			return pos;
		}

		public List<EntityPlayer> getPlayers(){
			return players;
		}

		public DisruptionEntry getDisruption(){
			return disruption;
		}
	}
}
