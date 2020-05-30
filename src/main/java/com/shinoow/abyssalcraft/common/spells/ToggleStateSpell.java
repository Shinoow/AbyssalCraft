package com.shinoow.abyssalcraft.common.spells;

import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class ToggleStateSpell extends Spell {

	public ToggleStateSpell() {
		super("toggleState", 3, 1000F, new Object[] {Blocks.LEVER});

	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		MutableBlockPos pos1 = new MutableBlockPos();
		for(int x = pos.getX() - 16; x < pos.getX() + 16; x++)
			for(int y = pos.getY() - 16; y < pos.getY() + 16; y++)
				for(int z = pos.getZ() - 16; z < pos.getZ() + 16; z++) {
					pos1.setPos(x, y, z);
					TileEntity te = world.getTileEntity(pos1);
					if(te != null) {
						IItemTransferCapability cap = ItemTransferCapability.getCap(te);
						if(cap != null && !cap.getTransferConfigurations().isEmpty())
							return true;
					}
				}
		return false;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {

	}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {

		MutableBlockPos pos1 = new MutableBlockPos();
		for(int x = pos.getX() - 16; x < pos.getX() + 16; x++)
			for(int y = pos.getY() - 16; y < pos.getY() + 16; y++)
				for(int z = pos.getZ() - 16; z < pos.getZ() + 16; z++) {
					pos1.setPos(x, y, z);
					TileEntity te = world.getTileEntity(pos1);
					if(te != null) {
						IItemTransferCapability cap = ItemTransferCapability.getCap(te);
						if(cap != null && !cap.getTransferConfigurations().isEmpty())
							cap.setRunning(!cap.isRunning());
					}
				}
	}
}
