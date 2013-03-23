import java.util.Random;

/**
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */

	/**
	 * FITNESS VALUES: 
	 * -1 : Fitness has not been calculated yet 
	 *  0 : Perfect fitness, queen is not under attack nor is attacking others. 10/10 would queen.
	 * >0 : Queen is under attack. The fitness value indicates how many queens it is attacking.
	 *  8 : Worst fitness level, very bad queen. Not worthy of queen title.q
	 */

public class Mating {

	/**
	 * This class handles the crossover mating of the chromosome.
	 */
	public static void Crossover(){
		
		//Random number generator
		Random random = new Random();		
		
		//Swap index holder
		int swapIndex = random.nextInt(8);
		
		//temporary variable
		int[] temp = new int[8 - swapIndex];
		
		//temporary crossover offspring
		int[] offspringA = new int[8];
		int[] offspringB = new int[8];
		
		//Chooses 2 new parents for mating
		Mating.chooseParents();
		
		//Copies parents in offspring in preparation for crossover
		System.arraycopy(Global.currentGeneration[Global.parentA], 0, offspringA, 0, 8);
		System.arraycopy(Global.currentGeneration[Global.parentB], 0, offspringB, 0, 8);
		
		
		//Performs crossover, by using a temporary array to swap
		System.arraycopy(offspringA, swapIndex, temp, 0, temp.length);
		System.arraycopy(offspringB, swapIndex, offspringA, swapIndex, temp.length);
		System.arraycopy(temp, 0, offspringB, swapIndex, temp.length);

		
		//Both offspring for mutation
		//If mutation probability is met, mutation is applied and arrays are returned
		Mutation(offspringA);
		
		Population.addCurrent(offspringA);
		Population.addCurrent(offspringB);		
	} 
	
	/**
	 * Chooses two parents for mating via the roulette selection method
	 */
	public static void chooseParents()
	{
		
		double totalFitness = 0;
		int temp = 0, prev = 0;
		
		int[] rouletteWheelSlices = new int[Global.populationSize];
		double[] fitnessRatios = new double[Global.populationSize];
		int[] localTotal = new int[Global.populationSize];
		Random random = new Random();		

		boolean uniqueParent = false;
		
		for (int i = 0; i < localTotal.length; i++)
		{
			localTotal[i] = 0;
		}
		
		//Calculates total fitness, sum of all fitness
		//Needed to calculate fitness ratio
		for (int outer = 0; outer < Global.populationSize; outer++)
		{
			for (int inner = 0; inner < 8; inner++)
			{
				totalFitness += Global.previousFitness[outer][inner];
				localTotal[outer] += Global.previousFitness[outer][inner]; 
			}
		}		
		
		//Calculates the fitness ratio for all 8 solutions
		for (int i = 0; i < Global.populationSize; i++)
		{
			fitnessRatios[i] = ((localTotal[i]/totalFitness) * 100);
			//System.out.println("Fitness Ratio: "  + fitnessRatios[i] + ((localTotal[i]/totalFitness) * 100));
		}
		
		temp = random.nextInt(100);
		
		//Chooses parentA through roulette selection
		for (int outer = 1; outer < Global.populationSize; outer++)
		{
			prev = 0;
			for (int inner = outer - 1; inner >= 0; inner--)
			{
				prev += fitnessRatios[inner];
			}	
			
			if ((temp > prev) && (temp < (prev + fitnessRatios[outer])))
			{
				Global.parentA = outer;
				//System.out.println("Parent A:" + Global.parentA);
			}
					
		}	
				
		//Chooses parentB through roulette selection
		//A loop is neccesary to ensure parentB is not the same as parentA
		while(uniqueParent == false)
		{
			temp = random.nextInt(100);
			
			for (int outer = 1; outer < Global.populationSize; outer++)
			{
				prev = 0;
				for (int inner = outer - 1; inner >= 0; inner--)
				{
					prev += fitnessRatios[inner];
				}	
				
				if ((outer != Global.parentA) && (temp > prev) && (temp < (prev + fitnessRatios[outer])))
				{
					Global.parentB = outer;
					uniqueParent = true;
				}			
			}
		}
	}
	
	//Performs a mutation on a particular chromosome sent as a parameter
	public static int[] Mutation(int[] chromosome){
	
		//Random number generator
		Random random = new Random();
		int temp = random.nextInt(8);

		chromosome[temp] = random.nextInt(8);
		
	
		return chromosome;		
	}
	
	//Performs a mutation on a random chromosome
	public static void Mutation(){
		
		//Random number generator
		Random random = new Random();
		int temp = random.nextInt(8);
		System.out.println("Non-mutated: " + Global.currentGeneration[Global.currentChildIndex][temp]);
		Global.currentGeneration[Global.currentChildIndex][temp] = random.nextInt(Global.populationSize);
		//Global.currentGeneration[Global.currentChildIndex] = random.nextInt(8);
		
		System.out.println("Mutated: " + Global.currentGeneration[Global.currentChildIndex][temp]);
		
		Population.addCurrent(Global.currentGeneration[Global.currentChildIndex]);
	}
	
	public static void Cloning()
	{
		chooseParents();
		
		Population.addCurrent(Global.currentGeneration[Global.parentA]);
		Population.addCurrent(Global.currentGeneration[Global.parentB]);
	}

}
