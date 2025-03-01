package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.common.entity.EntitySpiritItem;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.DisplayRoutesMessage;
import com.shinoow.abyssalcraft.lib.util.SpiritItemUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntitySpiritAltar extends TileEntity implements ITickable {

	Set<BlockPos> positions = new HashSet<>();
	List<TileEntity> tileEntites = new ArrayList<>();

	boolean enabled;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		enabled = nbttagcompound.getBoolean("Enabled");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Enabled", enabled);

		return nbttagcompound;
	}
	
	@Override
	public void onLoad() {

		if(world.isRemote)
			world.tickableTileEntities.remove(this);

		calculatePositions();
	}

	@Override
	public void update() {
		if(world.getTotalWorldTime() % 400 == 0) {
			positions.removeIf(p -> !SpiritItemUtil.validPos(p, world));

			if(positions.size() != tileEntites.size()) {
				tileEntites = positions.stream().map(p -> world.getTileEntity(p))
						.collect(Collectors.toList());
			}
		}
		if(world.getTotalWorldTime() % 20 != 0) return;
		if(tileEntites.isEmpty()) return;

		for(TileEntity tile : tileEntites) {
			if(!world.isBlockLoaded(tile.getPos())) continue;
			IItemTransferCapability cap = ItemTransferCapability.getCap(tile);
			if(cap == null || !cap.isRunning()) continue;

			for(ItemTransferConfiguration cfg : cap.getTransferConfigurations()) {
				IItemHandler inventory = SpiritItemUtil.getInventory(tile, cfg.getExitFacing());
				if(inventory != null) {//sided inventories, you never know
					boolean hasFilter = !cfg.getFilter().isEmpty() && cfg.getFilter().stream().anyMatch(i -> !i.isEmpty());
					ItemStack stack = ItemStack.EMPTY;
					int slot = -1;
					for(int i = 0; i < inventory.getSlots(); i++) {
						stack = inventory.getStackInSlot(i);
						if(!stack.isEmpty())
							if(!hasFilter || isInFilter(cfg.getFilter(), stack, cfg.filterByNBT())) {
								stack = inventory.extractItem(i, 1, true);
								slot = i;
								break;
							}
					}
					if(!stack.isEmpty() && slot > -1) {
						BlockPos exitPos = cfg.getRoute()[cfg.getRoute().length-1];
						TileEntity te = world.getTileEntity(exitPos);
						if(te != null) {
							IItemHandler exitInv = SpiritItemUtil.getInventory(te, cfg.getEntryFacing());
							if(exitInv != null && ItemHandlerHelper.insertItem(exitInv, stack, true).isEmpty()) {//insertion worked
								stack = inventory.extractItem(slot, 1, false);
								BlockPos pos = tile.getPos();
								EntitySpiritItem spirit = new EntitySpiritItem(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, stack.copy());
								spirit.setRoute(cfg.getRoute());
								spirit.setFacing(cfg.getEntryFacing());
								world.spawnEntity(spirit);
							}
						}
					}
				}
			}
		}
	}

	private void calculatePositions() {
		positions.clear();
		tileEntites.clear();

		MutableBlockPos pos1 = new MutableBlockPos();

		for(int x = pos.getX() - 16; x < pos.getX() + 16; x++)
			for(int y = pos.getY() - 16; y < pos.getY() + 16; y++)
				for(int z = pos.getZ() - 16; z < pos.getZ() + 16; z++) {
					pos1.setPos(x, y, z);
					if(!world.isBlockLoaded(pos1)) continue;
					TileEntity te = world.getTileEntity(pos1);
					if(te != null && SpiritItemUtil.hasInventory(te)) {
						positions.add(pos1.toImmutable());
						tileEntites.add(te);
					}
				}
	}

	public void spiritTabletToggle(EntityPlayer player, int mode) {
		if(world.isRemote) return;
		if(player.isSneaking()) {

			List<BlockPos[]> routes = new ArrayList<>();

			for(TileEntity tile : tileEntites) {
				if(!world.isBlockLoaded(tile.getPos())) continue;
				IItemTransferCapability cap = ItemTransferCapability.getCap(tile);
				if(cap == null) continue;
				for(ItemTransferConfiguration cfg : cap.getTransferConfigurations()) {
					List<BlockPos> route = Lists.asList(tile.getPos(), cfg.getRoute());
					routes.add(route.toArray(new BlockPos[0]));
				}
			}
			PacketDispatcher.sendTo(new DisplayRoutesMessage(routes), (EntityPlayerMP)player);

		}
		if(mode == 0) {
			calculatePositions();
			player.sendStatusMessage(new TextComponentString("Scanned for nearby containers"), true);
		}
		else if(mode == 1) {
			enabled = enabled ? false : true;
			toggleSpirits(enabled);
			((WorldServer)world).spawnParticle(enabled? EnumParticleTypes.VILLAGER_HAPPY : EnumParticleTypes.VILLAGER_ANGRY, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 1, 0, 0, 0, 1.0);
			player.sendStatusMessage(new TextComponentString(enabled ? "Enabled Spirits" : "Disabled Spirits"), true);
		}
		else if(mode == 2) {
			clearSpirits();
			player.sendMessage(new TextComponentTranslation("message.configurator.4"));
		}
	}

	private void toggleSpirits(boolean enable) {
		for(TileEntity tile : tileEntites) {
			if(!world.isBlockLoaded(tile.getPos())) continue;
			IItemTransferCapability cap = ItemTransferCapability.getCap(tile);
			if(cap == null) continue;
			cap.setRunning(enable);
		}
	}

	private void clearSpirits() {
		for(TileEntity tile : tileEntites) {
			if(!world.isBlockLoaded(tile.getPos())) continue;
			IItemTransferCapability cap = ItemTransferCapability.getCap(tile);
			if(cap == null) continue;
			cap.clearConfigurations();
		}
	}

	private boolean isInFilter(NonNullList<ItemStack> filter, ItemStack stack, boolean nbt) {

		for(ItemStack stack1 : filter)
			if(APIUtils.areStacksEqual(stack, stack1, nbt))
				return true;

		return false;
	}

}
