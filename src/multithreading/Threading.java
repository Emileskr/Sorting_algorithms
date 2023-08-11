package multithreading;

import java.util.ArrayList;

public class Threading extends Thread {

	private ArrayList<String> list;
	/**
	 * constructor
	 * @param a - a list to be sorted
	 */
	public Threading (ArrayList<String> a) {
		list = a;
	}
	
	@Override
	public void run() {
		sort(list);
	}
	
	public ArrayList<String> getList (){
		return list;
	}
	
	/**
	 * function which sorts list using merge sort algorithm
	 * @param list - the given list to sort
	 */
	private void sort(ArrayList<String> list) {
		if (list.size()<=1) { return;}
		
		ArrayList<String>first = new ArrayList<String>();
		ArrayList<String>second = new ArrayList<String>();
		for (int i = 0; i<list.size()/2; i++) {
			first.add(list.get(i));
		}
		for (int j = 0; j<list.size()-first.size(); j++) {
			second.add(list.get(first.size()+j));
		}
		sort(first);
		sort(second);
		merge(first, second, list);
	}
	
	private void merge(ArrayList<String>first, ArrayList<String>second, ArrayList<String> list) {
		int i = 0;
		int j = 0;
		list.clear();
		
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
	}
}
