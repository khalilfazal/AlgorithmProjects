import java.util.*;

/**
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class Global {

	//Holds the population size for all generations
	public static int populationSize = 8;
	
	//Stores the values of the initial randomly generated generation
	public static int[] initialGeneration = new int[8];
	
	//Holds the values of the current working generation
	public static int[][] currentGeneration = new int[Global.populationSize][8];
	
	//Holds the values of the previous established generation
	public static int[][] previousGeneration = new int[Global.populationSize][8];
	
	//Stores all the generated generations
	public static int[][] allGenerations = new int[100][8];
	
	//Holds the fitness values for the current generation
	public static int[][] currentFitness = new int[Global.populationSize][8];
	
	//Stores the fitness values for the previous generation
	public static int[][] previousFitness = new int[Global.populationSize][8];
	
	//Stores all the fitness values of every generation
	public static ArrayList<Integer> allFitness = new ArrayList<Integer>();
	
	//Stores the best fitness of every generation
	public static ArrayList<Integer> allBestFitness = new ArrayList<Integer>();
	
	//Stores the average fitness of every generation
	public static ArrayList<Integer> allAverageFitness = new ArrayList<Integer>();
	
	//Current possible solution's total fitness is stored here
	public static int totalFitness;
	
	//Holds the number of generations generated
	public static int generationAge = -1;
	
	//Stores the index of the currently worked on child
	public static int currentChildIndex = 0;
	
	//Stores index of ParentA in Global.previousGeneration
	public static int parentA;
	
	//Stores index of ParentA in Global.previousGeneration
	public static int parentB;
	
	public static int solutionChild;
	
	//Stores program start and end time
	public static long startTime;
	public static long endTime;
	

}
