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
package com.shinoow.abyssalcraft.api;

/**
 * List of InterModComms messages you can use instead of the API registry methods
 * 
 * @author shinoow
 *
 */
public class IMCHelper {

	//SHOGGOTH FOOD ////////////////////////////////////////////////////////////////////////////
	/**
	 * This is a IMC version of AbyssalCraftAPI#addShoggothFood
	 * You can use the IMC message "shoggothFood" to add a Entity to the Shoggoth Food list
	 * The format for the message should be the string path to the entity class
	 * 
	 * Example of how it would look like if I added the Depths Ghoul to the food list:
	 * FMLInterModComms.sendMessage("abyssalcraft", "shoggothFood", "com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul");
	 */

	//TRANSMUTATOR FUEL HANDLER ///////////////////////////////////////////////////////////////
	/**
	 * This is a IMC version of AbyssalCraftAPI#registerFuelHandler made for registering Transmutator fuel handlers
	 * You can use the IMC message "registerTransmutatorFuel" to register a fuel handler
	 * The format for the message should be the string path to the handler class
	 * 
	 * Example of how it would look like if I added my Furnace fuel handler as a Transmutator fuel handler:
	 * FMLInterModComms.sendMessage("abyssalcraft", "registerTransmutatorFuel", "com.shinoow.abyssalcraft.common.handlers.FurnaceFuelHandler");
	 */

	//CRYSTALLIZER FUEL HANDLER ///////////////////////////////////////////////////////////////
	/**
	 * This is a IMC version of AbyssalCraftAPI#registerFuelHandler made for registering Crystallizer fuel handlers
	 * You can use the IMC message "registerCrystallizerFuel" to register a fuel handler
	 * The format for the message should be the string path to the handler class
	 * 
	 * Example of how it would look like if I added my Furnace fuel handler as a Crystallizer fuel handler:
	 * FMLInterModComms.sendMessage("abyssalcraft", "registerCrystallizerFuel", "com.shinoow.abyssalcraft.common.handlers.FurnaceFuelHandler");
	 */
}