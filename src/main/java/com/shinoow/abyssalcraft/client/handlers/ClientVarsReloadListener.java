package com.shinoow.abyssalcraft.client.handlers;

import java.io.Closeable;
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
			if(data != null) {
				ACClientVars.setClientVars(data);
			}
		}
		catch (Exception e)
		{
			ACLogger.severe("An error occurred when parsing clientvars.json: {}", e);
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly((Closeable)iresource);
		}
	}
}
