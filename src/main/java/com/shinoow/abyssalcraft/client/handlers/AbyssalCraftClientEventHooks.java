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
package com.shinoow.abyssalcraft.client.handlers;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.client.ClientProxy;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.FireMessage;
import com.shinoow.abyssalcraft.common.network.server.StaffModeMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AbyssalCraftClientEventHooks {

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
			Vec3d var8 = pos.addVector(lookvec.xCoord * var2,
					lookvec.yCoord * var2,
					lookvec.zCoord * var2);
			Entity pointedEntity = null;
			float var9 = 1.0F;
			List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(
					theRenderViewEntity,
					theViewBoundingBox.addCoord(
							lookvec.xCoord * var2,
							lookvec.yCoord * var2,
							lookvec.zCoord * var2).expand(var9, var9, var9));
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

					if (aabb.isVecInside(pos))
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
	}

	@SubscribeEvent
	public void tooltipStuff(ItemTooltipEvent event){
		ItemStack stack = event.getItemStack();

		if(stack.getItem() instanceof IEnergyContainerItem)
			event.getToolTip().add(1, String.format("%d/%d PE", (int)((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack), ((IEnergyContainerItem)stack.getItem()).getMaxEnergy(stack)));

		if(stack.getItem() instanceof IUnlockableItem && event.getEntityPlayer() != null && !NecroDataCapability.getCap(event.getEntityPlayer()).isUnlocked(((IUnlockableItem)stack.getItem()).getUnlockCondition(stack), event.getEntityPlayer())){
			event.getToolTip().remove(0);
			event.getToolTip().add(0, "Lorem ipsum");
		}
	}
}
