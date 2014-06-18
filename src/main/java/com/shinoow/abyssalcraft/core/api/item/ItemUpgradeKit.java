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
package com.shinoow.abyssalcraft.core.api.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Use this class to create Upgrade Kits for tools/armor.
 * You will need to create a crafting recipe for the upgrade:
 * GameRegistry.addShapelessRecipe(new ItemStack(NewItem), OldItem, UpgradeKit);
 * @author shinoow
 *
 */
public class ItemUpgradeKit extends Item {

	public final String typeName;
	public final String typeName2;

	/**
	 * The Strings are only for display.
	 * Remember to create a crafting recipe for the upgrade:
	 * GameRegistry.addShapelessRecipe(new ItemStack(NewItem), OldItem, UpgradeKit);
	 * @param par2Str The old material
	 * @param par3Str The new material
	 */
	public ItemUpgradeKit(String par2Str, String par3Str){
		super();
		typeName = par2Str;
		typeName2 = par3Str;
		maxStackSize = 16;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(typeName + " To " + typeName2);
	}
}