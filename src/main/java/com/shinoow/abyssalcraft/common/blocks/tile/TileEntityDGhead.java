package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityDGhead extends TileEntity {

	private int rotation;
	public int direction;

	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("Rot", (byte)(this.rotation & 255));
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		this.rotation = par1NBTTagCompound.getByte("Rot");
	}

	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, nbtTag);
	}

	public void setRotation(int par1)
	{
		this.rotation = par1;
	}

	@SideOnly(Side.CLIENT)
	public int func_82119_b()
	{
		return this.rotation;
	}

}
