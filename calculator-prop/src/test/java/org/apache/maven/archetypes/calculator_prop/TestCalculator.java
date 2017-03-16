package org.apache.maven.archetypes.calculator_prop;
//import static org.junit.Assert.*;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * This is the unit test class to test Calculator functionality
 */
public class TestCalculator {
	
	@Test
	public void shouldAddTwoNumbers() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("add(2 ,5)","A");
			 	assertEquals(7,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldMultiplyTwoNumbers() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("mult(2,3)","A");
			 	assertEquals(6,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldSubtractOneNumberFromAnother() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("sub(8,3)","A");
			 	assertEquals(5,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldDivideOneNumberByAnother() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("sub(9,3)","A");
			 	assertEquals(6,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldHandleAnExpressionAsAnArgument() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("add(1,mult(2,3))","A");
			 	assertEquals(7,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldHandleExpressionsAsBothArguments() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("mult(add(2,2),div(9,3))","A");
			 	assertEquals(12,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldAssignValuesToVariables() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("let(a,5,add(a,a))","A");
			 	assertEquals(10,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldAssignValuesToVariablesInUsageExpressions() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("let(a,5,let(b,mult(a,10),add(b,a)))","A");
			 	assertEquals(55,calculatorExpression.getCalculatorExpressionResult());  
	}
	
	@Test
	public void shouldAssignValuesToVariablesInValueExpressions() {
		 	 CalculatorExpression calculatorExpression=new CalculatorExpression("let(a,let(b,10,add(b,b)),let(b,20,add(a,b))","A");
			 	assertEquals(40,calculatorExpression.getCalculatorExpressionResult());  
	}
	 
	
}
