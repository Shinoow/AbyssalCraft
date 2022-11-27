package com.shinoow.abyssalcraft.client.model.entity;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.shinoow.abyssalcraft.client.render.entity.ModelShoggy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLesserShoggoth extends ModelBase {
	public final ModelRenderer bodyBase;
	public final ModelRenderer lHindTentacle01a;
	public final ModelRenderer lHindTentacle01b;
	public final ModelRenderer lHindTentacle01c;
	public final ModelRenderer lHindTentacle01d;
	public final ModelRenderer lHindTentacle01e;
	public final ModelRenderer eye25;
	public final ModelRenderer lBodyTentacle01a;
	public final ModelRenderer lBodyTentacle01b;
	public final ModelRenderer lBodyTentacle01c;
	public final ModelRenderer eye22;
	public final ModelRenderer bodyMid;
	public final ModelRenderer lBodyTentacle04a;
	public final ModelRenderer lBodyTentacle04b;
	public final ModelRenderer lBackTentacle01a;
	public final ModelRenderer lBackTentacle01b;
	public final ModelRenderer lBackTentacle01c;
	public final ModelRenderer eye21;
	public final ModelRenderer rBodyTentacle03a;
	public final ModelRenderer rBodyTentacle03b;
	public final ModelRenderer lArm01a;
	public final ModelRenderer lArm01b;
	public final ModelRenderer lArm01c;
	public final ModelRenderer lArm01d;
	public final ModelRenderer eye14;
	public final ModelRenderer eye15;
	public final ModelRenderer eye13;
	public final ModelRenderer rBackTentacle01a;
	public final ModelRenderer rBackTentacle01b;
	public final ModelRenderer rBackTentacle01c;
	public final ModelRenderer rArm01a;
	public final ModelRenderer rArm01b;
	public final ModelRenderer rArm01c;
	public final ModelRenderer rArm01d;
	public final ModelRenderer eye17;
	public final ModelRenderer eye16;
	public final ModelRenderer eye18;
	public final ModelRenderer eye20;
	public final ModelRenderer rBackTentacle02a;
	public final ModelRenderer rBackTentacle02b;
	public final ModelRenderer rBackTentacle02c;
	public final ModelRenderer eye19;
	public final ModelRenderer rBodyTentacle04a;
	public final ModelRenderer rBodyTentacle04b;
	public final ModelRenderer lBodyTentacle02a;
	public final ModelRenderer lBodyTentacle02b;
	public final ModelRenderer lBodyTentacle02c;
	public final ModelRenderer lBackTentacle02a;
	public final ModelRenderer lBackTentacle02b;
	public final ModelRenderer lBackTentacle02c;
	public final ModelRenderer lBodyTentacle03a;
	public final ModelRenderer lBodyTentacle03b;
	public final ModelRenderer lBackTentacle02a_1;
	public final ModelRenderer lBackTentacle02b_1;
	public final ModelRenderer lBackTentacle02c_1;
	public final ModelRenderer rBodyTentacle02a;
	public final ModelRenderer rBodyTentacle02b;
	public final ModelRenderer rBodyTentacle02c;
	public final ModelRenderer rBackTentacle02a_1;
	public final ModelRenderer rBackTentacle02b_1;
	public final ModelRenderer rBackTentacle02c_1;
	public final ModelRenderer rBodyTentacle01a;
	public final ModelRenderer rBodyTentacle01b;
	public final ModelRenderer rBodyTentacle01c;
	public final ModelRenderer rHindTentacle03a;
	public final ModelRenderer rHindTentacle03b;
	public final ModelRenderer rHindTentacle03c;
	public final ModelRenderer eye32;
	public final ModelRenderer rHindTentacle03;
	public final ModelRenderer rHindTentacle01a;
	public final ModelRenderer rHindTentacle01b;
	public final ModelRenderer rHindTentacle01c;
	public final ModelRenderer eye29;
	public final ModelRenderer rHindTentacle01d;
	public final ModelRenderer rHindTentacle01e;
	public final ModelRenderer eye30;
	public final ModelRenderer lHindTentacle03a;
	public final ModelRenderer lHindTentacle03b;
	public final ModelRenderer lHindTentacle03c;
	public final ModelRenderer lHindTentacle03;
	public final ModelRenderer lHindTentacle02a;
	public final ModelRenderer lHindTentacle02b;
	public final ModelRenderer eye23;
	public final ModelRenderer lHindTentacle02c;
	public final ModelRenderer lHindTentacle02d;
	public final ModelRenderer lHindTentacle02e;
	public final ModelRenderer eye24;
	public final ModelRenderer rHindTentacle02a;
	public final ModelRenderer rHindTentacle02b;
	public final ModelRenderer rHindTentacle02c;
	public final ModelRenderer eye31;
	public final ModelRenderer rHindTentacle02d;
	public final ModelRenderer rHindTentacle02e;
	public final ModelRenderer tail01a;
	public final ModelRenderer eye28;
	public final ModelRenderer tail01b;
	public final ModelRenderer tail01c;
	public final ModelRenderer tail01d;
	public final ModelRenderer tail01e;
	public final ModelRenderer eye26;
	public final ModelRenderer eye27;
	public final ModelRenderer headJoint;
	public final ModelRenderer bodyUpper;
	public final ModelRenderer mouth01Upper;
	public final ModelRenderer mouth01Lower;
	public final ModelRenderer mouth01Teeth03;
	public final ModelRenderer mouth01Teeth02;
	public final ModelRenderer eye05;
	public final ModelRenderer mouth01Tooth05;
	public final ModelRenderer mouth01Tooth03;
	public final ModelRenderer mouth01Snout;
	public final ModelRenderer mouth01Tooth02;
	public final ModelRenderer mouth01Tooth04;
	public final ModelRenderer mouth01Tooth01;
	public final ModelRenderer eye04_1;
	public final ModelRenderer mouth01Tooth06;
	public final ModelRenderer mouth01Teeth01;
	public final ModelRenderer eye09;
	public final ModelRenderer eye01;
	public final ModelRenderer eye11;
	public final ModelRenderer eye03;
	public final ModelRenderer eye02;
	public final ModelRenderer mouth02Upper;
	public final ModelRenderer mouth02Snout;
	public final ModelRenderer mouth02Lower;
	public final ModelRenderer mouth02Teeth02;
	public final ModelRenderer mouth02Teeth03;
	public final ModelRenderer mouth02Tooth02;
	public final ModelRenderer mouth02Tooth01;
	public final ModelRenderer eye06;
	public final ModelRenderer mouth02Tooth03;
	public final ModelRenderer mouth02Teeth01;
	public final ModelRenderer mouth02Tooth04;
	public final ModelRenderer mouth02Tooth04_1;
	public final ModelRenderer eye12;
	public final ModelRenderer eye10;
	public final ModelRenderer eye04;
	public final ModelRenderer mouth03Upper;
	public final ModelRenderer mouth03Snout;
	public final ModelRenderer mouth03Teeth01;
	public final ModelRenderer mouth03Tooth05;
	public final ModelRenderer mouth03Tooth02;
	public final ModelRenderer mouth03Lower;
	public final ModelRenderer mouth02Teeth03_1;
	public final ModelRenderer mouth03Teeth02;
	public final ModelRenderer eye08;
	public final ModelRenderer mouth03Tooth01;
	public final ModelRenderer mouth03Tooth04;
	public final ModelRenderer eye07;
	public final ModelRenderer mouth03Tooth03;

	public ModelLesserShoggoth() {
		textureWidth = 128;
		textureHeight = 128;

		bodyBase = new ModelRenderer(this);
		bodyBase.setRotationPoint(0.0F, 12.0F, 0.0F);
		bodyBase.cubeList.add(new ModelBox(bodyBase, 0, 64, -7.0F, 0.0F, -4.0F, 14, 12, 16, 0.0F));

		lHindTentacle01a = new ModelRenderer(this);
		lHindTentacle01a.setRotationPoint(4.1F, 6.7F, 4.9F);
		bodyBase.addChild(lHindTentacle01a);
		setRotationAngle(lHindTentacle01a, -0.1396F, 0.2094F, 0.0F);
		lHindTentacle01a.cubeList.add(new ModelBox(lHindTentacle01a, 0, 96, -3.0F, -3.0F, 0.0F, 6, 6, 10, 0.0F));

		lHindTentacle01b = new ModelRenderer(this);
		lHindTentacle01b.setRotationPoint(0.0F, 0.4F, 9.5F);
		lHindTentacle01a.addChild(lHindTentacle01b);
		setRotationAngle(lHindTentacle01b, 0.0F, -0.1396F, 0.0F);
		lHindTentacle01b.cubeList.add(new ModelBox(lHindTentacle01b, 34, 96, -2.5F, -2.5F, 0.0F, 5, 5, 8, 0.0F));

		lHindTentacle01c = new ModelRenderer(this);
		lHindTentacle01c.setRotationPoint(0.0F, 0.4F, 7.5F);
		lHindTentacle01b.addChild(lHindTentacle01c);
		setRotationAngle(lHindTentacle01c, 0.1396F, -0.0698F, 0.0F);
		lHindTentacle01c.cubeList.add(new ModelBox(lHindTentacle01c, 63, 96, -2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F));

		lHindTentacle01d = new ModelRenderer(this);
		lHindTentacle01d.setRotationPoint(-4.1F, -18.7F, -6.8F);
		lHindTentacle01c.addChild(lHindTentacle01d);
		setRotationAngle(lHindTentacle01d, 0.0F, -0.0524F, 0.0F);
		lHindTentacle01d.cubeList.add(new ModelBox(lHindTentacle01d, 92, 96, -1.5F, -1.5F, 0.0F, 3, 3, 9, 0.0F));

		lHindTentacle01e = new ModelRenderer(this);
		lHindTentacle01e.setRotationPoint(0.0F, 0.4F, 8.9F);
		lHindTentacle01d.addChild(lHindTentacle01e);
		setRotationAngle(lHindTentacle01e, 0.0F, 0.0698F, 0.0F);
		lHindTentacle01e.cubeList.add(new ModelBox(lHindTentacle01e, 0, 113, -1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F));

		eye25 = new ModelRenderer(this);
		eye25.setRotationPoint(0.8F, -2.8F, 3.8F);
		lHindTentacle01b.addChild(eye25);
		eye25.cubeList.add(new ModelBox(eye25, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		lBodyTentacle01a = new ModelRenderer(this);
		lBodyTentacle01a.setRotationPoint(4.8F, 4.1F, 0.7F);
		bodyBase.addChild(lBodyTentacle01a);
		setRotationAngle(lBodyTentacle01a, 0.3665F, -0.7854F, 0.0F);
		lBodyTentacle01a.cubeList.add(new ModelBox(lBodyTentacle01a, 63, 96, -2.0F, -2.0F, -8.0F, 4, 4, 8, 0.0F));

		lBodyTentacle01b = new ModelRenderer(this);
		lBodyTentacle01b.setRotationPoint(0.0F, -12.0F, -7.0F);
		lBodyTentacle01a.addChild(lBodyTentacle01b);
		setRotationAngle(lBodyTentacle01b, 0.0524F, 0.3665F, 0.0F);
		lBodyTentacle01b.cubeList.add(new ModelBox(lBodyTentacle01b, 93, 97, -1.5F, -1.5F, -8.0F, 3, 3, 8, 0.0F));

		lBodyTentacle01c = new ModelRenderer(this);
		lBodyTentacle01c.setRotationPoint(0.0F, 0.0F, -7.2F);
		lBodyTentacle01b.addChild(lBodyTentacle01c);
		setRotationAngle(lBodyTentacle01c, 0.4538F, -0.0873F, 0.0F);
		lBodyTentacle01c.cubeList.add(new ModelBox(lBodyTentacle01c, 1, 114, -1.0F, -1.0F, -9.0F, 2, 2, 9, 0.0F));

		eye22 = new ModelRenderer(this);
		eye22.setRotationPoint(1.6F, -1.7F, -3.4F);
		lBodyTentacle01a.addChild(eye22);
		eye22.cubeList.add(new ModelBox(eye22, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		bodyMid = new ModelRenderer(this);
		bodyMid.setRotationPoint(0.0F, 3.6F, 4.4F);
		bodyBase.addChild(bodyMid);
		setRotationAngle(bodyMid, 0.7854F, 0.0F, 0.0F);
		bodyMid.cubeList.add(new ModelBox(bodyMid, 0, 31, -6.5F, -16.0F, -7.0F, 13, 18, 14, 0.0F));

		lBodyTentacle04a = new ModelRenderer(this);
		lBodyTentacle04a.setRotationPoint(1.5F, -18.0F, -6.7F);
		bodyMid.addChild(lBodyTentacle04a);
		setRotationAngle(lBodyTentacle04a, 0.2793F, -0.1047F, 0.0F);
		lBodyTentacle04a.cubeList.add(new ModelBox(lBodyTentacle04a, 6, 119, -1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F));

		lBodyTentacle04b = new ModelRenderer(this);
		lBodyTentacle04b.setRotationPoint(0.0F, 0.0F, -3.8F);
		lBodyTentacle04a.addChild(lBodyTentacle04b);
		setRotationAngle(lBodyTentacle04b, 0.3142F, 0.0F, 0.0F);
		lBodyTentacle04b.cubeList.add(new ModelBox(lBodyTentacle04b, 17, 115, -0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F));

		lBackTentacle01a = new ModelRenderer(this);
		lBackTentacle01a.setRotationPoint(4.7F, -20.9F, 3.4F);
		bodyMid.addChild(lBackTentacle01a);
		setRotationAngle(lBackTentacle01a, -0.5236F, 0.5236F, -0.2793F);
		lBackTentacle01a.cubeList.add(new ModelBox(lBackTentacle01a, 94, 98, -1.5F, -1.5F, 0.0F, 3, 3, 7, 0.0F));

		lBackTentacle01b = new ModelRenderer(this);
		lBackTentacle01b.setRotationPoint(0.0F, 0.0F, 6.3F);
		lBackTentacle01a.addChild(lBackTentacle01b);
		setRotationAngle(lBackTentacle01b, -0.4189F, -0.2269F, 0.0F);
		lBackTentacle01b.cubeList.add(new ModelBox(lBackTentacle01b, 3, 116, -1.0F, -1.0F, 0.0F, 2, 2, 7, 0.0F));

		lBackTentacle01c = new ModelRenderer(this);
		lBackTentacle01c.setRotationPoint(-4.7F, 5.3F, -1.2F);
		lBackTentacle01b.addChild(lBackTentacle01c);
		setRotationAngle(lBackTentacle01c, -0.1396F, -0.2269F, 0.0F);
		lBackTentacle01c.cubeList.add(new ModelBox(lBackTentacle01c, 16, 114, -0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F));

		eye21 = new ModelRenderer(this);
		eye21.setRotationPoint(6.7F, -22.7F, 1.7F);
		bodyMid.addChild(eye21);
		eye21.cubeList.add(new ModelBox(eye21, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		rBodyTentacle03a = new ModelRenderer(this);
		rBodyTentacle03a.setRotationPoint(-4.8F, -19.8F, -6.6F);
		bodyMid.addChild(rBodyTentacle03a);
		setRotationAngle(rBodyTentacle03a, 0.1396F, 0.4363F, 0.0F);
		rBodyTentacle03a.cubeList.add(new ModelBox(rBodyTentacle03a, 6, 119, -1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F));

		rBodyTentacle03b = new ModelRenderer(this);
		rBodyTentacle03b.setRotationPoint(0.0F, 0.0F, -3.8F);
		rBodyTentacle03a.addChild(rBodyTentacle03b);
		setRotationAngle(rBodyTentacle03b, 0.192F, -0.2618F, 0.0F);
		rBodyTentacle03b.cubeList.add(new ModelBox(rBodyTentacle03b, 17, 114, -0.5F, -0.5F, -5.0F, 1, 1, 5, 0.0F));

		lArm01a = new ModelRenderer(this);
		lArm01a.setRotationPoint(4.0F, -23.9F, -2.7F);
		bodyMid.addChild(lArm01a);
		setRotationAngle(lArm01a, -0.2443F, -0.3665F, 0.0F);
		lArm01a.cubeList.add(new ModelBox(lArm01a, 34, 96, -2.5F, -2.5F, -8.1F, 5, 5, 8, 0.0F));

		lArm01b = new ModelRenderer(this);
		lArm01b.setRotationPoint(0.0F, 0.0F, -7.8F);
		lArm01a.addChild(lArm01b);
		setRotationAngle(lArm01b, 0.0698F, 0.0698F, 0.0F);
		lArm01b.cubeList.add(new ModelBox(lArm01b, 63, 96, -2.0F, -2.0F, -8.0F, 4, 4, 8, 0.0F));

		lArm01c = new ModelRenderer(this);
		lArm01c.setRotationPoint(0.0F, 0.0F, -7.0F);
		lArm01b.addChild(lArm01c);
		setRotationAngle(lArm01c, 0.1745F, 0.1396F, 0.0F);
		lArm01c.cubeList.add(new ModelBox(lArm01c, 93, 97, -1.5F, -1.5F, -8.0F, 3, 3, 8, 0.0F));

		lArm01d = new ModelRenderer(this);
		lArm01d.setRotationPoint(-4.0F, 8.3F, -1.1F);
		lArm01c.addChild(lArm01d);
		setRotationAngle(lArm01d, 0.1396F, 0.1396F, 0.0F);
		lArm01d.cubeList.add(new ModelBox(lArm01d, 1, 114, -1.0F, -1.0F, -9.0F, 2, 2, 9, 0.0F));

		eye14 = new ModelRenderer(this);
		eye14.setRotationPoint(-1.0F, -1.2F, -3.8F);
		lArm01c.addChild(eye14);
		eye14.cubeList.add(new ModelBox(eye14, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		eye15 = new ModelRenderer(this);
		eye15.setRotationPoint(2.6F, 1.1F, -7.6F);
		lArm01a.addChild(eye15);
		eye15.cubeList.add(new ModelBox(eye15, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		eye13 = new ModelRenderer(this);
		eye13.setRotationPoint(2.6F, -2.0F, -3.8F);
		lArm01a.addChild(eye13);
		eye13.cubeList.add(new ModelBox(eye13, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		rBackTentacle01a = new ModelRenderer(this);
		rBackTentacle01a.setRotationPoint(-4.7F, -20.9F, 3.4F);
		bodyMid.addChild(rBackTentacle01a);
		setRotationAngle(rBackTentacle01a, -0.5236F, -0.5236F, 0.2793F);
		rBackTentacle01a.cubeList.add(new ModelBox(rBackTentacle01a, 94, 98, -1.5F, -1.5F, 0.0F, 3, 3, 7, 0.0F));

		rBackTentacle01b = new ModelRenderer(this);
		rBackTentacle01b.setRotationPoint(0.0F, 0.0F, 6.3F);
		rBackTentacle01a.addChild(rBackTentacle01b);
		setRotationAngle(rBackTentacle01b, -0.4189F, 0.2269F, 0.0F);
		rBackTentacle01b.cubeList.add(new ModelBox(rBackTentacle01b, 3, 116, -1.0F, -1.0F, 0.0F, 2, 2, 7, 0.0F));

		rBackTentacle01c = new ModelRenderer(this);
		rBackTentacle01c.setRotationPoint(0.0F, 0.0F, 6.6F);
		rBackTentacle01b.addChild(rBackTentacle01c);
		setRotationAngle(rBackTentacle01c, -0.1396F, 0.2269F, 0.0F);
		rBackTentacle01c.cubeList.add(new ModelBox(rBackTentacle01c, 16, 114, -0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F));

		rArm01a = new ModelRenderer(this);
		rArm01a.setRotationPoint(-4.0F, -23.9F, -2.7F);
		bodyMid.addChild(rArm01a);
		setRotationAngle(rArm01a, -0.2443F, 0.3665F, 0.0F);
		rArm01a.cubeList.add(new ModelBox(rArm01a, 34, 96, -2.5F, -2.5F, -8.1F, 5, 5, 8, 0.0F));

		rArm01b = new ModelRenderer(this);
		rArm01b.setRotationPoint(0.0F, 0.0F, -7.8F);
		rArm01a.addChild(rArm01b);
		setRotationAngle(rArm01b, 0.0698F, -0.0698F, 0.0F);
		rArm01b.cubeList.add(new ModelBox(rArm01b, 63, 96, -2.0F, -2.0F, -8.0F, 4, 4, 8, 0.0F));

		rArm01c = new ModelRenderer(this);
		rArm01c.setRotationPoint(4.0F, 8.3F, -8.7F);
		rArm01b.addChild(rArm01c);
		setRotationAngle(rArm01c, 0.1745F, -0.1396F, 0.0F);
		rArm01c.cubeList.add(new ModelBox(rArm01c, 93, 97, -1.5F, -1.5F, -8.0F, 3, 3, 8, 0.0F));

		rArm01d = new ModelRenderer(this);
		rArm01d.setRotationPoint(0.0F, 0.0F, -7.2F);
		rArm01c.addChild(rArm01d);
		setRotationAngle(rArm01d, 0.1396F, -0.1396F, 0.0F);
		rArm01d.cubeList.add(new ModelBox(rArm01d, 1, 114, -1.0F, -1.0F, -9.0F, 2, 2, 9, 0.0F));

		eye17 = new ModelRenderer(this);
		eye17.setRotationPoint(6.0F, 9.4F, -9.9F);
		rArm01b.addChild(eye17);
		eye17.cubeList.add(new ModelBox(eye17, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		eye16 = new ModelRenderer(this);
		eye16.setRotationPoint(-1.6F, -1.9F, -5.1F);
		rArm01b.addChild(eye16);
		eye16.cubeList.add(new ModelBox(eye16, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		eye18 = new ModelRenderer(this);
		eye18.setRotationPoint(-2.6F, 1.7F, -3.4F);
		rArm01a.addChild(eye18);
		eye18.cubeList.add(new ModelBox(eye18, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		eye20 = new ModelRenderer(this);
		eye20.setRotationPoint(-6.7F, -18.6F, 0.7F);
		bodyMid.addChild(eye20);
		eye20.cubeList.add(new ModelBox(eye20, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		rBackTentacle02a = new ModelRenderer(this);
		rBackTentacle02a.setRotationPoint(-5.4F, -12.7F, 5.8F);
		bodyMid.addChild(rBackTentacle02a);
		setRotationAngle(rBackTentacle02a, -0.733F, -0.3665F, 0.0F);
		rBackTentacle02a.cubeList.add(new ModelBox(rBackTentacle02a, 94, 98, -1.5F, -1.5F, 0.0F, 3, 3, 7, 0.0F));

		rBackTentacle02b = new ModelRenderer(this);
		rBackTentacle02b.setRotationPoint(0.0F, 0.0F, 6.3F);
		rBackTentacle02a.addChild(rBackTentacle02b);
		setRotationAngle(rBackTentacle02b, -0.4189F, 0.0873F, 0.0F);
		rBackTentacle02b.cubeList.add(new ModelBox(rBackTentacle02b, 4, 117, -1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F));

		rBackTentacle02c = new ModelRenderer(this);
		rBackTentacle02c.setRotationPoint(0.0F, 0.0F, 5.8F);
		rBackTentacle02b.addChild(rBackTentacle02c);
		setRotationAngle(rBackTentacle02c, -0.1396F, -0.1396F, 0.0F);
		rBackTentacle02c.cubeList.add(new ModelBox(rBackTentacle02c, 16, 114, -0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F));

		eye19 = new ModelRenderer(this);
		eye19.setRotationPoint(-6.7F, -25.3F, 3.9F);
		bodyMid.addChild(eye19);
		eye19.cubeList.add(new ModelBox(eye19, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		rBodyTentacle04a = new ModelRenderer(this);
		rBodyTentacle04a.setRotationPoint(-1.5F, -18.0F, -6.7F);
		bodyMid.addChild(rBodyTentacle04a);
		setRotationAngle(rBodyTentacle04a, 0.2793F, 0.1047F, 0.0F);
		rBodyTentacle04a.cubeList.add(new ModelBox(rBodyTentacle04a, 6, 119, -1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F));

		rBodyTentacle04b = new ModelRenderer(this);
		rBodyTentacle04b.setRotationPoint(0.0F, -3.6F, -8.2F);
		rBodyTentacle04a.addChild(rBodyTentacle04b);
		setRotationAngle(rBodyTentacle04b, 0.3142F, 0.0F, 0.0F);
		rBodyTentacle04b.cubeList.add(new ModelBox(rBodyTentacle04b, 17, 115, -0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F));

		lBodyTentacle02a = new ModelRenderer(this);
		lBodyTentacle02a.setRotationPoint(3.5F, -10.4F, -6.8F);
		bodyMid.addChild(lBodyTentacle02a);
		setRotationAngle(lBodyTentacle02a, 0.0524F, -0.1047F, 0.0F);
		lBodyTentacle02a.cubeList.add(new ModelBox(lBodyTentacle02a, 96, 100, -1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F));

		lBodyTentacle02b = new ModelRenderer(this);
		lBodyTentacle02b.setRotationPoint(0.0F, 0.3F, -4.7F);
		lBodyTentacle02a.addChild(lBodyTentacle02b);
		setRotationAngle(lBodyTentacle02b, 0.192F, 0.0873F, 0.0F);
		lBodyTentacle02b.cubeList.add(new ModelBox(lBodyTentacle02b, 5, 118, -1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F));

		lBodyTentacle02c = new ModelRenderer(this);
		lBodyTentacle02c.setRotationPoint(0.0F, 0.0F, -4.8F);
		lBodyTentacle02b.addChild(lBodyTentacle02c);
		setRotationAngle(lBodyTentacle02c, 0.1047F, 0.0F, 0.0F);
		lBodyTentacle02c.cubeList.add(new ModelBox(lBodyTentacle02c, 17, 115, -0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F));

		lBackTentacle02a = new ModelRenderer(this);
		lBackTentacle02a.setRotationPoint(3.5F, -14.5F, 5.9F);
		bodyMid.addChild(lBackTentacle02a);
		setRotationAngle(lBackTentacle02a, -0.5236F, 0.1047F, 0.0F);
		lBackTentacle02a.cubeList.add(new ModelBox(lBackTentacle02a, 95, 99, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F));

		lBackTentacle02b = new ModelRenderer(this);
		lBackTentacle02b.setRotationPoint(0.0F, -15.6F, 0.8F);
		lBackTentacle02a.addChild(lBackTentacle02b);
		setRotationAngle(lBackTentacle02b, -0.2443F, -0.0873F, 0.0F);
		lBackTentacle02b.cubeList.add(new ModelBox(lBackTentacle02b, 5, 118, -1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F));

		lBackTentacle02c = new ModelRenderer(this);
		lBackTentacle02c.setRotationPoint(-3.5F, 14.5F, -1.2F);
		lBackTentacle02b.addChild(lBackTentacle02c);
		setRotationAngle(lBackTentacle02c, 0.1571F, 0.1396F, 0.0F);
		lBackTentacle02c.cubeList.add(new ModelBox(lBackTentacle02c, 17, 115, -0.5F, -0.5F, 0.0F, 1, 1, 4, 0.0F));

		lBodyTentacle03a = new ModelRenderer(this);
		lBodyTentacle03a.setRotationPoint(4.8F, -7.8F, -6.6F);
		bodyMid.addChild(lBodyTentacle03a);
		setRotationAngle(lBodyTentacle03a, 0.1396F, -0.4363F, 0.0F);
		lBodyTentacle03a.cubeList.add(new ModelBox(lBodyTentacle03a, 6, 119, -1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F));

		lBodyTentacle03b = new ModelRenderer(this);
		lBodyTentacle03b.setRotationPoint(0.0F, 0.0F, -3.8F);
		lBodyTentacle03a.addChild(lBodyTentacle03b);
		setRotationAngle(lBodyTentacle03b, 0.192F, 0.2618F, 0.0F);
		lBodyTentacle03b.cubeList.add(new ModelBox(lBodyTentacle03b, 17, 114, -0.5F, -0.5F, -5.0F, 1, 1, 5, 0.0F));

		lBackTentacle02a_1 = new ModelRenderer(this);
		lBackTentacle02a_1.setRotationPoint(5.4F, -0.7F, 5.8F);
		bodyMid.addChild(lBackTentacle02a_1);
		setRotationAngle(lBackTentacle02a_1, -0.733F, 0.3665F, 0.0F);
		lBackTentacle02a_1.cubeList.add(new ModelBox(lBackTentacle02a_1, 94, 98, -1.5F, -1.5F, 0.0F, 3, 3, 7, 0.0F));

		lBackTentacle02b_1 = new ModelRenderer(this);
		lBackTentacle02b_1.setRotationPoint(0.0F, 0.0F, 6.3F);
		lBackTentacle02a_1.addChild(lBackTentacle02b_1);
		setRotationAngle(lBackTentacle02b_1, -0.4189F, -0.0873F, 0.0F);
		lBackTentacle02b_1.cubeList.add(new ModelBox(lBackTentacle02b_1, 4, 117, -1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F));

		lBackTentacle02c_1 = new ModelRenderer(this);
		lBackTentacle02c_1.setRotationPoint(-5.4F, -14.9F, -4.4F);
		lBackTentacle02b_1.addChild(lBackTentacle02c_1);
		setRotationAngle(lBackTentacle02c_1, -0.1396F, 0.1396F, 0.0F);
		lBackTentacle02c_1.cubeList.add(new ModelBox(lBackTentacle02c_1, 16, 114, -0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F));

		rBodyTentacle02a = new ModelRenderer(this);
		rBodyTentacle02a.setRotationPoint(-3.5F, -10.4F, -6.8F);
		bodyMid.addChild(rBodyTentacle02a);
		setRotationAngle(rBodyTentacle02a, 0.0524F, 0.1047F, 0.0F);
		rBodyTentacle02a.cubeList.add(new ModelBox(rBodyTentacle02a, 96, 100, -1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F));

		rBodyTentacle02b = new ModelRenderer(this);
		rBodyTentacle02b.setRotationPoint(0.0F, -15.3F, -9.1F);
		rBodyTentacle02a.addChild(rBodyTentacle02b);
		setRotationAngle(rBodyTentacle02b, 0.192F, -0.0873F, 0.0F);
		rBodyTentacle02b.cubeList.add(new ModelBox(rBodyTentacle02b, 5, 118, -1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F));

		rBodyTentacle02c = new ModelRenderer(this);
		rBodyTentacle02c.setRotationPoint(0.0F, 0.0F, -4.8F);
		rBodyTentacle02b.addChild(rBodyTentacle02c);
		setRotationAngle(rBodyTentacle02c, 0.1047F, 0.0F, 0.0F);
		rBodyTentacle02c.cubeList.add(new ModelBox(rBodyTentacle02c, 17, 115, -0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F));

		rBackTentacle02a_1 = new ModelRenderer(this);
		rBackTentacle02a_1.setRotationPoint(-3.5F, -14.5F, 5.9F);
		bodyMid.addChild(rBackTentacle02a_1);
		setRotationAngle(rBackTentacle02a_1, -0.5236F, -0.1047F, 0.0F);
		rBackTentacle02a_1.cubeList.add(new ModelBox(rBackTentacle02a_1, 95, 99, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F));

		rBackTentacle02b_1 = new ModelRenderer(this);
		rBackTentacle02b_1.setRotationPoint(0.0F, -15.6F, 0.8F);
		rBackTentacle02a_1.addChild(rBackTentacle02b_1);
		setRotationAngle(rBackTentacle02b_1, -0.2443F, 0.0873F, 0.0F);
		rBackTentacle02b_1.cubeList.add(new ModelBox(rBackTentacle02b_1, 5, 118, -1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F));

		rBackTentacle02c_1 = new ModelRenderer(this);
		rBackTentacle02c_1.setRotationPoint(0.0F, 0.0F, 4.7F);
		rBackTentacle02b_1.addChild(rBackTentacle02c_1);
		setRotationAngle(rBackTentacle02c_1, 0.1571F, -0.1396F, 0.0F);
		rBackTentacle02c_1.cubeList.add(new ModelBox(rBackTentacle02c_1, 17, 115, -0.5F, -0.5F, 0.0F, 1, 1, 4, 0.0F));

		rBodyTentacle01a = new ModelRenderer(this);
		rBodyTentacle01a.setRotationPoint(-4.8F, 4.1F, 0.7F);
		bodyBase.addChild(rBodyTentacle01a);
		setRotationAngle(rBodyTentacle01a, 0.3665F, 0.7854F, 0.0F);
		rBodyTentacle01a.cubeList.add(new ModelBox(rBodyTentacle01a, 63, 96, -2.0F, -2.0F, -8.0F, 4, 4, 8, 0.0F));

		rBodyTentacle01b = new ModelRenderer(this);
		rBodyTentacle01b.setRotationPoint(0.0F, 0.0F, -7.0F);
		rBodyTentacle01a.addChild(rBodyTentacle01b);
		setRotationAngle(rBodyTentacle01b, 0.0524F, -0.3665F, 0.0F);
		rBodyTentacle01b.cubeList.add(new ModelBox(rBodyTentacle01b, 93, 97, -1.5F, -1.5F, -8.0F, 3, 3, 8, 0.0F));

		rBodyTentacle01c = new ModelRenderer(this);
		rBodyTentacle01c.setRotationPoint(4.8F, -16.1F, -7.9F);
		rBodyTentacle01b.addChild(rBodyTentacle01c);
		setRotationAngle(rBodyTentacle01c, 0.4538F, 0.0873F, 0.0F);
		rBodyTentacle01c.cubeList.add(new ModelBox(rBodyTentacle01c, 1, 114, -1.0F, -1.0F, -9.0F, 2, 2, 9, 0.0F));

		rHindTentacle03a = new ModelRenderer(this);
		rHindTentacle03a.setRotationPoint(-4.2F, 10.0F, -3.5F);
		bodyBase.addChild(rHindTentacle03a);
		setRotationAngle(rHindTentacle03a, 0.0F, -1.0472F, 0.0F);
		rHindTentacle03a.cubeList.add(new ModelBox(rHindTentacle03a, 63, 96, -2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F));

		rHindTentacle03b = new ModelRenderer(this);
		rHindTentacle03b.setRotationPoint(0.0F, -11.6F, 7.2F);
		rHindTentacle03a.addChild(rHindTentacle03b);
		setRotationAngle(rHindTentacle03b, 0.0F, 0.5236F, 0.0F);
		rHindTentacle03b.cubeList.add(new ModelBox(rHindTentacle03b, 92, 96, -1.5F, -1.5F, 0.0F, 3, 3, 9, 0.0F));

		rHindTentacle03c = new ModelRenderer(this);
		rHindTentacle03c.setRotationPoint(0.0F, 0.4F, 8.6F);
		rHindTentacle03b.addChild(rHindTentacle03c);
		setRotationAngle(rHindTentacle03c, 0.0F, 0.2618F, 0.0F);
		rHindTentacle03c.cubeList.add(new ModelBox(rHindTentacle03c, 0, 113, -1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F));

		eye32 = new ModelRenderer(this);
		eye32.setRotationPoint(-1.1F, -1.2F, 3.4F);
		rHindTentacle03b.addChild(eye32);
		eye32.cubeList.add(new ModelBox(eye32, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		rHindTentacle03 = new ModelRenderer(this);
		rHindTentacle03.setRotationPoint(0.0F, 0.0F, 0.0F);
		rHindTentacle03a.addChild(rHindTentacle03);
		setRotationAngle(rHindTentacle03, 0.0F, -0.7854F, 0.0F);
		rHindTentacle03.cubeList.add(new ModelBox(rHindTentacle03, 66, 99, -2.0F, -2.0F, -3.5F, 4, 4, 5, 0.0F));

		rHindTentacle01a = new ModelRenderer(this);
		rHindTentacle01a.setRotationPoint(-4.1F, 6.7F, 4.9F);
		bodyBase.addChild(rHindTentacle01a);
		setRotationAngle(rHindTentacle01a, -0.1396F, -0.2094F, 0.0F);
		rHindTentacle01a.cubeList.add(new ModelBox(rHindTentacle01a, 0, 96, -3.0F, -3.0F, 0.0F, 6, 6, 10, 0.0F));

		rHindTentacle01b = new ModelRenderer(this);
		rHindTentacle01b.setRotationPoint(0.0F, -11.6F, 9.5F);
		rHindTentacle01a.addChild(rHindTentacle01b);
		setRotationAngle(rHindTentacle01b, 0.0F, 0.1396F, 0.0F);
		rHindTentacle01b.cubeList.add(new ModelBox(rHindTentacle01b, 34, 96, -2.5F, -2.5F, 0.0F, 5, 5, 8, 0.0F));

		rHindTentacle01c = new ModelRenderer(this);
		rHindTentacle01c.setRotationPoint(0.0F, 0.4F, 7.5F);
		rHindTentacle01b.addChild(rHindTentacle01c);
		setRotationAngle(rHindTentacle01c, 0.1396F, 0.0698F, 0.0F);
		rHindTentacle01c.cubeList.add(new ModelBox(rHindTentacle01c, 63, 96, -2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F));

		eye29 = new ModelRenderer(this);
		eye29.setRotationPoint(3.1F, -9.1F, -13.7F);
		rHindTentacle01c.addChild(eye29);
		eye29.cubeList.add(new ModelBox(eye29, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		rHindTentacle01d = new ModelRenderer(this);
		rHindTentacle01d.setRotationPoint(0.0F, 0.4F, 7.6F);
		rHindTentacle01c.addChild(rHindTentacle01d);
		setRotationAngle(rHindTentacle01d, 0.0F, 0.0524F, 0.0F);
		rHindTentacle01d.cubeList.add(new ModelBox(rHindTentacle01d, 92, 96, -1.5F, -1.5F, 0.0F, 3, 3, 9, 0.0F));

		rHindTentacle01e = new ModelRenderer(this);
		rHindTentacle01e.setRotationPoint(4.1F, -7.1F, -13.0F);
		rHindTentacle01d.addChild(rHindTentacle01e);
		setRotationAngle(rHindTentacle01e, 0.0F, -0.0698F, 0.0F);
		rHindTentacle01e.cubeList.add(new ModelBox(rHindTentacle01e, 0, 113, -1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F));

		eye30 = new ModelRenderer(this);
		eye30.setRotationPoint(4.0F, -9.3F, -18.1F);
		rHindTentacle01d.addChild(eye30);
		eye30.cubeList.add(new ModelBox(eye30, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		lHindTentacle03a = new ModelRenderer(this);
		lHindTentacle03a.setRotationPoint(4.2F, 10.0F, -3.5F);
		bodyBase.addChild(lHindTentacle03a);
		setRotationAngle(lHindTentacle03a, 0.0F, 1.0472F, 0.0F);
		lHindTentacle03a.cubeList.add(new ModelBox(lHindTentacle03a, 63, 96, -2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F));

		lHindTentacle03b = new ModelRenderer(this);
		lHindTentacle03b.setRotationPoint(0.0F, -11.6F, 7.2F);
		lHindTentacle03a.addChild(lHindTentacle03b);
		setRotationAngle(lHindTentacle03b, 0.0F, -0.5236F, 0.0F);
		lHindTentacle03b.cubeList.add(new ModelBox(lHindTentacle03b, 92, 96, -1.5F, -1.5F, 0.0F, 3, 3, 9, 0.0F));

		lHindTentacle03c = new ModelRenderer(this);
		lHindTentacle03c.setRotationPoint(0.0F, 0.4F, 8.6F);
		lHindTentacle03b.addChild(lHindTentacle03c);
		setRotationAngle(lHindTentacle03c, 0.0F, -0.2618F, 0.0F);
		lHindTentacle03c.cubeList.add(new ModelBox(lHindTentacle03c, 0, 113, -1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F));

		lHindTentacle03 = new ModelRenderer(this);
		lHindTentacle03.setRotationPoint(0.0F, -12.0F, 0.0F);
		lHindTentacle03a.addChild(lHindTentacle03);
		setRotationAngle(lHindTentacle03, 0.0F, 0.7854F, 0.0F);
		lHindTentacle03.cubeList.add(new ModelBox(lHindTentacle03, 66, 99, -2.0F, -2.0F, -3.5F, 4, 4, 5, 0.0F));

		lHindTentacle02a = new ModelRenderer(this);
		lHindTentacle02a.setRotationPoint(4.2F, 9.5F, -1.9F);
		bodyBase.addChild(lHindTentacle02a);
		setRotationAngle(lHindTentacle02a, 0.0F, 0.5236F, 0.0F);
		lHindTentacle02a.cubeList.add(new ModelBox(lHindTentacle02a, 0, 96, -3.0F, -3.0F, 0.0F, 6, 6, 10, 0.0F));

		lHindTentacle02b = new ModelRenderer(this);
		lHindTentacle02b.setRotationPoint(0.0F, -11.6F, 9.5F);
		lHindTentacle02a.addChild(lHindTentacle02b);
		setRotationAngle(lHindTentacle02b, 0.0F, -0.1047F, 0.0F);
		lHindTentacle02b.cubeList.add(new ModelBox(lHindTentacle02b, 34, 96, -2.5F, -2.5F, 0.0F, 5, 5, 8, 0.0F));

		eye23 = new ModelRenderer(this);
		eye23.setRotationPoint(2.1F, -2.5F, 3.5F);
		lHindTentacle02b.addChild(eye23);
		eye23.cubeList.add(new ModelBox(eye23, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		lHindTentacle02c = new ModelRenderer(this);
		lHindTentacle02c.setRotationPoint(0.0F, 0.4F, 7.5F);
		lHindTentacle02b.addChild(lHindTentacle02c);
		setRotationAngle(lHindTentacle02c, 0.0F, -0.3491F, 0.0F);
		lHindTentacle02c.cubeList.add(new ModelBox(lHindTentacle02c, 63, 96, -2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F));

		lHindTentacle02d = new ModelRenderer(this);
		lHindTentacle02d.setRotationPoint(0.0F, 0.4F, 7.6F);
		lHindTentacle02c.addChild(lHindTentacle02d);
		setRotationAngle(lHindTentacle02d, 0.0F, -0.0698F, 0.0F);
		lHindTentacle02d.cubeList.add(new ModelBox(lHindTentacle02d, 92, 96, -1.5F, -1.5F, 0.0F, 3, 3, 9, 0.0F));

		lHindTentacle02e = new ModelRenderer(this);
		lHindTentacle02e.setRotationPoint(-4.2F, -9.9F, -6.2F);
		lHindTentacle02d.addChild(lHindTentacle02e);
		setRotationAngle(lHindTentacle02e, 0.0F, 0.0698F, 0.0F);
		lHindTentacle02e.cubeList.add(new ModelBox(lHindTentacle02e, 0, 113, -1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F));

		eye24 = new ModelRenderer(this);
		eye24.setRotationPoint(1.6F, -0.2F, 3.6F);
		lHindTentacle02d.addChild(eye24);
		eye24.cubeList.add(new ModelBox(eye24, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		rHindTentacle02a = new ModelRenderer(this);
		rHindTentacle02a.setRotationPoint(-4.2F, 9.5F, -1.9F);
		bodyBase.addChild(rHindTentacle02a);
		setRotationAngle(rHindTentacle02a, 0.0F, -0.5236F, 0.0F);
		rHindTentacle02a.cubeList.add(new ModelBox(rHindTentacle02a, 0, 96, -3.0F, -3.0F, 0.0F, 6, 6, 10, 0.0F));

		rHindTentacle02b = new ModelRenderer(this);
		rHindTentacle02b.setRotationPoint(0.0F, -11.6F, 9.5F);
		rHindTentacle02a.addChild(rHindTentacle02b);
		setRotationAngle(rHindTentacle02b, 0.0F, 0.1047F, 0.0F);
		rHindTentacle02b.cubeList.add(new ModelBox(rHindTentacle02b, 34, 96, -2.5F, -2.5F, 0.0F, 5, 5, 8, 0.0F));

		rHindTentacle02c = new ModelRenderer(this);
		rHindTentacle02c.setRotationPoint(0.0F, 0.4F, 7.5F);
		rHindTentacle02b.addChild(rHindTentacle02c);
		setRotationAngle(rHindTentacle02c, 0.0F, 0.3491F, 0.0F);
		rHindTentacle02c.cubeList.add(new ModelBox(rHindTentacle02c, 63, 96, -2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F));

		eye31 = new ModelRenderer(this);
		eye31.setRotationPoint(2.7F, -11.7F, -3.8F);
		rHindTentacle02c.addChild(eye31);
		eye31.cubeList.add(new ModelBox(eye31, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		rHindTentacle02d = new ModelRenderer(this);
		rHindTentacle02d.setRotationPoint(4.2F, -9.5F, 0.0F);
		rHindTentacle02c.addChild(rHindTentacle02d);
		setRotationAngle(rHindTentacle02d, 0.0F, 0.0698F, 0.0F);
		rHindTentacle02d.cubeList.add(new ModelBox(rHindTentacle02d, 92, 96, -1.5F, -1.5F, 0.0F, 3, 3, 9, 0.0F));

		rHindTentacle02e = new ModelRenderer(this);
		rHindTentacle02e.setRotationPoint(0.0F, 0.0F, 1.4F);
		rHindTentacle02d.addChild(rHindTentacle02e);
		setRotationAngle(rHindTentacle02e, 0.0F, -0.0698F, 0.0F);
		rHindTentacle02e.cubeList.add(new ModelBox(rHindTentacle02e, 0, 113, -1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F));

		tail01a = new ModelRenderer(this);
		tail01a.setRotationPoint(0.0F, 3.7F, 11.3F);
		bodyBase.addChild(tail01a);
		setRotationAngle(tail01a, -0.2793F, 0.0F, 0.0F);
		tail01a.cubeList.add(new ModelBox(tail01a, 0, 96, -3.0F, -3.0F, 0.0F, 6, 6, 10, 0.0F));

		eye28 = new ModelRenderer(this);
		eye28.setRotationPoint(-0.9F, -15.3F, 3.8F);
		tail01a.addChild(eye28);
		eye28.cubeList.add(new ModelBox(eye28, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		tail01b = new ModelRenderer(this);
		tail01b.setRotationPoint(0.0F, -11.6F, 9.5F);
		tail01a.addChild(tail01b);
		setRotationAngle(tail01b, -0.0698F, 0.0F, 0.0F);
		tail01b.cubeList.add(new ModelBox(tail01b, 34, 96, -2.5F, -2.5F, 0.0F, 5, 5, 8, 0.0F));

		tail01c = new ModelRenderer(this);
		tail01c.setRotationPoint(0.0F, -3.3F, -3.8F);
		tail01b.addChild(tail01c);
		setRotationAngle(tail01c, 0.2793F, 0.0F, 0.0F);
		tail01c.cubeList.add(new ModelBox(tail01c, 63, 96, -2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F));

		tail01d = new ModelRenderer(this);
		tail01d.setRotationPoint(0.0F, 0.4F, 7.6F);
		tail01c.addChild(tail01d);
		setRotationAngle(tail01d, 0.0873F, 0.0F, 0.0F);
		tail01d.cubeList.add(new ModelBox(tail01d, 92, 96, -1.5F, -1.5F, 0.0F, 3, 3, 9, 0.0F));

		tail01e = new ModelRenderer(this);
		tail01e.setRotationPoint(0.0F, 0.4F, 8.9F);
		tail01d.addChild(tail01e);
		tail01e.cubeList.add(new ModelBox(tail01e, 0, 113, -1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F));

		eye26 = new ModelRenderer(this);
		eye26.setRotationPoint(1.0F, -2.0F, 3.8F);
		tail01c.addChild(eye26);
		eye26.cubeList.add(new ModelBox(eye26, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		eye27 = new ModelRenderer(this);
		eye27.setRotationPoint(-2.0F, -6.2F, -7.5F);
		tail01b.addChild(eye27);
		eye27.cubeList.add(new ModelBox(eye27, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		headJoint = new ModelRenderer(this);
		headJoint.setRotationPoint(0.0F, 1.6F, -4.0F);
		headJoint.cubeList.add(new ModelBox(headJoint, 0, 0, -0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F));

		bodyUpper = new ModelRenderer(this);
		bodyUpper.setRotationPoint(0.0F, 3.1F, 2.0F);
		headJoint.addChild(bodyUpper);
		setRotationAngle(bodyUpper, 1.5708F, 0.0F, 0.0F);
		bodyUpper.cubeList.add(new ModelBox(bodyUpper, 0, 1, -5.5F, -17.0F, -5.5F, 11, 17, 11, 0.0F));

		mouth01Upper = new ModelRenderer(this);
		mouth01Upper.setRotationPoint(0.0F, -16.9F, 4.3F);
		bodyUpper.addChild(mouth01Upper);
		mouth01Upper.cubeList.add(new ModelBox(mouth01Upper, 59, 0, -4.0F, -6.0F, -1.0F, 8, 6, 2, 0.0F));

		mouth01Lower = new ModelRenderer(this);
		mouth01Lower.setRotationPoint(0.0F, -1.3F, -3.5F);
		mouth01Upper.addChild(mouth01Lower);
		setRotationAngle(mouth01Lower, 0.2618F, 0.0F, 0.0F);
		mouth01Lower.cubeList.add(new ModelBox(mouth01Lower, 59, 11, -3.5F, -4.0F, -0.5F, 7, 5, 1, 0.0F));

		mouth01Teeth03 = new ModelRenderer(this);
		mouth01Teeth03.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth01Lower.addChild(mouth01Teeth03);
		mouth01Teeth03.cubeList.add(new ModelBox(mouth01Teeth03, 96, 19, 2.4F, -3.9F, 0.3F, 1, 4, 1, 0.0F));

		mouth01Teeth02 = new ModelRenderer(this);
		mouth01Teeth02.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth01Lower.addChild(mouth01Teeth02);
		mouth01Teeth02.cubeList.add(new ModelBox(mouth01Teeth02, 80, 19, -3.4F, -3.9F, 0.3F, 6, 4, 1, 0.0F));

		eye05 = new ModelRenderer(this);
		eye05.setRotationPoint(-2.3F, -3.2F, 2.9F);
		mouth01Upper.addChild(eye05);
		eye05.cubeList.add(new ModelBox(eye05, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		mouth01Tooth05 = new ModelRenderer(this);
		mouth01Tooth05.setRotationPoint(3.4F, -3.4F, -0.4F);
		mouth01Upper.addChild(mouth01Tooth05);
		setRotationAngle(mouth01Tooth05, 0.0F, -0.192F, 0.5236F);
		mouth01Tooth05.cubeList.add(new ModelBox(mouth01Tooth05, 105, 10, -0.5F, -0.5F, -2.9F, 1, 1, 3, 0.0F));

		mouth01Tooth03 = new ModelRenderer(this);
		mouth01Tooth03.setRotationPoint(-3.1F, -3.4F, -0.4F);
		mouth01Upper.addChild(mouth01Tooth03);
		setRotationAngle(mouth01Tooth03, 0.0F, 0.192F, 0.0F);
		mouth01Tooth03.cubeList.add(new ModelBox(mouth01Tooth03, 105, 10, -0.5F, -0.5F, -2.9F, 1, 1, 3, 0.0F));

		mouth01Snout = new ModelRenderer(this);
		mouth01Snout.setRotationPoint(0.0F, -1.3F, 2.4F);
		mouth01Upper.addChild(mouth01Snout);
		setRotationAngle(mouth01Snout, 0.5236F, 0.0F, 0.0F);
		mouth01Snout.cubeList.add(new ModelBox(mouth01Snout, 81, 0, -3.5F, -4.5F, -1.5F, 7, 6, 3, 0.0F));

		mouth01Tooth02 = new ModelRenderer(this);
		mouth01Tooth02.setRotationPoint(0.4F, -5.5F, -0.5F);
		mouth01Upper.addChild(mouth01Tooth02);
		setRotationAngle(mouth01Tooth02, -0.3142F, 0.2793F, 0.7854F);
		mouth01Tooth02.cubeList.add(new ModelBox(mouth01Tooth02, 105, 10, -0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F));

		mouth01Tooth04 = new ModelRenderer(this);
		mouth01Tooth04.setRotationPoint(-3.1F, -5.3F, -0.5F);
		mouth01Upper.addChild(mouth01Tooth04);
		setRotationAngle(mouth01Tooth04, 0.0F, 0.0F, 0.2618F);
		mouth01Tooth04.cubeList.add(new ModelBox(mouth01Tooth04, 105, 10, -0.5F, -0.5F, -2.2F, 1, 1, 3, 0.0F));

		mouth01Tooth01 = new ModelRenderer(this);
		mouth01Tooth01.setRotationPoint(-2.0F, -5.5F, -0.7F);
		mouth01Upper.addChild(mouth01Tooth01);
		setRotationAngle(mouth01Tooth01, -0.3142F, -0.2793F, -0.7854F);
		mouth01Tooth01.cubeList.add(new ModelBox(mouth01Tooth01, 105, 17, -0.5F, -0.5F, -3.7F, 1, 1, 5, 0.0F));

		eye04_1 = new ModelRenderer(this);
		eye04_1.setRotationPoint(2.3F, -3.2F, 2.9F);
		mouth01Upper.addChild(eye04_1);
		eye04_1.cubeList.add(new ModelBox(eye04_1, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		mouth01Tooth06 = new ModelRenderer(this);
		mouth01Tooth06.setRotationPoint(2.7F, -5.5F, -0.5F);
		mouth01Upper.addChild(mouth01Tooth06);
		setRotationAngle(mouth01Tooth06, -0.3142F, 0.2793F, 0.7854F);
		mouth01Tooth06.cubeList.add(new ModelBox(mouth01Tooth06, 105, 17, -0.5F, -0.5F, -4.4F, 1, 1, 5, 0.0F));

		mouth01Teeth01 = new ModelRenderer(this);
		mouth01Teeth01.setRotationPoint(0.0F, -5.0F, 0.0F);
		mouth01Upper.addChild(mouth01Teeth01);
		mouth01Teeth01.cubeList.add(new ModelBox(mouth01Teeth01, 79, 10, -3.5F, -0.7F, -2.9F, 7, 5, 2, 0.0F));

		eye09 = new ModelRenderer(this);
		eye09.setRotationPoint(-1.2F, -13.5F, 10.1F);
		bodyUpper.addChild(eye09);
		eye09.cubeList.add(new ModelBox(eye09, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		eye01 = new ModelRenderer(this);
		eye01.setRotationPoint(5.5F, -15.6F, 9.0F);
		bodyUpper.addChild(eye01);
		eye01.cubeList.add(new ModelBox(eye01, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		eye11 = new ModelRenderer(this);
		eye11.setRotationPoint(-2.1F, -5.5F, 10.3F);
		bodyUpper.addChild(eye11);
		eye11.cubeList.add(new ModelBox(eye11, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		eye03 = new ModelRenderer(this);
		eye03.setRotationPoint(5.6F, -5.4F, 2.6F);
		bodyUpper.addChild(eye03);
		eye03.cubeList.add(new ModelBox(eye03, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		eye02 = new ModelRenderer(this);
		eye02.setRotationPoint(-5.5F, -14.0F, 5.0F);
		bodyUpper.addChild(eye02);
		eye02.cubeList.add(new ModelBox(eye02, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		mouth02Upper = new ModelRenderer(this);
		mouth02Upper.setRotationPoint(3.8F, -12.2F, -1.8F);
		bodyUpper.addChild(mouth02Upper);
		setRotationAngle(mouth02Upper, 0.0F, 0.0F, 0.9599F);
		mouth02Upper.cubeList.add(new ModelBox(mouth02Upper, 59, 0, -4.0F, -6.0F, -1.0F, 8, 6, 2, 0.0F));

		mouth02Snout = new ModelRenderer(this);
		mouth02Snout.setRotationPoint(0.0F, -6.0F, 4.4F);
		mouth02Upper.addChild(mouth02Snout);
		setRotationAngle(mouth02Snout, 0.5236F, 0.0F, 0.0F);
		mouth02Snout.cubeList.add(new ModelBox(mouth02Snout, 81, 0, -3.5F, -4.5F, -1.5F, 7, 6, 3, 0.0F));

		mouth02Lower = new ModelRenderer(this);
		mouth02Lower.setRotationPoint(0.0F, -6.0F, -1.5F);
		mouth02Upper.addChild(mouth02Lower);
		setRotationAngle(mouth02Lower, 0.2618F, 0.0F, 0.0F);
		mouth02Lower.cubeList.add(new ModelBox(mouth02Lower, 59, 11, -3.5F, -4.0F, -0.5F, 7, 5, 1, 0.0F));

		mouth02Teeth02 = new ModelRenderer(this);
		mouth02Teeth02.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth02Lower.addChild(mouth02Teeth02);
		mouth02Teeth02.cubeList.add(new ModelBox(mouth02Teeth02, 80, 19, -3.4F, -3.9F, 0.3F, 6, 4, 1, 0.0F));

		mouth02Teeth03 = new ModelRenderer(this);
		mouth02Teeth03.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth02Lower.addChild(mouth02Teeth03);
		mouth02Teeth03.cubeList.add(new ModelBox(mouth02Teeth03, 96, 19, 2.4F, -3.9F, 0.3F, 1, 4, 1, 0.0F));

		mouth02Tooth02 = new ModelRenderer(this);
		mouth02Tooth02.setRotationPoint(2.7F, -10.2F, 1.5F);
		mouth02Upper.addChild(mouth02Tooth02);
		setRotationAngle(mouth02Tooth02, -0.3142F, 0.2793F, 0.7854F);
		mouth02Tooth02.cubeList.add(new ModelBox(mouth02Tooth02, 105, 17, -0.5F, -0.5F, -4.4F, 1, 1, 5, 0.0F));

		mouth02Tooth01 = new ModelRenderer(this);
		mouth02Tooth01.setRotationPoint(0.3F, -10.2F, 1.5F);
		mouth02Upper.addChild(mouth02Tooth01);
		setRotationAngle(mouth02Tooth01, -0.3142F, 0.2793F, 0.7854F);
		mouth02Tooth01.cubeList.add(new ModelBox(mouth02Tooth01, 105, 10, -0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F));

		eye06 = new ModelRenderer(this);
		eye06.setRotationPoint(0.4F, -3.0F, 3.5F);
		mouth02Upper.addChild(eye06);
		eye06.cubeList.add(new ModelBox(eye06, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		mouth02Tooth03 = new ModelRenderer(this);
		mouth02Tooth03.setRotationPoint(-3.4F, -5.5F, -0.7F);
		mouth02Upper.addChild(mouth02Tooth03);
		setRotationAngle(mouth02Tooth03, -0.3142F, -0.2793F, -0.7854F);
		mouth02Tooth03.cubeList.add(new ModelBox(mouth02Tooth03, 105, 17, -0.5F, -0.5F, -3.7F, 1, 1, 5, 0.0F));

		mouth02Teeth01 = new ModelRenderer(this);
		mouth02Teeth01.setRotationPoint(0.0F, -5.0F, 0.0F);
		mouth02Upper.addChild(mouth02Teeth01);
		mouth02Teeth01.cubeList.add(new ModelBox(mouth02Teeth01, 79, 10, -3.5F, -0.7F, -2.9F, 7, 5, 2, 0.0F));

		mouth02Tooth04 = new ModelRenderer(this);
		mouth02Tooth04.setRotationPoint(-1.6F, -5.5F, -0.6F);
		mouth02Upper.addChild(mouth02Tooth04);
		setRotationAngle(mouth02Tooth04, -0.3142F, -0.2793F, -0.7854F);
		mouth02Tooth04.cubeList.add(new ModelBox(mouth02Tooth04, 105, 17, -0.5F, -0.5F, -4.7F, 1, 1, 5, 0.0F));

		mouth02Tooth04_1 = new ModelRenderer(this);
		mouth02Tooth04_1.setRotationPoint(-3.4F, -3.4F, -0.4F);
		mouth02Upper.addChild(mouth02Tooth04_1);
		setRotationAngle(mouth02Tooth04_1, 0.192F, -0.192F, 0.8727F);
		mouth02Tooth04_1.cubeList.add(new ModelBox(mouth02Tooth04_1, 105, 10, -0.5F, -0.5F, -2.9F, 1, 1, 3, 0.0F));

		eye12 = new ModelRenderer(this);
		eye12.setRotationPoint(-6.0F, -6.4F, 3.9F);
		bodyUpper.addChild(eye12);
		eye12.cubeList.add(new ModelBox(eye12, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		eye10 = new ModelRenderer(this);
		eye10.setRotationPoint(3.4F, -7.7F, 6.0F);
		bodyUpper.addChild(eye10);
		eye10.cubeList.add(new ModelBox(eye10, 61, 30, -1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F));

		eye04 = new ModelRenderer(this);
		eye04.setRotationPoint(4.8F, -16.8F, 2.7F);
		bodyUpper.addChild(eye04);
		eye04.cubeList.add(new ModelBox(eye04, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		mouth03Upper = new ModelRenderer(this);
		mouth03Upper.setRotationPoint(-3.8F, -12.2F, -1.8F);
		bodyUpper.addChild(mouth03Upper);
		setRotationAngle(mouth03Upper, 0.0F, 0.0F, -0.9599F);
		mouth03Upper.cubeList.add(new ModelBox(mouth03Upper, 59, 0, -4.0F, -6.0F, -1.0F, 8, 6, 2, 0.0F));

		mouth03Snout = new ModelRenderer(this);
		mouth03Snout.setRotationPoint(0.0F, -6.0F, 4.4F);
		mouth03Upper.addChild(mouth03Snout);
		setRotationAngle(mouth03Snout, 0.5236F, 0.0F, 0.0F);
		mouth03Snout.cubeList.add(new ModelBox(mouth03Snout, 81, 0, -3.5F, -4.5F, -1.5F, 7, 6, 3, 0.0F));

		mouth03Teeth01 = new ModelRenderer(this);
		mouth03Teeth01.setRotationPoint(0.0F, -9.7F, 2.0F);
		mouth03Upper.addChild(mouth03Teeth01);
		mouth03Teeth01.cubeList.add(new ModelBox(mouth03Teeth01, 79, 10, -3.5F, -0.7F, -2.9F, 7, 5, 2, 0.0F));

		mouth03Tooth05 = new ModelRenderer(this);
		mouth03Tooth05.setRotationPoint(3.4F, -9.8F, 1.6F);
		mouth03Upper.addChild(mouth03Tooth05);
		setRotationAngle(mouth03Tooth05, 0.0873F, -0.3142F, 0.5236F);
		mouth03Tooth05.cubeList.add(new ModelBox(mouth03Tooth05, 105, 17, -0.5F, -0.5F, -3.8F, 1, 1, 5, 0.0F));

		mouth03Tooth02 = new ModelRenderer(this);
		mouth03Tooth02.setRotationPoint(0.2F, -10.2F, 1.6F);
		mouth03Upper.addChild(mouth03Tooth02);
		setRotationAngle(mouth03Tooth02, -0.3142F, 0.2793F, 0.7854F);
		mouth03Tooth02.cubeList.add(new ModelBox(mouth03Tooth02, 105, 10, -0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F));

		mouth03Lower = new ModelRenderer(this);
		mouth03Lower.setRotationPoint(0.0F, -6.0F, -1.5F);
		mouth03Upper.addChild(mouth03Lower);
		setRotationAngle(mouth03Lower, 0.2618F, 0.0F, 0.0F);
		mouth03Lower.cubeList.add(new ModelBox(mouth03Lower, 59, 11, -3.5F, -4.0F, -0.5F, 7, 5, 1, 0.0F));

		mouth02Teeth03_1 = new ModelRenderer(this);
		mouth02Teeth03_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth03Lower.addChild(mouth02Teeth03_1);
		mouth02Teeth03_1.cubeList.add(new ModelBox(mouth02Teeth03_1, 96, 19, -3.3F, -3.9F, 0.3F, 1, 4, 1, 0.0F));

		mouth03Teeth02 = new ModelRenderer(this);
		mouth03Teeth02.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth03Lower.addChild(mouth03Teeth02);
		mouth03Teeth02.cubeList.add(new ModelBox(mouth03Teeth02, 80, 19, -2.7F, -3.9F, 0.3F, 6, 4, 1, 0.0F));

		eye08 = new ModelRenderer(this);
		eye08.setRotationPoint(3.3F, -9.5F, 3.4F);
		mouth03Upper.addChild(eye08);
		eye08.cubeList.add(new ModelBox(eye08, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		mouth03Tooth01 = new ModelRenderer(this);
		mouth03Tooth01.setRotationPoint(-1.7F, -10.2F, 1.3F);
		mouth03Upper.addChild(mouth03Tooth01);
		setRotationAngle(mouth03Tooth01, -0.3142F, -0.2793F, -0.7854F);
		mouth03Tooth01.cubeList.add(new ModelBox(mouth03Tooth01, 105, 17, -0.5F, -0.5F, -4.8F, 1, 1, 5, 0.0F));

		mouth03Tooth04 = new ModelRenderer(this);
		mouth03Tooth04.setRotationPoint(2.2F, -10.2F, 1.6F);
		mouth03Upper.addChild(mouth03Tooth04);
		setRotationAngle(mouth03Tooth04, -0.4189F, 0.0873F, 0.7854F);
		mouth03Tooth04.cubeList.add(new ModelBox(mouth03Tooth04, 105, 10, -0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F));

		eye07 = new ModelRenderer(this);
		eye07.setRotationPoint(-1.8F, -7.7F, 5.5F);
		mouth03Upper.addChild(eye07);
		eye07.cubeList.add(new ModelBox(eye07, 75, 30, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F));

		mouth03Tooth03 = new ModelRenderer(this);
		mouth03Tooth03.setRotationPoint(-3.3F, -10.2F, 1.6F);
		mouth03Upper.addChild(mouth03Tooth03);
		setRotationAngle(mouth03Tooth03, -0.3142F, 0.192F, -0.9561F);
		mouth03Tooth03.cubeList.add(new ModelBox(mouth03Tooth03, 105, 10, -0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bodyBase.render(f5);
		headJoint.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}