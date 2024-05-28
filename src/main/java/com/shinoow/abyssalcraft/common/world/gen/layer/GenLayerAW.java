/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.gen.layer;

import net.minecraft.world.gen.layer.*;

public abstract class GenLayerAW extends GenLayer
{
	public GenLayerAW(long seed) {
		super(seed);
	}

	public static GenLayer[] makeTheWorld(long seed) {

		GenLayer genlayer = new GenLayerIsland(1L);
        genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
        GenLayer genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
        GenLayer genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
        GenLayer genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
        genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
        genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
        GenLayer genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
        GenLayer genlayeraddisland2 = new GenLayerAddIsland(3L, genlayerremovetoomuchocean);
        GenLayer genlayerzoom1 = new GenLayerZoom(2002L, genlayeraddisland2);
        genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
        GenLayer genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
        GenLayer genlayer4 = GenLayerZoom.magnify(1000L, genlayeraddisland3, 0); //eh...
        
        int i = 4;
        int j = i;
        
        GenLayer lvt_7_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
        GenLayer genlayerriverinit = new GenLayerRiverInit(100L, lvt_7_1_);
        GenLayer genlayerbiomeedge = new GenLayerBiomesAW(200L, genlayerriverinit);
        GenLayer lvt_9_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        GenLayer genlayerhills = new GenLayerHillsAW(1000L, genlayerbiomeedge, lvt_9_1_);
        GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, j);
        GenLayer genlayerriver = new GenLayerRiverAW(1L, genlayer5);
        GenLayer genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        
        for (int k = 0; k < i; ++k)
        {
            genlayerhills = new GenLayerZoom((long)(1000 + k), genlayerhills);

            if (k == 0)
            {
                genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
            }

//            if (k == 1 || i == 1)
//            {
//                genlayerhills = new GenLayerShore(1000L, genlayerhills);
//            }
        }
        GenLayer genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
        GenLayer genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(seed);
        genlayer3.initWorldGenSeed(seed);
//		GenLayer biomes = new GenLayerBiomesAW(1L);
//
//		biomes = new GenLayerZoom(1000L, biomes);
//		biomes = new GenLayerZoom(1001L, biomes);
//		biomes = new GenLayerZoom(1002L, biomes);
//		biomes = new GenLayerZoom(1003L, biomes);
//		biomes = new GenLayerZoom(1004L, biomes);
//
//		GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);
//
//		biomes.initWorldGenSeed(seed);
//		genlayervoronoizoom.initWorldGenSeed(seed);

        return new GenLayer[] {genlayerrivermix, genlayer3, genlayerrivermix};
	}
}
