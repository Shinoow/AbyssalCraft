package com.shinoow.abyssalcraft.common.items.armor;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class DreadArmor extends ItemArmor {
	public DreadArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
	{
		if(stack.getItem() == AbyssalCraft.helmetD || stack.getItem() == AbyssalCraft.plateD || stack.getItem() == AbyssalCraft.bootsD){
			return "abyssalcraft:textures/armor/dread_1.png";
		}

		if(stack.getItem() == AbyssalCraft.legsD){
			return "abyssalcraft:textures/armor/dread_2.png";
		}
		else return null;
	}
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack)
	{
		if (itemstack.getItem() == AbyssalCraft.helmetD)
		{
			player.addPotionEffect((new PotionEffect(Potion.nightVision.getId(), 260, 0)));
		}
		if (itemstack.getItem() == AbyssalCraft.plateD)
		{
			player.addPotionEffect((new PotionEffect(Potion.fireResistance.getId(), 200, 3)));
			List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(4D, 0.0D, 4D));

			if (list != null)
			{
				for (int k2 = 0; k2 < list.size(); k2++)
				{
					Entity entity = (Entity)list.get(k2);

					if ((entity instanceof EntityLiving) && !entity.isDead)
					{
						entity.setFire(99);
					}
					//EntityLiving entity2 = (EntityLiving)list.get(k2);

					// if (entity2 instanceof Entity && !entity2.isDead)
					//{
					// 	entity2.addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 220, 0));
					// }
				}
			}
		}
		if (itemstack.getItem() == AbyssalCraft.legsD)
		{
			player.addPotionEffect((new PotionEffect(Potion.fireResistance.getId(), 200, 3)));
		}
		if (itemstack.getItem() == AbyssalCraft.bootsD)
		{
			player.addPotionEffect((new PotionEffect(Potion.fireResistance.getId(), 200, 3)));
		}
	}

}