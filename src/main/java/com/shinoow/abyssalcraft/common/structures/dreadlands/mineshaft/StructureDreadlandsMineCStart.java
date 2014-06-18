package com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureDreadlandsMineCStart extends StructureStart
{

    public StructureDreadlandsMineCStart() {}

    @SuppressWarnings("unchecked")
	public StructureDreadlandsMineCStart(World par1World, Random par2Random, int par3, int par4)
    {
        super(par3, par4);
        StructureDreadlandsMineCPieces.Room room = new StructureDreadlandsMineCPieces.Room(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
        this.components.add(room);
        room.buildComponent(room, this.components, par2Random);
        this.updateBoundingBox();
        this.markAvailableHeight(par1World, par2Random, 10);
    }
}