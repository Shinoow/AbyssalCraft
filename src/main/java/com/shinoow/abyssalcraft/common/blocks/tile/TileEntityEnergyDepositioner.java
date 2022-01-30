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
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.*;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.common.items.ItemStoneTablet;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityEnergyDepositioner extends TileEntity implements IEnergyManipulator, ITickable, ISidedInventory {

	private AmplifierType currentAmplifier;
	private int tolerance;
	private float energy;
	private int processingTime;
	private ItemStack processingStack = ItemStack.EMPTY;
	private NonNullList<ItemStack> containerItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private Set<BlockPos> positions = new HashSet<>();

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		tolerance = nbttagcompound.getInteger("Tolerance");
		energy = nbttagcompound.getFloat("PotEnergy");
		PEUtils.readManipulatorNBT(this, nbttagcompound);
		ItemStackHelper.loadAllItems(nbttagcompound, containerItemStacks);
		processingTime = nbttagcompound.getInteger("ProcessingTime");
		processingStack = new ItemStack(nbttagcompound.getCompoundTag("ProcessingStack"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Tolerance", tolerance);
		nbttagcompound.setFloat("PotEnergy", energy);
		PEUtils.writeManipulatorNBT(this, nbttagcompound);
		ItemStackHelper.saveAllItems(nbttagcompound, containerItemStacks);
		nbttagcompound.setInteger("ProcessingTime", processingTime);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(!processingStack.isEmpty())
			processingStack.writeToNBT(nbtItem);
		nbttagcompound.setTag("ProcessingStack", nbtItem);

		return nbttagcompound;
	}

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public float getEnergyQuanta() {

		float f = isActive() ? 20 * Math.max(getAmplifier(AmplifierType.POWER), 1.0F) : 15;

		if(f < energy){
			energy -= f;
			markDirty();
			return f;
		} else {
			float ret = energy;
			energy = 0;
			markDirty();
			return ret;
		}
	}

	@Override
	public void setActive(AmplifierType amp, DeityType deity) {
		if(!isActive())
			setActiveAmplifier(amp);
	}

	@Override
	public boolean isActive() {

		return currentAmplifier != null;
	}

	@Override
	public DeityType getDeity(IBlockState state) {

		return null;
	}

	@Override
	public float getAmplifier(AmplifierType type){

		if(type == currentAmplifier)
			switch(type){
			case DURATION:
				return 2;
			case POWER:
				return 1.5F;
			case RANGE:
				return 4;
			default:
				return 0;
			}
		else return 0;
	}

	@Override
	public DeityType getActiveDeity() {

		return null;
	}

	@Override
	public AmplifierType getActiveAmplifier() {

		return currentAmplifier;
	}

	@Override
	public void setActiveDeity(DeityType deity) {}

	@Override
	public void setActiveAmplifier(AmplifierType amplifier) {

		currentAmplifier = amplifier;
	}

	@Override
	public void addTolerance(int num) {
		tolerance += num;
	}

	@Override
	public boolean canTransferPE() {

		return energy > 0;
	}

	@Override
	public int getTolerance() {

		return tolerance;
	}

	@Override
	public void disrupt() {
		tolerance = 0;
		if(!ACConfig.no_disruptions) {
			world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 1, pos.getZ(), true));
			DisruptionHandler.instance().generateDisruption(DeityType.values()[world.rand.nextInt(DeityType.values().length)], world, pos, world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(16, 16, 16)));
		}
	}

	@Override
	public Set<BlockPos> getEnergyCollectors(){
		return positions;
	}

	public float getContainedEnergy(){
		return energy;
	}

	@Override
	public void update() {

		if(isActive())
			world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0, 0, 0);
		else PEUtils.clearManipulatorData(this);

		boolean flag = false;

		if(canProcess()){

			++processingTime;
			energy += drainPEFromInput();
			if(energy > 10000) energy = 10000;

			if(processingTime == 200){

				processingTime = 0;
				processItem();
				flag = true;

			}
		} else processingTime = 0;

		if(flag)
			markDirty();

		if(PEUtils.checkForAdjacentManipulators(world, pos)) {
			if(world.getWorldTime() % 200 == 0)
				PEUtils.locateCollectors(world, pos, this);

			PEUtils.transferPEToCollectors(world, pos, this);
		}

		if(tolerance >= 200)
			disrupt();
	}

	private boolean canProcess(){

		if(!processingStack.isEmpty()) return true;

		if(containerItemStacks.get(0).isEmpty())
			return false;
		else {

			ItemStack stack = containerItemStacks.get(0);

			if(stack.getItem() instanceof ItemStoneTablet && !((ItemStoneTablet)stack.getItem()).isCursed(stack) &&
					((ItemStoneTablet)stack.getItem()).hasInventory(stack))
				processingStack = removeStackFromSlot(0);

			return !processingStack.isEmpty();
		}
	}

	private float drainPEFromInput(){

		if(processingStack.isEmpty()) return 0;

		if(!processingStack.hasTagCompound()) return 0;

		float f = processingStack.getTagCompound().getFloat("PotEnergy");

		return f / 200;
	}

	private void processItem(){

		float n = 0;
		List<BlockPos> positions = new ArrayList<>();

		for(int x = pos.getX() -8; x < pos.getX() + 9; x++)
			for(int z = pos.getZ() - 8; z < pos.getZ() + 9; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(world.getBiome(pos1) instanceof IDarklandsBiome){
					n++;
					continue;
				}

				positions.add(pos1);
			}

		for(BlockPos pos1 : positions){

			if(world.rand.nextInt(289) > n) continue;
			n++;

			for(int y = 0; y < 256; y++){
				IBlockState state = world.getBlockState(pos1.up(y));
				if(state.getBlock() == Blocks.STONE)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone.getDefaultState());
				else if(state.getBlock() == Blocks.LEAVES)
					world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, state.getValue(BlockLeaves.CHECK_DECAY)).withProperty(BlockLeaves.DECAYABLE, state.getValue(BlockLeaves.DECAYABLE)));
				else if(state.getBlock() == Blocks.LOG)
					world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_wood.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
				else if(state.getBlock() == Blocks.COBBLESTONE)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone.getDefaultState());
				else if(state.getBlock() == Blocks.STONEBRICK)
					switch(state.getValue(BlockStoneBrick.VARIANT)){
					case CHISELED:
						world.setBlockState(pos1.up(y), ACBlocks.chiseled_darkstone_brick.getDefaultState());
						break;
					case CRACKED:
						world.setBlockState(pos1.up(y), ACBlocks.cracked_darkstone_brick.getDefaultState());
						break;
					default:
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState());
						break;
					}
				else if(state.getBlock() == Blocks.COBBLESTONE_WALL)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_wall.getDefaultState());
				else if(state == ACBlocks.ritual_altar_stone.getDefaultState())
					world.setBlockState(pos1.up(y), ACBlocks.ritual_altar_darkstone.getDefaultState());
				else if(state == ACBlocks.ritual_pedestal_stone.getDefaultState())
					world.setBlockState(pos1.up(y), ACBlocks.ritual_pedestal_darkstone.getDefaultState());
				else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SMOOTHBRICK)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
				else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.COBBLESTONE)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
				else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
				else if(state.getBlock() == Blocks.DOUBLE_STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE)
					world.setBlockState(pos1.up(y), BlockHandler.Darkstoneslab2.getDefaultState());
				else if(state.getBlock() == Blocks.STONE_BRICK_STAIRS)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
				else if(state.getBlock() == Blocks.STONE_STAIRS)
					world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
				else if(state.getBlock() == Blocks.PLANKS)
					world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_planks.getDefaultState());
				else if(state.getBlock() == Blocks.OAK_STAIRS)
					world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
				else if(state.getBlock() == Blocks.WOODEN_SLAB)
					world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
				else if(state.getBlock() == Blocks.OAK_FENCE)
					world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_fence.getDefaultState());
			}

			BiomeUtil.updateBiome(world, pos1, ACBiomes.darklands);
		}

		if(canProcess()){
			ItemStack stack = processingStack;
			stack.getTagCompound().removeTag("ItemInventory");
			stack.getTagCompound().removeTag("PotEnergy");

			if(world.rand.nextFloat() < n/289)
				((ItemStoneTablet)stack.getItem()).setCursed(stack);

			containerItemStacks.set(1, stack);
			processingStack = ItemStack.EMPTY;
		}
	}

	@Override
	public String getName() {

		return "container.abyssalcraft.energydepositioner";
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public ITextComponent getDisplayName() {

		return new TextComponentTranslation(getName());
	}

	@Override
	public int getSizeInventory() {

		return containerItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : containerItemStacks)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return containerItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {

		return ItemStackHelper.getAndSplit(containerItemStacks, var1, var2);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return ItemStackHelper.getAndRemove(containerItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		containerItemStacks.set(index, stack);

		if(!stack.isEmpty() && stack.getCount() > getInventoryStackLimit())
			stack.setCount(getInventoryStackLimit());
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		return world.getTileEntity(pos) != this ? false : player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return stack.getItem() instanceof ItemStoneTablet;
	}

	@Override
	public int getField(int id) {

		switch(id){
		case 0:
			return processingTime;
		case 1:
			return (int)energy;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch(id){
		case 0:
			processingTime = value;
			break;
		case 1:
			energy = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {

		return side == EnumFacing.DOWN ? new int[]{1} : side == EnumFacing.UP ? new int[]{0} : new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

		return direction == EnumFacing.UP && index == 0;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {

		return direction == EnumFacing.DOWN && index == 1;
	}

	IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else if(facing == EnumFacing.UP)
				return (T) handlerTop;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (facing == EnumFacing.DOWN || facing == EnumFacing.UP) || super.hasCapability(capability, facing);
	}
}
