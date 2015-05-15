/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.*;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class EntityLesserShoggoth extends EntityMob implements ICoraliumEntity, IDreadEntity {

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);

	private static List<Class<? extends EntityLivingBase>> noms = new ArrayList<Class<? extends EntityLivingBase>>();

	public EntityLesserShoggoth(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		for(int i = 0; i < noms.size(); i++)
			tasks.addTask(2, new EntityAIAttackOnCollide(this, noms.get(i), 0.40D, true));
		tasks.addTask(3, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadling.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadSpawn.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDemonPig.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadguard.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityOmotholWarden.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityOmotholGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityLesserShoggoth.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		for(int i = 0; i < noms.size(); i++)
			targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, noms.get(i), 0, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		getDataWatcher().addObject(14, Byte.valueOf((byte)0));
		getDataWatcher().addObject(16, Byte.valueOf((byte)0));
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public String getCommandSenderName()
	{
		switch (getShoggothType())
		{
		case 0:
			return StatCollector.translateToLocal("entity.lessershoggoth.name");
		case 1:
			return StatCollector.translateToLocal("entity.lessershoggoth.abyssal.name");
		case 2:
			return StatCollector.translateToLocal("entity.lessershoggoth.dreaded.name");
		case 3:
			return StatCollector.translateToLocal("entity.lessershoggoth.omothol.name");
		case 4:
			return StatCollector.translateToLocal("entity.lessershoggoth.dark.name");
		default:
			return StatCollector.translateToLocal("entity.lessershoggoth.name");
		}
	}

	@Override
	public boolean isChild()
	{
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	public void setChild(boolean par1)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote)
		{
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				attributeinstance.applyModifier(babySpeedBoostModifier);
		}
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	public int getShoggothType() {

		return dataWatcher.getWatchableObjectByte(14);
	}

	public void setShoggothType(int par1) {

		dataWatcher.updateObject(14, Byte.valueOf((byte)par1));
	}

	public void setFoodLevel(int par1){

		dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
	}

	public int getFoodLevel(){

		return dataWatcher.getWatchableObjectByte(16);
	}

	public void feed(){
		int food = getFoodLevel() + 1;
		dataWatcher.updateObject(16, Byte.valueOf((byte)food));
	}

	@Override
	public void onLivingUpdate()
	{
		if(getFoodLevel() == 3 && !worldObj.isRemote){
			setFoodLevel(0);
			if(!isChild()){
				EntityLesserShoggoth shoggoth = new EntityLesserShoggoth(worldObj);
				shoggoth.copyLocationAndAnglesFrom(this);
				shoggoth.onSpawnWithEgg((IEntityLivingData)null);
				shoggoth.setChild(true);
				worldObj.spawnEntityInWorld(shoggoth);
				playSound("mob.chicken.plop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			}
			else setChild(false);
		}

		super.onLivingUpdate();
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				if(worldObj.provider.dimensionId == AbyssalCraft.configDimId1 &&
				!EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 100));
				else if(worldObj.provider.dimensionId == AbyssalCraft.configDimId2 &&
						!EntityUtil.isEntityDread((EntityLivingBase)par1Entity))
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
				else if(worldObj.provider.dimensionId == AbyssalCraft.configDimId3)
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100));
				else if(worldObj.provider.dimensionId == AbyssalCraft.configDimId4)
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100));

		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par1DamageSource.isProjectile())
			return false;

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return new ItemStack(AbyssalCraft.shoggothFlesh, 1, getShoggothType()).getItem();
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return getShoggothType() == 4 ? AbyssalCraftAPI.SHADOW : EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);

		if (par1NBTTagCompound.hasKey("ShoggothType"))
		{
			byte var2 = par1NBTTagCompound.getByte("ShoggothType");
			setShoggothType(var2);
		}

		if(par1NBTTagCompound.hasKey("FoodLevel")){
			byte var2 = par1NBTTagCompound.getByte("FoodLevel");
			setFoodLevel(var2);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setByte("ShoggothType", (byte)getShoggothType());

		par1NBTTagCompound.setByte("FoodLevel", (byte)getFoodLevel());
	}

	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if(isFood(par1EntityLivingBase))
			feed();
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onSpawnWithEgg(par1EntityLivingData);

		if(worldObj.provider.dimensionId == 0)
			setShoggothType(0);
		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId1)
			setShoggothType(1);
		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId2)
			setShoggothType(2);
		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId3)
			setShoggothType(3);
		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId4)
			setShoggothType(4);

		if (data == null)
			data = new EntityLesserShoggoth.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityLesserShoggoth.GroupData)
		{
			EntityLesserShoggoth.GroupData groupdata = (EntityLesserShoggoth.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
		}

		return (IEntityLivingData)data;
	}

	static {
		noms.add(EntityAnimal.class);
		noms.add(EntityAmbientCreature.class);
		noms.add(EntityWaterMob.class);
		noms.add(EntityEvilpig.class);
		noms.add(EntityDemonPig.class);
		noms.addAll(AbyssalCraftAPI.getShoggothFood());
	}

	/**
	 * Checks if the Entity class, it's superclass or it's superclass' superclass is food
	 * @param par1 The Entity to check
	 * @return true if the Entity was food, otherwise false
	 */
	private boolean isFood(EntityLivingBase par1){
		return noms.contains(par1.getClass()) ? true : noms.contains(par1.getClass().getSuperclass()) ? true :
			noms.contains(par1.getClass().getSuperclass().getSuperclass()) ? true : false;
	}

	class GroupData implements IEntityLivingData
	{
		public boolean isBaby;
		private GroupData(boolean par2)
		{
			isBaby = false;
			isBaby = par2;
		}

		GroupData(boolean par2, Object par4EntityLesserShoggothINNER1)
		{
			this(par2);
		}
	}
}