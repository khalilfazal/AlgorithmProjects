/**
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */


import java.lang.Math;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Fitness {

	/**
	 * FITNESS VALUES: 
	 * -1 : Fitness has not been calculated yet 
	 *  0 : Perfect fitness, queen is not under attack nor is attacking others. 10/10 would queen.
	 * >0 : Queen is under attack. The fitness value indicates how many queens it is attacking.
	 *  7 : Worst fitness level, very bad queen. Not worthy of queen title.
	 */
	
	//Calculates the fitness value for the current population
	public static void calculatePopulationFitness() {
		
		
		//Current fitness values are all set to -1
		Fitness.clearCurrentFitness();
		
		//This for loop calculates the fitness for all the queens in the current population
		//Outer queen is the base queen which is being compared to all the inner queens aka the other queens
		for (int chromosome = 0; chromosome < Global.populationSize ; chromosome++)
		{
			for (int column = 0; column < 8; column++)
			{
				for (int row = 0; row <8; row++)
				{
					//If currently traversed inner queen shares a row with the outer queen, the outer queen's
					//fitness value is increased
					
					//Same queen
					if (column == row){
						//do nothing since you don't want to compare the current queen with itself, doing so
						//will corrupt fitness value
					}					
					else if (collisionDetection(Global.currentGeneration[chromosome][column], column, Global.currentGeneration[chromosome][row], row)) {
					     Global.currentFitness[chromosome][column]++;
					}
				}
			}
		}
	}
	
	//Clears the fitness values of the current generation
	public static void clearCurrentFitness(){
		
		for (int outer = 0; outer < Global.populationSize; outer++){
			for (int inner = 0; inner < 8; inner++){
				Global.currentFitness[outer][inner] = 0;		
			}
		}
	}
	
	//Calculates total fitness of all queens in a particular generation
	public static int calculateTotalFitness(int generationIndex){
		
		int totalFitness = 0;
		
		//for loop iterates all queens in a particular generation
		for (int i = 0; i < Global.populationSize; i++)
		{
			totalFitness += Global.previousFitness[generationIndex][i];
		}
		
		return totalFitness;
	}
	
	//Calculates the total fitness for current generation
	public static void calculateTotalFitness()
	{
		//Resets totalFitness
		Global.totalFitness = 0;
		calculatePopulationFitness();
		
		for (int outer = 0; outer < Global.populationSize; outer++)
		{
			for (int inner = 0; inner < 8; inner++)
			{
				Global.totalFitness += Global.currentFitness[outer][inner];
			}
		}
		
		//Outputs the global and average fitness
		System.out.println("Global fitness is " + Global.totalFitness);
		System.out.println("Average fitness is " + (Global.totalFitness)/Global.populationSize);
		 
		
	}
	
	/**
	 * This function takes the location of two queens and returns a boolean stating if the two are in a collision or not
	 * @param row1 : the row of the base queen
	 * @param col1 : the column of base queen
	 * @param row2 : the row of the compared to queen
	 * @param col2 : the column of the compared to queen
	 * @return
	 */
	public static boolean collisionDetection(int row1, int col1, int row2, int col2) {
		return (row1 != row2 && col1 != col2 && (row1 - row2) != (col1 - col2) && (row1 - row2) != (col2 - col1) ? false : true);
	}
	
	/**
	 * Calculates the average fitness of the current generation and archives it in Global.allAverageFitness
	 */
	public static void addAverageFitness() 
	{
		int average = 0;
		
		for (int j = 0; j < Global.populationSize;j++)
		{
			for (int i = 0; i < 8;i++)
			{
			average += Global.currentFitness[j][i];
			}
		}
		
		average = average / Global.populationSize;
		
		Global.allAverageFitness.add(average);
	}
	
	/**
	 * Calculates the average fitness of the current generation and archives it in Global.allAverageFitness
	 */
	public static void addBestFitness() 
	{
		String best;
		int temp = 99;
		int localTotal = 0;
		
		for (int j = 0; j < Global.populationSize;j++)
		{
		}

	}
	
	/**
	 * This function compares the fitness of every chromosome in the current generation find a solution
	 */
	public static void checkForSolution(){
		
		int localTotal;
		
		for (int j = 0; j < Global.populationSize;j++)
		{
			localTotal = 0;
			
			for (int i = 0; i < 8;i++)
			{				
				localTotal+= Global.currentFitness[j][i];
			}
			
			if (localTotal == 0){ 
				Global.generationAge++;
				System.out.println("Solution found!\nGeneration:" + Global.generationAge + " Fitness is " + localTotal);
				Global.solutionChild = j;
				for (int i = 0; i < 8; i++)
				{					
					System.out.println("Chromosome: " + Global.currentGeneration[j][i] + " ");					
				}
				
				Global.endTime = System.nanoTime();				
				System.out.println("Run Time: " + TimeUnit.SECONDS.convert(Global.endTime - Global.startTime, TimeUnit.NANOSECONDS) + " seconds");
				
				Main.Save();
				Main.printSolution(Global.currentGeneration[Global.solutionChild]);
				
				System.exit(0);	
			}
		}
	}

	/**
	 * Archives the current generation's fitness values and makes room for the new generation
	 * 
	 */
	public static void addCurrent() 
	{		
		//Adds current fitness to previos value and 
		for (int i = 0; i < Global.populationSize; i++)
		{
			System.arraycopy(Global.currentFitness[i], 0,  Global.previousFitness[i], 0, 8);
		}
		
		
		
	}

}
