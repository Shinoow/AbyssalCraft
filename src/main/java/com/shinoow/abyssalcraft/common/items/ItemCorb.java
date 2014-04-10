package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.ItemGeneralAC;

public class ItemCorb extends ItemGeneralAC

{
	public ItemCorb() {
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(1000);
	}

	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int l, float f, float f1, float f3){ //Called when an item is right clicked on a block
		if(w.getBlock(x, y, z) == Blocks.stone){
			w.setBlock(x, y, z, AbyssalCraft.Darkstone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Darkstone){
			w.setBlock(x, y, z, Blocks.stone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.cobblestone){ 
			w.setBlock(x, y, z, AbyssalCraft.Darkstone_cobble); 
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Darkstone_cobble){
			w.setBlock(x, y, z, Blocks.cobblestone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.stonebrick){ 
			w.setBlock(x, y, z, AbyssalCraft.Darkstone_brick);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Darkstone_brick){
			w.setBlock(x, y, z, Blocks.stonebrick);
			is.damageItem(50, player);
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){ //Additional info (eg. the names of music discs)
		l.add("This item can infuse Abyssalnite items,");
		l.add("adding new powers to them.");
		l.add("It can also alter some overworld");
		l.add("elements (Blocks)");
	}

	public EnumRarity getRarity(ItemStack is){
		return EnumRarity.epic;
	}

	public boolean hasEffect(ItemStack is){
		return true;
	}

}
