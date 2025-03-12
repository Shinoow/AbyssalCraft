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
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.event.ACEvents.DisruptionEvent;
import com.shinoow.abyssalcraft.api.event.RitualEvent;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.RitualMessage;
import com.shinoow.abyssalcraft.common.network.client.RitualStartMessage;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualPedestal;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityRitualAltar extends TileEntity implements ITickable, IRitualAltar {

	private int ritualTimer, timerMax;
	private NecronomiconRitual ritual;
	private ItemStack item = ItemStack.EMPTY;
	private EntityPlayer user;
	private float consumedEnergy, energyToDrain;
	private boolean isDirty, hasSacrifice, sacrificeIsDead;
	private EntityLiving sacrifice;
	private List<IRitualPedestal> pedestals = new ArrayList<>();

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = new ItemStack(nbtItem);
		ritualTimer = nbttagcompound.getInteger("Cooldown");
		timerMax = nbttagcompound.getInteger("CooldownMax");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(!item.isEmpty())
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		nbttagcompound.setInteger("Cooldown", ritualTimer);
		nbttagcompound.setInteger("CooldownMax", timerMax);

		return nbttagcompound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void update()
	{
		if(isDirty || isPerformingRitual()){
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			isDirty = false;
		}

		if(isPerformingRitual()){
			if(ritualTimer == 1 && !world.isRemote){
				SoundEvent chant = getRandomChant();
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), chant, SoundCategory.PLAYERS, 1, 1);
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), chant, SoundCategory.PLAYERS, 1, 1);
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), chant, SoundCategory.PLAYERS, 1, 1);
				if(ritual != null && sacrifice != null && sacrifice.isEntityAlive())
					sacrifice.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 200, 0, false, false));
			}
			if(ritualTimer < 200)
				ritualTimer++;

			if(ritual != null){

				if(!world.isRemote && ritualTimer % 20 == 0) {

					if(user != null)
						collectPEFromPlayer();
					else user = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);

					boolean isReady;

					if(hasSacrifice) {
						if(sacrifice != null && !sacrifice.isEntityAlive())
							sacrificeIsDead = true;

						isReady = sacrificeIsDead && ritualTimer >= timerMax;
					} else
						isReady = ritualTimer == timerMax;
					if(isReady)
						if(user != null && !world.isRemote){
							if(!MinecraftForge.EVENT_BUS.post(new RitualEvent.Post(user, ritual, world, pos))){
								//Fixes any rounding errors from uneven numbers, but likely breaks rituals with decimal numbers for required energy. I can live with that.
								consumedEnergy = BigDecimal.valueOf(consumedEnergy).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
								if(consumedEnergy == ritual.getReqEnergy() && (sacrifice == null || sacrificeIsDead))
									ritual.completeRitual(world, pos, user);
								else
									triggerDisruption();
								reset();
							} else
								reset();
						} else {
							if(!world.isRemote)
								triggerDisruption();
							reset();
						}
				}

			} else ritualTimer = 0;

			world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0,0,0);
		}
	}

	private void reset() {
		ritualTimer = 0;
		user = null;
		ritual = null;
		consumedEnergy = 0;
		isDirty = true;
		sacrifice = null;
		timerMax = 0;
		hasSacrifice = false;
		sacrificeIsDead = false;
	}

	private void collectPEFromPlayer() {
		if(ritualTimer > timerMax || ritualTimer > 200) // timer runs out, enough should've been drained
			return;
		// Failsafe to avoid overdraining
		float temp = BigDecimal.valueOf(consumedEnergy).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
		if(temp == ritual.getReqEnergy())
			return;

		ItemStack stack = user.getHeldItem(EnumHand.MAIN_HAND);
		float drained = PEUtils.drainPEFromItem(stack, energyToDrain);
		if(drained > 0)
			consumedEnergy += drained;
		else
			for(ItemStack stack1 : user.inventory.mainInventory){
				float drained1 = PEUtils.drainPEFromItem(stack1, energyToDrain);
				if(drained1 > 0) {
					consumedEnergy += drained1;
					break;
				}
			}
	}

	private void triggerDisruption() {
		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 1, pos.getZ(), true));
		DeityType deity = DeityType.values()[world.rand.nextInt(DeityType.values().length)];
		List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(16, 16, 16));
		if(user != null) {
			RitualEvent.Failed event = new RitualEvent.Failed(user, ritual, DisruptionHandler.instance().getRandomDisruption(deity, world), world, pos);
			if(!MinecraftForge.EVENT_BUS.post(event) && !ACConfig.no_disruptions) {
				DisruptionEntry disruption = event.getDisruption();
				PacketDispatcher.sendToAllAround(new RitualMessage(ritual.getID(), pos, true, disruption.getUnlocalizedName()), user, 5);
				if(!MinecraftForge.EVENT_BUS.post(new DisruptionEvent(deity, world, pos, players, disruption)))
					disruption.disrupt(world, pos, players);
				AbyssalCraftAPI.getInternalMethodHandler().sendDisruption(deity, disruption.getUnlocalizedName().substring("ac.disruption.".length()), pos, world.provider.getDimension());
			} else if(!ACConfig.no_disruptions) DisruptionHandler.instance().generateDisruption(deity, world, pos, players);
		} else if(!ACConfig.no_disruptions) DisruptionHandler.instance().generateDisruption(deity, world, pos, players);
	}

	@Override
	public boolean canPerform(){

		if(pedestals.isEmpty())
			for(IRitualPedestal ped : getPedestals(world, pos))
				ped.setAltar(pos);

		return pedestals.size() == 8 && pedestals.stream().anyMatch(p -> !p.getItem().isEmpty());
	}

	private List<IRitualPedestal> getPedestals(World world, BlockPos pos){

		List<IRitualPedestal> pedestals = RitualUtil.PEDESTAL_POSITIONS.stream()
				.map(p -> world.getTileEntity(pos.add(p)))
				.filter(t -> t instanceof IRitualPedestal)
				.map(t -> (IRitualPedestal)t)
				.collect(Collectors.toList());

		return pedestals.size() == 8 ? pedestals : new ArrayList<>();
	}

	@Override
	public void performRitual(World world, BlockPos pos, EntityPlayer player){

		if(world.isRemote || isPerformingRitual()) return;
		ItemStack stack = player.getHeldItemMainhand();
		if(stack.getItem() instanceof ItemNecronomicon)
			if(RitualRegistry.instance().canPerformAction(world.provider.getDimension(), ((ItemNecronomicon)stack.getItem()).getBookType()))
				if(canPerform()){
					ritual = RitualRegistry.instance().getRitual(world.provider.getDimension(), ((ItemNecronomicon)stack.getItem()).getBookType(), pedestals.stream().map(IRitualPedestal::getItem).toArray(ItemStack[]::new), item);
					if(ritual != null && NecroDataCapability.getCap(player).isUnlocked(ritual.getResearchItem(ritual), player))
						if(ritual.requiresSacrifice()){
							if(!world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos).grow(4, 4, 4)).isEmpty()) {
								for(EntityLiving mob : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos).grow(4, 4, 4)))
									if(canBeSacrificed(mob))
										if(ritual.canCompleteRitual(world, pos, player))
											if(!MinecraftForge.EVENT_BUS.post(new RitualEvent.Pre(player, ritual, world, pos))){
												pedestals.stream().forEach(IRitualPedestal::consumeItem);
												Scheduler.schedule(new ScheduledProcess(0) {

													@Override
													public void execute() {
														Tuple<Float, Integer> data = ((ItemNecronomicon) stack.getItem()).getPercentileAndSeconds(stack, ritual.getReqEnergy());
														sacrifice = mob;
														ritualTimer = 1;
														timerMax = data.getSecond() * 20;
														user = player;
														consumedEnergy = 0;
														if(data.getFirst() > ritual.getReqEnergy())
															energyToDrain = ritual.getReqEnergy();
														else
															energyToDrain = ritual.getReqEnergy() / data.getSecond();
														hasSacrifice = true;
														isDirty = true;
														PacketDispatcher.sendToAllAround(new RitualStartMessage(pos, ritual.getID(), sacrifice.getEntityId(), timerMax), world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 30);
													}

												});

												return;
											}
							} else player.sendStatusMessage(new TextComponentTranslation("message.ritual.missingsacrifice"), true);
						} else if(ritual.canCompleteRitual(world, pos, player))
							if(!MinecraftForge.EVENT_BUS.post(new RitualEvent.Pre(player, ritual, world, pos))){
								pedestals.stream().forEach(IRitualPedestal::consumeItem);
								Scheduler.schedule(new ScheduledProcess(0) {

									@Override
									public void execute() {
										Tuple<Float, Integer> data = ((ItemNecronomicon) stack.getItem()).getPercentileAndSeconds(stack, ritual.getReqEnergy());
										ritualTimer = 1;
										timerMax = data.getSecond() * 20;
										user = player;
										consumedEnergy = 0;
										if(data.getFirst() > ritual.getReqEnergy())
											energyToDrain = ritual.getReqEnergy();
										else
											energyToDrain = ritual.getReqEnergy() / data.getSecond();
										isDirty = true;
										PacketDispatcher.sendToAllAround(new RitualStartMessage(pos, ritual.getID(), 0, timerMax), world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 30);
									}

								});
							}
				}
	}

	/**
	 * Checks if a certain Entity can be sacrificed
	 * @param entity Entity to potentially sacrifice
	 * @return True if the Entity can be sacrificed, otherwise false
	 */
	private boolean canBeSacrificed(EntityLiving entity){
		return (EntityUtil.isShoggothFood(entity) || entity instanceof EntityVillager) &&
				entity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD &&
				entity.isEntityAlive() && !entity.isChild();
	}

	public SoundEvent getRandomChant(){
		SoundEvent[] chants = {ACSounds.cthulhu_chant, ACSounds.yog_sothoth_chant_1, ACSounds.yog_sothoth_chant_2,
				ACSounds.hastur_chant_1, ACSounds.hastur_chant_2, ACSounds.sleeping_chant, ACSounds.cthugha_chant};
		return chants[world.rand.nextInt(chants.length)];
	}

	@Override
	public int getRitualCooldown(){
		return ritualTimer;
	}

	@Override
	public boolean isPerformingRitual(){
		return ritualTimer < (hasSacrifice ? 200 : timerMax) && ritualTimer > 0;
	}

	@Override
	public ItemStack getItem(){
		return item;
	}

	@Override
	public void setItem(ItemStack item){
		this.item = item;
		isDirty = true;
	}

	@Override
	public void setRitualFields(NecronomiconRitual ritual, EntityLiving sacrifice, int timerMax) {
		this.ritual = ritual;
		this.sacrifice = sacrifice;
		ritualTimer = 1;
		this.timerMax = timerMax;
	}

	@Override
	public void addPedestal(IRitualPedestal pedestal) {
		pedestals.add(pedestal);
	}

	@Override
	public List<IRitualPedestal> getPedestals() {
		return pedestals;
	}

	@Override
	public EnumRitualParticle getRitualParticle() {
		return ritual != null ? ritual.getRitualParticle() : EnumRitualParticle.NONE;
	}
}
