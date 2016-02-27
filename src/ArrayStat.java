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
	 * The statistics for how many elements are in each subdivision.
	 * 0 : 1 - 20
	 * 1 : 21 - 40
	 * 2 : 41 - 60
	 * 3 : 61 - 80
	 * 4 : 81 - 100
	 */
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
	 * random integers according to {@link #monteCarloNumber()}
	 * @param arrayLength The number of elements to create in numberArray.
	 */
	private void generateRandomArray(int arrayLength) {
		numberArray = new int[arrayLength];
		for (int i = 0; i < numberArray.length; i++) {
			numberArray[i] = generateRandomNumber();
		}
	}
	
	/**
	 * Returns a random number between 1-100 (inclusive).
	 * @return Random integer from [1, 100].
	 */
	private int generateRandomNumber() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(100) + 1; //Exclusive bound, 1-100
	}
	
	/**
	 * Makes sortedNumberArray an array of the elements of numberArray, but 
	 * sorted in increasing order.
	 */
	public void sort() {
		sortedNumberArray = mergeSort(numberArray);
	}
	
	/**
	 * Performs a merge sort on an array and returns a copy of the array sorted
	 * in increasing order.
	 * @param startingArray An int array to be sorted.
	 * @return A copy of startingArray sorted in increasing order.
	 */
	public int[] mergeSort(int[] startingArray) {
		// Check if array has any elements.
		if (startingArray == null || startingArray.length == 0) {
			System.out.println("Error has occurred, tried to sort empty array.");
			return new int[0];
		}
		// Base case, if array has one element, it is sorted, so return it.
		else if (startingArray.length == 1) {
			return startingArray;
		}
		// Recursive case, splits the array into two, and calls itself on both.
		else {
			// Create and populate first half array
			int[] array1 = new int[startingArray.length / 2];
			for (int i = 0; i < array1.length; i++) {
				array1[i] = startingArray[i];
			}
			// Create and populate second half array
			int[] array2 = new int[startingArray.length - startingArray.length / 2];
			for (int i = 0; i < array2.length; i++) {
				array2[i] = startingArray[i + array1.length];
			}
			// Recursion, sort both half arrays.
			array1 = mergeSort(array1);
			array2 = mergeSort(array2);
			// Return the merged array of both half arrays.
			return mergeSortMerge(array1, array2);
		}
	}
	
	/**
	 * Merges two sorted arrays into a new sorted array, for use by {@link #mergeSort}.
	 * Precondition: Both arrays are sorted in increasing order.
	 * @param array1 A sorted array of integers (in increasing order).
	 * @param array2 A sorted array of integers (in increasing order).
	 * @return A new sorted array of the elements in array1 and array2 (in 
	 * increasing order).
	 */
	private int[] mergeSortMerge(int[] array1, int[] array2) {
		int[] returnArray = new int[array1.length + array2.length];
		int array1index = 0;
		int array2index = 0;
		int returnArrayIndex = 0;
		while (array1index < array1.length && array2index < array2.length) {
			if (array1[array1index] <= array2[array2index]) {
				returnArray[returnArrayIndex] = array1[array1index];
				array1index ++;
			}
			else {
				returnArray[returnArrayIndex] = array2[array2index];
				array2index ++;
			}
			returnArrayIndex ++;
		}
		if (array1index < array1.length) {
			for (int i = array1index; i < array1.length; i++) {
				returnArray[returnArrayIndex] = array1[i];
				returnArrayIndex++;
			}
		}
		if (array2index < array2.length) {
			for (int i = array2index; i < array2.length; i++) {
				returnArray[returnArrayIndex] = array2[i];
				returnArrayIndex++;
			}
		}
		return returnArray;
	}
	
	/**
	 * Checks if both numberArray and sortedNumberArray are populated. If it can,
	 * it populates sortedNumberArray from numberArray to ensure arrays are 
	 * populated.
	 * @return true if both numberArray and sortedNumberArray are populated by 
	 * the end of this function, otherwise false.
	 */
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
			generateStatistics();
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
	 * Fills arrayStatistics with the proper values based on sortedNumberArray.
	 */
	private void generateStatistics() {
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
	
	/**
	 * Returns the index of the first element greater than breakPoint in 
	 * sortedNumberArray, which is equal to the number of elements less than
	 * or equal to breakPoint in sortedNumberArray.
	 * @param breakPoint The value for which to find the first element greater
	 * than it.
	 * @return The index of the first element greater than breakPoint. If breakPoint
	 * is greater than or equal to the last element, return what would be the next
	 * index, which is the same as the length as sortedNumberArray.
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
