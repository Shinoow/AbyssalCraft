package com.shinoow.abyssalcraft.common.structures.pe;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.structure.IPlaceOfPower;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureBase;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureComponent;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.common.blocks.BlockACStone;
import com.shinoow.abyssalcraft.common.blocks.BlockACStone.EnumStoneType;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArchwayStructure implements IPlaceOfPower {

	private IBlockState[][][] data;
	
	public ArchwayStructure() {
		
		data = new IBlockState[][][] {
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}, new IBlockState[] {null}, new IBlockState[] {ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.MONOLITH_STONE)},
				new IBlockState[] {null}, new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}, new IBlockState[] {null}, new IBlockState[] {ACBlocks.statue.getDefaultState()}, new IBlockState[] {null},
					new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}, new IBlockState[] {null}, new IBlockState[] {null}, new IBlockState[] {null},
						new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, EnumHalf.TOP)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState().withProperty(BlockSlab.HALF, EnumBlockHalf.TOP)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, EnumHalf.TOP)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST)}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()}, new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()},
								new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()}, new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()},
								new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()}}};
	}
	
	@Override
	public String getIdentifier() {

		return "archway";
	}

	@Override
	public int getBookType() {

		return 0;
	}

	@Override
	public IUnlockCondition getUnlockCondition() {

		return new DefaultCondition();
	}

	@Override
	public String getDescription() {

		return "ac.structure.archway.description";
	}

	@Override
	public float getAmplifier(AmplifierType type) {

		return 0;
	}

	@Override
	public void construct(World world, BlockPos pos) {
		world.setBlockState(pos, ACBlocks.multi_block.getDefaultState());
		((IStructureBase) world.getTileEntity(pos)).setMultiblock(this);
		if(world.getTileEntity(pos.up()) instanceof IStructureComponent) {
			((IStructureComponent) world.getTileEntity(pos.up())).setInMultiblock(true);
			((IStructureComponent) world.getTileEntity(pos.up())).setBasePosition(pos);
		}
	}

	@Override
	public void validate(World world, BlockPos pos) {
		
	}

	@Override
	public boolean canConstruct(World world, BlockPos pos, EntityPlayer player) {

		return false;
	}

	@Override
	public IBlockState[][][] getRenderData() {

		return data;
	}

	@Override
	public BlockPos getActivationPointForRender() {

		return new BlockPos(2, 0, 0);
	}

}
