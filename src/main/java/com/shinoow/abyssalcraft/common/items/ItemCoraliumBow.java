/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.EntityCoraliumArrow;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemCoraliumBow extends ItemBow {

	public float charge;

	public int anim_0;
	public int anim_1;
	public int anim_2;
	/**
	 * @param texture is String of item texture, ie itemName.png
	 * 		also sets the UnlocalizedName to avoid render issues
	 * @param texture_0 is String of item animation texture 0, ie itemName_0.png
	 * @param texture_1 is String of item animation texture 1, ie itemName_1.png
	 * @param texture_2 is String of item animation texture 2, ie itemName_2.png
	 * @param chargeTime is Float of how fast the bow charges, ie 21.0F where 20.0F is
	 * the default bow speed
	 * @param anim_0 is used for syncing charge time with pull_0 animation,
	 *  recommended left at 0
	 * @param anim_1 is used for syncing charge time with pull_1 animation, ie 9 where
	 * 8 is default bow
	 * @param anim_2 is used for syncing charge time with pull_2 animation, ie 17 where
	 * 16 is default bow
	 *
	 * Notes: adjust anim_0-2 whenever chargeTime is changed for smoother animation flow
	 */
	public ItemCoraliumBow(float chargeTime, int anim_0, int anim_1, int anim_2) {
		maxStackSize = 1;
		setUnlocalizedName("corbow");
		setCreativeTab(ACTabs.tabCombat);

		charge = chargeTime;

		this.anim_0 = anim_0;
		this.anim_1 = anim_1;
		this.anim_2 = anim_2;


		setMaxDamage(637);

	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);
	}

	public String[] bowPullIconNameArray;

	public void getBowPullIconNameArray(){

		bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2" };

	}

	@Override
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		if(player.isUsingItem()){
			int useTime = stack.getMaxItemUseDuration() - useRemaining;

			if(useTime >= 18)
				return new ModelResourceLocation("abyssalcraft:corbow_pulling_2", "inventory");
			else if(useTime > 13)
				return new ModelResourceLocation("abyssalcraft:corbow_pulling_1", "inventory");
			else if(useTime > 0)
				return new ModelResourceLocation("abyssalcraft:corbow_pulling_0", "inventory");
		}

		return null;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer entityplayer, List<String> list, boolean is){
		list.add(StatCollector.translateToLocal("tooltip.corbow.1"));
		list.add(StatCollector.translateToLocal("tooltip.corbow.2"));
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int j = getMaxItemUseDuration(par1ItemStack) - par4;

		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;
		j = event.charge;

		boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

		if (flag || par3EntityPlayer.inventory.hasItem(Items.arrow))
		{
			float f = j / charge;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1D)
				return;

			if (f > 1.0F)
				f = 1.0F;

			EntityCoraliumArrow entityarrow = new EntityCoraliumArrow(par2World, par3EntityPlayer, f * 2.0F);

			if (f == 1.0F)
				entityarrow.setIsCritical(true);

			entityarrow.setDamage(entityarrow.getDamage() + 3.0D);

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

			if (k > 0)
				entityarrow.setDamage(entityarrow.getDamage() + 3.0D + k * 0.5D + 0.5D);

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

			if (l > 0)
				entityarrow.setKnockbackStrength(l);

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
				entityarrow.setFire(100);

			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			if (flag)
				entityarrow.canBePickedUp = 2;
			else
				par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);

			if (!par2World.isRemote)
				par2World.spawnEntityInWorld(entityarrow);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{

		ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return event.result;

		if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Items.arrow))
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getItemEnchantability()
	{
		return 1;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return ACItems.refined_coralium_ingot == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
}
