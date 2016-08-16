/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.AbyssalCraft.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.*;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.enchantments.*;
import com.shinoow.abyssalcraft.common.handlers.*;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.potion.*;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;

public class MiscHandler implements ILifeCycleHandler {

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		AbyssalCraftAPI.coralium_plague = new PotionCplague(new ResourceLocation("abyssalcraft", "Cplague"), true, 0x00FFFF).setIconIndex(1, 0).setPotionName("potion.Cplague");
		AbyssalCraftAPI.dread_plague = new PotionDplague(new ResourceLocation("abyssalcraft", "Dplague"), true, 0xAD1313).setIconIndex(1, 0).setPotionName("potion.Dplague");
		AbyssalCraftAPI.antimatter_potion = new PotionAntimatter(new ResourceLocation("abyssalcraft", "Antimatter"), true, 0xFFFFFF).setIconIndex(1, 0).setPotionName("potion.Antimatter");

		AbyssalCraftAPI.coralium_enchantment = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "coralium");
		AbyssalCraftAPI.dread_enchantment = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "dread");
		AbyssalCraftAPI.light_pierce = new EnchantmentLightPierce(getNextAvailableEnchantmentId());
		AbyssalCraftAPI.iron_wall = new EnchantmentIronWall(getNextAvailableEnchantmentId(), 2);

		Enchantment.addToBookList(AbyssalCraftAPI.coralium_enchantment);
		Enchantment.addToBookList(AbyssalCraftAPI.dread_enchantment);
		Enchantment.addToBookList(AbyssalCraftAPI.light_pierce);
		Enchantment.addToBookList(AbyssalCraftAPI.iron_wall);

		LIQUID_CORALIUM.setBlock(ACBlocks.liquid_coralium);
		LIQUID_ANTIMATTER.setBlock(ACBlocks.liquid_antimatter);
		if(CFluid.getBlock() == null)
			CFluid.setBlock(ACBlocks.liquid_coralium);
		if(antifluid.getBlock() == null)
			antifluid.setBlock(ACBlocks.liquid_antimatter);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(CFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_coralium_bucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_coralium.getDefaultState(), ACItems.liquid_coralium_bucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(antifluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_antimatter_bucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_antimatter.getDefaultState(), ACItems.liquid_antimatter_bucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		RitualUtil.addBlocks();
		addOreDictionaryStuff();
		addChestGenHooks();
		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
	}

	@Override
	public void init(FMLInitializationEvent event) {

		necro = new Achievement("achievement.necro", "necro", 0, 0, ACItems.necronomicon, AchievementList.openInventory).registerStat();
		//Materials Achievements
		mineAby = new Achievement("achievement.mineAby", "mineAby", 2, 0, ACBlocks.abyssalnite_ore, necro).registerStat();
		mineCorgem = new Achievement("achievement.mineCorgem", "mineCorgem", 4, 0, ACItems.coralium_gem, mineAby).registerStat();
		shadowGems = new Achievement("achievement.shadowGems", "shadowGems", 6, 0, ACItems.shadow_gem, mineCorgem).registerStat();
		//coraliumpearl
		mineCor = new Achievement("achievement.mineCor", "mineCor", 8, 0, ACBlocks.liquified_coralium_ore, shadowGems).registerStat();
		mineAbyOres = new Achievement("achievement.mineAbyOres", "mineAbyOres", 10, 0, ACBlocks.abyssal_diamond_ore, mineCor).registerStat();
		mineDread = new Achievement("achievement.mineDread", "mineDread", 12, 0, ACBlocks.dreaded_abyssalnite_ore, mineAbyOres).registerStat();
		dreadium = new Achievement("achievement.dreadium", "dreadium", 14, 0, ACItems.dreadium_ingot, mineDread).registerStat();
		eth = new Achievement("achievement.ethaxium", "ethaxium", 16, 0, ACItems.ethaxium_ingot, dreadium).setSpecial().registerStat();
		//Depths Ghoul Achievements
		killghoul = new Achievement("achievement.killghoul", "killghoul", -2, 0, ACItems.coralium_plagued_flesh_on_a_bone, necro).registerStat();
		ghoulhead = new Achievement("achievement.ghoulhead", "ghoulhead", -4, 0, ACBlocks.depths_ghoul_head, killghoul).registerStat();
		petehead = new Achievement("achievement.petehead", "petehead", -4, -2, ACBlocks.pete_head, ghoulhead).registerStat();
		wilsonhead = new Achievement("achievement.wilsonhead", "wilsonhead", -4, -4, ACBlocks.mr_wilson_head, petehead).registerStat();
		orangehead = new Achievement("achievement.orangehead", "orangehead", -4, -6, ACBlocks.dr_orange_head, wilsonhead).registerStat();
		//Necronomicon Achievements
		necrou1 = new Achievement("achievement.necrou1", "necrou1", 2, 1, ACItems.abyssal_wasteland_necronomicon, necro).registerStat();
		necrou2 = new Achievement("achievement.necrou2", "necrou2", 4, 1, ACItems.dreadlands_necronomicon, necrou1).registerStat();
		necrou3 = new Achievement("achievement.necrou3", "necrou3", 6, 1, ACItems.omothol_necronomicon, necrou2).registerStat();
		abyssaln = new Achievement("achievement.abyssaln", "abyssaln", 8, 1, ACItems.abyssalnomicon, necrou3).setSpecial().registerStat();
		//Ritual Achievements
		ritual = new Achievement("achievement.ritual", "ritual", -2, 1, ACBlocks.ritual_altar, necro).setSpecial().registerStat();
		ritualSummon = new Achievement("achievement.ritualSummon", "ritualSummon", -4, 1, ACBlocks.depths_ghoul_head, ritual).registerStat();
		ritualCreate = new Achievement("achievement.ritualCreate", "ritualCreate", -4, 2, ACItems.life_crystal, ritual).registerStat();
		ritualBreed = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.egg, ritual).registerStat();
		ritualPotion = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, Items.potionitem, ritual).registerStat();
		ritualPotionAoE = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, new ItemStack(Items.potionitem, 1, 16384), ritual).registerStat();
		ritualInfusion = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, ACItems.depths_helmet, ritual).registerStat();
		shoggothInfestation = new Achievement("achievement.shoggothInfestation", "shoggothInfestation", -6, 3, Items.skull, ritualBreed).registerStat();
		//Progression Achievements
		enterabyss = new Achievement("achievement.enterabyss", "enterabyss", 0, 2, ACBlocks.abyssal_stone, necro).setSpecial().registerStat();
		killdragon = new Achievement("achievement.killdragon", "killdragon", 2, 2, ACItems.coralium_plagued_flesh, enterabyss).registerStat();
		summonAsorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 4, Altar, enterabyss).registerStat();
		killAsorah = new Achievement("achievement.killAsorah", "killAsorah", 2, 4, ACItems.eye_of_the_abyss, summonAsorah).setSpecial().registerStat();
		enterdreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 2, 6, ACBlocks.dreadstone, killAsorah).setSpecial().registerStat();
		killdreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 4, 6, ACItems.dreaded_shard_of_abyssalnite, enterdreadlands).registerStat();
		summonChagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 2, 8, ACBlocks.chagaroth_altar_bottom, enterdreadlands).registerStat();
		killChagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 4, 8, ACItems.dread_plagued_gateway_key, summonChagaroth).setSpecial().registerStat();
		enterOmothol = new Achievement("achievement.enterOmothol", "enterOmothol", 4, 10, ACBlocks.omothol_stone, killChagaroth).setSpecial().registerStat();
		enterDarkRealm = new Achievement("achievement.darkRealm", "darkRealm", 2, 10, ACBlocks.darkstone, enterOmothol).registerStat();
		killOmotholelite = new Achievement("achievement.killOmotholelite", "killOmotholelite", 6, 10, ACItems.eldritch_scale, enterOmothol).registerStat();
		locateJzahar = new Achievement("achievement.locateJzahar", "locateJzahar", 4, 12, ACItems.jzahar_charm, enterOmothol).registerStat();
		killJzahar = new Achievement("achievement.killJzahar", "killJzahar", 6, 12, ACItems.staff_of_the_gatekeeper, locateJzahar).setSpecial().registerStat();
		//nowwhat
		//Gateway Key Achievements
		GK1 = new Achievement("achievement.GK1", "GK1", 0, -2, ACItems.gateway_key, necro).registerStat();
		findPSDL = new Achievement("achievement.findPSDL", "findPSDL", -2, -2, ACBlocks.dreadlands_infused_powerstone, GK1).registerStat();
		GK2 = new Achievement("achievement.GK2", "GK2", 0, -4, ACItems.dreaded_gateway_key, GK1).registerStat();
		GK3 = new Achievement("achievement.GK3", "GK3", 0, -6, ACItems.rlyehian_gateway_key, GK2).registerStat();
		//Machinery Achievements
		makeTransmutator = new Achievement("achievement.makeTransmutator", "makeTransmutator", 2, -1, ACBlocks.transmutator_idle, necro).registerStat();
		makeCrystallizer = new Achievement("achievement.makeCrystallizer", "makeCrystallizer", 4, -2, ACBlocks.crystallizer_idle, makeTransmutator).registerStat();
		makeMaterializer = new Achievement("achievement.makeMaterializer", "makeMaterializer", 6, -2, ACBlocks.materializer, makeCrystallizer).registerStat();
		makeCrystalBag = new Achievement("achievement.makeCrystalBag", "makeCrystalBag", 6, -4, ACItems.small_crystal_bag, makeMaterializer).registerStat();
		makeEngraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, ACBlocks.engraver, AchievementList.openInventory).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[]{necro, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah,
				enterdreadlands, killdreadguard, ghoulhead, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth,
				enterOmothol, enterDarkRealm, necrou1, necrou2, necrou3, abyssaln, ritual, ritualSummon, ritualCreate, killOmotholelite, locateJzahar, killJzahar, shadowGems,
				mineAbyOres, mineDread, dreadium, eth, makeTransmutator, makeCrystallizer, makeMaterializer, makeCrystalBag, makeEngraver, ritualBreed, ritualPotion, ritualPotionAoE,
				ritualInfusion, shoggothInfestation}));

		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.CRYSTALLIZER);
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.TRANSMUTATOR);
		AbyssalCrafting.addRecipes();
		AbyssalCraftAPI.addGhoulArmorTextures(Items.leather_helmet, Items.leather_chestplate, Items.leather_leggings, Items.leather_boots, "abyssalcraft:textures/armor/ghoul/leather_1.png", "abyssalcraft:textures/armor/ghoul/leather_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.chainmail_helmet, Items.chainmail_chestplate, Items.chainmail_leggings, Items.chainmail_boots, "abyssalcraft:textures/armor/ghoul/chainmail_1.png", "abyssalcraft:textures/armor/ghoul/chainmail_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots, "abyssalcraft:textures/armor/ghoul/iron_1.png", "abyssalcraft:textures/armor/ghoul/iron_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.golden_helmet, Items.golden_chestplate, Items.golden_leggings, Items.golden_boots, "abyssalcraft:textures/armor/ghoul/gold_1.png", "abyssalcraft:textures/armor/ghoul/gold_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots, "abyssalcraft:textures/armor/ghoul/diamond_1.png", "abyssalcraft:textures/armor/ghoul/diamond_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/abyssalnite_1.png", "abyssalcraft:textures/armor/ghoul/abyssalnite_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots, "abyssalcraft:textures/armor/ghoul/coralium_1.png", "abyssalcraft:textures/armor/ghoul/coralium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots, "abyssalcraft:textures/armor/ghoul/dreadium_1.png", "abyssalcraft:textures/armor/ghoul/dreadium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.ethaxium_helmet, ACItems.ethaxium_chestplate, ACItems.ethaxium_leggings, ACItems.ethaxium_boots, "abyssalcraft:textures/armor/ghoul/ethaxium_1.png", "abyssalcraft:textures/armor/ghoul/ethaxium_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreaded_abyssalnite_helmet, ACItems.dreaded_abyssalnite_chestplate, ACItems.dreaded_abyssalnite_leggings, ACItems.dreaded_abyssalnite_boots, "abyssalcraft:textures/armor/ghoul/dread_1.png", "abyssalcraft:textures/armor/ghoul/dread_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.depths_helmet, ACItems.depths_chestplate, ACItems.depths_leggings, ACItems.depths_boots, "abyssalcraft:textures/armor/ghoul/depths_1.png", "abyssalcraft:textures/armor/ghoul/depths_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.plated_coralium_helmet, ACItems.plated_coralium_chestplate, ACItems.plated_coralium_leggings, ACItems.plated_coralium_boots, "abyssalcraft:textures/armor/ghoul/coraliump_1.png", "abyssalcraft:textures/armor/ghoul/coraliump_2.png");
		AbyssalCraftAPI.addGhoulArmorTextures(ACItems.dreadium_samurai_helmet, ACItems.dreadium_samurai_chestplate, ACItems.dreadium_samurai_leggings, ACItems.dreadium_samurai_boots, "abyssalcraft:textures/armor/ghoul/dreadiums_1.png", "abyssalcraft:textures/armor/ghoul/dreadiums_2.png");
		AbyssalCraftAPI.getInternalNDHandler().registerInternalPages();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	private void addOreDictionaryStuff(){

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
		OreDictionary.registerOre("logWood", ACBlocks.dreadlands_log);
		OreDictionary.registerOre("plankWood", ACBlocks.darklands_oak_planks);
		OreDictionary.registerOre("plankWood", ACBlocks.dreadlands_planks);
		OreDictionary.registerOre("treeSapling", ACBlocks.darklands_oak_sapling);
		OreDictionary.registerOre("treeSapling", ACBlocks.dreadlands_sapling);
		OreDictionary.registerOre("treeLeaves", ACBlocks.darklands_oak_leaves);
		OreDictionary.registerOre("treeLeaves", ACBlocks.dreadlands_leaves);
		OreDictionary.registerOre("blockAbyssalnite", ACBlocks.block_of_abyssalnite);
		OreDictionary.registerOre("blockLiquifiedCoralium", ACBlocks.block_of_coralium);
		OreDictionary.registerOre("blockDreadium", ACBlocks.block_of_dreadium);
		OreDictionary.registerOre("ingotCoraliumBrick", ACItems.coralium_brick);
		OreDictionary.registerOre("ingotDreadium", ACItems.dreadium_ingot);
		OreDictionary.registerOre("dustSulfur", ACItems.sulfur);
		OreDictionary.registerOre("dustSaltpeter", ACItems.nitre);
		OreDictionary.registerOre("materialMethane", ACItems.methane);
		OreDictionary.registerOre("oreSaltpeter", ACBlocks.nitre_ore);
		OreDictionary.registerOre("crystalIron", new ItemStack(ACItems.crystal, 1, 0));
		OreDictionary.registerOre("crystalGold", new ItemStack(ACItems.crystal, 1, 1));
		OreDictionary.registerOre("crystalSulfur", new ItemStack(ACItems.crystal, 1, 2));
		OreDictionary.registerOre("crystalCarbon", new ItemStack(ACItems.crystal, 1, 3));
		OreDictionary.registerOre("crystalOxygen", new ItemStack(ACItems.crystal, 1, 4));
		OreDictionary.registerOre("crystalHydrogen", new ItemStack(ACItems.crystal, 1, 5));
		OreDictionary.registerOre("crystalNitrogen", new ItemStack(ACItems.crystal, 1, 6));
		OreDictionary.registerOre("crystalPhosphorus", new ItemStack(ACItems.crystal, 1, 7));
		OreDictionary.registerOre("crystalPotassium", new ItemStack(ACItems.crystal, 1, 8));
		OreDictionary.registerOre("crystalNitrate", new ItemStack(ACItems.crystal, 1, 9));
		OreDictionary.registerOre("crystalMethane", new ItemStack(ACItems.crystal, 1, 10));
		OreDictionary.registerOre("crystalRedstone", new ItemStack(ACItems.crystal, 1, 11));
		OreDictionary.registerOre("crystalAbyssalnite", new ItemStack(ACItems.crystal, 1, 12));
		OreDictionary.registerOre("crystalCoralium", new ItemStack(ACItems.crystal, 1, 13));
		OreDictionary.registerOre("crystalDreadium", new ItemStack(ACItems.crystal, 1, 14));
		OreDictionary.registerOre("crystalBlaze", new ItemStack(ACItems.crystal, 1, 15));
		OreDictionary.registerOre("crystalTin", new ItemStack(ACItems.crystal, 1, 16));
		OreDictionary.registerOre("crystalCopper", new ItemStack(ACItems.crystal, 1, 17));
		OreDictionary.registerOre("crystalSilicon", new ItemStack(ACItems.crystal, 1, 18));
		OreDictionary.registerOre("crystalMagnesium", new ItemStack(ACItems.crystal, 1, 19));
		OreDictionary.registerOre("crystalAluminium", new ItemStack(ACItems.crystal, 1, 20));
		OreDictionary.registerOre("crystalSilica", new ItemStack(ACItems.crystal, 1, 21));
		OreDictionary.registerOre("crystalAlumina", new ItemStack(ACItems.crystal, 1, 22));
		OreDictionary.registerOre("crystalMagnesia", new ItemStack(ACItems.crystal, 1, 23));
		OreDictionary.registerOre("crystalZinc", new ItemStack(ACItems.crystal, 1, 24));
		OreDictionary.registerOre("foodFriedEgg", ACItems.fried_egg);
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
		OreDictionary.registerOre("blockEthaxium", ACBlocks.block_of_ethaxium);
		OreDictionary.registerOre("nuggetAbyssalnite", new ItemStack(ACItems.ingot_nugget, 1, 0));
		OreDictionary.registerOre("nuggetLiquifiedCoralium", new ItemStack(ACItems.ingot_nugget, 1, 1));
		OreDictionary.registerOre("nuggetDreadium", new ItemStack(ACItems.ingot_nugget, 1, 2));
		OreDictionary.registerOre("nuggetEthaxium", new ItemStack(ACItems.ingot_nugget, 1, 3));
		OreDictionary.registerOre("crystalShardIron", new ItemStack(ACItems.crystal_shard, 1, 0));
		OreDictionary.registerOre("crystalShardGold", new ItemStack(ACItems.crystal_shard, 1, 1));
		OreDictionary.registerOre("crystalShardSulfur", new ItemStack(ACItems.crystal_shard, 1, 2));
		OreDictionary.registerOre("crystalShardCarbon", new ItemStack(ACItems.crystal_shard, 1, 3));
		OreDictionary.registerOre("crystalShardOxygen", new ItemStack(ACItems.crystal_shard, 1, 4));
		OreDictionary.registerOre("crystalShardHydrogen", new ItemStack(ACItems.crystal_shard, 1, 5));
		OreDictionary.registerOre("crystalShardNitrogen", new ItemStack(ACItems.crystal_shard, 1, 6));
		OreDictionary.registerOre("crystalShardPhosphorus", new ItemStack(ACItems.crystal_shard, 1, 7));
		OreDictionary.registerOre("crystalShardPotassium", new ItemStack(ACItems.crystal_shard, 1, 8));
		OreDictionary.registerOre("crystalShardNitrate", new ItemStack(ACItems.crystal_shard, 1, 9));
		OreDictionary.registerOre("crystalShardMethane", new ItemStack(ACItems.crystal_shard, 1, 10));
		OreDictionary.registerOre("crystalShardRedstone", new ItemStack(ACItems.crystal_shard, 1, 11));
		OreDictionary.registerOre("crystalShardAbyssalnite", new ItemStack(ACItems.crystal_shard, 1, 12));
		OreDictionary.registerOre("crystalShardCoralium", new ItemStack(ACItems.crystal_shard, 1, 13));
		OreDictionary.registerOre("crystalShardDreadium", new ItemStack(ACItems.crystal_shard, 1, 14));
		OreDictionary.registerOre("crystalShardBlaze", new ItemStack(ACItems.crystal_shard, 1, 15));
		OreDictionary.registerOre("crystalShardTin", new ItemStack(ACItems.crystal_shard, 1, 16));
		OreDictionary.registerOre("crystalShardCopper", new ItemStack(ACItems.crystal_shard, 1, 17));
		OreDictionary.registerOre("crystalShardSilicon", new ItemStack(ACItems.crystal_shard, 1, 18));
		OreDictionary.registerOre("crystalShardMagnesium", new ItemStack(ACItems.crystal_shard, 1, 19));
		OreDictionary.registerOre("crystalShardAluminium", new ItemStack(ACItems.crystal_shard, 1, 20));
		OreDictionary.registerOre("crystalShardSilica", new ItemStack(ACItems.crystal_shard, 1, 21));
		OreDictionary.registerOre("crystalShardAlumina", new ItemStack(ACItems.crystal_shard, 1, 22));
		OreDictionary.registerOre("crystalShardMagnesia", new ItemStack(ACItems.crystal_shard, 1, 23));
		OreDictionary.registerOre("crystalShardZinc", new ItemStack(ACItems.crystal_shard, 1, 24));
	}

	private void addChestGenHooks(){

		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_axe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_pickaxe), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_shovel), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.darkstone_sword), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACBlocks.darklands_oak_wood), 1, 3, 10));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.cobblestone_upgrade_kit), 1, 2, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.oblivion_catalyst), 1,1,1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_ingot), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.refined_coralium_ingot), 1, 2, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.refined_coralium_ingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.refined_coralium_ingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.refined_coralium_ingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.refined_coralium_ingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.copper_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.tin_ingot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.crystal, 1, 24), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_pickaxe), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.refined_coralium_pickaxe), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_helmet), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_chestplate), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_leggings), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.abyssalnite_boots), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.cobblestone_upgrade_kit), 1, 2, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.iron_upgrade_kit), 1, 2, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.gold_upgrade_kit), 1, 2, 4));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ACItems.diamond_upgrade_kit), 1, 2, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.mre), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.coralium_gem), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_fragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_shard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ACItems.shadow_gem), 1, 3, 3));
	}

	private void addDungeonHooks(){
		DungeonHooks.addDungeonMob("abyssalcraft.abyssalzombie", 150);
		DungeonHooks.addDungeonMob("abyssalcraft.depthsghoul", 100);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowcreature", 120);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowmonster", 100);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowbeast", 30);
		DungeonHooks.addDungeonMob("abyssalcraft.antiabyssalzombie", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antighoul", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antiskeleton", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antispider", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antizombie", 50);
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.abyssal_gateway));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreaded_gateway));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.omothol_gateway));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.coralium_fire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.dreaded_fire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.omothol_fire));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.transmutator_active));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.crystallizer_active));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.engraver));
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
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_altar));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.ritual_pedestal));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.cthulhu_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.hastur_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.jzahar_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.azathoth_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.nyarlathotep_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.yog_sothoth_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.shub_niggurath_statue));
		FMLInterModComms.sendMessage("BuildCraft|Core", "blacklist-facade", new ItemStack(ACBlocks.energy_pedestal));
	}

	private int getNextAvailableEnchantmentId(){

		int i = 0;

		do{
			i++;
			if(i >= 256) throw new RuntimeException("Out of available Enchantment IDs, AbyssalCraft can't load unless some IDs are freed up!");

		} while(Enchantment.getEnchantmentById(i) != null);

		return i;
	}
}
