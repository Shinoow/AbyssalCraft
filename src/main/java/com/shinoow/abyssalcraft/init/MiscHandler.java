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
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.AbyssalCraft.modid;
import static com.shinoow.abyssalcraft.init.BlockHandler.*;
import static com.shinoow.abyssalcraft.lib.ACSounds.*;

import java.io.File;
import java.util.Stack;

import javax.annotation.Nonnull;

import com.google.common.base.Predicate;
import com.google.gson.JsonObject;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.structure.StructureHandler;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.condition.ConditionProcessorRegistry;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.UnlockConditions;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapabilityStorage;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapabilityStorage;
import com.shinoow.abyssalcraft.common.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster;
import com.shinoow.abyssalcraft.common.datafix.BlockFlatteningDefinitions;
import com.shinoow.abyssalcraft.common.enchantments.*;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.potion.PotionBuilder;
import com.shinoow.abyssalcraft.common.potion.PotionEffectUtil;
import com.shinoow.abyssalcraft.common.structures.pe.ArchwayStructure;
import com.shinoow.abyssalcraft.common.structures.pe.BasicStructure;
import com.shinoow.abyssalcraft.common.structures.pe.TotemPoleStructure;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.common.util.ShapedNBTRecipe;
import com.shinoow.abyssalcraft.lib.*;
import com.shinoow.abyssalcraft.lib.item.ItemCrystal;
import com.shinoow.abyssalcraft.lib.util.NecroDataJsonUtil;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class MiscHandler implements ILifeCycleHandler {

	public static PotionType Cplague_normal, Cplague_long, Dplague_normal, Dplague_long,
	Dplague_strong, antiMatter_normal, antiMatter_long;

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(this);

		AbyssalCraftAPI.coralium_plague = new PotionBuilder(true, ACClientVars.getCoraliumPlaguePotionColor())
				.setReadyFunction((a,b) -> true)
				.setCurativeItems(new ItemStack(ACItems.coralium_plague_antidote))
				.setEffectFunction(PotionEffectUtil::applyCoraliumEffect)
				.setIconIndex(1, 0)
				.setStatusIconIndex(0)
				.setPotionName("potion.Cplague")
				.build();
		AbyssalCraftAPI.dread_plague = new PotionBuilder(true, ACClientVars.getDreadPlaguePotionColor())
				.setReadyFunction((a,b) -> true)
				.setCurativeItems(new ItemStack(ACItems.dread_plague_antidote))
				.setEffectFunction(PotionEffectUtil::applyDreadEffect)
				.setIconIndex(1, 0)
				.setStatusIconIndex(1)
				.setPotionName("potion.Dplague")
				.build();
		AbyssalCraftAPI.antimatter_potion = new PotionBuilder(true, ACClientVars.getAntimatterPotionColor())
				.setReadyFunction((a,b) -> true)
				.setEffectFunction(PotionEffectUtil::applyAntimatterEffect)
				.setIconIndex(1, 0)
				.setStatusIconIndex(2)
				.setPotionName("potion.Antimatter")
				.build();
		AbyssalCraftAPI.coralium_antidote = new PotionBuilder(false, ACClientVars.getCoraliumAntidotePotionColor())
				.setReadyFunction((a,b) -> true)
				.setEffectFunction((e,a) -> e.removePotionEffect(AbyssalCraftAPI.coralium_plague))
				.setIconIndex(1, 0)
				.setStatusIconIndex(3)
				.setPotionName("potion.coralium_antidote")
				.build();
		AbyssalCraftAPI.dread_antidote = new PotionBuilder(false, ACClientVars.getDreadAntidotePotionColor())
				.setReadyFunction((a,b) -> true)
				.setEffectFunction((e,a) -> e.removePotionEffect(AbyssalCraftAPI.dread_plague))
				.setIconIndex(1, 0)
				.setStatusIconIndex(3)
				.setPotionName("potion.dread_antidote")
				.build();

		registerPotion(new ResourceLocation("abyssalcraft", "cplague"), AbyssalCraftAPI.coralium_plague);
		registerPotion(new ResourceLocation("abyssalcraft", "dplague"), AbyssalCraftAPI.dread_plague);
		registerPotion(new ResourceLocation("abyssalcraft", "antimatter"), AbyssalCraftAPI.antimatter_potion);
		registerPotion(new ResourceLocation("abyssalcraft", "coralium_antidote"), AbyssalCraftAPI.coralium_antidote);
		registerPotion(new ResourceLocation("abyssalcraft", "dread_antidote"), AbyssalCraftAPI.dread_antidote);

		Cplague_normal = new PotionType("Cplague", new PotionEffect(AbyssalCraftAPI.coralium_plague, 3600));
		Cplague_long = new PotionType("Cplague", new PotionEffect(AbyssalCraftAPI.coralium_plague, 9600));
		Dplague_normal = new PotionType("Dplague", new PotionEffect(AbyssalCraftAPI.dread_plague, 3600));
		Dplague_long = new PotionType("Dplague", new PotionEffect(AbyssalCraftAPI.dread_plague, 9600));
		Dplague_strong = new PotionType("Dplague", new PotionEffect(AbyssalCraftAPI.dread_plague, 432, 1));
		antiMatter_normal = new PotionType("Antimatter", new PotionEffect(AbyssalCraftAPI.antimatter_potion, 3600));
		antiMatter_long = new PotionType("Antimatter", new PotionEffect(AbyssalCraftAPI.antimatter_potion, 9600));

		registerPotionType(new ResourceLocation("abyssalcraft", "cplague"), Cplague_normal);
		registerPotionType(new ResourceLocation("abyssalcraft", "cplague_long"), Cplague_long);
		registerPotionType(new ResourceLocation("abyssalcraft", "dplague"), Dplague_normal);
		registerPotionType(new ResourceLocation("abyssalcraft", "dplague_long"), Dplague_long);
		registerPotionType(new ResourceLocation("abyssalcraft", "dplague_strong"), Dplague_strong);
		registerPotionType(new ResourceLocation("abyssalcraft", "antimatter"), antiMatter_normal);
		registerPotionType(new ResourceLocation("abyssalcraft", "antimatter_long"), antiMatter_long);

		addBrewing(PotionTypes.AWKWARD, ACItems.coralium_plagued_flesh, Cplague_normal);
		addBrewing(PotionTypes.AWKWARD, ACItems.coralium_plagued_flesh_on_a_bone, Cplague_normal);
		addBrewing(Cplague_normal, Items.REDSTONE, Cplague_long);
		addBrewing(PotionTypes.AWKWARD, ACItems.dread_fragment, Dplague_normal);
		addBrewing(Dplague_normal, Items.REDSTONE, Dplague_long);
		addBrewing(Dplague_normal, Items.GLOWSTONE_DUST, Dplague_strong);

		AbyssalCraftAPI.STAFF_OF_RENDING = EnumHelper.addEnchantmentType("STAFF_OF_RENDING", i -> i instanceof IStaffOfRending);

		AbyssalCraftAPI.light_pierce = new EnchantmentLightPierce();
		AbyssalCraftAPI.iron_wall = new EnchantmentIronWall();
		AbyssalCraftAPI.sapping = new EnchantmentSapping();
		AbyssalCraftAPI.multi_rend = new EnchantmentMultiRend();
		AbyssalCraftAPI.blinding_light = new EnchantmentBlindingLight();

		registerEnchantment(new ResourceLocation("abyssalcraft", "light_pierce"), AbyssalCraftAPI.light_pierce);
		registerEnchantment(new ResourceLocation("abyssalcraft", "iron_wall"), AbyssalCraftAPI.iron_wall);
		registerEnchantment(new ResourceLocation("abyssalcraft", "sapping"), AbyssalCraftAPI.sapping);
		registerEnchantment(new ResourceLocation("abyssalcraft", "multi_rend"), AbyssalCraftAPI.multi_rend);
		registerEnchantment(new ResourceLocation("abyssalcraft", "blinding_light"), AbyssalCraftAPI.blinding_light);

		InitHandler.LIQUID_CORALIUM.setBlock(ACBlocks.liquid_coralium);
		InitHandler.LIQUID_ANTIMATTER.setBlock(ACBlocks.liquid_antimatter);
		if(AbyssalCraftAPI.liquid_coralium_fluid.getBlock() == null)
			AbyssalCraftAPI.liquid_coralium_fluid.setBlock(ACBlocks.liquid_coralium);
		if(AbyssalCraftAPI.liquid_antimatter_fluid.getBlock() == null)
			AbyssalCraftAPI.liquid_antimatter_fluid.setBlock(ACBlocks.liquid_antimatter);

		dreadguard_ambient = registerSoundEvent("dreadguard.idle");
		dreadguard_hurt = registerSoundEvent("dreadguard.hit");
		dreadguard_death = registerSoundEvent("dreadguard.death");
		ghoul_normal_ambient = registerSoundEvent("ghoul.normal.idle");
		ghoul_hurt = registerSoundEvent("ghoul.hit");
		ghoul_death = registerSoundEvent("ghoul.death");
		ghoul_pete_ambient = registerSoundEvent("ghoul.pete.idle");
		ghoul_wilson_ambient = registerSoundEvent("ghoul.wilson.idle");
		ghoul_orange_ambient = registerSoundEvent("ghoul.orange.idle");
		golem_death = registerSoundEvent("golem.death");
		golem_hurt = registerSoundEvent("golem.hit");
		golem_ambient = registerSoundEvent("golem.idle");
		sacthoth_death = registerSoundEvent("sacthoth.death");
		shadow_death = registerSoundEvent("shadow.death");
		shadow_hurt = registerSoundEvent("shadow.hit");
		remnant_scream = registerSoundEvent("remnant.scream");
		remnant_yes = registerSoundEvent("remnant.yes");
		remnant_no = registerSoundEvent("remnant.no");
		remnant_priest_chant = registerSoundEvent("remnant.priest.chant");
		shoggoth_ambient = registerSoundEvent("shoggoth.idle");
		shoggoth_hurt = registerSoundEvent("shoggoth.hit");
		shoggoth_death = registerSoundEvent("shoggoth.death");
		shoggoth_step = registerSoundEvent("shoggoth.step");
		shoggoth_shoot = registerSoundEvent("shoggoth.shoot");
		shoggoth_birth = registerSoundEvent("shoggoth.birth");
		shoggoth_consume = registerSoundEvent("shoggoth.consume");
		jzahar_charge = registerSoundEvent("jzahar.charge");
		cthulhu_chant = registerSoundEvent("chant.cthulhu");
		yog_sothoth_chant_1 = registerSoundEvent("chant.yog_sothoth_1");
		yog_sothoth_chant_2 = registerSoundEvent("chant.yog_sothoth_2");
		hastur_chant_1 = registerSoundEvent("chant.hastur_1");
		hastur_chant_2 = registerSoundEvent("chant.hastur_2");
		sleeping_chant = registerSoundEvent("chant.sleeping");
		cthugha_chant = registerSoundEvent("chant.cthugha");
		dread_spawn_ambient = registerSoundEvent("dreadspawn.idle");
		dread_spawn_hurt = registerSoundEvent("dreadspawn.hit");
		dread_spawn_death = registerSoundEvent("dreadspawn.death");
		abyssal_zombie_ambient = registerSoundEvent("abyssalzombie.idle");
		abyssal_zombie_hurt = registerSoundEvent("abyssalzombie.hit");
		abyssal_zombie_death = registerSoundEvent("abyssalzombie.death");
		antiplayer_hurt = registerSoundEvent("antiplayer.hurt");
		dreadguard_barf = registerSoundEvent("dreadguard.barf");
		jzahar_blast = registerSoundEvent("jzahar.blast");
		jzahar_shout = registerSoundEvent("jzahar.shout");
		jzahar_earthquake = registerSoundEvent("jzahar.earthquake");
		jzahar_implosion = registerSoundEvent("jzahar.implosion");
		jzahar_black_hole = registerSoundEvent("jzahar.black_hole");
		compass = registerSoundEvent("misc.compass");

		CapabilityManager.INSTANCE.register(INecroDataCapability.class, NecroDataCapabilityStorage.instance, NecroDataCapability::new);
		CapabilityManager.INSTANCE.register(IItemTransferCapability.class, ItemTransferCapabilityStorage.instance, ItemTransferCapability::new);

		ConditionProcessorRegistry.instance().registerProcessor(0, (condition, cap, player) -> cap.getBiomeTriggers().contains(condition.getConditionObject()));
		ConditionProcessorRegistry.instance().registerProcessor(1, (condition, cap, player) -> cap.getEntityTriggers().contains(condition.getConditionObject()));
		ConditionProcessorRegistry.instance().registerProcessor(2, (condition, cap, player) -> cap.getDimensionTriggers().contains(condition.getConditionObject()));
		ConditionProcessorRegistry.instance().registerProcessor(3, (condition, cap, player) -> {
			for(String name : (String[])condition.getConditionObject())
				if(cap.getBiomeTriggers().contains(name))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(4, (condition, cap, player) -> {
			for(String name : (String[])condition.getConditionObject())
				if(cap.getEntityTriggers().contains(name))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(5, (condition, cap, player) -> {
			for(String name : cap.getBiomeTriggers())
				if(((Predicate<Biome>)condition.getConditionObject()).apply(ForgeRegistries.BIOMES.getValue(new ResourceLocation(name))))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(6, (condition, cap, player) -> {
			for(String name : cap.getEntityTriggers())
				if(((Predicate<Class<? extends Entity>>)condition.getConditionObject()).apply(EntityList.getClass(new ResourceLocation(name))))
					return true;
			return false;
		});
		ConditionProcessorRegistry.instance().registerProcessor(7, (condition, cap, player) -> cap.getArtifactTriggers().contains(condition.getConditionObject()));
		ConditionProcessorRegistry.instance().registerProcessor(8, (condition, cap, player) -> cap.getPageTriggers().contains(condition.getConditionObject()));
		ConditionProcessorRegistry.instance().registerProcessor(9, (condition, cap, player) -> cap.getWhisperTriggers().contains(condition.getConditionObject()));
		ConditionProcessorRegistry.instance().registerProcessor(10, (condition, cap, player) -> cap.getMiscTriggers().contains(condition.getConditionObject()));
		ConditionProcessorRegistry.instance().registerProcessor(11, (condition, cap, player) -> {
			for(String name : (String[])condition.getConditionObject())
				if(!cap.getEntityTriggers().contains(name))
					return false;
			return true;
		});

		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		//Achievements
		//		//Depths Ghoul Achievements
		//		ACAchievements.kill_depths_ghoul = new Achievement("achievement.killghoul", "killghoul", -2, 0, ACItems.coralium_plagued_flesh_on_a_bone, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.depths_ghoul_head = new Achievement("achievement.ghoulhead", "ghoulhead", -4, 0, ACBlocks.depths_ghoul_head, ACAchievements.kill_depths_ghoul).registerStat();
		//		ACAchievements.pete_head = new Achievement("achievement.petehead", "petehead", -4, -2, ACBlocks.pete_head, ACAchievements.depths_ghoul_head).registerStat();
		//		ACAchievements.mr_wilson_head = new Achievement("achievement.wilsonhead", "wilsonhead", -4, -4, ACBlocks.mr_wilson_head, ACAchievements.pete_head).registerStat();
		//		ACAchievements.dr_orange_head = new Achievement("achievement.orangehead", "orangehead", -4, -6, ACBlocks.dr_orange_head, ACAchievements.mr_wilson_head).registerStat();
		//		//Necronomicon Achievements
		//		ACAchievements.abyssal_wasteland_necronomicon = new Achievement("achievement.necrou1", "necrou1", 2, 1, ACItems.abyssal_wasteland_necronomicon, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.dreadlands_necronomicon = new Achievement("achievement.necrou2", "necrou2", 4, 1, ACItems.dreadlands_necronomicon, ACAchievements.abyssal_wasteland_necronomicon).registerStat();
		//		ACAchievements.omothol_necronomicon = new Achievement("achievement.necrou3", "necrou3", 6, 1, ACItems.omothol_necronomicon, ACAchievements.dreadlands_necronomicon).registerStat();
		//		ACAchievements.abyssalnomicon = new Achievement("achievement.abyssaln", "abyssaln", 8, 1, ACItems.abyssalnomicon, ACAchievements.omothol_necronomicon).setSpecial().registerStat();
		//		//Ritual Achievements
		//		ACAchievements.ritual_altar = new Achievement("achievement.ritual", "ritual", -2, 1, ACBlocks.ritual_altar, ACAchievements.necronomicon).setSpecial().registerStat();
		//		ACAchievements.summoning_ritual = new Achievement("achievement.ritualSummon", "ritualSummon", -4, 1, ACBlocks.depths_ghoul_head, ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.creation_ritual = new Achievement("achievement.ritualCreate", "ritualCreate", -4, 2, ACItems.life_crystal, ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.breeding_ritual = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.EGG, ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.potion_ritual = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.aoe_potion_ritual = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.WATER), ACAchievements.ritual_altar).registerStat();
		//		ACAchievements.infusion_ritual = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, ACItems.depths_helmet, ACAchievements.ritual_altar).registerStat();
		//		//Progression Achievements
		//		ACAchievements.enter_abyssal_wasteland = new Achievement("achievement.enterabyss", "enterabyss", 0, 2, ACBlocks.abyssal_stone, ACAchievements.necronomicon).setSpecial().registerStat();
		//		ACAchievements.kill_spectral_dragon = new Achievement("achievement.killdragon", "killdragon", 2, 2, ACItems.coralium_plagued_flesh, ACAchievements.enter_abyssal_wasteland).registerStat();
		//		ACAchievements.summon_asorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 4, Altar, ACAchievements.enter_abyssal_wasteland).registerStat();
		//		ACAchievements.kill_asorah = new Achievement("achievement.killAsorah", "killAsorah", 2, 4, ACItems.eye_of_the_abyss, ACAchievements.summon_asorah).setSpecial().registerStat();
		//		ACAchievements.enter_dreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 2, 6, ACBlocks.dreadstone, ACAchievements.kill_asorah).setSpecial().registerStat();
		//		ACAchievements.kill_dreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 4, 6, ACItems.dreaded_shard_of_abyssalnite, ACAchievements.enter_dreadlands).registerStat();
		//		ACAchievements.summon_chagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 2, 8, ACBlocks.chagaroth_altar_bottom, ACAchievements.enter_dreadlands).registerStat();
		//		ACAchievements.kill_chagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 4, 8, ACItems.dread_plagued_gateway_key, ACAchievements.summon_chagaroth).setSpecial().registerStat();
		//		ACAchievements.enter_omothol = new Achievement("achievement.enterOmothol", "enterOmothol", 4, 10, ACBlocks.omothol_stone, ACAchievements.kill_chagaroth).setSpecial().registerStat();
		//		ACAchievements.enter_dark_realm = new Achievement("achievement.darkRealm", "darkRealm", 2, 10, ACBlocks.darkstone, ACAchievements.enter_omothol).registerStat();
		//		ACAchievements.kill_omothol_elite = new Achievement("achievement.killOmotholelite", "killOmotholelite", 6, 10, ACItems.eldritch_scale, ACAchievements.enter_omothol).registerStat();
		//		ACAchievements.locate_jzahar = new Achievement("achievement.locateJzahar", "locateJzahar", 4, 12, ACItems.jzahar_charm, ACAchievements.enter_omothol).registerStat();
		//		ACAchievements.kill_jzahar = new Achievement("achievement.killJzahar", "killJzahar", 6, 12, ACItems.staff_of_the_gatekeeper, ACAchievements.locate_jzahar).setSpecial().registerStat();
		//		//nowwhat
		//		//Gateway Key Achievements
		//		ACAchievements.gateway_key = new Achievement("achievement.GK1", "GK1", 0, -2, ACItems.gateway_key, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.find_powerstone = new Achievement("achievement.findPSDL", "findPSDL", -2, -2, ACBlocks.dreadlands_infused_powerstone, ACAchievements.gateway_key).registerStat();
		//		ACAchievements.dreaded_gateway_key = new Achievement("achievement.GK2", "GK2", 0, -4, ACItems.dreaded_gateway_key, ACAchievements.gateway_key).registerStat();
		//		ACAchievements.rlyehian_gateway_key = new Achievement("achievement.GK3", "GK3", 0, -6, ACItems.rlyehian_gateway_key, ACAchievements.dreaded_gateway_key).registerStat();
		//		//Machinery Achievements
		//		ACAchievements.make_transmutator = new Achievement("achievement.makeTransmutator", "makeTransmutator", 2, -1, ACBlocks.transmutator_idle, ACAchievements.necronomicon).registerStat();
		//		ACAchievements.make_crystallizer = new Achievement("achievement.makeCrystallizer", "makeCrystallizer", 4, -2, ACBlocks.crystallizer_idle, ACAchievements.make_transmutator).registerStat();
		//		ACAchievements.make_materializer = new Achievement("achievement.makeMaterializer", "makeMaterializer", 6, -2, ACBlocks.materializer, ACAchievements.make_crystallizer).registerStat();
		//		ACAchievements.make_crystal_bag = new Achievement("achievement.makeCrystalBag", "makeCrystalBag", 6, -4, ACItems.small_crystal_bag, ACAchievements.make_materializer).registerStat();
		//		ACAchievements.make_engraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, ACBlocks.engraver, AchievementList.OPEN_INVENTORY).registerStat();
		//
		//		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", ACAchievements.getAchievements()));

		//		RecipeSorter.register("abyssalcraft:shapednbt", ShapedNBTRecipe.class, Category.SHAPED, "after:minecraft:shaped");

		setUnlockConditions();

		AbyssalCrafting.addRecipes();
		AbyssalCraftAPI.addGhoulArmorTextures(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS, "abyssalcraft:textures/armor/ghoul/leather_1.png", "abyssalcraft:textures/armor/ghoul/leather_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS, "abyssalcraft:textures/armor/ghoul/chainmail_1.png", "abyssalcraft:textures/armor/ghoul/chainmail_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, "abyssalcraft:textures/armor/ghoul/iron_1.png", "abyssalcraft:textures/armor/ghoul/iron_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, "abyssalcraft:textures/armor/ghoul/gold_1.png", "abyssalcraft:textures/armor/ghoul/gold_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS, "abyssalcraft:textures/armor/ghoul/diamond_1.png", "abyssalcraft:textures/armor/ghoul/diamond_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/abyssalnite_1.png", "abyssalcraft:textures/armor/ghoul/abyssalnite_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots, "abyssalcraft:textures/armor/ghoul/coralium_1.png", "abyssalcraft:textures/armor/ghoul/coralium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots, "abyssalcraft:textures/armor/ghoul/dreadium_1.png", "abyssalcraft:textures/armor/ghoul/dreadium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.ethaxium_helmet, ACItems.ethaxium_chestplate, ACItems.ethaxium_leggings, ACItems.ethaxium_boots, "abyssalcraft:textures/armor/ghoul/ethaxium_1.png", "abyssalcraft:textures/armor/ghoul/ethaxium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreaded_abyssalnite_helmet, ACItems.dreaded_abyssalnite_chestplate, ACItems.dreaded_abyssalnite_leggings, ACItems.dreaded_abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/dread_1.png", "abyssalcraft:textures/armor/ghoul/dread_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.depths_helmet, ACItems.depths_chestplate, ACItems.depths_leggings, ACItems.depths_boots, "abyssalcraft:textures/armor/ghoul/depths_1.png", "abyssalcraft:textures/armor/ghoul/depths_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.plated_coralium_helmet, ACItems.plated_coralium_chestplate, ACItems.plated_coralium_leggings, ACItems.plated_coralium_boots, "abyssalcraft:textures/armor/ghoul/coraliump_1.png", "abyssalcraft:textures/armor/ghoul/coraliump_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_samurai_helmet, ACItems.dreadium_samurai_chestplate, ACItems.dreadium_samurai_leggings, ACItems.dreadium_samurai_boots, "abyssalcraft:textures/armor/ghoul/dreadiums_1.png", "abyssalcraft:textures/armor/ghoul/dreadiums_2.png");
		AbyssalCraftAPI.getInternalNDHandler().registerInternalPages();
		StructureHandler.instance().registerStructure(new BasicStructure());
		StructureHandler.instance().registerStructure(new TotemPoleStructure());
		StructureHandler.instance().registerStructure(new ArchwayStructure());
		ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(modid, 4);
		modFixs.registerFix(FixTypes.CHUNK, BlockFlatteningDefinitions.createBlockFlattening());
		ACTabs.tabTools.setRelevantEnchantmentTypes(AbyssalCraftAPI.STAFF_OF_RENDING);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		File folder = new File("config/abyssalcraft/");
		folder.mkdirs();
		Stack<File> folders = new Stack<>();
		folders.add(folder);
		while(!folders.isEmpty()){
			File dir = folders.pop();
			File[] listOfFiles = dir.listFiles();
			for(File file : listOfFiles != null ? listOfFiles : new File[0])
				if(file.isFile()){
					JsonObject json = NecroDataJsonUtil.readNecroDataJsonFromFile(file);
					if(json != null){
						NecroData nd = NecroDataJsonUtil.deserializeNecroData(json);
						int book = NecroDataJsonUtil.getInteger(json, "booktype");
						if(nd != null){
							ACLogger.info("Successfully deserialized JSON file for NecroData {}", nd.getIdentifier());
							AbyssalCraftAPI.registerNecronomiconData(nd, book);
						}
					}
				}
		}
	}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}

	static void addOreDictionaryStuff(){

		OreDictionary.registerOre("ingotAbyssalnite", ACItems.abyssalnite_ingot);
		OreDictionary.registerOre("ingotLiquifiedCoralium", ACItems.refined_coralium_ingot);
		OreDictionary.registerOre("gemCoralium", ACItems.coralium_gem);
		OreDictionary.registerOre("oreAbyssalnite", ACBlocks.abyssalnite_ore);
		OreDictionary.registerOre("oreCoralium", ACBlocks.coralium_ore);
		OreDictionary.registerOre("oreCoralium", ACBlocks.abyssal_coralium_ore);
		OreDictionary.registerOre("oreDreadedAbyssalnite", ACBlocks.dreaded_abyssalnite_ore);
		OreDictionary.registerOre("oreAbyssalnite", ACBlocks.dreadlands_abyssalnite_ore);
		OreDictionary.registerOre("oreCoraliumStone", ACBlocks.coralium_infused_stone);
		OreDictionary.registerOre("gemShadow", ACItems.shadow_gem);
		OreDictionary.registerOre("liquidCoralium", ACBlocks.liquid_coralium);
		OreDictionary.registerOre("materialCoraliumPearl", ACItems.coralium_pearl);
		OreDictionary.registerOre("liquidAntimatter", ACBlocks.liquid_antimatter);
		OreDictionary.registerOre("logWood", ACBlocks.darklands_oak_wood);
		OreDictionary.registerOre("logWood", ACBlocks.darklands_oak_wood_2);
		OreDictionary.registerOre("logWood", ACBlocks.dreadlands_log);
		OreDictionary.registerOre("plankWood", ACBlocks.darklands_oak_planks);
		OreDictionary.registerOre("plankWood", ACBlocks.dreadlands_planks);
		OreDictionary.registerOre("slabWood", ACBlocks.darklands_oak_slab);
		OreDictionary.registerOre("fenceWood", ACBlocks.darklands_oak_fence);
		OreDictionary.registerOre("fenceWood", ACBlocks.dreadlands_wood_fence);
		OreDictionary.registerOre("stairWood", ACBlocks.darklands_oak_stairs);
		OreDictionary.registerOre("doorWood", ACItems.darklands_oak_door);
		OreDictionary.registerOre("doorWood", ACItems.dreadlands_door);
		OreDictionary.registerOre("treeSapling", ACBlocks.darklands_oak_sapling);
		OreDictionary.registerOre("treeSapling", ACBlocks.dreadlands_sapling);
		OreDictionary.registerOre("treeLeaves", ACBlocks.darklands_oak_leaves);
		OreDictionary.registerOre("treeLeaves", ACBlocks.dreadlands_leaves);
		OreDictionary.registerOre("blockAbyssalnite", new ItemStack(ACBlocks.block_of_abyssalnite));
		OreDictionary.registerOre("blockLiquifiedCoralium", new ItemStack(ACBlocks.block_of_refined_coralium));
		OreDictionary.registerOre("blockDreadium", new ItemStack(ACBlocks.block_of_dreadium));
		OreDictionary.registerOre("ingotCoraliumBrick", ACItems.coralium_brick);
		OreDictionary.registerOre("ingotDreadium", ACItems.dreadium_ingot);
		OreDictionary.registerOre("dustSulfur", ACItems.sulfur);
		OreDictionary.registerOre("dustSaltpeter", ACItems.nitre);
		OreDictionary.registerOre("materialMethane", ACItems.methane);
		OreDictionary.registerOre("oreSaltpeter", ACBlocks.nitre_ore);
		OreDictionary.registerOre("oreIron", ACBlocks.abyssal_iron_ore);
		OreDictionary.registerOre("oreGold", ACBlocks.abyssal_gold_ore);
		OreDictionary.registerOre("oreDiamond", ACBlocks.abyssal_diamond_ore);
		OreDictionary.registerOre("oreSaltpeter", ACBlocks.abyssal_nitre_ore);
		OreDictionary.registerOre("oreTin", ACBlocks.abyssal_tin_ore);
		OreDictionary.registerOre("oreCopper", ACBlocks.abyssal_copper_ore);
		OreDictionary.registerOre("ingotTin", ACItems.tin_ingot);
		OreDictionary.registerOre("ingotCopper", ACItems.copper_ingot);
		OreDictionary.registerOre("orePearlescentCoralium", ACBlocks.pearlescent_coralium_ore);
		OreDictionary.registerOre("oreLiquifiedCoralium", ACBlocks.liquified_coralium_ore);
		OreDictionary.registerOre("ingotEthaxiumBrick", ACItems.ethaxium_brick);
		OreDictionary.registerOre("ingotEthaxium", ACItems.ethaxium_ingot);
		OreDictionary.registerOre("blockEthaxium", new ItemStack(ACBlocks.block_of_ethaxium));
		OreDictionary.registerOre("nuggetAbyssalnite", new ItemStack(ACItems.abyssalnite_nugget));
		OreDictionary.registerOre("nuggetLiquifiedCoralium", new ItemStack(ACItems.refined_coralium_nugget));
		OreDictionary.registerOre("nuggetDreadium", new ItemStack(ACItems.dreadium_nugget));
		OreDictionary.registerOre("nuggetEthaxium", new ItemStack(ACItems.ethaxium_nugget));
		OreDictionary.registerOre("blockGlass", ACBlocks.abyssal_sand_glass);
		OreDictionary.registerOre("coal", ACItems.charcoal);
		OreDictionary.registerOre("listAllmeatraw", ACItems.generic_meat);
		OreDictionary.registerOre("listAllmeatcooked", ACItems.cooked_generic_meat);

		InitHandler.INSTANCE.ITEMS.stream().filter(i -> i instanceof ItemCrystal).forEach(i -> {
			//"item.crystalType.Element" -> crystalTypeElement
			OreDictionary.registerOre(i.getTranslationKey().substring(5).replace(".", ""), new ItemStack(i));
		});

		InitHandler.INSTANCE.BLOCKS.stream().filter(b -> b instanceof BlockCrystalCluster).forEach(b -> {
			String name = Crystals.crystalNames[((BlockCrystalCluster)b).index];
			OreDictionary.registerOre("crystalCluster"+name, new ItemStack(b));
		});
	}

	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent event){
		if(!ACConfig.lootTableContent) return;
		if(event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.darkstone_axe, 3, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_axe"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_pickaxe, 3, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_pickaxe"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_shovel, 2, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_shovel"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_sword, 2, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_sword"));
				main.addEntry(new LootEntryItem(Item.getItemFromBlock(ACBlocks.darklands_oak_wood), 10, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":darklands_oak_wood"));
				main.addEntry(new LootEntryItem(Item.getItemFromBlock(ACBlocks.darklands_oak_wood_2), 10, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":darklands_oak_wood_2"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal_zinc, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":crystallized_zinc"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal_zinc, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.coralium_gem, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":coralium_gem"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal_zinc, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal_zinc, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal_zinc, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event){

		IForgeRegistry<IRecipe> reg = event.getRegistry();

		addShapedNBTRecipe(reg, rl("oblivion_deathbomb_0"), null, new ItemStack(ACBlocks.oblivion_deathbomb), "#%%", "&$%", "£%%", '#', ACItems.liquid_antimatter_bucket_stack, '£', ACItems.liquid_coralium_bucket_stack, '%', Blocks.OBSIDIAN, '&', ACItems.oblivion_catalyst, '$', ACBlocks.odb_core);
		addShapedNBTRecipe(reg, rl("oblivion_deathbomb_1"), null, new ItemStack(ACBlocks.oblivion_deathbomb), "#%%", "&$%", "£%%", '#', ACItems.liquid_coralium_bucket_stack, '£', ACItems.liquid_antimatter_bucket_stack, '%', Blocks.OBSIDIAN, '&', ACItems.oblivion_catalyst, '$', ACBlocks.odb_core);
		addShapedNBTRecipe(reg, rl("transmutator"), null, new ItemStack(ACBlocks.transmutator_idle, 1), "###", "#%#", "&$&", '#', ACItems.coralium_brick, '%', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), '&', new ItemStack(ACBlocks.block_of_refined_coralium), '$', ACItems.liquid_coralium_bucket_stack);
		addShapedNBTRecipe(reg, rl("materializer"), null, new ItemStack(ACBlocks.materializer), "###", "#%#", "&$&", '#', ACItems.ethaxium_brick, '%', Blocks.OBSIDIAN, '&', new ItemStack(ACBlocks.block_of_ethaxium), '$', ACItems.liquid_antimatter_bucket_stack);
	}

	private ResourceLocation rl(String name){
		return new ResourceLocation("abyssalcraft", name);
	}

	private void addShapedNBTRecipe(IForgeRegistry<IRecipe> reg, ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Object... params)
	{
		ShapedPrimer primer = CraftingHelper.parseShaped(params);
		reg.register(new ShapedNBTRecipe(group == null ? "" : group.toString(), primer.width, primer.height, primer.input, output).setRegistryName(name));
	}

	private void addDungeonHooks(){
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","abyssalzombie"), 150);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","depthsghoul"), 100);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","shadowcreature"), 120);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","shadowmonster"), 100);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","shadowbeast"), 30);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antiabyssalzombie"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antighoul"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antiskeleton"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antispider"), 50);
		DungeonHooks.addDungeonMob(new ResourceLocation("abyssalcraft","antizombie"), 50);
	}

	private void sendIMC(){
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.abyssal_wasteland_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.dreadlands_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.omothol_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityDryad|"+ String.valueOf(ACLib.dark_realm_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.abyssal_wasteland_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.dreadlands_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.omothol_id));
		FMLInterModComms.sendMessage("arsmagica2", "dbs", "am2.entities.EntityManaElemental|"+ String.valueOf(ACLib.dark_realm_id));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abyslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkstoneslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkcobbleslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Darkbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(DLTslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(abydreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(dreadbrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(cstonebrickslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ethaxiumslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.transmutator_active));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.crystallizer_active));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.oblivion_deathbomb));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.odb_core));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_wall));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_wood_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssalnite_stone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_button));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_pressure_plate));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(Altar));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.chagaroth_altar_top));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.chagaroth_altar_bottom));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_infused_powerstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.depths_ghoul_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.pete_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.mr_wilson_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dr_orange_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_sapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_sapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(house));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dark_ethaxium_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dark_ethaxium_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(darkethaxiumslab2));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dark_ethaxium_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_darkstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_abyssal_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_coralium_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_dreadstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_abyssalnite_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_ethaxium));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_dark_ethaxium));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_darkstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_abyssal_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_coralium_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_dreadstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_abyssalnite_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_ethaxium));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_dark_ethaxium));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.cthulhu_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.hastur_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.jzahar_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.azathoth_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.nyarlathotep_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.yog_sothoth_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.shub_niggurath_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.energy_pedestal));
		FMLInterModComms.sendMessage( "chiselsandbits", "ignoreblocklogic", ACBlocks.coralium_stone.getRegistryName());
		FMLInterModComms.sendMessage( "chiselsandbits", "ignoreblocklogic", ACBlocks.abyssal_sand.getRegistryName());
		FMLInterModComms.sendMessage( "chiselsandbits", "ignoreblocklogic", ACBlocks.abyssal_sand_glass.getRegistryName());
	}

	private static void registerPotion(ResourceLocation res, Potion pot){
		InitHandler.INSTANCE.POTIONS.add(pot.setRegistryName(res));
	}

	private static void registerEnchantment(ResourceLocation res, Enchantment ench){
		InitHandler.INSTANCE.ENCHANTMENTS.add(ench.setRegistryName(res));
	}

	private static void registerPotionType(ResourceLocation res, PotionType pot){
		InitHandler.INSTANCE.POTION_TYPES.add(pot.setRegistryName(res));
	}

	private static SoundEvent registerSoundEvent(String name){
		ResourceLocation res = new ResourceLocation(modid, name);
		SoundEvent evt = new SoundEvent(res).setRegistryName(res);
		InitHandler.INSTANCE.SOUND_EVENTS.add(evt);
		return evt;
	}

	private void addBrewing(PotionType input, Item ingredient, PotionType output){
		PotionHelper.addMix(input, ingredient, output);
	}

	private void setUnlockConditions() {

		//Items
		addCondition(ACItems.staff_of_the_gatekeeper, UnlockConditions.OMOTHOL);
		addCondition(ACItems.powerstone_tracker, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.eye_of_the_abyss, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadedlands_infused_gateway_key, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.coralium_brick, UnlockConditions.CORALIUM_BIOMES);
		addCondition(ACItems.cudgel, UnlockConditions.SKELETON_GOLIATH);
		addCondition(ACItems.carbon_cluster, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dense_carbon_cluster, UnlockConditions.DREADLANDS);
		addCondition(ACItems.omothol_forged_gateway_key, UnlockConditions.DREADLANDS);
		addCondition(ACItems.life_crystal, UnlockConditions.DREADLANDS);
		addCondition(ACItems.abyssal_shoggoth_flesh, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreaded_shoggoth_flesh, UnlockConditions.DREADLANDS);
		addCondition(ACItems.omothol_shoggoth_flesh, UnlockConditions.OMOTHOL);
		addCondition(ACItems.shadow_shoggoth_flesh, UnlockConditions.DARK_REALM);
		addCondition(ACItems.eldritch_scale, UnlockConditions.OMOTHOL);
		addCondition(ACItems.omothol_flesh, UnlockConditions.OMOTHOL_GHOUL);
		addCondition(ACItems.abyssal_wasteland_necronomicon, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadlands_necronomicon, UnlockConditions.DREADLANDS);
		addCondition(ACItems.omothol_necronomicon, UnlockConditions.OMOTHOL);
		addCondition(ACItems.abyssalnomicon, UnlockConditions.OMOTHOL);
		addCondition(ACItems.small_crystal_bag, UnlockConditions.DREADLANDS);
		addCondition(ACItems.medium_crystal_bag, UnlockConditions.DREADLANDS);
		addCondition(ACItems.large_crystal_bag, UnlockConditions.DREADLANDS);
		addCondition(ACItems.huge_crystal_bag, UnlockConditions.DREADLANDS);
		addCondition(ACItems.abyssalnite_nugget, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.refined_coralium_nugget, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadium_nugget, UnlockConditions.DREADLANDS);
		addCondition(ACItems.ethaxium_nugget, UnlockConditions.OMOTHOL);
		addCondition(ACItems.abyssal_wasteland_essence, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadlands_essence, UnlockConditions.DREADLANDS);
		addCondition(ACItems.omothol_essence, UnlockConditions.OMOTHOL);
		addCondition(ACItems.skin_of_the_abyssal_wasteland, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.skin_of_the_dreadlands, UnlockConditions.DREADLANDS);
		addCondition(ACItems.skin_of_omothol, UnlockConditions.OMOTHOL);
		addCondition(ACItems.essence_of_the_gatekeeper, UnlockConditions.OMOTHOL);
		addCondition(ACItems.interdimensional_cage, UnlockConditions.DREADLANDS);
		addCondition(ACItems.configurator_shard_0, UnlockConditions.OMOTHOL);
		addCondition(ACItems.configurator_shard_1, UnlockConditions.OMOTHOL);
		addCondition(ACItems.configurator_shard_2, UnlockConditions.OMOTHOL);
		addCondition(ACItems.configurator_shard_3, UnlockConditions.OMOTHOL);
		addCondition(ACItems.silver_key, UnlockConditions.OMOTHOL);
		addCondition(ACItems.coralium_plague_antidote, UnlockConditions.CORALIUM_PLAGUE);
		addCondition(ACItems.dread_plague_antidote, UnlockConditions.DREAD_PLAGUE);
		addCondition(ACItems.book_of_many_faces, UnlockConditions.DREADLANDS);
		addCondition(ACItems.coin, UnlockConditions.OMOTHOL);
		addCondition(ACItems.token_of_jzahar, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_brick, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_ingot, UnlockConditions.OMOTHOL);
		addCondition(ACItems.anti_beef, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_chicken, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_pork, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.rotten_anti_flesh, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_bone, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_spider_eye, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_plagued_flesh, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_plagued_flesh_on_a_bone, UnlockConditions.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.shadow_fragment, UnlockConditions.SHADOW_MOBS);
		addCondition(ACItems.shadow_shard, UnlockConditions.SHADOW_MOBS);
		addCondition(ACItems.shadow_gem, UnlockConditions.SHADOW_MOBS);
		addCondition(ACItems.shard_of_oblivion, UnlockConditions.SHADOW_MOBS);
		addCondition(ACItems.dreaded_shard_of_abyssalnite, UnlockConditions.ELITE_DREAD_MOB);
		addCondition(ACItems.dreaded_chunk_of_abyssalnite, UnlockConditions.DREADED_ABYSSALNITE_GOLEM);
		addCondition(ACItems.dreadium_ingot, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dread_fragment, UnlockConditions.DREAD_MOB);
		addCondition(ACItems.dread_cloth, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_plate, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_katana_blade, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dread_plagued_gateway_key, UnlockConditions.DREADLANDS);
		addCondition(ACItems.charcoal, UnlockConditions.DREADLANDS);
		addCondition(ACItems.chunk_of_abyssalnite, UnlockConditions.ABYSSALNITE_GOLEM);
		addCondition(ACItems.abyssalnite_ingot, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.chunk_of_coralium, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_ingot, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.coralium_plate, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.coralium_plagued_flesh, UnlockConditions.ABYSSAL_ZOMBIE);
		addCondition(ACItems.coralium_plagued_flesh_on_a_bone, UnlockConditions.DEPTHS_GHOUL);
		addCondition(ACItems.coralium_longbow, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.abyssalnite_pickaxe, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_axe, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_shovel, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_sword, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_hoe, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.refined_coralium_pickaxe, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_axe, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_shovel, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_sword, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_hoe, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadium_pickaxe, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_axe, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_shovel, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_sword, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_hoe, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_katana_hilt, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_katana, UnlockConditions.DREADLANDS);
		addCondition(ACItems.sacthoths_soul_harvesting_blade, UnlockConditions.SACTHOTH);
		addCondition(ACItems.ethaxium_pickaxe, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_axe, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_shovel, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_sword, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_hoe, UnlockConditions.OMOTHOL);
		addCondition(ACItems.staff_of_rending, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssal_wasteland_staff_of_rending, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.dreadlands_staff_of_rending, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.omothol_staff_of_rending, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.configurator, UnlockConditions.OMOTHOL);
		addCondition(ACItems.abyssalnite_helmet, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_chestplate, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_leggings, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_boots, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACItems.dreaded_abyssalnite_helmet, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreaded_abyssalnite_chestplate, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreaded_abyssalnite_leggings, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreaded_abyssalnite_boots, UnlockConditions.DREADLANDS);
		addCondition(ACItems.refined_coralium_helmet, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_chestplate, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_leggings, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_boots, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_helmet, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_chestplate, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_leggings, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_boots, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_helmet, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_chestplate, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_leggings, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_boots, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadium_helmet, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_chestplate, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_leggings, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_boots, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_helmet, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_chestplate, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_leggings, UnlockConditions.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_boots, UnlockConditions.DREADLANDS);
		addCondition(ACItems.ethaxium_helmet, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_chestplate, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_leggings, UnlockConditions.OMOTHOL);
		addCondition(ACItems.ethaxium_boots, UnlockConditions.OMOTHOL);
		InitHandler.INSTANCE.ITEMS.stream().filter(i -> i instanceof ICrystal).forEach(i->addCondition(i,UnlockConditions.DREADLANDS));

		//Blocks
		addCondition(ACBlocks.abyssal_stone_brick, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.chiseled_abyssal_stone_brick, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.cracked_abyssal_stone_brick, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_stone_brick_stairs, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssalnite_ore, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACBlocks.abyssal_stone_brick_fence, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.oblivion_deathbomb, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_infused_powerstone, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_coralium_ore, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_stone_button, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_stone_pressure_plate, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreaded_abyssalnite_ore, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadlands_abyssalnite_ore, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadstone_brick, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.chiseled_dreadstone_brick, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.cracked_dreadstone_brick, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssalnite_stone_brick, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.chiseled_abyssalnite_stone_brick, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.cracked_abyssalnite_stone_brick, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadlands_sapling, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadlands_log, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadlands_leaves, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadlands_planks, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.depths_ghoul_head, UnlockConditions.DEPTHS_GHOUL);
		addCondition(ACBlocks.dreadlands_grass, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.pete_head, UnlockConditions.DEPTHS_GHOUL);
		addCondition(ACBlocks.mr_wilson_head, UnlockConditions.DEPTHS_GHOUL);
		addCondition(ACBlocks.dr_orange_head, UnlockConditions.DEPTHS_GHOUL);
		addCondition(ACBlocks.dreadstone_brick_stairs, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadstone_brick_fence, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssalnite_stone_brick_stairs, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssalnite_stone_brick_fence, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.coralium_stone_brick, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.chiseled_coralium_stone_brick, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.cracked_coralium_stone_brick, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_brick_fence, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_brick_stairs, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_button, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_pressure_plate, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.chagaroth_altar_top, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.chagaroth_altar_bottom, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.crystallizer_idle, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.crystallizer_active, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.transmutator_idle, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.transmutator_active, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_wood_fence, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssal_iron_ore, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_gold_ore, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_diamond_ore, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.pearlescent_coralium_ore, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.liquified_coralium_ore, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.ethaxium_brick, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.chiseled_ethaxium_brick, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.cracked_ethaxium_brick, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.ethaxium_pillar, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.ethaxium_brick_stairs, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.ethaxium_brick_fence, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.materializer, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_brick, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.chiseled_dark_ethaxium_brick, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.cracked_dark_ethaxium_brick, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_pillar, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_brick_stairs, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_brick_fence, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.abyssal_sand, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.fused_abyssal_sand, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_sand_glass, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_dirt, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssal_cobblestone_stairs, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_cobblestone_wall, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadstone_cobblestone_stairs, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadstone_cobblestone_wall, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssalnite_cobblestone_stairs, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssalnite_cobblestone_wall, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.coralium_cobblestone_stairs, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_cobblestone_wall, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.luminous_thistle, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.wastelands_thorn, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.sequential_brewing_stand, UnlockConditions.NETHER);
		addCondition(ACBlocks.abyssal_stone, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_cobblestone, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_cobblestone, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadstone, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssalnite_stone, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.dreadstone_cobblestone, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.abyssalnite_cobblestone, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.ethaxium, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.omothol_stone, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.block_of_abyssalnite, UnlockConditions.DARKLANDS_BIOME);
		addCondition(ACBlocks.block_of_refined_coralium, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.block_of_dreadium, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.block_of_ethaxium, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_pedestal, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_pedestal, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_pedestal, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_sacrificial_altar, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_sacrificial_altar, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.omothol_sacrificial_altar, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_collector, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_collector, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_collector, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_relay, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_relay, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_relay, UnlockConditions.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_container, UnlockConditions.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_container, UnlockConditions.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_container, UnlockConditions.OMOTHOL);
	}

	private void addCondition(Block block, IUnlockCondition condition) {
		addCondition(Item.getItemFromBlock(block), condition);
	}

	private void addCondition(Item item, IUnlockCondition condition){
		((IUnlockableItem) item).setUnlockCondition(condition);
	}
}
