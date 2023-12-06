/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.util;

import net.minecraft.util.text.translation.I18n;

/**
 * Wrapping around I18n calls to reduce deprecation warnings
 * @author shinoow
 *
 */
@SuppressWarnings("deprecation")
public class TranslationUtil {

	public static String toLocal(String key) {

		return I18n.translateToLocal(key);
	}

	public static String toLocalFormatted(String key, Object...format) {
		return I18n.translateToLocalFormatted(key, format);
	}
}
