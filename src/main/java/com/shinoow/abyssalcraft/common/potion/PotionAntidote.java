package com.shinoow.abyssalcraft.common.potion;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionAntidote extends Potion {

	private Supplier<Potion> dispellPotion;
	
	public PotionAntidote(boolean isBadEffectIn, int liquidColorIn, Supplier<Potion> dispellPotion) {
		super(isBadEffectIn, liquidColorIn);
		this.dispellPotion = dispellPotion;
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}
	
	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		
		entityLivingBaseIn.removePotionEffect(dispellPotion.get());
	}
	
	@Override
	public boolean isReady(int par1, int par2)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 3;
	}
}
