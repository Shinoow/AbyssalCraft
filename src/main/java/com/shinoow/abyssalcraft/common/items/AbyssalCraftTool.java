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
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;

import com.google.common.collect.Multimap;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.util.EnumToolMaterialAC;

public class AbyssalCraftTool extends Item
{
	private float weaponDamage;
	private final EnumToolMaterialAC toolMaterial;
	public AbyssalCraftTool(EnumToolMaterialAC abyssalniteC)
	{
		super();
		toolMaterial = abyssalniteC;
		maxStackSize = 1;
		setMaxDamage(abyssalniteC.getMaxUses());
		weaponDamage = 500000;
		setCreativeTab(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B)
	{
		l.add("This blade is so unholy that your");
		l.add("hand starts to decay while holding it");
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public float func_82803_g()
	{
		return this.toolMaterial.getDamageVsEntity();
	}
	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}
	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.block;
	}
	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 0x11940;
	}
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@SuppressWarnings("rawtypes")
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));

		List list = par3EntityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.expand(40D, 40D, 40D));

		if (list != null)
		{
			for (int k2 = 0; k2 < list.size(); k2++)
			{
				Entity entity = (Entity)list.get(k2);

				if (entity instanceof EntityLiving && !entity.isDead)
				{
					entity.attackEntityFrom(DamageSource.generic, 50);
				}
				if(entity instanceof EntityJzahar)
				{
					par3EntityPlayer.setGameType(GameType.SURVIVAL);
					par3EntityPlayer.attackTargetEntityWithCurrentItem(par3EntityPlayer);
					((EntityJzahar) entity).heal(Float.MAX_VALUE);
					Minecraft.getMinecraft().thePlayer.sendChatMessage("I really thought I could do that, didn't I?");
				}
			}
		}

		return par1ItemStack;
	}
	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean func_150897_b(Block par1Block)
	{
		return par1Block == Blocks.web;
	}
	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability()
	{
		return toolMaterial.getEnchantability();
	}

	public String getToolMaterialName()
	{
		return this.toolMaterial.toString();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return this.toolMaterial.getToolCraftingMaterial() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.weaponDamage, 0));
		return multimap;
	}
}