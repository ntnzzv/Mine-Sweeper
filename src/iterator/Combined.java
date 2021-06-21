package iterator;

import java.util.Iterator;


public class Combined<E> implements Iterable<E>{

	Iterable<E> first,second;
	
	public Combined(Iterable<E> first, Iterable<E> second) {
		this.first = first;
		this.second = second;
	}

	
	private class DoubleIter implements Iterator<E>{
		Iterator<E> A = first.iterator();
		Iterator<E> B = second.iterator();
		int flag = 0;

		@Override
		public boolean hasNext() {
			return(A.hasNext() || B.hasNext()); // uses Iterable methods to check if next exists
		}

		@Override
		public E next() {
			
			if(A.hasNext() && B.hasNext()) { // uses flags to shuffle between the iterators
				if(flag == 0) {
					flag = 1;
					return A.next();
					}
				
				flag = 0;
				return B.next();
			}
			if(A.hasNext()) { return A.next();} // returns the leftover values;
			
			return B.next();
			}
		
		
	}
	@Override
	public Iterator<E> iterator() {
		return new DoubleIter();
	}
	
	
}
