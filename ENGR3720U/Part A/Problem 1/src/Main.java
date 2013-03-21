import java.util.Random;

/**
 * @author Rayhaan Shakeel 100425726
 *
 */

	/**
	 * FITNESS VALUES: 
	 * -1 : Fitness has not been calculated yet 
	 *  0 : Perfect fitness, queen is not under attack nor is attacking others. 10/10 would queen.
	 * >0 : Queen is under attack. The fitness value indicates how many queens it is attacking.
	 *  7 : Worst fitness level, very bad queen. Not worthy of queen title.
	 *  
	 * PIECE POSITIONS:
	 * Start from top left
	 */

//import java.util.*;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.io.* ;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean fitnessAchieved = false;
		
		//Randomly generates 8 chromosomes for an initial generation
		Population.randomInitial();		
		
		//If initial generation's fitness is perfect, program will end
		Fitness.calculateTotalFitness();
		if (Global.totalFitness == 0)
			fitnessAchieved = true;
		
		Global.parentA = 4;
		Global.parentB = 5;
		Mating.Crossover();
		
		for (int outer = 0; outer < Global.populationSize; outer++)
		{
			//Global.generationAge++; GENERATION AGE IS INCREMENTED IN Population.randomInitial()
			for (int inner = 0; inner < Global.populationSize; inner++)
			{
				System.out.println("Fitness of Chromosome " + inner + " of Initial Parent " + outer + " : " + Global.currentGeneration[outer][inner] + " - " + Global.currentFitness[outer][inner]);
			}
			System.out.println();
		}
		
		while (!fitnessAchieved)
		{		
			Population.addCurrent();
			//Fitness.addBestFitness();
			//Fitness.addAverageFitness();
			Fitness.checkForSolution();
			Population.newGeneration();
			Global.currentChildIndex = 0;
			System.out.println("Generation: " + Global.generationAge);
			
			Random random = new Random();
			int matingProbability = random.nextInt(10);
			
			//Checks for 80% crossover probability
			if (matingProbability < 8){
				Mating.Crossover();
				System.out.println("Crossover " + matingProbability);
			}
			//If Crossover is improbable it does Mutation
			else if((matingProbability < 5) && (matingProbability >= 0))
			{
				Mating.Mutation();
				System.out.println("Mutation " + matingProbability);
			}
			//Last resort is Cloning
			else{
				Mating.Cloning();
				System.out.println("Cloning " + matingProbability);
			}
			
			//Adds total fitness of all chromosomes in current population
			//This is done by adding fitness of all genes, and if fitness of any one is 0, then while loop ends
			int tempFitness;
			fitnessAchieved = false;
			
			Fitness.calculateTotalFitness();
			
			if (Global.totalFitness == 0)
				fitnessAchieved = true;
		}
		
		System.out.println("Fitness achieved after " + Global.generationAge + " generations");
		System.out.print("Solution: ");
		
		for (int i = 0; i < Global.populationSize; i++)
		{
			System.out.print(" " + Global.currentGeneration[Global.solutionChild][i] +" ");
		}
	}
}
