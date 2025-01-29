/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.energy;

import java.util.List;
import java.util.Set;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureBase;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureComponent;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

/**
 * Backbone for handling interactions between<br>
 * various PE mechanics
 * @author shinoow
 *
 */
public class PEUtils {

	/**
	 * Attempts to transfer PE from a Manipulator to nearby Players
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param manipulator PE Manipulator
	 * @param range Transfer Range
	 */
	public static void transferPEToNearbyPlayers(World world, BlockPos pos, IEnergyManipulator manipulator, int range){

		List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(range, range, range),
				EntityUtil::hasNecronomicon);

		for(EntityPlayer player : players){
			ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack item1 = player.getHeldItem(EnumHand.OFF_HAND);
			if(canStackAcceptPE(item) || canStackAcceptPE(item1))
				if(manipulator.canTransferPE()){
					transferPEToStack(item, manipulator);
					transferPEToStack(item1, manipulator);
					AbyssalCraftAPI.getInternalMethodHandler().spawnPEStream(pos, player, world.provider.getDimension());
				}
		}
	}

	private static boolean canStackAcceptPE(ItemStack stack) {
		if(stack.getItem() instanceof IEnergyTransporterItem)
			return ((IEnergyTransporterItem) stack.getItem()).canAcceptPEExternally(stack);
		else return false;
	}

	/**
	 * Attempts to transfer PE from a Manipulator to nearby Dropped Items<br>
	 * (that are Energy Container Items)
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param manipulator PE Manipulator
	 * @param range Transfer Range
	 */
	public static void transferPEToNearbyDroppedItems(World world, BlockPos pos, IEnergyManipulator manipulator, int range){

		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(range, range, range),
				e -> canStackAcceptPE(e.getItem()));

		for(EntityItem item : items)
			if(world.rand.nextInt(120-(int)(20 * manipulator.getAmplifier(AmplifierType.DURATION))) == 0)
				if(manipulator.canTransferPE()){
					transferPEToStack(item.getItem(), manipulator);
					AbyssalCraftAPI.getInternalMethodHandler().spawnPEStream(pos, item, world.provider.getDimension());
				}
	}

	/**
	 * Shortcut method for Transferring PE to an ItemStack
	 * @param stack ItemStack to potentially transfer PE to
	 * @param manipulator PE Manipulator to transfer said PE
	 */
	private static void transferPEToStack(ItemStack stack, IEnergyManipulator manipulator){
		if(stack.getItem() instanceof IEnergyTransporterItem)
			if(((IEnergyTransporterItem) stack.getItem()).canAcceptPEExternally(stack)){
				((IEnergyTransporterItem) stack.getItem()).addEnergy(stack, manipulator.getEnergyQuanta());
				manipulator.addTolerance(manipulator.isActive() ? 4 : 2);
			}
	}

	/**
	 * Attempts to transfer PE from a Manipulator to nearby Collectors
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param manipulator PE Manipulator
	 */
	public static void transferPEToCollectors(World world, BlockPos pos, IEnergyManipulator manipulator){
		int timeDiscount = (int)(20 * manipulator.getAmplifier(AmplifierType.DURATION));

		manipulator.getEnergyCollectors().stream().map(p -> world.getTileEntity(p)).filter(PEUtils::isCollector).forEach(tile -> {
			if(checkForAdjacentCollectors(world, tile.getPos()))
				if(world.rand.nextInt(120-timeDiscount) == 0)
					if(((IEnergyCollector) tile).canAcceptPE() && manipulator.canTransferPE()){
						((IEnergyCollector) tile).addEnergy(manipulator.getEnergyQuanta());
						manipulator.addTolerance(manipulator.isActive() ? 2 : 1);
						AbyssalCraftAPI.getInternalMethodHandler().spawnPEStream(pos, tile.getPos(), world.provider.getDimension());
					}
		});
	}

	/**
	 * Locates and stores the positions of Energy Containers in range of the Manipulator
	 * @param world Current World
	 * @param pos Current Position
	 * @param manipulator PE Manipulator
	 */
	public static void locateCollectors(World world, BlockPos pos, IEnergyManipulator manipulator) {
		manipulator.getEnergyCollectors().clear();

		Set<BlockPos> positions = manipulator.getEnergyCollectors();

		int boost = (int) (getRangeAmplifiers(world, pos, manipulator) + manipulator.getAmplifier(AmplifierType.RANGE)/2);

		MutableBlockPos pos1 = new MutableBlockPos();

		outer: for(int x = -1*(3+boost); x <= 3+boost; x++)
			for(int y = 0; y <= getRangeAmplifiers(world, pos, manipulator); y++)
				for(int z = -1*(3+boost); z <= 3+boost; z++)
					if(x < -2 || x > 2 || z < -2 || z > 2){
						if(positions.size() == 20)
							break outer;
						pos1.setPos(pos.getX() + x, pos.getY() - y, pos.getZ() + z);
						TileEntity te = world.getTileEntity(pos1);
						if(isCollector(te))
							positions.add(pos1.toImmutable());
					}
	}

	/**
	 * Utility method for clearing the active Deity and Amplifier<br>
	 * when a PE Manipulator no longer is active. Should be called in<br>
	 * update() ({@link ITickable}) whenever the manipulator isn't active<br>
	 * to ensure the data is properly erased when it should be.
	 * @param manipulator PE Manipulator to reset
	 */
	public static void clearManipulatorData(IEnergyManipulator manipulator){
		if(manipulator.getActiveAmplifier() != null || manipulator.getActiveDeity() != null){
			NBTTagCompound tag = new NBTTagCompound();
			((TileEntity) manipulator).writeToNBT(tag);
			tag.setString("ActiveDeity", "");
			tag.setString("ActiveAmplifier", "");
			manipulator.setActiveDeity(null);
			manipulator.setActiveAmplifier(null);
			((TileEntity) manipulator).readFromNBT(tag);
		}
	}

	/**
	 * Utility method for reading specific NBT data in a PE Manipulator.<br>
	 * Call in your TE's readFromNBT!
	 * @param manipulator PE Manipulator
	 * @param compound NBT Tag Compound
	 */
	public static void readManipulatorNBT(IEnergyManipulator manipulator, NBTTagCompound compound){
		if(compound.hasKey("ActiveDeity") && !compound.getString("ActiveDeity").equals(""))
			manipulator.setActiveDeity(DeityType.valueOf(compound.getString("ActiveDeity")));
		if(compound.hasKey("ActiveAmplifier") && !compound.getString("ActiveAmplifier").equals(""))
			manipulator.setActiveAmplifier(AmplifierType.valueOf(compound.getString("ActiveAmplifier")));
	}

	/**
	 * Utility method for writing specific NBT data in a PE Manipulator.<br>
	 * Call in your TE's writeToNBT!
	 * @param manipulator PE Manipulator
	 * @param compound NBT Tag Compound
	 */
	public static void writeManipulatorNBT(IEnergyManipulator manipulator, NBTTagCompound compound){
		if(manipulator.getActiveDeity() != null)
			compound.setString("ActiveDeity", manipulator.getActiveDeity().name());
		else compound.setString("ActiveDeity", "");
		if(manipulator.getActiveAmplifier() != null)
			compound.setString("ActiveAmplifier", manipulator.getActiveAmplifier().name());
		else compound.setString("ActiveAmplifier", "");
	}

	/**
	 * Checks for any range amplifying blocks below a specific BlockPos
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @return A number between 0 and 2, representing the amount of range amplifiers below this BlockPos
	 */
	public static int getRangeAmplifiers(World world, BlockPos pos, IEnergyManipulator manipulator){
		Block block1 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();
		Block block2 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ())).getBlock();
		int num = 0;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE)
			num = 1;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE
				&& block2 != null && block2 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block2).getAmplifierType() == AmplifierType.RANGE)
			num = 2;

		if(manipulator instanceof IStructureComponent)
			num += PEUtils.getStructureAmplifier(world, (IStructureComponent) manipulator, AmplifierType.RANGE);

		return num;
	}

	/**
	 * Checks if the Structure Component is currently part of a structure, then attempts to fetch any amplifiers
	 * @param world Current World
	 * @param component Component being checked
	 * @param type Amplifier Type to check for
	 * @return A value to amplify a stat with, or 0
	 */
	public static float getStructureAmplifier(World world, IStructureComponent component, AmplifierType type) {

		float amplifier = 0;

		if(component.isInMultiblock() && component.getBasePosition() != null) {
			if(world.getTileEntity(component.getBasePosition()) instanceof IStructureBase)
				amplifier = ((IStructureBase) world.getTileEntity(component.getBasePosition())).getAmplifier(type);
		} else {
			component.setInMultiblock(false);
			component.setBasePosition(null);
		}

		return amplifier;
	}

	/**
	 * Checks if a TileEntity is a PE Collector (extends IEnergyCollector)
	 * @param tile TileEntity to check
	 * @return True if the TileEntity is a PE Collector, otherwise false
	 */
	public static boolean isCollector(TileEntity tile){
		return tile instanceof IEnergyCollector;
	}

	/**
	 * Checks if a TileEntity is a PE Manipulator (extends IEnergyManipulator)
	 * @param tile TileEntity to check
	 * @return True if the TileEntity is a PE Manipulator, otherwise false
	 */
	public static boolean isManipulator(TileEntity tile){
		return tile instanceof IEnergyManipulator;
	}

	/**
	 * Checks if a TileEntity is a PE Container (extends IEnergyContainer)
	 * @param tile TileEntity to check
	 * @return True if the TileEntity is a PE Collector, otherwise false
	 */
	public static boolean isContainer(TileEntity tile){
		return tile instanceof IEnergyContainer;
	}

	/**
	 * Checks that the BlockPos has no adjacent PE Collectors
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @return True if the BlockPos has no adjacent PE Collectors, otherwise false
	 */
	public static boolean checkForAdjacentCollectors(World world, BlockPos pos){
		for(EnumFacing face : EnumFacing.values())
			if(isCollector(world.getTileEntity(pos.offset(face))))
				return false;
		return true;
	}

	/**
	 * Checks that the BlockPos has no adjacent PE Manipulators
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @return True if the BlockPos has no adjacent PE Manipulators, otherwise false
	 */
	public static boolean checkForAdjacentManipulators(World world, BlockPos pos){
		for(EnumFacing face : EnumFacing.values())
			if(isManipulator(world.getTileEntity(pos.offset(face))))
				return false;
		if(isManipulator(world.getTileEntity(pos.up(2)))) //Might remove this extra check
			return false;
		if(isManipulator(world.getTileEntity(pos.down(2)))) // ^
			return false;
		return true;
	}

	/**
	 * Checks for anything accepting PE in the given direction
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param face Direction to check
	 * @param dist Transport distance
	 * @return True if a PE Container is found, otherwise false
	 */
	public static boolean canTransfer(World world, BlockPos pos, EnumFacing face, int dist){

		for(int i = 1; i < dist+1; i++){
			if(world.getBlockState(pos.offset(face, i)).isFullCube() && !world.isAirBlock(pos.offset(face, i))) return false;
			if(isContainer(world.getTileEntity(pos.offset(face, i))))
				return ((IEnergyContainer) world.getTileEntity(pos.offset(face, i))).canAcceptPE();
		}
		return false;
	}

	/**
	 * Looks for a PE Collector in the given direction
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param face Direction to check
	 * @param dist Transport distance
	 * @return A PE Collector if one is found, otherwise null
	 */
	public static IEnergyCollector getCollector(World world, BlockPos pos, EnumFacing face, int dist){
		for(int i = 1; i < dist+1; i++)
			if(isCollector(world.getTileEntity(pos.offset(face, i))))
				return (IEnergyCollector) world.getTileEntity(pos.offset(face, i));
		return null;
	}

	/**
	 * Looks for a PE Container in the given distance
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param face Direction to check
	 * @param dist Transport distance
	 * @return A TileEntity that can accept PE, otherwise null
	 */
	public static IEnergyContainer getContainer(World world, BlockPos pos, EnumFacing face, int dist){
		for(int i = 1; i < dist+1; i++)
			if(isContainer(world.getTileEntity(pos.offset(face, i))))
				return (IEnergyContainer) world.getTileEntity(pos.offset(face, i));
		return null;
	}

	/**
	 * Attempts to collect PE from nearby PE Collectors
	 * @param relay PE Relay
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param face Direction to collect from
	 * @param amount Amount of PE to drain
	 */
	public static void collectNearbyPE(IEnergyTransporter relay, World world, BlockPos pos, EnumFacing face, float amount){
		if(relay.canAcceptPE()){
			IEnergyContainer container = getContainer(world, pos, face, 1);
			if(container != null && container.canTransferPE())
				container.addEnergy(relay.addEnergy(container.consumeEnergy(amount)));

		}
	}

	/**
	 * Gets the current contained PE from a ItemStack<br>
	 * Unless you need special code, this can be called in {@link IEnergyContainerItem#getContainedEnergy(ItemStack)}
	 * @param stack ItemStack containing energy
	 * @return The contained energy, if any
	 */
	public static float getContainedEnergy(ItemStack stack){
		float energy;
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(stack.getTagCompound().hasKey("PotEnergy"))
			energy = stack.getTagCompound().getFloat("PotEnergy");
		else {
			energy = 0;
			stack.getTagCompound().setFloat("PotEnergy", energy);
		}
		return energy;
	}

	/**
	 * Adds PE to the supplied ItemStack<br>
	 * Unless you need special code, this can be called in {@link IEnergyContainerItem#addEnergy(ItemStack, float)}
	 * @param container PE container Item instance
	 * @param stack ItemStack containing the Item
	 * @param energy Energy quanta to add
	 * @return Energy overflow, if any
	 */
	public static float addEnergy(IEnergyContainerItem container, ItemStack stack, float energy){
		float total = container.getContainedEnergy(stack) + energy;
		if(total >= container.getMaxEnergy(stack)) {
			float ret = total - container.getMaxEnergy(stack);
			stack.getTagCompound().setFloat("PotEnergy", container.getMaxEnergy(stack));
			return ret; //overflow goes here
		}
		stack.getTagCompound().setFloat("PotEnergy", total);
		return 0;
	}

	/**
	 * Consumes PE from the supplied ItemStack<br>
	 * Unless you need special code, this can be called in {@link IEnergyContainerItem#consumeEnergy(ItemStack, float)}
	 * @param stack ItemStack containing energy
	 * @param energy Energy quanta to consume
	 * @return The amount of energy consumed
	 */
	public static float consumeEnergy(ItemStack stack, float energy){
		float contained = getContainedEnergy(stack);
		if(energy < contained){
			stack.getTagCompound().setFloat("PotEnergy", contained -= energy);
			return energy;
		} else {
			stack.getTagCompound().setFloat("PotEnergy", 0);
			return contained;
		}
	}

	/**
	 * Adds PE to the supplied Energy Container<br>
	 * Unless you need special code, this can be called in {@link IEnergyContainer#addEnergy(float)}
	 * @param container PE container Item instance
	 * @param stack ItemStack containing the Item
	 * @param energy Energy quanta to add
	 * @return Energy overflow, if any
	 */
	public static float addEnergy(IEnergyContainer container, float energy){
		float total = container.getContainedEnergy() + energy;
		if(total >= container.getMaxEnergy()) {
			float ret = total - container.getMaxEnergy();
			container.setEnergy(container.getMaxEnergy());
			return ret; //overflow goes here
		}
		container.setEnergy(total);
		return 0;
	}

	/**
	 * Consumes PE from the supplied Energy Container<br>
	 * Unless you need special code, this can be called in {@link IEnergyContainer#consumeEnergy(float)}
	 * @param stack ItemStack containing energy
	 * @param energy Energy quanta to consume
	 * @return The amount of energy consumed
	 */
	public static float consumeEnergy(IEnergyContainer container, float energy){
		float contained = container.getContainedEnergy();
		if(energy < contained){
			container.setEnergy(contained -= energy);
			return energy;
		} else {
			container.setEnergy(0);
			return contained;
		}
	}

	/**
	 * Transfers PE from an IEnergyContainerItem to a IEnergyContainer (that former likely is contained in)
	 * @param stack ItemStack containing the PE container Item
	 * @param container PE Container
	 * @param amount Amount of PE to transfer
	 */
	public static void transferPEToContainer(ItemStack stack, IEnergyContainer container, float amount) {
		if(!stack.isEmpty() && stack.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem containerItem = (IEnergyContainerItem) stack.getItem();
			if(containerItem.canTransferPE(stack) && container.canAcceptPE())
				containerItem.addEnergy(stack, container.addEnergy(containerItem.consumeEnergy(stack, amount))); // returns overflow
		}
	}

	/**
	 * Transfers PE from an IEnergyContainer to a IEnergyContainerItem (that the former likely contains)
	 * @param stack ItemStack containing the PE container Item
	 * @param container PE Container
	 * @param amount Amount of PE to transfer
	 */
	public static void transferPEFromContainer(ItemStack stack, IEnergyContainer container, float amount) {
		if(!stack.isEmpty() && stack.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem containerItem = (IEnergyContainerItem) stack.getItem();
			if(containerItem.canAcceptPE(stack) && container.canTransferPE())
				container.addEnergy(containerItem.addEnergy(stack, container.consumeEnergy(amount))); // returns overflow
		}
	}

	/**
	 * Drains PE from an IEnergyTransporterItem
	 * @param stack ItemStack containing the PE container Item
	 * @param amount Amount of PE to drain
	 * @return Amount of PE drained
	 */
	public static float drainPEFromItem(ItemStack stack, float amount) {
		if(!stack.isEmpty() && stack.getItem() instanceof IEnergyTransporterItem) {
			IEnergyTransporterItem containerItem = (IEnergyTransporterItem) stack.getItem();
			if(containerItem.canTransferPEExternally(stack))
				return containerItem.consumeEnergy(stack, amount);
		}
		return 0;
	}
}
