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
package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;
import com.shinoow.abyssalcraft.common.world.data.NecromancyWorldSavedData;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class NecronomiconResurrectionRitual extends NecronomiconRitual {

	public NecronomiconResurrectionRitual() {
		super("resurrection", 2, OreDictionary.WILDCARD_VALUE, 1000F, true, new Object[]{ new ItemStack[]{new ItemStack(ACItems.crystal_shard, 1, 3), new ItemStack(ACItems.crystal, 1, 3),
				new ItemStack(ACBlocks.carbon_crystal_cluster)}, new ItemStack[]{new ItemStack(ACItems.crystal_shard, 1, 5), new ItemStack(ACItems.crystal, 1, 5),
						new ItemStack(ACBlocks.hydrogen_crystal_cluster)}, new ItemStack[]{new ItemStack(ACItems.crystal_shard, 1, 6), new ItemStack(ACItems.crystal, 1, 6),
								new ItemStack(ACBlocks.nitrogen_crystal_cluster)}, new ItemStack[]{new ItemStack(ACItems.crystal_shard, 1, 4), new ItemStack(ACItems.crystal, 1, 4),
										new ItemStack(ACBlocks.oxygen_crystal_cluster)}, new ItemStack[]{new ItemStack(ACItems.crystal_shard, 1, 7), new ItemStack(ACItems.crystal, 1, 7),
												new ItemStack(ACBlocks.phosphorus_crystal_cluster)}, new ItemStack[]{new ItemStack(ACItems.crystal_shard, 1, 2), new ItemStack(ACItems.crystal, 1, 2),
														new ItemStack(ACBlocks.sulfur_crystal_cluster)}});
		sacrifice = Items.NAME_TAG;
		setRitualParticle(EnumRitualParticle.SMOKE_PILLARS);
	}

	@Override
	public boolean requiresItemSacrifice(){
		return true;
	}

	private boolean mismatch(ItemStack stack, Object obj, int size){

		if(!stack.isEmpty() && obj instanceof ItemStack[])
			return stack.getItem() != ((ItemStack[])obj)[size].getItem();

		return false;
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		TileEntity altar = world.getTileEntity(pos);

		ItemStack stack = ItemStack.EMPTY;

		if(altar instanceof IRitualAltar)
			stack = ((IRitualAltar) altar).getItem();

		if(!stack.isEmpty() && stack.getItem() == Items.NAME_TAG){
			NecromancyWorldSavedData cap = NecromancyWorldSavedData.get(world);
			NBTTagCompound data = cap.getDataForName(stack.getDisplayName());
			return data != null && ((IRitualAltar) altar).getPedestals().stream().noneMatch(p -> mismatch(p.getItem(), getOfferings()[0], data.getInteger("ResurrectionRitualCrystalSize")));
		}

		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {

	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		TileEntity altar = world.getTileEntity(pos);

		ItemStack stack = ItemStack.EMPTY;

		if(altar instanceof IRitualAltar)
			stack = ((IRitualAltar) altar).getItem();

		if(!stack.isEmpty() && stack.getItem() == Items.NAME_TAG){
			NecromancyWorldSavedData cap = NecromancyWorldSavedData.get(world);
			NBTTagCompound data = cap.getDataForName(stack.getDisplayName());
			Entity e = EntityList.createEntityFromNBT(data, world);
			if(e instanceof EntityLiving){
				EntityLiving entity = getEntity(e, data.getInteger("ResurrectionRitualCrystalSize"));
				world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 2, pos.getZ(), true));
				entity.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				entity.setHealth(entity.getMaxHealth());
				world.spawnEntity(entity);
				cap.clearEntry(stack.getDisplayName());
				((IRitualAltar) altar).setItem(ItemStack.EMPTY);
			}
		}
	}

	private EntityLiving getEntity(Entity e, int size){

		int i = e.getEntityData().getInteger("Reanimations");

		if(i > 3){
			boolean b = false;

			switch(i){
			case 4:
				b = e.world.rand.nextFloat() < 0.9;
				break;
			case 5:
				b = e.world.rand.nextFloat() < 0.75;
				break;
			case 6:
				b = e.world.rand.nextFloat() < 0.6;
				break;
			case 7:
				b = e.world.rand.nextFloat() < 0.45;
				break;
			case 8:
				b = e.world.rand.nextFloat() < 0.3;
				break;
			case 9:
				b = e.world.rand.nextFloat() < 0.15;
				break;
			case 10:
				b = true;
				break;
			}

			if(b){

				EntityLiving e1 = new EntityDreadSpawn(e.world);

				switch(size){
				case 0:
					e1 = new EntityDreadSpawn(e.world);
					break;
				case 1:
					e1 = new EntityGreaterDreadSpawn(e.world);
					break;
				case 2:
					e1 = new EntityLesserDreadbeast(e.world);
					break;
				}

				e1.setCustomNameTag(e.getCustomNameTag());
				return e1;
			}
		}

		e.getEntityData().setInteger("Reanimations", i + 1);

		return (EntityLiving) e;
	}
}
