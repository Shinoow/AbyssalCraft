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
package com.shinoow.abyssalcraft.client.render.item;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.item.ModelStaff;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderStaff implements IItemRenderer
{
	protected ModelStaff model;

	ResourceLocation resource = new ResourceLocation("/assets/abyssalcraft/textures/model/staff.png");

	public RenderStaff()
	{
		model = new ModelStaff();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {

		switch(type)
		{
		case EQUIPPED: return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		switch(type)
		{
		case EQUIPPED:
		{
			GL11.glPushMatrix();

			FMLClientHandler.instance().getClient().renderEngine.bindTexture(resource);

			model.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

			GL11.glPopMatrix();
		}
		default:
			break;
		}

	}

}