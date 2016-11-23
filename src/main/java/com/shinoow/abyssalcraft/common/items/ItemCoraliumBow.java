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

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

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

		addPropertyOverride(new ResourceLocation("pull"), (stack, worldIn, entityIn) -> {
			if (entityIn == null)
				return 0.0F;
			else
			{
				ItemStack itemstack = entityIn.getActiveItemStack();
				return itemstack != null && itemstack.getItem() == ACItems.coralium_longbow ? (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
			}
		});
		addPropertyOverride(new ResourceLocation("pulling"), (stack, worldIn, entityIn) -> entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer entityplayer, List list, boolean is){
		list.add(I18n.translateToLocal("tooltip.corbow.1"));
		list.add(I18n.translateToLocal("tooltip.corbow.2"));
	}

	private ItemStack findAmmo(EntityPlayer player)
	{
		if (isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
			return player.getHeldItem(EnumHand.OFF_HAND);
		else if (isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
			return player.getHeldItem(EnumHand.MAIN_HAND);
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (isArrow(itemstack))
					return itemstack;
			}

			return null;
		}
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityLivingBase par3EntityLivingBase, int par4)
	{
		if(par3EntityLivingBase instanceof EntityPlayer){

			EntityPlayer player = (EntityPlayer) par3EntityLivingBase;
			boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
			ItemStack itemstack = findAmmo(player);


			int i = getMaxItemUseDuration(par1ItemStack) - par4;
			i = ForgeEventFactory.onArrowLoose(par1ItemStack, par2World, player, i, itemstack != null || flag);
			if(i < 0) return;

			if (itemstack != null || flag)
			{
				if (itemstack == null)
					itemstack = new ItemStack(Items.ARROW);

				float f = getArrowVelocity(i);

				if (f >= 0.1D)
				{
					boolean flag1 = flag && itemstack.getItem() instanceof ItemArrow; //Forge: Fix consuming custom arrows.

					if (!par2World.isRemote)
					{
						EntityCoraliumArrow entityarrow = new EntityCoraliumArrow(par2World, player);
						entityarrow.setAim(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 3.0F, 1.0F);

						if (f == 1.0F)
							entityarrow.setIsCritical(true);

						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, par1ItemStack);

						if (j > 0)
							entityarrow.setDamage(entityarrow.getDamage() + j * 0.5D + 0.5D);

						int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, par1ItemStack);

						if (k > 0)
							entityarrow.setKnockbackStrength(k);

						if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, par1ItemStack) > 0)
							entityarrow.setFire(100);

						par1ItemStack.damageItem(1, player);

						if (flag1)
							entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;

						par2World.spawnEntityInWorld(entityarrow);
					}

					par2World.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

					if (!flag1)
					{
						--itemstack.stackSize;

						if (itemstack.stackSize == 0)
							player.inventory.deleteStack(itemstack);
					}

					player.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{

		boolean flag = findAmmo(par3EntityPlayer) != null;

		ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(par1ItemStack, par2World, par3EntityPlayer, hand, flag);
		if(ret != null) return ret;

		if (!par3EntityPlayer.capabilities.isCreativeMode && !flag)
			return !flag ? new ActionResult(EnumActionResult.FAIL, par1ItemStack) : new ActionResult(EnumActionResult.PASS, par1ItemStack);
			else
			{
				par3EntityPlayer.setActiveHand(hand);
				return new ActionResult(EnumActionResult.SUCCESS, par1ItemStack);
			}
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