package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntitySpiritAltar;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockSpiritAltar extends BlockContainer {

	public BlockSpiritAltar() {
		super(Material.IRON);
		setHardness(2.5F);
		setResistance(20.0F);
		setSoundType(SoundType.METAL);
		setCreativeTab(ACTabs.tabDecoration);
		setTranslationKey("spirit_altar");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntitySpiritAltar();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

}
