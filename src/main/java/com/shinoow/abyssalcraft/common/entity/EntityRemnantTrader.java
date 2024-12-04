package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIWorship;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EntityRemnantTrader extends EntityMob implements IOmotholEntity {

	private int interactions = 0;
	
	public EntityRemnantTrader(World worldIn) {
		super(worldIn);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(3, new EntityAIAttackMelee(this, 0.35D, false));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(6, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		tasks.addTask(8, new EntityAIWorship(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
//		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 20, true, false, entity -> entity.getClass() == target));
		setSize(0.6F, 1.95F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 100.0D : 50.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 20.0D: 10.0D);
	}


	@Override
	public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand)
	{
		if(isEntityAlive() && !par1EntityPlayer.isSneaking()) {
			par1EntityPlayer.sendMessage(new TextComponentString(getText()));
			interactions++;
			if(interactions >= 3) {
				playSound(ACSounds.remnant_scream, 3F, 1F);
			}
		}
		

		return super.processInteract(par1EntityPlayer, hand);
	}
	
	private String getText() {
		return "";
	}
	
}
