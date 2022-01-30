/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.client.ClientProxy;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.InterdimensionalCageMessage;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemInterdimensionalCage extends ItemACBasic implements IEnergyContainerItem {

	public ItemInterdimensionalCage() {
		super("interdimensionalcage");
		setMaxStackSize(1);
		setCreativeTab(ACTabs.tabTools);

		addPropertyOverride(new ResourceLocation("captured"), (stack, worldIn, entityIn) -> stack.hasTagCompound() && stack.getTagCompound().hasKey("Entity") && stack.getTagCompound().hasKey("EntityName") ? 1.0F : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List){
		if(isInCreativeTab(par2CreativeTab)){
			par3List.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			addEnergy(stack, getMaxEnergy(stack));
			par3List.add(stack);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		player.setActiveHand(hand);

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		if(stack.getTagCompound().hasKey("Entity")){
			if(world.isRemote) return new ActionResult(EnumActionResult.PASS, stack);

			RayTraceResult movingobjectposition = rayTrace(world, player, true);

			if(movingobjectposition == null) return new ActionResult(EnumActionResult.PASS, stack);
			else{
				if(movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					BlockPos blockpos = movingobjectposition.getBlockPos();

					if(!world.isBlockModifiable(player, blockpos))
						return new ActionResult(EnumActionResult.PASS, stack);

					if(!player.canPlayerEdit(blockpos, movingobjectposition.sideHit, stack))
						return new ActionResult(EnumActionResult.PASS, stack);

					Entity entity = spawnCreature(world, stack.getTagCompound().getCompoundTag("Entity"), blockpos.getX() + 0.5D, blockpos.getY() + 1.5D, blockpos.getZ() + 0.5D);

					if(entity != null){
						stack.getTagCompound().removeTag("Entity");
						stack.getTagCompound().removeTag("EntityName");
					}
				}
				return new ActionResult(EnumActionResult.PASS, stack);
			}
		} else {
			if(!world.isRemote) return new ActionResult(EnumActionResult.PASS, stack);

			RayTraceResult mov = AbyssalCraftClientEventHooks.getMouseOverExtended(3);

			if (mov != null)
				if (mov.entityHit != null && !mov.entityHit.isDead)
					if (mov.entityHit != player )
						PacketDispatcher.sendToServer(new InterdimensionalCageMessage(mov.entityHit.getEntityId(), hand));
			return new ActionResult(EnumActionResult.PASS, stack);
		}
	}

	private Entity spawnCreature(World worldIn, NBTTagCompound nbt, double x, double y, double z)
	{
		Entity entity = null;

		for (int i = 0; i < 1; ++i)
		{
			entity = EntityList.createEntityFromNBT(nbt, worldIn);

			if (entity instanceof EntityLivingBase)
			{
				EntityLiving entityliving = (EntityLiving)entity;
				entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
				entityliving.rotationYawHead = entityliving.rotationYaw;
				entityliving.renderYawOffset = entityliving.rotationYaw;
				worldIn.spawnEntity(entity);
				entityliving.playLivingSound();
			}
		}

		return entity;
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		if(is.hasTagCompound() && is.getTagCompound().hasKey("EntityName"))
			l.add("Captured Entity: "+is.getTagCompound().getString("EntityName"));
		l.add("Press "+TextFormatting.GOLD+ClientProxy.use_cage.getDisplayName()+TextFormatting.GRAY+" to capture a mob");
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public float getContainedEnergy(ItemStack stack) {
		return PEUtils.getContainedEnergy(stack);
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {

		return 1000;
	}

	@Override
	public void addEnergy(ItemStack stack, float energy) {
		PEUtils.addEnergy(this, stack, energy);
	}

	@Override
	public float consumeEnergy(ItemStack stack, float energy) {
		return PEUtils.consumeEnergy(stack, energy);
	}

	@Override
	public boolean canAcceptPE(ItemStack stack) {
		return getContainedEnergy(stack) < getMaxEnergy(stack);
	}

	@Override
	public boolean canTransferPE(ItemStack stack) {
		return false;
	}
}
