/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items.armor;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.items.IRevealer;
import thaumcraft.api.items.IVisDiscountGear;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACTabs;

@InterfaceList(value = { @Interface(iface = "thaumcraft.api.items.IVisDiscountGear", modid = "Thaumcraft"),
		@Interface(iface = "thaumcraft.api.items.IRevealer", modid = "Thaumcraft")})
public class ItemDepthsArmor extends ItemArmor implements IVisDiscountGear, IRevealer {
	public ItemDepthsArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		setUnlocalizedName(name);
		setCreativeTab(ACTabs.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
	{
		if(stack.getItem() == ACItems.depths_helmet || stack.getItem() == ACItems.depths_chestplate || stack.getItem() == ACItems.depths_boots)
			return "abyssalcraft:textures/armor/depths_1.png";

		if(stack.getItem() == ACItems.depths_leggings)
			return "abyssalcraft:textures/armor/depths_2.png";
		else return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if(world.isRemote) return;
		if (itemstack.getItem() == ACItems.depths_helmet)
		{
			player.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			if(player.getActivePotionEffect(AbyssalCraftAPI.coralium_plague) !=null)
				player.removePotionEffect(AbyssalCraftAPI.coralium_plague.getId());
		}
		if (itemstack.getItem() == ACItems.depths_chestplate)
			player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 20, 0));
		if (itemstack.getItem() == ACItems.depths_leggings)
			player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 20, 1));
		if (itemstack.getItem() == ACItems.depths_boots)
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks){
		final ResourceLocation coraliumBlur = new ResourceLocation("abyssalcraft:textures/misc/coraliumblur.png");


		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && stack != null && stack.getItem() == ACItems.depths_helmet) {
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

			Tessellator t = Tessellator.getInstance();

			ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
			int width = scale.getScaledWidth();
			int height = scale.getScaledHeight();

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			Minecraft.getMinecraft().renderEngine.bindTexture(coraliumBlur);

			WorldRenderer wr = t.getWorldRenderer();

			wr.begin(7, DefaultVertexFormats.POSITION_TEX);
			wr.pos(0.0D, height, 90.0D).tex(0.0D, 1.0D).endVertex();;
			wr.pos(width, height, 90.0D).tex(1.0D, 1.0D).endVertex();;
			wr.pos(width, 0.0D, 90.0D).tex(1.0D, 0.0D).endVertex();;
			wr.pos(0.0D, 0.0D, 90.0D).tex(0.0D, 0.0D).endVertex();;
			t.draw();

			GL11.glPopAttrib();
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack stack, EntityPlayer player,
			Aspect aspect) {
		return stack.getItem() == ACItems.depths_helmet ? 5 : stack.getItem() == ACItems.depths_chestplate ? 2 :
			stack.getItem() == ACItems.depths_leggings ? 2 : stack.getItem() == ACItems.depths_boots ? 1 : 0;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean B){
		if(Loader.isModLoaded("Thaumcraft")){
			if(is.getItem() == ACItems.depths_helmet)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 5%");
			if(is.getItem() == ACItems.depths_chestplate)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 2%");
			if(is.getItem() == ACItems.depths_leggings)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 2%");
			if(is.getItem() == ACItems.depths_boots)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 1%");
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {

		return itemstack.getItem() == ACItems.depths_helmet;
	}
}