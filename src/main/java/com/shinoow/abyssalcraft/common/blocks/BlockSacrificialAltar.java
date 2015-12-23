package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntitySacrificialAltar;

public class BlockSacrificialAltar extends BlockContainer {

	public BlockSacrificialAltar(){
		super(Material.rock);
		setHardness(6.0F);
		setResistance(12.0F);
		setBlockName("sacrificialaltar");
		setStepSound(Block.soundTypeStone);
		setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F);
		setBlockTextureName("abyssalcraft:monolithStone");
		setCreativeTab(AbyssalCraft.tabDecoration);
//		setLightLevel(0.375F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileEntitySacrificialAltar();
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		super.randomDisplayTick(world, x, y, z, random);

		if(AbyssalCraft.particleBlock){
			world.spawnParticle("flame", x + 0.75, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", x + 0.25, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", x + 0.25, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", x + 0.75, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x + 0.75, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x + 0.25, y + 1.05, z + 0.75, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x + 0.25, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x + 0.75, y + 1.05, z + 0.25, 0.0D, 0.0D, 0.0D);
		}
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntitySacrificialAltar){
			int timer = ((TileEntitySacrificialAltar) tile).getCooldownTimer();
			
			if(timer > 0){
				world.spawnParticle("lava", x + 0.5, y + 1, z + 0.5, 0, 0, 0);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntitySacrificialAltar)
			if(((TileEntitySacrificialAltar)tile).getItem() != null){
				player.inventory.addItemStackToInventory(((TileEntitySacrificialAltar)tile).getItem());
				((TileEntitySacrificialAltar)tile).setItem(null);
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
				return true;
			} else {
				ItemStack heldItem = player.getHeldItem();
				if(heldItem != null){
					ItemStack newItem = heldItem.copy();
					newItem.stackSize = 1;
					((TileEntitySacrificialAltar)tile).setItem(newItem);
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
					return true;
				}
			}
		return false;
	}

	@Override
	public int getRenderType() {
		return -7;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		Random rand = new Random();
		TileEntitySacrificialAltar altar = (TileEntitySacrificialAltar) world.getTileEntity(x, y, z);

		if(altar != null)
			if(altar.getItem() != null){
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, altar.getItem());
				float f3 = 0.05F;
				item.motionX = (float)rand.nextGaussian() * f3;
				item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f3;
				world.spawnEntityInWorld(item);
			}

		super.breakBlock(world, x, y, z, block, meta);
	}
}