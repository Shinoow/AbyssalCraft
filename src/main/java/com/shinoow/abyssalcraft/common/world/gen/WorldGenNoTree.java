package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenNoTree extends WorldGenTrees {

	public WorldGenNoTree() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos blockPos) {
		return false;
	}
}
