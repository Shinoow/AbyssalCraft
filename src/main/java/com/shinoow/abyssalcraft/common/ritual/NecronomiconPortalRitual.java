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
package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.blocks.BlockPortalAnchor;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPortalAnchor;
import com.shinoow.abyssalcraft.common.entity.EntityPortal;
import com.shinoow.abyssalcraft.common.items.ItemGatewayKey;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecronomiconPortalRitual extends NecronomiconRitual {

	public NecronomiconPortalRitual() {
		super("portal", 0, 1000F, new Object[] {ACItems.shadow_gem, null, ACItems.shadow_gem, null,
				ACItems.shadow_gem, null, ACItems.shadow_gem});
		sacrifice = new ItemStack[] {new ItemStack(ACItems.gateway_key), new ItemStack(ACItems.dreaded_gateway_key),
				new ItemStack(ACItems.rlyehian_gateway_key)};
		setRitualParticle(EnumRitualParticle.SMOKE_PILLARS);
	}

	@Override
	public boolean requiresItemSacrifice(){
		return true;
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		TileEntity altar = world.getTileEntity(pos);

		ItemStack stack = ItemStack.EMPTY;

		if(altar instanceof IRitualAltar)
			stack = ((IRitualAltar) altar).getItem();

		if(!stack.isEmpty() && stack.getItem() instanceof ItemGatewayKey) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			int id = stack.getTagCompound().getInteger("Dimension");
			if(id == world.provider.getDimension())
				return false;

			int key = ((ItemGatewayKey)stack.getItem()).getKeyType();

			DimensionData data = DimensionDataRegistry.instance().getDataForDim(id);

			if(data == null)
				return false;

			if(DimensionDataRegistry.instance().areDimensionsConnected(world.provider.getDimension(), id, key))
				return true;
		}

		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {

		TileEntity altar = world.getTileEntity(pos);

		ItemStack stack = ItemStack.EMPTY;

		if(altar instanceof IRitualAltar)
			stack = ((IRitualAltar) altar).getItem();

		if(!stack.isEmpty()) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			int id = stack.getTagCompound().getInteger("Dimension");

			EntityPortal portal = new EntityPortal(world);
			portal.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
			portal.setDestination(id);
			world.spawnEntity(portal);
			if(!player.addItemStackToInventory(stack))
				player.dropItem(stack, false);
			((IRitualAltar) altar).setItem(ItemStack.EMPTY);
			((IRitualAltar) altar).getPedestals()
			.stream()
			.map(p -> ((TileEntity) p).getPos())
			.forEach(p -> {
				world.destroyBlock(p, false);
				world.setBlockState(p, ACBlocks.monolith_pillar.getDefaultState());
			});
			world.destroyBlock(pos, false);
			world.setBlockState(pos, ACBlocks.portal_anchor.getDefaultState().withProperty(BlockPortalAnchor.ACTIVE, true), 2);

			((TileEntityPortalAnchor) world.getTileEntity(pos)).setDestination(id);
		}
	}

}
