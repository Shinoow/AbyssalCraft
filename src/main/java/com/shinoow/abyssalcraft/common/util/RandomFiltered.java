/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.util;

import java.util.Random;

public class RandomFiltered extends Random {

	private static final long serialVersionUID = 1L;

	public RandomFiltered(long par2) {
		super(par2);
	}

	@Override
	public int nextInt() {
		return this.nextInt(1);
	}

	@Override
	public int nextInt(int n) {
		if (n > 0)
			return super.nextInt(n);
		return 0;
	}
}