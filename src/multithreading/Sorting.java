package multithreading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Sorting {

	public static void main(String[] args) {

		ArrayList<String> words = new ArrayList<String>();
		File f = new File("words.txt");
		try (Scanner sc = new Scanner(f)) {
			while (sc.hasNextLine()) {
				words.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int threadsNumber = 5;
		ArrayList<String> sortedWords = threadSorting(words, threadsNumber);
		
		try (PrintWriter myWriter = new PrintWriter("sortedWords.txt"))
		{
			for(String s : sortedWords) {
				myWriter.println(s);
				
				myWriter.flush();
			}
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * function which merge sorts the given string using given number of threads
	 * @param list - given list to sort
	 * @param n - given number of threads
	 * @return - sorted list
	 */
	public static ArrayList<String> threadSorting (ArrayList<String> list, int n) {
		return mergeGroups(sortedGroups(divideToGroups(list, n)));
	}
	
	/**
	 * Function which divides the list into the given number of groups
	 * @param list - the given list for sorting
	 * @param groups - the number of threads to be divided to
	 * @return list of groups after division 
	 */
	public static ArrayList<ArrayList<String>> divideToGroups (ArrayList<String> list, int groups){
		if (groups<1 && groups > list.size()) {
			throw new IllegalArgumentException("Incorrect number of groups");
		}
		
		int n = groups;
		ArrayList<ArrayList<String>> sortedGroups = new ArrayList<ArrayList<String>>();
		int el = (int) Math.ceil((double)list.size()/n);
		for (int i = 0; i < n; i++) {
			ArrayList<String> partOfList = new ArrayList<String>();
			int start = el * i;
			if (i < n -1) {
				for (int j = 0; j < el; j++) {
				partOfList.add(list.get(start));
				start++;
				}
			} else {
				for (int k = el * (n-1); k < list.size(); k++) {
				start = el * (n-1);
				partOfList.add(list.get(k));
				}
			}
			
			sortedGroups.add(partOfList);			
		}
		return sortedGroups;
	}
	
	/**
	 * function which sorts the given lists using threads
	 * @param list - list of lists to sort
	 * @return list of sorted lists
	 */
	public static ArrayList<ArrayList<String>> sortedGroups (ArrayList<ArrayList<String>> list){
		ArrayList<Threading> threadList = new ArrayList<Threading>();
		ArrayList<ArrayList<String>> sorted = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < list.size(); i++) {
			Threading thread = new Threading(list.get(i));
			thread.start();	
			threadList.add(thread);
		}
		for (Threading t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Threading t : threadList) {
			sorted.add(t.getList());
		}
		return sorted;
	}
	
	/**
	 * function which merges a group of lists
	 * @param list - list of lists to merge
	 * @return - merged list
	 */
	public static ArrayList<String> mergeGroups (ArrayList<ArrayList<String>> list){
		if (list.size()<2) {
			return list.get(0);
		} else {
			list.add(0, merge(list.get(0), list.get(1)));
			list.remove(1);
			list.remove(1);
			return mergeGroups(list);
		}
	}
	
	/**
	 * function which merges two array lists
	 * @param first
	 * @param second
	 * @return merged list
	 */
	public static ArrayList<String> merge (ArrayList<String> first, ArrayList<String> second){
	
		int i = 0;
		int j = 0;
		ArrayList<String> list = new ArrayList<String>();
		
		while (i < first.size() && j < second.size()) {
			if (first.get(i).compareTo(second.get(j)) < 0) {
				list.add(first.get(i));
				i++;
			} else {
				list.add(second.get(j));
				j++;
			}
		}
		
		while (i < first.size()) {
			list.add(first.get(i));
			i++;
		}
		while (j < second.size()) {
			list.add(second.get(j));
			j++;
		}
		return list;
	}
	
}
	

