import java.util.*;

/**
 * 
 */

/**
 * @author Rayhaan Shakeel 100425726
 *
 */
public class Global {

	//Holds the population size for all generations
	public static int populationSize = 8;
	
	//Stores the values of the initial randomly generated generation
	public static int[] initialGeneration = new int[8];
	
	//Holds the values of the current working generation
	public static int[][] currentGeneration = new int[8][8];
	
	//Holds the values of the previous established generation
	public static int[][] previousGeneration = new int[8][8];
	
	//Stores all the generated generations
	public static int[][] allGenerations = new int[100][8];
	
	//Holds the fitness values for the current generation
	public static int[][] currentFitness = new int[8][8];
	
	//Stores the fitness values for the previous generation
	public static int[][] previousFitness = new int[8][8];
	
	//Holds the fitness values of all generated generations
	//public static int[][] allFitness = new int[8][];
	public static ArrayList<String> allFitness = new ArrayList<String>();
	
	public static int totalFitness;
	
	//Holds the number of generations generated
	public static int generationAge = -1;
	
	//Stores the index of the currently worked on child
	public static int currentChildIndex = 0;
	
	//Stores index of ParentA in Global.previousGeneration
	public static int parentA;
	
	//Stores index of ParentA in Global.previousGeneration
	public static int parentB;
	

}
