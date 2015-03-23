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
package com.shinoow.abyssalcraft.common;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.client.gui.GuiCrystallizer;
import com.shinoow.abyssalcraft.client.gui.GuiEngraver;
import com.shinoow.abyssalcraft.client.gui.GuiTransmutator;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEngraver;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTransmutator;
import com.shinoow.abyssalcraft.common.inventory.ContainerCrystallizer;
import com.shinoow.abyssalcraft.common.inventory.ContainerEngraver;
import com.shinoow.abyssalcraft.common.inventory.ContainerTransmutator;

import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if(entity != null)
			switch(ID) {
			case AbyssalCraft.crystallizerGuiID:
				if (entity instanceof TileEntityCrystallizer)
					return new ContainerCrystallizer(player.inventory, (TileEntityCrystallizer) entity);
				break;
			case AbyssalCraft.transmutatorGuiID:
				if (entity instanceof TileEntityTransmutator)
					return new ContainerTransmutator(player.inventory, (TileEntityTransmutator) entity);
				break;
			case AbyssalCraft.engraverGuiID:
				if (entity instanceof TileEntityEngraver)
					return new ContainerEngraver(player.inventory, (TileEntityEngraver) entity);
				break;
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		ItemStack stack = player.getCurrentEquippedItem();

		if(entity != null)
			switch(ID) {
			case AbyssalCraft.crystallizerGuiID:
				if (entity instanceof TileEntityCrystallizer)
					return new GuiCrystallizer(player.inventory, (TileEntityCrystallizer) entity);
				break;
			case AbyssalCraft.transmutatorGuiID:
				if (entity instanceof TileEntityTransmutator)
					return new GuiTransmutator(player.inventory, (TileEntityTransmutator) entity);
				break;
			case AbyssalCraft.engraverGuiID:
				if (entity instanceof TileEntityEngraver)
					return new GuiEngraver(player.inventory, (TileEntityEngraver) entity);
				break;
			}
		if(stack != null)
			switch(ID){
			case AbyssalCraft.necronmiconGuiID:
				if(stack.getItem() == AbyssalCraft.necronomicon)
					return new GuiNecronomicon(0);
				if(stack.getItem() == AbyssalCraft.necronomicon_cor)
					return new GuiNecronomicon(1);
				if(stack.getItem() == AbyssalCraft.necronomicon_dre)
					return new GuiNecronomicon(2);
				if(stack.getItem() == AbyssalCraft.necronomicon_omt)
					return new GuiNecronomicon(3);
				if(stack.getItem() == AbyssalCraft.abyssalnomicon)
					return new GuiNecronomicon(4);
				break;
			}
		return null;
	}

	public void registerRenderThings() {
	}

	public void preInit() {
	}

	public void init() {
	}

	public void postInit() {
	}

	public int addArmor(String armor) {
		return 0;
	}

	public ModelBiped getArmorModel(int id){
		return null;
	}
}