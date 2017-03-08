/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.InterdimensionalCageMessage;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemInterdimensionalCage extends ItemACBasic implements IEnergyContainerItem {

	public ItemInterdimensionalCage() {
		super("interdimensionalcage");
		setMaxStackSize(1);
		setCreativeTab(ACTabs.tabTools);

		addPropertyOverride(new ResourceLocation("captured"), (stack, worldIn, entityIn) -> stack.hasTagCompound() && stack.getTagCompound().hasKey("Entity") && stack.getTagCompound().hasKey("EntityName") ? 1.0F : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List<ItemStack> par3List){
		par3List.add(new ItemStack(par1Item));
		ItemStack stack = new ItemStack(par1Item);
		addEnergy(stack, getMaxEnergy(stack));
		par3List.add(stack);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {

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
				worldIn.spawnEntityInWorld(entity);
				entityliving.playLivingSound();
			}
		}

		return entity;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(String.format("%d/%d PE", (int)getContainedEnergy(is), getMaxEnergy(is)));
		if(is.hasTagCompound() && is.getTagCompound().hasKey("EntityName"))
			l.add("Captured Entity: "+is.getTagCompound().getString("EntityName"));
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public float getContainedEnergy(ItemStack stack) {
		float energy;
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(stack.getTagCompound().hasKey("PotEnergy"))
			energy = stack.getTagCompound().getFloat("PotEnergy");
		else {
			energy = 0;
			stack.getTagCompound().setFloat("PotEnergy", energy);
		}
		return energy;
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {

		return 1000;
	}

	@Override
	public void addEnergy(ItemStack stack, float energy) {
		float contained = getContainedEnergy(stack);
		if(contained + energy >= getMaxEnergy(stack))
			stack.getTagCompound().setFloat("PotEnergy", getMaxEnergy(stack));
		else stack.getTagCompound().setFloat("PotEnergy", contained += energy);
	}

	@Override
	public float consumeEnergy(ItemStack stack, float energy) {
		float contained = getContainedEnergy(stack);
		if(energy < contained){
			stack.getTagCompound().setFloat("PotEnergy", contained -= energy);
			return energy;
		} else {
			stack.getTagCompound().setFloat("PotEnergy", 0);
			return contained;
		}
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
