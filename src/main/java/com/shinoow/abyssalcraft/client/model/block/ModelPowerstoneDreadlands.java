package com.shinoow.abyssalcraft.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPowerstoneDreadlands extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
  
  public ModelPowerstoneDreadlands()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 8, 8, 8);
      Shape1.setRotationPoint(-4F, 16F, -4F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 32, 12);
      Shape2.addBox(0F, 0F, 0F, 4, 4, 4);
      Shape2.setRotationPoint(-1F, 15F, 4F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0.4833219F, 1.041001F, 0.4089647F);
      Shape3 = new ModelRenderer(this, 0, 16);
      Shape3.addBox(0F, 0F, 0F, 5, 5, 5);
      Shape3.setRotationPoint(-4F, 14F, 2F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, -0.6320364F, 0.7807508F, 0.7435722F);
      Shape4 = new ModelRenderer(this, 0, 16);
      Shape4.addBox(0F, 0F, 0F, 5, 5, 5);
      Shape4.setRotationPoint(-1F, 15F, -3F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0.4833219F, 0.8551081F, 0.1115358F);
      Shape5 = new ModelRenderer(this, 32, 12);
      Shape5.addBox(0F, 0F, 0F, 4, 4, 4);
      Shape5.setRotationPoint(-5F, 16F, -7F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0.4461433F, -0.2974289F, -0.0743572F);
      Shape6 = new ModelRenderer(this, 32, 0);
      Shape6.addBox(0F, 0F, 0F, 6, 6, 6);
      Shape6.setRotationPoint(-2F, 17F, -5F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0.8551081F, -0.1115358F, 0.6123111F);
      Shape7 = new ModelRenderer(this, 20, 16);
      Shape7.addBox(0F, 0F, 0F, 3, 3, 3);
      Shape7.setRotationPoint(2F, 18F, 0F);
      Shape7.setTextureSize(64, 32);
      Shape7.mirror = true;
      setRotation(Shape7, 0.3717861F, 0.5205006F, 0.5948578F);
      Shape8 = new ModelRenderer(this, 20, 16);
      Shape8.addBox(0F, 0F, 0F, 3, 3, 3);
      Shape8.setRotationPoint(-1F, 18F, -5F);
      Shape8.setTextureSize(64, 32);
      Shape8.mirror = true;
      setRotation(Shape8, 0.4833219F, 0.7807508F, 0.8353827F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
    Shape8.render(f5);
  }
  
  public void renderModel(float f5)
  {
	  Shape1.render(f5);
	  Shape2.render(f5);
	  Shape3.render(f5);
	  Shape4.render(f5);
	  Shape5.render(f5);
	  Shape6.render(f5);
	  Shape7.render(f5);
	  Shape8.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
  
  }

}
