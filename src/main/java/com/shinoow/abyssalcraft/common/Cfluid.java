package com.shinoow.abyssalcraft.common;

import net.minecraftforge.fluids.Fluid;

public class Cfluid extends Fluid
{

	public Cfluid() {
		super( "Liquid Coralium" );
		
		setDensity(3000); 
		setViscosity( 1000 );
		setTemperature(350);
		
		registerLegacyName("Liquid Coralium", "Liquid Coralium");
//		FluidRegistry.registerFluid(this);
	}

}
