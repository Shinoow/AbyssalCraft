/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Patched PathNavigateClimber (see comment further down)
 */
public class PatchedPathNavigateClimber extends PathNavigateClimber {

	public PatchedPathNavigateClimber(EntityLiving entityLivingIn, World worldIn) {
		super(entityLivingIn, worldIn);
	}

	@Override
	protected void pathFollow()
	{
		Vec3d vec3d = getEntityPosition();
		int i = currentPath.getCurrentPathLength();

		for (int j = currentPath.getCurrentPathIndex(); j < currentPath.getCurrentPathLength(); ++j)
			if (currentPath.getPathPointFromIndex(j).y != Math.floor(vec3d.y))
			{
				i = j;
				break;
			}

		maxDistanceToWaypoint = entity.width > 0.75F ? entity.width / 2.0F : 0.75F - entity.width / 2.0F;
		Vec3d vec3d1 = currentPath.getCurrentPos();

		// https://github.com/MinecraftForge/MinecraftForge/pull/6091
		// stops random spinning while navigating
		if (MathHelper.abs((float)(entity.posX - (vec3d1.x + (entity.width + 1) / 2D))) < maxDistanceToWaypoint && MathHelper.abs((float)(entity.posZ - (vec3d1.z + (entity.width + 1) / 2D))) < maxDistanceToWaypoint && Math.abs(entity.posY - vec3d1.y) < 1.0D)
			currentPath.setCurrentPathIndex(currentPath.getCurrentPathIndex() + 1);

		int k = MathHelper.ceil(entity.width);
		int l = MathHelper.ceil(entity.height);
		int i1 = k;

		for (int j1 = i - 1; j1 >= currentPath.getCurrentPathIndex(); --j1)
			if (isDirectPathBetweenPoints(vec3d, currentPath.getVectorFromIndex(entity, j1), k, l, i1))
			{
				currentPath.setCurrentPathIndex(j1);
				break;
			}

		checkForStuck(vec3d);
	}
}
