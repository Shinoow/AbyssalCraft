package com.shinoow.abyssalcraft.common.util;

import java.util.Random;

public class RandomFiltered extends Random
{
	private static final long serialVersionUID = 1L;

	public RandomFiltered(long par2)
	{
		super(par2);
	}

	@Override
	public int nextInt()
	{
		return this.nextInt(1);
	}

	@Override
	public int nextInt(int n)
	{
		if (n > 0)
		{
			return super.nextInt(n);
		}
		return 0;
	}
}