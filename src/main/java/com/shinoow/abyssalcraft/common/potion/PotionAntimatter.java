/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.potion;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonChicken;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonCow;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionAntimatter extends Potion{

	private boolean wasKilled;

	public PotionAntimatter(boolean par2, int par3) {
		super(par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2){

		if(EntityUtil.isEntityAnti(par1EntityLivingBase)) return;

		par1EntityLivingBase.attackEntityFrom(AbyssalCraftAPI.antimatter, 5);

		if(par1EntityLivingBase instanceof EntityPlayer && !par1EntityLivingBase.isDead && wasKilled)
			wasKilled = false;

		if(!par1EntityLivingBase.world.isRemote && par1EntityLivingBase.isDead)
			if(par1EntityLivingBase instanceof EntityZombie){
				EntityAntiZombie entity = new EntityAntiZombie(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityAbyssalZombie){
				EntityAntiAbyssalZombie entity = new EntityAntiAbyssalZombie(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityDepthsGhoul){
				EntityAntiGhoul entity = new EntityAntiGhoul(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityBat){
				EntityAntiBat entity = new EntityAntiBat(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(null, null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityChicken || par1EntityLivingBase instanceof EntityDemonChicken){
				EntityAntiChicken entity = new EntityAntiChicken(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(null, null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityCow || par1EntityLivingBase instanceof EntityDemonCow){
				EntityAntiCow entity = new EntityAntiCow(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(null, null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityCreeper){
				EntityAntiCreeper entity = new EntityAntiCreeper(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(null, null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityPig || par1EntityLivingBase instanceof EntityDemonPig){
				EntityAntiPig entity = new EntityAntiPig(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(null, null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntityPlayer && !wasKilled){
				wasKilled = true;
				EntityAntiPlayer entity = new EntityAntiPlayer(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntitySkeleton){
				EntityAntiSkeleton entity = new EntityAntiSkeleton(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(entity);
			} else if(par1EntityLivingBase instanceof EntitySpider){
				EntityAntiSpider entity = new EntityAntiSpider(par1EntityLivingBase.world);
				entity.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				entity.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(entity);
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
