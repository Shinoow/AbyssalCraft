package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.structures.dreadlands.chagarothlair;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getItem() == ACItems.dreadlands_infused_gateway_key) //TODO replace
			if(world.provider.getDimension() == ACLib.dreadlands_id){
				if(world.getBiome(pos) == ACBiomes.dreadlands_mountains){
					if(pos.getY() == 41) {
						SpecialTextUtil.ChagarothGroup(world, TranslationUtil.toLocal("message.dreadaltartop.spawn"));
						chagarothlair lair = new chagarothlair();
						world.destroyBlock(pos, false);
						lair.generate(world, world.rand, pos);
						world.getChunk(pos).markDirty();
						if(!player.capabilities.isCreativeMode)
							stack.shrink(1);
					}
					else if(pos.getY() < 41)
						player.sendMessage(new TextComponentString("You still need to place the altar "+ (41 - pos.getY()) +" blocks higher."));
					else if(pos.getY() > 41)
						player.sendMessage(new TextComponentString("You still need to place the altar "+ (pos.getY() - 41) +" blocks lower."));
				} else player.sendMessage(new TextComponentTranslation("message.dreadaltar.error.2"));
			} else player.sendMessage(new TextComponentTranslation("message.dreadaltar.error.3"));
		return false;
	}
}
