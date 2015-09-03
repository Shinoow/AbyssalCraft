/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDrainStaff extends Item {

	public ItemDrainStaff(){
		super();
		setUnlocalizedName("drainStaff");
		setCreativeTab(AbyssalCraft.tabTools);
		setTextureName("abyssalcraft:DrainStaff");
		setMaxStackSize(1);
	}

	@Override
	public boolean isFull3D(){
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 0x11940;
	}

	public void increaseEnergy(ItemStack stack, String type){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setInteger("energy"+type, getEnergy(stack, type) + 1);
	}

	public void setEnergy(int amount, ItemStack stack, String type){
		stack.stackTagCompound.setInteger("energy"+type, amount);
	}

	public int getEnergy(ItemStack par1ItemStack, String type)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.stackTagCompound.hasKey("energy"+type) ? (int)par1ItemStack.stackTagCompound.getInteger("energy"+type) : 0;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		player.setItemInUse(stack, getMaxItemUseDuration(stack));


		int range = 50;
		Vec3 v = player.getLookVec().normalize();
		for(int i = 1;i<range;i++){

			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX + v.xCoord * i, player.posY + v.yCoord * i, player.posZ + v.zCoord * i, player.posX + v.xCoord * i, player.posY + v.yCoord * i, player.posZ + v.zCoord * i);
			List list = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
			if(list.iterator().hasNext()){
				EntityLiving target = (EntityLiving)list.get(0);

				if(target.getCreatureAttribute() == AbyssalCraftAPI.SHADOW){
					if(!target.isDead){
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 1))
							increaseEnergy(stack, "Shadow");
						if(getEnergy(stack, "Shadow") == 200){
							setEnergy(0, stack, "Shadow");
							player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.shadowgem));
						}
					}
				} else if(world.provider.dimensionId == AbyssalCraft.configDimId1 && target instanceof ICoraliumEntity){
					if(!target.isDead){
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 1))
							increaseEnergy(stack, "Abyssal");
						if(getEnergy(stack, "Abyssal") == 100){
							setEnergy(0, stack, "Abyssal");
							player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 0));
						}
					}
				} else if(world.provider.dimensionId == AbyssalCraft.configDimId2 && target instanceof IDreadEntity){
					if(!target.isDead){
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 1))
							increaseEnergy(stack, "Dread");
						if(getEnergy(stack, "Dread") == 100){
							setEnergy(0, stack, "Dread");
							player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 1));
						}
					}
				} else if(world.provider.dimensionId == AbyssalCraft.configDimId3 && target instanceof ICoraliumEntity
						&& target instanceof IDreadEntity && target instanceof IAntiEntity &&
						target.getCreatureAttribute() != AbyssalCraftAPI.SHADOW)
					if(!target.isDead){
						if(target.attackEntityFrom(DamageSource.causePlayerDamage(player), 1))
							increaseEnergy(stack, "Omothol");
						if(getEnergy(stack, "Omothol") == 100){
							setEnergy(0, stack, "Omothol");
							player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 2));
						}
					}
			}

		}
		if(getEnergy(stack, "Shadow") == 200){
			setEnergy(0, stack, "Shadow");
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.shadowgem));
		}
		if(getEnergy(stack, "Abyssal") == 100){
			setEnergy(0, stack, "Abyssal");
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 0));
		}
		if(getEnergy(stack, "Dread") == 100){
			setEnergy(0, stack, "Dread");
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 1));
		}
		if(getEnergy(stack, "Omothol") == 100){
			setEnergy(0, stack, "Omothol");
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 2));
		}


		return stack;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + abyssal + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + dread + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + omothol + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.4")+": " + shadow + "/200");
	}
}
