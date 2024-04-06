package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * DreadTentacles - shinoow
 * Created using Tabula 7.0.0
 */
public class ModelDreadTentacles extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer tentacle_1;
    public ModelRenderer tentacle_1_1;
    public ModelRenderer tentacle_2;
    public ModelRenderer tentacle_2_1;
    public ModelRenderer tentacle_3;
    public ModelRenderer tentacle_3_1;

    public ModelDreadTentacles() {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.tentacle_1_1 = new ModelRenderer(this, 0, 9);
        this.tentacle_1_1.setRotationPoint(0.0F, 0.0F, 5.0F);
        this.tentacle_1_1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, 0.0F);
        this.tentacle_3 = new ModelRenderer(this, 6, 2);
        this.tentacle_3.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.tentacle_3.addBox(1.0F, 1.0F, 0.0F, 1, 1, 4, 0.0F);
        this.tentacle_2_1 = new ModelRenderer(this, 4, 5);
        this.tentacle_2_1.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.tentacle_2_1.addBox(0.5F, 0.5F, 0.0F, 2, 2, 3, 0.0F);
        this.tentacle_1 = new ModelRenderer(this, 0, 8);
        this.tentacle_1.setRotationPoint(-1.0F, -1.0F, 1.0F);
        this.tentacle_1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 5, 0.0F);
        this.tentacle_2 = new ModelRenderer(this, 4, 4);
        this.tentacle_2.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.tentacle_2.addBox(0.5F, 0.5F, 0.0F, 2, 2, 4, 0.0F);
        this.tentacle_3_1 = new ModelRenderer(this, 6, 2);
        this.tentacle_3_1.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.tentacle_3_1.addBox(1.0F, 1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.base.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.tentacle_1.addChild(this.tentacle_1_1);
        this.tentacle_2_1.addChild(this.tentacle_3);
        this.tentacle_2.addChild(this.tentacle_2_1);
        this.base.addChild(this.tentacle_1);
        this.tentacle_1_1.addChild(this.tentacle_2);
        this.tentacle_3.addChild(this.tentacle_3_1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.base.render(f5);
        
        float animation = MathHelper.sin((f * 0.4F + 2) * 1.5F) * 0.3F * f1 * 0.3F;
		float flap = MathHelper.sin(entity.ticksExisted * 0.2F) * 0.3F;
		float flap2 = MathHelper.cos(entity.ticksExisted * 0.2F) * 0.4F;

		tentacle_1.rotateAngleY = flap *0.1F + animation * 0.4f;// + 0.20943951023931953F
		tentacle_2.rotateAngleY = tentacle_1_1.rotateAngleY * 1.5F;
		tentacle_3.rotateAngleY = tentacle_2_1.rotateAngleY * 1.75F;
		
		tentacle_1.rotateAngleX = -0.13962634015954636F - flap2 * 0.1F - animation * 0.4f;
		tentacle_2.rotateAngleX = -0.41887902047863906F - flap2 * 0.75F;
		tentacle_3.rotateAngleX = -0.13962634015954636F - flap2 * 1F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
