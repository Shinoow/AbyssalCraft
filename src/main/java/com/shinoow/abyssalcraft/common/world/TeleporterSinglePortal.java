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
package com.shinoow.abyssalcraft.common.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class TeleporterSinglePortal extends Teleporter
{
	private final WorldServer worldServerInstance;
	private final Random random;
	private double movementFactor;

	public TeleporterSinglePortal(WorldServer par1WorldServer, int prevDimension, WorldProvider provider)
	{
		super(par1WorldServer);
		worldServerInstance = par1WorldServer;
		random = new Random(par1WorldServer.getSeed());
		movementFactor = provider.getMovementFactor() / worldServerInstance.provider.getMovementFactor();

	}

	public static void changeDimension(Entity entity, int dimension) {
		Teleporter teleporter = new TeleporterSinglePortal(entity.getServer().getWorld(dimension), entity.dimension, entity.world.provider);
		if(entity instanceof EntityPlayerMP) {
			if(!ForgeHooks.onTravelToDimension(entity, dimension)) return;
			if(!((EntityPlayerMP)entity).capabilities.isCreativeMode) {
				ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, true, "field_184851_cj");
				ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1, "field_71144_ck");
				ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1.0F, "field_71149_ch");
				ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1, "field_71146_ci");
			}
			((EntityPlayerMP)entity).server.getPlayerList().transferPlayerToDimension((EntityPlayerMP)entity, dimension, teleporter);
		} else
			entity.changeDimension(dimension, teleporter);
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
		placeInExistingPortal(entityIn, rotationYaw);
	}

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float p_180620_2_) {

		double posX = entityIn.posX * movementFactor;
		double posZ = entityIn.posZ * movementFactor;

		int i = 16;
		double d0 = -1.0D;
		int j = MathHelper.floor(posX);
		int k = MathHelper.floor(entityIn.posY);
		int l = MathHelper.floor(posZ);
		int i1 = j;
		int j1 = k;
		int k1 = l;
		int l1 = 0;
		int i2 = random.nextInt(4);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int j2 = j - i; j2 <= j + i; ++j2)
		{
			double d1 = j2 + 0.5D - posX;

			for (int l2 = l - i; l2 <= l + i; ++l2)
			{
				double d2 = l2 + 0.5D - posZ;
				label142:

					for (int j3 = worldServerInstance.getActualHeight() - 1; j3 >= 6; --j3)
						if (worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2)))
						{
							while (j3 > 6 && worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2)))
								--j3;

							for (int k3 = i2; k3 < i2 + 4; ++k3)
							{
								int l3 = k3 % 2;
								int i4 = 1 - l3;

								if (k3 % 4 >= 2)
								{
									l3 = -l3;
									i4 = -i4;
								}

								for (int j4 = 0; j4 < 3; ++j4)
									for (int k4 = 0; k4 < 4; ++k4)
										for (int l4 = -1; l4 < 4; ++l4)
										{
											int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
											int j5 = j3 + l4;
											int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
											blockpos$mutableblockpos.setPos(i5, j5, k5);

											if (l4 < 0 && !worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !worldServerInstance.isAirBlock(blockpos$mutableblockpos))
												continue label142;
										}

								double d5 = j3 + 0.5D - entityIn.posY;
								double d7 = d1 * d1 + d5 * d5 + d2 * d2;

								if (d0 < 0.0D || d7 < d0)
								{
									d0 = d7;
									i1 = j2;
									j1 = j3;
									k1 = l2;
									l1 = k3 % 4;
								}
							}
						}
			}
		}

		if (d0 < 0.0D)
			for (int l5 = j - i; l5 <= j + i; ++l5)
			{
				double d3 = l5 + 0.5D - posX;

				for (int j6 = l - i; j6 <= l + i; ++j6)
				{
					double d4 = j6 + 0.5D - posZ;
					label562:

						for (int i7 = worldServerInstance.getActualHeight() - 1; i7 >= 6; --i7)
							if (worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6)))
							{
								while (i7 > 6 && worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6)))
									--i7;

								for (int k7 = i2; k7 < i2 + 2; ++k7)
								{
									int j8 = k7 % 2;
									int j9 = 1 - j8;

									for (int j10 = 0; j10 < 4; ++j10)
										for (int j11 = -1; j11 < 4; ++j11)
										{
											int j12 = l5 + (j10 - 1) * j8;
											int i13 = i7 + j11;
											int j13 = j6 + (j10 - 1) * j9;
											blockpos$mutableblockpos.setPos(j12, i13, j13);

											if (j11 < 0 && !worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !worldServerInstance.isAirBlock(blockpos$mutableblockpos))
												continue label562;
										}

									double d6 = i7 + 0.5D - entityIn.posY;
									double d8 = d3 * d3 + d6 * d6 + d4 * d4;

									if (d0 < 0.0D || d8 < d0)
									{
										d0 = d8;
										i1 = l5;
										j1 = i7;
										k1 = j6;
										l1 = k7 % 2;
									}
								}
							}
				}
			}

		int i6 = i1;
		int k2 = j1;
		int k6 = k1;
		int l6 = l1 % 2;
		int i3 = 1 - l6;

		if (l1 % 4 >= 2)
		{
			l6 = -l6;
			i3 = -i3;
		}

		if (d0 < 0.0D)
		{
			IBlockState state = worldServerInstance.getBiome(new BlockPos(i6, k2, k6)).topBlock;
			j1 = MathHelper.clamp(j1, 70, worldServerInstance.getActualHeight() - 10);
			k2 = j1;

			for (int j7 = -1; j7 <= 1; ++j7)
				for (int l7 = 1; l7 < 3; ++l7)
					for (int k8 = -1; k8 < 3; ++k8)
					{
						int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
						int k10 = k2 + k8;
						int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
						boolean flag = k8 < 0;
						worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? state : Blocks.AIR.getDefaultState());
					}
		}

		if (entityIn instanceof EntityPlayerMP)
			((EntityPlayerMP)entityIn).connection.setPlayerLocation(i6, k2, k6, entityIn.rotationYaw, entityIn.rotationPitch);
		else 
			entityIn.setLocationAndAngles(i6, k2, k6, entityIn.rotationYaw, entityIn.rotationPitch);

		return true;

	}
}
