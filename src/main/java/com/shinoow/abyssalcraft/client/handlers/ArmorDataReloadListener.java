/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.handlers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Predicate;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.shinoow.abyssalcraft.api.armor.ArmorDataCollection;
import com.shinoow.abyssalcraft.api.armor.ArmorDataRegistry;
import com.shinoow.abyssalcraft.common.util.ACLogger;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;

public class ArmorDataReloadListener implements ISelectiveResourceReloadListener {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		updateArmorData(resourceManager);
	}

	public static void updateArmorData(IResourceManager resourceManager) {
		IResource iresource = null;

		ArmorDataRegistry.instance().clearInnerCollections();
		for(String str : resourceManager.getResourceDomains()) {
			try {
				resourceManager.getResourceDomains();
				iresource = resourceManager.getResource(new ResourceLocation(str, "ac_armordata.json"));
				InputStreamReader reader = new InputStreamReader(iresource.getInputStream());
				ArmorDataCollection data = new Gson().fromJson(reader, ArmorDataCollection.class);
				reader.close();
				if(data != null)
					ArmorDataRegistry.instance().processCollection(data);
			}
			catch (Exception e)
			{
				if(!(e instanceof IOException)) {
					ACLogger.severe("An error occurred when parsing ac_armordata.json from {}: {}", str, e);
					e.printStackTrace();
				}
			}
			finally
			{
				IOUtils.closeQuietly(iresource);
			}
		}
	}
}
