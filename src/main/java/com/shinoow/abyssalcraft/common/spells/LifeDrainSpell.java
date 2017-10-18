package com.shinoow.abyssalcraft.common.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.LifeDrainMessage;

public class LifeDrainSpell extends Spell {

	public LifeDrainSpell() {
		super("lifedrain", 100, Items.APPLE);
		setParchment(new ItemStack(ACItems.scroll, 1, 0));
		setRequiresCharging();
		setColor(0xa00404);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {

		if(world.isRemote){
			RayTraceResult r = AbyssalCraftClientEventHooks.getMouseOverExtended(15);
			if(r != null && r.entityHit instanceof EntityLivingBase)
				if(r.entityHit instanceof EntityPlayer && ((EntityPlayer)r.entityHit).isCreative()) return false;
				else return true;
		}
		return false;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {
		RayTraceResult r = AbyssalCraftClientEventHooks.getMouseOverExtended(15);
		if(r != null && r.entityHit instanceof EntityLivingBase && !r.entityHit.isDead){
			PacketDispatcher.sendToServer(new LifeDrainMessage(r.entityHit.getEntityId()));
			BlockPos pos1 = r.entityHit.getPosition();

			Vec3d vec = new Vec3d(pos1.subtract(pos)).normalize();

			double d = Math.sqrt(pos1.distanceSq(pos));

			for(int i = 0; i < d * 15; i++){
				double i1 = i / 15D;
				double xp = pos.getX() + vec.x * i1 + .5;
				double yp = pos.getY() + vec.y * i1 + .5;
				double zp = pos.getZ() + vec.z * i1 + .5;
				player.world.spawnParticle(EnumParticleTypes.FLAME, xp, yp, zp, vec.x * .1, .15, vec.z * .1);
			}
		}
	}

	@Override
	protected void castSpellServer(World world, BlockPos pos, EntityPlayer player) {}
}
