/**
 * @author Rayhaan Shakeel 100425726
 *
 */

import java.util.Random;
public class Population {

	/**
	 * @param args
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
		for (int outer = 0; outer < 8; outer++)
		{
			for (int inner = 0; inner < 8 ;inner++)
			{				
				temp = randomGenerator.nextInt(8); 
				//Global.allGenerations[Global.generationAge][i] = temp;
				Global.currentGeneration[outer][inner] = temp;
				System.out.println("Chromosome: " + outer + " gene: " + inner + " value: " + temp);
			}
		}
		
		//Calculates fitness for randomly generated initial population
		Fitness.calculatePopulationFitness();		
	}
	
	
	//Adds an array to the current population
	public static void addCurrent(int[] newChild) 
	{
		System.arraycopy(newChild, 0,  Global.currentGeneration[Global.currentChildIndex], 0, 8);
		
		//Fitness.calculateIndividualFitness(newChild); FITNESS IS CALCULATED ALL TOGETHER AT THE END
		
		Global.currentChildIndex++;
	}
}
