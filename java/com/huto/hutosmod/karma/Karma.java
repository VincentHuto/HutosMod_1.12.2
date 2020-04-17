package com.huto.hutosmod.karma;


public class Karma implements IKarma {
	private float karma = 0.0F;

	public void consume(float points) {
		this.karma -= points;
	}

	public void add(float points) {
		this.karma += points;
	}

	public void set(float points) {
		this.karma = points;
	}

	public float getKarma() {
		return this.karma;
	}
}
