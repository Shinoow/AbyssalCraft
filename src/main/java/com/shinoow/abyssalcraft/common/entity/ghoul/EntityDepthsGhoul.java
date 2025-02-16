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
package com.shinoow.abyssalcraft.common.entity.ghoul;

import java.util.UUID;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;
import com.shinoow.abyssalcraft.lib.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityDepthsGhoul extends EntityGhoulBase implements ICoraliumEntity {

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityDepthsGhoul.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> CHILD = EntityDataManager.createKey(EntityDepthsGhoul.class, DataSerializers.BOOLEAN);
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);

	private float ghoulWidth = -1.0F;
	private float ghoulHeight;

	public EntityDepthsGhoul(World par1World) {
		super(par1World);
		setSize(1.0F, 1.7F);
		tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 60.0D : 30.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 10.0D : 5.0D);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public String getName()
	{
		switch (getGhoulType())
		{
		case 0:
			return super.getName();
		case 1:
			return "Pete";
		case 2:
			return "Mr. Wilson";
		case 3:
			return "Dr. Orange";
		default:
			return super.getName();
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(CHILD, false);
		dataManager.register(TYPE, 0);
	}

	@Override
	public boolean isChild()
	{
		return dataManager.get(CHILD);
	}

	public void setChild(boolean par1)
	{
		dataManager.set(CHILD, par1);

		if (world != null && !world.isRemote)
		{
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				attributeinstance.applyModifier(babySpeedBoostModifier);
		}

		setChildSize(par1);
	}

	public int getGhoulType()
	{
		return dataManager.get(TYPE);
	}

	public void setGhoulType(int par1)
	{
		dataManager.set(TYPE, par1);
	}

	@Override
	public void onLivingUpdate()
	{
		if(ACConfig.ghouls_burn)
			EntityUtil.burnFromSunlight(this);

		if(world.isRemote)
			setChildSize(isChild());

		super.onLivingUpdate();
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		switch (getGhoulType())
		{
		case 0:
			return ACSounds.ghoul_normal_ambient;
		case 1:
			return ACSounds.ghoul_pete_ambient;
		case 2:
			return ACSounds.ghoul_wilson_ambient;
		case 3:
			return ACSounds.ghoul_orange_ambient;
		default:
			return ACSounds.ghoul_normal_ambient;
		}
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.coralium_plagued_flesh_on_a_bone;
	}

	@Override
	protected ResourceLocation getLootTable(){
		switch(getGhoulType()){
		case 0:
			return ACLoot.ENTITY_DEPTHS_GHOUL;
		case 1:
			return ACLoot.ENTITY_DEPTHS_GHOUL_PETE;
		case 2:
			return ACLoot.ENTITY_DEPTHS_GHOUL_WILSON;
		case 3:
			return ACLoot.ENTITY_DEPTHS_GHOUL_ORANGE;
		}
		return null;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);

		if (par1NBTTagCompound.hasKey("GhoulType"))
		{
			int var2 = par1NBTTagCompound.getInteger("GhoulType");
			setGhoulType(var2);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if(isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setInteger("GhoulType", getGhoulType());
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		IEntityLivingData data = super.onInitialSpawn(difficulty, par1EntityLivingData);

		int type = 0;

		if(world.rand.nextFloat() < 0.2F){
			int temp = world.rand.nextInt(4);
			if(temp < 4)
				type = temp;
		}

		setGhoulType(type);

		if (data == null)
			data = new EntityDepthsGhoul.GroupData(world.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityDepthsGhoul.GroupData)
		{
			EntityDepthsGhoul.GroupData groupdata = (EntityDepthsGhoul.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
		}

		return data;
	}

	public void setChildSize(boolean p_146071_1_)
	{
		multiplySize(p_146071_1_ ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float p_70105_1_, float p_70105_2_)
	{
		boolean flag = ghoulWidth > 0.0F && ghoulHeight > 0.0F;
		ghoulWidth = p_70105_1_;
		ghoulHeight = p_70105_2_;

		if (!flag)
			multiplySize(1.0F);
	}

	protected final void multiplySize(float p_146069_1_)
	{
		super.setSize(ghoulWidth * p_146069_1_, ghoulHeight * p_146069_1_);
	}

	class GroupData implements IEntityLivingData
	{
		public boolean isBaby;
		private GroupData(boolean par2)
		{
			isBaby = false;
			isBaby = par2;
		}

		GroupData(boolean par2, Object par4EntityDepthsGhoulINNER1)
		{
			this(par2);
		}
	}

	@Override
	public void onAttacking(Entity entity) {
		if(entity instanceof EntityLivingBase)
			if(world.provider.getDimension() == ACLib.abyssal_wasteland_id && !EntityUtil.isEntityCoralium((EntityLivingBase)entity)
			|| ACConfig.shouldInfect == true && !EntityUtil.isEntityCoralium((EntityLivingBase)entity))
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));
		if(getHeldItemMainhand().isEmpty() && isBurning() && rand.nextFloat() < world.getDifficulty().getId() * 0.3F)
			entity.setFire(2 * world.getDifficulty().getId());
	}

	@Override
	public float getBonusDamage() {
		return 1.5F;
	}
}
