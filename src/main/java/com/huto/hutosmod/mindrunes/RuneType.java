package com.huto.hutosmod.mindrunes;

public enum RuneType {
	CONTRACT(0),
	RUNE(1,2,3),
	OVERRIDE(0,1,2,3);
/*	BELT(3),
	TRINKET(0,1,2,3,4,5,6),
	HEAD(4),
	BODY(5),
	CHARM(6);*/

	int[] validSlots;

	private RuneType(int ... validSlots) {
		this.validSlots = validSlots;
	}

	public boolean hasSlot(int slot) {
		for (int s:validSlots) {
			if (s == slot) return true;
		}
		return false; 
	}

	public int[] getValidSlots() {
		return validSlots;
	}
}
