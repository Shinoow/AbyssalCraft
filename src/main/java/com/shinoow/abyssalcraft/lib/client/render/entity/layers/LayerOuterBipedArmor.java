package com.shinoow.abyssalcraft.lib.client.render.entity.layers;

import java.util.Map;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.lib.util.items.IOuterArmor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerOuterBipedArmor extends LayerBipedArmor {

	private final RenderLivingBase<?> renderer;
	private float alpha = 1.0F;
	private float colorR = 1.0F;
	private float colorG = 1.0F;
	private float colorB = 1.0F;
	private boolean skipRenderGlint;
	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.<String, ResourceLocation>newHashMap();
	private static final ResourceLocation DEFAULT = new ResourceLocation("abyssalcraft:textures/armor/default.png");

	public LayerOuterBipedArmor(RenderLivingBase<?> rendererIn) {
		super(rendererIn);
		this.renderer = rendererIn;
	}

	protected void initArmor()
	{
		this.modelLeggings = new ModelBiped(0.8F);
		this.modelArmor = new ModelBiped(1.4F);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
	}

	private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
	{
		ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

		if (itemstack.getItem() instanceof ItemArmor && itemstack.getItem() instanceof IOuterArmor)
		{
			ItemArmor itemarmor = (ItemArmor)itemstack.getItem();

			if (itemarmor.getEquipmentSlot() == slotIn)
			{
				ModelBiped t = this.getModelFromSlot(slotIn);
				t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
				t.setModelAttributes(this.renderer.getMainModel());
				t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
				this.setModelSlotVisible(t, slotIn);
				boolean flag = this.isLegSlot(slotIn);
				this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, null));

				{
					if (itemarmor.hasOverlay(itemstack)) // Allow this for anything, not only cloth
					{
						int i = itemarmor.getColor(itemstack);
						float f = (float)(i >> 16 & 255) / 255.0F;
						float f1 = (float)(i >> 8 & 255) / 255.0F;
						float f2 = (float)(i & 255) / 255.0F;
						GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
						t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
						this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, "overlay"));
					}
					{ // Non-colored
						GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
						t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					} // Default
					if (!this.skipRenderGlint && itemstack.hasEffect())
					{
						renderEnchantedGlint(this.renderer, entityLivingBaseIn, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
					}
				}
			}
		}
	}

	private boolean isLegSlot(EntityEquipmentSlot slotIn)
	{
		return slotIn == EntityEquipmentSlot.LEGS;
	}

	@Override
	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
	{
		if(stack.getItem() instanceof IOuterArmor) {
			String s1 = ((IOuterArmor) stack.getItem()).getOuterArmorTexture(stack, entity, slot, type);

			if(s1 != null) {
				ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

				if (resourcelocation == null)
				{
					resourcelocation = new ResourceLocation(s1);
					ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
				}
				
				return resourcelocation;
			}
		}

		return DEFAULT;
	}
}
