package selectionSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SelectionSort {

	public static void main(String[] args) {
		
		String [] countries = new String [196];
		File f = new File("countries.txt");
		try (Scanner sc = new Scanner(f)) {
			int index = 0;
			while (sc.hasNextLine()) {
				countries [index] = sc.nextLine();
				index ++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(Arrays.toString(countries));		
		
		selectionSort(countries);
		
		System.out.println(Arrays.toString(countries));
		
		System.out.println(search(countries, "albania", 0, 195));
		
	}

	/**
	 * swaps the lowest value element with the first element in line
	 * @param list - given array list
	 * @param index - the starting index of array
	 * @param min - the index of lowest value element in the array
	 */
	private static void swap (String[] list, int index, int min) {
		String temp = list[index];
		list [index] = list[min];
		list[min] = temp;
	}
	
	/**
	 * finds the element of the lowest value alphabetically
	 * @param list - array list
	 * @param start - the starting index of array
	 * @return - index of the lowest value
	 */
	private static int minValue (String[] list, int start) {
		String minVal = list[start];
		int index = start;
		for (int i = start; i<list.length; i++) {
			if (list[i].compareTo(minVal)<0) {
				minVal = list[i];
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * sorts the given array of strings alphabetically
	 * @param list
	 */
	private static void selectionSort(String[] list) {
		
		for (int i = 0; i < list.length-1; i++) {
			int minVal = minValue(list, i);
			swap(list, i, minVal);
		}
		
	}
	
	/**
	 * searches for the given string in the list using binary search (ignores case differences)
	 * @param list - given list
	 * @param word - the word to search for
	 * @param start - the starting index of an array
	 * @param finish - the ending index of an array
	 * @return - the index where the string is located, -1 if it does not exist
	 */
	public static int search (String[] list, String word, int start, int finish) {
		if (start <= finish) {
			int mid = (start + finish)/2;
			if (list[mid].equalsIgnoreCase(word)) {
				return mid;
			}else if (list[mid].compareToIgnoreCase(word) < 1){
				return search (list, word, mid + 1, finish);
			} else {
				return search(list, word, start, mid - 1);
			}
		} else {
			return -1;
		}
	}

}
