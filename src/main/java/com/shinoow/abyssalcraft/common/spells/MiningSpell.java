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
package com.shinoow.abyssalcraft.common.spells;

import java.util.Set;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.spell.Spell;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSand.EnumType;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MiningSpell extends Spell {

	public MiningSpell() {
		super("mining", 500F, Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE);
		setRequiresCharging();
		setColor(0xc45b05);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		RayTraceResult r = rayTrace(player, world, 16, 1);

		if(r != null && r.typeOfHit == Type.BLOCK)
			for(BlockPos pos2 : getPositions(r.getBlockPos(), r.sideHit.getOpposite())){
				IBlockState state = getRemains(world.getBlockState(pos2));
				if(state != null && world.isBlockModifiable(player, pos2))
					return true;
			}

		return false;
	}

	private IBlockState getRemains(IBlockState state){

		if(state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.DIRT || state.getBlock() == ACBlocks.darkstone
				|| state.getBlock() == ACBlocks.abyssal_stone || state.getBlock() == ACBlocks.dreadstone
				|| state.getBlock() == ACBlocks.abyssalnite_stone || state.getBlock() == ACBlocks.omothol_stone
				|| state.getBlock() == ACBlocks.monolith_stone || state.getBlock() == Blocks.GRAVEL
				|| state.getBlock() == Blocks.SANDSTONE || state.getBlock() == ACBlocks.dreadlands_dirt)
			return Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, 7);
		if(state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER)
			return Blocks.AIR.getDefaultState();
		if(state == ACBlocks.coralium_stone.getDefaultState())
			return ACBlocks.liquid_coralium.getDefaultState();
		return null;
	}

	private IBlockState getResult(IBlockState state){

		if(state.getBlock() == Blocks.COBBLESTONE)
			return Blocks.STONE.getDefaultState();
		if(state.getBlock() == ACBlocks.darkstone_cobblestone)
			return ACBlocks.darkstone.getDefaultState();
		if(state.getBlock() == ACBlocks.abyssal_cobblestone)
			return ACBlocks.abyssal_stone.getDefaultState();
		if(state.getBlock() == ACBlocks.coralium_cobblestone)
			return ACBlocks.coralium_stone.getDefaultState();
		if(state.getBlock() == ACBlocks.dreadstone_cobblestone)
			return ACBlocks.dreadstone.getDefaultState();
		if(state.getBlock() == ACBlocks.abyssalnite_cobblestone)
			return ACBlocks.abyssalnite_stone.getDefaultState();
		if(state.getBlock() == Blocks.SAND && state.getValue(BlockSand.VARIANT) == EnumType.SAND)
			return Blocks.GLASS.getDefaultState();
		if(state.getBlock() == Blocks.SAND && state.getValue(BlockSand.VARIANT) == EnumType.RED_SAND)
			return Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.RED);
		if(state.getBlock() == ACBlocks.abyssal_sand)
			return ACBlocks.abyssal_sand_glass.getDefaultState();

		return null;
	}

	public RayTraceResult rayTrace(EntityPlayer player, World world, double blockReachDistance, float partialTicks)
	{
		Vec3d vec3d = player.getPositionEyes(partialTicks);
		Vec3d vec3d1 = player.getLook(partialTicks);
		Vec3d vec3d2 = vec3d.add(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
		return world.rayTraceBlocks(vec3d, vec3d2, true, false, true);
	}

	private Set<BlockPos> getPositions(BlockPos pos, EnumFacing facing){
		Set<BlockPos> stuff = Sets.newHashSetWithExpectedSize(9);
		Axis a = facing.getAxis();
		if(a.isHorizontal())
			for(int i = 1; i > -2; i--)
				for(int j = 1; j > -2; j--)
					stuff.add(pos.add(a == Axis.Z ? i : 0, j, a == Axis.X ? i : 0));
		else
			for(int i = 1; i > -2; i--)
				for(int j = 1; j > -2; j--)
					stuff.add(pos.add(i, 0, j));
		return stuff;
	}

	private Set<BlockPos> getOuterPositions(BlockPos pos, EnumFacing facing){
		Set<BlockPos> stuff = Sets.newHashSet();
		Axis a = facing.getAxis();
		if(a.isHorizontal())
			for(int i = 2; i > -3; i--)
				for(int j = 2; j > -3; j--)
					stuff.add(pos.add(a == Axis.Z ? i : 0, j, a == Axis.X ? i : 0));
		else
			for(int i = 2; i > -3; i--)
				for(int j = 2; j > -3; j--)
					stuff.add(pos.add(i, 0, j));

		stuff.removeAll(getPositions(pos, facing));

		return stuff;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {

		RayTraceResult r = rayTrace(player, world, 16, 1);

		float f = 0;

		if(r != null && r.typeOfHit == Type.BLOCK)
			for(int i = 0; f < 500; i++){
				if(f >= 500) break;
				for(BlockPos pos2 : getPositions(r.getBlockPos().offset(r.sideHit.getOpposite(), i), r.sideHit.getOpposite())){
					IBlockState state = getRemains(world.getBlockState(pos2));
					if(state != null && world.isBlockModifiable(player, pos2)){
						f+= world.getBlockState(pos2).getBlockHardness(world, pos2)*(world.getBlockState(pos2).getMaterial().isLiquid() ? 0.5 : 2);
						if(i == 0)
							world.destroyBlock(pos2, false);
						world.setBlockState(pos2, state);
						if(f >= 500) break;
					}
				}
				for(BlockPos pos2 : getOuterPositions(r.getBlockPos().offset(r.sideHit.getOpposite(), i), r.sideHit.getOpposite())){
					IBlockState state = getResult(world.getBlockState(pos2));
					if(state != null && world.isBlockModifiable(player, pos2)){
						f+= world.getBlockState(pos2).getBlockHardness(world, pos2)*(world.getBlockState(pos2).getMaterial().isLiquid() ? 0.5 : 2);
						if(i == 0)
							world.destroyBlock(pos2, false);
						world.setBlockState(pos2, state);
						if(f >= 500) break;
					}
				}
				if(i >= 128) break;
			}
	}
}
