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
package com.shinoow.abyssalcraft.common.entity.demon;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.EvilSheepMessage;

public class EntityEvilSheep extends EntityMob {

	private UUID playerUUID = null;
	private String playerName = null;
	private int sheepTimer;
	private EntityAIEatGrass entityAIEatGrass = new EntityAIEatGrass(this);

	public EntityEvilSheep(World worldIn) {
		super(worldIn);
		setSize(0.9F, 1.3F);
		((PathNavigateGround)getNavigator()).setAvoidsWater(true);
		isImmuneToFire = true;
		double var2 = 0.35D;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, var2, true));
		tasks.addTask(3, entityAIEatGrass);
		tasks.addTask(4, new EntityAIWander(this, var2));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void updateAITasks()
	{
		sheepTimer = entityAIEatGrass.getEatingGrassTimer();
		super.updateAITasks();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(AbyssalCraft.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 1.5F);

		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	public String getName()
	{
		return StatCollector.translateToLocal("entity.Sheep.name");
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.sheep.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.sheep.say";
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound("mob.sheep.step", 0.15F, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	public float getHeadRotationPointY(float p_70894_1_)
	{
		return sheepTimer <= 0 ? 0.0F : sheepTimer >= 4 && sheepTimer <= 36 ? 1.0F : sheepTimer < 4 ? (sheepTimer - p_70894_1_) / 4.0F : -(sheepTimer - 40 - p_70894_1_) / 4.0F;
	}

	@SideOnly(Side.CLIENT)
	public float getHeadRotationAngleX(float p_70890_1_)
	{
		if (sheepTimer > 4 && sheepTimer <= 36)
		{
			float f = (sheepTimer - 4 - p_70890_1_) / 32.0F;
			return (float)Math.PI / 5F + (float)Math.PI * 7F / 100F * MathHelper.sin(f * 28.7F);
		} else
			return sheepTimer > 0 ? (float)Math.PI / 5F : rotationPitch / (180F / (float)Math.PI);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound){
		super.readEntityFromNBT(nbttagcompound);

		if(nbttagcompound.hasKey("PlayerUUID"))
			playerUUID = UUID.fromString(nbttagcompound.getString("PlayerUUID"));
		if(nbttagcompound.hasKey("PlayerName"))
			playerName = nbttagcompound.getString("PlayerName");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound){
		super.writeEntityToNBT(nbttagcompound);

		if(playerUUID != null)
			nbttagcompound.setString("PlayerUUID", playerUUID.toString());
		if(playerName != null && playerName.length() > 0)
			nbttagcompound.setString("PlayerName", playerName);
	}

	public UUID getKilledPlayerUUID(){
		return playerUUID;
	}

	public String getKilledPlayerName(){
		return playerName;
	}

	public void setKilledPlayer(EntityPlayer player){
		playerUUID = player.getUniqueID();
		playerName = player.getName();
	}

	@SideOnly(Side.CLIENT)
	public void setKilledPlayer(UUID uuid, String name){
		playerUUID = uuid;
		playerName = name;
	}

	@Override
	public void onLivingUpdate(){

		if (worldObj.isRemote)
			sheepTimer = Math.max(0, sheepTimer - 1);

		if(ticksExisted % 20 == 0)
			if(playerUUID != null && playerName != null && playerName.length() > 0)
				if(worldObj.isRemote){}
				else PacketDispatcher.sendToDimension(new EvilSheepMessage(playerUUID, playerName, getEntityId()), worldObj.provider.getDimensionId());
		super.onLivingUpdate();
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if(!worldObj.isRemote)
			if(!(par1DamageSource.getEntity() instanceof EntityLesserShoggoth))
			{
				EntityDemonSheep demonsheep = new EntityDemonSheep(worldObj);
				demonsheep.copyLocationAndAnglesFrom(this);
				worldObj.removeEntity(this);
				demonsheep.onInitialSpawn(worldObj.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
				worldObj.spawnEntityInWorld(demonsheep);
			}
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool)), 0.0F);

		int i = rand.nextInt(2) + 1 + rand.nextInt(1 + p_70628_2_);

		for (int j = 0; j < i; ++j)
			dropItem(Items.mutton, 1);
	}
}
