package com.huto.hutosmod.karma;


public interface IKarma {
	public void consume(float points);

	public void add(float points);

	public void set(float points);

	public float getKarma();

}
