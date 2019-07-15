/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Mouse;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.spell.IScroll;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellUtils;
import com.shinoow.abyssalcraft.client.ClientProxy;
import com.shinoow.abyssalcraft.common.blocks.*;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster.EnumCrystalType;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster2.EnumCrystalType2;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.FireMessage;
import com.shinoow.abyssalcraft.common.network.server.InterdimensionalCageMessage;
import com.shinoow.abyssalcraft.common.network.server.StaffModeMessage;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AbyssalCraftClientEventHooks {

	public static float partialTicks = 0;

	@SubscribeEvent
	public void onUpdateFOV(FOVUpdateEvent event) {
		float fov = event.getFov();

		if( event.getEntity().isHandActive() && event.getEntity().getActiveItemStack() != null
				&& event.getEntity().getActiveItemStack().getItem() == ACItems.coralium_longbow) {
			int duration = event.getEntity().getItemInUseCount();
			float multiplier = duration / 20.0F;

			if( multiplier > 1.0F )
				multiplier = 1.0F;
			else
				multiplier *= multiplier;

			fov *= 1.0F - multiplier * 0.15F;
		}

		event.setNewfov(fov);
	}

	@SubscribeEvent
	public void onMouseEvent(MouseEvent event) {
		int button = event.getButton() - 100;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		World world = mc.world;
		int key = mc.gameSettings.keyBindAttack.getKeyCode();

		if (button == key && Mouse.isButtonDown(button + 100))
			if(mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == Type.BLOCK){
				BlockPos pos = mc.objectMouseOver.getBlockPos();
				EnumFacing face = mc.objectMouseOver.sideHit;
				if(pos != null && face != null)
					if (world.getBlockState(pos).getBlock() != null)
						extinguishFire(player, pos, face, world, event);
			}
	}

	private void extinguishFire(EntityPlayer player, BlockPos posIn, EnumFacing face, World world, Event event) {
		BlockPos pos = posIn.offset(face);

		if (world.getBlockState(pos).getBlock() == ACBlocks.mimic_fire ||
				world.getBlockState(pos).getBlock() == ACBlocks.coralium_fire ||
				world.getBlockState(pos).getBlock() == ACBlocks.dreaded_fire ||
				world.getBlockState(pos).getBlock() == ACBlocks.omothol_fire)
			if (event instanceof MouseEvent) {
				PacketDispatcher.sendToServer(new FireMessage(pos));
				player.swingArm(EnumHand.MAIN_HAND);
				event.setCanceled(true);
			}
	}

	@SideOnly(Side.CLIENT)
	public static RayTraceResult getMouseOverExtended(float dist)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		Entity theRenderViewEntity = mc.getRenderViewEntity();
		AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(
				theRenderViewEntity.posX-0.5D,
				theRenderViewEntity.posY-0.0D,
				theRenderViewEntity.posZ-0.5D,
				theRenderViewEntity.posX+0.5D,
				theRenderViewEntity.posY+1.5D,
				theRenderViewEntity.posZ+0.5D
				);
		RayTraceResult returnMOP = null;
		if (mc.world != null)
		{
			double var2 = dist;
			returnMOP = theRenderViewEntity.rayTrace(var2, 0);
			double calcdist = var2;
			Vec3d pos = theRenderViewEntity.getPositionEyes(0);
			var2 = calcdist;
			if (returnMOP != null)
				calcdist = returnMOP.hitVec.distanceTo(pos);

			Vec3d lookvec = theRenderViewEntity.getLook(0);
			Vec3d var8 = pos.addVector(lookvec.x * var2,
					lookvec.y * var2,
					lookvec.z * var2);
			Entity pointedEntity = null;
			float var9 = 1.0F;
			List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(
					theRenderViewEntity,
					theViewBoundingBox.expand(
							lookvec.x * var2,
							lookvec.y * var2,
							lookvec.z * var2).grow(var9, var9, var9));
			double d = calcdist;

			for (Entity entity : list)
				if (entity.canBeCollidedWith())
				{
					float bordersize = entity.getCollisionBorderSize();
					AxisAlignedBB aabb = new AxisAlignedBB(
							entity.posX-entity.width/2,
							entity.posY,
							entity.posZ-entity.width/2,
							entity.posX+entity.width/2,
							entity.posY+entity.height,
							entity.posZ+entity.width/2);
					aabb.expand(bordersize, bordersize, bordersize);
					RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);

					if (aabb.contains(pos))
					{
						if (0.0D < d || d == 0.0D)
						{
							pointedEntity = entity;
							d = 0.0D;
						}
					} else if (mop0 != null)
					{
						double d1 = pos.distanceTo(mop0.hitVec);

						if (d1 < d || d == 0.0D)
						{
							pointedEntity = entity;
							d = d1;
						}
					}
				}

			if (pointedEntity != null && (d < calcdist || returnMOP == null))
				returnMOP = new RayTraceResult(pointedEntity);
		}
		return returnMOP;
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onKeyPressed(KeyInputEvent event){

		if(ClientProxy.staff_mode.isPressed()){
			ItemStack mainStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack offStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.OFF_HAND);
			int mode1 = -1, mode2 = -1;

			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.staff_of_the_gatekeeper){
				if(mainStack.hasTagCompound())
					mode1 = mainStack.getTagCompound().getInteger("Mode");
				if(mode1 > -1){
					if(mode1 == 0)
						mode1 = 1;
					else mode1 = 0;
					Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("tooltip.staff.mode.1")+": "+TextFormatting.GOLD + I18n.format(mode1 == 1 ? "item.drainstaff.normal.name" : "item.gatewaykey.name")));
				}
			}
			if(!offStack.isEmpty() && offStack.getItem() == ACItems.staff_of_the_gatekeeper){
				if(offStack.hasTagCompound())
					mode2 = offStack.getTagCompound().getInteger("Mode");
				if(mode2 > -1){
					if(mode2 == 0)
						mode2 = 1;
					else mode2 = 0;
					Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("tooltip.staff.mode.1")+": "+TextFormatting.GOLD + I18n.format(mode2 == 1 ? "item.drainstaff.normal.name" : "item.gatewaykey.name")));
				}
			}

			if(mode1 > -1 || mode2 > -1)
				PacketDispatcher.sendToServer(new StaffModeMessage());
		}
		if(ClientProxy.use_cage.isPressed()) {
			ItemStack mainStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack offStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.OFF_HAND);

			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.interdimensional_cage && !mainStack.getTagCompound().hasKey("Entity")) {
				RayTraceResult mov = getMouseOverExtended(3);

				if (mov != null)
					if (mov.entityHit != null && !mov.entityHit.isDead)
						if (mov.entityHit != Minecraft.getMinecraft().player )
							PacketDispatcher.sendToServer(new InterdimensionalCageMessage(mov.entityHit.getEntityId(), EnumHand.MAIN_HAND));
			}
			if (!offStack.isEmpty() && offStack.getItem() == ACItems.interdimensional_cage && !offStack.getTagCompound().hasKey("Entity")) {
				RayTraceResult mov = getMouseOverExtended(3);

				if (mov != null)
					if (mov.entityHit != null && !mov.entityHit.isDead)
						if (mov.entityHit != Minecraft.getMinecraft().player )
							PacketDispatcher.sendToServer(new InterdimensionalCageMessage(mov.entityHit.getEntityId(), EnumHand.OFF_HAND));
			}
		}
	}

	@SubscribeEvent
	public void tooltipStuff(ItemTooltipEvent event){
		ItemStack stack = event.getItemStack();

		if(stack.getItem() instanceof IEnergyContainerItem)
			event.getToolTip().add(1, String.format("%d/%d PE", (int)((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack), ((IEnergyContainerItem)stack.getItem()).getMaxEnergy(stack)));

		if(!APIUtils.display_names)
			if(stack.getItem() instanceof IUnlockableItem && event.getEntityPlayer() != null && !NecroDataCapability.getCap(event.getEntityPlayer()).isUnlocked(((IUnlockableItem)stack.getItem()).getUnlockCondition(stack), event.getEntityPlayer())){
				event.getToolTip().remove(0);
				event.getToolTip().add(0, "Lorem ipsum");
			}
		if(stack.getItem() instanceof IScroll) {
			Spell spell = SpellUtils.getSpell(stack);
			if(spell != null){
				event.getToolTip().add(1, "Spell: "+TextFormatting.AQUA+spell.getLocalizedName());
				event.getToolTip().add(2, "Required PE per cast: "+(int)spell.getReqEnergy());
				event.getToolTip().add(3, "Cast type: "+TextFormatting.GOLD+(spell.requiresCharging() ? "Charge" : "Instant"));
			}
		}
	}

	@SubscribeEvent
	public void tooltipFont(RenderTooltipEvent.Pre event) {
		if(!APIUtils.display_names && event.getLines().get(0).startsWith("\u00A7fLorem ipsum"))
			event.setFontRenderer(AbyssalCraftAPI.getAkloFont());
	}

	@SubscribeEvent
	public static void renderTick(RenderTickEvent event) {
		if(event.phase == Phase.START)
			partialTicks = event.renderTickTime;
	}

	@SubscribeEvent
	public static void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if(gui == null || !gui.doesGuiPauseGame())
				partialTicks = 0;
		}
	}

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event){

		ModelBakery.registerItemVariants(ACItems.shoggoth_flesh, makerl("shoggothflesh_overworld", "shoggothflesh_abyssalwasteland",
				"shoggothflesh_dreadlands", "shoggothflesh_omothol", "shoggothflesh_darkrealm"));
		ModelBakery.registerItemVariants(ACItems.essence, makerl("essence_abyssalwasteland", "essence_dreadlands", "essence_omothol"));
		ModelBakery.registerItemVariants(ACItems.skin, makerl("skin_abyssalwasteland", "skin_dreadlands", "skin_omothol"));
		ModelBakery.registerItemVariants(ACItems.ritual_charm, makerl("ritualcharm_empty", "ritualcharm_range", "ritualcharm_duration", "ritualcharm_power"));
		ModelBakery.registerItemVariants(ACItems.ingot_nugget, makerl("nugget_abyssalnite", "nugget_coralium", "nugget_dreadium", "nugget_ethaxium"));
		ModelBakery.registerItemVariants(ACItems.staff_of_rending, makerl("drainstaff", "drainstaff_aw", "drainstaff_dl", "drainstaff_omt"));
		ModelBakery.registerItemVariants(ACItems.staff_of_the_gatekeeper, makerl("staff", "staff2"));
		ModelBakery.registerItemVariants(ACItems.scroll, makerl("scroll_basic", "scroll_lesser", "scroll_moderate", "scroll_greater"));
		ModelBakery.registerItemVariants(ACItems.unique_scroll, makerl("scroll_unique_anti", "scroll_unique_oblivion"));
		ModelBakery.registerItemVariants(ACItems.antidote, makerl("coralium_antidote", "dread_antidote"));

		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ethaxium_brick), makerl("ethaxiumbrick_0", "ethaxiumbrick_1", "ethaxiumbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.dark_ethaxium_brick), makerl("darkethaxiumbrick_0", "darkethaxiumbrick_1", "darkethaxiumbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_energy_pedestal), makerl("tieredenergypedestal_0", "tieredenergypedestal_1",
				"tieredenergypedestal_2", "tieredenergypedestal_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_sacrificial_altar), makerl("tieredsacrificialaltar_0", "tieredsacrificialaltar_1",
				"tieredsacrificialaltar_2", "tieredsacrificialaltar_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ritual_altar), makerl("ritualaltar_0", "ritualaltar_1", "ritualaltar_2", "ritualaltar_3",
				"ritualaltar_4", "ritualaltar_5", "ritualaltar_6", "ritualaltar_7"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.ritual_pedestal), makerl("ritualpedestal_0", "ritualpedestal_1", "ritualpedestal_2",
				"ritualpedestal_3", "ritualpedestal_4", "ritualpedestal_5", "ritualpedestal_6", "ritualpedestal_7"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.darkstone_brick), makerl("darkstone_brick_0", "darkstone_brick_1", "darkstone_brick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.abyssal_stone_brick), makerl("abybrick_0", "abybrick_1", "abybrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.dreadstone_brick), makerl("dreadbrick_0", "dreadbrick_1", "dreadbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.abyssalnite_stone_brick), makerl("abydreadbrick_0", "abydreadbrick_1", "abydreadbrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.coralium_stone_brick), makerl("cstonebrick_0", "cstonebrick_1", "cstonebrick_2"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_energy_collector), makerl("tieredenergycollector_0", "tieredenergycollector_1",
				"tieredenergycollector_2", "tieredenergycollector_3"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.tiered_energy_container), makerl("tieredenergycontainer_0", "tieredenergycontainer_1",
				"tieredenergycontainer_2", "tieredenergycontainer_3"));
		//		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.statue), makerl("cthulhustatue", "hasturstatue", "jzaharstatue", "azathothstatue",
		//				"nyarlathotepstatue", "yogsothothstatue", "shubniggurathstatue"));
		//		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.decorative_statue), makerl("cthulhustatue", "hasturstatue", "jzaharstatue", "azathothstatue",
		//				"nyarlathotepstatue", "yogsothothstatue", "shubniggurathstatue"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ACBlocks.cobblestone), makerl("darkstone_cobble", "abyssalcobblestone", "dreadstonecobblestone",
				"abyssalnitecobblestone", "coraliumcobblestone"));

		registerFluidModel(ACBlocks.liquid_coralium, "cor");
		registerFluidModel(ACBlocks.liquid_antimatter, "anti");

		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_leaves, new StateMap.Builder().ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadlands_leaves, new StateMap.Builder().ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.oblivion_deathbomb, new StateMap.Builder().ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.odb_core, new StateMap.Builder().ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
		if(ACConfig.darkstone_brick_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.darkstone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.darkstone_cobblestone_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.darkstone_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.abyssal_stone_brick_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.abyssal_stone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.darkstone_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.darkstone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.darklands_oak_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.dreadstone_brick_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.abyssalnite_stone_brick_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.abyssalnite_stone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.coralium_stone_brick_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.coralium_stone_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.ethaxium_brick_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.ethaxium_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dark_ethaxium_brick_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_sapling, new StateMap.Builder().ignore(new IProperty[] {BlockSapling.TYPE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadlands_sapling, new StateMap.Builder().ignore(new IProperty[] {BlockSapling.TYPE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.mimic_fire, new StateMap.Builder().ignore(new IProperty[] {BlockFire.AGE}).build());
		if(ACConfig.darkstone_cobblestone_wall)
			ModelLoader.setCustomStateMapper(ACBlocks.darkstone_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.crystal_cluster, new StateMap.Builder().ignore(new IProperty[]{BlockCrystalCluster.TYPE}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.crystal_cluster2, new StateMap.Builder().ignore(new IProperty[]{BlockCrystalCluster2.TYPE}).build());
		if(ACConfig.abyssal_cobbblestone_wall)
			ModelLoader.setCustomStateMapper(ACBlocks.abyssal_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		if(ACConfig.dreadstone_cobblestone_wall)
			ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		if(ACConfig.abyssalnite_cobblestone_wall)
			ModelLoader.setCustomStateMapper(ACBlocks.abyssalnite_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		if(ACConfig.coralium_cobblestone_wall)
			ModelLoader.setCustomStateMapper(ACBlocks.coralium_cobblestone_wall, new StateMap.Builder().ignore(new IProperty[] {BlockWall.VARIANT}).build());
		if(ACConfig.abyssal_cobblestone_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.abyssal_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.dreadstone_cobblestone_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.abyssalnite_cobblestone_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.abyssalnite_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		if(ACConfig.coralium_cobblestone_slab)
			ModelLoader.setCustomStateMapper(ACBlocks.coralium_cobblestone_slab, new StateMap.Builder().ignore(new IProperty[] {BlockACSlab.VARIANT_PROPERTY}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.statue, new StateMapperBase(){
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String stuff = "abyssalcraft:"+state.getValue(BlockStatue.TYPE).getName()+"statue";
				Map<IProperty<?>, Comparable<?>> map = new LinkedHashMap<>(state.getProperties());
				map.remove(BlockStatue.TYPE);
				return new ModelResourceLocation(stuff, getPropertyString(map));
			}});
		ModelLoader.setCustomStateMapper(ACBlocks.decorative_statue, new StateMapperBase(){
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String stuff = "abyssalcraft:"+state.getValue(BlockStatue.TYPE).getName()+"statue";
				Map<IProperty<?>, Comparable<?>> map = new LinkedHashMap<>(state.getProperties());
				map.remove(BlockStatue.TYPE);
				return new ModelResourceLocation(stuff, getPropertyString(map));
			}});
		ModelLoader.setCustomStateMapper(ACBlocks.cobblestone, new StateMapperBase(){
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String stuff = "abyssalcraft:"+state.getValue(BlockACCobblestone.TYPE).getState();
				Map<IProperty<?>, Comparable<?>> map = new LinkedHashMap<>(state.getProperties());
				map.remove(BlockACCobblestone.TYPE);
				return new ModelResourceLocation(stuff, getPropertyString(map));
			}});
		ModelLoader.setCustomStateMapper(ACBlocks.ingot_block, new StateMapperBase(){
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String stuff = "abyssalcraft:"+state.getValue(IngotBlock.TYPE).getState();
				Map<IProperty<?>, Comparable<?>> map = new LinkedHashMap<>(state.getProperties());
				map.remove(IngotBlock.TYPE);
				return new ModelResourceLocation(stuff, getPropertyString(map));
			}});
		ModelLoader.setCustomStateMapper(ACBlocks.tiered_energy_relay, new StateMapperBase(){
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String stuff = "abyssalcraft:";
				switch(state.getValue(BlockTieredEnergyPedestal.DIMENSION)){
				case ABYSSAL_WASTELAND:
					stuff+="awenergyrelay";
					break;
				case DREADLANDS:
					stuff+="dlenergyrelay";
					break;
				case OMOTHOL:
					stuff+="omtenergyrelay";
					break;
				case OVERWORLD:
					stuff+="owenergyrelay";
					break;
				default:
					break;
				}
				Map<IProperty<?>, Comparable<?>> map = new LinkedHashMap<>(state.getProperties());
				map.remove(BlockTieredEnergyPedestal.DIMENSION);
				return new ModelResourceLocation(stuff, getPropertyString(map));
			}});
		ModelLoader.setCustomStateMapper(ACBlocks.stone, new StateMapperBase(){
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String stuff = "abyssalcraft:"+state.getValue(BlockACStone.TYPE).getState();
				Map<IProperty<?>, Comparable<?>> map = new LinkedHashMap<>(state.getProperties());
				map.remove(BlockACStone.TYPE);
				return new ModelResourceLocation(stuff, getPropertyString(map));
			}});
		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_door, new StateMap.Builder().ignore(new IProperty[]{BlockDoor.POWERED}).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadlands_door, new StateMap.Builder().ignore(new IProperty[]{BlockDoor.POWERED}).build());

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.statue), 0, new ModelResourceLocation("abyssalcraft:cthulhustatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_statue), 0, new ModelResourceLocation("abyssalcraft:cthulhustatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.statue), 1, new ModelResourceLocation("abyssalcraft:hasturstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_statue), 1, new ModelResourceLocation("abyssalcraft:hasturstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.statue), 2, new ModelResourceLocation("abyssalcraft:jzaharstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_statue), 2, new ModelResourceLocation("abyssalcraft:jzaharstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.statue), 3, new ModelResourceLocation("abyssalcraft:azathothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_statue), 3, new ModelResourceLocation("abyssalcraft:azathothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.statue), 4, new ModelResourceLocation("abyssalcraft:nyarlathotepstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_statue), 4, new ModelResourceLocation("abyssalcraft:nyarlathotepstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.statue), 5, new ModelResourceLocation("abyssalcraft:yogsothothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_statue), 5, new ModelResourceLocation("abyssalcraft:yogsothothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.statue), 6, new ModelResourceLocation("abyssalcraft:shubniggurathstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_statue), 6, new ModelResourceLocation("abyssalcraft:shubniggurathstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.engraver), 0, new ModelResourceLocation("abyssalcraft:engraver", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.oblivion_deathbomb), 0, new ModelResourceLocation("abyssalcraft:odb", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.chagaroth_altar_top), 0, new ModelResourceLocation("abyssalcraft:dreadaltartop", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.chagaroth_altar_bottom), 0, new ModelResourceLocation("abyssalcraft:dreadaltarbottom", "inventory"));

		ModelLoader.setCustomModelResourceLocation(ACItems.cudgel, 0, new ModelResourceLocation("abyssalcraft:cudgel", "inventory"));
		ModelLoader.setCustomModelResourceLocation(ACItems.dreadium_katana, 0, new ModelResourceLocation("abyssalcraft:dreadkatana", "inventory"));

		ModelLoader.setCustomMeshDefinition(ACItems.staff_of_the_gatekeeper, stack -> stack.hasTagCompound() && stack.getTagCompound().getInteger("Mode") == 1 ? new ModelResourceLocation("abyssalcraft:staff2", "inventory") : new ModelResourceLocation("abyssalcraft:staff", "inventory"));

		registerItemRender(ItemHandler.devsword, 0);
		registerItemRender(ACItems.oblivion_catalyst, 0);
		registerItemRender(ACItems.gateway_key, 0);
		registerItemRender(ACItems.powerstone_tracker, 0);
		registerItemRender(ACItems.eye_of_the_abyss, 0);
		registerItemRender(ACItems.dreaded_gateway_key, 0);
		registerItemRender(ACItems.dreaded_shard_of_abyssalnite, 0);
		registerItemRender(ACItems.dreaded_chunk_of_abyssalnite, 0);
		registerItemRender(ACItems.chunk_of_abyssalnite, 0);
		registerItemRender(ACItems.abyssalnite_ingot, 0);
		registerItemRender(ACItems.coralium_gem, 0);
		registerItemRender(ACItems.coralium_gem_cluster_2, 0);
		registerItemRender(ACItems.coralium_gem_cluster_3, 0);
		registerItemRender(ACItems.coralium_gem_cluster_4, 0);
		registerItemRender(ACItems.coralium_gem_cluster_5, 0);
		registerItemRender(ACItems.coralium_gem_cluster_6, 0);
		registerItemRender(ACItems.coralium_gem_cluster_7, 0);
		registerItemRender(ACItems.coralium_gem_cluster_8, 0);
		registerItemRender(ACItems.coralium_gem_cluster_9, 0);
		registerItemRender(ACItems.coralium_pearl, 0);
		registerItemRender(ACItems.chunk_of_coralium, 0);
		registerItemRender(ACItems.refined_coralium_ingot, 0);
		registerItemRender(ACItems.coralium_plate, 0);
		registerItemRender(ACItems.transmutation_gem, 0);
		registerItemRender(ACItems.coralium_plagued_flesh, 0);
		registerItemRender(ACItems.coralium_plagued_flesh_on_a_bone, 0);
		registerItemRender(ACItems.darkstone_pickaxe, 0);
		registerItemRender(ACItems.darkstone_axe, 0);
		registerItemRender(ACItems.darkstone_shovel, 0);
		registerItemRender(ACItems.darkstone_sword, 0);
		registerItemRender(ACItems.darkstone_hoe, 0);
		registerItemRender(ACItems.abyssalnite_pickaxe, 0);
		registerItemRender(ACItems.abyssalnite_axe, 0);
		registerItemRender(ACItems.abyssalnite_shovel, 0);
		registerItemRender(ACItems.abyssalnite_sword, 0);
		registerItemRender(ACItems.abyssalnite_hoe, 0);
		registerItemRender(ACItems.refined_coralium_pickaxe, 0);
		registerItemRender(ACItems.refined_coralium_axe, 0);
		registerItemRender(ACItems.refined_coralium_shovel, 0);
		registerItemRender(ACItems.refined_coralium_sword, 0);
		registerItemRender(ACItems.refined_coralium_hoe, 0);
		registerItemRender(ACItems.abyssalnite_boots, 0);
		registerItemRender(ACItems.abyssalnite_helmet, 0);
		registerItemRender(ACItems.abyssalnite_chestplate, 0);
		registerItemRender(ACItems.abyssalnite_leggings, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_boots, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_helmet, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_chestplate, 0);
		registerItemRender(ACItems.dreaded_abyssalnite_leggings, 0);
		registerItemRender(ACItems.refined_coralium_boots, 0);
		registerItemRender(ACItems.refined_coralium_helmet, 0);
		registerItemRender(ACItems.refined_coralium_chestplate, 0);
		registerItemRender(ACItems.refined_coralium_leggings, 0);
		registerItemRender(ACItems.plated_coralium_boots, 0);
		registerItemRender(ACItems.plated_coralium_helmet, 0);
		registerItemRender(ACItems.plated_coralium_chestplate, 0);
		registerItemRender(ACItems.plated_coralium_leggings, 0);
		registerItemRender(ACItems.depths_boots, 0);
		registerItemRender(ACItems.depths_helmet, 0);
		registerItemRender(ACItems.depths_chestplate, 0);
		registerItemRender(ACItems.depths_leggings, 0);
		registerItemRender(ACItems.cobblestone_upgrade_kit, 0);
		registerItemRender(ACItems.iron_upgrade_kit, 0);
		registerItemRender(ACItems.gold_upgrade_kit, 0);
		registerItemRender(ACItems.diamond_upgrade_kit, 0);
		registerItemRender(ACItems.abyssalnite_upgrade_kit, 0);
		registerItemRender(ACItems.coralium_upgrade_kit, 0);
		registerItemRender(ACItems.mre, 0);
		registerItemRender(ACItems.iron_plate, 0);
		registerItemRender(ACItems.chicken_on_a_plate, 0);
		registerItemRender(ACItems.pork_on_a_plate, 0);
		registerItemRender(ACItems.beef_on_a_plate, 0);
		registerItemRender(ACItems.fish_on_a_plate, 0);
		registerItemRender(ACItems.dirty_plate, 0);
		registerItemRender(ACItems.fried_egg, 0);
		registerItemRender(ACItems.fried_egg_on_a_plate, 0);
		registerItemRender(ACItems.washcloth, 0);
		registerItemRender(ACItems.shadow_fragment, 0);
		registerItemRender(ACItems.shadow_shard, 0);
		registerItemRender(ACItems.shadow_gem, 0);
		registerItemRender(ACItems.shard_of_oblivion, 0);
		registerItemRender(ACItems.coralium_longbow, 0);
		registerItemRender(ACItems.coralium_brick, 0);
		registerItemRender(ACItems.dreadium_ingot, 0);
		registerItemRender(ACItems.dread_fragment, 0);
		registerItemRender(ACItems.dreadium_boots, 0);
		registerItemRender(ACItems.dreadium_helmet, 0);
		registerItemRender(ACItems.dreadium_chestplate, 0);
		registerItemRender(ACItems.dreadium_leggings, 0);
		registerItemRender(ACItems.dreadium_pickaxe, 0);
		registerItemRender(ACItems.dreadium_axe, 0);
		registerItemRender(ACItems.dreadium_shovel, 0);
		registerItemRender(ACItems.dreadium_sword, 0);
		registerItemRender(ACItems.dreadium_hoe, 0);
		registerItemRender(ACItems.dreadium_upgrade_kit, 0);
		registerItemRender(ACItems.carbon_cluster, 0);
		registerItemRender(ACItems.dense_carbon_cluster, 0);
		registerItemRender(ACItems.methane, 0);
		registerItemRender(ACItems.nitre, 0);
		registerItemRender(ACItems.sulfur, 0);
		registerItemRenders(ACItems.crystal, ACLib.crystalNames.length);
		registerItemRenders(ACItems.crystal_shard, ACLib.crystalNames.length);
		registerItemRender(ACItems.dread_cloth, 0);
		registerItemRender(ACItems.dreadium_plate, 0);
		registerItemRender(ACItems.dreadium_katana_blade, 0);
		registerItemRender(ACItems.dreadium_katana_hilt, 0);
		registerItemRender(ACItems.dread_plagued_gateway_key, 0);
		registerItemRender(ACItems.rlyehian_gateway_key, 0);
		registerItemRender(ACItems.dreadium_samurai_boots, 0);
		registerItemRender(ACItems.dreadium_samurai_helmet, 0);
		registerItemRender(ACItems.dreadium_samurai_chestplate, 0);
		registerItemRender(ACItems.dreadium_samurai_leggings, 0);
		registerItemRender(ACItems.tin_ingot, 0);
		registerItemRender(ACItems.copper_ingot, 0);
		registerItemRender(ACItems.anti_beef, 0);
		registerItemRender(ACItems.anti_chicken, 0);
		registerItemRender(ACItems.anti_pork, 0);
		registerItemRender(ACItems.rotten_anti_flesh, 0);
		registerItemRender(ACItems.anti_bone, 0);
		registerItemRender(ACItems.anti_spider_eye, 0);
		registerItemRender(ACItems.sacthoths_soul_harvesting_blade, 0);
		registerItemRender(ACItems.ethaxium_brick, 0);
		registerItemRender(ACItems.ethaxium_ingot, 0);
		registerItemRender(ACItems.life_crystal, 0);
		registerItemRender(ACItems.ethaxium_boots, 0);
		registerItemRender(ACItems.ethaxium_helmet, 0);
		registerItemRender(ACItems.ethaxium_chestplate, 0);
		registerItemRender(ACItems.ethaxium_leggings, 0);
		registerItemRender(ACItems.ethaxium_pickaxe, 0);
		registerItemRender(ACItems.ethaxium_axe, 0);
		registerItemRender(ACItems.ethaxium_shovel, 0);
		registerItemRender(ACItems.ethaxium_sword, 0);
		registerItemRender(ACItems.ethaxium_hoe, 0);
		registerItemRender(ACItems.ethaxium_upgrade_kit, 0);
		registerItemRender(ACItems.coin, 0);
		registerItemRender(ACItems.cthulhu_engraved_coin, 0);
		registerItemRender(ACItems.elder_engraved_coin, 0);
		registerItemRender(ACItems.jzahar_engraved_coin, 0);
		registerItemRender(ACItems.blank_engraving, 0);
		registerItemRender(ACItems.cthulhu_engraving, 0);
		registerItemRender(ACItems.elder_engraving, 0);
		registerItemRender(ACItems.jzahar_engraving, 0);
		registerItemRender(ACItems.eldritch_scale, 0);
		registerItemRender(ACItems.omothol_flesh, 0);
		registerItemRender(ACItems.anti_plagued_flesh, 0);
		registerItemRender(ACItems.anti_plagued_flesh_on_a_bone, 0);
		registerItemRender(ACItems.necronomicon, 0);
		registerItemRender(ACItems.abyssal_wasteland_necronomicon, 0);
		registerItemRender(ACItems.dreadlands_necronomicon, 0);
		registerItemRender(ACItems.omothol_necronomicon, 0);
		registerItemRender(ACItems.abyssalnomicon, 0);
		registerItemRender(ACItems.small_crystal_bag, 0);
		registerItemRender(ACItems.medium_crystal_bag, 0);
		registerItemRender(ACItems.large_crystal_bag, 0);
		registerItemRender(ACItems.huge_crystal_bag, 0);
		registerItemRender(ACItems.shoggoth_flesh, 0, "shoggothflesh_overworld");
		registerItemRender(ACItems.shoggoth_flesh, 1, "shoggothflesh_abyssalwasteland");
		registerItemRender(ACItems.shoggoth_flesh, 2, "shoggothflesh_dreadlands");
		registerItemRender(ACItems.shoggoth_flesh, 3, "shoggothflesh_omothol");
		registerItemRender(ACItems.shoggoth_flesh, 4, "shoggothflesh_darkrealm");
		registerItemRender(ACItems.ingot_nugget, 0, "nugget_abyssalnite");
		registerItemRender(ACItems.ingot_nugget, 1, "nugget_coralium");
		registerItemRender(ACItems.ingot_nugget, 2, "nugget_dreadium");
		registerItemRender(ACItems.ingot_nugget, 3, "nugget_ethaxium");
		registerItemRender(ACItems.staff_of_rending, 0, "drainstaff");
		registerItemRender(ACItems.staff_of_rending, 1, "drainstaff_aw");
		registerItemRender(ACItems.staff_of_rending, 2, "drainstaff_dl");
		registerItemRender(ACItems.staff_of_rending, 3, "drainstaff_omt");
		registerItemRender(ACItems.essence, 0, "essence_abyssalwasteland");
		registerItemRender(ACItems.essence, 1, "essence_dreadlands");
		registerItemRender(ACItems.essence, 2, "essence_omothol");
		registerItemRender(ACItems.skin, 0, "skin_abyssalwasteland");
		registerItemRender(ACItems.skin, 1, "skin_dreadlands");
		registerItemRender(ACItems.skin, 2, "skin_omothol");
		registerItemRender(ACItems.ritual_charm, 0, "ritualcharm_empty");
		registerItemRender(ACItems.ritual_charm, 1, "ritualcharm_range");
		registerItemRender(ACItems.ritual_charm, 2, "ritualcharm_duration");
		registerItemRender(ACItems.ritual_charm, 3, "ritualcharm_power");
		registerItemRenders(ACItems.cthulhu_charm, 4);
		registerItemRenders(ACItems.hastur_charm, 4);
		registerItemRenders(ACItems.jzahar_charm, 4);
		registerItemRenders(ACItems.azathoth_charm, 4);
		registerItemRenders(ACItems.nyarlathotep_charm, 4);
		registerItemRenders(ACItems.yog_sothoth_charm, 4);
		registerItemRenders(ACItems.shub_niggurath_charm, 4);
		registerItemRender(ACItems.hastur_engraved_coin, 0);
		registerItemRender(ACItems.azathoth_engraved_coin, 0);
		registerItemRender(ACItems.nyarlathotep_engraved_coin, 0);
		registerItemRender(ACItems.yog_sothoth_engraved_coin, 0);
		registerItemRender(ACItems.shub_niggurath_engraved_coin, 0);
		registerItemRender(ACItems.hastur_engraving, 0);
		registerItemRender(ACItems.azathoth_engraving, 0);
		registerItemRender(ACItems.nyarlathotep_engraving, 0);
		registerItemRender(ACItems.yog_sothoth_engraving, 0);
		registerItemRender(ACItems.shub_niggurath_engraving, 0);
		registerItemRender(ACItems.essence_of_the_gatekeeper, 0);
		registerItemRender(ACItems.interdimensional_cage, 0);
		registerItemRenders(ACItems.crystal_fragment, ACLib.crystalNames.length);
		registerItemRender(ACItems.stone_tablet, 0);
		registerItemRender(ACItems.scroll, 0, "scroll_basic");
		registerItemRender(ACItems.scroll, 1, "scroll_lesser");
		registerItemRender(ACItems.scroll, 2, "scroll_moderate");
		registerItemRender(ACItems.scroll, 3, "scroll_greater");
		registerItemRender(ACItems.unique_scroll, 0, "scroll_unique_anti");
		registerItemRender(ACItems.unique_scroll, 1, "scroll_unique_oblivion");
		registerItemRender(ACItems.antidote, 0, "coralium_antidote");
		registerItemRender(ACItems.antidote, 1, "dread_antidote");
		registerItemRender(ACItems.darklands_oak_door, 0);
		registerItemRender(ACItems.dreadlands_door, 0);
		registerItemRender(ACItems.charcoal, 0);

		registerItemRender(ACBlocks.stone, 0, "darkstone");
		registerItemRender(ACBlocks.stone, 1, "abystone");
		registerItemRender(ACBlocks.stone, 2, "dreadstone");
		registerItemRender(ACBlocks.stone, 3, "abydreadstone");
		registerItemRender(ACBlocks.stone, 4, "cstone");
		registerItemRender(ACBlocks.stone, 5, "ethaxium");
		registerItemRender(ACBlocks.stone, 6, "omotholstone");
		registerItemRender(ACBlocks.stone, 7, "monolithstone");
		registerItemRender(ACBlocks.cobblestone, 0, "darkstone_cobble");
		registerItemRender(ACBlocks.cobblestone, 1, "abyssalcobblestone");
		registerItemRender(ACBlocks.cobblestone, 2, "dreadstonecobblestone");
		registerItemRender(ACBlocks.cobblestone, 3, "abyssalnitecobblestone");
		registerItemRender(ACBlocks.cobblestone, 4, "coraliumcobblestone");
		registerItemRender(ACBlocks.darkstone_brick, 0, "darkstone_brick_0");
		registerItemRender(ACBlocks.darkstone_brick, 1, "darkstone_brick_1");
		registerItemRender(ACBlocks.darkstone_brick, 2, "darkstone_brick_2");
		registerItemRender(ACBlocks.glowing_darkstone_bricks, 0);
		if(ACConfig.darkstone_brick_slab) {
			registerItemRender(ACBlocks.darkstone_brick_slab, 0);
			registerItemRender(BlockHandler.Darkbrickslab2, 0);
		}
		if(ACConfig.darkstone_cobblestone_slab) {
			registerItemRender(ACBlocks.darkstone_cobblestone_slab, 0);
			registerItemRender(BlockHandler.Darkcobbleslab2, 0);
		}
		if(ACConfig.darkstone_brick_stairs)
			registerItemRender(ACBlocks.darkstone_brick_stairs, 0);
		if(ACConfig.darkstone_cobblestone_stairs)
			registerItemRender(ACBlocks.darkstone_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.darklands_oak_leaves, 0);
		registerItemRender(ACBlocks.darklands_oak_wood, 0);
		registerItemRender(ACBlocks.darklands_oak_wood_2, 0);
		registerItemRender(ACBlocks.darklands_oak_sapling, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick, 0, "abybrick_0");
		registerItemRender(ACBlocks.abyssal_stone_brick, 1, "abybrick_1");
		registerItemRender(ACBlocks.abyssal_stone_brick, 2, "abybrick_2");
		if(ACConfig.abyssal_stone_brick_slab) {
			registerItemRender(ACBlocks.abyssal_stone_brick_slab, 0);
			registerItemRender(BlockHandler.abyslab2, 0);
		}
		if(ACConfig.abyssal_stone_brick_stairs)
			registerItemRender(ACBlocks.abyssal_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.coralium_ore, 0);
		registerItemRender(ACBlocks.abyssalnite_ore, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_fence, 0);
		if(ACConfig.darkstone_cobblestone_wall)
			registerItemRender(ACBlocks.darkstone_cobblestone_wall, 0);
		registerItemRender(ACBlocks.ingot_block, 0, "abyblock");
		registerItemRender(ACBlocks.ingot_block, 1, "corblock");
		registerItemRender(ACBlocks.ingot_block, 2, "dreadiumblock");
		registerItemRender(ACBlocks.ingot_block, 3, "ethaxiumblock");
		registerItemRender(ACBlocks.coralium_infused_stone, 0);
		registerItemRender(ACBlocks.odb_core, 0);
		registerItemRender(ACBlocks.wooden_crate, 0);
		registerItemRender(ACBlocks.abyssal_gateway, 0);
		if(ACConfig.darkstone_slab) {
			registerItemRender(ACBlocks.darkstone_slab, 0);
			registerItemRender(BlockHandler.Darkstoneslab2, 0);
		}
		registerItemRender(ACBlocks.coralium_fire, 0);
		registerItemRender(ACBlocks.darkstone_button, 0);
		registerItemRender(ACBlocks.darkstone_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_planks, 0);
		registerItemRender(ACBlocks.darklands_oak_button, 0);
		registerItemRender(ACBlocks.darklands_oak_pressure_plate, 0);
		if(ACConfig.darklands_oak_stairs)
			registerItemRender(ACBlocks.darklands_oak_stairs, 0);
		if(ACConfig.darklands_oak_slab) {
			registerItemRender(ACBlocks.darklands_oak_slab, 0);
			registerItemRender(BlockHandler.DLTslab2, 0);
		}
		registerItemRender(ACBlocks.dreadlands_infused_powerstone, 0);
		registerItemRender(ACBlocks.abyssal_coralium_ore, 0);
		registerItemRender(BlockHandler.Altar, 0);
		registerItemRender(ACBlocks.abyssal_stone_button, 0);
		registerItemRender(ACBlocks.abyssal_stone_pressure_plate, 0);
		registerItemRender(ACBlocks.darkstone_brick_fence, 0);
		registerItemRender(ACBlocks.darklands_oak_fence, 0);
		registerItemRender(ACBlocks.dreadlands_abyssalnite_ore, 0);
		registerItemRender(ACBlocks.dreaded_abyssalnite_ore, 0);
		registerItemRender(ACBlocks.dreadstone_brick, 0, "dreadbrick_0");
		registerItemRender(ACBlocks.dreadstone_brick, 1, "dreadbrick_1");
		registerItemRender(ACBlocks.dreadstone_brick, 2, "dreadbrick_2");
		registerItemRender(ACBlocks.abyssalnite_stone_brick, 0, "abydreadbrick_0");
		registerItemRender(ACBlocks.abyssalnite_stone_brick, 1, "abydreadbrick_1");
		registerItemRender(ACBlocks.abyssalnite_stone_brick, 2, "abydreadbrick_2");
		registerItemRender(ACBlocks.dreadlands_grass, 0);
		registerItemRender(ACBlocks.dreadlands_log, 0);
		registerItemRender(ACBlocks.dreadlands_leaves, 0);
		registerItemRender(ACBlocks.dreadlands_sapling, 0);
		registerItemRender(ACBlocks.dreadlands_planks, 0);
		registerItemRender(ACBlocks.dreaded_gateway, 0);
		registerItemRender(ACBlocks.dreaded_fire, 0);
		registerItemRender(ACBlocks.depths_ghoul_head, 0);
		registerItemRender(ACBlocks.pete_head, 0);
		registerItemRender(ACBlocks.mr_wilson_head, 0);
		registerItemRender(ACBlocks.dr_orange_head, 0);
		if(ACConfig.dreadstone_brick_stairs)
			registerItemRender(ACBlocks.dreadstone_brick_stairs, 0);
		registerItemRender(ACBlocks.dreadstone_brick_fence, 0);
		if(ACConfig.dreadstone_brick_slab) {
			registerItemRender(ACBlocks.dreadstone_brick_slab, 0);
			registerItemRender(BlockHandler.dreadbrickslab2, 0);
		}
		if(ACConfig.abyssalnite_stone_brick_stairs)
			registerItemRender(ACBlocks.abyssalnite_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.abyssalnite_stone_brick_fence, 0);
		if(ACConfig.abyssalnite_stone_brick_slab) {
			registerItemRender(ACBlocks.abyssalnite_stone_brick_slab, 0);
			registerItemRender(BlockHandler.abydreadbrickslab2, 0);
		}
		registerItemRender(ACBlocks.coralium_stone_brick, 0, "cstonebrick_0");
		registerItemRender(ACBlocks.coralium_stone_brick, 1, "cstonebrick_1");
		registerItemRender(ACBlocks.coralium_stone_brick, 2, "cstonebrick_2");
		registerItemRender(ACBlocks.coralium_stone_brick_fence, 0);
		if(ACConfig.coralium_stone_brick_slab) {
			registerItemRender(ACBlocks.coralium_stone_brick_slab, 0);
			registerItemRender(BlockHandler.cstonebrickslab2, 0);
		}
		if(ACConfig.coralium_stone_brick_stairs)
			registerItemRender(ACBlocks.coralium_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.coralium_stone_button, 0);
		registerItemRender(ACBlocks.coralium_stone_pressure_plate, 0);
		registerItemRender(ACBlocks.crystallizer_idle, 0);
		registerItemRender(ACBlocks.crystallizer_active, 0);
		registerItemRender(ACBlocks.transmutator_idle, 0);
		registerItemRender(ACBlocks.transmutator_active, 0);
		registerItemRender(ACBlocks.dreadguard_spawner, 0);
		registerItemRender(ACBlocks.chagaroth_spawner, 0);
		registerItemRender(ACBlocks.jzahar_spawner, 0);
		registerItemRender(ACBlocks.dreadlands_wood_fence, 0);
		registerItemRender(ACBlocks.nitre_ore, 0);
		registerItemRender(ACBlocks.abyssal_iron_ore, 0);
		registerItemRender(ACBlocks.abyssal_gold_ore, 0);
		registerItemRender(ACBlocks.abyssal_diamond_ore, 0);
		registerItemRender(ACBlocks.abyssal_nitre_ore, 0);
		registerItemRender(ACBlocks.abyssal_tin_ore, 0);
		registerItemRender(ACBlocks.abyssal_copper_ore, 0);
		registerItemRender(ACBlocks.pearlescent_coralium_ore, 0);
		registerItemRender(ACBlocks.liquified_coralium_ore, 0);
		registerItemRender(ACBlocks.solid_lava, 0);
		registerItemRender(ACBlocks.ethaxium_brick, 0, "ethaxiumbrick_0");
		registerItemRender(ACBlocks.ethaxium_brick, 1, "ethaxiumbrick_1");
		registerItemRender(ACBlocks.ethaxium_brick, 2, "ethaxiumbrick_2");
		registerItemRender(ACBlocks.ethaxium_pillar, 0);
		if(ACConfig.ethaxium_brick_stairs)
			registerItemRender(ACBlocks.ethaxium_brick_stairs, 0);
		if(ACConfig.ethaxium_brick_slab) {
			registerItemRender(ACBlocks.ethaxium_brick_slab, 0);
			registerItemRender(BlockHandler.ethaxiumslab2, 0);
		}
		registerItemRender(ACBlocks.ethaxium_brick_fence, 0);
		registerItemRender(ACBlocks.omothol_gateway, 0);
		registerItemRender(ACBlocks.omothol_fire, 0);
		registerItemRender(BlockHandler.house, 0);
		registerItemRender(ACBlocks.materializer, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick, 0, "darkethaxiumbrick_0");
		registerItemRender(ACBlocks.dark_ethaxium_brick, 1, "darkethaxiumbrick_1");
		registerItemRender(ACBlocks.dark_ethaxium_brick, 2, "darkethaxiumbrick_2");
		registerItemRender(ACBlocks.dark_ethaxium_pillar, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_stairs, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_slab, 0);
		registerItemRender(BlockHandler.darkethaxiumslab2, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_fence, 0);
		registerItemRender(ACBlocks.ritual_altar, 0, "ritualaltar_0");
		registerItemRender(ACBlocks.ritual_altar, 1, "ritualaltar_1");
		registerItemRender(ACBlocks.ritual_altar, 2, "ritualaltar_2");
		registerItemRender(ACBlocks.ritual_altar, 3, "ritualaltar_3");
		registerItemRender(ACBlocks.ritual_altar, 4, "ritualaltar_4");
		registerItemRender(ACBlocks.ritual_altar, 5, "ritualaltar_5");
		registerItemRender(ACBlocks.ritual_altar, 6, "ritualaltar_6");
		registerItemRender(ACBlocks.ritual_altar, 7, "ritualaltar_7");
		registerItemRender(ACBlocks.ritual_pedestal, 0, "ritualpedestal_0");
		registerItemRender(ACBlocks.ritual_pedestal, 1, "ritualpedestal_1");
		registerItemRender(ACBlocks.ritual_pedestal, 2, "ritualpedestal_2");
		registerItemRender(ACBlocks.ritual_pedestal, 3, "ritualpedestal_3");
		registerItemRender(ACBlocks.ritual_pedestal, 4, "ritualpedestal_4");
		registerItemRender(ACBlocks.ritual_pedestal, 5, "ritualpedestal_5");
		registerItemRender(ACBlocks.ritual_pedestal, 6, "ritualpedestal_6");
		registerItemRender(ACBlocks.ritual_pedestal, 7, "ritualpedestal_7");
		registerItemRender(ACBlocks.shoggoth_ooze, 0);
		registerItemRender(ACBlocks.shoggoth_biomass, 0);
		registerItemRender(ACBlocks.energy_pedestal, 0);
		registerItemRender(ACBlocks.monolith_pillar, 0);
		registerItemRender(ACBlocks.sacrificial_altar, 0);
		registerItemRender(ACBlocks.tiered_energy_pedestal, 0, "tieredenergypedestal_0");
		registerItemRender(ACBlocks.tiered_energy_pedestal, 1, "tieredenergypedestal_1");
		registerItemRender(ACBlocks.tiered_energy_pedestal, 2, "tieredenergypedestal_2");
		registerItemRender(ACBlocks.tiered_energy_pedestal, 3, "tieredenergypedestal_3");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 0, "tieredsacrificialaltar_0");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 1, "tieredsacrificialaltar_1");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 2, "tieredsacrificialaltar_2");
		registerItemRender(ACBlocks.tiered_sacrificial_altar, 3, "tieredsacrificialaltar_3");
		registerItemRender(ACBlocks.minion_of_the_gatekeeper_spawner, 0);
		registerItemRender(ACBlocks.mimic_fire, 0);
		registerItemRenders(ACBlocks.crystal_cluster, EnumCrystalType.values().length);
		registerItemRenders(ACBlocks.crystal_cluster2, EnumCrystalType2.values().length);
		registerItemRender(ACBlocks.energy_collector, 0);
		registerItemRender(ACBlocks.energy_relay, 0);
		registerItemRender(ACBlocks.energy_container, 0);
		registerItemRender(ACBlocks.tiered_energy_collector, 0, "tieredenergycollector_0");
		registerItemRender(ACBlocks.tiered_energy_collector, 1, "tieredenergycollector_1");
		registerItemRender(ACBlocks.tiered_energy_collector, 2, "tieredenergycollector_2");
		registerItemRender(ACBlocks.tiered_energy_collector, 3, "tieredenergycollector_3");
		registerItemRender(ACBlocks.tiered_energy_relay, 0, "owenergyrelay");
		registerItemRender(ACBlocks.tiered_energy_relay, 1, "awenergyrelay");
		registerItemRender(ACBlocks.tiered_energy_relay, 2, "dlenergyrelay");
		registerItemRender(ACBlocks.tiered_energy_relay, 3, "omtenergyrelay");
		registerItemRender(ACBlocks.tiered_energy_container, 0, "tieredenergycontainer_0");
		registerItemRender(ACBlocks.tiered_energy_container, 1, "tieredenergycontainer_1");
		registerItemRender(ACBlocks.tiered_energy_container, 2, "tieredenergycontainer_2");
		registerItemRender(ACBlocks.tiered_energy_container, 3, "tieredenergycontainer_3");
		registerItemRender(ACBlocks.abyssal_sand, 0);
		registerItemRender(ACBlocks.fused_abyssal_sand, 0);
		registerItemRender(ACBlocks.abyssal_sand_glass, 0);
		registerItemRender(ACBlocks.dreadlands_dirt, 0);
		if(ACConfig.abyssal_cobblestone_stairs)
			registerItemRender(ACBlocks.abyssal_cobblestone_stairs, 0);
		if(ACConfig.abyssal_cobblestone_slab) {
			registerItemRender(ACBlocks.abyssal_cobblestone_slab, 0);
			registerItemRender(BlockHandler.abycobbleslab2, 0);
		}
		if(ACConfig.abyssal_cobbblestone_wall)
			registerItemRender(ACBlocks.abyssal_cobblestone_wall, 0);
		if(ACConfig.dreadstone_cobblestone_stairs)
			registerItemRender(ACBlocks.dreadstone_cobblestone_stairs, 0);
		if(ACConfig.dreadstone_cobblestone_slab) {
			registerItemRender(ACBlocks.dreadstone_cobblestone_slab, 0);
			registerItemRender(BlockHandler.dreadcobbleslab2, 0);
		}
		if(ACConfig.dreadstone_cobblestone_wall)
			registerItemRender(ACBlocks.dreadstone_cobblestone_wall, 0);
		if(ACConfig.abyssalnite_cobblestone_stairs)
			registerItemRender(ACBlocks.abyssalnite_cobblestone_stairs, 0);
		if(ACConfig.abyssalnite_cobblestone_slab) {
			registerItemRender(ACBlocks.abyssalnite_cobblestone_slab, 0);
			registerItemRender(BlockHandler.abydreadcobbleslab2, 0);
		}
		if(ACConfig.abyssalnite_cobblestone_wall)
			registerItemRender(ACBlocks.abyssalnite_cobblestone_wall, 0);
		if(ACConfig.coralium_cobblestone_stairs)
			registerItemRender(ACBlocks.coralium_cobblestone_stairs, 0);
		if(ACConfig.coralium_cobblestone_slab) {
			registerItemRender(ACBlocks.coralium_cobblestone_slab, 0);
			registerItemRender(BlockHandler.cstonecobbleslab2, 0);
		}
		if(ACConfig.coralium_cobblestone_wall)
			registerItemRender(ACBlocks.coralium_cobblestone_wall, 0);
		registerItemRender(ACBlocks.luminous_thistle, 0);
		registerItemRender(ACBlocks.wastelands_thorn, 0);
		registerItemRender(ACBlocks.rending_pedestal, 0);
		registerItemRender(ACBlocks.state_transformer, 0);
		registerItemRender(ACBlocks.energy_depositioner, 0);
		registerItemRender(ACBlocks.calcified_stone, 0);
		registerItemRender(ACBlocks.multi_block, 0);
	}

	private void registerFluidModel(Block fluidBlock, String name) {
		Item item = Item.getItemFromBlock(fluidBlock);

		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation("abyssalcraft:fluid", name);

		ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

		ModelLoader.setCustomStateMapper(fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}

	protected void registerItemRender(Item item, int meta, String res){
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("abyssalcraft:" + res, "inventory"));
	}

	protected void registerItemRender(Item item, int meta){
		registerItemRender(item, meta, item.getRegistryName().getResourcePath());
	}

	protected void registerItemRenders(Item item, int metas){
		for(int i = 0; i < metas; i++)
			registerItemRender(item, i);
	}

	protected void registerItemRender(Block block, int meta, String res){
		registerItemRender(Item.getItemFromBlock(block), meta, res);
	}

	protected void registerItemRender(Block block, int meta){
		registerItemRender(block, meta, block.getRegistryName().getResourcePath());
	}

	protected void registerItemRenders(Block block, int metas){
		for(int i = 0; i < metas; i++)
			registerItemRender(block, i);
	}

	private ResourceLocation[] makerl(String...strings){
		ResourceLocation[] res = new ResourceLocation[strings.length];
		for(int i = 0; i < strings.length; i++)
			res[i] = new ResourceLocation("abyssalcraft", strings[i]);
		return res;
	}
}
