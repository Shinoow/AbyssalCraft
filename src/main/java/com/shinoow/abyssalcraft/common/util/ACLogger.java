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
package com.shinoow.abyssalcraft.common.util;

import net.minecraftforge.fml.common.FMLLog;

import org.apache.logging.log4j.Level;

public class ACLogger {

	public static void log(Level level, String format, Object... data) {
		FMLLog.log("AbyssalCraft", level, format, data);
	}

	public static void severe(String format, Object... data) {
		log(Level.ERROR, format, data);
	}

	public static void warning(String format, Object... data) {
		log(Level.WARN, format, data);
	}

	public static void info(String format, Object... data) {
		log(Level.INFO, format, data);
	}

	public static void fine(String format, Object... data) {
		log(Level.DEBUG, format, data);
	}

	public static void finer(String format, Object... data) {
		log(Level.TRACE, format, data);
	}
}
