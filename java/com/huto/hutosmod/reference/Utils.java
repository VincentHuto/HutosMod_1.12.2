package com.huto.hutosmod.reference;

import org.apache.commons.codec.language.bm.Lang;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

	
	/**
	 * Makes the variables which will be initialized when there getter method is
	 * called
	 */
	private static Logger logger;
	private static Lang lang;

	/**
	 * Returns the logger. This makes System.our.println look shabby
	 * 
	 * @return The {@link Logger}
	 */
	public static Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getFormatterLogger(Reference.MODID);
		}
		return logger;
	}
	
}


