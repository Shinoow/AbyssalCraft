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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.common.blocks.BlockACCobblestone;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntropySpell extends Spell {

	public EntropySpell() {
		super("entropy", 0, Items.COAL, new ItemStack(ACBlocks.darkstone_cobblestone));
		setParchment(new ItemStack(ACItems.scroll, 1, 3));
		setColor(0x171f68);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		RayTraceResult r = rayTrace(player, world, 16, 1);
		if(r != null && r.typeOfHit == Type.BLOCK)
			return getDegradation(world.getBlockState(r.getBlockPos())) != null;
		return false;
	}

	private IBlockState getDegradation(IBlockState state){
		if(state.getBlock() == ACBlocks.darkstone)
			return ACBlocks.darkstone_cobblestone.getDefaultState();
		else if(state.getBlock() == ACBlocks.abyssal_stone)
			return ACBlocks.abyssal_cobblestone.getDefaultState();
		else if(state.getBlock() == ACBlocks.coralium_stone)
			return ACBlocks.coralium_cobblestone.getDefaultState();
		else if(state.getBlock() == ACBlocks.dreadstone)
			return ACBlocks.dreadstone_cobblestone.getDefaultState();
		else if(state.getBlock() == ACBlocks.abyssalnite_stone)
			return ACBlocks.abyssalnite_cobblestone.getDefaultState();
		else if(state.getBlock() == Blocks.STONE)
			return Blocks.COBBLESTONE.getDefaultState();
		else if(state.getBlock() instanceof BlockACCobblestone || state.getBlock() == Blocks.COBBLESTONE)
			return Blocks.GRAVEL.getDefaultState();
		else if(state.getBlock() == Blocks.GRAVEL)
			return Blocks.SAND.getDefaultState();
		else if(state.getBlock() == Blocks.SAND)
			return Blocks.AIR.getDefaultState();
		return null;
	}

	public RayTraceResult rayTrace(EntityPlayer player, World world, double blockReachDistance, float partialTicks)
	{
		Vec3d vec3d = player.getPositionEyes(partialTicks);
		Vec3d vec3d1 = player.getLook(partialTicks);
		Vec3d vec3d2 = vec3d.add(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
		return world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {

		RayTraceResult r = rayTrace(player, world, 16, 1);

		if(r != null && r.typeOfHit == Type.BLOCK){
			IBlockState state = getDegradation(world.getBlockState(r.getBlockPos()));
			if(state != null && world.isBlockModifiable(player, r.getBlockPos())){
				world.destroyBlock(r.getBlockPos(), false);
				world.setBlockState(r.getBlockPos(), state);
				AbyssalCraftAPI.getInternalMethodHandler().spawnPEStream(pos, r.getBlockPos(), world.provider.getDimension());
				for(int i = 0; i < 9; i++){
					ItemStack stack = player.inventory.getStackInSlot(i);
					if(stack.getItem() instanceof IEnergyContainerItem){
						((IEnergyContainerItem)stack.getItem()).addEnergy(stack, 5 + world.rand.nextInt(5));
						break;
					}
				}
			}
		}

	}

}
