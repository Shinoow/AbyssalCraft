package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Antiliquid extends BlockFluidClassic {

	public static final MaterialLiquid antimatter = (new MaterialLiquid(MapColor.silverColor));


	@SideOnly(Side.CLIENT)
	protected IIcon[] theIcon;

	public Antiliquid() {
		super(AbyssalCraft.antifluid, antimatter);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.theIcon = new IIcon[]{iconRegister.registerIcon("abyssalcraft:anti_still"), iconRegister.registerIcon("abyssalcraft:anti_flow")};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if ( side <= 1 ) {
			return this.theIcon[0];
		} else {
			return this.theIcon[1];
		}
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if(world.getBlock(x, y, z).getMaterial() == CLiquid.Cwater || world.getBlock(x, y, z).getMaterial() == Material.water || world.getBlock(x, y, z).getMaterial() == Material.lava)
		{
			return true;
		}

		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {

		if(!world.isRemote && world.getBlock(x, y, z).getMaterial() == CLiquid.Cwater)
		{
			world.setBlock(x, y, z, AbyssalCraft.cstone);
		}
		if(!world.isRemote && world.getBlock(x, y, z).getMaterial() == Material.water)
		{
			world.setBlock(x, y, z, Blocks.packed_ice);
		}

		if(!world.isRemote && world.getBlock(x, y, z).getMaterial() == Material.lava)
		{
			world.setBlock(x, y, z, Blocks.obsidian);
		}

		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
		if(par5Entity instanceof EntityLivingBase){
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 1));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 1));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 100, 1));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 100, 1));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 100, 1));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 1));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 100, 1));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 100, 1));
		}
	}
}