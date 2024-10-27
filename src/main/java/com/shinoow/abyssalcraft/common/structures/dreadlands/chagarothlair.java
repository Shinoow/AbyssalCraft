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
				return worldIn.rand.nextInt(10) > 3 ? blockInfoIn : new BlockInfo(pos1, ACBlocks.cracked_dreadstone_brick.getDefaultState(), null);
				return blockInfoIn;
		};

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_top"));

		template.addBlocksToWorld(world, pos.add(-6, -5, -23), processor, placeSettings, 2);

		//repositioning for front
		pos = pos.add(0, -9, -17);
		template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "chagarothlair/chagarothlair_front"));

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
					if(world.rand.nextInt(100) == 0){
						tileentitychest.setInventorySlotContents(0, new ItemStack(ACItems.crystal_iron));
						tileentitychest.setInventorySlotContents(1, new ItemStack(ACItems.crystal_gold));
						tileentitychest.setInventorySlotContents(2, new ItemStack(ACItems.crystal_sulfur));
						tileentitychest.setInventorySlotContents(3, new ItemStack(ACItems.crystal_carbon));
						tileentitychest.setInventorySlotContents(4, new ItemStack(ACItems.crystal_oxygen));
						tileentitychest.setInventorySlotContents(5, new ItemStack(ACItems.crystal_hydrogen));
						tileentitychest.setInventorySlotContents(6, new ItemStack(ACItems.crystal_nitrogen));
						tileentitychest.setInventorySlotContents(7, new ItemStack(ACItems.crystal_phosphorus));
						tileentitychest.setInventorySlotContents(8, new ItemStack(ACItems.crystal_potassium));
						tileentitychest.setInventorySlotContents(9, new ItemStack(ACItems.crystal_redstone));
						tileentitychest.setInventorySlotContents(10, new ItemStack(ACItems.crystal_abyssalnite));
						tileentitychest.setInventorySlotContents(11, new ItemStack(ACItems.crystal_coralium));
						tileentitychest.setInventorySlotContents(12, new ItemStack(ACItems.crystal_dreadium));
						tileentitychest.setInventorySlotContents(14, new ItemStack(ACItems.crystal_blaze));
						tileentitychest.setInventorySlotContents(15, new ItemStack(ACItems.crystal_tin));
						tileentitychest.setInventorySlotContents(16, new ItemStack(ACItems.crystal_copper));
						tileentitychest.setInventorySlotContents(17, new ItemStack(ACItems.crystal_silicon));
						tileentitychest.setInventorySlotContents(18, new ItemStack(ACItems.crystal_magnesium));
						tileentitychest.setInventorySlotContents(19, new ItemStack(ACItems.crystal_aluminium));
						tileentitychest.setInventorySlotContents(20, new ItemStack(ACItems.crystal_zinc));
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
