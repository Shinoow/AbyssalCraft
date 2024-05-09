/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.dreadlands;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick.EnumBrickType;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.*;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class chagarothlair extends WorldGenerator {

	public chagarothlair() { }

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		PlacementSettings placeSettings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID);

		MinecraftServer server = world.getMinecraftServer();
		TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();

		ITemplateProcessor processor = (worldIn, pos1, blockInfoIn) -> {

			if(blockInfoIn.blockState == ACBlocks.dreadstone_brick.getDefaultState())
				return worldIn.rand.nextInt(10) > 3 ? blockInfoIn : new BlockInfo(pos1, blockInfoIn.blockState.withProperty(BlockACBrick.TYPE, EnumBrickType.CRACKED), null);
				return blockInfoIn;
		};

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_front"));

		template.addBlocksToWorld(world, pos.add(-8, -2, -22), processor, placeSettings, 2);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_middle"));

		template.addBlocksToWorld(world, pos.add(-8, -17, -54), processor, placeSettings, 2);

		Map<BlockPos, String> map = template.getDataBlocks(pos.add(-8, -17, -54), placeSettings);

		for (Entry<BlockPos, String> entry : map.entrySet())
			if("chest".equals(entry.getValue())) {
				TileEntity te = world.getTileEntity(entry.getKey().down());
				world.setBlockToAir(entry.getKey());
				if(te instanceof TileEntityChest){
					TileEntityChest tileentitychest = (TileEntityChest)te;

					tileentitychest.setInventorySlotContents(13, new ItemStack(Blocks.DIRT, 1).setStackDisplayName(TextFormatting.GOLD +"The Legendary Treasure of The Dreadlands"));
					if(world.rand.nextInt(10) == 0 && ACConfig.foodstuff) {
						tileentitychest.decrStackSize(13, 1);
						tileentitychest.setInventorySlotContents(13, new ItemStack(ACItems.fried_egg, 1).setStackDisplayName(TextFormatting.GOLD +"The Legendary Treasure of The Dreadlands"));
					}
					if(world.rand.nextInt(100) == 0){
						tileentitychest.setInventorySlotContents(0, new ItemStack(ACItems.crystal, 1, 0));
						tileentitychest.setInventorySlotContents(1, new ItemStack(ACItems.crystal, 1, 1));
						tileentitychest.setInventorySlotContents(2, new ItemStack(ACItems.crystal, 1, 2));
						tileentitychest.setInventorySlotContents(3, new ItemStack(ACItems.crystal, 1, 3));
						tileentitychest.setInventorySlotContents(4, new ItemStack(ACItems.crystal, 1, 4));
						tileentitychest.setInventorySlotContents(5, new ItemStack(ACItems.crystal, 1, 5));
						tileentitychest.setInventorySlotContents(6, new ItemStack(ACItems.crystal, 1, 6));
						tileentitychest.setInventorySlotContents(7, new ItemStack(ACItems.crystal, 1, 7));
						tileentitychest.setInventorySlotContents(8, new ItemStack(ACItems.crystal, 1, 8));
						tileentitychest.setInventorySlotContents(9, new ItemStack(ACItems.crystal, 1, 11));
						tileentitychest.setInventorySlotContents(10, new ItemStack(ACItems.crystal, 1, 12));
						tileentitychest.setInventorySlotContents(11, new ItemStack(ACItems.crystal, 1, 13));
						tileentitychest.setInventorySlotContents(12, new ItemStack(ACItems.crystal, 1, 14));
						tileentitychest.setInventorySlotContents(14, new ItemStack(ACItems.crystal, 1, 15));
						tileentitychest.setInventorySlotContents(15, new ItemStack(ACItems.crystal, 1, 16));
						tileentitychest.setInventorySlotContents(16, new ItemStack(ACItems.crystal, 1, 17));
						tileentitychest.setInventorySlotContents(17, new ItemStack(ACItems.crystal, 1, 18));
						tileentitychest.setInventorySlotContents(18, new ItemStack(ACItems.crystal, 1, 19));
						tileentitychest.setInventorySlotContents(19, new ItemStack(ACItems.crystal, 1, 20));
						tileentitychest.setInventorySlotContents(20, new ItemStack(ACItems.crystal, 1, 24));
						tileentitychest.setInventorySlotContents(21, new ItemStack(ACItems.oblivion_catalyst, 1));
					}
				}
			}

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_middle_left"));

		template.addBlocksToWorld(world, pos.add(-24, 1, -41), processor, placeSettings, 2);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_middle_right"));

		template.addBlocksToWorld(world, pos.add(9, 1, -41), processor, placeSettings, 2);

		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_back"));

		template.addBlocksToWorld(world, pos.add(-9, -18, -84), processor, placeSettings, 2);

		return true;
	}
}
