/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import net.minecraftforge.fml.common.event.*;

/**
 * Simple interface to create separate modules for things during mod startup
 * @author shinoow
 *
 */
public interface ILifeCycleHandler {

	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void postInit(FMLPostInitializationEvent event);

	void loadComplete(FMLLoadCompleteEvent event);
}
