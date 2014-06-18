package com.shinoow.abyssalcraft.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**Shin = lazy. Deal with it.*/
public class ItemBlockColorName extends ItemBlock {

	public ItemBlockColorName(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(this.getUnlocalizedName().contains("BOA"))
			return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		else if(this.getUnlocalizedName().contains("BOC"))
			return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		else if(this.getUnlocalizedName().contains("ODB"))
			return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		else if(this.getUnlocalizedName().contains("AS"))
			return EnumChatFormatting.BLUE + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		
		return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}
}