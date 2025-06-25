/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.lib.item.ItemACBasic;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemAntidote extends ItemACBasic {

	private Potion antidote;

	public ItemAntidote(String translationKey) {
		super(translationKey);
		setMaxStackSize(1);

		addPropertyOverride(new ResourceLocation("content"), (stack, worldIn, entityIn) -> stack.hasTagCompound() && stack.getTagCompound().getInteger("content") > 0 ? getContentToFloat(stack.getTagCompound().getInteger("content")) : 0);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 40;
	}

	private float getContentToFloat(int content) {
		if(content < 9 && content > 6)
			return 0.2f;
		if(content < 7 && content > 4)
			return 0.4f;
		if(content < 5 && content > 2)
			return 0.6f;
		if(content < 3 && content > 0)
			return 0.8f;
		return 0;
	}

	private void drink(ItemStack stack) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(stack.getTagCompound().hasKey("content"))
			stack.getTagCompound().setInteger("content", stack.getTagCompound().getInteger("content") - 1);
		else
			stack.getTagCompound().setInteger("content", 9);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if (!worldIn.isRemote) {
			entityLiving.curePotionEffects(stack);
			entityLiving.addPotionEffect(new PotionEffect(antidote, 1200));
		}
		EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;
		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
			drink(stack);

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
			if (stack.getTagCompound().getInteger("content") == 0)
				return new ItemStack(Items.GLASS_BOTTLE);

		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		int uses = 10;
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("content"))
			uses = stack.getTagCompound().getInteger("content");
		tooltip.add(TranslationUtil.toLocalFormatted("tooltip.antidote.contents", uses));
	}

	public void setCure(Potion potion) {
		antidote = potion;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		playerIn.setActiveHand(handIn);
		return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
}
