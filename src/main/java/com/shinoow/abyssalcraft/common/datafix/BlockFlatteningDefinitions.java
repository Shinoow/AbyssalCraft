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
package com.shinoow.abyssalcraft.common.datafix;

import java.util.Objects;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.common.blocks.*;

import net.minecraft.util.EnumFacing;

/**
 * Manages the flattening definitions for the {@link BlockFlattening} DataFixer.
 *
 * @author Choonster
 */
public class BlockFlatteningDefinitions {
	/**
	 * Create an instance of the BlockFlattening DataFixer with the definitions of the blocks to flatten.
	 *
	 * @return The BlockFlattening instance
	 */
	public static BlockFlattening createBlockFlattening() {
		final ImmutableList.Builder<BlockFlattening.FlatteningDefinition> flatteningDefinitions = new ImmutableList.Builder<>();

		BlockACCobblestone.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("cobblestone",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});
		BlockACStone.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("stone",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});
		IngotBlock.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("ingotblock",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});
		BlockStatue.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("statue",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> {
						final int facingIndex = Objects.requireNonNull(tileEntityNBT).getInteger("Facing");
						final EnumFacing facing = EnumFacing.byIndex(facingIndex);
						return block.getDefaultState().withProperty(BlockStatue.FACING, facing);
					},
					tileEntityNBT -> {
						tileEntityNBT.removeTag("Facing");
						return BlockFlattening.TileEntityAction.KEEP;
					}));
		});
		BlockDecorativeStatue.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("decorativestatue",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> {
						final int facingIndex = Objects.requireNonNull(tileEntityNBT).getInteger("Facing");
						final EnumFacing facing = EnumFacing.byIndex(facingIndex);
						return block.getDefaultState().withProperty(BlockDecorativeStatue.FACING, facing);
					},
					tileEntityNBT -> BlockFlattening.TileEntityAction.REMOVE));
		});
		BlockACBrick.VARIANTS.forEach((name, variants) -> {
			variants.forEach((type, variant) -> {
				flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition(name,
						type.getMeta(),
						variant,
						(block, tileEntityNBT) -> block.getDefaultState(),
						null));
			});
		});

		//version 3 definitions

		BlockRitualAltar.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("ritualaltar",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});

		BlockRitualPedestal.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("ritualpedestal",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});

		BlockTieredEnergyPedestal.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("tieredenergypedestal",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});

		BlockTieredSacrificialAltar.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("tieredsacrificialaltar",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});

		BlockTieredEnergyCollector.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("tieredenergycollector",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});

		BlockTieredEnergyRelay.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("tieredenergyrelay",
					type.getMeta(),
					variant,
					(block, tileEntityNBT) -> {
						final int facingIndex = Objects.requireNonNull(tileEntityNBT).getInteger("Facing");
						final EnumFacing facing = EnumFacing.byIndex(facingIndex);
						return block.getDefaultState().withProperty(BlockEnergyRelay.FACING, facing);
					},
					tileEntityNBT -> {
						tileEntityNBT.removeTag("Facing");
						return BlockFlattening.TileEntityAction.KEEP;
					}));
		});

		//version 4 definitions TBD

		BlockCrystalCluster.VARIANTS.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("crystalcluster",
					type.getMetadata(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});

		BlockCrystalCluster.VARIANTS_2.forEach((type, variant) -> {
			flatteningDefinitions.add(new BlockFlattening.FlatteningDefinition("crystalcluster2",
					type.getMetadata(),
					variant,
					(block, tileEntityNBT) -> block.getDefaultState(),
					null));
		});

		return new BlockFlattening(flatteningDefinitions.build());
	}
}
