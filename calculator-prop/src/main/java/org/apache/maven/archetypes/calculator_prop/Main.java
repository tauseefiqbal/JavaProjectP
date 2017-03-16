package org.apache.maven.archetypes.calculator_prop;

import java.util.Scanner;

/*
 * This is the used to call calculator class manually or through command prompt
 * As per the requirement this class will take two input parameter, one is expression and other one is Log Level
 */
public class Main {

	
	public static void main(String[] args) {
	
		//create a scanner so we can read the command-line input
        Scanner scannerExpression = new Scanner(System.in);
        //prompt for a valid expression
        System.out.print("Please enter the valid expression to calculate: ");
        //get their input as a String
        String expression = scannerExpression.nextLine();

        //create a scanner so we can read the command-line input
        Scanner  scannerLogLevel = new Scanner(System.in);       
        //prompt for a valid Log Level 
        //If I is selected as LogLevel then all the INFO types logs will be entered in the log file and 
        //all the different type of logs like ERROR, DEBUG will be entered as TRACE type of logs in log file
        //If D is selected as LogLevel then all the DEBUG types logs will be entered in the log file and 
        //all the different type of logs like INFO, ERROR will be entered as TRACE type of logs in log file
        //If E is selected as LogLevel then all the ERROR types logs will be entered in the log file and 
        //all the different type of logs like INFO, DEBUG will be entered as TRACE type of logs in log file
        //If A is selected as LogLevel then all types of logs will be entered in log file
        System.out.print("Please enter the valid Log Level (Valid values: " +
        				 "I for Info and Trace Type logs, D for Debug and Trace Type logs, " +
        				 "E for Error and Trace Type logs, A for ALL type of logs) ");
        //get their input as a String
        String logLevel = scannerLogLevel.nextLine();     
           
        CalculatorExpression calculatorExpression = new CalculatorExpression(expression,logLevel);	 		
	}

}
