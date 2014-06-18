/**AbyssalCraft Core
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.core.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelStarSpawnPlayer extends ModelBiped {

	public ModelRenderer tentacle1;
	public ModelRenderer tentacle2;
	public ModelRenderer tentacle3;
	public ModelRenderer tentacle4;
	public ModelRenderer limb1;
	public ModelRenderer limb2;
	public ModelRenderer limb3;
	public ModelRenderer limb4;

	public ModelStarSpawnPlayer()
	{
		this(0.0F);
	}

	public ModelStarSpawnPlayer(float par1)
	{
		this(par1, 0.0F, 64, 32);
	}

	public ModelStarSpawnPlayer(float par1, float par2, int par3, int par4)
	{
		textureWidth = par3;
		textureHeight = par4;

		tentacle1 = new ModelRenderer(this, 36, 8);
		tentacle1.setRotationPoint(-3F, 0F + par2, -6F);
		tentacle1.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		tentacle2 = new ModelRenderer(this, 36, 8);
		tentacle2.setRotationPoint(-1F, 0F + par2, -6F);
		tentacle2.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		tentacle3 = new ModelRenderer(this, 36, 8);
		tentacle3.setRotationPoint(1F, 0F + par2, -6F);
		tentacle3.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		tentacle4 = new ModelRenderer(this, 36, 8);
		tentacle4.setRotationPoint(3F, 0F + par2, -6F);
		tentacle4.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		limb1 = new ModelRenderer(this, 36, 8);
		limb1.setRotationPoint(0,0,0);
		limb1.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle1.addChild(limb1);
		limb2 = new ModelRenderer(this, 36, 8);
		limb2.setRotationPoint(0,0,0);
		limb2.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle2.addChild(limb2);
		limb3 = new ModelRenderer(this, 36, 8);
		limb3.setRotationPoint(0,0,0);
		limb3.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle3.addChild(limb3);
		limb4 = new ModelRenderer(this, 36, 8);
		limb4.setRotationPoint(0,0,0);
		limb4.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle4.addChild(limb4);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par6Entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, par6Entity);
		this.limb1.offsetX = this.limb1.offsetY = this.limb1.offsetZ = 0.0F;
        float f6 = 0.01F * (float)(par6Entity.getEntityId() % 10);
        this.limb1.rotateAngleX = MathHelper.sin((float)par6Entity.ticksExisted * f6) * 4.5F * (float)Math.PI / 180.0F;
        this.limb1.rotateAngleY = 0.0F;
        this.limb1.rotateAngleZ = MathHelper.cos((float)par6Entity.ticksExisted * f6) * 2.5F * (float)Math.PI / 180.0F;
        float f7 = 0.02F * (float)(par6Entity.getEntityId() % 10);
        this.limb2.offsetX = this.limb2.offsetY = this.limb2.offsetZ = 0.0F;
        this.limb2.rotateAngleX = MathHelper.sin((float)par6Entity.ticksExisted * f7) * 4.5F * (float)Math.PI / 180.0F;
        this.limb2.rotateAngleY = 0.0F;
        this.limb2.rotateAngleZ = MathHelper.cos((float)par6Entity.ticksExisted * f7) * 2.5F * (float)Math.PI / 180.0F;
        float f8 = 0.03F * (float)(par6Entity.getEntityId() % 10);
        this.limb3.offsetX = this.limb3.offsetY = this.limb3.offsetZ = 0.0F;
        this.limb3.rotateAngleX = MathHelper.sin((float)par6Entity.ticksExisted * f8) * 4.5F * (float)Math.PI / 180.0F;
        this.limb3.rotateAngleY = 0.0F;
        this.limb3.rotateAngleZ = MathHelper.cos((float)par6Entity.ticksExisted * f8) * 2.5F * (float)Math.PI / 180.0F;
        float f9 = 0.04F * (float)(par6Entity.getEntityId() % 10);
        this.limb4.offsetX = this.limb4.offsetY = this.limb4.offsetZ = 0.0F;
        this.limb4.rotateAngleX = MathHelper.sin((float)par6Entity.ticksExisted * f9) * 4.5F * (float)Math.PI / 180.0F;
        this.limb4.rotateAngleY = 0.0F;
        this.limb4.rotateAngleZ = MathHelper.cos((float)par6Entity.ticksExisted * f9) * 2.5F * (float)Math.PI / 180.0F;
	}
	
	public void renderTentacles(float par1){
		tentacle1.render(par1);
		tentacle2.render(par1);
		tentacle3.render(par1);
		tentacle4.render(par1);
	}
}
