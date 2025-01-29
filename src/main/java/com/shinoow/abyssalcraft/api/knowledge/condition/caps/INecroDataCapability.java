/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.knowledge.condition.caps;

import java.util.List;

import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface INecroDataCapability {

	boolean isUnlocked(IUnlockCondition cond, EntityPlayer player);

	boolean isUnlocked(IResearchItem research, EntityPlayer player);

	void triggerEntityUnlock(String name);

	void triggerBiomeUnlock(String name);

	void triggerDimensionUnlock(int id);

	void triggerArtifactUnlock(String name);

	void triggerPageUnlock(String name);

	void triggerWhisperUnlock(String name);

	void triggerMiscUnlock(String name);

	void completeResearch(ResourceLocation rel);

	void unlockAllKnowledge(boolean unlock);

	void setKnowledgeLevel(int level);

	void setKnowledgePoints(int points);

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

	List<ResourceLocation> getCompletedResearches();

	boolean hasUnlockedAllKnowledge();

	int getKnowledgeLevel();

	int getKnowledgePoints();

	long getLastSyncTime();

	int getSyncTimer();

	void copy(INecroDataCapability cap);
}
