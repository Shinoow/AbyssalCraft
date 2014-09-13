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
package com.shinoow.abyssalcraft.common.potion;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.util.ACDamageSource;
import com.shinoow.abyssalcraft.core.api.entity.*;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

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
		par1EntityLivingBase.attackEntityFrom(ACDamageSource.antimatter, 5);
		if(par1EntityLivingBase instanceof AntiMob || par1EntityLivingBase instanceof AntiAnimal
				|| par1EntityLivingBase instanceof AntiAmbientCreature){
			par1EntityLivingBase.removePotionEffect(AbyssalCraft.antiMatter.id);
			par1EntityLivingBase.heal(5);
		}
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
			} else if(par1EntityLivingBase instanceof EntityDepthsghoul){
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