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
		for (int chromosome = 0; chromosome <8 ; chromosome++)
		{
			System.out.println();
			for (int column = 0; column < 8; column++)
			{
				for (int row = 0; row < 8; row++)
				{
					//If currently traversed inner queen shares a row with the outer queen, the outer queen's
					//fitness value is increased
					
					//Same queen
					if (column == row){
						//do nothing since you don't want to compare the current queen with itself, doing so
						//will corrupt fitness value
					}
					//Two queens share a horizontal row
					else if (Global.currentGeneration[chromosome][column] == Global.currentGeneration[chromosome][row]){					
						Global.currentFitness[column][row]++;
						System.out.println("Chromosome " + chromosome +" Queen " + column + " and Queen " + row + " are conflicting horizontally");
					}
					//Two queens are sharing a diagonal line
					//formula is ((row + column) - (row + column)) == (divisible by 2)
					//else if (((Math.abs((Global.currentGeneration[outer] + outer) - (Global.currentGeneration[inner] + inner))) % 2) == 0){
					else if ((Global.currentGeneration[chromosome][column] + column) == (Global.currentGeneration[chromosome][row] + row)){
						Global.currentFitness[chromosome][column]++;
						System.out.println("Chromosome " + chromosome +" Queen " + column + " and Queen " + row + " are conflicting diagonally, up");
					}
					else if ((Math.abs((Math.abs(Global.currentGeneration[chromosome][column] - column)) - (Math.abs(Global.currentGeneration[chromosome][row] - row)))) == 0){
						Global.currentFitness[chromosome][column]++;
						System.out.println("Chromosome " + chromosome +" Queen " + column + " and Queen " + row + " are conflicting diagonally");
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
		
		for (int outer = 0; outer < Global.populationSize; outer++)
		{
			for (int inner = 0; inner < Global.populationSize; inner++)
			{
				Global.totalFitness += Global.currentFitness[outer][inner];
			}
		}
		
	}
	
	//Calculates the total fitness for previous generation
	public static void calculatePreviousFitness()
	{
		
		
	}
}
