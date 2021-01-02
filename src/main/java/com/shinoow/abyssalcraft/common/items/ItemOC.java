/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOC extends ItemACBasic {

	private final IUnlockCondition killed_bosses = new IUnlockCondition() {

		@Override
		public boolean areConditionObjectsEqual(Object stuff) {

			return false;
		}

		@Override
		public Object getConditionObject() {

			return new String[] {"abyssalcraft:dragonboss", "abyssalcraft:chagaroth", "abyssalcraft:jzahar", "abyssalcraft:shadowboss"};
		}

		@Override
		public int getType() {

			return 11;
		}

	};

	public ItemOC() {
		super("oc");
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if(!world.isRemote) {
			INecroDataCapability cap = NecroDataCapability.getCap(player);
			if(cap != null && cap.isUnlocked(killed_bosses, player)) {
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, 6, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 600, 6, false, false));

				player.getHeldItem(hand).shrink(1);
			}
		}

		return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		l.add(I18n.format("tooltip.oc"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
