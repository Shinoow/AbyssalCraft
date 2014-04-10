package com.shinoow.abyssalcraft.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
@MCVersion("1.7.2")
@TransformerExclusions("com.shinoow.abyssalcraft.asm")
@Name("AbyssalCraft Core")
public class ACFMLCorePlugin implements IFMLLoadingPlugin, IFMLCallHook {

	public static File location;
	
	public String[] getLibraryRequestClass() {
		
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		
		return new String[]{ACAccessTransformer.class.getName(),
				};
	}
	//ACClassTransformer.class.getName()
	@Override
	public String getModContainerClass() {
		
		return null;
	}

	@Override
	public String getSetupClass() {
		
		return "com.shinoow.abyssalcraft.asm.ACFMLCorePlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
		if(data.containsKey("coremodLocation"))
			location = (File) data.get("coremodLocation");
	}

	@Override
	public Void call() throws Exception {
		
		return null;
	}

	@Override
	public String getAccessTransformerClass() {
		
		return ACAccessTransformer.class.getName();
	}

}
