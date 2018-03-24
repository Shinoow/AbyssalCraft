/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.necronomicon.condition.caps;

import java.util.List;

import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

import net.minecraft.entity.player.EntityPlayer;

public interface INecroDataCapability {

	public boolean isUnlocked(IUnlockCondition cond, EntityPlayer player);

	public void triggerEntityUnlock(String name);

	public void triggerBiomeUnlock(String name);

	public void triggerDimensionUnlock(int id);

	public void triggerArtifactUnlock(String name);

	public void triggerPageUnlock(String name);

	public void triggerWhisperUnlock(String name);

	public void triggerMiscUnlock(String name);

	public void unlockAllKnowledge(boolean unlock);

	public List<String> getBiomeTriggers();

	public List<String> getEntityTriggers();

	public List<Integer> getDimensionTriggers();

	public List<String> getArtifactTriggers();

	public List<String> getPageTriggers();

	public List<String> getWhisperTriggers();

	public List<String> getMiscTriggers();

	public boolean hasUnlockedAllKnowledge();

	public void copy(INecroDataCapability cap);
}
