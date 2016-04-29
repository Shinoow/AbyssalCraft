package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;

public class FireMessage extends AbstractServerMessage<FireMessage> {

	private int x;
	private int y;
	private int z;

	public FireMessage() {}

	public FireMessage(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		x = ByteBufUtils.readVarInt(buffer, 5);
		y = ByteBufUtils.readVarInt(buffer, 5);
		z = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, x, 5);
		ByteBufUtils.writeVarInt(buffer, y, 5);
		ByteBufUtils.writeVarInt(buffer, z, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		World world = player.worldObj;


		world.playSoundEffect(player.posX, player.posY, player.posZ, "random.fizz", 1.0F, 1.0F);
		world.setBlockToAir(x, y, z);
	}

}
