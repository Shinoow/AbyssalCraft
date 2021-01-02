/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.handlers;

import java.io.InputStreamReader;
import java.util.function.Predicate;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.util.ClientVars;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;

public class ClientVarsReloadListener implements ISelectiveResourceReloadListener {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {

		if(resourcePredicate.test(VanillaResourceType.TEXTURES))
			updateVars(resourceManager);
	}

	public static void updateVars(IResourceManager resourceManager) {
		IResource iresource = null;

		try {
			iresource = resourceManager.getResource(new ResourceLocation("abyssalcraft", "clientvars.json"));
			InputStreamReader reader = new InputStreamReader(iresource.getInputStream());
			ClientVars data = new Gson().fromJson(reader, ClientVars.class);
			reader.close();
			if(data != null)
				ACClientVars.setClientVars(data);
		}
		catch (Exception e)
		{
			ACLogger.severe("An error occurred when parsing clientvars.json: {}", e);
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly(iresource);
		}
	}
}
