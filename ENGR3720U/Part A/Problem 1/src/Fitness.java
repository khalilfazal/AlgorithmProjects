/**
 * @author Rayhaan Shakeel 100425726
 *
 */

import java.lang.Math;

public class Fitness {

	/**
	 * FITNESS VALUES: 
	 * -1 : Fitness has not been calculated yet 
	 *  0 : Perfect fitness, queen is not under attack nor is attacking others. 10/10 would queen.
	 * >0 : Queen is under attack. The fitness value indicates how many queens it is attacking.
	 *  7 : Worst fitness level, very bad queen. Not worthy of queen title.q
	 */
	
	//Calculates the fitness value for the current population
	public static void calculatePopulationFitness() {
		
		
		//Current fitness values are all set to -1
		Fitness.clearCurrentFitness();
		
		//This for loop calculates the fitness for all the queens in the current population
		//Outer queen is the base queen which is being compared to all the inner queens aka the other queens
		for (int chromosome = 0; chromosome < Global.populationSize ; chromosome++)
		{
			for (int column = 0; column < Global.populationSize; column++)
			{
				for (int row = 0; row < Global.populationSize; row++)
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
			for (int inner = 0; inner < Global.populationSize; inner++){
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
			for (int inner = 0; inner < Global.populationSize; inner++)
			{
				Global.totalFitness += Global.currentFitness[outer][inner];
			}
		}
		
		System.out.println("Global fitness is " + Global.totalFitness);
		System.out.println("Average fitness is " + (Global.totalFitness)/Global.populationSize);
		 
		
	}
	
	public static boolean collisionDetection(int row1, int col1, int row2, int col2) {
		return (row1 != row2 && col1 != col2 && (row1 - row2) != (col1 - col2) && (row1 - row2) != (col2 - col1) ? false : true);
	}
	
	public static void addAverageFitness() 
	{
		int average = 0;
		String averageFitness;
		
		for (int j = 0; j < Global.populationSize;j++)
		{
			for (int i = 0; i < Global.populationSize;i++)
			{
			average += Global.currentFitness[j][i];
			}
		}
		
		average = average / Global.populationSize;
		averageFitness = Integer.toString(average);
		
		Global.allAverageFitness.add(averageFitness);
	}
	
	public static void addBestFitness() 
	{
		String best;
		int temp = 99;
		int localTotal = 0;
		
		for (int j = 0; j < Global.populationSize;j++)
		{
			if (temp > 0)
				temp = 0;
			else if (temp == 0)
			{
				System.out.println("Solution FOUND! Generation: " + Global.generationAge);
				System.exit(0);			
			}
			else if (temp < 5)
				System.out.println("temp is " + temp);
			
			for (int i = 0; i < Global.populationSize;i++)
			{
				localTotal += Global.currentFitness[j][i];
				
				temp+= Global.currentFitness[j][i];
			}
			
			
				
		}
		
		best = Integer.toString(temp);
		
		Global.allBestFitness.add(best);
		System.out.println("Best Fitness is " + temp);
		
		if (temp == 0)
		{
			System.out.println("Solution FOUND! Generation: " + Global.generationAge);
			System.exit(0);
		}
	}
	
	public static void checkForSolution(){
		
		int localTotal;
		
		for (int j = 0; j < Global.populationSize;j++)
		{
			localTotal = 0;
			
			for (int i = 0; i < Global.populationSize;i++)
			{				
				localTotal+= Global.currentFitness[j][i];
			}
			
			if (localTotal == 0){
				System.out.println("Solution found!" + Global.generationAge);
				System.exit(0);
			}
			else if (localTotal < 5)
				System.out.println("Less than 5!!!!!!!!!!!!!!!!!!1");
			
				
		}
	}
}
