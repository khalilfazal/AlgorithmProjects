/**
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
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
			
			boolean[] valueGenerated = new boolean[Global.populationSize];
			
			for (int inner = 0; inner < 8 ;inner++)
			{
				while(true)
				{
					temp = randomGenerator.nextInt(Global.populationSize); 
					//Global.allGenerations[Global.generationAge][i] = temp;
					
					if (valueGenerated[temp] == false)
					{
						Global.currentGeneration[outer][inner] = temp;
						valueGenerated[temp] = true;
						break;
					}
				}
				System.out.println("Chromosome: " + outer + " gene: " + inner + " value: " + temp);
			}
		}
		
		//Calculates fitness for randomly generated initial population
		Fitness.calculatePopulationFitness();
	}
	
	
	//Adds an array to the current population
	public static void addCurrent(int[] child)
	{
		if (Global.currentChildIndex == Global.populationSize-1)
			return;
		
		System.arraycopy(child, 0,  Global.currentGeneration[Global.currentChildIndex], 0, 8);
		
		Global.currentChildIndex++;
	}
	
	/**
	 * Stores current population as parent and readies generation of a new population
	 */
	public static void addCurrent() 
	{
		
		if (Global.currentChildIndex == Global.populationSize-1)
			return;
		
		System.arraycopy(Global.currentGeneration[Global.currentChildIndex], 0,  Global.previousGeneration[Global.currentChildIndex], 0, 8);
		
		Global.currentChildIndex++;
	}
	
	/**
	 * Increments the generation age, and by doing so it initiates the generation of a new generation
	 */
	public static void newGeneration()
	{
		Global.generationAge++;
		
	}
	
	
}
