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
package com.shinoow.abyssalcraft.client.handlers;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.dimension.IAbyssalWorldProvider;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.IResearchable;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.spell.IScroll;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellUtils;
import com.shinoow.abyssalcraft.client.ClientProxy;
import com.shinoow.abyssalcraft.common.blocks.BlockACSlab;
import com.shinoow.abyssalcraft.common.items.ItemSpiritTablet;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.*;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.item.ItemCrystal;
import com.shinoow.abyssalcraft.lib.item.ItemCrystalFragment;
import com.shinoow.abyssalcraft.lib.item.ItemCrystalShard;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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

		if(event.getEntity().isHandActive() && !event.getEntity().getActiveItemStack().isEmpty()
				&& event.getEntity().getActiveItemStack().getItem() == ACItems.coralium_longbow) {
			int duration = event.getEntity().getItemInUseCount();
			float multiplier = duration / 20.0F;
			float fov = event.getFov();

			if(multiplier > 1.0F )
				multiplier = 1.0F;
			else
				multiplier *= multiplier;

			fov *= 1.0F - multiplier * 0.15F;
			event.setNewfov(fov);
		}

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

		if (world.getBlockState(pos).getBlock() == ACBlocks.mimic_fire)
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
			Vec3d var8 = pos.add(lookvec.x * var2,
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
				if(!mainStack.hasTagCompound())
					mainStack.setTagCompound(new NBTTagCompound());
				mode1 = mainStack.getTagCompound().getInteger("Mode");
				if(mode1 > -1){
					if(mode1 == 0)
						mode1 = 1;
					else mode1 = 0;
					Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("tooltip.staff.mode.1")+": "+TextFormatting.GOLD + I18n.format(mode1 == 1 ? "item.drainstaff.normal.name" : "item.gatewaykey.name")));
				}
			}
			if(!offStack.isEmpty() && offStack.getItem() == ACItems.staff_of_the_gatekeeper){
				if(!offStack.hasTagCompound())
					offStack.setTagCompound(new NBTTagCompound());
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

			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.interdimensional_cage) {
				if(!mainStack.hasTagCompound())
					mainStack.setTagCompound(new NBTTagCompound());
				if(!mainStack.getTagCompound().hasKey("Entity")) {
					RayTraceResult mov = getMouseOverExtended(3);

					if (mov != null)
						if (mov.entityHit != null && !mov.entityHit.isDead)
							if (mov.entityHit != Minecraft.getMinecraft().player )
								PacketDispatcher.sendToServer(new InterdimensionalCageMessage(mov.entityHit.getEntityId(), EnumHand.MAIN_HAND));
				}
			}
			if (!offStack.isEmpty() && offStack.getItem() == ACItems.interdimensional_cage) {
				if(!offStack.hasTagCompound())
					offStack.setTagCompound(new NBTTagCompound());
				if(!offStack.getTagCompound().hasKey("Entity")) {
					RayTraceResult mov = getMouseOverExtended(3);

					if (mov != null)
						if (mov.entityHit != null && !mov.entityHit.isDead)
							if (mov.entityHit != Minecraft.getMinecraft().player )
								PacketDispatcher.sendToServer(new InterdimensionalCageMessage(mov.entityHit.getEntityId(), EnumHand.OFF_HAND));
				}
			}
		}
		if(ClientProxy.spirit_tablet_mode.isPressed()) {
			ItemStack mainStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack offStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.OFF_HAND);
			int mode1 = -1, mode2 = -1;

			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.spirit_tablet){
				if(!mainStack.hasTagCompound())
					mainStack.setTagCompound(new NBTTagCompound());
				mode1 = mainStack.getTagCompound().getInteger("Mode");
				if(mode1 > -1){
					mode1 = mode1 == 0 ? 1 : mode1 == 1 ? 2 : 0;
					Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("tooltip.staff.mode.1")+": "+TextFormatting.GOLD + ItemSpiritTablet.getMode(mode1)));
				}
			}
			if(!offStack.isEmpty() && offStack.getItem() == ACItems.spirit_tablet){
				if(!offStack.hasTagCompound())
					offStack.setTagCompound(new NBTTagCompound());
				mode2 = offStack.getTagCompound().getInteger("Mode");
				if(mode2 > -1){
					mode2 = mode2 == 0 ? 1 : mode2 == 1 ? 2 : 0;
					Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("tooltip.staff.mode.1")+": "+TextFormatting.GOLD + ItemSpiritTablet.getMode(mode2)));
				}
			}

			if(mode1 > -1 || mode2 > -1)
				PacketDispatcher.sendToServer(new SpiritTabletMessage(mode1, mode2));
		}
		if(ClientProxy.spirit_tablet_filter.isPressed()) {
			ItemStack mainStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack offStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.OFF_HAND);
			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.spirit_tablet ||
					!offStack.isEmpty() && offStack.getItem() == ACItems.spirit_tablet)
				PacketDispatcher.sendToServer(new SpiritTabletMessage(true));
		}
		if(ClientProxy.spirit_tablet_path.isPressed()) {
			ItemStack mainStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack offStack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.OFF_HAND);
			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.spirit_tablet ||
					!offStack.isEmpty() && offStack.getItem() == ACItems.spirit_tablet) {
				PacketDispatcher.sendToServer(new SpiritTabletMessage(true, 0));
				Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation("message.configurator.5"));
			}
		}
	}

	@SubscribeEvent
	public void tooltipStuff(ItemTooltipEvent event){
		ItemStack stack = event.getItemStack();

		if(stack.getItem() instanceof IEnergyContainerItem)
			event.getToolTip().add(1, String.format("%d/%d PE", (int)((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack), ((IEnergyContainerItem)stack.getItem()).getMaxEnergy(stack)));

		if(stack.getItem() instanceof IScroll) {
			Spell spell = SpellUtils.getSpell(stack);
			if(spell != null){
				event.getToolTip().add(1, I18n.format(NecronomiconText.LABEL_SPELL_NAME)+": "+TextFormatting.AQUA+spell.getLocalizedName());
				event.getToolTip().add(2, I18n.format(NecronomiconText.LABEL_SPELL_PE)+": "+(int)spell.getReqEnergy());
				event.getToolTip().add(3, I18n.format(NecronomiconText.LABEL_SPELL_TYPE)+": "+TextFormatting.GOLD+I18n.format(NecronomiconText.getSpellType(spell.requiresCharging())));
			}
		}
		if(stack.getItem() instanceof ICrystal)
			event.getToolTip().add(String.format("%s: %s", I18n.format("tooltip.crystal"), ((ICrystal) stack.getItem()).getFormula(stack)));

		if(!APIUtils.display_names)
			if(stack.getItem() instanceof IResearchable && event.getEntityPlayer() != null){
				IResearchItem research = ((IResearchable)stack.getItem()).getResearchItem(stack);
				if(!NecroDataCapability.getCap(event.getEntityPlayer()).isUnlocked(research, event.getEntityPlayer())) {
					event.getToolTip().remove(0);
					event.getToolTip().add(0, "...What's this?");
				}
			}
	}

	@SubscribeEvent
	public void tooltipFont(RenderTooltipEvent.Pre event) {
		if(!APIUtils.display_names && event.getLines().get(0).startsWith("\u00A7f...What's this?"))
			event.setFontRenderer(AbyssalCraftAPI.getAkloFont());
	}

	@SubscribeEvent
	public void renderTick(RenderTickEvent event) {
		if(event.phase == Phase.START)
			partialTicks = event.renderTickTime;
	}

	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if(gui == null || !gui.doesGuiPauseGame())
				partialTicks = 0;
		}
	}

	@SubscribeEvent
	public void voidFog(LivingUpdateEvent event) {
		if(event.getEntityLiving() == Minecraft.getMinecraft().player)
			doVoidFogParticles(event.getEntityLiving().world, event.getEntityLiving());
	}

	public void doVoidFogParticles(World world, Entity entity)
	{
		if(Minecraft.getMinecraft().isGamePaused() || !world.isRemote) return;
		if(!(world.provider instanceof IAbyssalWorldProvider)) return;
		byte b0 = 16;
		byte b1 = 14;
		int x = MathHelper.floor(entity.posX);
		int y = MathHelper.floor(entity.posY);
		int z = MathHelper.floor(entity.posZ);
		MutableBlockPos pos = new MutableBlockPos();
		boolean darkRealm = world.provider.getDimension() == ACLib.dark_realm_id;
		for (int l = 0; l < 100; ++l)
		{
			int i1 = x + world.rand.nextInt(b0) - world.rand.nextInt(b0);
			int j1 = y + world.rand.nextInt(b0) - world.rand.nextInt(b0);
			int k1 = z + world.rand.nextInt(b0) - world.rand.nextInt(b0);
			if(darkRealm) {
				if(j1 > y+b1 || j1+b1 < y) return;
				if(i1 > x+b1 || i1+b1 < x) return;
				if(k1 > z+b1 || k1+b1 < z) return;
			}
			pos.setPos(i1, j1, k1);
			IBlockState state = world.getBlockState(pos);

			if (state.getMaterial() == Material.AIR)
				if (world.getLightFromNeighbors(pos) == 0)
				{
					boolean canSpawn = false;
					if(darkRealm)
						canSpawn = world.canBlockSeeSky(pos);
					else if(world.provider.getDimension() == ACLib.omothol_id){
						if(world.getBlockState(pos.down()).getMaterial() != Material.AIR ||
								world.getBlockState(pos.down(2)).getMaterial() != Material.AIR ||
								world.getBlockState(pos.down(3)).getMaterial() != Material.AIR)
							canSpawn = true;
					} else if(j1 <= 6)
						if(world.getBlockState(pos.up()).getMaterial() != Material.AIR ||
						world.getBlockState(pos.up(2)).getMaterial() != Material.AIR)
							canSpawn = true;

					if(canSpawn)
						world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, i1 + world.rand.nextFloat(), j1 + world.rand.nextFloat(), k1 + world.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
		}
	}

	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event){
		if(event.getEntity() == Minecraft.getMinecraft().player)
			AbyssalCraft.proxy.resetParticleCount();
	}

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event){

		ModelBakery.registerItemVariants(ACItems.staff_of_the_gatekeeper, makerl("staff", "staff2"));

		registerFluidModel(ACBlocks.liquid_coralium, "cor");
		registerFluidModel(ACBlocks.liquid_antimatter, "anti");

		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_leaves, new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadwood_leaves, new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
		ModelLoader.setCustomStateMapper(ACBlocks.oblivion_deathbomb, new StateMap.Builder().ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(ACBlocks.odb_core, new StateMap.Builder().ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_brick_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_cobblestone_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssal_stone_brick_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_brick_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.elysian_stone_brick_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.coralium_stone_brick_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.ethaxium_brick_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dark_ethaxium_brick_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_sapling, new StateMap.Builder().ignore(BlockSapling.TYPE).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadwood_sapling, new StateMap.Builder().ignore(BlockSapling.TYPE).build());
		ModelLoader.setCustomStateMapper(ACBlocks.mimic_fire, new StateMap.Builder().ignore(BlockFire.AGE).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darkstone_cobblestone_wall, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssal_cobblestone_wall, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_cobblestone_wall, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
		ModelLoader.setCustomStateMapper(ACBlocks.elysian_cobblestone_wall, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
		ModelLoader.setCustomStateMapper(ACBlocks.coralium_cobblestone_wall, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
		ModelLoader.setCustomStateMapper(ACBlocks.abyssal_cobblestone_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadstone_cobblestone_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.elysian_cobblestone_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.coralium_cobblestone_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadwood_slab, new StateMap.Builder().ignore(BlockACSlab.VARIANT_PROPERTY).build());
		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadwood_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());

		ModelLoader.setCustomStateMapper(ACBlocks.darklands_oak_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(ACBlocks.dreadwood_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.cthulhu_statue), 0, new ModelResourceLocation("abyssalcraft:cthulhustatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_cthulhu_statue), 0, new ModelResourceLocation("abyssalcraft:cthulhustatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.hastur_statue), 0, new ModelResourceLocation("abyssalcraft:hasturstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_hastur_statue), 0, new ModelResourceLocation("abyssalcraft:hasturstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.jzahar_statue), 0, new ModelResourceLocation("abyssalcraft:jzaharstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_jzahar_statue), 0, new ModelResourceLocation("abyssalcraft:jzaharstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.azathoth_statue), 0, new ModelResourceLocation("abyssalcraft:azathothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_azathoth_statue), 0, new ModelResourceLocation("abyssalcraft:azathothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.nyarlathotep_statue), 0, new ModelResourceLocation("abyssalcraft:nyarlathotepstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_nyarlathotep_statue), 0, new ModelResourceLocation("abyssalcraft:nyarlathotepstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.yog_sothoth_statue), 0, new ModelResourceLocation("abyssalcraft:yogsothothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_yog_sothoth_statue), 0, new ModelResourceLocation("abyssalcraft:yogsothothstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.shub_niggurath_statue), 0, new ModelResourceLocation("abyssalcraft:shubniggurathstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.decorative_shub_niggurath_statue), 0, new ModelResourceLocation("abyssalcraft:shubniggurathstatue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ACBlocks.oblivion_deathbomb), 0, new ModelResourceLocation("abyssalcraft:odb", "inventory"));

		ModelLoader.setCustomModelResourceLocation(ACItems.cudgel, 0, new ModelResourceLocation("abyssalcraft:cudgel", "inventory"));
		ModelLoader.setCustomModelResourceLocation(ACItems.dreadium_katana, 0, new ModelResourceLocation("abyssalcraft:dreadkatana", "inventory"));

		ModelLoader.setCustomMeshDefinition(ACItems.staff_of_the_gatekeeper, stack -> stack.hasTagCompound() && stack.getTagCompound().getInteger("Mode") == 1 ? new ModelResourceLocation("abyssalcraft:staff2", "inventory") : new ModelResourceLocation("abyssalcraft:staff", "inventory"));

		registerItemRender(ItemHandler.devsword, 0);
		registerItemRender(ItemHandler.shoggoth_projectile, 0);
		registerItemRender(ACItems.oblivion_catalyst, 0);
		registerItemRender(ACItems.gateway_key, 0);
		registerItemRender(ACItems.powerstone_tracker, 0);
		registerItemRender(ACItems.eye_of_the_abyss, 0);
		registerItemRender(ACItems.dreadlands_infused_gateway_key, 0);
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
		registerItemRender(ACItems.carbon_cluster, 0);
		registerItemRender(ACItems.dense_carbon_cluster, 0);
		registerItemRender(ACItems.methane, 0);
		registerItemRender(ACItems.nitre, 0);
		registerItemRender(ACItems.sulfur, 0);
		InitHandler.INSTANCE.ITEMS.stream().filter(i -> i instanceof ItemCrystal).forEach(i -> {
			if(i instanceof ItemCrystalFragment)
				registerItemRender(i, 0, "crystalfragment");
			else if(i instanceof ItemCrystalShard)
				registerItemRender(i, 0, "crystalshard");
			else
				registerItemRender(i, 0, "crystal");
		});
		registerItemRender(ACItems.dread_cloth, 0);
		registerItemRender(ACItems.dreadium_plate, 0);
		registerItemRender(ACItems.dreadium_katana_blade, 0);
		registerItemRender(ACItems.dreadium_katana_hilt, 0);
		registerItemRender(ACItems.dread_plagued_gateway_key, 0);
		registerItemRender(ACItems.omothol_forged_gateway_key, 0);
		registerItemRender(ACItems.dreadium_samurai_boots, 0);
		registerItemRender(ACItems.dreadium_samurai_helmet, 0);
		registerItemRender(ACItems.dreadium_samurai_chestplate, 0);
		registerItemRender(ACItems.dreadium_samurai_leggings, 0);
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
		registerItemRender(ACItems.coin, 0);
		registerItemRender(ACItems.token_of_jzahar, 0);
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
		registerItemRender(ACItems.overworld_shoggoth_flesh, 0, "shoggothflesh_overworld");
		registerItemRender(ACItems.abyssal_shoggoth_flesh, 0, "shoggothflesh_abyssalwasteland");
		registerItemRender(ACItems.dreaded_shoggoth_flesh, 0, "shoggothflesh_dreadlands");
		registerItemRender(ACItems.omothol_shoggoth_flesh, 0, "shoggothflesh_omothol");
		registerItemRender(ACItems.shadow_shoggoth_flesh, 0, "shoggothflesh_darkrealm");
		registerItemRender(ACItems.abyssalnite_nugget, 0);
		registerItemRender(ACItems.refined_coralium_nugget, 0);
		registerItemRender(ACItems.dreadium_nugget, 0);
		registerItemRender(ACItems.ethaxium_nugget, 0);
		registerItemRender(ACItems.staff_of_rending, 0);
		registerItemRender(ACItems.abyssal_wasteland_staff_of_rending, 0);
		registerItemRender(ACItems.dreadlands_staff_of_rending, 0);
		registerItemRender(ACItems.omothol_staff_of_rending, 0);
		registerItemRender(ACItems.abyssal_wasteland_essence, 0);
		registerItemRender(ACItems.dreadlands_essence, 0);
		registerItemRender(ACItems.omothol_essence, 0);
		registerItemRender(ACItems.skin_of_the_abyssal_wasteland, 0);
		registerItemRender(ACItems.skin_of_the_dreadlands, 0);
		registerItemRender(ACItems.skin_of_omothol, 0);
		registerItemRender(ACItems.ritual_charm, 0, "ritualcharm_empty");
		registerItemRender(ACItems.range_ritual_charm, 0, "ritualcharm_range");
		registerItemRender(ACItems.duration_ritual_charm, 0, "ritualcharm_duration");
		registerItemRender(ACItems.power_ritual_charm, 0, "ritualcharm_power");
		registerItemRender(ACItems.cthulhu_charm, 0, "cthulhucharm");
		registerItemRender(ACItems.range_cthulhu_charm, 0, "cthulhucharm");
		registerItemRender(ACItems.duration_cthulhu_charm, 0, "cthulhucharm");
		registerItemRender(ACItems.power_cthulhu_charm, 0, "cthulhucharm");
		registerItemRender(ACItems.hastur_charm, 0, "hasturcharm");
		registerItemRender(ACItems.range_hastur_charm, 0, "hasturcharm");
		registerItemRender(ACItems.duration_hastur_charm, 0, "hasturcharm");
		registerItemRender(ACItems.power_hastur_charm, 0, "hasturcharm");
		registerItemRender(ACItems.jzahar_charm, 0, "jzaharcharm");
		registerItemRender(ACItems.range_jzahar_charm, 0, "jzaharcharm");
		registerItemRender(ACItems.duration_jzahar_charm, 0, "jzaharcharm");
		registerItemRender(ACItems.power_jzahar_charm, 0, "jzaharcharm");
		registerItemRender(ACItems.azathoth_charm, 0, "azathothcharm");
		registerItemRender(ACItems.range_azathoth_charm, 0, "azathothcharm");
		registerItemRender(ACItems.duration_azathoth_charm, 0, "azathothcharm");
		registerItemRender(ACItems.power_azathoth_charm, 0, "azathothcharm");
		registerItemRender(ACItems.nyarlathotep_charm, 0, "nyarlathotepcharm");
		registerItemRender(ACItems.range_nyarlathotep_charm, 0, "nyarlathotepcharm");
		registerItemRender(ACItems.duration_nyarlathotep_charm, 0, "nyarlathotepcharm");
		registerItemRender(ACItems.power_nyarlathotep_charm, 0, "nyarlathotepcharm");
		registerItemRender(ACItems.yog_sothoth_charm, 0, "yogsothothcharm");
		registerItemRender(ACItems.range_yog_sothoth_charm, 0, "yogsothothcharm");
		registerItemRender(ACItems.duration_yog_sothoth_charm, 0, "yogsothothcharm");
		registerItemRender(ACItems.power_yog_sothoth_charm, 0, "yogsothothcharm");
		registerItemRender(ACItems.shub_niggurath_charm, 0, "shubniggurathcharm");
		registerItemRender(ACItems.range_shub_niggurath_charm, 0, "shubniggurathcharm");
		registerItemRender(ACItems.duration_shub_niggurath_charm, 0, "shubniggurathcharm");
		registerItemRender(ACItems.power_shub_niggurath_charm, 0, "shubniggurathcharm");
		registerItemRender(ACItems.essence_of_the_gatekeeper, 0);
		registerItemRender(ACItems.interdimensional_cage, 0);
		registerItemRender(ACItems.stone_tablet, 0);
		registerItemRender(ACItems.basic_scroll, 0);
		registerItemRender(ACItems.lesser_scroll, 0);
		registerItemRender(ACItems.moderate_scroll, 0);
		registerItemRender(ACItems.greater_scroll, 0);
		registerItemRender(ACItems.antimatter_scroll, 0);
		registerItemRender(ACItems.oblivion_scroll, 0);
		registerItemRender(ACItems.coralium_plague_antidote, 0);
		registerItemRender(ACItems.dread_plague_antidote, 0);
		registerItemRender(ACItems.darklands_oak_door, 0);
		registerItemRender(ACItems.dreadlands_door, 0);
		registerItemRender(ACItems.charcoal, 0);
		registerItemRender(ACItems.spirit_tablet, 0);
		registerItemRender(ACItems.spirit_tablet_shard_0, 0);
		registerItemRender(ACItems.spirit_tablet_shard_1, 0);
		registerItemRender(ACItems.spirit_tablet_shard_2, 0);
		registerItemRender(ACItems.spirit_tablet_shard_3, 0);
		registerItemRender(ACItems.silver_key, 0);
		registerItemRender(ACItems.book_of_many_faces, 0);
		registerItemRender(ACItems.generic_meat, 0);
		registerItemRender(ACItems.cooked_generic_meat, 0);
		registerItemRender(ACItems.lost_page, 0);
		registerItemRender(ACItems.scriptures_of_omniscience, 0);
		registerItemRender(ACItems.sealing_key, 0);

		registerItemRender(ACBlocks.darkstone, 0, "darkstone");
		registerItemRender(ACBlocks.abyssal_stone, 0, "abystone");
		registerItemRender(ACBlocks.dreadstone, 0, "dreadstone");
		registerItemRender(ACBlocks.elysian_stone, 0, "elysian_stone");
		registerItemRender(ACBlocks.coralium_stone, 0, "cstone");
		registerItemRender(ACBlocks.ethaxium, 0, "ethaxium");
		registerItemRender(ACBlocks.omothol_stone, 0, "omotholstone");
		registerItemRender(ACBlocks.monolith_stone, 0, "monolithstone");
		registerItemRender(ACBlocks.darkstone_cobblestone, 0, "darkstone_cobble");
		registerItemRender(ACBlocks.abyssal_cobblestone, 0, "abyssalcobblestone");
		registerItemRender(ACBlocks.dreadstone_cobblestone, 0, "dreadstonecobblestone");
		registerItemRender(ACBlocks.elysian_cobblestone, 0, "elysian_cobblestone");
		registerItemRender(ACBlocks.coralium_cobblestone, 0, "coraliumcobblestone");
		registerItemRender(ACBlocks.darkstone_brick, 0, "darkstone_brick_0");
		registerItemRender(ACBlocks.chiseled_darkstone_brick, 0, "darkstone_brick_1");
		registerItemRender(ACBlocks.cracked_darkstone_brick, 0, "darkstone_brick_2");
		registerItemRender(ACBlocks.glowing_darkstone_bricks, 0);
		registerItemRender(ACBlocks.darkstone_brick_slab, 0);
		registerItemRender(BlockHandler.Darkbrickslab2, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_slab, 0);
		registerItemRender(BlockHandler.Darkcobbleslab2, 0);
		registerItemRender(ACBlocks.darkstone_brick_stairs, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.darklands_oak_leaves, 0);
		registerItemRender(ACBlocks.darklands_oak_wood, 0);
		registerItemRender(ACBlocks.darklands_oak_wood_2, 0);
		registerItemRender(ACBlocks.darklands_oak_sapling, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick, 0, "abybrick_0");
		registerItemRender(ACBlocks.chiseled_abyssal_stone_brick, 0, "abybrick_1");
		registerItemRender(ACBlocks.cracked_abyssal_stone_brick, 0, "abybrick_2");
		registerItemRender(ACBlocks.abyssal_stone_brick_slab, 0);
		registerItemRender(BlockHandler.abyslab2, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.coralium_ore, 0);
		registerItemRender(ACBlocks.abyssalnite_ore, 0);
		registerItemRender(ACBlocks.abyssal_stone_brick_fence, 0);
		registerItemRender(ACBlocks.darkstone_cobblestone_wall, 0);
		registerItemRender(ACBlocks.block_of_abyssalnite, 0, "abyblock");
		registerItemRender(ACBlocks.block_of_refined_coralium, 0, "corblock");
		registerItemRender(ACBlocks.block_of_dreadium, 0, "dreadiumblock");
		registerItemRender(ACBlocks.block_of_ethaxium, 0, "ethaxiumblock");
		registerItemRender(ACBlocks.coralium_infused_stone, 0);
		registerItemRender(ACBlocks.odb_core, 0);
		registerItemRender(ACBlocks.wooden_crate, 0);
		registerItemRender(ACBlocks.darkstone_slab, 0);
		registerItemRender(BlockHandler.Darkstoneslab2, 0);
		registerItemRender(ACBlocks.darkstone_button, 0);
		registerItemRender(ACBlocks.darkstone_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_planks, 0);
		registerItemRender(ACBlocks.darklands_oak_button, 0);
		registerItemRender(ACBlocks.darklands_oak_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_stairs, 0);
		registerItemRender(ACBlocks.darklands_oak_slab, 0);
		registerItemRender(BlockHandler.DLTslab2, 0);
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
		registerItemRender(ACBlocks.chiseled_dreadstone_brick, 0, "dreadbrick_1");
		registerItemRender(ACBlocks.cracked_dreadstone_brick, 0, "dreadbrick_2");
		registerItemRender(ACBlocks.elysian_stone_brick, 0, "elysian_brick_0");
		registerItemRender(ACBlocks.chiseled_elysian_stone_brick, 0, "elysian_brick_1");
		registerItemRender(ACBlocks.cracked_elysian_stone_brick, 0, "elysian_brick_2");
		registerItemRender(ACBlocks.dreadlands_grass, 0);
		registerItemRender(ACBlocks.dreadwood_log, 0);
		registerItemRender(ACBlocks.dreadwood_leaves, 0);
		registerItemRender(ACBlocks.dreadwood_sapling, 0);
		registerItemRender(ACBlocks.dreadwood_planks, 0);
		registerItemRender(ACBlocks.depths_ghoul_head, 0);
		registerItemRender(ACBlocks.pete_head, 0);
		registerItemRender(ACBlocks.mr_wilson_head, 0);
		registerItemRender(ACBlocks.dr_orange_head, 0);
		registerItemRender(ACBlocks.dreadstone_brick_stairs, 0);
		registerItemRender(ACBlocks.dreadstone_brick_fence, 0);
		registerItemRender(ACBlocks.dreadstone_brick_slab, 0);
		registerItemRender(BlockHandler.dreadbrickslab2, 0);
		registerItemRender(ACBlocks.elysian_stone_brick_stairs, 0);
		registerItemRender(ACBlocks.elysian_stone_brick_fence, 0);
		registerItemRender(ACBlocks.elysian_stone_brick_slab, 0);
		registerItemRender(BlockHandler.elysianbrickslab2, 0);
		registerItemRender(ACBlocks.coralium_stone_brick, 0, "cstonebrick_0");
		registerItemRender(ACBlocks.chiseled_coralium_stone_brick, 0, "cstonebrick_1");
		registerItemRender(ACBlocks.cracked_coralium_stone_brick, 0, "cstonebrick_2");
		registerItemRender(ACBlocks.coralium_stone_brick_fence, 0);
		registerItemRender(ACBlocks.coralium_stone_brick_slab, 0);
		registerItemRender(BlockHandler.cstonebrickslab2, 0);
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
		registerItemRender(ACBlocks.dreadwood_fence, 0);
		registerItemRender(ACBlocks.nitre_ore, 0);
		registerItemRender(ACBlocks.abyssal_iron_ore, 0);
		registerItemRender(ACBlocks.abyssal_gold_ore, 0);
		registerItemRender(ACBlocks.abyssal_diamond_ore, 0);
		registerItemRender(ACBlocks.abyssal_nitre_ore, 0);
		registerItemRender(ACBlocks.pearlescent_coralium_ore, 0);
		registerItemRender(ACBlocks.liquified_coralium_ore, 0);
		registerItemRender(ACBlocks.solid_lava, 0);
		registerItemRender(ACBlocks.ethaxium_brick, 0, "ethaxiumbrick_0");
		registerItemRender(ACBlocks.chiseled_ethaxium_brick, 0, "ethaxiumbrick_1");
		registerItemRender(ACBlocks.cracked_ethaxium_brick, 0, "ethaxiumbrick_2");
		registerItemRender(ACBlocks.ethaxium_pillar, 0);
		registerItemRender(ACBlocks.ethaxium_brick_stairs, 0);
		registerItemRender(ACBlocks.ethaxium_brick_slab, 0);
		registerItemRender(BlockHandler.ethaxiumslab2, 0);
		registerItemRender(ACBlocks.ethaxium_brick_fence, 0);
		registerItemRender(BlockHandler.house, 0);
		registerItemRender(ACBlocks.materializer, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick, 0, "darkethaxiumbrick_0");
		registerItemRender(ACBlocks.chiseled_dark_ethaxium_brick, 0, "darkethaxiumbrick_1");
		registerItemRender(ACBlocks.cracked_dark_ethaxium_brick, 0, "darkethaxiumbrick_2");
		registerItemRender(ACBlocks.dark_ethaxium_pillar, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_stairs, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_slab, 0);
		registerItemRender(BlockHandler.darkethaxiumslab2, 0);
		registerItemRender(ACBlocks.dark_ethaxium_brick_fence, 0);
		registerItemRender(ACBlocks.ritual_altar_stone, 0, "ritualaltar_0");
		registerItemRender(ACBlocks.ritual_altar_darkstone, 0, "ritualaltar_1");
		registerItemRender(ACBlocks.ritual_altar_abyssal_stone, 0, "ritualaltar_2");
		registerItemRender(ACBlocks.ritual_altar_coralium_stone, 0, "ritualaltar_3");
		registerItemRender(ACBlocks.ritual_altar_dreadstone, 0, "ritualaltar_4");
		registerItemRender(ACBlocks.ritual_altar_elysian_stone, 0, "ritualaltar_5");
		registerItemRender(ACBlocks.ritual_altar_ethaxium, 0, "ritualaltar_6");
		registerItemRender(ACBlocks.ritual_altar_dark_ethaxium, 0, "ritualaltar_7");
		registerItemRender(ACBlocks.ritual_pedestal_stone, 0, "ritualpedestal_0");
		registerItemRender(ACBlocks.ritual_pedestal_darkstone, 0, "ritualpedestal_1");
		registerItemRender(ACBlocks.ritual_pedestal_abyssal_stone, 0, "ritualpedestal_2");
		registerItemRender(ACBlocks.ritual_pedestal_coralium_stone, 0, "ritualpedestal_3");
		registerItemRender(ACBlocks.ritual_pedestal_dreadstone, 0, "ritualpedestal_4");
		registerItemRender(ACBlocks.ritual_pedestal_elysian_stone, 0, "ritualpedestal_5");
		registerItemRender(ACBlocks.ritual_pedestal_ethaxium, 0, "ritualpedestal_6");
		registerItemRender(ACBlocks.ritual_pedestal_dark_ethaxium, 0, "ritualpedestal_7");
		registerItemRender(ACBlocks.shoggoth_ooze, 0);
		registerItemRender(ACBlocks.shoggoth_biomass, 0);
		registerItemRender(ACBlocks.energy_pedestal, 0);
		registerItemRender(ACBlocks.monolith_pillar, 0);
		registerItemRender(ACBlocks.sacrificial_altar, 0);
		registerItemRender(ACBlocks.overworld_energy_pedestal, 0, "tieredenergypedestal_0");
		registerItemRender(ACBlocks.abyssal_wasteland_energy_pedestal, 0, "tieredenergypedestal_1");
		registerItemRender(ACBlocks.dreadlands_energy_pedestal, 0, "tieredenergypedestal_2");
		registerItemRender(ACBlocks.omothol_energy_pedestal, 0, "tieredenergypedestal_3");
		registerItemRender(ACBlocks.overworld_sacrificial_altar, 0, "tieredsacrificialaltar_0");
		registerItemRender(ACBlocks.abyssal_wasteland_sacrificial_altar, 0, "tieredsacrificialaltar_1");
		registerItemRender(ACBlocks.dreadlands_sacrificial_altar, 0, "tieredsacrificialaltar_2");
		registerItemRender(ACBlocks.omothol_sacrificial_altar, 0, "tieredsacrificialaltar_3");
		registerItemRender(ACBlocks.minion_of_the_gatekeeper_spawner, 0);
		registerItemRender(ACBlocks.mimic_fire, 0);
		registerItemRender(ACBlocks.iron_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.gold_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.sulfur_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.carbon_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.oxygen_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.hydrogen_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.nitrogen_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.phosphorus_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.potassium_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.nitrate_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.methane_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.redstone_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.abyssalnite_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.coralium_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.dreadium_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.blaze_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.silicon_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.magnesium_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.aluminium_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.silica_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.alumina_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.magnesia_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.zinc_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.calcium_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.beryllium_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.beryl_crystal_cluster, 0, "crystalcluster");
		registerItemRender(ACBlocks.energy_collector, 0);
		registerItemRender(ACBlocks.energy_relay, 0);
		registerItemRender(ACBlocks.energy_container, 0);
		registerItemRender(ACBlocks.overworld_energy_collector, 0, "tieredenergycollector_0");
		registerItemRender(ACBlocks.abyssal_wasteland_energy_collector, 0, "tieredenergycollector_1");
		registerItemRender(ACBlocks.dreadlands_energy_collector, 0, "tieredenergycollector_2");
		registerItemRender(ACBlocks.omothol_energy_collector, 0, "tieredenergycollector_3");
		registerItemRender(ACBlocks.overworld_energy_relay, 0, "owenergyrelay");
		registerItemRender(ACBlocks.abyssal_wasteland_energy_relay, 0, "awenergyrelay");
		registerItemRender(ACBlocks.dreadlands_energy_relay, 0, "dlenergyrelay");
		registerItemRender(ACBlocks.omothol_energy_relay, 0, "omtenergyrelay");
		registerItemRender(ACBlocks.overworld_energy_container, 0, "tieredenergycontainer_0");
		registerItemRender(ACBlocks.abyssal_wasteland_energy_container, 0, "tieredenergycontainer_1");
		registerItemRender(ACBlocks.dreadlands_energy_container, 0, "tieredenergycontainer_2");
		registerItemRender(ACBlocks.omothol_energy_container, 0, "tieredenergycontainer_3");
		registerItemRender(ACBlocks.abyssal_sand, 0);
		registerItemRender(ACBlocks.fused_abyssal_sand, 0);
		registerItemRender(ACBlocks.abyssal_sand_glass, 0);
		registerItemRender(ACBlocks.dreadlands_dirt, 0);
		registerItemRender(ACBlocks.abyssal_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.abyssal_cobblestone_slab, 0);
		registerItemRender(BlockHandler.abycobbleslab2, 0);
		registerItemRender(ACBlocks.abyssal_cobblestone_wall, 0);
		registerItemRender(ACBlocks.dreadstone_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.dreadstone_cobblestone_slab, 0);
		registerItemRender(BlockHandler.dreadcobbleslab2, 0);
		registerItemRender(ACBlocks.dreadstone_cobblestone_wall, 0);
		registerItemRender(ACBlocks.elysian_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.elysian_cobblestone_slab, 0);
		registerItemRender(BlockHandler.elysiancobbleslab2, 0);
		registerItemRender(ACBlocks.elysian_cobblestone_wall, 0);
		registerItemRender(ACBlocks.coralium_cobblestone_stairs, 0);
		registerItemRender(ACBlocks.coralium_cobblestone_slab, 0);
		registerItemRender(BlockHandler.cstonecobbleslab2, 0);
		registerItemRender(ACBlocks.coralium_cobblestone_wall, 0);
		registerItemRender(ACBlocks.luminous_thistle, 0);
		registerItemRender(ACBlocks.wastelands_thorn, 0);
		registerItemRender(ACBlocks.rending_pedestal, 0);
		registerItemRender(ACBlocks.state_transformer, 0);
		registerItemRender(ACBlocks.energy_depositioner, 0);
		registerItemRender(ACBlocks.calcified_stone, 0);
		registerItemRender(ACBlocks.multi_block, 0);
		registerItemRender(ACBlocks.sequential_brewing_stand, 0);
		registerItemRender(ACBlocks.portal_anchor, 0);
		registerItemRender(ACBlocks.dead_tree_log, 0);
		registerItemRender(ACBlocks.idol_of_fading, 0);
		registerItemRender(ACBlocks.abyssal_abyssalnite_ore, 0);
		registerItemRender(ACBlocks.mural, 0);
		registerItemRender(ACBlocks.dreadwood_slab, 0);
		registerItemRender(BlockHandler.dreadwoodslab2, 0);
		registerItemRender(ACBlocks.dreadwood_stairs, 0);
		registerItemRender(ACBlocks.dreadwood_button, 0);
		registerItemRender(ACBlocks.dreadwood_pressure_plate, 0);
		registerItemRender(ACBlocks.darklands_oak_fence_gate, 0);
		registerItemRender(ACBlocks.dreadwood_fence_gate, 0);
		registerItemRender(ACBlocks.research_table, 0);
		registerItemRender(ACBlocks.dreadlands_muck, 0);
		registerItemRender(ACBlocks.sealing_lock, 0);
		registerItemRender(ACBlocks.unlocked_sealing_lock, 0);
		registerItemRender(ACBlocks.tombstone_stone, 0);
		registerItemRender(ACBlocks.tombstone_abyssal_stone, 0);
		registerItemRender(ACBlocks.tombstone_coralium_stone, 0);
		registerItemRender(ACBlocks.tombstone_darkstone, 0);
		registerItemRender(ACBlocks.tombstone_dreadstone, 0);
		registerItemRender(ACBlocks.tombstone_elysian_stone, 0);
		registerItemRender(ACBlocks.tombstone_ethaxium, 0);
		registerItemRender(ACBlocks.tombstone_monolith_stone, 0);
		registerItemRender(ACBlocks.tombstone_omothol_stone, 0);
		registerItemRender(ACBlocks.spirit_altar, 0);
	}

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent event){
		ResourceLocation blueflame = new ResourceLocation("abyssalcraft","particles/blueflame");
		event.getMap().registerSprite(blueflame);
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
		registerItemRender(item, meta, item.getRegistryName().getPath());
	}

	protected void registerItemRenders(Item item, int metas){
		for(int i = 0; i < metas; i++)
			registerItemRender(item, i);
	}

	protected void registerItemRender(Block block, int meta, String res){
		registerItemRender(Item.getItemFromBlock(block), meta, res);
	}

	protected void registerItemRender(Block block, int meta){
		registerItemRender(block, meta, block.getRegistryName().getPath());
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
