/**AbyssalCraft
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
package com.shinoow.abyssalcraft.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStaff extends ModelBase
{

    ModelRenderer Staff1;
    ModelRenderer Staff2;
    ModelRenderer Staff3;
    ModelRenderer Staff4;
    ModelRenderer Staff5;
    ModelRenderer Staff6;
    ModelRenderer Cube;
  
  public ModelStaff()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Staff1 = new ModelRenderer(this, 0, 0);
      Staff1.addBox(0F, -2F, 0F, 1, 18, 1);
      Staff1.setRotationPoint(0F, 0F, 0F);
      Staff1.setTextureSize(64, 32);
      Staff1.mirror = true;
      setRotation(Staff1, 0F, 0F, 0F);
      Staff2 = new ModelRenderer(this, 8, 0);
      Staff2.addBox(0.9F, -5.2F, 0F, 1, 4, 1);
      Staff2.setRotationPoint(0F, 0F, 0F);
      Staff2.setTextureSize(64, 32);
      Staff2.mirror = true;
      setRotation(Staff2, 0F, 0F, -0.5235988F);
      Staff3 = new ModelRenderer(this, 12, 0);
      Staff3.addBox(-1.8F, -6.9F, 0F, 1, 2, 1);
      Staff3.setRotationPoint(0F, 0F, 0F);
      Staff3.setTextureSize(64, 32);
      Staff3.mirror = true;
      setRotation(Staff3, 0F, 0F, 0F);
      Staff4 = new ModelRenderer(this, 12, 3);
      Staff4.addBox(-5.7F, -8F, 0F, 1, 4, 1);
      Staff4.setRotationPoint(0F, 0F, 0F);
      Staff4.setTextureSize(64, 32);
      Staff4.mirror = true;
      setRotation(Staff4, 0F, 0F, 0.6981317F);
      Staff5 = new ModelRenderer(this, 8, 5);
      Staff5.addBox(6.9F, -5.7F, 0F, 1, 4, 1);
      Staff5.setRotationPoint(0F, 0F, 0F);
      Staff5.setTextureSize(64, 32);
      Staff5.mirror = true;
      setRotation(Staff5, 0F, 0F, -0.8726646F);
      Staff6 = new ModelRenderer(this, 12, 8);
      Staff6.addBox(0F, 0F, 0F, 1, 1, 1);
      Staff6.setRotationPoint(0.5F, 15F, 0.7F);
      Staff6.setTextureSize(64, 32);
      Staff6.mirror = true;
      setRotation(Staff6, -0.8922867F, 0.5948578F, 0.2230717F);
      Cube = new ModelRenderer(this, 8, 15);
      Cube.addBox(-2.8F, -6F, 0F, 2, 2, 2);
      Cube.setRotationPoint(0F, 0F, 0F);
      Cube.setTextureSize(64, 32);
      Cube.mirror = true;
      setRotation(Cube, 0F, 0.4833219F, 0.5205006F);
  }
  
  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
  {
	super.render(par1Entity, par2, par3, par4, par5, par6, par7);
	setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
    Staff1.render(par7);
    Staff2.render(par7);
    Staff3.render(par7);
    Staff4.render(par7);
    Staff5.render(par7);
    Staff6.render(par7);
    Cube.render(par7);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
  {
    super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
  }

}
