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
package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.spell.SpellUtils;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MobSpellMessage extends AbstractServerMessage<MobSpellMessage> {

	private int id, spell;

	public MobSpellMessage(){}

	public MobSpellMessage(int id, int spell){
		this.id = id;
		this.spell = spell;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		id = ByteBufUtils.readVarInt(buffer, 5);
		spell = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, id, 5);
		ByteBufUtils.writeVarInt(buffer, spell, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		Entity e = player.world.getEntityByID(id);
		if(e == null) return;

		if(e instanceof EntityLivingBase && SpellUtils.canPlayerHurt(player, e)){
			EntityLivingBase target = (EntityLivingBase)e;
			switch(spell) {
			case 0:
				if(!target.isDead && target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), 5)) {
					player.heal(5);
					BlockPos pos = new BlockPos(player.posX, player.posY+ 1, player.posZ);
					BlockPos pos1 = target.getPosition();

					Vec3d vec = new Vec3d(pos1.subtract(pos)).normalize();

					double d = Math.sqrt(pos1.distanceSq(pos));

					for(int i = 0; i < d * 15; i++){
						double i1 = i / 15D;
						double xp = pos.getX() + vec.x * i1 + .5;
						double yp = pos.getY() + vec.y * i1 + .5;
						double zp = pos.getZ() + vec.z * i1 + .5;
						((WorldServer)player.world).spawnParticle(EnumParticleTypes.FLAME, xp, yp, zp, 0, vec.x * .1, .15, vec.z * .1, 1.0);
					}
				}
				break;
			case 1:
				target.motionX = target.motionY = target.motionZ = 0;
				target.setSprinting(false);
				target.isAirBorne = false;
				target.onGround = true;
				target.setInWeb();
				target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 14, false, false));
				if(target.ticksExisted % 20 == 0 && target.getHealth() > 1)
					target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), 1);
				break;
			case 2:
				target.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 6000, 0, false, false));
				break;
			case 3:
				List<EntityEquipmentSlot> slots = new ArrayList<>();
				for(EntityEquipmentSlot slot : EntityEquipmentSlot.values())
					if(target.hasItemInSlot(slot))
						slots.add(slot);
				EntityEquipmentSlot removeSlot = slots.get(player.world.rand.nextInt(slots.size()));
				target.entityDropItem(target.getItemStackFromSlot(removeSlot), 0);
				target.setItemStackToSlot(removeSlot, ItemStack.EMPTY);
				break;
			case 4:
				target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1200, 1));
				target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1200, 1));
				target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1200, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 1200, 1));
				break;
			case 5:
				if(target instanceof EntityTameable && !((EntityTameable)target).isOwner(player)) {
					((EntityTameable)target).setTamedBy(player);
					target.setHealth(target.getMaxHealth());
				} else if(target instanceof AbstractHorse && !player.getUniqueID().equals(((AbstractHorse)target).getOwnerUniqueId())) {
					((AbstractHorse)target).setTamedBy(player);
					target.setHealth(target.getMaxHealth());
				}
				break;
			case 6:
				if(target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && target.isNonBoss()) {
					player.world.removeEntity(target);
					((WorldServer)player.world).spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, target.posX, target.posY + 2.0D, target.posZ, 0, 0.0D, 0.0D, 0.0D, 1.0);
				}
				break;
			case 7:
				if(target.isCreatureType(EnumCreatureType.CREATURE, false) && target.isChild())
					if(target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), 200F))
						for(EntityLivingBase mob : player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(50)))
							if(mob.isCreatureType(EnumCreatureType.MONSTER, false))
								teleportRandomly(mob, player.world, player);
				break;
			default:
				break;
			}
		}
	}

	protected boolean teleportRandomly(EntityLivingBase entity, World world, EntityPlayer player)
	{
		int tries = 0;
		double d0 = entity.posX + (world.rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = entity.posY + (world.rand.nextInt(64) - 32);
		double d2 = entity.posZ + (world.rand.nextDouble() - 0.5D) * 64.0D;
		while(player.getDistance(d0, d1, d2) < 50) {
			if(tries == 20) break;
			tries++;
			d0 = entity.posX + (world.rand.nextDouble() - 0.5D) * 96.0D;
			d1 = entity.posY + (world.rand.nextInt(64) - 32);
			d2 = entity.posZ + (world.rand.nextDouble() - 0.5D) * 96.0D;
			if(teleportTo(d0, d1, d2, entity, world))
				return true;
		}
		return teleportTo(d0, d1, d2, entity, world);
	}

	protected boolean teleportTo(double par1, double par3, double par5, EntityLivingBase entity, World world)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(entity, par1, par3, par5, 0);
		if (MinecraftForge.EVENT_BUS.post(event))
			return false;
		double d3 = entity.posX;
		double d4 = entity.posY;
		double d5 = entity.posZ;
		entity.posX = event.getTargetX();
		entity.posY = event.getTargetY();
		entity.posZ = event.getTargetZ();
		boolean flag = false;
		BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);

		if (world.isBlockLoaded(pos))
		{
			boolean flag1 = false;

			while (!flag1 && pos.getY() > 0)
			{
				BlockPos pos1 = pos.down();
				IBlockState block = world.getBlockState(pos1);

				if (block.getMaterial().blocksMovement())
					flag1 = true;
				else
				{
					--entity.posY;
					pos = pos1;
				}
			}

			if (flag1)
			{
				entity.setPosition(entity.posX, entity.posY, entity.posZ);

				if (world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(entity.getEntityBoundingBox()))
					flag = true;
			}
		}

		if (!flag)
		{
			entity.setPosition(d3, d4, d5);
			return false;
		}
		else
		{
			short short1 = 128;

			for (int l = 0; l < short1; ++l)
			{
				double d6 = l / (short1 - 1.0D);
				float f = (world.rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (world.rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (world.rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = d3 + (entity.posX - d3) * d6 + (world.rand.nextDouble() - 0.5D) * entity.width * 2.0D;
				double d8 = d4 + (entity.posY - d4) * d6 + world.rand.nextDouble() * entity.height;
				double d9 = d5 + (entity.posZ - d5) * d6 + (world.rand.nextDouble() - 0.5D) * entity.width * 2.0D;
				world.spawnParticle(EnumParticleTypes.PORTAL, d7, d8, d9, f, f1, f2);
			}

			world.playSound(d3, d4, d5, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
			entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
			return true;
		}
	}
}
