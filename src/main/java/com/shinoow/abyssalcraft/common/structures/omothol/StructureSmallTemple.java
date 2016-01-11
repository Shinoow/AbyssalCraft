package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.Random;

import com.shinoow.abyssalcraft.common.structures.IOmotholBuilding;
import com.shinoow.abyssalcraft.common.structures.StructureData;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class StructureSmallTemple extends WorldGenerator implements IOmotholBuilding {

	@Override
	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z, ForgeDirection dir) {

		switch(dir){
		case EAST:
			break;
		case NORTH:
			break;
		case SOUTH:
			break;
		case WEST:
			break;
		default:
			break;
		
		}
		
		return false;
	}

	@Override
	public StructureData getStructureData(ForgeDirection dir) {

		switch(dir){
		case EAST:
			return new StructureData(-5, 0, -4, 5, 10, 5);
		case NORTH:
			return new StructureData(-4, 0, -5, 5, 10, 5);
		case SOUTH:
			return new StructureData(-5, 0, -5, 4, 10, 5);
		case WEST:
			return new StructureData(-5, 0, -5, 4, 10, 5);
		default:
			return null;
		}
	}
}