package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Comparator;
import java.util.HashMap;

public class HashMapHistogramComparator<T> implements Comparator<T>{

	private HashMapHistogram<T> hm;
	
	public HashMapHistogramComparator(HashMapHistogram<T> hm){
		this.hm = hm;
	} 
	
	@Override
	public int compare(T arg0, T arg1) {
		return Integer.compare(this.hm.getCountForItem(arg0),this.hm.getCountForItem(arg1));
	}
	
}
