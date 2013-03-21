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
	 *  8 : Worst fitness level, very bad queen. Not worthy of queen title.q
	 */

public class Mating {
	/**
	 * @param args
	 */

	//Performs crossover function
	public static void Crossover(){
		
		//Random number generator
		Random random = new Random();		
		
		//Swap index holder
		int swapIndex = random.nextInt(8);
		
		//temporary variable
		int[] temp = new int[Global.populationSize - swapIndex];
		
		//temporary crossover offspring
		int[] offspringA = new int[8];
		int[] offspringB = new int[8];
		
		//Chooses 2 new parents for mating
		Mating.chooseParents();
		
		//Copies parents in offspring in preparation for crossover
		System.arraycopy(Global.currentGeneration[Global.parentA], 0, offspringA, 0, 8);
		System.arraycopy(Global.currentGeneration[Global.parentB], 0, offspringB, 0, 8);
		
		
//		System.out.print("offSpringA before: ");
//		for (int i = 0; i < 8; i++){
//			System.out.print(offspringA[i]);
//			if (i == swapIndex)
//				System.out.print("|");
//		}
//		
//		System.out.println();
//		System.out.print("offSpringB before: ");
//		
//		for (int i = 0; i < 8; i++){
//			System.out.print(offspringB[i]);
//			if (i == swapIndex)
//				System.out.print("|");
//		}
		
		//Performs crossover, by using a temporary array to swap
		System.arraycopy(offspringA, swapIndex, temp, 0, temp.length);
		System.arraycopy(offspringB, swapIndex, offspringA, swapIndex, temp.length);
		System.arraycopy(temp, 0, offspringB, swapIndex, temp.length);

//		System.out.println();
//		System.out.print("offSpringA after: ");
//		
//		for (int i = 0; i < 8; i++){
//			System.out.print(offspringA[i]);
//			if (i == swapIndex)
//				System.out.print("|");
//		}
//		
//		System.out.println();
//		System.out.print("offSpringB after: ");
//		
//		for (int i = 0; i < 8; i++){
//			System.out.print(offspringB[i]);
//			if (i == swapIndex)
//				System.out.print("|");
//		}
//		
//		System.out.println();
		
		//Both offspring for mutation
		//If mutation probability is met, mutation is applied and arrays are returned
		Mutation(offspringA);
		
		//Sends both offspring to addCurrent, which adds them to the current population
		Population.addCurrent(offspringA);
		Population.addCurrent(offspringB);		
	}
	
	public static void chooseParents()
	{
		Random random = new Random();
		
		Global.parentA = random.nextInt(8);
		Global.parentB = random.nextInt(8);
	}
	
	//Performs a mutation on a particular chromosome sent as a parameter
	public static int[] Mutation(int[] chromosome){
	
		//Random number generator
		Random random = new Random();
		int temp = random.nextInt(Global.populationSize);

		//System.out.println("Non-mutated: " +chromosome[temp]);
		chromosome[temp] = random.nextInt(Global.populationSize);
		//Global.currentGeneration[Global.currentChildIndex] = random.nextInt(8);
		
		//System.out.println("Mutated: " + chromosome[temp]);		
		
		return chromosome;		
	}
	
	//Performs a mutation on a random chromosome
	public static void Mutation(){
		
		//Random number generator
		Random random = new Random();
		int temp = random.nextInt(8);
		System.out.println("Non-mutated: " + Global.currentGeneration[Global.currentChildIndex][temp]);
		Global.currentGeneration[Global.currentChildIndex][temp] = random.nextInt(8);
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
