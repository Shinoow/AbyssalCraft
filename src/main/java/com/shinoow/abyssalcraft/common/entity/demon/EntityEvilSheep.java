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
package com.shinoow.abyssalcraft.common.entity.demon;

import java.util.UUID;

import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.EvilSheepMessage;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEvilSheep extends EntityMob implements IShearable {

	private UUID playerUUID = null;
	private String playerName = null;
	private int sheepTimer;
	private EntityAIEatGrass entityAIEatGrass = new EntityAIEatGrass(this);

	public EntityEvilSheep(World worldIn) {
		super(worldIn);
		setSize(0.9F, 1.3F);
		isImmuneToFire = true;
		double var2 = 0.35D;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, var2, true));
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

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	public String getName()
	{
		return I18n.translateToLocal("entity.Sheep.name");
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_SHEEP_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SHEEP_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
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

		if (world.isRemote)
			sheepTimer = Math.max(0, sheepTimer - 1);

		if(ticksExisted % 20 == 0)
			if(playerUUID != null && playerName != null && playerName.length() > 0)
				if(world.isRemote){}
				else PacketDispatcher.sendToDimension(new EvilSheepMessage(playerUUID, playerName, getEntityId()), world.provider.getDimension());
		super.onLivingUpdate();
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if(!world.isRemote)
			if(!(par1DamageSource.getEntity() instanceof EntityLesserShoggoth))
			{
				EntityDemonSheep demonsheep = new EntityDemonSheep(world);
				demonsheep.copyLocationAndAnglesFrom(this);
				world.removeEntity(this);
				demonsheep.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
				world.spawnEntity(demonsheep);
			}
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_EVIL_SHEEP;
	}

	@Override public boolean isShearable(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
	@Override
	public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess w, BlockPos pos, int fortune)
	{
		int i = 1 + rand.nextInt(3);

		java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
		for (int j = 0; j < i; ++j)
			ret.add(new ItemStack(playerUUID != null && playerName != null ? Items.ROTTEN_FLESH : Item.getItemFromBlock(Blocks.WOOL)));

		playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
		playSound(SoundEvents.ENTITY_GHAST_HURT, 1.0F, 0.2F);
		if(!world.isRemote){
			EntityDemonSheep demonsheep = new EntityDemonSheep(world);
			demonsheep.copyLocationAndAnglesFrom(this);
			world.removeEntity(this);
			demonsheep.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
			world.spawnEntity(demonsheep);
		}
		return ret;
	}
}
