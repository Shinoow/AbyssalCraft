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
package com.shinoow.abyssalcraft.api.necronomicon.condition.caps;

import java.util.List;

import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

import net.minecraft.entity.player.EntityPlayer;

public interface INecroDataCapability {

	boolean isUnlocked(IUnlockCondition cond, EntityPlayer player);

	void triggerEntityUnlock(String name);

	void triggerBiomeUnlock(String name);

	void triggerDimensionUnlock(int id);

	void triggerArtifactUnlock(String name);

	void triggerPageUnlock(String name);

	void triggerWhisperUnlock(String name);

	void triggerMiscUnlock(String name);

	void unlockAllKnowledge(boolean unlock);

	void setLastSyncTime(long time);

	void incrementSyncTimer();

	void resetSyncTimer();

	List<String> getBiomeTriggers();

	List<String> getEntityTriggers();

	List<Integer> getDimensionTriggers();

	List<String> getArtifactTriggers();

	List<String> getPageTriggers();

	List<String> getWhisperTriggers();

	List<String> getMiscTriggers();

	boolean hasUnlockedAllKnowledge();

	long getLastSyncTime();

	int getSyncTimer();

	void copy(INecroDataCapability cap);
}
