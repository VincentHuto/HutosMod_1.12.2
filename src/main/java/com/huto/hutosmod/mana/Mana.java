package com.huto.hutosmod.mana;

/**
 * Default implementation of IMana
 */
public class Mana implements IMana {
	private float mana = 0.0F;
	private float manaLimit = 300.0F;

	public void consume(float points) {
		this.mana -= points;

		if (this.mana < 0.0F)
			this.mana = 0.0F;
	}

	public void fill(float points) {
		this.mana += points;
	}

	public void set(float points) {
		this.mana = points;
	}

	public float getMana() {
		return this.mana;
	}

	public float manaLimit() {
		return manaLimit;
	}
	public void setLimit(float points) {
		manaLimit = points;
	}

}
