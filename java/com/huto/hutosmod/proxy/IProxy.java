package com.huto.hutosmod.proxy;

public interface IProxy {

	default void lightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, int colorOuter,
			int colorInner) {
		lightningFX(vectorStart, vectorEnd, ticksPerMeter, System.nanoTime(), colorOuter, colorInner);
	}

	void lightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, long seed, int colorOuter,
			int colorInner);

}
