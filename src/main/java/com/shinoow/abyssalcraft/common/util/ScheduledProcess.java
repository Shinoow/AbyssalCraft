package com.shinoow.abyssalcraft.common.util;

public abstract class ScheduledProcess {

	private int time;
	
	public ScheduledProcess(int time) {
		this.time = time;
	}
	
	public boolean tick() {
		time--;
		
		return time <= 0;
	}
	
	public int getTime() {
		return time;
	}
	
	public abstract void execute();
}
