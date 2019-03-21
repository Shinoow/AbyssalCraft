package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class BiomeUtil {
    public static void updateBiome(World worldIn, BlockPos pos, Biome b) {
        updateBiome(worldIn, pos, b, false);
    }
    
    public static void updateBiome(World worldIn, BlockPos pos, Biome b, boolean batched) {
        Chunk c = worldIn.getChunkFromBlockCoords(pos);
        c.getBiomeArray()[(pos.getZ() & 0xF) << 4 | pos.getX() & 0xF] = (byte)Biome.getIdForBiome(b);
        c.setModified(true);
        PacketDispatcher.sendToDimension(new CleansingRitualMessage(pos.getX(), pos.getZ(), Biome.getIdForBiome(b), batched), worldIn.provider.getDimension());
    }
}
