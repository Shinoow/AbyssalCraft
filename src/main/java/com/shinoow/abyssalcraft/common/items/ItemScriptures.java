package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.api.knowledge.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.NecroDataCapMessage;
import com.shinoow.abyssalcraft.lib.item.ItemACBasic;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemScriptures extends ItemACBasic {

	public ItemScriptures() {
		super("scriptures_omniscience");
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		
		if(!world.isRemote){
			INecroDataCapability cap = NecroDataCapability.getCap(player);
			if(!cap.hasUnlockedAllKnowledge()){
				cap.unlockAllKnowledge(true);
				if(!player.capabilities.isCreativeMode)
					stack.shrink(1);
			}

			cap.setLastSyncTime(System.currentTimeMillis());
			PacketDispatcher.sendTo(new NecroDataCapMessage(player), (EntityPlayerMP)player);
		}
		
		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		l.add("Everything... all in your hands...");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
