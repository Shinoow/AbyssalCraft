/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRitualAltar;

public class BlockRitualAltar extends BlockContainer {

	private static HashMap<Integer, Block> blockMeta = Maps.newHashMap();

	public BlockRitualAltar() {
		super(Material.rock);
		setHardness(6.0F);
		setResistance(12.0F);
		setStepSound(Block.soundTypeStone);
		setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 1.0F, 0.85F);
		setBlockTextureName("anvil_top_damaged_0");
		setCreativeTab(null);
		setLightLevel(0.375F);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 5));
		par3List.add(new ItemStack(par1, 1, 6));
		par3List.add(new ItemStack(par1, 1, 7));
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
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {

		return new TileEntityRitualAltar();
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return Item.getItemFromBlock(blockMeta.get(j));
	}

	@Override
	public int getRenderType() {
		return -7;
	}

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
		int timer = 0;
		TileEntity altar = par1World.getTileEntity(par2, par3, par4);
		if(altar instanceof TileEntityRitualAltar)
			timer = ((TileEntityRitualAltar)altar).getRitualCooldown();

		if(AbyssalCraft.particleBlock){
			par1World.spawnParticle("flame", par2 + 0.75, par3 + 1.05, par4 + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", par2 + 0.25, par3 + 1.05, par4 + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", par2 + 0.25, par3 + 1.05, par4 + 0.25, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", par2 + 0.75, par3 + 1.05, par4 + 0.25, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("smoke", par2 + 0.75, par3 + 1.05, par4 + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("smoke", par2 + 0.25, par3 + 1.05, par4 + 0.75, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("smoke", par2 + 0.25, par3 + 1.05, par4 + 0.25, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("smoke", par2 + 0.75, par3 + 1.05, par4 + 0.25, 0.0D, 0.0D, 0.0D);
			if(timer < 200 && timer > 0){
				par1World.spawnParticle("lava", par2 + 0.5, par3 + 1, par4 + 0.5, 0,0,0);

				for(double i = 0; i <= 0.7; i += 0.03) {
					double x = i * Math.cos(i) / 2, z = i * Math.sin(i) / 2, o = i * Math.tan(i) / 2;
					par1World.spawnParticle("largesmoke", par2 - 2.5, par3 + 0.95, par4 + 0.5, x,0,0);
					par1World.spawnParticle("largesmoke", par2 + 0.5, par3 + 0.95, par4 - 2.5, 0,0,z);
					par1World.spawnParticle("largesmoke", par2 + 3.5, par3 + 0.95, par4 + 0.5, -x,0,0);
					par1World.spawnParticle("largesmoke", par2 + 0.5, par3 + 0.95, par4 + 3.5, 0,0,-z);
					par1World.spawnParticle("largesmoke", par2 - 1.5, par3 + 0.95, par4 + 2.5, o,0,-o);
					par1World.spawnParticle("largesmoke", par2 - 1.5, par3 + 0.95, par4 - 1.5, o,0,o);
					par1World.spawnParticle("largesmoke", par2 + 2.5, par3 + 0.95, par4 + 2.5, -o,0,-o);
					par1World.spawnParticle("largesmoke", par2 + 2.5, par3 + 0.95, par4 - 1.5, -o,0,o);
				}

				par1World.spawnParticle("flame", par2 - 2.5, par3 + 1.05, par4 + 0.5, 0,0,0);
				par1World.spawnParticle("flame", par2 + 0.5, par3 + 1.05, par4 - 2.5, 0,0,0);
				par1World.spawnParticle("flame", par2 + 3.5, par3 + 1.05, par4 + 0.5, 0,0,0);
				par1World.spawnParticle("flame", par2 + 0.5, par3 + 1.05, par4 + 3.5, 0,0,0);
				par1World.spawnParticle("flame", par2 - 1.5, par3 + 1.05, par4 + 2.5, 0,0,0);
				par1World.spawnParticle("flame", par2 - 1.5, par3 + 1.05, par4 - 1.5, 0,0,0);
				par1World.spawnParticle("flame", par2 + 2.5, par3 + 1.05, par4 + 2.5, 0,0,0);
				par1World.spawnParticle("flame", par2 + 2.5, par3 + 1.05, par4 - 1.5, 0,0,0);

				par1World.spawnParticle("smoke", par2 - 2.5, par3 + 1.05, par4 + 0.5, 0,0,0);
				par1World.spawnParticle("smoke", par2 + 0.5, par3 + 1.05, par4 - 2.5, 0,0,0);
				par1World.spawnParticle("smoke", par2 + 3.5, par3 + 1.05, par4 + 0.5, 0,0,0);
				par1World.spawnParticle("smoke", par2 + 0.5, par3 + 1.05, par4 + 3.5, 0,0,0);
				par1World.spawnParticle("smoke", par2 - 1.5, par3 + 1.05, par4 + 2.5, 0,0,0);
				par1World.spawnParticle("smoke", par2 - 1.5, par3 + 1.05, par4 - 1.5, 0,0,0);
				par1World.spawnParticle("smoke", par2 + 2.5, par3 + 1.05, par4 + 2.5, 0,0,0);
				par1World.spawnParticle("smoke", par2 + 2.5, par3 + 1.05, par4 - 1.5, 0,0,0);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityRitualAltar)
			if(((TileEntityRitualAltar)tile).getItem() != null){
				player.inventory.addItemStackToInventory(((TileEntityRitualAltar)tile).getItem());
				((TileEntityRitualAltar)tile).setItem(null);
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
				return true;
			} else {
				ItemStack heldItem = player.getHeldItem();
				if(heldItem != null){
					ItemStack newItem = heldItem.copy();
					newItem.stackSize = 1;
					((TileEntityRitualAltar)tile).setItem(newItem);
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
					return true;
				}
			}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		Random rand = new Random();
		TileEntityRitualAltar altar = (TileEntityRitualAltar) world.getTileEntity(x, y, z);

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

	static {
		blockMeta.put(0, Blocks.cobblestone);
		blockMeta.put(1, AbyssalCraft.Darkstone_cobble);
		blockMeta.put(2, AbyssalCraft.abybrick);
		blockMeta.put(3, AbyssalCraft.cstonebrick);
		blockMeta.put(4, AbyssalCraft.dreadbrick);
		blockMeta.put(5, AbyssalCraft.abydreadbrick);
		blockMeta.put(6, AbyssalCraft.ethaxiumbrick);
		blockMeta.put(7, AbyssalCraft.darkethaxiumbrick);
	}
}
