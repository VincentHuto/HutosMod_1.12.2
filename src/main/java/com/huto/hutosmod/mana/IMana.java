package com.huto.hutosmod.mana;

/**
 * Mana capability
 */
public interface IMana {
	public void consume(float points);

	public void fill(float points);

	public void set(float points);

	public float getMana();

	public float manaLimit();
	
	public void setLimit(float points);
}
