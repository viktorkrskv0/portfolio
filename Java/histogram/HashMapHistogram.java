package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HashMapHistogram<T> implements IHistogram<T> {

	private HashMap<T, Integer> hm;

	public HashMapHistogram(){
		this.hm = new HashMap<T, Integer>();
	}
	
	public void addItem(T item) {
		if (hm.containsKey(item)) {
			this.hm.put(item, hm.get(item) + 1);
		} else {
			this.hm.put(item, 1);
		}
	}

	public void addItemKTimes(T item, int k) throws IllegalKValue {
		if (k <= 0) {
			throw new IllegalKValue(k);
		}
		if (hm.containsKey(item)) {
			this.hm.put(item, hm.get(item) + k);
		} else {
			this.hm.put(item, k);
		}

	}

	public void addAll(Collection<T> items) {
		Iterator<T> iter = items.iterator();
		while (iter.hasNext()) {
			addItem(iter.next());
		}
	}

	public int getCountForItem(T item) {
		if (hm.containsKey(item)) {
			return this.hm.get(item);
		}
		return 0;
	}

	public void clear() {
		this.hm.clear();
	}

	public Set<T> getItemsSet() {
		return this.hm.keySet();
	}

	@Override
	public Iterator<T> iterator() {
		Set<T> set =this.getItemsSet();
		Iterator<T> iter = set.iterator();
		return iter;
	}

}
