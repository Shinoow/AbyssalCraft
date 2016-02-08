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
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import com.google.common.collect.Multimap;
import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemDreadiumKatana extends Item {

	private float weaponDamage;

	public ItemDreadiumKatana(String par1Str, float par2, int par3){
		super();
		//		GameRegistry.registerItem(this, par1Str);
		setUnlocalizedName(par1Str);
		setCreativeTab(AbyssalCraft.tabCombat);
		//		setTextureName("abyssalcraft:" + par1Str);
		weaponDamage = par2;
		setMaxDamage(par3);
		setMaxStackSize(1);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.BLOCK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 0x11940;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return new ItemStack(AbyssalCraft.crystal, 1, 14) == par2ItemStack ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", weaponDamage, 0));
		return multimap;
	}
}
