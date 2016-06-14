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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemSoulReaper extends Item {

	private float weaponDamage = 30.0F;

	public ItemSoulReaper(String par1Str){
		super();
		setUnlocalizedName(par1Str);
		setCreativeTab(ACTabs.tabCombat);
		setMaxDamage(2000);
		setMaxStackSize(1);
	}

	@Override
	public boolean isFull3D(){
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
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}

	/** Increases the amount of souls by 1 */
	public int increaseSouls(ItemStack par1ItemStack){
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.setTagCompound(new NBTTagCompound());
		par1ItemStack.getTagCompound().setInteger("souls", getSouls(par1ItemStack) + 1);
		return getSouls(par1ItemStack);
	}

	/** Sets the amount of souls */
	public int setSouls(int par1, ItemStack par2ItemStack){
		par2ItemStack.getTagCompound().setInteger("souls", par1);
		return getSouls(par2ItemStack);
	}

	public int getSouls(ItemStack par1ItemStack)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("souls") ? (int)par1ItemStack.getTagCompound().getInteger("souls") : 0;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		if(par2EntityLivingBase.getHealth() == 0){
			increaseSouls(par1ItemStack);
			switch(getSouls(par1ItemStack)){
			case 32:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 600));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 64:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 1200));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 128:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 1200, 1));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 600));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 256:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 2400, 1));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 1200));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 300));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 512:
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 1200, 2));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 1200, 1));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 600));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 300));
				par3EntityLivingBase.heal(20.0F);
				break;
			case 1024:
				setSouls(0, par1ItemStack);
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 2400, 2));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 2400, 2));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 1200));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 600));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.saturation.getId(), 300));
				par3EntityLivingBase.addPotionEffect(new PotionEffect(Potion.healthBoost.getId(), 2400, 2));
				par3EntityLivingBase.heal(20.0F);
				break;
			}
			return true;
		}
		return true;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		int souls = getSouls(is);
		l.add(StatCollector.translateToLocal("tooltip.soulreaper") + ": " + souls + "/1024");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return ACItems.shadow_gem == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", weaponDamage, 0));
		return multimap;
	}
}