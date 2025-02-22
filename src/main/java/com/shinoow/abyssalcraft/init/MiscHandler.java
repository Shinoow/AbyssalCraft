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
import com.shinoow.abyssalcraft.api.armor.ArmorData;
import com.shinoow.abyssalcraft.api.armor.ArmorDataRegistry;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.structure.StructureHandler;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.IResearchable;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;
import com.shinoow.abyssalcraft.api.knowledge.condition.ConditionProcessorRegistry;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapabilityStorage;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
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
import com.shinoow.abyssalcraft.common.util.ShapedFluidContainerRecipe;
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
import net.minecraft.item.ItemArmor.ArmorMaterial;
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

		setResearchItems();

		AbyssalCrafting.addRecipes();

		registerCustomArmors();

		AbyssalCraftAPI.getInternalNDHandler().registerInternalPages();
		StructureHandler.instance().registerStructure(new BasicStructure());
		StructureHandler.instance().registerStructure(new TotemPoleStructure());
		StructureHandler.instance().registerStructure(new ArchwayStructure());
		// Update getFixVersion in BlockFlattening too!!!
		ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(modid, 5);
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
		OreDictionary.registerOre("logWood", ACBlocks.dreadwood_log);
		OreDictionary.registerOre("plankWood", ACBlocks.darklands_oak_planks);
		OreDictionary.registerOre("plankWood", ACBlocks.dreadwood_planks);
		OreDictionary.registerOre("slabWood", ACBlocks.darklands_oak_slab);
		OreDictionary.registerOre("fenceWood", ACBlocks.darklands_oak_fence);
		OreDictionary.registerOre("fenceWood", ACBlocks.dreadwood_fence);
		OreDictionary.registerOre("stairWood", ACBlocks.darklands_oak_stairs);
		OreDictionary.registerOre("doorWood", ACItems.darklands_oak_door);
		OreDictionary.registerOre("doorWood", ACItems.dreadlands_door);
		OreDictionary.registerOre("treeSapling", ACBlocks.darklands_oak_sapling);
		OreDictionary.registerOre("treeSapling", ACBlocks.dreadwood_sapling);
		OreDictionary.registerOre("treeLeaves", ACBlocks.darklands_oak_leaves);
		OreDictionary.registerOre("treeLeaves", ACBlocks.dreadwood_leaves);
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
		OreDictionary.registerOre("logWood", ACBlocks.dead_tree_log);
		OreDictionary.registerOre("oreAbyssalnite", ACBlocks.abyssal_abyssalnite_ore);

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

		addShapedFluidContainerRecipe(reg, rl("oblivion_deathbomb_0"), null, new ItemStack(ACBlocks.oblivion_deathbomb), "#%%", "&$%", "£%%", '#', ACItems.liquid_antimatter_bucket_stack, '£', ACItems.liquid_coralium_bucket_stack, '%', Blocks.OBSIDIAN, '&', ACItems.oblivion_catalyst, '$', ACBlocks.odb_core);
		addShapedFluidContainerRecipe(reg, rl("oblivion_deathbomb_1"), null, new ItemStack(ACBlocks.oblivion_deathbomb), "#%%", "&$%", "£%%", '#', ACItems.liquid_coralium_bucket_stack, '£', ACItems.liquid_antimatter_bucket_stack, '%', Blocks.OBSIDIAN, '&', ACItems.oblivion_catalyst, '$', ACBlocks.odb_core);
		addShapedFluidContainerRecipe(reg, rl("transmutator"), null, new ItemStack(ACBlocks.transmutator_idle, 1), "###", "#%#", "&$&", '#', ACItems.coralium_brick, '%', new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), '&', new ItemStack(ACBlocks.block_of_refined_coralium), '$', ACItems.liquid_coralium_bucket_stack);
		addShapedFluidContainerRecipe(reg, rl("materializer"), null, new ItemStack(ACBlocks.materializer), "###", "#%#", "&$&", '#', ACItems.ethaxium_brick, '%', Blocks.OBSIDIAN, '&', new ItemStack(ACBlocks.block_of_ethaxium), '$', ACItems.liquid_antimatter_bucket_stack);
	}

	private ResourceLocation rl(String name){
		return new ResourceLocation("abyssalcraft", name);
	}

	private void addShapedFluidContainerRecipe(IForgeRegistry<IRecipe> reg, ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Object... params)
	{
		ShapedPrimer primer = CraftingHelper.parseShaped(params);
		reg.register(new ShapedFluidContainerRecipe(group == null ? "" : group.toString(), primer.width, primer.height, primer.input, output).setRegistryName(name));
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.elysian_stone_brick_slab));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(elysianbrickslab2));
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.elysian_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadstone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_stone_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ethaxium_brick_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadwood_fence));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_stone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_brick_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darkstone_cobblestone_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_stairs));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.elysian_stone_brick_stairs));
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.sealing_lock));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.unlocked_sealing_lock));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadlands_infused_powerstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.depths_ghoul_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.pete_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.mr_wilson_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dr_orange_head));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.darklands_oak_sapling));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreadwood_sapling));
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_elysian_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_ethaxium));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar_dark_ethaxium));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_darkstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_abyssal_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_coralium_stone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_dreadstone));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal_elysian_stone));
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

	private void setResearchItems() {

		//Items
		addCondition(ACItems.staff_of_the_gatekeeper, ResearchItems.OMOTHOL);
		addCondition(ACItems.powerstone_tracker, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.eye_of_the_abyss, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadlands_infused_gateway_key, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.coralium_brick, ResearchItems.CORALIUM_BIOMES);
		addCondition(ACItems.cudgel, ResearchItems.SKELETON_GOLIATH);
		addCondition(ACItems.carbon_cluster, ResearchItems.DREADLANDS);
		addCondition(ACItems.dense_carbon_cluster, ResearchItems.DREADLANDS);
		addCondition(ACItems.omothol_forged_gateway_key, ResearchItems.DREADLANDS);
		addCondition(ACItems.life_crystal, ResearchItems.DREADLANDS);
		addCondition(ACItems.abyssal_shoggoth_flesh, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreaded_shoggoth_flesh, ResearchItems.DREADLANDS);
		addCondition(ACItems.omothol_shoggoth_flesh, ResearchItems.OMOTHOL);
		addCondition(ACItems.shadow_shoggoth_flesh, ResearchItems.DARK_REALM);
		addCondition(ACItems.eldritch_scale, ResearchItems.OMOTHOL);
		addCondition(ACItems.omothol_flesh, ResearchItems.OMOTHOL_GHOUL);
		addCondition(ACItems.abyssal_wasteland_necronomicon, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadlands_necronomicon, ResearchItems.DREADLANDS);
		addCondition(ACItems.omothol_necronomicon, ResearchItems.OMOTHOL);
		addCondition(ACItems.abyssalnomicon, ResearchItems.OMOTHOL);
		addCondition(ACItems.small_crystal_bag, ResearchItems.DREADLANDS);
		addCondition(ACItems.medium_crystal_bag, ResearchItems.DREADLANDS);
		addCondition(ACItems.large_crystal_bag, ResearchItems.DREADLANDS);
		addCondition(ACItems.huge_crystal_bag, ResearchItems.DREADLANDS);
		addCondition(ACItems.abyssalnite_nugget, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.refined_coralium_nugget, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadium_nugget, ResearchItems.DREADLANDS);
		addCondition(ACItems.ethaxium_nugget, ResearchItems.OMOTHOL);
		addCondition(ACItems.abyssal_wasteland_essence, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadlands_essence, ResearchItems.DREADLANDS);
		addCondition(ACItems.omothol_essence, ResearchItems.OMOTHOL);
		addCondition(ACItems.skin_of_the_abyssal_wasteland, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.skin_of_the_dreadlands, ResearchItems.DREADLANDS);
		addCondition(ACItems.skin_of_omothol, ResearchItems.OMOTHOL);
		addCondition(ACItems.essence_of_the_gatekeeper, ResearchItems.OMOTHOL);
		addCondition(ACItems.interdimensional_cage, ResearchItems.DREADLANDS);
		addCondition(ACItems.configurator_shard_0, ResearchItems.OMOTHOL);
		addCondition(ACItems.configurator_shard_1, ResearchItems.OMOTHOL);
		addCondition(ACItems.configurator_shard_2, ResearchItems.OMOTHOL);
		addCondition(ACItems.configurator_shard_3, ResearchItems.OMOTHOL);
		addCondition(ACItems.silver_key, ResearchItems.OMOTHOL);
		addCondition(ACItems.coralium_plague_antidote, ResearchItems.CORALIUM_PLAGUE);
		addCondition(ACItems.dread_plague_antidote, ResearchItems.DREAD_PLAGUE);
		addCondition(ACItems.book_of_many_faces, ResearchItems.DREADLANDS);
		addCondition(ACItems.coin, ResearchItems.OMOTHOL);
		addCondition(ACItems.token_of_jzahar, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_brick, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_ingot, ResearchItems.OMOTHOL);
		addCondition(ACItems.anti_beef, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_chicken, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_pork, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.rotten_anti_flesh, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_bone, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_spider_eye, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_plagued_flesh, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.anti_plagued_flesh_on_a_bone, ResearchItems.CORALIUM_INFESTED_SWAMP);
		addCondition(ACItems.shadow_fragment, ResearchItems.SHADOW_MOBS);
		addCondition(ACItems.shadow_shard, ResearchItems.SHADOW_MOBS);
		addCondition(ACItems.shadow_gem, ResearchItems.SHADOW_MOBS);
		addCondition(ACItems.shard_of_oblivion, ResearchItems.SHADOW_MOBS);
		addCondition(ACItems.dreaded_shard_of_abyssalnite, ResearchItems.ELITE_DREAD_MOB);
		addCondition(ACItems.dreaded_chunk_of_abyssalnite, ResearchItems.DREADED_ABYSSALNITE_GOLEM);
		addCondition(ACItems.dreadium_ingot, ResearchItems.DREADLANDS);
		addCondition(ACItems.dread_fragment, ResearchItems.DREAD_MOB);
		addCondition(ACItems.dread_cloth, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_plate, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_katana_blade, ResearchItems.DREADLANDS);
		addCondition(ACItems.dread_plagued_gateway_key, ResearchItems.DREADLANDS);
		addCondition(ACItems.charcoal, ResearchItems.DREADLANDS);
		addCondition(ACItems.chunk_of_abyssalnite, ResearchItems.ABYSSALNITE_GOLEM);
		addCondition(ACItems.abyssalnite_ingot, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.chunk_of_coralium, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_ingot, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.coralium_plate, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.coralium_plagued_flesh, ResearchItems.ABYSSAL_ZOMBIE);
		addCondition(ACItems.coralium_plagued_flesh_on_a_bone, ResearchItems.DEPTHS_GHOUL);
		addCondition(ACItems.coralium_longbow, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.abyssalnite_pickaxe, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_axe, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_shovel, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_sword, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_hoe, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.refined_coralium_pickaxe, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_axe, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_shovel, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_sword, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_hoe, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadium_pickaxe, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_axe, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_shovel, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_sword, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_hoe, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_katana_hilt, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_katana, ResearchItems.DREADLANDS);
		addCondition(ACItems.sacthoths_soul_harvesting_blade, ResearchItems.SACTHOTH);
		addCondition(ACItems.ethaxium_pickaxe, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_axe, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_shovel, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_sword, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_hoe, ResearchItems.OMOTHOL);
		addCondition(ACItems.staff_of_rending, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssal_wasteland_staff_of_rending, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.dreadlands_staff_of_rending, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.omothol_staff_of_rending, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.configurator, ResearchItems.OMOTHOL);
		addCondition(ACItems.abyssalnite_helmet, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_chestplate, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_leggings, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.abyssalnite_boots, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACItems.dreaded_abyssalnite_helmet, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreaded_abyssalnite_chestplate, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreaded_abyssalnite_leggings, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreaded_abyssalnite_boots, ResearchItems.DREADLANDS);
		addCondition(ACItems.refined_coralium_helmet, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_chestplate, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_leggings, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.refined_coralium_boots, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_helmet, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_chestplate, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_leggings, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.plated_coralium_boots, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_helmet, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_chestplate, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_leggings, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.depths_boots, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACItems.dreadium_helmet, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_chestplate, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_leggings, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_boots, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_helmet, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_chestplate, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_leggings, ResearchItems.DREADLANDS);
		addCondition(ACItems.dreadium_samurai_boots, ResearchItems.DREADLANDS);
		addCondition(ACItems.ethaxium_helmet, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_chestplate, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_leggings, ResearchItems.OMOTHOL);
		addCondition(ACItems.ethaxium_boots, ResearchItems.OMOTHOL);
		InitHandler.INSTANCE.ITEMS.stream().filter(i -> i instanceof ICrystal).forEach(i->addCondition(i,ResearchItems.DREADLANDS));
		addCondition(ACItems.sealing_key, ResearchItems.DREADLANDS);

		//Blocks
		addCondition(ACBlocks.abyssal_stone_brick, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.chiseled_abyssal_stone_brick, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.cracked_abyssal_stone_brick, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_stone_brick_stairs, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssalnite_ore, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACBlocks.abyssal_stone_brick_fence, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.oblivion_deathbomb, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_infused_powerstone, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_coralium_ore, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_stone_button, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_stone_pressure_plate, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreaded_abyssalnite_ore, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadlands_abyssalnite_ore, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadstone_brick, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.chiseled_dreadstone_brick, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.cracked_dreadstone_brick, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.elysian_stone_brick, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.chiseled_elysian_stone_brick, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.cracked_elysian_stone_brick, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_sapling, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_log, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_leaves, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_planks, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_stairs, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_button, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_pressure_plate, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadwood_fence_gate, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.depths_ghoul_head, ResearchItems.DEPTHS_GHOUL);
		addCondition(ACBlocks.dreadlands_grass, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.pete_head, ResearchItems.DEPTHS_GHOUL);
		addCondition(ACBlocks.mr_wilson_head, ResearchItems.DEPTHS_GHOUL);
		addCondition(ACBlocks.dr_orange_head, ResearchItems.DEPTHS_GHOUL);
		addCondition(ACBlocks.dreadstone_brick_stairs, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadstone_brick_fence, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.elysian_stone_brick_stairs, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.elysian_stone_brick_fence, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.coralium_stone_brick, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.chiseled_coralium_stone_brick, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.cracked_coralium_stone_brick, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_brick_fence, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_brick_stairs, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_button, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone_pressure_plate, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.sealing_lock, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.unlocked_sealing_lock, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.crystallizer_idle, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.crystallizer_active, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.transmutator_idle, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.transmutator_active, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadwood_fence, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.abyssal_iron_ore, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_gold_ore, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_diamond_ore, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.pearlescent_coralium_ore, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.liquified_coralium_ore, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.ethaxium_brick, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.chiseled_ethaxium_brick, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.cracked_ethaxium_brick, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.ethaxium_pillar, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.ethaxium_brick_stairs, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.ethaxium_brick_fence, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.materializer, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_brick, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.chiseled_dark_ethaxium_brick, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.cracked_dark_ethaxium_brick, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_pillar, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_brick_stairs, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.dark_ethaxium_brick_fence, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.abyssal_sand, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.fused_abyssal_sand, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_sand_glass, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_dirt, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.abyssal_cobblestone_stairs, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_cobblestone_wall, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadstone_cobblestone_stairs, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadstone_cobblestone_wall, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.elysian_cobblestone_stairs, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.elysian_cobblestone_wall, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.coralium_cobblestone_stairs, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_cobblestone_wall, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.luminous_thistle, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.wastelands_thorn, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.sequential_brewing_stand, ResearchItems.NETHER);
		addCondition(ACBlocks.abyssal_stone, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.abyssal_cobblestone, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_stone, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.coralium_cobblestone, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadstone, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.elysian_stone, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.dreadstone_cobblestone, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.elysian_cobblestone, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.ethaxium, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.omothol_stone, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.block_of_abyssalnite, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACBlocks.block_of_refined_coralium, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.block_of_dreadium, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.block_of_ethaxium, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_pedestal, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_pedestal, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_pedestal, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_sacrificial_altar, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_sacrificial_altar, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.omothol_sacrificial_altar, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_collector, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_collector, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_collector, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_relay, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_relay, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_relay, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.abyssal_wasteland_energy_container, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.dreadlands_energy_container, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.omothol_energy_container, ResearchItems.OMOTHOL);
		addCondition(ACBlocks.abyssal_abyssalnite_ore, ResearchItems.DARKLANDS_BIOME);
		addCondition(ACBlocks.oblivion_deathbomb, ResearchItems.ABYSSAL_WASTELAND);
		addCondition(ACBlocks.sealing_lock, ResearchItems.DREADLANDS);
		addCondition(ACBlocks.unlocked_sealing_lock, ResearchItems.DREADLANDS);
		//TODO Lock all slab blocks
	}

	private void addCondition(Block block, IResearchItem condition) {
		addCondition(Item.getItemFromBlock(block), condition);
	}

	private void addCondition(Item item, IResearchItem condition){
		((IResearchable) item).setResearchItem(condition);
	}

	private void registerCustomArmors() {
		ArmorDataRegistry.instance().registerGhoulData(ArmorMaterial.CHAIN, new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/ghoul/chainmail_1.png"), new ResourceLocation("abyssalcraft:textures/armor/ghoul/chainmail_2.png")));
		ArmorDataRegistry.instance().registerGhoulData(ArmorMaterial.LEATHER, new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/ghoul/leather_1.png"), new ResourceLocation("abyssalcraft:textures/armor/ghoul/leather_2.png"), true));
		ArmorDataRegistry.instance().registerGhoulData(ArmorMaterial.IRON, new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/ghoul/base_1.png"), new ResourceLocation("abyssalcraft:textures/armor/ghoul/base_2.png")));

		ArmorDataRegistry.instance().registerSkeletonGoliathData(ArmorMaterial.CHAIN, new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/chainmail_1.png"), new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/chainmail_2.png")));
		ArmorDataRegistry.instance().registerSkeletonGoliathData(ArmorMaterial.LEATHER, new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/leather_1.png"), new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/leather_2.png"), true));
		ArmorDataRegistry.instance().registerSkeletonGoliathData(ArmorMaterial.IRON, new ArmorData(new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_1.png"), new ResourceLocation("abyssalcraft:textures/armor/skeleton_goliath/base_2.png")));

		ArmorDataRegistry.instance().registerColor(ArmorMaterial.GOLD, ACClientVars.getCrystalColors()[Crystals.GOLD]);
		ArmorDataRegistry.instance().registerColor(ArmorMaterial.DIAMOND, 0x4bfbea);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.dreadiumArmor, ACClientVars.getCrystalColors()[Crystals.DREADIUM]);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.dreadiumSamuraiArmor, ACClientVars.getCrystalColors()[Crystals.DREADIUM]);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.abyssalniteArmor, ACClientVars.getCrystalColors()[Crystals.ABYSSALNITE]);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.dreadedAbyssalniteArmor, ACClientVars.getCrystalColors()[Crystals.ABYSSALNITE]);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.refinedCoraliumArmor, 0x067047);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.platedCoraliumArmor, 0x067047);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.depthsArmor, 0x067047);
		ArmorDataRegistry.instance().registerColor(AbyssalCraftAPI.ethaxiumArmor, 0xadc3ac);
	}
}
