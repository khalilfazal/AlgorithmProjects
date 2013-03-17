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
		if (Fitness.calculateTotalFitness(0) == 0)
			fitnessAchieved = true;
		
		Global.parentA = 4;
		Global.parentB = 5;
		Mating.Crossover();
		
		for (int outer = 0; outer < 8; outer++)
		{
			//Global.generationAge++; GENERATION AGE IS INCREMENTED IN Population.randomInitial()
			for (int inner = 0; inner < 8; inner++)
			{
				System.out.println("Fitness of Chromosome " + inner + " of Initial Parent " + outer + " : " + Global.currentGeneration[outer][inner] + " - " + Global.currentFitness[outer][inner]);
			}
			System.out.println();
		}
		
		while (!fitnessAchieved)
		{
			//select chromosome here
			fitnessAchieved = true;
			
		}
	}
}
