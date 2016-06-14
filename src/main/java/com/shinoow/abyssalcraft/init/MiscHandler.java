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
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.common.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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

		MinecraftForge.EVENT_BUS.register(this);

		AbyssalCraftAPI.coralium_plague = new PotionCplague(true, 0x00FFFF).setIconIndex(1, 0).setPotionName("potion.Cplague");
		AbyssalCraftAPI.dread_plague = new PotionDplague(true, 0xAD1313).setIconIndex(1, 0).setPotionName("potion.Dplague");
		AbyssalCraftAPI.antimatter_potion = new PotionAntimatter(true, 0xFFFFFF).setIconIndex(1, 0).setPotionName("potion.Antimatter");

		registerPotion(new ResourceLocation("abyssalcraft", "cplague"), AbyssalCraftAPI.coralium_plague);
		registerPotion(new ResourceLocation("abyssalcraft", "dplague"), AbyssalCraftAPI.dread_plague);
		registerPotion(new ResourceLocation("abyssalcraft", "antimatter"), AbyssalCraftAPI.antimatter_potion);

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

		addBrewing(Items.POTIONITEM, PotionTypes.AWKWARD, new ItemStack(ACItems.coralium_plagued_flesh), Items.POTIONITEM, Cplague_normal);
		addBrewing(Items.POTIONITEM, PotionTypes.AWKWARD, new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone), Items.POTIONITEM, Cplague_normal);
		addBrewingConversions(Cplague_normal, Cplague_long, null);
		addBrewing(Items.POTIONITEM, PotionTypes.AWKWARD, new ItemStack(ACItems.dread_fragment), Items.POTIONITEM, Dplague_normal);
		addBrewingConversions(Dplague_normal, Dplague_long, Dplague_strong);
		addBrewing(Items.POTIONITEM, PotionTypes.AWKWARD, new ItemStack(ACItems.rotten_anti_flesh), Items.POTIONITEM, antiMatter_normal);
		addBrewing(Items.POTIONITEM, PotionTypes.AWKWARD, new ItemStack(ACItems.anti_plagued_flesh), Items.POTIONITEM, antiMatter_normal);
		addBrewing(Items.POTIONITEM, PotionTypes.AWKWARD, new ItemStack(ACItems.anti_plagued_flesh_on_a_bone), Items.POTIONITEM, antiMatter_normal);
		addBrewingConversions(antiMatter_normal, antiMatter_long, null);

		AbyssalCraftAPI.coralium_enchantment = new EnchantmentWeaponInfusion("coralium");
		AbyssalCraftAPI.dread_enchantment = new EnchantmentWeaponInfusion("dread");
		AbyssalCraftAPI.light_pierce = new EnchantmentLightPierce();
		AbyssalCraftAPI.iron_wall = new EnchantmentIronWall();

		registerEnchantment(new ResourceLocation("abyssalcraft", "coralium"), AbyssalCraftAPI.coralium_enchantment);
		registerEnchantment(new ResourceLocation("abyssalcraft", "dread"), AbyssalCraftAPI.dread_enchantment);
		registerEnchantment(new ResourceLocation("abyssalcraft", "light_pierce"), AbyssalCraftAPI.light_pierce);
		registerEnchantment(new ResourceLocation("abyssalcraft", "iron_wall"), AbyssalCraftAPI.iron_wall);

		LIQUID_CORALIUM.setBlock(ACBlocks.liquid_coralium).setUnlocalizedName(ACBlocks.liquid_coralium.getUnlocalizedName());
		LIQUID_ANTIMATTER.setBlock(ACBlocks.liquid_antimatter).setUnlocalizedName(ACBlocks.liquid_antimatter.getUnlocalizedName());
		if(CFluid.getBlock() == null)
			CFluid.setBlock(ACBlocks.liquid_coralium);
		if(antifluid.getBlock() == null)
			antifluid.setBlock(ACBlocks.liquid_antimatter);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(CFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_coralium_bucket), new ItemStack(Items.BUCKET));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_coralium.getDefaultState(), ACItems.liquid_coralium_bucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(antifluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ACItems.liquid_antimatter_bucket), new ItemStack(Items.BUCKET));
		BucketHandler.INSTANCE.buckets.put(ACBlocks.liquid_antimatter.getDefaultState(), ACItems.liquid_antimatter_bucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		dreadguard_ambient = registerSoundEvent("dreadguard.idle");
		dreadguard_hurt = registerSoundEvent("dreadguard.hit");
		dreadguard_death = registerSoundEvent("dreadguard.death");
		ghoul_normal_ambient = registerSoundEvent("ghoul.normal.idle");
		ghoul_normal_hurt = registerSoundEvent("ghoul.normal.hit");
		ghoul_death = registerSoundEvent("ghoul.death");
		ghoul_pete_hurt = registerSoundEvent("ghoul.pete.hit");
		ghoul_pete_ambient = registerSoundEvent("ghoul.pete.idle");
		golem_death = registerSoundEvent("golem.death");
		golem_hurt = registerSoundEvent("golem.hit");
		golem_ambient = registerSoundEvent("golem.idle");
		sacthoth_death = registerSoundEvent("sacthoth.death");
		shadow_death = registerSoundEvent("shadow.death");
		shadow_hurt = registerSoundEvent("shadow.hit");
		remnant_scream = registerSoundEvent("remnant.scream");
		shoggoth_ambient = registerSoundEvent("shoggoth.idle");
		shoggoth_hurt = registerSoundEvent("shoggoth.hit");
		shoggoth_death = registerSoundEvent("shoggoth.death");
		jzahar_charge = registerSoundEvent("jzahar.charge");

		RitualUtil.addBlocks();
		addOreDictionaryStuff();
		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		//Achievements
		necro = new Achievement("achievement.necro", "necro", 0, 0, ACItems.necronomicon, AchievementList.OPEN_INVENTORY).registerStat();
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
		ritualBreed = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.EGG, ritual).registerStat();
		ritualPotion = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, Items.POTIONITEM, ritual).registerStat();
		ritualPotionAoE = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, Items.SPLASH_POTION, ritual).registerStat();
		ritualInfusion = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, ACItems.depths_helmet, ritual).registerStat();
		shoggothInfestation = new Achievement("achievement.shoggothInfestation", "shoggothInfestation", -6, 3, Items.SKULL, ritualBreed).registerStat();
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
		makeEngraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, ACBlocks.engraver, AchievementList.OPEN_INVENTORY).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[]{necro, mineAby, killghoul, enterabyss, killdragon, summonAsorah, killAsorah,
				enterdreadlands, killdreadguard, ghoulhead, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth,
				enterOmothol, enterDarkRealm, necrou1, necrou2, necrou3, abyssaln, ritual, ritualSummon, ritualCreate, killOmotholelite, locateJzahar, killJzahar, shadowGems,
				mineAbyOres, mineDread, dreadium, eth, makeTransmutator, makeCrystallizer, makeMaterializer, makeCrystalBag, makeEngraver, ritualBreed, ritualPotion, ritualPotionAoE,
				ritualInfusion, shoggothInfestation}));

		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.CRYSTALLIZER);
		AbyssalCraftAPI.registerFuelHandler(new CrystalFuelHandler(), FuelType.TRANSMUTATOR);
		AbyssalCrafting.addRecipes();
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

	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent event){
		if(event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.darkstone_axe, 3, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_axe"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_pickaxe, 3, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_pickaxe"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_shovel, 2, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_shovel"));
				main.addEntry(new LootEntryItem(ACItems.darkstone_sword, 2, 0, new LootFunction[0], new LootCondition[0], modid + ":darkstone_sword"));
				main.addEntry(new LootEntryItem(Item.getItemFromBlock(ACBlocks.darklands_oak_wood), 10, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":darklands_oak_wood"));
				main.addEntry(new LootEntryItem(ACItems.cobblestone_upgrade_kit, 2, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":cobblestone_upgrade_kit"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.oblivion_catalyst, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":oblivion_catalyst"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.refined_coralium_ingot, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":refined_coralium_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_pickaxe, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_pickaxe"));
				main.addEntry(new LootEntryItem(ACItems.refined_coralium_pickaxe, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":refined_coralium_pickaxe"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_helmet, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_helmet"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_chestplate, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_chestplate"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_leggings, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_leggings"));
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_boots, 2, 0 , new LootFunction[0], new LootCondition[0], modid + ":abyssalnite_boots"));
				main.addEntry(new LootEntryItem(ACItems.cobblestone_upgrade_kit, 10, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":cobblestone_upgrade_kit"));
				main.addEntry(new LootEntryItem(ACItems.iron_upgrade_kit, 7, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":iron_upgrade_kit"));
				main.addEntry(new LootEntryItem(ACItems.gold_upgrade_kit, 4, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":gold_upgrade_kit"));
				main.addEntry(new LootEntryItem(ACItems.diamond_upgrade_kit, 1, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], modid + ":diamond_upgrade_kit"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.refined_coralium_ingot, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":refined_coralium_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.mre, 5, 0 , new LootFunction[0], new LootCondition[0], modid + ":mre"));
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
				main.addEntry(new LootEntryItem(ACItems.refined_coralium_ingot, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":refined_coralium_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.refined_coralium_ingot, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":refined_coralium_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
		if(event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)){
			LootPool main = event.getTable().getPool("main");
			if(main != null){
				main.addEntry(new LootEntryItem(ACItems.abyssalnite_ingot, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":abyssalnite_ingot"));
				main.addEntry(new LootEntryItem(ACItems.refined_coralium_ingot, 1, 0 , new LootFunction[0], new LootCondition[0], modid + ":refined_coralium_ingot"));
				main.addEntry(new LootEntryItem(ACItems.copper_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":copper_ingot"));
				main.addEntry(new LootEntryItem(ACItems.tin_ingot, 7, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5))}, new LootCondition[0], modid + ":tin_ingot"));
				main.addEntry(new LootEntryItem(ACItems.crystal, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 5)), new SetMetadata(new LootCondition[0], new RandomValueRange(24))}, new LootCondition[0], modid + ":crystallized_zinc"));
				main.addEntry(new LootEntryItem(ACItems.shadow_fragment, 8, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 10))}, new LootCondition[0], modid + ":shadow_fragment"));
				main.addEntry(new LootEntryItem(ACItems.shadow_shard, 5, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 6))}, new LootCondition[0], modid + ":shadow_gem_shard"));
				main.addEntry(new LootEntryItem(ACItems.shadow_gem, 3, 0 , new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 3))}, new LootCondition[0], modid + ":shadow_gem"));
			}
		}
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

	private static void registerPotion(ResourceLocation res, Potion pot){
		GameRegistry.register(pot.setRegistryName(res));
	}

	private static void registerEnchantment(ResourceLocation res, Enchantment ench){
		GameRegistry.register(ench.setRegistryName(res));
	}

	private static void registerPotionType(ResourceLocation res, PotionType pot){
		GameRegistry.register(pot.setRegistryName(res));
	}

	private static SoundEvent registerSoundEvent(String name){
		ResourceLocation res = new ResourceLocation(modid, name);
		SoundEvent evt = new SoundEvent(res).setRegistryName(res);
		GameRegistry.register(evt);
		return evt;
	}

	private void addBrewingConversions(PotionType normal, PotionType duration, PotionType strength){
		Item[] types = {Items.POTIONITEM, Items.SPLASH_POTION, Items.LINGERING_POTION};
		ItemStack[] ingreds = {new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.DRAGON_BREATH)};
		addBrewing(types[0], normal, ingreds[2], types[1], normal);
		addBrewing(types[1], normal, ingreds[3], types[2], normal);
		if(duration != null){
			addBrewing(types[0], duration, ingreds[2], types[1], duration);
			addBrewing(types[1], duration, ingreds[3], types[2], duration);
		}
		if(strength != null){
			addBrewing(types[0], strength, ingreds[2], types[1], strength);
			addBrewing(types[1], strength, ingreds[3], types[2], strength);
		}
		for(int i = 0; i < types.length; i++){
			if(duration != null)
				addBrewing(types[i], normal, ingreds[0], types[i], duration);
			if(strength != null)
				addBrewing(types[i], normal, ingreds[1], types[i], strength);
		}
	}

	private void addBrewing(Item input, PotionType inputpot, ItemStack ingredient, Item output, PotionType outputpot){
		ItemStack in = PotionUtils.addPotionToItemStack(new ItemStack(input), inputpot);
		ItemStack out = PotionUtils.addPotionToItemStack(new ItemStack(output), outputpot);
		BrewingRecipeRegistry.addRecipe(in, ingredient, out);
	}
}