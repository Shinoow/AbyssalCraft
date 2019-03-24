package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelShubOffspring;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerShubOffspringEyes;
import com.shinoow.abyssalcraft.common.entity.EntityShubOffspring;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShubOffspring extends RenderLiving<EntityShubOffspring>
{
	private static final ResourceLocation offspringTextures = new ResourceLocation("abyssalcraft:textures/model/shub_offspring.png");

	public RenderShubOffspring(RenderManager manager)
	{
		super(manager, new ModelShubOffspring(), 1.0F);
		addLayer(new LayerShubOffspringEyes(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShubOffspring par1EntityAntiSpider)
	{
		return offspringTextures;
	}
}
