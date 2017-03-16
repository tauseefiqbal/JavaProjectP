package org.apache.maven.archetypes.calculator_prop;

/**
 * 
 */
import java.util.HashMap;
import java.util.Stack;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * This is the calculator expression class to handle expression to be calculated
 */
public class CalculatorExpression {

	Log log = new Log();
	
	// logLevel value can be provided by the user through command prompt, if not then system will go with default value of A, which will creates all types of logs
	// Valid Values for logLevel are I for Info type log, D for Debug type log, E for Error type log, Default is T for Trace log type**//
	private static String logLevel = "A";
	
	private static String logTypeTrace = "TRACE";
	private static String logTypeDebug = "DEBUG";
	private static String logTypeError = "ERROR";
	private static String logTypeInfo = "INFO";
	
	private CalculatorExpressionNode _head=null;
	private static HashMap<String,Integer> _variables=new HashMap<String,Integer>();
	
	
	/**
	 * 
	 * @param expression
	 * @throws Exception 
	 */
	public CalculatorExpression(String expression, String logLevelReq){
		
		//removing blank spaces in the input expression
		expression= expression.replaceAll(" ", "");
        		
		if (logLevelReq.equals("I") || logLevelReq.equals("D")|| logLevelReq.equals("E") || logLevelReq.equals("A"))
		{
			logLevel = logLevelReq;
		}
		else
		{
			log.GenerateLogEntry (logTypeError,"Invalid Log Level Type entered by User", logLevel);
			return;
		}
		
		log.GenerateLogEntry(logTypeTrace,"Constructor method is called", logLevel);
		try{
			ExpressionBuild(expression);
			log.GenerateLogEntry(logTypeInfo,"Input Proccessed", logLevel);
		}
		catch(Exception e)
		{
			log.GenerateLogEntry(logTypeError,e.getMessage(), logLevel);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCalculatorExpressionResult(){
		
		log.GenerateLogEntry(logTypeTrace,"getExpressionResult method is called.", logLevel);
		
		try{
		return TraverseCalculatorExpTree(_head);
		}
		catch(Exception e){
			
			log.GenerateLogEntry(logTypeError,e.getMessage(), logLevel);
			return 0;
		}
	}
	
	
	/**
	 * This method is used to evaluate the expression tree by post order traversal and returns the result
	 * @param node Takes parameter as Expression Node
	 * @return
	 */
	private int TraverseCalculatorExpTree(CalculatorExpressionNode node){
		
		log.GenerateLogEntry(logTypeTrace,"TraverseCalculatorExpTree method is called", logLevel);
		
		if(node==null)
		{
			log.GenerateLogEntry(logTypeError,"Invalid Calculator Expression Tree", logLevel);
			return 0;
		}
		
		if(node.isLeaf)
		{
			if(node.name==null){
				return node.value;
			}
			else 
			{	if(_variables.get(node.name)==null){
					log.GenerateLogEntry(logTypeError,"Entered Expression to calculate is not valid.", logLevel);
					throw new IllegalArgumentException("Invalid Calculator Expression Tree");
				}
			log.GenerateLogEntry(logTypeError,"Leaf node with value "+_variables.get(node.name) +" varibale name "+node.name, logLevel);
			return _variables.get(node.name);
			}
			
		}
		
		int var1=0;
		int var2=0;
		
		if(node.left!=null){
			log.GenerateLogEntry(logTypeTrace,"Calling TraverseExpTree on left node", logLevel);
			var1=TraverseCalculatorExpTree(node.left);
			log.GenerateLogEntry(logTypeDebug,"Output of left node traversal is "+var1, logLevel);
		}
		

		if(node.operator!=null && node.operator.equals("let")){
			log.GenerateLogEntry(logTypeDebug,"let operator found storing variable with name"+node.name+" and value "+var1, logLevel);
			_variables.put(node.name, var1);
		}

	
		if(node.right!=null){
			log.GenerateLogEntry(logTypeTrace,"Calling TraverseCalculatorExpTree on right node", logLevel);
			var2=TraverseCalculatorExpTree(node.right);
			log.GenerateLogEntry(logTypeDebug,"Output of right node traversal is "+var2, logLevel);
		}
		
		if(!node.operator.equals("let")){
			try{
				log.GenerateLogEntry(logTypeTrace,"Evaluating operator "+node.operator+ " for values "+var1+" and "+var2, logLevel);
				var2=new Calculator().CalculatorOperation(var1, var2, node.operator,"A");
				log.GenerateLogEntry(logTypeDebug,"Result of "+node.operator+" is "+var2, logLevel);
			}
			catch(Exception e){
				log.GenerateLogEntry(logTypeError,e.getMessage(), logLevel);
				return 0;
			}
		}
		log.GenerateLogEntry(logTypeTrace,"Returning result "+var2, logLevel);
		return var2;	
	}


	/**
	 *  This methods is used to build an expression tree node 
	 * @param input takes string as input parameter 
	 * @return return the expression node
	 * @throws NumberFormatException if the input string exceed the max rang of integer then a number expression is thrown
	 */
	private CalculatorExpressionNode CalculatorExpressionNodeBuilder(String input) throws NumberFormatException{
		
		CalculatorExpressionNode node=new CalculatorExpressionNode();
		log.GenerateLogEntry(logTypeTrace,"New Node is initialized", logLevel);
		//check of the string matches valid operator or number or variable name
		
		if(input.equals("let"))   
		{
			node.operator="let";
			node.value=-1;
			log.GenerateLogEntry(logTypeTrace,"Let operator is set with value -1", logLevel);
			return node;
		}
		else if(input.startsWith("add"))
		{
			node.operator="add";
			node.value=-1;
			log.GenerateLogEntry(logTypeTrace,"add operator is set with value -1", logLevel);
			return node;
		}
		else if(input.startsWith("sub"))
		{
			node.operator="sub";
			node.value=-1;
			log.GenerateLogEntry(logTypeTrace,"sub operator is set with value -1", logLevel);
			return node;
		}
		else if(input.startsWith("mult")){
			
			node.operator="mult";
			node.value=-1;
			log.GenerateLogEntry(logTypeTrace,"mult operator is set with value -1",logLevel);	
			return node;
		}
		else if(input.startsWith("div"))  
		{		
			node.operator="div";
			node.value=-1;
			log.GenerateLogEntry(logTypeTrace,"div operator is set with value -1",logLevel);	
			return node;
		}
		
		else if(input.matches("[0-9]+")){ 
			//regular expression matches numbers so set value of node
			node.value=Integer.parseInt(input);   //will throw number format exception of large number string is given grater than range of string
			node.isLeaf=true;   //value will be leaf
			log.GenerateLogEntry(logTypeTrace,"value of the node is set to "+node.value+ " and is set to leaf node",logLevel);	
			return node;
		}
		else if(input.matches("[a-zA-Z]+")){  //regular expression matches alphabets the set name of the node
			node.value=-1;
			node.name=input;
			node.isLeaf=true;     //variable with no value will be leaf
			log.GenerateLogEntry(logTypeTrace,"node of the node is set to "+node.name+ " with value -1 and is set to leaf node",logLevel);	
			return node;
		}
		return null;  //if the input is invalid then return null
	}
	
	/**
	 * This method is used to generate expression tree 
	 * @param input takes string as input parameter to generate expression
	 * @return return expression tree head
	 */
	private CalculatorExpressionNode ExpressionBuild(String input){
		log.GenerateLogEntry(logTypeTrace,"ExpressionBuild method called",logLevel);	
		
		if(input==null || input.isEmpty()){		//if the input string is null or empty return null
			log.GenerateLogEntry(logTypeError,"Empty or null input",logLevel);	
			return null;
		}
	try{	

		Stack<CalculatorExpressionNode> backtrack=new Stack<CalculatorExpressionNode>();  // Maintain a stack generate data in pre-order 
		CalculatorExpressionNode node=new CalculatorExpressionNode();   

		int index=0;
		
		while(index<input.length())
		{
			if(input.charAt(index)=='('){   // "(" will have operator as prefix and will have left and right child
				
				String prefix=input.substring(0,index); // get prefix string till index and generate node out of it

				node=CalculatorExpressionNodeBuilder(prefix);  
				if(node.operator==null)  //check if the operator  was not found
				{
					_head=null;
					log.GenerateLogEntry(logTypeError,"invalid input string",logLevel);	
					throw new IllegalArgumentException("invalid input string");
				}
				
				if(backtrack.isEmpty())      //if the stack is empty then the node is head 
				{
					log.GenerateLogEntry(logTypeDebug,"head is found "+node.operator,logLevel);	
					backtrack.push(node);    //push the node to stack
					_head=node;				 //set head
				}
				
				else if(backtrack.peek().left==null)  //check if previous node has left by check top of stack
				{
					log.GenerateLogEntry(logTypeDebug,"Left node is found "+node.operator,logLevel);	
					backtrack.peek().left=node;   //assign node as left node to previous node
					if(!node.isLeaf)             //if left is not found and if the node is not a leaf node then push on stack
						backtrack.push(node);					
				}
				else
				{
					log.GenerateLogEntry(logTypeDebug,"Right node is found "+node.operator,logLevel);	
					
					backtrack.peek().right=node;    //else assign node as right node
					
					//if node is node not leaf push on stack so that we can add more
					if(!node.isLeaf)               
						backtrack.push(node);
				}
				
				input=input.substring(index+1); //since the prefix is processed remove the prefix from input string
				index=0;   //set index to 0 to process the cut string from start
								
			}
			else if(input.charAt(index)==','){   //if comma is found the element will now only can be left node
				
				String prefix=input.substring(0,index);
				
				if(!prefix.equals(""))   //check of prefix is not empty to avoid ")," such strings
				{
					node=CalculatorExpressionNodeBuilder(prefix);	
					
					if(backtrack.peek().operator!=null && backtrack.peek().operator.equals("let") && node.name!=null)
					{
						log.GenerateLogEntry(logTypeDebug,"Left node is found with varibale name "+node.name,logLevel);	
						backtrack.peek().name=node.name;
					}
					else {
						
						if(backtrack.peek().left!=null){  //This will be thrown when invalid string is provided
							log.GenerateLogEntry(logTypeError,"invalid input string, possibly too many arguments "+node.value,logLevel);	
							throw new IllegalArgumentException("invalid input string");
						}
						
						log.GenerateLogEntry(logTypeDebug,"Left node is found with value "+node.value,logLevel);	
						backtrack.peek().left=node;
					
						if(!node.isLeaf){
						
							backtrack.push(node);	
						}
					}
					
				}
				else {
					/*
					 * if prefix was empty that means we have process 
					 * all the child node for the previous node 
					 * we can now remove it from the stack
					 */
					backtrack.pop();
				}
				input=input.substring(index+1); //since the prefix is processed remove the prefix from input string
				index=0; //set index to 0 to process the cut string from start
			}
			else if(input.charAt(index)==')'){   //closing brackets are found therefore the node will be right node
				
				String prefix=input.substring(0,index);	
								
				if(!prefix.equals(""))  //check if the prefix is not empty to avoid ")))" such strings
				{
					node=CalculatorExpressionNodeBuilder(prefix);	
					
					if(node.name==null)
						log.GenerateLogEntry(logTypeDebug,"Right node is found with value "+node.value,logLevel);	
					else 
						log.GenerateLogEntry(logTypeDebug,"Right node is found with varibale name "+node.name,logLevel);	
					backtrack.peek().right=node;

					if(!node.isLeaf)
						backtrack.push(node);
				}
				else {
					/*
					 * if prefix was empty that means we have process 
					 * all the child node for the previous node 
					 * we can now remove it from the stack
					 */
					backtrack.pop();   
				}
				input=input.substring(index+1); //since the prefix is processed remove the prefix from input string
				index=0; //set index to 0 to process the cut string from start
				
			}
			else index++;  
		}
		
	}
	catch(Exception e){  //if the input string is in invalid format null pointer exception will be thrown 
		log.GenerateLogEntry(logTypeError,e.getMessage(),logLevel);	
		_head=null;  //set the head to null
	}
		
		return _head; 
	}
	
	/**
	 * This method is used to get the head of the expression tree
	 * @return Returns the head of the expression tree
	 */
	 public CalculatorExpressionNode getHead() {
		return _head;
	}

	/*
	 * *Inner class to expression tree
	 * 
	 * Represents Expression tree node objects
	 */
	private class CalculatorExpressionNode {
		
		//used to store name of the node that represents
		//variable name for let or variable name in general in the expression
		String name=null;
			
		int value;				//stores the value of the node               
		String operator=null;    //stored if the node is an operator
		
		boolean isLeaf=false;      
		
		CalculatorExpressionNode left=null;     //Reference to left node of expression tree
		CalculatorExpressionNode right=null;		//Reference to left node of expression tree
			
	}
	
}
