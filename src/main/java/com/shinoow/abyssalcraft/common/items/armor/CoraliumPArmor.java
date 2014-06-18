/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items.armor;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CoraliumPArmor extends ItemArmor {
	public CoraliumPArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.GREEN + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if(stack.getItem() == AbyssalCraft.CorhelmetP || stack.getItem() == AbyssalCraft.CorplateP || stack.getItem() == AbyssalCraft.CorbootsP)
			return "abyssalcraft:textures/armor/coraliumP_1.png";

		if(stack.getItem() == AbyssalCraft.CorlegsP)
			return "abyssalcraft:textures/armor/coraliumP_2.png";
		else return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.CorhelmetP) {
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
		}
		if (itemstack.getItem() == AbyssalCraft.CorplateP) {
			List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(4D, 0.0D, 4D));

			if (list != null) {
				for (int k2 = 0; k2 < list.size(); k2++) {
					Entity entity = (Entity)list.get(k2);

					if (entity instanceof EntityLiving && !entity.isDead) {
						entity.attackEntityFrom(DamageSource.generic, 1);
					}
					else if (entity instanceof EntityPlayer && !entity.isDead) {
						entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 1);
					}
				}
			}
		}
		if (itemstack.getItem() == AbyssalCraft.legsD){
		}
		if (itemstack.getItem() == AbyssalCraft.bootsD){
			if(player.isInsideOfMaterial(Material.water )){
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200, 2));
				player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 200, 1));
			}
		}
	}
}