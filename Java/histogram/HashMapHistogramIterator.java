package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashMapHistogramIterator<T> implements Iterator<T>{
	
	private Set<T> set;
	private HashMapHistogram<T> hmH;
	private List<T> list;
	private Iterator<T> iter;
	
	public HashMapHistogramIterator(HashMapHistogram<T> h){
		this.set = h.getItemsSet();
		list = new ArrayList<T>(this.set);
		Collections.sort(list, new HashMapHistogramComparator<T>(this.hmH));
		this.iter = list.iterator();
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean hasNext() {
		return this.iter.hasNext();
	}

	@Override
	public T next() {
		return this.iter.next();
	}
}
