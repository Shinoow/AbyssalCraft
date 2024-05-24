/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public abstract class ModelArmoredBase extends ModelBase {

	public abstract void setVisible(boolean visible);

	public abstract void setEquipmentSlotVisible(EntityEquipmentSlot slot);
}
