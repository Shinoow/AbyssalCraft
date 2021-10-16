/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import com.google.common.collect.Multimap;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCudgel extends ItemACBasic {

	public ItemCudgel(){
		super("cudgel");
		setMaxDamage(1500);
		setMaxStackSize(1);
		setCreativeTab(ACTabs.tabCombat);
		setFull3D();
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected){
		if(stack.isItemEnchanted())
			stack.getTagCompound().removeTag("ench");
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return Items.BONE == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	public Multimap getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap multimap = super.getAttributeModifiers(equipmentSlot, stack);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 19, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
		}

		return multimap;
	}
}
