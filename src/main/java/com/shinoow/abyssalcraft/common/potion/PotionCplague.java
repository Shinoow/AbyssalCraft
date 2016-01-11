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
package com.shinoow.abyssalcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionCplague extends Potion{

	public PotionCplague(int par1, boolean par2, int par3) {
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
			par1EntityLivingBase.removePotionEffect(AbyssalCraft.Cplague.id);
		else par1EntityLivingBase.attackEntityFrom(AbyssalCraftAPI.coralium, 2);

		if(!par1EntityLivingBase.isEntityAlive() && !par1EntityLivingBase.worldObj.isRemote)
			if(par1EntityLivingBase instanceof EntityZombie){
				if(par1EntityLivingBase.worldObj.getWorldInfo().isHardcoreModeEnabled() && par1EntityLivingBase.worldObj.rand.nextInt(10) == 0) {
					EntityDepthsGhoul ghoul = new EntityDepthsGhoul(par1EntityLivingBase.worldObj);
					ghoul.copyLocationAndAnglesFrom(par1EntityLivingBase);
					ghoul.onSpawnWithEgg((IEntityLivingData)null);
					par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
					ghoul.setGhoulType(0);
					par1EntityLivingBase.worldObj.spawnEntityInWorld(ghoul);
				}
				else if(par1EntityLivingBase.worldObj.difficultySetting == EnumDifficulty.HARD && par1EntityLivingBase.worldObj.rand.nextBoolean()
						|| par1EntityLivingBase.worldObj.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(par1EntityLivingBase.worldObj);
					entityzombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
					entityzombie.onSpawnWithEgg((IEntityLivingData)null);
					if(par1EntityLivingBase.isChild())
						entityzombie.setChild(true);
					par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.worldObj.spawnEntityInWorld(entityzombie);
				}
			} else if(par1EntityLivingBase instanceof EntityPlayer)
				if(par1EntityLivingBase.worldObj.difficultySetting == EnumDifficulty.HARD && par1EntityLivingBase.worldObj.rand.nextBoolean()
				|| par1EntityLivingBase.worldObj.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(par1EntityLivingBase.worldObj);
					entityzombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
					entityzombie.onSpawnWithEgg((IEntityLivingData)null);
					if(par1EntityLivingBase.isChild())
						entityzombie.setChild(true);
					par1EntityLivingBase.worldObj.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.worldObj.spawnEntityInWorld(entityzombie);
				}
	}

	@Override
	public boolean isReady(int par1, int par2)
	{
		int k = 40 >> par2;
		return k > 0 ? par1 % k == 0 : true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 0;
	}
}
