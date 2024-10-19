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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockACStone extends Block {

	private EnumStoneType TYPE;
	public static final Map<EnumStoneType, Block> VARIANTS = new HashMap<>();

	public BlockACStone(EnumStoneType type) {
		super(Material.ROCK, type.getMapColor());
		TYPE = type;
		setHardness(type.getHardness());
		setResistance(type.getResistance());
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel("pickaxe", type.getHarvestLevel());
		VARIANTS.put(TYPE, this);
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon)
			return TYPE != EnumStoneType.OMOTHOL_STONE &&
			TYPE != EnumStoneType.ETHAXIUM;
		else if (entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return TYPE != EnumStoneType.ETHAXIUM;
		return super.canEntityDestroy(state, world, pos, entity);
	}

	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		if (!par1World.isRemote && TYPE == EnumStoneType.CORALIUM_STONE && par1World.getBiome(pos) != ACBiomes.coralium_lake)
			for(EnumFacing face : EnumFacing.values())
				if (par1World.getBlockState(pos.offset(face)).getBlock() == ACBlocks.liquid_coralium && par5Random.nextFloat() < 0.3)
					par1World.setBlockState(pos.offset(face), state);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return Item.getItemFromBlock(TYPE.getDrop());
	}

	public enum EnumStoneType implements IStringSerializable
	{
		DARKSTONE(0, "darkstone", "darkstone", 0, 1.65F, 12.0F, MapColor.BLACK, () -> ACBlocks.darkstone_cobblestone),
		ABYSSAL_STONE(1, "abyssalstone", "abystone", 2, 1.8F, 12.0F, MapColor.GREEN, () -> ACBlocks.abyssal_cobblestone),
		DREADSTONE(2, "dreadstone", "dreadstone", 4, 2.5F, 20.0F, MapColor.RED, () -> ACBlocks.dreadstone_cobblestone),
		ELYSIAN_STONE(3, "elysianstone", "elysian_stone", 4, 2.5F, 20.0F, MapColor.LIGHT_BLUE, () -> ACBlocks.elysian_cobblestone),
		CORALIUM_STONE(4, "coraliumstone", "cstone", 0, 1.5F, 10.0F, MapColor.CYAN, () -> ACBlocks.coralium_cobblestone),
		ETHAXIUM(5, "ethaxium", "ethaxium", 8, 100.0F, Float.MAX_VALUE, MapColor.CLOTH, () -> ACBlocks.ethaxium),
		OMOTHOL_STONE(6, "omotholstone", "omotholstone", 6, 10.0F, 12.0F, MapColor.BLACK, () -> ACBlocks.omothol_stone),
		MONOLITH_STONE(7, "monolithstone", "monolithstone", 0, 6.0F, 24.0F, MapColor.BLACK, () -> ACBlocks.monolith_stone);

		private static final EnumStoneType[] META_LOOKUP = new EnumStoneType[values().length];
		private final int meta;
		private final String name;
		private final String state;
		private final int harvest;
		private final float hardness;
		private final float resistance;
		private final MapColor mapColor;
		private final Supplier<Block> drop;

		private EnumStoneType(int meta, String name, String state, int harvest, float hardness, float resistance, MapColor mapColor, Supplier<Block> drop)
		{
			this.meta = meta;
			this.name = name;
			this.state = state;
			this.harvest = harvest;
			this.hardness = hardness;
			this.resistance = resistance;
			this.mapColor = mapColor;
			this.drop = drop;
		}

		public int getMeta()
		{
			return meta;
		}

		public MapColor getMapColor()
		{
			return mapColor;
		}

		public int getHarvestLevel(){
			return harvest;
		}

		public float getHardness(){
			return hardness;
		}

		public float getResistance(){
			return resistance;
		}

		@Override
		public String toString()
		{
			return name;
		}

		public String getState(){
			return state;
		}

		public Block getDrop() {
			return drop.get();
		}

		public static EnumStoneType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length)
				meta = 0;

			return META_LOOKUP[meta];
		}

		@Override
		public String getName()
		{
			return name;
		}

		static
		{
			for (EnumStoneType enumtype : values())
				META_LOOKUP[enumtype.getMeta()] = enumtype;
		}
	}
}
