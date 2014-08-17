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
package com.shinoow.abyssalcraft.core;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@Name("AbyssalCraft Core")
@MCVersion("1.7.2")
@TransformerExclusions("com.shinoow.abyssalcraft.core")
public class ACCorePlugin implements IFMLLoadingPlugin, IFMLCallHook {

	public static File coreLocation;

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return Core.class.getName();
	}

	@Override
	public String getSetupClass() {
		return "com.shinoow.abyssalcraft.core.ACCorePlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if(data.containsKey("coremodLocation"))
			coreLocation = (File) data.get("coremodLocation");
	}

	@Override
	public Void call() throws Exception {
		return null;
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}