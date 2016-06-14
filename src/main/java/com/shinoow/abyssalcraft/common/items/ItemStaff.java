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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemStaff extends Item {

	public ItemStaff() {
		super();
		setUnlocalizedName("staff");
		setCreativeTab(ACTabs.tabTools);
		setFull3D();
		setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.staff"));
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + abyssal + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + dread + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + omothol + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.4")+": " + shadow + "/200");
	}

	@Override
	public boolean isFull3D(){
		return true;
	}

	public void increaseEnergy(ItemStack stack, String type){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("energy"+type, getEnergy(stack, type) + 4);
	}

	public void setEnergy(int amount, ItemStack stack, String type){
		stack.getTagCompound().setInteger("energy"+type, amount);
	}

	public int getEnergy(ItemStack par1ItemStack, String type)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("energy"+type) ? (int)par1ItemStack.getTagCompound().getInteger("energy"+type) : 0;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(getEnergy(stack, "Shadow") >= 200){
			setEnergy(0, stack, "Shadow");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.shadow_gem));
		}
		if(getEnergy(stack, "Abyssal") >= 100){
			setEnergy(0, stack, "Abyssal");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.essence, 1, 0));
		}
		if(getEnergy(stack, "Dread") >= 100){
			setEnergy(0, stack, "Dread");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.essence, 1, 1));
		}
		if(getEnergy(stack, "Omothol") >= 100){
			setEnergy(0, stack, "Omothol");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.essence, 1, 2));
		}

		int range = 50;
		Vec3 v = player.getLookVec().normalize();
		for(int i = 1;i<range;i++){

			AxisAlignedBB aabb = AxisAlignedBB.fromBounds(player.posX + v.xCoord * i, player.posY + v.yCoord * i, player.posZ + v.zCoord * i, player.posX + v.xCoord * i, player.posY + v.yCoord * i, player.posZ + v.zCoord * i);
			List list = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
			if(list.iterator().hasNext()){
				EntityLiving target = (EntityLiving)list.get(0);

				if(target.getCreatureAttribute() == AbyssalCraftAPI.SHADOW && !(target instanceof IBossDisplayData)){
					if(!target.isDead)
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4))
							increaseEnergy(stack, "Shadow");
				} else if(world.provider.getDimensionId() == ACLib.abyssal_wasteland_id && target instanceof ICoraliumEntity &&
						!(target instanceof IBossDisplayData)){
					if(!target.isDead)
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4))
							increaseEnergy(stack, "Abyssal");
				} else if(world.provider.getDimensionId() == ACLib.dreadlands_id && target instanceof IDreadEntity &&
						!(target instanceof IBossDisplayData)){
					if(!target.isDead)
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4))
							increaseEnergy(stack, "Dread");
				} else if(world.provider.getDimensionId() == ACLib.omothol_id && target instanceof ICoraliumEntity
						&& target instanceof IDreadEntity && target instanceof IAntiEntity &&
						target.getCreatureAttribute() != AbyssalCraftAPI.SHADOW && !(target instanceof IBossDisplayData))
					if(!target.isDead)
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4))
							increaseEnergy(stack, "Omothol");
			}

		}

		return stack;
	}
}