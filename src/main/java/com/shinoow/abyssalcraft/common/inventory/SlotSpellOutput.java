package com.shinoow.abyssalcraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;

public class SlotSpellOutput extends Slot
{
	/** The craft matrix inventory linked to this result slot. */
	private final InventorySpellbook spellbookInv;
	public SlotSpellOutput(InventorySpellbook spellbookInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
	{
		super(inventoryIn, slotIndex, xPosition, yPosition);
		spellbookInv = spellbookInventory;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 */
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {

		Spell spell = ((InventorySpellbookOutput)inventory).spell;

		for(int i = 0; i < spellbookInv.getSizeInventory(); i++)
			spellbookInv.decrStackSize(i, 1);

		return SpellRegistry.instance().inscribeSpell(spell, stack);
	}
}