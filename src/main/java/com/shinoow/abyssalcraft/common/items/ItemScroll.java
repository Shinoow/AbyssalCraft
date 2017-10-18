package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.lib.item.ItemMetadata;

public class ItemScroll extends ItemMetadata {

	public ItemScroll(String name, String...names) {
		super(name, names);

		if(name.equals("scroll"))
			addPropertyOverride(new ResourceLocation("inscribed"), (stack, worldIn, entityIn) -> stack.hasTagCompound() && stack.getTagCompound().hasKey("Spell") ? 1.0F : 0);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		Spell spell = getSpell(stack);
		return spell != null ? spell.requiresCharging() ? 50 : 0 : 0;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		Spell spell = getSpell(stack);
		if(spell != null && entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entityLiving;
			if(spell.requiresCharging() && spell.canCastSpell(worldIn, player.getPosition(), player)
					&& hasEnoughPE(player, spell.getReqEnergy())){
				spell.castSpell(worldIn, player.getPosition(), player);
				drainPE(player, spell.getReqEnergy());
			}
		}

		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		Spell spell = getSpell(stack);
		if(spell != null)
			if(!spell.requiresCharging()){
				if(spell.canCastSpell(worldIn, playerIn.getPosition(), playerIn) && hasEnoughPE(playerIn, spell.getReqEnergy())){
					spell.castSpell(worldIn, playerIn.getPosition(), playerIn);
					drainPE(playerIn, spell.getReqEnergy());
				}
			} else{
				playerIn.setActiveHand(handIn);
				for(int i = 0; i < 3; i++)
					worldIn.spawnParticle(EnumParticleTypes.FLAME, playerIn.posX + (worldIn.rand.nextDouble() - 0.5D)*playerIn.width, playerIn.posY + 1.0, playerIn.posZ + (worldIn.rand.nextDouble() - 0.5D)*playerIn.width, 0, 0, 0);
			}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}

	private Spell getSpell(ItemStack stack){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return SpellRegistry.instance().getSpell(stack.getTagCompound().getString("Spell"));
	}

	private boolean hasEnoughPE(EntityPlayer player, float req){
		for(int i = 0; i < 9; i++){
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() instanceof IEnergyContainerItem
					&& ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= req)
				return true;
		}
		return false;
	}

	private void drainPE(EntityPlayer player, float energy){
		for(int i = 0; i < 9; i++){
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() instanceof IEnergyContainerItem
					&& ((IEnergyContainerItem)stack.getItem()).getContainedEnergy(stack) >= energy){
				((IEnergyContainerItem)stack.getItem()).consumeEnergy(stack, energy);
				break;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		Spell spell = getSpell(stack);
		if(spell != null){
			tooltip.add("Spell: "+TextFormatting.AQUA+spell.getLocalizedName());
			tooltip.add("Required PE per cast: "+(int)spell.getReqEnergy());
			tooltip.add("Cast type: "+TextFormatting.GOLD+(spell.requiresCharging() ? "Charge" : "Instant"));
		}
	}
}
