/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.spell.Spell;

public class MiningSpell extends Spell {

	public MiningSpell() {
		super("mining", 1000F, Blocks.IRON_ORE, Blocks.GOLD_ORE);
		setRequiresCharging();
		setColor(0xc45b05);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		return true;
	}

	private IBlockState getRemains(IBlockState state){

		if(state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.DIRT || state.getBlock() == ACBlocks.darkstone
			|| state.getBlock() == ACBlocks.abyssal_stone || state.getBlock() == ACBlocks.dreadstone
			|| state.getBlock() == ACBlocks.abyssalnite_stone || state.getBlock() == ACBlocks.omothol_stone
			|| state.getBlock() == ACBlocks.monolith_stone || state.getBlock() == Blocks.GRAVEL)
			return Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, 7);
		if(state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER)
			return Blocks.AIR.getDefaultState();
		if(state.getBlock() == ACBlocks.coralium_stone)
			return ACBlocks.liquid_coralium.getDefaultState();
		return null;
	}

	public RayTraceResult rayTrace(EntityPlayer player, World world, double blockReachDistance, float partialTicks)
	{
		Vec3d vec3d = player.getPositionEyes(partialTicks);
		Vec3d vec3d1 = player.getLook(partialTicks);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * blockReachDistance, vec3d1.yCoord * blockReachDistance, vec3d1.zCoord * blockReachDistance);
		return world.rayTraceBlocks(vec3d, vec3d2, true, false, true);
	}

	private Set<BlockPos> getPositions(BlockPos pos, EnumFacing facing){
		Set<BlockPos> stuff = Sets.newHashSet();
		stuff.add(pos);
		Axis a = facing.getAxis();
		if(a.isHorizontal()){
			stuff.add(pos.up());
			stuff.add(pos.down());
			if(a == Axis.Z){
				stuff.add(pos.east());
				stuff.add(pos.east().up());
				stuff.add(pos.east().down());
				stuff.add(pos.west());
				stuff.add(pos.west().up());
				stuff.add(pos.west().down());
			} else {
				stuff.add(pos.north());
				stuff.add(pos.north().up());
				stuff.add(pos.north().down());
				stuff.add(pos.south());
				stuff.add(pos.south().up());
				stuff.add(pos.south().down());
			}
		} else {
			for(EnumFacing face : Plane.HORIZONTAL.facings())
				stuff.add(pos.offset(face));
			stuff.add(pos.north().east());
			stuff.add(pos.north().west());
			stuff.add(pos.south().east());
			stuff.add(pos.south().west());
		}
		return stuff;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {

		RayTraceResult r = rayTrace(player, world, 16, 1);

		if(r != null && r.typeOfHit == Type.BLOCK) for(int i = 0; i < 3; i++)
			for(BlockPos pos2 : getPositions(r.getBlockPos().offset(r.sideHit.getOpposite(), i), r.sideHit.getOpposite())){
				IBlockState state = getRemains(world.getBlockState(pos2));
				if(state != null && world.isBlockModifiable(player, pos2)){
					if(i == 0)
						world.destroyBlock(pos2, false);
					world.setBlockState(pos2, state);
				}
			}
	}
}
