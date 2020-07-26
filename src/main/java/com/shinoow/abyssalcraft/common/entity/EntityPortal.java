package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.world.TeleporterAC;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPortal extends EntityLiving {

	private static final DataParameter<Integer> DIMENSION = EntityDataManager.createKey(EntityPortal.class, DataSerializers.VARINT);

	public EntityPortal(World worldIn) {
		super(worldIn);
		setSize(1.0F, 2.0F);
	}

	public EntityPortal setDestination(int dim) {

		dataManager.set(DIMENSION, dim);
		return this;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(DIMENSION, 0);
	}

	@Override
	public void onUpdate() {
		if(!world.isRemote && ticksExisted % 10 == 0) {
			boolean playerNearby = ACConfig.portalSpawnsNearPlayer ? world.getClosestPlayer(posX, posY, posZ, 32, false) != null : true;
			boolean nearbyMobs = world.getEntitiesWithinAABB(EntityAbyssalZombie.class, new AxisAlignedBB(getPosition()).grow(16)).size() < 10;

			if (world.provider.getDimension() != ACLib.abyssal_wasteland_id && world.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < world.getDifficulty().getDifficultyId()
					&& playerNearby && nearbyMobs)
			{
				int i = getPosition().getY();
				BlockPos blockpos;

				for (blockpos = getPosition(); !world.getBlockState(blockpos).isSideSolid(world, blockpos, EnumFacing.UP) && blockpos.getY() > 0; blockpos = blockpos.down())
					;

				if (i > 0 && !world.getBlockState(blockpos.up()).isNormalCube())
				{
					//TODO set based on destination dimension
					Entity entity = ItemMonsterPlacer.spawnCreature(world, EntityList.getKey(EntityAbyssalZombie.class), blockpos.getX() + 0.5D, blockpos.getY() + 1.1D, blockpos.getZ() + 0.5D);

					if (entity != null)
						entity.timeUntilPortal = entity.getPortalCooldown();
				}
			}
		}
	}

	@Override
	protected void collideWithEntity(Entity entity)
	{
		if(!(entity instanceof EntityPortal)) {
			if (!entity.isRiding() && !entity.isBeingRidden() && !world.isRemote && !entity.isDead && entity.isNonBoss())
				if(entity.timeUntilPortal > 0)
					entity.timeUntilPortal = entity instanceof EntityPlayerMP ? ACConfig.portalCooldown :  entity.getPortalCooldown();
				else {
					entity.timeUntilPortal = entity instanceof EntityPlayerMP ? ACConfig.portalCooldown :  entity.getPortalCooldown();
					TeleporterAC.changeDimension(entity, dataManager.get(DIMENSION));
				}
		}
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public EnumPushReaction getPushReaction()
	{
		return EnumPushReaction.IGNORE;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		return true;
	}
}
