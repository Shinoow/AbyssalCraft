/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures;

/**
 * Theoretic Bounding Box for Omothol buildings
 * @author shinoow
 *
 */
public class StructureData {

	private final int minX, minY, minZ, maxX, maxY, maxZ, width, height, depth;

	/**
	 * Theoretic Bounding Box for Omothol buildings.<br>
	 * All the <i>min</i> values should return negative numbers (while <b>minY</b><br>
	 * should be 0 unless the building has some form of basement)
	 * @param minX Smallest X-coordinate value (from structure center)
	 * @param minY Smallest Y-coordinate value (from structure center)
	 * @param minZ Smallest Z-coordinate value (from structure center)
	 * @param maxX Largest X-coordinate value (from structure center)
	 * @param maxY Largest Y-coordinate value (from structure center)
	 * @param maxZ Largest Z-coordinate value (from structure center)
	 */
	public StructureData(int minX, int minY, int minZ, int maxX, int maxY, int maxZ){
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		width = maxX - minX;
		height = maxY - minY;
		depth = maxZ - minZ;
	}

	public int getMaxX(){
		return maxX;
	}

	public int getMinX(){
		return minX;
	}

	public int getMaxY(){
		return maxY;
	}

	public int getMinY(){
		return minY;
	}

	public int getMaxZ(){
		return maxZ;
	}

	public int getMinZ(){
		return minZ;
	}

	public double getMaxXfromCoord(double x){
		return x + maxX;
	}

	public double getMinXfromCoord(double x){
		return x + minX;
	}

	public double getMaxYfromCoord(double y){
		return y + maxY;
	}

	public double getMinYfromCoord(double y){
		return y + minY;
	}

	public double getMaxZfromCoord(double z){
		return z + maxZ;
	}

	public double getMinZfromCoord(double z){
		return z + minZ;
	}

	public int getStructureHeight(){
		return height;
	}

	public int getStructureWidth(){
		return width;
	}

	public int getStructureDepth(){
		return depth;
	}
}
