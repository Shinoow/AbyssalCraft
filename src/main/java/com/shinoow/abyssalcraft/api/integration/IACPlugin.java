/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.api.integration;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;

/**
 * Simple interface to handle integrations. If used by another mod, register the
 * file implementing this interface in {@link AbyssalCraftAPI} or through a IMC message
 * 
 * @author shinoow
 *
 * @since 1.3
 */
public interface IACPlugin {

	/**
	 * Used to fetch the mod name
	 * @return A String representing the mod's name
	 */
	public String getModName();

	/**
	 * Will be called at the end of the pre-init stage
	 */
	public void preInit();

	/**
	 * Will be called at the end of the init stage
	 */
	public void init();

	/**
	 * Will be called at the post-init stage
	 */
	public void postInit();
}
