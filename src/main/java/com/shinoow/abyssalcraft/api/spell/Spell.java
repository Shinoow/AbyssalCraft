/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.spell;

import com.shinoow.abyssalcraft.api.necronomicon.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Base Necronomicon Spell.<br>
 * Extend to make your own spells.<br>
 * <b>Currently WIP</b>
 * @author shinoow
 *
 * @since 1.9
 */
public abstract class Spell {

	private Object[] reagents = new Object[5];
	private ItemStack parchment = ItemStack.EMPTY;
	private final String unlocalizedName;
	private int bookType, color;
	private float requiredEnergy;
	private boolean nbtSensitive, requiresCharging;
	private Spell parent;
	private ResourceLocation glyph;
	private IUnlockCondition condition = new DefaultCondition();
	private ScrollType scrollType = ScrollType.BASIC;

	/**
	 * A Necronomicon Spell
	 * @param unlocalizedName A String representing the spell name
	 * @param bookType Necronomicon book type required to inscribe and/or cast
	 * @param requiredEnergy Amount of Potential Energy required to cast
	 * @param requiresChrging If the spell has to charge before casting (eg. holding down the right mouse button instead of just clicking)
	 * @param parchment Parchment to inscribe the spell on
	 * @param reagents Components used to inscribe the spell with
	 */
	public Spell(String unlocalizedName, int bookType, float requiredEnergy, Object...reagents){
		this.unlocalizedName = unlocalizedName;
		this.bookType = bookType;
		this.requiredEnergy = requiredEnergy;
		if(reagents.length < 5){
			this.reagents = new Object[reagents.length];
			for(int i = 0; i < reagents.length; i++)
				this.reagents[i] = reagents[i];
		} else this.reagents = reagents;
	}

	/**
	 * A Necronomicon Spell
	 * @param unlocalizedName A String representing the spell name
	 * @param requiredEnergy Amount of Potential Energy required to cast
	 * @param parchment Parchment to inscribe the spell on
	 * @param reagents Components used to inscribe the spell with
	 */
	public Spell(String unlocalizedName, float requiredEnergy, Object...reagents){
		this(unlocalizedName, 0, requiredEnergy, reagents);
	}

	/**
	 * Sets a parchment required in order to inscribe this spell
	 */
	public Spell setParchment(ItemStack parchment){
		this.parchment = parchment;
		return this;
	}

	/**
	 * Sets the spell glyph color
	 */
	public Spell setColor(int color){
		this.color = color;
		return this;
	}

	/**
	 * Sets the spell to require specific NBT values on the reagents
	 */
	public Spell setNBTSensitive(){
		nbtSensitive = true;
		return this;
	}

	/**
	 * Sets the spell to require charging before casting (eg. holding down the right mouse button instead of just clicking)
	 */
	public Spell setRequiresCharging(){
		requiresCharging = true;
		return this;
	}

	/**
	 * Sets a parent spell required in order to inscribe this spell
	 */
	public Spell setParent(Spell parent){
		this.parent = parent;
		return this;
	}

	/**
	 * Sets the spell glyph texture
	 */
	public Spell setGlyph(ResourceLocation glyph){
		this.glyph = glyph;
		return this;
	}

	/**
	 * Sets a condition required in order to unlock this spell
	 */
	public Spell setUnlockCondition(IUnlockCondition condition){
		this.condition = condition;
		return this;
	}

	/**
	 * Sets a Scroll Type required in order to inscribe this spell
	 */
	public Spell setScrollType(ScrollType scrollType) {
		this.scrollType = scrollType;
		return this;
	}

	/**
	 * Used to fetch the reagents used to inscribe this spell
	 * @return An array of Objects representing reagents
	 */
	public Object[] getReagents(){
		return reagents;
	}

	/**
	 * Used to fetch the parchment this spell can be inscribed on
	 * @return An ItemStack representing the parchment
	 */
	public ItemStack getParchment(){
		return parchment;
	}

	/**
	 * Used to fetch the required book type
	 * @return A Integer representing the book type required for the spell
	 */
	public int getBookType(){
		return bookType;
	}

	/**
	 * Used to fetch the spell glyph color
	 * @return A Integer representing the color on the spell glyph when inscribed
	 */
	public int getColor(){
		return color;
	}

	/**
	 * Used to fetch the required Potential Energy for the spell
	 * @return A Float representing the amount of Potential Energy required to cast the spell
	 */
	public float getReqEnergy(){
		return requiredEnergy;
	}

	/**
	 * Returns whether or not the spell has to charge before casting<br>
	 * (whether you have to hold down the right mouse button or just click it to cast a spell)
	 */
	public boolean requiresCharging(){
		return requiresCharging;
	}

	/**
	 * Used to fetch the spell parent (for spells that are either tiered or evolutions of others)
	 * @return A Spell Object representing the parent spell, or null if none was specified
	 */
	public Spell getParent(){
		return parent;
	}

	/**
	 * Used to fetch the Spell Glyph texture
	 * @return A ResourceLocation pointing to the glyph texture
	 */
	public ResourceLocation getSpellGlyph(){
		return glyph;
	}

	/**
	 * Used to fetch the unlock condition, which determines how the spell is unlocked
	 * @return A IUnlockCondition object, or a DefaultCondition if none was set
	 */
	public IUnlockCondition getUnlockCondition(){
		return condition;
	}

	/**
	 * Used to fetch the Scroll Type required to inscribe this spell
	 * @return A Scroll Type corresponding to this spell
	 */
	public ScrollType getScrollType() {
		return scrollType;
	}

	/**
	 * Used to fetch the unlocalized name for a spell
	 * @return A string prefixed by "ac.spell."
	 */
	public String getUnlocalizedName(){
		return "ac.spell." + unlocalizedName;
	}

	/**
	 * Used to fetch the localized name for a spell
	 * @return A localized string representing a name
	 */
	@SideOnly(Side.CLIENT)
	public String getLocalizedName(){
		return I18n.format(getUnlocalizedName());
	}

	/**
	 * Used to fetch the description for the spell
	 * @return A localized string representing a description
	 */
	@SideOnly(Side.CLIENT)
	public String getDescription(){
		return I18n.format(getUnlocalizedName() + ".desc");
	}

	/**
	 * Determines if the spell should check for identical NBT tag compounds while<br>
	 * checking if the reagents are correct (so a stricter Item match).
	 */
	public boolean isNBTSensitive(){
		return nbtSensitive;
	}

	/**
	 * Override this to ensure that the spell can be cast
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 * @return True if all conditions are met, otherwise false
	 */
	public abstract boolean canCastSpell(World world, BlockPos pos, EntityPlayer player);

	/**
	 * Called when a spell is cast
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 */
	public void castSpell(World world, BlockPos pos, EntityPlayer player){
		if(!world.isRemote) castSpellServer(world, pos, player);
		if(world.isRemote) castSpellClient(world, pos, player);
	}

	/**
	 * Override this to do something client-side when the spell is cast
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 */
	protected abstract void castSpellClient(World world, BlockPos pos, EntityPlayer player);

	/**
	 * Override this to do something server-side when the spell is casted
	 * @param world Current World
	 * @param pos Current position
	 * @param player Player casting the spell
	 */
	protected abstract void castSpellServer(World world, BlockPos pos, EntityPlayer player);
}
