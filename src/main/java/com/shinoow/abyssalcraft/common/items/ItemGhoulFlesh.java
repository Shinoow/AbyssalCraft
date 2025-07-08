package com.shinoow.abyssalcraft.common.items;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.IResearchableItem;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGhoulFlesh extends ItemFood implements IResearchableItem {

	private IResearchItem condition = ResearchItems.DEFAULT;

	public ItemGhoulFlesh(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setCreativeTab(ACTabs.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.getItem() == ACItems.ghoul_flesh) {
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 0));
		} else if(itemStack.getItem() == ACItems.anti_ghoul_flesh){
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 0));
		} else if (itemStack.getItem() == ACItems.abyssal_ghoul_flesh){
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 1));
			if(!EntityUtil.isPlayerCoralium(entityPlayer))
				entityPlayer.addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 600, 0));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 0));
		} else if(itemStack.getItem() == ACItems.dreaded_ghoul_flesh) {
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 600, 0));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 0));
		} else if(itemStack.getItem() == ACItems.omothol_ghoul_flesh) {
			if(EntityUtil.isPlayerCoralium(entityPlayer)){
				entityPlayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100));
				entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 1));
				entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
			} else {
				entityPlayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100));
				entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400, 1));
				entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300));
			}
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 40));
		} else if(itemStack.getItem() == ACItems.shadow_ghoul_flesh) {
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 0));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 600));
		}
	}

	@Override
	public Item setResearchItem(IResearchItem condition) {
		this.condition = condition;
		return this;
	}

	@Override
	public IResearchItem getResearchItem(ItemStack stack) {

		return condition;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public net.minecraft.client.gui.FontRenderer getFontRenderer(ItemStack stack)
	{
		return APIUtils.getFontRenderer(stack);
	}
}
