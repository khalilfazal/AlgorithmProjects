import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	 *  7 : Worst fitness level, very bad queen. Not worthy of queen title.
	 *  
	 * PIECE POSITIONS:
	 * Start from top left
	 */

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Boolean that keeps track 
		boolean fitnessAchieved = false;
		
		//Calculates the start time
		Global.startTime = System.nanoTime();
		
		//Randomly generates 8 chromosomes for an initial generation
		Population.randomInitial();		
		
		//If initial generation's fitness is 0, program will end
		Fitness.calculateTotalFitness();
		if (Global.totalFitness == 0)
			fitnessAchieved = true;
		
		/**
		 * This for loop outputs the genes and fitness of the first generation
		 * This is neccesary because the fitness of the first generation gives us a good picture of how long
		 * it will take to find the solution.
		 */
		
		for (int outer = 0; outer < Global.populationSize; outer++)
		{
			for (int inner = 0; inner < 8; inner++)
			{
				System.out.println("Fitness of Initial Generation Chromosome " + inner + " of Initial Parent " + outer + " : " + Global.currentGeneration[outer][inner] + " - " + Global.currentFitness[outer][inner]);
			}
			System.out.println();
		}
		
		/**
		* While loop runs until a solution has been found
		* With every iteration it generates a new generation based on the previous is created
		* This new generation has its fitness calculated and if there's no solution, it is prepared to be replaced by a newer generation
		**/
		while (!fitnessAchieved)
		{	
			//Total fitness needs to be calculated for a new generation to be created or to initiate mating
			Fitness.calculateTotalFitness();
			
			//The current generation is 'retired', and the program prepares to create a new set of solutions
			Population.addCurrent();
			
			//Current generation's best and average fitness is calculated and stored
			//Fitness.addBestFitness();
			Fitness.addAverageFitness();
			
			//All fitness values are calculated and checked for a solution 
			Fitness.checkForSolution();
			
			//The current generation's fitness values are stored to make space for the new generation's fitness values
			Fitness.addCurrent();
			
			//A new generation is generated
			Population.newGeneration();
			
			//This resets the current child index is reset
			Global.currentChildIndex = 0;
			
			//The current generation age is output
			System.out.println("Generation: " + Global.generationAge);
			
			//Random variable used to help simulate a dice roll for mating probability
			Random random = new Random();
			int crossoverProbability = random.nextInt(10);
			int mutationProbability = random.nextInt(10);
			
			//Checks for 80% crossover probability
			if (crossoverProbability < 8){
				Mating.Crossover();
			}
			//If mutation probability is met, mutation will occur
			else if((mutationProbability < 5) && (mutationProbability >= 0))
			{
				Mating.Mutation();
			}
			//Last resort is Cloning. If probability for Crossover and Mutation aren't met, Cloning is initiated
			else{
				Mating.Cloning();
			}
			
			//Adds total fitness of all chromosomes in current population
			//This is done by adding fitness of all genes, and if fitness of any one is 0, then while loop ends
			int tempFitness;
			fitnessAchieved = false;
			
			//Calculates the total fitness for all solutions in the current generation
			Fitness.calculateTotalFitness();
			
			//If the total fitness has been achieved, the for loop ends and solution is outputted.
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
	
	/**
	 * This function saves the average fitness of every generation in a text file
	 */
	public static void Save() 
	{	
		//Buffer stores the values of all the average fitness values in 
        List<String> buffer = new ArrayList<String>(Global.allAverageFitness.size()) ;
        
        //The buffer stores all the average fitness values through the for loop
        for (Integer myInt : Global.allAverageFitness) 
        { 
        	buffer.add(Integer.toString(myInt)); 
        }
        
	    //All the average fitness is saved in an output file
        //An io exception is also caught and dealt with
	    try {
	    	FileWriter writer = new FileWriter("output.txt"); 
	    	for(String str: buffer) {
	    	  writer.write('\n' + str + '\n');
	    	}
	    	writer.close();
	    } catch (IOException e) {
	    }
	}
	
	/**
	 * This for loop simply outputs the solution in an ASCII chessboard format in console
	 * @param places : this array stores the genes of the solution and is used to display the answer
	 */
	public static void printSolution(int[] places)
	{
		for (int i= 0; i< 8; i++)
		{
		    for (int j= 0; j< 9; j++)
			{
			    System.out.print("|");
			    if (places[i] == j)
			    	System.out.print("Q");
			    else
			    	System.out.print(" ");
			}
			System.out.println("");
		}
	}
}
