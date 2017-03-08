/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import org.apache.logging.log4j.Level;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

public class IMCHandler {

	public static void handleIMC(FMLInterModComms.IMCEvent event){
		List<String> senders = new ArrayList<String>();
		for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
			if(imcMessage.key.equals("shoggothFood"))
				try {
					EntityUtil.addShoggothFood((Class<? extends EntityLivingBase>)Class.forName(imcMessage.getStringValue()));
					info("Received Shoggoth Food addition %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (ClassNotFoundException e) {
					warning("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equals("registerTransmutatorFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler)Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.TRANSMUTATOR);
					info("Recieved Transmutator fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					warning("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					warning("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					warning("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equals("registerCrystallizerFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler)Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.CRYSTALLIZER);
					info("Recieved Crystallizer fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					warning("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					warning("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					warning("Could not find class %s", imcMessage.getStringValue());
				}
			else if(imcMessage.key.equals("addCrystal")){
				boolean failed = false;
				if(!imcMessage.isItemStackMessage())
					failed = true;
				else{
					ItemStack crystal = imcMessage.getItemStackValue();
					if(crystal == null)
						failed = true;
					if(!failed)
						AbyssalCraftAPI.addCrystal(crystal);
				}
				if(failed)
					warning("Received invalid Crystal addition from mod %s", imcMessage.getSender());
				else info("Received Crystal addition from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp"))
						failed = true;
					ItemStack input = new ItemStack(stuff.getCompoundTag("input"));
					ItemStack output1 = new ItemStack(stuff.getCompoundTag("output1"));
					ItemStack output2 = new ItemStack(stuff.getCompoundTag("output2"));
					if(input == null || output1 == null || output2 == null)
						failed = true;
					if(!failed)
						AbyssalCraftAPI.addCrystallization(input, output1, output2, stuff.getFloat("xp"));
				}
				if(failed)
					warning("Received invalid Crystallizer recipe from mod %s!", imcMessage.getSender());
				else info("Received Crystallizer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addSingleCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					ItemStack input = new ItemStack(stuff.getCompoundTag("input"));
					ItemStack output = new ItemStack(stuff.getCompoundTag("output"));
					if(input == null || output == null)
						failed = true;
					if(!failed)
						AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					warning("Received invalid Single Crystallizer recipe from mod %s!", imcMessage.getSender());
				else info("Received Single Crystallizer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addOredictCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp"))
						failed = true;
					String input = stuff.getString("input");
					String output1 = stuff.getString("output1");
					String output2 = stuff.getString("output2");
					if(input == null || output1 == null || output2 == null)
						failed = true;
					if(!failed)
						if(stuff.hasKey("quantity1") && stuff.hasKey("quantity2"))
							AbyssalCraftAPI.addCrystallization(input, output1, stuff.getInteger("quantity1"), output2, stuff.getInteger("quantity2"), stuff.getFloat("xp"));
						else AbyssalCraftAPI.addCrystallization(input, output1, output2, stuff.getFloat("xp"));
				}
				if(failed)
					warning("Received invalid OreDictionary Crystallizer recipe from mod %s!", imcMessage.getSender());
				else info("Received OreDictionary Crystallizer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addSingleOredictCrystallization")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if(input == null || output == null)
						failed = true;
					if(!failed)
						if(stuff.hasKey("quantity"))
							AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getInteger("quantity"), stuff.getFloat("xp"));
						else AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					warning("Received invalid Single OreDictionary Crystallizer recipe from mod %s!", imcMessage.getSender());
				else info("Received Single OreDictionary Crystallizer recipe from mod %s", imcMessage.getSender());

			}
			else if(imcMessage.key.equals("addTransmutation")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					ItemStack input = new ItemStack(stuff.getCompoundTag("input"));
					ItemStack output = new ItemStack(stuff.getCompoundTag("output"));
					if(input == null || output == null)
						failed = true;
					if(!failed)
						AbyssalCraftAPI.addTransmutation(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					warning("Received invalid Transmutator recipe from mod %s!", imcMessage.getSender());
				else info("Received Transmutator recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addOredictTransmutation")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp"))
						failed = true;
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if(input == null || output == null)
						failed = true;
					if(!failed)
						if(stuff.hasKey("quantity"))
							AbyssalCraftAPI.addTransmutation(input, output, stuff.getInteger("quantity"), stuff.getFloat("xp"));
						else AbyssalCraftAPI.addTransmutation(input, output, stuff.getFloat("xp"));
				}
				if(failed)
					warning("Received invalid OreDictionary Transmutator recipe from mod %s!", imcMessage.getSender());
				else info("Received OreDictionary Transmutator recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addMaterialization")){ //TODO: rewrite this
				boolean failed = false;
				ItemStack[] items;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if(!stuff.hasKey("input1") || !stuff.hasKey("output"))
						failed = true;
					ItemStack input1 = new ItemStack(stuff.getCompoundTag("input1"));
					ItemStack input2 = new ItemStack(stuff.getCompoundTag("input2"));
					ItemStack input3 = new ItemStack(stuff.getCompoundTag("input3"));
					ItemStack input4 = new ItemStack(stuff.getCompoundTag("input4"));
					ItemStack input5 = new ItemStack(stuff.getCompoundTag("input5"));
					ItemStack output = new ItemStack(stuff.getCompoundTag("output"));
					if(input1 == null || output == null)
						failed = true;
					items = new ItemStack[5];
					items[0] = input1;
					items[1] = input2;
					items[2] = input3;
					items[3] = input4;
					items[4] = input5;
					if(!failed)
						AbyssalCraftAPI.addMaterialization(items, output);
				}
				if(failed)
					warning("Received invalid Materializer recipe from mod %s!", imcMessage.getSender());
				else info("Received Materializer recipe from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("shoggothBlacklist")){
				boolean failed = false;
				if(!imcMessage.isItemStackMessage())
					failed = true;
				else if(Block.getBlockFromItem(imcMessage.getItemStackValue().getItem()) != null){
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					AbyssalCraftAPI.addShoggothBlacklist(Block.getBlockFromItem(imcMessage.getItemStackValue().getItem()));
				} else failed = true;
				if(failed)
					warning("Received invalid Shoggoth Block Blacklist from mod %s!", imcMessage.getSender());
				else info("Received Shoggoth Block Blacklist from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulArmor")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack helmet = new ItemStack(tag.getCompoundTag("helmet"));
					ItemStack chestplate = new ItemStack(tag.getCompoundTag("chestplate"));
					ItemStack leggings = new ItemStack(tag.getCompoundTag("leggings"));
					ItemStack boots = new ItemStack(tag.getCompoundTag("boots"));
					if(helmet != null && chestplate != null && leggings != null && boots != null){
						if(tag.hasKey("res1") && tag.hasKey("res2"))
							AbyssalCraftAPI.addGhoulArmorTextures(helmet.getItem(), chestplate.getItem(), leggings.getItem(), boots.getItem(), tag.getString("res1"), tag.getString("res2"));
						else failed = true;
					} else failed = true;
				}
				if(failed)
					warning("Received invalid Ghoul Armor Texture Registration from mod %s!", imcMessage.getSender());
				else info("Received Ghoul Armor Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulHelmet")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack helmet = new ItemStack(tag.getCompoundTag("helmet"));
					if(helmet != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulHelmetTexture(helmet.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					warning("Received invalid Ghoul Helmet Texture Registration from mod %s!", imcMessage.getSender());
				else info("Received Ghoul Helmet Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulChestplate")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack chestplate = new ItemStack(tag.getCompoundTag("chestplate"));
					if(chestplate != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulChestplateTexture(chestplate.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					warning("Received invalid Ghoul Chestplate Texture Registration from mod %s!", imcMessage.getSender());
				else info("Received Ghoul Chestplate Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulLeggings")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack leggings = new ItemStack(tag.getCompoundTag("leggings"));
					if(leggings != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulLeggingsTexture(leggings.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					warning("Received invalid Ghoul Leggings Texture Registration from mod %s!", imcMessage.getSender());
				else info("Received Ghoul Leggings Texture Registration from mod %s", imcMessage.getSender());
			}
			else if(imcMessage.key.equals("addGhoulBoots")){
				boolean failed = false;
				if(!imcMessage.isNBTMessage())
					failed = true;
				else {
					if(!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound tag = imcMessage.getNBTValue();
					ItemStack boots = new ItemStack(tag.getCompoundTag("boots"));
					if(boots != null && tag.hasKey("res"))
						AbyssalCraftAPI.addGhoulBootsTexture(boots.getItem(), tag.getString("res"));
					else failed = true;
				}
				if(failed)
					warning("Received invalid Ghoul Boots Texture Registration from mod %s!", imcMessage.getSender());
				else info("Received Ghoul Boots Texture Registration from mod %s", imcMessage.getSender());
			}
			else warning("Received an IMC Message with unknown key (%s) from mod %s!", imcMessage.key, imcMessage.getSender());
		if(!senders.isEmpty())
			info("Recieved messages from the following mods: %s", senders);
	}

	private static void info(String format, Object...data){
		FMLLog.log("AbyssalCraft|IMC", Level.INFO, format, data);
	}

	private static void warning(String format, Object...data){
		FMLLog.log("AbyssalCraft|IMC", Level.WARN, format, data);
	}
}
