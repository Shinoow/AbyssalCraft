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
package com.shinoow.abyssalcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;

public class PotionCplague extends Potion{

	public PotionCplague(ResourceLocation par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2){

		if(EntityUtil.isEntityCoralium(par1EntityLivingBase))
			par1EntityLivingBase.removePotionEffect(AbyssalCraftAPI.coralium_plague.id);
		else if(par1EntityLivingBase.ticksExisted % 40 >> par2 == 0)
			par1EntityLivingBase.attackEntityFrom(AbyssalCraftAPI.coralium, 2);

		if(!par1EntityLivingBase.worldObj.isRemote && par1EntityLivingBase.isDead)
			if(par1EntityLivingBase instanceof EntityZombie){
				if(par1EntityLivingBase.worldObj.getWorldInfo().isHardcoreModeEnabled() && par1EntityLivingBase.worldObj.rand.nextInt(10) == 0) {
					EntityDepthsGhoul ghoul = new EntityDepthsGhoul(par1EntityLivingBase.worldObj);
					ghoul.copyLocationAndAnglesFrom(par1EntityLivingBase);
					ghoul.onInitialSpawn(par1EntityLivingBase.worldObj.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
					ghoul.setGhoulType(0);
					par1EntityLivingBase.worldObj.spawnEntityInWorld(ghoul);
				}
				else if(par1EntityLivingBase.worldObj.getDifficulty() == EnumDifficulty.HARD && par1EntityLivingBase.worldObj.rand.nextBoolean()
						|| par1EntityLivingBase.worldObj.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(par1EntityLivingBase.worldObj);
					entityzombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
					entityzombie.onInitialSpawn(par1EntityLivingBase.worldObj.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					if(par1EntityLivingBase.isChild())
						entityzombie.setChild(true);
					par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.worldObj.spawnEntityInWorld(entityzombie);
				}
			} else if(par1EntityLivingBase instanceof EntityPlayer){
				if(par1EntityLivingBase.worldObj.getDifficulty() == EnumDifficulty.HARD && par1EntityLivingBase.worldObj.rand.nextBoolean()
						|| par1EntityLivingBase.worldObj.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(par1EntityLivingBase.worldObj);
					entityzombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
					entityzombie.onInitialSpawn(par1EntityLivingBase.worldObj.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					if(par1EntityLivingBase.isChild())
						entityzombie.setChild(true);
					par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.worldObj.spawnEntityInWorld(entityzombie);
				}
			} else if(par1EntityLivingBase instanceof EntitySquid)
				if(par1EntityLivingBase.worldObj.rand.nextBoolean()){
					EntityCoraliumSquid squid = new EntityCoraliumSquid(par1EntityLivingBase.worldObj);
					squid.copyLocationAndAnglesFrom(par1EntityLivingBase);
					squid.onInitialSpawn(par1EntityLivingBase.worldObj.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.worldObj.spawnEntityInWorld(squid);
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
		return 0;
	}
}
