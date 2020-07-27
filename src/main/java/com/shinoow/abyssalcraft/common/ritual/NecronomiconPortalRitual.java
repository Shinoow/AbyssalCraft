package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.entity.EntityPortal;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecronomiconPortalRitual extends NecronomiconRitual {

	public NecronomiconPortalRitual() {
		super("portal", 0, 1000F, new Object[] {Blocks.DIRT});
		sacrifice = new ItemStack[] {new ItemStack(ACItems.gateway_key), new ItemStack(ACItems.dreaded_gateway_key),
				new ItemStack(ACItems.rlyehian_gateway_key)};
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
		
		if(!stack.isEmpty()) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			int id = stack.getTagCompound().getInteger("dimension");
			if(id == world.provider.getDimension())
				return false;
			
			DimensionData data = DimensionDataRegistry.instance().getDataForDim(id);
			
			if(data == null)
				return false;
			
			//TODO add gateway key override check around here
			if(data.getConnectedDimensions().isEmpty() || data.getConnectedDimensions().contains(world.provider.getDimension()))
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
			int id = stack.getTagCompound().getInteger("dimension");
			
			EntityPortal portal = new EntityPortal(world);
			portal.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
			portal.setDestination(id);
			world.spawnEntity(portal);
		}
	}

}
