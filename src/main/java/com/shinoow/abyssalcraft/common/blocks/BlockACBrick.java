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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockACBrick extends BlockACBasic {

	//	public static final PropertyEnum<EnumBrickType> TYPE = PropertyEnum.create("type", BlockACBrick.EnumBrickType.class);

	public static final Map<String, Map<EnumBrickType, Block>> VARIANTS = new HashMap<>();

	public BlockACBrick(int harvestlevel, float hardness, float resistance) {
		super(Material.ROCK, "pickaxe", harvestlevel, hardness, resistance, SoundType.STONE);
	}

	public BlockACBrick(float hardness, float resistance) {
		super(Material.ROCK, hardness, resistance, SoundType.STONE);
	}

	public BlockACBrick(int harvestlevel, float hardness, float resistance, MapColor mapColor) {
		super(Material.ROCK, "pickaxe", harvestlevel, hardness, resistance, SoundType.STONE, mapColor);
	}

	public BlockACBrick(float hardness, float resistance, MapColor mapColor) {
		super(Material.ROCK, hardness, resistance, SoundType.STONE, mapColor);
	}

	public BlockACBrick remap(String oldName, EnumBrickType type) {
		Map<EnumBrickType, Block> variant = VARIANTS.getOrDefault(oldName, new HashMap<>());
		variant.put(type, this);
		VARIANTS.put(oldName, variant);
		return this;
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return state.getBlock().getUnlocalizedName().contains("ethaxium");
		return super.canEntityDestroy(state, world, pos, entity);
	}

	public enum EnumBrickType implements IStringSerializable {
		NORMAL(0, "normal"),
		CHISELED(1, "chiseled"),
		CRACKED(2, "cracked");

		private static final BlockACBrick.EnumBrickType[] META_LOOKUP = new BlockACBrick.EnumBrickType[values().length];

		private int meta;
		private String name;

		private EnumBrickType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public static BlockACBrick.EnumBrickType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length)
				meta = 0;

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta() {
			return meta;
		}

		@Override
		public String toString() {
			return getName();
		}

		static {
			for(BlockACBrick.EnumBrickType type : values())
				META_LOOKUP[type.getMeta()] = type;
		}
	}
}
