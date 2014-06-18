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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOC extends ItemFood {

	public ItemOC(int j, float f, boolean b) {

		super(j, f, b);
		setAlwaysEdible();
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 6));

		return;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.oc"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass)
	{
		return true;
	}
}