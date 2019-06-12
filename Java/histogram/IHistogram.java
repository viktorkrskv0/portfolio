package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.Set;


public interface IHistogram<T> extends Iterable<T> {
	public void addItem(T item);
	public void addItemKTimes(T item, int k) throws IllegalKValue;
	public void addAll(Collection<T> items);
	public int getCountForItem(T item);
	public void clear();
	public Set<T> getItemsSet();

}
