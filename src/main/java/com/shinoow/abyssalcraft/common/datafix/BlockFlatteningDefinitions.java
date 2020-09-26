package com.shinoow.abyssalcraft.common.datafix;

import java.util.Objects;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.common.blocks.*;
import com.shinoow.abyssalcraft.common.util.ACLogger;

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
						final EnumFacing facing = EnumFacing.getFront(facingIndex);
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
						final EnumFacing facing = EnumFacing.getFront(facingIndex);
						return block.getDefaultState().withProperty(BlockDecorativeStatue.FACING, facing);
					},
					tileEntityNBT -> BlockFlattening.TileEntityAction.REMOVE));
		});

		return new BlockFlattening(flatteningDefinitions.build());
	}
}