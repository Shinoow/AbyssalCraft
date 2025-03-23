/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.potion;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.handlers.PlagueEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCplague extends Potion {

	private boolean wasKilled;

	public PotionCplague(boolean par2, int par3) {
		super(par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2){

		if(par1EntityLivingBase.ticksExisted % 200 == 0 && par1EntityLivingBase.getRNG().nextFloat() > 0.7F
				&& par1EntityLivingBase.world.getBiome(par1EntityLivingBase.getPosition()) != ACBiomes.purged) {
			AxisAlignedBB axisalignedbb = par1EntityLivingBase.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D);
			List<EntityLivingBase> list = par1EntityLivingBase.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			for(EntityLivingBase entity : list)
				if(entity.getRNG().nextBoolean() && !entity.world.isRemote && par1EntityLivingBase != entity)
					entity.addPotionEffect(PlagueEventHandler.getEffect(par1EntityLivingBase.getActivePotionEffect(this)));
		}

		if(par1EntityLivingBase instanceof EntityPlayer && par1EntityLivingBase.ticksExisted % 200 == 0)
			NecroDataCapability.getCap((EntityPlayer) par1EntityLivingBase).triggerMiscUnlock("coralium_plague");

		if(EntityUtil.isEntityCoralium(par1EntityLivingBase)) return;

		if(par1EntityLivingBase.ticksExisted % 40 >> par2 == 0)
			par1EntityLivingBase.attackEntityFrom(AbyssalCraftAPI.coralium, 2);

		if(par1EntityLivingBase instanceof EntityPlayer && !par1EntityLivingBase.isDead && wasKilled)
			wasKilled = false;

		if(!par1EntityLivingBase.world.isRemote && par1EntityLivingBase.isDead
				&& par1EntityLivingBase.world.getBiome(par1EntityLivingBase.getPosition()) != ACBiomes.purged)
			if(par1EntityLivingBase instanceof EntityZombie){
				if(par1EntityLivingBase.world.getWorldInfo().isHardcoreModeEnabled() && par1EntityLivingBase.world.rand.nextInt(10) == 0) {
					EntityDepthsGhoul ghoul = new EntityDepthsGhoul(par1EntityLivingBase.world);
					ghoul.copyLocationAndAnglesFrom(par1EntityLivingBase);
					ghoul.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
					ghoul.setGhoulType(0);
					par1EntityLivingBase.world.spawnEntity(ghoul);
				}
				else if(par1EntityLivingBase.world.getDifficulty() == EnumDifficulty.HARD && par1EntityLivingBase.world.rand.nextBoolean()
						|| par1EntityLivingBase.world.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(par1EntityLivingBase.world);
					entityzombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
					entityzombie.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					if(par1EntityLivingBase.isChild())
						entityzombie.setChild(true);
					par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.world.spawnEntity(entityzombie);
				}
			} else if(par1EntityLivingBase instanceof EntityPlayer && !wasKilled){
				wasKilled = true;
				if(par1EntityLivingBase.world.getDifficulty() == EnumDifficulty.HARD && par1EntityLivingBase.world.rand.nextBoolean()
						|| par1EntityLivingBase.world.rand.nextInt(8) == 0) {
					EntityAbyssalZombie entityzombie = new EntityAbyssalZombie(par1EntityLivingBase.world);
					entityzombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
					entityzombie.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					if(par1EntityLivingBase.isChild())
						entityzombie.setChild(true);
					par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.world.spawnEntity(entityzombie);
				}
			} else if(par1EntityLivingBase instanceof EntitySquid)
				if(par1EntityLivingBase.world.rand.nextBoolean()){
					EntityCoraliumSquid squid = new EntityCoraliumSquid(par1EntityLivingBase.world);
					squid.copyLocationAndAnglesFrom(par1EntityLivingBase);
					squid.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
					par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
					par1EntityLivingBase.world.spawnEntity(squid);
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

	@Override
	public List<ItemStack> getCurativeItems()
	{
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(ACItems.antidote));
		return list;
	}
}
