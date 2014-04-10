package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPSDL;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.Entitydreadgolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadguard;

public class PSDL extends BlockContainer {

	public PSDL()
	{
		super(Material.rock);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.8F, 0.7F, 0.8F);
		this.setHarvestLevel("pickaxe", 5);
	}


	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPSDL();
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

		if (par5Random.nextInt(10) == 0)
		{
			par1World.spawnParticle("largesmoke", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + 1.1F), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
		if(par5Entity instanceof EntityLivingBase){
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100, 1));
			if(par5Entity instanceof Entitydreadguard || par5Entity instanceof EntityDreadling || par5Entity instanceof EntityDreadSpawn || par5Entity instanceof Entitydreadgolem)
			{
				((EntityLivingBase)par5Entity).removePotionEffect(AbyssalCraft.Dplague.id);
			}
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int dir = MathHelper.floor_double((double)((par5EntityLivingBase.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, dir, 0);
	}

}