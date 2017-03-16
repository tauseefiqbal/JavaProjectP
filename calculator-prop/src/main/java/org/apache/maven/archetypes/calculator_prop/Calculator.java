package org.apache.maven.archetypes.calculator_prop;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Class to perform basic calculation
 */
public class Calculator {
	
	Log log = new Log();
	
	private static String logTypeTrace = "TRACE";
	private static String logTypeDebug = "DEBUG";
	private static String logTypeError = "ERROR";
	private static String logTypeInfo = "INFO";
	
	private static final String ADD = "add";
	private static final String SUB = "sub";
	private static final String MULT = "mult";
	private static final String DIV = "div";
	
	//constructor
	public Calculator(){
		log.GenerateLogEntry(logTypeTrace,"Calculator Loaded", "A");
	}
	
	public int CalculatorOperation(int value1, int value2, String operation, String logLevelReq) throws ArithmeticException{
		
		log.GenerateLogEntry(logTypeTrace,"BasicArithmatic method called", logLevelReq);
		
		int result = 0;
	
				if (!( operation.equals(ADD) ||operation.equals(SUB) ||operation.equals(MULT) ||operation.equals(DIV) ))
				{
					log.GenerateLogEntry(logTypeError,"Inavlid Operation Type, please select add, sub, div, mult operation type", logLevelReq);
					throw new ArithmeticException("Invalid operation type");
				}
				
				if(operation.equals(ADD))
				{					
					result= Math.addExact(value1, value2);
				}
				else if(operation.equals(SUB))
				{
					result= Math.subtractExact(value1, value2);			
				}
				else if(operation.equals(DIV))
				{
					if(value2==0)
						throw new ArithmeticException("Divide by Zero is not allowed");
					result= Math.floorDiv(value1, value2);
				}
				else if(operation.equals(MULT))
				{
					result= Math.multiplyExact(value1, value2);
				}
				
				log.GenerateLogEntry(logTypeInfo,operation.toUpperCase() + " operation started", logLevelReq);
				log.GenerateLogEntry(logTypeTrace,operation.toUpperCase() + " operation finished for "+value1+" and "+value2+" and the result is "+result, logLevelReq);				
				
				return result;
		
	}
	
}