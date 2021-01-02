/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
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
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.handlers.PlagueEventHandler;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionDplague extends Potion{

	private boolean wasKilled;

	public PotionDplague(boolean par2, int par3) {
		super(par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2){

		if(par1EntityLivingBase.ticksExisted % 100 == 0 && par1EntityLivingBase.getRNG().nextFloat() > 0.3F
				&& par1EntityLivingBase.world.getBiome(par1EntityLivingBase.getPosition()) != ACBiomes.purged) {
			AxisAlignedBB axisalignedbb = par1EntityLivingBase.getEntityBoundingBox().grow(3.0D, 3.0D, 3.0D);
			List<EntityLivingBase> list = par1EntityLivingBase.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			for(EntityLivingBase entity : list)
				if(entity.getRNG().nextBoolean() && !entity.world.isRemote && par1EntityLivingBase != entity)
					if(entity.isPotionActive(this) && entity.getActivePotionEffect(this).getAmplifier() > 0)
						entity.addPotionEffect(PlagueEventHandler.getEffect(par1EntityLivingBase.getActivePotionEffect(this), 1));
					else entity.addPotionEffect(PlagueEventHandler.getEffect(par1EntityLivingBase.getActivePotionEffect(this), entity.isPotionActive(this) && par1EntityLivingBase.getRNG().nextInt(10) == 0 ? 1 : 0));
		}

		if(!ACConfig.no_dreadlands_spread)
			if(par2 > 0 || ACConfig.hardcoreMode)
				if(par1EntityLivingBase.ticksExisted % 100 == 0 && !par1EntityLivingBase.world.isRemote)
					if(par1EntityLivingBase.dimension != ACLib.dark_realm_id && par1EntityLivingBase.dimension != ACLib.omothol_id)
						for(int x = par1EntityLivingBase.getPosition().getX() - 1; x <= par1EntityLivingBase.getPosition().getX() + 1; x++)
							for(int z = par1EntityLivingBase.getPosition().getZ() - 1; z <= par1EntityLivingBase.getPosition().getZ() + 1; z++)
								if(!(par1EntityLivingBase.world.getBiome(new BlockPos(x, 0, z)) instanceof IDreadlandsBiome)
										&& par1EntityLivingBase.world.getBiome(new BlockPos(x, 0, z)) != ACBiomes.purged)
									BiomeUtil.updateBiome(par1EntityLivingBase.world, new BlockPos(x, 0, z), ACBiomes.dreadlands);

		if(par1EntityLivingBase instanceof EntityPlayer && par1EntityLivingBase.ticksExisted % 200 == 0)
			NecroDataCapability.getCap((EntityPlayer) par1EntityLivingBase).triggerMiscUnlock("dread_plague");

		if(EntityUtil.isEntityDread(par1EntityLivingBase)) return;

		if(par1EntityLivingBase.ticksExisted % 25 >> par2 == 0)
			par1EntityLivingBase.attackEntityFrom(AbyssalCraftAPI.dread, 1);

		if(par1EntityLivingBase instanceof EntityPlayer && !par1EntityLivingBase.isDead && wasKilled)
			wasKilled = false;

		if(par1EntityLivingBase instanceof EntityPlayer)
			((EntityPlayer)par1EntityLivingBase).addExhaustion(0.025F * (par2+2));
		if(!par1EntityLivingBase.world.isRemote && par1EntityLivingBase.isDead
				&& par1EntityLivingBase.world.rand.nextBoolean()
				&& par1EntityLivingBase.world.getBiome(par1EntityLivingBase.getPosition()) != ACBiomes.purged)
			if(par1EntityLivingBase instanceof EntityZombie || par1EntityLivingBase instanceof EntityAbyssalZombie
					|| par1EntityLivingBase instanceof EntityAntiPlayer || par1EntityLivingBase instanceof EntityAntiAbyssalZombie
					|| par1EntityLivingBase instanceof EntityAntiZombie || par1EntityLivingBase instanceof EntitySkeleton
					|| par1EntityLivingBase instanceof EntityAntiSkeleton){
				EntityDreadling dreadling = new EntityDreadling(par1EntityLivingBase.world);
				dreadling.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				dreadling.onInitialSpawn(null, null);
				par1EntityLivingBase.world.spawnEntity(dreadling);
			} else if(par1EntityLivingBase instanceof EntityPlayer && !wasKilled){
				wasKilled = true;
				EntityDreadling dreadling = new EntityDreadling(par1EntityLivingBase.world);
				dreadling.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				dreadling.onInitialSpawn(null, null);
				par1EntityLivingBase.world.spawnEntity(dreadling);
			} else if(par1EntityLivingBase instanceof EntitySkeletonGoliath){
				EntityDreadguard dg = new EntityDreadguard(par1EntityLivingBase.world);
				dg.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				dg.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(dg);
			} else if(par1EntityLivingBase instanceof EntityPig){
				EntityDemonPig dp = new EntityDemonPig(par1EntityLivingBase.world);
				dp.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				dp.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(dp);
			} else if(par1EntityLivingBase instanceof EntityCow){
				EntityDemonCow dc = new EntityDemonCow(par1EntityLivingBase.world);
				dc.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				dc.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(dc);
			} else if(par1EntityLivingBase instanceof EntityChicken){
				EntityDemonChicken dc = new EntityDemonChicken(par1EntityLivingBase.world);
				dc.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				dc.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(dc);
			} else if(par1EntityLivingBase instanceof EntitySheep){
				EntityDemonSheep ds = new EntityDemonSheep(par1EntityLivingBase.world);
				ds.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				ds.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(ds);
			} else if(par1EntityLivingBase instanceof EntityAbygolem) {
				EntityDreadgolem sg = new EntityDreadgolem(par1EntityLivingBase.world);
				sg.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				sg.onInitialSpawn(par1EntityLivingBase.world.getDifficultyForLocation(par1EntityLivingBase.getPosition()),(IEntityLivingData)null);
				par1EntityLivingBase.world.spawnEntity(sg);
			} else if(!(par1EntityLivingBase instanceof EntityPlayer)){
				EntityDreadSpawn ds = new EntityDreadSpawn(par1EntityLivingBase.world);
				ds.copyLocationAndAnglesFrom(par1EntityLivingBase);
				par1EntityLivingBase.world.removeEntity(par1EntityLivingBase);
				par1EntityLivingBase.world.spawnEntity(ds);
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
		return 1;
	}

	@Override
	public List<ItemStack> getCurativeItems()
	{
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(ACItems.antidote, 1, 1));
		return list;
	}
}
