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
package com.shinoow.abyssalcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiBat;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiChicken;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCow;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPig;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSpider;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionAntimatter extends Potion{

	public PotionAntimatter(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2){

		if(par1EntityLivingBase instanceof IAntiEntity)
			par1EntityLivingBase.removePotionEffect(AbyssalCraft.antiMatter.id);
		else par1EntityLivingBase.attackEntityFrom(AbyssalCraftAPI.antimatter, 5);

		if(!par1EntityLivingBase.worldObj.isRemote && par1EntityLivingBase.isDead)
			if(par1EntityLivingBase instanceof EntityZombie){
				EntityAntiZombie entity = new EntityAntiZombie(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityAbyssalZombie){
				EntityAntiAbyssalZombie entity = new EntityAntiAbyssalZombie(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityDepthsGhoul){
				EntityAntiGhoul entity = new EntityAntiGhoul(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityBat){
				EntityAntiBat entity = new EntityAntiBat(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityChicken){
				EntityAntiChicken entity = new EntityAntiChicken(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityCow){
				EntityAntiCow entity = new EntityAntiCow(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityCreeper){
				EntityAntiCreeper entity = new EntityAntiCreeper(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityPig || par1EntityLivingBase instanceof EntityDemonPig){
				EntityAntiPig entity = new EntityAntiPig(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityPlayer){
				EntityAntiPlayer entity = new EntityAntiPlayer(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntitySkeleton){
				EntityAntiSkeleton entity = new EntityAntiSkeleton(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntitySpider){
				EntityAntiSpider entity = new EntityAntiSpider(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			} else if(par1EntityLivingBase instanceof EntityVillager){
				EntityRemnant entity = new EntityRemnant(par1EntityLivingBase.worldObj);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				par1EntityLivingBase.worldObj.spawnEntityInWorld(entity);
			}
	}

	@Override
	public boolean isReady(int par1, int par2)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 2;
	}
}
