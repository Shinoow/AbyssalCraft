/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.*;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.item.ItemMetadata;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemScroll extends ItemMetadata implements IScroll {

	public ItemScroll(String name, String...names) {
		super(name, names);

		if(name.equals("scroll"))
			addPropertyOverride(new ResourceLocation("inscribed"), (stack, worldIn, entityIn) -> stack.hasTagCompound() && stack.getTagCompound().hasKey("Spell") ? 1.0F : 0);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		Spell spell = SpellUtils.getSpell(stack);
		return spell != null ? spell.requiresCharging() ? 50 : 0 : 0;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if(entityLiving instanceof EntityPlayer)
			SpellUtils.castChargingSpell(stack, worldIn, (EntityPlayer)entityLiving);
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		SpellUtils.castInstantSpell(stack, worldIn, playerIn, handIn);

		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	@Override
	public ScrollType getScrollType(ItemStack stack) {

		if(stack.getItem() == ACItems.scroll)
			switch(stack.getMetadata()) {
			case 1:
				return ScrollType.LESSER;
			case 2:
				return ScrollType.MODERATE;
			case 3:
				return ScrollType.GREATER;
			}
		if(stack.getItem() == ACItems.unique_scroll)
			return ScrollType.UNIQUE;
		return ScrollType.BASIC;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List){
		super.getSubItems(par2CreativeTab, par3List);
		if(this == ACItems.scroll && par2CreativeTab == ACTabs.tabSpells) {
			ItemStack greater_scroll = new ItemStack(ACItems.scroll, 1, 3);
			ItemStack antimatter_scroll = new ItemStack(ACItems.unique_scroll, 1, 0);
			ItemStack oblivion_scroll = new ItemStack(ACItems.unique_scroll, 1, 1);
			SpellRegistry.instance().getSpells().stream().forEach(s -> {
				par3List.add(SpellRegistry.instance().inscribeSpell(s, greater_scroll.copy()));
				par3List.add(SpellRegistry.instance().inscribeSpell(s, antimatter_scroll.copy()));
				par3List.add(SpellRegistry.instance().inscribeSpell(s, oblivion_scroll.copy()));
			});
		}
	}
}
