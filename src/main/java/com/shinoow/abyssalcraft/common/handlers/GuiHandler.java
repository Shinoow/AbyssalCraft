/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.shinoow.abyssalcraft.client.gui.*;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.common.inventory.*;
import com.shinoow.abyssalcraft.common.items.*;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	private Map<Integer, Function<ContainerContext, Object>> serverElements = new HashMap<>();
	private Map<Integer, Function<ContainerContext, Object>> clientElements = new HashMap<>();

	public GuiHandler() {
		serverElements.put(ACLib.crystallizerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityCrystallizer)
				return new ContainerCrystallizer(ctx.player.inventory, (TileEntityCrystallizer) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.transmutatorGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityTransmutator)
				return new ContainerTransmutator(ctx.player.inventory, (TileEntityTransmutator) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.engraverGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityEngraver)
				return new ContainerEngraver(ctx.player.inventory, (TileEntityEngraver) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.materializerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityMaterializer)
				return new ContainerMaterializer(ctx.player.inventory, (TileEntityMaterializer) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.energycontainerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityEnergyContainer)
				return new ContainerEnergyContainer(ctx.player.inventory, (TileEntityEnergyContainer) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.rendingPedestalGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityRendingPedestal)
				return new ContainerRendingPedestal(ctx.player.inventory, (TileEntityRendingPedestal) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.stateTransformerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityStateTransformer)
				return new ContainerStateTransformer(ctx.player.inventory, (TileEntityStateTransformer) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.energyDepositionerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityEnergyDepositioner)
				return new ContainerEnergyDepositioner(ctx.player.inventory, (TileEntityEnergyDepositioner) ctx.getTE());
			return null;
		});
		serverElements.put(ACLib.crystalbagGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemCrystalBag)
				return new ContainerCrystalBag(ctx.player.inventory, new InventoryCrystalBag(ctx.getMainHand(), EnumHand.MAIN_HAND));
			if(ctx.getOffHand().getItem() instanceof ItemCrystalBag)
				return new ContainerCrystalBag(ctx.player.inventory, new InventoryCrystalBag(ctx.getOffHand(), EnumHand.OFF_HAND));
			return null;
		});
		serverElements.put(ACLib.necronomiconspellbookGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemNecronomicon && ((ItemNecronomicon)ctx.getMainHand().getItem()).isOwner(ctx.player, ctx.getMainHand()))
				return new ContainerSpellbook(ctx.player, ctx.getMainHand());
			if(ctx.getOffHand().getItem() instanceof ItemNecronomicon && ((ItemNecronomicon)ctx.getOffHand().getItem()).isOwner(ctx.player, ctx.getOffHand()))
				return new ContainerSpellbook(ctx.player, ctx.getOffHand());
			return null;
		});
		serverElements.put(ACLib.configuratorGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemConfigurator)
				return new ContainerConfigurator(ctx.player.inventory, new InventoryConfigurator(ctx.getMainHand(), EnumHand.MAIN_HAND));
			if(ctx.getOffHand().getItem() instanceof ItemConfigurator)
				return new ContainerConfigurator(ctx.player.inventory, new InventoryConfigurator(ctx.getOffHand(), EnumHand.OFF_HAND));
			return null;
		});
		serverElements.put(ACLib.sequentialBrewingStandGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntitySequentialBrewingStand)
				return new ContainerSequentialBrewingStand(ctx.player.inventory, (TileEntitySequentialBrewingStand) ctx.getTE());
			return null;
		});

		clientElements.put(ACLib.crystallizerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityCrystallizer)
				return new GuiCrystallizer(ctx.player.inventory, (TileEntityCrystallizer) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.transmutatorGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityTransmutator)
				return new GuiTransmutator(ctx.player.inventory, (TileEntityTransmutator) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.engraverGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityEngraver)
				return new GuiEngraver(ctx.player.inventory, (TileEntityEngraver) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.materializerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityMaterializer)
				return new GuiMaterializer(ctx.player.inventory, (TileEntityMaterializer) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.energycontainerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityEnergyContainer)
				return new GuiEnergyContainer(ctx.player.inventory, (TileEntityEnergyContainer) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.rendingPedestalGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityRendingPedestal)
				return new GuiRendingPedestal(ctx.player.inventory, (TileEntityRendingPedestal) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.stateTransformerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityStateTransformer)
				return new GuiStateTransformer(ctx.player.inventory, (TileEntityStateTransformer) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.energyDepositionerGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntityEnergyDepositioner)
				return new GuiEnergyDepositioner(ctx.player.inventory, (TileEntityEnergyDepositioner) ctx.getTE());
			return null;
		});
		clientElements.put(ACLib.necronmiconGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemNecronomicon && ((ItemNecronomicon)ctx.getMainHand().getItem()).isOwner(ctx.player, ctx.getMainHand()))
				return GuiNecronomicon.currentNecro.withBookType(((ItemNecronomicon)ctx.getMainHand().getItem()).getBookType());
			if(ctx.getOffHand().getItem() instanceof ItemNecronomicon && ((ItemNecronomicon)ctx.getOffHand().getItem()).isOwner(ctx.player, ctx.getOffHand()))
				return GuiNecronomicon.currentNecro.withBookType(((ItemNecronomicon)ctx.getOffHand().getItem()).getBookType());
			return null;
		});
		clientElements.put(ACLib.crystalbagGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemCrystalBag)
				return new GuiCrystalBag(new ContainerCrystalBag(ctx.player.inventory, new InventoryCrystalBag(ctx.getMainHand(), EnumHand.MAIN_HAND)));
			if(ctx.getOffHand().getItem() instanceof ItemCrystalBag)
				return new GuiCrystalBag(new ContainerCrystalBag(ctx.player.inventory, new InventoryCrystalBag(ctx.getOffHand(), EnumHand.OFF_HAND)));
			return null;
		});
		clientElements.put(ACLib.necronomiconspellbookGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemNecronomicon && ((ItemNecronomicon)ctx.getMainHand().getItem()).isOwner(ctx.player, ctx.getMainHand()))
				return new GuiSpellbook(new ContainerSpellbook(ctx.player, ctx.getMainHand()));
			return null;
		});
		clientElements.put(ACLib.configuratorGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemConfigurator)
				return new GuiConfigurator(new ContainerConfigurator(ctx.player.inventory, new InventoryConfigurator(ctx.getMainHand(), EnumHand.MAIN_HAND)));
			if(ctx.getOffHand().getItem() instanceof ItemConfigurator)
				return new GuiConfigurator(new ContainerConfigurator(ctx.player.inventory, new InventoryConfigurator(ctx.getOffHand(), EnumHand.OFF_HAND)));
			return null;
		});
		clientElements.put(ACLib.faceBookGuiID, ctx -> {
			if(ctx.getMainHand().getItem() instanceof ItemFaceBook)
				return new GuiFaceBook();
			if(ctx.getOffHand().getItem() instanceof ItemFaceBook)
				return new GuiFaceBook();
			return null;
		});
		clientElements.put(ACLib.sequentialBrewingStandGuiID, ctx -> {
			if (ctx.getTE() instanceof TileEntitySequentialBrewingStand)
				return new GuiSequentialBrewingStand(ctx.player.inventory, (TileEntitySequentialBrewingStand) ctx.getTE());
			return null;
		});
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return serverElements.getOrDefault(ID, ctx -> null).apply(new ContainerContext(player, world, x, y, z));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return clientElements.getOrDefault(ID, ctx -> null).apply(new ContainerContext(player, world, x, y, z));
	}

	private class ContainerContext {
		public EntityPlayer player;
		public World world;
		public int x, y, z;
		private TileEntity te;
		private ItemStack main = ItemStack.EMPTY;
		private ItemStack off = ItemStack.EMPTY;
		private boolean initTE, initMain, initOff;

		public ContainerContext(EntityPlayer player, World world, int x, int y, int z) {
			this.player = player;
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public TileEntity getTE() {
			if(!initTE) {
				te = world.getTileEntity(new BlockPos(x, y, z));
				initTE = true;
			}
			return te;
		}

		public ItemStack getMainHand() {
			if(!initMain) {
				main = player.getHeldItem(EnumHand.MAIN_HAND);
				initMain = true;
			}
			return main;
		}

		public ItemStack getOffHand() {
			if(!initOff) {
				off = player.getHeldItem(EnumHand.OFF_HAND);
				initOff = true;
			}
			return off;
		}
	}
}
