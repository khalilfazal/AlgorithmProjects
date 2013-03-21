/**
 * @author Rayhaan Shakeel 100425726
 *
 */

import java.util.Random;
public class Population {

	/**
	 * @param args
	 * @category Randomly generates an initial population of 8 chromosomes
	 */
	//Randomly generates an initial population of 8 chromosomes
	public static void randomInitial() {
		
		//Marks current generation as Generation 0
		Global.generationAge++;

		//Randomly generates an initial population
		Random randomGenerator = new Random();
		
		//Temporarily stores random value
		int temp;
		
		//For loop randomly generates an initial population
		for (int outer = 0; outer < Global.populationSize; outer++)
		{
			for (int inner = 0; inner < Global.populationSize ;inner++)
			{				
				temp = randomGenerator.nextInt(Global.populationSize); 
				//Global.allGenerations[Global.generationAge][i] = temp;
				Global.currentGeneration[outer][inner] = temp;
				System.out.println("Chromosome: " + outer + " gene: " + inner + " value: " + temp);
			}
		}
		
		//Calculates fitness for randomly generated initial population
		Fitness.calculatePopulationFitness();
	}
	
	
	//Adds an array to the current population
	public static void addCurrent(int[] child)
	{
		if (Global.currentChildIndex == 7)
			return;
		
		System.arraycopy(child, 0,  Global.currentGeneration[Global.currentChildIndex], 0, Global.populationSize);
		
		Global.currentChildIndex++;
	}
	
	public static void addCurrent() 
	{
		
		if (Global.currentChildIndex == 7)
			return;
		
		System.arraycopy(Global.currentGeneration[Global.currentChildIndex], 0,  Global.previousGeneration[Global.currentChildIndex], 0, Global.populationSize);
		
		Global.currentChildIndex++;
	}
	
	public static void newGeneration()
	{
		Global.generationAge++;
		
	}
	
	
}
