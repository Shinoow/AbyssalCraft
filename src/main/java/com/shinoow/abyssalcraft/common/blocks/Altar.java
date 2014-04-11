package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityAltar;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;

import cpw.mods.fml.client.FMLClientHandler;

public class Altar extends BlockContainer {

	public Altar() {
		super(Material.rock);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.7F, 0.9F);

	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {

		return new TileEntityAltar();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	protected boolean isDestroyed;

	@Override
	public int getRenderType() {
		return -2;
	}

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();

		if(!par1World.isRemote && itemstack != null && itemstack.getItem() == AbyssalCraft.Cbucket && !par5EntityPlayer.capabilities.isCreativeMode)
		{
			if (itemstack.stackSize-- == 1)
			{
				par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Items.bucket));
			}
			EntityDragonBoss EntityDragonBoss = new EntityDragonBoss(par1World);
			EntityDragonBoss.setLocationAndAngles(par2, par3, par4, MathHelper.wrapAngleTo180_float(par1World.rand.nextFloat() * 360.0F), 10.0F);
			par1World.spawnEntityInWorld(EntityDragonBoss);
			par5EntityPlayer.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 300));
			par5EntityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 200));
			this.removedByPlayer(par1World, par5EntityPlayer, par2, par3, par4);
			this.isDestroyed = true;
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Asorah: Haha, foolish human, I AM UNLEASHED!"));
			par5EntityPlayer.addStat(AbyssalCraft.summonAsorah, 1);
		}
		return false;
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

		if (par5Random.nextInt(10) == 0)
		{
			par1World.spawnParticle("enchantmenttable", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + 1.1F), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("smoke", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + 1.1F), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
			if (this.isDestroyed)
			{
				par1World.spawnParticle("hugeexplosion", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + 1.1F), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int dir = MathHelper.floor_double((double)((par5EntityLivingBase.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, dir, 0);
	}

}
