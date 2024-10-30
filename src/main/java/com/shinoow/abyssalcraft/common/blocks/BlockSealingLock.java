package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.common.structures.dreadlands.chagarothlair;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSealingLock extends BlockACBasic {

	public BlockSealingLock() {
		super(Material.ROCK, 2.5F, 20.0F, SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setTranslationKey("sealing_lock");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1World, BlockPos pos)
	{
		return new AxisAlignedBB(0.20F, 0.0F, 0.20F, 0.8F, 0.95F, 0.8F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9) {
		if(par1World.isRemote) return true;
		if(par1World.provider.getDimension() == ACLib.dreadlands_id){
			if(par1World.getBiome(pos) == ACBiomes.dreadlands_mountains){
				if(pos.getY() == 41) {
					SpecialTextUtil.ChagarothGroup(par1World, TranslationUtil.toLocal("message.dreadaltartop.spawn"));
					chagarothlair lair = new chagarothlair();
					par1World.destroyBlock(pos, false);
					lair.generate(par1World, par1World.rand, pos);
					par1World.getChunk(pos).markDirty();
				}
				else if(pos.getY() < 41)
					par5EntityPlayer.sendMessage(new TextComponentString("You still need to place the altar "+ (41 - pos.getY()) +" blocks higher."));
				else if(pos.getY() > 41)
					par5EntityPlayer.sendMessage(new TextComponentString("You still need to place the altar "+ (pos.getY() - 41) +" blocks lower."));
			} else par5EntityPlayer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.2"));
		} else par5EntityPlayer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.3"));
		return false;
	}
}
