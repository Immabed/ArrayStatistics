import java.util.Random;

/**
 * Homework 3 for CMPT 166, Spring 2016, Trinity Western University.
 * ArrayStat Class created according to homework specification.
 * Allows an array of numbers, randomly generated, to be analyzed by frequency.
 * @author Brady Coles
 */
public class ArrayStat {
	/**
	 * The array of unsorted random numbers.
	 */
	private int[] numberArray;
	/**
	 * The sorted array of numberArray's elements.
	 */
	private int[] sortedNumberArray;
	
	/**
	 * If no array length is provided, this will be used in the initialization
	 * of numberArray.
	 */
	private final static int DEFAULT_ARRAY_SIZE = 300;
	
	/**
	 * Creates and sorts an array using the default array size.
	 */
	public ArrayStat() {
		this(DEFAULT_ARRAY_SIZE);
	}
	/**
	 * Creates and sorts an array using the given array size.
	 * @param arrayLength The number of array elements to create.
	 */
	public ArrayStat(int arrayLength) {
		generateRandomArray(arrayLength);
		sort();
	}
	/**
	 * Attempts to copy the array and sorted array from another ArrayStat object,
	 * if not possible (the object or arrays are null), creates a new array of
	 * default length.
	 * @param otherArray An ArrayStat object to replicate, should be initialized.
	 */
	public ArrayStat(ArrayStat otherArray) {
		if (otherArray == null) {
			generateRandomArray(DEFAULT_ARRAY_SIZE);
			sort();
		}
		else if (otherArray.numberArray != null) {
			this.numberArray = otherArray.numberArray;
			if (otherArray.sortedNumberArray != null) {
				this.sortedNumberArray = otherArray.sortedNumberArray;
			}
			else {
				sort();
			}
		}
		else {
			generateRandomArray(DEFAULT_ARRAY_SIZE);
			sort();
		}
	}
	
	/**
	 * Initializes numberArray with the specified length, and fills it with
	 * random integers between 0 and 100 (inclusive).
	 * @param arrayLength The number of elements to create in numberArray.
	 */
	private void generateRandomArray(int arrayLength) {
		Random randomGenerator = new Random();
		numberArray = new int[arrayLength];
		for (int element : numberArray) {
			element = randomGenerator.nextInt(101); //Exclusive bound, 0-100
		}
	}
	
	public void sort() {
		// TODO: Implement Sort
	}
}
