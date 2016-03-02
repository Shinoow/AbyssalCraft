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

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;

import com.google.common.collect.Multimap;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

public class AbyssalCraftTool extends Item {

	private float weaponDamage;
	public AbyssalCraftTool() {
		super();
		maxStackSize = 1;
		weaponDamage = 500000;
		setUnlocalizedName("devsword");
		setCreativeTab(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B) {
		l.add(StatCollector.translateToLocal("tooltip.devblade.1"));
		l.add(StatCollector.translateToLocal("tooltip.devblade.2"));
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
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
	@SuppressWarnings("rawtypes")
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));

		List list = par3EntityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.getEntityBoundingBox().expand(40D, 40D, 40D));

		if(list != null)
			for(int k2 = 0; k2 < list.size(); k2++) {
				Entity entity = (Entity)list.get(k2);

				if(entity instanceof EntityLiving && !entity.isDead)
					entity.attackEntityFrom(DamageSource.causePlayerDamage(par3EntityPlayer), 50000);
				else if(entity instanceof EntityPlayer && !entity.isDead)
					entity.attackEntityFrom(DamageSource.causePlayerDamage(par3EntityPlayer), 50000);
				if(entity instanceof EntityJzahar) {
					par3EntityPlayer.setGameType(GameType.SURVIVAL);
					par3EntityPlayer.attackTargetEntityWithCurrentItem(par3EntityPlayer);
					((EntityJzahar)entity).heal(Float.MAX_VALUE);
					if(par2World.isRemote)
						Minecraft.getMinecraft().thePlayer.sendChatMessage("I really thought I could do that, didn't I?");
				}
			}
		return par1ItemStack;
	}

	@Override
	public boolean canHarvestBlock(Block par1Block) {
		return par1Block == Blocks.web;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", weaponDamage, 0));
		return multimap;
	}
}
