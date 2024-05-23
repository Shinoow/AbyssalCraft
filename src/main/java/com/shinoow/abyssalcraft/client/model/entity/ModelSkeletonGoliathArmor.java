package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSkeletonGoliathArmor extends ModelSkeletonGoliath {

	public ModelRenderer chestplate;

	public ModelSkeletonGoliathArmor() {
		this(0.0F);
	}

	public ModelSkeletonGoliathArmor(float f) {
		super(f);

		this.chestplate = new ModelRenderer(this, 79, 0);
		this.chestplate.setRotationPoint(0.0F, -11.0F, -2.0F);
		this.chestplate.addBox(-6.0F, 0.0F, 0.0F, 13, 17, 5, f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);

		this.chestplate.render(f5);
	}

	@Override
	public void setInvisible(boolean invisible)
	{
		super.setInvisible(invisible);
		chestplate.showModel = invisible;
	}
}
