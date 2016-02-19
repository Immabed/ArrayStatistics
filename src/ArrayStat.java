import java.util.Random;
import java.util.Arrays;

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
	
	private int[] arrayStatistics;
	
	/**
	 * If no array length is provided, this will be used in the initialization
	 * of numberArray.
	 */
	private final static int DEFAULT_ARRAY_SIZE = 300;
	/**
	 * The size of statistic ranges, array sizes should be a multiple of this.
	 */
	private final static int CLASS_INTERVAL = 20;
	
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
		for (int i = 0; i < numberArray.length; i++) {
			numberArray[i] = randomGenerator.nextInt(101); //Exclusive bound, 0-100
		}
	}
	
	
	public void sort() {
		sortedNumberArray = new int[numberArray.length];
		for (int i = 0; i < numberArray.length; i++) {
			sortedNumberArray[i] = numberArray[i];
		}
		// TODO: Implement Sort
		Arrays.sort(sortedNumberArray);
	}
	
	private boolean ensureArraysArePopulated() {
		if (sortedNumberArray == null) {
			if (numberArray == null) {
				generateRandomArray(DEFAULT_ARRAY_SIZE);
			}
			sort();
		}
		if (sortedNumberArray != null && numberArray != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Prints out the statistics to screen. If statistics have not been generated,
	 * generates statistics.
	 */
	public void getStatistics() { 
		if (arrayStatistics == null) {
			if (!ensureArraysArePopulated()) {
				System.out.println("Arrays are empty,cannot comply.");
				return;
			}
			/**
			 * The statistics for how many elements are in each subdivision.
			 * 0 : 1 - 20
			 * 1 : 21 - 40
			 * 2 : 41 - 60
			 * 3 : 61 - 80
			 * 4 : 81 - 100
			 */
			arrayStatistics = new int[5];
			int runningTotal = 0;
			for (int i = 0; i < arrayStatistics.length; i++) {
				arrayStatistics[i] = binarySearch(CLASS_INTERVAL 
						+ (CLASS_INTERVAL * i)) - runningTotal;
				runningTotal += arrayStatistics[i];
			}
		}
		int maxLength = Integer.toString(
				sortedNumberArray[sortedNumberArray.length - 1]).length();
		for (int i = 0; i < arrayStatistics.length; i++) {
			System.out.printf("%"+maxLength+"d - %"+maxLength+"d: ", 
					(i * CLASS_INTERVAL) + 1, (i + 1) * CLASS_INTERVAL); //TODO: Support odd length arrays
			for (int n = 0; n <= (arrayStatistics[i] * DEFAULT_ARRAY_SIZE 
					/ numberArray.length); n++) {
				System.out.print("*");
			}
			System.out.println("  " + arrayStatistics[i]);
		}
		
	}
	
	/**
	 * 
	 * @param breakPoint 
	 * @return The index of the first element greater than breakPoint
	 */
	private int binarySearch(int breakPoint) {
		if (sortedNumberArray == null) {
			return -1;
		}
		/**
		 * high : one greater than last index in search range
		 * low : first index in search range
		 * mid : midpoint of range
		 * When high = low + 1, only one element is left in the range
		 */
		int high = sortedNumberArray.length;
		int low = 0;
		int mid = (high - low) / 2;
		while (high > low + 1) {
			mid = low + (high - low) / 2;
			if (sortedNumberArray[mid] > breakPoint) {
				high = mid;
			}
			else if (sortedNumberArray[mid] <= breakPoint) {
				low = mid;
			}
		}
		/**
		 * high is returned because it represents the index after the last 
		 * element less than or equal to breakPoint. That is the same as the
		 * number of elements less than or equal to breakPoint.
		 */
		return high;
	}
	
	/**
	 * Checks if sorted arrays contain same values.
	 * @param otherArray An initialized ArrayStat object to compare.
	 * @return Only true if if the values of the array are the same, 
	 * (not the order). Compares sorted arrays.
	 */
	public boolean equals(ArrayStat otherArray) {
		if (otherArray == null || otherArray.getClass() != this.getClass()) {
			return false;
		}
		
		if (this.sortedNumberArray == null && this.numberArray != null) {
			this.sort();
		}
		if (otherArray.sortedNumberArray == null && 
				otherArray.numberArray != null) {
			otherArray.sort();
		}
		
		if (this.sortedNumberArray.length 
				!= otherArray.sortedNumberArray.length) {
			return false;
		}
		else {
			for (int i = 0; i < sortedNumberArray.length; i ++) {
				if (otherArray.sortedNumberArray[i] 
						!= this.sortedNumberArray[i]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the length of the array, as well as every element in the sorted
	 * array in order.
	 * @return The length of the array, and every element of the sorted array.
	 */
	public String toString() {
		if (sortedNumberArray == null && numberArray != null) {
			sort();
		}
		else if (numberArray == null && sortedNumberArray == null) {
			return "Empty ArrayStat: Array is not initialized";
		}
		String returnString = String.format("ArrayStat object of length %d \n", 
				sortedNumberArray.length);
		
		int maxLength = Integer.toString(
				sortedNumberArray[sortedNumberArray.length-1]).length();
		for (int i = 0; i < sortedNumberArray.length; i++) {
			returnString += String.format("%"+maxLength+"d ", sortedNumberArray[i]);
			if ((i + 1) % 20 == 0) {
				returnString += "\n";
			}
		}
		returnString += "\n";
		return returnString;
	}
}
