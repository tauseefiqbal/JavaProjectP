package org.apache.maven.archetypes.calculator_prop;


import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This is a log class of calculator application
 */
public final class Log
{

	
	private static final Logger logger = LogManager.getLogger(Log.class);
	
	/**
	 * This method generate log entries
	 */
	public  void GenerateLogEntry(String logType, String message, String logLevel){
					
			if(logType == "DEBUG" && (logLevel.equals("D") || logLevel.equals("A")))
				logger.debug(message);
			if(logType == "ERROR" && (logLevel.equals("E") || logLevel.equals("A")))
				logger.error(message);
			if(logType == "INFO" && (logLevel.equals("I") || logLevel.equals("A")))
				logger.info(message);
			else
				logger.trace(message);		 					
	}
	    	
}
